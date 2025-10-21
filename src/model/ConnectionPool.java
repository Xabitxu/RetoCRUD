/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Duration;
import javax.sql.DataSource;

public class ConnectionPool {

    private static BasicDataSource dataSource;
    
    private static final String DB = "crud";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB + "?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "";

    // Configuración del pool
    private static void inicializaDataSource() {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASS);
        
        // Configura el pool
        dataSource.setInitialSize(3);  // conexiones iniciales
        dataSource.setMaxTotal(10);    // máximo de conexiones
        dataSource.setMaxWait(Duration.ofSeconds(10)); // espera máxima para obtener una conexión
    }

    // Constructor estático: solo se inicializa una vez
    static {
        inicializaDataSource();
    }

    // Método público para obtener el DataSource
    public static DataSource getDataSource() {
        return dataSource;
    }
}