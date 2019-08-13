/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.FXMLBookRoom.RESIDPayment;
import static Controller.FXMLBookRoom.stageConfirmPayment;
import Models.Accessright;
import Models.Bill;
import Models.Employee;
import Models.Guest;
import Models.Reservation;
import Models.ServiceBillDetail;
import Models.UserAndRoleType;
import View.Login;
import static View.Login.stageMain;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLHistory implements Initializable {

    @FXML
    private ImageView imageView_avatar;

    @FXML
    private JFXButton btnHistory;

    @FXML
    private Label labelGuestName;

    @FXML
    private TableView<Bill> tableBill;

    @FXML
    private JFXButton btnGuest;

    @FXML
    private Label labelRESID;

    @FXML
    private Label labelServicePrice;

    @FXML
    private TableColumn<Bill, String> colRESID;

    @FXML
    private Label labelPhone;

    @FXML
    private Label labelROOMID;

    @FXML
    private Label labelTotalPrice;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private DatePicker datePickerTo;

    @FXML
    private Label labelPayDate;

    @FXML
    private Label labelResPrice;

    @FXML
    private Label labelDateIn;

    @FXML
    private Label labelDateOut;

    @FXML
    private TableView<ServiceBillDetail> tableServiceUsed;

    @FXML
    private JFXButton btnHome;

    @FXML
    private TableColumn<Bill, String> colBookDate;

    @FXML
    private Label labelEMPID;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private TableColumn<Bill, Integer> colTotal;

    @FXML
    private JFXButton btnService;

    @FXML
    private JFXButton btnRoomList;

    @FXML
    private JFXButton btnView;

    @FXML
    private JFXButton btnBookRoomShowPane;

    @FXML
    private JFXButton btnAdmin;

    @FXML
    private TableColumn<Bill, String> colSTT;

    @FXML
    private JFXButton btnBookRoom;

    @FXML
    private DatePicker datePickerFrom;

    @FXML
    private Label labelGender;

    @FXML
    private Label txtRoleName;

    @FXML
    private Label txtUsername;

    @FXML
    private Label labelEmail;

    @FXML
    private Label labelPassport;
    @FXML
    private TableColumn<ServiceBillDetail, String> colNo;
    @FXML
    private TableColumn<ServiceBillDetail, Date> colDate;
    @FXML
    private TableColumn<ServiceBillDetail, String> colSerName;
    @FXML
    private TableColumn<ServiceBillDetail, String> colPrice;
    @FXML
    private TableColumn<ServiceBillDetail, Integer> colQuantity;
    String ResIDBill;
    @FXML
    private JFXButton btnRefresh;
    @FXML
    private JFXButton btnInvoice;
    DataAccessObject dataAccessObject;
    public static Stage stageInvoice;
    public static String RESID_history;
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
        loadTableBill();
        utils =  new Utils();
        utils.pressKey();
        utils.loadAvatar(txtUsername,imageView_avatar);
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
        // click table Bill; 
        tableBill.setOnMouseClicked((event) -> {
            getdateTableBill();
            loadTableServiceUsed();
        });
        btnBookRoomShowPane.setOnAction((event) -> {
            loadTableBillSearch();
        });
        btnRefresh.setOnAction((event) -> {
            datePickerFrom.setValue(null);
            datePickerTo.setValue(null);
            loadTableBill();
        });
        btnInvoice.setOnAction((event) -> {
           try {
               invoice();
           } catch (IOException ex) {
               Logger.getLogger(FXMLHistory.class.getName()).log(Level.SEVERE, null, ex);
           }
        });
    }    
    public  void setRoleType() throws SQLException{
        dataAccessObject =  new DataAccessObject();
        String query = "select  UserName ,Password ,type from Employee as Emp join RoleUser as R on Emp.ROLEID =  r.ROLEID where Emp.UserName ='"+txtUsername.getText()+"' ";
        UserAndRoleType userAndRoleType = dataAccessObject.getUserAndRoleType(query);
        txtRoleName.setText(userAndRoleType.getRoleType());
        txtUsername.setText(userAndRoleType.getUserName());
    }    
    // set Col table Bill.
    private void initTableBill() {
        colSTT.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bill, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Bill, String> p) {
                return new ReadOnlyObjectWrapper((tableBill.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
         colRESID.setCellValueFactory(new PropertyValueFactory<Bill, String>("RESID"));
         colBookDate.setCellValueFactory(new PropertyValueFactory<Bill, String>("PaymentDate"));
         colTotal.setCellValueFactory(new PropertyValueFactory<Bill, Integer>("TotalPrice"));
    }
    // Load data  Table Bill
    private void loadTableBill(){
        initTableBill();
        dataAccessObject =  new DataAccessObject();
        String query = "select * from Bill";  
        tableBill.setItems(dataAccessObject.getBillData(query));
    }
    // get Information by Table Bill.
    private void getdateTableBill() {
        dataAccessObject = new DataAccessObject();
        try {
            Bill bill = tableBill.getSelectionModel().getSelectedItem();
            ResIDBill = bill.getRESID();
            String query = "select * from Reservation where RESID = '" + bill.getRESID() + "'";
            Reservation res = dataAccessObject.getReservationDataByRESID(query);
            labelRESID.setText(res.getRESID());
            labelROOMID.setText(res.getROOMID());
            labelDateIn.setText(res.getBookDate().toString());
            labelDateOut.setText(res.getDateOut().toString());
            labelResPrice.setText(res.getResPrice().toString()+"  $");
            labelEMPID.setText(res.getEMPID());
            labelPayDate.setText(bill.getPaymentDate());
            String sql = "Select * from Guest where PASSPORT = '"+res.getPASSPORT() + "'";
           
            Guest guest = dataAccessObject.getGuestDataByPASSPORT(sql);
            labelPassport.setText(res.getPASSPORT());
            labelGuestName.setText(guest.getGuestName());
            if (guest.getEmail() == null) {
                labelEmail.setText("");
            }else{
                labelEmail.setText(guest.getEmail());
            }
            if (guest.getGender() == true) {
                labelGender.setText("Male");
            } else {
                labelGender.setText("Female");
            }
            labelPhone.setText(guest.getPhone());
            labelTotalPrice.setText(bill.getTotalPrice().toString()+"  $");
            Integer priceSer = (bill.getTotalPrice() - res.getResPrice());
            labelServicePrice.setText(priceSer.toString()+"  $");
        } catch (Exception ex) {
            Utils.messageAlertError("You haven't selected");
        }
    }
    private void initTableServiceDetail(){
         colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceBillDetail, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceBillDetail, String> p) {
                return new ReadOnlyObjectWrapper((tableServiceUsed.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        colSerName.setCellValueFactory(new PropertyValueFactory<ServiceBillDetail, String>("SERID"));
        colDate.setCellValueFactory(new PropertyValueFactory<ServiceBillDetail, Date>("BookDate"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<ServiceBillDetail, Integer>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<ServiceBillDetail, String>("SerPrice"));
    }
    private void loadTableServiceUsed(){
        initTableServiceDetail();
        dataAccessObject = new DataAccessObject();
        String query = "select * from ServiceBillDetail where RESID = '" + ResIDBill + "'";
        tableServiceUsed.setItems(dataAccessObject.getServiceBillDetailData(query));
    }
    private void loadTableBillSearch(){
        
        String query;
        if (datePickerFrom.getValue() == null && datePickerTo.getValue() == null) {
            dataAccessObject = new DataAccessObject();
            query = "select * from Bill";
            tableBill.setItems(dataAccessObject.getBillData(query));

        }else if (datePickerFrom.getValue() != null && datePickerTo.getValue() == null) {
                java.util.Date date = java.util.Date.from(datePickerFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date dateFrom = new java.sql.Date(date.getTime());
                query = "select * from Bill where PaymentDate >= '"+dateFrom+"'";
                tableBill.setItems(dataAccessObject.getBillData(query));          
           
        } else if (datePickerFrom.getValue() == null && datePickerTo.getValue() != null) {
                java.util.Date date = java.util.Date.from(datePickerTo.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date dateTo = new java.sql.Date(date.getTime());
                query = "select * from Bill where PaymentDate <= DATEADD(day,1,'"+dateTo+"')";
                tableBill.setItems(dataAccessObject.getBillData(query)); 
        }else{
                long date3 = ChronoUnit.DAYS.between(datePickerFrom.getValue(), datePickerTo.getValue());
                int datOfUse = (int) date3;
                if (datOfUse < 0) { // bao loi date out < date in 
                    Utils.messageAlertError("DateTo must not be less than DateFrom!");
                    return;
                }
                java.util.Date date = java.util.Date.from(datePickerFrom.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date dateFrom = new java.sql.Date(date.getTime());
                java.util.Date date2 = java.util.Date.from(datePickerTo.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date dateTo = new java.sql.Date(date2.getTime());
                query = "select * from Bill where PaymentDate >= '"+dateFrom+"' AND PaymentDate <= DATEADD(day,1,'"+dateTo+"')";
                tableBill.setItems(dataAccessObject.getBillData(query)); 
        }
    }
    
    // invoice
    private void invoice() throws IOException{
        if (labelRESID.getText().equalsIgnoreCase("")) {
            Utils.messageAlertNotification("You haven't select Bill!");
            return;
        }
        RESID_history = labelRESID.getText();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/FXMLInvoiceHistory.fxml"));
        Scene scene = new Scene(root);
        stageInvoice = new Stage();
        stageInvoice.setScene(scene);
        stageInvoice.setResizable(false);
        stageInvoice.setTitle("Invoice");
        stageInvoice.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Capture2.PNG")));
        stageInvoice.showAndWait();
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
