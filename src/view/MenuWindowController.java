/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Profile;

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
        Object source = event.getSource();
        Button sourceButton = (Button) event.getSource();
        if (source == Button_Delete) {
        }
        if (source == Button_Modify) {
        }
        if (source == Button_LogOut) {
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
