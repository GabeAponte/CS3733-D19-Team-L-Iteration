package Controller;

import API.ChildThread;
import Access.ServiceRequestAccess;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class CancelController {
    private String typeOfService;
    private String comment;
    private boolean signedIn;
    private String uname;

    @FXML
    private Button Back;

    @FXML
    private Button yes;

    @FXML
    private Button no;

    @FXML
    public Label typeLabel;

    public void init(String service, String description, boolean loggedIn, String username){
        uname = username;
        init(service, description, loggedIn);
    }

    public void init(String service, boolean loggedIn, String username){
        uname = username;
        init(service, loggedIn);
    }

    //Nathan - stores information passed from another controller
    public void init(String service, boolean loggedIn){
        typeOfService = service;
        comment = "No comment added";
        signedIn = loggedIn;
    }

    //Nathan - stores information passed from another controller
    public void init(String service, String description, boolean loggedIn){
        typeOfService = service;
        comment = description;
        signedIn = loggedIn;
    }

    //Nathan - takes user back to Controller.ServiceSubController (and fills in proper info)
    @SuppressWarnings("Duplicates")
    @FXML
    private void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceSubController.fxml"));

        Parent sceneMain = loader.load();

        ServiceSubController controller = loader.<ServiceSubController>getController();
        if(signedIn) {
            if (comment == null || comment.equals("No comment added")) {
                controller.init(typeOfService, signedIn, uname);
            } else {
                controller.init(typeOfService, comment, signedIn, uname);
            }
        } else {
            if (comment == null || comment.equals("No comment added")) {
                controller.init(typeOfService, signedIn);
            } else {
                controller.init(typeOfService, comment, signedIn);
            }
        }
        Stage theStage = (Stage) yes.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    //Nathan - make a new service request and store it in the database, and sends email
    @FXML
    private void yesClicked() throws IOException, InterruptedException{
        ServiceRequestAccess sra = new ServiceRequestAccess();
        ChildThread ct = new ChildThread(typeOfService, comment);
        ct.start();
        sra.makeRequest(comment, typeOfService);
        noClicked();
    }

    //Nathan - return the user to the home screen or signed in screen
    @SuppressWarnings("Duplicates")
    @FXML
    private void noClicked() throws IOException {
        Stage theStage = (Stage) no.getScene().getWindow();
        AnchorPane root;
        if(signedIn){
            FXMLLoader sLoader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));

            Parent sceneMain = sLoader.load();

            LoggedInHomeController sController = sLoader.<LoggedInHomeController>getController();
            sController.init(uname);

            theStage = (Stage) yes.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);
            return;
        } else {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("HospitalHome.fxml"));
        }
        Scene scene = new Scene(root);
        theStage.setScene(scene);
    }
}
