/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.ConnectSQL;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author TechCare
 */
public class ConnectDB {

    static String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String DB_URL;
    static String DATABASENAME = "databasename = HCNResortManagement;";
    static String USER;
    static String PASS;
    private Connection connect;
    static ConnectSQL connectSQL;

    public ConnectDB() {
    }

    public static Connection createDB() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DB_URL + USER + PASS);
        return connection;
    }

    public static Connection SQLServer() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        Connection connection = DriverManager.getConnection(DB_URL + USER + PASS + DATABASENAME);
        return connection;
    }

    //*******************
    // setConnectDB.
    public static void setConnectDB() throws FileNotFoundException, JAXBException {
        File dos = new File("");
        dos = new File(dos.getAbsolutePath() + "/src/Data/ConnectSQL.xml");
        connectSQL = new ConnectSQL();
        FileInputStream file = new FileInputStream(dos);
        JAXBContext jAXBContext = JAXBContext.newInstance(ConnectSQL.class);
        Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
        connectSQL = (ConnectSQL) unmarshaller.unmarshal(file);
        DB_URL = connectSQL.getURL();
        USER = " user = " + connectSQL.getName() + ";";
        PASS = " password = " + connectSQL.getPassword() + "; ";
    }

    // ReadFile TXT.
    public static String readFile(String filename) throws FileNotFoundException, IOException {
        File file = new File("");
        file = new File(file.getAbsolutePath() + "/src/Data/" + filename);
        String sql = "";
        try (FileReader fileReader = new FileReader(file)) {
            int ch = 0;
            while ((ch = fileReader.read()) != -1) {
                sql += String.valueOf((char) ch);
            }
        }
        return sql;
    }

    //  Create Database.
    public static void creatDatabase() throws IOException, ClassNotFoundException, SQLException {
        String sql = readFile("CreateDB.txt");
        Connection connection;
        Statement statement ;
       
            connection = ConnectDB.createDB();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        
    }
    // Create table.

    public static void createTable() throws IOException, ClassNotFoundException, SQLException {
        String createDepartment = ConnectDB.readFile("Department.txt");
        String createPosition = ConnectDB.readFile("Position.txt");
        String createRoleUser = ConnectDB.readFile("RoleUser.txt");
        String createEmployee = ConnectDB.readFile("Employee.txt");
        String createGuest = ConnectDB.readFile("Guest.txt");
        String createRoom = ConnectDB.readFile("Room.txt");
        String createService = ConnectDB.readFile("Service.txt");
        String createReservation = ConnectDB.readFile("Reservation.txt");
        String createBill = ConnectDB.readFile("Bill.txt");
        String createServiceBillDetail = ConnectDB.readFile("ServiceBillDetail.txt");
        String createProduct = ConnectDB.readFile("Product.txt");
        String createServiceDetail = ConnectDB.readFile("ServiceDetail.txt");
        String createImagesAvatar = ConnectDB.readFile("ImagesAvatar.txt");
        String createAccess = ConnectDB.readFile("Accessright.txt");
        String insertDepartment = ConnectDB.readFile("InsertDepartment.txt");
        String insertPosition = ConnectDB.readFile("InsertPosition.txt");
        String insertRoleUser = ConnectDB.readFile("InsertRoleUser.txt");
        String insertRoleUser2 = ConnectDB.readFile("InsertRoleUser2.txt");
//        String insertAccess = ConnectDB.readFile("insertAccess.txt");
//        String createAdmin = ConnectDB.readFile("InsertAdmin.txt");
        Connection connection = null;
       
            connection = ConnectDB.SQLServer();
            Statement statement = connection.createStatement();
            statement.executeUpdate(createDepartment);
            statement.executeUpdate(createPosition);
            statement.executeUpdate(createRoleUser);
            statement.executeUpdate(createEmployee);
            statement.executeUpdate(createGuest);
            statement.executeUpdate(createRoom);
            statement.executeUpdate(createService);
            statement.executeUpdate(createReservation);
            statement.executeUpdate(createBill);
            statement.executeUpdate(createServiceBillDetail);
            statement.executeUpdate(createProduct);
            statement.executeUpdate(createServiceDetail);
            statement.executeUpdate(createImagesAvatar);
            statement.executeUpdate(createAccess);
            statement.executeUpdate(insertDepartment);
            statement.executeUpdate(insertPosition);
            statement.executeUpdate(insertRoleUser);
            statement.executeUpdate(insertRoleUser2);
//            statement.executeUpdate(insertAccess);
//            statement.executeUpdate(createAdmin);
            statement.close();
            connection.close();
   }
    // check Database.

    public static boolean CheckDB() throws ClassNotFoundException, SQLException, IOException {
        Class.forName(JDBC_DRIVER);
        Connection conn = createDB();
        String url = null;
        String sql = readFile("CreateDB.txt");
        String DBname = sql.substring(sql.lastIndexOf(" ")).trim();
        ResultSet rs = conn.getMetaData().getCatalogs();
        while (rs.next()) {
            String dbname = rs.getString(1);
            if (dbname.equals(DBname)) {
                conn.close();
                return true;
            }
        }
        conn.close();
        return false;
    }
    //check connect database .

    public static boolean checkConnect() throws SQLException {
        Connection connection = null;
        try {
            setConnectDB();
            Class.forName(JDBC_DRIVER);
            connection = createDB();
        } catch (FileNotFoundException | ClassNotFoundException | SQLException | JAXBException ex) {
            return false;
        }
        return true;
    }

    //*******************
    public Connection getConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            try {
                connect = DriverManager.getConnection(DB_URL + USER + PASS + DATABASENAME);
            } catch (SQLException ex) {
                Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connect;
    }

    // close
    public void close(Connection connect, PreparedStatement pstm, ResultSet rs) {
        try {
            if (connect != null) {
                connect.close();
            }
            if (pstm != null) {
                pstm.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void close(Connection connect, PreparedStatement pstm) {
        try {
            close(connect, pstm, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close(PreparedStatement pstm) {
        try {
            close(null, pstm, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
