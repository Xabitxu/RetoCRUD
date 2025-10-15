
package main;
import controller.Controller;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.Utilities;

/**
 * Main class for the Colegio Reto application.
 * This class provides the main menu and handles user interactions for managing teaching units, exam calls, and statements.
 *
 * @author Deusto
 */
public class Main extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LogInWindow.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Login Application");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);        
    }
    
}
