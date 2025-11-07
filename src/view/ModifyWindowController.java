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
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author acer
 */
public class ModifyWindowController implements Initializable {

    @FXML
    private TextField TextField_Username;
    @FXML
    private TextField TextField_Email;
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

    public void setCont(Controller cont) {
        this.cont = cont;
    }

    @FXML
    private void save(ActionEvent event) throws passwordequalspassword {
        String Username = TextField_Username.getText();
        String Email = TextField_Email.getText();
        String Name = TextField_Name.getText();
        String Surname = TextField_Surname.getText();
        String Telephone = TextField_Telephone.getText();
        String NewPass = TextField_NewPass.getText();
        String CNewPass = TextField_CNewPass.getText();
        String gender = null;
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
        if()
        if (!NewPass.equals(CNewPass)) {
                throw new passwordequalspassword("No son iguales las contraseñas");
            } else {
                if(cont.)){
                    System.out.println("Si");
                }else{
                System.out.println("no");}
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
