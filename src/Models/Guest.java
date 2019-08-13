/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Controller.ConnectDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TechCare
 */
public class Guest {
    private String PASSPORT;
    private String GuestName;
    private String Email;
    private String Phone;
    private Boolean Gender;

    public Guest() {
    }

    public Guest(String PASSPORT, String GuestName, String Email, String Phone, Boolean Gender) {
        this.PASSPORT = PASSPORT;
        this.GuestName = GuestName;
        this.Email = Email;
        this.Phone = Phone;
        this.Gender = Gender;
    }

    public String getPASSPORT() {
        return PASSPORT;
    }

    public void setPASSPORT(String PASSPORT) {
        this.PASSPORT = PASSPORT;
    }

    public String getGuestName() {
        return GuestName;
    }

    public void setGuestName(String GuestName) {
        this.GuestName = GuestName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public Boolean getGender() {
        return Gender;
    }

    public void setGender(Boolean Gender) {
        this.Gender = Gender;
    }
    
}
