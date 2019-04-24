package edu.wpi.cs3733.d19.teamL.HomeScreens;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.jfoenix.controls.JFXButton;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.PathFindingController;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.Reports.BarGraphChartData;
import edu.wpi.cs3733.d19.teamL.Reports.PieChartData;
import edu.wpi.cs3733.d19.teamL.Reports.ReportThread;
import edu.wpi.cs3733.d19.teamL.Reports.pathReportAccess;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.MakeServiceRequest.ServiceRequestController;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


import java.awt.*;
import javax.imageio.ImageIO;
import javax.management.AttributeList;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.Timer;

import static java.lang.Thread.sleep;


public class HomeScreenController {

    @FXML
    Button HomeFindPath;

    @FXML
    Button back;

    @FXML
    JFXButton aboutButton;

    @FXML
    Button HomeServiceRequest;

    @FXML
    Button HomeSuggestions;

    @FXML
    Button LogIn;

    @FXML
    Label timeLabel;

    @FXML
    ImageView weatherIcon;

    @FXML
    TextField tweetBox;

    @FXML
    Label tempDisplay;

    @FXML
    Label dateLabel;

    @FXML
    Button yes;

    @FXML
    AnchorPane ap;

    Timeline clock;
    Timeline tweets;
    Boolean isAM = true;

