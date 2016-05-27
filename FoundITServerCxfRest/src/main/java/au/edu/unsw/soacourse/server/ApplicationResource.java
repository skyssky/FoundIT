package au.edu.unsw.soacourse.server;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;

import au.edu.unsw.soacourse.auxiliary.IdGenerator;
import au.edu.unsw.soacourse.auxiliary.Paths;
import au.edu.unsw.soacourse.model.Application;
import au.edu.unsw.soacourse.model.IdCounter;
import au.edu.unsw.soacourse.model.Application.AppStatus;
import au.edu.unsw.soacourse.model.Job;
import au.edu.unsw.soacourse.model.Job.JobStatus;

/* NOTES: 
 * 
 * @Path can be applied to resource classes or methods.
*/

@Path("/")	// the URL path will be http://localhost:8080/FoundITServerCxfRest/hello
public class ApplicationResource {
	
	final boolean debug = true;
//	final String appPath = System.getProperty("catalina.home") + "/webapps/server-database/application/";
//	final String jobPath = System.getProperty("catalina.home") + "/webapps/server-database/job/";
	Paths path = new Paths();
 
    @GET																	// the method will handle GET request method on the said path
//    @Path("")														// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/echo/{some text input here}
    @Produces(MediaType.APPLICATION_XML)									// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
    public List<Application> getApplications() throws JAXBException {			// map the path parameter text after /echo to String input.
    	// Get all applications
    	List<Application> applications = new ArrayList<Application>();
    	Application application;
    	Collection<File> files = getFiles(path.getAppPath());
		for (File file: files) {
			// Bind XML to Java object
	    	JAXBContext jaxbContext;
			jaxbContext = JAXBContext.newInstance(Application.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			application = (Application) jaxbUnmarshaller.unmarshal(file);
    		if (debug) System.out.println("Application is found: " + application.getAppId());
    		applications.add(application);
		}
    	return applications;
    }
    
    @GET																	// the method will handle GET request method on the said path
    @Path("{appId}")														// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/echo/{some text input here}
    @Produces(MediaType.APPLICATION_XML)									// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
    public Response getApplication(@PathParam("appId") String appId) throws JAXBException {	// map the path parameter text after /echo to String input.
    	Application application = null;
		String filename = path.getAppPath() + appId + ".xml";
    	File file = new File(filename);
    	if (!file.exists()) {
    		return Response.status(Response.Status.NOT_FOUND).entity("Application '" + appId + "' is not found.").build();
    	}
    	// Bind XML to Java object
    	JAXBContext jaxbContext = JAXBContext.newInstance(Application.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		application = (Application) jaxbUnmarshaller.unmarshal(file);
		if (debug) System.out.println("Application is found: " + appId);
		return Response.ok(application, MediaType.APPLICATION_XML).build();
    }

    @POST									// the method will handle POST request method on the said path
    @Produces(MediaType.APPLICATION_XML)	// the response will contain JSON
    @Consumes(MediaType.APPLICATION_XML)	// applies to the input parameter JsonBean input. map the POST body content (which will contain JSON) to JsonBean input
//    @Path("")								// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/jsonBean
    public Response addApplication(Application application) throws JAXBException, IOException {
    	
    	// Get next Id to use
    	IdGenerator idGenerator = new IdGenerator(); 
    	IdCounter idCounter = idGenerator.getCounter(path.getAppPath());
    	application.setAppId("app" + idCounter.getId());
    	idGenerator.updateCounter(path.getAppPath(), idCounter);
		
    	Response response;
    	// An application can be created iff the job posting is opening
    	if (!jobIsOpening(application)) {
    		response = Response.status(Response.Status.FORBIDDEN).entity("Application '" + application.getAppId() + "' cannot be made because the job posting is not opening or does not exist.").build();
    		return response;
    	}
    	// Once the job posting is opening, create the application.
		String filename = path.getAppPath() + application.getAppId() + ".xml";
    	File file = new File(filename);			// create the file if does not exist
    	if(!file.exists()) {
    		file.createNewFile();
    	} else {								// return 'CONFLICT' response if file already exists
    		response = Response.status(Response.Status.CONFLICT).entity("Application '" + application.getAppId() + "' already exists").build();
    		return response;
    	}
    	if (debug) System.out.println("file: " + filename);
    	// Bind Java object to XML
    	JAXBContext jaxbContext = JAXBContext.newInstance(Application.class);
    	Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(application, file);
		jaxbMarshaller.marshal(application, System.out);
    	response = Response.status(Response.Status.CREATED).entity("Application '" + application.getAppId() + "' has been created").build();
    	return response;
    }
    
	@PUT
	@Path("{appId}")							
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response putApplication(Application application) throws JAXBException, IOException {
		Response response;
		// An application can be updated iff the job posting is opening
    	if (!jobIsOpening(application)) {
    		response = Response.status(Response.Status.FORBIDDEN).entity("Application '" + application.getAppId() + "' cannot be updated because the job posting is not opening or does not exist.").build();
    		return response;
    	}
		String filename = path.getAppPath() + application.getAppId() + ".xml";
    	File file = new File(filename);	// create the file if does not exist
    	if(!file.exists()) {
//    		file.createNewFile();
    		response = Response.status(Response.Status.NOT_FOUND).entity("Application '" + application.getAppId() + "' is not found.").build();
    		return response;
    	}
    	if (debug) System.out.println("file: " + filename);
    	// Bind Java object to XML
    	JAXBContext jaxbContext = JAXBContext.newInstance(Application.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(application, file);
		jaxbMarshaller.marshal(application, System.out);
		response = Response.status(Response.Status.ACCEPTED).entity("Application '" + application.getAppId() + "' has been updated").build();
		return response;
	}
	
	@DELETE
	@Path("{appId}")
	public Response deleteApplication(@PathParam("appId") String appId) throws IOException, JAXBException {
		Response response;
		// An application can be deleted iff the job posting is opening
		if (!jobIsOpening(appId) && !appIsProcessed(appId)) {
    		response = Response.status(Response.Status.FORBIDDEN).entity("Application '" + appId + "' cannot be deleted or archived because its processing has not been finished.").build();
    		return response;
    	}
		String filename = path.getAppPath() + appId + ".xml";
		File file = new File(filename);	// create the file if does not exist
		if(!file.exists()) {
			response = Response.status(Response.Status.NOT_FOUND).entity("Application '" + appId + "' is not found.").build();
			return response;
		}
		File dfile = new File(path.getAppPath() + "_" + appId + ".xml");
		boolean success = file.renameTo(dfile);
		if (!success) {
			response = Response.status(Response.Status.FORBIDDEN).entity("Application '" + appId + "' cannot be deleted.").build();
			return response;
		}
		response = Response.status(Response.Status.OK).entity("Application '" + appId + "' has been deleted").build();
		return response;
	}
    
	Collection<File> getFiles(String directoryName) {
		File directory = new File(directoryName);
		Collection<File> files = FileUtils.listFiles(directory, new RegexFileFilter("^[^_][^.]*.xml"), null);
		System.out.println("Get collections: size = " + files.size());
		return files;
	}
	
	boolean jobIsOpening(Application application) throws JAXBException {
		Job job = null;
    	String filename = path.getAppPath() + application.getJobId() + ".xml";
    	File file = new File(filename);	// create the file if does not exist
    	if(!file.exists()) {
    		if (debug) System.out.println("Job '" + application.getJobId() + "' is not found, so cannot confirm your application.");
    		return false;
    	}
    	if (debug) System.out.println("file: " + filename);
    	// Bind XML to Java object
    	JAXBContext jaxbContext = JAXBContext.newInstance(Job.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		job = (Job) jaxbUnmarshaller.unmarshal(file);
		// A new application can be created iff the job posting is opening.
    	if (job.getStatus() != JobStatus.OPEN) {
    		if (debug) System.out.println("Application '" + application.getAppId() + "' cannot be made because the job posting is not opening.");
			return false;
    	}
    	return true;
	}
	
	boolean jobIsOpening(String appId) throws JAXBException {
		Application application = null;
		String filename = path.getAppPath() + appId + ".xml";
    	File file = new File(filename);
    	// Bind XML to Java object
    	JAXBContext jaxbContext = JAXBContext.newInstance(Application.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		application = (Application) jaxbUnmarshaller.unmarshal(file);
		return jobIsOpening(application);
	}
	
	boolean appIsProcessed(String appId) throws JAXBException {		
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
		if (application.getStatus() != AppStatus.PROCESSED) {
			return false;
		}
		return true;
	}

}

