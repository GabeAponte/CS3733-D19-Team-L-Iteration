import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Cancel {

    Stage thestage;
    private String typeOfService;
    private String comment;

    @FXML
    Button Back;

    @FXML
    private Button yes;

    @FXML
    private Button no;

    @FXML
    public Label typeLabel;

    public void init(String service){
        typeOfService = service;
        comment = "";
    }
    public void init(String service, String description){
        typeOfService = service;
        comment = description;
    }

    //takes user back to ServiceSubController (and fills in proper info)
    @SuppressWarnings("Duplicates")
    @FXML
    private void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServiceSubController.fxml"));

        Parent sceneMain = loader.load();

        ServiceSubController controller = loader.<ServiceSubController>getController();

        if(comment == null || comment == ""){
            controller.init(typeOfService);
        } else {
            controller.init(typeOfService, comment);
        }
        thestage = (Stage) yes.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        thestage.setScene(scene);
    }

    //Nathan - make a new service request and store it in the database
    @FXML
    private void yesClicked() throws IOException{
        noClicked();
    }

    //Nathan - return the user to the home screen
    @FXML
    private void noClicked() throws IOException {
        thestage = (Stage) no.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }
}
