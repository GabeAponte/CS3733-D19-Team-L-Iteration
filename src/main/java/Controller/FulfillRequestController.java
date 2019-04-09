package Controller;

import Access.ServiceRequestAccess;
import Object.ServiceRequestTable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import Object.*;

import java.io.IOException;

public class FulfillRequestController {

    private Stage thestage;

    private ServiceRequestTable theRequest;

    @FXML
    private Button back;

    @FXML
    private Button fulfill;

    @FXML
    private TextField staffMember;

    @FXML
    private Label errorLabel;
    @SuppressWarnings("Duplicates")

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

                        Stage thisStage = (Stage) fulfill.getScene().getWindow();

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
    /**@author Gabe
     * Returns user to the Logged In Home screen when the back button is pressed
     */
    private void backPressed() throws IOException {
        timeout.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ActiveServiceRequests.fxml"));
        Parent roots = loader.load();

        Scene scene = new Scene(roots);
        Stage thestage = (Stage) errorLabel.getScene().getWindow();
        //Show scene 2 in new window
        thestage.setScene(scene);
    }

    @FXML
    public void getRequestID(ServiceRequestTable request) {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        theRequest = request;
    }

    @FXML
    @SuppressWarnings("Duplicates")
    /**@author Gabe, DJ
     * Once a staff members name is inputed into the text field and the submit button is pressed,
     * the user is brough back to the active service requests screen and the inputed information
     * is updated in the database
     */
    private void SwitchToAdminServiceRequestTable() throws IOException {

        if (staffMember.getText().trim().isEmpty() || staffMember.getText().equals("Staff Member")) {
            errorLabel.setText("Please enter a staff member name");
            Singleton single = Singleton.getInstance();
            single.setLastTime();
        }
        else{
            timeout.stop();
            ServiceRequestAccess sa = new ServiceRequestAccess();
            sa.fulfillRequest(Integer.parseInt(theRequest.getRequestID()), staffMember.getText());
            errorLabel.setText("Request Fulfilled");

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ActiveServiceRequests.fxml"));
            Parent roots = loader.load();

            Scene scene = new Scene(roots);
            Stage thestage = (Stage) errorLabel.getScene().getWindow();

            //Show scene 2 in new window
            thestage.setScene(scene);
            }
        }
    }
