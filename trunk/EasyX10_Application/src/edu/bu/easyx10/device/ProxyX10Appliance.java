package edu.bu.easyx10.device;

import java.sql.Time;
import edu.bu.easyx10.util.LoggingUtilities;


/**
 * ProxyX10Appliance is derived from and identical to X10Appliance except 
 * that it overrides the X10Appliance constructor such that they don’t 
 * instantiate TriggerTimer OnTime and OffTime objects.  Instead, 
 * ProxyX10Appliancejust sets two Time object attributes with the same 
 * names; OnTime on OffTime. ProxyX10Appliance objects are instantiated
 * in the GUI and are passed off to DeviceManager. DeviceManager then
 * creates X10Appliance Objects by passing a proxy object into the constructor. 
 * ProxyX10Appliance does not contain any of it’s own methods; only overridden 
 * ones.
 * 
 * @author  Damon Gabrielle
 * @version please refer to subversion
 * @date:   11/06/08
 */

public class ProxyX10Appliance extends X10Appliance{
	
	/**
	 * Constructor
	 * 
	 * Construct for a new ProxyX10Applaince class.  The constructor is provided
	 * with a X10MAppliance object which contains all of the desired 
	 * attributes as defined by the higher level GUI.  The constructor must
	 * transfer all the attributes to the local object. The only real difference
	 * between an X10Appliance object and a ProxyX10Appliance is that
	 * Proxy objects do not send or receive events or instantiate timer objects.
	 */

	public ProxyX10Appliance (String name, char houseCode, int deviceCode){
		
		// Create the super X10Device class and pass to it its attribute;
			super(name,houseCode,deviceCode);
		
		
	}
	
	public ProxyX10Appliance ( X10Appliance applianceDevice ) {	
		
		
		//Set the following required attributes
		super(applianceDevice.getName(),
			  applianceDevice.getHouseCode(),
			  applianceDevice.getDeviceCode());
		
		/* 
		 * Load our member variables from the X10ApplianceDevice
		 * If the triggerTimer is enabled proceed with setting 
		 * the onTimer, OffTimer, onTime and OffTime attributes.
		 */
		if(applianceDevice.getTriggerTimerEnabled()){
				
			setTriggerTimerEnabled(true);                 //call to parent Method
			
			setOnTime(applianceDevice.getOnTime());       //call to overridden Method
			
			setOffTime(applianceDevice.getOffTime());     //call to overridden Method
			
		}
		else {
			setTriggerTimerEnabled(false);
		}
		
		// Check to see if the location was instantiated in the X10Appliance
		if(applianceDevice.getLocation()!= null){
			
			//Call base classes setMethod
			setLocation(applianceDevice.getLocation());			
		}
			
		setState ( applianceDevice.getState( ) );
		
		
		
	}

	/**
	 * This method sets the time that an Appliance should be turned on.
	 * 
	 *  @override X10Appliance setOnTime
	 *  @param A Time to turn on the Appliance
	 */
	public void setOnTime(Time onTime) {
		
		mOnTime = onTime;

	}
	
	/**
	 * This method sets the time that an Appliance should be turned off.
	 * 
	 *  @override X10Appliance setOffTime
	 *  @param A Time to turn off the Appliance
	 */
	public void setOffTime(Time offTime) {
		
		mOffTime = offTime;

	}
	
	public synchronized void setState( X10DeviceState state ) {

		//check to see if they're already the same
		if (!getState().toString().equals(state.toString())){
			
			//set the new state of this X10Appliance
			mState = state;
			
	    }
		else{
			 LoggingUtilities.logInfo(ProxyX10Appliance.class.getCanonicalName(),
			 "setState()", "INFO: The state of " + getName() + " was ignored" +
			 "because the current state is already" + getState().toString());
		}	
			
		
	}
	
}
