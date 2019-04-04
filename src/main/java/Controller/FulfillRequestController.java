package Controller;

import Access.ServiceRequestAccess;
import Object.ServiceRequestTable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FulfillRequestController {

    private Stage thestage;

    private String uname;

    private ServiceRequestTable theRequest;

    @FXML
    private Button back;

    @FXML
    private Button fulfill;

    @FXML
    private TextField staffMember;

    @FXML
    private Label errorLabel;

    public void init(String username){
        uname = username;
    }

    @SuppressWarnings("Duplicates")

    @FXML
    /**@author Gabe
     * Returns user to the Logged In Home screen when the back button is pressed
     */
    private void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ActiveServiceRequests.fxml"));
        Parent roots = loader.load();

        //Get controller of scene2
        ActiveServiceRequestsController scene2Controller = loader.getController();
        scene2Controller.init(uname);

        Scene scene = new Scene(roots);
        Stage thestage = (Stage) errorLabel.getScene().getWindow();
        //Show scene 2 in new window
        thestage.setScene(scene);
    }

    @FXML
    public void getRequestID(ServiceRequestTable request) {
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
        }
        else{
            ServiceRequestAccess sa = new ServiceRequestAccess();
            sa.fulfillRequest(Integer.parseInt(theRequest.getRequestID()), staffMember.getText());
            errorLabel.setText("Request Fulfilled");

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ActiveServiceRequests.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            ActiveServiceRequestsController scene2Controller = loader.getController();
            scene2Controller.init(uname);

            Scene scene = new Scene(roots);
            Stage thestage = (Stage) errorLabel.getScene().getWindow();

            //Show scene 2 in new window
            thestage.setScene(scene);
            }
        }
    }
