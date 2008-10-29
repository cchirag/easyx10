package TextX10;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import javax.comm.*;

/**
 * This class implements the controller interface for the CM11A X10 controller.
 * The primary interface methods are send and receive.  The interace to this
 * class uses the X10Command class  Each X10Command object provides
 * a HOUSE and UNIT code as well as the FUNCTION to be sent/received.
 * *
 * @author Jim Duda
 * @version 1.0, 09/27/08
 */
public class CM11a implements Runnable, SerialPortEventListener {

        // X10 Protocol Constants
    private static final byte X10_STANDARD_HEADER_ADDRESS  = (byte)0x04;
    private static final byte X10_EXTENDED_HEADER_ADDRESS  = (byte)0x05;
    private static final byte X10_STANDARD_HEADER_FUNCTION = (byte)0x06;
    private static final byte X10_EXTENDED_HEADER_FUNCTION = (byte)0x07;

    private static final int  X10_MAX_RETRIES     = 20;

    private static final byte X10_INTERFACE_OKAY  = (byte)0x00;
    private static final byte X10_INTERFACE_READY = (byte)0x55;
    private static final byte X10_STATUS_POLL     = (byte)0x5a;
    private static final byte X10_STATUS_ACK      = (byte)0xc3;
    private static final byte X10_POWER_FAIL_POLL = (byte)0xa5;
    private static final byte X10_POWER_FAIL_ACK  = (byte)0x9b;

    // Private Member Classes and Variables
    private SerialPort         serialPort;
    private InputStream        inputStream;
    private OutputStream       outputStream;

    private BlockingQueue<X10Command> sendQueue;
    private BlockingQueue<X10Command> receiveQueue;
    private BlockingQueue<Byte> readQueue;

