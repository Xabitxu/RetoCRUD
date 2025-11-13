/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import exception.passwordequalspassword;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Profile;
import model.User;

/**
 * FXML Controller class for modifying a user's profile.
 */
public class ModifyWindowController implements Initializable {

    @FXML
    private Label LabelUsername; // Label showing current username
    @FXML
    private Label LabelEmail; // Label showing current email
    @FXML
    private TextField TextField_Name; // Field to modify name
    @FXML
    private TextField TextField_Surname; // Field to modify surname
    @FXML
    private TextField TextField_Telephone; // Field to modify telephone
    @FXML
    private TextField TextField_NewPass; // Field to enter new password
    @FXML
    private TextField TextField_CNewPass; // Field to confirm new password
    @FXML
    private RadioButton RadioButton_Man; // Radio button for male
    @FXML
    private RadioButton RadioButton_Woman; // Radio button for female
    @FXML
    private RadioButton RadioButton_Other; // Radio button for other gender
    private ToggleGroup grupOp; // ToggleGroup for gender radio buttons
    @FXML
    private Button Button_Cancel; // Cancel button

    private Controller cont; // Controller instance for business logic
    private Profile profile; // Currently logged-in user

    // Set controller instance
    public void setCont(Controller cont) {
        this.cont = cont;
    }

    // Set current profile and populate labels
    public void setProfile(Profile profile) {
        this.profile = profile;
        LabelUsername.setText(profile.getUsername());
        LabelEmail.setText(profile.getEmail());
    }

    // Save changes button action
    @FXML
    private void save(ActionEvent event) throws passwordequalspassword {
        // Read all input values
        String name = TextField_Name.getText();
        String surname = TextField_Surname.getText();
        String telephone = TextField_Telephone.getText();
        String newPass = TextField_NewPass.getText();
        String cNewPass = TextField_CNewPass.getText();
        String gender = ((User) profile).getGender();
        String username = profile.getUsername();
        String email = profile.getEmail();

        // Update gender based on selected radio button
        if (RadioButton_Man.isSelected()) {
            gender = "Man";
        } else if (RadioButton_Woman.isSelected()) {
            gender = "Woman";
        } else if (RadioButton_Other.isSelected()) {
            gender = "Other";
        }

        // If fields are empty, keep current profile values
        if (name == null || name.equals("Insert your new name")) {
            name = profile.getName();
        }
        if (surname == null || surname.equals("Insert your new surname")) {
            surname = profile.getSurname();
        }
        if (telephone.equals("") || telephone.equals("Insert your new telephone")) {
            telephone = profile.getTelephone();
        }

        // Check if password fields are empty
        if (newPass.equals("") || cNewPass.equals("") || newPass.equals("New Password") || cNewPass.equals("Confirm New Password")) {
            newPass = profile.getPassword();
            cont.modificarUser(newPass, email, name, telephone, surname, username, gender);
        } else {
            // If passwords are not equal, throw exception
            if (!newPass.equals(cNewPass)) {
                throw new passwordequalspassword("No son iguales las contraseñas");
            } else {
                // If passwords match, modify user and return to menu
                if (cont.modificarUser(newPass, email, name, telephone, surname, username, gender)) {
                    try {
                        javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/MenuWindow.fxml"));
                        javafx.scene.Parent root = fxmlLoader.load();

                        view.MenuWindowController controllerWindow = fxmlLoader.getController();
                        controllerWindow.setUsuario(profile);
                        controllerWindow.setCont(this.cont);

                        Stage stage = new Stage();
                        stage.setScene(new javafx.scene.Scene(root));
                        stage.show();

                        Stage currentStage = (Stage) Button_Cancel.getScene().getWindow();
                        currentStage.close();

                    } catch (IOException ex) {
                        Logger.getLogger(MenuWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    // Cancel button action: returns to MenuWindow without saving
    @FXML
    private void cancel() {
        try {
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/MenuWindow.fxml"));
            javafx.scene.Parent root = fxmlLoader.load();

            view.MenuWindowController controllerWindow = fxmlLoader.getController();
            controllerWindow.setUsuario(profile);
            controllerWindow.setCont(this.cont);

            Stage stage = new Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();

            Stage currentStage = (Stage) Button_Cancel.getScene().getWindow();
            currentStage.close();

        } catch (IOException ex) {
            Logger.getLogger(MenuWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialization logic (if needed) can be added here
    }
}
