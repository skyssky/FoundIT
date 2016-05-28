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

import au.edu.unsw.soacourse.auxiliary.FileOperations;
import au.edu.unsw.soacourse.auxiliary.IdGenerator;
import au.edu.unsw.soacourse.auxiliary.Paths;
import au.edu.unsw.soacourse.model.IdCounter;
import au.edu.unsw.soacourse.model.Job;

/* NOTES: 
 * 
 * @Path can be applied to resource classes or methods.
*/

//@Path("/")	// the URL path will be http://localhost:8080/FoundITServerCxfRest/hello
public class JobResource {
	
	final boolean debug = true;
//	final String path = System.getProperty("catalina.home") + "/webapps/server-database/job/";
	Paths path = new Paths();
	FileOperations fop = new FileOperations();
 
    @GET																	// the method will handle GET request method on the said path
//    @Path("posting")														// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/echo/{some text input here}
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})									// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
    public List<Job> getJobPostings(@QueryParam("sort") String sort) {			// map the path parameter text after /echo to String input.
    	// Get all jobs, sort by position ????
    	List<Job> jobs = new ArrayList<Job>();
    	Job job;
    	Collection<File> files = fop.getFiles(path.getJobPath());
    	try {
    		for (File file: files) {
				// Bind XML to Java object
		    	JAXBContext jaxbContext;
				jaxbContext = JAXBContext.newInstance(Job.class);
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    		job = (Job) jaxbUnmarshaller.unmarshal(file);
	    		if (debug) System.out.println("Job posting is found: " + job.getJobId());
    			jobs.add(job);
    		}
    	} catch (JAXBException e) {
			e.printStackTrace();
		}
    	return jobs;
    }
    
    @GET																	// the method will handle GET request method on the said path
    @Path("{jobId}")														// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/echo/{some text input here}
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})									// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
    public Job getJobPosting(@PathParam("jobId") String jobId) {	// map the path parameter text after /echo to String input.
    	Job job = null;
    	try {
    		String filename = path.getJobPath() + jobId + ".xml";
	    	File file = new File(filename);
	    	// Bind XML to Java object
	    	JAXBContext jaxbContext = JAXBContext.newInstance(Job.class);
    		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
    		job = (Job) jaxbUnmarshaller.unmarshal(file);
    		if (debug) System.out.println("Job posting is found: " + jobId);
    	} catch (JAXBException e) {
    		// TODO throw Response/Exception: job posting for job 'jobId' does not exist
    		e.printStackTrace();
    		if (debug) System.out.println("Job posting for job '" + jobId + "' does not exist");
    	}
    	return job;
    }

    @POST									// the method will handle POST request method on the said path
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})	// the response will contain JSON
    @Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})	// applies to the input parameter JsonBean input. map the POST body content (which will contain JSON) to JsonBean input
