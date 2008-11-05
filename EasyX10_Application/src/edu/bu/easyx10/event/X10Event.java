package edu.bu.easyx10.event;

import edu.bu.easyx10.event.Event;

/**
 * This is the abstract base class for the X10Event class.  Concrete
 * X10Event types must extended from this class.  This class inherits
 * all attributes and methods from the abstract base Event class.
 *
 * All X10Events have houseCodes, deviceCodes, and eventCodes
 *
 * @author:  Jim Duda
 * @version: refer to EasyX10 subversion
 * @date:    10/30/08
 *
 */
public abstract class X10Event extends Event {

	// Create an enumeration which defines all the possible X10 house codes.
	public static enum X10_HOUSE_CODE {
		X10_A, X10_B, X10_C, X10_D,
		X10_E, X10_F, X10_G, X10_H,
		X10_I, X10_J, X10_K, X10_L,
		X10_M, X10_N, X10_O, X10_P
	}

	// Create an enumeration which defines all the possible X10 device codes.
	public static enum X10_DEVICE_CODE {
		X10_1,  X10_2,  X10_3,  X10_4,
		X10_5,  X10_6,  X10_7,  X10_8,
		X10_9,  X10_10, X10_11, X10_12,
		X10_13, X10_14, X10_15, X10_16
	}

	// Create an enumeration which defines all the possible X10 event codes.
	public static enum X10_EVENT_CODE {
		X10_ALL_UNITS_OFF,
		X10_ALL_LIGHTS_ON,
		X10_ON,
		X10_OFF,
		X10_DIM,
		X10_BRIGHT,
		X10_ALL_LIGHTS_OFF,
		X10_EXTENDED_CODE,
		X10_HAIL_REQUEST,
		X10_HAIL_ACKNOWLEDGE,
		X10_PRESET_DIM_1,
		X10_PRESET_DIM_2,
		X10_EXTENDED_DATA_TRANSFER,
		X10_STATUS_ON,
		X10_STATUS_OFF,
		X10_STATUS_REQUEST
	}

	// Create the private member variables for this class.
	private X10_HOUSE_CODE  m_houseCode;
	private X10_DEVICE_CODE m_deviceCode;
	private X10_EVENT_CODE  m_eventCode;

	/**
	 * Constructor - default implicit constructor
	 */
	public X10Event ( ) {

	}

	/**
	 * Construct a new X10Event Object using human readable parameters.
	 *
	 * @param deviceName - String Device name
	 * @param houseCode  - char House code value between 'A' and 'P' inclusive.
	 * @param deviceCode - integer Unit code value between 1 and 16 inclusive.
	 * @param commmand   - String ON, OFF, DIM, BRIGHT
	 * @see
	 */
	public X10Event(String deviceName, char houseCode, int deviceCode, String eventCode) throws IllegalArgumentException {

		// Load the deviceName in the base class
		super ( deviceName );

		// Load the houseCode member variable
		try {
			setHouseCode ( houseCode );
		} catch ( IllegalArgumentException e ) {
			throw e;
		}

		// Load the deviceCode member variable
		try {
			setDeviceCode ( deviceCode );
		} catch ( IllegalArgumentException e ) {
			throw e;
		}

		// Load the eventCode member variable
		try {
			setEventCode ( eventCode );
		} catch ( IllegalArgumentException e ) {
			throw e;
		}

	}

	/**
	 * Create and X10Event object from X10 enumerations.
	 *
	 * @param deviceName - Device name
	 * @param houseCode  - House code value between 'A' and 'P' inclusive.
	 * @param deviceCode - Unit code value between 1 and 16 inclusive.
	 * @param commmand   - String ON, OFF, DIM, BRIGHT
	 * @see
	 */
	public X10Event(String deviceName,
			X10_HOUSE_CODE houseCode,
			X10_DEVICE_CODE deviceCode,
			X10_EVENT_CODE eventCode) throws IllegalArgumentException {

		// Load the deviceName in the base class
		super ( deviceName );

		// Load local member variables
		setHouseCode ( houseCode );
		setDeviceCode ( deviceCode );
		setEventCode ( eventCode );
	}

	/**
	 * Copy Constructor for X10Event Object
	 *
	 * @param e X10Event
	 *
	 */
	public X10Event( X10Event e ) {
		super ( e.getDeviceName( ) );
		setHouseCode ( e.getHouseCode( ) );
		setDeviceCode ( e.getDeviceCode( ) );
		setEventCode ( e.getEventCode( ) );
	}

