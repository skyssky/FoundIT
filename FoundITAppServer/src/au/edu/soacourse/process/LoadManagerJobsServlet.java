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
 * Servlet implementation class ManagerServlet
 */
@WebServlet("/LoadManagerJobsServlet")
public class LoadManagerJobsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadManagerJobsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Step1. Get list of jobs by manager
		String managerId = request.getParameter("userID");
//		String jobId = request.getParameter("jobId");
		String serviceURLString = getServletContext().getInitParameter("RestfulURL") + "jobs";
		System.out.println("managerId = *" + managerId + "*");
//		System.out.println("jobId = *" + jobId + "*");

		if(managerId != null && managerId != ""){
			serviceURLString += "?manager=" + managerId;
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
		
//				
//				// Step2. Get job profile and update its status to "INREVIEW"
//				String jobId = request.getParameter("jobId");
//				uri = getServletContext().getInitParameter("RestfulURL") + "jobs/" + jobId;
//				HttpResponse hresponse2 = reqOp.makeGetRequest(uri);
//				// TODO display the job profile to client, finally close the response connection by close()
//				
//				// Step3. Get list of applications by jobId
//				uri = getServletContext().getInitParameter("RestfulURL") + "apps?jobId=" + jobId;
//				HttpResponse hresponse3 = reqOp.makeGetRequest(uri);
//				
//				// Step4. Get list of candidates' profiles by list of appIds
////				TODO for () {
//					String appId = "";
//					uri = getServletContext().getInitParameter("RestfulURL") + "users?appId=" + appId;
//					HttpResponse hresponse4 = reqOp.makeGetRequest(uri);
////				}
//				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
