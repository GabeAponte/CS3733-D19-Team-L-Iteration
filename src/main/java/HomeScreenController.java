import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class HomeScreenController {

    private Stage stage;

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
    private void SwitchToPathfindScreen(ActionEvent event) {
        try {
            Stage thestage = (Stage) HomeFindPath.getScene().getWindow();
            AnchorPane root;
            root = FXMLLoader.load(getClass().getResource("HospitalPathFinding.fxml"));
            Scene scene = new Scene(root);
            thestage.setScene(scene);
        } catch (Exception e) {
        }
    }
////
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
    private void SwitchToServiceScreen(ActionEvent event){
        try {
            Stage thestage = (Stage) HomeServiceRequest.getScene().getWindow();
            AnchorPane root;
            root = FXMLLoader.load(getClass().getResource("ServiceRequest.fxml"));
            Scene scene = new Scene(root);
            thestage.setScene(scene);
        } catch (Exception e){
        }
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
