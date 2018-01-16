package at.fhooe.mc.servlet;

import java.rmi.*;

/**
 * 
 * @author David
 *
 */
public interface IEnvService extends Remote{
	
	/**
	* Liefert die Typen der zur Verfügung stehenden Umweltsensoren
	*
	* @return Ein String-Array mit den Typen der Umweltsensoren
	* @throws RemoteException Ein Fehler trat bei der Kommunikation auf
	* @see java.lang.String
	* @see java.rmi.RemoteException
	*/
	public String[] requestEnvironmentDataTypes() throws RemoteException;
	
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
	public EnvData requestEnvironmentData(String _type) throws RemoteException;
	
	/**
	* Liefert die Messwerte aller verfügbarer Sensoren zurück
	*
	* @return EnvData[] alle verfügbaren Messwerte
	* @throws RemoteException Ein Fehler trat bei der Kommunikation auf
	* @see at.fhooe.mc.vis.EnvData
	* @see java.lang.String
	* @see java.rmi.RemoteException
	*/
	public EnvData[] requestAll() throws RemoteException;
}
