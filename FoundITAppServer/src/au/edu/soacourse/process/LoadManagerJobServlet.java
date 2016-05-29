package au.edu.soacourse.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class ManagerJobServlet
 */
@WebServlet("/LoadManagerJobServlet")
public class LoadManagerJobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadManagerJobServlet() {
        super();
        
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Step2. load manager job servlet - GET*");
		// Step2. Get job profile and update its status to "INREVIEW"
		
		// Read jobId from query string
		String jobId = null;
		String[] queryStrs = request.getQueryString().split("=");
		if (queryStrs.length >= 2) {
			if (queryStrs[0].equals("jobId")) {
				jobId = queryStrs[1];
			}
		}
		
		String serviceURLString = getServletContext().getInitParameter("RestfulURL") + "jobs";
		System.out.println("jobId = *" + jobId + "*");
		
		if (jobId != null && jobId != "") {
			serviceURLString += "/" + jobId;
			//System.out.println(serviceURLString);
			URL serviceURL = new URL(serviceURLString);
			URLConnection connection = serviceURL.openConnection();
			connection.setRequestProperty("Accept", "application/json");
			int responseCode = ((HttpURLConnection) connection).getResponseCode();
			if(responseCode == 200){
				InputStream serviceResponse = connection.getInputStream();
				String responseBody = "";		
				try (Scanner scanner = new Scanner(serviceResponse)) {
				    responseBody = scanner.useDelimiter("\\A").next();
				    System.out.println("responseBody ===>" + responseBody);
				}
				response.setContentType("application/json");
				
				// Update status to "INREVIEW"
				JSONObject profile = new JSONObject(responseBody);
				profile.put("status", "INREVIEW");
//				URL serviceURL = new URL(serviceURLString);
				HttpURLConnection connection2 = (HttpURLConnection) serviceURL.openConnection();
				connection2.setRequestMethod("PUT");
				connection2.setRequestProperty("Content-Type","application/json");
				connection2.setDoOutput(true);
				OutputStreamWriter out2 = new OutputStreamWriter(connection2.getOutputStream());
				//System.out.println("UPDATE\n"+profile.toString());
	            out2.write(profile.toString());
	            out2.flush();
	            out2.close();
	            BufferedReader in = new BufferedReader(new InputStreamReader(connection2.getInputStream()));
	            in.close();
			} else{
				// TODO FAILED TO UPDATE the status
			}
		} else {
			// TODO FAILED TO UPDATE the status
		}
		
		// LIST ALL CANDIDATES ACCORDING TO APPLICATIONS
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
