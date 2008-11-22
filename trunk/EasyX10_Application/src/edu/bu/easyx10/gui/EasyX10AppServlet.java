package edu.bu.easyx10.gui;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.bu.easyx10.util.LoggingUtilities;

/**
 * Servlet implementation class EasyX10AppServlet
 */
public class EasyX10AppServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static enum ActionType {ADD_DEVICE, MODIFY_DEVICE, DELETE_DEVIC}; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EasyX10AppServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Retrieve the Action Type from the Request
		String actionString = (String)request.getParameter("action");
		
		ActionType selectedAction = null;
		try {
			selectedAction = ActionType.valueOf(actionString);
		} catch (IllegalArgumentException iae){
			LoggingUtilities.logInfo(EasyX10AppServlet.class.getCanonicalName(), 
					"doPost()", "Unknown ActionType: Action = " + actionString);
		}
		
		LoggingUtilities.logInfo(EasyX10AppServlet.class.getCanonicalName(), 
				"doPost()", "EasyX10AppServlet called: Action = " + actionString);
		
		
		String statusMessage = "Unknown Error - Please Contact Administrator";
		if(selectedAction != null) {
			if(selectedAction == ActionType.ADD_DEVICE) {
				processDeviceAdd(request);
				statusMessage = "Device Successfully Added";
			}
		}
		
		// Set status message in request
		request.setAttribute("statusMessage", statusMessage);
	
		// Redirect Browser to Status Page
		request.getRequestDispatcher("/Status.jsp").forward(request, response); 
		//response.sendRedirect("Status.jsp");
	}
	
	private void processDeviceAdd(HttpServletRequest request){
		
		
		
		LoggingUtilities.logInfo(EasyX10AppServlet.class.getCanonicalName(), 
				"processDeviceAdd()", "New Device Added");
	}

}
