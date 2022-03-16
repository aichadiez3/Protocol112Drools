package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import interfaces.Interface;
import pojos.*;

public class SQLManager implements Interface {

	private Connection sqlite_connection;
	
	public SQLManager() {
		super();
	}
	
	
	public boolean Connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			this.sqlite_connection = DriverManager.getConnection("jdbc:sqlite:./db/database.db");
			this.sqlite_connection.createStatement().execute("PRAGMA foreign_keys=ON");
			return true;
		} catch (ClassNotFoundException | SQLException connection_error) {
			connection_error.printStackTrace();
			return false;
		}
	}

	// Creates the tables in biomat.db file
	public boolean Create_tables() {
		try {
			Statement statement_0 = this.sqlite_connection.createStatement();
			String table_0 = " CREATE TABLE user " + "(user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			        + " username TEXT NOT NULL UNIQUE, " + " password TEXT NOT NULL, "
					+ " charge TEXT NOT NULL)";
			statement_0.execute(table_0);
			statement_0.close();
			
			Statement statement_1 = this.sqlite_connection.createStatement();
			String table_1 = " CREATE TABLE speciality " + "(speciality_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			        + " name INTEGER NOT NULL UNIQUE)";
			statement_1.execute(table_1);
			statement_1.close();
			
			Statement statement_2 = this.sqlite_connection.createStatement();
			String table_2 = " CREATE TABLE protocol " + "(protocol_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " info TEXT default NULL)";
			statement_2.execute(table_2);
			statement_2.close();
			
			Statement statement_3 = this.sqlite_connection.createStatement();
			String table_3 = " CREATE TABLE location " + "(location_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			        + " type TEXT NOT NULL, " + " vehicle ENUM ('ambulance','helicopter','boat') default NULL)";
			statement_3.execute(table_3);
			statement_3.close();
			
			Statement statement_4 = this.sqlite_connection.createStatement();
			String table_4 = " CREATE TABLE emergency " + "(emergency_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			        + " code INTEGER NOT NULL UNIQUE, " + " level INTEGER default NULL, " + "register_date TEXT NOT NULL, "
					+ " direction TEXT default NULL, "
			        + " location_id INTEGER FOREING KEY REFERENCES location(location_id), "
			        + " speciality_id INTEGER FOREING KEY REFERENCES speciality(speciality_id) ON DELETE CASCADE, "
					+ " protocol_id INTEGER FOREING KEY REFERENCES protocol(protocol_id) ON DELETE CASCADE)";
			statement_4.execute(table_4);
			statement_4.close();
			
			Statement statement_5 = this.sqlite_connection.createStatement();
			String table_5 = " CREATE TABLE patient " + "(patient_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			        + " name TEXT NOT NULL UNIQUE, " + " surname TEXT NOT NULL, " + " gender TEXT default NULL, " 
					+ " age TEXT NOT NULL, " + "chronic BIT default NULL, "
			        + " drugs BIT default NULL, " + " reference_number TEXT NOT NULL,"
			        + " emergency_id INTEGER FOREING KEY REFERENCES emergency(emergency_id) ON DELETE CASCADE)";
			statement_5.execute(table_5);
			statement_5.close();
			
			Statement statement_6 = this.sqlite_connection.createStatement();
			String table_6 = " CREATE TABLE symptom " + "(symptom_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			        + " name TEXT NOT NULL)";
			statement_6.execute(table_6);
			statement_6.close();
			
			Statement statement_7 = this.sqlite_connection.createStatement();
			String table_7 = " CREATE TABLE disease " + "(disease_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			        + " name TEXT NOT NULL," + "speciality_id INTEGER FOREING KEY REFERENCES speciality(speciality_id) ON DELETE CASCADE)";
			statement_7.execute(table_7);
			statement_7.close();
			
			Statement statement_8 = this.sqlite_connection.createStatement();
			String table_8 = "CREATE TABLE user_emergency " + " (user_id INTEGER REFERENCES user(user_id), " 
					+ "emergency_id INTEGER REFERENCES emergency(emergency_id), "
					+ "PRIMARY KEY (user_id, emergency_id))";
			statement_8.execute(table_8);
			statement_8.close();
			
			Statement statement_9 = this.sqlite_connection.createStatement();
			String table_9 = "CREATE TABLE disease_symptom " + " (disease_id INTEGER REFERENCES disease(disease_id), " 
					+ "symptom_id INTEGER REFERENCES symptom(symptom_id), "
					+ "PRIMARY KEY (disease_id, symptom_id))";
			statement_9.execute(table_9);
			statement_9.close();
			
			
			return true;
			
		} catch (SQLException tables_error) {
			tables_error.printStackTrace();
			return false;
		}
	}
	
	public void Insert_default_elements_toDB() {
		
		Insert_new_location("Ambulance", "Home");
		Insert_new_location("Ambulance", "Transit");
		Insert_new_location("Ambulance", "Workplace");
		Insert_new_location("Helicopter", "Mountain");
		Insert_new_location("Boat", "Beach");
		
		Insert_new_speciality("Oncology");
		Insert_new_speciality("Cardiology");
		Insert_new_speciality("Toxicology");
		Insert_new_speciality("Traumatology");
		Insert_new_speciality("Neurology");
		Insert_new_speciality("Other");
		
		Insert_new_protocol("Take paracetamol");
		Insert_new_protocol("Connect to oxygen supply");
		
		// ADD HERE MORE PROTOCOLS FROM THE EXCEL
		
	}
	
	
	 // -----------------------> INSERT METHODS <---------------------------

	
	public Integer Insert_new_user(String user_name, String password, String charge) {
		try {
			String table = "INSERT INTO user (username, password, charge) " + " VALUES(?,?,?);";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, user_name);
			template.setString(2, password);
			template.setString(3, charge);
			template.executeUpdate();
			
			String SQL_code = "SELECT * FROM user WHERE username = ?";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setString(1, user_name);
			ResultSet result_set = template.executeQuery();
			User user = new User();
		    user.setUserName(result_set.getString("username"));
		    user.setPassword(result_set.getString("password"));
		    user.setCharge(result_set.getString("charge"));
		    user.setUserId(result_set.getInt("user_id"));
		    template.close();
		    return user.getUserId();
		} catch (SQLException insert_user_error) {
			insert_user_error.printStackTrace();
			return null;
		}
	}
	
	public Integer Insert_new_patient(String name, String surname, String gender, String age, Boolean chronic, Boolean drugs, String reference_number, Integer emergency_id) {
		try {
			String table = "INSERT INTO patient (name, surname, gender, age, chronic, drugs, reference_number, emergency_id) " + " VALUES(?,?,?,?,?,?,?,?);";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, name);
			template.setString(2, surname);
			template.setString(3, gender);
			template.setString(4, age);
			template.setBoolean(5, chronic);
			template.setBoolean(6, drugs);
			template.setString(7, reference_number);
			template.setInt(8, emergency_id);
			template.executeUpdate();
			
			String SQL_code1 = "SELECT last_insert_rowid() AS patient_id";
			template = this.sqlite_connection.prepareStatement(SQL_code1);
			ResultSet result_set = template.executeQuery();
			Integer patient_id = result_set.getInt("patient_id");
			template.close();
			
			return patient_id;	
			
		} catch (SQLException insert_patient_error) {
			insert_patient_error.printStackTrace();
			return null;
		}
		
	}
	
	public Integer Insert_new_emergency(Integer code, String date) {
		try {
			String table = "INSERT INTO emergency (code, register_date) " + " VALUES(?,?);";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setInt(1, code);
			template.setString(2, date);
			template.executeUpdate();
			
			String SQL_code1 = "SELECT last_insert_rowid() AS emergency_id";
			template = this.sqlite_connection.prepareStatement(SQL_code1);
			ResultSet result_set = template.executeQuery();
			Integer emergency_id = result_set.getInt("emergency_id");
			Emergency emerg = new Emergency(emergency_id);
			template.close();
			
			return emerg.getId();	
			
		} catch (SQLException insert_record_error) {
			insert_record_error.printStackTrace();
			return null;
		}
	}
	
	
	public Integer Insert_new_location(String type, String vehicle) {
		Integer where_id;
		String table = "INSERT INTO location (type, vehicle) " + "VALUES (?,?)";
		try {
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(2, type);
			template.setString(1, vehicle);
			template.executeUpdate();
			String SQL_code = "SELECT last_insert_rowid() AS location_id";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet result_set = template.executeQuery();
			where_id = result_set.getInt("location_id");
			template.close();
			return where_id;
		} catch (SQLException new_location_error) {
			new_location_error.printStackTrace();
			return -1;
		}
	}
	
	public Integer Insert_new_speciality(String name) {
		Integer speciality_id;
		String table = "INSERT INTO speciality (name) " + "VALUES (?)";
		try {
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, name);
			template.executeUpdate();
			String SQL_code = "SELECT last_insert_rowid() AS speciality_id";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet result_set = template.executeQuery();
			speciality_id = result_set.getInt("speciality_id");
			template.close();
			return speciality_id;
		} catch (SQLException new_speciality_error) {
			new_speciality_error.printStackTrace();
			return -1;
		}
	}
	
	public Integer Insert_new_disease(String name, Integer spe_id) {
		Integer disease_id;
		String table = "INSERT INTO disease (name, speciality_id) " + "VALUES (?,?)";
		try {
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, name);
			template.setInt(2, spe_id);
			template.executeUpdate();
			String SQL_code = "SELECT last_insert_rowid() AS disease_id";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet result_set = template.executeQuery();
			disease_id = result_set.getInt("disease_id");
			template.close();
			return disease_id;
		} catch (SQLException new_disease_error) {
			new_disease_error.printStackTrace();
			return -1;
		}
	}
	
	public Integer Insert_new_symptom(String name) {
		Integer symptom_id;
		String table = "INSERT INTO symptom (name) " + "VALUES (?)";
		try {
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, name);
			template.executeUpdate();
			String SQL_code = "SELECT last_insert_rowid() AS symptom_id";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet result_set = template.executeQuery();
			symptom_id = result_set.getInt("symptom_id");
			template.close();
			return symptom_id;
		} catch (SQLException new_symptom_error) {
			new_symptom_error.printStackTrace();
			return -1;
		}
	}
	
	public Integer Insert_new_protocol(String info) {
		Integer protocol_id;
		String table = "INSERT INTO protocol (info) " + "VALUES (?)";
		try {
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, info);
			template.executeUpdate();
			String SQL_code = "SELECT last_insert_rowid() AS protocol_id";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet result_set = template.executeQuery();
			protocol_id = result_set.getInt("protocol_id");
			template.close();
			return protocol_id;
		} catch (SQLException new_protocol_error) {
			new_protocol_error.printStackTrace();
			return -1;
		}
	}
	
	
	 // -----------------------> SEARCH METHODS <---------------------------

	
	public Patient Search_stored_patient_by_id(Integer patient_id) {
		try {
			String SQL_code = "SELECT * FROM patient WHERE patient_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, patient_id);
			ResultSet result_set = template.executeQuery();
			Patient pat = new Patient(patient_id);
			pat.setName(result_set.getString("name"));
			pat.setSurname(result_set.getString("surname"));
			pat.setGender(result_set.getString("gender"));
			pat.setAge_range(result_set.getString("age"));
			pat.setChronic(result_set.getBoolean("chronic"));
			pat.setDrugs(result_set.getBoolean("drugs"));
			pat.setReference_number(result_set.getString("reference_number"));
			template.close();
			return pat;
		} catch (SQLException search_patient_error) {
			search_patient_error.printStackTrace();
			return null;
		}
		
	}
	
	public Patient Search_stored_patient_by_emergency_id(Integer emergency_id) {
		try {
			String SQL_code = "SELECT * FROM patient WHERE emergency_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, emergency_id);
			ResultSet result_set = template.executeQuery();
			Patient pat = new Patient();
			pat = Search_stored_patient_by_id(result_set.getInt("patient_id"));
			template.close();
			return pat;
		} catch (SQLException search_patient_error) {
			search_patient_error.printStackTrace();
			return null;
		}
		
	}
	
	
	public Integer Search_stored_user_by_username(String username) {
    	Integer user_id=-1;
		try {
			String SQL_code = "SELECT user_id FROM user WHERE username LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setString(1, username);
			ResultSet result_set = template.executeQuery();
			
		    while(result_set.next()) {
		    	user_id = result_set.getInt("user_id");
		    }
			template.close();
			return user_id;
		} catch (SQLException search_user_error) {
			search_user_error.printStackTrace();
			return user_id;
		}
		
	}
	
	 public String Get_user_password (String username) {
	    	String pass="ERROR! Password doesn't match";
			try {
				String SQL_code = "SELECT password FROM user WHERE username LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setString(1, username);
				ResultSet result_set = template.executeQuery();
				while (result_set.next()) {
					pass = result_set.getString("password");
				}
				template.close();
				return pass;
				
				
			} catch (SQLException search_password_error) {
				search_password_error.printStackTrace();
				return pass;
			}
			
		}
	
	
	 public Location Search_vehicle_by_place_type(String place) {
		 
			try {
				Location.Vehicle vehicle=null;
				Integer loc_id=-1;
				
				String SQL_code = "SELECT * FROM location WHERE type LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setString(1, place);
				ResultSet result_set = template.executeQuery();
				while(result_set.next()) {
					vehicle = (Location.Vehicle) result_set.getObject("vehicle");
					//vehicle = result_set.getStringObject("vehicle");
					loc_id = result_set.getInt("location_id");
				}
				Location loc = new Location(loc_id, place, vehicle);
				template.close();
				return loc;
			} catch (SQLException search_vehicle_error) {
				search_vehicle_error.printStackTrace();
				return null;
			}
		}
	 
	 public Integer Search_location_id_from_emergency(Integer emergency_id) {
		 Integer location_id=-1;
			try {
				String SQL_code = "SELECT location_id FROM emergency WHERE emergency_id LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, emergency_id);
				ResultSet result_set = template.executeQuery();
				while(result_set.next()) {
					location_id = result_set.getInt("location_id");
				}
				template.close();
				return location_id;
			} catch (SQLException search_location_error) {
				search_location_error.printStackTrace();
				return location_id;
			}
		}
	 
	 public Emergency Search_stored_emergency_by_code(Integer code) {
		 	
			try {
				String SQL_code = "SELECT * FROM emergency WHERE code LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, code);
				ResultSet result_set = template.executeQuery();
				Integer emergency_id = null, level = null;
				String date = null;
			    while(result_set.next()) {
			    	emergency_id = result_set.getInt("emergency_id");
			    	level = result_set.getInt("level");
			    	date = result_set.getString("register_date");
			    }
				Emergency emergency = new Emergency(emergency_id, code, date, level);
				template.close();
				return emergency;
			} catch (SQLException search_emergency_error) {
				search_emergency_error.printStackTrace();
				return null;
			}
			
		}
	 
	 public Integer Search_speciality_id_by_name(String name) {
	    	Integer spe_id=-1;
			try {
				String SQL_code = "SELECT speciality_id FROM speciality WHERE name LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setString(1, name);
				ResultSet result_set = template.executeQuery();
				
			    while(result_set.next()) {
			    	spe_id = result_set.getInt("speciality_id");
			    }
				template.close();
				return spe_id;
			} catch (SQLException search_speciality_error) {
				search_speciality_error.printStackTrace();
				return spe_id;
			}
			
		}
	 
	 public String Search_speciality_by_id(Integer id) {
			try {
				String name="";
				String SQL_code = "SELECT name FROM speciality WHERE speciality_id LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, id);
				ResultSet result_set = template.executeQuery();
				while (result_set.next()) {
					name = result_set.getString("name");
				}
				template.close();
				return name;
				
				
			} catch (SQLException search_specialityName_error) {
				search_specialityName_error.printStackTrace();
				return null;
			}
			
		}
	 
	 
	 public Integer Search_speciality_by_emergency_id(Integer id) {
		 Integer spe_id=-1;
			try {
				
				String SQL_code = "SELECT speciality_id FROM emergency WHERE emergency_id LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, id);
				ResultSet result_set = template.executeQuery();
				while (result_set.next()) {
					spe_id = result_set.getInt("speciality_id");
				}
				template.close();
				return spe_id;
				
				
			} catch (SQLException search_speId_error) {
				search_speId_error.printStackTrace();
				return spe_id;
			}
			
		}
	 
	 public Integer Search_protocol_by_emergency_id(Integer id) {
		 Integer prot_id=-1;
			try {
				
				String SQL_code = "SELECT protocol_id FROM emergency WHERE emergency_id LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, id);
				ResultSet result_set = template.executeQuery();
				while (result_set.next()) {
					prot_id = result_set.getInt("protocol_id");
				}
				template.close();
				return prot_id;
				
				
			} catch (SQLException search_protocol_error) {
				search_protocol_error.printStackTrace();
				return prot_id;
			}
			
		}
	 
	 public String Search_protocol_info_by_id(Integer id) {
		 	String info="";
			try {
				
				String SQL_code = "SELECT info FROM protocol WHERE protocol_id LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, id);
				ResultSet result_set = template.executeQuery();
				while (result_set.next()) {
					info = result_set.getString("info");
				}
				template.close();
				return info;
				
				
			} catch (SQLException search_protocol_error) {
				search_protocol_error.printStackTrace();
				return info;
			}
			
		}
	 
	 
	 // -----------------------> UPDATE METHODS <---------------------------
	 
	 
	 public boolean Update_location_and_vehicle(String location, Integer location_id, Integer emergency_id) {
			try {
				String SQL_code = "UPDATE emergency SET direction = ?, location_id = ? WHERE emergency_id LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setString(1, location);
				template.setInt(2, location_id);
				template.setInt(3, emergency_id);
				template.executeUpdate();
				template.close();	
				return true;
			} catch (SQLException update_location_error) {
				update_location_error.printStackTrace();
				return false;
			}
		}
	 
	 public boolean Update_emergency_info(String direction, Integer severity, Integer speciality_id, Integer location_id, Integer protocol_id, Integer emergency_id) {
		 try {
				String SQL_code = "UPDATE emergency SET direction = ?, level = ?, speciality_id = ?, location_id = ?, protocol_id = ? WHERE emergency_id LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setString(1, direction);
				template.setInt(2, severity);
				template.setInt(3, speciality_id);
				template.setInt(4, location_id);
				template.setInt(5, protocol_id);
				template.setInt(6, emergency_id);
				template.executeUpdate();
				template.close();	
				return true;
			} catch (SQLException update_emergency_error) {
				update_emergency_error.printStackTrace();
				return false;
			}
	 }
	 
	 
	 // -----------------------> LIST METHODS <---------------------------

	
	public List<Emergency> List_all_codes() {
		List<Emergency> list = new LinkedList<Emergency>();
		try {
			Statement statement = this.sqlite_connection.createStatement();
			String SQL_code = "SELECT * FROM emergency";
			ResultSet rs = statement.executeQuery(SQL_code);
			while(rs.next()) {
				Integer id = rs.getInt("emergency_id");
				Integer code = rs.getInt("code");
				Integer level = rs.getInt("level");
				String register_date = rs.getString("register_date");
				list.add(new Emergency(id, code, register_date, level));
			}
			return list;
		} catch (SQLException list_codes_error) {
			list_codes_error.printStackTrace();
			return null;
		}
	}
	
	public List<String> List_all_symptoms(){
		List<String> list = new LinkedList<String>();
		try {
			Statement statement = this.sqlite_connection.createStatement();
			String SQL_code = "SELECT * FROM symptom";
			ResultSet rs = statement.executeQuery(SQL_code);
			while(rs.next()) {
				String name = rs.getString("name");
				list.add(name);
			}
			return list;
		} catch (SQLException list_symptoms_error) {
			list_symptoms_error.printStackTrace();
			return null;
		}
	}
	
	public List<String> List_all_specialities(){
		List<String> list = new LinkedList<String>();
		try {
			Statement statement = this.sqlite_connection.createStatement();
			String SQL_code = "SELECT name FROM speciality";
			ResultSet rs = statement.executeQuery(SQL_code);
			while(rs.next()) {
				String name = rs.getString("name");
				list.add(name);
			}
			return list;
		} catch (SQLException list_symptoms_error) {
			list_symptoms_error.printStackTrace();
			return null;
		}
	}
	
	
	public List<String> List_all_places(){
		List<String> list = new LinkedList<String>();
		try {
			Statement statement = this.sqlite_connection.createStatement();
			String SQL_code = "SELECT type FROM location";
			ResultSet rs = statement.executeQuery(SQL_code);
			while(rs.next()) {
				String type = rs.getString("type");
				list.add(type);
			}
			return list;
		} catch (SQLException list_places_error) {
			list_places_error.printStackTrace();
			return null;
		}
	}
	
	//------------> ESTE ESTA MAL. CORREGIR MAS ADELANTE
	
	public List<String> List_all_symptoms_by_speciality(Integer speciality_id){
		List<String> list = new LinkedList<String>();
		try {
			Statement statement = this.sqlite_connection.createStatement();
			String SQL_code = "SELECT symptom_id FROM speciality_symptom WHERE speciality_id LIKE ?";
			ResultSet rs = statement.executeQuery(SQL_code);
			while(rs.next()) {
				String name = rs.getString("name");
				list.add(name);
			}
			return list;
		} catch (SQLException list_symptoms_error) {
			list_symptoms_error.printStackTrace();
			return null;
		}
	}
	
	
	
	 // -----------------------> DB METHODS <---------------------------

	
	public boolean Close_connection() {
		try {
			this.sqlite_connection.close();
			return true;
		} catch (SQLException close_connection_error) {
			close_connection_error.printStackTrace();
			return false;
		}
	}



}
