import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoggedInHomeController {
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
    private void logOut() throws IOException {
        Stage thestage = (Stage) logOut.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    private void bookRoom() throws IOException {
        Stage thestage = (Stage) bookRoom.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("BookRoom.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    private void SwitchToPathfindScreen() throws IOException{
        boolean signedIn = true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HospitalPathFinding.fxml"));

        Parent sceneMain = loader.load();

        PathFindingController controller = loader.<PathFindingController>getController();
        controller.initialize(signedIn);

        Stage theStage = (Stage) findPath.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToServiceScreen() throws IOException{
        boolean signedIn = true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServiceRequest.fxml"));

        Parent sceneMain = loader.load();

        ServiceRequestController controller = loader.<ServiceRequestController>getController();
        controller.init(signedIn);

        Stage theStage = (Stage) findPath.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

}