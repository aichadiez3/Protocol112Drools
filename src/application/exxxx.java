package application;

import jdbc.SQLManager;

public class exxxx{
	public static void main(String[] args) {
		SQLManager manager = new SQLManager();
		
		System.out.println(manager.getProtocol_list().toString());
		
	}
}
