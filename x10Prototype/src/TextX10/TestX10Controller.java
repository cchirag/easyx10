package TextX10;

import java.io.*;

/**
 Application which exercises the FireCracker library, as well as providing
 a command-line interface to the FireCracker interface.
 */
public class TestX10Controller implements Runnable {

    private Thread ReceiveThread;
    private static CM11a  Controller;

    /** 
     * Constructor
     */
    TestX10Controller ( String portName ) {

  //  	CM11a Controller = null;

        try {
            System.out.println("Connecting to CM11A on port " + portName);
            Controller = new CM11a(portName);
        } catch (IOException e) {
            //  listPorts( );
            System.exit(1);
        }

        // Create the worker thread
        ReceiveThread = new Thread(this);
        ReceiveThread.start();
    }

    public static void main(String args[]) throws IOException {

        String portName = "/dev/ttyUSB0";
        if (args.length > 0) {
            portName = args[0];
        }

        // Instantiate the Test Controller
        TestX10Controller Test = new TestX10Controller(portName );
        
        //********************************************
        // Prompt to enter a command
        //********************************************

        System.out.println("Type a command to send to a device or type exit to quit");
        System.out.print("SEND>");

        BufferedReader keyboardInput;
        keyboardInput = new BufferedReader(new InputStreamReader(System.in));
        String command = "";
        X10Command xcmd = null;

        try {
            while(!command.equalsIgnoreCase("exit")) {
                command = keyboardInput.readLine();
                if(!command.equalsIgnoreCase("exit")) {
                    //********************************************
                    //Send the users Message
                    //********************************************
                    xcmd = decode(command);
                    if (xcmd == null) {
                        System.out.println("    Unrecognized command: " + command);
                        usage( );
                    } else {
                        System.out.println("    " + xcmd);
                        if (Controller != null) {
                            Controller.send(xcmd);
                        }
                        //********************************************
                        //Tell the user their command was queued
                        //********************************************
                        System.out.println("Your command was delivered to CM11a");
                    }
                    System.out.print("\nSEND>");
                }
            }
        } finally {
            System.out.println("\nDisconnecting from CM11A");
            Controller.close( );
        }
    }

    public void run() {

        X10Command receiveCmd;

        // Check the receiveQueue
        while (true) {
            // get the next command to send from the queue
            try {
                receiveCmd = Controller.receive( true );
                System.out.println ( "Message Received:: " + receiveCmd );
            } catch (Exception e) {}
        }
    }

    private static X10Command decode(String str) {
        str = str.toUpperCase();
        int index;
        char house;
        String sunit;
        byte unit;
        String command;
        try {
            if ((index = str.indexOf("_ON")) != -1) {
                house = str.charAt(0);
                sunit = str.substring(1,index);
                unit = (byte)Integer.parseInt(sunit);
                command = str.substring(index+1);
                return new X10Command ( house, unit, command );
            }
            if ((index = str.indexOf("_OFF")) != -1) {
                System.out.println("here");
                house = str.charAt(0);
                sunit = str.substring(1,index);
                unit = (byte)Integer.parseInt(sunit);
                command = str.substring(index+1);
                return new X10Command ( house, unit, command);
            }
            if ((index = str.indexOf("_BRIGHT")) != -1) {
                house = str.charAt(0);
                sunit = str.substring(1,index);
                unit = (byte)Integer.parseInt(sunit);
                command = str.substring(index+1);
                return new X10Command ( house, unit, command);
            }
            if ((index = str.indexOf("_DIM")) != -1) {
                house = str.charAt(0);
                sunit = str.substring(1,index);
                unit = (byte)Integer.parseInt(sunit);
                command = str.substring(index+1);
                return new X10Command ( house, unit, command);
            }
            return null;
        } catch ( Exception e ){
            return null;
        }
    }

    private static void usage() {
        System.out.println("usage: java " + TestX10Controller.class.getName());
        System.out.println("    command examples: A1_ON B15_OFF A9_DIM B10_BRIGHT");
    }

    /*
    private static void listPorts() {
        System.out.println("Listing available ports (possibly including fictional ones):");
        Enumeration e = FireCracker.getPorts();
        while(e.hasMoreElements()) {
            System.out.println("   " + e.nextElement());
        }
        System.out.println();
    }
    */

}
