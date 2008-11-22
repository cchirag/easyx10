package edu.bu.easyx10.protocol;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import gnu.io.*;
import edu.bu.easyx10.device.X10Appliance;
import edu.bu.easyx10.event.*;
import edu.bu.easyx10.event.X10Event.*;
import edu.bu.easyx10.util.LoggingUtilities;
import edu.bu.easyx10.util.LoggingUtilities;

/**
 * This class implements the controller interface for the CM11A X10 controller.
 * Input messages come in via the processProtocolEvent( ) method, which is an
 * implementation of the EventHandlerListener interface.  Output messages are
 * provided to the EventGenerator:fireEvent( ) method.  During construction,
 * we register with the EventGenerator to listen for inbound events.
 *
 * @author  Jim Duda
 * @version please refer to subversion
 * @date:   11/04/08
 */
public class CM11A_X10Protocol extends Protocol implements Runnable, SerialPortEventListener {

	// Define some X10 Protocol Constants
	private static final byte X10_STANDARD_HEADER_ADDRESS  = (byte)0x04;
//	private static final byte X10_EXTENDED_HEADER_ADDRESS  = (byte)0x05;
	private static final byte X10_STANDARD_HEADER_FUNCTION = (byte)0x06;
//	private static final byte X10_EXTENDED_HEADER_FUNCTION = (byte)0x07;

	private static final int  X10_MAX_RETRIES     = 20;

	private static final byte X10_INTERFACE_OKAY  = (byte)0x00;
	private static final byte X10_INTERFACE_READY = (byte)0x55;
	private static final byte X10_STATUS_POLL     = (byte)0x5a;
	private static final byte X10_STATUS_ACK      = (byte)0xc3;
	private static final byte X10_POWER_FAIL_POLL = (byte)0xa5;
	private static final byte X10_POWER_FAIL_ACK  = (byte)0x9b;

	// Private Member Classes and Variables
	private SerialPort          m_serialPort;
	private InputStream         m_inputStream;
	private OutputStream        m_outputStream;
	private BlockingQueue<Byte> m_rxTxQueue;
	private Thread              m_txThread;

	/**
	 * Convert X10Event House Code enumeration to X10 binary code
	 *
	 * @param  X10_HOUSE_CODE houseCode
	 * @return byte
	 */
	private static byte HouseCodeToBinary ( X10_HOUSE_CODE houseCode )  throws IllegalArgumentException {
		byte binary = 0;
		switch (houseCode) {
		case X10_A: binary = 0x6; break;
		case X10_B: binary = 0xe; break;
		case X10_C: binary = 0x2; break;
		case X10_D: binary = 0xa; break;
		case X10_E: binary = 0x1; break;
		case X10_F: binary = 0x9; break;
		case X10_G: binary = 0x5; break;
		case X10_H: binary = 0xd; break;
		case X10_I: binary = 0x7; break;
		case X10_J: binary = 0xf; break;
		case X10_K: binary = 0x3; break;
		case X10_L: binary = 0xb; break;
		case X10_M: binary = 0x0; break;
		case X10_N: binary = 0x8; break;
		case X10_O: binary = 0x4; break;
		case X10_P: binary = 0xc; break;
		default: throw new IllegalArgumentException ( "Invalid houseCode: " + houseCode ) ;
		}
		return binary;
	}

