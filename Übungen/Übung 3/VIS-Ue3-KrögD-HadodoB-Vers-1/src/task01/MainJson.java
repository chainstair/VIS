package task01;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.eclipse.persistence.jaxb.xmlmodel.ObjectFactory;
import org.eclipse.persistence.oxm.MediaType;

public class MainJson {

	public static void main(String[] args) {
		Pet pet = new Pet("Thomas", "Tom", new Date(40, 01, 10), Type.CAT,
				new String[] { "Katzenschnupfen", "Katzenseuche", "Tollwut", "Leukose" }, "123456789");
		try {
			JAXBContext jaxb = JAXBContext.newInstance(new Class[] { Pet.class, ObjectFactory.class });
			Marshaller marshaller = jaxb.createMarshaller();
			marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			StringWriter stringWriter = new StringWriter();
			marshaller.marshal(pet, stringWriter);
			System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
			String data = stringWriter.getBuffer().toString();
			System.out.println(data);

			Unmarshaller unmarshaller = jaxb.createUnmarshaller();
			unmarshaller.setProperty(UnmarshallerProperties.MEDIA_TYPE, MediaType.APPLICATION_JSON);
			
			//without root
			unmarshaller.setProperty(UnmarshallerProperties.JSON_INCLUDE_ROOT, Boolean.FALSE);
			StreamSource stream = new StreamSource(new StringReader(data));
			JAXBElement<Pet> petContainer = unmarshaller.unmarshal(stream, Pet.class);
			Pet resPet = petContainer.getValue();
			
			//Pet resPet = (Pet) unmarshaller.unmarshal(new StringReader(data));
			System.out.println(resPet);
		} catch (JAXBException e) {
			System.out.println("JAXB Error");
			e.printStackTrace();
		}

	}

}