    /**
     * Construct a new CM11a object.
     *
     * The portName input parameter defines the physical serial port address
     * which the CM11a is attached to on the host computer.  Attempt to open
     * the serial port and define input and output streams.  Throw an IOException
     * if the port cannot be opened for any reason.
     *     *
     * @param portName - String which identifies the serial port to use.
     */
    public CM11a(String portName) throws java.io.IOException {

        CommPortIdentifier portId;

        // Attempt to find the SerialPort identified by portName
        try {
            portId = CommPortIdentifier.getPortIdentifier(portName);
        } catch (NoSuchPortException e) {
            throw new java.io.IOException ("Invalid portName: " + portName );
        }

        // Attempt to open the SerialPort
        try {
            serialPort = (SerialPort) portId.open("CM11aApp", 2000);
        } catch (PortInUseException e) {
            throw new java.io.IOException ("Serial port is in use: " + portName );
        }

        // Attempt to create the InputStream
        try {
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {
            throw new java.io.IOException ("Unable to create Input Stream: " + portName );
        }

        // Attempt to create the OutputStream
        try {
            outputStream = serialPort.getOutputStream();
        } catch (IOException e) {
            throw new java.io.IOException ("Unable to create Output Stream: " + portName );
        }

        // Attempt to create the event listener
        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e) {
            throw new java.io.IOException ("Unable to create Event Listener: " + portName );
        }

        // Define notification events
        serialPort.notifyOnDataAvailable(true);

        // Configure the serial port
        try {
                serialPort.setSerialPortParams(4800,
                                           SerialPort.DATABITS_8,
                                           SerialPort.STOPBITS_1,
                                           SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {
            throw new java.io.IOException ("Unable to establish proper serial port paramters: " + portName );
        }

        // Create the sendQueue
        sendQueue = new LinkedBlockingQueue<X10Command>( );

        // Create the receiveQueue
        receiveQueue = new LinkedBlockingQueue<X10Command>( );

        // Create the readQueue
        readQueue = new LinkedBlockingQueue<Byte>( );

        // Enable Debugging
        System.setProperty("DEBUG", "1");
    }

    /**
     * Destructor for the CM11a class.
     *
     * Simply shut down the serial port.
     *
     * @param - None
     */
    public void finalize() throws Throwable
    {
        serialPort.close( );
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
        serialPort.close( );
    }

    /**
    * Implmentation of the send method.  Send accepts an X10Command object and
    * sends the X10 command through the CM11 controller.
    *
    * @param cmd - X10Command class which provides House/Device/Function codes.
    */
    public void send( X10Command cmd ) throws IllegalArgumentException {
        // Copy the object and stuff the object into the sendQueue
        X10Command command = cmd;
        debug ("send:: " + cmd );
        try {
           sendQueue.put ( command );
        } catch ( InterruptedException e) {}
    }

    /**
    * Implementation of the receive method.  Receive accepts an X10Command object and
    * receives the X10 function through the CM11a controller.
    *
    * @param  - block - true for a blocking call
    * @return - Reference to a new X10Command which has the received House/Device/Function.
    *           This method returns null if there isn't any new data received.
    */
    public X10Command receive( boolean block ) {

        X10Command response;

        if (block) {
            try {
                response = receiveQueue.take( );
                return response;
            } catch ( Exception e ) {}
        } else {
            try {
                response = receiveQueue.remove ( );
                return response;
            } catch ( NoSuchElementException e) {
                return null;
            }
        }
        return null;
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
            System.out.println ( this.getClass().getName( ) + ":: " + Message );
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
            outputStream.write ( header );
            outputStream.write ( (byte)code );
        } catch (Exception e) {
            throw new IOException ( "sendDeviceAddress write:: " + e );
        };

        // Fetch checksum and validate
        try {
            readByte = readQueue.take();
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
            outputStream.write ( X10_INTERFACE_OKAY );
        } catch (Exception e) {
            throw new IOException ( "sendOkay write:: " + e );
        };

        // Fetch READY
        try {
            readByte = readQueue.take( );
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
     * @param   - byte houseCode, byte functionCode - function to send
     * @returns - void
     * @throws  - IOException
     */
    private void sendFunction ( byte houseCode, byte functionCode )  throws IOException {

        byte header, checksum, readByte;
        int  code;

        debug ("sendFunction:: houseCode: " + Integer.toHexString(houseCode)
                + "deviceCode " + Integer.toHexString(functionCode));

        header = X10_STANDARD_HEADER_FUNCTION;
        code   = (houseCode << 4) | functionCode;
        try {
            outputStream.write ( header );
            outputStream.write ( (byte)code );
        } catch (Exception e) {
            throw new IOException ( "sendFunction write:: " + e );
        };

        // Fetch checksum
        try {
            readByte = readQueue.take( );
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
          outputStream.write (powerFail);
        } catch ( Exception e ) {
            throw new IOException ( "handlePowerFailPoll:: " + e );
        }
        // Read and disgard the checksum
        try {
            int readByte = (byte)inputStream.read( );
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
            outputStream.write (X10_STATUS_ACK);
        } catch ( Exception e ) {
            throw new IOException ( "handleStatusPoll:: " + e );
        }

        // Fetch the number of Response Bytes
        try {
          responseBytes = (byte)inputStream.read( );
        } catch ( Exception e ) {
            throw new IOException ( "handleStatusPoll:: read: " + e );
        }
        debug ( "handleStatusPoll:: responseBytes: " + Integer.toHexString(responseBytes));

        // Fetch the address/function mask byte
        try {
            addrFunctionMask = (byte)inputStream.read( );
        } catch ( Exception e ) {
            throw new IOException ( "handleStatusPoll:: read: " + e );
        }
        debug ( "handleStatusPoll:: addrFunctionMask: " + Integer.toHexString(addrFunctionMask));

        // Read the remaining bytes address and function bytes
        try {
            numBytes = inputStream.read ( readBuffer, 0, responseBytes-1 );
        } catch ( Exception e ) {
            throw new IOException ( "handleStatusPoll:: read: " + e );
        }

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
                    char houseCode = X10Command.BinaryToHouseCode ( (byte)(addr>>4));
                    byte deviceCode = X10Command.BinaryToDeviceCode ((byte)(addr & 0xf));
                    String functionCode = X10Command.BinaryToFunctionCode ((byte)(readBuffer[i] & 0x0f));
                    debug ( "handleStatusPoll:: processAddresses: command: "
                                        + houseCode + " "
                                        + Integer.toHexString(deviceCode) + " "
                                        + functionCode);
                    try {
                        X10Command response = new X10Command ( houseCode, deviceCode, functionCode );
                        // Put the new event into the queue
                        receiveQueue.put ( response );
                    } catch ( Exception e ) {
                        debug ( "handleStatusPoll:: processAddresses: X10Command exception: " + e);
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
     * for the controller.  This thread waits for X10Command(s) to be available in the
     * sendQueue.  When available, the thread reads the X10Command from the queue and
     * transmits the message to the CM11a controller.
     *
     * @param  - None
     * @return - void
     */
    public void run() {

        X10Command sendCmd;
        byte       houseCode, deviceCode, functionCode;


        // Check the sendQueue
        while (true) {

            // get the next command to send from the queue
            try {
                sendCmd = sendQueue.take( );
                debug ("run:: processing: " + sendCmd );
            } catch (Exception e) {
                break;
            }

            // Convert the House/Device/Function codes from enumerations
            // to binary for transmission to the CM11a controller.
            houseCode = sendCmd.getHouseCodeBinary( );
            deviceCode = sendCmd.getDeviceCodeBinary( );
            functionCode = sendCmd.getFunctionCodeBinary( );

            // Run the CM11a transmission protocol to pass the House/Device/Function
            // codes to the CM11a controller.
            int failures = 0;
            while (true) {

                try {
                    // Send Header:Code (Address)
                    sendDeviceAddress ( houseCode, deviceCode );

                    // Send OKAY
                    sendOkay ( );

                    // Send Header:Code (Function)
                    sendFunction ( houseCode, functionCode );

                    // Send OKAY
                    sendOkay ( );
                } catch ( Exception e ) {
                    debug ( "Send:: " + sendCmd + " EXCEPTION: " + e );
                    failures++;
                    if (failures >= X10_MAX_RETRIES) {
                        debug ( "Send:: " + sendCmd + "FAILED!!");
                        break;
                    }
                    continue;
                }
                debug ( "Send:: " + sendCmd + " SUCCESS!!");
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
     * the byte is stuffed into the readQueue to be made available to the other
     * running Thread.
     *
     * @param event
     * @return void
     */
    public void serialEvent(SerialPortEvent event) {

        int readByte = 0;

        switch (event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
        break;

        case SerialPortEvent.DATA_AVAILABLE:
            try {
                while (inputStream.available() > 0) {

                    // Fetch the next byte from the I/O stream
                    try {
                        readByte = inputStream.read( );
                    } catch (Exception e) {}
                    // -1 indicates no data is available, other it's the read value
                    if (readByte >= 0) {

                        // Check for specific read values
                        switch ((byte)readByte) {

                        // Handle the status poll command
                        case X10_STATUS_POLL:
                            debug ( "SerialPortEvent:: X10_STATUS_POLL");
                            handleStatusPoll ( );
                            break;

                        // Handle the power fail poll command
                        case X10_POWER_FAIL_POLL:
                            debug ( "SerialPortEvent:: X10_POWER_FAIL_POLL");
                            handlePowerFailPoll ( );
                            break;

                        // Dump the unkown bytes into the readQueue for the Send process
                        default:
                            debug ( "SerialPortEvent:: unknown received byte: " + Integer.toHexString( readByte ) );
                            readQueue.put ( (byte)readByte );
                        break;

                        }
                    }
                }
            } catch (Exception e) {}
            break;
        }
    }
}
