package edu.bu.easyx10.device;

import edu.bu.easyx10.event.Event;
import edu.bu.easyx10.event.TimerEvent;
import edu.bu.easyx10.event.X10DeviceEvent;
import edu.bu.easyx10.event.X10Event.X10_EVENT_CODE;
import edu.bu.easyx10.device.timer.*;

import java.sql.Time;

/** The X10Appliance class is derived from the abstract class X10DeviceClass. 
 * It�s purpose is to model the state and behavior of X10 appliance modules.
 * The state of X10 devices are controlled either by device updates coming
 * down from the Device manager from the gui package, or from incoming
 * DeviceEvents published by EventGenerator. X10Applianc objects also implement
 * TriggerTimers to turn lights on and off during a particular time period. It's 
 * important to note that incoming events are processed regardless of whatever
 * Period is set in a TriggeTimer. 
*/

public class X10Appliance extends X10Device{
	
	//Declare Private Member Variables
	private TriggerTimer mOnTimer;            // Timer used to trigger On Event
	private TriggerTimer mOffTimer;           // Timer used to trigger Off Event
	private Time mOnTime;                     // Time to turn appliance on
	private Time mOffTime;                    // Time to shut appliance off
	private boolean mTriggerTimerEnabled;     // Check if TriggerTimer is Enabled 
	private TimerEvent mOnEvent;               // The  ON event riggerTimer will fire
	private TimerEvent mOffEvent;              // The  OFF event riggerTimer will fire

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
		
		//If the triggerTimer is enabled proceed with setting 
		// the onTimer, OffTimer, onTime and OffTime attributes.
		if(proxyX10Appliance.getTriggerTimerEnabled()){
			setTriggerTimerEnabled(true);
			setOnTime(proxyX10Appliance.getOnTime());
			setOffTime(proxyX10Appliance.getOffTime());
			
			//Create the onEvent to be passed to the TriggerTimer
			mOnEvent = new TimerEvent ( getName(), "ON" );
			
			//instantiate the member TriggerTimer mOnTimer
			mOnTimer = new TriggerTimer (mOnEvent);
			
			//Create the onEvent to be passed to the TriggerTimer
			mOffEvent = new TimerEvent ( getName(), "OFF" );
			
			//instantiate the member TriggerTimer mOnTimer
			mOffTimer = new TriggerTimer (mOffEvent);
		}
		else {
			setTriggerTimerEnabled(false);
		}
			
		setState ( proxyX10Appliance.getState( ) );
		
		
		
	}
	
	//This constructor is required by ProxyX10Appliance
	//TODO finish constructor that's used  by proxyAppliance
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
	private void setOnTimer(Time anOnTime){
		
		//set the time to fire the on event
	    mOnTimer.setTriggerTime(anOnTime);
	    
		//start up the timer
		mOnTimer.startTimer();
		
	}
	
	/**
	 * This method instantiates a TriggerTimer object containing the time
	 * to turn this appliance off. When the system time reaches the time specified
	 * an event will be fired to turn the appliance on.
	 * 
	 * @param Pass in a Time object equal to the time to turn the Appliance on.
	 */
	private void setOffTimer(Time anOffTime){
		//set the time to fire the on event
	    mOffTimer.setTriggerTime(anOffTime);
	    
		//start up the timer
		mOffTimer.startTimer();
		
	}
	
	/**
	 * @return Returns a Time object representing the time an event will be 
	 * fired to turn on the Appliance
	 */
	public Time getOnTime() {
		return mOnTime;
	}

	/**
	 * This method sets the time that an Appliance should be turned on.
	 * It then internally calls setOnTimer to instantiate the actual timer
	 * that will fire an X10Event to turn the device on.
	 * 
	 *  @param A Time to turn on the Appliance
	 */
	public void setOnTime(Time onTime) {
		
		mOnTime = onTime;
		
		//now hand off the onTime to the onTimer;
		setOnTimer(mOnTime);
	}
	
	/**
	 * This method returns a Time object representing the time an off Event
	 * will be sent to turn the appliance off.
	 * @return
	 */
	public Time getOffTime() {
		return mOffTime;
	}
	
	/**
	 * This method sets the time that an Appliance should be turned off.
	 * It then internally calls setOffTimer to instantiate the actual timer
	 * that will fire an X10Event to turn the device off.
	 * 
	 *  @param A time to turn off the Appliance
	 */
	
	public void setOffTime(Time offTime) {
		
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

	@Override
	public synchronized boolean setState( X10DeviceState state ) {
		// TODO Auto-generated method stub
		return false;
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
			setTriggerTimerEnabled(false);
			//TODO Figure out how to stop the timer
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
			System.out.println("INFO: Skipping state change to " + 
								proxyDeviceState + " on device " + 
								this.getName() );
		}
	}

	/**
	 * The processDeviceEvent method is used to listen for events from the Protocol
	 * class.  We simply watch for events which have a matching House and Device code
	 * and act upon all Device events.
	 * 
	 * @param Event e
	 */
	public void processDeviceEvent(Event e) {

		/* 
		 * Verify the incoming event was intended for this device by checking
		 * that the Device name and house and unit codes match. If it doesn't,
		 * don't do anything with it. Remember that EventGenerator broadcasts
		 * events to all listeners so not all events heard are meant to be processed.
		 */
		if (e instanceof X10DeviceEvent &&
				((X10DeviceEvent)e).getDeviceName().equals(getName()) &&
				((X10DeviceEvent)e).getHouseCodeChar( ) == getHouseCode( ) &&
				((X10DeviceEvent)e).getDeviceCodeInt( ) == getDeviceCode( )){
				
					//Now that we know the event was intended for this device
					if (((X10DeviceEvent)e).getEventCode( ) == X10_EVENT_CODE.X10_ON ){
						setState ( X10DeviceState.ON );
					}
					else if (((X10DeviceEvent)e).getEventCode( ) == X10_EVENT_CODE.X10_OFF ){
						setState ( X10DeviceState.ON );
					}
					else{
						System.out.println("Error: Device: " + getName() +
								            " was unables to process " +
								            ((X10DeviceEvent)e).toString() +
								            " This is likely due to an unrecognized event type");
					}
		}
	}

	/**
	 * The processTimeEvent method is used to listen for events from the Timer class.
	 * We simply watch for events which match the same device Name.
	 */
	public void processTimerEvent(Event e) {
		/* We only deal with timerEvents attached to this device name.  When
		 * we get a timer event, we set our state to OFF to indicate no motion.
		 */
		if (e instanceof X10DeviceEvent &&
				((X10DeviceEvent)e).getDeviceName().equals(getName()) &&
				((X10DeviceEvent)e).getHouseCodeChar( ) == getHouseCode( ) &&
				((X10DeviceEvent)e).getDeviceCodeInt( ) == getDeviceCode( )){
				
					//Now that we know the event was intended for this device
					if (((X10DeviceEvent)e).getEventCode( ) == X10_EVENT_CODE.X10_ON ){
						setState ( X10DeviceState.ON );
					}
					else if (((X10DeviceEvent)e).getEventCode( ) == X10_EVENT_CODE.X10_OFF ){
						setState ( X10DeviceState.ON );
					}
					else{
						System.out.println("Error: Device: " + getName() +
								            " was unables to process " +
								            ((X10DeviceEvent)e).toString() +
								            " This is likely due to an unrecognized event type");
					}
		}
	}
	
	/**
	 * The processProtocolEvent is not used by this class.  We simply need
	 * to override it.
	 */
	public void processProtocolEvent(Event e) { 

	}

}
