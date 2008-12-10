 package edu.bu.easyx10.gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.bu.easyx10.device.Device;
import edu.bu.easyx10.device.DeviceLocation;
import edu.bu.easyx10.device.DeviceManagerFactory;
import edu.bu.easyx10.device.ProxyX10Appliance;
import edu.bu.easyx10.device.ProxyX10MotionSensor;
import edu.bu.easyx10.device.X10Device;
import edu.bu.easyx10.util.LoggingUtilities;

/**
 * This class implements serveral static help methods for the EasyX10 user 
 * interface.  This includes methods to generate html code and create devices
 * based on user input.
 *
 * @author winderjj
 * @version Please Refer to Subversion
 * @date Nov 20, 2008
 */
public class GuiUtilities {
	
	/**
	 * This method updates the list of devices that is stored in the
	 * session bean.  Devices are first retrieved from the DeviceManager
	 * and then stored in the session bean.
	 * 
	 * @param theSession the current session
	 */
	public static void updateSessionDeviceList(HttpSession theSession){
		
		// Retrieve the devices from the Manager
		List<X10Device> devices = 
			DeviceManagerFactory.getDeviceManager().getDevices();
		
		// Store the Device List in the session
		theSession.setAttribute("deviceList", devices);
		
		LoggingUtilities.logInfo(GuiUtilities.class.getCanonicalName(), 
				"updateSessionDeviceList", 
				"Session Device List Updated");
	}
	
	/**
	 * This method retrieves a device from the DeviceManger.  It is provided 
	 * as a helper method to the jsp pages.
	 * 
	 * @param name the device to retrieve
	 * @return the device form the manager or null if no device with that name
	 * exists.
	 */
	public static Device getDevice(String name){
		return DeviceManagerFactory.getDeviceManager().getDevice(name);
	}

	
	/**
	 * Generates the appliance option html for the  
	 * Appliance Select box.
	 *
	 * @return html for appliance options
	 */
	public static String generateHtmlApplianceOptions() {
		String optionsHtml = "";
		
		// Retrieve the list of devices from the Manager
		List<X10Device> devices = 
			DeviceManagerFactory.getDeviceManager().getDevices();
		
		// Iterate through each device and create an option for each appliance
		for( Device device : devices ){
			if( device instanceof ProxyX10Appliance ){
				optionsHtml += "<option value=\"" +
				device.getName() + "\">" + device.getName() + "</option>";
			}
		}
		return optionsHtml;
	}
	
	/**
	 * Generates the html for the time options used
	 * by a select box.
	 * 
	 * @param selectedValue the value to be selected
	 * @return html for time options.
	 */
	public static String generateHtmlTimeOptions(String fieldPrefix, boolean enabled, String hour, 
			String minutes, String amOrPm){
		String timeOptionsHtml = "";
		
		// Create dropdown for Hours
		timeOptionsHtml += "<select name=\"" + fieldPrefix + 
						"Hour\" " + (enabled ? "" : "disabled=\"disabled\"") + " tabindex=6\">\n";
		for(int i=1; i<=12; i++){
			String hr;
			if (i<10){
				hr = "0" + i;
			} else {
				hr = String.valueOf(i);
			}
			
			timeOptionsHtml += "<option value=\"" +
			hr + "\" " +
			(hour.equalsIgnoreCase(hr) ? "selected=\"selected\"" : "") + 
			">" + hr + "</option>\n";
		}
		timeOptionsHtml += "</select>:";
		
		// Create dropdown for Minutes
		timeOptionsHtml += "<select name=\"" + fieldPrefix + 
						"Minute\" " + (enabled ? "" : "disabled=\"disabled\"") + " tabindex=7\">\n";
		for(int i=0; i<=59; i++){
			String min;
			if (i<10){
				min = "0" + i;
			} else {
				min = String.valueOf(i);
			}
			
			timeOptionsHtml += "<option value=\"" +
			min + "\" " +
			(minutes.equalsIgnoreCase(min) ? "selected=\"selected\"" : "") + 
			">" + min + "</option>\n";
		}
		timeOptionsHtml += "</select>";
		
		// Create dropdown for am and pm
		timeOptionsHtml += "<select name=\"" + fieldPrefix + 
							"AmOrPm\" " + (enabled ? "" : "disabled=\"disabled\"") + " tabindex=8\">\n";
		timeOptionsHtml += "<option value=\"am\" " +
			(amOrPm.equalsIgnoreCase("am") ? "selected=\"selected\"" : "") + 
			">am</option>\n";
		timeOptionsHtml += "<option value=\"pm\" " +
		(amOrPm.equalsIgnoreCase("pm") ? "selected=\"selected\"" : "") + 
		">pm</option>\n";
		timeOptionsHtml += "</select>";
		
		return timeOptionsHtml;
	}
	
