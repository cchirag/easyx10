package edu.bu.easyx10.device;

import edu.bu.easyx10.event.TimerEvent;
import edu.bu.easyx10.util.LoggingUtilities;
import java.util.Calendar;

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
		
		// Create the super X10Device class and pass it to its attribute;
			super(name,houseCode,deviceCode);
			
			// unregister the Device with the EventGenerator
		    eventGenerator.deleteEventListener(this);
		
		
	}
	
	public ProxyX10Appliance ( X10Appliance applianceDevice ) {	
		
		
		//Set the following required attributes
		super(applianceDevice.getName(),
			  applianceDevice.getHouseCode(),
			  applianceDevice.getDeviceCode());
		
		
		// unregister the Device with the EventGenerator
	    eventGenerator.deleteEventListener(this);
		
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

		setLocation(applianceDevice.getLocation());			
			
		setState ( applianceDevice.getState( ) );
		
		
		
	}

	/**
	 * This method sets the time that an Appliance should be turned on.
	 * 
	 *  @override X10Appliance setOnTime
	 *  @param A Time to turn on the Appliance
	 */
	public void setOnTime(Calendar onTime) {
		
		mOnTime = onTime;

	}
	
	/**
	 * This method sets the time that an Appliance should be turned off.
	 * 
	 *  @override X10Appliance setOffTime
	 *  @param A Time to turn off the Appliance
	 */
	public void setOffTime(Calendar offTime) {
		
		mOffTime = offTime;

	}
	
	/**
	 * This method overrides setState in X10Appliance. It's used to represent
	 * the state of the X10Appliance and can either be set with X10DeviceState.ON 
	 * or X10DeviceState.OFF.
	 *  
	 *  @param X10DeviceState state - new state of ON (Appliance turn on) or OFF
	 */
	public synchronized void setState( X10DeviceState state ) {

		mState = state;
	}
	
	/**
	 * This method returns null. This method is completely useless but it's
	 * been overridden to prevent returning a real X10Appliance object to
	 * the caller.
	 * 
	 * @override X10Appliance getProxyDevice()
	 * @return ProxyX10Appliance object
	 */
	public Device getProxyDevice(){ 
		
		return null;
		
	}
	
	/**
	 * This method takes in a ProxyX10Appliance object and updates this object
	 * such that all the attributes match. Honestly, I don't know what you'd use 
	 * this method for but since we need to be able to update a real device with a proxy 
	 * device, I'm overriding it here so that an accidental call on a proxy devices
	 * updateDevice method doesn't return the caller a real X10ApplianceDevice.
	 * 
	 * @override X10Appliance updateDevice()
	 * @param ProxyX10Appliance
	 */
	public void updateDevice(Device proxyDevice) {
		
		/*
		 * We need to crash if we get an update for a device which
		 * is not a ProxyX10Appliance.  This means we found a bug.
		 */
		if (!(proxyDevice instanceof ProxyX10Appliance) ) {
			assert(false);
		
		}
		
		/*
		 * We need to crash if we get an update for a device with a 
		 * different name.  This means we found a bug.
		 */
		if ( proxyDevice.getName( ) != getName( ) ) {
			assert(false);
		}
		
		// Update the attributes from the Proxy Object
		setHouseCode ( ((ProxyX10Appliance)proxyDevice).getHouseCode( ) );
		setDeviceCode ( ((ProxyX10Appliance)proxyDevice).getDeviceCode( ) );
	
		
		/*
		 * If the triggerTimer is enabled proceed with setting
		 * the onTimer, OffTimer, onTime and OffTime attributes. 
		 */
		if(((ProxyX10Appliance)proxyDevice).getTriggerTimerEnabled()){
			setTriggerTimerEnabled(true);
			setOnTime(((ProxyX10Appliance)proxyDevice).getOnTime());  //Call overridden setOnTime
			setOffTime(((ProxyX10Appliance)proxyDevice).getOffTime());//Call overridden setOffTime
		}
		else {
				
			setTriggerTimerEnabled(false);
	
		}
		
		/*
		 * Store the proxyDevice state and this objects current state in 
		 * instance variables to make the if statement easier to read.
		 */
		 String proxyDeviceState = ((ProxyX10Appliance)proxyDevice).getState().toString();
		 String currentState = getState().toString();
		
		/*
		 * Prevent Extra events from being fired by determining if the requested 
		 * state change already equals the current state of this object
		 */
		 if (!proxyDeviceState.equals(currentState)){
			setState ( ((ProxyX10Appliance)proxyDevice).getState() );
		 }
		 else{
			 LoggingUtilities.logInfo(X10Appliance.class.getCanonicalName(),
			 "processDeviceEvent()","INFO: Skipping state change to " + 
			 proxyDeviceState + " on device " + getName() );
		 }
	}
	/*
	 * 
	 */
	public void processDeviceEvent(TimerEvent e) {}
	public void processTimerEvent(TimerEvent e) {}
	
	
}
