import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ServiceRequestController {

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

    //TODO: when submit is pressed, prompt "Are You Sure"
    @FXML
    private void promptCancel(ActionEvent e){

    }

    //TODO: when button is pressed, change label, make service request object with type
    @FXML
    private void makeRequest(ActionEvent e){
        //source button determines type for service request object, text for label
        if(e.getSource() == SanitationServices) {

        } else if(e.getSource() == Transportation) {

        } else if(e.getSource() == ITServices) {

        } else if(e.getSource() == FacilitiesMaitnence) {

        } else if(e.getSource() == LanguageInterpreter) {

        } else {

        }
    }

}
