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

    private Stage theStage;
    private String typeOfService;
    private boolean signedIn;

    @FXML
    private Button Back2;

    @FXML
    private Label typeLabel;

    @FXML
    private Button SubmitRequest;

    @FXML
    private TextArea ServiceComments;


    //Nathan - sets values passed from another controller
    void init(String type, boolean loggedIn){
        typeOfService = type;
        typeLabel.setText(typeOfService + " Services");
        signedIn = loggedIn;
    }

    //Nathan - sets values passed from another controller
    void init(String type, String comment, boolean loggedIn){
        typeOfService = type;
        typeLabel.setText(typeOfService + " Services");
        ServiceComments.setText(comment);
        signedIn = loggedIn;
    }

    //Nathan - changes screen to service sub screen, param "service" determines label on sub screen
    @FXML
    private void backPressed() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServiceRequestController.fxml"));

        Parent sceneMain = loader.load();

        ServiceRequestController controller = loader.<ServiceRequestController>getController();
        controller.init(signedIn);

        theStage = (Stage) SubmitRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    //Nathan - Changes screen to cancel screen, passes along information
    @FXML
    private void promptCancel(ActionEvent e) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Cancel.fxml"));

        Parent sceneMain = loader.load();

        Cancel controller = loader.<Cancel>getController();

        if(ServiceComments == null || ServiceComments.getText() == null || ServiceComments.getText().trim().isEmpty()){
            controller.init(typeOfService, signedIn);
        } else {
            controller.init(typeOfService, ServiceComments.getText(), signedIn);
        }
        theStage = (Stage) SubmitRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }
}
