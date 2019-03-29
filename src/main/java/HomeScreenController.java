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
    Button HomeReqService;

    @FXML
    private void SwitchToPathfindScreen(ActionEvent event){
        try {
            Stage thestage = (Stage) HomeFindPath.getScene().getWindow();
            AnchorPane root;
            root = FXMLLoader.load(getClass().getResource("HospitalPathFinding.fxml"));
            Scene scene = new Scene(root);
            thestage.setScene(scene);
        } catch (Exception e){

        }
    }

    @FXML
    private void SwitchToServiceRequest(ActionEvent event){
        try {
            Stage thestage = (Stage) HomeReqService.getScene().getWindow();
            AnchorPane root;
            root = FXMLLoader.load(getClass().getResource("ServiceRequest.fxml"));
            Scene scene = new Scene(root);
            thestage.setScene(scene);
        } catch (Exception e){

        }
    }

}
