package at.fhooe.mc.server;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

import at.fhooe.mc.interfaces.*;


/**
 *
 * @author David
 * 
 * Implements the server 
 *
 */
public class Server extends UnicastRemoteObject implements IEnvService {
	private String[] mSensorTypes = {"air"};
	
	protected Server() throws RemoteException {
		super();
	}

	/**
	* Liefert die Messwerte für einen speziellen Sensor in Form eines
	* Environment Data (EnvData) Objektes zurück
	*
	* @param _type der betreffende Umweltsensor
	* @return EnvData Die aktuellen Messwerte des entsprechenden Sensors
	* null, falls der Sensor nicht existiert
	* @throws RemoteException Ein Fehler trat bei der Kommunikation auf
	* @see at.fhooe.mc.vis.EnvData
	* @see java.lang.String
	* @see java.rmi.RemoteException
	*/
	@Override
	public EnvData requestEnvironmentData(String _type) throws RemoteException {
		EnvData data = null;
		
		if (_type.equals("air")) {
			data = new PressureData();
			System.out.println("" + data.toString());
		} else {
			System.out.println("Error");
		}
		
		return data;
	}
	
	/**
	* Liefert die Typen der zur Verfügung stehenden Umweltsensoren
	*
	* @return Ein String-Array mit den Typen der Umweltsensoren
	* @throws RemoteException Ein Fehler trat bei der Kommunikation auf
	* @see java.lang.String
	* @see java.rmi.RemoteException
	*/
	@Override
	public String[] requestEnvironmentDataTypes() throws RemoteException {
		System.out.println("Sensortypes: ");
		for (String type : mSensorTypes){
			System.out.println(type + " ");
		}
		return mSensorTypes;
	}

	/**
	* Liefert die Messwerte aller verfügbarer Sensoren zurück
	*
	* @return EnvData[] alle verfügbaren Messwerte
	* @throws RemoteException Ein Fehler trat bei der Kommunikation auf
	* @see at.fhooe.mc.vis.EnvData
	* @see java.lang.String
	* @see java.rmi.RemoteException
	*/
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
