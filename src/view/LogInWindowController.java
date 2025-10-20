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
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import model.DBImplementation;
import model.Profile;

/**
 * FXML Controller class
 *
 * @author Deusto
 */
public class LogInWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField TextField_Username;
    @FXML
    private PasswordField PasswordField_Password;
    @FXML
    private Button Button_LogIn;
    @FXML
    private Button Button_SignUp;

    private Controller cont = new Controller(new DBImplementation());

    @FXML
    private void handleButtonAction(ActionEvent event) {
        //Object source = event.getSource();
        Button sourceButton = (Button) event.getSource();
        String username = TextField_Username.getText();
        String password = PasswordField_Password.getText();

        if (sourceButton == Button_LogIn) {
            Profile profile = cont.logIn(username, password);
            if (profile != null) {
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
                    Stage currentStage = (Stage) Button_LogIn.getScene().getWindow();
                    currentStage.close();

                } catch (IOException ex) {
                    Logger.getLogger(LogInWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                System.out.println("No esta");
            }

        }
        if (sourceButton == Button_SignUp) {

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
