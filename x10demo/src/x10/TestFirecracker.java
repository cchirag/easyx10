package x10;


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
//package x10;
import java.util.Enumeration;
import x10.FireCracker;
import x10.X10Command;
import java.io.*;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;

/**
 Application which exercises the FireCracker library, as well as providing
 a command-line interface to the FireCracker interface.
 */
public class TestFirecracker
{

	public static void main(String args[]) throws IOException
	{

			FireCracker fc = new FireCracker();
			String port = "COM1";
			try 
			{
				System.out.println("Connecting to FireCracker on port " + port);
				fc.openPort(port);
			} catch (PortInUseException piux) 
			{
				System.out.println("Specified port in use by application: " + 
									piux.currentOwner);
				return;
			} catch (NoSuchPortException nspx) 
			{	
				System.out.println("Specified port not recognized: " + port + "\n");
				usage();
			}
			
			// Give the device time to power up?
			// It's not very reliable without this delay.
			delay(500);

			System.out.println("\nSending X10 commands");

				
					
			    //********************************************		  
				//Prompt to enter a command
				//********************************************
	 
				  System.out.println("Type a command to send to a device or type exit to quit");
				  System.out.print("SEND>");

	
				  BufferedReader keyboardInput;
				  keyboardInput = new BufferedReader(new InputStreamReader(System.in));
				  String command = "";
	  
				  while(!command.equalsIgnoreCase("exit"))
				  {
	  
	  					command = keyboardInput.readLine();

						if(!command.equalsIgnoreCase("exit"))
						{
								
							//********************************************
							//Send the users Message
							//********************************************
				
								X10Command xcmd = decode(command);
								if (xcmd == null) 
								{
									System.out.println("    Unrecognized command: " + command);
								} 
								else 
								{
									System.out.println("    " + xcmd);
									fc.sendCommand(xcmd);
								}
								delay(500);	
								
				
							//********************************************	
							//Tell the user their command was sent
							//********************************************
									
							  		System.out.println("Your command was sent");
						}//end if
						  	
						  	System.out.print("\nSEND>");
						  
      			}//end while
      			
				System.out.println("\nDisconnecting from FireCracker");

				fc.closePort();		

	}
	
	private static void
	delay(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ix) {}
	}
	
	private static X10Command
	decode(String str) 
	{
		try {
			str = str.toUpperCase();
		
			char house = str.charAt(0);
			
			if (str.indexOf("_BRIGHT") == 1) 
			{
				return X10Command.makeBrightCommand(house);
			} 
			else if (str.indexOf("_DIM") == 1) 
			{
				return X10Command.makeDimCommand(house);
			} 
			else if (str.endsWith("_ON")) 
			{
				int unit = Integer.parseInt(str.substring(1, str.indexOf('_')));
				return X10Command.makeOnCommand(house, unit);
			} 
			else if (str.endsWith("_OFF")) 
			{
				int unit = Integer.parseInt(str.substring(1, str.indexOf('_')));
				return X10Command.makeOffCommand(house, unit);
			} 
			else if (str.length() == 2) 
			{
				if (str.charAt(1) == 'B') 
				{
					return X10Command.makeBrightCommand(house);
				} 
				else if (str.charAt(1) == 'D') 
				{
					return X10Command.makeDimCommand(house);
				} 
				else 
				{
					return null;
				}
			} 
			else if (str.charAt(str.length()-1) == 'N') 
			{
				int unit = Integer.parseInt(str.substring(1, str.length()-1));
				return X10Command.makeOnCommand(house, unit);
			} 
			else if (str.charAt(str.length()-1) == 'F') 
			{
				int unit = Integer.parseInt(str.substring(1, str.length()-1));
				return X10Command.makeOffCommand(house, unit);
			} 
			else 
			{
				return null;
			}

		} catch (Exception x) {
			return null;
		}
	}

	private static void
	usage() {
		System.out.println("usage: java " + TestFirecracker.class.getName() + 
						" [-l] [port_name cmd*]");
		System.out.println("    command examples: A1_ON B15_OFF J_DIM K_BRIGHT");
		System.out.println("    or, equivalently: a1n b15f jd kb");
		System.out.println("    delay 1/2 sec. per period: ...");

		System.exit(1);
	}

	private static void
	listPorts() {
		System.out.println("Listing available ports (possibly including fictional ones):");

		Enumeration e = FireCracker.getPorts();
		while(e.hasMoreElements()) {
			System.out.println("   " + e.nextElement());
		}

		System.out.println();
	}
}
