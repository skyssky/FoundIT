package au.edu.unsw.soacourse.auxiliary;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import au.edu.unsw.soacourse.model.Application;
import au.edu.unsw.soacourse.model.Application.AppStatus;

public class CheckStatus {

	final boolean debug = true;
	Paths path = new Paths();
	
	 public boolean appStatus(String appId, AppStatus status) throws JAXBException {		
		 Application application = null;
		 String filename = path.getAppPath() + appId + ".xml";
		 File file = new File(filename);
		 if(!file.exists()) {
			if (debug) System.out.println("Application '" + appId + "' is not found, so cannot check its status.");
			return false;
		 }
		 // Bind XML to Java object
	 	JAXBContext jaxbContext = JAXBContext.newInstance(Application.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		application = (Application) jaxbUnmarshaller.unmarshal(file);
		if (application.getStatus() != status) {
			return false;
		}
		return true;
 	}
}
