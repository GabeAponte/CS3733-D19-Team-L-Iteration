import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    //TODO: Format Error Label
    @FXML
    private Label errorLabel;
    @SuppressWarnings("Duplicates")
    @FXML
    private void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ActiveServiceRequests.fxml"));
        Parent roots = loader.load();

        //Get controller of scene2
        ActiveServiceRequestsController scene2Controller = loader.getController();

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
    private void SwitchToAdminServiceRequestTable() throws IOException {
        if (staffMember.getText().trim().isEmpty() || staffMember.getText().equals("Staff Member")) {
            errorLabel.setText("Please enter a staff member name");

        }
        else{
            ServiceRequestAccess sa = new ServiceRequestAccess();
            sa.fulfillRequest(Integer.parseInt(theRequest.getRequestID()), staffMember.getText());
            errorLabel.setText("Request Fulfilled");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ActiveServiceRequests.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            ActiveServiceRequestsController scene2Controller = loader.getController();

            Scene scene = new Scene(roots);
            Stage thestage = (Stage) errorLabel.getScene().getWindow();
            //Show scene 2 in new window
            thestage.setScene(scene);
            }
        }
    }
