package at.fhooe.mc.jaxws.service;

import javax.xml.bind.annotation.*;

/**
 * 
 * @author David
 *
 */
@XmlRootElement
public class EnvData{
	@XmlElement
	long mTimestamp;
	
	@XmlElement
	int mValue;
	
	public EnvData(){}
	public EnvData(int _value, long _time) {
		mValue= _value;
		mTimestamp= _time;
	}
	
	public String toString() {
		return mTimestamp +" | " + mValue;
	}
}


