package at.fhooe.mc.jaxws.client;

import java.net.MalformedURLException;

public class ClientMain {

	public static void main(String[] args) throws MalformedURLException {
		Client client = new Client();
		
		System.out.println("server --> " + client.mSOAP.saySomething());
		System.out.println(client.mSOAP.getData("server").toString());

	}

}
