package at.fhooe.mc.jaxb;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Main {

	public static void main(String[] args) {//1940, 02, 10		
		Pet pet = new Pet("Thomas", "Tom", new Date(40, 01, 10), Type.CAT, new String[]{"Katzenschnupfen", "Katzenseuche", "Tollwut", "Leukose"}, "123456789");
		try {
			JAXBContext jaxb = JAXBContext.newInstance(new Class[]{Pet.class});
			Marshaller marshaller = jaxb.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(pet, stringWriter);
			System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
			String data = stringWriter.getBuffer().toString();
			System.out.println(data);
			
			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			Pet resPet = (Pet) unmarshaller.unmarshal(new StringReader(data));
			System.out.println(resPet);
		} catch (JAXBException e) {
			System.out.println("JAXB Error");
			e.printStackTrace();
		}
		
	}
}
