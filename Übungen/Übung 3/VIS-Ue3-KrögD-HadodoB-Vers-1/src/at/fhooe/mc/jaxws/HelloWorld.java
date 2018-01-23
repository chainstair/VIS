package at.fhooe.mc.jaxws;

import javax.jws.*;

@WebService	//(endpointInterface="at.fhooe.mc.vis.IEnvService")
public class HelloWorld {

	public static void main(String[] args) {
		
	}
	
	@WebMethod
	public String saySomething() {
		return"HelloWorld";
	}
	
	@WebMethod
	public DummyData getData(String _name) {
		return new DummyData("Dies ist ein Test", System.currentTimeMillis());
	}

}
