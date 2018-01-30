package at.fhooe.mc.jaxws.task2b.server;

import java.io.Serializable;
import java.util.Random;

import at.fhooe.mc.jaxws.task2b.service.EnvData;

/**
 * 
 * @author David
 *
 */
public class PressureData extends EnvData {

	/**
	 * 
	 */
	
	private int mPressure;
	private String mTime;
	private Random mRandom = new Random();
	
	public PressureData(){
		mPressure = mRandom.nextInt() %120;
		System.out.println(mPressure);
		mTime = System.currentTimeMillis() + "";
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
	public String getTimestamp() {
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
