package edu.bu.easyx10.device;

import edu.bu.easyx10.event.*;
import edu.bu.easyx10.event.X10Event.*;
import edu.bu.easyx10.util.LoggingUtilities;
import edu.bu.easyx10.device.timer.*;
import java.util.Calendar;
import java.util.Timer;


/** The X10Appliance class is derived from the abstract class X10DeviceClass. 
 * It's purpose is to model the state and behavior of X10 appliance modules.
 * The state of X10 devices are controlled either by device updates coming
 * down from the Device manager from the GUI package, or from incoming
 * DeviceEvents published by EventGenerator. X10Applianc objects also implement
 * TriggerTimers to turn lights on and off during a particular time period. It's 
 * important to note that incoming events are processed regardless of whatever
 * Period is set in a TriggeTimer. 
*/

public class X10Appliance extends X10Device{
	
	//Declare Private Member Variables
	private TriggerTimer mOnTimer;            // Timer used to trigger On Event
	private TriggerTimer mOffTimer;           // Timer used to trigger Off Event
	protected Calendar mOnTime = Calendar.getInstance();// Time to turn appliance on
	protected Calendar mOffTime = Calendar.getInstance();// Time to shut appliance off
	private boolean mTriggerTimerEnabled;     // Check if TriggerTimer is Enabled 
	private TimerEvent mOnEvent;              // The  ON event riggerTimer will fire
	private TimerEvent mOffEvent;             // The  OFF event riggerTimer will fire

	/**
	 * The default X10Appliance constructor. This takes in a device name
	 * followed by a houseCode and deviceCode. Acceptable housecode values
	 * are char's from A to P and deviceCodes are int's between 1 and 16.
	 * 
	 * It's important to note that the Devices themselves do not prevent the 
	 * creation of duplicates during instantiation or modification. 
	 * While Device names are the unique identifier, it's up to the DeviceManager 
	 * to enforce that no two devices are created with the same name.
	 * 
	 */
	
	/*
	 * TODO I think we need to throw exceptions in the X10Appliance setters
	 * The way ProxyDevice works right now there's no enforcement in the constructor
	 * That ensures that Appliances created from a proxyDevice have all the 
	 * correct fields set. That seems ok as long as the setter methods can detect
	 * this and throw an exception thereby preventing invalid instantiations.
	 */

	
	
	public X10Appliance(ProxyX10Appliance proxyX10Appliance){

		// Call super in X10Device and pass in the required attributes
		super(proxyX10Appliance.getName(),
			  proxyX10Appliance.getHouseCode(),
			  proxyX10Appliance.getDeviceCode());
		
		// Set the member variables from the X10Appliance
		
			// Check to see if the location was instantiated in the proxyX10Appliance
			if(proxyX10Appliance.getLocation()!= null){
				
				//Call base classes setMethod
				setLocation(proxyX10Appliance.getLocation());			
			}
				
			
			/* If the triggerTimer is enabled proceed with setting onTime 
			 *  offTime. setOnTimer and setOffTimer get called within
			 *  setOnTime and setOffTime methods so there is no need to 
			 *  set them here. 
			 */
			if(proxyX10Appliance.getTriggerTimerEnabled()){
				
				setTriggerTimerEnabled(true);                   //call to parent Method
				
				setOnTime(proxyX10Appliance.getOnTime());       //call to overridden Method
				
				setOffTime(proxyX10Appliance.getOffTime());     //call to overridden Method
				
				//Create the onEvent to be passed to the TriggerTimer
				mOnEvent = new TimerEvent ( getName(), "ON" );
				
				//Create the onEvent to be passed to the TriggerTimer
				mOffEvent = new TimerEvent ( getName(), "OFF" );
				
			}
			else {
				setTriggerTimerEnabled(false);
			}
				
			setState ( proxyX10Appliance.getState( ) );
		
		
		
	}
	
	//This constructor is required by ProxyX10Appliance
	public X10Appliance(String name, char houseCode, int deviceCode){
		
		// Call super in X10Device and pass in the required attributes
		super(name,houseCode,deviceCode);
		
		
	}
	

	/**
	 * This method instantiates a TriggerTimer object containing the time
	 * to turn this appliance on. When the system time reaches the time specified
	 * an event will be fired to turn the appliance on.
	 * 
	 * @param Pass in a Time object equal to the time to turn the Appliance on.
	 */
	private void setOnTimer(Calendar anOnTime){

		//If the mOnTimer is null setOnTimer is being called by the constructor
		//So you must instantiate the TriggerTimer
		
		if(mOnTimer == null){
		
			//instantiate the member TriggerTimer mOnTimer
			mOnTimer = new TriggerTimer (mOnEvent,anOnTime);
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new TriggerTimer(mOnEvent,anOnTime),anOnTime.getTime(), 86400000);
			mOnTimer.startTimer();
		}
		else{  
			
			//If we're in here the TriggerTimer has already been instantiated
			//so just set the TriggerTimer to it's new value
			
			mOnTimer.setTriggerTime(anOnTime);
			mOnTimer.startTimer();
		}
		

