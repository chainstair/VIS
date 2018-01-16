package at.fhooe.mc.server;

import java.util.Random;

public class AirData implements EnvData {

	private int mPressure;
	private int mTime;
	private Random mRandom;
	
	public AirData(){
		mPressure = mRandom.nextInt() % 120;
		mTime = (int) System.currentTimeMillis();
	}
		
	public String toString(){
		return("AirData" + ": " + mTime + "|" + "  Air: " + mPressure);
	}
}
