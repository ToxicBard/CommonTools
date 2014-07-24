package commonTools;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;


public class Timer {
	private DateTime mStartTime = null;
	private DateTime mEndTime = null;
	
	/*
	 * Clear any previous values and set the
	 * start time to the current time
	 */
	public void start(){
		this.reset();
		mStartTime = new DateTime();
	}
	
	public void stop(){
		if(mStartTime != null){
			mEndTime = new DateTime();
		}
	}
	
	public void reset(){
		mStartTime = null;
		mEndTime = null;
	}
	
	public Period getElapsedInterval(){
		//If the timer hasn't been run and stopped, then there's nothing to tell
		if(mStartTime == null || mEndTime == null){
			return null;
		}
		
		return new Period(mStartTime, mEndTime);
	}
	
	public String getElapsedIntervalString(){
		Period elapsedInterval = this.getElapsedInterval();
		
		return PeriodFormat.getDefault().print(elapsedInterval);
	}

}
