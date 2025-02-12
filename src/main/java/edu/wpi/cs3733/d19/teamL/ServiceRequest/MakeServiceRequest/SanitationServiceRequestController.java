package edu.wpi.cs3733.d19.teamL.ServiceRequest.MakeServiceRequest;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.NodesAccess;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.Reports.requestReportAccess;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.ServiceRequestDBAccess.SanitationAccess;
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
import com.jfoenix.controls.JFXButton;
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

public class SanitationServiceRequestController {
    @FXML
    private JFXButton submit1;

    @FXML
    private JFXButton back;

    @FXML
    private JFXComboBox<Location> location1;

    @FXML
    private JFXTextArea comment1;

    @FXML
    private JFXComboBox<String> typeBox1;

    @FXML
    private JFXComboBox<String> urgencyLevel1;

    @FXML
    private Button logOut;

    private NodesAccess na;
    private final ObservableList<Location> data = FXCollections.observableArrayList();
    private HashMap<String, Location> lookup = new HashMap<String, Location>();

    Timeline timeout;
    /**Andrew made this
     * Initializes the items that the controller uses and adds fields to combo boxes
     */
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
                        Stage thisStage = (Stage) comment1.getScene().getWindow();
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

        na = new NodesAccess();
        initializeTable(na);
        location1.setItems(data);
        typeBox1.getItems().addAll(
                "Vomit",
                "Spill",
                "Rodent Found",
                "Bio Hazard"
        );
        urgencyLevel1.getItems().addAll(
                "Utmost Urgency",
                "Complete Quickly",
                "Complete Within Week"
        );
        submit1.setDisable(true);
    }

    /** Andrew copied this from somewhere
     * initializes the location combo box to show possible node locations
     * @param na
     */
    private void initializeTable(NodesAccess na) {
        ArrayList<String> edgeList;
        int count;
        count = 0;
        while (count < na.countRecords()) {
            ArrayList<String> arr = na.getNodes(count);
            ArrayList<String> arr2;
            Location testx = new Location(arr.get(0), Integer.parseInt(arr.get(1)), Integer.parseInt(arr.get(2)), arr.get(3), arr.get(4), arr.get(5), arr.get(6), arr.get(7));
            //only add the node if it hasn't been done yet
            if (!(lookup.containsKey(arr.get(0)))) {
                lookup.put((arr.get(0)), testx);
                data.add(testx);
            }
            count++;
        }
    }

    /**Andrew made this
     * checks if the submit should be enabled or not
     */
    @FXML
    private void checkSubmit(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if(comment1 == null || comment1.getText().trim().isEmpty()){
            submit1.setDisable(true);
            return;
        }else if(typeBox1.getValue() == null){
            submit1.setDisable(true);
            return;
        }else if(location1.getValue() == null){
            submit1.setDisable(true);
            return;
        }else if(urgencyLevel1.getValue() == null){
            submit1.setDisable(true);
            return;
        }

        submit1.setDisable(false);
    }

    /**Andrew made this
     * submits the request fields to be stored in the database
     */
    @FXML
    private void makeRequest(ActionEvent event) throws IOException{
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        ServiceRequestAccess sra = new ServiceRequestAccess();
        String time = LocalDateTime.now().toString();
        sra.makeSanitationRequest(location1.getValue().getLocID(), comment1.getText(), typeBox1.getValue(), urgencyLevel1.getValue(), time);
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);

        requestReportAccess ra = new requestReportAccess();
        ra.addReport(time, "none", "inprogress", "Sanitation", typeBox1.getValue(), location1.getValue().getLongName());

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