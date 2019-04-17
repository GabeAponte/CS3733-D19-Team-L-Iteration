package edu.wpi.cs3733.d19.teamL.ServiceRequest.FulfillServiceRequest;

import edu.wpi.cs3733.d19.teamL.Account.EmployeeAccess;
import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.ServiceRequestDBAccess.ServiceRequestAccess;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class FulfillRequestController {

    private Stage thestage;

    private TreeItem<ServiceRequestTable> theRequest;

    @FXML
    private Button back;


    @FXML
    private ToggleButton fulfill;

    @FXML
    private Button submit;

    @FXML
    private JFXComboBox<String> staffMember;

    private int rid;
    private String table = "";
    @SuppressWarnings("Duplicates")

    Timeline timeout;

    public void initialize() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if ((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()) {
                    try {
                        single.setLastTime();
                        single.setDoPopup(true);
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        Parent sceneMain = loader.load();
                        HomeScreenController controller = loader.<HomeScreenController>getController();
                        single.setLastTime();
                        controller.displayPopup();
                        single.setLastTime();

                        Stage thisStage = (Stage) submit.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
                        timeout.stop();
                    } catch (IOException io) {
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));
        timeout.setCycleCount(Timeline.INDEFINITE);
        timeout.play();
        submit.setDisable(true);

    }
    @FXML
    /**@author Gabe
     * Returns user to the Logged In Home screen when the back button is pressed
     */
    private void backPressed() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ActiveServiceRequests.fxml"));
        Parent roots = loader.load();

        Scene scene = new Scene(roots);
        Stage thestage = (Stage) back.getScene().getWindow();
        //Show scene 2 in new window
        thestage.setScene(scene);
    }

    @FXML
    public void open(){
        if (staffMember.getValue() != null) {
            submit.setDisable(false);
        } else {
            submit.setDisable(true);
        }
    }

    @FXML
    public void getRequestID(TreeItem<ServiceRequestTable> request) {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        theRequest = request;
    }

    public void setRid(int rid, String table){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        this.rid = rid;
        if(table.equals("Religious")){
            this.table = "religiousRequest";
        }else if(table.equals("Internal Transportation")){
            this.table = "internalTransportationRequest";
        }else if(table.equals("Audio/Visual")){
            this.table = "audioVisualRequest";
        }else if(table.equals("External Transportation")){
            this.table = "externalTransportationRequest";
        }else if(table.equals("Florist Delivery")){
            this.table = "floristDelivery";
        }else if(table.equals("IT")){
            this.table = "ITRequest";
        }else if(table.equals("Language Assistance")){
            this.table = "languageRequest";
        }else if(table.equals("Maintenance")){
            this.table = "maintenanceRequest";
        }else if(table.equals("Prescriptions")){
            this.table = "prescriptionRequest";
        }else if(table.equals("Sanitations")){
            this.table = "sanitationRequest";
        }else if(table.equals("Security")){
            this.table = "securityRequest";
        }

        EmployeeAccess ea = new EmployeeAccess();
        String field = "";
        if (this.table.equals("audioVisualRequest")) {
            field = "Audio Visual";
        } else if (this.table.equals("externalTransportationRequest")) {
            field = "External Transportation";
        } else if (this.table.equals("floristDeliveryRequest")) {
            field = "Florist Delivery";
        } else if (this.table.equals("internalTransportationRequest")) {
            field = "Internal Transportation";
        } else if (this.table.equals("ITRequest")) {
            field = "IT";
        } else if (this.table.equals("languageRequest")) {
            field = "Language";
        } else if (this.table.equals("maintenanceRequest")) {
            field = "Maintenance";
        } else if (this.table.equals("prescriptionRequest")) {
            field = "Prescription";
        } else if (this.table.equals("religiousRequest")) {
            field = "Religious";
        } else if (this.table.equals("sanitationRequest")) {
            field = "Sanitation";
        } else if (this.table.equals("securityRequest")) {
            field = "Security";
        }
        ArrayList<ArrayList<String>> list = ea.getEmployees("department", field);
        for (int i = 0; i < list.size(); i++) {
            staffMember.getItems().add(list.get(i).get(0));
        }
        if(!single.isIsAdmin()){
            field = ea.getEmployeeInformation(single.getUsername()).get(1);
            staffMember.setValue(ea.getEmployeeInformation(single.getUsername()).get(0));
            staffMember.setDisable(true);
        }
    }

    @FXML
    private void submitPressed() throws IOException{
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        ServiceRequestAccess sra = new ServiceRequestAccess();
        sra.assignEmployee(this.rid, staffMember.getValue().toString(), this.table);
        if(fulfill.isSelected()){
            sra.fulfillRequest(this.rid,this.table);
        }
        if(staffMember.getValue() != null){
            backPressed();
        }
    }

    @FXML
    @SuppressWarnings("Duplicates")
    /**@author Gabe, DJ
     * Once a staff members name is inputed into the text field and the submit button is pressed,
     * the user is brough back to the active service requests screen and the inputed information
     * is updated in the database
     */
    private void SwitchToAdminServiceRequestTable() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ActiveServiceRequests.fxml"));
        Parent roots = loader.load();

        //Get controller of scene2
        ActiveServiceRequestsController scene2Controller = loader.getController();

        Scene scene = new Scene(roots);
        Stage thestage = (Stage) back.getScene().getWindow();

        //Show scene 2 in new window
        thestage.setScene(scene);
    }

}