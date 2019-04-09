package Controller;

import API.Weather;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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


    public void initialize(){
        /*Weather weatherBoy = new Weather();
        String icon = weatherBoy.getIcon();
        Image img = new Image(icon);
        weatherIcon.setImage(img);
        tempDisplay.setText(weatherBoy.getActTemp());*/

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            long second = LocalDateTime.now().getSecond();
            long minute = LocalDateTime.now().getMinute();
            long hour = LocalDateTime.now().getHour();
            if((hour = hour%12) == 0){
                hour = 12;
            }
            if(minute < 10) {
                if(second > 9) {
                    timeLabel.setText("The Time is: " + hour + ":0" + (minute) + ":" + second);
                } else {
                    timeLabel.setText("The Time is: " + hour + ":0" + (minute) + ":0" + second);
                }
            } else {
                if(second > 9) {
                    timeLabel.setText("The Time is: " + hour + ":" + (minute) + ":" + second);
                } else {
                    timeLabel.setText("The Time is: " + hour + ":" + (minute) + ":0" + second);
                }
            }
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    private void SwitchToPathfindScreen() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalPathFinding.fxml"));

        Parent sceneMain = loader.load();

        PathFindingController controller = loader.<PathFindingController>getController();

        Stage theStage = (Stage) HomeFindPath.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToSuggestionBox() throws IOException{
            Stage thestage = (Stage) HomeSuggestions.getScene().getWindow();
            AnchorPane root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("SuggestionBox.fxml"));
            Scene scene = new Scene(root);
            thestage.setScene(scene);
    }

    @FXML
    private void SwitchToServiceScreen() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequest.fxml"));

        Parent sceneMain = loader.load();

        ServiceRequestController controller = loader.<ServiceRequestController>getController();

        Stage theStage = (Stage) HomeServiceRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToLoginScreen(ActionEvent event){
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
