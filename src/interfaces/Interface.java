package interfaces;

import java.util.List;

import pojos.*;

public interface Interface {

	
	public boolean Connect();
	public boolean Create_tables();
	
	public void Insert_default_elements_toDB();
	public Integer Insert_new_user(String user_name, String password, String charge);
	public Integer Insert_new_patient(String name, String surname, String gender, String age, Boolean chronic, Boolean drugs, String reference_number, Integer urgency_id);
	public Integer Insert_new_emergency(Integer code, String date);
	public Integer Insert_new_location(String type, String vehicle);
	public Integer Insert_new_speciality(String name);
	public Integer Insert_new_symptom(String name);
	public Integer Insert_new_disease(String name, Integer spe_id);
	
	public Patient Search_stored_patient_by_id(Integer patient_id);
	public Patient Search_stored_patient_by_emergency_id(Integer emergency_id);
	public Integer Search_stored_user_by_username(String username);
	public String Get_user_password (String username);
	public Location Search_vehicle_by_place_type(String place);
	public Integer Search_location_id_from_emergency(Integer urgency_id);
	public Emergency Search_stored_emergency_by_code(Integer code);
	public Integer Search_speciality_id_by_name(String name);
	public String Search_speciality_by_id(Integer id);
	public Integer Search_speciality_by_emergency_id(Integer id);
	public Integer Search_protocol_by_emergency_id(Integer id);
	public String Search_protocol_info_by_id(Integer id);
	
	public boolean Update_location_and_vehicle(String location, Integer location_id, Integer urgency_id);
	public boolean Update_emergency_info(String direction, Integer severity, Integer speciality_id, Integer location_id, Integer protocol_id, Integer emergency_id);
	
	public List<Emergency> List_all_codes();
	public List<String> List_all_symptoms();
	public List<String> List_all_specialities();
	public List<String> List_all_places();
	public List<String> List_all_symptoms_by_speciality(Integer speciality_id);
	
	
	public boolean Close_connection();
}