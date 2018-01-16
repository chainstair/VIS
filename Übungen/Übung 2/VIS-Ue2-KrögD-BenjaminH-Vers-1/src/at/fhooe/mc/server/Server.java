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
			System.out.println(data.toString());
		} else {
			System.out.println("Error");
		}
		
		return data;
	}

	@Override
	public String[] requestEnvironmentDataTypes() throws RemoteException {
<<<<<<< HEAD

		return msg.split(";");
=======
		System.out.println("Sensortypes: ");
		for (String type : mSensorTypes){
			System.out.println(type + " ");
		}
		return mSensorTypes;
>>>>>>> 9cb7a6a6326c38d66b7093bda2e253c25acf54b4
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
