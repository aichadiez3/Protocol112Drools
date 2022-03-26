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
import javafx.scene.control.SelectionMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jdbc.SQLManager;
import pojos.Disease;
import pojos.Emergency;
import pojos.Patient;

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
    private ComboBox<String> diseaseField;
	
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
		
		diseaseField.setDisable(true);
		
		symptomsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // To select multiple items
		selectedList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		
		saveButton.setOnMouseClicked((MouseEvent event) -> {

			urgency.setSpecialty(manager_object.Search_specialty_by_name(specialityField.getValue()));
			
			Disease disease = manager_object.Search_disease_by_name(diseaseField.getValue().toString());
			urgency.setDisease(disease);			
			
			
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
		
		
		diseaseField.setOnAction((ActionEvent e) -> {
			
			List<String> list = manager_object.List_all_symptoms_by_disease(diseaseField.getValue());
			ObservableList<String>symps = FXCollections.observableArrayList(list); 
            selectedList.setItems(symps);
            symptomsList.getItems().removeAll(selectedList.getItems());
            
		});
		
	}
	
	
	 public static void execute(KieServices ks, KieContainer kc) {
		 
			//System.out.println("BEFORE:\n" + urgency);
			
			KieSession ksession = null;
		 
			
		 if(urgency.getSpecialty().getName().equals("Cardiology")) {
			 ksession = kc.newKieSession("cardiologyKS");
			 
		 } else if(urgency.getSpecialty().getName().equals("Oncology")) {
			 ksession = kc.newKieSession("oncologyKS");
			 
		 } else if(urgency.getSpecialty().getName().equals("Toxicology")) {
			 ksession = kc.newKieSession("toxicologyKS");
			 
		 } else if(urgency.getSpecialty().getName().equals("Traumatology")) {
			 ksession = kc.newKieSession("traumatologyKS");
			 
		 } else if(urgency.getSpecialty().getName().equals("Neurology")) {
			 ksession = kc.newKieSession("neurologyKS");
			 
		 } else if(urgency.getSpecialty().getName().equals("Other")){
			 ksession = kc.newKieSession("othersKS");
			 
		 }
		 
		 
		 ksession.insert(manager_object);
		 ksession.insert(urgency);
		 ksession.fireAllRules();
		 ksession.dispose();
		
		 
		manager_object.Insert_new_emergency(urgency.getCode(), urgency.getSeverity(), urgency.getDate(),urgency.getDirection(), urgency.getLocation().getId(), urgency.getSpecialty().getId(), urgency.getDisease().getId(), urgency.getProtocol().getId());
		urgency.setId(manager_object.Search_stored_emergency_by_code(urgency.getCode()).getId());
		
		Patient p = urgency.getPatient();
		manager_object.Insert_new_patient(p.getName(), p.getSurname(), p.getGender(), p.getAge_range(), p.getChronic(), p.getDrugs(), p.getReference_number(), manager_object.Search_stored_emergency_by_code(urgency.getCode()).getId());
		
		p.setId(manager_object.Search_stored_emergency_by_code(urgency.getCode()).getPatient().getId());

		//System.out.println("AFTER:\n" + urgency);
		
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
		
		diseaseField.setDisable(false);
		Integer spe_id = manager_object.Search_specialty_id_by_name(specialityField.getValue());
		List<Disease> list = manager_object.List_all_diseases_by_specialty_id(spe_id);
		ObservableList<String> diseases = FXCollections.observableArrayList();

		for(Disease d : list) {
			diseases.add(d.getName());
		}
		
		diseaseField.setItems(diseases);
		refresh_list();
    }
	
	
	void refresh_list() {
		
		String spe_name = specialityField.getValue().toString();
		Integer spe_id = manager_object.Search_specialty_id_by_name(spe_name);
		
		List<String> symp = manager_object.List_all_symptoms_by_specialty_id(spe_id);
		System.out.println(symp);
        ObservableList<String> symptoms = delete_repetitions(symp);
		symptomsList.setItems(symptoms);
		
	}
	
	private ObservableList<String> delete_repetitions(List<String> input) {
		Set<String> hashSet = new LinkedHashSet<>(input); // avoid repetitions
        ArrayList<String> final_list = new ArrayList<>(hashSet);
		
        ObservableList<String> output = FXCollections.observableArrayList(final_list);
        System.out.println(output);
        
		return output;
	}

	
	@SuppressWarnings("unlikely-arg-type")
	@FXML
    void removeFunction(MouseEvent event) {
		ObservableList<String> selectedItems =  selectedList.getSelectionModel().getSelectedItems();
        ObservableList<String> items = FXCollections.observableArrayList();
        
        for(String s : selectedItems){
        	
        	items.add(s);
        	
        }
        	symptomsList.setItems(items);
        	selectedList.getItems().removeAll(selectedItems);
        	
        	
        	// To check repetitions in list
        	if (!symptomsList.getItems().contains(selectedList.getItems())) {
    			refresh_list();
    			symptomsList.getItems().removeAll(selectedList.getItems());
    		}
    		
    }

    @FXML
    void selectFunction(MouseEvent event) {
    	
        List<String> selectedItems =  symptomsList.getSelectionModel().getSelectedItems();
        ObservableList<String> items = FXCollections.observableArrayList(selectedList.getItems());
        
        for(String s : selectedItems){
        	if(!items.contains(s)) {			// NO ELIMINA LAS REPETICIONES
        		items.add(s);
        	}
        	
        }
        selectedList.setItems(items);
     // when item is selected, it is deleted from the list
    	symptomsList.getItems().removeAll(selectedList.getItems());
    	
    
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
