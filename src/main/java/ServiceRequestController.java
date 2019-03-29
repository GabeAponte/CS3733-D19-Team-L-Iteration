import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ServiceRequestController {

    private Stage thestage;

    @FXML
    private Button SanitationServices;

    @FXML
    private Button Transportation;

    @FXML
    private Button ITServices;

    @FXML
    private Button FacilitiesMaitnence;

    @FXML
    private Button LanguageInterpreter;

    @FXML
    private Button SecurityStaff;

    @FXML
    private Button ServiceNext;

    @FXML
    private Button SubmitRequest;

    @FXML
    private TextArea ServiceComments;

    @FXML
    private Button Back;

    @FXML
    private Button Back2;

    @FXML
    private void ServiceNextScreen() throws IOException {
        thestage = (Stage) ServiceNext.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("ServiceSubController.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    private void backPressed() throws IOException {
        thestage = (Stage) Back.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    private void back2Pressed() throws IOException {
        thestage = (Stage) Back2.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("ServiceRequest.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }


}
