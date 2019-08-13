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
 */
public class Employee {
    private String EMPID;
    private String EmpName;
    private String Address;
    private Boolean Gender;
    private String Email;
    private String Phone;
    private String DEPID;
    private String POSID;
    private String UserName;
    private String Password;
    private String ROLEID;
    private Boolean Active;

    public Employee() {
    }

    public Employee(String EMPID, String EmpName, String Address, Boolean Gender, String Email, String Phone, String DEPID, String POSID, String UserName, String Password, String ROLEID, Boolean Active) {
        this.EMPID = EMPID;
        this.EmpName = EmpName;
        this.Address = Address;
        this.Gender = Gender;
        this.Email = Email;
        this.Phone = Phone;
        this.DEPID = DEPID;
        this.POSID = POSID;
        this.UserName = UserName;
        this.Password = Password;
        this.ROLEID = ROLEID;
        this.Active = Active;
    }

    public String getEMPID() {
        return EMPID;
    }

    public void setEMPID(String EMPID) {
        this.EMPID = EMPID;
    }

    public String getEmpName() {
        return EmpName;
    }

    public void setEmpName(String EmpName) {
        this.EmpName = EmpName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public Boolean getGender() {
        return Gender;
    }

    public void setGender(Boolean Gender) {
        this.Gender = Gender;
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

    public String getDEPID() {
        return DEPID;
    }

    public void setDEPID(String DEPID) {
        this.DEPID = DEPID;
    }

    public String getPOSID() {
        return POSID;
    }

    public void setPOSID(String POSID) {
        this.POSID = POSID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getROLEID() {
        return ROLEID;
    }

    public void setROLEID(String ROLEID) {
        this.ROLEID = ROLEID;
    }

    public Boolean getActive() {
        return Active;
    }

    public void setActive(Boolean Active) {
        this.Active = Active;
    }
    
    public static String getNewEmployeeID() {
        String ID = "";
        try {
            Connection connection = ConnectDB.SQLServer();
            String sql = "select EMPID from Employee order by EMPID desc";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                ID = rs.getString("EMPID");
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
                ID = "EMP001";
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Department.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ID;
    }
    
}
