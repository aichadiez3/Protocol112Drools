package application;

import jdbc.SQLManager;

public class ConnectToDB implements Runnable{

	
	public ConnectToDB() {
		super();
	}
	
	@Override
	public void run() {
        
        SQLManager manager = new SQLManager();
        manager.Connect();
        Boolean tables = manager.Create_tables();
        if(tables==true) {
        	manager.Insert_default_elements_toDB();
        }
	}
}