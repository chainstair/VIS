package at.fhooe.mc.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class RemoteClient {

	public static void main(String[] _args){
		try{
			String adr = "saySomething";
			Registry reg = LocateRegistry.getRegistry();
			RMIRemote rs = (RMIRemote) reg.lookup(adr);
			rs.saySomething();
		}
		catch (Exception _e) {
			_e.printStackTrace();
		}
	}
}
