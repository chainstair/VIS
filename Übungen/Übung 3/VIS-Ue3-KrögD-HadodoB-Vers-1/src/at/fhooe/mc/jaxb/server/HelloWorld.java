package at.fhooe.mc.jaxb.server;

import javax.jws.*;

import at.fhooe.mc.jaxws.service.ISimpleInterface;

@WebService(endpointInterface="at.fhooe.mc.jaxws.service.ISimpleInterface")
public class HelloWorld implements ISimpleInterface{
	
	@Override
	@WebMethod
	public String saySomething() {
		return"HelloWorld";
	}
	
	@Override
	@WebMethod
	public DummyData getData(String _name) {
		return new DummyData("Dies ist ein Test", System.currentTimeMillis());
	}
}