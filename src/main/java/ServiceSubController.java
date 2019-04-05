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

    @FXML
    private Button Back2;

    @FXML
    private Label typeLabel;

    @FXML
    private Button SubmitRequest;

    @FXML
    private TextArea ServiceComments;

    public void init(String type, String comment){
        typeOfService = type;
        typeLabel.setText(type + " Services");
        ServiceComments.setText(comment);
        reenable();
    }

    //Nathan - changes screen to service sub screen, param "service" determines label on sub screen
    @FXML
    private void backPressed() throws IOException{
        Singleton single = Singleton.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServiceRequest.fxml"));

        Parent sceneMain = loader.load();

        ServiceRequestController controller = loader.<ServiceRequestController>getController();

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
        String comm = ServiceComments.getText();
        if(comm == null || comm.trim().isEmpty()) {
            controller.init(typeOfService, "");
        } else {
            controller.init(typeOfService, ServiceComments.getText());
        }
        Stage theStage = (Stage) SubmitRequest.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void reenable(){
        Boolean disable = (ServiceComments.getText().isEmpty() || ServiceComments.getText().trim().isEmpty());
        if(!disable){
            SubmitRequest.setDisable(false);
        } else {
            SubmitRequest.setDisable(true);
        }
    }

}
