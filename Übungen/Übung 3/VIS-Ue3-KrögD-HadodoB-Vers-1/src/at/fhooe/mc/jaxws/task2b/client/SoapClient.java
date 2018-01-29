package at.fhooe.mc.jaxws.task2b.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import at.fhooe.mc.jaxws.task2b.service.IEnvService;


public class SoapClient {
	
	private IEnvService mSOAP;
	
	public SoapClient() throws MalformedURLException {
		Service service = Service.create(
				new URL("http://localhost:8081/EnvironmentData?wsdl"), 
				new QName("http://server.task2b.jaxws.mc.fhooe.at/", "EnvironmentDataService"));
		mSOAP = service.getPort(IEnvService.class);
	}

	public IEnvService getmSOAP() {
		return mSOAP;
	}

	public void setmSOAP(IEnvService mSOAP) {
		this.mSOAP = mSOAP;
	}
}
