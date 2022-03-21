package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
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

public class SymptomsController implements Initializable {

	private static Stage main_menu_stage;
	private static SQLManager manager_object;
	private static Emergency urgency;
	private List<String> disease_list;
	private ListIterator<String> litr = null;
	
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
		
		symptomsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // To select nultiple items
		selectedList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
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
		Integer spe_id = manager_object.Search_specialty_id_by_name(spe_name);
		
		List<String> symp = manager_object.List_all_symptoms_by_specialty_id(spe_id);
		
		Set<String> hashSet = new LinkedHashSet<>(symp); // avoid repetitions
        ArrayList<String> final_list = new ArrayList<>(hashSet);
		
        ObservableList<String> symptoms = FXCollections.observableArrayList(final_list);
		
		symptomsList.setItems(symptoms);
		
	}


	void check_repetitions() {
		
		if (!symptomsList.getItems().contains(selectedList.getItems())) {
			refresh_list();
			
			symptomsList.getItems().removeAll(selectedList.getItems());
			
			System.out.println("no repeated items");
		}else {
			System.out.println("items repeated");
		}
	}
	
	@FXML
    void removeFunction(MouseEvent event) {
		ObservableList<String> selectedItems =  selectedList.getSelectionModel().getSelectedItems();
        ObservableList<String> items = FXCollections.observableArrayList();
        
        for(String s : selectedItems){
        	
        	items.add(s);
        	
        }
        	symptomsList.setItems(items);
        	selectedList.getItems().removeAll(selectedItems);
        	check_repetitions();
    }

    @FXML
    void selectFunction(MouseEvent event) {
    	
        ObservableList<String> selectedItems =  symptomsList.getSelectionModel().getSelectedItems();
        ObservableList<String> items = FXCollections.observableArrayList(selectedList.getItems());
        
        for(String s : selectedItems){
        	items.add(s);
        }
        	selectedList.setItems(items);
	     // when item is selected, it is deleted from the list
	    	symptomsList.getItems().removeAll(selectedItems);
        	
        
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
