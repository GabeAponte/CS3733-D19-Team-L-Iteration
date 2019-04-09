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
    private Button SanitationServices;

    @FXML
    private Button Transportation;

    @FXML
    private Button ITServices;

    @FXML
    private Button FacilitiesMaintenance;

    @FXML
    private Button LanguageInterpreter;

    @FXML
    private Button SecurityStaff;

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
                        single.setLastTime();
                        timeout.stop();
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        Parent sceneMain = loader.load();

                        Stage thisStage = (Stage) Back.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
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
        String typeOfService = "";
        if(e.getSource() == SanitationServices) {
            typeOfService = "Sanitation";
        } else if(e.getSource() == Transportation) {
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

       // changeToSub(typeOfService);
    }

    //Nathan - changes screen to service sub screen, param "service" determines label on sub scree
    @FXML
    private void changeToFlorist() throws IOException{
        timeout.stop();
        Stage theStage = (Stage) Back.getScene().getWindow();
        AnchorPane root;
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequestFloristDelivery.fxml"));

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

}