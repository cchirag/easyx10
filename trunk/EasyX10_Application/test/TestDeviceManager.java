
import java.util.Calendar;

import edu.bu.easyx10.device.*;
//import edu.bu.easyx10.event.*;
//import edu.bu.easyx10.protocol.*;

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
		ProxyX10Appliance proxyApp = new ProxyX10Appliance("app1", 'A', 1);
		proxyApp.setOnTime(onTime);
		proxyApp.setOffTime(offTime);
		proxyApp.setTriggerTimerEnabled(false);
		
		System.out.println("The proxyApp TriggerTimer is " + proxyApp.getTriggerTimerEnabled());
		
		//Test AddDevice
		  dm.addDevice(proxyApp);
		
		//add again to make sure it doesn't allow it  
		  dm.addDevice(proxyApp);
		  
		//now delete it
		  dm.deleteDevice(proxyApp.getName());
		  
		//attempt to delete twice  
		  dm.deleteDevice(proxyApp.getName());
		  
		//now add it back in again
		  dm.addDevice(proxyApp);

		//Test update device;
		
		
	}
	
	
}
