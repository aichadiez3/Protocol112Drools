package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import jdbc.SQLManager;
import pojos.*;
import javafx.fxml.Initializable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class MenuController implements Initializable{

	private static Stage main_menu_stage;
	private static SQLManager manager_object;
	private static Emergency urgency;
	
	public DataController data_controller;
	public ProtocolController protocol_controller;
	
	public static void setValues(SQLManager SQL_manager) {
		manager_object = SQL_manager;
	}
	
	@FXML
    private AnchorPane anchorPane;
	
	@FXML
    private Pane menuPane;
	
	@FXML
    private Pane codePanel;
	
	@FXML
    private Group new_case;

    @FXML
    private Group protocol_consult;
    
    @FXML
    private TextField codeField;

    @FXML
    private Group proceedButton;
    
    @FXML
    private TreeTableView<CodeObject> codeTable;
    
    @FXML
    private ObservableList<CodeObject> code_objects = FXCollections.observableArrayList();
    
    
    public MenuController() {
    	super();
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		new_case.setOnMouseClicked((MouseEvent event) -> {
			try {
				urgency = new Emergency();
				DataController.setValues(manager_object, urgency);
								
				FXMLLoader loader = new FXMLLoader(getClass().getResource("DataView.fxml"));
				Parent root = (Parent) loader.load();
				this.data_controller = new DataController();
				this.data_controller = loader.getController();
				
				main_menu_stage = (Stage) menuPane.getScene().getWindow();
				main_menu_stage.close();
				
				Stage stage = new Stage();
				stage.setAlwaysOnTop(true);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setScene(new Scene(root));
				stage.show();
			
			} catch (IOException open_new_case_error) {
				open_new_case_error.printStackTrace();
				manager_object.Close_connection();
			}
		});
		
		protocol_consult.setOnMouseClicked((MouseEvent event) -> {
			
			codePanel.setVisible(true);
			menuPane.setEffect(new BoxBlur(4,4,4));
			menuPane.setDisable(true);
			
			
			List<Emergency> code_list = manager_object.List_all_codes();
			for(Emergency urgency : code_list) {
				code_objects.add(new CodeObject(urgency.getCode().toString(), urgency.getSeverity().toString(), urgency.getDate().toString()));
			}
			
			TreeItem<CodeObject> root = new RecursiveTreeItem<CodeObject>(code_objects, RecursiveTreeObject::getChildren);
			codeTable.setPlaceholder(new javafx.scene.control.Label("No urgencies found"));
			
			codeTable.setRoot(root);
			codeTable.setShowRoot(false);
			TreeTableColumn<CodeObject, String> ref_date = new TreeTableColumn<>("Date");
			ref_date.setPrefWidth(170);
			ref_date.setCellValueFactory(
					new Callback<TreeTableColumn.CellDataFeatures<CodeObject, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<CodeObject, String> param) {
							return param.getValue().getValue().recordDate;
						}
					});
			ref_date.getStyleClass().add("tree-table-column");
			ref_date.setResizable(false);
			
			TreeTableColumn<CodeObject, String> code = new TreeTableColumn<>("Code");
			code.setPrefWidth(50);
			code.setCellValueFactory(
					new Callback<TreeTableColumn.CellDataFeatures<CodeObject, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<CodeObject, String> param) {
							return param.getValue().getValue().code;
						}
					});
			code.getStyleClass().add("tree-table-column");
			code.setResizable(false);
			
			TreeTableColumn<CodeObject, String> level = new TreeTableColumn<>("Level");
			level.setPrefWidth(50);
			level.setCellValueFactory(
					new Callback<TreeTableColumn.CellDataFeatures<CodeObject, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<CodeObject, String> param) {
							return param.getValue().getValue().level;
						}
					});
			level.getStyleClass().add("tree-table-column");
			level.setResizable(false);
			
			codeTable.getColumns().setAll(code, level, ref_date);
			
			proceedButton.setOnMouseClicked((MouseEvent event2) -> {
				
				
				urgency = manager_object.Search_stored_emergency_by_code(Integer.parseInt(codeField.getText()));
				
				try {
					ProtocolController.setValues(manager_object, urgency);
					FXMLLoader loader = new FXMLLoader(getClass().getResource("ProtocolView.fxml"));
					Parent root2 = (Parent) loader.load();
					this.protocol_controller = new ProtocolController();
					this.protocol_controller = loader.getController();
					main_menu_stage = (Stage) menuPane.getScene().getWindow();
					main_menu_stage.close();
					
					Stage stage = new Stage();
					stage.setAlwaysOnTop(true);
					stage.initStyle(StageStyle.UNDECORATED);
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.setScene(new Scene(root2));
					stage.show();
				
				} catch (IOException open_protocol_error) {
					open_protocol_error.printStackTrace();
					manager_object.Close_connection();
				}
			});
			
			
			
		});
		
	}
	
	
	@FXML
    void select_code_item(MouseEvent event) {
		TreeItem<CodeObject> item = codeTable.getSelectionModel().getSelectedItem();
		Integer code = Integer.parseInt(item.getValue().code.getValue());
		codeField.setText(code.toString());
    }
	 

    @FXML
    void close_window(MouseEvent event) {
    	System.exit(0);
    	manager_object.Close_connection();
    }
    
    @FXML
    void return_window(MouseEvent event) {
    	codePanel.setVisible(false);
    	codeField.setText("");
    	menuPane.setEffect(null);
		menuPane.setDisable(false);
    }

}


class CodeObject extends RecursiveTreeObject<CodeObject> {
	
	StringProperty code;
	StringProperty recordDate;
	StringProperty level;
	
    public CodeObject(String code, String level, String recordDate) {
    	this.recordDate = new SimpleStringProperty(recordDate);
    	this.code = new SimpleStringProperty(code);
    	this.level = new SimpleStringProperty(level);
    }
}