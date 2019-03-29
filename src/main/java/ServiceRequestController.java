import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ServiceRequestController {

    private Stage thestage;
    private String typeOfService;

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
    public Label typeLabel;

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

    //TODO: when submit is pressed, prompt "Are You Sure"
    @FXML
    private void promptCancel(ActionEvent e){

    }

    //TODO: when button is pressed, change fxml, change label, make service request object or pass type into subcontroller
    @FXML
    private void makeRequest(ActionEvent e) throws IOException{
        //source button determines type for service request object, text for label
        if(e.getSource() == SanitationServices) {
            typeOfService = "Sanitation";
        } else if(e.getSource() == Transportation) {
            typeOfService = "Transportation";
        } else if(e.getSource() == ITServices) {
            typeOfService = "IT";
        } else if(e.getSource() == FacilitiesMaitnence) {
            typeOfService = "Maintenance";
        } else if(e.getSource() == LanguageInterpreter) {
            typeOfService = "Language Interpreter";
        } else {
            typeOfService = "Security";
        }

        changeToSub();
    }

    private void changeToSub() throws IOException{
        thestage = (Stage) SanitationServices.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("ServiceSubController.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

}
