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
public class UserAndRoleType {
    private String UserName;
    private String RoleType;
    private String Password;

    public UserAndRoleType(String UserName, String RoleType, String Password) {
        this.UserName = UserName;
        this.RoleType = RoleType;
        this.Password = Password;
    }

    public UserAndRoleType() {
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getRoleType() {
        return RoleType;
    }

    public void setRoleType(String RoleType) {
        this.RoleType = RoleType;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }
    
    
}
