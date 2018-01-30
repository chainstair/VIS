package at.fhooe.mc.jaxws.task2b.client;

import at.fhooe.mc.jaxws.task2b.service.EnvData;

public class SoapClientMain {

	public static void main(String[] args) {
		try{
			SoapClient soapClient = new SoapClient();
		
			EnvData[] data = soapClient.getmSOAP().requestAll();
			
			for (EnvData e : data){
				System.out.println(e.getTimestamp() + e.getType() + e.getValue());
			}
		}
		catch (Exception e) {
			System.out.println("schlecht)");
		}
	}

}
