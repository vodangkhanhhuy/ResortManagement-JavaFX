/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Accessright;
import Models.BookServiceModel;
import Models.Employee;
import Models.Room;
import Models.Service;
import Models.ServiceBillDetail;
import Models.UserAndRoleType;
import View.Login;
import static View.Login.stageMain;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLBookRoom implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btnHistory;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private JFXButton btnService;
    @FXML
    private JFXButton btnGuest;
    @FXML
    private JFXButton btnRoomList;
    @FXML
    private JFXButton btnView;
    @FXML
    private JFXButton btnAdmin;
    @FXML
    private JFXButton btnHome;
    @FXML
    private Label txtRoleName;
    @FXML
    private Label txtUsername;
    @FXML
    private JFXButton btnEmployee;

    // add vao sau
    @FXML
    private JFXButton btnBookRoomShowPane;
    @FXML
    private JFXButton btnAddServiceShowPane;
    @FXML
    private JFXButton btnPaymentShowPane;
    @FXML
    private AnchorPane paneBookRoom;
    @FXML
    private AnchorPane paneAddService;
    @FXML
    private AnchorPane panePayment;

//**************************************************
// pane BookRoom
    // search + table
    @FXML
    private TableView<Room> paneBR_tableRoom;
    @FXML
    private TableColumn<Room, String> paneBR_colROOMID;
    @FXML
    private TableColumn<Room, String> paneBR_colRoomType;
    @FXML
    private TableColumn<Room, String> paneBR_colRoomStatus;
    @FXML
    private TableColumn<Room, String> paneBR_colActive;
    @FXML
    private TableColumn<Room, String> paneBR_colPrice;

    // information
    @FXML
    private JFXButton paneBR_btnRefresh;
    @FXML
    private Label paneBR_labelRESID;
    @FXML
    private Label paneBR_labelROOMID;
    @FXML
    private DatePicker paneBR_DatepickerDateOut;
    @FXML
    private DatePicker paneBR_DatepickerDatIn;
    @FXML
    private Label paneBR_labelEMPID;
    @FXML
    private Label paneBR_labelResPrice;

    //guest
    @FXML
    private JFXTextField paneBR_txtPassport;
    @FXML
    private JFXTextField paneBR_txtGuestName;
    @FXML
    private JFXTextField paneBR_txtEmail;
    @FXML
    private JFXTextField paneBR_txtPhone;
    @FXML
    private JFXComboBox<?> paneBR_Gender;
    @FXML
    private JFXButton paneBR_btnAddService;
    @FXML
    private JFXButton paneBR_btnAddBookRoom;
    @FXML
    private JFXButton paneBR_btnCheckInDate;

//**************************************************
//pane add service
    // table RES
    @FXML
    private TableView<BookServiceModel> paneAS_tableRes;
    @FXML
    private TableColumn<BookServiceModel, String> paneAS_tableRes_colROOMID;
    @FXML
    private TableColumn<BookServiceModel, String> paneAS_tableRes_colPassport;
    @FXML
    private TableColumn<BookServiceModel, String> paneAS_tableRes_colGuestName;
    @FXML
    private TableColumn<BookServiceModel, Integer> paneAS_tableRes_colResPrice;
    @FXML
    private TableColumn<BookServiceModel, String> paneAS_tableRes_colEMPID;
    @FXML
    private TableColumn<BookServiceModel, Date> paneAS_tableRes_colBookDate;
    @FXML
    private TableColumn<BookServiceModel, String> paneAS_tableRes_colRESID;
    @FXML
    private TableColumn<BookServiceModel, Date> paneAS_tableRes_colDateOut;

    // table Service
    @FXML
    private TableView<Service> paneAS_tableService;
    @FXML
    private TableColumn<Service, String> paneAS_tableService_colActive;
    @FXML
    private TableColumn<Service, String> paneAS_tableService_colSERID;
    @FXML
    private TableColumn<Service, String> paneAS_tableService_colSerName;
    @FXML
    private TableColumn<Service, String> paneAS_tableService_colSerPrice;
    @FXML
    private TableColumn<Service, Void> paneAS_tableService_colAddService;

    // Search
    @FXML
    private JFXButton paneAS_btnSearch;
    @FXML
    private JFXTextField paneAS_txtSearch;

    // infomation
    @FXML
    private JFXButton paneAS_Info_btnRefresh;
    @FXML
    private Label paneAS_Info_labelRESID;
    @FXML
    private Label paneAS_Info_labelROOMID;
    @FXML
    private Label paneAS_Info_labelPassport;
    @FXML
    private Label paneAS_Info_labelGuestName;
    @FXML
    private Label paneAS_Info_labelDateIn;
    @FXML
    private Label paneAS_Info_labelDateOut;
    @FXML
    private Label paneAS_Info_labelResPrice;
    @FXML
    private Label paneAS_Info_labelTotalPriceServiceUsed;
    @FXML
    private JFXButton paneAS_Info_btnDeleteService;

    //information table
    @FXML
    private TableView<ServiceBillDetail> paneAS_Info_tableSerUsed;
    @FXML
    private TableColumn<ServiceBillDetail, String> paneAS_Info_tableSerUsed_colNo;
    @FXML
    private TableColumn<ServiceBillDetail, String> paneAS_Info_tableSerUsed_colSerName;
    @FXML
    private TableColumn<ServiceBillDetail, Date> paneAS_Info_tableSerUsed_colDate;
    @FXML
    private TableColumn<ServiceBillDetail, Integer> paneAS_Info_tableSerUsed_colQuantity;
    @FXML
    private TableColumn<ServiceBillDetail, String> paneAS_Info_tableSerUsed_colPrice;

