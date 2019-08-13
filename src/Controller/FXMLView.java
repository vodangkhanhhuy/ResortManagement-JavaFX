/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Accessright;
import Models.Employee;
import Models.UserAndRoleType;
import View.Login;
import static View.Login.stageMain;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLView implements Initializable {

    @FXML
    private ImageView imageView_avatar;

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
    private DatePicker dateFrom;

    @FXML
    private JFXButton btnAdmin;

    @FXML
    private JFXButton btnBookRoom;

    @FXML
    private Label txtRoleName;

    @FXML
    private Label txtUsername;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private DatePicker dateTo;

    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnCheck;
    @FXML
    private LineChart<?, ?> linechar;
    @FXML
    private CategoryAxis X;

    @FXML
    private NumberAxis Y;
    @FXML
    private BarChart<?, ?> barChar;
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
        utils.loadAvatar(txtUsername, imageView_avatar);
        utils.pressKey();

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
        btnBookRoom.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLBookRoom.fxml");
        });

        btnCheck.setOnAction((event) -> {
            if (dateFrom.getValue() == null) {
                Utils.messageAlertError("You haven't input Date From ! ");
                return;
            } else if (dateTo.getValue() == null) {
                Utils.messageAlertError("You haven't input Date To! ");
                return;
            }
            long date3 = ChronoUnit.DAYS.between(dateFrom.getValue(), dateTo.getValue());
            int datOfUse = (int) date3;
            if (datOfUse < 0) { // bao loi date out < date in 
                Utils.messageAlertError("DateTo must not be less than DateFrom!");
                return;
            } else {
                loadlineChar();
                loadBarchar();
            }
        });
    }

    public void setRoleType() throws SQLException {
        dataAccessObject = new DataAccessObject();
        String query = "select  UserName ,Password ,type from Employee as Emp join RoleUser as R on Emp.ROLEID =  r.ROLEID where Emp.UserName ='" + txtUsername.getText() + "' ";
        UserAndRoleType userAndRoleType = dataAccessObject.getUserAndRoleType(query);
        txtRoleName.setText(userAndRoleType.getRoleType());
        txtUsername.setText(userAndRoleType.getUserName());
    }

    private void loadlineChar() {
        long date3 = ChronoUnit.DAYS.between(dateFrom.getValue(), dateTo.getValue());
        int datOfUse = (int) date3;
        java.util.Date date = java.util.Date.from(dateFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date datesql = new java.sql.Date(date.getTime());

        ObservableList list = dataAccessObject.getListBillDataByDate(datesql, datOfUse);
        // load date.
        LocalDate start = dateFrom.getValue();
        LocalDate end = dateTo.getValue();
        ObservableList<LocalDate> totalDates = FXCollections.observableArrayList();
        while (!start.isAfter(end)) {
            totalDates.add(start);
            start = start.plusDays(1);
        }
        // create Line Char: 
        XYChart.Series series = new XYChart.Series();
        linechar.getData().clear();
        for (int i = 0; i < list.size(); i++) {
            series.getData().add(new XYChart.Data<>(totalDates.get(i).toString(), list.get(i)));

        }
        series.setName("Revenue");
        linechar.getData().add(series);

    }

    private void loadBarchar() {
        dataAccessObject = new DataAccessObject();
        long date3 = ChronoUnit.DAYS.between(dateFrom.getValue(), dateTo.getValue());
        int datOfUse = (int) date3;

        java.util.Date date = java.util.Date.from(dateFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date datesql = new java.sql.Date(date.getTime());

        ObservableList list = dataAccessObject.getListBillDataByDate(datesql, datOfUse);
        // load date.
        LocalDate start = dateFrom.getValue();
        LocalDate end = dateTo.getValue();
        ObservableList<LocalDate> totalDates = FXCollections.observableArrayList();
        while (!start.isAfter(end)) {
            totalDates.add(start);
            start = start.plusDays(1);
        }
        // create Bar Char: 
        XYChart.Series series = new XYChart.Series();
        barChar.getData().clear();
        for (int i = 0; i < list.size(); i++) {
            series.getData().add(new XYChart.Data<>(totalDates.get(i).toString(), list.get(i)));

        }
        series.setName("Revenue");
        barChar.getData().add(series);
    }
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
}
