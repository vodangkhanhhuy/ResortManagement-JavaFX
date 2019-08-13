/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author Chí Công Jr
 */
public class Accessright {
    private String EMPID;
    private boolean HomePage, BookRoomPage,GuestPage,RoomListPage,EmployeePage,ServicePage,ViewPage,HistoryPage,AdminPage;

    public Accessright() {
    }

    public Accessright(String EMPID, boolean HomePage, boolean BookRoomPage, boolean GuestPage, boolean RoomListPage, boolean EmployeePage, boolean ServicePage, boolean ViewPage, boolean HistoryPage, boolean AdminPage) {
        this.EMPID = EMPID;
        this.HomePage = HomePage;
        this.BookRoomPage = BookRoomPage;
        this.GuestPage = GuestPage;
        this.RoomListPage = RoomListPage;
        this.EmployeePage = EmployeePage;
        this.ServicePage = ServicePage;
        this.ViewPage = ViewPage;
        this.HistoryPage = HistoryPage;
        this.AdminPage = AdminPage;
    }

    public String getEMPID() {
        return EMPID;
    }

    public void setEMPID(String EMPID) {
        this.EMPID = EMPID;
    }

    public boolean isHomePage() {
        return HomePage;
    }

    public void setHomePage(boolean HomePage) {
        this.HomePage = HomePage;
    }

    public boolean isBookRoomPage() {
        return BookRoomPage;
    }

    public void setBookRoomPage(boolean BookRoomPage) {
        this.BookRoomPage = BookRoomPage;
    }

    public boolean isGuestPage() {
        return GuestPage;
    }

    public void setGuestPage(boolean GuestPage) {
        this.GuestPage = GuestPage;
    }

    public boolean isRoomListPage() {
        return RoomListPage;
    }

    public void setRoomListPage(boolean RoomListPage) {
        this.RoomListPage = RoomListPage;
    }

    public boolean isEmployeePage() {
        return EmployeePage;
    }

    public void setEmployeePage(boolean EmployeePage) {
        this.EmployeePage = EmployeePage;
    }

    public boolean isServicePage() {
        return ServicePage;
    }

    public void setServicePage(boolean ServicePage) {
        this.ServicePage = ServicePage;
    }

    public boolean isViewPage() {
        return ViewPage;
    }

    public void setViewPage(boolean ViewPage) {
        this.ViewPage = ViewPage;
    }

    public boolean isHistoryPage() {
        return HistoryPage;
    }

    public void setHistoryPage(boolean HistoryPage) {
        this.HistoryPage = HistoryPage;
    }

    public boolean isAdminPage() {
        return AdminPage;
    }

    public void setAdminPage(boolean AdminPage) {
        this.AdminPage = AdminPage;
    }
    
    
}

