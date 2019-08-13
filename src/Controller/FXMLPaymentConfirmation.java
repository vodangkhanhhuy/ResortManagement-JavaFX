/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Models.BookServiceModel;
import Models.Service;
import Models.ServiceBillDetail;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author TechCare
 */
public class FXMLPaymentConfirmation implements Initializable {

    public FXMLPaymentConfirmation() {
    }

    @FXML
    private Label labelGuestName;

    @FXML
    private Label labelRESID;

    @FXML
    private AnchorPane panePayment;

    @FXML
    private Label labelGetDateTime;

    @FXML
    private JFXButton btnConfirm;

    @FXML
    private Label labelROOMID;

    @FXML
    private Label labelResPrice;

    @FXML
    private Label labelDateIn;

    @FXML
    private Label labelTotalServiceUsed;

    @FXML
    private Label labelDateOut;

    @FXML
    private Label labelPassport;

    @FXML
    private Label labelTotalPrice;

    private DataAccessObject dao;
    private String query;
    private int RESprice;
    public static boolean checkInvoice = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dao = new DataAccessObject();
        init();
        btnConfirm.setOnAction((event) -> {
            print(panePayment);
        });
        loadService();
    }

    private void init() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        labelRESID.setText(FXMLBookRoom.RESIDPayment);
        BookServiceModel bs = dao.getBookServiceByRESID(FXMLBookRoom.RESIDPayment);
        labelROOMID.setText(bs.getROOMID());
        labelPassport.setText(bs.getPASSPORT());
        labelGuestName.setText(bs.getGuestName());
        labelDateIn.setText(bs.getBookDate().toString());
        labelDateOut.setText(bs.getDateOut().toString());
        labelGetDateTime.setText(dateFormat.format(cal.getTime()));
        labelResPrice.setText(String.valueOf(bs.getResPrice()) + " $");
        RESprice = bs.getResPrice();
    }

    private void loadService() {
        dao = new DataAccessObject();
        query = "Select * from ServiceBillDetail where RESID = '" + labelRESID.getText() + "'";
        ObservableList<ServiceBillDetail> billDetailList = dao.getServiceBillDetailData(query);
        query = "Select * from Service";
        ObservableList<Service> serviceList = dao.getServiceData(query);

        // column name header
        Label no = new Label("No.");
        Label serID = new Label("SERVICE");
        Label qtt = new Label("QUANTITY");
        Label price = new Label("PRICE");
        // css col header
        no.setStyle("-fx-font-size: 11px;-fx-text-fill: #1BBC9B;");
        serID.setStyle("-fx-font-size: 11px;;-fx-text-fill: #1BBC9B;");
        qtt.setStyle("-fx-font-size: 11px;;-fx-text-fill: #1BBC9B;");
        price.setStyle("-fx-font-size: 11px;;-fx-text-fill: #1BBC9B;");
        // line
        Label line1 = new Label("-------");
        Label line2 = new Label("-------");
        Label line3 = new Label("-------");
        Label line4 = new Label("-------");
        // create grid pane
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(15); // margin H 15px
        grid.setVgap(0);
//        grid.setStyle("-fx-background-color: red;");
        
        // add column into grid pane
        grid.add(no, 0, 0);
        grid.add(serID, 1, 0);
        grid.add(qtt, 2, 0);
        grid.add(price, 3, 0);
        // add line into grid pane
        grid.add(line1, 0, 1);
        grid.add(line2, 1, 1);
        grid.add(line3, 2, 1);
        grid.add(line4, 3, 1);
        
        int j = 0, x = 0; // j: count row(row = 1 / j = 3) x: total price service
        for (int i = 0; i < billDetailList.size(); i++) { // get data insert into row
            for (Service ser : serviceList) { // get service name by serid
                if (billDetailList.get(i).getSERID().equals(ser.getSERID())) {
                    // row j, data column 0,1,2,3
                    Label stt = new Label(String.valueOf(i + 1));
                    Label name = new Label(ser.getSerName());
                    Label qtty = new Label(String.valueOf(billDetailList.get(i).getQuantity()));
                    Label pri = new Label(String.valueOf(billDetailList.get(i).getSerPrice()));
                    // css
                    name.setStyle("-fx-font-size: 9px; -fx-font-family: Book Antiqua;");
                    stt.setStyle("-fx-font-size: 9px; -fx-font-family: Book Antiqua;");
                    qtty.setStyle("-fx-font-size: 9px; -fx-font-family: Book Antiqua;");
                    pri.setStyle("-fx-font-size: 9px; -fx-font-family: Book Antiqua;");
                    // insert into gridpane
                    grid.add(stt, 0, i + 2);
                    grid.add(name, 1, i + 2);
                    grid.add(qtty, 2, i + 2);
                    grid.add(pri, 3, i + 2);
                }
            }
            j++;
            x += billDetailList.get(i).getSerPrice();
        }
        // add line 
        Label l1 = new Label("-------");
        Label l2 = new Label("-------");
        Label l3 = new Label("-------");
        Label l4 = new Label("-------");
        grid.add(l1, 0, j + 2);
        grid.add(l2, 1, j + 2);
        grid.add(l3, 2, j + 2);
        grid.add(l4, 3, j + 2);
        // if total service price != 0 table(grid pane) show
        if (x != 0) {
            panePayment.getChildren().add(grid);
            AnchorPane.setLeftAnchor(grid, 30.0);
            AnchorPane.setTopAnchor(grid, 260.0);
            AnchorPane.setRightAnchor(grid, 20.0);
            AnchorPane.setBottomAnchor(grid, 50.0);
        }
        labelTotalServiceUsed.setText(String.valueOf(x) + " $");
        int totalPrice = x + RESprice; // total price
        labelTotalPrice.setText(String.valueOf(totalPrice) + " $");
    }

    private void print(Node node) {
        // Create a printer job for the default printer
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            // Print the node
            boolean printed = job.printPage(node);
            if (printed) {
                // End the printer job
                job.endJob();
                checkInvoice = true;
                FXMLBookRoom.stageConfirmPayment.close();
            } else {
                checkInvoice = false;
            }
        } else {
            System.out.println("2");
        }
    }
}
