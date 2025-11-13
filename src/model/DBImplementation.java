package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ConnectionPool;
import threads.HiloConnection;

/**
 * Implementation of ClassDAO using database operations. Handles all database
 * interactions for the application.
 *
 * @author acer
 */
public class DBImplementation implements ClassDAO {

    //private Connection con;
    private PreparedStatement stmt;

    // The following attributes are used to retrieve values from the config file
    private ResourceBundle configFile;
    private String driverDB;
    private String urlDB;
    private String userDB;
    private String passwordDB;

    // SQL statements
    //Inserts  (New user sing up) Metodos Hechos
    final String SQLSINGUPPROFILE = "INSERT INTO PROFILE_ (USERNAME, PASSWORD_, EMAIL, NAME_, TELEPHONE, SURNAME) VALUES (?,?,?,?,?,?);";
    final String SQLSIGNUPUSER = "INSERT INTO USER_ (USERNAME, GENDER, CARD_NUMBER) VALUES (?,?,?);";
    //final String SQLSIGNUPADMIN = "INSERT INTO ADMIN_ (USERNAME, CURRENT_ACCOUNT) VALUES (?,?);";
    //Delete (Drop out)
    //final String SQLDELETEUSER = "DELETE FROM USER_u WHERE USERNAME = ? AND PASSWORD_ = ?;";
    //final String SQLDELETEADMIN = "DELETE * FROM ADMIN_ WHERE USERNAME = ? AND PASSWORD_ = ?;";
    final String SLQDELETEPROFILE = "DELETE FROM PROFILE_ WHERE USERNAME = ? AND PASSWORD_ = ?;";
    final String SLQDELETEPROFILEADMIN = "DELETE p FROM PROFILE_ p JOIN USER_ u ON p.USERNAME = u.USERNAME JOIN ADMIN_ a ON p.USERNAME = a.USERNAME WHERE p.PASSWORD_ = ? AND u.username = ?;";
    //Log In Metodos hechos
    final String SLQLOGINUSER = "SELECT p.*, u.GENDER, u.CARD_NUMBER FROM PROFILE_ p JOIN USER_ u ON p.USERNAME= u.USERNAME WHERE u.USERNAME = ? AND p.PASSWORD_ = ?;";
    final String SLQLOGINADMIN = "SELECT p.*, a.CURRENT_ACCOUNT FROM PROFILE_ p JOIN ADMIN_ a ON p.USERNAME= a.USERNAME WHERE a.USERNAME = ? AND p.PASSWORD_ = ?;";

    final String SQLMODIFYPROFILE = "UPDATE PROFILE_ P SET P.PASSWORD_ = ?, P.EMAIL = ?, P.NAME_ = ?, P.TELEPHONE = ?, P.SURNAME = ? WHERE USERNAME = ?;";
    final String SQLMODIFYUSER = "UPDATE USER_ U SET U.GENDER = ? WHERE USERNAME = ?";

    final String SLQSELECTNUSER = "SELECT u.USERNAME FROM USER_ u;";

    public DBImplementation() {
        this.configFile = ResourceBundle.getBundle("model.configClass");
        this.driverDB = this.configFile.getString("Driver");
        this.urlDB = this.configFile.getString("Conn");
        this.userDB = this.configFile.getString("DBUser");
        this.passwordDB = this.configFile.getString("DBPass");
    }

