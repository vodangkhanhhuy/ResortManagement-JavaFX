/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.UserAndRoleType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Chí Công Jr
 */
public class CheckController {
    private ConnectDB database = new ConnectDB();
    private ResultSet rs;
    private PreparedStatement pstm;
    private Connection connect;
    public boolean checkUserName( String query) throws SQLException{
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()){
                database.close(connect, pstm, rs);
               return true;
            }else {
                database.close(connect, pstm, rs);
                return false;
            }
      
    }
     public  boolean checkAdmin( String username) throws SQLException{
            String query =" select  type from Employee as Emp\n" +
                          " join RoleUser as R\n" +
                          " on Emp.ROLEID =  r.ROLEID\n" +
                          " where Emp.UserName = '"+username
                          +"'and R.type = 'admin' " ;
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()){
                database.close(connect, pstm, rs);
               return true;
            }else {
                database.close(connect, pstm, rs);
                return false;
            }
      
    }
    public UserAndRoleType getUserAndRoleType(String query) throws SQLException{
       
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if(rs.next()){
                UserAndRoleType userAndRoleType = new UserAndRoleType(rs.getString("UserName"),rs.getString("type"),rs.getString("Password"));
                database.close(connect, pstm, rs);
                return userAndRoleType;
            }
            
                database.close(connect, pstm, rs);
             return null;
    }
    // Check Number Phone (10 so ) bat dau tu so 0.;
    public static  boolean checkNumberPhone(String number) {
        Pattern pattern = Pattern.compile("^0\\d{9}$");
        Matcher matcher = pattern.matcher(number);
        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }
    
    
    // Check chuoi khong co ki tu so.
    
     public static boolean checkName(String str) {
        Pattern pattern = Pattern.compile("^[a-z A-Z]+$");
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }
    // check User Khong co ky tu trang va 5 ky tu tro len. co it nhat 5 ki tu.
     public static boolean checkUser(String str) {
        Pattern pattern = Pattern.compile("^[a-z A-Z](?=\\S+$).{4,}$");
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }
      // check User Khong co ky tu trang va 5 ky tu tro len. co it nhat 5 ki tu.
     public static boolean checkNumber(String str) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }
     
    // check password co it nhat 1 ky tu hoa , 1 ki tu thuong , 1 ki tu dac biet [@#$%^&+=]; co it nhat 6 ki tu.
    public static boolean checkPass(String str) {
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{5,}$");
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    } 
    
    // check Email.
    public static boolean checkEmail(String str) {
        Pattern pattern = Pattern.compile("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
        Matcher matcher = pattern.matcher(str);
        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }
    // UserName Emp.
   
}
