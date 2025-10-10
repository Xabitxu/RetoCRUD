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
    final String SQLSELECTIIDS = "SELECT ID_S FROM TEACHINGUNIT_STATEMENT WHERE ID_T = ?";
    final String SQLSHOWSTATEMENTS = "SELECT * FROM STATEMENT WHERE ID_S = ?"; //hacer
    final String SQLADDTEACHINGUNIT = "INSERT INTO TEACHINGUNIT (ACRONYM, TITLE, ASSESSMENT, DESCRIPTION_T) VALUES(?,?,?,?)";
    final String SQLINSERTEXAMCALL = "INSERT INTO EXAMCALL VALUES(?,?,?,?,?)";
    final String SQL_CALL = "SELECT * FROM EXAMCALL WHERE ID_S = ?";
    final String SQL_VIEWTEXT = "SELECT DESCRIPTION_S, ID_S FROM STATEMENT WHERE ID_S = ?";
    final String SQL_INSERT = "INSERT INTO EXAMCALL (CALL_EXAM, DESCRIPTION_EXAM, DATE_EXAM, COURSE, ID_S) VALUES (?, ?, ?, ?, ?)";
    final String SQL_CREATESTATEMENT = "INSERT INTO STATEMENT (ID_S,DESCRIPTION_S,LEVEL_S,AVAILABLE,ROUTE) VALUES (?,?,?,?,?)";
    final String SQL_ADDUNIT = "INSERT INTO TEACHINGUNIT_STATEMENT (ID_T,ID_S) VALUES (?,?)";
    final String SQL_CREATEEXAMCALL = "INSERT INTO EXAMCALL (CALL_EXAM,DESCRIPTION_EXAM,DATE_EXAM,COURSE,ID_S) VALUES (?,?,?,?,?)";

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
