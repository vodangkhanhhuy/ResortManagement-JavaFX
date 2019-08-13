/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Accessright;
import Models.Department;
import Models.Employee;
import Models.EmployeeDaoImpl;
import Models.Position;
import Models.UserAndRoleType;
import View.Login;
import static View.Login.stageMain;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class FXMLEmployee implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btnHistory;

    @FXML
    private TableColumn<EmployeeDaoImpl, String> colEMPID;
    @FXML
    private TableColumn<EmployeeDaoImpl, Void> colAccess;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private JFXButton btnService;

    @FXML
    private TableColumn<EmployeeDaoImpl, String> colGender;

    @FXML
    private TableColumn<EmployeeDaoImpl, String> colPhone;

    @FXML
    private JFXButton btnGuest;

    @FXML
    private TableColumn<EmployeeDaoImpl, String> colUser;

    @FXML
    private JFXButton btnRoomList;

    @FXML
    private TableColumn<EmployeeDaoImpl, String> colActive;

    @FXML
    private JFXButton btnView;

    @FXML
    private JFXButton btnAdmin;

    @FXML
    private TableColumn<EmployeeDaoImpl, String> colEmail;

    @FXML
    private JFXButton btnBookRoom;

    @FXML
    private Label txtRoleName;

    @FXML
    private Label txtUsername;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private TableColumn<EmployeeDaoImpl, String> colAddress;

    @FXML
    private TableColumn<EmployeeDaoImpl, String> colDepName;

    @FXML
    private TableColumn<EmployeeDaoImpl, String> colPosName;

    @FXML
    private TableColumn<EmployeeDaoImpl, String> colEmpName;

    @FXML
    private TableColumn<EmployeeDaoImpl, String> colRole;

    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnSearch;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private JFXButton btnInsert;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton btnDelete;
    @FXML
    private JFXButton btnRefresh;
    @FXML
    private JFXTextField txtEmpName;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtEmail;
    @FXML
    private JFXTextField txtPhone;
    @FXML
    private JFXComboBox<String> cbxDepName;
    @FXML
    private JFXComboBox<String> cbxPosName;
    @FXML
    private JFXComboBox<String> cbxGender;
    @FXML
    private JFXCheckBox checkBoxAdmin;
    @FXML
    private JFXCheckBox checkBoxActive;
    @FXML
    private JFXTextField txtUserNameTextField;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private ImageView imageView_avatar;
    @FXML
    private Label labelNumAdmin;
    @FXML
    private Label labelNumEmp;
    @FXML
    private AnchorPane paneAdmin;
    @FXML
    private AnchorPane paneEmp;

    EmployeeDaoImpl employeeDaoImpl;
    DataAccessObject dataAccessObject;
    Utils utils;
    private String query;
    private String idSelected;
    @FXML
    private TableView tableEMP;
    private String itemsGender[] = {"Male", "Female"};
    private String name, address, email, phone, DEPID, POSID, username, password, ROLEID;
    private Boolean gender, active;
    public static Stage stageInfor;
    public static String empIDAccess;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtUsername.setText(FXMLLogin.UserName);
        setAccessMain();
        setRoleType();
        dataAccessObject = new DataAccessObject();
        loadTableEMP();     // load table   
        initNumberAdminEmp();
        initCbxGenderEmp();     // load combobox gender
        initCbxDepName();       // load combobox DepName
        initCbxPosName();       // load combobox PosName
        utils = new Utils();
        utils.loadAvatar(txtUsername, imageView_avatar);
        utils.pressKey();

        // button dashboard
        btnGuest.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLGuest.fxml");
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
        // isert
        btnInsert.setOnAction((event) -> {
            if (checkInformation() == true) {
                insertEmp();
            }
        });
        //update
        btnUpdate.setOnAction((event) -> {
            if (checkInformation() == true) {
                updateEmp();

            }
        });
        // search by name
        btnSearch.setOnAction((event) -> {
            searchEmpByName();
        });
        txtSearch.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                searchEmpByName();
            }
        });
        // btn refresh
        btnRefresh.setOnAction((event) -> {
            refresh();
        });
        // Clicked Table.
        paneAdmin.setOnMouseClicked((event) -> {
            loadTableForAdmin();
        });
        paneEmp.setOnMouseClicked((event) -> {
            loadTableForEmp();
        });
        tableEMP.setOnMouseClicked((event) -> {
            try {
                getDataSelected();
            } catch (SQLException ex) {
                Logger.getLogger(FXMLEmployee.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }

    public void initLoadTableEmployee() {
        colEMPID.setCellValueFactory(new PropertyValueFactory<EmployeeDaoImpl, String>("empID"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<EmployeeDaoImpl, String>("empName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<EmployeeDaoImpl, String>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<EmployeeDaoImpl, String>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<EmployeeDaoImpl, String>("phone"));
        colDepName.setCellValueFactory(new PropertyValueFactory<EmployeeDaoImpl, String>("depName"));
        colPosName.setCellValueFactory(new PropertyValueFactory<EmployeeDaoImpl, String>("posName"));
        colRole.setCellValueFactory(new PropertyValueFactory<EmployeeDaoImpl, String>("type"));
        colUser.setCellValueFactory(new PropertyValueFactory<EmployeeDaoImpl, String>("userName"));
        colGender.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<EmployeeDaoImpl, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<EmployeeDaoImpl, String> param) {
                if (param.getValue().isGender()) {
                    return new ReadOnlyObjectWrapper<String>("Male");
                } else {
                    return new ReadOnlyObjectWrapper<String>("Female");
                }
            }
        });
        colActive.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<EmployeeDaoImpl, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<EmployeeDaoImpl, String> param) {
                if (param.getValue().isActive()) {
                    return new ReadOnlyObjectWrapper<String>("Active");
                } else {
                    return new ReadOnlyObjectWrapper<String>("UnActive");
                }
            }
        });
        colAccess.setCellFactory(new Callback<TableColumn<EmployeeDaoImpl, Void>, TableCell<EmployeeDaoImpl, Void>>() {
            @Override
            public TableCell<EmployeeDaoImpl, Void> call(TableColumn<EmployeeDaoImpl, Void> param) {
                final TableCell<EmployeeDaoImpl, Void> cell = new TableCell<EmployeeDaoImpl, Void>() {
                    private final JFXButton btn = new JFXButton("  Set Access  ");

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        btn.setStyle("-fx-background-color:   #45A9B1; -fx-text-fill: white;-fx-background-radius: 0;");
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            Employee empLogin = dataAccessObject.getUserName(txtUsername.getText());

                            EmployeeDaoImpl emp = getTableView().getItems().get(getIndex());
                            Employee empRow = dataAccessObject.getEmpByID(emp.getEmpID());

                            if (empLogin.getEMPID().equals("EMP001")) { // neu empLogin la emp001
                                if (empRow.getEMPID().equals("EMP001")) {
                                    btn.setDisable(true);
                                } else {
                                    if (empRow.getUserName() != null) {
                                        btn.setDisable(false);
                                    } else {
                                        btn.setDisable(true);
                                    }
                                }
                            } else { // empLogin != emp001
                                if (txtRoleName.getText().equals("Admin")) { // neu empLogin == Admin (Role)
                                    if (empRow.getROLEID().equals("ROL001")) { // row admin disable
                                        btn.setDisable(true);
                                    } else { // nhung dong con lai mo ra 

                                        if (empRow.getUserName() != null) {
                                            btn.setDisable(false);
                                        } else {
                                            btn.setDisable(true);
                                        }
                                    }
                                } else { // emplogin == employee 
                                    btn.setDisable(true);

                                }
                            }

                            btn.setOnAction((event) -> {
                                try {
                                    empIDAccess = empRow.getEMPID();
                                    setAccess();
                                } catch (IOException ex) {
                                    Logger.getLogger(FXMLEmployee.class.getName()).log(Level.SEVERE, null, ex);
                                }
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

    // open stage set access
    private void setAccess() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/FXMLEmployee_SetAccess.fxml"));
        Scene scene = new Scene(root);
        stageInfor = new Stage();
        stageInfor.setScene(scene);
        stageInfor.setResizable(false);
        stageInfor.setTitle("Access");
        stageInfor.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Capture2.PNG")));
        stageInfor.showAndWait();
    }

    // load and sorted;
    private void loadTableEMP() {
        initLoadTableEmployee();
        employeeDaoImpl = new EmployeeDaoImpl();
        query = "select EMPID , EmpName,Address,Email,Phone,DepName,PosName,type,UserName,Gender,Active from Employee as E\n"
                + " join Department as D\n"
                + " on D.DEPID = E.DEPID\n"
                + " join Position as P\n"
                + " on E.POSID =  P.POSID\n"
                + " join RoleUser as R\n"
                + " on E.ROLEID = R.ROLEID"
                + " order by Active desc";
        tableEMP.setItems(employeeDaoImpl.getEmployee(query));
    }

    // load emp roleuser is admin
    private void loadTableForAdmin() {
        initLoadTableEmployee();
        employeeDaoImpl = new EmployeeDaoImpl();
        query = "select EMPID , EmpName,Address,Email,Phone,DepName,PosName,type,UserName,Gender,Active from Employee as E\n"
                + " join Department as D\n"
                + " on D.DEPID = E.DEPID\n"
                + " join Position as P\n"
                + " on E.POSID =  P.POSID\n"
                + " join RoleUser as R\n"
                + " on E.ROLEID = R.ROLEID and R.ROLEID = 'ROL001'"
                + " order by Active desc";
        tableEMP.setItems(employeeDaoImpl.getEmployee(query));
    }

    // load emp roleuser is Emp
    private void loadTableForEmp() {
        initLoadTableEmployee();
        employeeDaoImpl = new EmployeeDaoImpl();
        query = "select EMPID , EmpName,Address,Email,Phone,DepName,PosName,type,UserName,Gender,Active from Employee as E\n"
                + " join Department as D\n"
                + " on D.DEPID = E.DEPID\n"
                + " join Position as P\n"
                + " on E.POSID =  P.POSID\n"
                + " join RoleUser as R\n"
                + " on E.ROLEID = R.ROLEID and R.ROLEID = 'ROL002'"
                + " order by Active desc";
        tableEMP.setItems(employeeDaoImpl.getEmployee(query));
    }

    public void setRoleType() {
        try {
            dataAccessObject = new DataAccessObject();
            String query = "select  UserName ,Password ,type from Employee as Emp join RoleUser as R on Emp.ROLEID =  r.ROLEID where Emp.UserName ='" + txtUsername.getText() + "' ";
            UserAndRoleType userAndRoleType = dataAccessObject.getUserAndRoleType(query);
            txtRoleName.setText(userAndRoleType.getRoleType());
            txtUsername.setText(userAndRoleType.getUserName());
            if (!userAndRoleType.getRoleType().equals("Admin")) {
                checkBoxAdmin.setDisable(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // init load number of admin and emp
    private void initNumberAdminEmp() {
        int admin = 0;
        int emp = 0;
        dataAccessObject = new DataAccessObject();
        query = "select * from Employee";
        ObservableList<Employee> empList = dataAccessObject.getEmployeeData(query);
        for (int i = 0; i < empList.size(); i++) {
            if (empList.get(i).getROLEID().equals("ROL001")) {
                admin++;
            } else {
                emp++;
            }
        }
        labelNumAdmin.setText(String.valueOf(admin));
        labelNumEmp.setText(String.valueOf(emp));

    }

    // laod combobox gender
    public void initCbxGenderEmp() {
        List<String> list = new ArrayList<String>();
        for (String item : itemsGender) {
            list.add(item);
        }
        ObservableList obList = FXCollections.observableArrayList(list);
        cbxGender.setItems(obList);
        cbxGender.getSelectionModel().select(0);
    }

    // load cbx DepName
    public void initCbxDepName() {
        List<String> listDepItems = new ArrayList<String>();
        query = "select * from Department";
        ObservableList<Department> list = dataAccessObject.getDepartmentData(query);
        for (Department dep : list) {
            listDepItems.add(dep.getDepName());
        }
        ObservableList obList = FXCollections.observableArrayList(listDepItems);
        cbxDepName.setItems(obList);
        cbxDepName.getSelectionModel().select(0);
    }

    //load cbx PosName
    public void initCbxPosName() {
        query = "select * from Position";
        List<String> listPosItems = new ArrayList<String>();
        ObservableList<Position> list = dataAccessObject.getPositionData(query);
        for (Position pos : list) {
            listPosItems.add(pos.getPosName());
        }
        ObservableList obList = FXCollections.observableArrayList(listPosItems);
        cbxPosName.setItems(obList);
        cbxPosName.getSelectionModel().select(0);
    }

    // Selected Emp to updata
    public void getDataSelected() throws SQLException {
        dataAccessObject = new DataAccessObject();
        try {
            idSelected = tableEMP.getSelectionModel().getSelectedItem().toString();
        } catch (Exception e) {
            refresh();
            return;
        }
        Employee emp = dataAccessObject.getEmpByID(idSelected);
        // set quyen truy cap
        Employee empLogin = dataAccessObject.getUserName(txtUsername.getText());

        txtEmpName.setText(emp.getEmpName());
        txtAddress.setText(emp.getAddress());
        txtEmail.setText(emp.getEmail());
        txtPhone.setText(emp.getPhone());

        //user password
        if (emp.getUserName() == null) { // neu select 
            txtUserNameTextField.setText("");
            txtPassword.setText("");
        } else {
            txtUserNameTextField.setText(emp.getUserName());
            txtPassword.setText(emp.getPassword());
        }

        // set quyen truy cap
        if (empLogin.getEMPID().equals("EMP001")) { // neu user login == emp001
            if (emp.getEMPID().equals(empLogin.getEMPID())) { // neu select emp001 
                checkBoxActive.setDisable(true);
                checkBoxAdmin.setDisable(true);
            } else {
                checkBoxActive.setDisable(false);
                checkBoxAdmin.setDisable(false);
            }
        } else { // neu user login != emp001
            if (emp.getEMPID().equals(empLogin.getEMPID())) { // khi emp select == emp login / khi click vao chinh no
                btnInsert.setDisable(false);
                btnUpdate.setDisable(false);
                checkBoxActive.setDisable(true);
                checkBoxAdmin.setDisable(true);
                txtEmail.setDisable(false);
                txtUserNameTextField.setDisable(false);
                txtPassword.setDisable(false);
            } else { // khi emp selected != empLogin
                if (emp.getEMPID().equals("EMP001")) { // khi emp select == emp001
                    btnInsert.setDisable(true);
                    btnUpdate.setDisable(true);
                    checkBoxActive.setDisable(false);
                    checkBoxAdmin.setDisable(false);
                    txtEmail.setDisable(false);
                    txtUserNameTextField.setDisable(false);
                    txtPassword.setDisable(false);
                } else if (emp.getROLEID().equals("ROL001")) { // neu select emp admin
                    btnInsert.setDisable(true);
                    btnUpdate.setDisable(true);
                    checkBoxActive.setDisable(false);
                    checkBoxAdmin.setDisable(false);
                    txtEmail.setDisable(false);
                    txtUserNameTextField.setDisable(false);
                    txtPassword.setDisable(false);
                } else { // neu select nhung emp con lai tru admin
                    btnInsert.setDisable(false);
                    btnUpdate.setDisable(false);
                    txtEmail.setDisable(true);
                    checkBoxActive.setDisable(false);
                    checkBoxAdmin.setDisable(true);
                    txtUserNameTextField.setDisable(false);
                    txtPassword.setDisable(false);
                }
            }
        }
        // type name
        String query = "select  UserName ,Password ,type from Employee as Emp join RoleUser as R on Emp.ROLEID =  r.ROLEID where Emp.EMPID ='" + emp.getEMPID() + "' ";
        UserAndRoleType role = dataAccessObject.getUserAndRoleType(query);
        if (role.getRoleType().equalsIgnoreCase("Admin")) {
            checkBoxAdmin.setSelected(true);
        } else {
            checkBoxAdmin.setSelected(false);
        }
        // active
        if (emp.getActive()) {
            checkBoxActive.setSelected(true);
        } else {
            checkBoxActive.setSelected(false);
        }

        //gender
        if (emp.getGender() == true) {
            cbxGender.getSelectionModel().select(0);
        } else {
            cbxGender.getSelectionModel().select(1);
        }

        // dep name
        query = "select * from Department";
        String DepName = emp.getDEPID();
        ObservableList<Department> listDep = dataAccessObject.getDepartmentData(query);
        for (Department dep : listDep) {
            if (dep.getDEPID().equalsIgnoreCase(DepName)) {
                DepName = dep.getDepName();
                break;
            }
        }
        cbxDepName.getSelectionModel().select(DepName);

        // pos name
        query = "select * from Position";
        String PosName = emp.getPOSID();
        ObservableList<Position> listPos = dataAccessObject.getPositionData(query);
        for (Position pos : listPos) {
            if (pos.getPOSID().equalsIgnoreCase(PosName)) {
                PosName = pos.getPosName();
                break;
            }
        }
        cbxPosName.getSelectionModel().select(PosName);
    }

    // search name
    private void searchEmpByName() {
        initLoadTableEmployee();
        employeeDaoImpl = new EmployeeDaoImpl();
        query = "select EMPID , EmpName,Address,Email,Phone,DepName,PosName,type,UserName,Gender,Active from Employee as E\n"
                + " join Department as D\n"
                + " on D.DEPID = E.DEPID\n"
                + " join Position as P\n"
                + " on E.POSID =  P.POSID and E.EmpName like " + "'%" + txtSearch.getText() + "%'\n"
                + " join RoleUser as R\n"
                + " on E.ROLEID = R.ROLEID"
                + " order by Active desc";
        tableEMP.setItems(employeeDaoImpl.getEmployee(query));
        txtSearch.setText("");
    }

    // refresh
    public void refresh() {
        String query = "select  UserName ,Password ,type from Employee as Emp join RoleUser as R on Emp.ROLEID =  r.ROLEID where Emp.UserName ='" + txtUsername.getText() + "' ";
        UserAndRoleType userAndRoleType;
        try {
            userAndRoleType = dataAccessObject.getUserAndRoleType(query);
            if (!userAndRoleType.getRoleType().equals("Admin")) {
                checkBoxAdmin.setDisable(true);
            } else {
                checkBoxAdmin.setDisable(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXMLEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }
        txtEmpName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        checkBoxActive.setSelected(false);
        checkBoxAdmin.setSelected(false);
        cbxDepName.getSelectionModel().select(0);
        cbxPosName.getSelectionModel().select(0);
        cbxGender.getSelectionModel().select(0);
        txtUserNameTextField.setText("");
        txtPassword.setText("");
        checkBoxActive.setDisable(false);
        initNumberAdminEmp();
    }

    // set information Emp to insert and update
    public void utilsEmp() {
        name = txtEmpName.getText();// name
        address = txtAddress.getText();// address
        gender = false; // gender
        if (cbxGender.getSelectionModel().getSelectedIndex() == 0) {
            gender = true;
        }

        email = txtEmail.getText(); //email
        phone = txtPhone.getText(); // phone
        active = checkBoxActive.isSelected();// active

        //DEPID
        query = "select * from Department";
        DEPID = cbxDepName.getSelectionModel().getSelectedItem();
        ObservableList<Department> listDep = dataAccessObject.getDepartmentData(query);
        for (Department dep : listDep) {
            if (dep.getDepName().equalsIgnoreCase(DEPID)) {
                DEPID = dep.getDEPID();
                break;
            }
        }

        //POSID
        query = " select * from position";
        POSID = cbxPosName.getSelectionModel().getSelectedItem();
        ObservableList<Position> listPos = dataAccessObject.getPositionData(query);
        for (Position pos : listPos) {
            if (pos.getPosName().equalsIgnoreCase(POSID)) {
                POSID = pos.getPOSID();
                break;
            }
        }

        // ROLEID
        if (checkBoxAdmin.isSelected()) {
            ROLEID = "ROL001";
        } else {
            ROLEID = "ROL002";
        }

        username = txtUserNameTextField.getText();
        password = txtPassword.getText();
        if (username.equalsIgnoreCase("")) {
            username = null;
        }
        if (password.equalsIgnoreCase("")) {
            password = null;
        }
    }

    // insert emp
    public void insertEmp() {
        if (checkUserPass() == true) {
            dataAccessObject = new DataAccessObject();
            String EMPID = Employee.getNewEmployeeID();
            utilsEmp();
            // Check New UserName.
            if ((username == null) && (password == null)) {
                query = "insert into Employee values('" + EMPID
                        + "', '" + name
                        + "', '" + address
                        + "', '" + gender
                        + "', '" + email
                        + "', '" + phone
                        + "', '" + DEPID
                        + "', '" + POSID
                        + "'," + username
                        + ", " + password
                        + ", '" + ROLEID
                        + "', '" + active
                        + "')";
                dataAccessObject.saveData(query);
                loadTableEMP();
                refresh();
            } else {
                if (checkNewUser(username)) {
                    txtUserNameTextField.requestFocus();
                    Utils.messageAlertError("UserName exists !");
                    return;
                } else {
                    query = "insert into Employee values('" + EMPID
                            + "', '" + name
                            + "', '" + address
                            + "', '" + gender
                            + "', '" + email
                            + "', '" + phone
                            + "', '" + DEPID
                            + "', '" + POSID
                            + "','" + username
                            + "', '" + password
                            + "', '" + ROLEID
                            + "', '" + active
                            + "')";
                    String sql = " insert into dbo.Accessright values('" + EMPID + "','True','false','false','false','false','false','false','false','false')";
                    dataAccessObject.saveData(query);
                    loadTableEMP();
                    dataAccessObject.saveData(sql);
                    try {
                        sendEmailInforAddNew(EMPID);
                    } catch (Exception ex) {
                        Utils.messageAlertError("Email does not exist ! ");
                        return;
                    }
                    refresh();
                }
            }
            Utils.messageAlertNotification("Successfully inserted!");
        }

    }
//    // btn Update

    public void updateEmp() {
        dataAccessObject = new DataAccessObject();
        String EMPID = idSelected;
        utilsEmp();

        // if co user == "" -> query user pass null ... else ...
        if (username == null && password == null) {
            query = "Update Employee set EmpName ='" + name + "', Address = '" + address + "', Gender = '" + gender
                    + "',Email = '" + email + "', Phone = '" + phone + "',DEPID = '" + DEPID + "',POSID =  '" + POSID + "',Username =  " + username
                    + ", Password = " + password + ", ROLEID = '" + ROLEID + "', Active = '" + active + "'"
                    + " where EMPID = '" + EMPID + "'";
        } else {
            Employee emp = dataAccessObject.getEmpByID(EMPID);
            if (emp.getUserName() == null) {
                if (checkUserPass() == true) {
                    if (checkNewUser(username)) {
                        txtUserNameTextField.requestFocus();
                        Utils.messageAlertError("UserName exists !");
                        return;
                    } else {
                        query = "Update Employee set EmpName ='" + name + "', Address = '" + address + "', Gender = '" + gender
                                + "',Email = '" + email + "', Phone = '" + phone + "',DEPID = '" + DEPID + "',POSID =  '" + POSID + "',Username =  '" + username
                                + "', Password = '" + password + "', ROLEID = '" + ROLEID + "', Active = '" + active + "'"
                                + " where EMPID = '" + EMPID + "'";
                    }
                }

            } else if (emp.getUserName().equals(username) && (emp.getPassword().equalsIgnoreCase(password))) {
                query = "Update Employee set EmpName ='" + name + "', Address = '" + address + "', Gender = '" + gender
                        + "',Email = '" + email + "', Phone = '" + phone + "',DEPID = '" + DEPID + "',POSID =  '" + POSID + "',Username =  '" + username
                        + "', Password = '" + password + "', ROLEID = '" + ROLEID + "', Active = '" + active + "'"
                        + " where EMPID = '" + EMPID + "'";
            } else if (emp.getUserName().equals(username) && !(emp.getPassword().equalsIgnoreCase(password))) {
                if (!checkUserPass()) {
                    return;
                } else {
                    query = "Update Employee set EmpName ='" + name + "', Address = '" + address + "', Gender = '" + gender
                            + "',Email = '" + email + "', Phone = '" + phone + "',DEPID = '" + DEPID + "',POSID =  '" + POSID + "',Username =  '" + username
                            + "', Password = '" + password + "', ROLEID = '" + ROLEID + "', Active = '" + active + "'"
                            + " where EMPID = '" + EMPID + "'";
                }
            } else {
                if (checkUserPass() == true) {
                    if (checkNewUser(username)) {
                        txtUserNameTextField.requestFocus();
                        Utils.messageAlertError("UserName exists !");
                        return;
                    } else {
                        query = "Update Employee set EmpName ='" + name + "', Address = '" + address + "', Gender = '" + gender
                                + "',Email = '" + email + "', Phone = '" + phone + "',DEPID = '" + DEPID + "',POSID =  '" + POSID + "',Username =  '" + username
                                + "', Password = '" + password + "', ROLEID = '" + ROLEID + "', Active = '" + active + "'"
                                + " where EMPID = '" + EMPID + "'";
                    }
                }

            }
        }
        System.out.println(query);
        try {
            sendEmailInforUpdate();
        } catch (Exception ex) {
            Logger.getLogger(FXMLEmployee.class.getName()).log(Level.SEVERE, null, ex);
        }
        dataAccessObject.saveData(query);
        loadTableEMP();
        refresh();
        Utils.messageAlertNotification("Successfully updated!");
    }

    //check input information
    public boolean checkInformation() {
        boolean check = true;
        if (txtEmpName.getText().equalsIgnoreCase("")) {
            Utils.messageAlertError("You haven't input Guest's Emp Name!");
            txtEmpName.requestFocus();
            check = false;
        } else if (CheckController.checkName(txtEmpName.getText()) == false) {
            Utils.messageAlertError("Guest name cannot enter numbers ! \n Exmaple : Pham Chi Cong.");
            txtEmpName.requestFocus();
            check = false;
        } else if (txtAddress.getText().equalsIgnoreCase("")) {
            Utils.messageAlertError("You haven't input Adress!");
            txtAddress.requestFocus();
            check = false;
        } else if (txtEmail.getText().equalsIgnoreCase("")) {
            Utils.messageAlertError("You haven't input Email!");
            txtEmail.requestFocus();
            check = false;
        } else if (CheckController.checkEmail(txtEmail.getText()) == false) {
            Utils.messageAlertError("You are input Email Wrong ! \n Example :  Phamchiconghinhsu@gmail.com");
            txtEmail.requestFocus();
            check = false;
        } else if (txtPhone.getText().equalsIgnoreCase("")) {
            Utils.messageAlertError("You haven't input Phone Number!");
            txtPhone.requestFocus();
            check = false;
        } else if (CheckController.checkNumberPhone(txtPhone.getText()) == false) {
            Utils.messageAlertError("You are input Phone Number Wrong !\n Example: 0399912345  ");
            txtPhone.requestFocus();
            check = false;
        } else if (!(txtUserNameTextField.getText().equalsIgnoreCase("")) && txtPassword.getText().equalsIgnoreCase("")) {
            Utils.messageAlertError("ERROR UserName or Password  !");
            txtUserNameTextField.requestFocus();
            check = false;
        } else if (!(txtPassword.getText().equalsIgnoreCase("")) && txtUserNameTextField.getText().equalsIgnoreCase("")) {
            txtUserNameTextField.requestFocus();
            Utils.messageAlertError("ERROR UserName or Password  !");
            check = false;
        } else if (!(txtUserNameTextField.getText().equalsIgnoreCase("")) && (CheckController.checkUser(txtUserNameTextField.getText()) == false)) {
            Utils.messageAlertError("You are input User  Wrong !\n Example : Admin12  ");
            txtUserNameTextField.requestFocus();
            check = false;
        }
        return check;
    }

    // check userPassword ;
    private boolean checkUserPass() {
        boolean check = true;
        if (!(txtPassword.getText().equalsIgnoreCase("")) && (CheckController.checkPass(txtPassword.getText()) == false)) {
            txtPassword.requestFocus();
            Utils.messageAlertError("You are input Password  Wrong !\n Example : Admin12@ ");
            check = false;
        }
        return check;
    }

    // Check New UserName Exists.
    public boolean checkNewUser(String Usernamenew) {
        dataAccessObject = new DataAccessObject();
        boolean nameCheck = false;
        Employee emp = dataAccessObject.getUserName(Usernamenew);
        if (emp != null) {
            nameCheck = true;
        }
        return nameCheck;
    }

    // send email information if update or add new emp;
    private void sendEmailInforUpdate() throws Exception {
        dataAccessObject = new DataAccessObject();
        Employee empAdmin = dataAccessObject.getUserName(txtUsername.getText());
        Employee emp = dataAccessObject.getEmpByID(idSelected);
        UtilsEx.sendEmail.send_Email("smtp.gmail.com", emp.getEmail(), "hcnresortmanagement@gmail.com", "Hcnresort1102", "HCN Resort Management", "EMPID  : " + empAdmin.getEMPID() + "  Name : " + empAdmin.getEmpName() + " Update your information");
    }

    private void sendEmailInforAddNew(String EMPID) throws Exception {
        dataAccessObject = new DataAccessObject();
        Employee empAdmin = dataAccessObject.getUserName(txtUsername.getText());
        Employee emp = dataAccessObject.getEmpByID(EMPID);
        if (emp.getUserName() != null && emp.getPassword() != null) {
            UtilsEx.sendEmail.send_Email("smtp.gmail.com", emp.getEmail(), "hcnresortmanagement@gmail.com", "Hcnresort1102", "HCN Resort Management", "EMPID  : " + empAdmin.getEMPID() + "  Name : " + empAdmin.getEmpName() + " Update your information\n"
                    + " UserName : " + emp.getUserName() + "      Password : " + emp.getPassword());
        }
    }

    private void setAccessMain() {
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
