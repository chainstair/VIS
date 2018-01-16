package at.fhooe.mc.server;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

import java.io.IOException;

public class Server extends UnicastRemoteObject implements IEnvService {
	private PrintWriter mOutput;
	private BufferedReader mInput;

	protected Server() throws RemoteException {
		super();
	}


	@Override
	public EnvData requestEnvironmentData(String _type) throws RemoteException {
		EnvData data = null;

		if (_type.equals("air")) {
			data = new AirData();
		} else {
			System.out.println("Error");
		}

		return data;
	}

	@Override
	public String[] requestEnvironmentDataTypes() throws RemoteException {
		char[] buffer = new char[100];
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
		for (int i = 0; i < sensorPack.length; i++) {
			EnvData singleData = requestEnvironmentData(sensorPack[i]);
			data[i] = singleData;
		}
		return data;
	}

}
