package application;


public class ThreadCreation implements Runnable{

	
	public ThreadCreation() {
		super();
	}
	
	@Override
	public void run() {
		
		while (true) {
		     new Thread(new ConnectToDB()).start();	
		     
		}
	}
}