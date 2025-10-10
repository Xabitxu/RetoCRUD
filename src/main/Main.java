package colegioreto;

import controller.Controller;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import utilities.Utilities;

/**
 * Main class for the Colegio Reto application.
 * This class provides the main menu and handles user interactions for managing teaching units, exam calls, and statements.
 *
 * @author Deusto
 */
public class Main {

    /**
     * Displays the main menu options and reads the user's choice.
     * @return the selected menu option as an integer
     */
    public static int menu() {
        int op;
        System.out.println("------------------------------------");
        System.out.println("1. Create a teaching UNITS .");
        System.out.println("2. Create an EXAMCALL (session).");
        System.out.println("3. Create an STATEMENT by adding the UNITS it will refer to. The EXAMCALL for which it is created will also be associated.");
        System.out.println("4. Consult the STATEMENTS in which a specific UNIT is covered.");
        System.out.println("5. Consult in which EXAMCALL a specific STATEMENTS has been used.");
        System.out.println("6. View the text document associated with a STATEMENTS.");
        System.out.println("7. Assign a STATEMENTS to a EXAMCALL.");
        System.out.println("8. Exit");
        System.out.println("------------------------------------");
        op = Utilities.leerInt(1, 8);
        return op;
    }
    
    public static void main(String[] args) {
        int op;
        Controller cont = new Controller();
        do {
            switch (op = menu()) {
                case 1:
                    
                    break;
                case 2:
                    
                    break;
                case 3:
                    
                    break;
                case 4:
                    
                    break;
                case 5:
                    
                    break;
                case 6:
                    
                    break;
                case 7:
                    
                    break;
                case 8:
                    System.out.println("Bye, Bye :)");
                    break;
            }
        } while (op != 8);
    }
}
