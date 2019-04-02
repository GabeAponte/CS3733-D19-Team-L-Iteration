import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import java.io.IOException;

public class ServiceSubController {

    private String typeOfService;
    private boolean signedIn;
    private String uname;

    @FXML
    private Button Back2;

    @FXML
    private Label typeLabel;

    @FXML
    private Button SubmitRequest;

    @FXML
    private TextArea ServiceComments;


    void init(String type, boolean loggedIn, String username){
        uname = username;
        init(type, loggedIn);
    }

    void init(String type, String comment, boolean loggedIn, String username){
        uname = username;
        init(type, loggedIn);
    }

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServiceRequest.fxml"));

        Parent sceneMain = loader.load();

        ServiceRequestController controller = loader.<ServiceRequestController>getController();
        if(signedIn){
            controller.init(signedIn, uname);
        } else {
            controller.init(signedIn);
        }

        Stage theStage = (Stage) Back2.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    //Nathan - Changes screen to cancel screen, passes along information
    @FXML
    private void promptCancel() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Cancel.fxml"));

        Parent sceneMain = loader.load();

        Cancel controller = loader.<Cancel>getController();

        if(ServiceComments == null || ServiceComments.getText() == null || ServiceComments.getText().trim().isEmpty()){
            controller.init(typeOfService, signedIn);
        } else {
            controller.init(typeOfService, ServiceComments.getText(), signedIn);
        }
        Stage theStage = (Stage) SubmitRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }
}
