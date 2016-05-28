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

/**
 * Servlet implementation class LoadProfileServlet
 */
@WebServlet("/LoadProfileServlet")
public class LoadProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = request.getParameter("userID");
		String serviceURLString = getServletContext().getInitParameter("RestfulURL")+"users/";
		if(userID != null && userID != ""){
			serviceURLString += userID;
		}
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
			    System.out.println(responseBody);
			}
			response.setContentType("application/json");
			java.io.PrintWriter out = response.getWriter( );
			out.print(responseBody);
			out.flush();
			out.close();	
		}else{
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
