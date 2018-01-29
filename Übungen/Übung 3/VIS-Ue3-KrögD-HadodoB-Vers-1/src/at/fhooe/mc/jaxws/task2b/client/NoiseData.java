package at.fhooe.mc.jaxws.task2b.client;

import at.fhooe.mc.jaxws.task2b.service.EnvData;

public class NoiseData extends EnvData {
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
