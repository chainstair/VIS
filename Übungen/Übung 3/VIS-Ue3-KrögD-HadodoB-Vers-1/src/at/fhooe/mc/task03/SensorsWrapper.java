package at.fhooe.mc.task03;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "mSensors")
@XmlAccessorType(XmlAccessType.FIELD)
public class SensorsWrapper {
    @XmlElement(name = "sensor")
    public EnvData[] mSensors;

    /**
     * Constructor for SensorsWrapper
     */
    public SensorsWrapper() {
    }

    /**
     * Constructor for SensorsWrapper
     * @param _sensors sensors
     */
    public SensorsWrapper(EnvData[] _sensors) {
        this.mSensors = _sensors;
    }
}
