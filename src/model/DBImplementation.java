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
 * Implementation of ClassDAO using database operations. Handles all database
 * interactions for the application.
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
    //Inserts  (New user sing up) Metodos Hechos
    final String SQLSINGUPPROFILE = "INSERT INTO PROFILE_ (USERNAME, PASSWORD, EMAIL, USER_CODE, NAME_, TELEPHONE, SURNAME) VALUES (?,?,?,?,?,?,?);";
    final String SQLSIGNUPUSER = "INSERT INTO USER_ (USERNAME, GENDER, CARD_NUMBER) VALUES (?,?,?);";
    //final String SQLSIGNUPADMIN = "INSERT INTO ADMIN_ (USERNAME, CURRENT_ACCOUNT) VALUES (?,?);";
    //Delete (Drop out)
    final String SQLDELETEUSER = "DELETE * FROM USER_ WHERE USERNAME = ?;";
    final String SQLADMIN = "DELETE * FROM ADMIN_ WHERE USERNAME = ?;";
    final String SLQPROFILE = "DELETE * FROM PROFILE_ WHERE USERNAME = ?;";
    //Log In Metodos hechos
    final String SLQLOGINUSER = "SELECT p.*, u.GENDER, u.CARD_NUMBER FROM PROFILE_ p JOIN USER_ u ON p.USERNAME= u.USERNAME WHERE u.USERNAME = ? AND p.PASSWORD_ = ?;";
    final String SLQLOGINADMIN = "SELECT p.*, a.CURRENT_ACCOUNT FROM PROFILE_ p JOIN ADMIN_ a ON p.USERNAME= a.USERNAME WHERE a.USERNAME = ? AND p.PASSWORD_ = ?;";

    //
    //TODO falta poner que cuando alguien se conecte y se desconecte la conexion de
    // peticion de la bd tiene que estar 30 seg mas encendida para que coinicidan 2
    //peticiones
    /**
     * Opens a database connection.
     */
    private void openConnection() {       
        try (Connection con = ConnectionPool.getDataSource().getConnection()) {
            System.out.println("Conexión abierta correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al abrir la conexión con la BD:");
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

    @Override
    public Profile logIn(String username, String password) {
        this.openConnection();
        try {
            stmt = con.prepareStatement(SLQLOGINUSER);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet result = stmt.executeQuery();
            if (!(result.next())) {
                stmt = con.prepareStatement(SLQLOGINADMIN);
                stmt.setString(1, username);
                stmt.setString(2, password);
                result = stmt.executeQuery();
                if (result.next()) {
                    Admin profile_admin = new Admin();
                    profile_admin.setUsername(result.getString("USERNAME"));
                    profile_admin.setPassword(result.getString("PASSWORD_"));
                    profile_admin.setEmail(result.getString("EMAIL"));
                    profile_admin.setUserCode(result.getInt("USER_CODE"));
                    profile_admin.setName(result.getString("NAME_"));
                    profile_admin.setTelephone(result.getString("TELEPHONE"));
                    profile_admin.setSurname(result.getString("SURNAME"));
                    profile_admin.setCurrentAccount(result.getString("CURRENT_ACCOUNT"));
                    return profile_admin;
                } else {
                    System.out.println("Usuario encontrado en la base de datos");
                }
            } else {
                User profile_user = new User();
                profile_user.setUsername(result.getString("USERNAME"));
                profile_user.setPassword(result.getString("PASSWORD_"));
                profile_user.setEmail(result.getString("EMAIL"));
                profile_user.setUserCode(result.getInt("USER_CODE"));
                profile_user.setName(result.getString("NAME_"));
                profile_user.setTelephone(result.getString("TELEPHONE"));
                profile_user.setSurname(result.getString("SURNAME"));
                profile_user.setGender(result.getString("GENDER"));
                profile_user.setCardNumber(result.getString("CARD_NUMBER"));
                return profile_user;
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta a la base de datos");
            e.printStackTrace();
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión a la BD");
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public Boolean signUp(String gender, String cardNumber, String username, String password, String email, int userCode, String name, String telephone, String surname) {
        this.openConnection();
        try {
            stmt = con.prepareStatement(SQLSINGUPPROFILE);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setInt(4, userCode);
            stmt.setString(5, name);
            stmt.setString(6, telephone);
            stmt.setString(7, surname);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                stmt = con.prepareStatement(SQLSIGNUPUSER);
                stmt.setString(1, username);
                stmt.setString(2, gender);
                stmt.setString(3, cardNumber);
                result = stmt.executeQuery();
                if (result.next()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta a la base de datos");
            e.printStackTrace();
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión a la BD");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean dropOutUser(String username) {
        this.openConnection();
        try {
            stmt = con.prepareStatement(SQLDELETEUSER);
            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                stmt = con.prepareStatement(SLQPROFILE);
                stmt.setString(1, username);
                result = stmt.executeQuery();
                if (result.next()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta a la base de datos");
            e.printStackTrace();
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión a la BD");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean dropOutAdmin(String username) {
        this.openConnection();
        try {
            stmt = con.prepareStatement(SQLADMIN);
            stmt.setString(1, username);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                stmt = con.prepareStatement(SLQPROFILE);
                stmt.setString(1, username);
                result = stmt.executeQuery();
                if (result.next()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta a la base de datos");
            e.printStackTrace();
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión a la BD");
            e.printStackTrace();
        }
        return null;
    }

}
