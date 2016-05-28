package au.edu.unsw.soacourse.server;

import java.io.File;
import java.io.IOException;

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

import au.edu.unsw.soacourse.auxiliary.IdGenerator;
import au.edu.unsw.soacourse.auxiliary.Paths;
import au.edu.unsw.soacourse.model.IdCounter;
import au.edu.unsw.soacourse.model.User;

/* NOTES: 
 * 
 * @Path can be applied to resource classes or methods.
*/

//@Path("/profile")	// the URL path will be http://localhost:8080/FoundITServerCxfRest/hello
public class UserResource {
	
	final boolean debug = true;
//	final String path = System.getProperty("catalina.home") + "/webapps/server-database/user/";
	Paths path = new Paths();
 
    @GET																	// the method will handle GET request method on the said path
    @Path("/{username}")											// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/echo/{some text input here}
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})									// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
    public User getUserProfile(@PathParam("username") String username) {	// map the path parameter text after /echo to String input.
    	User user = null;
    	try {
    		String filename = path.getUserPath() + username + ".xml";
	    	File file = new File(filename);
	    	// Bind XML to Java object
	    	JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
    		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    		user = (User) jaxbUnmarshaller.unmarshal(file);
    		if (debug) System.out.println("user is found: " + username);
    	} catch (JAXBException e) {
    		// TODO throw Response/Exception: user profile for user 'username' does not exist
    		e.printStackTrace();
    		if (debug) System.out.println("User profile for user '" + username + "' does not exist");
    	}
    	return user;
    }

    @POST									// the method will handle POST request method on the said path
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})	// the response will contain JSON
    @Consumes(MediaType.APPLICATION_XML)	// applies to the input parameter JsonBean input. map the POST body content (which will contain JSON) to JsonBean input
//    @Path("/")								// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/jsonBean
    public Response addUserProfile(User user) throws JAXBException, IOException {
    	
//    	// Get next Id to use
//    	IdGenerator idGenerator = new IdGenerator(); 
//    	IdCounter idCounter = idGenerator.getCounter(path.getUserPath());
//    	user.setUsername("user" + idCounter.getId());
//    	idGenerator.updateCounter(path.getUserPath(), idCounter);
    	
    	Response response;
		String filename = path.getUserPath() + user.getUsername() + ".xml";
    	File file = new File(filename);	// create the file if does not exist
    	if(!file.exists()) {
    		file.createNewFile();
    	} else {						// return 'CONFLICT' response if file already exists
    		response = Response.status(Response.Status.CONFLICT).entity("User profile for user '" + user.getUsername() + "' already exists").build();
    		return response;
    	}
    	if (debug) System.out.println("file: " + filename);
    	// Bind Java object to XML
    	JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(user, file);
		jaxbMarshaller.marshal(user, System.out);

    	response = Response.status(Response.Status.CREATED).entity("User profile for user '" + user.getUsername() + "' has been created").build();
    	return response;
    }
    
	@PUT
	@Path("/{username}")						// TODO seems to be useless. Just set path to "/" ????
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Consumes(MediaType.APPLICATION_XML)
	public Response putUser(User user) {
		Response response;
		try {
			String filename = path.getUserPath() + user.getUsername() + ".xml";
	    	File file = new File(filename);	// create the file if does not exist
	    	if(!file.exists()) {
	    		file.createNewFile();
	    		response = Response.status(Response.Status.NOT_FOUND).entity("User profile for user '" + user.getUsername() + "' is not found.").build();
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
		response = Response.status(Response.Status.ACCEPTED).entity("User profile for user '" + user.getUsername() + "' has been updated").build();
		return response;
	}
	
	@DELETE
	@Path("/{username}")
	public Response deleteUser(@PathParam("username") String username) throws IOException {
		Response response;
		String filename = path.getUserPath() + username + ".xml";
		File file = new File(filename);	// create the file if does not exist
		if(!file.exists()) {
			response = Response.status(Response.Status.NOT_FOUND).entity("User posting for user '" + username + "' is not found.").build();
			return response;
		}
		File dfile = new File(path.getUserPath() + "_" + username + ".xml");
		boolean success = file.renameTo(dfile);
		if (!success) {
			response = Response.status(Response.Status.FORBIDDEN).entity("User posting for user '" + username + "' cannot be deleted.").build();
			return response;
		}
		response = Response.status(Response.Status.OK).entity("User posting for user '" + username + "' has been deleted").build();
		return response;
	}
}

