package at.fhooe.mc.jaxws.task2b.service;

import java.rmi.*;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * 
 * @author David
 *
 */
@WebService
public interface IEnvService extends Remote{
	
	/**
	* Liefert die Typen der zur Verf�gung stehenden Umweltsensoren
	*
	* @return Ein String-Array mit den Typen der Umweltsensoren
	* @throws RemoteException Ein Fehler trat bei der Kommunikation auf
	* @see java.lang.String
	* @see java.rmi.RemoteException
	*/
	@WebMethod
	public String[] requestEnvironmentDataTypes() throws RemoteException;
	
	/**
	* Liefert die Messwerte f�r einen speziellen Sensor in Form eines
	* Environment Data (EnvData) Objektes zur�ck
	*
	* @param _type der betreffende Umweltsensor
	* @return EnvData Die aktuellen Messwerte des entsprechenden Sensors
	* null, falls der Sensor nicht existiert
	* @throws RemoteException Ein Fehler trat bei der Kommunikation auf
	* @see at.fhooe.mc.jaxws.task2b.service.vis.EnvData
	* @see java.lang.String
	* @see java.rmi.RemoteException
	*/
	@WebMethod
	public EnvData requestEnvironmentData(String _type) throws RemoteException;
	
	/**
	* Liefert die Messwerte aller verf�gbarer Sensoren zur�ck
	*
	* @return EnvData[] alle verf�gbaren Messwerte
	* @throws RemoteException Ein Fehler trat bei der Kommunikation auf
	* @see at.fhooe.mc.jaxws.task2b.service.vis.EnvData
	* @see java.lang.String
	* @see java.rmi.RemoteException
	*/
	@WebMethod
	public EnvData[] requestAll() throws RemoteException;
}
