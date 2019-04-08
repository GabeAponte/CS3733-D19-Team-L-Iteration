package Controller;

import Object.Singleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    // TODO Make label display "Welcome, [nickname of employee signed in]"
    //   myAccount() should switch to the create/edit account screen with the employee's info
    //   filled in already. Also they should be able to change the changeable fields
    //   seeSuggestions should switch to the suggestions table screen
    //   switching to the fulfillRequest screen should only display any requests assigned to the employee who is signed in

    @FXML
    private void logOut() throws IOException {
        Stage thestage = (Stage) logOut.getScene().getWindow();
        AnchorPane root;
        Singleton.setLoggedIn(false);
        Singleton.setUsername("");
        root = FXMLLoader.load(getClass().getClassLoader().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    private void bookRoom() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("BookRoom.fxml"));

        Parent sceneMain = loader.load();

        BookRoomController controller = loader.<BookRoomController>getController();

        Stage theStage = (Stage) bookRoom.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToPathfindScreen() throws IOException {
        boolean signedIn = true;
        FXMLLoader pLoader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalPathFinding.fxml"));

        Parent sceneMain = pLoader.load();

        PathFindingController pController = pLoader.<PathFindingController>getController();

        Stage theStage = (Stage) findPath.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToServiceScreen() throws IOException {
        boolean signedIn = true;
        FXMLLoader sLoader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequest.fxml"));

        Parent sceneMain = sLoader.load();

        ServiceRequestController sController = sLoader.<ServiceRequestController>getController();

        Stage theStage = (Stage) findPath.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToFullfillRequestScreen() throws IOException {
        boolean signedIn = true;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ActiveServiceRequests.fxml"));

        Parent sceneMain = loader.load();

        ActiveServiceRequestsController controller = loader.<ActiveServiceRequestsController>getController();

        Stage theStage = (Stage) fufillServiceRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void myAccount() throws IOException {

    }

    @FXML
    private void seeSuggestions() throws IOException {

    }
}