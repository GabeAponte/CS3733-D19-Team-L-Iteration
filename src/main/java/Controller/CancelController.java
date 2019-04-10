package Controller;

import API.ChildThread;
import Object.*;
import Access.ServiceRequestAccess;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class CancelController {
    private String typeOfService;
    private String comment;

    @FXML
    private Button Back;

    @FXML
    private Button yes;

    @FXML
    private Button no;

    @FXML
    public Label typeLabel;

    Timeline timeout;

    public void initialize(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()){
                    try{
                        single.setLastTime();
                        timeout.stop();
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        Parent sceneMain = loader.load();

                        Stage thisStage = (Stage) yes.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
                    } catch (IOException io){
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));
        timeout.setCycleCount(Timeline.INDEFINITE);
        timeout.play();
    }

    //Nathan - stores information passed from another controller
    public void init(String service, String description){
        Singleton single = Singleton.getInstance();
        single.setLastTime();

        typeOfService = service;
        comment = description;
    }

    //Nathan - takes user back to Controller.ServiceSubController (and fills in proper info)
    @SuppressWarnings("Duplicates")
    @FXML
    private void backPressed() throws IOException {
        timeout.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceSubController.fxml"));

        Parent sceneMain = loader.load();

        ServiceSubController controller = loader.<ServiceSubController>getController();

        controller.init(typeOfService, comment);

        Stage theStage = (Stage) yes.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    //Nathan - make a new service request and store it in the database, and sends email
    @FXML
    private void yesClicked() throws IOException, InterruptedException{
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        ServiceRequestAccess sra = new ServiceRequestAccess();
        ChildThread ct = new ChildThread(typeOfService, comment);
        ct.start();
        noClicked();
    }

    //Nathan - return the user to the home screen or signed in screen
    @SuppressWarnings("Duplicates")
    @FXML
    private void noClicked() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.getLastTime();
        Stage theStage = (Stage) no.getScene().getWindow();
        AnchorPane root;
        if(single.isLoggedIn()){
            FXMLLoader sLoader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));

            Parent sceneMain = sLoader.load();

            LoggedInHomeController sController = sLoader.<LoggedInHomeController>getController();

            theStage = (Stage) yes.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);
            return;
        } else {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("HospitalHome.fxml"));
        }
        Scene scene = new Scene(root);
        theStage.setScene(scene);
    }
}
