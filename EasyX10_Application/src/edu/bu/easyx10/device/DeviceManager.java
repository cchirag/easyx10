package edu.bu.easyx10.device;

//import java.util.Iterator;
import java.util.Set;

//import edu.bu.easyx10.device.X10Device.X10DeviceState;
//import edu.bu.easyx10.device.timer.*;
//import java.sql.Time;


/* 
 * DeviceManager is the interface between the GUI and the device class. 
 * Its main function is to simplify interaction and management of 
 * devices for the GUI Package. It does this by providing access to a
 * list of devices and proxy changes from the GUI to the device objects
 * themselves using proxy devices. It also allows or rejects the creation
 * of devices with duplicate names, and regularly saves configuration data
 * out to disk so that it persists between system restarts. 
 */

public final class DeviceManager {

	//member variables
	private Set<Device> mDeviceList;   // List of Devices
	

	public final Set<Device> getDevices(){
		
		//TODO Return a list of proxyDevices instead of the real devices
		//DEFINITELY CANT LEAVE THIS AS IT IS FOR TOO LONG
		return mDeviceList;
		
	}
	
	
	public final Device getDevice(String deviceName){
		
		// TODO replace Temporary stuff that there just to reduce compilation errors
		X10Appliance temp = new X10Appliance("Test", 'a',1);
		return temp;
	}
	
	public final boolean updateDevice(Device aDevice){
		
		return true;
		
	}
	
	public final boolean addDevice(Device proxyDevice){
		return true;
	}
	
	public final boolean deleteDevice(String name){
		return true;
	}
	
	public final boolean saveConfig(){
		
		return true;		
	}
	
	/*
	private final void loadConfig(){
		
	}
	*/
	
}