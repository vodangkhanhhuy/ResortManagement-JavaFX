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
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLHome implements Initializable {

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
    private JFXButton btnBookRoom;

    @FXML
    private Label txtRoleName;

    @FXML
    private Label txtUsername;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnHome;

// Pane Information Emp
    @FXML
    private Label if_labelAddress;

    @FXML
    private Label if_labelEMPID;

    @FXML
    private Label if_labelPhone;

    @FXML
    private Pane if_avatar;

    @FXML
    private Label if_labelEmail;

    @FXML
    private Label if_labelDepName;

    @FXML
    private Label if_labelPosName;

    @FXML
    private Label if_labelGender;

    @FXML
    private JFXButton if_btnChageAvatar;

    @FXML
    private JFXButton if_btnChangeInfomartion;

    @FXML
    private JFXButton if_btnUserManual;

    @FXML
    private Label if_labelName;

    @FXML
    private ImageView imageView_avatar;

    private DataAccessObject dataAccessObject;
    private Utils utils;
    private String query;
    final FileChooser fileChooser = new FileChooser();
    public static Stage stageChangeInfor;

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
        utils.pressKey();
        btnLogout.setOnAction((event) -> {
            try {
                Parent sampleParent = FXMLLoader.load(getClass().getResource("/FXML/FXMLLogin.fxml"));
                Login.scene = new Scene(sampleParent);
//                Login.scene.setRoot(sampleParent);
                Login.scene.getStylesheets().add(getClass().getResource("/Css/CSSMain.css").toExternalForm());
                Login.stageMain.setScene(Login.scene);
                stageMain.setResizable(false);
                stageMain.setMaximized(false);
            } catch (IOException ex) {
                Logger.getLogger(FXMLHome.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnDashBoard();
        loadInforUserLogin();
        paneInfo();
        loadAvatar();
    }

    public void setRoleType() throws SQLException {
        dataAccessObject = new DataAccessObject();
        String query = "select  UserName ,Password ,type from Employee as Emp join RoleUser as R on Emp.ROLEID =  r.ROLEID where Emp.UserName ='" + txtUsername.getText() + "' ";
        UserAndRoleType userAndRoleType = dataAccessObject.getUserAndRoleType(query);
        txtRoleName.setText(userAndRoleType.getRoleType());
        txtUsername.setText(userAndRoleType.getUserName());
    }
//DashBoard

    private void btnDashBoard() {
        btnGuest.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLGuest.fxml");
        });
        btnEmployee.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLEmployee.fxml");
        });
        btnAdmin.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLAdmin.fxml");
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
    }

    // load information
    private void loadInforUserLogin() {
        Employee emp = dataAccessObject.getUserName(txtUsername.getText());
        if_labelEMPID.setText(emp.getEMPID());
        if_labelName.setText(emp.getEmpName());
        if_labelPhone.setText(emp.getPhone());
        if_labelAddress.setText(emp.getAddress());
        if_labelEmail.setText(emp.getEmail());
        if (emp.getGender()) {
            if_labelGender.setText("Male");
        } else {
            if_labelGender.setText("Female");
        }

        query = "select * from Department";
        ObservableList<Department> depList = dataAccessObject.getDepartmentData(query);
        for (Department dep : depList) {
            if (emp.getDEPID().equalsIgnoreCase(dep.getDEPID())) {
                if_labelDepName.setText(dep.getDepName());
                break;
            }
        }

        query = "select * from Position";
        ObservableList<Position> posList = dataAccessObject.getPositionData(query);
        for (Position pos : posList) {
            if (emp.getPOSID().equalsIgnoreCase(pos.getPOSID())) {
                if_labelPosName.setText(pos.getPosName());
                break;
            }
        }
    }
// Pane Information

    private void paneInfo() {
        if_btnChageAvatar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                changeAvatar();
            }
        });
        if_btnChangeInfomartion.setOnAction((event) -> {
            try {
                changeInforUserLogin();
            } catch (IOException ex) {
                Logger.getLogger(FXMLHome.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        if_btnUserManual.setOnAction((event) -> {
            try {
                UserManual();
            } catch (IOException ex) {
                Logger.getLogger(FXMLHome.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // load avatar
    private void loadAvatar() {
        Employee emp = dataAccessObject.getUserName(txtUsername.getText());
        String link = dataAccessObject.getLinkImagesByEMPID(emp.getEMPID());
        if (link != null) {
            File file = new File(link);
            try {
                if_avatar.setStyle("-fx-background-image: url(" + file.toURI().toURL().toExternalForm() + ");");
                Image img = new Image(file.toURI().toURL().toExternalForm());
                imageView_avatar.setImage(img);
            } catch (MalformedURLException ex) {
                Logger.getLogger(FXMLHome.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // change avatar
    private void changeAvatar() {
        File file = fileChooser.showOpenDialog(Login.stageMain);
        Employee emp = dataAccessObject.getUserName(txtUsername.getText());
        String link = dataAccessObject.getLinkImagesByEMPID(emp.getEMPID());
        if (link != null) {
            if (file != null) {
                try {
                    if_avatar.setStyle("-fx-background-image: url(" + file.toURI().toURL().toExternalForm() + ");");
                    query = "update ImagesAvatar set imageLink = '" + file + "' where EMPID = '" + emp.getEMPID() + "'";
                    dataAccessObject.saveData(query);
                    loadAvatar();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(FXMLHome.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (link == null) {
            if (file != null) {
                try {
                    if_avatar.setStyle("-fx-background-image: url(" + file.toURI().toURL().toExternalForm() + ");");
                    query = "insert into ImagesAvatar (EMPID, imageLink) values ('" + emp.getEMPID() + "', '" + file + "')";
                    dataAccessObject.saveData(query);
                    loadAvatar();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(FXMLHome.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    // change information user login
    private void changeInforUserLogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/FXMLHome_ChangeInfoUser.fxml"));
        Scene scene = new Scene(root);
        stageChangeInfor = new Stage();
        stageChangeInfor.setScene(scene);
        stageChangeInfor.setResizable(false);
        stageChangeInfor.setTitle("Information");
        stageChangeInfor.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Capture2.PNG")));
        stageChangeInfor.showAndWait();
        loadInforUserLogin();
    }

    //  user manual
    private void UserManual() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/FXMLHome_UserManual.fxml"));
        Scene scene = new Scene(root);
        stageChangeInfor = new Stage();
        stageChangeInfor.setScene(scene);
        stageChangeInfor.setResizable(false);
        stageChangeInfor.setTitle("User manual");
        stageChangeInfor.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Capture2.PNG")));
        stageChangeInfor.showAndWait();
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
