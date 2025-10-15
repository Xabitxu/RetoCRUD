package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

/**
 * Implementation of ClassDAO using database operations.
 * Handles all database interactions for the application.
 *
 * @author acer
 */
public class DBImplementation implements ClassDAO {

    private Connection con;
    private PreparedStatement stmt;

    // The following attributes are used to retrieve values from the config file
    private ResourceBundle configFile;
    private String driverDB;
    private String urlDB;
    private String userDB;
    private String passwordDB;

    // SQL statements
    //Inserts  (New user sing up)
    final String SQLSINGUPPROFILE = "INSERT INTO PROFILE_ (USERNAME, PASSWORD, EMAIL, USER_CODE, NAME_, TELEPHONE, SURNAME) VALUES (?,?,?,?,?,?,?);";
    final String SQSIGNUPUSER = "INSERT INTO USER_ (USERNAME, GENDER, CARD_NUMBER) VALUES (?,?,?);";
    final String SQLSIGNUPADMIN = "INSERT INTO ADMIN_ (USERNAME, CURRENT_ACCOUNT) VALUES (?,?);";
    //Delete (Drop out)
    final String SQLDELETEUSER = "DELETE * FROM USER_ WHERE USERNAME = ?;";
    final String SQLADMIN = "DELETE * FROM ADMIN_ WHERE USERNAME = ?;";
    final String SLQPROFILE = "DELETE * FROM PROFILE_ WHERE USERNAME = ?;";
    //Log In 
    final String SLQLOGINUSER = "SELECT p.*, u.GENDER, u.CARD_NUMBER FROM PROFILE_ p JOIN USER_ u ON p.USERNAME= u.USERNAME WHERE u.USERNAME = ?;";
    final String SLQLOGINADMIN = "SELECT p.*, a.GENDER, a.CARD_NUMBER FROM PROFILE_ p JOIN ADMIN_ a ON p.USERNAME= a.USERNAME WHERE a.USERNAME = ?;";

    /**
     * Opens a database connection.
     */
    private void openConnection() {
        try {
            con = DriverManager.getConnection(urlDB, this.userDB, this.passwordDB);
        } catch (SQLException e) {
            System.out.println("Error trying to open the DB");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor that initializes the database configuration.
     */
    public DBImplementation() {
        this.configFile = ResourceBundle.getBundle("model.configClass");
        this.driverDB = this.configFile.getString("Driver");
        this.urlDB = this.configFile.getString("Conn");
        this.userDB = this.configFile.getString("DBUser");
        this.passwordDB = this.configFile.getString("DBPass");
    }

    
}
