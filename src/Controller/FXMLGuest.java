/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Accessright;
import Models.Employee;
import Models.Guest;
import Models.UserAndRoleType;
import View.Login;
import static View.Login.stageMain;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author TechCare
 */
public class FXMLGuest implements Initializable {

    @FXML
    private JFXTextField txtPhone;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtGuestName;
    @FXML
    private JFXButton btnClear;
    @FXML
    private JFXComboBox<String> cbbGender;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXTextField txtPASSPORT;
    @FXML
    private JFXButton btnSearch;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private TableColumn<Guest, String> ColPASSPORT;
    @FXML
    private TableColumn<Guest, String> ColGuestName;
    @FXML
    private TableColumn<Guest, String> ColEmail;
    @FXML
    private TableColumn<Guest, String> ColPhone;
    @FXML
    private TableColumn<Guest, String> ColGender;
    @FXML
    private TableView<Guest> tableGuest;

    private DataAccessObject dao;
    private String query;
    private String itemsGender[] = {"Male", "Female"};
    @FXML
    private JFXButton btnHistory;
    @FXML
    private JFXButton btnEmployee;
    @FXML
    private JFXButton btnGuest;
    @FXML
    private JFXButton btnService;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnRoomList;
    @FXML
    private JFXButton btnView;

    @FXML
    private JFXButton btnAdmin;
    @FXML
    private JFXButton btnBookRoom;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private Label txtRoleName;
    @FXML
    private Label labelNumGuest;
    @FXML
    private ImageView imageView_avatar;

