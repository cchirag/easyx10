package edu.bu.easyx10.gui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.bu.easyx10.device.Device;
import edu.bu.easyx10.device.DeviceManagerFactory;
import edu.bu.easyx10.util.LoggingUtilities;


/**
 * The EasyX10AppServlet represents the main controller for the GUI package.  Once 
 * a user has logged into the system all of their requests go through this servlet.  
 * The servlet will identify the type of request and perform the necessary processing.  
 * The EasyX10AppServlet is a typical Java Servlet that extends HttpServlet.
 *
 * @author winderjj
 * @version Please Refer to Subversion
 * @date Nov 29, 2008
 */
public class EasyX10AppServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// Enum used to determine the type of action performed
	public static enum ActionType {ADD_DEVICE, MODIFY_DEVICE}; 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EasyX10AppServlet() {
        super();
    }

	/**
	 * Routes the user's request to the appropriate method.
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		
		String toPage = "/Status.jsp";
		
		// Retrieve the Action Type from the Request
		String actionString = request.getParameter("action");
		
		ActionType selectedAction = null;
		try {
			selectedAction = ActionType.valueOf(actionString);
		} catch (IllegalArgumentException iae){
			LoggingUtilities.logError(EasyX10AppServlet.class.getCanonicalName(), 
					"doPost()", "Unknown ActionType: Action = " + actionString);
			iae.printStackTrace();
		}
		
		LoggingUtilities.logInfo(EasyX10AppServlet.class.getCanonicalName(), 
				"doPost()", "EasyX10AppServlet called: Action = " + actionString);
		
		
		// Perform the appropriate processing based on the Action
		String statusMessage = "Unknown Error - Please Contact Administrator";
		if(selectedAction != null) {
			// Process ADD_DEVICE Action
			if(selectedAction == ActionType.ADD_DEVICE) {
				String deviceName = request.getParameter("deviceName");
				String deviceType = request.getParameter("deviceType");
				
				// Report error if no name entered
				if( (deviceName == null) || (deviceName.equals("")) ){
					statusMessage = "Cannot leave Name Field Blank";
					
					// Determine the redirect page
					toPage = "/AddMotionSensor.jsp";
					if( deviceType.equals("APPLIANCE") ){
						toPage = "/AddAppliance.jsp";
					}
					
				// Report error if Device name already exists
				} else if( !DeviceManagerFactory.getDeviceManager().isUnique(deviceName)){
					// TODO Repopulate Add Fields
					statusMessage = "Error: Device Name Already Exists";
					
					// Determine the redirect page
					toPage = "/AddMotionSensor.jsp";
					if( deviceType.equals("APPLIANCE") ){
						toPage = "/AddAppliance.jsp";
					}
					
				// Add the new device
				} else{
					processDeviceAdd(request);
					statusMessage = "Device Successfully Added";
					toPage = "/Status.jsp";
				}
				
			// Process MODIFY_DEVICE Action
			} else if(selectedAction == ActionType.MODIFY_DEVICE) {
				
				// Access parameters from the request
				String deviceName = request.getParameter("deviceName");
				String deviceType = request.getParameter("deviceType");
				String update = request.getParameter("update");
				String delete = request.getParameter("delete");
				
				// Process request to delete a device
				if( delete != null ){

					// Delete the Device through the Manager
					DeviceManagerFactory.getDeviceManager().deleteDevice(deviceName);
					
					// Set the status message and page to display
					statusMessage = "Device Sucessfully Deleted";
					toPage = "/Status.jsp";
					
				// Process request to update a device
				} else if( update != null ){
					Device updatedDevice = null;
					if( deviceType.equals("APPLIANCE") ){
						
						// Create a new appliance that is used to update the existing one
						updatedDevice = GuiUtilities.createNewAppliance(request);
					} else if( deviceType.equals("MOTION") ){
						
						// Create a new motion sensor that is used to update the existing one
						updatedDevice = GuiUtilities.createNewMotionSensor(request);
					}
					
					// Update the Device through the Manager
					DeviceManagerFactory.getDeviceManager().updateDevice(updatedDevice);
					
					LoggingUtilities.logInfo(EasyX10AppServlet.class.getCanonicalName(), 
							"processDeviceUpdate()", "Device Updated: " + updatedDevice.getName());
					
					// Set the status message and page to display
					statusMessage = "Device Successfully Updated";
					toPage = "/Status.jsp";
				}
			} 
		}
		
		// Refresh the Device List
		HttpSession session = request.getSession();
		GuiUtilities.updateSessionDeviceList(session);
		
		// Set status message in request
		request.setAttribute("statusMessage", statusMessage);
	
		// Redirect Browser to Status Page
		request.getRequestDispatcher(toPage).forward(request, response); 
	}
	
	
	/**
	 * This method performs the processing required to add a new
	 * device to the system.  This includes creating a new proxy
	 * device and passing it on to the DeviceManager
	 * 
	 * @param request the http request that contains the device data.
	 */
	private void processDeviceAdd(HttpServletRequest request){
		
		// Create the new Device Object
		String deviceType = request.getParameter("deviceType"); 
		Device newDevice = null;
		if( deviceType.equals("APPLIANCE") ){
			// Create Proxy Appliance
			newDevice = GuiUtilities.createNewAppliance(request);
			
			if( newDevice == null ){
				LoggingUtilities.logInfo(EasyX10AppServlet.class.getCanonicalName(), 
					"processDeviceAdd()", "Cannot Add Device - Already Exists");
			}
		} else if (deviceType.equals("MOTION")){
			// Create Proxy Motion Sensor
			newDevice = GuiUtilities.createNewMotionSensor(request);
		} else {
			// Report Error
			LoggingUtilities.logInfo(EasyX10AppServlet.class.getCanonicalName(), 
					"processDeviceAdd", "Unrecognized Device Type: " + deviceType);
		}
		
		// Add the new Device
		DeviceManagerFactory.getDeviceManager().addDevice(newDevice);
		
		LoggingUtilities.logInfo(EasyX10AppServlet.class.getCanonicalName(), 
				"processDeviceAdd()", "New Device Added: " + newDevice.getName());
	}
}
