package Controller;

import Object.*;
import Access.SuggestionBasicAccess;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

//@SuppressWarnings("ALL")
public class SuggestionBoxController {

    Timeline timeout;

    @FXML
    private Button submitFeedback;

    @FXML
    private TextArea feedbackComments;

    @FXML
    private Stage thestage;

    @FXML
    private Label error;

    @FXML
    private Button SuggestionBack;

    public void initialize(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()){
                    try{
                        single.setLastTime();
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        single.setDoPopup(true);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));
                        Parent sceneMain = loader.load();
                        HomeScreenController controller = loader.<HomeScreenController>getController();
                        controller.displayPopup();
                        Stage thisStage = (Stage) submitFeedback.getScene().getWindow();

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
    }
    @FXML
    /**@author Gabe
     * Returns user to the Logged In Home screen when the back button is pressed
     */
    private void backPressed() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));
        Parent sceneMain = loader.load();
        HomeScreenController controller = loader.<HomeScreenController>getController();
        controller.displayPopup();
        Stage thisStage = (Stage) submitFeedback.getScene().getWindow();

        Scene newScene = new Scene(sceneMain);
        thisStage.setScene(newScene);
    }

    @FXML
    /**@author Gabe
     * When the submit button is pressed, the suggestion is added to the databse if the inputed
     * value is valid.
     */
    private void submitPressed() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        SuggestionBasicAccess sga = new SuggestionBasicAccess();

        //Gabe - checks if the comment is nothing and that it isn't the prompt text
        if (feedbackComments.getText().trim().isEmpty() || feedbackComments.getText().equals("Type suggestions here")) {
            timeout.stop();
            error.setText("Please enter your feedback");

        } else {
            //Gabe - valid suggestion and is added to database
            sga.addSuggestion(feedbackComments.getText());
            //error.setText("Thank you for your feedback");
            feedbackComments.setText("");
        }
    }

}