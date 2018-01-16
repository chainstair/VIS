package at.fhooe.mc.server;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements IEnvService {
	private String[] mSensorTypes = {"air"};
	
	protected Server() throws RemoteException {
		super();
	}


	@Override
	public EnvData requestEnvironmentData(String _type) throws RemoteException {
		EnvData data = null;
		
		if (_type.equals("air")) {
			data = new AirData();
			System.out.println("" + data.toString());
		} else {
			System.out.println("Error");
		}
		
		return data;
	}

	@Override
	public String[] requestEnvironmentDataTypes() throws RemoteException {
		System.out.println("Sensortypes: ");
		for (String type : mSensorTypes){
			System.out.println(type + " ");
		}
		return mSensorTypes;
	}

	@Override
	public EnvData[] requestAll() throws RemoteException {
		String[] sensorPack = requestEnvironmentDataTypes();
		int length = sensorPack.length;
		EnvData[] data = new EnvData[length];
		for (int i = 0; i < sensorPack.length; i++) {
			EnvData singleData = requestEnvironmentData(sensorPack[i]);
			data[i] = singleData;
		}
		return data;
	}

}
