/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Accessright;
import Models.Employee;
import Models.Product;
import Models.Service;
import Models.ServiceDetail;
import Models.UserAndRoleType;
import View.Login;
import static View.Login.stageMain;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLService implements Initializable {

    @FXML
    private ImageView imageView_avatar;

    @FXML
    private JFXButton btnHistory;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnAddProduct;

    @FXML
    private JFXButton btnService;

    @FXML
    private JFXButton btnGuest;

    @FXML
    private JFXButton btnRoomList;

    @FXML
    private JFXCheckBox checkBoxActive;

    @FXML
    private JFXButton btnView;

    @FXML
    private Label txtSerID;

    @FXML
    private JFXButton btnAdmin;

    @FXML
    private JFXButton btnAddService;

    @FXML
    private JFXButton btnBookRoom;

    @FXML
    private Label txtRoleName;

    @FXML
    private Label txtUsername;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXTextField txtSerName;

    @FXML
    private JFXButton btnUpdateProduct;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton btnDelete;
    
    @FXML
    private JFXButton btnSearchService;
    
    @FXML
    private JFXButton btnSearchProduct;
    
    @FXML
    private JFXTextField txtSearchService;
    
    @FXML
    private JFXTextField txtSearchProduct;
    
    // table ServiceCombo.
    @FXML
    private TableView<Service> tableService;
    @FXML
    private TableColumn<Service, String> colActive;
    @FXML
    private TableColumn<Service, String> colSERID;
    @FXML
    private TableColumn<Service, String> colSerPrice;
    @FXML
    private TableColumn<Service, String> colSerName;
    
    
    // table Product.
    @FXML
    private TableView<Product> tableProduct;
    @FXML
    private TableColumn<Product, String> colProID;
    @FXML
    private TableColumn<Product, String> colProName;
    @FXML
    private TableColumn<Product, Void> colProAdd;
    // table ServiceDetail.
    @FXML
    private TableView<ServiceDetail> tableServiceDetail;
    @FXML
    private TableColumn<ServiceDetail, String> colNo;
    @FXML
    private TableColumn<ServiceDetail, String> colProIDDeitail;
    @FXML
    private TableColumn<ServiceDetail, String> colProNameDetail;
    @FXML
    private TableColumn<ServiceDetail, Integer> colQuantityDetail;

    private DataAccessObject dataAccessObject;
    private Utils utils;
    private String query, SERIDSelected, sername;
    private String SERIDDetail, ProIDDetail;
    public static Stage stageInfor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dataAccessObject = new DataAccessObject();
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
        btnView.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLView.fxml");
        });
        btnBookRoom.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLBookRoom.fxml");
        });
        btnAddService.setOnAction((event) -> {
            try {
                addService();
            } catch (IOException ex) {
                Logger.getLogger(FXMLService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnAddProduct.setOnAction((event) -> {
            try {
                addProduct();
            } catch (IOException ex) {
                Logger.getLogger(FXMLService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnUpdateProduct.setOnAction((event) -> {
            try {
                updateProduct();
            } catch (IOException ex) {
                Logger.getLogger(FXMLService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        // load data: 
        loadTableService();
        loadTableProduct();
        // selected
        tableService.setOnMouseClicked((event) -> {
            getDataSelected();
            loadTableServiceDetail();
        });
        tableServiceDetail.setOnMouseClicked((event) -> {
            getDataTableServiceDetail();
        });
        btnUpdate.setOnAction((event) -> {
            if (chechInformation() == true) {
                updateService();
            }
        });
        btnDelete.setOnAction((event) -> {
            deleteRowServiceDetail();
        });
        btnSearchService.setOnAction((event) -> {
            searchService();
        });
        btnSearchProduct.setOnAction((event) -> {
            searchProduct();
        });
        txtSearchService.setOnKeyPressed((event) -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                searchService();
            }
        });
        txtSearchProduct.setOnKeyPressed((event) -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                searchProduct();
            }
        });
    }

    public void setRoleType() throws SQLException {
        dataAccessObject = new DataAccessObject();
        String query = "select  UserName ,Password ,type from Employee as Emp join RoleUser as R on Emp.ROLEID =  r.ROLEID where Emp.UserName ='" + txtUsername.getText() + "' ";
        UserAndRoleType userAndRoleType = dataAccessObject.getUserAndRoleType(query);
        txtRoleName.setText(userAndRoleType.getRoleType());
        txtUsername.setText(userAndRoleType.getUserName());
        if (!userAndRoleType.getRoleType().equals("Admin")) {
            btnEmployee.setDisable(true);
            btnView.setDisable(true);
            btnAdmin.setDisable(true);
        }
    }

    private void addService() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/FXMLService_AddService.fxml"));
        Scene scene = new Scene(root);
        stageInfor = new Stage();
        stageInfor.setScene(scene);
        stageInfor.setResizable(false);
        stageInfor.setTitle("Add Service");
        stageInfor.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Capture2.PNG")));
        stageInfor.showAndWait();
        loadTableService();
    }

    private void addProduct() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/FXMLService_AddProduct.fxml"));
        Scene scene = new Scene(root);
        stageInfor = new Stage();
        stageInfor.setScene(scene);
        stageInfor.setResizable(false);
        stageInfor.setTitle("Add Product");
        stageInfor.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Capture2.PNG")));
        stageInfor.showAndWait();
        loadTableProduct();
    }

    private void updateProduct() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/FXMLService_UpdateProduct.fxml"));
        Scene scene = new Scene(root);
        stageInfor = new Stage();
        stageInfor.setScene(scene);
        stageInfor.setResizable(false);
        stageInfor.setTitle("Update Product");
        stageInfor.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Capture2.PNG")));
        stageInfor.showAndWait();
        loadTableProduct();
    }

    // init table and load table Service Combo.
    private void initTableService() {
        colSERID.setCellValueFactory(new PropertyValueFactory<Service, String>("SERID"));
        colSerName.setCellValueFactory(new PropertyValueFactory<Service, String>("SerName"));
        colSerPrice.setCellValueFactory(new PropertyValueFactory<Service, String>("SerPrice"));
        colActive.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Service, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Service, String> param) {
                if (param.getValue().getActive()) {
                    return new ReadOnlyObjectWrapper<String>("Active");
                } else {
                    return new ReadOnlyObjectWrapper<String>("UnActive");
                }
            }
        });
    }

    private void loadTableService() {
        initTableService();
        dataAccessObject = new DataAccessObject();
        query = "Select * from Service order by Active DESC";
        tableService.setItems(dataAccessObject.getServiceData(query));
    }

    // init table and load table Product.
    private void initTableProduct() {
        colProID.setCellValueFactory(new PropertyValueFactory<Product, String>("ProID"));
        colProName.setCellValueFactory(new PropertyValueFactory<Product, String>("ProName"));
        colProAdd.setCellFactory(new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {

            @Override
            public TableCell<Product, Void> call(TableColumn<Product, Void> param) {
                final TableCell<Product, Void> cell = new TableCell<Product, Void>() {
                    private final JFXButton btn = new JFXButton("   Add   ");

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        btn.setStyle("-fx-background-color:  #45A9B1; -fx-text-fill: white;-fx-background-radius: 0;");
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction((event) -> {
                                Product data = getTableView().getItems().get(getIndex());
                                insetServiceDetail(data);
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

    private void loadTableProduct() {
        initTableProduct();
        dataAccessObject = new DataAccessObject();
        query = "select * from Product where Active =  1 ";
        tableProduct.setItems(dataAccessObject.getProductData(query));
    }

    // init table and load table Service combo Detail.
    private void initTableServiceDetail() {
        colNo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ServiceDetail, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ServiceDetail, String> p) {
                return new ReadOnlyObjectWrapper((tableServiceDetail.getItems().indexOf(p.getValue()) + 1) + "");
            }
        });
        colProIDDeitail.setCellValueFactory(new PropertyValueFactory<ServiceDetail, String>("ProID"));
        colProNameDetail.setCellValueFactory(new PropertyValueFactory<ServiceDetail, String>("ProName"));
        colQuantityDetail.setCellValueFactory(new PropertyValueFactory<ServiceDetail, Integer>("Quantity"));
    }

    private void loadTableServiceDetail() {
        initTableServiceDetail();
        dataAccessObject = new DataAccessObject();
        query = "select SerID ,SE.ProID ,ProName , Quantity from ServiceDetail as SE\n"
                + "join Product as p\n"
                + "on SE.ProID =  p.ProID where SERID = '" + SERIDSelected + "' ";
        tableServiceDetail.setItems(dataAccessObject.getServiceDetailData(query));
    }
    private ObservableList<ServiceDetail> listServicedetail(){
        dataAccessObject =  new DataAccessObject();
         query = "select SerID ,SE.ProID ,ProName , Quantity from ServiceDetail as SE\n"
                + "join Product as p\n"
                + "on SE.ProID =  p.ProID where SERID = '" + SERIDSelected + "' ";
          ObservableList<ServiceDetail> list  = dataAccessObject.getServiceDetailData(query);
         return  list;
    }

    // selected
    private void getDataSelected() {
        Service service;
        try {
            service = tableService.getSelectionModel().getSelectedItem();
            SERIDSelected = service.getSERID();
            sername = service.getSerName();
            txtSerID.setText(service.getSERID());
            txtSerName.setText(service.getSerName());
            txtPrice.setText(service.getSerPrice().toString());
            if (service.getActive()) {
                checkBoxActive.setSelected(true);
            } else {
                checkBoxActive.setSelected(false);
            }
        } catch (Exception e) {
            Utils.messageAlertError("You haven't selected");
        }
    }

    // update
    private void updateService() {
        boolean active = true;
        ObservableList<ServiceDetail> list = listServicedetail();
        if (!checkBoxActive.isSelected()) {
            active = false;
        }
        if(list.isEmpty() ){
             active = false;
        }
        if (sername.equalsIgnoreCase(txtSerName.getText())) {
            query = "Update Service set SerName = '" + txtSerName.getText()
                    + "', SerPrice = '" + txtPrice.getText()
                    + "', Active = '" + active
                    + "' where SERID = '" + SERIDSelected
                    + "'";
        } else {
            if (checkNewSer(txtSerName.getText()) == true) {
                Utils.messageAlertError("Service's name already exists !");
                txtSerName.requestFocus();
                return;
            }
            query = "Update Service set SerName = '" + txtSerName.getText()
                    + "', SerPrice = '" + txtPrice.getText()
                    + "', Active = '" + active
                    + "' where SERID = '" + SERIDSelected
                    + "'";
        }

        dataAccessObject.saveData(query);
        loadTableService();
        Utils.messageAlertNotification("Update success!");
    }

    // Check New SerName Exists.
    private boolean checkNewSer(String sernamenew) {
        dataAccessObject = new DataAccessObject();
        boolean nameCheck;
        Service ser = dataAccessObject.getSerName(sernamenew);
        if (ser != null) {
            nameCheck = true;
        } else {
            nameCheck = false;
        }
        return nameCheck;
    }

    // Check Information Service.
    private boolean chechInformation() {
        boolean check = true;
        if (txtSerName.getText().equalsIgnoreCase("")) {
            txtSerName.requestFocus();
            Utils.messageAlertError("You haven't input Service's SerName!");
            check = false;
        } else if (txtPrice.getText().equalsIgnoreCase("")) {
            txtSerName.requestFocus();
            Utils.messageAlertError("You haven't input Service's Price!");
            check = false;
        } else if (CheckController.checkNumber(txtPrice.getText()) == false) {
            txtPrice.requestFocus();
            Utils.messageAlertError("Input Price by Number !");
            check = false;
        }
        return check;
    }

    private void getDataTableServiceDetail() {
        try {
            ServiceDetail serviceDetail = tableServiceDetail.getSelectionModel().getSelectedItem();
            SERIDDetail = serviceDetail.getSERID();
            ProIDDetail = serviceDetail.getProID();

        } catch (NullPointerException e) {
            Utils.messageAlertNotification("You must select Service Combo Detail !");
        }
    }

    private void deleteRowServiceDetail() {
        dataAccessObject = new DataAccessObject();
        query = "delete from ServiceDetail where SERID = '" + SERIDDetail + "' and ProID = '" +ProIDDetail+ "'";
        dataAccessObject.saveData(query);
        loadTableServiceDetail();
    }
    private void insetServiceDetail( Product product){
        if (txtSerID.getText().equalsIgnoreCase("")) {
            Utils.messageAlertError("You must choose Service!");
        } else {
            dataAccessObject = new DataAccessObject();
//            Product product = tableProduct.getSelectionModel().getSelectedItem();
            if (dataAccessObject.getServiceDetailBySerID( txtSerID.getText(),product.getProID()) ==  false) {
                String sql = "insert into ServiceDetail( SERID,ProID,Quantity)\n" +
                             "values ('"+txtSerID.getText()+"','"+product.getProID()+"',1)";
                dataAccessObject.saveData(sql);
                loadTableServiceDetail();
            } else {
                String sql = "update ServiceDetail set Quantity =  Quantity+1 "
                        + " where SerID = '" + txtSerID.getText() 
                        + "' and ProID = '" + product.getProID()+ "'";
                dataAccessObject.saveData(sql);
                loadTableServiceDetail();
            }
//            paneAS_Info_labelTotalPriceServiceUsed.setText(String.valueOf(initTotalPriceService(paneAS_Info_labelRESID.getText())));
        }
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
     
    private void searchService(){
        initTableService();
        query = "select * from dbo.Service where SerName like " + "'%" + txtSearchService.getText() + "%'";
        tableService.setItems(dataAccessObject.getServiceData(query));
    }
    private void searchProduct(){
        initTableProduct();
        query = "select * from dbo.Product where ProName like " + "'%" + txtSearchProduct.getText() + "%'";
        tableProduct.setItems(dataAccessObject.getProductData(query));
    }
}
