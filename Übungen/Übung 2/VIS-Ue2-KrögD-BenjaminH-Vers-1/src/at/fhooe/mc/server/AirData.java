package at.fhooe.mc.server;

public class AirData implements EnvData {

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
}