    @Override
    public Profile logIn(String username, String password) {
        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
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
                    System.out.println("Usuario no encontrado en la base de datos");
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
    public Boolean signUp(String gender, String cardNumber, String username, String password, String email, String name, String telephone, String surname) {
        HiloConnection conectionThread = new HiloConnection(30);
        conectionThread.start();
        boolean success = false;
        try {
            Connection con = waitForConnection(conectionThread);
            //INSERT INTO PROFILE_ (USERNAME, PASSWORD, EMAIL, NAME_, TELEPHONE, SURNAME) VALUES (?,?,?,?,?,?)
            stmt = con.prepareStatement(SQLSINGUPPROFILE);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, name);
            stmt.setString(5, telephone);
            stmt.setString(6, surname);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                stmt = con.prepareStatement(SQLSIGNUPUSER);
                stmt.setString(1, username);
                stmt.setString(2, gender);
                stmt.setString(3, cardNumber);
                rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    success = true;
                } else {
                    success = false;
                }
            } else {
                success = false;
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta a la base de datos");
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(DBImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                conectionThread.releaseConnection();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión a la BD");
                e.printStackTrace();
            }
        }
        return success;
    }

    @Override
    public Boolean dropOutUser(String username, String password) {
        HiloConnection conectionThread = new HiloConnection(30);
        conectionThread.start();
        boolean success = false;
        PreparedStatement stmtUser = null;
        try {
            Connection con = waitForConnection(conectionThread);
            
            // verificar que la password es correcta
            String checkPassword = "SELECT PASSWORD_ FROM PROFILE_ WHERE USERNAME = ?";
            stmt = con.prepareStatement(checkPassword);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String dbPassword = rs.getString("PASSWORD_");
                if (!dbPassword.equals(password)) {
                    return false;
                }
            } else {
                return false;
            }
            rs.close();
            stmt.close();
            
            // eliminar de USER_ primero
            String deleteUser = "DELETE FROM USER_ WHERE USERNAME = ?";
            stmtUser = con.prepareStatement(deleteUser);
            stmtUser.setString(1, username);
            stmtUser.executeUpdate();
            stmtUser.close();
            
            // luego eliminar de PROFILE_
            stmt = con.prepareStatement(SLQDELETEPROFILE);
            stmt.setString(1, username);
            stmt.setString(2, password);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                success = true;
            } else {
                success = false;
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta a la base de datos");
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(DBImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (stmtUser != null) {
                    stmtUser.close();
                }
                conectionThread.releaseConnection();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión a la BD");
                e.printStackTrace();
            }
        }
        return success;
    }

    @Override
    public Boolean dropOutAdmin(String usernameToDelete, String adminUsername, String adminPassword) {
        HiloConnection conectionThread = new HiloConnection(30);
        conectionThread.start();
        boolean success = false;
        PreparedStatement stmtDeleteUser = null;
        PreparedStatement stmtDeleteAdmin = null;
        try {
            Connection con = waitForConnection(conectionThread);
            
            // verificar password del admin logueado
            String checkAdminPassword = "SELECT PASSWORD_ FROM PROFILE_ WHERE USERNAME = ?";
            stmt = con.prepareStatement(checkAdminPassword);
            stmt.setString(1, adminUsername);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String dbPassword = rs.getString("PASSWORD_");
                if (!dbPassword.equals(adminPassword)) {
                    return false;
                }
            } else {
                return false;
            }
            rs.close();
            stmt.close();
            
            // eliminar de USER_ si existe
            String deleteUser = "DELETE FROM USER_ WHERE USERNAME = ?";
            stmtDeleteUser = con.prepareStatement(deleteUser);
            stmtDeleteUser.setString(1, usernameToDelete);
            stmtDeleteUser.executeUpdate();
            stmtDeleteUser.close();
            
            // eliminar de ADMIN_ si existe
            String deleteAdmin = "DELETE FROM ADMIN_ WHERE USERNAME = ?";
            stmtDeleteAdmin = con.prepareStatement(deleteAdmin);
            stmtDeleteAdmin.setString(1, usernameToDelete);
            stmtDeleteAdmin.executeUpdate();
            stmtDeleteAdmin.close();
            
            // eliminar de PROFILE_
            String deleteProfile = "DELETE FROM PROFILE_ WHERE USERNAME = ?";
            stmt = con.prepareStatement(deleteProfile);
            stmt.setString(1, usernameToDelete);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                success = true;
            } else {
                success = false;
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta a la base de datos");
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(DBImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (stmtDeleteUser != null) {
                    stmtDeleteUser.close();
                }
                if (stmtDeleteAdmin != null) {
                    stmtDeleteAdmin.close();
                }
                conectionThread.releaseConnection();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión a la BD");
                e.printStackTrace();
            }
        }
        return success;
    }

    @Override
    public Boolean modificarUser(String password, String email, String name, String telephone, String surname, String username, String gender) {
        HiloConnection conectionThread = new HiloConnection(30);
        conectionThread.start();
        boolean success = false;
        PreparedStatement stmtUser = null;

        try {
            Connection con = waitForConnection(conectionThread);
            
            // actualizar PROFILE_
            stmt = con.prepareStatement(SQLMODIFYPROFILE);
            stmt.setString(1, password);
            stmt.setString(2, email);
            stmt.setString(3, name);
            stmt.setString(4, telephone);
            stmt.setString(5, surname);
            stmt.setString(6, username);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                // actualizar USER_ si existe
                stmtUser = con.prepareStatement(SQLMODIFYUSER);
                stmtUser.setString(1, gender);
                stmtUser.setString(2, username);
                stmtUser.executeUpdate();
                stmtUser.close();
                
                success = true;
            } else {
                System.out.println("Usuario no encontrado en la base de datos");
                success = false;
            }
        } catch (SQLException e) {
            System.out.println("Error en la consulta a la base de datos");
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(DBImplementation.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (stmtUser != null) {
                    stmtUser.close();
                }
                conectionThread.releaseConnection();

            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión a la BD");
                e.printStackTrace();
            }
        }
        return success;

    }

    private Connection waitForConnection(HiloConnection thread) throws InterruptedException {
        int attempts = 0;

        while (!thread.isReady() && attempts < 50) {
            Thread.sleep(10);
            attempts++;
        }

        return thread.getConnection();
    }

    @Override
    public List comboBoxInsert() {
        ObservableList<String> listaUsuarios = FXCollections.observableArrayList();

        Connection con = null;
        try {
            con = ConnectionPool.getConnection();
            stmt = con.prepareStatement(SLQSELECTNUSER);
            ResultSet result = stmt.executeQuery();
            while ((result.next())) {
                listaUsuarios.add(result.getString("USERNAME"));
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
        return listaUsuarios;
    }

}
