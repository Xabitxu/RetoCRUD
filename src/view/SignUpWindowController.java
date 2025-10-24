/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import model.DBImplementation;

/**
 * FXML Controller class
 *
 * @author Deusto
 */
public class SignUpWindowController implements Initializable {

	/**
	 * Initializes the controller class.
	 */
	@FXML
	private TextField TextField_Email;
	@FXML
	private TextField TextField_Username;
	@FXML
	private TextField TextField_Name;
	@FXML
	private TextField TextField_Surname;
	@FXML
	private TextField TextField_Telephone;
	@FXML
	private TextField TextField_CardNumber;
	@FXML
	private TextField TextField_Password;
	@FXML
	private TextField TextField_ConfirmPassword;
	@FXML
	private RadioButton RadioButton_Man;
	@FXML
	private RadioButton RadioButton_Woman;
	@FXML
	private RadioButton RadioButton_Other;
	@FXML
	private Button Button_SignUp;
	@FXML
	private Button Button_LogIn;

	private Controller cont = new Controller(new DBImplementation());

	@FXML
	private void handleButtonAction(ActionEvent event) {
		Button sourceButton = (Button) event.getSource();
		
		if (sourceButton == Button_SignUp) {
			String email = TextField_Email.getText();
			String username = TextField_Username.getText();
			String name = TextField_Name.getText();
			String surname = TextField_Surname.getText();
			String telephone = TextField_Telephone.getText();
			String cardNumber = TextField_CardNumber.getText();
			String password = TextField_Password.getText();
			String confirmPassword = TextField_ConfirmPassword.getText();
			String gender = getSelectedGender();

			// Validar contraseñas
			if (!password.equals(confirmPassword)) {
				System.out.println("Las contraseñas no coinciden");
				return;
			}

			// Validar todos los campos
			if (email.isEmpty() || username.isEmpty() || name.isEmpty() || surname.isEmpty()
					|| telephone.isEmpty() || cardNumber.isEmpty() || password.isEmpty() || gender == null) {
				System.out.println("Por favor, completa todos los campos");
				return;
			}

			// Registrarse
			Boolean signUpSuccess = cont.signUp(gender, cardNumber, username, password, email, 0, name, telephone, surname);
			if (signUpSuccess) {
				System.out.println("Registro exitoso");
				try {
					javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/LogInWindow.fxml"));
					javafx.scene.Parent root = fxmlLoader.load();

					javafx.stage.Stage stage = new javafx.stage.Stage();
					stage.setScene(new javafx.scene.Scene(root));
					stage.show();
					
					// Cerrar ventana
					Stage currentStage = (Stage) Button_SignUp.getScene().getWindow();
					currentStage.close();

				} catch (IOException ex) {
					Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
				}
			} else {
				System.out.println("Error al registrarse");
			}
		}
		
		if (sourceButton == Button_LogIn) {
			try {
				javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/LogInWindow.fxml"));
				javafx.scene.Parent root = fxmlLoader.load();

				javafx.stage.Stage stage = new javafx.stage.Stage();
				stage.setScene(new javafx.scene.Scene(root));
				stage.show();

				// Cerrar ventana
				Stage currentStage = (Stage) Button_LogIn.getScene().getWindow();
				currentStage.close();

			} catch (IOException ex) {
				Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	private String getSelectedGender() {
		if (RadioButton_Man.isSelected()) {
			return "Man";
		} else if (RadioButton_Woman.isSelected()) {
			return "Woman";
		} else if (RadioButton_Other.isSelected()) {
			return "Other";
		}
		return null;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

}
