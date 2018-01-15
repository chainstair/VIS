package at.fhooe.mc.server;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;

/**
 * Client class for socket communication
 */
public class Client {
	
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
        	mPortNumber = Integer.valueOf(args[0]);
        	mHost = args[1];
        	
   	        try (
//   	       	Build socket
	            Socket socket = new Socket(mHost, mPortNumber);
	            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
	        ) {
//   	        Read Lines
	            String lineInput;
	            while ((lineInput = stdIn.readLine()) != null) {
	                out.println(lineInput);
	                System.out.println("echo: " + in.readLine());
	            }
	        } 
   	        catch (IOException e) {
	            System.err.println("IP: " + mHost);
	            System.exit(1);
	        }
        }
        else {
        	System.out.println("");
        }
    }
}
