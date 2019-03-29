import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ServiceSubController {

    private Stage thestage;
    private String typeOfService;

    @FXML
    private Button Back;

    @FXML
    public Label typeLabel;

    @FXML
    private Button SubmitRequest;

    @FXML
    private TextArea ServiceComments;

    @FXML
    public void initialize(String type){
        typeOfService = type;
        typeLabel.setText(typeOfService + " Services");
    }

    @FXML
    private void back2Pressed() throws IOException {
        thestage = (Stage) Back.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("ServiceRequest.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    //TODO: when submit is pressed, prompt "Are You Sure"
    @FXML
    private void promptCancel(ActionEvent e){

    }
}
