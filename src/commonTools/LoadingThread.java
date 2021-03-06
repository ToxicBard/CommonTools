package commonTools;

public class LoadingThread extends Thread {
	private boolean mKeepRunning = true;
	private boolean mStarted = false;
	private boolean mDebugMode = false;
	private int mProgressCounter = 0;
	private int mExecDelay = 250;
	
	public LoadingThread(){
		mDebugMode = CommonTools.isDebugMode();
	}
	
	public void stopRunning(){
		mKeepRunning = false;
		System.out.println("");
	}
	
	public boolean isRunning(){
		if(mStarted && mKeepRunning){
			return true;
		}
		return false;
	}
	
	public void run(){
		mStarted = true;
		
		while(mKeepRunning == true && !mDebugMode){
			this.printLoadingString();
			try {
				Thread.sleep(mExecDelay);
			} catch (InterruptedException e) {
				CommonTools.processError("Thread Interruption Error");
			}
		}
	}
	
	private void printLoadingString(){
		System.out.print(getLoadingCharacter(mProgressCounter) + "\r");
		mProgressCounter = (mProgressCounter + 1) % 4;
	}
	
	private String getLoadingCharacter(int loadingNumber){
		switch(loadingNumber){
			case 0:
				return "|";
			case 1:
				return "/";
			case 2:
				return "-";
			case 3:
				return "\\";
		}
		return "0";
	}

}
