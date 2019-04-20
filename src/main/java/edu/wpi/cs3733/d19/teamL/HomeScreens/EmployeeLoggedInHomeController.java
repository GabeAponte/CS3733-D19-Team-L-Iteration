package edu.wpi.cs3733.d19.teamL.HomeScreens;

import edu.wpi.cs3733.d19.teamL.Account.CreateEditAccountController;
import edu.wpi.cs3733.d19.teamL.Account.EmployeeAccess;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.FulfillServiceRequest.ActiveServiceRequestsController;
import edu.wpi.cs3733.d19.teamL.Singleton;
import edu.wpi.cs3733.d19.teamL.Suggestion.SuggestionTableController;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class EmployeeLoggedInHomeController {
    @FXML
    private Button fufillServiceRequest;

    @FXML
    private Button logOut;

    @FXML
    private Button bookRoom;

    @FXML
    private Button myAccount;

    @FXML
    private Button findPath;

    @FXML
    private Button seeSuggestions;

    @FXML
    private Button serviceRequest;

    @FXML
    private Label welcome;

    Timeline timeout;

    // TODO Make label display "Welcome, [nickname of employee signed in]"
    //   myAccount() should switch to the create/edit account screen with the employee's info
    //   filled in already. Also they should be able to change the changeable fields
    // TODO
    //   seeSuggestions should switch to the suggestions table screen
    //   switching to the fulfillRequest screen should only display any requests assigned to the employee who is signed in

    public void initialize(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        EmployeeAccess ea = new EmployeeAccess();
        welcome.setText("Welcome, " + ea.getEmployeeInformation(single.getUsername()).get(3));

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

                        Parent sceneMain = loader.load();
                        HomeScreenController controller = loader.<HomeScreenController>getController();
                        single.setLastTime();
                        controller.displayPopup();
                        single.setLastTime();

                        Stage thisStage = (Stage) seeSuggestions.getScene().getWindow();

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
    private void logOut() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Stage thestage = (Stage) logOut.getScene().getWindow();
        AnchorPane root;
        single.setLoggedIn(false);
        single.setUsername("");
        single.setDoPopup(true);
        root = FXMLLoader.load(getClass().getClassLoader().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    private void bookRoom() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("BookRoom.fxml"));

        Parent sceneMain = loader.load();

        Stage theStage = (Stage) bookRoom.getScene().getWindow();

        Scene scene = new Scene(sceneMain);

        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToPathfindScreen() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader pLoader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalPathFinding.fxml"));

        Parent sceneMain = pLoader.load();

        Stage theStage = (Stage) findPath.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToServiceScreen() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader sLoader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequest.fxml"));

        Parent sceneMain = sLoader.load();

        Stage theStage = (Stage) findPath.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToFullfillRequestScreen() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setIsAdmin(false);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ActiveServiceRequests.fxml"));

        Parent sceneMain = loader.load();

        ActiveServiceRequestsController controller = loader.<ActiveServiceRequestsController>getController();

        Stage theStage = (Stage) fufillServiceRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToEditAccountScreen() throws IOException{
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("CreateEditAccount.fxml"));

        Parent sceneMain = loader.load();

        CreateEditAccountController controller = loader.<CreateEditAccountController>getController();
        controller.setType(2, "");

        Stage theStage = (Stage) fufillServiceRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }


    @FXML
    private void myAccount() throws IOException {
        //timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
    }

    @FXML
    private void SwitchToSuggestionScreen() throws IOException{
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("SuggestionTable.fxml"));

        Parent sceneMain = loader.load();

        SuggestionTableController controller = loader.<SuggestionTableController>getController();

        Stage theStage = (Stage) fufillServiceRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    }
