/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import model.ClassDAO;
import model.DBImplementation;
import model.Profile;
import model.User;

public class Controller {

    private ClassDAO dao;

    public Controller(ClassDAO dao) {
        this.dao = dao;
    }

    public Profile logIn(String username, String password) {
        return dao.logIn(username, password);
    }

    public Boolean signUp(String gender, String cardNumber, String username, String password, String email,
            int userCode, String name, String telephone, String surname) {
        return dao.signUp(gender, cardNumber, username, password, email, 0, name, telephone, surname);
    }

    public Boolean dropOutUser(String username) {
        return dao.dropOutUser(username);
    }

    public Boolean dropOutAdmin(String username) {
        return dao.dropOutAdmin(username);
    }
}
