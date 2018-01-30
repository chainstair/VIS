package at.fhooe.mc.task03;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class EnvData{
	
    @XmlElement(name = "name")
    private String mSensorName;
    
    @XmlElement(name = "date")
    private Date mDate;
    
    @XmlElement(name = "values")
    private int[] mValues;

    
    public EnvData(String _sensorName, Date _date, int _valueCount) {
        this.mSensorName = _sensorName;
        this.mDate = _date;

        this.mValues = new int[_valueCount];

        for (int i = 0; i < _valueCount; i++) {
            this.mValues[i] = (int) (Math.random() * (500 - 1)) + 1;
        }
    }

    
    public EnvData() {}

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date _date) {
        this.mDate = _date;
    }

    public int[] getValues() {
        return mValues;
    }

    public void setValues(int[] _values) {
        this.mValues = _values;
    }

    public String getSensorName() {
        return mSensorName;
    }

    public void setSensorName(String _sensorName) {
        this.mSensorName = _sensorName;
    }

    @Override
    public String toString() {
        String toPrint = "";
        toPrint+= ("Sensor: "+ mSensorName + "\n");
        toPrint+= "Time: " + mDate.toString()+ "\n";
        toPrint+= "Values: ";

        for (int i = 0; i < mValues.length; i++) {
            toPrint+= mValues[i];

            if (i < mValues.length - 1) {
                toPrint += ",";
            }
        }

        toPrint+="\n";

        return toPrint;
    }
}