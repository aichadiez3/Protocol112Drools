package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdbc.SQLManager;
import pojos.Patient;
import pojos.Protocol;
import pojos.Specialty;
import pojos.Disease;
import pojos.Emergency;

public class ProtocolController implements Initializable {

	private static Stage main_menu_stage;
	public MenuController menu_controller;
	private static SQLManager manager_object;
	private static Patient patient;
	private static Emergency urgency;
	
	public static void setManager(SQLManager manager) {
		manager_object=manager;
	}
	
	public static void setValues(SQLManager manager, Emergency urg) {
		manager_object=manager;
		urgency = urg;
		
	}
	
	 @FXML
	    private Pane menuPane;

	    @FXML
	    private Label ageLabel;

	    @FXML
	    private Label genderLabel;

	    @FXML
	    private Label diseaseLabel;

	    @FXML
	    private Label specialityLabel;

	    @FXML
	    private TextArea protocolInfo;

	    @FXML
	    private Label typeLabel;

	    @FXML
	    private Label nameLabel;

	    @FXML
	    private Label surnameLabel;

	    @FXML
	    private Label drugs;

	    @FXML
	    private Label code;

	
	public ProtocolController() {
		super();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		patient = urgency.getPatient();
		
		System.out.println(patient.toString());
		
		nameLabel.setText(patient.getName());
		surnameLabel.setText(patient.getSurname());
		ageLabel.setText(patient.getAge_range());
		genderLabel.setText(patient.getGender());
		drugs.setText(patient.getDrugs().toString());
		code.setText(urgency.getCode().toString());
		
		
		specialityLabel.setText(urgency.getSpecialty().getName().toString());
		
		diseaseLabel.setText(urgency.getDisease().getName().toString());
		
		protocolInfo.setText(urgency.getProtocol().getInfo().toString());
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
