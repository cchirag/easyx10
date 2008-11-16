package edu.bu.easyx10.device;

import edu.bu.easyx10.event.Event;
import edu.bu.easyx10.device.timer.*;
import java.sql.Time;

/* The X10Appliance class is derived from the abstract class X10DeviceClass. 
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


	
	private Time getOnTimer(Time anOnTime){
		// TODO Implement getOnTimer Method
		return mOnTimer.getTriggerTime();
	}

	private void setOnTimer(Time anOnTime){
		// TODO Implement setOnTimer Method
		mOnTimer.setTriggerTime(anOnTime);
	}
	
	private Time getOffTimer(Time anOffTime){
		// TODO Implement getOffTimer Method
		return mOffTimer.getTriggerTime();
	}

	private void setOffTimer(Time anOffTime){
		// TODO Implement setOffTimer Method
		mOnTimer.setTriggerTime(anOffTime);
	}
	
	public Time getOnTime() {
		return mOnTime;
	}

	public void setOnTime(Time onTime) {
		mOnTime = onTime;
	}
	
	public Time getOffTime() {
		return mOffTime;
	}

	public void setOffTime(Time offTime) {
		mOnTime = offTime;
	}

	public boolean isTriggerTimerEnabled() {
		return mTriggerTimerEnabled;
	}

	public void setTriggerTimerEnabled(boolean triggerTimerEnabled) {
		mTriggerTimerEnabled = triggerTimerEnabled;
	}

	@Override
	public X10DeviceState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public synchronized boolean setState( X10DeviceState state ) {
		// TODO Auto-generated method stub
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
	public void processProtocolEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processTimerEvent(Event e) {
		// TODO Auto-generated method stub
		
	}

}
