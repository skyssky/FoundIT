package au.edu.unsw.soacourse.server;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/* NOTES: 
 * 
 * @Path can be applied to resource classes or methods.
*/

@Path("/hello")	// the URL path will be http://localhost:8080/FoundITServerCxfRest/hello
public class HelloWorld {

    @GET					// the method will handle GET request method on the said path
    @Path("/echo/{input}")	// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/echo/{some text input here}
    @Produces("text/plain")	// the response will contain text plain content. (Note: @Produces({MediaType.TEXT_PLAIN}) means the same)
    public String ping(@PathParam("input") String input) {	// map the path parameter text after /echo to String input.
        return input;
    }

    @POST							// the method will handle POST request method on the said path
    @Produces("application/json")	// the response will contain JSON
    @Consumes("application/json")	// applies to the input parameter JsonBean input. map the POST body content (which will contain JSON) to JsonBean input
    @Path("/jsonBean")				// this method will handle request paths http://localhost:8080/FoundITServerCxfRest/hello/jsonBean
    public Response modifyJson(JsonBean input) {
        input.setVal2(input.getVal1());
        return Response.ok().entity(input).build();
    }
}

