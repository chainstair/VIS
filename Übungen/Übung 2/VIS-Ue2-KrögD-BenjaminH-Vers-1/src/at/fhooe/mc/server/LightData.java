package at.fhooe.mc.server;

public class LightData implements EnvData {
	
	private int mTime;
	private int mLight;

	public LightData(int _time,int _light){
		mTime = _time;
		mLight = _light;
	}
	
	public String toString(){
		return("LightData" + ": " + mTime + "|" + "Light: " + mLight);
	}
}