//**************************************************
//pane Payment
    // search
    @FXML
    private JFXButton panePM_btnSearch;
    @FXML
    private JFXTextField panePM_txtSearch;

    // table RES
    @FXML
    private TableView<BookServiceModel> panePM_tableRes;
    @FXML
    private TableColumn<BookServiceModel, String> panePM_tableRes_colGuestName;
    @FXML
    private TableColumn<BookServiceModel, Date> panePM_tableRes_colBookDate;
    @FXML
    private TableColumn<BookServiceModel, String> panePM_tableRes_colRESID;
    @FXML
    private TableColumn<BookServiceModel, Date> panePM_tableRes_colDateOut;
    @FXML
    private TableColumn<BookServiceModel, String> panePM_tableRes_colRoomID;
    @FXML
    private TableColumn<BookServiceModel, String> panePM_tableRes_colPassport;
    @FXML
    private TableColumn<BookServiceModel, Integer> panePM_tableRes_colResPrice;
    @FXML
    private TableColumn<BookServiceModel, String> panePM_tableRes_colEMPID;

    // information
    @FXML
    private JFXButton panePM_info_btnRefresh;
    @FXML
    private Label panePM_info_labelRESID;
    @FXML
    private Label panePM_info_labelROOMID;
    @FXML
    private Label panePM_info_labelPassport;
    @FXML
    private Label panePM_info_labelGuestName;
    @FXML
    private Label panePM_info_labelDateIn;
    @FXML
    private Label panePM_info_labelDateOut;
    @FXML
    private Label panePM_info_labelResPrice;

    // information - table
    @FXML
    private TableView<ServiceBillDetail> panePM_info_tableSerUsed;
    @FXML
    private TableColumn<ServiceBillDetail, String> panePM_info_tableSerUsed_colNo;
    @FXML
    private TableColumn<ServiceBillDetail, String> panePM_info_tableSerUsed_colPrice;
    @FXML
    private TableColumn<ServiceBillDetail, Integer> panePM_info_tableSerUsed_colQuantity;
    @FXML
    private TableColumn<ServiceBillDetail, String> panePM_info_tableSerUsed_colSerName;
    @FXML
    private TableColumn<ServiceBillDetail, Date> panePM_info_tableSerUsed_colDate;

    // payment
    @FXML
    private Label panePM_info_totalPrice;
    @FXML
    private Label panePM_info_labelTotalServiceUsed;
    @FXML
    private JFXButton panePM_info_btnPayment;
    @FXML
    private JFXButton panePM_info_btnInvoice;
    @FXML
    private JFXButton if_btnBookToday;
    @FXML
    private JFXButton if_btnResTerm;
    @FXML
    private ImageView imageView_avatar;

    private DataAccessObject dataAccessObject;
    private Utils utils;
    private String query;
    private String itemsGender[] = {"Male", "Female"};
    private String valueRESIDInfoPaneBR;
    public static Stage stageConfirmPayment;
    public static String RESIDPayment;
    public static Stage stageChangeInfor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
        dataAccessObject = new DataAccessObject();
        btnVisited(btnBookRoomShowPane, btnAddServiceShowPane, btnPaymentShowPane);
        loadTableRoomPaneBR(); // load table room pane BR
