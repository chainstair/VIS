package at.fhooe.mc.server;
import java.rmi.*;

public interface RMIRemote extends Remote {
	public void saySomething() throws RemoteException;
}