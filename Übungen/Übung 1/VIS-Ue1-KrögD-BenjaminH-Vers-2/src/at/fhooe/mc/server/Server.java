package at.fhooe.mc.server;

import java.io.BufferedReader;
import java.net.ServerSocket;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.io.InputStreamReader;

/**
 * Server class for socket communication
 */
public class Server{

	/**
	 * @param args
	 * args[0] = portnumber
	 */
	public static void main(String[] args) {
		
		int mPortNumber;
//		Check if params available
	    if(args.length >= 1) {
	    	mPortNumber = Integer.valueOf(args[0]);	    
	        try (
//	        	Build Socket
	            ServerSocket serverSocket = new ServerSocket(mPortNumber);
	            Socket clientSocket = serverSocket.accept();   
	            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);  
	            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        	)
	        {
	        	
//	        	Read lines
	            String lineInput;
	            while ((lineInput = in.readLine()) != null) {
	                System.out.println("message: " + lineInput);
	                out.println(lineInput);
	            }
	        } 
	        catch (IOException _e) {
	            System.out.println(_e.getMessage());
	        }
	    }
	    else {
	    	System.out.println("Übergebe einen Port als Startparameter");
	    }
	}
	
}