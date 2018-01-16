package Helper;

import java.io.Serializable;

public class EnvData implements Serializable {

    private String mTimestamp = null;
    private float mLight = 0;
    private float mNoise = 0;
    private float[] mAir = null;
    private float mPressure = 0;

    public EnvData() {
    }

    public EnvData(String _timestamp) {
        this.mTimestamp = _timestamp;
    }

    public EnvData(String _timestamp, float _light, float _noise, float[] _air, float _pressure) {
        this.mTimestamp = _timestamp;
        this.mLight = _light;
        this.mNoise = _noise;
        this.mAir = _air;
        this.mPressure = _pressure;
    }

    public String getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(String _timestamp) {
        this.mTimestamp = _timestamp;
    }

    public float getLight() {
        return mLight;
    }

    public void setLight(float _light) {
        this.mLight = _light;
    }

    public float getNoise() {
        return mNoise;
    }

    public void setNoise(float _noise) {
        this.mNoise = _noise;
    }

    public float[] getAir() {
        return mAir;
    }

    public void setAir(float[] _air) {
        this.mAir = _air;
    }

    public float getPressure() {
        return mPressure;
    }

    public void setPressure(float mPressure) {
        this.mPressure = mPressure;
    }

    public String getType() {
        if (mLight != 0) return "Light";
        if (mNoise != 0) return "Noise";
        if (mAir != null) return "Air";
        if (mPressure != 0) return "Pressure";
        return "";
    }

    @Override
    public String toString() {
        String str = "";
        if (mTimestamp != null || mTimestamp != "") str += "Timestamp: " + mTimestamp;
        if (mLight != 0) str += "\tLight: " + mLight;
        if (mNoise != 0) str += "\tNoise: " + mNoise;
        if (mAir != null) {
            str += "\tAir: ";
            for (float a : mAir)
                str += a + "  ";
        }
        if (mPressure != 0) str += "\tPressure: " + mPressure;
        return str;
    }

    public String getData() {
        String str = "";
        if (mLight != 0) str += mLight;
        if (mNoise != 0) str += mNoise;
        if (mAir != null) {
            for (float a : mAir)
                str += a + "; ";
        }
        if (mPressure != 0) str += mPressure;
        return str;
    }
}
