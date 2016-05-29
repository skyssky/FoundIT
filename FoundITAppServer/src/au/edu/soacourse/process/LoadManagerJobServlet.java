package au.edu.soacourse.process;

import java.io.IOException;
import java.io.InputStream;
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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Step2. Get job profile and update its status to "INREVIEW"
		String managerId = request.getParameter("userID");
		String jobId = request.getParameter("jobId");
		String serviceURLString = getServletContext().getInitParameter("RestfulURL") + "jobs";
		System.out.println("managerId = *" + managerId + "*");
		System.out.println("jobId = *" + jobId + "*");
		
		if (managerId != null && managerId != "" && jobId != null && jobId != "") {
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
				    //System.out.println(responseBody);
				}
				response.setContentType("application/json");
				
				// TODO update status to "INREVIEW"
				JSONObject profile = new JSONObject();
				profile.put("status", "INREVIEW");
				
				
				
				java.io.PrintWriter out = response.getWriter( );
				out.print(responseBody);
				out.flush();
				out.close();	
			} else{
				java.io.PrintWriter out = response.getWriter( );
				out.print("{}");
				out.flush();
				out.close();	
			}
		} else {
			// DO NOT HANDLE ==> return empty to user, as manager can only see jobs he created at this recruitment process
			java.io.PrintWriter out = response.getWriter( );
			out.print("{}");
			out.flush();
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