	/**
	 * Accessor for the m_houseCode member variable.
	 *
	 * @param houseCode - char House Code, values 'A' through 'P' inclusive.
	 */
	public void setHouseCode ( char houseCode ) throws IllegalArgumentException {
		// Validate input parameters and throw exception
		switch (Character.toUpperCase(houseCode) ) {
		case 'A': m_houseCode = X10_HOUSE_CODE.X10_A; break;
		case 'B': m_houseCode = X10_HOUSE_CODE.X10_B; break;
		case 'C': m_houseCode = X10_HOUSE_CODE.X10_C; break;
		case 'D': m_houseCode = X10_HOUSE_CODE.X10_D; break;
		case 'E': m_houseCode = X10_HOUSE_CODE.X10_E; break;
		case 'F': m_houseCode = X10_HOUSE_CODE.X10_F; break;
		case 'G': m_houseCode = X10_HOUSE_CODE.X10_G; break;
		case 'H': m_houseCode = X10_HOUSE_CODE.X10_H; break;
		case 'I': m_houseCode = X10_HOUSE_CODE.X10_I; break;
		case 'J': m_houseCode = X10_HOUSE_CODE.X10_J; break;
		case 'K': m_houseCode = X10_HOUSE_CODE.X10_K; break;
		case 'L': m_houseCode = X10_HOUSE_CODE.X10_L; break;
		case 'M': m_houseCode = X10_HOUSE_CODE.X10_M; break;
		case 'N': m_houseCode = X10_HOUSE_CODE.X10_N; break;
		case 'O': m_houseCode = X10_HOUSE_CODE.X10_O; break;
		case 'P': m_houseCode = X10_HOUSE_CODE.X10_P; break;
		default: throw new IllegalArgumentException("Invalid house code: " + houseCode);
		}
	}

	// set the house code using an enumeration
	/**
	 * Accessor for the m_houseCode member variable.
	 *
	 * @param houseCode - enumeration X10_A through X10_P inclusive.
	 */
	public void setHouseCode ( X10_HOUSE_CODE houseCode ) {
		m_houseCode = houseCode;
	}


	/**
	 * Accessor for the m_houseCode member variable.
	 *
	 * @return - enumeration X10_A through X10_P inclusive.
	 */
	public X10_HOUSE_CODE getHouseCode( ) {
		return m_houseCode;
	}

	/**
	 * Accessor for the m_houseCode member variable.
	 *
	 * @return String - House Code values "A" through "P" inclusive.
	 */
	public String getHouseCodeString( ) {
		String houseCode = "";
		switch (m_houseCode) {
		case X10_A: houseCode = "A"; break;
		case X10_B: houseCode = "B"; break;
		case X10_C: houseCode = "C"; break;
		case X10_D: houseCode = "D"; break;
		case X10_E: houseCode = "E"; break;
		case X10_F: houseCode = "F"; break;
		case X10_G: houseCode = "G"; break;
		case X10_H: houseCode = "H"; break;
		case X10_I: houseCode = "I"; break;
		case X10_J: houseCode = "J"; break;
		case X10_K: houseCode = "K"; break;
		case X10_L: houseCode = "L"; break;
		case X10_M: houseCode = "M"; break;
		case X10_N: houseCode = "N"; break;
		case X10_O: houseCode = "O"; break;
		case X10_P: houseCode = "P"; break;
		default: assert(false);
		}
		return houseCode;
	}

	/**
	 * Accessor for the m_deviceCode member variable.
	 *
	 * @param deviceCode - integer Device Code values 1 through 16 inclusive.
	 * @throws IllegalArgumentException
	 */
	public void setDeviceCode ( int deviceCode ) throws IllegalArgumentException {
		switch(deviceCode) {
		case 1:  m_deviceCode = X10_DEVICE_CODE.X10_1; break;
		case 2:  m_deviceCode = X10_DEVICE_CODE.X10_2; break;
		case 3:  m_deviceCode = X10_DEVICE_CODE.X10_3; break;
		case 4:  m_deviceCode = X10_DEVICE_CODE.X10_4; break;
		case 5:  m_deviceCode = X10_DEVICE_CODE.X10_5; break;
		case 6:  m_deviceCode = X10_DEVICE_CODE.X10_6; break;
		case 7:  m_deviceCode = X10_DEVICE_CODE.X10_7; break;
		case 8:  m_deviceCode = X10_DEVICE_CODE.X10_8; break;
		case 9:  m_deviceCode = X10_DEVICE_CODE.X10_9; break;
		case 10: m_deviceCode = X10_DEVICE_CODE.X10_10; break;
		case 11: m_deviceCode = X10_DEVICE_CODE.X10_11; break;
		case 12: m_deviceCode = X10_DEVICE_CODE.X10_12; break;
		case 13: m_deviceCode = X10_DEVICE_CODE.X10_13; break;
		case 14: m_deviceCode = X10_DEVICE_CODE.X10_14; break;
		case 15: m_deviceCode = X10_DEVICE_CODE.X10_15; break;
		case 16: m_deviceCode = X10_DEVICE_CODE.X10_16; break;
		default: throw new IllegalArgumentException("Invalid unit code: " + deviceCode);
		}
	}

	/**
	 * Accessor for the m_deviceCode member variable.
	 *
	 * @param deviceCode - enumeration for Device Code 1 through 16 inclusive.
	 */
	public void setDeviceCode ( X10_DEVICE_CODE deviceCode ) {
		m_deviceCode = deviceCode;
	}

