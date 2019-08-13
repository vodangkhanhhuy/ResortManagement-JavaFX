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

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLRoomList_AddHouse implements Initializable {

    @FXML
    private JFXTextField txtRoomType;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXTextField txtRoomPrice;

    @FXML
    private JFXTextField txtROOMID;

    @FXML
    private JFXCheckBox cbActive;
    
    private DataAccessObject dao;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dao = new DataAccessObject();
        btnSave.setOnAction((event) -> {
            save();
        });
    }
    private void save(){
        if(checkInformation()){
            return;
        }
        dao = new DataAccessObject();
        String query = "insert into Room values('" + txtROOMID.getText()
                    + "', '"
                    + txtRoomType.getText()
                    + "', '"
                    + false
                    + "', '"
                    + cbActive.isSelected()
                    + "', '"
                    + txtRoomPrice.getText()
                    + "')";
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
    // check ID kg vuot qua 4 ki tu
    private boolean checkIDCharacters() {
        boolean check = false;
        String id = txtROOMID.getText();
        if (id.length() >= 5) {
            check = true;
        }
        return check;
    }
    // check Information.
    private boolean checkInformation() {
        boolean check = false;
        if(checkIDCharacters()){
            Utils.messageAlertNotification("ROOMID cannot exceed 4 characters!");
            check = true;
        }else if(checkRoomExist(txtROOMID.getText())){
            Utils.messageAlertNotification("ROOMID cannot exceed 4 characters!");
            check = true;
        }else if (txtROOMID.getText().equalsIgnoreCase("")) {
            Utils.messageAlertNotification("You haven't input Roomlist's RoomID!");
            check = true;
        } else if (txtRoomType.getText().equalsIgnoreCase("")) {
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
