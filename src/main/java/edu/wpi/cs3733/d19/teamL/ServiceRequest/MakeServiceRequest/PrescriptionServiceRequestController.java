package edu.wpi.cs3733.d19.teamL.ServiceRequest.MakeServiceRequest;

import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.Reports.requestReportAccess;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.ServiceRequestDBAccess.ServiceRequestAccess;
import edu.wpi.cs3733.d19.teamL.Singleton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;

public class PrescriptionServiceRequestController {
    @FXML
    private Button submitButton;

    @FXML
    private Button back;

    @FXML
    private JFXTextField destinationField;

    @FXML
    private JFXTextField medicineTypeField;

    @FXML
    private JFXTextField deliveryTimeField;

    @FXML
    private JFXTextField amountField;

    @FXML
    private JFXTextArea commentsField;


    Timeline timeout;

    public void initialize() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if ((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()) {
                    try {
                        single.setDoPopup(true);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));
                        Parent sceneMain = loader.load();
                        if(single.isLoggedIn()) {
                            HomeScreenController controller = loader.<HomeScreenController>getController();
                            controller.displayPopup();
                        }
                        single.setLastTime();
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        Stage thisStage = (Stage) submitButton.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
                        timeout.stop();
                    } catch (IOException io) {
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));
        timeout.setCycleCount(Timeline.INDEFINITE);
        timeout.play();
        submitButton.setDisable(true);

    }
    @FXML
    private void reenableSubmit() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if (destinationField.getText().trim().isEmpty() || medicineTypeField.getText().trim().isEmpty() || deliveryTimeField.getText().trim().isEmpty() || amountField.getText().trim().isEmpty()) {
            submitButton.setDisable(true);
        } else {
            submitButton.setDisable(false);
        }
    }
    @FXML
    private void submitClicked(ActionEvent event) throws IOException {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        ServiceRequestAccess sra = new ServiceRequestAccess();
        String time = LocalDateTime.now().toString();
        sra.makePrescriptionRequest(commentsField.getText(),destinationField.getText(), medicineTypeField.getText(), destinationField.getText(), deliveryTimeField.getText(), amountField.getText(), time);
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
        requestReportAccess ra = new requestReportAccess();
        ra.addReport(time, "none", "inprogress", "Precription", medicineTypeField.getText(), destinationField.getText());

    }

    @FXML
    private void backPressed(ActionEvent event) throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        Memento m = single.restore();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setUsername("");
        single.setIsAdmin(false);
        single.setLoggedIn(false);
        single.setDoPopup(true);
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        //saveState();
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

}