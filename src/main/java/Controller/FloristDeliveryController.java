package Controller;

import API.ChildThread;
import Access.ReligiousRequestAccess;
import Access.ServiceRequestAccess;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import Object.*;

import java.io.IOException;

public class FloristDeliveryController {
    private boolean signedIn;
    private String uname;


    @FXML
    public Button backBtn;

    @FXML
    public Button Submit;


    @FXML
    public JFXTextField Location;

    @FXML
    public JFXTextField receiverName;

    @FXML
    public JFXTextField flowerName;


    @FXML
    public JFXTextArea comment;

    Timeline timeout;

    public void init(boolean loggedIn) {
        signedIn = loggedIn;
    }

    public void init(boolean loggedIn, String username) {
        uname = username;
        init(loggedIn);
    }

    public void initialize(){
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
                        controller.displayPopup();
                        Stage thisStage = (Stage) receiverName.getScene().getWindow();

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

    }

    @FXML
    private void reenableSubmit() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if (comment.getText().trim().isEmpty() || flowerName.getText().trim().isEmpty() || Location.getText().trim().isEmpty() || receiverName.getText().trim().isEmpty()) {
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
        sra.makeFloristRequest(comment.getText(), receiverName.getText(), Location.getText(), flowerName.getText());
       // System.out.println("Submit Pressed");
        backPressed();
    }

    @FXML
    protected void backPressed() throws IOException {
        timeout.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequest.fxml"));

        Parent sceneMain = loader.load();

        Stage theStage = (Stage) backBtn.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }
}
