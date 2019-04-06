package Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ServiceRequestController {
    private boolean signedIn;
    private String uname;

    @FXML
    private Button SanitationServices;

    @FXML
    private Button ExternalTransportation;

    @FXML
    private Button ITServices;

    @FXML
    private Button FacilitiesMaintenance;

    @FXML
    private Button LanguageInterpreter;

    @FXML
    private Button SecurityStaff;

    @FXML
    private Button Florist;

    @FXML
    private Button InternalTransportation;

    @FXML
    private Button ReligiousServices;

    @FXML
    private Button AudioVisual;

    @FXML
    private Button PrescriptionServices;

    @FXML
    private Button GiftStoreServices;

    @FXML
    public Button Back;

    public void init(boolean loggedIn){
        signedIn = loggedIn;
    }

    public void init(boolean loggedIn, String username) {
        uname = username;
        init(loggedIn);
    }

    @FXML
    protected void backPressed() throws IOException {
        Stage theStage = (Stage) Back.getScene().getWindow();
        AnchorPane root;
        if(signedIn){
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));

            Parent sceneMain = loader.load();

            LoggedInHomeController controller = loader.<LoggedInHomeController>getController();
            controller.init(uname);

            theStage = (Stage) SanitationServices.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);
            return;
        } else {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("HospitalHome.fxml"));
        }
        Scene scene = new Scene(root);
        theStage.setScene(scene);
    }

    //passes off type of button
    @FXML
    /*private void makeRequest(ActionEvent e) throws IOException{
        //source button determines type for service request object, text for label
        String typeOfService = "";
        if(e.getSource() == SanitationServices) {
            typeOfService = "Sanitation";
        } else if(e.getSource() == ExternalTransportation) {
            typeOfService = "Transportation";
        } else if(e.getSource() == ITServices) {
            typeOfService = "IT";
        } else if(e.getSource() == FacilitiesMaintenance) {
            typeOfService = "Maintenance";
        } else if(e.getSource() == LanguageInterpreter) {
            typeOfService = "Language Interpreter";
        } else {
            typeOfService = "Security";
        }

        changeToSub(typeOfService);
    }
*/
    //Nathan - changes screen to service sub screen, param "service" determines label on sub screen
    private void changeToSub(ActionEvent e) throws IOException{
        String fxml;

        if(e.getSource() == SanitationServices) {
            fxml = "HospitalHome.fxml";
        } else if(e.getSource() == ExternalTransportation) {
            fxml = "HospitalHome.fxml";
        } else if(e.getSource() == ITServices) {
            fxml = "HospitalHome.fxml";
        } else if(e.getSource() == FacilitiesMaintenance) {
            fxml = "HospitalHome.fxml";
        } else if(e.getSource() == LanguageInterpreter) {
            fxml = "HospitalHome.fxml";
        } else if(e.getSource() == Florist) {
            fxml = "HospitalHome.fxml";
        } else if(e.getSource() == InternalTransportation) {
            fxml = "HospitalHome.fxml";
        } else if(e.getSource() == ReligiousServices) {
            fxml = "ReligiousServiceRequest.fxml";
        } else if(e.getSource() == AudioVisual) {
            fxml = "HospitalHome.fxml";
        } else if(e.getSource() == PrescriptionServices) {
            fxml = "HospitalHome.fxml";
        } else if(e.getSource() == GiftStoreServices) {
            fxml = "HospitalHome.fxml";
        } else if(e.getSource() == SecurityStaff) {
            fxml = "HospitalHome.fxml";

        } else {
            fxml = "HospitalHome.fxml";
        }
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxml));

        Parent sceneMain = loader.load();

        //ServiceSubController controller = loader.<ServiceSubController>getController();

        Stage theStage = (Stage) ReligiousServices.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

}
