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

import au.edu.unsw.soacourse.model.Company;

/* NOTES: 
 * 
 * @Path can be applied to resource classes or methods.
*/

//@Path("/profile")	// the URL path will be http://localhost:8080/FoundITServerCxfRest/hello
public class CompanyResource {
	
	final boolean debug = true;
	final String path = System.getProperty("catalina.home") + "/webapps/server-database/company/";
 
    @GET																	// the method will handle GET request method on the said path
    @Path("/{profileId}")											// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/echo/{some text input here}
    @Produces(MediaType.APPLICATION_XML)									// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
    public Response getCompanyProfile(@PathParam("profileId") String profileId) throws JAXBException {	// map the path parameter text after /echo to String input.
    	Company company = null;
		String filename = path + profileId + ".xml";
    	File file = new File(filename);
    	if (!file.exists()) {
    		return Response.status(Response.Status.NOT_FOUND).entity("Company profile '" + profileId + "' is not found.").build();
    	}
    	// Bind XML to Java object
    	JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		company = (Company) jaxbUnmarshaller.unmarshal(file);
		if (debug) System.out.println("company is found: " + profileId);
    	return Response.ok(company, MediaType.APPLICATION_XML).build();
    }

    @POST									// the method will handle POST request method on the said path
    @Produces(MediaType.APPLICATION_XML)	// the response will contain JSON
    @Consumes(MediaType.APPLICATION_XML)	// applies to the input parameter JsonBean input. map the POST body content (which will contain JSON) to JsonBean input
//    @Path("/")								// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/jsonBean
    public Response addCompanyProfile(Company company) {
    	Response response;
    	try {
    		String filename = path + company.getProfileId() + ".xml";
	    	File file = new File(filename);	// create the file if does not exist
	    	if(!file.exists()) {
	    		file.createNewFile();
	    	} else {						// return 'CONFLICT' response if file already exists
	    		response = Response.status(Response.Status.CONFLICT).entity("Company profile for company '" + company.getProfileId() + "' already exists").build();
	    		return response;
	    	}
	    	if (debug) System.out.println("file: " + filename);
	    	// Bind Java object to XML
	    	JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(company, file);
			jaxbMarshaller.marshal(company, System.out);
    	} catch (JAXBException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	response = Response.status(Response.Status.CREATED).entity("Company profile for company '" + company.getProfileId() + "' has been created").build();
    	return response;
    }
    
	@PUT
	@Path("/{profileId}")						// TODO seems to be useless. Just set path to "/" ????
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response putCompany(Company company) throws JAXBException {

		String filename = path + company.getProfileId() + ".xml";
    	File file = new File(filename);	// create the file if does not exist
    	if(!file.exists()) {
//	    		file.createNewFile();
    		return Response.status(Response.Status.NOT_FOUND).entity("Company profile for company '" + company.getProfileId() + "' is not found.").build();
    	}
    	if (debug) System.out.println("file: " + filename);
    	// Bind Java object to XML
    	JAXBContext jaxbContext = JAXBContext.newInstance(Company.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(company, file);
		jaxbMarshaller.marshal(company, System.out);

		return Response.status(Response.Status.ACCEPTED).entity("Company profile for company '" + company.getProfileId() + "' has been updated").build();
	}
	
	@DELETE
	@Path("/{profileId}")
	public Response deleteUser(@PathParam("profileId") String profileId) throws IOException {
		Response response;
		String filename = path + profileId + ".xml";
		File file = new File(filename);	// create the file if does not exist
		if(!file.exists()) {
			response = Response.status(Response.Status.NOT_FOUND).entity("Company posting for company '" + profileId + "' is not found.").build();
			return response;
		}
		File dfile = new File(path + "_" + profileId + ".xml");
		boolean success = file.renameTo(dfile);
		if (!success) {
			response = Response.status(Response.Status.FORBIDDEN).entity("Company posting for company '" + profileId + "' cannot be deleted.").build();
			return response;
		}
		response = Response.status(Response.Status.OK).entity("Company posting for company '" + profileId + "' has been deleted").build();
		return response;
	}
}

