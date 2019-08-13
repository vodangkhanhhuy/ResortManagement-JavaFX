/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.ConnectSQL;
import Models.UserAndRoleType;
import View.Login;
import static View.Login.stageMain;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * FXML Controller class
 *
 * @author Chí Công Jr
 */
public class FXMLConnectSever implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btnConnectDB;

    @FXML
    private JFXTextField idLoalhost;

    @FXML
    private JFXTextField idUserName;

    @FXML
    private Label idMess;

    @FXML
    private JFXTextField idEmail;

    @FXML
    private JFXButton btnReLogin;

    @FXML
    private JFXPasswordField idPassword;
    public static String user, URL, pass;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        idEmail.setText(FXMLSignUpMail.Email);
        btnConnectDB.setOnAction((event) -> {
            if (checkInfoe() == true) {
                ConnectSever();
            }

        });
        btnConnectDB.setOnKeyPressed((event) -> {
            if (checkInfoe() == true) {
                ConnectSever();
            }
        });
        idPassword.setOnKeyPressed((event) -> {
            if (checkInfoe() == true) {
                ConnectSever();
            }
        });
        btnReLogin.setOnAction((event) -> {
            try {
                Parent sampleParent = FXMLLoader.load(getClass().getResource("/FXML/FXMLLogin.fxml"));
                Login.scene.setRoot(sampleParent);
                Login.scene.getStylesheets().add(getClass().getResource("/Css/CSSMain.css").toExternalForm());
                Login.stageMain.setScene(Login.scene);
                stageMain.setResizable(false);
                stageMain.setMaximized(false);
            } catch (IOException ex) {
                Logger.getLogger(FXMLAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    // check information.

    public boolean checkInfoe() {
        boolean check = true;
        if (idLoalhost.getText().equalsIgnoreCase("")) {
            idLoalhost.requestFocus();
            Utils.messageAlertError("Local host empty !");
            check = false;
        } else if (idUserName.getText().equalsIgnoreCase("")) {
            idUserName.requestFocus();
            Utils.messageAlertError("User Name empty ! ");
            check = false;
        } else if (idPassword.getText().equalsIgnoreCase("")) {
            idPassword.requestFocus();
            Utils.messageAlertError("Password empty ! ");
            check = false;
        }
        return true;
    }

    // Connect Sever.
    public void ConnectSever() {
        try {
            URL = idLoalhost.getText();
            user = idUserName.getText();
            pass = idPassword.getText();

            Models.ConnectSQL connectSQL = new ConnectSQL(URL, user, pass);
            JAXBContext jAXBContext = JAXBContext.newInstance(ConnectSQL.class);
            Marshaller marshallerObj = jAXBContext.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(connectSQL, System.out);
            File file = new File("");
            file = new File(file.getAbsolutePath() + "/src/Data/ConnectSQL.xml");
            marshallerObj.marshal(connectSQL, new FileOutputStream(file));
            try {
                Controller.ConnectDB.setConnectDB();
                Controller.ConnectDB.creatDatabase();
                Controller.ConnectDB.createTable();
                insertEmpAdmin();
                insertAccess();
                idMess.setText("Connect succeed");
                
                try {
                    SendEmail();
                    if(idMess.getText().equalsIgnoreCase("Please check your Email then login!!")){
                        btnConnectDB.setDisable(true);
                        btnReLogin.setDisable(false);
                    }
                } catch (Exception ex) {
                    idMess.setText(" Send mail Error !");
                }

            } catch (IOException | ClassNotFoundException | SQLException ex) {
                Logger.getLogger(FXMLConnectSever.class.getName()).log(Level.SEVERE, null, ex);
                idMess.setText("Error!");
            }

        } catch (JAXBException | FileNotFoundException ex) {
            Logger.getLogger(FXMLConnectSever.class.getName()).log(Level.SEVERE, null, ex);
            idMess.setText("connection failed");
        }
        
    }

    public void SendEmail() throws SQLException, Exception {
        DataAccessObject dataAccessObject = new DataAccessObject();
        String query = "select  UserName ,Password ,type from Employee as Emp join RoleUser as R on Emp.ROLEID =  r.ROLEID where Emp.UserName ='admin'";
        UserAndRoleType userAndRoleType = dataAccessObject.getUserAndRoleType(query);
        if (userAndRoleType != null) {
            UtilsEx.sendEmail.send_Email("smtp.gmail.com", idEmail.getText(), "hcnresortmanagement@gmail.com", "Hcnresort1102", "HCN Resort Management", "User : " + userAndRoleType.getUserName() + "  Password : " + userAndRoleType.getPassword());
            idMess.setText("Please check your Email then login!!");
        } else {
            idMess.setText("Information does not exist ! ");
        }
    }

    private void insertEmpAdmin() {
        String query = "insert into dbo.Employee\n"
                + "values('EMP001', 'HCN Resort Management', 'Viet Nam', 1, '"
                + idEmail.getText()
                + "', '0377791305', 'DEP001', 'POS001', 'admin', 'admin','ROL001', 1)";
        DataAccessObject dataAccessObject = new DataAccessObject();
        dataAccessObject.saveData(query);
    }
    private void insertAccess(){
        String  query = "insert into dbo.Accessright values('EMP001','True','True','True','True','True','True','True','True','True')";
        DataAccessObject dataAccessObject = new DataAccessObject();
        dataAccessObject.saveData(query);
    }
}
