package at.fhooe.mc.jaxws;

import javax.jws.*;
import javax.xml.ws.Endpoint;

@WebService(endpointInterface="at.fhooe.mc.jaxws.ISimpleInterface")
public class HelloWorld {
	
	@WebMethod
	public String saySomething() {
		return"HelloWorld";
	}
	
	@WebMethod
	public DummyData getData(String _name) {
		return new DummyData("Dies ist ein Test", System.currentTimeMillis());
	}

}