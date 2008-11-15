package edu.bu.easyx10.device.timer;

import edu.bu.easyx10.event.*;

public class WaitTimer extends DeviceTimer{

	// private member variables
	int mSecondsToWait;
	
	public WaitTimer(int secondsToWait, Event eventToFire){
		super(eventToFire);
		setSecondsToWait(secondsToWait);
		
		startTimer();
	}
		
	public int getSecondsToWait() {
		return mSecondsToWait;
	}


	public void setSecondsToWait(int secondsToWait) {
		
		//set the number of seconds to wait
		mSecondsToWait = secondsToWait;
		
		//Restart the Timer
		startTimer();
		
	}

	@Override
	public Event getEventToFire() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEventToFire(Event eventToFire) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void startTimer() {
		// TODO Auto-generated method stub
		
	}

}
