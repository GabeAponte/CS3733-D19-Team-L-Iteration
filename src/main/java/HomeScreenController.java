import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeScreenController {

    @FXML
    Button HomeFindPath;

    @FXML
    Button HomeServiceRequest;

    @FXML
    Button BookRoom;

    @FXML
    Button HomeSuggestions;

    @FXML
    Button LogIn;

    @FXML
    private void SwitchToBookRoom(ActionEvent event) {
        try {
            Stage thestage = (Stage) BookRoom.getScene().getWindow();
            AnchorPane root;
            root = FXMLLoader.load(getClass().getResource("BookRoom.fxml"));
            Scene scene = new Scene(root);
            thestage.setScene(scene);
        } catch (Exception e) {
        }
    }

    @FXML
    private void SwitchToPathfindScreen() throws IOException{
        boolean signedIn = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HospitalPathFinding.fxml"));

        Parent sceneMain = loader.load();

        PathFindingController controller = loader.<PathFindingController>getController();
        controller.initialize(signedIn);

        Stage theStage = (Stage) HomeServiceRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToSuggestionBox(ActionEvent event) {
        try {
            Stage thestage = (Stage) HomeFindPath.getScene().getWindow();
            AnchorPane root;
            root = FXMLLoader.load(getClass().getResource("SuggestionBox.fxml"));
            Scene scene = new Scene(root);
            thestage.setScene(scene);
        } catch (Exception e){

        }
    }

    @FXML
    private void SwitchToServiceScreen() throws IOException{
        boolean signedIn = false;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServiceRequest.fxml"));

        Parent sceneMain = loader.load();

        ServiceRequestController controller = loader.<ServiceRequestController>getController();
        controller.init(signedIn);

        Stage theStage = (Stage) HomeServiceRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void SwitchToLoginScreen(ActionEvent event){
        try {
            Stage thestage = (Stage) LogIn.getScene().getWindow();
            AnchorPane root;
            root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
            Scene scene = new Scene(root);
            thestage.setScene(scene);
        } catch (Exception e){
        }
    }
}
