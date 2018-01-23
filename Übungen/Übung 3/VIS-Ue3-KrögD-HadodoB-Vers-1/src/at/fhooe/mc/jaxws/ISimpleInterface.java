package at.fhooe.mc.jaxws;

import javax.jws.*;

@WebService
public interface ISimpleInterface {
	String saySomething();
	DummyData getData(String _name);
}