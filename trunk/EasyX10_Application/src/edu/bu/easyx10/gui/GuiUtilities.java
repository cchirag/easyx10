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
	}

}
