import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;



//import java.io.*;
//import edu.bu.easyx10.device.*;
import edu.bu.easyx10.event.*;
import edu.bu.easyx10.device.timer.*;
import java.util.Calendar;
//import java.util.Timer;

public class TestTimerPackage {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// Instantiate the Test Controller
		new TestTimerPackage( );

		//********************************************
		// Prompt to enter a command
		//********************************************

		System.out.println("Type a command to send to a device or type exit to quit");
		System.out.print("SEND>");

		BufferedReader keyboardInput;
		keyboardInput = new BufferedReader(new InputStreamReader(System.in));
		String command = "";
	
		Calendar currentTime = Calendar.getInstance();
		String DATE_FORMAT_NOW = "H:mm:ss:SSS";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        System.out.println("The current time is " + sdf.format(currentTime.getTime()));
		
		
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR, 2);
	    date.set(Calendar.MINUTE, 15);
		TimerEvent timerEvent = new TimerEvent("TestTimer","ON");
		TriggerTimer testTimer = new TriggerTimer(timerEvent,date);



		try {
			while(!command.equalsIgnoreCase("exit")) {
				command = keyboardInput.readLine();
				if(!command.equalsIgnoreCase("exit")) {
					//********************************************
					//Send the users Message
					//********************************************

					if (testTimer == null) {
						System.out.println("    Unrecognized command: " + command);

					} else {
						
						if(command.equalsIgnoreCase("go")){
							System.out.println("Starting timer");
							testTimer.startTimer();
						}

						//********************************************
						//Tell the user their command was queued
						//********************************************
						System.out.println("Your command was delivered to CM11a");
					}
					System.out.print("\nSEND>");
				}
			}
		} catch ( Exception e ) {};
		
	}
		

}
