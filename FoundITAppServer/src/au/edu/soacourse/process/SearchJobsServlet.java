package au.edu.soacourse.process;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class SearchJobsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchJobsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/search.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		String skill = request.getParameter("skill");
		String jobstatus = request.getParameter("jobstatus");
		String errorMessageString = "";
		if (keyword == "" && skill == "" && jobstatus == "" && errorMessageString == "") {
			errorMessageString = "";
		}
		String serviceURLString = getServletContext().getInitParameter("RestfulURL")+"jobs/search?";
		if(keyword != null && keyword != ""){
			serviceURLString = serviceURLString+"keyword="+keyword;
		}
		if(skill != null && skill != ""){
			if(keyword != null && keyword != ""){
				serviceURLString = serviceURLString+"&skill="+skill;
			}else{
				serviceURLString = serviceURLString+"skill="+skill;
			}
		}
		if(jobstatus != null && jobstatus != ""){
			if((skill != null && skill != "") || (keyword != null && keyword != "")){				
				serviceURLString = serviceURLString+"&status="+jobstatus;			
			} else {
				serviceURLString = serviceURLString+"status="+jobstatus;
			}
		}
		//System.out.println(serviceURLString);
		URL serviceURL = new URL(serviceURLString);
		URLConnection connection = serviceURL.openConnection();
		connection.setRequestProperty("Accept", "application/json");
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
	}
}