	/**
	 * Convert X10 binary code to X10Event House Code enumeration
	 *
	 * @param  byte binary
	 * @return X10_HOUSE_CODE
	 */
	private static X10_HOUSE_CODE BinaryToHouseCode ( byte binary )  throws IllegalArgumentException {
		X10_HOUSE_CODE houseCode;
		switch (binary) {
		case 0x6: houseCode = X10_HOUSE_CODE.X10_A; break;
		case 0xe: houseCode = X10_HOUSE_CODE.X10_B; break;
		case 0x2: houseCode = X10_HOUSE_CODE.X10_C; break;
		case 0xa: houseCode = X10_HOUSE_CODE.X10_D; break;
		case 0x1: houseCode = X10_HOUSE_CODE.X10_E; break;
		case 0x9: houseCode = X10_HOUSE_CODE.X10_F; break;
		case 0x5: houseCode = X10_HOUSE_CODE.X10_G; break;
		case 0xd: houseCode = X10_HOUSE_CODE.X10_H; break;
		case 0x7: houseCode = X10_HOUSE_CODE.X10_I; break;
		case 0xf: houseCode = X10_HOUSE_CODE.X10_J; break;
		case 0x3: houseCode = X10_HOUSE_CODE.X10_K; break;
		case 0xb: houseCode = X10_HOUSE_CODE.X10_L; break;
		case 0x0: houseCode = X10_HOUSE_CODE.X10_M; break;
		case 0x8: houseCode = X10_HOUSE_CODE.X10_N; break;
		case 0x4: houseCode = X10_HOUSE_CODE.X10_O; break;
		case 0xc: houseCode = X10_HOUSE_CODE.X10_P; break;
		default: throw new IllegalArgumentException ( "Invalid houseCode: " + binary ) ;
		}
		return houseCode;
	}

	/**
	 * Convert X10Event Device Code to enumeration to X10 binary code
	 *
	 * @param  X10_DEVICE_CODE deviceCode
	 * @return byte
	 */
	private byte DeviceCodeToBinary ( X10_DEVICE_CODE deviceCode )  throws IllegalArgumentException {
		byte binary = 0;
		switch (deviceCode) {
		case X10_1:  binary = 0x6; break;
		case X10_2:  binary = 0xe; break;
		case X10_3:  binary = 0x2; break;
		case X10_4:  binary = 0xa; break;
		case X10_5:  binary = 0x1; break;
		case X10_6:  binary = 0x9; break;
		case X10_7:  binary = 0x5; break;
		case X10_8:  binary = 0xd; break;
		case X10_9:  binary = 0x7; break;
		case X10_10: binary = 0xf; break;
		case X10_11: binary = 0x3; break;
		case X10_12: binary = 0xb; break;
		case X10_13: binary = 0x0; break;
		case X10_14: binary = 0x8; break;
		case X10_15: binary = 0x4; break;
		case X10_16: binary = 0xc; break;
		default: throw new IllegalArgumentException ( "Invalid deviceCode: " + deviceCode ) ;
		}
		return binary;
	}

	/**
	 * Convert X10 binary code to X10Event Device Code enumeration
	 *
	 * @param  byte binary
	 * @return X10_DEVICE_CODE
	 */
	private X10_DEVICE_CODE BinaryToDeviceCode ( byte binary )  throws IllegalArgumentException {
		X10_DEVICE_CODE deviceCode;
		switch (binary) {
		case 0x6: deviceCode = X10_DEVICE_CODE.X10_1; break;
		case 0xe: deviceCode = X10_DEVICE_CODE.X10_2; break;
		case 0x2: deviceCode = X10_DEVICE_CODE.X10_3; break;
		case 0xa: deviceCode = X10_DEVICE_CODE.X10_4; break;
		case 0x1: deviceCode = X10_DEVICE_CODE.X10_5; break;
		case 0x9: deviceCode = X10_DEVICE_CODE.X10_6; break;
		case 0x5: deviceCode = X10_DEVICE_CODE.X10_7; break;
		case 0xd: deviceCode = X10_DEVICE_CODE.X10_8; break;
		case 0x7: deviceCode = X10_DEVICE_CODE.X10_9; break;
		case 0xf: deviceCode = X10_DEVICE_CODE.X10_10; break;
		case 0x3: deviceCode = X10_DEVICE_CODE.X10_11; break;
		case 0xb: deviceCode = X10_DEVICE_CODE.X10_12; break;
		case 0x0: deviceCode = X10_DEVICE_CODE.X10_13; break;
		case 0x8: deviceCode = X10_DEVICE_CODE.X10_14; break;
		case 0x4: deviceCode = X10_DEVICE_CODE.X10_15; break;
		case 0xc: deviceCode = X10_DEVICE_CODE.X10_16; break;
		default: throw new IllegalArgumentException ( "Invalid deviceCode: " + binary ) ;
		}
		return deviceCode;
	}

