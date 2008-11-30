import java.io.BufferedReader;
import java.io.InputStreamReader;





import edu.bu.easyx10.event.*;
import edu.bu.easyx10.device.timer.*;
import java.util.Calendar;


/**
 * The purpose of this package is to test TriggerTimer Events. It instantiates a 
 * Calendar date and then passes it into the TriggerTimer.
 * 
 * @author dgabriel
 * @date 11/30/2008
 */

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

		System.out.println("Enter a time to sent a timer event");
		System.out.print("SEND>");

		BufferedReader keyboardInput;
		keyboardInput = new BufferedReader(new InputStreamReader(System.in));
		String command = "";
	
		//
		//Calendar currentTime = Calendar.getInstance();
		//String DATE_FORMAT_NOW = "H:mm:ss:SSS";
		//SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        //System.out.println("The current time is " + sdf.format(currentTime.getTime()));
		
		
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, 0);
	    date.set(Calendar.MINUTE, 6);
	    date.set(Calendar.SECOND, 0);
	    date.set(Calendar.MILLISECOND, 0);
	    
		TimerEvent timerEvent = new TimerEvent("TestTimer","ON");
		TriggerTimer testTimer = new TriggerTimer(timerEvent);



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
						System.out.println("Wait for response");
					}
				}
			}
		} catch ( Exception e ) {};
		
	}
		

}
