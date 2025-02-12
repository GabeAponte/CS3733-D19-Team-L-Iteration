package edu.wpi.cs3733.d19.teamL.ServiceRequest.MakeServiceRequest;

import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Memento;
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
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ExternalTransportationController {
    @FXML
    public Button back;

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

    @FXML
    public Button logOut;

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
                        Stage thisStage = (Stage) uber.getScene().getWindow();
                        Screen screen = Screen.getPrimary();
                        Rectangle2D bounds = screen.getVisualBounds();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setMaximized(true);
                        thisStage.setScene(newScene);
                        thisStage.setX(bounds.getMinX());
                        thisStage.setY(bounds.getMinY());
                        thisStage.setWidth(bounds.getWidth());
                        thisStage.setHeight(bounds.getHeight());
                        timeout.stop();
                    } catch (IOException io){
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));
        timeout.setCycleCount(Timeline.INDEFINITE);
        timeout.play();
    }
    
    public void openBrowser() throws IOException {
        timeout.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Browser.fxml"));
        Parent sceneMain = loader.load();

        Stage theStage = (Stage) back.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    public void openMaps() throws IOException {
        timeout.pause();
        Singleton single = Singleton.getInstance();
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getClassLoader().getResource("Google.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Google");
        stage.initModality(Modality.APPLICATION_MODAL);
        single.setLastTime();
        stage.showAndWait();
        timeout.play();
        single = Singleton.getInstance();
        single.setLastTime();
    }

    public void openUber() throws IOException {
        timeout.pause();
        Singleton single = Singleton.getInstance();
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getClassLoader().getResource("Uber.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Uber");
        stage.initModality(Modality.APPLICATION_MODAL);
        single.setLastTime();
        stage.showAndWait();
        timeout.play();
        single = Singleton.getInstance();
        single.setLastTime();
    }

    public void openLyft() throws IOException {
        timeout.pause();
        Singleton single = Singleton.getInstance();
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getClassLoader().getResource("Lyft.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Lyft");
        stage.initModality(Modality.APPLICATION_MODAL);
        single.setLastTime();
        stage.showAndWait();
        timeout.play();
        single = Singleton.getInstance();
        single.setLastTime();
    }

    public void openTaxi() throws IOException {
        timeout.pause();
        Singleton single = Singleton.getInstance();
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getClassLoader().getResource("Taxi.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Taxi");
        stage.initModality(Modality.APPLICATION_MODAL);
        single.setLastTime();
        stage.showAndWait();
        timeout.play();
        single = Singleton.getInstance();
        single.setLastTime();
    }

    public void openFlight() throws IOException {
        timeout.pause();
        Singleton single = Singleton.getInstance();
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getClassLoader().getResource("Flight.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Flight");
        stage.initModality(Modality.APPLICATION_MODAL);
        single.setLastTime();
        stage.showAndWait();
        timeout.play();
        single = Singleton.getInstance();
        single.setLastTime();
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
        if(!single.isLoggedIn()){
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("LogIn.fxml"));
            ((Node) event.getSource()).getScene().setRoot(newPage);
            return;
        }
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