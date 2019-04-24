package edu.wpi.cs3733.d19.teamL.HomeScreens;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import edu.wpi.cs3733.d19.teamL.Account.CreateEditAccountController;
import edu.wpi.cs3733.d19.teamL.Account.EmployeeAccess;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.PathFindingController;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.FulfillServiceRequest.ActiveServiceRequestsController;
import edu.wpi.cs3733.d19.teamL.Singleton;
import edu.wpi.cs3733.d19.teamL.Suggestion.SuggestionTableController;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static java.lang.Thread.sleep;

public class EmployeeLoggedInHomeController {
    @FXML
    private Button fufillServiceRequest;

    @FXML
    private Button back;

    @FXML
    private Button logOut;

    @FXML
    private Button bookRoom;

    @FXML
    private Button myAccount;

    @FXML
    private Button findPath;

    @FXML
    private Button seeSuggestions;

    @FXML
    private Button serviceRequest;

    @FXML
    private Label welcome;

    Timeline timeout;

    // TODO Make label display "Welcome, [nickname of employee signed in]"
    //   myAccount() should switch to the create/edit account screen with the employee's info
    //   filled in already. Also they should be able to change the changeable fields
    // TODO
    //   seeSuggestions should switch to the suggestions table screen
    //   switching to the fulfillRequest screen should only display any requests assigned to the employee who is signed in

    public void initialize(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        EmployeeAccess ea = new EmployeeAccess();
        welcome.setText("Welcome, " + ea.getEmployeeInformation(single.getUsername()).get(3));

        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()){
                    try{
                        single.setLastTime();
                        single.setDoPopup(true);
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        Parent sceneMain = loader.load();
                        HomeScreenController controller = loader.<HomeScreenController>getController();
                        single.setLastTime();
                        controller.displayPopup();
                        single.setLastTime();

                        Stage thisStage = (Stage) seeSuggestions.getScene().getWindow();
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

    @FXML
    private void bookRoom(ActionEvent event) throws IOException {
        timeout.stop();
        saveState();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("BookRoom.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void SwitchToPathfindScreen(ActionEvent event) throws IOException{
        timeout.stop();
        saveState();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("HospitalPathFinding.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void SwitchToServiceScreen(ActionEvent event) throws IOException{
        timeout.stop();
        saveState();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("ServiceRequest.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void SwitchToFullfillRequestScreen(ActionEvent event) throws IOException{
        timeout.stop();
        saveState();
        Singleton single = Singleton.getInstance();
        single.setLastTime();

        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("ActiveServiceRequests.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void SwitchToEditAccountScreen(ActionEvent event) throws IOException{
        timeout.stop();
        saveState();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("EmployeeTable.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void SwitchToSuggestionScreen(ActionEvent event) throws IOException{
        timeout.stop();
        saveState();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("SuggestionTable.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void ActivateEmergencyMode(ActionEvent event) throws IOException {
        // popup - activate emergency mode?
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("ACTIVATING EMERGENCY MODE");
        alert.setHeaderText("YOU ARE ACTIVATING EMERGENCY MODE. THIS WILL HAVE CONSEQUENCES IF THERE IS NO REAL EMERGENCY PRESENT");
        alert.setContentText("YOUR PICTURE HAS BEEN TAKEN. IF YOU PROCEED TO ACTIVATE EMERGENCY MODE, YOUR FACE WILL BE STORED IN OUR DATABASE. " +
                "\n CONFIRM?");

        //CODE TO TAKE PICTURE
        try {
            Webcam webcam;
            webcam = Webcam.getDefault();
            //THE VIEW SIZE WILL PROBABLY CHANGE DEPENDING ON THE COMPUTER
            //IMAGE COMPARISON WILL FAIL IMMEDIATELY IF SIZE CHANGES
            webcam.setViewSize(WebcamResolution.VGA.getSize());
            webcam.open();
            BufferedImage image = webcam.getImage();
            ImageIO.write(image, "JPG", new File("EMode.jpg"));
            webcam.close();


        } catch (Exception e){
            e.printStackTrace();
        }

        //when press ok, store pic in database
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            saveState();
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("EmergencyScreen.fxml"));
            ((Node) event.getSource()).getScene().setRoot(newPage);
        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }


    @FXML
    private void backPressed(ActionEvent event) throws IOException{
        Singleton single = Singleton.getInstance();
        timeout.stop();
        single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);

        Memento m = single.restore();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(m.getFxml()));
        Parent newPage = loader.load();
        if(m.getFxml().contains("HospitalPathFinding")){
            PathFindingController pfc = loader.getController();
            pfc.initWithMeme(m.getPathPref(), m.getTypeFilter(), m.getFloorFilter(), m.getStart(), m.getEnd());
        }

        //Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
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
        saveState();
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }


    /**@author Nathan
     * Saves the memento state
     */
    private void saveState(){
        Singleton single = Singleton.getInstance();
        single.saveMemento("EmployeeLoggedInHome.fxml");
    }

}
