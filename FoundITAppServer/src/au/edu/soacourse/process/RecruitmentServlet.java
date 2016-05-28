package au.edu.soacourse.process;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;

import au.edu.soacourse.httprequest.HttpRequestOp;

/**
 * Servlet implementation class RecruitmentServlet
 */
@WebServlet("/RecruitmentServlet")
public class RecruitmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	HttpRequestOp reqOp = new HttpRequestOp();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecruitmentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Step1. Get list of jobs by manager
		String managerId = request.getParameter("managerId");
		String uri = getServletContext().getInitParameter("RestfulURL") + "jobs?manager=" + managerId;
		HttpResponse hresponse = reqOp.makeGetRequest(uri);
		// TODO display list of jobs to client 
		
		// Step2. Get job profile and update its status to "INREVIEW"
		String jobId = request.getParameter("jobId");
		String uri2 = getServletContext().getInitParameter("RestfulURL") + "jobs?jobId=" + jobId;
		HttpResponse hresponse2 = reqOp.makeGetRequest(uri2);
		// TODO display the job profile to client
		
		// Step3. Get list of applications by jobId
		
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
