package at.fhooe.mc.api;

import java.rmi.RemoteException;

public class Client_PDF {
	public static void main(String[] _argv) {
		IEnvService service = new MyJavaClient();
			while (true) {
			String[] sensors = null;
			try {
				sensors = service.requestEnvironmentDataTypes();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				System.out.println("ErrorCatch");
				e.printStackTrace();
			}
			for(String sensor : sensors) {
				EnvData dataO = null;
				try {
					dataO = service.requestEnvironmentData(sensor);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.print(dataO);
				System.out.println();
				System.out.println("*****************************");
			 }
			 // for sensor
			 System.out.println();
			 System.out.println();
			 EnvData[] dataOs = null;
			try {
				dataOs = service.requestAll();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 for (EnvData dataO : dataOs) {
				 System.out.println(dataO);
			 } // for data
			 try {
				 Thread.sleep(1000);
			 } catch (Exception _e) {
				 _e.printStackTrace();
			 }
		}
	}
}
