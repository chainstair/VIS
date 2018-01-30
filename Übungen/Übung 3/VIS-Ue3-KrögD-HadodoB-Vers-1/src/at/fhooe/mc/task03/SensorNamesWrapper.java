package at.fhooe.mc.task03;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mSensorNames")
@XmlAccessorType(XmlAccessType.FIELD)
public class SensorNamesWrapper {
    @XmlElement(name = "sensorName")
    public String[] mSensorNames;

    /**
     * Constructor for SensorNamesWrapper
     * @param _sensorNames sensornames
     */
    public SensorNamesWrapper(String[] _sensorNames) {
        this.mSensorNames = _sensorNames;
    }

    /**
     * Constructor for SensorNamesWrapper
     */
    public SensorNamesWrapper() {
    }
}