/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.BookServiceModel;
import Models.Room;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLBookRoom_BookToday implements Initializable {

    @FXML
    private Label labelEMPID;

    @FXML
    private Label labelGuestName;

    @FXML
    private TableColumn<BookServiceModel, Integer> tableRes_colResPrice;

    @FXML
    private TableColumn<BookServiceModel, String> tableRes_colPassport;

    @FXML
    private TableColumn<BookServiceModel, String> tableRes_colEMPID;

    @FXML
    private TableColumn<BookServiceModel, String> tableRes_colRESID;

    @FXML
    private TableColumn<BookServiceModel, String> tableRes_colROOMID;

    @FXML
    private TableColumn<BookServiceModel, Date> tableRes_colDateOut;

    @FXML
    private TableColumn<BookServiceModel, Date> tableRes_colBookDate;

    @FXML
    private TableColumn<BookServiceModel, String> tableRes_colGuestName;

    @FXML
    private TableView<BookServiceModel> tableRes;

    @FXML
    private Label labelGetToDay;

    @FXML
    private Label labelROOMID;

    @FXML
    private Label labelDateIn;

    @FXML
    private Label labelDateOut;

    @FXML
    private Label labelPassport;

    @FXML
    private JFXTextField txtSearch;
    
    @FXML
    private JFXButton btnQuitBooking;

    @FXML
    private JFXButton btnBookingToday;
    
    @FXML
    private JFXButton btnSearch;

    private String idResSelected;
    /**
     * Initializes the controller class.
     */
    private DataAccessObject dao;
    private Utils utils;
    private String query;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dao = new DataAccessObject();
        labelEMPID.setText(dao.getUserName(FXMLLogin.UserName).getEMPID());
        getToday();
        refreshInfor();
        loadTableResPanePM();
        tableRes.setOnMouseClicked((event) -> {
            getDataSelected();
        });
        btnQuitBooking.setOnAction((event) -> {
            quitBooking();
        });
        btnBookingToday.setOnAction((event) -> {
            bookingToday();
        });
        btnSearch.setOnAction((event) -> {
            searchGuestByName();
        });
        txtSearch.setOnKeyPressed((event) -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                searchGuestByName();
            }
        });
    }

    private void getToday() {
        LocalDate today = LocalDate.now();
        labelGetToDay.setText(today.toString());

    }
    // load table Res

    private void initTableResPanePM() {
        tableRes_colRESID.setCellValueFactory(new PropertyValueFactory<BookServiceModel, String>("RESID"));
        tableRes_colROOMID.setCellValueFactory(new PropertyValueFactory<BookServiceModel, String>("ROOMID"));
        tableRes_colPassport.setCellValueFactory(new PropertyValueFactory<BookServiceModel, String>("PASSPORT"));
        tableRes_colGuestName.setCellValueFactory(new PropertyValueFactory<BookServiceModel, String>("GuestName"));
        tableRes_colBookDate.setCellValueFactory(new PropertyValueFactory<BookServiceModel, Date>("BookDate"));
        tableRes_colDateOut.setCellValueFactory(new PropertyValueFactory<BookServiceModel, Date>("DateOut"));
        tableRes_colEMPID.setCellValueFactory(new PropertyValueFactory<BookServiceModel, String>("EMPID"));
        tableRes_colResPrice.setCellValueFactory(new PropertyValueFactory<BookServiceModel, Integer>("ResPrice"));
    }

    private void loadTableResPanePM() {
        initTableResPanePM();
        dao = new DataAccessObject();
        query = "select r.RESID, r.ROOMID,r.PASSPORT, r.BookDate, r.DateOut, g.GuestName, r.EMPID, r.ResPrice from Reservation as r\n"
                + "join guest as g\n"
                + "on r.PASSPORT = g.PASSPORT and r.Status = 0\n"
                + "join Room \n"
                + "on Room.ROOMID = r.ROOMID and Room.RoomStatus = 0\n";
        tableRes.setItems(dao.getBookServiceModelData(query));
    }

    private void refreshInfor() {
        labelROOMID.setText("");
        labelPassport.setText("");
        labelGuestName.setText("");
        labelDateIn.setText("");
        labelDateOut.setText("");
        txtSearch.setText("");
    }

    private void getDataSelected() {
        dao = new DataAccessObject();
        idResSelected = new String();
        try {
            BookServiceModel rs = tableRes.getSelectionModel().getSelectedItem();
            labelROOMID.setText(rs.getROOMID());
            idResSelected = rs.getRESID();
            labelPassport.setText(rs.getPASSPORT());
            labelGuestName.setText(rs.getGuestName());
            labelDateIn.setText(rs.getBookDate().toString());
            labelDateOut.setText(rs.getDateOut().toString());
        } catch (NullPointerException e) {
            refreshInfor();
        }
    }

    private void quitBooking() {
        if (idResSelected == null) {
            Utils.messageAlertNotification("You must choose an order!");
            return;
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Notification");
            alert.setHeaderText("Confirmation Dialog");
            alert.setContentText("Do you want to cancel this order?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                updateRes(idResSelected);
            }
        }
    }

    private void updateRes(String RESID) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        BookServiceModel bs = dao.getBookServiceByRESID(RESID);

        query = "insert into Bill values ('" + bs.getRESID() + "', '"
                + dateFormat.format(cal.getTime()) + "', "
                + " '" + bs.getResPrice() + "'"
                + ")";
        dao.saveData(query);
        query = "Update Reservation set Status = 1, EMPID = '" + labelEMPID.getText() + "' where RESID = '" + RESID + "'";
        dao.saveData(query);
        loadTableResPanePM();
        refreshInfor();
    }

    private void bookingToday() {
        // chay ngam
        dao = new DataAccessObject();
        query = "select r.RESID,r.ROOMID,r.PASSPORT,r.BookDate,r.DateOut, GuestName,r.EMPID,r.ResPrice from Reservation as r\n"
                + "join guest as g\n"
                + "on r.PASSPORT = g.PASSPORT and r.Status = 0 and BookDate = '" + labelGetToDay.getText() + "'\n"
                + "join Room\n"
                + "on Room.ROOMID = r.ROOMID and Room.RoomStatus = 0";
        ObservableList<BookServiceModel> bsList = dao.getBookServiceModelData(query);
        query = "Select * from Room";
        ObservableList<Room> roomList = dao.getRoomData(query);

        List<String> choices = new ArrayList<>();
        for (BookServiceModel rs : bsList) {
            for (Room room : roomList) {
                if (rs.getROOMID().equals(room.getROOMID())) {
                    choices.add(room.getROOMID());
                }
            }
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>("ROOMID", choices);
        dialog.setTitle("Notification");
        dialog.setWidth(600);
        dialog.setHeaderText("Booking Today");
        dialog.setContentText("Choose House: ");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (!result.get().equals("ROOMID")) {
                query = "Update Room set RoomStatus = '" + 1
                        + "' where ROOMID = '" + result.get()
                        + "'";
                dao.saveData(query);
                loadTableResPanePM();
                refreshInfor();
            }
        }

    }
    // search by guest's name
    private void searchGuestByName(){
        initTableResPanePM();
        dao = new DataAccessObject();
        query = "select r.RESID, r.ROOMID,r.PASSPORT, r.BookDate, r.DateOut, g.GuestName, r.EMPID, r.ResPrice from Reservation as r\n"
                + "join guest as g\n"
                + "on r.PASSPORT = g.PASSPORT and r.Status = 0 and GuestName like " + "'%" + txtSearch.getText() + "%'\n"
                + "join Room \n"
                + "on Room.ROOMID = r.ROOMID and Room.RoomStatus = 0\n";
        tableRes.setItems(dao.getBookServiceModelData(query));
    }
}
