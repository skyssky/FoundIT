package au.edu.unsw.soacourse.dataservice;

// Search job alerts by keyword (and sort by position)

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import au.edu.unsw.soacourse.auxiliary.Paths;
import au.edu.unsw.soacourse.model.JobAlerts;

/* Example requests: 
 * 
 * URL: http://localhost:8080/FoundITServerCxfRest/jobalerts?keyword=java
 * URL: http://localhost:8080/FoundITServerCxfRest/jobalerts?keyword=java&sort_by=jobtitle
*/

//@Path("/")	// the URL path will be http://localhost:8080/FoundITServerCxfRest/hello
public class DataService {
	
	final boolean debug = true;
	Paths path = new Paths();

	@GET																	// the method will handle GET request method on the said path
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})									// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
	public Response getJobAlertsByKeywordAndSort(@QueryParam("keyword") String keyword, @QueryParam("sort_by") String sort_by) throws TransformerException, JAXBException {			// map the path parameter text after /echo to String input.
  	
    	File stylesheet = null, datafile = null, outFile = null;
    	
    	if (sort_by != null && !sort_by.equals("jobtitle") && keyword != null) {
    		// Unknown sort_by 
    		return Response.status(Response.Status.NOT_FOUND).entity("UNKNOWN: Cannot sort by '" + sort_by).build();
    		
    	} else if (sort_by == null && keyword != null) {
    		// search by keyword
    		stylesheet = new File(path.getJobalertPath() + "job2Search.xsl");
    	    datafile = new File(path.getJobalertPath() + "joblist2.xml");
    	    outFile = new File(path.getJobalertPath() + "job2SearchResult.xml");
    	    
    	} else if (sort_by != null && keyword != null && sort_by.equals("jobtitle")) {
    		// search by keyword and sort by jobtitle
    		stylesheet = new File(path.getJobalertPath() + "job2SearchSort.xsl");
    	    datafile = new File(path.getJobalertPath() + "joblist2.xml");
    	    outFile = new File(path.getJobalertPath() + "job2SearchSortResult.xml");
    	    
    	} else {
    		// bad request: illegal keyword
    		return Response.status(Response.Status.BAD_REQUEST).entity("Expected: keyword=XXX (&sort_by=jobtitle).").build();
    	}
    	
	    TransformerFactory factory = TransformerFactory.newInstance();
	    StreamSource xslStream = new StreamSource(stylesheet);
	    Transformer transformer = factory.newTransformer(xslStream);
	    transformer.setParameter("keyword", keyword);
	    StreamSource in = new StreamSource(datafile);
	    StreamResult out = new StreamResult(outFile);
	    transformer.transform(in, out);
	    
//	    JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] {JobAlert.class, JobAlerts.class});
	    JAXBContext jaxbContext = JAXBContext.newInstance(JobAlerts.class);
	    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	    JobAlerts jobalerts = (JobAlerts) jaxbUnmarshaller.unmarshal(outFile);
	    
		return Response.ok(jobalerts, MediaType.APPLICATION_XML).build();
	}
}


