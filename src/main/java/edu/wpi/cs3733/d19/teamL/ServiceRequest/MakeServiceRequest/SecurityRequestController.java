package edu.wpi.cs3733.d19.teamL.ServiceRequest.MakeServiceRequest;

import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.ServiceRequestDBAccess.ServiceRequestAccess;
import edu.wpi.cs3733.d19.teamL.Singleton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;

public class SecurityRequestController {
    @FXML
    public Button Back;

    @FXML
    public Button Submit;

    @FXML
    public JFXTextField Identifiers;

    @FXML
    public JFXTextField Location;

    @FXML
    public JFXComboBox<String> Type;

    @FXML
    public JFXTextArea Description;

    @FXML
    public Button Level1;

    @FXML
    public Button Level2;

    @FXML
    public Button Level3;

    @FXML
    public Button Level4;

    @FXML
    public Button Level5;

    @FXML
    public Label UrgencyLabel;

    Timeline timeout;

    String Level;

    public void initialize(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
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
                        Stage thisStage = (Stage) Type.getScene().getWindow();

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
        Type.getItems().addAll(
                "Altercations", "Suspicious Activity", "Guards/Escort", "Weapon Sighted", "Bag Unattended", "Other");
    }

    @FXML
    private void showUrgency1(){
        UrgencyLabel.setText("Urgency Level : 1");
        Level = "1";
    }

    @FXML
    private void showUrgency2(){
        UrgencyLabel.setText("Urgency Level : 2");
        Level = "2";
    }

    @FXML
    private void showUrgency3(){
        UrgencyLabel.setText("Urgency Level : 3");
        Level = "3";
    }

    @FXML
    private void showUrgency4(){
        UrgencyLabel.setText("Urgency Level : 4");
        Level = "4";
    }
    @FXML
    private void showUrgency5(){
        UrgencyLabel.setText("Urgency Level : 5");
        Level = "5";
    }


    @FXML
    private void reenableSubmit() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if (Description.getText().trim().isEmpty() || Type.getValue() == null || Location.getText().trim().isEmpty() || Identifiers.getText().trim().isEmpty() || UrgencyLabel.getText().trim().isEmpty()) {
            Submit.setDisable(true);
        } else {
            Submit.setDisable(false);
        }
    }
    @FXML
    private void submitClicked() throws IOException {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        ServiceRequestAccess sra = new ServiceRequestAccess();
        sra.makeSecurityRequest(Description.getText(), Location.getText(),Identifiers.getText(), Type.getValue(), Level);
       // System.out.println("Submit Pressed");
        backPressed();
    }

    @FXML
    protected void backPressed() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequest.fxml"));

        Parent sceneMain = loader.load();

        Stage theStage = (Stage) Back.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }
}