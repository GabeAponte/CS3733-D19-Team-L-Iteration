package Controller;

import Object.*;
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

    @FXML
    private Button ITServices;

    @FXML
    private Button ReligiousServices;

    @FXML
    private Button LanguageInterpreter;

    @FXML
    private Button ExternalTransportation;

    @FXML
    private Button Florist;

    @FXML
    private Button AudioVisual;

    @FXML
    private Button FacilitiesMaintenance;

    @FXML
    private Button SecurityStaff;

    @FXML
    private Button SanitationServices;

    @FXML
    private Button InternalTransportation;

    @FXML
    private Button PrescriptionServices;

    @FXML
    private Button GiftStoreServices;


    @FXML
    public Button Back;

    @FXML
    protected void backPressed() throws IOException {
        Singleton single = Singleton.getInstance();
        Stage theStage = (Stage) Back.getScene().getWindow();
        AnchorPane root;
        if(single.isLoggedIn()){
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));

            Parent sceneMain = loader.load();

            LoggedInHomeController controller = loader.<LoggedInHomeController>getController();

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
    private void makeRequest(ActionEvent e) throws IOException{
        //source button determines type for service request object, text for label
        String serviceFXML = "";
        if(e.getSource() == ITServices) {
            serviceFXML = "ITServiceRequest.fxml";
        } else if(e.getSource() == ReligiousServices) {
            serviceFXML = "ReligiousServiceRequest";
        } else if(e.getSource() == LanguageInterpreter) {
            serviceFXML = "ITServiceRequest.fxml";
        } else if(e.getSource() == ExternalTransportation) {
            serviceFXML = "ITServiceRequest.fxml";
        } else if(e.getSource() == Florist) {
            serviceFXML = "ITServiceRequest.fxml";
        }else if(e.getSource() == AudioVisual) {
            serviceFXML = "ITServiceRequest.fxml";
        }else if(e.getSource() == FacilitiesMaintenance) {
            serviceFXML = "ITServiceRequest.fxml";
        }else if(e.getSource() == SecurityStaff) {
            serviceFXML = "ITServiceRequest.fxml";
        }else if(e.getSource() == SanitationServices) {
            serviceFXML = "sanitationServiceRequest.fxml";
        }else if(e.getSource() == InternalTransportation) {
            serviceFXML = "ITServiceRequest.fxml";
        }else if(e.getSource() == PrescriptionServices) {
            serviceFXML = "ITServiceRequest.fxml";
        }else if(e.getSource() == GiftStoreServices) {
            serviceFXML = "ITServiceRequest.fxml";
        }
        changeToSub(serviceFXML);
    }

    //Nathan - changes screen to service sub screen, param "service" determines label on sub scree
    @FXML
    private void changeToSub(String fxml) throws IOException{

        Stage theStage = (Stage) Back.getScene().getWindow();
        AnchorPane root;
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequestFloristDelivery.fxml"));

            Parent sceneMain = loader.load();

            theStage = (Stage) SanitationServices.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);

    }

    @FXML
    private void changeToInternalTransport() throws IOException{

        Stage theStage = (Stage) Back.getScene().getWindow();
        AnchorPane root;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("InternalTransport.fxml"));

        Parent sceneMain = loader.load();

        //LoggedInHomeController controller = loader.<LoggedInHomeController>getController();

        theStage = (Stage) SanitationServices.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);

    }

    @FXML
    private void changeToReligiousRequest() throws IOException{

        Stage theStage = (Stage) Back.getScene().getWindow();
        AnchorPane root;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ReligiousServiceRequest.fxml"));

        Parent sceneMain = loader.load();

        //LoggedInHomeController controller = loader.<LoggedInHomeController>getController();

        theStage = (Stage) SanitationServices.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);

    }

}