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
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Deusto
 */
public class DeleteAccountController implements Initializable {

    @FXML
    private Label LabelUsername;
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
        LabelUsername.setText(profile.getUsername());
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

        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete account");
        alert.setHeaderText("Are you sure you want to delete your account?");
        alert.setContentText("This action cannot be undone..");

        // Mostrar el alert y esperar la respuesta del usuario
        java.util.Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            // Aquí va la lógica para eliminar la cuenta
            try {
                String user, password;
                user = LabelUsername.getText();
                password = TextFieldPassword.getText();
                cont.dropOutUser(user, password);
                // Mostrar un mensaje de éxito
                javafx.scene.control.Alert success = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                success.setTitle("Deleted account");
                success.setHeaderText(null);
                success.setContentText("Your account has been successfully deleted.");
                success.showAndWait();
                try {
                    javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/LogInWindow.fxml"));
                    javafx.scene.Parent root = fxmlLoader.load();

                    view.LogInWindowController controllerWindow = fxmlLoader.getController();
                    javafx.stage.Stage stage = new javafx.stage.Stage();
                    stage.setScene(new javafx.scene.Scene(root));
                    stage.show();
                    Stage currentStage = (Stage) Button_Delete.getScene().getWindow();
                    currentStage.close();

                } catch (IOException ex) {
                    Logger.getLogger(DeleteAccountController.class.getName()).log(Level.SEVERE, null, ex);
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
