package au.edu.soacourse.process;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class LoadApplicationListServlet
 */
@WebServlet("/LoadApplicationListServlet")
public class LoadApplicationListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadApplicationListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = request.getParameter("userID");
		System.out.println(userID);
		String serviceURLString = getServletContext().getInitParameter("RestfulURL")+"applications?";
		if(userID != null && userID != ""){
			serviceURLString += "userId="+userID;
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
				JSONArray applications = new JSONArray(responseBody);
				for(int i = 0; i < applications.length(); i++){
					JSONObject application = applications.getJSONObject(i);
					String serviceURLString2 = getServletContext().getInitParameter("RestfulURL")+"jobs/"+application.getString("jobId");
					//System.out.println(serviceURLString2);
					URL serviceURL2 = new URL(serviceURLString2);
					URLConnection connection2 = serviceURL2.openConnection();
					connection2.setRequestProperty("Accept", "application/json");
					int responseCode2 = ((HttpURLConnection) connection2).getResponseCode();
					if(responseCode2 == 200){
						InputStream serviceResponse2 = connection2.getInputStream();
						String responseBody2 = "";		
						try (Scanner scanner = new Scanner(serviceResponse2)) {
						    responseBody2 = scanner.useDelimiter("\\A").next();
						    //System.out.println(responseBody2);
						}
						JSONObject job = new JSONObject(responseBody2);
						Iterator<?> keys = job.keys();
						while( keys.hasNext() ) {
						    String key = (String)keys.next();
						    applications.getJSONObject(i).put(key, job.get(key));
						}
					}
				}
				
				response.setContentType("application/json");
				java.io.PrintWriter out = response.getWriter( );
				out.print(applications);
				out.flush();
				out.close();	
			}else{
				java.io.PrintWriter out = response.getWriter( );
				out.print("{}");
				out.flush();
				out.close();	
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
