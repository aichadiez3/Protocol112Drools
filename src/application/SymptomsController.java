package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

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
import pojos.Disease;
import pojos.Emergency;

public class SymptomsController implements Initializable {

	private static Stage main_menu_stage;
	private static SQLManager manager_object;
	private static Emergency urgency;
	private List<Disease> disease_list;
	
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

			urgency.setSpecialty(manager_object.Search_specialty_by_name(specialityField.getValue().toString()));
						
			KieServices ks = KieServices.Factory.get();
			KieContainer kc = ks.getKieClasspathContainer();
			
			execute(ks, kc);
				
			try {
			
				ProtocolController.setValues(manager_object, urgency);
				Pane protocol_pane_fxml = FXMLLoader.load(getClass().getResource("ProtocolView.fxml"));
				menuPane.getChildren().removeAll();
				menuPane.getChildren().setAll(protocol_pane_fxml);
			
			} catch(IOException protocol_error) {
				protocol_error.printStackTrace();
			}
			
		});
		
	}
	
	
	 public static void execute(KieServices ks, KieContainer kc) {
		 
			System.out.println("BEFORE:\n" + urgency);
		 
		 KieSession ksession = kc.newKieSession("exampKS");
				 
		 ksession.insert(manager_object);
		 ksession.insert(urgency);
		 ksession.fireAllRules();
		 ksession.dispose();
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
		
		refresh_list();
    }
	
	void refresh_list() {
		String spe_name = specialityField.getValue().toString();
		
		disease_list = new ArrayList<>(manager_object.List_all_diseases_by_specialty_id(manager_object.Search_specialty_id_by_name(spe_name)));
		
		ObservableList<String> symptoms = FXCollections.observableArrayList();
		List<String> symps_spe = new ArrayList<>();
		
		for(Disease d : disease_list) {
			symps_spe.addAll(d.getSymptomsList());
		}
		
		Set<String> hashSet = new LinkedHashSet<>(symps_spe);
        ArrayList<String> final_list = new ArrayList<>(hashSet);
		symptoms.addAll(final_list);
		
		symptomsList.setItems(symptoms);
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
