import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    private Button Back;

    @FXML
    private void backPressed() throws IOException {
        thestage = (Stage) Back.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    //passes off type of button
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

        changeToSub(typeOfService);
    }

    //Nathan - changes screen to service sub screen, param "service" determines label on sub screen
    private void changeToSub(String service) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServiceSubController.fxml"));

        Parent sceneMain = loader.load();

        ServiceSubController controller = loader.<ServiceSubController>getController();
        controller.init(service);

        thestage = (Stage) SanitationServices.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        thestage.setScene(scene);
    }

}
