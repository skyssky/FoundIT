package au.edu.soacourse.process;

import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.soacourse.dao.LoginDAO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/login.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userNameString = request.getParameter("userName");
		String pwdString = request.getParameter("pwd");
		String errorMessageString = "";
		//String roleString = request.getParameter("role");
		if (userNameString == "" || pwdString == "" && errorMessageString == "") {
			errorMessageString = "You must fill this form!";
		}
		
		String pattern1 = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern pattern = Pattern.compile(pattern1);
		Matcher mat = pattern.matcher(userNameString);
		if (!mat.find() && errorMessageString == "") {
			errorMessageString = "Invalid e-mail address!";
		}
		
		if (errorMessageString == "") {
			LoginDAO login = new LoginDAO(getServletContext().getInitParameter("DBPath"));
			HashMap<String,String> meta = login.login(userNameString, pwdString);
			if(meta != null){
				String infoMessageString = "Welcome "+userNameString+" !";
				request.getSession().setAttribute("infoMessage",
						infoMessageString);
				request.getSession().setAttribute("userName", meta.get("userName"));
				request.getSession().setAttribute("role", meta.get("role"));
				//System.out.println(meta.get("role"));
				request.getSession().setAttribute("userID", meta.get("userID"));
				response.sendRedirect("index");
			}else{
				errorMessageString = "Password not match User Name!";
				request.getSession().setAttribute("errorMessage",
						errorMessageString);
				response.sendRedirect("login");
			}
		}else {
			request.getSession().setAttribute("errorMessage",
					errorMessageString);
			response.sendRedirect("login");
		}
		
	}

}
