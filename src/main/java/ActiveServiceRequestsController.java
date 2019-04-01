import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ActiveServiceRequestsController {

    private boolean signedIn;
    private Stage thestage;

    @FXML
    private Button back;

    @FXML
    private TableView activeRequests;

    @SuppressWarnings("Duplicates")

    void init(boolean loggedIn){
        signedIn = loggedIn;
    }

    @FXML
    private void backPressed() throws IOException {
        thestage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("LoggedInHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }
}
//TODO: Add database functionality to populate service request table and select one to go to fulfill screen
       /* @FXML
        private void SwitchToFulfillRequestScreen() throws IOException{
            Stage thestage = (Stage) placeholder.getScene().getWindow();
            AnchorPane root;
            root = FXMLLoader.load(getClass().getResource("LoggedInHome.fxml"));
            Scene scene = new Scene(root);
            thestage.setScene(scene);
        }
    } */
