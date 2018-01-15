package at.fhooe.mc.api;

public class Client_PDF {
	public static void main(String[] _argv) {
		IEnvService service = new MyJavaClient();
			while (true) {
			String[] sensors = service.requestEnvironmentDataTypes();
			for(String sensor : sensors) {
				EnvData dataO = service.requestEnvironmentData(sensor);
				System.out.print(dataO);
				System.out.println();
				System.out.println("*****************************");
			 }
			 // for sensor
			 System.out.println();
			 System.out.println();
			 EnvData[] dataOs = service.requestAll();
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
