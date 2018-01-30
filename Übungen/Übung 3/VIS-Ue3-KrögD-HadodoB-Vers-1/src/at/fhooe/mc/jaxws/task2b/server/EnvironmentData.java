package at.fhooe.mc.jaxws.task2b.server;


import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Scanner;

import javax.jws.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory;
import org.eclipse.persistence.oxm.MediaType;

import at.fhooe.mc.jaxws.task2b.service.EnvData;
import at.fhooe.mc.jaxws.task2b.service.IEnvService;

@WebService(endpointInterface="at.fhooe.mc.jaxws.task2b.service.IEnvService")
public class EnvironmentData implements IEnvService{

	@Override
	public String[] requestEnvironmentDataTypes() throws RemoteException {
		String[] array = {"90117073", "90720313", "90123289"};	//Rom, Salzburg, Milan
		return array;
	}

	@Override
	public EnvData requestEnvironmentData(String _type) throws RemoteException {
		EnvData resultData = null;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("https://query.yahooapis.com/v1/public/yql?q=select%20item.condition%20from%20weather.forecast%20where%20woeid%20in%20(");
		stringBuilder.append(_type);
		stringBuilder.append(")&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
		
		try {
			//create Url
			URL text = new URL(stringBuilder.toString());
			HttpURLConnection http =
			(HttpURLConnection)text.openConnection();
			int length = http.getContentLength();
			int resCode = http.getResponseCode();
			String mime = http.getContentType();
			Scanner s = new Scanner(http.getInputStream(), "UTF-8");
			s.useDelimiter("\\z"); // \z --> till end of input
			String content = s.next();
			s.close();
			
			int start = content.indexOf("condition");
			int end = content.indexOf("}", start);
			
			String jsonResponse = content.substring(start+11, end+1);
			
			System.out.println(jsonResponse);
			
			JAXBContext jaxb = JAXBContext.newInstance(new Class[] { EnvData.class, ObjectFactory.class });
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
			
			//without root
			unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, Boolean.FALSE);
			StreamSource json = new StreamSource(new StringReader(jsonResponse));
			JAXBElement<EnvData> envContainer = unmarshaller.unmarshal(json, EnvData.class);
			resultData = envContainer.getValue();
			System.out.println(resultData);
		} catch (JAXBException e) {
			System.out.println("JAXB Error");
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultData;

	}

	@Override
	public EnvData[] requestAll() throws RemoteException {
		String[] locations = requestEnvironmentDataTypes();
		EnvData[] dataArray = new EnvData[locations.length];
		for (int i = 0; i < locations.length; i++){
			dataArray[i].setmLocation(locations[i]);
			dataArray[i] = requestEnvironmentData(locations[i]);
		}
		return dataArray;
	}



}
