/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.Accessright;
import Models.Bill;
import Models.BookServiceModel;
import Models.Department;
import Models.Employee;
import Models.Guest;
import Models.ImagesAvatar;
import Models.Position;
import Models.Product;
import Models.Reservation;
import Models.RoleUser;
import Models.Room;
import Models.Service;
import Models.ServiceBillDetail;
import Models.ServiceDetail;
import Models.UserAndRoleType;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author TechCare
 */
public class DataAccessObject {

    private ConnectDB database = new ConnectDB();
    private ResultSet rs;
    private PreparedStatement pstm;
    private Connection connect;

    public DataAccessObject() {
    }

    public void saveData(String query) {
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            pstm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, null);
        }
    }

    public ObservableList<Department> getDepartmentData(String query) {
        ObservableList<Department> list = FXCollections.observableArrayList();
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new Department(rs.getString("DEPID"), rs.getString("DepName")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return list;
    }

    public ObservableList<Position> getPositionData(String query) {
        ObservableList<Position> list = FXCollections.observableArrayList();
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new Position(rs.getString("POSID"), rs.getString("PosName")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return list;
    }

    public ObservableList<RoleUser> getRoleUserData(String query) {
        ObservableList<RoleUser> list = FXCollections.observableArrayList();
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new RoleUser(rs.getString("ROLEID"), rs.getBoolean("type")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return list;
    }

    public ObservableList<Employee> getEmployeeData(String query) {
        ObservableList<Employee> list = FXCollections.observableArrayList();
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new Employee(rs.getString("EMPID"), rs.getString("EmpName"), rs.getString("Address"),
                        rs.getBoolean("Gender"), rs.getString("Email"), rs.getString("Phone"), rs.getString("DEPID"),
                        rs.getString("POSID"), rs.getString("UserName"), rs.getString("Password"), rs.getString("ROLEID"),
                        rs.getBoolean("Active")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return list;
    }

    public ObservableList<Guest> getGuestData(String query) {
        ObservableList<Guest> list = FXCollections.observableArrayList();
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new Guest(rs.getString("PASSPORT"), rs.getString("GuestName"), rs.getString("Email"),
                        rs.getString("Phone"), rs.getBoolean("Gender")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return list;
    }

    public Guest getGuestDataByPASSPORT(String query) {
        Guest guest = null;
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                guest = new Guest(rs.getString("PASSPORT"), rs.getString("GuestName"), rs.getString("Email"),
                        rs.getString("Phone"), rs.getBoolean("Gender")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return guest;
    }

    public ObservableList<Room> getRoomData(String query) {
        ObservableList<Room> list = FXCollections.observableArrayList();
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new Room(rs.getString("ROOMID"), rs.getString("RoomType"), rs.getBoolean("RoomStatus"),
                        rs.getBoolean("Active"), rs.getInt("RoomPrice")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return list;
    }

    public ObservableList<Service> getServiceData(String query) {
        ObservableList<Service> list = FXCollections.observableArrayList();
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new Service(rs.getString("SERID"), rs.getString("SerName"), rs.getInt("SerPrice"), rs.getBoolean("Active")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return list;
    }

    public ObservableList<Reservation> getReservationData(String query) {
        ObservableList<Reservation> list = FXCollections.observableArrayList();
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new Reservation(rs.getString("RESID"), rs.getString("ROOMID"), rs.getString("PASSPORT"),
                        rs.getDate("BookDate"), rs.getDate("DateOut"), rs.getString("EMPID"), rs.getInt("DateUse"),
                        rs.getInt("ResPrice"), rs.getBoolean("Status")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return list;
    }

    // get res by resid
    public Reservation getReservationDataByRESID(String query) {
        Reservation res = null;
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                res = new Reservation(rs.getString("RESID"), rs.getString("ROOMID"), rs.getString("PASSPORT"),
                        rs.getDate("BookDate"), rs.getDate("DateOut"), rs.getString("EMPID"), rs.getInt("DateUse"),
                        rs.getInt("ResPrice"), rs.getBoolean("Status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return res;
    }

    public ObservableList<Bill> getBillData(String query) {
        ObservableList<Bill> list = FXCollections.observableArrayList();
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new Bill(rs.getString("RESID"), rs.getString("PaymentDate"), rs.getInt("TotalPrice")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return list;
    }

    public ObservableList<ServiceBillDetail> getServiceBillDetailData(String query) {
        ObservableList<ServiceBillDetail> list = FXCollections.observableArrayList();
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new ServiceBillDetail(rs.getInt("BILLID"), rs.getString("RESID"), rs.getString("SERID"),
                        rs.getDate("BookDate"), rs.getInt("quantity"), rs.getInt("SerPrice")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return list;
    }

    // get User , Pass and Type.
    public UserAndRoleType getUserAndRoleType(String query) throws SQLException {

        connect = database.getConnection();
        pstm = connect.prepareStatement(query);
        rs = pstm.executeQuery();
        if (rs.next()) {
            UserAndRoleType userAndRoleType = new UserAndRoleType(rs.getString("UserName"), rs.getString("type"), rs.getString("Password"));
            database.close(connect, pstm, rs);
            return userAndRoleType;
        }
        database.close(connect, pstm, rs);
        return null;
    }

    // get EMP by id .
    public Employee getEmpByID(String id) {
        Employee emp = new Employee();
        String query = "select * from Employee where EMPID = '" + id + "'";
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            rs.next();
            emp = new Employee(rs.getString("EMPID"), rs.getString("EmpName"), rs.getString("Address"),
                    rs.getBoolean("Gender"), rs.getString("Email"), rs.getString("Phone"), rs.getString("DEPID"),
                    rs.getString("POSID"), rs.getString("UserName"), rs.getString("Password"), rs.getString("ROLEID"),
                    rs.getBoolean("Active"));
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            database.close(connect, pstm, rs);
        }
        return emp;
    }

    // Get EMP by UserName.
    public Employee getUserName(String User) {
        Employee emp = null;
        String query = "select * from Employee where UserName = '" + User + "'";
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                emp = new Employee(rs.getString("EMPID"), rs.getString("EmpName"), rs.getString("Address"),
                        rs.getBoolean("Gender"), rs.getString("Email"), rs.getString("Phone"), rs.getString("DEPID"),
                        rs.getString("POSID"), rs.getString("UserName"), rs.getString("Password"), rs.getString("ROLEID"),
                        rs.getBoolean("Active"));

            } else {
                emp = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            database.close(connect, pstm, rs);
        }
        return emp;
    }

    // Get Service by SerName.
    public Service getSerName(String name) {
        Service ser = null;
        String query = "select * from Service where SerName = '" + name + "'";
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                ser = new Service(rs.getString("SERID"), rs.getString("SerName"), rs.getInt("SerPrice"), rs.getBoolean("Active"));
                return ser;
            } else {
                ser = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            database.close(connect, pstm, rs);
        }
        return ser;
    }

    public ObservableList<BookServiceModel> getBookServiceModelData(String query) {
        ObservableList<BookServiceModel> list = FXCollections.observableArrayList();
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new BookServiceModel(rs.getString("RESID"), rs.getString("ROOMID"), rs.getString("PASSPORT"), rs.getString("GuestName"), rs.getDate("BookDate"),
                        rs.getDate("DateOut"), rs.getString("EMPID"), rs.getInt("ResPrice")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return list;
    }

    public boolean checkExistsPassport(String passport) {
        boolean check = false;
        String query = "Select * from Guest where passport = '" + passport + "'";
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            database.close(connect, pstm, rs);
        }
        return check;
    }

    public boolean getServiceBillDetailBySerID(String serID, String RESID) {
        boolean check = false;
        String query = "select * from ServiceBillDetail where SerID = '" + serID + "' "
                + "and RESID = '" + RESID + "'";
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            database.close(connect, pstm, rs);
        }
        return check;
    }

    public BookServiceModel getBookServiceByRESID(String RESID) {
        BookServiceModel bs = null;
        String query = "select RESID,ROOMID,r.PASSPORT,BookDate,DateOut, GuestName,EMPID,ResPrice from Reservation as r\n"
                + "join guest as g\n"
                + "on r.PASSPORT = g.PASSPORT and r.Status = 0 and RESID = '" + RESID + "'";
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                bs = new BookServiceModel(rs.getString("RESID"), rs.getString("ROOMID"), rs.getString("PASSPORT"), rs.getString("GuestName"), rs.getDate("BookDate"),
                        rs.getDate("DateOut"), rs.getString("EMPID"), rs.getInt("ResPrice"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return bs;
    }

    // load avatar
    public String getLinkImagesByEMPID(String EMPID) {
        String link = null;
        String query = "Select * from ImagesAvatar where EMPID = '" + EMPID + "'";
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                link = rs.getString("imageLink");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return link;
    }

    // get room by ID
    public Room getRoomByID(String ROOMID) {
        Room room = null;
        try {
            String query = "select * from Room where ROOMID = '" + ROOMID + "'";
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                room = new Room(ROOMID, rs.getString("RoomType"), rs.getBoolean("RoomStatus"), rs.getBoolean("Active"), rs.getInt("RoomPrice"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return room;
    }

    public Department getDepartmentDataByDepName(String Name) {
        Department dep = null;
        String query = "Select * from Department where DepName = '" + Name + "'";
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                dep = new Department(rs.getString("DEPID"), rs.getString("DepName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return dep;
    }

    public Department getDepartmentDataByDepID(String ID) {
        Department dep = null;
        String query = "Select * from Department where DEPID = '" + ID + "'";
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                dep = new Department(rs.getString("DEPID"), rs.getString("DepName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return dep;
    }

    public Position getPositionDataByPosName(String Name) {
        Position pos = null;
        String query = "Select * from Position where PosName = '" + Name + "'";
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                pos = new Position(rs.getString("POSID"), rs.getString("PosName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return pos;
    }

    public Position getPositionDataByPosID(String ID) {
        Position pos = null;
        String query = "Select * from Position where POSID = '" + ID + "'";
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                pos = new Position(rs.getString("POSID"), rs.getString("PosName"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return pos;
    }
    // get bill data by date .
    // them date sql vao !

    public int getBillDataByDate(Date Date) {
//          String query = "select * from Bill where BookDate>= '2019/06 /21' AND BookDate <= DATEADD(day,1,'2019/06 /21')";
        String query = "select * from Bill where PaymentDate>= '" + Date + "' AND PaymentDate <= DATEADD(day,1,'" + Date + "')";
        ObservableList<Bill> list = FXCollections.observableArrayList();
        int total = 0;
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new Bill(rs.getString("RESID"), rs.getString("PaymentDate"), rs.getInt("TotalPrice")));
            }
            for (Bill bill : list) {
                total += bill.getTotalPrice();
            }
        } catch (Exception e) {
            e.printStackTrace();
            total = 0;
        } finally {
            database.close(connect, pstm, rs);
        }
        return total;
    }
    // get total date from --> dateTo ( list);

    public ObservableList getListBillDataByDate(Date Date, int Dateused) {
        ObservableList list = FXCollections.observableArrayList();
        for (int i = 0; i < Dateused + 1; i++) {
            String query = "select * from Bill where PaymentDate>= DATEADD(day," + (i) + ",'" + Date + "') AND PaymentDate <= DATEADD(day," + (i + 1) + ",'" + Date + "')";
//        String query = "select * from Bill where BookDate>= '" + Date + "' AND BookDate <= DATEADD(day,1,'" + Date + "')";
            ObservableList<Bill> listbill = FXCollections.observableArrayList();
            int total;
            try {
                connect = database.getConnection();
                pstm = connect.prepareStatement(query);
                rs = pstm.executeQuery();
                while (rs.next()) {
                    listbill.add(new Bill(rs.getString("RESID"), rs.getString("PaymentDate"), rs.getInt("TotalPrice")));
                }
//                if(!rs.next()){
//                    System.out.println(rs.getString("BookDate"));
//                }
                total = 0;
                for (Bill bill : listbill) {
                    total += bill.getTotalPrice();
                }
                list.add(total);
            } catch (Exception e) {
                e.printStackTrace();
                total = 0;
                list.add(total);
            } finally {
                database.close(connect, pstm, rs);
            }

        }

        return list;
    }

    // get bill by RESID
    public Bill getBillByRESID(String RESID) {
        Bill bill = null;
        try {
            String query = "select * from Bill where RESID = '" + RESID + "'";
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                bill = new Bill(RESID, rs.getString("PaymentDate"), rs.getInt("TotalPrice"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bill;
    }

    // get data product.
    public ObservableList<Product> getProductData(String query) {
        ObservableList<Product> list = FXCollections.observableArrayList();
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getString("ProID"), rs.getString("ProName"), rs.getBoolean("Active")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return list;
    }

    public boolean getProductByName(String name) {
        boolean check = false;
        String query = "select * from Product where ProName = '" + name + "'";
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            database.close(connect, pstm, rs);
        }
        return check;
    }
    // get date Service detail.

    public ObservableList<ServiceDetail> getServiceDetailData(String query) {
        ObservableList<ServiceDetail> list = FXCollections.observableArrayList();
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
                list.add(new ServiceDetail(rs.getString("SERID"), rs.getString("ProID"), rs.getString("ProName"), rs.getInt("Quantity")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return list;
    }

    public boolean getServiceDetailBySerID(String serID, String ProID) {
        boolean check = false;
        String query = "select * from ServiceDetail where SERID = '" + serID + "' and ProID = '" + ProID + "'";
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            if (rs.next()) {
                check = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataAccessObject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            database.close(connect, pstm, rs);
        }
        return check;
    }

    // get data Access right;
    public Accessright getAccessData(String id) {
        String query = " select * from Accessright where EMPID = '"+ id +"'";
       Accessright accessright = null;
        try {
            connect = database.getConnection();
            pstm = connect.prepareStatement(query);
            rs = pstm.executeQuery();
            while (rs.next()) {
               accessright = new Accessright(rs.getString("EMPID"), rs.getBoolean("HomePage"), rs.getBoolean("BookRoomPage"), 
                             rs.getBoolean("GuestPage"),rs.getBoolean("RoomListPage"), rs.getBoolean("EmployeePage"),
                             rs.getBoolean("ServicePage"), rs.getBoolean("ViewPage"), rs.getBoolean("HistoryPage"), 
                             rs.getBoolean("AdminPage"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close(connect, pstm, rs);
        }
        return accessright;
    }
}
