package at.fhooe.mc.vis_ue4_krgd_hadodob_vers_1;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.*;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.*;
import org.androidannotations.annotations.*;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.activity_menu)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "VIS-UE4-MainActivity";
    private static final int ENABLE_PERIPHERAL_REQUEST = 32798;

    @NonConfigurationInstance
    Map<String, BTDevice> mBtDevices;
    @NonConfigurationInstance
    String currentSelectedText;
    @NonConfigurationInstance
    MyHandler mHandler;
    Messenger mBLEServiceMessenger;

    @ViewById(R.id.list_view_bt)
    ListView mListView;
    @ViewById(R.id.text_view_selected)
    TextView mTextView;

    @SystemService
    LocationManager mLocationManager;
    @SystemService
    BluetoothManager mBluetoothManager;
    @NonConfigurationInstance
    boolean mScanning;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @OnActivityResult(ENABLE_PERIPHERAL_REQUEST)
    void init() {
        if (mBtDevices == null) {
            Log.d(TAG, "creating new map!");
            mBtDevices = new HashMap<>();
        }

        if (!(checkBLEEnabled() && checkLocationEnabled())) {
            return;
        }

        if (mHandler == null) {
            Log.d(TAG, "Creating new handler!");
            mHandler = new MyHandler(this);
        } else {
            Log.d(TAG, "Using old handler!");
            mHandler.setMainActivity(this);
        }

        //startService(new Intent(this, BLEService.class).putExtra("messenger", new Messenger(mHandler)));
        BLEService_.intent(this).extra("messenger", new Messenger(mHandler)).start();
    }

    static class MyHandler extends Handler {

        private WeakReference<MainActivity> mMainActivity;

        private MyHandler(MainActivity _mainActivity) {
            super(Looper.getMainLooper());
            mMainActivity = new WeakReference<>(_mainActivity);
        }

        void setMainActivity(MainActivity _mainActivity) {
            mMainActivity = new WeakReference<>(_mainActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity mainActivity = mMainActivity.get();
            if (mainActivity == null) return;
            mainActivity.handleMessage(msg);
        }

    }

    @AfterViews
    void initListView() {
        ListAdapter adapter = getNewBTDeviceListAdapter();
        mListView.setAdapter(adapter);
    }

    private ListAdapter getNewBTDeviceListAdapter() {
        BTDevice[] btDevices = mBtDevices.values().toArray(new BTDevice[mBtDevices.size()]);
        return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, btDevices);
    }

    @AfterViews
    void initTextView() {
        if (currentSelectedText != null) {
            setText(currentSelectedText);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ENABLE_PERIPHERAL_REQUEST) init();
    }

    private boolean checkBLEEnabled() {
        BluetoothAdapter bluetoothAdapter = mBluetoothManager.getAdapter();
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(i, ENABLE_PERIPHERAL_REQUEST);
            return false;
        }
        return true;
    }

    private boolean checkLocationEnabled() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ENABLE_PERIPHERAL_REQUEST);
            return false;
        }

        return true;
    }

    @OptionsItem
    void refreshSelected() {
        if (mScanning) {
            Toast.makeText(this, "Already scanning...", Toast.LENGTH_SHORT).show();
            return;
        }
        clearList();
        startScanningService();

        Toast.makeText(this, "Started scanning...", Toast.LENGTH_SHORT).show();
        mScanning = true;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Stop scanning!");
                sendMessageToService(BLEService.STOP_SCANNING);
                mScanning = false;
            }
        }, 10000);

    }

    @OptionsItem
    void stopSelected() {
        setText("Not connected");
        sendMessageToService(BLEService.CLOSE);
    }

    private void clearList() {
        mBtDevices.clear();
        mListView.setAdapter(getNewBTDeviceListAdapter());
    }

    @ItemClick(R.id.list_view_bt)
    void listItemClicked(BTDevice _btDevice) {
        String id = _btDevice.getId();
        Toast.makeText(this, "Connecting to " + _btDevice.getDisplayName(), Toast.LENGTH_LONG).show();
        sendMessageToService(BLEService.CONNECT, id);
    }

    private void setText(String _text) {
        currentSelectedText = _text;
        mTextView.setText(_text);
    }

    private void startScanningService() {
        Log.d(TAG, "Starting intent service!");
        sendMessageToService(BLEService.START_SCANNING);
    }

    private void sendMessageToService(int _what) {
        sendMessageToService(_what, null);
    }

    private void sendMessageToService(int _what, String _data) {
        if (mBLEServiceMessenger == null) {
            Log.e(TAG, "service messenger should not be null!");
            return;
        }
        Log.i(TAG, "send message to server, action: " + _what);

        Message message = Message.obtain();
        message.what = _what;
        if (_data != null) {
            Bundle bundle = new Bundle();
            bundle.putString("value", _data);
            message.setData(bundle);
        }
        try {
            mBLEServiceMessenger.send(message);
        } catch (RemoteException _e) {
            _e.printStackTrace();
        }
    }

    private void handleMessage(Message _message) {
        switch (_message.what) {
            case BLEService.UPDATE_ACTIVITY_MESSENGER:
                mBLEServiceMessenger = _message.replyTo;
                break;
            case BLEService.SCANNING_DEVICE_FOUND:
                BTDevice btDevice = _message.getData().getParcelable("device");
                displayBluetoothDevice(btDevice);
                break;
            case BLEService.BPM_CHANGED:
                int value = _message.getData().getInt("value");
                handleBpmUpdate(value);
                break;
            case BLEService.CONNECTION_CHANGE:
                int connectionStatus = _message.getData().getInt("value");
                Log.d(TAG, "Connection change: " + connectionStatus);
                break;
        }
    }

    private void handleBpmUpdate(int _value) {
        updateBpmDisplay(_value);
    }

    private void updateBpmDisplay(int _value) {
        Log.d(TAG, "Bpm Update: " + _value);
        setText(_value + " bpm");
    }

    private void displayBluetoothDevice(BTDevice _btDevice) {
        if (_btDevice.getDisplayName() == null) {
            return;
        }
        Log.d(TAG, "New Device: " + _btDevice.toString());
        BTDevice previousValue = mBtDevices.put(_btDevice.getId(), _btDevice);
        mListView.setAdapter(getNewBTDeviceListAdapter());

//        ArrayAdapter<BTDevice> adapter = (ArrayAdapter<BTDevice>) mListView.getAdapter();
//        if (previousValue != null) {
//            adapter.remove(previousValue);
//        }
//        adapter.add(_btDevice);
//        adapter.notifyDataSetChanged();
    }
}