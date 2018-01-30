package at.fhooe.mc.jaxws.service;

import javax.jws.*;

import at.fhooe.mc.jaxws.server.DummyData;

@WebService
public interface ISimpleInterface {
	String saySomething();
	DummyData getData(String _name);
}