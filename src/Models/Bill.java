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
public class Bill {
    private String RESID;
    private String PaymentDate;
    private Integer TotalPrice;

    public Bill() {
    }

    public Bill(String RESID, String PaymentDate, Integer TotalPrice) {
        this.RESID = RESID;
        this.PaymentDate = PaymentDate;
        this.TotalPrice = TotalPrice;
    }

    public String getRESID() {
        return RESID;
    }

    public void setRESID(String RESID) {
        this.RESID = RESID;
    }

    public String getPaymentDate() {
        return PaymentDate;
    }

    public void setPaymentDate(String PaymentDate) {
        this.PaymentDate = PaymentDate;
    }

    public Integer getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Integer TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    
    
    
}
