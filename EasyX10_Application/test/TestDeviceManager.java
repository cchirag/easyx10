
import java.util.ArrayList;
import java.util.Calendar;

import edu.bu.easyx10.device.*;

/**
 * The purpose of the TestDeviceManager class is to test Device manager methods.
 * It creates a proxyX10Appliance and runs through all the available methods
 * in DeviceManager.
 * 
 * @author dgabriel
 * @version please refer to subversion
 * @date:   11/29/08
 */

public class TestDeviceManager {

	
	public static void main(String[] args) {
		
		
		DeviceManager dm = DeviceManagerFactory.getDeviceManager();
		//EventGenerator eg = EventGeneratorFactory.getEventGenerator();	
	
    	//Create an onTime
		Calendar onTime = Calendar.getInstance();
		onTime.set(Calendar.HOUR_OF_DAY, 9);
		onTime.set(Calendar.MINUTE, 59);
		onTime.set(Calendar.SECOND, 0);
		onTime.set(Calendar.MILLISECOND, 0);

		//Create an offTime
		Calendar offTime = Calendar.getInstance();
		offTime.set(Calendar.HOUR_OF_DAY, 10);
		offTime.set(Calendar.MINUTE, 00);
		offTime.set(Calendar.SECOND, 30);
		offTime.set(Calendar.MILLISECOND, 0);
	    
		//Test creation of ProxyX10Appliance
		ProxyX10Appliance proxyApp = new ProxyX10Appliance("APP3", 'B', 1);
		proxyApp.setOnTime(onTime);
		proxyApp.setOffTime(offTime);
		proxyApp.setTriggerTimerEnabled(false);
		
		System.out.println("The proxyApp TriggerTimer is " + proxyApp.getTriggerTimerEnabled());
		
		//Test AddDevice
		  System.out.println("Next Line should be INFO");
		  dm.addDevice(proxyApp);
		
		//add again to make sure it doesn't allow it  
		  System.out.println("Next Line should be SEVERE");
		  dm.addDevice(proxyApp);
		  
		//now delete it
		  System.out.println("Next Line should be INFO");
		  dm.deleteDevice(proxyApp.getName());
		  
		//attempt to delete twice  
		  System.out.println("Next Line should be SEVERE");
		  dm.deleteDevice(proxyApp.getName());
		  
		//now add it back in again
		  System.out.println("Next Line should be INFO");
		  dm.addDevice(proxyApp);
		  
		//Create another appliance
		  
		  ProxyX10Appliance proxyApp2 = new ProxyX10Appliance("APP4", 'B', 2);
		  proxyApp2.setOnTime(onTime);
		  proxyApp2.setOffTime(offTime);
		  proxyApp2.setTriggerTimerEnabled(false);
		  
		//add it
		  System.out.println("Next Line should be INFO");
		  dm.addDevice(proxyApp2);
		  
		//Add an X10Motion Sensor
		    ProxyX10MotionSensor motionSensorProxy = new ProxyX10MotionSensor ("MOTION B16", 'B', 16);
			motionSensorProxy.addAppliance ("Appliance C1");
			motionSensorProxy.setDetectionPeriodEnabled(false);
			Calendar startTime = Calendar.getInstance( );
			startTime.set(Calendar.HOUR_OF_DAY,18);
			startTime.set(Calendar.MINUTE,0);
			motionSensorProxy.setStartTime(startTime);
			Calendar endTime = Calendar.getInstance( );
			endTime.set(Calendar.HOUR_OF_DAY,19);
			endTime.set(Calendar.MINUTE,0);
			motionSensorProxy.setEndTime(endTime);
			motionSensorProxy.setInactivityTimeEnabled(true);
			motionSensorProxy.setInactivityTime (10);
			dm.addDevice(motionSensorProxy);


		//Test getDevice
		  System.out.println("Next Line should be INFO");
		  ProxyX10Appliance proxyApp1 = (ProxyX10Appliance)dm.getDevice("app1");
		  System.out.println("The proxyApp1's name is " + proxyApp1.getName());
  
	
		//Test getDevices
		  ArrayList<X10Device> deviceList = new ArrayList<X10Device>();
		  System.out.println("Next Line should be INFO");
		  deviceList = dm.getDevices();
		  

		  System.out.println("Here are the contents of the Arraylist");		  
          int count = 1;
		  for (X10Device device : deviceList) {
			  System.out.println("Item " + count + " is named " + device.getName());
			  count++;
		  }
		
	}
	
	
}