// user login
        txtUsername.setText(FXMLLogin.UserName);
        setAccess();
        try {
            setRoleType();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLBookRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
        utils = new Utils();
        utils.loadAvatar(txtUsername, imageView_avatar);
        utils.pressKey();
// btn dashboard
        BtnsDashboard();
// show / hide pane
        showAndHidePane();
// Pane BookRoom
        initEmpID();
        initGuestGender();
        paneBookRoom();
// pane Add service
        paneAddService();
// pane Payment
        panePayment();
        // book to day
        if_btnBookToday.setOnAction((event) -> {
            try {
                BookToday();
            } catch (IOException ex) {
                Logger.getLogger(FXMLBookRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        // res term
        if_btnResTerm.setOnAction((event) -> {
            try {
                ResTerm();
            } catch (IOException ex) {
                Logger.getLogger(FXMLBookRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

// Main ********************************************************    
    // btn dashboard
    private void BtnsDashboard() {
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
    }

    // btns show / hide pane
    private void showAndHidePane() {
        btnBookRoomShowPane.setOnAction((event) -> {
            clickBtnBookRoomShowPane();
            btnVisited(btnBookRoomShowPane, btnAddServiceShowPane, btnPaymentShowPane);
            loadTableRoomPaneBR(); // load table room pane BR
            refreshInfoPaneBR();
        });
        btnAddServiceShowPane.setOnAction((event) -> {
            clickBtnAddServiceShowPane();
            btnVisited(btnAddServiceShowPane, btnBookRoomShowPane, btnPaymentShowPane);
            // load table Res pane AS
            loadTableRoomPaneAS();
            //  load Table Service pane AS.
            loadTableServicepaneAS();
            refreshInfoPaneAS();
        });
        btnPaymentShowPane.setOnAction((event) -> {
            clickBtnPaymentShowPane();
            loadTableResPanePM();
            btnVisited(btnPaymentShowPane, btnBookRoomShowPane, btnAddServiceShowPane);
            refreshInfoPanePM();
        });
    }

    // Pane bookroom
    private void paneBookRoom() {
        paneBR_btnAddBookRoom.setOnAction((event) -> {
            if (paneBR_labelROOMID.getText().equalsIgnoreCase("")) {
                Utils.messageAlertError("You must choose House!");
                return;
            } else if (CheckInforBookRoom() == true) {
                insertBookRoom();
            }

        });
        paneBR_tableRoom.setOnMouseClicked((event) -> {
            getDataSelectedTableRoomPaneBR();
        });
        paneBR_btnRefresh.setOnAction((event) -> {
            refreshInfoPaneBR();
        });
        paneBR_btnAddService.setOnAction((event) -> {
            if (paneBR_labelROOMID.getText().equalsIgnoreCase("")) {
                Utils.messageAlertError("You must choose House!");
                return;
            } else if (CheckInforBookRoom() == true) {
                insertBookRoom();
                clickBtnAddServiceShowPane();
                btnVisited(btnAddServiceShowPane, btnBookRoomShowPane, btnPaymentShowPane);
                // load table Res pane AS
                loadTableRoomPaneAS();
                //  load Table Service pane AS.
                loadTableServicepaneAS();
                getDataFromPaneBR();
            }
        });
        checkInRoomEmpty();
    }

    //Pane Add service
    private void paneAddService() {
        // Click paneAS_tableService
        paneAS_Info_btnRefresh.setOnAction((event) -> {
            refreshInfoPaneAS();
        });
        paneAS_tableRes.setOnMouseClicked((event) -> {
            getDataSelectedTableRes();
        });

        paneAS_Info_btnDeleteService.setOnAction((event) -> {
            deleteRowServiceUsed();
        });
        paneAS_btnSearch.setOnAction((event) -> {
            searchRoomPaneAS();
        });
        paneAS_txtSearch.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                searchRoomPaneAS();
            }
        });
    }

    // Pane panePayment
    private void panePayment() {
        panePM_info_btnRefresh.setOnAction((event) -> {
            refreshInfoPanePM();
        });
        panePM_tableRes.setOnMouseClicked((event) -> {
            getDataSelectedPanePM();
        });
        panePM_info_btnPayment.setOnAction((event) -> {
            payment();
        });
        panePM_info_btnInvoice.setOnAction((event) -> {
            try {
                invoice();
            } catch (IOException ex) {
                Logger.getLogger(FXMLBookRoom.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        panePM_btnSearch.setOnAction((event) -> {
            searchRoomPanePM();
        });
        panePM_txtSearch.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                searchRoomPanePM();
            }
        });
    }

// content ********************************************************    
    // set role
    public void setRoleType() throws SQLException {
        dataAccessObject = new DataAccessObject();
        String query = "select  UserName ,Password ,type from Employee as Emp join RoleUser as R on Emp.ROLEID =  r.ROLEID where Emp.UserName ='" + txtUsername.getText() + "' ";
        UserAndRoleType userAndRoleType = dataAccessObject.getUserAndRoleType(query);
        txtRoleName.setText(userAndRoleType.getRoleType());
        txtUsername.setText(userAndRoleType.getUserName());
    }

    // method show / hide pane
    private void clickBtnBookRoomShowPane() {
        paneBookRoom.setVisible(true);
        paneAddService.setVisible(false);
        panePayment.setVisible(false);
    }

    private void clickBtnAddServiceShowPane() {
        paneBookRoom.setVisible(false);
        paneAddService.setVisible(true);
        panePayment.setVisible(false);
    }

    private void clickBtnPaymentShowPane() {
        paneBookRoom.setVisible(false);
        paneAddService.setVisible(false);
        panePayment.setVisible(true);
    }

    private void btnVisited(JFXButton btn1, JFXButton btn2, JFXButton btn3) {
        btn1.setStyle("-fx-border-color: black;-fx-background-color: #45A9B1;-fx-text-fill: black;");
        btn2.setStyle("-fx-border-color:  #45A9B1;-fx-background-color: #45A9B1;-fx-text-fill: White;");
        btn3.setStyle("-fx-border-color:  #45A9B1;-fx-background-color: #45A9B1;-fx-text-fill: White;");
    }
    // Book today
    private void BookToday() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/FXMLBookRoom_BookToday.fxml"));
        Scene scene = new Scene(root);
        stageChangeInfor = new Stage();
        stageChangeInfor.setScene(scene);
        stageChangeInfor.setResizable(false);
        stageChangeInfor.setTitle("Book Today");
        stageChangeInfor.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Capture2.PNG")));
        stageChangeInfor.showAndWait();
    }
    // Res term
    private void ResTerm() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/FXMLBookRoom_ReservationTerm.fxml"));
        Scene scene = new Scene(root);
        stageChangeInfor = new Stage();
        stageChangeInfor.setScene(scene);
        stageChangeInfor.setResizable(false);
        stageChangeInfor.setTitle("Reservation Term");
        stageChangeInfor.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Capture2.PNG")));
        stageChangeInfor.showAndWait();
    }
// pane BookRoom ********************************************************
    //load table
    private void initTablePaneBR() {
        paneBR_colROOMID.setCellValueFactory(new PropertyValueFactory<Room, String>("ROOMID"));
        paneBR_colRoomType.setCellValueFactory(new PropertyValueFactory<Room, String>("RoomType"));
        paneBR_colRoomStatus.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Room, String> param) {
                if (param.getValue().getRoomStatus()) {
                    return new ReadOnlyObjectWrapper<String>("Using");
                } else {
                    return new ReadOnlyObjectWrapper<String>("Empty");
                }
            }
        });
        paneBR_colActive.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Room, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Room, String> param) {
                if (param.getValue().getActive()) {
                    return new ReadOnlyObjectWrapper<String>("Active");
                } else {
                    return new ReadOnlyObjectWrapper<String>("UnActive");
                }
            }
        });
        paneBR_colPrice.setCellValueFactory(new PropertyValueFactory<Room, String>("RoomPrice"));
    }

    private void loadTableRoomPaneBR() {
        initTablePaneBR();
        dataAccessObject = new DataAccessObject();
        query = "Select * from Room where ROOMID like '%0000%'";
        paneBR_tableRoom.setItems(dataAccessObject.getRoomData(query));
        refreshInfoPaneBR();
    }

    private void initEmpID() {
        Employee emp = dataAccessObject.getUserName(FXMLLogin.UserName);
        paneBR_labelEMPID.setText(emp.getEMPID());
    }

    private void initGuestGender() {
        List<String> list = new ArrayList<String>();
        for (String item : itemsGender) {
            list.add(item);
        }
        ObservableList obList = FXCollections.observableArrayList(list);
        paneBR_Gender.setItems(obList);
        paneBR_Gender.getSelectionModel().select(0);
    }

    // selected
    private void getDataSelectedTableRoomPaneBR() {
        try {
            Room room = paneBR_tableRoom.getSelectionModel().getSelectedItem();
            paneBR_labelROOMID.setText(room.getROOMID());
            paneBR_labelResPrice.setText(room.getRoomPrice().toString());
        } catch (Exception e) {
            Utils.messageAlertNotification("You haven't select room");
            return;
        }
    }

    // insert
    private void insertBookRoom() {
        dataAccessObject = new DataAccessObject();
        //date in
        Date bookDate = utils.changeLocalDateToDate(paneBR_DatepickerDatIn.getValue());
        // date out
        Date dateOut = utils.changeLocalDateToDate(paneBR_DatepickerDateOut.getValue());
        LocalDate today = LocalDate.now();
        int compareTo = paneBR_DatepickerDatIn.getValue().compareTo(today);// date - today : <0 (ngay book nho hon ngay hien tai), >0(ngay book lon hon ngay hien tai)
        // date use
        long date3 = ChronoUnit.DAYS.between(paneBR_DatepickerDatIn.getValue(), paneBR_DatepickerDateOut.getValue());
        int datOfUse = (int) date3;
        if (datOfUse == 0) { // ngay su dung = 0 thi payment 1 ngay
            datOfUse = 1;
        } else if (datOfUse < 0) { // bao loi date out < date in 
            Utils.messageAlertError("Date of use must not be less than 0!");
            return;
        } else if (compareTo < 0) {
            Utils.messageAlertError("Book dates cannot be less than the today!");
            return;
        } else if (paneBR_DatepickerDatIn.getValue() == null || paneBR_DatepickerDateOut.getValue() == null) {
            Utils.messageAlertError("You haven't selected date!");
            return;
        }
        // ResPrice
        int ResPrice;
        ResPrice = Integer.parseInt(paneBR_labelResPrice.getText()) * datOfUse;

        // insert or update guest
        if (dataAccessObject.checkExistsPassport(paneBR_txtPassport.getText())) {
            initUpdateGuest();
        } else {
            initInsertGuest();
        }

        // update room and res
        if (compareTo == 0) {
            // update room
            query = "Update Room set RoomStatus = '" + 1
                    + "' where ROOMID = '" + paneBR_labelROOMID.getText()
                    + "'";
            dataAccessObject.saveData(query);
            // insert res
            valueRESIDInfoPaneBR = paneBR_labelRESID.getText(); // de get infor khi chuyen sang pane add service
            query = "insert into Reservation values('" + paneBR_labelRESID.getText()
                    + "', '"
                    + paneBR_labelROOMID.getText()
                    + "', '"
                    + paneBR_txtPassport.getText()
                    + "', '"
                    + bookDate
                    + "', '"
                    + dateOut
                    + "', '"
                    + paneBR_labelEMPID.getText()
                    + "', '"
                    + datOfUse
                    + "', '"
                    + ResPrice
                    + "', '"
                    + 0 // false la chua thanh toan - true la da thanh toan
                    + "')";
            dataAccessObject.saveData(query);
        } else if (compareTo > 0) {
            // insert res
            valueRESIDInfoPaneBR = paneBR_labelRESID.getText(); // de get infor khi chuyen sang pane add service
            query = "insert into Reservation values('" + paneBR_labelRESID.getText()
                    + "', '"
                    + paneBR_labelROOMID.getText()
                    + "', '"
                    + paneBR_txtPassport.getText()
                    + "', '"
                    + bookDate
                    + "', '"
                    + dateOut
                    + "', '"
                    + paneBR_labelEMPID.getText()
                    + "', '"
                    + datOfUse
                    + "', '"
                    + ResPrice
                    + "', '"
                    + 0 // false la chua thanh toan - true la da thanh toan
                    + "')";
            dataAccessObject.saveData(query);
        }
        Utils.messageAlertNotification("Insert sucess!");
        loadTableRoomPaneBR();
    }

    // Search room by date
    private ObservableList<Room> searchRoomByDate() {
        LocalDate today = LocalDate.now();
        dataAccessObject = new DataAccessObject();
        //list room
        query = "select * from Room";
        ObservableList<Room> roomList = dataAccessObject.getRoomData(query);
        // List Res book room before
        query = "select RESID,ROOMID,r.PASSPORT,BookDate,DateOut, GuestName,EMPID,ResPrice from Reservation as r\n"
                + "join guest as g\n"
                + "on r.PASSPORT = g.PASSPORT and r.Status = 0 and DateOut >= '" + today.toString() + "'";
        ObservableList<BookServiceModel> bsList = dataAccessObject.getBookServiceModelData(query);
        // date picker
        Date bookDate = utils.changeLocalDateToDate(paneBR_DatepickerDatIn.getValue());
        Date dateOut = utils.changeLocalDateToDate(paneBR_DatepickerDateOut.getValue());
        Room room = new Room();
        for (BookServiceModel rs : bsList) {
            room = dataAccessObject.getRoomByID(rs.getROOMID());
            if (bookDate.before(rs.getBookDate())) {
                if (dateOut.after(rs.getBookDate()) || dateOut.equals(rs)) {
                    for (int i = 0; i < roomList.size(); i++) {
                        if (roomList.get(i).getROOMID().equalsIgnoreCase(room.getROOMID())) {
                            roomList.remove(i);
                        }
                    }
                }
            } else if (bookDate.equals(rs.getBookDate())) {
                for (int i = 0; i < roomList.size(); i++) {
                    if (roomList.get(i).getROOMID().equalsIgnoreCase(room.getROOMID())) {
                        roomList.remove(i);
                    }
                }
            } else if (bookDate.after(rs.getBookDate())) {
                if (bookDate.equals(rs.getDateOut()) || bookDate.before(rs.getDateOut())) {
                    for (int i = 0; i < roomList.size(); i++) {
                        if (roomList.get(i).getROOMID().equalsIgnoreCase(room.getROOMID())) {
                            roomList.remove(i);
                        }
                    }
                }
            }
        }
        return roomList;
    }

    // paneBR_btnCheckInDate
    private void checkInRoomEmpty() {
        paneBR_btnCheckInDate.setOnAction((event) -> {
            if (paneBR_DatepickerDatIn.getValue() == null || paneBR_DatepickerDateOut.getValue() == null) {
                Utils.messageAlertError("You haven't selected date!");
                return;
            } else {
                //date in
                Date bookDate = utils.changeLocalDateToDate(paneBR_DatepickerDatIn.getValue());
                // date out
                Date dateOut = utils.changeLocalDateToDate(paneBR_DatepickerDateOut.getValue());
                LocalDate today = LocalDate.now();
                int compareTo = paneBR_DatepickerDatIn.getValue().compareTo(today);// date - today : <0 (ngay book nho hon ngay hien tai), >0(ngay book lon hon ngay hien tai)
                // date use
                long date3 = ChronoUnit.DAYS.between(paneBR_DatepickerDatIn.getValue(), paneBR_DatepickerDateOut.getValue());
                int datOfUse = (int) date3;
                if (datOfUse < 0) { // bao loi date out < date in 
                    Utils.messageAlertError("Date of use must not be less than 0!");
                    return;
                } else if (compareTo < 0) {
                    Utils.messageAlertError("Book dates cannot be less than the today!");
                    return;
                } else {
                    initTablePaneBR();
                    paneBR_tableRoom.setItems(searchRoomByDate());
                    refreshInfoPaneBR();
                }
            }
        });
    }

    // insert guest
    private void initInsertGuest() {
        boolean gender = true;
        if (paneBR_Gender.getSelectionModel().getSelectedIndex() == 1) {
            gender = false;
        }
        if (paneBR_txtEmail.getText().equalsIgnoreCase("")) { // neu email trong
            query = "insert into Guest values('" + paneBR_txtPassport.getText()
                    + "', '" + paneBR_txtGuestName.getText() + "', null, '"
                    + paneBR_txtPhone.getText() + "', '" + gender + "')";
        } else if (!paneBR_txtEmail.getText().equalsIgnoreCase("")) { // neu email co gia tri
            query = "insert into Guest values('" + paneBR_txtPassport.getText()
                    + "', '" + paneBR_txtGuestName.getText() + "', '"
                    + paneBR_txtEmail.getText() + "', '" + paneBR_txtPhone.getText()
                    + "', '" + gender + "')";
        }
        dataAccessObject.saveData(query);
    }

    // init update Guest
    private void initUpdateGuest() {
        boolean gender = true;
        if (paneBR_Gender.getSelectionModel().getSelectedIndex() == 1) {
            gender = false;
        }

        if (paneBR_txtEmail.getText().equalsIgnoreCase("")) { // neu email trong
            query = "Update Guest set GuestName = '" + paneBR_txtGuestName.getText()
                    + "', Email = null, Phone = '" + paneBR_txtPhone.getText()
                    + "', Gender = '" + gender
                    + "' where PASSPORT = '" + paneBR_txtPassport.getText()
                    + "'";
        } else if (!paneBR_txtEmail.getText().equalsIgnoreCase("")) { // neu email co gia tri
            query = "Update Guest set GuestName = '" + paneBR_txtGuestName.getText()
                    + "', Email = '" + paneBR_txtEmail.getText()
                    + "', Phone = '" + paneBR_txtPhone.getText()
                    + "', Gender = '" + gender
                    + "' where PASSPORT = '" + paneBR_txtPassport.getText()
                    + "'";
        }
        dataAccessObject.saveData(query);
    }

    //refresh
    private void refreshInfoPaneBR() {
        paneBR_labelRESID.setText(Models.Reservation.getNewReservationID());
        paneBR_labelROOMID.setText("");
        paneBR_labelResPrice.setText("");
        paneBR_txtPassport.setText("");
        paneBR_txtGuestName.setText("");
        paneBR_txtEmail.setText("");
        paneBR_txtPhone.setText("");
        paneBR_Gender.getSelectionModel().select(0);
    }

    // Check Information Book Room. 
    private boolean CheckInforBookRoom() {
        boolean check = true;
        if (paneBR_DatepickerDatIn.getValue() == null) {
            Utils.messageAlertError("Date In Empty !");
            paneBR_DatepickerDatIn.requestFocus();
            check = false;
        } else if (paneBR_DatepickerDateOut.getValue() == null) {
            Utils.messageAlertError("Date Out Empty !");
            paneBR_DatepickerDateOut.requestFocus();
            check = false;
        } else if (paneBR_txtPassport.getText().equalsIgnoreCase("")) {
            Utils.messageAlertError("You haven't input Guest's Passport!");
            paneBR_txtPassport.requestFocus();
            check = false;
        } else if (paneBR_txtGuestName.getText().equalsIgnoreCase("")) {
            Utils.messageAlertError("You haven't input Guest's Name!");
            paneBR_txtGuestName.requestFocus();
            check = false;
        } else if (CheckController.checkName(paneBR_txtGuestName.getText()) == false) {
            Utils.messageAlertError("Guest name cannot enter numbers ! \n Example : Pham Chi Cong.");
            paneBR_txtGuestName.requestFocus();
            check = false;
        } else if (!paneBR_txtEmail.getText().equalsIgnoreCase("") && CheckController.checkEmail(paneBR_txtEmail.getText()) == false) {
            Utils.messageAlertError("You are input Email Wrong ! \n Example :  Phamchiconghinhsu@gmail.com");
            paneBR_txtEmail.requestFocus();
            check = false;
        } else if (paneBR_txtPhone.getText().equalsIgnoreCase("") || CheckController.checkNumberPhone(paneBR_txtPhone.getText()) == false) {
            Utils.messageAlertError("You are input Phone Number Wrong !\n Example : 0399912345  ");
            paneBR_txtPhone.requestFocus();
            check = false;
        }

        return check;
    }

