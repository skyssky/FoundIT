package au.edu.unsw.soacourse.auxiliary;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;

public class FileOperations {

	final boolean debug = true;
	
	public Collection<File> getFiles(String directoryName) {
		File directory = new File(directoryName);
		Collection<File> files = FileUtils.listFiles(directory, new RegexFileFilter("^[^_][^.]*.xml"), null);
		if (debug) System.out.println("Get collections: size = " + files.size());
		return files;
	}
}
