package at.fhooe.mc.client;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.net.Socket;
import java.rmi.RemoteException;

import at.fhooe.mc.api.EnvData;
import at.fhooe.mc.api.IEnvService;

/**
 * Client class for socket communication
 */
public class Client implements IEnvService{
	/**
	 * @param args
	 * args[0]= portnumber
	 * args[1]= host/IP
	 */
	public static void main(String[] args) {
        int mPortNumber = 0;
		String mHost;

//		Check if params available
        if(args.length >= 2) {
        	mHost = args[1];
        	mPortNumber = Integer.valueOf(args[0]);
        	try (
//        		Build socket
	            Socket socket = new Socket(mHost, mPortNumber);
	            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
	        ) {
	            System.out.println("Type Commando:");
	            String lineInput;
	            while ((lineInput = stdIn.readLine()) != null) {
	            	StringBuilder stringbuilder = new StringBuilder();
	            	stringbuilder.append(lineInput);
	            	stringbuilder.append(('\0'));
	                out.println(stringbuilder.toString());
	                
//	                Get string and print out
	                char[] buffer = new char[100];
	                in.read(buffer);
	                System.out.println("Message: " + new String(buffer) + "\n Please enter your command:");
	            }
//	            Close Socket
	            out.close();
	            in.close();
	            socket.close();
	        } catch (IOException e) {
	            System.err.println("IP: " + mHost);
	            System.exit(1);
	        } 
        }
        else {
        	System.out.println("IP MISSING");
        }
	}

	@Override
	public String[] requestEnvironmentDataTypes() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnvData requestEnvironmentData(String _type) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnvData[] requestAll() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
}
