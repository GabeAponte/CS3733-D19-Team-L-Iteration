import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ServiceSubController {

    Stage thestage;
    private String typeOfService;

    @FXML
    Button Back2;

    @FXML
    public Label typeLabel;

    @FXML
    private Button SubmitRequest;

    @FXML
    private TextArea ServiceComments;

    @FXML
    public void init(String type){
        typeOfService = type;
        typeLabel.setText(typeOfService + " Services");
    }

    @FXML
    private void backPressed() throws IOException {
        thestage = (Stage) Back2.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("ServiceRequest.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    //TODO: when submit is pressed, prompt "Are You Sure"
    @FXML
    private void promptCancel(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Cancel.fxml"));

        Parent sceneMain = loader.load();

        Cancel controller = loader.<Cancel>getController();
        controller.init(typeOfService, ServiceComments.getText());

        thestage = (Stage) Back2.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        thestage.setScene(scene);
    }
}
