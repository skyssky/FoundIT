package au.edu.soacourse.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class ManagerJobServlet
 */
@WebServlet("/ManagerJobServlet")
public class ManagerJobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerJobServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String jobId = request.getParameter("jobId");
		request.setAttribute("jobId", jobId);
		getServletContext().getRequestDispatcher("/recruitmentjob.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String managerId = (String) request.getSession().getAttribute("userID");
		String jobId = request.getParameter("jobId");
		String position = request.getParameter("position");
		String salary = request.getParameter("salary");
		String location = request.getParameter("location");
		String skill = request.getParameter("skill");
		String link = request.getParameter("link");
		String detail = request.getParameter("detail");
		System.out.println(jobId);
		String serviceURLString = getServletContext().getInitParameter("RestfulURL") + "jobs";
		JSONObject job = new JSONObject();
		if(jobId != null && !jobId.equals("")){
			//update job use HTTP/PUT
			serviceURLString += "/"+jobId;
			System.out.println(serviceURLString);
			job.put("jobId", jobId);
			job.put("position", position);
			job.put("salary", salary);
			job.put("location", location);
			job.put("skill", skill);
			job.put("link",link);
			job.put("detail", detail);
			URL serviceURL = new URL(serviceURLString);
			HttpURLConnection connection = (HttpURLConnection) serviceURL.openConnection();
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type","application/json");
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			//System.out.println("UPDATE\n"+profile.toString());
            out.write(job.toString());
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            in.close();
		}else{
			//create job use HTTP/POST
			job.put("managerId", managerId);
			job.put("position", position);
			job.put("salary", salary);
			job.put("location", location);
			job.put("skill", skill);
			job.put("link",link);
			job.put("detail", detail);
			URL serviceURL = new URL(serviceURLString);
			HttpURLConnection connection = (HttpURLConnection) serviceURL.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type","application/json");
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			//System.out.println("CREATE\n"+profile.toString());
            out.write(job.toString());
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            in.close();
		}
		java.io.PrintWriter out = response.getWriter();
		out.print("Change successfully saved!");
		out.flush();
		out.close();
	}
}