    @FXML
    private Label txtUsername;
    DataAccessObject dataAccessObject;
    Utils utils;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtUsername.setText(FXMLLogin.UserName);
        setAccess();
        dao = new DataAccessObject();
        utils = new Utils();
        utils.pressKey();
        utils.loadAvatar(txtUsername, imageView_avatar);
        loadTableGuest();
        initGuestGender();
        tableGuest.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getDataSelected();
            }
        });
        btnClear.setOnAction(e -> {
            clear();
        });
        btnAdd.setOnAction(e -> {
            addGuest();
        });
        btnUpdate.setOnAction(e -> {
            updateGuest();
        });
        btnSearch.setOnAction(e -> {
            searchName();
        });
        txtSearch.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                searchName();
            }
        });
        try {
            setRoleType();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLHome.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        btnAdmin.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLAdmin.fxml");
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
        btnBookRoom.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLBookRoom.fxml");
        });
    }
    // load column table

    public void initLoadTableGuest() {
        ColPASSPORT.setCellValueFactory(new PropertyValueFactory<Guest, String>("PASSPORT"));
        ColGuestName.setCellValueFactory(new PropertyValueFactory<Guest, String>("GuestName"));
        ColEmail.setCellValueFactory(new PropertyValueFactory<Guest, String>("Email"));
        ColPhone.setCellValueFactory(new PropertyValueFactory<Guest, String>("Phone"));
        ColGender.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Guest, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Guest, String> param) {
                if (param.getValue().getGender()) {
                    return new ReadOnlyObjectWrapper<String>("Male");
                } else {
                    return new ReadOnlyObjectWrapper<String>("Female");
                }
            }
        });
    }

    // load table
    private void loadTableGuest() {
        initLoadTableGuest();
        dao = new DataAccessObject();
        query = "Select * from Guest";
        ObservableList<Guest> gList = dao.getGuestData(query);
        int numGuest = gList.size();
        labelNumGuest.setText(String.valueOf(numGuest));
        tableGuest.setItems(dao.getGuestData(query));
    }
    
    // load gender
    private void initGuestGender() {
        List<String> list = new ArrayList<String>();
        for (String item : itemsGender) {
            list.add(item);
        }
        ObservableList obList = FXCollections.observableArrayList(list);
        cbbGender.setItems(obList);
        cbbGender.getSelectionModel().select(0);
    }

    // selected
    private void getDataSelected() {
        Guest guest = tableGuest.getSelectionModel().getSelectedItem();
        if (guest == null) {
            Utils.messageAlertNotification("You haven't selected guest");
        } else {
            txtPASSPORT.setText(guest.getPASSPORT());
            txtGuestName.setText(guest.getGuestName());
            txtEmail.setText(guest.getEmail());
            if (txtEmail.getText() == null) {
                txtEmail.setText("");
            }
            txtPhone.setText(guest.getPhone());
            if (txtPhone.getText() == null) {
                txtPhone.setText("");
            }
            if (guest.getGender() == true) {
                cbbGender.getSelectionModel().select(0);
            } else {
                cbbGender.getSelectionModel().select(1);
            }
        }
    }

    // Btn Clear
    private void clear() {
        txtPASSPORT.setText("");
        txtGuestName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        cbbGender.getSelectionModel().select(0);
    }

    // check ID
    public boolean checkID() {
        boolean check = false;
        query = "Select * from guest";
        ObservableList<Guest> list = dao.getGuestData(query);
        for (Guest guest : list) {
            if (guest.getPASSPORT().equalsIgnoreCase(txtPASSPORT.getText())) {
                check = true;
                break;
            }
        }
        return check;
    }

    //check input information
    public boolean checkInformation() {
        boolean check = true;
        int phone;
        if (txtPASSPORT.getText().equalsIgnoreCase("")) {
            Utils.messageAlertError("You haven't input Guest's Passport!");
            txtPASSPORT.requestFocus();
            check = false;
        } else if (txtGuestName.getText().equalsIgnoreCase("")) {
            Utils.messageAlertError("You haven't input Guest's Name!");
            txtGuestName.requestFocus();
            check = false;
        } else if (CheckController.checkName(txtGuestName.getText()) == false) {
            Utils.messageAlertError("Guest name cannot enter numbers ! \n Example : Pham Chi Cong.");
            txtGuestName.requestFocus();
            check = false;
        } else if (!txtEmail.getText().equalsIgnoreCase("") && CheckController.checkEmail(txtEmail.getText()) == false) {
            Utils.messageAlertError("You are input Email Wrong ! \n Example :  Phamchiconghinhsu@gmail.com");
            txtEmail.requestFocus();
            check = false;
        } else if (txtPhone.getText().equalsIgnoreCase("") || CheckController.checkNumberPhone(txtPhone.getText()) == false) {
            Utils.messageAlertError("You are input Phone Number Wrong !\n Example : 0399912345  ");
            txtPhone.requestFocus();
            check = false;
        }
        return check;
    }

    // add guest
    private void addGuest() {
        if (!checkInformation()) {
            return;
        } else if (checkID()) {
            Utils.messageAlertError("Passport exists!");
            return;
        }
        boolean gender = true;
        if (cbbGender.getSelectionModel().getSelectedIndex() == 1) {
            gender = false;
        }
        if (txtEmail.getText().equalsIgnoreCase("")) {
            query = "insert into Guest values('" + txtPASSPORT.getText()
                    + "', '"
                    + txtGuestName.getText()
                    + "', null, '"
                    + txtPhone.getText()
                    + "', '"
                    + gender
                    + "')";
        } else {
            query = "insert into Guest values('" + txtPASSPORT.getText()
                    + "', '"
                    + txtGuestName.getText()
                    + "', '"
                    + txtEmail.getText()
                    + "', '"
                    + txtPhone.getText()
                    + "', '"
                    + gender
                    + "')";
        }
        dao.saveData(query);
        loadTableGuest();
        clear();
        Utils.messageAlertNotification("Insert success!");
    }

    // update
    public void updateGuest() {
        if (!checkInformation()) {
            return;
        } else if (!checkID()) {
            Utils.messageAlertError("Passport does not exists!");
            return;
        }
        boolean gender = true;
        if (cbbGender.getSelectionModel().getSelectedIndex() == 1) {
            gender = false;
        }
        if (txtEmail.getText().equalsIgnoreCase("")) {
            query = "Update Guest set GuestName = '" + txtGuestName.getText()
                    + "', Email = null, Phone = '" + txtPhone.getText()
                    + "', Gender = '" + gender
                    + "' where PASSPORT = '" + txtPASSPORT.getText()
                    + "'";
        } else {
            query = "Update Guest set GuestName = '" + txtGuestName.getText()
                    + "', Email = '" + txtEmail.getText()
                    + "', Phone = '" + txtPhone.getText()
                    + "', Gender = '" + gender
                    + "' where PASSPORT = '" + txtPASSPORT.getText()
                    + "'";
        }
        dao.saveData(query);
        clear();
        loadTableGuest();
        Utils.messageAlertNotification("Update success!");
    }

    // search 
    public void searchName() {
        initLoadTableGuest();
        query = "select * from dbo.Guest where GuestName like " + "'%" + txtSearch.getText() + "%'";
        tableGuest.setItems(dao.getGuestData(query));
        txtSearch.setText("");
    }

    // Set Role Type.
    public void setRoleType() throws SQLException {
        dataAccessObject = new DataAccessObject();
        String query = "select  UserName ,Password ,type from Employee as Emp join RoleUser as R on Emp.ROLEID =  r.ROLEID where Emp.UserName ='" + txtUsername.getText() + "' ";
        UserAndRoleType userAndRoleType = dataAccessObject.getUserAndRoleType(query);
        txtRoleName.setText(userAndRoleType.getRoleType());
        txtUsername.setText(userAndRoleType.getUserName());
    }
    //set Access.
    private void setAccess() {
        dataAccessObject =  new DataAccessObject();
        Employee emp =  dataAccessObject.getUserName(txtUsername.getText());
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
}
