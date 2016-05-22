package au.edu.unsw.soacourse.server;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import au.edu.unsw.soacourse.model.User;

/* NOTES: 
 * 
 * @Path can be applied to resource classes or methods.
*/

@Path("/user")	// the URL path will be http://localhost:8080/FoundITServerCxfRest/hello
public class UsersResource {
 
    @GET					// the method will handle GET request method on the said path
    @Path("/profile/{input}")	// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/echo/{some text input here}
    @Produces("text/plain")	// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
    public String getUserProfile(@PathParam("input") String input) {	// map the path parameter text after /echo to String input.
    	
//    	User user = 
    	
//		if(user == null) throw new RuntimeException("GET: User " + user +  " is not found");
		
    	String userDir = System.getProperty("user.dir");
    	String sourceFile = userDir + "/User/UserProfiles.xml";
    	String xslFile = userDir + "/User/getUserProfile.xsl";
    	String targetFile = userDir + "/User/" + input + ".xml";
    	Runtime rt = Runtime.getRuntime();
//    	try {
//			Process pr = rt.exec("java -jar saxon9he.jar " + sourceFile + " " + xslFile + " username=" + input + " > " + targetFile);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	File fXmlFile = new File("userDir/");
    	System.out.println("GET: userDir = " + userDir);
    	return String.valueOf(userDir);
    }

    @POST							// the method will handle POST request method on the said path
    @Produces(MediaType.APPLICATION_XML)	// the response will contain JSON
    @Consumes(MediaType.APPLICATION_XML)	// applies to the input parameter JsonBean input. map the POST body content (which will contain JSON) to JsonBean input
//    @Path("/jsonBean")				// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/jsonBean
    public User addUserProfile(User user) {
//        System.out.println("POST username = " + username);
//        System.out.println("POST profileId = " + profileId);
//        System.out.println("POST title = " + title);
    	System.out.println("POST: user ");
        return user;
    }
}

