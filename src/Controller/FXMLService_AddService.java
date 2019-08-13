/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Service;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLService_AddService implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXTextField txtSerName;

    @FXML
    private JFXCheckBox checkBoxActive;

    @FXML
    private JFXTextField txtPrice;
    DataAccessObject dataAccessObject;
    String query;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnAdd.setOnAction((event) -> {
            if (chechInformation() == true) {
                addService();
            }
        });
    } 
     private void addService() {
        if (checkNewSer(txtSerName.getText()) != null) {
            txtSerName.requestFocus();
            Utils.messageAlertError("UserName exists !");
        } else {
            query = "insert into Service values('" + Service.getNewServiceID()
                    + "', '"
                    + txtSerName.getText()
                    + "', '"
                    + txtPrice.getText()
                    + "', '"
                    + checkBoxActive.isSelected()
                    + "')";
            dataAccessObject.saveData(query);
            Utils.messageAlertNotification("Insert success!");
            Refresh();
        }
    }
    private void Refresh(){
        txtSerName.setText("");
        txtPrice.setText("");
        checkBoxActive.setSelected(false);
    }
    // Check New SerName Exists.
    private String checkNewSer(String sernamenew) {
        dataAccessObject = new DataAccessObject();
        String nameCheck;
        Service ser = dataAccessObject.getSerName(sernamenew);
        if (ser != null) {
            nameCheck = ser.getSerName();
            
        } else {
            nameCheck = null;
        }
        return nameCheck;
    }
    // Check Information Service.
    private boolean chechInformation(){
        boolean check = true;
       if (txtSerName.getText().equalsIgnoreCase("")) {
           txtSerName.requestFocus();
            Utils.messageAlertError("You haven't input Service's SerName!");
            check = false;
        } else if (txtPrice.getText().equalsIgnoreCase("")) {
            txtSerName.requestFocus();
            Utils.messageAlertError("You haven't input Service's Price!");
            check = false;
        } else if (CheckController.checkNumber(txtPrice.getText()) == false) {
            txtPrice.requestFocus();
            Utils.messageAlertError("Input Price by Number !");
            check = false;
        }
        return check;
    }
}
