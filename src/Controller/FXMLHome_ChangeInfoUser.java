/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Department;
import Models.Employee;
import Models.Position;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLHome_ChangeInfoUser implements Initializable {

    @FXML
    private JFXTextField txtPhone;

    @FXML
    private Label labelPosName;

    @FXML
    private Label labelEMPID;

    @FXML
    private JFXComboBox<String> cbbGender;

    @FXML
    private Label labelDepName;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXPasswordField txtConfirmPassword;

    @FXML
    private JFXPasswordField txtPassword;

    private DataAccessObject dao;
    private String query;
    private String itemsGender[] = {"Male", "Female"};

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao = new DataAccessObject();
        initGender();
        loadInfo();
        btnSave.setOnAction((event) -> {
            Save();
        });
        btnSave.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                Save();
            }
        });
        txtConfirmPassword.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                Save();
            }
        });
    }

    private void initGender() {
        List<String> list = new ArrayList<String>();
        for (String item : itemsGender) {
            list.add(item);
        }
        ObservableList obList = FXCollections.observableArrayList(list);
        cbbGender.setItems(obList);
    }

    private void loadInfo() {
        Employee emp = dao.getUserName(FXMLLogin.UserName);
        labelEMPID.setText(emp.getEMPID());
        txtName.setText(emp.getEmpName());
        txtPhone.setText(emp.getPhone());
        txtAddress.setText(emp.getAddress());
        txtEmail.setText(emp.getEmail());

        if (emp.getGender()) {
            cbbGender.getSelectionModel().select(0);
        } else {
            cbbGender.getSelectionModel().select(1);
        }

        query = "select * from Department";
        ObservableList<Department> depList = dao.getDepartmentData(query);
        for (Department dep : depList) {
            if (emp.getDEPID().equalsIgnoreCase(dep.getDEPID())) {
                labelDepName.setText(dep.getDepName());
                break;
            }
        }

        query = "select * from Position";
        ObservableList<Position> posList = dao.getPositionData(query);
        for (Position pos : posList) {
            if (emp.getPOSID().equalsIgnoreCase(pos.getPOSID())) {
                labelPosName.setText(pos.getPosName());
                break;
            }
        }
    }

    private void Save() {
        if(!checkInfor()){
            return;
        }
        dao = new DataAccessObject();
        boolean empGender = true;
        if (cbbGender.getSelectionModel().getSelectedIndex() == 1) {
            empGender = false;
        }
        if (!txtPassword.getText().equalsIgnoreCase("") && txtConfirmPassword.getText().equalsIgnoreCase("")) {
            Utils.messageAlertError("You haven't input Confirm Password!");
            return;
        } else if (txtPassword.getText().equalsIgnoreCase("") && !txtConfirmPassword.getText().equalsIgnoreCase("")) {
            Utils.messageAlertError("You haven't input New Password!");
            return;
        } else if (!txtPassword.getText().equalsIgnoreCase("") && !txtConfirmPassword.getText().equalsIgnoreCase("")) {
            if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
                query = "Update Employee set "
                        + " EmpName = '" + txtName.getText() + "',"
                        + " Address = '" + txtAddress.getText() + "', "
                        + " Gender = '" + empGender + "',"
                        + " Email = '" + txtEmail.getText() + "',"
                        + " Phone = '" + txtPhone.getText() + "',"
                        + " Password = '" + txtPassword.getText() + "'"
                        + " where EMPID = '" + labelEMPID.getText() + "'";
                dao.saveData(query);
                ChangePass();
                Utils.messageAlertNotification("Update success!");
                FXMLHome.stageChangeInfor.close();
            } else {
                Utils.messageAlertError("Invalid Password"); // set validex confirm password
                return;
            }
        } else if (txtPassword.getText().equalsIgnoreCase("") && txtConfirmPassword.getText().equalsIgnoreCase("")) {
            query = "Update Employee set "
                    + " EmpName = '" + txtName.getText() + "',"
                    + " Phone = '" + txtPhone.getText() + "',"
                    + " Gender = '" + empGender + "',"
                    + " Address = '" + txtAddress.getText() + "', "
                    + " Email = '" + txtEmail.getText() + "'"
                    + " where EMPID = '" + labelEMPID.getText() + "'";
            dao.saveData(query);
            Utils.messageAlertNotification("Update success!");
            FXMLHome.stageChangeInfor.close();
        }
    }

    private void ChangePass() {
        try {
            dao = new DataAccessObject();
            String encode = UtilsEx.encode.encrypt(txtConfirmPassword.getText());
            String query = "Update Employee set Password = '" + encode + "'where EMPID = '" + labelEMPID.getText() + "'";
            dao.saveData(query);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(FXMLChangePass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean checkInfor(){
        boolean check = true;
        if (CheckController.checkName(txtName.getText()) == false) {
            Utils.messageAlertError("Guest name cannot enter numbers ! \n Example : Pham Chi Cong.");
            txtName.requestFocus();
            check = false;
        } else if (txtPhone.getText().equalsIgnoreCase("") || CheckController.checkNumberPhone(txtPhone.getText()) == false) {
            Utils.messageAlertError("You are input Phone Number Wrong !\n Example : 0399912345  ");
            txtPhone.requestFocus();
            check = false;
        }else if (txtEmail.getText().equalsIgnoreCase("") || CheckController.checkEmail(txtEmail.getText()) == false){
            Utils.messageAlertError("You are input Email Wrong ! \n Example :  Phamchiconghinhsu@gmail.com");
            txtEmail.requestFocus();
            check = false;
        }else if(CheckController.checkPass(txtPassword.getText()) == false){
            txtPassword.requestFocus();
            Utils.messageAlertError("You are input Password  Wrong !\n Example : Admin12@ ");
            check = false;
        }else if(CheckController.checkPass(txtConfirmPassword.getText()) == false){
            txtConfirmPassword.requestFocus();
            Utils.messageAlertError("You are input Confirm Password  Wrong !\n Example : Admin12@ ");
            check = false;
        }
        return check;
    }
}
