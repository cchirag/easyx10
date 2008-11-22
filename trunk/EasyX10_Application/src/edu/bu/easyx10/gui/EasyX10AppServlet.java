package edu.bu.easyx10.gui;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.bu.easyx10.device.Device;
import edu.bu.easyx10.device.DeviceLocation;
import edu.bu.easyx10.device.ProxyX10Appliance;
import edu.bu.easyx10.device.X10Device;
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
		
		
		// Perform the appropriate processing based on the Action
		String statusMessage = "Unknown Error - Please Contact Administrator";
		if(selectedAction != null) {
			if(selectedAction == ActionType.ADD_DEVICE) {
				processDeviceAdd(request);
				statusMessage = "Device Successfully Added";
			} else if(selectedAction == ActionType.MODIFY_DEVICE) {
				// TODO Add Modify Logic
			} else if(selectedAction == ActionType.DELETE_DEVIC) {
				// TODO Add Delete Logic
			}
		}
		
		// Set status message in request
		request.setAttribute("statusMessage", statusMessage);
	
		// Redirect Browser to Status Page
		request.getRequestDispatcher("/Status.jsp").forward(request, response); 
	}
	
	private void processDeviceAdd(HttpServletRequest request){
		
		// Create the new Device Object
		String deviceType = request.getParameter("deviceType"); 
		Device newDevice = null;
		if( deviceType.equals("APPLIANCE") ){
			// Create Proxy Appliance
			newDevice = createNewAppliance(request);
		} else if (deviceType.equals("MOTION")){
			// Create Proxy Motion Sensor
		} else {
			// Report Error
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
	}
	
	private ProxyX10Appliance createNewAppliance(HttpServletRequest request){
		
		// Retrieve the name/house code/unit code
		String name = request.getParameter("applianceName");
		char houseCode = request.getParameter("houseCode").charAt(0);
		int unitCode = Integer.parseInt(request.getParameter("unitCode"));
		
		// Check to see if this device already exists
		// TODO: Add Device Manager Code 
		// Device alreadyExists = DeviceManagerFactory.getDeviceManager().getDevice(name);
		List<Device> tempDeviceList = GuiUtilities.getTempDeviceList();
		boolean deviceExists = false;
		for( Device d : tempDeviceList ){
			if( d.getName().equals(name) ){
				deviceExists = true;
				break;
			}
		}
		
		// If the Device Exists Log an Error
		if( deviceExists ){
			// TODO Throw an exception
			return null;
		}
		
		// Create the new Appliance
		ProxyX10Appliance newDevice = new ProxyX10Appliance(name, houseCode, unitCode);
		
		// Set the State
		newDevice.setState(
				X10Device.X10DeviceState.valueOf(request.getParameter("deviceStatus")));
		
		// Set timer details
		// TODO Fix time formatting
		if( request.getParameter("timer").equals("ON")){
			SimpleDateFormat dateFormat = new SimpleDateFormat("h:mma");
			
			// Retrieve the time strings
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
		
			// Set the On Time
			try{
				Calendar onTime = Calendar.getInstance();
				onTime.setTime(dateFormat.parse(startTime));
				newDevice.setOnTime(onTime);
			} catch( ParseException  pe){
				pe.printStackTrace();
			}
			
			// Set the Off Time
			try{
				Calendar offTime = Calendar.getInstance();
				offTime.setTime(dateFormat.parse(endTime));
				newDevice.setOnTime(offTime);
			} catch( ParseException  pe){
				pe.printStackTrace();
			}
		}
			
		// Set the Location
		String leftString = request.getParameter("left");
		int endLeft = leftString.indexOf("px");
		String topString = request.getParameter("top");
		int endTop = topString.indexOf("px");
		DeviceLocation location = 
			new DeviceLocation(Integer.parseInt(request.getParameter("floorNumber")),
					Integer.parseInt(leftString.substring(0,endLeft)),
					Integer.parseInt(topString.substring(0, endTop)));
							
		newDevice.setLocation(location);
			
		return newDevice;
	}

}
