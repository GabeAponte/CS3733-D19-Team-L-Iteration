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
    private String uname;

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

    public void init(String username){
        uname = username;
    }
    @SuppressWarnings("Duplicates")
    @FXML
    private void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ActiveServiceRequests.fxml"));
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
        System.out.println(theRequest.getRequestID());
    }

    @FXML
    //TODO: Add database functionality to mark request as fulfilled and update table of requests
    private void SwitchToAdminServiceRequestTable() throws IOException {
        if (staffMember.getText().trim().isEmpty() || staffMember.getText().equals("Staff Member")) {
            errorLabel.setText("Please enter a staff member name");

        }
        else{
            ServiceRequestAccess sa = new ServiceRequestAccess();
            sa.fulfillRequest(Integer.parseInt(theRequest.getRequestID()), staffMember.getText());
            errorLabel.setText("Request Fulfilled");
            thestage = (Stage) back.getScene().getWindow();
            AnchorPane root;
            root = FXMLLoader.load(getClass().getResource("ActiveServiceRequests.fxml"));
            Scene scene = new Scene(root);
            thestage.setScene(scene);
            }
        }
    }
