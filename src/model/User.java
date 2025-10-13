/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author acer
 */
public class User extends Profile{
    private String gender;
    private int cardNumber;

    public User(String gender, int cardNumber, String username, String password, String email, int userCode, String name, int telephone, String surname) {
        super(username, password, email, userCode, name, telephone, surname);
        this.gender = gender;
        this.cardNumber = cardNumber;
    }

    public User() {
        super();
        this.gender = "";
        this.cardNumber = 0;
    }

    public String getGender() {
        return gender;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "User{" + "gender=" + gender + ", cardNumber=" + cardNumber + '}';
    }

    @Override
    public void logIn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
