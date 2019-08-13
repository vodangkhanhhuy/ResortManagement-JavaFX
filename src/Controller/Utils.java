/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Models.Accessright;
import Models.Employee;
import View.Login;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

/**
 *
 * @author TechCare
 */
public class Utils {
    public DataAccessObject dataAccessObject;
    public static void messageAlertNotification(String str) {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("Notification");
        message.setContentText(str);
        message.show();
    }

    public static void messageAlertError(String str) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error");
        message.setContentText(str);
        message.showAndWait();
    }
     public void changePage(String URL) {
        try {
            Parent sampleParent = FXMLLoader.load(getClass().getResource(URL));
            Login.scene.setRoot(sampleParent);
//            Login.scene = new Scene(sampleParent);
            Login.scene.getStylesheets().add(getClass().getResource("/Css/CSSMain.css").toExternalForm());
            Login.stageMain.setScene(Login.scene);
            Login.stageMain.setMaximized(true);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public void pressKey(){
        Login.scene.setOnKeyPressed((key) -> {
            if(key.getCode() == KeyCode.H && key.isControlDown()){
                changePage("/FXML/FXMLHome.fxml");
            }
            if(key.getCode() == KeyCode.B && key.isControlDown()){
                changePage("/FXML/FXMLBookRoom.fxml");
            }
            if(key.getCode() == KeyCode.G && key.isControlDown()){
                changePage("/FXML/FXMLGuest.fxml");
            }
            if(key.getCode() == KeyCode.R && key.isControlDown()){
                changePage("/FXML/FXMLRoomList.fxml");
            }
            if(key.getCode() == KeyCode.E && key.isControlDown()){
                changePage("/FXML/FXMLEmployee.fxml");
            }
            if(key.getCode() == KeyCode.S && key.isControlDown()){
                changePage("/FXML/FXMLService.fxml");
            }
            if(key.getCode() == KeyCode.V && key.isControlDown()){
                changePage("/FXML/FXMLView.fxml");
            }
            if(key.getCode() == KeyCode.T && key.isControlDown()){
                changePage("/FXML/FXMLHistory.fxml");
            }
            if(key.getCode() == KeyCode.A && key.isControlDown()){
                changePage("/FXML/FXMLAdmin.fxml");
            }
        });
     }
    public void loadAvatar(Label txtUsername, ImageView imageView){
        dataAccessObject = new DataAccessObject();
        Employee emp = dataAccessObject.getUserName(txtUsername.getText());
        String link = dataAccessObject.getLinkImagesByEMPID(emp.getEMPID());
        if (link != null) {
            File file = new File(link);
            try {
                Image img = new Image(file.toURI().toURL().toExternalForm());
                imageView.setImage(img);
            } catch (MalformedURLException ex) {
                Logger.getLogger(FXMLHome.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (link == null) {
            
        }
    }
    
    // utils
    public Date changeLocalDateToDate(LocalDate localDate){
        java.util.Date date = java.util.Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date bookDate = new java.sql.Date(date.getTime());
        return bookDate;
    }
}   
