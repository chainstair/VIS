package at.fhooe.mc.jaxws.task2b.client;

import at.fhooe.mc.jaxws.task2b.service.EnvData;

public class AirData extends EnvData {

	private int mAir;
	private int mPollution;
	private int mTime;
	
	public AirData(int _time, int _air, int _pollution){
		mAir = _air;
		mPollution = _pollution;
		mTime = _time;
	}
		
	public String toString(){
		return("AirData" + ": " + mTime + "|" + "  Air: " + mAir + "  Pollution: " + mPollution);
	}

	@Override
	public long getTimestamp() {
		return mTime;
	}

	@Override
	public int getValue() {
		return mAir;
	}

	@Override
	public String getType() {
		return "air";
	}
}
