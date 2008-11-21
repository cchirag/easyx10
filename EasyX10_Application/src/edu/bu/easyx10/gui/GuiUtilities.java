package edu.bu.easyx10.gui;

import java.util.ArrayList;
import java.util.Set;

import javax.servlet.http.HttpSession;

import edu.bu.easyx10.device.Device;
import edu.bu.easyx10.device.DeviceManagerFactory;
import edu.bu.easyx10.device.ProxyX10Appliance;
import edu.bu.easyx10.device.ProxyX10MotionSensor;

/**
 * 
 *
 * @author winderjj
 * @version Please Refer to Subversion
 * @date Nov 20, 2008
 */
public class GuiUtilities {
	
	public static void updateSessionDeviceList(HttpSession theSession){
		
		// Retrieve the devices from the Manager
		// TODO Resolve Return Type
		//Set<Device> devices = DeviceManagerFactory.getDeviceManager().getDevices();
		
		ArrayList<Device> deviceList = new ArrayList<Device>();
		deviceList.add(new ProxyX10Appliance("Light1", 'A', 1));
		deviceList.add(new ProxyX10MotionSensor("Sensor1", 'B', 1));
		deviceList.add(new ProxyX10Appliance("Light2", 'A', 2));
		deviceList.add(new ProxyX10MotionSensor("Sensor2", 'B', 3));
		deviceList.add(new ProxyX10Appliance("Light3", 'A', 3));
		
		// Store the Device List in the session
		theSession.setAttribute("deviceList", deviceList);
	}

}
