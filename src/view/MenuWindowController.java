/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import static com.sun.org.apache.bcel.internal.Repository.instanceOf;
import controller.Controller;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import model.Admin;
import model.Profile;
import model.User;

/**
 * FXML Controller class
 *
 * @author acer
 */
public class MenuWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button Button_Delete;
    @FXML
    private Button Button_Modify;
    @FXML
    private Button Button_LogOut;
    @FXML
    private Label label_Username;
    @FXML
    private Profile profile;
    @FXML
    private Controller cont;

    public MenuWindowController() {
    }

    public MenuWindowController(Button Button_Delete, Button Button_Modify, Button Button_LogOut, Label label_Username, Profile profile, Controller cont) {
        this.Button_Delete = Button_Delete;
        this.Button_Modify = Button_Modify;
        this.Button_LogOut = Button_LogOut;
        this.label_Username = label_Username;
        this.profile = profile;
        this.cont = cont;
    }

    public void setUsuario(Profile profile) {
        this.profile = profile;
        label_Username.setText(profile.getUsername());
    }

    public Controller getCont() {
        return cont;
    }

    public void setCont(Controller cont) {
        this.cont = cont;
    }

    @FXML
    private void modifyVentana(ActionEvent event) {
        try {
            javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/ModifyWindow.fxml"));
            javafx.scene.Parent root = fxmlLoader.load();

            view.ModifyWindowController controllerWindow = fxmlLoader.getController();
            //Generar un set usuario para poder tenero ahi y usarlo
            controllerWindow.setProfile(profile);
            controllerWindow.setCont(this.cont);
            javafx.stage.Stage stage = new javafx.stage.Stage();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
            Stage currentStage = (Stage) Button_Modify.getScene().getWindow();
            currentStage.close();

        } catch (IOException ex) {
            Logger.getLogger(MenuWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void delete() {
        if (this.profile instanceof User) {

            try {
                javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/DeleteAccount.fxml"));
                javafx.scene.Parent root = fxmlLoader.load();

                view.DeleteAccountController controllerWindow = fxmlLoader.getController();
                //Generar un set usuario para poder tenero ahi y usarlo
                controllerWindow.setProfile(profile);
                controllerWindow.setCont(this.cont);
                javafx.stage.Stage stage = new javafx.stage.Stage();
                stage.setScene(new javafx.scene.Scene(root));
                stage.show();
                Stage currentStage = (Stage) Button_Delete.getScene().getWindow();
                currentStage.close();

            } catch (IOException ex) {
                Logger.getLogger(MenuWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(this.profile instanceof Admin){
            try {
                javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/DeleteAccountAdmin.fxml"));
                javafx.scene.Parent root = fxmlLoader.load();

                view.DeleteAccountAdminController controllerWindow = fxmlLoader.getController();
                //Generar un set usuario para poder tenero ahi y usarlo
                controllerWindow.setProfile(profile);
                controllerWindow.setCont(this.cont);
                controllerWindow.setComboBoxUser();
                javafx.stage.Stage stage = new javafx.stage.Stage();
                stage.setScene(new javafx.scene.Scene(root));
                stage.show();
                Stage currentStage = (Stage) Button_Delete.getScene().getWindow();
                currentStage.close();

            } catch (IOException ex) {
                Logger.getLogger(MenuWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
