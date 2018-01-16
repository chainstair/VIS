package at.fhooe.mc.server;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author David
 *
 */
public class Client {

	public static void main(String[] _args) {
		try {
			String adr = "EnvService";

			Registry reg = LocateRegistry.getRegistry();
			IEnvService server = (IEnvService)reg.lookup(adr);

			while (true) {
				String[] sensors = null;
				try {
					sensors = server.requestEnvironmentDataTypes();
					for (String s : sensors){
						System.out.println(s.toString());
					}
				} catch (RemoteException e) {
					System.out.println("ErrorCatch");
					e.printStackTrace();
				}
				for (String sensor : sensors) {
					EnvData dataO = null;
					try {
						dataO = server.requestEnvironmentData(sensor);
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					System.out.print(dataO);
					System.out.println("\n\n*****************************");
				}
				// for sensor
				System.out.println();
				System.out.println();
				EnvData[] dataOs = null;
				try {
					dataOs = server.requestAll();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				for (EnvData dataO : dataOs) {
					System.out.println(dataO);
				} // for data
				try {
					Thread.sleep(5000);
				} catch (Exception _e) {
					_e.printStackTrace();
				}
			}

		} catch (Exception _e) {
			_e.printStackTrace();
		}
	}

}
