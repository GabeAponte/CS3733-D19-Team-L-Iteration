package edu.wpi.cs3733.d19.teamL.ServiceRequest.MakeServiceRequest;

import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.Singleton;
import edu.wpi.cs3733.d19.teamMService.Main;
import giftRequest.GiftRequest;
import giftRequest.ServiceException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import edu.wpi.cs3733.d19.teamMService.controllers.LanguageRequests;
import imaging.*;
import foodRequest.FoodRequest;

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
    private Button logOut;

    @FXML
    private Button GiftStoreServices;


    @FXML
    public Button back;

    Timeline timeout;

    public void initialize(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if(!single.isLoggedIn()){
            logOut.setText("Log In");
        }
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()){
                    try{
                        single.setDoPopup(true);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));
                        Parent sceneMain = loader.load();
                        if(single.isLoggedIn()) {
                            single.setLoggedIn(false);
                            HomeScreenController controller = loader.<HomeScreenController>getController();
                            controller.displayPopup();
                        }
                        single.setLastTime();
                        single.setUsername("");
                        single.setIsAdmin(false);
                        Stage thisStage = (Stage) back.getScene().getWindow();
                        Screen screen = Screen.getPrimary();
                        Rectangle2D bounds = screen.getVisualBounds();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setMaximized(true);
                        thisStage.setScene(newScene);
                        thisStage.setX(bounds.getMinX());
                        thisStage.setY(bounds.getMinY());
                        thisStage.setWidth(bounds.getWidth());
                        thisStage.setHeight(bounds.getHeight());
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


    //passes off type of button
    @FXML
    private void makeRequest(ActionEvent e) throws IOException{
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        //source button determines type for service request object, text for label
        String serviceFXML = "";
        if(e.getSource() == ITServices) {
            serviceFXML = "ITServiceRequest.fxml";
            timeout.stop();
            saveState();
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(serviceFXML));
            ((Node) e.getSource()).getScene().setRoot(newPage);
        }
        else if(e.getSource() == ReligiousServices) {
            serviceFXML = "ReligiousServiceRequest.fxml";
            timeout.stop();
            saveState();
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(serviceFXML));
            ((Node) e.getSource()).getScene().setRoot(newPage);
        }
        else if(e.getSource() == LanguageInterpreter) {
            serviceFXML = "ServiceRequestLanguage.fxml";
            timeout.stop();
            saveState();
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(serviceFXML));
            ((Node) e.getSource()).getScene().setRoot(newPage);
        }
        else if(e.getSource() == ExternalTransportation) {
            serviceFXML = "ExternalTransportationServiceRequest.fxml";
            timeout.stop();
            saveState();
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(serviceFXML));
            ((Node) e.getSource()).getScene().setRoot(newPage);
        }
        else if(e.getSource() == Florist) {
            serviceFXML = "FloristDeliveryServiceRequest.fxml";
            timeout.stop();
            saveState();
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(serviceFXML));
            ((Node) e.getSource()).getScene().setRoot(newPage);
        }
        else if(e.getSource() == AudioVisual) {
            serviceFXML = "AudioVisualRequest.fxml";
            timeout.stop();
            saveState();
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(serviceFXML));
            ((Node) e.getSource()).getScene().setRoot(newPage);
        }
        else if(e.getSource() == FacilitiesMaintenance) {
            serviceFXML = "ServiceRequestMaintenance.fxml";
            timeout.stop();
            saveState();
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(serviceFXML));
            ((Node) e.getSource()).getScene().setRoot(newPage);
        }
        else if(e.getSource() == SecurityStaff) {
            serviceFXML = "SecurityServiceRequest.fxml";
            timeout.stop();
            saveState();
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(serviceFXML));
            ((Node) e.getSource()).getScene().setRoot(newPage);
        }
        else if(e.getSource() == SanitationServices) {
            serviceFXML = "sanitationServiceRequest.fxml";
            timeout.stop();
            saveState();
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(serviceFXML));
            ((Node) e.getSource()).getScene().setRoot(newPage);
        }
        else if(e.getSource() == InternalTransportation) {
            serviceFXML = "InternalTransport.fxml";
            timeout.stop();
            saveState();
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(serviceFXML));
            ((Node) e.getSource()).getScene().setRoot(newPage);
        }
        else if(e.getSource() == PrescriptionServices) {
            serviceFXML = "PrescriptionServiceRequest.fxml";
            timeout.stop();
            saveState();
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(serviceFXML));
            ((Node) e.getSource()).getScene().setRoot(newPage);
        }
        else if(e.getSource() == GiftStoreServices) {
            serviceFXML = "ReligiousServiceRequest.fxml";
            timeout.stop();
            saveState();
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(serviceFXML));
            ((Node) e.getSource()).getScene().setRoot(newPage);
        }
    }

    //Nathan - changes screen to service sub screen, param "service" determines label on sub scree
    @FXML
    private void changeToSub(Object e, String fxml) throws IOException{
        timeout.stop();
        saveState();

    }

    @FXML
    private void openGiftService() throws ServiceException {
        GiftRequest gr = new GiftRequest();
        try{
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            gr.run(0,0,(int)bounds.getWidth(),(int)bounds.getHeight(), "",null,null);
        }catch (Exception e){
            System.out.println("Failed to run API");
            e.printStackTrace();
        }
    }

    @FXML
    private void openMedical(){
        ImagingRequest ir = new ImagingRequest();
        try{
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

            ir.run(0,0,(int)bounds.getHeight(),(int)bounds.getWidth(), "","ELABS00101", "ELABS00101");
        }catch (Exception e){
            System.out.println("Failed to run API");
            e.printStackTrace();
        }

    }

    @FXML
    private void openFood(){
        FoodRequest foodRequest = new FoodRequest();
        try{
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            foodRequest.run(0,0,(int)bounds.getWidth(),(int)bounds.getHeight(),null,null,null);
        }catch (Exception e){
            System.out.println("Failed to run API");
            e.printStackTrace();
        }
    }

    @FXML
    private void openLanguage(){
        Main m = new Main();
        try {
            Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            m.run(0, 0, (int)bounds.getWidth(),(int)bounds.getHeight(), "", null);
        }catch(Exception e){
            System.out.println("Failed to load Food API");
        }
    }

    /**@author Nathan
     * Saves the memento state
     */
    private void saveState(){
        Singleton single = Singleton.getInstance();
        single.saveMemento("ServiceRequest.fxml");
    }

    @FXML
    private void backPressed(ActionEvent event) throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        Memento m = single.restore();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        timeout.stop();

        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if(!single.isLoggedIn()){
            saveState();
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("LogIn.fxml"));
            ((Node) event.getSource()).getScene().setRoot(newPage);
            return;
        }
        single.setUsername("");
        single.setIsAdmin(false);
        single.setLoggedIn(false);
        single.setDoPopup(true);
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        saveState();
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

}