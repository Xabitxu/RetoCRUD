/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewText;

import controller.Controller;
import java.util.concurrent.TimeoutException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.DBImplementation;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.api.FxAssert;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import view.SignUpWindowController;

/**
 * Integration test for SignUpWindowController using TestFX.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SignUpTest extends ApplicationTest {

    @BeforeClass
    public static void setUpClass() throws TimeoutException {
        // Inicializa JavaFX para todos los tests
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(main.Main.class); // Se puede cambiar a Main que abre Login o vacío
    }

    @Override
    public void stop() throws Exception {
        // TestFX cierra las ventanas después de cada test automáticamente
    }

    /**
     * Abre la ventana SignUp de manera independiente para cada test.
     */
    @Test
    public void test_SignUpWindow_OpensIndependently() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignUpWindow.fxml"));
        Parent root = loader.load();

        SignUpWindowController controller = loader.getController();
        controller.setCont(new Controller(new DBImplementation()));

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

        verifyThat("#SignUpRoot", isVisible());
    }

    /**
     * Comprueba que los campos estén vacíos al abrir SignUp.
     */
    @Test
    public void test1_InitialState() throws Exception {
        openSignUpWindow();

        FxAssert.verifyThat("#textFieldEmail", hasText(""));
        FxAssert.verifyThat("#textFieldName", hasText(""));
        FxAssert.verifyThat("#textFieldSurname", hasText(""));
        FxAssert.verifyThat("#textFieldTelephone", hasText(""));
        FxAssert.verifyThat("#textFieldCardN", hasText(""));
        FxAssert.verifyThat("#textFieldPassword", hasText(""));
        FxAssert.verifyThat("#textFieldCPassword", hasText(""));
        FxAssert.verifyThat("#textFieldUsername", hasText(""));
        FxAssert.verifyThat("#buttonSignUp", isDisabled()); // si tu app deshabilita botón al inicio
    }

    /**
     * Comprueba que al pulsar LogIn abre la ventana Login.
     */
    @Test
    public void test2_LogInButton_OpensLogin() throws Exception {
        openSignUpWindow();

        clickOn("#buttonLogIn");
        FxAssert.verifyThat("#TextField_Username", isVisible()); // verifica ventana Login
    }

    /**
     * Flujo de registro: contraseña incorrecta → error → corregir → registro
     * exitoso.
     */
    @Test
    public void test3_SignUpFlow_IncorrectPasswordThenCorrect() throws Exception {
        openSignUpWindow();

        clickOn("#textFieldEmail").write("alex@example.com");
        clickOn("#textFieldName").write("Alex");
        clickOn("#textFieldSurname").write("Doe");
        clickOn("#textFieldTelephone").write("123456789");
        clickOn("#textFieldCardN").write("1234123412341234");
        clickOn("#textFieldUsername").write("alexd");
        clickOn("#textFieldPassword").write("pass1");
        clickOn("#textFieldCPassword").write("pass2"); // contraseñas distintas
        clickOn("#rButtonM"); // selecciona género

        // Pulsar signUp → espera excepción (puedes capturar)
        try {
            clickOn("#buttonSignUp");
        } catch (Exception e) {
            // Esperado: passwordequalspassword
        }

        // Corrige contraseñas
        clickOn("#textFieldPassword").eraseText(5).write("pass123");
        clickOn("#textFieldCPassword").eraseText(5).write("pass123");

        // Pulsar signUp de nuevo
        clickOn("#buttonSignUp");

        // Verifica que se abre menú principal
        FxAssert.verifyThat("#MenuRoot", isVisible());
    }
}
