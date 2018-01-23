package at.fhooe.mc.jaxws;

import javax.xml.ws.Endpoint;

public class MainWS {

	public static void main(String[] args) {
		HelloWorld helloWorld = new HelloWorld();
		Endpoint endpoint = Endpoint.publish("http://localhost:8081/HelloWorld", helloWorld);
		System.out.println("server up running ... ");
	}
}