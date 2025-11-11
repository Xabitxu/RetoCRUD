/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author Deusto
 */
public class passwordequalspassword extends Exception {

    public passwordequalspassword(String text) {
        super(text);
        showPopup(text);
    }

    private void showPopup(String message) {
        // Crear una alerta de tipo error
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Password error");
        alert.setHeaderText("Invalid password");
        alert.setContentText(message);

        alert.showAndWait();
    }
}
