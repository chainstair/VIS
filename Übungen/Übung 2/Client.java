import Helper.EnvData;
import Helper.IEnvService;

import java.io.*;
import java.net.Socket;

public class Client implements IEnvService {

    private Socket mSocket;
    private BufferedReader mBufferedReader;
    private BufferedWriter mBufferedWriter;

    public Client(String _address, int _port) {
        initializeSocket(_address, _port);
    }

    /**
     * Initializes a Socket for the Client.
     * Will be called automatically when initializing a new Client.
     * Use Constructor {@link #Client(String, int)}  new Client(String _address, int _port)} preferably!
     *
     * @param  _address  address of server
     * @param  _port     port on which server listens
     */
    public void initializeSocket(String _address, int _port) {
        try {
            mSocket = new Socket(_address, _port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive() {
        String msg = "";
        try {
            InputStream socketInputStream = mSocket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(socketInputStream);
            mBufferedReader = new BufferedReader(inputStreamReader);

            StringBuffer sb = new StringBuffer();
            do {
                char[] c = new char[] { 1024 };
                mBufferedReader.read(c);
                sb.append(c);
            } while (mBufferedReader.ready());
            msg = sb.toString().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public void send(String _msg) {
        try {
            OutputStream socketOutputStream = mSocket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socketOutputStream);
            mBufferedWriter = new BufferedWriter(outputStreamWriter);

            mBufferedWriter.write(_msg);
            mBufferedWriter.newLine();
            mBufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            mSocket.close();
            System.out.println("Socked closed");
            mBufferedReader.close();
            mBufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[] requestEnvironmentDataTypes() {
        send("sensortypes#");
        String[] dataTypes = convertResult(receive());
        return dataTypes;
    }

    @Override
    public EnvData requestEnvironmentData(String _type) {
        send("sensor;" + _type + "#");
        String[] result = receive().split("[|]");
        EnvData envData = new EnvData(result[0]);
        switch (_type) {
            case "light":
                envData.setLight(Float.parseFloat(convertResult(result[1])[0]));
                break;
            case "noise":
                envData.setNoise(Float.parseFloat(convertResult(result[1])[0]));
                break;
            case "air":
                float[] airs = new float[3];
                String[] airStrings = convertResult(result[1]);
                for (int i = 0; i < airStrings.length; i++) {
                    airs[i] = Float.parseFloat(airStrings[i]);
                }
                envData.setAir(airs);
                break;
            default:
                System.out.println("Invalid type!");
        }
        return envData;
    }

    @Override
    public EnvData[] requestAll() {
        EnvData[] envDataArr = new EnvData[3];
        envDataArr[0] = requestEnvironmentData("light");
        envDataArr[1] = requestEnvironmentData("noise");
        envDataArr[2] = requestEnvironmentData("air");
        return envDataArr;
    }

    private String[] convertResult(String _str) {
        String[] arr = _str.split(";");
        arr[arr.length - 1] = arr[arr.length - 1].replace("#", "");
        return arr;
    }
}