    public void initialize() throws IOException {
        Singleton single = Singleton.getInstance();

        if(single.isDoPopup()) {
            updateWeatherDisplay();
            single.setDoPopup(false);
            Text fillBox = single.getTxt();

            tweetBox.setText(fillBox.getText());
            tweetBox.setEditable(false);

            // Get the Width of the Scene and the Text
            double sceneWidth = 1350;
            double textWidth = tweetBox.getLayoutBounds().getWidth();

            // Define the Durations
            Duration startDuration = Duration.ZERO;
            Duration endDuration = Duration.seconds(120);

            // Create the start and end Key Frames
            KeyValue startKeyValue = new KeyValue(tweetBox.translateXProperty(), sceneWidth);
            KeyFrame startKeyFrame = new KeyFrame(startDuration, startKeyValue);
            KeyValue endKeyValue = new KeyValue(tweetBox.translateXProperty(), -3.5 * sceneWidth - textWidth * 2);
            KeyFrame endKeyFrame = new KeyFrame(endDuration, endKeyValue);

            // Create a Timeline
            tweets = new Timeline(startKeyFrame, endKeyFrame);
            // Let the animation run forever
            tweets.setCycleCount(Timeline.INDEFINITE);
            //tweets.setRate(2.5);
            // Run the animation
            tweets.play();


            clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                if((System.currentTimeMillis() - single.getStartTime()) > 1800000){
                    System.out.println("updated");
                    single.setStartTime();
                    single.updateWeather();

                    updateWeatherDisplay();
                }
                if (single.isEmergency()) {
                    try {
                        ActivateEmergencyMode();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                long minute = LocalDateTime.now().getMinute();
                long hour = LocalDateTime.now().getHour();
                if(hour > 12){
                    isAM = false;
                }
                if ((hour = hour % 12) == 0) {
                    hour = 12;
                }
                if (minute < 10) {
                    timeLabel.setText(hour + ":0" + (minute));
                } else {
                    timeLabel.setText(hour + ":" + (minute));
                }

                if(isAM){
                    timeLabel.setText(timeLabel.getText()+" AM");
                }
                else{
                    timeLabel.setText(timeLabel.getText()+" PM");
                }
            }),
                    new KeyFrame(Duration.seconds(1))
            );
            clock.setCycleCount(Animation.INDEFINITE);
            clock.play();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate localDate = LocalDate.now();
            dateLabel.setText(dtf.format(localDate));
        }

    }


    public void updateWeatherDisplay(){
        Singleton single = Singleton.getInstance();

        String icon = single.getWeatherIcon();
        //clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
        if (icon.contains("clear") && icon.contains("day")) {
            icon = "weatherIcons/SunImage.PNG";
        } else if(icon.contains("clear") && icon.contains("night")){
            icon = "weatherIcons/MoonImage.PNG";
        }else if(icon.contains("rain") || icon.contains("sleet")){
            icon = "weatherIcons/RainImage.PNG";
        } else if(icon.contains("partly") && icon.contains("day")){
            icon = "weatherIcons/PartlyCloudImage.PNG";
        } else if(icon.contains("partly") && icon.contains("night")){
            icon = "weatherIcons/PartlyCloudNightImage.PNG";
        } else if(icon.contains("cloudy")){
            icon = "weatherIcons/CloudyImage.PNG";
        } else if(icon.contains("fog")){
            icon = "weatherIcons/FogImage.PNG";
        } else if(icon.contains("snow")){
            icon = "weatherIcons/SnowImage.PNG";
        } else if(icon.contains("wind")){
            icon = "weatherIcons/WindImage.PNG";
        }else{
            icon = "weatherIcons/ThunderImage.PNG";
        }
        //System.out.println("icon is being set to: "+icon);
        Image img = new Image(icon);
        weatherIcon.setImage(img);
        tempDisplay.setText(single.getWeatherTemp());
    }

    public void displayPopup(){
        Singleton single = Singleton.getInstance();
        single.updateWeather();
        try {
            clock.pause();
            Stage stage;
            Parent root;
            stage = new Stage();
            root = FXMLLoader.load(getClass().getClassLoader().getResource("TimeoutPopup.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Inactivity Popup");
            stage.initModality(Modality.APPLICATION_MODAL);
            single.setLastTime();
            stage.setResizable(false);
            stage.show();
            single = Singleton.getInstance();
            single.setLastTime();
            clock.play();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    private void yesPressed(){
        Stage stage = (Stage) yes.getScene().getWindow();
        //clock.stop();
        stage.close();
    }
    @FXML
    private void SwitchToPathfindScreen(ActionEvent event) throws IOException{
        stop();
        saveState();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("HospitalPathFinding.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void SwitchToSuggestionBox(ActionEvent event) throws IOException{
        stop();
        saveState();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("SuggestionBox.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void SwitchToServiceScreen(ActionEvent event) throws IOException{
        stop();
        saveState();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("ServiceRequest.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void SwitchToLoginScreen(ActionEvent event){
        stop();
        saveState();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        try {
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("LogIn.fxml"));
            ((Node) event.getSource()).getScene().setRoot(newPage);

        } catch (Exception e){
        }
    }

    @FXML
    private void AboutPress(ActionEvent event){
        stop();
        saveState();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        try {
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("AboutPage_fancy.fxml"));
            ((Node) event.getSource()).getScene().setRoot(newPage);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void ActivateEmergencyMode() throws IOException {
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
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("EmergencyScreen.fxml"));
            back.getScene().setRoot(newPage);
        } else {
            // ... user chose CANCEL or closed the dialog
        }

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
        stop();
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
        single.saveMemento("HospitalHome.fxml");
    }

    /**@author Nathan
     * Stops all timelines on the screen
     */
    private void stop(){
        tweets.stop();
        clock.stop();
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setUsername("");
        single.setIsAdmin(false);
        single.setLoggedIn(false);
        single.setDoPopup(true);
        Stage thestage = (Stage) LogIn.getScene().getWindow();
        AnchorPane root;
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        saveState();
        Stage thestage = (Stage) LogIn.getScene().getWindow();
        AnchorPane root;
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    public void loadBrighams() throws IOException {
        Singleton single = Singleton.getInstance();
        Stage stage;
        Parent root;
        stage = new Stage();
        root = FXMLLoader.load(getClass().getClassLoader().getResource("Brigham.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Brigham and Women's Hospital");
        stage.initModality(Modality.APPLICATION_MODAL);
        single.setLastTime();
        stage.showAndWait();
        single = Singleton.getInstance();
        single.setLastTime();
    }
}
