package edu.bu.easyx10.gui;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import edu.bu.easyx10.device.Device;
import edu.bu.easyx10.device.DeviceLocation;
import edu.bu.easyx10.device.ProxyX10Appliance;
import edu.bu.easyx10.device.ProxyX10MotionSensor;
import edu.bu.easyx10.device.X10Device.X10DeviceState;
import edu.bu.easyx10.util.LoggingUtilities;

/**
 * 
 *
 * @author winderjj
 * @version Please Refer to Subversion
 * @date Nov 20, 2008
 */
public class GuiUtilities {
	
	// TODO Remove when real DeviceList is accessed
	private static ArrayList<Device> tempDeviceList = new ArrayList<Device>();
	
	/**
	 * @param theSession
	 */
	public static void updateSessionDeviceList(HttpSession theSession){
		
		// Retrieve the devices from the Manager
		// TODO Resolve Return Type
		//Set<Device> devices = DeviceManagerFactory.getDeviceManager().getDevices();
		
		ArrayList<Device> deviceList = new ArrayList<Device>();
		ProxyX10Appliance l1 = new ProxyX10Appliance("Light1", 'A', 1);
		l1.setLocation(new DeviceLocation(1, 100, 150));
		l1.setState(X10DeviceState.ON);
		deviceList.add(l1);
		
		ProxyX10MotionSensor m1 = new ProxyX10MotionSensor("Motion1", 'B', 1);
		m1.setLocation(new DeviceLocation(2, 200, 250));
		m1.setState(X10DeviceState.ON);
		deviceList.add(m1);
		
		ProxyX10Appliance l2 = new ProxyX10Appliance("HAHAHAH", 'A', 2);
		l2.setLocation(new DeviceLocation(3, 400, 350));
		l2.setState(X10DeviceState.OFF);
		deviceList.add(l2);
		
		ProxyX10MotionSensor m2 = new ProxyX10MotionSensor("Motion2", 'B', 2);
		m2.setLocation(new DeviceLocation(1, 20, 20));
		m2.setState(X10DeviceState.ON);
		deviceList.add(m2);
		
		ProxyX10Appliance l3 = new ProxyX10Appliance("Light3", 'A', 3);
		l3.setLocation(new DeviceLocation(1, 340, 50));
		deviceList.add(l3);
		l3.setState(X10DeviceState.OFF);
		
		LoggingUtilities.logInfo("GuiUtilities", "updateSessionDeviceList", "Device List Size = " + deviceList.size());
		// Store the Device List in the session
		theSession.setAttribute("deviceList", deviceList);
		
		// TODO Remove
		tempDeviceList = deviceList;
	}
	
	/**
	 * @return the tempDeviceList
	 */
	public static ArrayList<Device> getTempDeviceList() {
		return tempDeviceList;
	}

	/**
	 * @param tempDeviceList the tempDeviceList to set
	 */
	public static void setTempDeviceList(ArrayList<Device> tempDeviceList) {
		GuiUtilities.tempDeviceList = tempDeviceList;
	}

	/**
	 * @return
	 */
	public static String generateHtmlTimeOptions(){
		String timeOptionsHtml = "";
		
		// Define the list of times the user can select
		String[] times = {
				"12:00am","12:30am",
				"01:00am","01:30am",
				"02:00am","02:30am",
				"03:00am","03:30am",
				"04:00am","04:30am",
				"05:00am","05:30am",
				"06:00am","06:30am",
				"07:00am","07:30am",
				"08:00am","08:30am",
				"09:00am","09:30am",
				"10:00am","10:30am",
				"11:00am","11:30am",
				"12:00pm","12:30pm",
				"01:00pm","01:30pm",
				"02:00pm","02:30pm",
				"03:00pm","03:30pm",
				"04:00pm","04:30pm",
				"05:00pm","05:30pm",
				"06:00pm","06:30pm",
				"07:00pm","07:30pm",
				"08:00pm","08:30pm",
				"09:00pm","09:30pm",
				"10:00pm","10:30pm",
				"11:00pm","11:30pm",
		};
		
		// Generate the html for the time options
		for( int i=0; i< times.length; i++){
			timeOptionsHtml += "<option value=\"" +
				times[i] + "\">" + times[i] + "</option>";
		}
		
		return timeOptionsHtml;
	}

}
