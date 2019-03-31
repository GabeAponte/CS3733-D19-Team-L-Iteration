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
import java.util.*;
//TODO: Uncomment the following import statements

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;


public class Cancel {

    private Stage theStage;
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

    //Nathan - stores information passed from another controller
    void init(String service){
        typeOfService = service;
        comment = "";
    }

    //Nathan - stores information passed from another controller
    void init(String service, String description){
        typeOfService = service;
        comment = description;
    }

    //Nathan - takes user back to ServiceSubController (and fills in proper info)
    @SuppressWarnings("Duplicates")
    @FXML
    private void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServiceSubController.fxml"));

        Parent sceneMain = loader.load();

        ServiceSubController controller = loader.<ServiceSubController>getController();

        if(comment == null || comment.equals("")){
            controller.init(typeOfService);
        } else {
            controller.init(typeOfService, comment);
        }
        theStage = (Stage) yes.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    //Nathan - make a new service request and store it in the database, and sends email
    //TODO: Store request in Database
    @FXML
    private void yesClicked() throws IOException, InterruptedException{
        ChildThread ct = new ChildThread(typeOfService, comment);
        ct.start();

        noClicked();
    }

    //Nathan - return the user to the home screen
    @SuppressWarnings("Duplicates")
    @FXML
    private void noClicked() throws IOException {
        theStage = (Stage) no.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        theStage.setScene(scene);
    }
}
