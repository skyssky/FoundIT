package au.edu.soacourse.process;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.soacourse.dao.RegisterDAO;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher("/register.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userNameString = request.getParameter("userName");
		String pwdString = request.getParameter("pwd");
		String roleString = request.getParameter("role");
		String errorMessageString = "";
		//System.out.println(getServletContext().getInitParameter("DBPath"));
		if (userNameString == "" || roleString == null || roleString == "" || pwdString == "" && errorMessageString == "") {
			errorMessageString = "You must fill this form!";
		}
		String pattern1 = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern pattern = Pattern.compile(pattern1);
		Matcher mat = pattern.matcher(userNameString);
		if (!mat.find() && errorMessageString == "") {
			errorMessageString = "Invalid e-mail address!";
		}
		if (errorMessageString == "") {
			RegisterDAO register = new RegisterDAO(getServletContext().getInitParameter("DBPath"));		
			if(register.createAccount(userNameString, pwdString, roleString)){
				String infoMessageString = "Register Success! Welcome "+userNameString+" !";
				request.getSession().setAttribute("infoMessage",
						infoMessageString);
				response.sendRedirect("index");
			}
		}else {
			request.getSession().setAttribute("errorMessage",
					errorMessageString);
			response.sendRedirect("register");
		}
	}

}
