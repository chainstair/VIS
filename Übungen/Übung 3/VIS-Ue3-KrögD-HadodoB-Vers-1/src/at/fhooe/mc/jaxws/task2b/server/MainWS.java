package at.fhooe.mc.jaxws.task2b.server;

import javax.xml.ws.Endpoint;

public class MainWS {

	public static void main(String[] args) {
		EnvironmentData envData = new EnvironmentData();
		Endpoint endpoint = Endpoint.publish("http://localhost:8081/EnvironmentData", envData);
		System.out.println("server up and running ... ");
	}
}