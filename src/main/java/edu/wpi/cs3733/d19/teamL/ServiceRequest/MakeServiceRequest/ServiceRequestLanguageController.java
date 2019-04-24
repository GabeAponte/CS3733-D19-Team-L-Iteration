package edu.wpi.cs3733.d19.teamL.ServiceRequest.MakeServiceRequest;

import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.Reports.requestReportAccess;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.ServiceRequestDBAccess.ServiceRequestAccess;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.d19.teamL.Singleton;
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

public class ServiceRequestLanguageController {
    @FXML
    public Button back;

    @FXML
    public Button Submit;

    @FXML
    public JFXTextField language;

    @FXML
    public JFXTextField Location;

    @FXML
    public JFXComboBox<String> level;


    @FXML
    public JFXComboBox<String> interpreters;

    @FXML
    public JFXTextArea Description;

    @FXML
    private Button logOut;

    Timeline timeout;

    public void initialize() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if(!single.isLoggedIn()){
            logOut.setText("Log In");
        }
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()){
                    try{
                        single.setLastTime();
                        single.setUsername("");
                        single.setIsAdmin(false);
                        single.setDoPopup(true);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));
                        Parent sceneMain = loader.load();
                        if(single.isLoggedIn()) {
                            single.setLoggedIn(false);
                            HomeScreenController controller = loader.<HomeScreenController>getController();
                            controller.displayPopup();
                        }
                        Stage thisStage = (Stage) level.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
                        timeout.stop();
                    } catch (IOException io){
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));
        timeout.setCycleCount(Timeline.INDEFINITE);
        timeout.play();
        Submit.setDisable(true);
        interpreters.getItems().addAll(
                "3", "2", "1");

        level.getItems().addAll(
                "5", "4", "3", "2", "1");
    }

    @FXML
    private void reenableSubmit() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if (Description.getText().trim().isEmpty() || interpreters.getValue() == null || Location.getText().trim().isEmpty() || level.getValue() == null || language.getText().trim().isEmpty()){
            Submit.setDisable(true);
        } else {
            Submit.setDisable(false);
        }
    }

    @FXML
    private void submitClicked(ActionEvent event) throws IOException {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        ServiceRequestAccess sra = new ServiceRequestAccess();
        String time = LocalDateTime.now().toString();
        sra.makeLanguageRequest(Description.getText(), Location.getText(), language.getText(), level.getValue(), interpreters.getValue(), time);
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
        requestReportAccess ra = new requestReportAccess();
        ra.addReport(time, "none", "inprogress", "Security", language.getText(), Location.getText());
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