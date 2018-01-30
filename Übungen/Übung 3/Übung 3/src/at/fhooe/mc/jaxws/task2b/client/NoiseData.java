package at.fhooe.mc.jaxws.task2b.client;

import at.fhooe.mc.jaxws.task2b.service.EnvData;

public class NoiseData extends EnvData {
	private String mTime;
	private int mNoise;

	public NoiseData(String _time,int _noise){
		mTime = _time;
		mNoise = _noise;
	}
	
	public String toString(){
		return("NoiseData" + ": " + mTime + "|" + "Noise: " + mNoise);
	}

	@Override
	public String getTimestamp() {
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
