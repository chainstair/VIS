package at.fhooe.mc.api;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

public class MyJavaClient implements IEnvService {
	private PrintWriter mOutput;
	private BufferedReader mInput;
	
	public MyJavaClient(){
	    int mPortNumber = 5015;
		String mHost = "127.0.0.1";

	    try {
            Socket socket = new Socket(mHost, mPortNumber);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            mOutput = out;
            mInput = in;
	    } catch (IOException e) {
            System.err.println("IP: " + mHost);
            System.exit(1);
	    }
	}


	@Override
	public EnvData requestEnvironmentData(String _type) {
        char[] buffer=new char[100];
		EnvData data = null;
        try {
			mInput.read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder stingbuilder = new StringBuilder();
    	stingbuilder.append("sensor;" + _type + "#\0");
        mOutput.println(stingbuilder.toString());
        String msg = new String(buffer).split("#")[0];
        if (_type == "noise"){
        	String[] splitMsg = msg.split("\\|");
        	data = new NoiseData(Integer.parseInt(splitMsg[0]),Integer.parseInt(splitMsg[1]));
        }
        else if (_type == "air"){
        	String[] splitMsg = msg.split("\\|");
        	String[] messageVal = splitMsg[1].split(";");
        	data = new AirData(Integer.parseInt(splitMsg[0]),Integer.parseInt(messageVal[0]),Integer.parseInt(messageVal[1]),Integer.parseInt(messageVal[2]));
        }
        else if (_type == "light"){
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
        try {
			mInput.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuilder stringbuilder = new StringBuilder();
    	stringbuilder.append("sensortypes#\0");
        String msg = new String(buffer).split("#")[0];
        mOutput.println(stringbuilder.toString());
        return msg.split(";");
	}
}
