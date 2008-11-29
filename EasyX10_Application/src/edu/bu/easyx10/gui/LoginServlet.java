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
 * The LoginServlet class implements the functionality to process a user’s 
 * request to log into the EasyX10 System.  The main method of the class 
 * doPost() is called when the user submits a request to log in.  
 * LoginServlet is a typical Java Servlet that extends HttpServlet.
 *
 * @author winderjj
 * @version Please Refer to Subversion
 * @date Nov 29, 2008
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static String configFilePath = "/WEB-INF/SysConfig.xml";
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * Process a user's request to log into the system.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoggingUtilities.logInfo(LoginServlet.class.getCanonicalName(),
				"doPost()", "Login Servlet Executed");
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
	
	/**
	 * Validates a username and password against the user data 
	 * stored in the system configuration file.
	 * 
	 * @param username the username to check.
	 * @param password the password to check.
	 * 
	 * @return the User object if the validation was successful.
	 * Otherwise, null is returned.
	 */
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
