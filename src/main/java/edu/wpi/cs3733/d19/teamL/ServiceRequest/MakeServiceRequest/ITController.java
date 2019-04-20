package edu.wpi.cs3733.d19.teamL.ServiceRequest.MakeServiceRequest;

import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.NodesAccess;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.ServiceRequestDBAccess.ServiceRequestAccess;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;


public class ITController {

    @FXML
    private JFXComboBox<String> device;

    @FXML
    private JFXComboBox<String> problem;

    @FXML
    private JFXComboBox<String> floor;

    @FXML
    private JFXComboBox<String> loc;

    @FXML
    private JFXTextArea description;

    @FXML
    public Button back;

    @FXML
    private Button submit;

    @FXML
    private TextArea FAQ;

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
                        single.setDoPopup(true);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        Parent sceneMain = loader.load();
                        if(single.isLoggedIn()) {
                            single.setLoggedIn(false);
                            HomeScreenController controller = loader.<HomeScreenController>getController();
                            controller.displayPopup();
                        }

                        Stage thisStage = (Stage) back.getScene().getWindow();

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

        submit.setDisable(true);
        device.getItems().addAll("Desktop Computer", "Laptop Computer", "Tablet", "Smartphone", "Kiosk", "Television", "Other");
        floor.getItems().addAll("L1", "L2", "G", "1", "2", "3");

    }

    @FXML
    private void submitRequest() throws IOException {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        ServiceRequestAccess sra = new ServiceRequestAccess();
        sra.makeITRequest(description.getText(), loc.getValue(), device.getValue(), problem.getValue());
        Memento m = single.getOrig();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(m.getFxml()));

        Parent sceneMain = loader.load();
        Stage theStage = (Stage) loc.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);

    }

    @FXML
    private void deviceSelected() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if(device.getValue().equals("Desktop Computer")){
            problem.getItems().clear();
            problem.getItems().addAll("Computer not powering on","Computer running slowly", "Computer frozen","Internet or network connectivity issues", "Strange noises from computer", "Malware/Virus related issue", "Display not powering on or working","Need HDMI", "Need ethernet cord", "Mouse/Keyboard needed", "Other");
        }
        else if(device.getValue().equals("Laptop Computer")){
            problem.getItems().clear();
            problem.getItems().addAll("Laptop not powering on", "Laptop running slowly", "Laptop frozen", "Internet or network connectivity issues", "Strange noises from laptop","Malware/Virus related issue", "Display not powering on or working","Need HDMI", "Need ethernet cord", "Laptop overheating", "Trackpad malfunctioning", "Keyboard malfunctioning", "Other");
        }
        else if(device.getValue().equals("Tablet")){
            problem.getItems().clear();
            problem.getItems().addAll("Tablet not powering","Tablet running slowly","Tablet frozen", "Internet or network connectivity issues","Screen cracked", "Display not working", "Touch screen not working", "Power/Home button not working", "Tablet not charging", "Tablet overheating" , "Other");
        }
        else if(device.getValue().equals("Smartphone")){
            problem.getItems().clear();
            problem.getItems().addAll("Phone not powering on", "Phone running slowly", "Phone Frozen", "Internet or network connectivity", "Cellular network connection issues", "Screen cracked", "Display not working","Touch screen not working", "Power/Home button not working", "Phone not charging", "Phone overheating", "Other");
        }
        else if(device.getValue().equals("Kiosk")){
            problem.getItems().clear();
            problem.getItems().addAll("Kiosk not powering on", "Kiosk running", "Kiosk frozen", "Kiosk touch screen not working", "Display not powering on or working","Keyboard/Mouse malfunctioning", "Strange noises coming from kiosk", "Other");
        }
        else if(device.getValue().equals("Television")){
            problem.getItems().clear();
            problem.getItems().addAll("TV not powering", "TV Frozen", "Display not powering on or working","Internet or network connectivity issues","Need HDMI", "Need coaxial cable", "Remote out of batteries", "Remote not working", "Picture quality poor", "Cable box not working", "Other");
        }
        else if(device.getValue().equals("Other")){
            problem.getItems().clear();
            problem.getItems().add("Other");
        }
    }

    @FXML
    private void floorSelected(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        loc.getItems().clear();
        NodesAccess na = new NodesAccess();
        String theFloor = floor.getValue().toString();
        ArrayList<String> theRooms = na.getValidITLocations(theFloor);
        for(int i = 0; i < theRooms.size(); i++){
            loc.getItems().add(theRooms.get(i));
        }
    }

    @FXML
    private void backPressed() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        Stage thestage = (Stage) submit.getScene().getWindow();
        AnchorPane root;
        Memento m = single.restore();
        root = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    private void checkIfNull() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if(device.getValue() != null && problem.getValue() != null && floor.getValue() != null && loc.getValue() != null && (description.getText() != null && !description.getText().equals("Please explain your problem in more detail."))){
            submit.setDisable(false);
        }
        else{
            submit.setDisable(true);
        }
    }

    @FXML
    private void logOut() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setUsername("");
        single.setIsAdmin(false);
        single.setLoggedIn(false);
        single.setDoPopup(true);
        Stage thestage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        Memento m = single.getOrig();
        root = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    private void goHome() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        Stage thestage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        Memento m = single.getOrig();
        root = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }


}
