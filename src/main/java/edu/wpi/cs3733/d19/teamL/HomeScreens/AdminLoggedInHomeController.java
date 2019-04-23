package edu.wpi.cs3733.d19.teamL.HomeScreens;

import edu.wpi.cs3733.d19.teamL.Account.CreateEditAccountController;
import edu.wpi.cs3733.d19.teamL.Account.EmployeeAccess;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.PathFindingController;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

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
    private Button editAccount;

    @FXML
    private Label welcome;

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

    }
    // TODO Make label display "Welcome, [nickname of admin signed in]"
    //   New Account button should to create/edit account screen with title changed to Create Account and all the input fields are blank
    //   Edit Account should switch to the employee table screen. Double clicking on an employee in the table should bring up the
    //   create/edit account screen with the title changed to Edit Account and all the info for that employee filed into the fields.
    //   As an admin, the user should be able to edit any field other than employee ID
    //   seeSuggestions should switch to the suggestions table screen
    //   switching to the fulfillRequest screen should display all active service requests for the admin

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
}
