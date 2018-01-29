package at.fhooe.mc.jaxws.task2b.client;

import at.fhooe.mc.jaxws.task2b.service.EnvData;

public class LightData extends EnvData {
	
	private String mTime;
	private int mLight;

	public LightData(String _time,int _light){
		mTime = _time;
		mLight = _light;
	}
	
	public String toString(){
		return("LightData" + ": " + mTime + "|" + "Light: " + mLight);
	}

	@Override
	public String getTimestamp() {
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
