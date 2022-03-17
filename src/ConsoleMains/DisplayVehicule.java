package ConsoleMains;

import jdbc.SQLManager;
import pojos.*;

public class DisplayVehicule {
	private static SQLManager controller;
		
		public static void main(String[] args) {
			controller = new SQLManager();
			controller.Connect();
	        Boolean tables = controller.Create_tables();
	        
	        if(tables==true) {
	        	controller.Insert_default_elements_toDB();
	        }
	        
	        Emergency emergency = new Emergency();
			Integer emergency_id = controller.Insert_new_emergency(emergency.getCode(), emergency.getDate());
			emergency.setId(emergency_id);
	        System.out.println(emergency.toString());
	        
	        Location w = new Location(0,"Mountain");
	        Location out = controller.Search_vehicle_by_place_type(w.getPlace());
	        
	        System.out.println("loc_id: "+out.getId()+"\nvehicule type: " + out.getVehicle().toString());
	        
	        
	        System.out.println(controller.Update_location_and_vehicle("x", out.getId(), emergency_id));
	        
	        Integer loc_id = controller.Search_location_id_from_emergency(emergency_id);
	        	
		}
}
