package at.fhooe.mc.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.io.IOException;

public class Server extends UnicastRemoteObject implements IEnvService {
	private PrintWriter mOutput;
	private BufferedReader mInput;
	
	
	protected Server() throws RemoteException {
		super();
	}

	public static void main(String[] args) {
		try {
			Server server = new Server();
			Registry reg = LocateRegistry.createRegistry(3500);
			reg.rebind("envService", server);
		} catch (Exception _e) {
			_e.printStackTrace();	
		}

	}

	@Override
	public EnvData requestEnvironmentData(String _type) throws RemoteException {
		char[] buffer=new char[100];
		EnvData data = null;
		StringBuilder stringbuilder = new StringBuilder();
    	stringbuilder.append("sensor;" + _type + "#\0");
        mOutput.println(stringbuilder.toString());
        try {
			mInput.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String msg = new String(buffer).split("#")[0];
        System.out.println(msg);
        if (_type.equals("noise")){
        	String[] splitMsg = msg.split("\\|");
        	data = new NoiseData(Integer.parseInt(splitMsg[0]),Integer.parseInt(splitMsg[1]));
        }
        else if (_type.equals("air")){
        	String[] splitMsg = msg.split("\\|");
        	String[] messageVal = splitMsg[1].split(";");
        	data = new AirData(Integer.parseInt(splitMsg[0]),Integer.parseInt(messageVal[0]),Integer.parseInt(messageVal[1]));
        }
        else if (_type.equals("light")){
        	String[] splitMsg = msg.split("\\|");
        	data = new LightData(Integer.parseInt(splitMsg[0]),Integer.parseInt(splitMsg[1]));
        }
        else {
        	System.out.println("Error");
        }
        return data;
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
