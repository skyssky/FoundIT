package au.edu.unsw.soacourse.auxiliary;

import java.io.File;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;

import au.edu.unsw.soacourse.model.User;

public class FileOperations {

	final boolean debug = true;
	
	public Collection<File> getFiles(String directoryName) {
		File directory = new File(directoryName);
		Collection<File> files = FileUtils.listFiles(directory, new RegexFileFilter("^[^_i][^.d]*.xml"), null);
		if (debug) System.out.println("Get collections: size = " + files.size());
		return files;
	}

}
