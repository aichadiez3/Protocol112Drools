package application;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
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
    private Group okay;

    @FXML
    private Label warningLabel;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> roles = FXCollections.observableArrayList("Ambulance staff","Emergency call assistant");
		professionField.setItems(roles);
		
		signUpButton.setDisable(true);
		
		signUpButton.setOnAction((ActionEvent event) -> {
			try {
				
				
				List<String> encrypt = Encryption_using_salt_base64(passwordField.getText());
				
				manager_object.Insert_new_user(usernameField.getText(), encrypt.get(0), encrypt.get(1), professionField.getValue());
				
				MenuController.setValues(manager_object);
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

	@FXML
    void validate_password(MouseEvent event) {
		
    	String username = usernameField.getText();
    	    	
    	if(manager_object.Search_stored_user_by_username(username) == -1) {
			if (passwordField.getText().equals(passwordField2.getText())) {
				if(professionField.getValue() != null) {
					
					signUpButton.setDisable(false);
					warning.setVisible(false);
					okay.setVisible(true);
					
				} else {
					warning.setVisible(true);
					warningLabel.setText("ERROR! Specify a profession");
					signUpButton.setDisable(true);
					okay.setVisible(false);
				}
				
			} else {
				warning.setVisible(true);
				signUpButton.setDisable(true);
				warningLabel.setText("ERROR! The password must match");
				okay.setVisible(false);
			}
		} else {
			warning.setVisible(true);
			warningLabel.setText("ERROR! Username already exists");
			signUpButton.setDisable(true);
			okay.setVisible(false);
		}
    	
    }
	
	/*
	 * Password-Based Encryption using Salt and Base64:
The password-based encryption technique uses plain text passwords and salt values to generate a hash value. And the hash value is then encoded as a Base64 string. 
Salt value contains random data generated using an instance of Random class from java.util package.
	 *
	 * */
	

	public List<String> Encryption_using_salt_base64(String password){
	          
	        /* generates the Salt value. It can be stored in a database. */  
	        String saltValue = PasswordUtils.getSaltvalue(30);  
	          
	        /* generates an encrypted password. It can be stored in a database.*/  
	        String encryptedpassword = PasswordUtils.generateSecurePassword(password, saltValue);  
	        
	        String[] encript = new String[2];
	        encript[0] = encryptedpassword;
	        encript[1] = saltValue;
	        
	    return Arrays.asList(encript);
	}
	
}
