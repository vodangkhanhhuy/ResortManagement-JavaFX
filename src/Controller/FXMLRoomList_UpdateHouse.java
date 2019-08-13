/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Room;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLRoomList_UpdateHouse implements Initializable {

    @FXML
    private Label labelROOMID;

    @FXML
    private JFXTextField txtRoomType;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXTextField txtRoomPrice;

    @FXML
    private JFXCheckBox cbActive;
    
    private String query;
    private DataAccessObject dao;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dao = new DataAccessObject();
        initLoadInfor();
        btnSave.setOnAction((event) -> {
            save();
        });
    }

    private void initLoadInfor(){
        Room room = dao.getRoomByID(FXMLRoomList.ROOMIDSelected);
        labelROOMID.setText(room.getROOMID());
        txtRoomType.setText(room.getRoomType());
        txtRoomPrice.setText(String.valueOf(room.getRoomPrice()));
        if(room.getActive()){
            cbActive.setSelected(true);
        }
    }
    private void save(){
        dao = new DataAccessObject();
        if(checkInformation()){
            return;
        }
        Room room = dao.getRoomByID(labelROOMID.getText());
        if(room.getRoomStatus()){
            Utils.messageAlertNotification("This House using!");
            return;
        }
        String query = "Update Room set RoomType = '" + txtRoomType.getText()
                + "', Active = '" + cbActive.isSelected()
                + "', RoomPrice = '" + txtRoomPrice.getText()
                + "' where ROOMID = '" + labelROOMID.getText()
                + "'";
        dao.saveData(query);
        FXMLRoomList.stageInfor.close();
        Utils.messageAlertNotification("Save success!");
    }
    // check roomID exists
    private boolean checkRoomExist(String ROOMID) {
        boolean check = false;
        String query = "Select * from Room";
        ObservableList<Room> list = dao.getRoomData(query);
        for (Room room : list) {
            if (room.getROOMID().equalsIgnoreCase(ROOMID)) {
                check = true;
                break;
            }
        }
        return check;
    }
    // check Information.
    private boolean checkInformation() {
        boolean check = false;
        if (txtRoomType.getText().equalsIgnoreCase("")) {
            Utils.messageAlertNotification("You haven't input Roomlist's RoomType!");
            check = true;
            txtRoomType.requestFocus();
        } else if (CheckController.checkNumber(txtRoomPrice.getText()) == false) {
            Utils.messageAlertNotification("Input Price by Number !");
            check = true;
            txtRoomPrice.requestFocus();
        }
        return check;
    }
}
