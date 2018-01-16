package at.fhooe.mc.server;

import java.io.Serializable;
import java.util.Random;

public class AirData implements EnvData, Serializable {

	private int mPressure;
	private long mTime;
	private Random mRandom = new Random();
	
	public AirData(){
		mPressure = mRandom.nextInt() %120;
		System.out.println(mPressure);
		mTime = System.currentTimeMillis();
	}
		
	public String toString(){
		return("AirData" + ": " + mTime + "|" + "  Air: " + mPressure);
	}
}
