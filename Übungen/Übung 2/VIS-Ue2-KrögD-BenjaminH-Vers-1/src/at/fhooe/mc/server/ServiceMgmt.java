package at.fhooe.mc.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import javax.imageio.spi.RegisterableService;

import at.fhooe.mc.server.Server;

/**
 * 
 * @author David
 *
 */
public class ServiceMgmt {

	public static void main(String[] args) {
		String startDialog = "Choose option\n'start' --> Start RMI-Service\n'stop' --> Stop RMI-Service\n'quit' --> Quit Programm\n";
		System.out.println(startDialog);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String lineInput = null;
		Server server = null;
		Registry reg = null;
		boolean firstStart = true;

		try {
			lineInput = in.readLine();
		} catch (IOException e) {
			System.out.println("Read Line Error");
			e.printStackTrace();
		}

		boolean writeEnabled = true;
		while (writeEnabled) {
			if (lineInput == null) {
				try {
					lineInput = in.readLine();
				} catch (IOException e) {
					System.out.println("Read Line Error");
					e.printStackTrace();
				}
			}
			switch (lineInput) {
			case "start": {
				try {
					lineInput = null;
					server = new Server();
					if (firstStart) {
						reg = LocateRegistry.createRegistry(2000);
						firstStart = false;
					} else {
						reg = LocateRegistry.getRegistry();
					}
					reg.rebind("EnvService", server);
					System.out.println("RMI - Server started!");
				} catch (RemoteException e) {
					System.out.println("Error starting RMI - Server");
					e.printStackTrace();
				}
			}
				break;
			case "stop": {
				if (server != null && reg != null) {
					try {
						lineInput = null;
						reg.unbind("EnvService");
						if (!(UnicastRemoteObject.unexportObject(server, true))) {
							System.out.println("Error: unexporting!");
						}
						reg = null;

						System.out.println("RMI - Server stopped!");
					} catch (RemoteException e) {
						System.out.println("Error stopping RMI - Server");
						e.printStackTrace();
					} catch (NotBoundException e) {
						System.out.println("Not found registry name");
						e.printStackTrace();
					}
				} else {
					System.out.println(
							"RMI - Server wurde noch nicht gestartet. Zuerst starten, um es beenden zu können");
				}
			}
				break;
			case "quit": {
				lineInput = null;
				writeEnabled = false;
				System.exit(0);
			}
				break;
			default: {
				lineInput = null;
				System.out.println("Try again - Wrong Input");
				System.out.println(startDialog);
			}
			}
		}
	}

}
