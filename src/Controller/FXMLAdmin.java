/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Accessright;
import Models.Department;
import Models.Employee;
import Models.Position;
import Models.UserAndRoleType;
import View.Login;
import static View.Login.stageMain;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLAdmin implements Initializable {

    @FXML
    private ImageView imageView_avatar;

    @FXML
    private JFXButton btnHistory;

    @FXML
    private TableColumn<Position, String> colPosID;

    @FXML
    private JFXButton btnGuest;
    @FXML
    private TableView<Department> tableDep;
    @FXML
    private TableView<Position> tablePos;

    @FXML
    private JFXTextField PosName;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXTextField PosID;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnService;

    @FXML
    private JFXTextField DepID;

    @FXML
    private JFXButton btnRoomList;

    @FXML
    private JFXTextField DepName;

    @FXML
    private JFXButton btnView;

    @FXML
    private TableColumn<Department, String> colDepID;

    @FXML
    private JFXButton btnAdmin;

    @FXML
    private PieChart pieCharPostion;

    @FXML
    private JFXButton btnBookRoom;

    @FXML
    private Label txtRoleName;

    @FXML
    private Label txtUsername;

    @FXML
    private TableColumn<Department, String> colDepName;

    @FXML
    private TableColumn<Position, String> colPosName;
    @FXML
    private JFXButton btninsertDep;

    @FXML
    private JFXButton btnUpdateDep;
    @FXML
    private JFXButton btnInsertPos;
    @FXML
    private JFXButton btnUpdatePos;
    @FXML
    private JFXButton btnRemoveDep;
    @FXML
    private JFXButton btnRemovePos;

    DataAccessObject dataAccessObject;
    Utils utils;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txtUsername.setText(FXMLLogin.UserName);
        setAccess();
        try {
            setRoleType();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLHome.class.getName()).log(Level.SEVERE, null, ex);
        }

        utils = new Utils();
        utils.pressKey();
        utils.loadAvatar(txtUsername, imageView_avatar);
        btnGuest.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLGuest.fxml");
        });
        btnEmployee.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLEmployee.fxml");
        });
        btnLogout.setOnAction((event) -> {
            try {
                Parent sampleParent = FXMLLoader.load(getClass().getResource("/FXML/FXMLLogin.fxml"));
                Login.scene = new Scene(sampleParent);
                Login.scene.getStylesheets().add(getClass().getResource("/Css/CSSMain.css").toExternalForm());
                Login.stageMain.setScene(Login.scene);
                stageMain.setResizable(false);
                stageMain.setMaximized(false);
            } catch (IOException ex) {
                Logger.getLogger(FXMLAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnHome.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLHome.fxml");
        });
        btnBookRoom.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLBookRoom.fxml");
        });
        btnHistory.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLHistory.fxml");
        });
        btnRoomList.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLRoomList.fxml");
        });
        btnService.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLService.fxml");
        });
        btnView.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLView.fxml");
        });
        // load data table :
        loaddataTableDep();
        loaddataTablePos();
        // new ID moi san .
        DepID.setText(Department.getNewDepartmentID());
        PosID.setText(Position.getNewPositionID());
        // Insert Dep:
        btninsertDep.setOnAction((event) -> {
            if (checkInforDep() == true) {
                insertDep();
                refresh();
            }
        });
        //Insert Pos : 
        btnInsertPos.setOnAction((event) -> {
            if (checkInforPos() == true) {
                insertPos();
                refresh();
            }
        });
        // click Table Dep : 
        tableDep.setOnMouseClicked((event) -> {
            getdataTableDep();
            pieChar();
        });
        // clicl table Pos:
        tablePos.setOnMouseClicked((event) -> {
            getdataTablePos();

        });
        //Update Dep : 
        btnUpdateDep.setOnAction((event) -> {
            if (DepName.getText().equalsIgnoreCase("")) {
                Utils.messageAlertError("You haven't Input Dep Name");
                return;
            }
            updateDep();
            refresh();
        });
        // Update Pos : 
        btnUpdatePos.setOnAction((event) -> {
            if (PosName.getText().equalsIgnoreCase("")) {
                Utils.messageAlertError("You haven't Input Pos Name");
                return;
            }
            updatePos();
            refresh();
        });
        btnRemoveDep.setOnAction((event) -> {
            removeDep();
        });
        btnRemovePos.setOnAction((event) -> {
            removePos();
        });
    }

    public void setRoleType() throws SQLException {
        dataAccessObject = new DataAccessObject();
        String query = "select  UserName ,Password ,type from Employee as Emp join RoleUser as R on Emp.ROLEID =  r.ROLEID where Emp.UserName ='" + txtUsername.getText() + "' ";
        UserAndRoleType userAndRoleType = dataAccessObject.getUserAndRoleType(query);
        txtRoleName.setText(userAndRoleType.getRoleType());
        txtUsername.setText(userAndRoleType.getUserName());
    }

    // dep
    private void initTableDep() {
        colDepID.setCellValueFactory(new PropertyValueFactory<Department, String>("DEPID"));
        colDepName.setCellValueFactory(new PropertyValueFactory<Department, String>("DepName"));
    }

    private void loaddataTableDep() {
        initTableDep();
        dataAccessObject = new DataAccessObject();
        String query = "Select * from Department ";
        tableDep.setItems(dataAccessObject.getDepartmentData(query));
        pieChar();
    }

    private void insertDep() {
        String query = " insert into Department values('" + Department.getNewDepartmentID() + "', '" + DepName.getText() + "')";
        dataAccessObject.saveData(query);
        loaddataTableDep();
        Utils.messageAlertNotification("Insert success!");
    }

    private boolean checkInforDep() {
        boolean check = true;
        if (DepName.getText().equalsIgnoreCase("")) {
            Utils.messageAlertError("You haven't input Dep Name!");
            check = false;
        } else if (dataAccessObject.getDepartmentDataByDepName(DepName.getText()) != null) {
            Utils.messageAlertError("Dep name already exists !");
            check = false;
        }
        return check;
    }

    private void getdataTableDep() {
        Department dep = tableDep.getSelectionModel().getSelectedItem();
        if (dep != null) {
            DepID.setText(dep.getDEPID());
            DepName.setText(dep.getDepName());
        } else {
            Utils.messageAlertError("You haven't selected");
        }
    }

    private void updateDep() {
        String query;
        Department dep = dataAccessObject.getDepartmentDataByDepName(DepName.getText());
        if (dep != null) {
            if (dep.getDEPID().equalsIgnoreCase(DepID.getText())) {
                query = " Update Department set DepName = '" + DepName.getText() + "' where DEPID  = '" + DepID.getText() + "'";
                dataAccessObject.saveData(query);
                loaddataTableDep();
                Utils.messageAlertNotification("Update success!");
            } else {
                Utils.messageAlertError("Dep name already exists !");
            }
        } else {
            if (dataAccessObject.getDepartmentDataByDepID(DepID.getText()) == null) {
                Utils.messageAlertError("You haven't selected");
                return;
            }
            query = " Update Department set DepName = '" + DepName.getText() + "' where DEPID  = '" + DepID.getText() + "'";
            dataAccessObject.saveData(query);
            loaddataTableDep();
            Utils.messageAlertNotification("Update success!");
        }
    }
    // Pos

    private void initTablePos() {
        colPosID.setCellValueFactory(new PropertyValueFactory<Position, String>("POSID"));
        colPosName.setCellValueFactory(new PropertyValueFactory<Position, String>("PosName"));
    }

    private void loaddataTablePos() {
        initTablePos();
        dataAccessObject = new DataAccessObject();
        String query = "Select * from Position";
        tablePos.setItems(dataAccessObject.getPositionData(query));
    }

    private void insertPos() {
        String query = " insert into Position values('" + Position.getNewPositionID() + "', '" + PosName.getText() + "')";
        dataAccessObject.saveData(query);
        loaddataTablePos();
        Utils.messageAlertNotification("Insert success!");
    }

    private boolean checkInforPos() {
        boolean check = true;
        if (PosName.getText().equalsIgnoreCase("")) {
            Utils.messageAlertError("You haven't input Pos Name!");
            check = false;
        } else if (dataAccessObject.getPositionDataByPosName(PosName.getText()) != null) {
            Utils.messageAlertError("Pos name already exists !");
            check = false;
        }
        return check;
    }

    private void getdataTablePos() {
        Position pos = tablePos.getSelectionModel().getSelectedItem();
        if (pos != null) {
            PosID.setText(pos.getPOSID());
            PosName.setText(pos.getPosName());
        } else {
            Utils.messageAlertError("You haven't selected");
        }
    }

    private void updatePos() {
        String query;
        Position pos = dataAccessObject.getPositionDataByPosName(PosName.getText());
        if (pos != null) {
            if (pos.getPOSID().equalsIgnoreCase(PosID.getText())) {
                query = " Update Position set PosName = '" + PosName.getText() + "' where POSID  = '" + PosID.getText() + "'";
                dataAccessObject.saveData(query);
                loaddataTablePos();
                Utils.messageAlertNotification("Update success!");
            } else {
                Utils.messageAlertError("Pos Name already exists !");
            }
        } else {
            if (dataAccessObject.getPositionDataByPosID(PosID.getText()) == null) {
                Utils.messageAlertError("You haven't selected");
                return;
            }
            query = " Update Position set PosName = '" + PosName.getText() + "' where POSID  = '" + PosID.getText() + "'";
            dataAccessObject.saveData(query);
            loaddataTablePos();
            Utils.messageAlertNotification("Update success!");
        }
    }

    private void refresh() {
        // new ID moi san .
        DepID.setText(Department.getNewDepartmentID());
        PosID.setText(Position.getNewPositionID());
        DepName.setText("");
        PosName.setText("");
    }

    private void pieChar() {
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList(
                new PieChart.Data("Reception", 30),
                new PieChart.Data("Housekeeping", 60),
                new PieChart.Data("Technical ", 15),
                new PieChart.Data("protection ", 20)
        );
        pieCharPostion.setData(list);
    }

    //set Access.
    private void setAccess() {
        dataAccessObject = new DataAccessObject();
        Employee emp = dataAccessObject.getUserName(txtUsername.getText());
        Accessright accessright = dataAccessObject.getAccessData(emp.getEMPID());
        btnHome.setDisable(!accessright.isHomePage());
        btnBookRoom.setDisable(!accessright.isBookRoomPage());
        btnGuest.setDisable(!accessright.isGuestPage());
        btnRoomList.setDisable(!accessright.isRoomListPage());
        btnEmployee.setDisable(!accessright.isEmployeePage());
        btnService.setDisable(!accessright.isServicePage());
        btnView.setDisable(!accessright.isViewPage());
        btnHistory.setDisable(!accessright.isHistoryPage());
        btnAdmin.setDisable(!accessright.isAdminPage());
    }

    private void removeDep() {
        Department department = tableDep.getSelectionModel().getSelectedItem();
        if (department == null) {
            Utils.messageAlertNotification("You must choose Department!");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Notification");
        alert.setHeaderText("Confirmation Dialog");
        alert.setContentText("Do you want to remove this Department?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            String query = " Update Employee set DEPID = 'DEP001' where DEPID = '" + department.getDEPID() + "'";
            dataAccessObject = new DataAccessObject();
            dataAccessObject.saveData(query);
            query = "delete from Department where DEPID = '" + department.getDEPID() + "'";
            dataAccessObject.saveData(query);
            loaddataTableDep();
        }
    }

    private void removePos() {
        Position position = tablePos.getSelectionModel().getSelectedItem();
        if (position == null) {
            Utils.messageAlertNotification("You must choose Position!");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Notification");
        alert.setHeaderText("Confirmation Dialog");
        alert.setContentText("Do you want to remove this Department?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            String query = " Update Employee set POSID = 'POS001' where POSID = '" + position.getPOSID() + "'";
            dataAccessObject = new DataAccessObject();
            dataAccessObject.saveData(query);
            query = "delete from Position where POSID = '" + position.getPOSID() + "'";
            dataAccessObject.saveData(query);
            loaddataTablePos();
        }
    }
}
