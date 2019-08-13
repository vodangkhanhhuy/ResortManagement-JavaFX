/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Models.FillProgressIndicator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.xml.bind.JAXBException;

/**
 *
 * @author TechCare
 */
public class Login extends Application {

    public static Stage stageMain;
    public static Stage stageMain2;
    public static Scene scene;
    boolean CheckConnect;
    Label label;

    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException, SQLException, FileNotFoundException, JAXBException {
        stageMain = primaryStage;
        FillProgressIndicator indicator = new FillProgressIndicator();
        indicator.makeIndeterminate();
        StackPane rootStackpane = new StackPane();
        ImageView imgView = new ImageView(new Image(getClass().getResourceAsStream("/Images/bg.jpg")));
        imgView.setFitHeight(600);
        imgView.setFitWidth(900);
        imgView.setPreserveRatio(false);
        imgView.setStyle("-fx-opacity : 0.85 ;");
        rootStackpane.getChildren().add(imgView);
        rootStackpane.getChildren().add(indicator);
        label = new Label();
        label.setText("Loading ....");
        label.setLayoutX(418);
        label.setLayoutY(405);
        label.setStyle("-fx-font-size: 20px; -fx-text-fill : #1bbc9b; -fx-font-weight: bold;");
        AnchorPane anchorPane = new AnchorPane();
        AnchorPane.setBottomAnchor(rootStackpane, 0.0);
        AnchorPane.setTopAnchor(rootStackpane, 0.0);
        AnchorPane.setLeftAnchor(rootStackpane, 0.0);
        AnchorPane.setRightAnchor(rootStackpane, 0.0);
        anchorPane.getChildren().addAll(rootStackpane, label);
        scene = new Scene(anchorPane, 900, 600);
        stageMain.setScene(scene);
        stageMain.setTitle("HCN Resort Management");
        stageMain.getIcons().add(new Image(getClass().getResourceAsStream("../Images/Capture2.PNG")));
        stageMain.setResizable(false);
        stageMain.setMaximized(false);
        stageMain.show();
        new pr_thread(indicator, label).start();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    class pr_thread extends Thread {

        FillProgressIndicator rpi;
        Label label;
        int progress = 0;
        int i = 0;

        public pr_thread(FillProgressIndicator rpi, Label label) {
            this.rpi = rpi;
            this.label = label;
        }

        @Override
        public void run() {
            Thread th = new Thread();
            while (true) {
                try {
                    th.sleep(50);
                } catch (InterruptedException e) {
                }
                Platform.runLater(() -> {
                    rpi.setProgress(progress);
                    if (i % 10 == 0) {
                        label.setText("Loading . . .");
                    } else if (i % 5 == 0) {
                        label.setText("Loading . ");
                    }
                });

                progress += 1;
                i += 1;

                if (progress > 100) {
                    break;
                }
            }
            try {
                Parent root;

                CheckConnect = Controller.ConnectDB.checkConnect();
                if (CheckConnect == true) {
                    root = FXMLLoader.load(getClass().getResource("/FXML/FXMLLogin.fxml"));
                } else {
                    root = FXMLLoader.load(getClass().getResource("/FXML/FXMLSignUpMail.fxml"));
                }
                scene.setRoot(root);

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

}
