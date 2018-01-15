package at.fhooe.mc.api;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

public class MyJavaClient implements IEnvService {
	private PrintWriter mOutput;
	private BufferedReader mNormalInput;
	private BufferedReader mIn;
	private Socket socket;
	
	public MyJavaClient(){
	    int mPortNumber = 5028;
		String mHost = "192.168.188.30";

	    try {
            this.socket = new Socket(mHost, mPortNumber);
            this.mOutput = new PrintWriter(socket.getOutputStream(), true);
            this.mIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.mNormalInput = new BufferedReader(new InputStreamReader(System.in));
            
	    
//            System.out.println("Type Commando:");
//            String lineInput;
//            while ((lineInput = input.readLine()) != null) {
//            	StringBuilder stringbuilder = new StringBuilder();
//            	stringbuilder.append(lineInput);
//            	stringbuilder.append(('\0'));
//                out.println(stringbuilder.toString());
//                
////                Get string and print out
//                char[] buffer = new char[100];
//                in.read(buffer);
//                System.out.println("Message: " + new String(buffer) + "\n Please enter your command:");
//            }
////            Close Socket
//            out.close();
//            in.close();
//            socket.close();
        } catch (IOException e) {
            System.err.println("IP: " + mHost);
            System.exit(1);
        } 
    }



	@Override
	public EnvData requestEnvironmentData(String _type) {
        char[] buffer=new char[100];
		EnvData data = null;
		StringBuilder stringbuilder = new StringBuilder();
    	stringbuilder.append("sensor;" + _type + "#\0");
        mOutput.println(stringbuilder.toString());
        try {
			mIn.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String msg = new String(buffer).split("#")[0];
        System.out.println(msg);
        if (_type.equals("noise")){
        	String[] splitMsg = msg.split("\\|");
        	data = new NoiseData(Integer.parseInt(splitMsg[0]),Integer.parseInt(splitMsg[1]));
        }
        else if (_type.equals("air")){
        	String[] splitMsg = msg.split("\\|");
        	String[] messageVal = splitMsg[1].split(";");
        	data = new AirData(Integer.parseInt(splitMsg[0]),Integer.parseInt(messageVal[0]),Integer.parseInt(messageVal[1]));
        }
        else if (_type.equals("light")){
        	String[] splitMsg = msg.split("\\|");
        	data = new LightData(Integer.parseInt(splitMsg[0]),Integer.parseInt(splitMsg[1]));
        }
        else {
        	System.out.println("Error");
        }
        return data;
	}

	@Override
	public EnvData[] requestAll() {
		String[] sensorPack = requestEnvironmentDataTypes();
		int length = sensorPack.length;
		EnvData[] data = new EnvData[length];
		for (int i = 0; i < sensorPack.length; i++){
			 EnvData singleData = requestEnvironmentData(sensorPack[i]);
			 data[i] = singleData;
		}
		return data;
	}
	
	@Override
	public String[] requestEnvironmentDataTypes() {
        char[] buffer=new char[100];
		StringBuilder stringbuilder = new StringBuilder();
    	stringbuilder.append("sensortypes#\0");
        mOutput.println(stringbuilder.toString());
        try {
        	mIn.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
        String msg = new String(buffer).split("#")[0];
        System.out.println(msg);
        return msg.split(";");
	}
}