	/**
	 * Accessor for the m_deviceCode member variable.
	 *
	 * @return enumeration for Device Code 1 through 16 inclusive.
	 */
	public X10_DEVICE_CODE getDeviceCode( ) {
		return m_deviceCode;
	}

	/**
	 * Accessor for the m_deviceCode member variable.
	 *
	 * @return String for Device Code "1" through "16" inclusive.
	 */
	public String getDeviceCodeString( ) {
		String deviceCode = "";
		switch (m_deviceCode) {
		case X10_1: deviceCode = "1"; break;
		case X10_2: deviceCode = "2"; break;
		case X10_3: deviceCode = "3"; break;
		case X10_4: deviceCode = "4"; break;
		case X10_5: deviceCode = "5"; break;
		case X10_6: deviceCode = "6"; break;
		case X10_7: deviceCode = "7"; break;
		case X10_8: deviceCode = "8"; break;
		case X10_9: deviceCode = "9"; break;
		case X10_10: deviceCode = "10"; break;
		case X10_11: deviceCode = "11"; break;
		case X10_12: deviceCode = "12"; break;
		case X10_13: deviceCode = "13"; break;
		case X10_14: deviceCode = "14"; break;
		case X10_15: deviceCode = "15"; break;
		case X10_16: deviceCode = "16"; break;
		default: assert(false);
		}
		return deviceCode;
	}

	/**
	 * Accessor for the m_eventCode member variable.
	 *
	 * @param eventCode - String which identifies the X10 event.
	 *
	 * @throws IllegalArgumentException
	 */
	public void setEventCode ( String eventCode ) throws IllegalArgumentException {
		if (eventCode.equalsIgnoreCase ( "ON" ) ) {
			m_eventCode = X10_EVENT_CODE.X10_ON;
		} else if (eventCode.equalsIgnoreCase ( "OFF" ) ) {
			m_eventCode = X10_EVENT_CODE.X10_OFF;
		} else if (eventCode.equalsIgnoreCase ( "DIM" ) ) {
			m_eventCode = X10_EVENT_CODE.X10_DIM;
		} else if (eventCode.equalsIgnoreCase ( "BRIGHT" ) ) {
			m_eventCode = X10_EVENT_CODE.X10_BRIGHT;
		} else {
			throw new IllegalArgumentException("Invalid eventCode: " + eventCode);
		}
	}

	/**
	 * Accessor the m_eventCode member variable.
	 *
	 * @param eventCode - enumeration which identifies the X10 event.
	 */
	public void setEventCode ( X10_EVENT_CODE eventCode ) {
		m_eventCode = eventCode;
	}

	/**
	 * Accessor to the m_eventCode member variable.
	 *
	 * @return enumeration which identifies the X10 event.
	 */
	public X10_EVENT_CODE getEventCode( ) {
		return m_eventCode;
	}

	/**
	 * Accessor to the m_eventCode member variable.
	 *
	 * @return String which identifies the X10 event.
	 */
	public String getEventCodeString( ) {
		String event = "";
		switch (m_eventCode) {
		case X10_ALL_UNITS_OFF:    event = "ALL_UNITS_OFF"; break;
		case X10_ALL_LIGHTS_ON:    event = "ALL_LIGHTS_ON"; break;
		case X10_ON:               event = "ON"; break;
		case X10_OFF:              event = "OFF"; break;
		case X10_DIM:              event = "DIM"; break;
		case X10_BRIGHT:           event = "BRIGHT"; break;
		case X10_ALL_LIGHTS_OFF:   event = "ALL_LIGHTS_OFF"; break;
		case X10_EXTENDED_CODE:    event = "EXTENDED_CODE"; break;
		case X10_HAIL_REQUEST:     event = "HAIL_REQUEST"; break;
		case X10_HAIL_ACKNOWLEDGE: event = "HAIL_ACKNOWLEDGE"; break;
		case X10_PRESET_DIM_1:     event = "PRESET_DIM_1"; break;
		case X10_PRESET_DIM_2:     event = "PRESET_DIM_2"; break;
		case X10_EXTENDED_DATA_TRANSFER: event = "EXTENDED_DATA_TRANSFER"; break;
		case X10_STATUS_ON:        event = "STATUS_ON"; break;
		case X10_STATUS_OFF:       event = "STATUS_OFF"; break;
		case X10_STATUS_REQUEST:   event = "STATUS_REQUEST"; break;
		default: assert(false);
		}
		return event;
	}

	/**
	 *   Produce a human-readable representation of the X10Event.
	 *
	 *   @return Something along the lines of "G12_ON" or "E1_OFF" or "C_BRIGHT" or "A_DIM".
	 */
	public String toString() {
		String s = getDeviceName( ) + "::" + getHouseCodeString( ) + getDeviceCodeString( ) + "_" + getEventCodeString( );
		return s;
	}

}