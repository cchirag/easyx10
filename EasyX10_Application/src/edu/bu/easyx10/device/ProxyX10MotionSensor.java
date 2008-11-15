package edu.bu.easyx10.device;

import edu.bu.easyx10.event.*;
import java.util.*;
import java.sql.Time;

/**
 * 
 * The ProxyX10MotionSensor class is an extension of the X10MotionSensor
 * class.  It's purpose is to provide a vehicle to import and export the
 * various attributes of the X10MotionSensor class to the GUI layer.
 * 
 * @author  Jim Duda
 * @version please refer to subversion
 * @date:   11/15/08
 */
public class ProxyX10MotionSensor extends X10MotionSensor {

	// local member variables
	private Set<String> mApplianceList;       // List of Appliances to send events to

	/**
	 * Construct for a new ProxyX10MotionSensor class.  The constructor is provided
	 * with a X10MotionSensor object which contains all of the desired 
	 * attributes as defined by the higher level GUI.  The constructor must
	 * transfer all the attributes to the local object.  
	 */
	public ProxyX10MotionSensor ( X10MotionSensor motionDevice ) {	

		// create any required member classes
		mApplianceList = new HashSet<String>( );

		// load our member variables from the X10MotionDevice
		setName ( motionDevice.getName( ) );
		setHouseCode ( motionDevice.getHouseCode( ) );
		setDeviceCode ( motionDevice.getDeviceCode( ) );
		setInactivityTime ( motionDevice.getInactivityTime( ) );
		setDetectionPeriodEnabled ( motionDevice.getDetectionPeriodEnabled( ) );
		setStartTime ( motionDevice.getStartTime( ) );
		setEndTime ( motionDevice.getEndTime( ) );
		setApplianceList ( motionDevice.getApplianceList( ) );
		setState ( motionDevice.getState( ) );
	}

	/*
	 * Defined access methods for all of the member variables.
	 */

	/**
	 * This method loads the Inactivity Time attribute.  The Inactivity Time
	 * attribute defines the number of seconds for which a Motion Sensor will
	 * be in the MOTION state.  
	 * 
	 * @param int seconds Inactivity Time
	 */
	public void setInactivityTime (int seconds) {
		mInactivityTime = seconds;
	}

	/**
	 * This method returns the Inactivity Time attribute.  The Inactivity Time
	 * attribute defines the number of seconds for which a Motion Sensor will
	 * be in the MOTION state.  
	 * 
	 * @return int Inactivity Time
	 */
	public int getInactivityTime( ) {
		return mInactivityTime;
	}

	/**
	 * This method loads the Detection Period Enable attribute.  This attribute
	 * determines if the Motion Sensor is to determine if the current time is
	 * within the Detection Period, which is bound by Start Time and End Time
	 * attributes.  If the time is within the Detection Window and the Detection
	 * Period is Enabled, the Motion Sensor will turn on any Appliances defined
	 * by the Appliance List attribute.
	 * 
	 * @param boolean isEnabled
	 */
	public void setDetectionPeriodEnabled(boolean isEnabled){
		mDetectionPeriodEnabled = isEnabled;
	}

	/**
	 * This method returns the Detection Period Enable attribute.  This attribute
	 * determines if the Motion Sensor is to determine if the current time is
	 * within the Detection Period, which is bound by Start Time and End Time
	 * attributes.  If the time is within the Detection Window and the Detection
	 * Period is Enabled, the Motion Sensor will turn on any Appliances defined
	 * by the Appliance List attribute.
	 * 
	 * @return boolean isEnabled
	 */
	public boolean getDetectionPeriodEnabled(){
		return mDetectionPeriodEnabled;
	}

	/**
	 * This method loads the Start Time attribute.  When the Motion Detection
	 * Period Enable attribute is set TRUE, this attribute defines the start
	 * time for a window in which motion activity for this object can cause
	 * an Appliance object to be turned ON.
	 * 
	 * @param Time startTime Beginning time for Motion Activity Window
	 */
	public void setStartTime(Time startTime) {
		mStartTime = startTime;
	}

	/**
	 * This method returns the Start Time attribute.  When the Motion Detection
	 * Period Enable attribute is set TRUE, this attribute defines the start
	 * time for a window in which motion activity for this object can cause
	 * an Appliance object to be turned ON.
	 * 
	 * @return Time Beginning time for Motion Activity Window
	 */
	public Time getStartTime() {
		return mStartTime;
	}

	/**
	 * This method loads the End Time attribute.  When the Motion Detection
	 * Period Enable attribute is set TRUE, this attribute defines the end
	 * time for a window in which motion activity for this object can cause
	 * an Appliance object to be turned ON.
	 * 
	 * @param Time startTime Beginning time for Motion Activity Window
	 */
	public Time getEndTime() {
		return mEndTime;
	}

	/**
	 * This method returns the End Time attribute.  When the Motion Detection
	 * Period Enable attribute is set TRUE, this attribute defines the end
	 * time for a window in which motion activity for this object can cause
	 * an Appliance object to be turned ON.
	 * 
	 * @return Time Ending time for Motion Activity Window
	 */
	public void setEndTime(Time endTime) {
		mEndTime = endTime;
	}

	/**
	 * This method loads the Appliance List attribute.  The Appliance List
	 * is a Set of Strings where each String identifies a Device Name of
	 * an associated Appliance.  Each associated appliance can be controlled
	 * by this Motion Sensor when motion activity is detected.
	 * 
	 * @param applianceList the mApplianceList to set
	 */
	public void setApplianceList(Set<String> applianceList) {
		// clear out the old list
		mApplianceList.clear( );

		// All all the new ones
		mApplianceList.addAll ( applianceList );
	}

	/**
	 * This method loads the Appliance List attribute.  The Appliance List
	 * is a Set of Strings where each String identifies a Device Name of
	 * an associated Appliance.  Each associated appliance can be controlled
	 * by this Motion Sensor when motion activity is detected.
	 * 
	 * @return Set<String> 
	 */
	public Set<String> getApplianceList() {
		return mApplianceList;
	}

	/**
	 * This method loads the state attribute.
	 * 
	 * @param X10DeviceState state - new state of ON (motion) or OFF
	 */
	public void setState( X10DeviceState state ) {
		mState = state;
	}

	/**
	 * The processDeviceEvent is not used by this class.  We simply need
	 * to override it.
	 */
	public void processDeviceEvent(Event e) {

	}

	/**
	 * The processTimerEvent is not used by this class.  We simply need
	 * to override it.
	 */
	public void processTimerEvent(Event e) {

	}

	/**
	 * The processProtocolEvent is not used by this class.  We simply need
	 * to override it.
	 */
	public void processProtocolEvent(Event e) { 

	}

}

