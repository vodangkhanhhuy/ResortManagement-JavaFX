/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Product;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLService_UpdateProduct implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btnSearch;

    @FXML
    private TableColumn<Product, String> ColActive;

    @FXML
    private JFXTextField txtProName;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<Product, String> colProID;

    @FXML
    private JFXCheckBox checkBoxActive;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private TableColumn<Product, String> colProName;

    @FXML
    private Label txtProID;

    @FXML
    private TableView<Product> tableProduct;
    DataAccessObject dataAccessObject;
    String query , proSelected ,  proname;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadTableProduct();
        tableProduct.setOnMouseClicked((event) -> {
            getDataSelected();
        });
        btnUpdate.setOnAction((event) -> {
            if(CheckInformation() ==  true){
                updateService();
            }
        });
        btnSearch.setOnAction((event) -> {
            searchName();
        });
    } 
    // init and load table;
    private void initTableProduct() {
        colProID.setCellValueFactory(new PropertyValueFactory<Product, String>("ProID"));
        colProName.setCellValueFactory(new PropertyValueFactory<Product, String>("ProName"));
        ColActive.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Product, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Product, String> param) {
                if (param.getValue().isActive()) {
                    return new ReadOnlyObjectWrapper<String>("Active");
                } else {
                    return new ReadOnlyObjectWrapper<String>("UnActive");
                }
            }
        });
    }
    private void loadTableProduct() {
        initTableProduct();
        dataAccessObject = new DataAccessObject();
        query = "select * from Product";
        tableProduct.setItems(dataAccessObject.getProductData(query));
    }
    private void getDataSelected() {
        Product product;
        try {
            product = tableProduct.getSelectionModel().getSelectedItem();
            proSelected = product.getProID();
            proname = product.getProName();
            txtProID.setText(product.getProID());
            txtProName.setText(product.getProName());
            if (product.isActive()) {
                checkBoxActive.setSelected(true);
            } else {
                checkBoxActive.setSelected(false);
            }
        } catch (Exception e) {
            Utils.messageAlertError("You haven't selected");
        }
    }
    private boolean  CheckInformation(){
        dataAccessObject =  new DataAccessObject();
        boolean check =  true ;
        if (txtProName.getText().equalsIgnoreCase("")){
            txtProName.requestFocus();
            Utils.messageAlertError("You haven't input Product's Name!");
            check = false;
        }
        return  check;
    }
     private void updateService() {
        boolean active = true;
        if (!checkBoxActive.isSelected()) {
            active = false;
        }
        if (proname.equalsIgnoreCase(txtProName.getText())) {
            query = "Update Product set ProName = '" + txtProName.getText()
                    + "', Active = '" + active
                    + "' where ProID = '" + proSelected
                    + "'";
        } else {
            if (dataAccessObject.getProductByName(txtProName.getText())) {
                Utils.messageAlertError("Service's name already exists !");
                txtProName.requestFocus();
                return;
            }
           query = "Update Product set ProName = '" + txtProName.getText()
                    + "', Active = '" + active
                    + "' where ProID = '" + proSelected
                    + "'";
        }

        dataAccessObject.saveData(query);
        loadTableProduct();
        Utils.messageAlertNotification("Update success!");
    }
     // search 
    public void searchName() {
        initTableProduct();
        query = "select * from dbo.Product where ProName like " + "'%" + txtSearch.getText() + "%'";
        tableProduct.setItems(dataAccessObject.getProductData(query));
        txtSearch.setText("");
    }
}
