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
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Profile;
import model.User;

/**
 * FXML Controller class
 *
 * @author acer
 */
public class ModifyWindowController implements Initializable {

    @FXML
    private Label LabelUsername;
    @FXML
    private Label LabelEmail;
    @FXML
    private TextField TextField_Name;
    @FXML
    private TextField TextField_Surname;
    @FXML
    private TextField TextField_Telephone;
    @FXML
    private TextField TextField_NewPass;
    @FXML
    private TextField TextField_CNewPass;
    @FXML
    private RadioButton RadioButton_Man;
    @FXML
    private RadioButton RadioButton_Woman;
    @FXML
    private RadioButton RadioButton_Other;
    private ToggleGroup grupOp;
    @FXML
    private Button Button_Cancel;


    private Controller cont;
    private Profile profile;

    public void setCont(Controller cont) {
        this.cont = cont;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;

        LabelUsername.setText(profile.getUsername());
        LabelEmail.setText(profile.getEmail());
    }

    @FXML
    private void save(ActionEvent event) throws passwordequalspassword {
        String name = TextField_Name.getText();
        String surname = TextField_Surname.getText();
        String telephone = TextField_Telephone.getText();
        String newPass = TextField_NewPass.getText();
        String cNewPass = TextField_CNewPass.getText();
        String gender = ((User) profile).getGender();
        String username;
        String email;
        if (RadioButton_Man.isSelected()) {
            gender = "Man";
        } else {
            if (RadioButton_Woman.isSelected()) {
                gender = "Woman";
            } else {
                if (RadioButton_Other.isSelected()) {
                    gender = "Other";
                }
            }
        }
        username = profile.getUsername();

        email = profile.getEmail();

        if (name == null || name.equals("Insert your new name")) {
            name = profile.getName();
        }
        if (surname == null || surname.equals("Insert your new surname")) {
            surname = profile.getSurname();
        }
        if (telephone.equals("") || telephone.equals("Insert your new telephone")) {
            telephone = profile.getTelephone();
        }
        if (newPass.equals("") || cNewPass.equals("") || newPass.equals("New Password") || cNewPass.equals("Confirm New Password")) {
            newPass = profile.getPassword();
            cont.modificarUser(newPass, email, name, telephone, surname, username, gender);
        } else {
            if (!newPass.equals(cNewPass)) {

                throw new passwordequalspassword("No son iguales las contraseñas");
            } else {
                if (cont.modificarUser(newPass, email, name, telephone, surname, username, gender)) {
                    System.out.println("Si");
                } else {
                    System.out.println("no");
                }
            }
        }
    }

    @FXML
    private void cancel() {
        try {
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/MenuWindow.fxml"));
            javafx.scene.Parent root = fxmlLoader.load();

            view.MenuWindowController controllerWindow = fxmlLoader.getController();
            //Generar un set usuario para poder tenero ahi y usarlo
            controllerWindow.setUsuario(profile);
            controllerWindow.setCont(this.cont);
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
            Stage currentStage = (Stage) Button_Cancel.getScene().getWindow();
            currentStage.close();

        } catch (IOException ex) {
            Logger.getLogger(MenuWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
