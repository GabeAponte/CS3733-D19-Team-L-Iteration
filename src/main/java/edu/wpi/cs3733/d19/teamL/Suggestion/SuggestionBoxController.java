package edu.wpi.cs3733.d19.teamL.Suggestion;

import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.Singleton;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.bytedeco.javacv.FrameFilter;

import java.io.IOException;

//@SuppressWarnings("ALL")
public class SuggestionBoxController {

    Timeline timeout;

    @FXML
    private Button submitFeedback;

    @FXML
    private Button back;

    @FXML
    private TextArea feedbackComments;

    @FXML
    private Stage thestage;

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
                        Memento m = single.getOrig();
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(m.getFxml()));
                        Parent sceneMain = loader.load();
                        Stage thisStage = (Stage) submitFeedback.getScene().getWindow();
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
    /**@author Gabe
     * When the submit button is pressed, the suggestion is added to the databse if the inputed
     * value is valid.
     */
    private void submitPressed(ActionEvent event) {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        SuggestionBasicAccess sga = new SuggestionBasicAccess();

        //Gabe - checks if the comment is nothing and that it isn't the prompt text
        if (feedbackComments.getText().trim().isEmpty() || feedbackComments.getText().equals("Type suggestions here")) {

        } else {
            //Gabe - valid suggestion and is added to database
            sga.addSuggestion(feedbackComments.getText());
            //error.setText("Thank you for your feedback");
            feedbackComments.setText("");
            timeout.stop();
            saveState();
            try {
                Memento m = single.getOrig();
                single.setDoPopup(true);
                Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
                ((Node) event.getSource()).getScene().setRoot(newPage);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**@author Nathan
     * Saves the memento state
     */
    private void saveState(){
        Singleton single = Singleton.getInstance();
        single.saveMemento("SuggestionBox.fxml");
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

}