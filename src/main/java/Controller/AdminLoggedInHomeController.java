package Controller;

import Access.EmployeeAccess;
import Object.Singleton;
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

public class AdminLoggedInHomeController {
    @FXML
    private Button fufillServiceRequest;

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
                System.out.println("checking if");
                if((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()){
                    System.out.println("if successfull");
                    try{
                        single.setLastTime();
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        logOut();
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
    private void logOut() throws IOException {
        timeout.stop();
        Stage thestage = (Stage) logOut.getScene().getWindow();
        AnchorPane root;
        Singleton single = Singleton.getInstance();
        single.setLoggedIn(false);
        single.setUsername("");
        single.setIsAdmin(false);
        root = FXMLLoader.load(getClass().getClassLoader().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    private void bookRoom() throws IOException {
        timeout.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("BookRoom.fxml"));

        Parent sceneMain = loader.load();

        Stage theStage = (Stage) bookRoom.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToPathfindScreen() throws IOException{
        timeout.stop();
        FXMLLoader pLoader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalPathFinding.fxml"));

        Parent sceneMain = pLoader.load();

        Stage theStage = (Stage) findPath.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToServiceScreen() throws IOException{
        timeout.stop();
        FXMLLoader sLoader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequest.fxml"));

        Parent sceneMain = sLoader.load();

        Stage theStage = (Stage) findPath.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToFullfillRequestScreen() throws IOException{
        timeout.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ActiveServiceRequests.fxml"));

        Parent sceneMain = loader.load();

        Stage theStage = (Stage) fufillServiceRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToEditLocationScreen() throws IOException{
        timeout.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EditLocation.fxml"));

        Parent sceneMain = loader.load();

        EditLocationController controller = loader.<EditLocationController>getController();

        Stage theStage = (Stage) fufillServiceRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToAddAccountScreen() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("CreateEditAccount.fxml"));

        Parent sceneMain = loader.load();

        CreateEditAccountController controller = loader.<CreateEditAccountController>getController();
        controller.setType(1, "");
        Stage theStage = (Stage) fufillServiceRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToEditAccountScreen() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EmployeeTable.fxml"));

        Parent sceneMain = loader.load();

        EmployeeTableController controller = loader.<EmployeeTableController>getController();

        Stage theStage = (Stage) fufillServiceRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }
    /*
    @FXML
    private void SwitchToSuggestionScreen() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("SuggestionBox.fxml"));

        Parent sceneMain = loader.load();

        SuggestionBoxController controller = loader.<SuggestionBoxController>getController();

        Stage theStage = (Stage) fufillServiceRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }
     */

}
