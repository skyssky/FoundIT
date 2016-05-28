package au.edu.unsw.soacourse.founditclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.soacourse.loanapprover.*;
import au.edu.unsw.soacourse.loandefinitions.*;


/**
 * Servlet implementation class FoundITServiceClient
 */
@WebServlet("/FoundITServlet")
public class FoundITServiceClient extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public FoundITServiceClient() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		
		
		
		
		
		

//			String input = request.getParameter("license");
//			String input = "1s2d4f4g";
			
//			FoundITAppServer service = new FoundITAppServer(new URL(
//					"http://localhost:6060/ode/processes/AutoCheck/process?wsdl"
////					"http://localhost:8080/FoundITServiceClient/founditappserver?wsdl"
//					));
//			FoundITAppPT port = service.getFoundITAppServerSOAP();
//			ObjectFactory objectFactory = new ObjectFactory();
//			InputType req = objectFactory.createInputType();
//			req.setInput(input);
			
//			LoanApproverServiceT2 service = new LoanApproverServiceT2(new URL("http://localhost:6060/ode/processes/IdCheck/loanapprover?wsdl"));
//			LoanApproverServiceT2 service = new LoanApproverServiceT2();
//			LoanApprovalPT port = service.getLoanApproverSOAP();
//			ObjectFactory objectFactory = new ObjectFactory();
//			ApprovalType res = objectFactory.createApprovalType();
//			LoanInputType req = objectFactory.createLoanInputType();
//			req.setFirstName("May");
//			req.setName("abcd");
//			BigInteger amount = new BigInteger("50001");
//			req.setAmount(amount);
//			res = port.approve(req);
//			System.out.println("res.getAccept() = " + res.getAccept());
			

//			// Display as html
//			response.setContentType("text/html");
//			PrintWriter out = response.getWriter();
//			out.println("<HTML>");
////			out.println("<BODY>");
//			out.println("<HEAD>");
//			out.println("<TITLE>FoundITService Client Page</TITLE>");
//			out.println("</HEAD>");
//			out.println("<BODY>");
//			out.println("<CENTER>");
//			out.print(res.getAccept());
//			out.println("</CENTER>");
//			out.println("</BODY>");
//			out.println("</HTML>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	}

}
