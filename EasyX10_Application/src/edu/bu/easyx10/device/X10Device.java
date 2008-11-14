package edu.bu.easyx10.device;

/**
 * The X10Device class is the second level of abstraction 
 * above the Device base class. All X10Devices have attributes
 * State, DeviceCode and a HouseCode. Real X10 modules have 
 * house and device codes as well. The getters and setters 
 * for DeviceCode and HouseCode are implemented in this class, 
 * while setState is implemented in a Derived Class since states
 * are Device Specific.
 *
 * @author  Damon Gabrielle
 * @version please refer to subversion
 * @date:   11/06/08
 */

public abstract class X10Device extends Device{

	// Declare Private Member Variables
	private int mDeviceCode;                         //The Devices Device Code
	                                                 //1 thru 16 are valid
	
	private char mHouseCode;                         //The Devices house code
	                                                 //A thru P are valid
	
	public static enum X10DeviceState {ON, OFF};   //Valid states of X10 Devices
	
	protected X10DeviceState mState;                  //The state of the device
		
	/**
	 * @return Returns a char containing the X10 Devices HouseCode
	 */	
	public char getHouseCode(){
		return mHouseCode;
	}
	
	/**
	 * @param Set the  a char containing the X10 Devices HouseCode
	 * Valid mHouseCode values range from A thru P
	 */
	public boolean setHouseCode(char houseCode) {
		mHouseCode = houseCode;
		return true;
	}
	
	/**
	 * @return Returns a char containing the X10 Devices HouseCode
	 */	
	public int getDeviceCode(){
		return mDeviceCode;
	}

	/**
	 * @param Set the  a char containing the X10 Devices HouseCode
	 * Valid device codes range from 1 to 16
	 */
	public boolean setDeviceCode(int deviceCode) {
		mDeviceCode = deviceCode;
		return true;
	}
	
	/**
	 * @param the Derived class must implement this method in order to set
	 * the state of the X10 Device. Valid states should be specified in
	 * the derived class since they are specific to that device.
	 * 
	 */
	public abstract boolean setState(X10DeviceState state );
	
	
	/**
	 * @param the Derived class must implement this method.
	 * The purpose of this method is to return the Devices State
	 * 
	 */
	public abstract String getState();

	
}