// Pane add Service ********************************************************
    // table reservation pane AS.
    private void initTablePaneRESAS() {
        paneAS_tableRes_colRESID.setCellValueFactory(new PropertyValueFactory<BookServiceModel, String>("RESID"));
        paneAS_tableRes_colROOMID.setCellValueFactory(new PropertyValueFactory<BookServiceModel, String>("ROOMID"));
        paneAS_tableRes_colPassport.setCellValueFactory(new PropertyValueFactory<BookServiceModel, String>("PASSPORT"));
        paneAS_tableRes_colGuestName.setCellValueFactory(new PropertyValueFactory<BookServiceModel, String>("GuestName"));
        paneAS_tableRes_colBookDate.setCellValueFactory(new PropertyValueFactory<BookServiceModel, Date>("BookDate"));
        paneAS_tableRes_colDateOut.setCellValueFactory(new PropertyValueFactory<BookServiceModel, Date>("DateOut"));
        paneAS_tableRes_colEMPID.setCellValueFactory(new PropertyValueFactory<BookServiceModel, String>("EMPID"));
        paneAS_tableRes_colResPrice.setCellValueFactory(new PropertyValueFactory<BookServiceModel, Integer>("ResPrice"));
    }

    // load table reservation pane AS.
    private void loadTableRoomPaneAS() {
        LocalDate today = LocalDate.now();
        initTablePaneRESAS();
        dataAccessObject = new DataAccessObject();
        query = "select r.RESID, r.ROOMID,r.PASSPORT, r.BookDate, r.DateOut, g.GuestName, r.EMPID, r.ResPrice from Reservation as r\n"
                + "join guest as g\n"
                + "on r.PASSPORT = g.PASSPORT and r.Status = 0\n"
                + "join Room \n"
                + "on Room.ROOMID = r.ROOMID and Room.RoomStatus = 1\n"
                + "where r.BookDate <= '" + today.toString() + "'";
        paneAS_tableRes.setItems(dataAccessObject.getBookServiceModelData(query));
        refreshInfoPaneAS();
    }

    //search room pane aadd service
    private void searchRoomPaneAS() {
        LocalDate today = LocalDate.now();
        initTablePaneRESAS();
        dataAccessObject = new DataAccessObject();
        query = "select r.RESID, r.ROOMID,r.PASSPORT, r.BookDate, r.DateOut, g.GuestName, r.EMPID, r.ResPrice from Reservation as r\n"
                + "join guest as g\n"
                + "on r.PASSPORT = g.PASSPORT and r.Status = 0\n"
                + "join Room \n"
                + "on Room.ROOMID = r.ROOMID and Room.RoomStatus = 1 and r.ROOMID like '%" + paneAS_txtSearch.getText() + "%'\n"
                + "where r.BookDate <= '" + today.toString() + "'";
        paneAS_tableRes.setItems(dataAccessObject.getBookServiceModelData(query));
        refreshInfoPaneAS();
    }

    // init table service
    private void initTableServiceAS() {
        paneAS_tableService_colSERID.setCellValueFactory(new PropertyValueFactory<Service, String>("SERID"));
        paneAS_tableService_colSerName.setCellValueFactory(new PropertyValueFactory<Service, String>("SerName"));
        paneAS_tableService_colSerPrice.setCellValueFactory(new PropertyValueFactory<Service, String>("SerPrice"));
        paneAS_tableService_colActive.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Service, String> param) {
                if (param.getValue().getActive()) {
                    return new ReadOnlyObjectWrapper<String>("Active");
                } else {
                    return new ReadOnlyObjectWrapper<String>("UnActive");
                }
            }
        });
        paneAS_tableService_colAddService.setCellFactory(new Callback<TableColumn<Service, Void>, TableCell<Service, Void>>() {
            @Override
            public TableCell<Service, Void> call(TableColumn<Service, Void> param) {
                final TableCell<Service, Void> cell = new TableCell<Service, Void>() {
                    private final JFXButton btn = new JFXButton("Add Service");

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        btn.setStyle("-fx-background-color:  #45A9B1; -fx-text-fill: white;-fx-background-radius: 0;");
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction((event) -> {
                                Service data = getTableView().getItems().get(getIndex());
                                getDataSerPaneAS(data);
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
    }

    //load Table Service paneAS.
    private void loadTableServicepaneAS() {
        initTableServiceAS();
        dataAccessObject = new DataAccessObject();
        query = "Select * from Service where Active = 1 ";
        paneAS_tableService.setItems(dataAccessObject.getServiceData(query));
    }

    // information table Service Used
    private void initTableServiceUsed() {
        paneAS_Info_tableSerUsed_colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceBillDetail, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceBillDetail, String> p) {
                return new ReadOnlyObjectWrapper((paneAS_Info_tableSerUsed.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        paneAS_Info_tableSerUsed_colSerName.setCellValueFactory(new PropertyValueFactory<ServiceBillDetail, String>("SERID"));
        paneAS_Info_tableSerUsed_colDate.setCellValueFactory(new PropertyValueFactory<ServiceBillDetail, Date>("BookDate"));
        paneAS_Info_tableSerUsed_colQuantity.setCellValueFactory(new PropertyValueFactory<ServiceBillDetail, Integer>("quantity"));
        paneAS_Info_tableSerUsed_colPrice.setCellValueFactory(new PropertyValueFactory<ServiceBillDetail, String>("SerPrice"));

    }

    // load service used
    private void loadTableServiceUsed() {
        initTableServiceUsed();
        dataAccessObject = new DataAccessObject();
        if (paneAS_Info_labelRESID.getText().equalsIgnoreCase("")) {
            query = "Select * from ServiceBillDetail where RESID like " + "'%RES000%'";
        } else {
            query = "Select * from ServiceBillDetail where RESID = '" + paneAS_Info_labelRESID.getText() + "'";
        }
        paneAS_Info_tableSerUsed.setItems(dataAccessObject.getServiceBillDetailData(query));
    }

    // get data table Service Pane AS.
    private void getDataSerPaneAS(Service service) {
        if (paneAS_Info_labelRESID.getText().equalsIgnoreCase("")) {
            Utils.messageAlertError("You must choose Reservation!");
            return;
        } else {
            dataAccessObject = new DataAccessObject();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate localDate = LocalDate.now();
            String sqlDATE = dtf.format(localDate); // year / month / day
            if (!dataAccessObject.getServiceBillDetailBySerID(service.getSERID(), paneAS_Info_labelRESID.getText())) {
                String sql = "insert into ServiceBillDetail(RESID, SERID,BookDate,Quantity,SerPrice)\n"
                        + " values ('" + paneAS_Info_labelRESID.getText() + "','" + service.getSERID() + "','"
                        + sqlDATE + "',1,"
                        + service.getSerPrice()
                        + ")";
                dataAccessObject.saveData(sql);
                loadTableServiceUsed();
            } else {
                String sql = "update ServiceBillDetail set Quantity =  Quantity+1 , "
                        + "SerPrice= SerPrice+" + service.getSerPrice()
                        + ", BookDate = '" + sqlDATE + "'"
                        + " where SerID = '" + service.getSERID()
                        + "' and RESID = '" + paneAS_Info_labelRESID.getText() + "';";
                dataAccessObject.saveData(sql);
                loadTableServiceUsed();
            }
            paneAS_Info_labelTotalPriceServiceUsed.setText(String.valueOf(initTotalPriceService(paneAS_Info_labelRESID.getText())));
        }
    }

    // init price service used 
    private int initTotalPriceService(String RESID) {
        int totalPriceSer = 0;
        String query = "select * from ServiceBillDetail where RESID = '" + RESID + "'";
        ObservableList<ServiceBillDetail> obList = dataAccessObject.getServiceBillDetailData(query);
        for (ServiceBillDetail ser : obList) {
            totalPriceSer += ser.getSerPrice();
        }
        return totalPriceSer;
    }

    // get data selected from Res Table
    private void getDataSelectedTableRes() {
        dataAccessObject = new DataAccessObject();
        try {
            BookServiceModel rs = paneAS_tableRes.getSelectionModel().getSelectedItem();
            paneAS_Info_labelRESID.setText(rs.getRESID());
            paneAS_Info_labelROOMID.setText(rs.getROOMID());
            paneAS_Info_labelPassport.setText(rs.getPASSPORT());
            paneAS_Info_labelGuestName.setText(rs.getGuestName());
            paneAS_Info_labelDateIn.setText(rs.getBookDate().toString());
            paneAS_Info_labelDateOut.setText(rs.getDateOut().toString());
            paneAS_Info_labelResPrice.setText(String.valueOf(rs.getResPrice()));
            loadTableServiceUsed();
            paneAS_Info_labelTotalPriceServiceUsed.setText(String.valueOf(initTotalPriceService(paneAS_Info_labelRESID.getText())));
        } catch (Exception e) {
            Utils.messageAlertNotification("You haven't selected!");
            return;
        }
    }

    // get data from pane BR when click btn addService
    private void getDataFromPaneBR() {
        dataAccessObject = new DataAccessObject();
        if (dataAccessObject.getBookServiceByRESID(valueRESIDInfoPaneBR) != null) {
            BookServiceModel rs = dataAccessObject.getBookServiceByRESID(valueRESIDInfoPaneBR);
            paneAS_Info_labelRESID.setText(rs.getRESID());
            paneAS_Info_labelROOMID.setText(rs.getROOMID());
            paneAS_Info_labelPassport.setText(rs.getPASSPORT());
            paneAS_Info_labelGuestName.setText(rs.getGuestName());
            paneAS_Info_labelDateIn.setText(rs.getBookDate().toString());
            paneAS_Info_labelDateOut.setText(rs.getDateOut().toString());
            paneAS_Info_labelResPrice.setText(String.valueOf(rs.getResPrice()));
            loadTableServiceUsed();
            paneAS_Info_labelTotalPriceServiceUsed.setText(String.valueOf(initTotalPriceService(paneAS_Info_labelRESID.getText())));
        }
    }

    //refresh information pane add service
    private void refreshInfoPaneAS() {
        paneAS_Info_labelRESID.setText("");
        paneAS_txtSearch.setText("");
        paneAS_Info_labelROOMID.setText("");
        paneAS_Info_labelPassport.setText("");
        paneAS_Info_labelGuestName.setText("");
        paneAS_Info_labelDateIn.setText("");
        paneAS_Info_labelDateOut.setText("");
        paneAS_Info_labelResPrice.setText("0");
        paneAS_Info_labelTotalPriceServiceUsed.setText("0");
        loadTableServiceUsed();
    }

    // delete row service used
    private void deleteRowServiceUsed() {
        try {
            String SERID = paneAS_Info_tableSerUsed.getSelectionModel().getSelectedItem().toString();
            String RESID = paneAS_Info_labelRESID.getText();
            query = "Delete from ServiceBillDetail where SERID = '" + SERID + "' and RESID = '" + RESID + "'";
            dataAccessObject.saveData(query);
            loadTableServiceUsed();
            paneAS_Info_labelTotalPriceServiceUsed.setText(String.valueOf(initTotalPriceService(paneAS_Info_labelRESID.getText())));
        } catch (Exception e) {
            Utils.messageAlertNotification("You haven't selected!");
            return;
        }
    }

// Pane Payment ********************************************************
    // load table Res
    private void initTableResPanePM() {
        panePM_tableRes_colRESID.setCellValueFactory(new PropertyValueFactory<BookServiceModel, String>("RESID"));
        panePM_tableRes_colRoomID.setCellValueFactory(new PropertyValueFactory<BookServiceModel, String>("ROOMID"));
        panePM_tableRes_colPassport.setCellValueFactory(new PropertyValueFactory<BookServiceModel, String>("PASSPORT"));
        panePM_tableRes_colGuestName.setCellValueFactory(new PropertyValueFactory<BookServiceModel, String>("GuestName"));
        panePM_tableRes_colBookDate.setCellValueFactory(new PropertyValueFactory<BookServiceModel, Date>("BookDate"));
        panePM_tableRes_colDateOut.setCellValueFactory(new PropertyValueFactory<BookServiceModel, Date>("DateOut"));
        panePM_tableRes_colEMPID.setCellValueFactory(new PropertyValueFactory<BookServiceModel, String>("EMPID"));
        panePM_tableRes_colResPrice.setCellValueFactory(new PropertyValueFactory<BookServiceModel, Integer>("ResPrice"));
    }

    private void loadTableResPanePM() {
        LocalDate today = LocalDate.now();
        initTableResPanePM();
        dataAccessObject = new DataAccessObject();
        query = "select r.RESID, r.ROOMID,r.PASSPORT, r.BookDate, r.DateOut, g.GuestName, r.EMPID, r.ResPrice from Reservation as r\n"
                + "join guest as g\n"
                + "on r.PASSPORT = g.PASSPORT and r.Status = 0\n"
                + "join Room \n"
                + "on Room.ROOMID = r.ROOMID and Room.RoomStatus = 1 and r.ROOMID like '%" + panePM_txtSearch.getText() + "%'\n"
                + "where r.BookDate <= '" + today.toString() + "'";
        panePM_tableRes.setItems(dataAccessObject.getBookServiceModelData(query));
    }

    // search pane payment
    private void searchRoomPanePM() {
        LocalDate today = LocalDate.now();
        initTablePaneRESAS();
        dataAccessObject = new DataAccessObject();
        query = "select r.RESID, r.ROOMID,r.PASSPORT, r.BookDate, r.DateOut, g.GuestName, r.EMPID, r.ResPrice from Reservation as r\n"
                + "join guest as g\n"
                + "on r.PASSPORT = g.PASSPORT and r.Status = 0\n"
                + "join Room \n"
                + "on Room.ROOMID = r.ROOMID and Room.RoomStatus = 1 and r.ROOMID like '" + paneAS_txtSearch.getText() + "'\n"
                + "where r.BookDate <= '" + today.toString() + "'";
        panePM_tableRes.setItems(dataAccessObject.getBookServiceModelData(query));
        refreshInfoPaneAS();
    }

    // load table service used
    private void initTableServiceUsedPanePM() {
        panePM_info_tableSerUsed_colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceBillDetail, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceBillDetail, String> p) {
                return new ReadOnlyObjectWrapper((panePM_info_tableSerUsed.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        panePM_info_tableSerUsed_colSerName.setCellValueFactory(new PropertyValueFactory<ServiceBillDetail, String>("SERID"));
        panePM_info_tableSerUsed_colDate.setCellValueFactory(new PropertyValueFactory<ServiceBillDetail, Date>("BookDate"));
        panePM_info_tableSerUsed_colQuantity.setCellValueFactory(new PropertyValueFactory<ServiceBillDetail, Integer>("quantity"));
        panePM_info_tableSerUsed_colPrice.setCellValueFactory(new PropertyValueFactory<ServiceBillDetail, String>("SerPrice"));
    }

    private void loadTableServiceUsedPanePM() {
        initTableServiceUsedPanePM();
        dataAccessObject = new DataAccessObject();
        if (panePM_info_labelRESID.getText().equalsIgnoreCase("")) {
            query = "Select * from ServiceBillDetail where RESID like " + "'%RES000%'";
        } else {
            query = "Select * from ServiceBillDetail where RESID = '" + panePM_info_labelRESID.getText() + "'";
        }
        panePM_info_tableSerUsed.setItems(dataAccessObject.getServiceBillDetailData(query));
    }

    // refresh information
    private void refreshInfoPanePM() {
        panePM_txtSearch.setText("");
        panePM_info_labelRESID.setText("");
        panePM_info_labelROOMID.setText("");
        panePM_info_labelPassport.setText("");
        panePM_info_labelGuestName.setText("");
        panePM_info_labelDateIn.setText("");
        panePM_info_labelDateOut.setText("");
        panePM_info_labelResPrice.setText("");
        loadTableServiceUsedPanePM();
        panePM_info_labelTotalServiceUsed.setText("0");
        panePM_info_totalPrice.setText("");
        panePM_info_btnPayment.setDisable(true);
    }

    // get data selected
    private void getDataSelectedPanePM() {
        dataAccessObject = new DataAccessObject();
        try {
            BookServiceModel rs = panePM_tableRes.getSelectionModel().getSelectedItem();
            panePM_info_labelRESID.setText(rs.getRESID());
            panePM_info_labelROOMID.setText(rs.getROOMID());
            panePM_info_labelPassport.setText(rs.getPASSPORT());
            panePM_info_labelGuestName.setText(rs.getGuestName());
            panePM_info_labelDateIn.setText(rs.getBookDate().toString());
            panePM_info_labelDateOut.setText(rs.getDateOut().toString());
            panePM_info_labelResPrice.setText(String.valueOf(rs.getResPrice()));
            loadTableServiceUsedPanePM();
            panePM_info_labelTotalServiceUsed.setText(String.valueOf(initTotalPriceService(panePM_info_labelRESID.getText())));
            int servicePrice = initTotalPriceService(panePM_info_labelRESID.getText());
            int ResPricce = rs.getResPrice();
            int totalPrice = servicePrice + ResPricce;
            panePM_info_totalPrice.setText(String.valueOf(totalPrice));
            panePM_info_btnPayment.setDisable(true);
        } catch (NullPointerException e) {
            Utils.messageAlertNotification("You must select Reservation!");
            return;
        }
    }

    // Payment
    private void payment() {
        if (panePM_info_labelRESID.getText().equalsIgnoreCase("")) {
            Utils.messageAlertNotification("You must be select Reservation");
            return;
        } else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Message");
            alert.setHeaderText("Payment");
            alert.setContentText("Do you want to invoice this?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();
                String DateTime = dateFormat.format(cal.getTime());
                dataAccessObject = new DataAccessObject();
                // update res
                query = "Update Reservation set Status = 1 where RESID = '" + panePM_info_labelRESID.getText() + "'";
                dataAccessObject.saveData(query);
                //update room
                query = "Update Room set RoomStatus = 0 where ROOMID = '" + panePM_info_labelROOMID.getText() + "'";
                dataAccessObject.saveData(query);
                // insert bill
                query = "insert into Bill values ('" + panePM_info_labelRESID.getText() + "', '"
                        + DateTime + "', "
                        + " '" + panePM_info_totalPrice.getText() + "'"
                        + ")";
                dataAccessObject.saveData(query);

                loadTableResPanePM();
                refreshInfoPanePM();
                Utils.messageAlertNotification("Success!");
            }
        }
    }

    private void invoice() throws IOException {
        if (panePM_info_labelRESID.getText().equalsIgnoreCase("")) {
            Utils.messageAlertNotification("You haven't select Reservation!");
            return;
        }
        RESIDPayment = panePM_info_labelRESID.getText();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/FXMLPaymentConfirmation.fxml"));
        Scene scene = new Scene(root);
        stageConfirmPayment = new Stage();
        stageConfirmPayment.setScene(scene);
        stageConfirmPayment.setResizable(false);
        stageConfirmPayment.setTitle("Invoice");
        stageConfirmPayment.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Capture2.PNG")));
        stageConfirmPayment.showAndWait();
        if(FXMLPaymentConfirmation.checkInvoice == true){
            panePM_info_btnPayment.setDisable(false);
        }else{
            panePM_info_btnPayment.setDisable(true);
        }
    }
    //set Access.
    private void setAccess() {
        dataAccessObject =  new DataAccessObject();
        Employee emp =  dataAccessObject.getUserName(txtUsername.getText());
        Accessright accessright = dataAccessObject.getAccessData(emp.getEMPID());
        btnHome.setDisable(!accessright.isHomePage());
//        btnBookRoom.setDisable(!accessright.isBookRoomPage());
        btnGuest.setDisable(!accessright.isGuestPage());
        btnRoomList.setDisable(!accessright.isRoomListPage());
        btnEmployee.setDisable(!accessright.isEmployeePage());
        btnService.setDisable(!accessright.isServicePage());
        btnView.setDisable(!accessright.isViewPage());
        btnHistory.setDisable(!accessright.isHistoryPage());
        btnAdmin.setDisable(!accessright.isAdminPage());

    }
}
