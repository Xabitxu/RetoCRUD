/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import javax.sql.DataSource;

public class ConnectionPool {

    private static BasicDataSource dataSource;
    
    private static final String DB = "crud";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB + "?serverTimezone=UTC&useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "root";

    // Configuración del pool
    static {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);
        
        // Configura el pool
        dataSource.setInitialSize(5);      // Conexiones iniciales
        dataSource.setMaxTotal(10);        // Máximo total de conexiones
        dataSource.setMinIdle(2);          // Mínimo de conexiones inactivas
        dataSource.setMaxIdle(5);          // Máximo de conexiones inactivas   // máximo de conexiones
        dataSource.setMaxWait(Duration.ofSeconds(10)); // espera máxima para obtener una conexión
    }


        // Método para obtener una conexión
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}