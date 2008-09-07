// Copyright (C) 1999  Moss Prescott
// 
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program (see gpl.html); if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.

package x10;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.comm.*;

/*******************************************************************************
 Interface to X10's FireCracker(TM) Wireless Computer Interface (CM17A).
 <P>
 This implementation is based on the
 <A HREF=http://www.x10.com/manuals/cm17a_proto.txt>FireCracker (CM17A) 
 Communications Specification</A> available 
 from <A HREF=http://www.x10.com/>X10</A>.
 <P>
 Example use:
 <PRE>
 String port = "COM1";
 
 try {
 	FireCracker fc = new FireCracker();
	fc.openPort(port);
 
 	X10Command a1on = X10Command.makeOnCommand('A', 1);
	fc.sendCommand(a1on);
	
	fc.closePort();
 } catch (PortInUseException piux) {
	System.out.println("Specified port in use by application: " + 
						piux.currentOwner);
 } catch (NoSuchPortException nspx) {	
	System.out.println("Port not recognized: " + port + "\n");
 }
 </PRE>
 <P>
 Note: the device is fairly timing-sensitive. This class makes some attempt to 
 provide enough delay between commands, but you may find you need to add some 
 delays to ensure that commands aren't dropped.
 <P>
 Note further: this class is not thread-safe in any of its operations, so it 
 should only be accessed from a single thread, or interesting behavior could 
 result.

 @author Moss Prescott (moss@theprescotts.com)
 ******************************************************************************/
public class FireCracker {
// STATICS
	private static final long BIT_DELAY 	= 2;
	private static final String X10_HEADER 	= "11010101 10101010";
	private static final String X10_FOOTER 	= "10101101";
	
	private static final boolean DTR_HIGH 	= true;
	private static final boolean DTR_LOW 	= !DTR_HIGH;

	private static final boolean RTS_HIGH 	= true;
	private static final boolean RTS_LOW 	= !RTS_HIGH;

	private static int ms_cyclesPerTenth = calibrate();

// STATE
	private SerialPort	m_port;
	
// CONSTRUCTOR
	public
	FireCracker() {
	}
	
// MANIPULATORS
	/***************************************************************************
	 Make the device ready to accept commands. 
	 
	 @param portName Name of the serial port to which the device is connected.
	 
	 @exception PortInUseException if the port is already open.
	 @exception NoSuchPortException if the port name is not recognized.
	 
	 @see #getPorts
	 **************************************************************************/
	public void
	openPort(String portName) throws PortInUseException, NoSuchPortException {
		synchronized (this) {
			if (m_port != null) {
				closePort();
			}
		
			m_port = getCommPort(portName);
			
			/*	
			try {
				// Probably can't play with control lines if flow control is on...
				m_port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
			} catch (UnsupportedCommOperationException ucox) {
				System.out.println(ucox);
			}
			*/	
	
			// Make sure the device is powered:
			sendWait();			
		}
	}


	/***************************************************************************
	 Make the serial port available to other applications.
	 **************************************************************************/
	public void
	closePort() {
		synchronized (this) {
			m_port.close();
			m_port = null;
		}
	}
	
	/***************************************************************************
	 Return true if the device is ready to accept commands (openPort succeeded).
	 **************************************************************************/
	public boolean
	isOpenForBusiness() {
		return m_port != null;
	}
	
	/***************************************************************************
	 Send a stream of bytes to a serial device connected to the 
	 FireCracker's pass-through. This is useful for debugging.
	 
	 @param str "Hello, world" is a good choice, or "ATDT 1", depending on 
	 			what's on the other end of the serial cable.
	 **************************************************************************/
	public void
	sendBytes(String str) throws IOException {
		// use defaults:
		//m_port.setSerialPortParams(9600, DATABITS_8, STOPBITS_1, PARITY_NONE);
	
		OutputStream os = null;
		
		try {
			os = m_port.getOutputStream();
	
			byte[] bytes = str.getBytes();
			
			for (int i = 0; i < bytes.length; i++) {
				os.write(bytes[i]);
			}
		} finally {		
			os.close();
		}
	}
	
	/***************************************************************************
	 Send a coded command to the FireCracker device.
	 
	 @exception NullPointerException if the port is not opened.
	 **************************************************************************/
	public void
	sendCommand(X10Command command) {
		sendBits(X10_HEADER + " " + command.getBits() + " " + X10_FOOTER);
	}

	/***************************************************************************
	 An enumeration of the names of the available serial ports, 
	 which is a mapping from the enumeration of all javax.comm ports.
	 **************************************************************************/
	public static Enumeration
	getPorts() {
		return new Enumeration() {
			private Enumeration m_portIDs;
			private Object m_next;

			{
				m_portIDs = CommPortIdentifier.getPortIdentifiers();
				grabNext();
			}
			
			public boolean
			hasMoreElements() {
				return m_next != null;
			}
			
			public Object
			nextElement() {
				Object result = m_next;
				
				grabNext();
				
				return result;
			}
			
			private void
			grabNext() {
				m_next = null;
				
				while (m_portIDs.hasMoreElements()) {
					CommPortIdentifier portID = (CommPortIdentifier) m_portIDs.nextElement();
					if (portID.getPortType() == CommPortIdentifier.PORT_SERIAL) {
						m_next = portID.getName();
						break;
					}
				}
			}
		};
	}
	
// INTERNAL
	private void
	sendBits(String command) {
		synchronized (this) {
			while (command.length() > 0) {
				char c = command.charAt(0);
				
				if (c == '1') {
					sendOne();
					sendWait();
				} else if (c == '0') {
					sendZero();
					sendWait();
				}
				
				command = command.substring(1);
			}
	
			// give time to finish signaling:
			for (int i = 0; i < 100; i++) {
				waitForBitDelay();
			}
		}
	}
	
	private void
	sendReset() {
		waitForBitDelay();
		m_port.setRTS(RTS_LOW);
		m_port.setDTR(DTR_LOW);
	}

	private void
	sendOne() {
		waitForBitDelay();
		m_port.setRTS(RTS_HIGH);	// always raise first...
		m_port.setDTR(DTR_LOW);	
	}

	private void
	sendZero() {
		waitForBitDelay();
		m_port.setDTR(DTR_HIGH);	// always raise first...
		m_port.setRTS(RTS_LOW);
	}

	private void
	sendWait() {
		waitForBitDelay();
		m_port.setRTS(RTS_HIGH);
		m_port.setDTR(DTR_HIGH);	
	}

// STATIC
	// One millisecond delay.
	private static void
	waitForBitDelay() {
		for (int i = 10*ms_cyclesPerTenth; i >=0; i--)
			;
	}

	private static SerialPort
	getCommPort(String name) throws PortInUseException, NoSuchPortException {
		CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(name);
		CommPort port = portId.open("SEntrySentry", 2000);
		return (SerialPort) port;
	}

	/***************************************************************************
	 In order to exceed the resolution of java's getCurrentTimeMillis,
	 calibrate a delay loop, which should be accurate enough for our 
	 purposes.
	 **************************************************************************/
	private static int 
	calibrate() {
		for (int cycles = 1000; ; cycles *= 10) {
			long start = System.currentTimeMillis();
	
			for (int i = 0; i < cycles; i++)
				;
			
			long elapsed = System.currentTimeMillis() - start;

			if (elapsed	> 100) {
				int perMilli = (int) (cycles/elapsed);
				
				return perMilli/10;
			}
		}
	}
}