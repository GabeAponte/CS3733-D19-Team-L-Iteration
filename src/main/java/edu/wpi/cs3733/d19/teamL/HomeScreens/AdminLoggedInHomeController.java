package edu.wpi.cs3733.d19.teamL.HomeScreens;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import edu.wpi.cs3733.d19.teamL.API.ImageComparison;
import edu.wpi.cs3733.d19.teamL.Account.CreateEditAccountController;
import edu.wpi.cs3733.d19.teamL.Account.EmployeeAccess;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.PathFindingController;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.Reports.ReportThread;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static java.lang.Thread.sleep;

public class AdminLoggedInHomeController {
    @FXML
    private Button fufillServiceRequest;

    @FXML
    private Button back;

    @FXML
    private Button logOut;

    @FXML
    private Button bookRoom;

    @FXML
    private Button editLocations;

    @FXML
    private Button findPath;

    @FXML
    private Button serviceRequest;

    @FXML
    private Button seeSuggestions;

    @FXML
    private Button newAccount;

    @FXML
    private Button settingBtn;

    @FXML
    private Button menuBack;

    @FXML
    private Button editAccount;

    @FXML
    private Label welcome;

    @FXML
    private Button EmergencyButton;

    @FXML
    private AnchorPane settingPane;

    @FXML
    private ComboBox<String> RequestType;

    @FXML
    private TextField timeoutTime;
    Timeline timeout;

    public void initialize(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        settingPressed();
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

                        single.setLastTime();
                        Parent sceneMain = loader.load();
                        HomeScreenController controller = loader.<HomeScreenController>getController();
                        controller.displayPopup();
                        single.setLastTime();

                        Stage thisStage = (Stage) newAccount.getScene().getWindow();

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

        EmployeeAccess ea = new EmployeeAccess();
        welcome.setText("Welcome, " + ea.getEmployeeInformation(single.getUsername()).get(3));

        ObservableList<String> requestTypes = FXCollections.observableArrayList();
        requestTypes.add("AudioVisual");
        requestTypes.add("InternalTransport");
        requestTypes.add("IT");
        requestTypes.add("Maintenance");
        requestTypes.add("Prescription");
        requestTypes.add("Religious");
        requestTypes.add("Sanitation");
        requestTypes.add("Security");
        requestTypes.add("Language");
        RequestType.setItems(requestTypes);

    }

    @FXML
    private void settingPressed(){
        TranslateTransition openSetting = new TranslateTransition(new Duration(300.0D), this.settingPane);
        openSetting.setToX(0.0D);
        TranslateTransition closeSetting = new TranslateTransition(new Duration(300.0D), this.settingPane);
        this.settingBtn.setOnAction((evt) -> {
            //  settingPane.setLayoutX(mapColumn.getMaxWidth()-200);
            if (this.settingPane.getTranslateX() != -325.0D) {
                openSetting.setToX(-325.0D);
                openSetting.play();
            } else {
                //System.out.println("got here");
                closeSetting.setToX(this.settingPane.getWidth());
                closeSetting.play();
            }

        });
        this.menuBack.setOnAction((evt) -> {
            //  settingPane.setLayoutX(mapColumn.getMaxWidth()-200);
            if (this.settingPane.getTranslateX() != -325.0D) {
                openSetting.setToX(-325.0D);
                openSetting.play();
            } else {
                //System.out.println("got here");
                closeSetting.setToX(this.settingPane.getWidth());
                closeSetting.play();
            }
        });


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
    private void SwitchToEditLocationScreen(ActionEvent event) throws IOException{
        timeout.stop();
        saveState();
        Singleton single = Singleton.getInstance();
        single.setLastTime();

        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("EditLocation.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void SwitchToAddAccountScreen(ActionEvent event) throws IOException{
        timeout.stop();
        saveState();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("CreateEditAccount.fxml"));
        Parent newPage = loader.load();
        CreateEditAccountController controller = loader.<CreateEditAccountController>getController();
        controller.setType(1, "");
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
     * Restores previous screen
     * @throws IOException
     */
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

    /**@author Nathan
     * Saves the memento state
     */
    private void saveState(){
        Singleton single = Singleton.getInstance();
        single.saveMemento("AdminLoggedInHome.fxml");
    }

    /** Grace
     * do a popup that brings user to emergency mode
     */
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
            WebcamPanel wp = new WebcamPanel(webcam);
            wp.setFPSDisplayed(true);
            wp.setDisplayDebugInfo(true);
            wp.setImageSizeDisplayed(true);
            wp.setMirrored(true);
            //JFrame window = new JFrame("Hold still for 2.5 seconds");
            //window.add(wp);
            //window.setResizable(true);
            //window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //window.pack();
            //window.setLocationRelativeTo(null);
            //window.setVisible(true);
            try {
                sleep(200);
            } catch (InterruptedException e) {
                System.out.println(e);
                System.out.println(e.getMessage());
            }
            wp.stop();
            webcam.close();
            //window.dispose();

            webcam.open();
            BufferedImage image = webcam.getImage();
            ImageIO.write(image, "JPG", new File("TempOutput.jpg"));
            webcam.close();


        } catch (Exception e){
            e.printStackTrace();
        }


        //when press ok, store pic in database
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("EmergencyScreen.fxml"));
            ((Node) event.getSource()).getScene().setRoot(newPage);
        } else {
            // ... user chose CANCEL or closed the dialog
        }

    }

    @FXML
    private void GeneratePathFindingReport() {
        ReportThread rt = new ReportThread(1);
        rt.start();
    }

    @FXML
    private void GenerateGeneralServiceRequestOverview() {
        ReportThread rt = new ReportThread(2);
        rt.start();
    }

    @FXML
    private void GenerateSpecificServiceRequest() {
        if (RequestType!=null) {
            ReportThread rt = new ReportThread(3);
            rt.setRequestType(RequestType.getValue());
            rt.start();
        }
    }
}
