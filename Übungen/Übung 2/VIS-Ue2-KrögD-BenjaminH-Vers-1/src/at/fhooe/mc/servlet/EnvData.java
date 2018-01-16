package at.fhooe.mc.servlet;

public class EnvData {
<<<<<<< HEAD

=======
    private long mTimestamp;
    private int[] mAir;
    private int mNoise;
    private int mLight;
    private int mPressure;

	public EnvData(long _mTimestamp, int _mLight, int _mNoise, int[] _mAir, int _mPressure) {
		super();
		this.mTimestamp = _mTimestamp;
		this.mAir = _mAir;
		this.mNoise = _mNoise;
		this.mLight = _mLight;
		this.mPressure = _mPressure;
	}

	public EnvData() {
		super();
		mTimestamp = 0;
		mAir = null;
		mNoise = 0;
		mLight = 0;
		mPressure = 0;
	}

	public EnvData(long mTimestamp) {
		super();
		this.mTimestamp = mTimestamp;
		mAir = null;
		mNoise = 0;
		mLight = 0;
		mPressure = 0;
	}

	public Long getmTimestamp() {
		return mTimestamp;
	}

	public void setmTimestamp(long mTimestamp) {
		this.mTimestamp = mTimestamp;
	}

	public int[] getmAir() {
		return mAir;
	}

	public void setmAir(int[] mAir) {
		this.mAir = mAir;
	}

	public float getmNoise() {
		return mNoise;
	}

	public void setmNoise(int mNoise) {
		this.mNoise = mNoise;
	}

	public float getmLight() {
		return mLight;
	}

	public void setmLight(int mLight) {
		this.mLight = mLight;
	}

	public float getmPressure() {
		return mPressure;
	}

	public void setmPressure(int mPressure) {
		this.mPressure = mPressure;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		if (mTimestamp != 0){
			stringBuilder.append("TimeStamp: " + mTimestamp + "\n");
		}
		if (mAir != null){
			stringBuilder.append("Air: ");
			for (int val : mAir){
				stringBuilder.append(val + " ");
			}
			stringBuilder.append("\n");
		}
		if (mNoise != 0){
			stringBuilder.append("Noise: " + mNoise + "\n");
		}
		if (mLight != 0){
			stringBuilder.append("Light: " + mLight + "\n");
		}
		if (mPressure != 0){
			stringBuilder.append("Pressure: " + mPressure + "\n");
		}
		return stringBuilder.toString();
	}
	
	public String getType() {
		if (mAir != null){
			return "Air";
		}
		else if (mNoise != 0){
			return "Noise";
		}
		else if (mLight != 0){
			return "Light";
		}
		else if (mPressure != 0){
			return "Pressure";
		}
		else {
			return null;
		}
	}
	
    public String getData() {
    	StringBuilder stringBuilder = new StringBuilder();
		if (mAir != null){
			for (int val : mAir){
				stringBuilder.append(val + " ");
			}
		}
		if (mNoise != 0){
			stringBuilder.append(mNoise);
		}
		if (mLight != 0){
			stringBuilder.append(mLight);
		}
		if (mPressure != 0){
			stringBuilder.append(mPressure);
		}
    	return stringBuilder.toString();
    }	
>>>>>>> 0cea64ffb33945e71bea7f407e1120be5315416e
}
