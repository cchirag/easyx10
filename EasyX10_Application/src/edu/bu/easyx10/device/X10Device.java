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
	
	public static enum X10DeviceState {ON, OFF};     //Valid states of X10 Devices
	
	protected X10DeviceState mState;                 //The state of the device
	

	
	/** 
	 * Constructor for X10Devices must have a device name,
	 * and a corresponding house and device codes.
	 */
	 
	public X10Device(String name, char houseCode, int deviceCode){
		
		super(name);
		setHouseCode(houseCode);
		setDeviceCode(deviceCode);
		
	}
	
	
	/**
	 * This method returns the HouseCode char that corresponds to
	 * the houseCode set on the physical device.
	 * 
	 * @return Returns a char containing the X10 Devices HouseCode
	 */	
	public char getHouseCode(){
		return mHouseCode;
	}
	
	/**
	 * This method sets the HouseCode member variable.
	 * The value passed should correspond to the value
	 * on the actual device.
	 * 
	 * @param Set the  a char containing the X10 Devices HouseCode
	 * Valid mHouseCode values range from A thru P
	 * 
	 * @return Return True if houseCode is successfully set otherwise
	 * return false and print an error message.
	 *
	 */
	public boolean setHouseCode(char houseCode) {
		
		
		//convert char to uppercase in case it was lowercase when passed in
		Character.toUpperCase(houseCode);
		
		//Verify the passed in value is a valid houseCode

		switch (houseCode) {
		
			case 'A':  mHouseCode = houseCode; return true;
			case 'B':  mHouseCode = houseCode; return true;
			case 'C':  mHouseCode = houseCode; return true;
			case 'D':  mHouseCode = houseCode; return true;
			case 'E':  mHouseCode = houseCode; return true;
			case 'F':  mHouseCode = houseCode; return true;
			case 'G':  mHouseCode = houseCode; return true;
			case 'H':  mHouseCode = houseCode; return true;
			case 'I':  mHouseCode = houseCode; return true;
			case 'J':  mHouseCode = houseCode; return true;
			case 'K':  mHouseCode = houseCode; return true;
			case 'L':  mHouseCode = houseCode; return true;
			case 'M':  mHouseCode = houseCode; return true;
			case 'N':  mHouseCode = houseCode; return true;
			case 'O':  mHouseCode = houseCode; return true;
			case 'P':  mHouseCode = houseCode; return true;
			default: System.out.println(" Error: You entered an invalid houseCode"); 
			return false;
		}

	}
	
	/**
	 * 
	 * @return Returns a integer value between 1 and 16 representing Device's
	 * corresponding Device Code.
	 */	
	public int getDeviceCode(){
		return mDeviceCode;
	}

	/**
	 * 
	 * This method takes in an integer value and sets the deviceCode
	 * to the passed in value if and only if the value is between 1 and 16.
	 *  
	 * @param Set the  a char containing the X10 Devices DeviceCode
	 * Valid device codes range from 1 to 16
	 * 
	 * @return Returns a integer value between 1 and 16 representing Device's
	 * corresponding Device Code.
	 * 
	 */
	public boolean setDeviceCode(int deviceCode) {
		
		if (deviceCode >= 0 && deviceCode <=16){
		mDeviceCode = deviceCode;
		return true;
		}
		else{
			System.out.println("Error: The DeviceCode value" +
								deviceCode + " for " + this.getName() +
								" is not within the acceptable value range");
			return false;
		}
	
	}
	
	/**
	 * @param the Derived class must implement this method in order to set
	 * the state of the X10 Device. Valid states should be specified in
	 * the derived class since they are specific to that device.
	 * 
	 */
	public abstract void setState(X10DeviceState state );
	
	
	/**
	 * @param the Derived class must implement this method.
	 * The purpose of this method is to return the Devices State
	 * 
	 */
	public abstract X10DeviceState getState();
	
	
	/** 
	 * Implementation of the toString method.  This method
	 * will return a String representation of the current
	 * state.
	 *
	 * @return String
	 */
	public String toString(){
		
			String state;
			switch (getState( )) {
			
				case ON:  state = "ON"; break;
				case OFF: state = "OFF"; break;
				default: state = "UNKNOWN"; break;
			}
			return ( state );
	}

	
}