	/**
	 * Creates a new appliance object using the data provided
	 * in the request object.
	 * 
	 * @param request Contains form data from AddAppliance.jsp
	 * @return the new proxy appliance.
	 */
	public static ProxyX10Appliance createNewAppliance(HttpServletRequest request){
		
		// Retrieve the name/house code/unit code
		String name = request.getParameter("deviceName");
		char houseCode = request.getParameter("houseCode").charAt(0);
		int unitCode = Integer.parseInt(request.getParameter("unitCode"));
		
		// Create the new Appliance
		ProxyX10Appliance newDevice = new ProxyX10Appliance(name, houseCode, unitCode);
		
		// Set the State
		newDevice.setState(
				X10Device.X10DeviceState.valueOf(request.getParameter("deviceStatus")));
		
		// Set timer details
		newDevice.setTriggerTimerEnabled(request.getParameter("timer").equals("ON"));
			
		// Retrieve the time strings
		String startHour = request.getParameter("startHour");
		String startMinute = request.getParameter("startMinute");
		String startAmOrPm = request.getParameter("startAmOrPm");
		String endHour = request.getParameter("endHour");
		String endMinute = request.getParameter("endMinute");
		String endAmOrPm = request.getParameter("endAmOrPm");
		
		// Set the On and Off Time
		newDevice.setOnTime(convertTimeString(startHour + ":" + 
							startMinute + startAmOrPm));
		newDevice.setOffTime(convertTimeString(endHour + ":" + 
				endMinute + endAmOrPm));
			
		// Set the Location
		String leftString = request.getParameter("left");
		int endLeft = leftString.indexOf("px");
		String topString = request.getParameter("top");
		int endTop = topString.indexOf("px");
		
		// TODO Fix Location object to have floor name
		int floorNumber = Integer.parseInt(request.getParameter("floorNumber").substring(5));
		DeviceLocation location = 
			new DeviceLocation(floorNumber,
					Integer.parseInt(leftString.substring(0,endLeft)),
					Integer.parseInt(topString.substring(0, endTop)));
							
		newDevice.setLocation(location);
			
		return newDevice;
	}
	
	/**
	 * Creates a new motion sensor object using the data provided
	 * in the request object.
	 * 
	 * @param request Contains form data from AddMotionSensor.jsp
	 * @return the new proxy motion sensor.
	 */
	public static ProxyX10MotionSensor createNewMotionSensor(HttpServletRequest request){
		
		// Retrieve the name/house code/unit code
		String name = request.getParameter("deviceName");
		char houseCode = request.getParameter("houseCode").charAt(0);
		int unitCode = Integer.parseInt(request.getParameter("unitCode"));
		
		// Create the new Appliance
		ProxyX10MotionSensor newDevice = new ProxyX10MotionSensor(name, houseCode, unitCode);
		
		// Set the detection period
		String activityWindow = request.getParameter("activityWindow");
		newDevice.setDetectionPeriodEnabled(Boolean.parseBoolean(activityWindow));

		// Retrieve the time strings
		String startHour = request.getParameter("startHour");
		String startMinute = request.getParameter("startMinute");
		String startAmOrPm = request.getParameter("startAmOrPm");
		String endHour = request.getParameter("endHour");
		String endMinute = request.getParameter("endMinute");
		String endAmOrPm = request.getParameter("endAmOrPm");
		
		// Set the On and Off Time
		newDevice.setStartTime(convertTimeString(startHour + ":" + 
							startMinute + startAmOrPm));
		newDevice.setEndTime(convertTimeString(endHour + ":" + 
				endMinute + endAmOrPm));
		
		// Set the Inactivity Timeout
		String activityTimeout = request.getParameter("activityTimeout");
		if( activityTimeout != null ){
			newDevice.setInactivityTimeEnabled(Boolean.parseBoolean(activityTimeout));
		}
		
		String timeoutPeriod = request.getParameter("activityTimeoutPeriod");
		if( timeoutPeriod != null ){
			newDevice.setInactivityTime(Integer.parseInt(timeoutPeriod));
		}
		
		// Set the appliance list
		String listField = request.getParameter("associatedAppliances");
		String [] applianceArray = listField.split(",");
		HashSet<String> applianceSet = new HashSet<String>();
		for(int i=0; i< applianceArray.length; i++){
			applianceSet.add(applianceArray[i]);
		}
		newDevice.setApplianceList(applianceSet);
		
		// Set the Location
		String leftString = request.getParameter("left");
		int endLeft = leftString.indexOf("px");
		String topString = request.getParameter("top");
		int endTop = topString.indexOf("px");
		int floorNumber = Integer.parseInt(request.getParameter("floorNumber").substring(5));
		DeviceLocation location = 
			new DeviceLocation(floorNumber,
					Integer.parseInt(leftString.substring(0,endLeft)),
					Integer.parseInt(topString.substring(0, endTop)));
							
		newDevice.setLocation(location);
			
		return newDevice;
	}
	
	/**
	 * Converts a time string to a Calendar object.
	 * 
	 * @param requestTime the time to convert.
	 * @return the corresponding Calendar object.
	 */
	public static Calendar convertTimeString(String requestTime){
		// Create a date format and calendar instance
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mma");
		Calendar theTime = Calendar.getInstance();
		
		// Set the time in the calendar based on input string
		try{
			theTime.setTime(dateFormat.parse(requestTime));
		} catch( ParseException  pe){
			pe.printStackTrace();
		}
		return theTime;
	}
	
	/**
	 * Converts a Calendar object into a array of strings.
	 * 
	 * @param theTime the Calendar object to convert.
	 * @return a time string array.
	 */
	public static String[] convertCalendarToString(Calendar theTime){
		String[] dateArray = new String[3];
		
		if(theTime == null){
			dateArray[0] = "12";
			dateArray[1] = "00";
			dateArray[2] = "am";
			return dateArray;
		}
		
		// Create a date format
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mma");
		
		// Convert the calendar to a string array
		String dateString = dateFormat.format(theTime.getTime());
		dateArray[0] = dateString.substring(0, 2);
		dateArray[1] = dateString.substring(3,5);
		dateArray[2] = dateString.substring(5,7);

		return dateArray;
	}

}
