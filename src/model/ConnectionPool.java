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

public class ConnectionPool {
    // Instancia única del pool
    private static BasicDataSource dataSource;
    // Configuración estática del pool
    static {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/crud");
        dataSource.setUsername("usuario");
        dataSource.setPassword("contraseña");
    
        // Parámetros del pool
        dataSource.setInitialSize(5);      // Conexiones iniciales
        dataSource.setMaxTotal(10);        // Máximo total de conexiones
        dataSource.setMinIdle(2);          // Mínimo de conexiones inactivas
        dataSource.setMaxIdle(5);          // Máximo de conexiones inactivas
        dataSource.setMaxWaitMillis(10000); // Tiempo máximo para esperar una conexión
    }


    // Método para obtener una conexión
    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
}

