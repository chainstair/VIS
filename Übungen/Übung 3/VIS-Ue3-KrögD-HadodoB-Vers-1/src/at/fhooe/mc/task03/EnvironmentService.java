package at.fhooe.mc.task03;

import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Path("/EnvironmentService")
public class EnvironmentService extends Application {
    public static ArrayList<EnvData> mEnvDataArrayList = new ArrayList<EnvData>();

    public EnvironmentService() {
        if(mEnvDataArrayList.size() == 0){
            EnvData temperature = new EnvData();
            temperature.setSensorName("temperature");
            temperature.setDate(new Date());
            temperature.setValues(new int[]{20,18,17,15,22,31,27});

            EnvData pressure = new EnvData();
            pressure.setSensorName("pressure");
            pressure.setDate(new Date());
            pressure.setValues(new int[]{1033,1031,1027,1035,1039});

            mEnvDataArrayList.add(temperature);
            mEnvDataArrayList.add(pressure);
        }
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getOverview() {
        return "<head></head><body><h1>Overview</h1><h2>"+ mEnvDataArrayList.size()+" Elements are saved inside the array.</h2</body>";
    }

    @GET
    @Path("/sensors")
    @Produces(MediaType.APPLICATION_XML)
    public String getSensorTypes() {
        List<String> sensorNames = mEnvDataArrayList.stream().map(EnvData::getSensorName).collect(Collectors.toList());

        SensorNamesWrapper sensorNamesWrapper = new SensorNamesWrapper(sensorNames.toArray(new String[0]));
        String dataToSend = "";
        try {
            JAXBContext jc = JAXBContext.newInstance(new Class[]{SensorNamesWrapper.class});

            Marshaller m = jc.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter sw = new StringWriter();
            m.marshal(sensorNamesWrapper, sw);
            dataToSend = sw.getBuffer().toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return dataToSend;
    }

    /**
     * @param _SensorName name of sensor
     * @return returns environment data for given sensor in XML format
     */
    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_XML)
    public String getSensorData(@PathParam("name")String _SensorName) {
        EnvData sensor = null;
        for (EnvData envData : mEnvDataArrayList) {
            if(envData.getSensorName().equals(_SensorName)){
                sensor = envData;
            }
        }

        String dataToSend = "";
        if(sensor != null) {
            try {
                JAXBContext jc = JAXBContext.newInstance(new Class[]{EnvData.class});

                Marshaller m = jc.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                StringWriter sw = new StringWriter();
                m.marshal(sensor, sw);
                dataToSend = sw.getBuffer().toString();

            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }

        return dataToSend;
    }

    /**
     * @return returns data from all mSensors in XML format
     */
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_XML)
    public String getAllSensorData() {
        EnvData[] sensors = mEnvDataArrayList.toArray(new EnvData[0]);

        String dataToSend = "";
        if(sensors.length!=0) {
            SensorsWrapper sensorsWrapper = new SensorsWrapper(sensors);
            try {
                JAXBContext jc = JAXBContext.newInstance(new Class[]{SensorsWrapper.class});

                Marshaller m = jc.createMarshaller();
                m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

                StringWriter sw = new StringWriter();
                m.marshal(sensorsWrapper, sw);
                dataToSend = sw.getBuffer().toString();

            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }

        return dataToSend;
    }


    /**
     * @param _envData environment data you want to post
     * @return returns the posted data in XML format
     */
    @POST
    @Path("/values")
    @Consumes(MediaType.APPLICATION_XML)
    public String postValues(EnvData _envData) {
        if(_envData!=null && _envData.getValues().length!=0){
            mEnvDataArrayList.forEach((a) -> {
                if(a.getSensorName().equals(_envData.getSensorName())){
                    int[] newValues = new int[a.getValues().length+1];
                    for (int i = 0; i < a.getValues().length; i++) {
                        newValues[i] = a.getValues()[i];
                    }
                    newValues[a.getValues().length] = _envData.getValues()[0];
                    a.setValues(newValues);
                }
            });
        }
        return "";
    }
}
