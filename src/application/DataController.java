package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdbc.SQLManager;
import pojos.*;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DataController implements Initializable {

	private static Stage main_menu_stage;
	public MenuController menu_controller;
	public SymptomsController symptoms_controller;
	private static SQLManager manager_object;
	private static Emergency emergency;
	
	public static void setValues(SQLManager SQL_manager, Emergency urgen) {
		manager_object = SQL_manager;
		emergency = urgen;
	}
	
	@FXML
    private Pane menuPane;
	
	@FXML
	private TextField nameField;
	
	@FXML
	private TextField surnameField;
	
	@FXML
	private TextArea locationField;
	
	@FXML
    private Button proceedButton;
	
	@FXML
    private ComboBox<String> ageField;

    @FXML
    private ComboBox<String> genderField;

    @FXML
    private ComboBox<String> drugsField;

    @FXML
    private ComboBox<String> chronicField;

    @FXML
    private ComboBox<String> placeField;
    
    @FXML
    private Label vehiculeText;

	
	public DataController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ObservableList<String> gender = FXCollections.observableArrayList("Male","Female");
		genderField.setItems(gender);
		ObservableList<String> ages = FXCollections.observableArrayList("Child","Young","Adult","Elder");
		ageField.setItems(ages);
		ObservableList<String> drugs = FXCollections.observableArrayList("Yes","No");
		drugsField.setItems(drugs);
		ObservableList<String> chronic = FXCollections.observableArrayList("Yes","No");
		chronicField.setItems(chronic);
		ObservableList<String> location = FXCollections.observableArrayList(manager_object.List_all_places());
		placeField.setItems(location);
		
			
		
		proceedButton.setOnAction((ActionEvent event) -> {
			
			// Save patient information into DB
			Integer ref_number = (int) (Math.random()*(99999-10000+1)+10000);
			String option = chronicField.getValue(), option2 = drugsField.getValue();
			Boolean val, val2;
			if (option.equals("Yes")) {
				val = true;
			} else {
				val = false;
			}
			if (option2.equals("Yes")) {
				val2 = true;
			} else {
				val2 = false;
			}
			
						
			Patient patient = new Patient(nameField.getText(),surnameField.getText(), genderField.getValue(), ageField.getValue(), val, val2, ref_number.toString());
			manager_object.Insert_new_patient(patient.getName(), patient.getSurname(), patient.getGender(), patient.getAge_range(), patient.getChronic(), 
					patient.getDrugs(), patient.getReference_number(), emergency.getId());
						
			
			
			// ------> ESTO VIENE DE RULES
			
			Location loc = manager_object.Search_vehicle_by_place_type(placeField.getValue().toString());
			manager_object.Update_location_and_vehicle(locationField.getText(), loc.getId(), emergency.getId()); 
			
			try {
				SymptomsController.setValues(manager_object, emergency);
				Pane symptoms_pane_fxml = FXMLLoader.load(getClass().getResource("SymptomsView.fxml"));
				menuPane.getChildren().removeAll();
				menuPane.getChildren().setAll(symptoms_pane_fxml);
				
			} catch(IOException proceed_error) {
				proceed_error.printStackTrace();
			}
		});
				
	}
	
	
	@FXML
    void open_new_case(MouseEvent event) {
		try {
			MenuController.setValues(manager_object, null);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsView.fxml"));
			Parent root = (Parent) loader.load();
			this.menu_controller = new MenuController();
			this.menu_controller = loader.getController();
			main_menu_stage = (Stage) menuPane.getScene().getWindow();
			main_menu_stage.setIconified(true);
			
			Stage stage = new Stage();
			stage.setAlwaysOnTop(true);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.show();
		
		} catch(IOException open_case) {
			open_case.printStackTrace();
		}
		
    }
	
	
	@FXML
	void refresh_display(ActionEvent event) {
		String place = placeField.getValue();
		vehiculeText.setText("Display: " + manager_object.Search_vehicle_by_place_type(place).getVehicle().toString());
	}
	
	@FXML
    void close_window(MouseEvent event) {
		main_menu_stage = (Stage) menuPane.getScene().getWindow();
		main_menu_stage.close();
		manager_object.Close_connection();
    }
	
	@FXML
	void minimize_window(MouseEvent event) {
		main_menu_stage = (Stage) menuPane.getScene().getWindow();
		main_menu_stage.setIconified(true);
	}

}
