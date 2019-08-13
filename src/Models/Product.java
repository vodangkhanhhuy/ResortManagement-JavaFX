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
 * @author Chí Công Jr
 */
public class Product {
    private String ProID;
    private String ProName;
    private boolean Active ;

    public Product() {
    }

    public Product(String ProID, String ProName, boolean Active) {
        this.ProID = ProID;
        this.ProName = ProName;
        this.Active = Active;
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

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean Active) {
        this.Active = Active;
    }
     public static String getNewProductID() {
        String ID = "";
        try {
            Connection connection = ConnectDB.SQLServer();
            String sql = "select ProID from Product order by ProID desc";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                ID = rs.getString("ProID");
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
                ID = "Pro001";
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ID;
    }

   
}
