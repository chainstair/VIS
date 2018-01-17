package at.fhooe.mc.server;

import java.io.Serializable;
import java.util.Random;

import at.fhooe.mc.interfaces.EnvData;

/**
 * 
 * @author David
 *
 */
public class PressureData implements EnvData, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int mPressure;
	private long mTime;
	private Random mRandom = new Random();
	
	public PressureData(){
		mPressure = mRandom.nextInt() %120;
		System.out.println(mPressure);
		mTime = System.currentTimeMillis();
	}
		
	/**
	 * Formating the data of the class as String.
	 * 
	 * @return the data of the class as String object.
	 */
	public String toString(){
		return("AirData" + ": " + mTime + "|" + "  Pressure: " + mPressure);
	}

	@Override
	public long getTimestamp() {
		return mTime;
	}

	@Override
	public int getValue() {
		return mPressure;
	}

	@Override
	public String getType() {
		return "pressure";
	}
}
