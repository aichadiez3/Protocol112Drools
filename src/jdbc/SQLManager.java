package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import interfaces.Interface;
import pojos.*;

public class SQLManager implements Interface {

	private Connection sqlite_connection;
	private static List<Protocol> protocol_list;
	private static List<Disease> cardio_disease_list;
	
	public SQLManager() {
		super();
		Connect();
		 Boolean tables = Create_tables();
	        if(tables==true) {
	        	Insert_default_elements_toDB();
	        }
	}
	
	
	public List<Protocol> getProtocol_list() {
		return protocol_list;
	}

	public static List<Disease> getCardio_disease_list() {
		return cardio_disease_list;
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
			String table_1 = " CREATE TABLE specialty " + "(specialty_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			        + " name INTEGER NOT NULL UNIQUE)";
			statement_1.execute(table_1);
			statement_1.close();
			
			Statement statement_2 = this.sqlite_connection.createStatement();
			String table_2 = " CREATE TABLE protocol " + "(protocol_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " info TEXT default NULL," +" type TEXT default NULL)";
			statement_2.execute(table_2);
			statement_2.close();
			
			Statement statement_3 = this.sqlite_connection.createStatement();
			String table_3 = " CREATE TABLE location " + "(location_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			        + " type TEXT NOT NULL, " + " vehicle TEXT default NULL)";
			statement_3.execute(table_3);
			statement_3.close();
			
			Statement statement_4 = this.sqlite_connection.createStatement();
			String table_4 = " CREATE TABLE emergency (emergency_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " code INTEGER NOT NULL UNIQUE, " + " level INTEGER default NULL, " + "register_date TEXT NOT NULL, "
					+ " direction TEXT default NULL, " + " location_id INTEGER FOREING KEY REFERENCES location(location_id), "
					+ " specialty_id INTEGER FOREING KEY REFERENCES specialty(specialty_id) ON DELETE CASCADE, "
					+ " disease_id INTEGER FOREING KEY REFERENCES disease(disease_id) ON DELETE CASCADE, "
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
			        + " name TEXT NOT NULL," + " symptom_list TEXT default NULL, "
					+ "specialty_id INTEGER FOREING KEY REFERENCES specialty(specialty_id) ON DELETE CASCADE)";
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
			
			Statement statement_10 = this.sqlite_connection.createStatement();
			String table_10 = "CREATE TABLE specialty_symptom " + " (specialty_id INTEGER REFERENCES specialty(specialty_id), " 
					+ "symptom_id INTEGER REFERENCES symptom(symptom_id), "
					+ "PRIMARY KEY (specialty_id, symptom_id))";
			statement_10.execute(table_10);
			statement_10.close();
		
			
			return true;
			
		} catch (SQLException tables_error) {
			//tables_error.printStackTrace();
			return false;
		}
	}
	
	public void Insert_default_elements_toDB() {
		
		Insert_new_location("AMBULANCE", "Home");
		Insert_new_location("AMBULANCE", "Transit");
		Insert_new_location("AMBULANCE", "Workplace");
		Insert_new_location("HELICOPTER", "Mountain");
		Insert_new_location("BOAT", "Beach");
		
		Insert_new_specialty("Oncology");
		Insert_new_specialty("Cardiology");
		Insert_new_specialty("Toxicology");
		Insert_new_specialty("Traumatology");
		Insert_new_specialty("Neurology");
		Insert_new_specialty("Other");
		
		Insert_new_protocol("Take paracetamol", "ADVICE");
		Insert_new_protocol("Connect to oxygen supply", "SHIPMENT");
		
		// ADD HERE MORE PROTOCOLS FROM THE EXCEL
		
		protocol_list = new ArrayList<>(List_all_protocols());
		
		
		
		List<String> saver = new ArrayList<>(); 
		
		// --------> CARDIOLOGY
		
		
		List<String> cardio_symps_1 = Arrays.asList("Chest pressure/Fatigue/Pain extends to the left arm/Disnea/Cold sweat/|".split("/"));
		List<String> cardio_symps_2 = Arrays.asList("Phlegm/Swelling/Faints/Fatigue/Heart palpitations/|".split("/"));
		List<String> cardio_symps_3 = Arrays.asList("Swelling/Faints/Fatigue/|".split("/"));
		List<String> cardio_symps_4 = Arrays.asList("Sudden numbness/Paralysis/Confusion/Difficulty speaking or undestand/Loss of vision/Loss of balance/Pain/|".split("/"));
		List<String> cardio_symps_5 = Arrays.asList("Pale skin/Daze/Tunnel vision/Warmth sensation/Cold sweat/|".split("/"));

		
		List<String> names = new ArrayList<>();
			names.addAll(cardio_symps_1);
			names.addAll(cardio_symps_2);
			names.addAll(cardio_symps_3);
			names.addAll(cardio_symps_4);
			names.addAll(cardio_symps_5);
		
		List<Symptom> cardio_symp_list = new ArrayList<>();
		int m=0;
		for(String n : names) {
			
			if(m < names.size()) {
				cardio_symp_list.add(new Symptom(m, n));
				m++;
			} else {
				break;
			}
			
		}
		
		// Assosiate to disease
		
		List<String> cardio_diseases = Arrays.asList("Heart attack,Heart failure,Hipertensive crisis,Ictus,Syncope".split(","));
		
		Integer i = 0, spe_id = Search_specialty_id_by_name("Cardiology");
		
		for (String d: cardio_diseases) {
			
			Disease disease = Insert_new_disease(d, spe_id);
			
			if(cardio_symp_list.get(i).toString().contains("|")) {
				i++;
			} 
			
			while(!cardio_symp_list.get(i).toString().contains("|")) {

				saver.add(cardio_symp_list.get(i).getSymptom());
				i++;
				
			}
			
			Associate_symptom_list_to_disease(saver.toString(), Search_disease_by_id(disease.getId()).getId());
			saver.clear();
		}
		
		cardio_disease_list = new ArrayList<>(List_all_diseases_by_specialty_id(spe_id));
		
		//Remove repetitions and associate symptoms to specialty
		Set<String> hashSet = new LinkedHashSet<>(names);
        ArrayList<String> cardio_list = new ArrayList<>(hashSet);
				        
        for(int it = 0; it < cardio_list.size(); it++) {
        	
        	if (cardio_list.get(it).equals("|")) {
        		cardio_list.remove(it);
        	} else {
        		Integer index = Insert_new_symptom(cardio_list.get(it));
        		Associate_symptom_to_specialty(index, Search_specialty_id_by_name("Cardiology"));
        	}
		}
			
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
	
	public Integer Insert_new_emergency(Integer code, Integer severity, String date, String direction, Integer loc_id, Integer spe_id, Integer dis_id, Integer prot_id) {
		try {
			String table = "INSERT INTO emergency (code, level, register_date, direction, location_id, specialty_id, disease_id, protocol_id) " + " VALUES(?,?,?,?,?,?,?,?);";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setInt(1, code);
			template.setInt(2, severity);
			template.setString(3, date);
			template.setString(4, direction);
			template.setInt(5, loc_id);
			template.setInt(6, spe_id);
			template.setInt(7, dis_id);
			template.setInt(8, prot_id);
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
		String table = "INSERT INTO location (type,vehicle) " + "VALUES (?,?)";
		try {
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, vehicle);
			template.setString(2, type);
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
	
	
	public Integer Insert_new_specialty(String name) {
		Integer specialty_id;
		String table = "INSERT INTO specialty (name) " + "VALUES (?)";
		try {
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, name);
			template.executeUpdate();
			String SQL_code = "SELECT last_insert_rowid() AS specialty_id";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet result_set = template.executeQuery();
			specialty_id = result_set.getInt("specialty_id");
			template.close();
			return specialty_id;
		} catch (SQLException new_specialty_error) {
			new_specialty_error.printStackTrace();
			return -1;
		}
	}
	
	public Disease Insert_new_disease(String name, Integer spe_id) {
		String table = "INSERT INTO disease (name, specialty_id) " + "VALUES (?,?)";
		try {
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, name);
			template.setInt(2, spe_id);
			template.executeUpdate();
			String SQL_code = "SELECT last_insert_rowid() AS disease_id";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet result_set = template.executeQuery();
			Integer disease_id = result_set.getInt("disease_id");
			Disease disease = Search_disease_by_id(disease_id);
			template.close();
			return disease;
		} catch (SQLException new_disease_error) {
			new_disease_error.printStackTrace();
			return null;
		}
	}
	
	
	public Integer Insert_new_symptom(String name) {
		Integer symptom_id=-1;
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
			return symptom_id;
		}
	}
	
	public Boolean Associate_symptom_to_specialty(Integer symp_id, Integer spe_id) {
		
		try {
			String SQL_code = "INSERT INTO specialty_symptom (specialty_id, symptom_id) " + "VALUES (?,?)";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, spe_id);
			template.setInt(2, symp_id);
			template.executeUpdate();
			template.close();	
			return true;
		} catch (SQLException associate_symp_spe_error) {
			associate_symp_spe_error.printStackTrace();
			return false;
		}
	}
	
	
	
	public Integer Insert_new_protocol(String info, String type) {
		Integer protocol_id;
		String table = "INSERT INTO protocol (info, type) " + "VALUES (?,?)";
		try {
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, info);
			template.setString(2, type);
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
		 Location loc = null;
			try {
				String vehicle="";Integer loc_id=-1;
				
				String SQL_code = "SELECT * FROM location WHERE type LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setString(1, place);
				ResultSet result_set = template.executeQuery();
				while(result_set.next()) {
					vehicle = result_set.getString("vehicle");
					loc_id = result_set.getInt("location_id");
				}
				loc = new Location(loc_id, place, vehicle);
				template.close();
				return loc;
			} catch (SQLException search_vehicle_error) {
				search_vehicle_error.printStackTrace();
				return loc;
			}
		}
	 
	 public Location Search_location_from_emergency(Integer emergency_id) {
		 Integer location_id=-1; Location loc = null;
			try {
				String SQL_code = "SELECT location_id FROM emergency WHERE emergency_id LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, emergency_id);
				ResultSet result_set = template.executeQuery();
				while(result_set.next()) {
					location_id = result_set.getInt("location_id");
				}
				loc = Search_location_by_id(location_id);
				template.close();
				return loc;
			} catch (SQLException search_location_error) {
				search_location_error.printStackTrace();
				return loc;
			}
		}
	 
	 public Location Search_location_by_id(Integer id) {
		 Location loc = null; String type="",vehicle="";
			try {
				String SQL_code = "SELECT * FROM location WHERE location_id LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, id);
				ResultSet result_set = template.executeQuery();
				while(result_set.next()) {
					type = result_set.getString("type");
					vehicle = result_set.getString("vehicle");
				}
				loc = new Location(id,type,vehicle);

				template.close();
				return loc;
			} catch (SQLException search_location_error) {
				search_location_error.printStackTrace();
				return loc;
			}
		}
	 
	 public Emergency Search_stored_emergency_by_code(Integer code) {
		Integer emergency_id=-1, level=-1, spe_id=-1, disease_id=-1, protocol_id=-1, location_id=-1;
		String date = "", direction="";	
			try {
				String SQL_code = "SELECT * FROM emergency WHERE code LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, code);
				ResultSet result_set = template.executeQuery();
				
			    while(result_set.next()) {
			    	emergency_id = result_set.getInt("emergency_id");
			    	level = result_set.getInt("level");
			    	direction = result_set.getString("direction");
			    	date = result_set.getString("register_date");
			    	location_id = result_set.getInt("location_id");
			    	spe_id = result_set.getInt("specialty_id");
			    	disease_id = result_set.getInt("disease_id");
			    	protocol_id = result_set.getInt("protocol_id");
			    }
			    Specialty spe = Search_specialty_by_emergency_id(emergency_id);
			    Disease dis = Search_disease_by_id(disease_id);
			    Location loc = Search_location_from_emergency(emergency_id);
			    Protocol prot = Search_protocol_by_emergency_id(protocol_id);
				Emergency emergency = new Emergency(emergency_id, code, date, level, direction, prot, loc, spe, dis);
				template.close();
				return emergency;
			} catch (SQLException search_emergency_error) {
				search_emergency_error.printStackTrace();
				return null;
			}
			
		}
	 
	 public Integer Search_specialty_id_by_name(String name) {
	    	Integer spe_id=-1;
			try {
				String SQL_code = "SELECT specialty_id FROM specialty WHERE name LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setString(1, name);
				ResultSet result_set = template.executeQuery();
				
			    while(result_set.next()) {
			    	spe_id = result_set.getInt("specialty_id");
			    }
				template.close();
				return spe_id;
			} catch (SQLException search_specialty_error) {
				search_specialty_error.printStackTrace();
				return spe_id;
			}
			
		}
	 
	 
	 
	 public Specialty Search_specialty_by_name(String name) {
		 Specialty spe = null;
		 Integer id=-1;
			try {
				String SQL_code = "SELECT * FROM specialty WHERE name LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setString(1, name);
				ResultSet result_set = template.executeQuery();
				while (result_set.next()) {
					id = result_set.getInt("specialty_id");
				}
				spe = new Specialty(id,name);
				template.close();
				return spe;
				
				
			} catch (SQLException search_specialty_error) {
				search_specialty_error.printStackTrace();
				return spe;
			}
			
		}
	 
	 public Specialty Search_specialty_by_id(Integer id) {
		 Specialty spe = null;
		 String name ="";
			try {
				String SQL_code = "SELECT * FROM specialty WHERE specialty_id LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, id);
				ResultSet result_set = template.executeQuery();
				while (result_set.next()) {
					name = result_set.getString("name");
				}
				spe = new Specialty(id, name);
				template.close();
				return spe;
				
			} catch (SQLException search_specialty_error) {
				search_specialty_error.printStackTrace();
				return spe;
			}
			
		}
	 
	 
	 public Specialty Search_specialty_by_emergency_id(Integer id) {
		 Specialty spe = null;
		 Integer spe_id=-1;
			try {
				
				String SQL_code = "SELECT specialty_id FROM emergency WHERE emergency_id LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, id);
				ResultSet result_set = template.executeQuery();
				while (result_set.next()) {
					spe_id = result_set.getInt("specialty_id");
				}
				spe = Search_specialty_by_id(spe_id);
				template.close();
				return spe;
				
				
			} catch (SQLException search_specialty_error) {
				search_specialty_error.printStackTrace();
				return spe;
			}
			
		}
	 
	 public Protocol Search_protocol_by_emergency_id(Integer id) {
		 Protocol prot = null; Integer prot_id=-1;
			try {
				
				String SQL_code = "SELECT protocol_id FROM emergency WHERE emergency_id LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, id);
				ResultSet result_set = template.executeQuery();
				while (result_set.next()) {
					prot_id = result_set.getInt("protocol_id");
				}
				
				prot = Search_protocol_by_id(prot_id);
				template.close();
				return prot;
				
				
			} catch (SQLException search_protocol_error) {
				search_protocol_error.printStackTrace();
				return prot;
			}
			
		}
	 
	 public Protocol Search_protocol_by_id(Integer id) {
		 	Protocol prot = null; String info="", type="";
		 	
			try {
				String SQL_code = "SELECT * FROM protocol WHERE protocol_id = ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, id);
				ResultSet result_set = template.executeQuery();
				while (result_set.next()) { 	
					info = result_set.getString("info");
					type = result_set.getString("type");
				}
				prot = new Protocol(id,info,type);
				template.close();
				return prot;
				
				
			} catch (SQLException search_protocol_error) {
				search_protocol_error.printStackTrace();
				return prot;
			}
			
		}
	 
	 public Disease Search_disease_by_id(Integer id) {
		 Disease disease=null;
		 String name="", list="";
			try {
				String SQL_code = "SELECT * FROM disease WHERE disease_id LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, id);
				ResultSet result_set = template.executeQuery();
				while (result_set.next()) {
					name = result_set.getString("name");
					list = result_set.getString("symptom_list");
				}
				disease = new Disease(id,name,list);
				template.close();
				return disease;
			} catch (SQLException search_disease_error) {
				search_disease_error.printStackTrace();
				return disease;
			}
			
		}
	 
	 public Integer Search_disease_by_emergency_id(Integer id) {
		 Integer disease_id=-1;
			try {
				String SQL_code = "SELECT disease_id FROM emergency WHERE emergency_id LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, id);
				ResultSet result_set = template.executeQuery();
				while (result_set.next()) {
					disease_id = result_set.getInt("disease_id");
				}
				template.close();
				return disease_id;
			} catch (SQLException search_disease_error) {
				search_disease_error.printStackTrace();
				return disease_id;
			}
			
		}
	 
	 public Disease Search_disease_by_name(String name) {
			 Disease disease=null;
				try {
					String list=""; Integer id = -1;
					String SQL_code = "SELECT * FROM disease WHERE name LIKE ?";
					PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
					template.setString(1, name);
					ResultSet result_set = template.executeQuery();
					while (result_set.next()) {
						id = result_set.getInt("disease_id");
						list = result_set.getString("symptom_list");
					}
					disease = new Disease(id,name,list);
					template.close();
					
					System.out.println("From DB: "+disease.toString());
					return disease;
				} catch (SQLException search_disease_error) {
					search_disease_error.printStackTrace();
					return disease;
				}
				
			}
	 
	 public String Search_symptom_by_id(Integer id) {
		 String name="";
			try {
				String SQL_code = "SELECT * FROM symptom WHERE symptom_id LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, id);
				ResultSet result_set = template.executeQuery();
				while (result_set.next()) {
					name = result_set.getString("name");
				}
				template.close();
				return name;
			} catch (SQLException search_disease_error) {
				search_disease_error.printStackTrace();
				return name;
			}
			
		}
	 
	 // -----------------------> UPDATE METHODS <---------------------------
	 
	 
	 public Boolean Associate_symptom_list_to_disease(String symptom_list, Integer disease_id) {
			
			try {
				String SQL_code = "UPDATE disease SET symptom_list = ? WHERE disease_id LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setString(1, symptom_list);
				template.setInt(2, disease_id);
				template.executeUpdate();
				template.close();	
				return true;
			} catch (SQLException associate_symp_disease_error) {
				associate_symp_disease_error.printStackTrace();
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
	
	public List<String> List_all_specialities(){
		List<String> list = new LinkedList<String>();
		try {
			Statement statement = this.sqlite_connection.createStatement();
			String SQL_code = "SELECT name FROM specialty";
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
	
	public List<Disease> List_all_diseases_by_specialty_id(Integer spe_id){
		List<Disease> list = new LinkedList<Disease>();
		try {
			String SQL_code = "SELECT * FROM disease WHERE specialty_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, spe_id);
			ResultSet rs = template.executeQuery();
			while(rs.next()) {
				Integer id = rs.getInt("disease_id");
				String name = rs.getString("name");
				String symp_list = rs.getString("symptom_list");
				list.add(new Disease(id,name,symp_list));
			}
			return list;
		} catch (SQLException list_diseases_error) {
			list_diseases_error.printStackTrace();
			return null;
		}
	}
	
	public List<String> List_all_symptoms_by_disease(String disease){
		List<String> list = new ArrayList<String>();
		String symptom = "";
		try {
			String SQL_code = "SELECT symptom_list FROM disease WHERE name LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setString(1, disease);
			ResultSet rs = template.executeQuery();
			while(rs.next()) {
				symptom = rs.getString("symptom_list");
			}
			list.addAll(Arrays.asList(symptom.split(",")));
			return list;
		} catch (SQLException list_diseases_error) {
			list_diseases_error.printStackTrace();
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
	
	public List<String> List_all_symptoms_by_specialty_id(Integer spe_id){
		List<String> list = new LinkedList<String>();
		try {
			String SQL_code = "SELECT symptom_id FROM specialty_symptom WHERE specialty_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, spe_id);
			ResultSet rs = template.executeQuery();
			while(rs.next()) {
				Integer id = rs.getInt("symptom_id");
				String name = Search_symptom_by_id(id);
				list.add(name);
			}
			return list;
		} catch (SQLException list_symptoms_error) {
			list_symptoms_error.printStackTrace();
			return null;
		}
	}
	
	
	
	public List<Protocol> List_all_protocols() {
		List<Protocol> list = new LinkedList<Protocol>();
		try {
			Statement statement = this.sqlite_connection.createStatement();
			String SQL_code = "SELECT * FROM protocol";
			ResultSet rs = statement.executeQuery(SQL_code);
			while(rs.next()) {
				Integer id = rs.getInt("protocol_id");
				String info = rs.getString("info");
				String type = rs.getString("type");
				list.add(new Protocol(id, info, type));
			}
			return list;
		} catch (SQLException list_protocols_error) {
			list_protocols_error.printStackTrace();
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
