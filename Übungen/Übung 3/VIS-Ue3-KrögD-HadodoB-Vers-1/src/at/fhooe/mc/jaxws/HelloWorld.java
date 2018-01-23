package at.fhooe.mc.jaxws;

import javax.jws.*;

@WebService(endpointInterface="at.fhooe.mc.jaxws.ISimpleInterface")
public class HelloWorld implements ISimpleInterface {
	
	@WebMethod
	public String saySomething() {
		return"HelloWorld";
	}
	
	@WebMethod
	public DummyData getData(String _name) {
		return new DummyData("Dies ist ein Test", System.currentTimeMillis());
	}
}