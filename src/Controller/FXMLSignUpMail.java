/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import View.Login;
import static View.Login.stageMain;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

/**
 * FXML Controller class
 *
 * @author Chí Công Jr
 */
public class FXMLSignUpMail implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label idMess;

    @FXML
    private JFXTextField idEmail;

    @FXML
    private JFXButton btnNext;
    static int code;
    boolean sendmail;
    static String Email;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sendmail =  false;
        idEmail.setOnKeyPressed((event) -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                sendMail();
            }
        });
        btnNext.setOnAction((event) -> {
            sendMail();
        });
        btnNext.setOnKeyPressed((event) -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                sendMail();
            }
        });
    }    
    private void sendMail(){
        if (checkInfor() == true) {
                CodeCheck();
                try {
                    UtilsEx.sendEmail.send_Email("smtp.gmail.com", idEmail.getText(), "hcnresortmanagement@gmail.com", "Hcnresort1102", "HCN Resort Management", "Code : " + code);
                    idMess.setText("Please check your mail ! ");
                    sendmail = true;
                    Email = idEmail.getText();
                } catch (Exception ex) {
                    idMess.setText("Mail Error !");
                    sendmail = false;
                }
            }
            if (sendmail == true) {
                try {
                    Parent sampleParent = FXMLLoader.load(getClass().getResource("/FXML/CheckMail.fxml"));
                    Login.scene = new Scene(sampleParent);
//                Login.scene.setRoot(sampleParent);
//                Login.scene.getStylesheets().add(getClass().getResource("/Css/CSSMain.css").toExternalForm());
                    Login.stageMain.setScene(Login.scene);
                    Login.stageMain.setTitle("HCN Resort Management");
                    Login.stageMain.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Capture2.PNG")));
                    stageMain.setResizable(false);
                    stageMain.setMaximized(false);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLHome.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
    }
    private void CodeCheck(){
       Random rd = new Random();
        code = 0;
        while ((code <= 100000) | (code > 999999)) {
            code = rd.nextInt();
        }
       
    }
    private boolean checkInfor(){
        boolean check =  true;
        if(idEmail.getText().equalsIgnoreCase("")){
            idMess.setText("You haven't input Email!");
            check = false;
        }
        return  check;
    }
}
