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
 * Servlet implementation class CompanyProfileServlet
 */
@WebServlet("/CompanyProfileServlet")
public class CompanyProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CompanyProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/company.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String managerId = (String) request.getSession().getAttribute("userID");
		String formManagerID = request.getParameter("userID");
		String site = request.getParameter("site");
		String name = request.getParameter("name");
		String detail = request.getParameter("detail");
		String hq = request.getParameter("hq");
		String type = request.getParameter("type");
		String serviceURLString = getServletContext().getInitParameter("RestfulURL")+"companies";
		JSONObject profile = new JSONObject();
		if(formManagerID != null && !formManagerID.equals("")){
			//update profile use HTTP/PUT
			serviceURLString += "/"+formManagerID;
			profile.put("managerId", formManagerID);
			profile.put("hq", hq);
			profile.put("name", name);
			profile.put("detail", detail);
			profile.put("type",type);
			profile.put("site", site);
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
			profile.put("managerId", formManagerID);
			profile.put("hq", hq);
			profile.put("name", name);
			profile.put("detail", detail);
			profile.put("type",type);
			profile.put("site", site);
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
	}

}
