/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object interface for database operations.
 * Provides methods to interact with user and admin records in the database.
 */
public interface ClassDAO {

    Profile logIn(String username, String password);

    Boolean signUp(String gender, String cardNumber, String username, String password, String email, String name,
                   String telephone, String surname);

    Boolean dropOutUser(String username, String password);

    Boolean dropOutAdmin(String username, String password);

    Boolean modificarUser(String password, String email, String name, String telephone, String surname, String username, String gender);

    List comboBoxInsert();
}
