package application;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jdbc.SQLManager;

public class LogInController implements Initializable {

	private static Stage main_menu_stage;
	private MenuController menu_controller;
	private RegistrationController registration_controller;
	private static SQLManager manager_object = null;
	
	public static void setValues(SQLManager SQL_manager) {
		manager_object = SQL_manager;
	}
	
	

	@FXML
    private AnchorPane anchorPane;

    @FXML
    private Pane menuPane;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label signupButton;

    @FXML
    private Button loginButton;
    
    @FXML
    private Group warning;

    @FXML
    private Label warningLabel;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		warning.setVisible(false);
		loginButton.setDisable(true);
		
		manager_object = new SQLManager();
		
		loginButton.setOnAction((ActionEvent event) -> {
			
		Boolean validation = Check_encrypted_password(passwordField.getText());
		
			if (validation == true) {
				
				warning.setVisible(false);
				loginButton.setDisable(false);
				
				try {
					MenuController.setValues(manager_object);
					FXMLLoader loader = new FXMLLoader(getClass().getResource("OptionsView.fxml"));
					Parent root = (Parent) loader.load();
					this.menu_controller = new MenuController();
					this.menu_controller = loader.getController();
					
					Stage stage = new Stage();
					stage.setAlwaysOnTop(true);
					stage.initStyle(StageStyle.UNDECORATED);
					stage.initModality(Modality.APPLICATION_MODAL);
					stage.setScene(new Scene(root));
					stage.show();
					
					main_menu_stage = (Stage) anchorPane.getScene().getWindow();
					main_menu_stage.close();
						
					
				} catch (Exception log_in_error) {
					log_in_error.printStackTrace();
				}
			
			
			} else {
				warning.setVisible(true);
				loginButton.setDisable(true);

			}
		});
		
		
		signupButton.setOnMouseClicked((MouseEvent event) -> {
			try {
				RegistrationController.setValues(manager_object);
				FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
				Parent root = (Parent) loader.load();
				this.registration_controller = new RegistrationController();
				this.registration_controller = loader.getController();
				
				Stage stage = new Stage();
				stage.setAlwaysOnTop(true);
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setScene(new Scene(root));
				stage.show();
				
				// ---> To close the menu window
				
				main_menu_stage = (Stage) anchorPane.getScene().getWindow();
				main_menu_stage.close();
			
			} catch(IOException sign_in_error) {
				sign_in_error.printStackTrace();
			}
			
		});
		
	}
	
	/* verify the original password and encrypted password */  
	Boolean Check_encrypted_password(String password) {
		
		List<String> pass = manager_object.Get_user_password(usernameField.getText());
        Boolean status = PasswordUtils.verifyUserPassword(password, pass.get(0), pass.get(1));  
        
        if(status==true)  {
            System.out.println("Password Matched");
            return true;
        } else  {
        	warning.setVisible(true);
            warningLabel.setText("ERROR! Incorrect password"); 
            loginButton.setDisable(true);
            return false;
        }
	    
	}
	
	
	@FXML
    void check_user_existence(MouseEvent event) throws IOException {
		
		if (!usernameField.getText().isEmpty() & !passwordField.getText().isEmpty()) {
			
			if(manager_object.Search_stored_user_by_username(usernameField.getText()) == -1) {
				warning.setVisible(true);
				warningLabel.setText("ERROR! Username doesn't exist");
				loginButton.setDisable(true);
				
			} else {
				warning.setVisible(false);
				loginButton.setDisable(false);
			}
			
		} else {
			warning.setVisible(true);
			warningLabel.setText("ERROR! Fields are empty");
			loginButton.setDisable(true);
		}
	}
	

	@FXML
    void close_window(MouseEvent event) {
		main_menu_stage = (Stage) menuPane.getScene().getWindow();
		main_menu_stage.close();
    }
	
	
}
