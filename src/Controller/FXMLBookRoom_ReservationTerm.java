/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.BookServiceModel;
import Models.Room;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLBookRoom_ReservationTerm implements Initializable {

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
    
    private DataAccessObject dao;
    private Utils utils;
    private String query;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dao = new DataAccessObject();
        getToday();
        refreshInfor();
        loadTableResPanePM();
        tableRes.setOnMouseClicked((event) -> {
            getDataSelected();
        });
    }    
    private void getToday() {
        LocalDate today = LocalDate.now();
        labelGetToDay.setText(today.toString());
    }
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
        query = "select RESID,ROOMID,r.PASSPORT,BookDate,DateOut, GuestName,EMPID,ResPrice from Reservation as r\n"
                + "join guest as g\n"
                + "on r.PASSPORT = g.PASSPORT and r.Status = 0 and DateOut = '" + labelGetToDay.getText() + "'";
        tableRes.setItems(dao.getBookServiceModelData(query));
    }
    private void refreshInfor() {
        labelROOMID.setText("");
        labelPassport.setText("");
        labelGuestName.setText("");
        labelDateIn.setText("");
        labelDateOut.setText("");
    }

    private void getDataSelected() {
        dao = new DataAccessObject();
        try {
            BookServiceModel rs = tableRes.getSelectionModel().getSelectedItem();
            labelROOMID.setText(rs.getROOMID());
            labelPassport.setText(rs.getPASSPORT());
            labelGuestName.setText(rs.getGuestName());
            labelDateIn.setText(rs.getBookDate().toString());
            labelDateOut.setText(rs.getDateOut().toString());
        } catch (NullPointerException e) {
            refreshInfor();
        }

    }
    
}
