package at.fhooe.mc.android.btleproject;

/**
 * Created by David on 30.01.18.
 */

public class BluetoothModel {
    String mName;
    String [] mSensorData;

    BluetoothModel(String _name, String[] _sensorData ){
        mName = _name;
        mSensorData = _sensorData;
    }
}
