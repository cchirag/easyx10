package edu.bu.easyx10.device;

import java.util.HashMap;
import edu.bu.easyx10.util.LoggingUtilities;



/* 
 * DeviceManager is the interface between the GUI and the device class. 
 * Its main function is to simplify interaction and management of 
 * devices for the GUI Package. It does this by providing access to a
 * list of devices and proxy's changes from the GUI to the device objects
 * themselves using proxy devices. It also allows or rejects the creation
 * of devices with duplicate names, and regularly saves configuration data
 * out to disk so that it persists between system restarts. 
 */

public final class DeviceManager {

	//member variables
	private HashMap<String, X10Device> mDeviceHashMap = new HashMap();   // List of Devices

	
    //Change to HashMap
	public final HashMap getDevices(){	
		
		//TODO Return a list of proxyDevices instead of the real devices
		//DEFINITELY CANT LEAVE THIS AS IT IS FOR TOO LONG
		return mDeviceHashMap;
		
	}
	
	/**
	 * This method returns a proxyObject representing the original object.
	 * 
	 * @param A String representing the unique device name
	 * @return Either a ProxyX10Appliance or ProxyX10MotionSensor, depending
	 * on the type of the X10Device.
	 * 
	 */
	public final Device getDevice(String deviceName){
		
		/*
		// Make sure it's in the list
		//if (!isUnique(deviceName)){
			
			//Handle X10Appliance Devices
			
			
			
			//if(mDeviceHashMap.get(deviceName) instanceof X10Appliance){
				
				//(X10Appliance)mDeviceHashMap.get(deviceName).	
			
			//}
			// Handle X10MotionSensors
			else if {
				
			}
			// The DeviceType has no match - print to the log
			else {
				
				
				
			}
			
			*/
			
		}
		
		
		//ProxyX10Appliance temp = new X10Appliance("Test", 'a',1);
		//return temp;
	}
	
	/**
	 * This method takes in a proxyDevice of type ProxyX10MotionSensor or 
	 * ProxyX10Appliance and attempts to update the matching original in the
	 * the HashMap.
	 */
	public final boolean updateDevice(Device proxyDevice){
		
		//make sure the HashMap isn't empty and it exists in the Hashmap
		if (!mDeviceHashMap.isEmpty() && !isUnique(proxyDevice.getName())){
			
			//Determine it's Object type
			if(proxyDevice instanceof ProxyX10Appliance){
				
				//pull it out of the HashMap so it can be updated
				X10Appliance x10Appliance = (X10Appliance)mDeviceHashMap.get(proxyDevice.getName());
				
				//attempt an update
				//TODO insert try catch
				x10Appliance.updateDevice(proxyDevice);
				
				//write out the HashMap to disk
				saveConfig();
				
				return true; //TODO I know this return true is useless
				
				
			}else if (proxyDevice instanceof ProxyX10MotionSensor){
				
				//pull it out of the HashMap so it can be updated
				X10MotionSensor x10MotionSensor = (X10MotionSensor)mDeviceHashMap.get(proxyDevice.getName());
				
				//attempt an update
				//TODO insert try catch
				x10MotionSensor.updateDevice(proxyDevice);
				
				//write out the HashMap to disk
				saveConfig();
				
				return true; //TODO I know this return true is useless
				
			}else{
				//This is an unrecognized type of proxy Device
				 LoggingUtilities.logInfo(DeviceManager.class.getCanonicalName(),
				 "updateDevice()","ERROR: The proxy object type was invalid.");
				 return false;
			}
			
		
		}else {
		
			//This is ProxyObjects name is not unique
			 LoggingUtilities.logInfo(DeviceManager.class.getCanonicalName(),
			 "updateDevice()","ERROR: The proxy object "  + proxyDevice.getName() +
			 " did not match an existing device so no update could take place.");
			 
			 return false;
			
		}
		
	}
	
	/**
	 * This method attempts to add a Device to the managed HashMap of devices.
	 * It checks to see that the proxyDevice contains a unique device name. If 
	 * it's unique it will create the device and add it to the hashMap. A successful
	 * add returns true, and an unsuccessful one returns false.
	 * 
	 * @param Takes in either a ProxyX10Appliance or ProxyX10MotionSensor
	 */
	//TODO replace with exception handling and remove type boolean
	public final boolean addDevice(Device proxyDevice){
		
		//make sure it's unique
		if (isUnique(proxyDevice.getName())){
			
			//Determine it's Object type
			if(proxyDevice instanceof ProxyX10Appliance){
				
				//TODO insert try catch
				//Create the real X10Appliance 
				X10Appliance x10Appliance = new X10Appliance((ProxyX10Appliance)proxyDevice);
				
				//Insert it in the Hashmap
				mDeviceHashMap.put(x10Appliance.getName(), x10Appliance);
				
				//write out the Hashmap to disk
				saveConfig();
				
				return true; //TODO I know this return true is useless
				
				
			}else if (proxyDevice instanceof ProxyX10MotionSensor){
				
				//TODO insert try catch
				//Create the real X10MotionSensor 
				X10MotionSensor x10MotionSensor = new X10MotionSensor((ProxyX10MotionSensor)proxyDevice);
				
				//Insert it in the Hashmap
				mDeviceHashMap.put(x10MotionSensor.getName(), x10MotionSensor);
				
				 return true;  //TODO I know this return true is useless 
				
			}else{
				//This is an unrecognized type of proxy Device
				 LoggingUtilities.logInfo(DeviceManager.class.getCanonicalName(),
				 "addDevice()","ERROR: The proxy object type was invalid.");
				 return false;
			}
			
		
		}else {
		
			//This is ProxyObjects name is not unique
			 LoggingUtilities.logInfo(DeviceManager.class.getCanonicalName(),
			 "addDevice()","ERROR: The proxy objects name was not unique.");
			 
			 return false;
			
		}
		
	}
	
	/**
	 * This method attempts to remove a Device to the managed HashMap of devices.
	 * It checks to see that the proxyDevice contains a unique device name. If 
	 * it's unique it will create the device and add it to the hashMap. A successful
	 * add returns true, and an unsuccessful one returns false.
	 * 
	 * @param Takes in either a ProxyX10Appliance or ProxyX10MotionSensor
	 */
	public final boolean deleteDevice(String deviceName){
		
		if(!isUnique(deviceName)){
			
			//The device exists in the list. Delete it!
			
			mDeviceHashMap.remove(deviceName);
			
			return true;
			
		}
		else{
			//This Device name does not exist in the HashMap
			 LoggingUtilities.logInfo(DeviceManager.class.getCanonicalName(),
			 "addDelete()","ERROR: The proxy object " + deviceName + 
			 " does not exist so it could not be deleted.");
			
			return false;
		}

	}
	
	/**
	 * This method checks to see if a device already exists in the HashMap.
	 * A devices name is used as the key for the HashMap
	 * 
	 * @param A proxyX10Appliance or ProxyX10MotionSensor
	 * @return True or false
	 */
	public final boolean isUnique(String name){
		
		if(mDeviceHashMap.containsKey(name)){
		
			return true;
		}
		else {
			return false;
		}
		
		
	}

	/**
	 * This method is used to write out the contents of mDeviceHashMap
	 * to disk so that data persists between system restarts and to protect
	 * against data loss during power outages.
	 * 
	 * This method is synchronized in the rare chance that calling this method
	 * from run() coincides with a call made from within updateDevice, AddDevice
	 * 
	 * @return True if save is successful and false if unsuccessful.
	 */
	public final synchronized boolean saveConfig(){
		
		return true;		
	}

	/*
	@Override
	
	public void run() {
	
		int forever = 0;
		while(forever == 0){		
			try {
				
				//Save out the existing config to disk
				saveConfig();

				//Sleep for 5 minutes
				Thread.sleep(300000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();	
			}
			
		
		}
		
	}*/
	
	
	
	
	/*
	private final void loadConfig(){
		
	}
	*/
	
}