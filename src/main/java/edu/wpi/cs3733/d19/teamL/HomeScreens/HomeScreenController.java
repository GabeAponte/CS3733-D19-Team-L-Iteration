package edu.wpi.cs3733.d19.teamL.HomeScreens;

import edu.wpi.cs3733.d19.teamL.ServiceRequest.MakeServiceRequest.ServiceRequestController;
import edu.wpi.cs3733.d19.teamL.Singleton;
import edu.wpi.cs3733.d19.teamL.API.Weather;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HomeScreenController {

    @FXML
    Button HomeFindPath;

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

        Weather weatherBoy = new Weather();
        String icon = weatherBoy.getIcon();
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
        tempDisplay.setText(weatherBoy.getActTemp());


        if(single.isDoPopup()) {
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
                long second = LocalDateTime.now().getSecond();
                long minute = LocalDateTime.now().getMinute();
                long hour = LocalDateTime.now().getHour();
                if(hour > 12){
                    isAM = false;
                }
                if ((hour = hour % 12) == 0) {
                    hour = 12;
                }
                if (minute < 10) {
                    if (second > 9) {
                        timeLabel.setText(hour + ":0" + (minute));
                    } else {
                        timeLabel.setText(hour + ":0" + (minute));
                    }
                } else {
                    if (second > 9) {
                        timeLabel.setText(hour + ":" + (minute));
                    } else {
                        timeLabel.setText(hour + ":" + (minute));
                    }
                }

                if(isAM){
                    timeLabel.setText(timeLabel.getText()+" AM");
                }
                else if(! isAM){
                    timeLabel.setText(timeLabel.getText()+" PM");
                }
            }),
                    new KeyFrame(Duration.seconds(1))
            );
            clock.setCycleCount(Animation.INDEFINITE);
            clock.play();
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate localDate = LocalDate.now();
        dateLabel.setText(dtf.format(localDate));
    }

    public void displayPopup(){
        Singleton single = Singleton.getInstance();
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
    private void SwitchToPathfindScreen() throws IOException{
        tweets.stop();
        clock.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalPathFinding.fxml"));

        Parent sceneMain = loader.load();

        Stage theStage = (Stage) HomeFindPath.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToSuggestionBox() throws IOException{
        tweets.stop();
        clock.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Stage thestage = (Stage) HomeSuggestions.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getClassLoader().getResource("SuggestionBox.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    private void SwitchToServiceScreen() throws IOException{
        tweets.stop();
        clock.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequest.fxml"));

        Parent sceneMain = loader.load();

        ServiceRequestController controller = loader.<ServiceRequestController>getController();

        Stage theStage = (Stage) HomeServiceRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToLoginScreen(ActionEvent event){
        tweets.stop();
        clock.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        try {
            Stage thestage = (Stage) LogIn.getScene().getWindow();
            AnchorPane root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("LogIn.fxml"));
            Scene scene = new Scene(root);
            thestage.setScene(scene);
        } catch (Exception e){
        }
    }
}
