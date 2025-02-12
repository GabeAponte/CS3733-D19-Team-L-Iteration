package edu.wpi.cs3733.d19.teamL.ServiceRequest.MakeServiceRequest;

import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.Reports.requestReportAccess;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.ServiceRequestDBAccess.InternalTransportAccess;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.NodesAccess;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.ServiceRequestDBAccess.ServiceRequestAccess;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class InternalTransportController {
    @FXML
    JFXComboBox<Location> startBox;

    @FXML
    JFXComboBox<Location> endBox;

    @FXML
    JFXTextField phoneField;

    @FXML
    JFXComboBox<String> typeField;

    @FXML
    JFXTextArea commentBox;

    @FXML
    JFXButton submitbtn;

    @FXML
    JFXButton back;

    @FXML
    private Button logOut;

    private NodesAccess na;
    private final ObservableList<Location> data = FXCollections.observableArrayList();
    private HashMap<String, Location> lookup = new HashMap<String, Location>();

    Timeline timeout;

    public void initialize(){
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

                        Stage thisStage = (Stage) commentBox.getScene().getWindow();
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
        submitbtn.setDisable(true);
        na = new NodesAccess();
        //initializeTable(na);
        startBox.setItems(single.getData());
        endBox.setItems(single.getData());
        typeField.getItems().addAll(
            "Wheelchair", "Walker", "Escort", "Crutches", "Other"
        );
    }

    private boolean isDigit(char c){
        if(c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'){
            return true;
        }
        return false;
    }

    private boolean NotValidPhone(){
        String num = phoneField.getText();
        if(num.length() != 12){
            return true;
        }
        boolean isValid = isDigit(num.charAt(0)) && isDigit(num.charAt(1)) && isDigit(num.charAt(2));
        isValid = isValid && num.charAt(3) == '-' && isDigit(num.charAt(4)) && isDigit(num.charAt(5));
        isValid = isValid && isDigit(num.charAt(6)) && num.charAt(7) == '-' && isDigit(num.charAt(8));
        isValid = isValid && isDigit(num.charAt(9)) && isDigit(num.charAt(10)) && isDigit(num.charAt(11));
        return !isValid;
    }

    @FXML
    private void reenableSubmit(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if(commentBox.getText().trim().isEmpty() || typeField.getValue() == null || startBox.getValue() == null || endBox.getValue() == null || NotValidPhone()){
            submitbtn.setDisable(true);
            return;
        }

        submitbtn.setDisable(false);
    }

    @FXML
    private void submitPressed(ActionEvent event) throws IOException{
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        Location startNode = single.lookup.get(startBox.getValue().getLocID());
        Location endNode = single.lookup.get(endBox.getValue().getLocID());
        String comment = commentBox.getText();
        String type = typeField.getValue();
        String phone = phoneField.getText();
        ServiceRequestAccess sra = new ServiceRequestAccess();
        String time = LocalDateTime.now().toString();
        sra.makeInternalRequest(comment, startNode, endNode, type, phone, time);
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
        requestReportAccess ra = new requestReportAccess();
        ra.addReport(time, "none", "inprogress", "InternalTransport", typeField.getValue(), startNode.getLongName());


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