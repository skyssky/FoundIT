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
import au.edu.unsw.soacourse.model.Application;
import au.edu.unsw.soacourse.model.HiringTeam;
import au.edu.unsw.soacourse.model.IdCounter;
import au.edu.unsw.soacourse.model.Review.ReviewDecision;

/* TODO 
 * 
 * Need a method to get all teams in which the reviews are all done 
*/

public class HiringTeamResource {
	
	final boolean debug = true;
	FileOperations fop = new FileOperations();
	Paths path = new Paths();
//	final String teamPath = System.getProperty("catalina.home") + "/webapps/server-database/team/";
 
    @GET																	// the method will handle GET request method on the said path
    @Path("/{teamId}")											// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/echo/{some text input here}
    @Produces(MediaType.APPLICATION_XML)									// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
    public Response getHiringTeam(@PathParam("teamId") String teamId) throws JAXBException {	// map the path parameter text after /echo to String input.
    	HiringTeam team = null;
		String filename = path.getTeamPath() + teamId + ".xml";
    	File file = new File(filename);
    	if (!file.exists()) {
    		return Response.status(Response.Status.NOT_FOUND).entity("Hiring team '" + teamId + "' is not found.").build();
    	}
    	// Bind XML to Java object
    	JAXBContext jaxbContext = JAXBContext.newInstance(HiringTeam.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		team = (HiringTeam) jaxbUnmarshaller.unmarshal(file);
		if (debug) System.out.println("hiring team is found: " + teamId);
		return Response.ok(team, MediaType.APPLICATION_XML).build();
    }
    
    @GET																	// the method will handle GET request method on the said path
//    @Path("/{appId}")											// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/echo/{some text input here}
    @Produces(MediaType.APPLICATION_XML)									// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
    public Response getHiringTeamByApp(@QueryParam("appId") String appId) throws JAXBException {	// map the path parameter text after /echo to String input.
    	Application application = null;
    	HiringTeam team = null;
    	String reviewId1 = null, reviewId2 = null;
    	
    	Collection<File> files = fop.getFiles(path.getTeamPath());
		for (File file: files) {
			// Bind XML to Java object
	    	JAXBContext jaxbContext;
			jaxbContext = JAXBContext.newInstance(HiringTeam.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			team = (HiringTeam) jaxbUnmarshaller.unmarshal(file);
			if (team.getAppId().equals(appId)) {
				reviewId1 = team.getReviewId1();
	    		reviewId2 = team.getReviewId2();
	    		// check decision of each review, if all YES, add it to teams
	    		// TODO check status of APPLICATION, if processed, then check review decision
	    		ReviewResource reviewResource = new ReviewResource();
	    		// TODO status: YES, NO, WAITING (shall return resources which is not WAITING to APP, the app shall collate the result)
	    		if (reviewResource.isSpecificDecisionByReview(reviewId1, ReviewDecision.YES) && reviewResource.isSpecificDecisionByReview(reviewId2, ReviewDecision.YES)) {
	    			return Response.ok(true, MediaType.APPLICATION_XML).build();
	    		} else {
	    			return Response.ok(false, MediaType.APPLICATION_XML).build();
	    		}
			}
		}
		return Response.status(Response.Status.NOT_FOUND).entity("Hiring team for application '" + appId + "' is not found.").build();
    }
    
    @GET																	// the method will handle GET request method on the said path
    @Path("/finished")											// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/echo/{some text input here}
    @Produces(MediaType.APPLICATION_XML)									// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
    public List<HiringTeam> getFinishedHiringTeam() throws JAXBException {	// map the path parameter text after /echo to String input.
    	List<HiringTeam> teams = new ArrayList<HiringTeam>();
    	HiringTeam team = null;
    	String reviewId1 = null, reviewId2 = null;
    	Collection<File> files = fop.getFiles(path.getTeamPath());
		for (File file: files) {
			// Bind XML to Java object
	    	JAXBContext jaxbContext;
			jaxbContext = JAXBContext.newInstance(HiringTeam.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			team = (HiringTeam) jaxbUnmarshaller.unmarshal(file);
    		if (debug) System.out.println("Hiring team is found: " + team.getTeamId());
    		reviewId1 = team.getReviewId1();
    		reviewId2 = team.getReviewId2();
    		// check decision of each review, if all YES, add it to teams
    		// TODO check status of APPLICATION, if processed, then check review decision
    		// TODO shall return finished for specific managerId
    		ReviewResource reviewResource = new ReviewResource();
    		if (reviewResource.isSpecificDecisionByReview(reviewId1, ReviewDecision.YES) && reviewResource.isSpecificDecisionByReview(reviewId2, ReviewDecision.YES)) {
    			teams.add(team);
    		}
		}
//		return Response.ok(teams, MediaType.APPLICATION_XML).build();
		return teams;
    }

    @POST									// the method will handle POST request method on the said path
    @Produces(MediaType.APPLICATION_XML)	// the response will contain JSON
    @Consumes(MediaType.APPLICATION_XML)	// applies to the input parameter JsonBean input. map the POST body content (which will contain JSON) to JsonBean input
//    @Path("/")								// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/jsonBean
    public Response addHiringTeam(HiringTeam team) throws JAXBException, IOException {
    	
    	// Get next Id to use
    	IdGenerator idGenerator = new IdGenerator(); 
    	IdCounter idCounter = idGenerator.getCounter(path.getTeamPath());
    	team.setTeamId("team" + idCounter.getId());
    	idGenerator.updateCounter(path.getTeamPath(), idCounter);
    	
		String filename = path.getTeamPath() + team.getTeamId() + ".xml";
		File file = new File(filename);	// create the file if does not exist
		if(!file.exists()) {
			file.createNewFile();
		} else {						// return 'CONFLICT' response if file already exists
			return Response.status(Response.Status.CONFLICT).entity("Hiring team '" + team.getTeamId() + "' already exists").build();
		}
		if (debug) System.out.println("file: " + filename);
		// Bind Java object to XML
		JAXBContext jaxbContext = JAXBContext.newInstance(HiringTeam.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(team, file);
		jaxbMarshaller.marshal(team, System.out);		
    	return Response.status(Response.Status.CREATED).entity("Hiring team '" + team.getTeamId() + "' has been created").build();
    }
    
	@PUT
	@Path("/{teamId}")						
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response putHiringTeam(HiringTeam team) throws JAXBException {
		String filename = path.getTeamPath() + team.getTeamId() + ".xml";
		File file = new File(filename);	// create the file if does not exist
		if(!file.exists()) {
//			file.createNewFile();
			return Response.status(Response.Status.NOT_FOUND).entity("Hiring team '" + team.getTeamId() + "' is not found.").build();
		}
		if (debug) System.out.println("file: " + filename);
		// Bind Java object to XML
		JAXBContext jaxbContext = JAXBContext.newInstance(HiringTeam.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(team, file);
		jaxbMarshaller.marshal(team, System.out);
		return Response.status(Response.Status.ACCEPTED).entity("Hiring team '" + team.getTeamId() + "' has been updated").build();
	}
	
	@DELETE
	@Path("/{teamId}")
	public Response deleteHiringTeam(@PathParam("teamId") String teamId) throws IOException {
		String filename = path.getTeamPath() + teamId + ".xml";
		File file = new File(filename);	// create the file if does not exist
		if(!file.exists()) {
			return Response.status(Response.Status.NOT_FOUND).entity("Hiring team '" + teamId + "' is not found.").build();
		}
		File dfile = new File(path.getTeamPath() + "_" + teamId + ".xml");
		boolean success = file.renameTo(dfile);
		if (!success) {
			return Response.status(Response.Status.FORBIDDEN).entity("Hiring team '" + teamId + "' cannot be deleted.").build();
		}
		return Response.status(Response.Status.OK).entity("Hiring team '" + teamId + "' has been deleted").build();
	}
}

