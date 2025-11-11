/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;

import exception.passwordequalspassword;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import model.Profile;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Deusto
 */
public class DeleteAccountAdminController implements Initializable {

    @FXML
    private ComboBox<String> ComboBoxUser;
    @FXML
    private TextField TextFieldPassword;
    private Controller cont;
    private Profile profile;
    @FXML
    private Button Button_Cancel;
    @FXML
    private Button Button_Delete;

    public void setCont(Controller cont) {
        this.cont = cont;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setComboBoxUser() {
        this.ComboBoxUser = ComboBoxUser;
        ComboBoxUser.setItems((ObservableList<String>) cont.comboBoxInsert());
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

    @FXML
    private void delete() {
        if (!TextFieldPassword.getText().equals("")) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete account");
            alert.setHeaderText("Are you sure you want to delete your account?");
            alert.setContentText("This action cannot be undone..");

            java.util.Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
                // Aquí va la lógica para eliminar la cuenta
                try {
                    //ComboBoxUser.setItems((ObservableList<String>) cont.comboBoxInsert());
                    String user, password;
                    user = ComboBoxUser.getValue();
                    password = TextFieldPassword.getText();
                    cont.dropOutAdmin(user, password);
                    // Mostrar un mensaje de éxito
                    javafx.scene.control.Alert success = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                    success.setTitle("Deleted account");
                    success.setHeaderText(null);
                    success.setContentText("Your account has been successfully deleted.");
                    success.showAndWait();
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

                } catch (Exception ex) {
                    ex.printStackTrace();
                    javafx.scene.control.Alert error = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                    error.setTitle("Error");
                    error.setHeaderText("The account could not be deleted.");
                    error.setContentText(ex.getMessage());
                    error.showAndWait();
                }
            } else {
                // Si el usuario cancela, no hacer nada
                System.out.println("Deletion cancelled by the user.");
                try {
                    javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/MenuWindow.fxml"));
                    javafx.scene.Parent root = fxmlLoader.load();

                    view.MenuWindowController controllerWindow = fxmlLoader.getController();
                    //Generar un set usuario para poder tenero ahi y usarlo
                    controllerWindow.setUsuario(profile);
                    controllerWindow.setCont(cont);
                    javafx.stage.Stage stage = new javafx.stage.Stage();
                    stage.setScene(new javafx.scene.Scene(root));
                    stage.show();
                    Stage currentStage = (Stage) Button_Delete.getScene().getWindow();
                    currentStage.close();

                } catch (IOException ex) {
                    Logger.getLogger(LogInWindowController.class.getName()).log(Level.SEVERE, null, ex);
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
