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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import au.edu.unsw.soacourse.auxiliary.FileOperations;
import au.edu.unsw.soacourse.auxiliary.IdGenerator;
import au.edu.unsw.soacourse.auxiliary.Paths;
import au.edu.unsw.soacourse.model.IdCounter;
import au.edu.unsw.soacourse.model.Job;
import au.edu.unsw.soacourse.model.User;

/* NOTES: 
 * 
 * @Path can be applied to resource classes or methods.
*/

//@Path("/profile")	// the URL path will be http://localhost:8080/FoundITServerCxfRest/hello
public class UserResource {
	
	final boolean debug = true;
	Paths path = new Paths();
	FileOperations fop = new FileOperations();
 
    @GET																	// the method will handle GET request method on the said path
    @Path("/{userId}")											// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/echo/{some text input here}
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})									// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
    public User getUserProfile(@PathParam("userId") String userId) {	// map the path parameter text after /echo to String input.
    	User user = null;
    	try {
    		String filename = path.getUserPath() + userId + ".xml";
	    	File file = new File(filename);
	    	// Bind XML to Java object
	    	JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
    		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    		user = (User) jaxbUnmarshaller.unmarshal(file);
    		if (debug) System.out.println("user is found: " + userId);
    	} catch (JAXBException e) {
    		// TODO throw Response/Exception: user profile for user 'userId' does not exist
    		e.printStackTrace();
    		if (debug) System.out.println("User profile for user '" + userId + "' does not exist");
    	}
    	return user;
    }
    
    @GET																	// the method will handle GET request method on the said path
    // Get user profile by appId
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})									// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
    public Response getUserByApp(@QueryParam("appId") String appId) throws JAXBException {	// map the path parameter text after /echo to String input.
    	if (appId == null) {
    		return Response.status(Response.Status.NOT_FOUND).entity("Application is null.").build();
    	}
    	// Get all applications
    	ApplicationResource appResource = new ApplicationResource();
    	List<au.edu.unsw.soacourse.model.Application> apps = appResource.getApplications();
    	// Find userId by appId
	  	String userId = null;
	  	for (au.edu.unsw.soacourse.model.Application app: apps) {
	  		if (app.getAppId().equals(appId)) {
	  			if (debug) System.out.println("Application is found: " + appId);
	  			userId = app.getUserId();
	  			break;
	  		}
		}
	  	if (userId == null) {
	  		return Response.status(Response.Status.NOT_FOUND).entity("User profile for user '" + userId + "' is not found.").build();
	  	}
	  	User user = getUserProfile(userId);
	  	if (user == null) {
	  		return Response.status(Response.Status.NOT_FOUND).entity("User profile for user '" + userId + "' is not found.").build();
	  	}
	  	return Response.ok(user).build();
    }

    @POST									// the method will handle POST request method on the said path
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})	// the response will contain JSON
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})	// applies to the input parameter JsonBean input. map the POST body content (which will contain JSON) to JsonBean input
//    @Path("/")								// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/jsonBean
    public Response addUserProfile(User user) throws JAXBException, IOException {
    	
//    	// Get next Id to use
//    	IdGenerator idGenerator = new IdGenerator(); 
//    	IdCounter idCounter = idGenerator.getCounter(path.getUserPath());
//    	user.setUsername("user" + idCounter.getId());
//    	idGenerator.updateCounter(path.getUserPath(), idCounter);
    	
    	Response response;
		String filename = path.getUserPath() + user.getUserId() + ".xml";
    	File file = new File(filename);	// create the file if does not exist
    	if(!file.exists()) {
    		file.createNewFile();
    	} else {						// return 'CONFLICT' response if file already exists
    		response = Response.status(Response.Status.CONFLICT).entity("User profile for user '" + user.getUserId() + "' already exists").build();
    		return response;
    	}
    	if (debug) System.out.println("file: " + filename);
    	// Bind Java object to XML
    	JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(user, file);
		jaxbMarshaller.marshal(user, System.out);

    	response = Response.status(Response.Status.CREATED).entity("User profile for user '" + user.getUserId() + "' has been created").build();
    	return response;
    }
    
	@PUT
	@Path("/{userId}")						// TODO seems to be useless. Just set path to "/" ????
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response putUser(User user) {
		Response response;
		try {
			String filename = path.getUserPath() + user.getUserId() + ".xml";
	    	File file = new File(filename);	// create the file if does not exist
	    	if(!file.exists()) {
	    		file.createNewFile();
	    		response = Response.status(Response.Status.NOT_FOUND).entity("User profile for user '" + user.getUserId() + "' is not found.").build();
	    		return response;
	    	}
	    	if (debug) System.out.println("file: " + filename);
	    	// Bind Java object to XML
	    	JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(user, file);
			jaxbMarshaller.marshal(user, System.out);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		response = Response.status(Response.Status.ACCEPTED).entity("User profile for user '" + user.getUserId() + "' has been updated").build();
		return response;
	}
	
	@DELETE
	@Path("/{userId}")
	public Response deleteUser(@PathParam("userId") String userId) throws IOException {
		Response response;
		String filename = path.getUserPath() + userId + ".xml";
		File file = new File(filename);	// create the file if does not exist
		if(!file.exists()) {
			response = Response.status(Response.Status.NOT_FOUND).entity("User posting for user '" + userId + "' is not found.").build();
			return response;
		}
		File dfile = new File(path.getUserPath() + "_" + userId + ".xml");
		boolean success = file.renameTo(dfile);
		if (!success) {
			response = Response.status(Response.Status.FORBIDDEN).entity("User posting for user '" + userId + "' cannot be deleted.").build();
			return response;
		}
		response = Response.status(Response.Status.OK).entity("User posting for user '" + userId + "' has been deleted").build();
		return response;
	}
}

