package at.fhooe.mc.jaxws.task2b.client;

import at.fhooe.mc.jaxws.task2b.service.EnvData;

public class LightData extends EnvData {
	
	private int mTime;
	private int mLight;

	public LightData(int _time,int _light){
		mTime = _time;
		mLight = _light;
	}
	
	public String toString(){
		return("LightData" + ": " + mTime + "|" + "Light: " + mLight);
	}

	@Override
	public long getTimestamp() {
		return mTime;
	}

	@Override
	public int getValue() {
		return mLight;
	}

	@Override
	public String getType() {
		return "light";
	}
}
