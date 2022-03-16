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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdbc.SQLManager;

public class RegistrationController implements Initializable {
	
	private MenuController menu_controller;
	private LogInController login_controller;
	private static SQLManager manager_object;
	private static Stage main_menu_stage;
	
	public static void setValues(SQLManager SQL_manager) {
		manager_object = SQL_manager;
	}
	
	@FXML
    private Pane registrationPane;

    @FXML
    private TextField usernameField;

    @FXML
    private Label warning_email;

    @FXML
    private Label warning_username;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordField2;

    @FXML
    private Button signUpButton;
    
    @FXML
    private ComboBox<String> professionField;
    
    @FXML
    private Group warning;

    @FXML
    private Label warningLabel;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> roles = FXCollections.observableArrayList("Ambulance staff","Emergency call assistant");
		professionField.setItems(roles);
		
		signUpButton.setDisable(true);
		
		signUpButton.setOnAction((ActionEvent event) -> {
			try {
				if (passwordField.getText().equals(passwordField2.getText()) & !usernameField.equals(null)) {
					
					MenuController.setValues(manager_object, null);
					FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsView.fxml"));
					Parent root = (Parent) loader.load();
					this.menu_controller = new MenuController();
					this.menu_controller = loader.getController();
					main_menu_stage = (Stage) registrationPane.getScene().getWindow();
					main_menu_stage.close();
					
					Stage stage = new Stage();
					stage.setAlwaysOnTop(true);
					stage.initStyle(StageStyle.UNDECORATED);
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.setScene(new Scene(root));
					stage.show();
					
				} else {
					warning.setVisible(true);
					signUpButton.setDisable(true);
				}
			} catch (IOException signup_error) {
				signup_error.printStackTrace();
			}
		});
		
	}

	@FXML
    void return_window(MouseEvent event) {
		
		try {
			LogInController.setValues(null);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
			Parent root = (Parent) loader.load();
			this.login_controller = new LogInController();
			this.login_controller = loader.getController();
			main_menu_stage = (Stage) registrationPane.getScene().getWindow();
			main_menu_stage.close();
			
			Stage stage = new Stage();
			stage.setAlwaysOnTop(true);
			stage.initStyle(StageStyle.UNDECORATED);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root));
			stage.show();
			
		} catch (IOException return_error) {
			return_error.printStackTrace();
		}
		
    }

	@SuppressWarnings("unlikely-arg-type")
	@FXML
    void validate_password(MouseEvent event) {
    	
    	// ------------> Check if PASSWORD is correct
		
    	String username = usernameField.getText();
    	
    	if (!usernameField.getText().equals(manager_object.Search_stored_user_by_username(username))) {
    		
    		if(!passwordField.getText().contentEquals("") & !passwordField2.getText().contentEquals("") & passwordField.getText().equals(passwordField2.getText())) {
				warning.setVisible(false);
				signUpButton.setDisable(false);
			
			} else {
				signUpButton.setDisable(true);
				warning.setVisible(true);
				warningLabel.setText("ERROR! The password must match");
				
			}
    	} else {
			signUpButton.setDisable(true);
			warning.setVisible(true);
			warningLabel.setText("ERROR! Username already exists");
			
		}
    	
    	
    }
	
}
