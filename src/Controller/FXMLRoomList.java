/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import static Controller.FXMLHome.stageChangeInfor;
import Models.Accessright;
import Models.Employee;
import Models.Room;
import Models.UserAndRoleType;
import View.Login;
import static View.Login.stageMain;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLRoomList implements Initializable {

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
    private JFXButton btnBookRoom;

    @FXML
    private Label txtRoleName;

    @FXML
    private Label txtUsername;

    @FXML
    private JFXButton btnEmployee;

    @FXML
    private JFXButton btnHome;

    @FXML
    private ImageView imageView_avatar;

    @FXML
    private AnchorPane anchorPaneRoom;

    @FXML
    private JFXButton btnAddHouse;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXButton btnRoomUsing;
    @FXML
    private JFXButton btnRoomEmpty;
    @FXML
    private JFXButton btnRoomUnactive;
    @FXML
    private JFXButton btnRoomAll;

    DataAccessObject dataAccessObject;
    Utils utils;
    private String query;
    private GridPane grid;
    public static Stage stageInfor;
    public static String ROOMIDSelected;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dataAccessObject = new DataAccessObject();// set text user name
        txtUsername.setText(FXMLLogin.UserName);
        setAccess();
        try {
            setRoleType();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLRoomList.class.getName()).log(Level.SEVERE, null, ex);
        }
        utils = new Utils();
        utils.loadAvatar(txtUsername, imageView_avatar);
        utils.pressKey();   // Alt + char

