package edu.wpi.cs3733.d19.teamL.HomeScreens;

import edu.wpi.cs3733.d19.teamL.ServiceRequest.MakeServiceRequest.ServiceRequestController;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;

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

    //@FXML
    //ImageView weatherIcon;

    @FXML
    Label tempDisplay;

    @FXML
    Button yes;

    Timeline clock;


    public void initialize() throws IOException{
        Singleton single = Singleton.getInstance();
        /*Weather weatherBoy = new Weather();
        String icon = weatherBoy.getIcon();
        Image img = new Image(icon);
        weatherIcon.setImage(img);
        tempDisplay.setText(weatherBoy.getActTemp());*/
        if(single.isDoPopup()) {
            single.setDoPopup(false);
            clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
                long second = LocalDateTime.now().getSecond();
                long minute = LocalDateTime.now().getMinute();
                long hour = LocalDateTime.now().getHour();
                if ((hour = hour % 12) == 0) {
                    hour = 12;
                }
                if (minute < 10) {
                    if (second > 9) {
                        timeLabel.setText(hour + ":0" + (minute) + ":" + second);
                    } else {
                        timeLabel.setText(hour + ":0" + (minute) + ":0" + second);
                    }
                } else {
                    if (second > 9) {
                        timeLabel.setText(hour + ":" + (minute) + ":" + second);
                    } else {
                        timeLabel.setText(hour + ":" + (minute) + ":0" + second);
                    }
                }
            }),
                    new KeyFrame(Duration.seconds(1))
            );
            clock.setCycleCount(Animation.INDEFINITE);
            clock.play();
        }
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
        clock.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("NewHospitalPathFinding.fxml"));

        Parent sceneMain = loader.load();

        Stage theStage = (Stage) HomeFindPath.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToSuggestionBox() throws IOException{
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
