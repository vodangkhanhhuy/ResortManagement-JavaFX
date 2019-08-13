/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import Controller.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Chí Công Jr
 */
public class EmployeeDaoImpl {
    private ConnectDB database = new ConnectDB();
    private ResultSet rs;
    private PreparedStatement pstm;
    private Connection connect;
    private String empID , empName , address , email, phone , depName , posName , type , userName  ;
    private boolean  gender , active;

    public EmployeeDaoImpl() {
    }

    public EmployeeDaoImpl(String empID, String empName, String address, String email, String phone, String depName, String posName, String type, String userName, boolean gender, boolean active) {
        this.empID = empID;
        this.empName = empName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.depName = depName;
        this.posName = posName;
        this.type = type;
        this.userName = userName;
        this.gender = gender;
        this.active = active;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return this.empID;
    }
    
    
    // load Employee Theo Model.
    public ObservableList<EmployeeDaoImpl> getEmployee(String query){
        ObservableList<EmployeeDaoImpl> list = FXCollections.observableArrayList();
        try{
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            while(rs.next()){
               list.add(new EmployeeDaoImpl(rs.getString("EMPID"), rs.getString("EmpName"), rs.getString("Address"), rs.getString("Email"), rs.getString("Phone"),rs.getString("DepName"), rs.getString("PosName"), rs.getString("type"), rs.getString("UserName"), rs.getBoolean("Gender"), rs.getBoolean("Active")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
