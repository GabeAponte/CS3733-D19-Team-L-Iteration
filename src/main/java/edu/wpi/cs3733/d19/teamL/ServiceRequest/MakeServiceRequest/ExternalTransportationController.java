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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ExternalTransportationController {
    @FXML
    public Button Back;

    @FXML
    public Button maps;

    @FXML
    public Button uber;

    @FXML
    public Button lyft;

    @FXML
    public Button taxi;

    @FXML
    public Button flight;

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
                        Stage thisStage = (Stage) uber.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
                        timeout.stop();
                    } catch (IOException io){
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));
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

    public void openBrowser() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Browser.fxml"));
        Parent sceneMain = loader.load();

        Stage theStage = (Stage) Back.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    public void openMaps() throws IOException {
        Singleton single = Singleton.getInstance();
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getClassLoader().getResource("Google.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Inactivity Popup");
        stage.initModality(Modality.APPLICATION_MODAL);
        single.setLastTime();
        stage.showAndWait();
        single = Singleton.getInstance();
        single.setLastTime();
    }

    public void openUber() throws IOException {
        Singleton single = Singleton.getInstance();
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getClassLoader().getResource("Uber.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Inactivity Popup");
        stage.initModality(Modality.APPLICATION_MODAL);
        single.setLastTime();
        stage.showAndWait();
        single = Singleton.getInstance();
        single.setLastTime();
    }

    public void openLyft() throws IOException {
        Singleton single = Singleton.getInstance();
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getClassLoader().getResource("Lyft.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Inactivity Popup");
        stage.initModality(Modality.APPLICATION_MODAL);
        single.setLastTime();
        stage.showAndWait();
        single = Singleton.getInstance();
        single.setLastTime();
    }

    public void openTaxi() throws IOException {
        Singleton single = Singleton.getInstance();
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getClassLoader().getResource("Taxi.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Inactivity Popup");
        stage.initModality(Modality.APPLICATION_MODAL);
        single.setLastTime();
        stage.showAndWait();
        single = Singleton.getInstance();
        single.setLastTime();
    }

    public void openFlight() throws IOException {
        Singleton single = Singleton.getInstance();
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getClassLoader().getResource("Flight.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Inactivity Popup");
        stage.initModality(Modality.APPLICATION_MODAL);
        single.setLastTime();
        stage.showAndWait();
        single = Singleton.getInstance();
        single.setLastTime();
    }
}
