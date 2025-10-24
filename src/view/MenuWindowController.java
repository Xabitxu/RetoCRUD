/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private void handleButtonAction(ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        
        if (sourceButton == Button_Delete) {
        }
        
        if (sourceButton == Button_Modify) {
        }
        
        if (sourceButton == Button_LogOut) {
            Boolean logOutSuccess = false;
            
            if (profile instanceof User) {
                logOutSuccess = cont.dropOutUser(profile.getUsername());
            } else if (profile instanceof Admin) {
                logOutSuccess = cont.dropOutAdmin(profile.getUsername());
            }
            
            if (logOutSuccess) {
                System.out.println("Logout exitoso");
                try {
                    // Cargar LogInWindow
                    javafx.fxml.FXMLLoader fxmlLoader = new javafx.fxml.FXMLLoader(getClass().getResource("/view/LogInWindow.fxml"));
                    javafx.scene.Parent root = fxmlLoader.load();

                    javafx.stage.Stage stage = new javafx.stage.Stage();
                    stage.setScene(new javafx.scene.Scene(root));
                    stage.show();

                    // Cerrar ventana 
                    Stage currentStage = (Stage) Button_LogOut.getScene().getWindow();
                    currentStage.close();

                } catch (IOException ex) {
                    Logger.getLogger(MenuWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Error al salir");
            }
        }
    }    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
