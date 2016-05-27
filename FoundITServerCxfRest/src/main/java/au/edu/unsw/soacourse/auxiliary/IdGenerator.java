package au.edu.unsw.soacourse.auxiliary;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import au.edu.unsw.soacourse.model.IdCounter;

public class IdGenerator {

	public IdGenerator() {
		
	}
	
	public IdCounter getCounter(String path) throws JAXBException {
		// Get next Id to use
    	File counterFile = new File(path + "idcounter.xml");
    	// Bind XML to Java object
    	JAXBContext jaxbContext = JAXBContext.newInstance(IdCounter.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		IdCounter idCounter = (IdCounter) jaxbUnmarshaller.unmarshal(counterFile);
		idCounter.setId(idCounter.getId() + 1);
    	return idCounter;
	}
	
	public void updateCounter(String path, IdCounter idCounter) throws JAXBException {
    	File counterFile = new File(path + "idcounter.xml");
    	// Update the nextXXId to current ID
    	// Bind Java object to XML
    	JAXBContext jaxbContext = JAXBContext.newInstance(IdCounter.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(idCounter, counterFile);
		jaxbMarshaller.marshal(idCounter, System.out);
	}
}
