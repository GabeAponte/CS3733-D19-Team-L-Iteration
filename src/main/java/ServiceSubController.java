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

    private Stage thestage;
    private String typeOfService;

    @FXML
    private Button Back2;

    @FXML
    private Label typeLabel;

    @FXML
    private Button SubmitRequest;

    @FXML
    private TextArea ServiceComments;


    //Nathan - sets values passed from another controller
    void init(String type){
        typeOfService = type;
        typeLabel.setText(typeOfService + " Services");
    }

    //Nathan - sets values passed from another controller
    void init(String type, String comment){
        typeOfService = type;
        typeLabel.setText(typeOfService + " Services");
        ServiceComments.setText(comment);
    }

    //Nathan - takes you back to service request screen
    @FXML
    private void backPressed() throws IOException {
        thestage = (Stage) Back2.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("ServiceRequest.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    //Nathan - Changes screen to cancel screen, passes along information
    @FXML
    private void promptCancel(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Cancel.fxml"));

        Parent sceneMain = loader.load();

        Cancel controller = loader.<Cancel>getController();

        if(ServiceComments == null || ServiceComments.getText() == null || ServiceComments.getText().trim().isEmpty()){
            controller.init(typeOfService);
        } else {
            controller.init(typeOfService, ServiceComments.getText());
        }
        thestage = (Stage) SubmitRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        thestage.setScene(scene);
    }
}