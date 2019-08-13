/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.UserAndRoleType;
import View.Login;
import static View.Login.stageMain;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;

/**
 * FXML Controller class
 *
 * @author Chí Công Jr
 */
public class FXMLForgot implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField idUserName;

    @FXML
    private JFXButton btnSendmail;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXTextField idEmail;

    @FXML
    private Label idError;
    DataAccessObject dataAccessObject;
    Utils utils;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        utils = new Utils();
        // re-login
        btnLogin.setOnAction((event) -> {
            try {
                Parent sampleParent = FXMLLoader.load(getClass().getResource("/FXML/FXMLLogin.fxml"));
                Login.scene = new Scene(sampleParent);
                Login.scene.getStylesheets().add(getClass().getResource("/Css/CSSMain.css").toExternalForm());
                Login.stageMain.setScene(Login.scene);
                stageMain.setResizable(false);
                stageMain.setMaximized(false);
            } catch (IOException ex) {
                Logger.getLogger(FXMLAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnSendmail.setOnAction((e) -> {
            if (idUserName.getText().equalsIgnoreCase("")) {
                idError.setText("User Name Wrong  ! ");
            } else if (idEmail.getText().equalsIgnoreCase("")) {
                idError.setText("Email  Wrong  ! ");
            } else {
                try {
                    SendEmail();
                } catch (Exception ex) {
                    Logger.getLogger(FXMLForgot.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        idEmail.setOnKeyPressed((e) -> {
            if (e.getCode() == KeyCode.ENTER) {
                if (idUserName.getText().equalsIgnoreCase("")) {
                    idError.setText("User Name Wrong  ! ");
                } else if (idEmail.getText().equalsIgnoreCase("")) {
                    idError.setText("Email  Wrong  ! ");
                } else {
                    try {
                        SendEmail();
                    } catch (Exception ex) {
                        Logger.getLogger(FXMLForgot.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

        });
    }

    public void SendEmail() throws SQLException, Exception {
        dataAccessObject = new DataAccessObject();
        String query = "select  UserName ,Password ,type from Employee as Emp join RoleUser as R on Emp.ROLEID =  r.ROLEID where Emp.UserName ='" + idUserName.getText() + "' and  Email= '" + idEmail.getText() + "'";
        UserAndRoleType userAndRoleType = dataAccessObject.getUserAndRoleType(query);
        if (userAndRoleType != null) {
            UtilsEx.sendEmail.send_Email("smtp.gmail.com", idEmail.getText(), "hcnresortmanagement@gmail.com", "Hcnresort1102", "HCN Resort Management", "User : " + userAndRoleType.getUserName() + "  Password : " + userAndRoleType.getPassword());
            idError.setText("Send Email Successed ! ");
        } else {
            idError.setText("Information does not exist ! ");
        }
    }

}
