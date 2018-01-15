package at.fhooe.mc.api;

public class AirData implements EnvData {

	private int mAir;
	private int mPollution;
	private int mTime;
	private int mHumidity;
	
	public AirData(int _pollution, int _air, int _time, int _humidity){
		mAir = _air;
		mPollution = _pollution;
		mTime = _time;
		mHumidity = _humidity;
	}
		
	public String toString(){
		return("AirData" + ": " + mTime + "|" + "Humidity: " + mHumidity + "  Pollution: " + mPollution + "  Air: " + mAir);
	}
}
