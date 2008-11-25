package edu.bu.easyx10.gui;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.bu.easyx10.util.ConfigurationUtilities;
import edu.bu.easyx10.util.LoggingUtilities;
import edu.bu.easyx10.util.SystemConfiguration;

/**
 * @author winderjj
 *
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static String configFilePath = "/WEB-INF/SysConfig.xml";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub 
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println (System.getProperty("java.library.path"));
		LoggingUtilities.logInfo(LoginServlet.class.getCanonicalName(),
				"doPost()", "Login Servlet Reached");
		HttpSession session = request.getSession();
		
		// Retrieve the username and password from the request
		String username = request.getParameter("username");
		String password = request.getParameter("password");
					
		// Try to Validate the User
		User theUser;
		if( (theUser = validateUser(username, password)) == null ){
			// Invalid: Redirect Back to Login Page
			LoggingUtilities.logInfo(LoginServlet.class.getCanonicalName(),
					"doPost()", "Invalid Login Attempt for " + username);
			
			session.setAttribute("errorMessage", 
					"Username/Password Error<br>Please Try Again");
			response.sendRedirect("Login.jsp");
		} else {
			// Successful Login 
			LoggingUtilities.logInfo(LoginServlet.class.getCanonicalName(),
					"doPost()", "Successful Login for " + username);
			
			// Store user data in the session
			session.setAttribute("currentUser", theUser);
			
			// Set the current floor to display
			session.setAttribute("currentFloor", "floor1");
			
			// Retrieve the list of devices and store in session
			GuiUtilities.updateSessionDeviceList(session);
			
			// Redirect the browser to the status page
			response.sendRedirect("Status.jsp");
		}
		
	}
	
	private User validateUser(String username, String password){
		// Retrieve the list of Users from Configuration
		SystemConfiguration sysConfig = 
			ConfigurationUtilities.getSystemConfiguration();
		List<User> users = sysConfig.getUsers();
		
		// Search for Matching Username and Password
		for( User user : users ){
			if( user.getUsername().equals(username) ){
				// If a match is found return the user
				if( user.getPassword().equals(password)){
					return user;
				}
			}
		}
		
		// Return null if no match was found
		return null;
	}

}
