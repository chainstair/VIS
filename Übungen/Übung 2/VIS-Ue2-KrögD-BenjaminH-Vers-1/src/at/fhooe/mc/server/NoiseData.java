package at.fhooe.mc.server;

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
}
