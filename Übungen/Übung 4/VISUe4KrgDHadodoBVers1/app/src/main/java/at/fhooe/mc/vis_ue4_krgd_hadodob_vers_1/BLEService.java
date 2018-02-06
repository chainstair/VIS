package at.fhooe.mc.vis_ue4_krgd_hadodob_vers_1;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.*;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.*;
import android.util.Log;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.SystemService;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * BluetoothLowEnergy Heart Rate Sensor Service
 */
@EService
public class BLEService extends Service {

    public static final String TAG = "BLEService";
    public static final int SCANNING_DEVICE_FOUND = 1;
    public static final int BPM_CHANGED = 2;
    public static final int CONNECTION_CHANGE = 3;
    public static final int UPDATE_ACTIVITY_MESSENGER = 4;
    public static final int START_SCANNING = 5;
    public static final int STOP_SCANNING = 6;
    public static final int CONNECT = 7;
    public static final int DISCONNECT = 8;
    public static final int CLOSE = 9;
    public static final int RELOAD_HEART_RATE_DATA = 10;


    private BluetoothAdapter mBluetoothAdapter;

    @SystemService
    BluetoothManager mBluetoothManager;
    Handler mHandler;

    private ScanCallback mDeviceFoundCallback = new ScanCallback() {
        public void onScanResult(int callbackType, ScanResult result) {
            BluetoothDevice device = result.getDevice();
            sendData(SCANNING_DEVICE_FOUND, "device", new BTDevice(device.getAddress(), device.getName()));
        }

        public void onBatchScanResults(List<ScanResult> results) {
        }

        public void onScanFailed(int errorCode) {
        }
    };
    private boolean mScanningStarted;
    private Messenger mActivityMessenger;
    private String mDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothGattService mBluetoothSelectedService;
    private List<BluetoothGattService> mBluetoothGattServices;
    private boolean mConnected;
    private BluetoothGattCharacteristic mHeartRateCharacteristic;
    private Messenger mOwnMessenger;

    public BLEService() {
//        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.setupBluetoothAdapter();
        this.setupHandler();
        this.setForeground();

        communicateData(5);
    }

