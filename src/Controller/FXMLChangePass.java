/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.Login;
import static View.Login.stageMain;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
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
import jdk.nashorn.internal.parser.TokenType;

/**
 * FXML Controller class
 *
 * @author Chí Công Jr
 */
public class FXMLChangePass implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label idMess;

    @FXML
    private JFXPasswordField idConfirm;

    @FXML
    private JFXButton btnChange;

    @FXML
    private Label idRelogin;

    @FXML
    private JFXPasswordField idNewPass;
    DataAccessObject dataAccessObject;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnChange.setOnAction((event) -> {
            ChangePass();
        });
        idRelogin.setOnMouseClicked((event) -> {
             try {
                Parent sampleParent = FXMLLoader.load(getClass().getResource("/FXML/FXMLLogin.fxml"));
                Login.scene = new Scene(sampleParent);
                Login.scene.getStylesheets().add(getClass().getResource("/Css/CSSMain.css").toExternalForm());
                Login.stageMain.setScene(Login.scene);
                stageMain.setResizable(false);
                stageMain.setMaximized(false);
            } catch (IOException ex) {
                Logger.getLogger(FXMLChangePass.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        idConfirm.setOnKeyPressed((event) -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                ChangePass();
            }
        });
        btnChange.setOnKeyPressed((event) -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                ChangePass();
            }
        });
    }    
    private boolean  checkInfor(){
        boolean check = true;
        if (idNewPass.getText().equalsIgnoreCase("")){
            idMess.setText("You haven't input New Password!");
            idNewPass.requestFocus();
            check =  false;
        }else if (!idNewPass.getText().equalsIgnoreCase(idConfirm.getText())){
            idMess.setText("Invalid password");
            idConfirm.requestFocus();
            check =  false;
        }else if(CheckController.checkPass(idNewPass.getText()) == false){
            idMess.setText("Error : Example : Admin12@");
            idNewPass.requestFocus();
            check =  false;
        }
        return  check;
    }
    private void ChangePass(){
        if (checkInfor() == true) {
            try {
                dataAccessObject = new DataAccessObject();
                String encode = UtilsEx.encode.encrypt(idConfirm.getText());
                String query = "Update Employee set Password = '" + encode + "'where EMPID = '" + FXMLLogin.EMPID + "'";
                if (checkInfor() == true) {
                    dataAccessObject.saveData(query);
                    idMess.setText("Change successed !");
                }
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
                Logger.getLogger(FXMLChangePass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
