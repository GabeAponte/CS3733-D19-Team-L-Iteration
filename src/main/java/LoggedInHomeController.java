import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoggedInHomeController {
    String uname;
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

    public void init(String username){
        uname = username;
    }

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BookRoom.fxml"));

        Parent sceneMain = loader.load();

        BookRoomController controller = loader.<BookRoomController>getController();
        controller.init(uname);

        Stage theStage = (Stage) bookRoom.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToPathfindScreen() throws IOException{
        boolean signedIn = true;
        FXMLLoader pLoader = new FXMLLoader(getClass().getResource("HospitalPathFinding.fxml"));

        Parent sceneMain = pLoader.load();

        PathFindingController pController = pLoader.<PathFindingController>getController();
        pController.initialize(signedIn, uname);

        Stage theStage = (Stage) findPath.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToServiceScreen() throws IOException{
        boolean signedIn = true;
        FXMLLoader sLoader = new FXMLLoader(getClass().getResource("ServiceRequest.fxml"));

        Parent sceneMain = sLoader.load();

        ServiceRequestController sController = sLoader.<ServiceRequestController>getController();
        sController.init(signedIn, uname);

        Stage theStage = (Stage) findPath.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToFullfillRequestScreen() throws IOException{
        boolean signedIn = true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ActiveServiceRequests.fxml"));

        Parent sceneMain = loader.load();

        ActiveServiceRequestsController controller = loader.<ActiveServiceRequestsController>getController();
        controller.init(signedIn);

        Stage theStage = (Stage) fufillServiceRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToEditLocationScreen() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EditLocation.fxml"));

        Parent sceneMain = loader.load();

        EditLocationController controller = loader.<EditLocationController>getController();

        Stage theStage = (Stage) fufillServiceRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

}
