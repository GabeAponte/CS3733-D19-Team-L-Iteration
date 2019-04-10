package Controller;

import Access.ExternalTransportAccess;
import Access.ReligiousRequestAccess;
import Access.ServiceRequestAccess;
import Object.Singleton;
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
import javafx.util.Duration;

import java.io.IOException;

public class ExternalTransportationController {
    @FXML
    public Button Back;

    @FXML
    public Button Submit;

    @FXML
    public JFXTextField Name;

    @FXML
    public JFXTextField Location;

    @FXML
    public JFXTextField Destination;

    @FXML
    public JFXTextField PhoneNumber;

    @FXML
    public JFXComboBox<String> Type;

    @FXML
    public JFXTextArea Description;

    Timeline timeout;

    public void initialize() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()){
                    try{
                        single.setLastTime();
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        single.setDoPopup(true);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));
                        Parent sceneMain = loader.load();
                        HomeScreenController controller = loader.<HomeScreenController>getController();
                        single.setLastTime();
                        controller.displayPopup();
                        single.setLastTime();
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
                "Bus", "Taxi", "Uber", "Lyft", "Train");
    }

    @FXML
    private void reenableSubmit() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if (Description.getText().trim().isEmpty() || Type.getValue() == null || Location.getText().trim().isEmpty() || Destination.getText().trim().isEmpty() || Name.getText().trim().isEmpty() || PhoneNumber.getText().trim().isEmpty()) {
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
        sra.makeExternalRequest(Description.getText(), Location.getText(), Destination.getText(), Type.getValue(), PhoneNumber.getText());
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
