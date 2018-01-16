package at.fhooe.mc.server;

import java.io.Serializable;
import java.util.Random;

/**
 * 
 * @author David
 *
 */
public class AirData implements EnvData, Serializable {

	private int mPressure;
	private long mTime;
	private Random mRandom = new Random();
	
	public AirData(){
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
}
