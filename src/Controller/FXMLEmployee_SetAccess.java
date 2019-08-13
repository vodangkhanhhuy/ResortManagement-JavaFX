/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Accessright;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLEmployee_SetAccess implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXCheckBox cbService;

    @FXML
    private JFXCheckBox cbRoomList;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXCheckBox cbAdmin;

    @FXML
    private JFXCheckBox cbHistory;

    @FXML
    private JFXCheckBox cbView;

    @FXML
    private JFXCheckBox cbGuest;

    @FXML
    private JFXCheckBox cbEmployee;

    @FXML
    private JFXCheckBox cbBookRoom;
    @FXML
    private JFXCheckBox cbHome;
    String query;
    DataAccessObject dataAccessObject;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        System.out.println(FXMLEmployee.empIDAccess);
        getAccess();
        btnSave.setOnAction((event) -> {
            setAccess();
            Utils.messageAlertNotification("Update successed !");
            FXMLEmployee.stageInfor.close();
        });
    }    
    //update Access
    private void setAccess(){
        dataAccessObject = new DataAccessObject();
        query = "update dbo.Accessright set BookRoomPage = '"+ cbBookRoom.isSelected() +"',GuestPage = '"+cbGuest.isSelected()+"',"
                + "RoomListPage = '"+cbRoomList.isSelected()+"',EmployeePage = '"+cbEmployee.isSelected()+"',ServicePage = '"+cbService.isSelected()+"',"
                + "ViewPage = '"+cbView.isSelected()+"',HistoryPage = '"+cbHistory.isSelected()+"',AdminPage = '"+cbAdmin.isSelected()+"' Where EMPID = '"+FXMLEmployee.empIDAccess+"' ";
        dataAccessObject.saveData(query);
    }
    // get Access.
    private void getAccess(){
        String id = FXMLEmployee.empIDAccess;
        dataAccessObject = new DataAccessObject();
        Accessright accessright = dataAccessObject.getAccessData(id);
        cbHome.setSelected(accessright.isHomePage());
        cbBookRoom.setSelected(accessright.isBookRoomPage());
        cbGuest.setSelected(accessright.isGuestPage());
        cbRoomList.setSelected(accessright.isRoomListPage());
        cbEmployee.setSelected(accessright.isEmployeePage());
        cbService.setSelected(accessright.isServicePage());
        cbView.setSelected(accessright.isViewPage());
        cbHistory.setSelected(accessright.isHistoryPage());
        cbAdmin.setSelected(accessright.isAdminPage());
    }
}
