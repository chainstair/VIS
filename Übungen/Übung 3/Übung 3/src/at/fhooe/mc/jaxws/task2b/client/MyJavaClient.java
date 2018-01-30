package at.fhooe.mc.jaxws.task2b.client;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

import at.fhooe.mc.jaxws.task2b.service.EnvData;
import at.fhooe.mc.jaxws.task2b.service.IEnvService;


public class MyJavaClient implements IEnvService{
	private PrintWriter mOutput;
	private BufferedReader mIn;
	private Socket socket;
	int mPortNumber;
	String mHost;
	
	
	public MyJavaClient(String _ipAddress, int _portNumber){
	    mPortNumber = _portNumber;
		mHost = _ipAddress;

	    try {
            this.socket = new Socket(mHost, mPortNumber);
            this.mOutput = new PrintWriter(socket.getOutputStream(), true);
            this.mIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("IP: " + mHost);
            System.exit(1);
        } 
    }



	@Override
	public EnvData requestEnvironmentData(String _type) {
        char[] buffer=new char[100];
		EnvData data = null;
		StringBuilder stringbuilder = new StringBuilder();
    	stringbuilder.append("sensor;" + _type + "#\0");
        mOutput.println(stringbuilder.toString());
        try {
			mIn.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String msg = new String(buffer).split("#")[0];
        System.out.println(msg);
        if (_type.equals("noise")){
        	String[] splitMsg = msg.split("\\|");
        	data = new NoiseData(splitMsg[0],Integer.parseInt(splitMsg[1]));
        }
        else if (_type.equals("air")){
        	String[] splitMsg = msg.split("\\|");
        	String[] messageVal = splitMsg[1].split(";");
        	data = new AirData(splitMsg[0],Integer.parseInt(messageVal[0]),Integer.parseInt(messageVal[1]));
        }
        else if (_type.equals("light")){
        	String[] splitMsg = msg.split("\\|");
        	data = new LightData(splitMsg[0],Integer.parseInt(splitMsg[1]));
        }
        else {
        	System.out.println("Error");
        }
        return data;
	}

	@Override
	public EnvData[] requestAll() {
		String[] sensorPack = requestEnvironmentDataTypes();
		int length = sensorPack.length;
		EnvData[] data = new EnvData[length];
		for (int i = 0; i < sensorPack.length; i++){
			 EnvData singleData = requestEnvironmentData(sensorPack[i]);
			 data[i] = singleData;
		}
		return data;
	}
	
	@Override
	public String[] requestEnvironmentDataTypes() {
        char[] buffer=new char[100];
		StringBuilder stringbuilder = new StringBuilder();
    	stringbuilder.append("sensortypes#\0");
        mOutput.println(stringbuilder.toString());
        try {
        	mIn.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String msg = new String(buffer).split("#")[0];
        System.out.println(msg);
        return msg.split(";");
	}
}
