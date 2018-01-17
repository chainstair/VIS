package at.fhooe.mc.api;

import at.fhooe.mc.interfaces.EnvData;

public class NoiseData implements EnvData {
	private int mTime;
	private int mNoise;

	public NoiseData(int _time,int _noise){
		mTime = _time;
		mNoise = _noise;
	}
	
	public String toString(){
		return("NoiseData" + ": " + mTime + "|" + "Noise: " + mNoise);
	}

	@Override
	public long getTimestamp() {
		return mTime;
	}

	@Override
	public int getValue() {
		return mNoise;
	}

	@Override
	public String getType() {
		return "noise";
	}
}
