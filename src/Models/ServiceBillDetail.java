/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.util.Date;

/**
 *
 * @author TechCare
 */
public class ServiceBillDetail {
    private int BILLID;
    private String RESID;
    private String SERID;
    private Date BookDate;
    private int quantity;
    private int SerPrice;

    public ServiceBillDetail() {
    }

    public ServiceBillDetail(int BILLID, String RESID, String SERID, Date BookDate, int quantity, int SerPrice) {
        this.BILLID = BILLID;
        this.RESID = RESID;
        this.SERID = SERID;
        this.BookDate = BookDate;
        this.quantity = quantity;
        this.SerPrice = SerPrice;
    }

    public int getBILLID() {
        return BILLID;
    }

    public void setBILLID(int BILLID) {
        this.BILLID = BILLID;
    }

    public String getRESID() {
        return RESID;
    }

    public void setRESID(String RESID) {
        this.RESID = RESID;
    }

    public String getSERID() {
        return SERID;
    }

    public void setSERID(String SERID) {
        this.SERID = SERID;
    }

    public Date getBookDate() {
        return BookDate;
    }

    public void setBookDate(Date BookDate) {
        this.BookDate = BookDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSerPrice() {
        return SerPrice;
    }

    public void setSerPrice(int SerPrice) {
        this.SerPrice = SerPrice;
    }

    @Override
    public String toString() {
        return this.getSERID();
    }
    
}
