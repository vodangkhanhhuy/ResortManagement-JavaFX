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

/**
 * FXML Controller class
 *
 * @author Chí Công Jr
 */
public class CheckMailController implements Initializable {

    /**
     * Initializes the controller class.
     */
     @FXML
    private JFXButton btnBack = new JFXButton("");

    @FXML
    private Label idMess;

    @FXML
    private JFXTextField idEmail;

    @FXML
    private JFXTextField idCode;

    @FXML
    private JFXButton btnNext;
    int code;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        idEmail.setText(FXMLSignUpMail.Email);
       
        btnNext.setOnAction((event) -> {
            try{
                code = Integer.parseUnsignedInt(idCode.getText());
            }catch(NumberFormatException ex){
                idMess.setText("code is number !");
            }
            if(code ==  FXMLSignUpMail.code){
                try {
                    Parent sampleParent = FXMLLoader.load(getClass().getResource("/FXML/FXMLConnectSever.fxml"));
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
             }else {
                idMess.setText("Code Error !");
            }
        });
        btnBack.setOnAction((event) -> {
             try {
                    Parent sampleParent = FXMLLoader.load(getClass().getResource("/FXML/FXMLSignUpMail.fxml"));
                    Login.scene = new Scene(sampleParent);
//                Login.scene.setRoot(sampleParent);
                    Login.scene.getStylesheets().add(getClass().getResource("/Css/CSSMain.css").toExternalForm());
                    Login.stageMain.setScene(Login.scene);
                    Login.stageMain.setTitle("HCN Resort Management");
                    Login.stageMain.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Capture2.PNG")));
                    stageMain.setResizable(false);
                    stageMain.setMaximized(false);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLHome.class.getName()).log(Level.SEVERE, null, ex);
                }
        });
    }    
    
}
