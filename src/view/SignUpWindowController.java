/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import javafx.scene.control.ToggleGroup;

import exception.passwordequalspassword;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Profile;

/**
 * FXML Controller class
 *
 * @author alexd
 */
public class SignUpWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldSurname;
    @FXML
    private TextField textFieldTelephone;
    @FXML
    private TextField textFieldCardN;
    @FXML
    private TextField textFieldPassword;
    @FXML
    private TextField textFieldCPassword;
    @FXML
    private TextField textFieldUsername;
    @FXML
    private RadioButton rButtonM;
    @FXML
    private RadioButton rButtonW;
    @FXML
    private RadioButton rButtonO;
    @FXML
    private Button buttonSignUp;
    @FXML
    private Button buttonLogIn;
    private Controller cont;

    private ToggleGroup grupOp;

    public void setCont(Controller cont) {
        this.cont = cont;
    }

    public SignUpWindowController() {
    }

    @FXML
    private void login() {
        try {
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/LogInWindow.fxml"));
            javafx.scene.Parent root = fxmlLoader.load();

            view.LogInWindowController controllerWindow = fxmlLoader.getController();
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
            Stage currentStage = (Stage) buttonLogIn.getScene().getWindow();
            currentStage.close();

        } catch (IOException ex) {
            Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void signup() throws passwordequalspassword {
        String email = textFieldEmail.getText();
        String name = textFieldName.getText();
        String surname = textFieldSurname.getText();
        String telephone = textFieldTelephone.getText();
        String cardN = textFieldCardN.getText();
        String pass = textFieldPassword.getText();
        String passC = textFieldCPassword.getText();
        String username = textFieldUsername.getText();
        String gender = null;

        if (rButtonM.isSelected()) {
            gender = "Man";
        } else if (rButtonW.isSelected()) {
            gender = "Woman";
        } else if (rButtonO.isSelected()) {
            gender = "Other";
        }

        if (!pass.equals(passC)) {
            throw new passwordequalspassword("No son iguales las contraseñas");
        } else {
            if (cont.signUp(gender, cardN, username, pass, email, name, telephone, surname)) {
                Profile profile = cont.logIn(username, pass);
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MenuWindow.fxml"));
                    Parent root = fxmlLoader.load();

                    view.MenuWindowController controllerWindow = fxmlLoader.getController();
                    controllerWindow.setUsuario(profile);
                    controllerWindow.setCont(this.cont);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                    Stage currentStage = (Stage) buttonSignUp.getScene().getWindow();
                    currentStage.close();

                } catch (IOException ex) {
                    Logger.getLogger(SignUpWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grupOp = new ToggleGroup();
        rButtonM.setToggleGroup(grupOp);
        rButtonW.setToggleGroup(grupOp);
        rButtonO.setToggleGroup(grupOp);
    }

}
