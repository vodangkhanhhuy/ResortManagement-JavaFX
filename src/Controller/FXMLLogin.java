/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Employee;
import View.Login;
import static View.Login.stageMain;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

/**
 *
 * @author TechCare
 */
public class FXMLLogin implements Initializable {

    @FXML
    private JFXTextField idUserName;

    @FXML
    private JFXButton btnForgot;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXPasswordField idPassword;

    @FXML
    private Label idError;
    public static String UserName;
    CheckController checkController;
    Utils utils;
    String encode;
    DataAccessObject dataAccessObject;
    static String EMPID;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //To change body of generated methods, choose Tools | Templates.
        utils = new Utils();
        btnForgot.setOnAction((e) -> {
            try {
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/FXML/FXMLForgot.fxml"));
                Parent sampleParent = loader.load();
                Scene scene = new Scene(sampleParent);
                scene.getStylesheets().add(getClass().getResource("/Css/CSSMain.css").toExternalForm());
                stage.setScene(scene);
            } catch (IOException ex) {
                Logger.getLogger(FXMLLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnLogin.setOnAction((event) -> {
            login();
        });
        idPassword.setOnKeyPressed((e) -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                login();
            }
        });
        idUserName.setOnKeyPressed((e) -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                login();
            }
        });
    }

    // check User and Password.
    public boolean checkUserPassnotEncode() {
        String query = "select * from Employee where UserName =  '" + idUserName.getText() + "' and Password = '" + idPassword.getText() + "'";
        checkController = new CheckController();
        boolean check = false;
        try {
            check = checkController.checkUserName(query);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }
     public boolean checkUserPassEncode() {
        try {
            encode = UtilsEx.encode.encrypt(idPassword.getText());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(FXMLLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query = "select * from Employee where UserName =  '" + idUserName.getText() + "' and Password = '" + encode + "'";
        checkController = new CheckController();
        boolean check = false;
        try {
            check = checkController.checkUserName(query);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    // check Active.
    public boolean checkActive() {
        try {
            encode = UtilsEx.encode.encrypt(idPassword.getText());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(FXMLLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        String query = "select * from Employee where UserName =  '" + idUserName.getText() + "' and Password = '"
                + encode + "' and Active = 1";
        checkController = new CheckController();
        boolean check = false;
        try {
            check = checkController.checkUserName(query);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }
    //check login
    public void login() {
        if ((idUserName.getText().equalsIgnoreCase("")) || (idPassword.getText().equalsIgnoreCase(""))) {
            idError.setText("User or Passord Wrong !");
            idUserName.setText("");
            idPassword.setText("");
            idUserName.requestFocus();
        } else if (checkUserPassnotEncode() == true) {
            dataAccessObject =  new DataAccessObject();
            Employee emp = dataAccessObject.getUserName(idUserName.getText());
            EMPID =  emp.getEMPID();
            try {
                    Parent sampleParent = FXMLLoader.load(getClass().getResource("/FXML/ChangePass.fxml"));
                    Login.scene = new Scene(sampleParent);
//                Login.scene.setRoot(sampleParent);
                    Login.scene.getStylesheets().add(getClass().getResource("/Css/CSSMain.css").toExternalForm());
                    Login.stageMain.setScene(Login.scene);
                    Login.stageMain.setTitle("HCN Resort Management");
                    Login.stageMain.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Capture2.PNG")));
                    stageMain.setResizable(false);
                    stageMain.setMaximized(false);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
        }else if(checkUserPassEncode() ==  true){
            if (checkActive() == true) {
                UserName = idUserName.getText();
                utils.changePage("/FXML/FXMLHome.fxml");
            } else {
                idError.setText("Account has not been activated yet !");
            }
        } else {
            idError.setText("User or Passord Worng !");
            idUserName.setText("");
            idPassword.setText("");
            idUserName.requestFocus();
        }
    }
}
