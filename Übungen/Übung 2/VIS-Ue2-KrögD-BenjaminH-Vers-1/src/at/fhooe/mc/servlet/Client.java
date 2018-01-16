package at.fhooe.mc.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * @author David
 *
 */
public class Client implements IEnvService {
	
	private Socket mSocket;
	private PrintWriter mOutput;
	private BufferedReader mInput;
	
	public Client(String _ipAddress, int _port) {
		initializeSocket(_ipAddress, _port);
	}
	
	public void initializeSocket(String _ipAddress, int _port) {
		try {
			mSocket = new Socket(_ipAddress, _port);
			mOutput = new PrintWriter(mSocket.getOutputStream(), true);
			mInput = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
		} catch (IOException _e) {
			_e.printStackTrace();
		}
	}

	@Override
	public String[] requestEnvironmentDataTypes() throws RemoteException {
        char[] buffer=new char[100];
		StringBuilder stringbuilder = new StringBuilder();
    	stringbuilder.append("sensortypes#\0");
        mOutput.println(stringbuilder.toString());
        try {
        	mInput.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String msg = new String(buffer).split("#")[0];
        System.out.println(msg);
        return msg.split(";");
	}


	@Override
	public EnvData requestEnvironmentData(String _type) throws RemoteException {
		char[] buffer=new char[100];
		EnvData data = new EnvData();
		StringBuilder stringbuilder = new StringBuilder();
    	stringbuilder.append("sensor;" + _type + "#\0");
        mOutput.println(stringbuilder.toString());
        try {
			mInput.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String msg = new String(buffer).split("#")[0];
        System.out.println(msg);
        if (_type.equals("noise")){
        	String[] splitMsg = msg.split("\\|");
        	data.setmTimestamp(Integer.parseInt(splitMsg[0]));
        	data.setmNoise(Integer.parseInt(splitMsg[1]));
        }
        else if (_type.equals("air")){
        	String[] splitMsg = msg.split("\\|");
        	String[] messageVal = splitMsg[1].split(";");
        	data.setmTimestamp(Integer.parseInt(splitMsg[0]));
        	int[] airArray = {Integer.parseInt(messageVal[0]),Integer.parseInt(messageVal[1])};
        	data.setmAir(airArray);
        }
        else if (_type.equals("light")){
        	String[] splitMsg = msg.split("\\|");
        	data.setmTimestamp(Integer.parseInt(splitMsg[0]));
        	data.setmLight(Integer.parseInt(splitMsg[1]));
        }
        else {
        	System.out.println("Error");
        }
        return data;
	}

	@Override
	public EnvData[] requestAll() throws RemoteException {
		String[] sensorPack = requestEnvironmentDataTypes();
		int length = sensorPack.length;
		EnvData[] data = new EnvData[length];
		for (int i = 0; i < sensorPack.length; i++){
			 EnvData singleData = requestEnvironmentData(sensorPack[i]);
			 data[i] = singleData;
		}
		return data;
	}

}
