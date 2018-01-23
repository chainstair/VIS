package at.fhooe.mc.jaxws.client;

public class DummyClient {
	public static void main(String[] _argv) {
		HelloWorldService srv = new HelloWorldService();
		HelloWorld soap = srv.getHelloWorldPort();
		System.out.println("server --> " + soap.saySomething());
	}
}
