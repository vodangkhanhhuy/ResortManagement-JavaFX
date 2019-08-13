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
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author TechCare
 */
public class Reservation {
    private String RESID;
    private String ROOMID;
    private String PASSPORT;
    private Date BookDate;
    private Date DateOut;
    private String EMPID;
    private Integer DateUse;
    private Integer ResPrice;
    private Boolean Status;

    public Reservation() {
    }

    public Reservation(String RESID, String ROOMID, String PASSPORT, Date BookDate, Date DateOut, String EMPID, Integer DateUse, Integer ResPrice, Boolean Status) {
        this.RESID = RESID;
        this.ROOMID = ROOMID;
        this.PASSPORT = PASSPORT;
        this.BookDate = BookDate;
        this.DateOut = DateOut;
        this.EMPID = EMPID;
        this.DateUse = DateUse;
        this.ResPrice = ResPrice;
        this.Status = Status;
    }

    public String getRESID() {
        return RESID;
    }

    public void setRESID(String RESID) {
        this.RESID = RESID;
    }

    public String getROOMID() {
        return ROOMID;
    }

    public void setROOMID(String ROOMID) {
        this.ROOMID = ROOMID;
    }

    public String getPASSPORT() {
        return PASSPORT;
    }

    public void setPASSPORT(String PASSPORT) {
        this.PASSPORT = PASSPORT;
    }

    public Date getBookDate() {
        return BookDate;
    }

    public void setBookDate(Date BookDate) {
        this.BookDate = BookDate;
    }

    public Date getDateOut() {
        return DateOut;
    }

    public void setDateOut(Date DateOut) {
        this.DateOut = DateOut;
    }

    public String getEMPID() {
        return EMPID;
    }

    public void setEMPID(String EMPID) {
        this.EMPID = EMPID;
    }

    public Integer getDateUse() {
        return DateUse;
    }

    public void setDateUse(Integer DateUse) {
        this.DateUse = DateUse;
    }

    public Integer getResPrice() {
        return ResPrice;
    }

    public void setResPrice(Integer ResPrice) {
        this.ResPrice = ResPrice;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean Status) {
        this.Status = Status;
    }

    @Override
    public String toString() {
        return this.RESID;
    }

    
    
     
    public static String getNewReservationID() {
        String ID = "";
        try {
            Connection connection = ConnectDB.SQLServer();
            String sql = "select RESID from Reservation order by RESID desc";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                ID = rs.getString("RESID");
            }
            rs.close();
            statement.close();
            connection.close();
            if (!ID.isEmpty()) {
                int i = Integer.valueOf(ID.substring(3, ID.length())) + 1;
                if (i >= 0 && i <= 9) {
                    ID = ID.substring(0, 3) + "00" + i;
                } else {
                    if (i >= 10 && i <= 99) {
                        ID = ID.substring(0, 3) + "0" + i;
                    } else {
                        ID = ID.substring(0, 3) + i;
                    }
                }
            } else {
                ID = "RES001";
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Department.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ID;
    }
    
}
