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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import au.edu.unsw.soacourse.auxiliary.FileOperations;
import au.edu.unsw.soacourse.auxiliary.IdGenerator;
import au.edu.unsw.soacourse.auxiliary.Paths;
import au.edu.unsw.soacourse.model.Company;
import au.edu.unsw.soacourse.model.IdCounter;
import au.edu.unsw.soacourse.model.Review;

/* TODO
 * 
 * 1. GET company profile by managerId
*/

// Example URL: http://localhost:8080/FoundITServerCxfRest/companies?managerId=manager1

public class CompanyResource {
	
	final boolean debug = true;
	Paths path = new Paths();
	FileOperations fop = new FileOperations();
	
    @GET																	
    @Path("/{profileId}")													
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})									
    public Response getCompanyProfile(@PathParam("profileId") String profileId) throws JAXBException {	
    	Company company = null;
		String filename = path.getCompanyPath() + profileId + ".xml";
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
    
    @GET																	
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})		
    public Response getCompanyProfileByManager(@QueryParam("managerId") String managerId) throws JAXBException {
    	Company theCompany = null;
    	Company company = null;
    	Collection<File> files = fop.getFiles(path.getCompanyPath());
    	for (File file: files) {
			// Bind XML to Java object
	    	JAXBContext jaxbContext;
			jaxbContext = JAXBContext.newInstance(Company.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			company = (Company) jaxbUnmarshaller.unmarshal(file);
    		if (debug) System.out.println("Company profile is found: " + company.getProfileId());
    		if (company.getManagerId().equals(managerId)) {
    			theCompany = company;
    			break;
    		}
		}
    	if (theCompany == null) {
    		return Response.status(Response.Status.NOT_FOUND).entity("Company profile for manager '" + managerId + "' is not found.").build();
    	} else {
    		return Response.ok(theCompany, MediaType.APPLICATION_XML).build();
    	}
    }

    @POST								
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public Response addCompanyProfile(Company company) throws JAXBException, IOException {
    	// Get next Id to use
    	IdGenerator idGenerator = new IdGenerator(); 
    	IdCounter idCounter = idGenerator.getCounter(path.getCompanyPath());
    	company.setProfileId("com" + idCounter.getId());
    	idGenerator.updateCounter(path.getCompanyPath(), idCounter);

    	Response response;
		String filename = path.getCompanyPath() + company.getProfileId() + ".xml";
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

    	response = Response.status(Response.Status.CREATED).entity("Company profile for company '" + company.getProfileId() + "' has been created").build();
    	return response;
    }
    
	@PUT
	@Path("/{profileId}")	
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response putCompany(Company company) throws JAXBException {

		String filename = path.getCompanyPath() + company.getProfileId() + ".xml";
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
		String filename = path.getCompanyPath() + profileId + ".xml";
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

