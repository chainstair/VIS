package at.fhooe.mc.jaxb.server;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class DummyData{
	@XmlElement
	String mText;
	
	@XmlElement
	long mTime;
	
	public DummyData() {}
	public DummyData(String _txt, long _time) {
		mText= _txt;
		mTime= _time;
	}
	
	public String toString() {
		return"DummyDatafrom(" + mTime+ ") --> " + mText;
	}
}