//    @Path("posting")								// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/jsonBean
    public Response addJobPosting(Job job) throws IOException, JAXBException {
    	
    	// Get next Id to use
    	IdGenerator idGenerator = new IdGenerator(); 
    	IdCounter idCounter = idGenerator.getCounter(path.getJobPath());
    	job.setJobId("job" + idCounter.getId());
    	idGenerator.updateCounter(path.getJobPath(), idCounter);
    	
    	Response response;
		String filename = path.getJobPath() + job.getJobId() + ".xml";
    	File file = new File(filename);	// create the file if does not exist
    	if(!file.exists()) {
    		file.createNewFile();
    	} else {						// return 'CONFLICT' response if file already exists
    		response = Response.status(Response.Status.CONFLICT).entity("Job posting for job '" + job.getJobId() + "' already exists").build();
    		return response;
    	}
    	if (debug) System.out.println("file: " + filename);
    	// Bind Java object to XML
    	JAXBContext jaxbContext = JAXBContext.newInstance(Job.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		jaxbMarshaller.marshal(job, file);
		jaxbMarshaller.marshal(job, System.out);

    	response = Response.status(Response.Status.CREATED).entity("Job posting profile for job '" + job.getJobId() + "' has been created").build();
    	return response;
    }
    
	@PUT
	@Path("{jobId}")							// TODO seems to be useless. Just set path to "/" ????
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	public Response putJob(Job job) {
		Response response;
		try {
			String filename = path.getJobPath() + job.getJobId() + ".xml";
	    	File file = new File(filename);	// create the file if does not exist
	    	if(!file.exists()) {
	    		file.createNewFile();
	    		response = Response.status(Response.Status.NOT_FOUND).entity("Job posting for job '" + job.getJobId() + "' is not found.").build();
	    		return response;
	    	}
	    	if (debug) System.out.println("file: " + filename);
	    	// Bind Java object to XML
	    	JAXBContext jaxbContext = JAXBContext.newInstance(Job.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(job, file);
			jaxbMarshaller.marshal(job, System.out);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		response = Response.status(Response.Status.ACCEPTED).entity("Job posting for job '" + job.getJobId() + "' has been updated").build();
		return response;
	}
	
	@DELETE
	@Path("{jobId}")
	public Response deleteJob(@PathParam("jobId") String jobId) throws IOException {
		Response response;
		String filename = path.getJobPath() + jobId + ".xml";
		File file = new File(filename);	// create the file if does not exist
		if(!file.exists()) {
			response = Response.status(Response.Status.NOT_FOUND).entity("Job posting for job '" + jobId + "' is not found.").build();
			return response;
		}
		File dfile = new File(path.getJobPath() + "_" + jobId + ".xml");
		boolean success = file.renameTo(dfile);
		if (!success) {
			response = Response.status(Response.Status.FORBIDDEN).entity("Job posting for job '" + jobId + "' cannot be deleted.").build();
			return response;
		}
		response = Response.status(Response.Status.OK).entity("Job posting for job '" + jobId + "' has been deleted").build();
		return response;
	}

    // keyword, skill, status
	@GET																	// the method will handle GET request method on the said path
	@Path("search")														// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/echo/{some text input here}
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})									// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
	public List<Job> searchJobPostingByKSS(@QueryParam("keyword") String keyword, @QueryParam("skill") String skill, @QueryParam("status") String status) {			// map the path parameter text after /echo to String input.
	  	// Search with keyword, sort by position ????
		boolean keywordIsSpecified = true;
		if (keyword == null) {
			keyword = "";
			keywordIsSpecified = false;
		}
		boolean skillIsSpecified = true;
		if (skill == null) {
			skill = "";
			skillIsSpecified = false;
		}
		boolean statusIsSpecified = true;
		if (status == null) {
			status = "";
			statusIsSpecified = false;
		}

	  	keyword = keyword.toLowerCase();
	  	skill = skill.toLowerCase();
	    List<Job> jobs = new ArrayList<Job>();
	  	Job job;
	  	Collection<File> files = fop.getFiles(path.getJobPath());
	  	try {
	  		for (File file: files) {
	  			System.out.println("In file: " + file.toString());
					// Bind XML to Java object
			    	JAXBContext jaxbContext;
					jaxbContext = JAXBContext.newInstance(Job.class);
					Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
					System.out.println("filename = *" + file.toString() + "*");
		    		job = (Job) jaxbUnmarshaller.unmarshal(file);
		    		if (debug) System.out.println("Job posting is found: " + job.getJobId());
		    		if (keywordIsSpecified) {
		    			System.out.println("==> " + "Java Developer".contains(keyword));
		    			if (job.getPosition().toLowerCase().contains(keyword) || job.getDetail().toLowerCase().contains(keyword)) {
		    				System.out.println("keyword match => add a job");
		    				jobs.add(job);
		    			}
		    		} else {
		    			jobs.add(job);
		    		}
	  		}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	  	
	  	for (Job j: jobs) {
	  		if (skillIsSpecified) {
	  			if (!j.getSkill().toLowerCase().contains(skill)) {
	  				System.out.println("skill NOT match => delete a job");
	  				jobs.remove(j);
	  			}
	  		} 
	  	}
	  	
	  	for (Job j: jobs) {
	  		if (statusIsSpecified) {
	  			if (!j.getStatus().equals(status)) {
	  				System.out.println("status NOT match => delete a job");
	  				jobs.remove(j);
	  			}
	  		}
	  	}
	  	
	  	return jobs;
	}
}