// Button dashboard        
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
        btnService.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLService.fxml");
        });
        btnView.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLView.fxml");
        });
        btnBookRoom.setOnAction((event) -> {
            utils.changePage("/FXML/FXMLBookRoom.fxml");
        });

        loadRoom();
        btnAddHouse.setOnAction((event) -> {
            try {
                openDialogAddHouse();
            } catch (IOException ex) {
                Logger.getLogger(FXMLRoomList.class.getName()).log(Level.SEVERE, null, ex);
            }
            anchorPaneRoom.getChildren().removeAll(grid);
            loadRoom();
        });
        btnSearch.setOnAction((event) -> {
            searchHouse();
        });
        txtSearch.setOnKeyPressed((event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                searchHouse();
            }
        });
        viewRoomUsing();
        viewRoomEmpty();
        viewRoomUnactive();
        viewRoomAll();
    }

    public void setRoleType() throws SQLException {
        dataAccessObject = new DataAccessObject();
        String query = "select  UserName ,Password ,type from Employee as Emp join RoleUser as R on Emp.ROLEID =  r.ROLEID where Emp.UserName ='" + txtUsername.getText() + "' ";
        UserAndRoleType userAndRoleType = dataAccessObject.getUserAndRoleType(query);
        txtRoleName.setText(userAndRoleType.getRoleType());
        txtUsername.setText(userAndRoleType.getUserName());
    }

    private void loadRoom() {
        query = "select * from room order by active DESC";
        initLoadRoom(query);
    }

    private void initLoadRoom(String query) {
        grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(15);
        grid.setVgap(10);
        ObservableList<Room> roomList = dataAccessObject.getRoomData(query);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        int j = 0; // row
        int z = 0; // col
        for (int i = 0; i < roomList.size(); i++) {
            AnchorPane anchor = new AnchorPane();
            anchor.setPrefSize(270, 150);
            if (roomList.get(i).getRoomStatus()) { // room using
                anchor.setStyle("-fx-background-color:  #b5dad6;");
            } else if (!roomList.get(i).getActive()) { // room unactive
                anchor.setStyle("-fx-background-color: #cccccc;");
            } else { // room empty
                anchor.setStyle("-fx-background-color:  #e9ead9;");
            }
            // logo
            ImageView imgViewLogo = new ImageView(new Image(getClass().getResourceAsStream("/Images/Capture4.PNG")));
            imgViewLogo.setFitHeight(150);
            imgViewLogo.setFitWidth(250);
            imgViewLogo.setOpacity(0.1);
            anchor.getChildren().add(imgViewLogo);
            AnchorPane.setLeftAnchor(imgViewLogo, 00.0);
            AnchorPane.setTopAnchor(imgViewLogo, -10.0);
            // image
            ImageView imgView = new ImageView(new Image(getClass().getResourceAsStream("/Images/Capture2.PNG")));
            imgView.setFitHeight(30);
            imgView.setFitWidth(30);
            anchor.getChildren().add(imgView);
            AnchorPane.setLeftAnchor(imgView, 10.0);
            AnchorPane.setTopAnchor(imgView, 10.0);
            // roomID
            Label ROOMID = new Label(roomList.get(i).getROOMID());
            ROOMID.setFont(Font.font("Javanese Text", 18));
            anchor.getChildren().add(ROOMID);
            AnchorPane.setLeftAnchor(ROOMID, 50.0);
            AnchorPane.setTopAnchor(ROOMID, -5.0);
            // status
            String status = new String();
            if (roomList.get(i).getRoomStatus()) {
                status = "Using";
            } else {
                status = "Empty";
            }
            Label roomStatus = new Label(status);
            roomStatus.setFont(Font.font("Javanese Text", 15));
            anchor.getChildren().add(roomStatus);
            AnchorPane.setRightAnchor(roomStatus, 10.0);
            AnchorPane.setTopAnchor(roomStatus, 0.0);
            // room type
            Label roomType = new Label(roomList.get(i).getRoomType());
            roomType.setFont(Font.font("Javanese Text", 15));
            anchor.getChildren().add(roomType);
            AnchorPane.setLeftAnchor(roomType, 50.0);
            AnchorPane.setTopAnchor(roomType, 30.0);
            // room price
            Label roomPrice = new Label(String.valueOf(roomList.get(i).getRoomPrice()) + " $/Day");
            roomPrice.setFont(Font.font("Javanese Text", 15));
            anchor.getChildren().add(roomPrice);
            AnchorPane.setLeftAnchor(roomPrice, 50.0);
            AnchorPane.setTopAnchor(roomPrice, 60.0);
            // active
            String active = new String();
            if (roomList.get(i).getActive()) {
                active = "Active";
            } else {
                active = "UnActive";
            }
            Label roomActive = new Label(active);
            roomActive.setFont(Font.font("Javanese Text", 15));
            anchor.getChildren().add(roomActive);
            AnchorPane.setRightAnchor(roomActive, 10.0);
            AnchorPane.setTopAnchor(roomActive, 60.0);
            // Button Update 
            ImageView imgViewBtn = new ImageView(new Image(getClass().getResourceAsStream("/Images/iconDelete.png")));
            imgViewBtn.setFitHeight(20);
            imgViewBtn.setFitWidth(20);
            JFXButton btn = new JFXButton(" Update", imgViewBtn);
            btn.setPrefWidth(125.0);
            btn.setOpacity(0.8);
            btn.setStyle("-fx-background-color:  #45A9B1; -fx-text-fill: #e9ead9;-fx-background-radius: 0;");
            anchor.getChildren().add(btn);
            AnchorPane.setRightAnchor(btn, 10.0);
            AnchorPane.setTopAnchor(btn, 110.0);
            btn.setOnAction((event) -> {
                ROOMIDSelected = ROOMID.getText(); // lay id room
                try {
                    openDialogUpdateHouse();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLRoomList.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            // drop shadow
            anchor.setEffect(dropShadow);
            grid.add(anchor, z, j);
            z++;
            if ((i + 1) % 6 == 0) { // xuong hang
                j++;
                z = 0;
            }
        }
        anchorPaneRoom.getChildren().add(grid);
        AnchorPane.setLeftAnchor(grid, 0.0);
        AnchorPane.setTopAnchor(grid, 0.0);
        AnchorPane.setRightAnchor(grid, 0.0);
        AnchorPane.setBottomAnchor(grid, 0.0);
    }
// add room

    private void openDialogAddHouse() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/FXMLRoomList_AddHouse.fxml"));
        Scene scene = new Scene(root);
        stageInfor = new Stage();
        stageInfor.setScene(scene);
        stageInfor.setResizable(false);
        stageInfor.setTitle("Add House");
        stageInfor.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Capture2.PNG")));
        stageInfor.showAndWait();
        anchorPaneRoom.getChildren().removeAll(grid);
        loadRoom();
        viewRoomUsing();
        viewRoomEmpty();
        viewRoomUnactive();
        viewRoomAll();
    }
// update room

    private void openDialogUpdateHouse() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/FXMLRoomList_UpdateHouse.fxml"));
        Scene scene = new Scene(root);
        stageInfor = new Stage();
        stageInfor.setScene(scene);
        stageInfor.setResizable(false);
        stageInfor.setTitle("Update House");
        stageInfor.getIcons().add(new Image(getClass().getResourceAsStream("/Images/Capture2.PNG")));
        stageInfor.showAndWait();
        anchorPaneRoom.getChildren().removeAll(grid);
        loadRoom();
        viewRoomUsing();
        viewRoomEmpty();
        viewRoomUnactive();
        viewRoomAll();
    }
// search room

    private void searchHouse() {
        anchorPaneRoom.getChildren().removeAll(grid);
        query = "select * from dbo.Room where ROOMID like " + "'%" + txtSearch.getText() + "%' order by active DESC";
        initLoadRoom(query);
    }

    // room using
    private void viewRoomUsing() {
        query = "select * from dbo.Room where RoomStatus = 1";
        ObservableList<Room> roomList = dataAccessObject.getRoomData(query);
        int num = roomList.size();
        btnRoomUsing.setText(String.valueOf(num));
        btnRoomUsing.setOnAction((event) -> {
            query = "select * from dbo.Room where RoomStatus = 1";
            anchorPaneRoom.getChildren().removeAll(grid);
            initLoadRoom(query);
        });
    }

    // room empty
    private void viewRoomEmpty() {
        query = "select * from dbo.Room where RoomStatus = 0 and Active = 1";
        ObservableList<Room> roomList = dataAccessObject.getRoomData(query);
        int num = roomList.size();
        btnRoomEmpty.setText(String.valueOf(num));
        btnRoomEmpty.setOnAction((event) -> {
            query = "select * from dbo.Room where RoomStatus = 0 and Active = 1";
            anchorPaneRoom.getChildren().removeAll(grid);
            initLoadRoom(query);
        });
    }

    // room unactive
    private void viewRoomUnactive() {
        query = "select * from dbo.Room where Active = 0";
        ObservableList<Room> roomList = dataAccessObject.getRoomData(query);
        int num = roomList.size();
        btnRoomUnactive.setText(String.valueOf(num));
        btnRoomUnactive.setOnAction((event) -> {
            query = "select * from dbo.Room where Active = 0";
            anchorPaneRoom.getChildren().removeAll(grid);
            initLoadRoom(query);
        });
    }
    
    // room all
    private void viewRoomAll(){
        query = "select * from dbo.Room";
        ObservableList<Room> roomList = dataAccessObject.getRoomData(query);
        int num = roomList.size();
        btnRoomAll.setText(String.valueOf(num));
        btnRoomAll.setOnAction((event) -> {
            query = "select * from dbo.Room";
            anchorPaneRoom.getChildren().removeAll(grid);
            initLoadRoom(query);
        });
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
}
