/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import exception.passwordequalspassword;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import model.Profile;

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
    private RadioButton rButtonM;
    @FXML
    private RadioButton rButtonW;
    @FXML
    private RadioButton rButtonO;
    private ToggleGroup grupOp;
    @FXML
    private Button Button_Cancel;
    @FXML
    private Button Button_SaveChanges;

    private Controller cont;
    private Profile profile;

    public void setCont(Controller cont) {
        this.cont = cont;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public ModifyWindowController() {
        String username = profile.getUsername();
        LabelUsername.setText(username);
        String email = profile.getEmail();
        LabelUsername.setText(email);

    }

    @FXML
    private void save(ActionEvent event) throws passwordequalspassword {
        String name = TextField_Name.getText();
        String surname = TextField_Surname.getText();
        String telephone = TextField_Telephone.getText();
        String newPass = TextField_NewPass.getText();
        String cNewPass = TextField_CNewPass.getText();
        String gender = null;
        String username;
        String email;
        if (rButtonM.isSelected()) {
            gender = "Man";
        } else {
            if (rButtonW.isSelected()) {
                gender = "Woman";
            } else {
                if (rButtonO.isSelected()) {
                    gender = "Other";
                }
            }
        }
        username = profile.getUsername();

        email = profile.getEmail();

        if (name == null) {
            name = profile.getName();
        }
        if (surname == null) {
            surname = profile.getSurname();
        }
        if (telephone == null) {
            telephone = profile.getTelephone();
        }
        if (newPass == null || cNewPass == null) {
            newPass = profile.getPassword();
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
