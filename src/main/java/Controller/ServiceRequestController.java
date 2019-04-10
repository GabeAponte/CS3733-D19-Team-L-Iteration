package Controller;

import Object.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    Timeline timeout;

    public void initialize(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()){
                    try{
                        single.setDoPopup(true);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));
                        Parent sceneMain = loader.load();
                        if(single.isLoggedIn()){
                            HomeScreenController controller = loader.<HomeScreenController>getController();
                            controller.displayPopup();
                        }
                        single.setLastTime();
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        Stage thisStage = (Stage) Back.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
                        timeout.stop();
                    } catch (IOException io){
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));
        timeout.setCycleCount(Timeline.INDEFINITE);
        timeout.play();
    }
    @FXML
    protected void backPressed() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        Stage theStage = (Stage) Back.getScene().getWindow();
        AnchorPane root;
        if (single.isLoggedIn()) {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EmployeeLoggedInHome.fxml"));
            if (single.isIsAdmin()) {
                loader = new FXMLLoader(getClass().getClassLoader().getResource("AdminLoggedInHome.fxml"));
            }
            Parent sceneMain = loader.load();

            theStage = (Stage) SanitationServices.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);
            return;
        } else {
            single.setDoPopup(true);
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));
            Parent sceneMain = loader.load();

            theStage = (Stage) SanitationServices.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);
    }
    }

    //passes off type of button
    @FXML
    private void makeRequest(ActionEvent e) throws IOException{
        //source button determines type for service request object, text for label
        String serviceFXML = "";
        if(e.getSource() == ITServices) {
            serviceFXML = "ITServiceRequest.fxml";
        }
        else if(e.getSource() == ReligiousServices) {
            serviceFXML = "ReligiousServiceRequest.fxml";
        }
        else if(e.getSource() == LanguageInterpreter) {
            serviceFXML = "ServiceRequestLanguage.fxml";
        }
        else if(e.getSource() == ExternalTransportation) {
            serviceFXML = "ExternalTransportationServiceRequest.fxml";
        }
        else if(e.getSource() == Florist) {
            serviceFXML = "FloristDeliveryServiceRequest.fxml";
        }
        else if(e.getSource() == AudioVisual) {
            serviceFXML = "AudioVisualRequest.fxml";
        }
        else if(e.getSource() == FacilitiesMaintenance) {
            serviceFXML = "ServiceRequestMaintenance.fxml";
        }
        else if(e.getSource() == SecurityStaff) {
            serviceFXML = "SecurityServiceRequest.fxml";
        }
        else if(e.getSource() == SanitationServices) {
            serviceFXML = "sanitationServiceRequest.fxml";
        }
        else if(e.getSource() == InternalTransportation) {
            serviceFXML = "InternalTransport.fxml";
        }
        else if(e.getSource() == PrescriptionServices) {
            serviceFXML = "PrescriptionServiceRequest.fxml";
        }
        else if(e.getSource() == GiftStoreServices) {
            serviceFXML = "ReligiousServiceRequest.fxml";
        }
        changeToSub(e.getSource(), serviceFXML);
    }

    //Nathan - changes screen to service sub screen, param "service" determines label on sub scree
    @FXML
    private void changeToSub(Object e, String fxml) throws IOException{
        timeout.stop();
        Stage theStage = (Stage) Back.getScene().getWindow();
        AnchorPane root;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxml));

        Parent sceneMain = loader.load();

        //LoggedInHomeController controller = loader.<LoggedInHomeController>getController();

        theStage = (Stage) SanitationServices.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);

    }

    @FXML
    private void changeToInternalTransport() throws IOException{
        timeout.stop();
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
        timeout.stop();
        Stage theStage = (Stage) Back.getScene().getWindow();
        AnchorPane root;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ReligiousServiceRequest.fxml"));

        Parent sceneMain = loader.load();

        //LoggedInHomeController controller = loader.<LoggedInHomeController>getController();

        theStage = (Stage) SanitationServices.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);

    }

    @FXML
    private void changeToMaintenanceRequest() throws IOException {
        Stage theStage = (Stage) Back.getScene().getWindow();
        AnchorPane root;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequestMaintenance.fxml"));

        Parent sceneMain = loader.load();

        theStage = (Stage) SanitationServices.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

}