package interfaces;

import java.util.List;

import pojos.*;

public interface Interface {

	
	public boolean Connect();
	public boolean Create_tables();
	
	public void Insert_default_elements_toDB();
	public Integer Insert_new_user(String user_name, String password, String salt, String charge);
	public Integer Insert_new_patient(String name, String surname, String gender, String age, Boolean chronic, Boolean drugs, String reference_number, Integer urgency_id);
	public Integer Insert_new_emergency(Integer code, Integer severity, String date, String direction, Integer loc_id, Integer spe_id, Integer dis_id, Integer prot_id) ;
	public Integer Insert_new_location(String type, String vehicle);
	public Integer Insert_new_specialty(String name);
	public Disease Insert_new_disease(String name, Integer spe_id);
	public Integer Insert_new_symptom(String name);
	public Boolean Associate_symptom_to_specialty(Integer symp_id, Integer spe_id);
	public Integer Insert_new_protocol(String info, String type);
	
	public Patient Search_stored_patient_by_id(Integer patient_id);
	public Patient Search_stored_patient_by_emergency_id(Integer emergency_id);
	public Integer Search_stored_user_by_username(String username);
	public List<String> Get_user_password (String username);
	public Location Search_vehicle_by_place_type(String place);
	public Location Search_location_from_emergency(Integer urgency_id);
	public Location Search_location_by_id(Integer id);
	public Emergency Search_stored_emergency_by_code(Integer code);
	public Integer Search_specialty_id_by_name(String name);
	public Specialty Search_specialty_by_name(String name);
	public Specialty Search_specialty_by_id(Integer id);
	public Specialty Search_specialty_by_emergency_id(Integer id);
	public Protocol Search_protocol_by_emergency_id(Integer id);
	public Protocol Search_protocol_by_id(Integer id);
	public Disease Search_disease_by_id(Integer id);
	public Integer Search_disease_by_emergency_id(Integer id);
	public String Search_symptom_by_id(Integer id);
	
	public Boolean Associate_symptom_list_to_disease(String symptom_list, Integer disease_id);

	
	public List<Emergency> List_all_codes();
	public List<String> List_all_specialities();
	public List<Disease> List_all_diseases_by_specialty_id(Integer spe_id);
	public List<String> List_all_symptoms_by_disease(String disease);
	public List<String> List_all_places();
	public List<String> List_all_symptoms_by_specialty_id(Integer spe_id);
	public List<Protocol> List_all_protocols();
	
	public boolean Close_connection();
}
