/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDate;
import java.util.Map;

public interface ClassDAO {

    public Profile logIn(String username, String password);
    public Boolean signUp(String gender, String cardNumber, String username, String password, String email, int userCode, String name,
            String telephone, String surname);
    public Boolean dropOutUser(String username);
    public Boolean dropOutAdmin(String username);

}
