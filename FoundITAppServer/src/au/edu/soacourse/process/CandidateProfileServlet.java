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
 * Servlet implementation class CandidateProfile
 */
@WebServlet("/CandidateProfile")
public class CandidateProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CandidateProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/profile.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userID = (String) request.getSession().getAttribute("userID");
		String formUserID = request.getParameter("userID");
		String skill = request.getParameter("skill");
		String name = request.getParameter("name");
		String position = request.getParameter("position");
		String education = request.getParameter("education");
		String experience = request.getParameter("experience");
		String address = request.getParameter("address");
		String license = request.getParameter("license");
		String serviceURLString = getServletContext().getInitParameter("RestfulURL")+"users";
		JSONObject profile = new JSONObject();
		if(formUserID != null && !formUserID.equals("")){
			//update profile use HTTP/PUT
			serviceURLString += "/"+formUserID;
			profile.put("userId", formUserID);
			profile.put("skill", skill);
			profile.put("name", name);
			profile.put("position", position);
			profile.put("education",education);
			profile.put("experience", experience);
			profile.put("address", address);
			profile.put("license", license);
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
			//create profile use HTTP/POST
			profile.put("userId", userID);
			profile.put("skill", skill);
			profile.put("name", name);
			profile.put("position", position);
			profile.put("education",education);
			profile.put("experience", experience);
			profile.put("address", address);
			profile.put("license", license);
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
