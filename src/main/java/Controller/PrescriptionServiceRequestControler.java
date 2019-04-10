package Controller;

import com.jfoenix.controls.JFXTextArea;
import Access.ServiceRequestAccess;
import Object.Singleton;
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

public class PrescriptionServiceRequestControler {

    private boolean signedIn;
    private String uname;

    @FXML
    private Button submitButton;

    @FXML
    private Button backButton;

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

    public void init(boolean loggedIn) {
        signedIn = loggedIn;
    }

    public void init(boolean loggedIn, String username) {
        uname = username;
        init(loggedIn);
    }

    public void initialize() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if ((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()) {
                    try {
                        single.setLastTime();
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        single.setDoPopup(true);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));
                        Parent sceneMain = loader.load();
                        HomeScreenController controller = loader.<HomeScreenController>getController();
                        controller.displayPopup();
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
        if (destinationField.getText().trim().isEmpty() || medicineTypeField.getText().trim().isEmpty() || deliveryTimeField.getText().trim().isEmpty() || amountField.getText().trim().isEmpty()) {
            submitButton.setDisable(true);
        } else {
            submitButton.setDisable(false);
        }
    }
    @FXML
    private void submitClicked() throws IOException {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        ServiceRequestAccess sra = new ServiceRequestAccess();
        sra.makePrescriptionRequest(commentsField.getText(),destinationField.getText(), medicineTypeField.getText(), destinationField.getText(), deliveryTimeField.getText(), amountField.getText());
        backPressed();
    }

    @FXML
    protected void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequest.fxml"));

        Parent sceneMain = loader.load();

        Stage theStage = (Stage) backButton.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

}
