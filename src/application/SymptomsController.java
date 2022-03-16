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
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jdbc.SQLManager;
import pojos.Emergency;

public class SymptomsController implements Initializable {

	private static Stage main_menu_stage;
	private static SQLManager manager_object;
	private static Emergency urgency;
	
	public static void setValues(SQLManager SQL_manager, Emergency urgen) {
		manager_object = SQL_manager;
		urgency=urgen;
	}
	
	@FXML
    private Pane menuPane;
	
	@FXML
    private Group saveButton;
	
	@FXML
    private Label textLabel;
	
	@FXML
    private ComboBox<String> specialityField;
	
	@FXML
    private ListView<String> symptomsList;

    @FXML
    private ImageView unselectArrow;

    @FXML
    private ImageView selectArrow;

    @FXML
    private ListView<String> selectedList;

	public SymptomsController() {
		super();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		ObservableList<String> specialities = FXCollections.observableArrayList(manager_object.List_all_specialities());
		specialityField.setItems(specialities);
		
		
		saveButton.setOnMouseClicked((MouseEvent event) -> {

			try {
				 
				
				/*
				 * HERE ASSOCIATION OF SEVERITY ---> FROM RULES
				 * 
				 * */
				
				Integer protocol = 1, severity = 3;
				Integer loc_id = manager_object.Search_location_id_from_emergency(urgency.getId());
				Integer speciality = manager_object.Search_speciality_id_by_name(specialityField.getValue().toString());
				manager_object.Update_emergency_info("calle palacios", severity, speciality, loc_id, protocol, urgency.getId());
			
				ProtocolController.setValues(manager_object, urgency);
				Pane protocol_pane_fxml = FXMLLoader.load(getClass().getResource("ProtocolView.fxml"));
				menuPane.getChildren().removeAll();
				menuPane.getChildren().setAll(protocol_pane_fxml);
			
			} catch(IOException protocol_error) {
				protocol_error.printStackTrace();
				manager_object.Close_connection();
			}
			
		});
		
	}
	
	
	
	
	@FXML
    void update_label(ActionEvent event) {
		textLabel.setText("List of symptoms of ");
		String selected = specialityField.getValue();
		
		if (selected !=  specialityField.getValue()) {
			textLabel.setText(textLabel.getText());
		} else {
			textLabel.setText(textLabel.getText() + selected);
		}
		
		
    }
	
	@FXML
    void close_window(MouseEvent event) {
		//System.exit(0);
		main_menu_stage = (Stage) menuPane.getScene().getWindow();
		main_menu_stage.close();
		manager_object.Close_connection();
    }
	
	@FXML
	void minimize_window(MouseEvent event) {
		main_menu_stage = (Stage) menuPane.getScene().getWindow();
		main_menu_stage.setIconified(true);
	}

	@FXML
	void refresh_list(MouseEvent event) {
		ObservableList<String> symptoms = FXCollections.observableArrayList(manager_object.List_all_symptoms());
		int i=0;
		while(i < symptoms.size()) {
			symptomsList.getItems().add(i, symptoms.get(i));
			i++;
		}
		
	}
	
}
