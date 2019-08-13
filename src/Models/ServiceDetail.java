/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Chí Công Jr
 */
public class ServiceDetail {
    private String SERID;
    private String ProID;
    private String ProName;
    private int Quantity;

    public ServiceDetail() {
    }

    public ServiceDetail(String SERID, String ProID, String ProName, int Quantity) {
        this.SERID = SERID;
        this.ProID = ProID;
        this.ProName = ProName;
        this.Quantity = Quantity;
    }

    public String getSERID() {
        return SERID;
    }

    public void setSERID(String SERID) {
        this.SERID = SERID;
    }

    public String getProID() {
        return ProID;
    }

    public void setProID(String ProID) {
        this.ProID = ProID;
    }

    public String getProName() {
        return ProName;
    }

    public void setProName(String ProName) {
        this.ProName = ProName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }
     
}