    private void setupHandler() {
        HandlerThread thread = new HandlerThread(TAG + "Looper");
        thread.start();
        mHandler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                BLEService.this.handleMessage(msg);
            }
        };
        mOwnMessenger = new Messenger(mHandler);
    }

    private void handleMessage(Message _message) {
        Log.i(TAG, "handle message, received: " + _message.what);

        switch (_message.what) {
            case START_SCANNING:
                startScanning();
                break;
            case STOP_SCANNING:
                stopScanning();
                break;
            case CONNECT:
                connect(_message.getData().getString("value"));
                break;
            case DISCONNECT:
                disconnect();
                break;
            case CLOSE:
                close();
                break;
            case RELOAD_HEART_RATE_DATA:
                reloadHeartRateData();
                break;
        }
    }

    private void setForeground() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("BLE Service running")
                .setContentText("Der BLE Service läuft!")
                .setContentIntent(pendingIntent)
                .setTicker("Ticker Text")
                .build();

        startForeground(587, notification);
    }

    private void setupBluetoothAdapter() {
        mBluetoothAdapter = this.mBluetoothManager.getAdapter();
    }

    private void setActivityMessenger(Messenger _messenger) {
        mActivityMessenger = _messenger;
        Message message = Message.obtain();
        message.what = UPDATE_ACTIVITY_MESSENGER;
        message.replyTo = mOwnMessenger;
        try {
            _messenger.send(message);
        } catch (RemoteException _e) {
            Log.e(TAG, "Couldn't set reply messenger", _e);
        }
    }

    private void sendData(int _what, String _key, Parcelable _parcelable) {
        Message message = Message.obtain();
        message.what = _what;
        Bundle bundle = new Bundle();
        bundle.putParcelable(_key, _parcelable);
        message.setData(bundle);
        sendMessage(message);
    }

    private void sendMessage(Message _message) {
        try {
            mActivityMessenger.send(_message);
        } catch (RemoteException _e) {
            _e.printStackTrace();
        }
    }

    private void sendData(int _what, String _key, int _parcelable) {
        Message message = Message.obtain();
        message.what = _what;
        Bundle bundle = new Bundle();
        bundle.putInt(_key, _parcelable);
        message.setData(bundle);
        sendMessage(message);
    }

    private boolean deviceHasBluetoothEnabled() {
        return !(mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled());
    }

    private boolean deviceSupportsBluetoothLE() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    private boolean checkBluetoothSettings() {
        return deviceHasBluetoothEnabled() && deviceSupportsBluetoothLE();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Messenger messenger = intent.getParcelableExtra("messenger");
        setActivityMessenger(messenger);

        return START_STICKY;
    }

    void startScanning() {
        if (!this.checkBluetoothSettings() || mScanningStarted) {
            return;
        }
        mScanningStarted = true;
        ScanSettings.Builder builder = new ScanSettings.Builder();
        builder.setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY);
        ScanFilter.Builder filterBuilder = new ScanFilter.Builder().setServiceUuid(new ParcelUuid(BleDefinedUUIDs.Service.HEART_RATE));

        mBluetoothAdapter.getBluetoothLeScanner().startScan(Collections.singletonList(filterBuilder.build()), builder.build(), mDeviceFoundCallback);
    }

    void stopScanning() {
        if (!mScanningStarted && !this.checkBluetoothSettings()) {
            return;
        }

        mScanningStarted = false;
        mBluetoothAdapter.getBluetoothLeScanner().stopScan(mDeviceFoundCallback);
    }

    public void connect(final String deviceAddress) {
        if (mBluetoothAdapter == null || deviceAddress == null) return;

        mDeviceAddress = deviceAddress;

        // check if we need to connect from scratch or just reconnect to previous device
        if (mBluetoothGatt != null && mBluetoothGatt.getDevice().getAddress().equals(deviceAddress)) {
            // just reconnect
            mBluetoothGatt.connect();
        } else {
            // connect from scratch
            // get BluetoothDevice object for specified address
            mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);
            if (mBluetoothDevice == null) {
                // we got wrong address - that device is not available!
                return;
            }
            // connect with remote device
            mBluetoothGatt = mBluetoothDevice.connectGatt(this, false, mBleCallback);
        }
    }

    public void disconnect() {
        if (mBluetoothGatt != null) mBluetoothGatt.disconnect();
    }

    public void close() {
        if (mBluetoothGatt != null) mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    public void reloadHeartRateData() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null || mHeartRateCharacteristic == null)
            return;

        mBluetoothGatt.readCharacteristic(mHeartRateCharacteristic);
        // new value available will be notified in Callback Object
    }


    private final BluetoothGattCallback mBleCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                mConnected = true;
                sendData(CONNECTION_CHANGE, "value", 1);
                // in our case we would also like automatically to call for services discovery
                startServicesDiscovery();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                sendData(CONNECTION_CHANGE, "value", 0);
                mConnected = false;
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                // now, when services discovery is finished, we can call getServices() for Gatt
                BLEService.this.onServicesDiscovered();

                startMonitoringHeartRateCharacteristic();
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            // we got response regarding our request to fetch characteristic value
            if (status == BluetoothGatt.GATT_SUCCESS) {
                // and it success, so we can get the value
                onCharacteristicValueRead(characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            // characteristic's value was updated due to enabled notification, lets get this value
            // the value itself will be reported to the UI inside onCharacteristicValueRead
            onCharacteristicValueRead(characteristic);
        }
    };

    /* enables/disables notification for characteristic */
    public void setNotificationForCharacteristic(BluetoothGattCharacteristic ch, boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) return;

        boolean success = mBluetoothGatt.setCharacteristicNotification(ch, enabled);
        if (!success) {
            Log.e("------", "Seting proper notification status for characteristic failed!");
        }

        // This is also sometimes required (e.g. for heart rate monitors) to enable notifications/indications
        // see: https://developer.bluetooth.org/gatt/descriptors/Pages/DescriptorViewer.aspx?u=org.bluetooth.descriptor.gatt.client_characteristic_configuration.xml
        BluetoothGattDescriptor descriptor = ch.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));
        if (descriptor != null) {
            byte[] val = enabled ? BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE : BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE;
            descriptor.setValue(val);
            mBluetoothGatt.writeDescriptor(descriptor);
        }
    }

    private void startMonitoringHeartRateCharacteristic() {
        for (BluetoothGattService bluetoothGattService : mBluetoothGattServices) {
            for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService.getCharacteristics()) {
                if (bluetoothGattCharacteristic.getUuid().compareTo(BleDefinedUUIDs.Characteristic.HEART_RATE_MEASUREMENT) == 0) {
                    mHeartRateCharacteristic = bluetoothGattCharacteristic;
                    setNotificationForCharacteristic(bluetoothGattCharacteristic, true);
                    break;
                }
            }
        }
    }

    private void stopMonitoringHeartRateService() {
        if (mHeartRateCharacteristic != null) {
            setNotificationForCharacteristic(mHeartRateCharacteristic, false);
        }
    }

    private void onServicesDiscovered() {
        if (mBluetoothGattServices != null && mBluetoothGattServices.size() > 0)
            mBluetoothGattServices.clear();
        // keep reference to all services in local array:
        if (mBluetoothGatt != null) mBluetoothGattServices = mBluetoothGatt.getServices();
    }

    /* get all characteristic for particular service and pass them to the UI callback */
    public void getCharacteristicsForService(final BluetoothGattService service) {
        if (service == null) return;
        List<BluetoothGattCharacteristic> chars = null;

        chars = service.getCharacteristics();
        // keep reference to the last selected service
        mBluetoothSelectedService = service;
    }

    /* get characteristic's value (and parse it for some types of characteristics)
     * before calling this You should always update the value by calling requestCharacteristicValue() */
    public void onCharacteristicValueRead(BluetoothGattCharacteristic ch) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null || ch == null) return;

        byte[] rawValue = ch.getValue();
        String strValue = null;
        int intValue = 0;

        // lets read and do real parsing of some characteristic to get meaningful value from it
        UUID uuid = ch.getUuid();
        Log.i(TAG, "received new characteristic value " + Arrays.toString(rawValue));

        if (uuid.equals(BleDefinedUUIDs.Characteristic.HEART_RATE_MEASUREMENT)) { // heart rate
            // follow https://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.heart_rate_measurement.xml
            // first check format used by the device - it is specified in bit 0 and tells us if we should ask for index 1 (and uint8) or index 2 (and uint16)
            int index = ((rawValue[0] & 0x01) == 1) ? 2 : 1;
            // also we need to define format
            int format = (index == 1) ? BluetoothGattCharacteristic.FORMAT_UINT8 : BluetoothGattCharacteristic.FORMAT_UINT16;
            // now we have everything, get the value
            intValue = ch.getIntValue(format, index);
            strValue = intValue + " bpm"; // it is always in bpm units
            Log.i(TAG, "received new heart rate value " + strValue);
            sendData(BPM_CHANGED, "value", intValue);
            communicateData(intValue);
        }
    }

    private void startServicesDiscovery() {
        if (mBluetoothGatt != null) mBluetoothGatt.discoverServices();
    }


    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        this.disconnect();
        this.close();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent _intent) {
        return null;
    }

    private static final ExecutorService webRequestExecutor = Executors.newSingleThreadExecutor();

    private void communicateData(final int _value) {
        webRequestExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Log.d(TAG, "send data to server: " + _value);
                try {
                    String data = "<envData>";
                    data += "<name>heartrate</name>";
                    data += "<timestamp>" + System.currentTimeMillis() + "</timestamp>";
                    data += "<values>" + _value + "</values>";
                    data += "</envData>";

                    URL url = new URL("http://10.29.19.114:8080/ue3-b3/EnvironmentService/Values");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setFixedLengthStreamingMode(data.length());
                    con.setRequestProperty("Content-Type", "text/xml");
                    con.connect();

                    Log.d(TAG, "data: " + data);

                    Writer out = new OutputStreamWriter(con.getOutputStream());
                    out.write(data);
                    out.flush();
                    out.close();

                    return null;
                } catch (Exception _e) {
                    Log.e(TAG, "Web error", _e);
                    return null;
                }
            }
        });
    }
}