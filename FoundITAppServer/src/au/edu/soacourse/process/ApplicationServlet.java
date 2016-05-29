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
 * Servlet implementation class ApplicationServlet
 */
@WebServlet("/ApplicationServlet")
public class ApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ApplicationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String applyForJobId = request.getParameter("jobId");
		request.setAttribute("jobId", applyForJobId);
		getServletContext().getRequestDispatcher("/editapplication.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = (String) request.getSession().getAttribute("userID");
		String cover = request.getParameter("cover");
		String jobId = request.getParameter("jobId");
		String appId = request.getParameter("appId");
		String serviceURLString = getServletContext().getInitParameter("RestfulURL")+"applications";
		JSONObject profile = new JSONObject();
		if(appId != null && !appId.equals("")){		
			//update application use HTTP/PUT
			serviceURLString += "/"+appId;
			profile.put("cover", cover);
			profile.put("jobId", jobId);
			profile.put("userID", userID);
			profile.put("appId", appId);
			URL serviceURL = new URL(serviceURLString);
			HttpURLConnection connection = (HttpURLConnection) serviceURL.openConnection();
			connection.setRequestMethod("PUT");
			connection.setRequestProperty("Content-Type","application/json");
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			//System.out.println("UPDATE\n"+profile.toString());
            out.write(profile.toString());
            out.flush();
            out.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            in.close();
		}else{
			//create application use HTTP/POST
			profile.put("cover", cover);
			profile.put("jobId", jobId);
			profile.put("userId", userID);
			URL serviceURL = new URL(serviceURLString);
			HttpURLConnection connection = (HttpURLConnection) serviceURL.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type","application/json");
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			//System.out.println("CREATE\n"+profile.toString());
            out.write(profile.toString());
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
