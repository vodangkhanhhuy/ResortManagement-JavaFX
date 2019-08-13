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
public class Service {
    private String SERID;
    private String SerName;
    private Integer SerPrice;
    private Boolean Active;

    public Service() {
    }

    public Service(String SERID, String SerName, Integer SerPrice, Boolean Active) {
        this.SERID = SERID;
        this.SerName = SerName;
        this.SerPrice = SerPrice;
        this.Active = Active;
    }

    public String getSERID() {
        return SERID;
    }

    public void setSERID(String SERID) {
        this.SERID = SERID;
    }

    public String getSerName() {
        return SerName;
    }

    public void setSerName(String SerName) {
        this.SerName = SerName;
    }

    public Integer getSerPrice() {
        return SerPrice;
    }

    public void setSerPrice(Integer SerPrice) {
        this.SerPrice = SerPrice;
    }

    public Boolean getActive() {
        return Active;
    }

    public void setActive(Boolean Active) {
        this.Active = Active;
    }
    
    
    
    public static String getNewServiceID() {
        String ID = "";
        try {
            Connection connection = ConnectDB.SQLServer();
            String sql = "select SERID from Service order by SERID desc";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                ID = rs.getString("SERID");
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
                ID = "SER001";
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Department.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ID;
    }
    
    
}
