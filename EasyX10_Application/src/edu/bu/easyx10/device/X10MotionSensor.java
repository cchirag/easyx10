package edu.bu.easyx10.device;

import edu.bu.easyx10.event.Event;
import edu.bu.easyx10.device.timer.*;
import java.util.*;
import java.sql.Time;

/**
 * 
 * The X10Motion sensor class is where X10 motion sensor devices 
 * are created. In addition to inheriting from X10Device and 
 * Device, X10MotionSensor contains a WaitTimer, that stores the 
 * number of seconds to wait before deeming a motion 
 * sensor inactive. When the timer reaches zero, it fires an event 
 * that is processed by the X10MotionSensor that sets it’s state 
 * to inactive. It also contains an ApplianceList, so that it 
 * knows what devices to send DeviceEvents to when motion is detected,
 * a StartTime and EndTime to mark the window during which motion 
 * events should be processed and a boolean so that it can be checked 
 * whether a window time has been defined.
 * 
 * @author Damon Gabrielle
 * @version please refer to subversion
 * @date:   11/06/08
 */
public class X10MotionSensor extends X10Device{
	
	//Declare Private Member Variables
	private int mInactivityTime;              // Seconds before Sensor is marked inactive
	
	private WaitTimer mInactivityTimer;	      // Timer to instantiate with mInactivitTime
	
	private Set<X10Appliance> mApplianceList; // List of Appliances to send events to
	
	private Time mStartTime;                  // Beginning of Motion Detection Period
	
	private Time mEndTime;                    // End of motion Detection Period
	
	private boolean mDetectionPeriodEnabled;  // If Enabled Motion Detector will only 
                                              // send Device events between mStartime
	                                          // and mEndTime. 
									          // Incoming motion events still
									          // can change the Motion sensors state 
	
	/**
	 * Construct for a new X10MotionSensor class.  The constructor 
	 * always registers with the EventGenerator in order to 
	 * receive inbound events.
	 */
	public X10MotionSensor(Device proxyDevice) {
		
		//Instantiate an empty set of X10Appliances
		setApplianceList(new HashSet<X10Appliance>());
		
		//Code needed to Iterate through the X10Appliance set
		//and check for a duplicate before inserting a new entry
		
        /* TODO Continue ironing out Iterating HashSet Container
		for (X10Appliance app : proxyDevice.getApplianceList()){
            if (!mApplianceList.add(proxyDevice)){
                System.out.println("Duplicate detected: " + a);
            }
            else {
            	System.out.println("Added" + a);
            }
        }
        
        */
		
		//set the initial device state
		//mState =  MotionState.ACTIVE;

		
	}
	
	public void setInactivityTime(int seconds){
		
		//set the inactivity time to # of seconds passed in
		mInactivityTime = seconds;
		
		//instantiate the real WaitTimer
		setInactivityTimer (mInactivityTime);
	}
	
	public int getInactivityTime(){
		return mInactivityTime;
	}

	/**
	 * This method is called from 2 places
	 * 
	 * 	1. By setInactivityTime
	 *  2. It is directly called by processDeviceEvent() to reset the
	 *     timer
	 *  3.  
	 * @param secondsToWait
	 */
	private void setInactivityTimer(int secondsToWait){
		
		//Check if we've already instantiated the WaitTimer
		if (!(mInactivityTimer instanceof WaitTimer)){
			//It hasn't been instantiated yet so instantiate it.
			
			mInactivityTimer = new WaitTimer(secondsToWait);
		}
		else{
			mInactivityTimer.setSecondsToWait(secondsToWait);
		}
		//
		mInactivityTimer.setSecondsToWait(secondsToWait);
	}
	

	/**
	 * @param applianceList the mApplianceList to set
	 */
	public void setApplianceList(Set<X10Appliance> applianceList) {
		this.mApplianceList = applianceList;
	}

	/**
	 * @return the mApplianceList
	 */
	public Set<X10Appliance> getApplianceList() {
		return mApplianceList;
	}

	public void setStartTime(Time startTime) {
		mStartTime = startTime;
	}
	
	public Time getStartTime() {
		return mStartTime;
	}
	
	public Time getEndTime() {
		return mEndTime;
	}

	public void setEndTime(Time endTime) {
		mEndTime = endTime;
	}
	
	public void setDetectionPeriodEnabled(boolean isEnabled){
		mDetectionPeriodEnabled = isEnabled;
	}
	
	public boolean getDetectionPeriodEnabled(){
		return mDetectionPeriodEnabled;
	}
	
	@Override
	public String getState() {
		// TODO Auto-generated method stub
		
		return mState.toString();
	}

	@Override
	public synchronized boolean setState( X10DeviceState state ) {
		// TODO Must insert setState logic
		// just set it to eliminate compilation warnings for now.
		mState = state;
		return false;
	}

	@Override
	public Device getProxyDevice() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateDevice(Device proxyDevice) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processDeviceEvent(Event e) {
		// TODO Auto-generated method stub

	}


	@Override
	public void processTimerEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processProtocolEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

}
