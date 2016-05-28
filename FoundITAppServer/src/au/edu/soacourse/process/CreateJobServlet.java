package au.edu.soacourse.process;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import au.edu.soacourse.httprequest.HttpRequestOp;

/**
 * Servlet implementation class CreateJobServlet
 */
@WebServlet("/CreateJobServlet")
public class CreateJobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	HttpRequestOp reqOp = new HttpRequestOp();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateJobServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String link = request.getParameter("link");
		String salary = request.getParameter("salary");
		String position = request.getParameter("position");
		String location = request.getParameter("location");
		String detail = request.getParameter("detail");
		String status = request.getParameter("status");
		String skill = request.getParameter("skill");
		
		JSONObject obj = new JSONObject();
		obj.put("link", link);
		obj.put("salary", salary);
		obj.put("position", position);
		obj.put("location", location);
		obj.put("detail", detail);
		obj.put("status", status);
		obj.put("skill", skill);
		
		String objStr = obj.toString();
		System.out.println("objStr = " + objStr);
		
		HttpResponse res = reqOp.makePostRequest(getServletContext().getInitParameter("RestfulURL"), objStr);
		if (res.getStatusLine().getStatusCode() == 201) {
			// TODO tell the client : the job has been created
		} else {
			// TODO tell the client : the job can not created
		}
	}
	
	

}
