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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.RegexFileFilter;

import au.edu.unsw.soacourse.auxiliary.CheckStatus;
import au.edu.unsw.soacourse.auxiliary.FileOperations;
import au.edu.unsw.soacourse.auxiliary.IdGenerator;
import au.edu.unsw.soacourse.auxiliary.Paths;
import au.edu.unsw.soacourse.model.Application;
import au.edu.unsw.soacourse.model.IdCounter;
import au.edu.unsw.soacourse.model.Review;
import au.edu.unsw.soacourse.model.Application.AppStatus;
import au.edu.unsw.soacourse.model.Review.ReviewDecision;

/* TODO
 * 
 * get list of candidates according to list of reviews
*/

@Path("/")	// the URL path will be http://localhost:8080/FoundITServerCxfRest/hello
public class ReviewResource {
	
	FileOperations fop = new FileOperations();
	CheckStatus status = new CheckStatus();
	Paths path = new Paths();
	final boolean debug = true;
//	final String path = System.getProperty("catalina.home") + "/webapps/server-database/review/";
//	final String appPath = System.getProperty("catalina.home") + "/webapps/server-database/application/";
 
    @GET																	// the method will handle GET request method on the said path
//    @Path("")														// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/echo/{some text input here}
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})									// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
    public List<Review> getReviews() {			// map the path parameter text after /echo to String input.
    	// Get all jobs, sort by position ????
    	List<Review> reviews = new ArrayList<Review>();
    	Review review;
    	Collection<File> files = fop.getFiles(path.getReviewPath());
    	try {
    		for (File file: files) {
				// Bind XML to Java object
		    	JAXBContext jaxbContext;
				jaxbContext = JAXBContext.newInstance(Review.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				review = (Review) jaxbUnmarshaller.unmarshal(file);
	    		if (debug) System.out.println("Review is found: " + review.getReviewId());
	    		reviews.add(review);
    		}
    	} catch (JAXBException e) {
			e.printStackTrace();
		}
    	return reviews;
    }
    
    @GET																	// the method will handle GET request method on the said path
    @Path("{reviewId}")														// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/echo/{some text input here}
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})									// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
    public Review getReview(@PathParam("reviewId") String reviewId) {	// map the path parameter text after /echo to String input.
    	Review review = null;
    	try {
    		String filename = path.getReviewPath() + reviewId + ".xml";
	    	File file = new File(filename);
	    	// Bind XML to Java object
	    	JAXBContext jaxbContext = JAXBContext.newInstance(Review.class);
    		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    		review = (Review) jaxbUnmarshaller.unmarshal(file);
    		if (debug) System.out.println("Review is found: " + reviewId);
    	} catch (JAXBException e) {
    		// TODO throw Response/Exception: job posting for job 'jobId' does not exist
    		e.printStackTrace();
    		if (debug) System.out.println("Review '" + reviewId + "' does not exist");
    	}
    	return review;
    }
    
    @GET																	// the method will handle GET request method on the said path
    @Path("search")														// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/echo/{some text input here}
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})									// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
    public List<Review> getReviewsByReviewer(@QueryParam("reviewerId") String reviewerId) throws JAXBException {	// map the path parameter text after /echo to String input.
    	List<Review> reviews = new ArrayList<Review>();
    	Review review = null;
    	Collection<File> files = fop.getFiles(path.getReviewPath());
		for (File file: files) {
			// Bind XML to Java object
	    	JAXBContext jaxbContext;
			jaxbContext = JAXBContext.newInstance(Review.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			review = (Review) jaxbUnmarshaller.unmarshal(file);
    		if (debug) System.out.println("Review is found: " + review.getReviewId());
    		if (review.getReviewerId().equals(reviewerId)) {
    			reviews.add(review);
    		}
		}
//		if (reviews.size() == 0) {
//			return Response.status(Response.Status.NOT_FOUND).entity("Reviews for reviewer '" + reviewerId + "' are not found.").build();
//		}
//    	return Response.ok(reviews, MediaType.APPLICATION_XML).build();
		return reviews;
    }
    

    @POST									// the method will handle POST request method on the said path
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})	// the response will contain JSON
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})	// applies to the input parameter JsonBean input. map the POST body content (which will contain JSON) to JsonBean input
//    @Path("")								// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/jsonBean
    public Response addReview(Review review) throws IOException, JAXBException {
    	
    	// Get next Id to use
    	IdGenerator idGenerator = new IdGenerator(); 
    	IdCounter idCounter = idGenerator.getCounter(path.getReviewPath());
    	review.setReviewId("review" + idCounter.getId());
    	idGenerator.updateCounter(path.getReviewPath(), idCounter);
    	
    	if (!status.appStatus(review.getAppId(), AppStatus.INREVIEW)) {
    		return Response.status(Response.Status.FORBIDDEN).entity("Application '" + review.getAppId() + "' is not INREVIEW").build();
    	}
		String filename = path.getReviewPath() + review.getReviewId() + ".xml";
    	File file = new File(filename);	// create the file if does not exist
    	if(!file.exists()) {
    		file.createNewFile();
    	} else {						// return 'CONFLICT' response if file already exists
    		return Response.status(Response.Status.CONFLICT).entity("Review '" + review.getReviewId() + "' already exists").build();
    	}
    	if (debug) System.out.println("file: " + filename);
    	// Bind Java object to XML
    	JAXBContext jaxbContext = JAXBContext.newInstance(Review.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(review, file);
		jaxbMarshaller.marshal(review, System.out);
		return Response.status(Response.Status.CREATED).entity("Review '" + review.getReviewId() + "' has been created").build();
    }

	@PUT
	@Path("{reviewId}")							// TODO seems to be useless. Just set path to "/" ????
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response putReview(Review review) throws JAXBException {
		Response response;
		String filename = path.getReviewPath() + review.getReviewId() + ".xml";
    	File file = new File(filename);	// create the file if does not exist
    	if(!file.exists()) {
//	    		file.createNewFile();
    		response = Response.status(Response.Status.NOT_FOUND).entity("Review '" + review.getReviewId() + "' is not found.").build();
    		return response;
    	}
    	if (debug) System.out.println("file: " + filename);
    	// Bind Java object to XML
    	JAXBContext jaxbContext = JAXBContext.newInstance(Review.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(review, file);
		jaxbMarshaller.marshal(review, System.out);
		response = Response.status(Response.Status.ACCEPTED).entity("Review '" + review.getReviewId() + "' has been updated").build();
		return response;
	}
	
	@DELETE
	@Path("{reviewId}")
	public Response deleteReview(@PathParam("reviewId") String reviewId) throws IOException {
		Response response;
		String filename = path.getReviewPath() + reviewId + ".xml";
		File file = new File(filename);	// create the file if does not exist
		if(!file.exists()) {
			response = Response.status(Response.Status.NOT_FOUND).entity("Review '" + reviewId + "' is not found.").build();
			return response;
		}
		File dfile = new File(path.getReviewPath() + "_" + reviewId + ".xml");
		boolean success = file.renameTo(dfile);
		if (!success) {
			response = Response.status(Response.Status.FORBIDDEN).entity("Review '" + reviewId + "' cannot be deleted.").build();
			return response;
		}
		response = Response.status(Response.Status.OK).entity("Review '" + reviewId + "' has been deleted").build();
		return response;
	}
    
	public boolean isSpecificDecisionByReview(String reviewId, ReviewDecision decision) {
		List<Review> reviews = getReviews();
		for (Review review: reviews) {
			if (review.getReviewId().equals(reviewId)) {
				if (review.getDecision() == decision) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}
}