	/**
	 * Convert X10Event Event Code to enumeration to X10 binary code
	 *
	 * @param  X10_EVENT_CODE eventCode
	 * @return byte
	 */
	private byte EventCodeToBinary ( X10_EVENT_CODE eventCode )  throws IllegalArgumentException {
		byte binary = 0;
		switch (eventCode) {
		case X10_ALL_UNITS_OFF:          binary = 0x0; break;
		case X10_ALL_LIGHTS_ON:          binary = 0x1; break;
		case X10_ON:                     binary = 0x2; break;
		case X10_OFF:                    binary = 0x3; break;
		case X10_DIM:                    binary = 0x4; break;
		case X10_BRIGHT:                 binary = 0x5; break;
		case X10_ALL_LIGHTS_OFF:         binary = 0x6; break;
		case X10_EXTENDED_CODE:          binary = 0x7; break;
		case X10_HAIL_REQUEST:           binary = 0x8; break;
		case X10_HAIL_ACKNOWLEDGE:       binary = 0x9; break;
		case X10_PRESET_DIM_1:           binary = 0xa; break;
		case X10_PRESET_DIM_2:           binary = 0xb; break;
		case X10_EXTENDED_DATA_TRANSFER: binary = 0xc; break;
		case X10_STATUS_ON:              binary = 0xd; break;
		case X10_STATUS_OFF:             binary = 0xe; break;
		case X10_STATUS_REQUEST:         binary = 0xf; break;
		default: throw new IllegalArgumentException ( "Invalid eventCode: " + eventCode ) ;
		}
		return (binary);
	}

	/**
	 * Convert X10 binary code to X10Event Event Code enumeration
	 *
	 * @param  byte binary
	 * @return X10_EVENT_CODE
	 */
	private X10_EVENT_CODE BinaryToEventCode ( byte binary ) throws IllegalArgumentException  {
		X10_EVENT_CODE eventCode;
		switch (binary) {
		case 0x0: eventCode = X10_EVENT_CODE.X10_ALL_UNITS_OFF         ; break;
		case 0x1: eventCode = X10_EVENT_CODE.X10_ALL_LIGHTS_ON         ; break;
		case 0x2: eventCode = X10_EVENT_CODE.X10_ON                    ; break;
		case 0x3: eventCode = X10_EVENT_CODE.X10_OFF                   ; break;
		case 0x4: eventCode = X10_EVENT_CODE.X10_DIM                   ; break;
		case 0x5: eventCode = X10_EVENT_CODE.X10_BRIGHT                ; break;
		case 0x6: eventCode = X10_EVENT_CODE.X10_ALL_LIGHTS_OFF        ; break;
		case 0x7: eventCode = X10_EVENT_CODE.X10_EXTENDED_CODE         ; break;
		case 0x8: eventCode = X10_EVENT_CODE.X10_HAIL_REQUEST          ; break;
		case 0x9: eventCode = X10_EVENT_CODE.X10_HAIL_ACKNOWLEDGE      ; break;
		case 0xa: eventCode = X10_EVENT_CODE.X10_PRESET_DIM_1          ; break;
		case 0xb: eventCode = X10_EVENT_CODE.X10_PRESET_DIM_2          ; break;
		case 0xc: eventCode = X10_EVENT_CODE.X10_EXTENDED_DATA_TRANSFER; break;
		case 0xd: eventCode = X10_EVENT_CODE.X10_STATUS_ON             ; break;
		case 0xe: eventCode = X10_EVENT_CODE.X10_STATUS_OFF            ; break;
		case 0xf: eventCode = X10_EVENT_CODE.X10_STATUS_REQUEST        ; break;
		default: throw new IllegalArgumentException ( "Invalid eventCode: " + binary ) ;
		}
		return (eventCode);
	}

