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
public class Department {
    private String DEPID;
    private String DepName;

    public Department() {
    }

    public Department(String DEPID, String DepName) {
        this.DEPID = DEPID;
        this.DepName = DepName;
    }

    public String getDEPID() {
        return DEPID;
    }

    public void setDEPID(String DEPID) {
        this.DEPID = DEPID;
    }

    public String getDepName() {
        return DepName;
    }

    public void setDepName(String DepName) {
        this.DepName = DepName;
    }
    
    public static String getNewDepartmentID() {
        String ID = "";
        try {
            Connection connection = ConnectDB.SQLServer();
            String sql = "select DEPID from Department order by DEPID desc";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                ID = rs.getString("DEPID");
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
                ID = "DEP001";
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Department.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ID;
    }
}
