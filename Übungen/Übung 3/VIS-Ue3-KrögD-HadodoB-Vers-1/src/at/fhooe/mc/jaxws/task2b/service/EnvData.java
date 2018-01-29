package at.fhooe.mc.jaxws.task2b.service;

import javax.xml.bind.annotation.*;

/**
 * 
 * @author David
 *
 */
@XmlRootElement
public class EnvData {
	@XmlElement
	long mTimestamp;

	@XmlElement
	int mValue;

	@XmlElement
	String mLocation;

	public EnvData() {
	}

	public EnvData(int _value, long _time, String _location) {
		mValue = _value;
		mTimestamp = _time;
		mLocation = _location;
	}

	public long getTimestamp() {
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
}