	/**
	 * Construct a new CM11A-X10Protocol object.
	 *
	 * The portName input parameter defines the physical serial port address
	 * which the CM11a is attached to on the host computer.  Attempt to open
	 * the serial port and define input and output streams.  Throw an IOException
	 * if the port cannot be opened for any reason.
	 *     *
	 * @param portName - String which identifies the serial port to use.
	 */
	public CM11A_X10Protocol (String portName) throws java.io.IOException {

		CommPortIdentifier portId;

		// Attempt to find the SerialPort identified by portName
		try {
			portId = CommPortIdentifier.getPortIdentifier(portName);
		} catch (NoSuchPortException e) {
			throw new java.io.IOException ("Invalid portName: " + portName );
		}

		// Attempt to open the SerialPort
		try {
			m_serialPort = (SerialPort) portId.open("CM11A_X10Controller", 2000);
		} catch (PortInUseException e) {
			throw new java.io.IOException ("Serial port is in use: " + portName );
		}

		// Attempt to create the m_inputStream
		try {
			m_inputStream = m_serialPort.getInputStream();
		} catch (IOException e) {
			throw new java.io.IOException ("Unable to create Input Stream: " + portName );
		}

		// Attempt to create the OutputStream
		try {
			m_outputStream = m_serialPort.getOutputStream();
		} catch (IOException e) {
			throw new java.io.IOException ("Unable to create Output Stream: " + portName );
		}

		// Attempt to create the event listener
		try {
			m_serialPort.addEventListener(this);
		} catch (TooManyListenersException e) {
			throw new java.io.IOException ("Unable to create Event Listener: " + e);
		}

		// Define notification events
		m_serialPort.notifyOnDataAvailable(true);
		m_serialPort.notifyOnFramingError(true);
		m_serialPort.notifyOnParityError(true);
		m_serialPort.notifyOnOverrunError(true);

		// Configure the serial port
		try {
			m_serialPort.setSerialPortParams(
					4800,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e) {
			throw new java.io.IOException ("Unable to establish proper serial port paramters: " + portName );
		}

		// Create the rxTxQueue
		m_rxTxQueue = new LinkedBlockingQueue<Byte>( );

		// Create a new runnable thread.
		m_txThread = new Thread(this);
		m_txThread.start( );

	}

	/**
	 * Destructor for the CM11a class.
	 *
	 * Simply shut down the serial port.
	 *
	 * @param - None
	 */
	public void finalize( )
	{
		m_serialPort.close( );
	}

	/**
	 * Implementation of the close method.
	 *
	 * Simply shut down the serial port.
	 *
	 * @param - none
	 */
	public void close()
	{
		m_serialPort.close( );
	}

	/**
	 * Implementation of the processProtocolEvent method.  processProtocolEvent accepts an
	 * X10ProtocolEvent object and sends the X10 command through the CM11 controller.
	 *
	 * @param protocolEvent - X10ProtocolEvent class which provides House/Device/Function codes.
	 */
	public void processProtocolEvent ( X10ProtocolEvent protocolEvent ) {
		// Copy the object and stuff the object into the txQueue
		debug ("processProtocolEvent:: " + protocolEvent );
		if (protocolEvent instanceof X10ProtocolEvent) {
			try {
				m_txQueue.put ( (X10ProtocolEvent)protocolEvent );
			} catch ( InterruptedException e) {
				debug ( e.toString( ) );
			}
		}
	}

	/**
	 * Implementation of the debug method.  This method receives a string and
	 * uses the system DEBUG property to determine if the Message should be
	 * dumped to the console.
	 *
	 * @param - string Message - debug string to be displayed
	 *
	 * @returns - void
	 */
	private void debug ( String Message ) {
		if (System.getProperty("DEBUG") != null) {
			LoggingUtilities.logInfo(this.getClass( ).getCanonicalName(), "Debug",
			 this.getClass().getName( ) + ":: " + Message );
		}
	}

	/**
	 * Implementation of the sendDeviceAddress method.  This method is responsible
	 * for sending the Device Code for a message  to the CM11a controller.  After
	 * sending the DeviceAddress, we read the Checksum response from the CM11a.
	 *
	 * @param   - byte houseCode, byte deviceCode - address to send
	 * @returns - void
	 * @throws  - IOException
	 */
	private void sendDeviceAddress ( byte houseCode, byte deviceCode ) throws IOException {

		byte header, checksum, readByte;
		int  code;

		debug ("sendDeviceAddress:: houseCode: " + Integer.toHexString(houseCode)
				+ "deviceCode " + Integer.toHexString(deviceCode));

		// Send Header:Code (Address)
		header = X10_STANDARD_HEADER_ADDRESS;
		code   = (houseCode << 4) | deviceCode;
		try {
			m_outputStream.write ( header );
			m_outputStream.write ( (byte)code );
		} catch (Exception e) {
			throw new IOException ( "sendDeviceAddress write:: " + e );
		};

		// Fetch checksum and validate
		try {
			readByte = m_rxTxQueue.take();
		} catch (Exception e) {
			throw new IOException ( "sendDeviceAddress readByte:: " + e );
		};
		checksum = (byte)((header + code) & 0xff);
		if (readByte != checksum) {
			throw new IOException ( "sendDeviceAddress checksum failure:: " + Integer.toHexString(readByte) + " expected: " + Integer.toHexString(checksum) );
		}
	}

	/**
	 * Implementation of the sendOkay method.  This method sends the X10_OKAY to
	 * the CM11a controller and then waits for the X10_READY response.
	 *
	 * @param   - void
	 * @returns - void
	 * @throws  - IOException
	 */
	private void sendOkay ( )  throws IOException {

		byte readByte;

		debug ("sendOkay:: ");

		try {
			m_outputStream.write ( X10_INTERFACE_OKAY );
		} catch (Exception e) {
			throw new IOException ( "sendOkay write:: " + e );
		};

		// Fetch READY
		try {
			readByte = m_rxTxQueue.take( );
		} catch (Exception e) {
			throw new IOException ( "sendOkay read:: " + e );
		};
		if (readByte != X10_INTERFACE_READY) {
			throw new IOException ( "sendOkay bad value:: " + Integer.toHexString(readByte) + " expected: " + Integer.toHexString ( X10_INTERFACE_READY) );
		}
	}

	/**
	 * Implementation of the sendFunction method.  This method is responsible
	 * for sending the Function Code for a message  to the CM11a controller.  After
	 * sending the DeviceAddress, we read the Checksum response from the CM11a.
	 *
	 * @param   - byte houseCode, byte eventCode - function to send
	 * @returns - void
	 * @throws  - IOException
	 */
	private void sendFunction ( byte houseCode, byte eventCode )  throws IOException {

		byte header, checksum, readByte;
		int  code;

		debug ("sendFunction:: houseCode: " + Integer.toHexString(houseCode)
				+ "deviceCode " + Integer.toHexString(eventCode));

		header = X10_STANDARD_HEADER_FUNCTION;
		code   = (houseCode << 4) | eventCode;
		try {
			m_outputStream.write ( header );
			m_outputStream.write ( (byte)code );
		} catch (Exception e) {
			throw new IOException ( "sendFunction write:: " + e );
		};

		// Fetch checksum
		try {
			readByte = m_rxTxQueue.take( );
		} catch (Exception e) {
			throw new IOException ( "sendFunction read:: " + e );
		};
		checksum = (byte)((header + code) & 0xff);
		if (readByte != checksum) {
			throw new IOException ( "sendDeviceAddress checksum failure:: " + Integer.toHexString(readByte) + " expected: " + Integer.toHexString(checksum) );
		}
	}

	/**
	 * Implementation of the handlePowerFailPoll method.  This method provides the CM11a
	 * controller with the information required as a result of the POWER_FAIL_POLL request.
	 *
	 * @param  - void
	 * @return - void
	 * @throws - IOException
	 */
	private void handlePowerFailPoll ( ) throws IOException {

		// Create the response message
		byte[] powerFail = {X10_POWER_FAIL_ACK,   // time download header
				0,                    // current time (seconds)
				0,                    // current time (minutes ranging from 0 to 119)
				0,                    // current time (hours/2, ranging from 0 to 12)
				0,                    // current year day
				0,                    // day mask (SMTWTFS)
				0x67};                // house code "A", ack battery timer, moniotored status, timer purge
		try {
			m_outputStream.write (powerFail);
		} catch ( Exception e ) {
			throw new IOException ( "handlePowerFailPoll:: " + e );
		}
		// Read and discard the checksum
		try {
			m_inputStream.read( );
		} catch ( Exception e ) {
			throw new IOException ( "handlePowerFailPoll:: read: " + e );
		}
	}


	/**
	 * Implementation of the handleStatus method.  This method provides the CM11a
	 * controller with the information required as a result of the POWER_FAIL_POLL request.
	 *
	 * @param  - void
	 * @return - void
	 * @throws - IOException
	 */
	private void handleStatusPoll ( ) throws IOException {

		byte responseBytes, addrFunctionMask;
		int  numBytes;
		byte readBuffer[] = new byte[10];
		Vector<Byte> addrList;

		debug ("handleStatusPoll:: ");

		// Send the STATUS ACK byte
		try {
			m_outputStream.write (X10_STATUS_ACK);
		} catch ( Exception e ) {
			throw new IOException ( "handleStatusPoll:: " + e );
		}

		// Fetch the number of Response Bytes
		try {
			responseBytes = (byte)m_inputStream.read( );
		} catch ( Exception e ) {
			throw new IOException ( "handleStatusPoll:: read: " + e );
		}
		debug ( "handleStatusPoll:: responseBytes: " + Integer.toHexString(responseBytes));

		// Fetch the address/function mask byte
		try {
			addrFunctionMask = (byte)m_inputStream.read( );
		} catch ( Exception e ) {
			throw new IOException ( "handleStatusPoll:: read: " + e );
		}
		debug ( "handleStatusPoll:: addrFunctionMask: " + Integer.toHexString(addrFunctionMask));

		// Read the remaining bytes address and function bytes.  Each read can return less than
		// the desired result.  Therefore, we need to loop until all the required bytes have been
		// received.
		numBytes = 0;
		while (numBytes < responseBytes-1) {
			try {
				numBytes += m_inputStream.read ( readBuffer, numBytes, responseBytes-1-numBytes );
			} catch ( Exception e ) {
				throw new IOException ( "handleStatusPoll:: read: " + e );
			}
		}
		debug ( "handleStatusPoll:: numBytes: " + Integer.toHexString(numBytes));

		// create a vector which contains all the address bytes
		addrList = new Vector<Byte>();
		addrList.clear( );

		// each bit mask identifies the next byte
		int indexMask = 0x1;

		for (int i = 0; i < numBytes; i++) {
			debug ( "handleStatusPoll: data: " + Integer.toHexString((int)readBuffer[i]));
			// Function code detected
			if ( (addrFunctionMask & (byte)indexMask) != 0) {
				// Loop through all the gathered addresses
				for (int j = 0; j < addrList.size( ); j++) {
					debug ("handleStatusPoll:: processAddresses: " + Integer.toHexString(j));
					byte addr = addrList.get(j);
					addr &= 0xff;
					debug ( "handleStatusPoll:: processAddresses: addr: " + Integer.toHexString(addr));
					X10_HOUSE_CODE houseCode = BinaryToHouseCode ( (byte)(addr>>4));
					X10_DEVICE_CODE deviceCode = BinaryToDeviceCode ((byte)(addr & 0xf));
					X10_EVENT_CODE eventCode = BinaryToEventCode ((byte)(readBuffer[i] & 0x0f));
					debug ( "handleStatusPoll:: processAddresses: command: "
							+ houseCode + " "
							+ deviceCode + " "
							+ eventCode);
					try {
						X10DeviceEvent deviceEvent = new X10DeviceEvent ( "", houseCode, deviceCode, eventCode );
						// Send the deviceEvent to the recipient
						debug ("handleStatusPoll:: fire: " + deviceEvent );
						m_eventGenerator.fireEvent ( deviceEvent );
					} catch ( Exception e ) {
						debug ( "handleStatusPoll:: processAddresses: X10ProtocolEvent exception: " + e);
					}
				}
				// We're done with the address list, clear it for next time around
				addrList.clear( );
			}
			// New address, just add it to the vector
			else {
				debug ("handleStatusPoll:: newAddresses: " + Integer.toHexString(readBuffer[i]));
				addrList.add ( readBuffer[i] );
			}
			indexMask <<= 1;
		}
	}

	/**
	 * Implementation of the run method.  This method provide the main run loop for
	 * the CM11a object.  This method is responsible for running the transmitter thread
	 * for the controller.  This thread waits for X10ProtocolEvent(s) to be available in the
	 * txQueue.  When available, the thread reads the X10ProtocolEvent from the queue and
	 * transmits the message to the CM11a controller.
	 *
	 * @param  - None
	 * @return - void
	 */
	public void run() {

		X10ProtocolEvent protocolEvent;
		X10_HOUSE_CODE houseCode;
		X10_DEVICE_CODE deviceCode;
		X10_EVENT_CODE eventCode;
		byte houseCodeBinary, deviceCodeBinary, eventCodeBinary;

		// Check the txQueue
		while (true) {

			// get the next command to send from the queue
			try {
				protocolEvent = m_txQueue.take( );
				debug ("run:: processing: " + protocolEvent );
			} catch (Exception e) {
				break;
			}

			// Convert the House/Device/Function codes from enumerations
			// to binary for transmission to the CM11a controller.
			houseCode = protocolEvent.getHouseCode( );
			deviceCode = protocolEvent.getDeviceCode( );
			eventCode = protocolEvent.getEventCode( );

			// Validate the codes before using
			try {
				houseCodeBinary = HouseCodeToBinary ( houseCode );
				deviceCodeBinary = DeviceCodeToBinary ( deviceCode );
				eventCodeBinary = EventCodeToBinary ( eventCode );
			} catch ( Exception e ) {
				continue;
			}

			// Run the CM11a transmission protocol to pass the House/Device/Function
			// codes to the CM11a controller.
			int failures = 0;
			while (true) {

				try {
					// Send Header:Code (Address)
					sendDeviceAddress ( houseCodeBinary, deviceCodeBinary );

					// Send OKAY
					sendOkay ( );

					// Send Header:Code (Function)
					sendFunction ( houseCodeBinary, eventCodeBinary );

					// Send OKAY
					sendOkay ( );
				} catch ( Exception e ) {
					debug ( "run:: " + protocolEvent + " EXCEPTION: " + e );
					failures++;
					if (failures >= X10_MAX_RETRIES) {
						debug ( "run:: " + protocolEvent + "FAILED!!");
						break;
					}
					continue;
				}
				debug ( "run:: " + protocolEvent + " SUCCESS!!");
				break;
			}
		}
	}

	/**
	 * Implementation of the serialEvent method.  This method is called whenever
	 * there are bytes available in the serial port receive buffer.  This thread
	 * handles the receive side of the interface.  Specific bytes are looked for
	 * from the interface and handled as special codes.  If the byte is not one
	 * of the special codes, its assumed to be needed by the Send thread, therefore
	 * the byte is stuffed into the rxTxQueue to be made available to the other
	 * running Thread.
	 *
	 * @param event
	 * @return void
	 */
	public void serialEvent(SerialPortEvent event) {

		int readByte = 0;

		switch (event.getEventType()) {
		case SerialPortEvent.OE:
			debug ("serialEvent:: overrunError");
			break;

		case SerialPortEvent.FE:
			debug ("serialEvent:: framingError");
			break;

		case SerialPortEvent.PE:
			debug ("serialEvent:: parityError");
			break;

		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.BI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			debug ("serialEvent:: otherError");
			break;

		case SerialPortEvent.DATA_AVAILABLE:
			try {
				while (m_inputStream.available() > 0) {
					// Fetch the next byte from the I/O stream
					try {
						readByte = m_inputStream.read( );
					} catch (Exception e) {}
					// -1 indicates no data is available, other it's the read value
					if (readByte >= 0) {

						// Check for specific read values
						switch ((byte)readByte) {

						// Handle the status poll command
						case X10_STATUS_POLL:
							debug ( "m_serialPortEvent:: X10_STATUS_POLL");
							handleStatusPoll ( );
							break;

							// Handle the power fail poll command
						case X10_POWER_FAIL_POLL:
							debug ( "m_serialPortEvent:: X10_POWER_FAIL_POLL");
							handlePowerFailPoll ( );
							break;

							// Dump the unknown bytes into the rxTxQueue for the Send process
						default:
							debug ( "m_serialPortEvent:: forwarding received byte to transmit: " + Integer.toHexString( readByte ) );
						m_rxTxQueue.put ( (byte)readByte );
						break;
						}
					}
				}
			} catch (Exception e) {}
			break;
		}
	}
}
