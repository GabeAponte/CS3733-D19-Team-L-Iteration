package edu.wpi.cs3733.d19.teamL.HomeScreens;

import edu.wpi.cs3733.d19.teamL.Account.EmployeeAccess;
import edu.wpi.cs3733.d19.teamL.Memento;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class AboutPageFancyController {

    private Stage thestage;

    @FXML
    private Button back;

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
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        Memento m = single.getOrig();
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(m.getFxml()));

                        Parent sceneMain = loader.load();

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
    }


    @SuppressWarnings("Duplicates")
    @FXML
    private void backPressed() throws IOException {
        timeout.stop();
        saveState();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        Memento m = single.restore();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(m.getFxml()));

        Parent sceneMain = loader.load();

        Stage thisStage = (Stage) back.getScene().getWindow();

        Scene newScene = new Scene(sceneMain);
        thisStage.setScene(newScene);
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
        thestage = (Stage) back.getScene().getWindow();
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
        saveState();
        thestage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        Memento m = single.getOrig();
        root = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    /**@author Nathan
     * Saves the memento state
     */
    private void saveState(){
        Singleton single = Singleton.getInstance();
        single.saveMemento("AboutPage_fancy.fxml");
    }
}