		//Now start / restart the timer. If it's already started it will restart.
		
		  //mOnTimer.startTimer();
		
	}
	
	/**
	 * This method instantiates a TriggerTimer object containing the time
	 * to turn this appliance off. When the system time reaches the time specified
	 * an event will be fired to turn the appliance on.
	 * 
	 * @param Pass in a Time object equal to the time to turn the Appliance on.
	 */
	private void setOffTimer(Calendar anOffTime){
		
		//If the mOffTimer is null setOffTimer is being called by the constructor
		//So you must instantiate the TriggerTimer
		if(mOffTimer == null){
			
		//instantiate the member TriggerTimer mOffTimer
		
			mOffTimer = new TriggerTimer (mOffEvent,anOffTime);
		}
		else{  
			
			//If we're in here the TriggerTimer has already been instantiated
			//so just set the TriggerTimer to it's new value
			
			mOffTimer.setTriggerTime(anOffTime);		
		}
		
		//Now start / restart the timer. If it's already started it will restart.
		mOffTimer.startTimer();
		
	}
	
	/**
	 * @return Returns a Time object representing the time an event will be 
	 * fired to turn on the Appliance
	 */
	public Calendar getOnTime() {
		return mOnTime;
	}

	/**
	 * This method sets the time that an Appliance should be turned on.
	 * It then internally calls setOnTimer to instantiate the actual timer
	 * that will fire an X10Event to turn the device on.
	 * 
	 *  @param A Time to turn on the Appliance
	 */
	public void setOnTime(Calendar onTime) {
		
		mOnTime= onTime;
		
		//now hand off the onTime to the onTimer;
		setOnTimer(mOnTime);
	}
	
	/**
	 * This method returns a Time object representing the time an off Event
	 * will be sent to turn the appliance off.
	 * @return
	 */
	public Calendar getOffTime() {
		return mOffTime;
	}
	
	/**
	 * This method sets the time that an Appliance should be turned off.
	 * It then internally calls setOffTimer to instantiate the actual timer
	 * that will fire an X10Event to turn the device off.
	 * 
	 *  @param A time to turn off the Appliance
	 */
	
	public void setOffTime(Calendar offTime) {
		
		mOffTime = offTime;
		
		//now hand off the offTime to pass to the offTimer
		setOffTimer(mOffTime);
	}

	public boolean getTriggerTimerEnabled() {
		return mTriggerTimerEnabled;
	}

	public void setTriggerTimerEnabled(boolean triggerTimerEnabled) {
		mTriggerTimerEnabled = triggerTimerEnabled;
	}

	/**
	 * This method returns the current state as an X10DeviceState
	 * enumeration.  It has values ON and OFF.
	 * 
	 * @return X10DeviceState ( ON, OFF )
	 */
	public X10DeviceState getState() {

		return mState;
	}

	/**
	 * This method is used to change the state of the X10Appliance.  The ON state 
	 * is used to indicate that the appliance is on and OFF state is used to 
	 * indicate that the appliance is off. When the Appliance state changes to ON
	 * an event is fired. If the device is currently on, we do not fire an event
	 * as to avoid sending unnecessary events to protocol.
	 * @param X10DeviceState state - new state of ON (Appliance turn on) or OFF
	 */
	public synchronized void setState( X10DeviceState state ) {

		//check to see if they're already the same
		if (getState() != state ){
			
			//set the new state of this X10Appliance
			mState = state;
			
			/*
			 * TODO I'm concerned about instantiating this event object here
			 * I worry we're introducing a memory leak.
			 */

			// Create an X10Protocol Event to turn on the Appliance
			X10ProtocolEvent protocolEvent = new X10ProtocolEvent ( 
										         getName(),
										         getHouseCode(),
										         getDeviceCode(),
										         getState().toString());
			
			/* 
			 * Send the Event out to EventGenerator, so that protocol picks
			 * it up and send it out to the actual appliance.
			 */
			eventGenerator.fireEvent ( protocolEvent );
			
	    }
		else{
			 LoggingUtilities.logInfo(X10Appliance.class.getCanonicalName(),
			 "setState()","The state of " + getName() + " was ignored " +
			 "because the current state is already " + getState().toString());
		}	
			
		
	}

	/**
	 * This method returns a new ProxyX10Appliance object
	 * using the attributes from the local object.
	 * 
	 * @return ProxyX10Appliance object
	 */
	public Device getProxyDevice() {

		return new ProxyX10Appliance(this);
		
	}

	/**
	 * This method receives a ProxyX10Appliance object.  This 
	 * method updates the local object attributes with the values
	 * from the Proxy object.
	 * 
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
		
		//If the triggerTimer is enabled proceed with setting 
		// the onTimer, OffTimer, onTime and OffTime attributes.
		if(((ProxyX10Appliance)proxyDevice).getTriggerTimerEnabled()){
			setTriggerTimerEnabled(true);
			setOnTime(((ProxyX10Appliance)proxyDevice).getOnTime());
			setOffTime(((ProxyX10Appliance)proxyDevice).getOffTime());
		}
		else {
			//If we're in here we assume the TriggerTimer is not enabled
			//However if it was previously enabled we need to shut the timer
			//down otherwise we'll have a memory leak if subsequent updates
			//instantiate new timers
			if(getTriggerTimerEnabled()){
				
				//null it out
				setOnTimer(null);
			}
				
			setTriggerTimerEnabled(false);

		}
		
		//Store the proxyDevice state and this objects current state in 
		//instance variables to make the if statement easier to read
		String proxyDeviceState = ((ProxyX10Appliance)proxyDevice).getState().toString();
		String currentState = getState().toString();
		
		//Prevent Extra events from being fired by determining if the 
		//requested state change already equals the current state of this object
		if (!proxyDeviceState.equals(currentState)){
			setState ( ((ProxyX10Appliance)proxyDevice).getState() );
		}
		else{
			 LoggingUtilities.logInfo(X10Appliance.class.getCanonicalName(),
			 "processDeviceEvent()","INFO: Skipping state change to " + 
			 proxyDeviceState + " on device " + getName() );
		}
	}

	/**
	 * The processDeviceEvent method is used to listen for events from the Protocol
	 * class.  We simply watch for events which have a matching House and Device code
	 * and act upon all Device events.
	 * 
	 * @param Event e
	 */
	public void processDeviceEvent(X10DeviceEvent deviceEvent) {

		/* 
		 * Incoming events can originate from other Devices (Motion sensors or 
		 * Timers) or they can originate from protocol. Motion Sensors and 
		 * Timers send device events with only a Device Name since they are 
		 * not aware of an Appliances House and Device Codes. DeviceEvents
		 * sent by protocol do not contain a device name, rather only Device
		 * and House codes. The logic below handles both types of incoming events.
		 * 
		 * Another key concept to keep in mind here is that EventGenerator broadcasts
		 * events to all listeners. So, not all events heard by an Appliance
		 * are meant to be processed. Verify that all incoming event are intended
		 * for this device by checking that the Device name and house and Device 
		 * codes match. If they don't match, don't continue to process the event.
		 */
		
		//Handle incoming events from MotionSensor and Timer - when name is provided
		if (deviceEvent.getDeviceName( ).equals(getName())){
			
				if (deviceEvent.getEventCode( ) == X10_EVENT_CODE.X10_ON ){
					
					setState ( X10DeviceState.ON );
				}
				else if (deviceEvent.getEventCode( ) == X10_EVENT_CODE.X10_OFF ){
					
					setState ( X10DeviceState.OFF );
				}
				else{
					
					 LoggingUtilities.logError(X10Appliance.class.getCanonicalName(),
					 "processDeviceEvent()","Device: " + getName() +
					 " was unables to process the incoming " + deviceEvent.toString() +
					 " event from another device. This is likely due to an unrecognized event type");
				}
			
		}
		//Handle incoming event from Protocol - HouseCode and Device Code Provided
		else if ( (deviceEvent.getHouseCodeChar()  == getHouseCode( )) &&
		          (deviceEvent.getDeviceCodeInt() == getDeviceCode( ))){
				
					//Now that we know the event was intended for this device
					if (deviceEvent.getEventCode( ) == X10_EVENT_CODE.X10_ON ){
						setState ( X10DeviceState.ON );
					}
					else if (deviceEvent.getEventCode( ) == X10_EVENT_CODE.X10_OFF ){
						setState ( X10DeviceState.ON );
					}
					else{
						 LoggingUtilities.logError(X10Appliance.class.getCanonicalName(),
						 "processDeviceEvent()","Device: " + getName() +
						 " was unables to process the incoming " + 
						 deviceEvent.toString() + " event sent by Protocol." +
						 " This was likely due to an unrecognized event type");
					}
		}else {
			
			//The incoming event was not intended for this device.
			 LoggingUtilities.logInfo(X10Appliance.class.getCanonicalName(),
					 "processDeviceEvent()","Device: " + getName() +
					 " Did not process the incoming " + deviceEvent.toString() + 
					 " event. It was not intended for this device.");
		}
		
	}

	/**
	 * The processTimeEvent method is used to listen for events from the Timer class.
	 * We simply watch for events which match the same device Name.
	 */
	public void processTimerEvent(TimerEvent e) {
		
		// We only deal with timerEvents attached to this device name. 
		if (e instanceof TimerEvent &&
				((TimerEvent)e).equals(getName())){
				
					//Now that we know the event was intended for this device
					if (((TimerEvent)e).getEventName().equals("ON") ){
						setState ( X10DeviceState.ON );
					}
					else if (((TimerEvent)e).getEventName().equals("OFF") ){
						setState ( X10DeviceState.OFF );
					}
					else{
						 LoggingUtilities.logError(X10Appliance.class.getCanonicalName(),
						 "processTimerEvent()","Device: " + getName() +
						 " was unables to process " + ((TimerEvent)e).toString() +
						 " This is likely due to an unrecognized event type");
					}
		}
	}
	
	/**
	 * The processProtocolEvent is not used by this class.  We simply need
	 * to override it.
	 */
	public void processProtocolEvent(X10ProtocolEvent e) { 

	}

}
