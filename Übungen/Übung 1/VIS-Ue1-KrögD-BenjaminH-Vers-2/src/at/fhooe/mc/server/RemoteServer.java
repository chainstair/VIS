package at.fhooe.mc.server;

import java.rmi.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RemoteServer extends UnicastRemoteObject implements RMIRemote {

	protected RemoteServer() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		try {
			RemoteServer rs = new RemoteServer();
			Registry reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			reg.rebind("saySomething", rs);
		}
		catch (Exception _e) {
			_e.printStackTrace();
		}
	}

	public void saySomething(){
        System.out.println("Kekse!");
	}

}
