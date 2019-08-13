/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Product;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLService_AddProduct implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXTextField txtProName;

    @FXML
    private Label txtProID;
    DataAccessObject dataAccessObject;
    String query;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtProID.setText(Product.getNewProductID());
        btnAdd.setOnAction((event) -> {
            if (CheckInformation() ==  true){
                addProduct();
            }
        });
    }    
    private void addProduct(){
        dataAccessObject =  new DataAccessObject();
        query = "insert into Product values('"+txtProID.getText()+"','"+txtProName.getText()+"',1) ";
        dataAccessObject.saveData(query);
        Utils.messageAlertNotification("Insert success!");
        Refresh();
    }
    
    private void Refresh(){
        txtProID.setText(Product.getNewProductID());
        txtProName.setText("");
    }
    private boolean  CheckInformation(){
        dataAccessObject =  new DataAccessObject();
        boolean check =  true ;
        if (txtProName.getText().equalsIgnoreCase("")){
            txtProName.requestFocus();
            Utils.messageAlertError("You haven't input Product's Name!");
            check = false;
        }else if(dataAccessObject.getProductByName(txtProName.getText())){
             txtProName.requestFocus();
            Utils.messageAlertError("Product's Name already exist!");
            check = false;
        }
        return  check;
    }
}
