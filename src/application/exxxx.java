package application;

import jdbc.SQLManager;
import pojos.Protocol;

public class exxxx{
	public static void main(String[] args) {
		SQLManager manager = new SQLManager();
		
		Protocol p1 = manager.Search_protocol_by_id(1);
		System.out.println(p1);
		
	}
}
