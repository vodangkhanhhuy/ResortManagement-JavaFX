/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Date;

/**
 *
 * @author Softech
 */
public class BookServiceModel {
    private String RESID,ROOMID,PASSPORT, GuestName,EMPID;
    private Date BookDate, DateOut;
    private int ResPrice;

    public BookServiceModel() {
    }

    public BookServiceModel(String RESID, String ROOMID, String PASSPORT, String GuestName,Date BookDate,Date DateOut, String EMPID,  int ResPrice) {
        this.RESID = RESID;
        this.ROOMID = ROOMID;
        this.PASSPORT = PASSPORT;
        this.GuestName = GuestName;
        this.BookDate = BookDate;
        this.DateOut = DateOut;
        this.EMPID = EMPID;
        this.ResPrice = ResPrice;
    }

    public String getRESID() {
        return RESID;
    }

    public void setREDID(String RESID) {
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

    public String getGuestName() {
        return GuestName;
    }

    public void setGuestName(String GuestName) {
        this.GuestName = GuestName;
    }

    public String getEMPID() {
        return EMPID;
    }

    public void setEMPID(String EMPID) {
        this.EMPID = EMPID;
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

    public int getResPrice() {
        return ResPrice;
    }

    public void setResPrice(int ResPrice) {
        this.ResPrice = ResPrice;
    }
    
    
}
