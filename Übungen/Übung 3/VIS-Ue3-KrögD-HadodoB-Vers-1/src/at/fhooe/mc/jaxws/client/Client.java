package at.fhooe.mc.jaxws.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import at.fhooe.mc.jaxws.*;

public class Client {
	
	ISimpleInterface mSOAP;
	
	public Client() throws MalformedURLException {
		Service service = Service.create(
				new URL("http://localhost:8081/HelloWorld?wsdl"), 
				new QName("http://jaxws.mc.fhooe.at/", "HelloWorldService"));
		mSOAP = service.getPort(ISimpleInterface.class);
	}
	/*
	HelloWorldService srv = new HelloWorldService();
	HelloWorld soap = srv.getHelloWorldPort();
	System.out.println("server --> " + soap.saySomething());
	*/
}
