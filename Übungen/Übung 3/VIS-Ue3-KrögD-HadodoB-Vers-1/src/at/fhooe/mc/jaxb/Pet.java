package at.fhooe.mc.jaxb;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlType(propOrder={"mName", "mTyp", "mID", "mBirthday", "mVaccinations"})
public class Pet {
	
	public Pet() {
		super();
	}

	@XmlAttribute
	String mNickName;
	@XmlElement(name="name")
	String mName;
	@XmlElement(name="type")
	Type mTyp;
	@XmlElement(name="id")
	String mID;
	@XmlElement(name="birthday")
	Date mBirthday;
	@XmlElementWrapper(name="vaccinations")
	@XmlElement(name="vaccination")
	String[] mVaccinations;
	
	public Pet(String mName, String mNickName, Date mBirthday, Type mTyp, String[] mVaccinations, String mID) {
		super();
		this.mName = mName;
		this.mNickName = mNickName;
		this.mBirthday = mBirthday;
		this.mTyp = mTyp;
		this.mVaccinations = mVaccinations;
		this.mID = mID;
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		stringBuilder.append("Pet: \n");
		stringBuilder.append(mNickName + ", ");
		stringBuilder.append(mName + ", ");
		stringBuilder.append(mTyp + ", ");
		stringBuilder.append(mID + ", ");
	    //stringBuilder.append(dateFormat.format(mBirthday) + ", ");
		stringBuilder.append(mBirthday.toString() + ", ");
		stringBuilder.append("{");
		for (String s : mVaccinations){
			stringBuilder.append(s);
			stringBuilder.append(", ");
		}
		stringBuilder.append("}");
		return stringBuilder.toString();
	}
}