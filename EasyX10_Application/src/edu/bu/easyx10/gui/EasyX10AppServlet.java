package edu.bu.easyx10.gui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.bu.easyx10.device.Device;
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
		String toPage = "/Status.jsp";
		
		// Retrieve the Action Type from the Request
		String actionString = (String)request.getParameter("action");
		
		ActionType selectedAction = null;
		try {
			selectedAction = ActionType.valueOf(actionString);
		} catch (IllegalArgumentException iae){
			LoggingUtilities.logInfo(EasyX10AppServlet.class.getCanonicalName(), 
					"doPost()", "Unknown ActionType: Action = " + actionString);
			iae.printStackTrace();
		}
		
		LoggingUtilities.logInfo(EasyX10AppServlet.class.getCanonicalName(), 
				"doPost()", "EasyX10AppServlet called: Action = " + actionString);
		
		
		// Perform the appropriate processing based on the Action
		String statusMessage = "Unknown Error - Please Contact Administrator";
		if(selectedAction != null) {
			if(selectedAction == ActionType.ADD_DEVICE) {
				if( processDeviceAdd(request) == true ) {
					statusMessage = "Device Successfully Added";
					toPage = "/Status.jsp";
				} else {
					// TODO Repopulate Add Fields
					statusMessage = "Error: Device Name Already Exists";
					toPage = "/AddAppliance.jsp";
				}
			} else if(selectedAction == ActionType.MODIFY_DEVICE) {
				// TODO Add Modify Logic
			} else if(selectedAction == ActionType.DELETE_DEVIC) {
				// TODO Add Delete Logic
			}
		}
		
		// Set status message in request
		request.setAttribute("statusMessage", statusMessage);
	
		// Redirect Browser to Status Page
		request.getRequestDispatcher(toPage).forward(request, response); 
	}
	
	private boolean processDeviceAdd(HttpServletRequest request){
		
		// Create the new Device Object
		String deviceType = request.getParameter("deviceType"); 
		Device newDevice = null;
		if( deviceType.equals("APPLIANCE") ){
			// Create Proxy Appliance
			newDevice = GuiUtilities.createNewAppliance(request);
			
			if( newDevice == null ){
				LoggingUtilities.logInfo(EasyX10AppServlet.class.getCanonicalName(), 
					"processDeviceAdd()", "Cannot Add Device - Already Exists");
				return false;
			}
		} else if (deviceType.equals("MOTION")){
			// Create Proxy Motion Sensor
			newDevice = GuiUtilities.createNewMotionSensor(request);
		} else {
			// Report Error
			LoggingUtilities.logInfo(EasyX10AppServlet.class.getCanonicalName(), 
					"processDeviceAdd", "Unrecognized Device Type: " + deviceType);
			return false;
			
		}
		
		// Add the new Device
		// TODO Interact with Decice Manager
		//DeviceManagerFactory.getDeviceManager().addDevice(newDevice);
		GuiUtilities.getTempDeviceList().add(newDevice);
		
		// Refresh the Device List
		HttpSession session = request.getSession();
		session.setAttribute("deviceList", GuiUtilities.getTempDeviceList());
		
		LoggingUtilities.logInfo(EasyX10AppServlet.class.getCanonicalName(), 
				"processDeviceAdd()", "New Device Added: " + newDevice.getName());
		return true;
	}
}
