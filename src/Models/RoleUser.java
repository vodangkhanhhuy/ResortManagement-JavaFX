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
public class RoleUser {
    private String ROLEID;
    private Boolean type;

    public RoleUser() {
    }

    public RoleUser(String ROLEID, Boolean type) {
        this.ROLEID = ROLEID;
        this.type = type;
    }

    public String getROLEID() {
        return ROLEID;
    }

    public void setROLEID(String ROLEID) {
        this.ROLEID = ROLEID;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }
    
    public static String getNewRoleUserID() {
        String ID = "";
        try {
            Connection connection = ConnectDB.SQLServer();
            String sql = "select ROLEID from RoleUser order by ROLEID desc";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                ID = rs.getString("ROLEID");
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
                ID = "ROL001";
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Department.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ID;
    }
    
}
