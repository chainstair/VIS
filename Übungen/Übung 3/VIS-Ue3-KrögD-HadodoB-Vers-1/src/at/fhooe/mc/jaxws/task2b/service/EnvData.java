package at.fhooe.mc.jaxws.task2b.service;

import javax.xml.bind.annotation.*;

/**
 * 
 * @author David
 *
 */
public class EnvData {
	@XmlElement(name="date")
	String mTimestamp;

	@XmlElement(name="temp")
	int mValue;

	String mLocation;
		
	public EnvData() {
	}

	public EnvData(int _value, String _time, String _location) {
		mValue = _value;
		mTimestamp = _time;
		mLocation = _location;
	}
	
	public String getTimestamp() {
		return mTimestamp;
	}

	public int getValue() {
		return mValue;
	}

	public String getType(){
		return mLocation;
	}

	public String toString() {
		return mLocation + " | " + mTimestamp + " | " + mValue;
	}

	public void setmLocation(String mLocation) {
		this.mLocation = mLocation;
	}

	public String getmLocation() {
		return mLocation;
	}
}
