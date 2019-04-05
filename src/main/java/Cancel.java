import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class Cancel {
    private String typeOfService;
    private String comment;

    @FXML
    private Button Back;

    @FXML
    private Button yes;

    @FXML
    private Button no;

    @FXML
    public Label typeLabel;

    //Nathan - stores information passed from another controller
    public void init(String service, String description){
        typeOfService = service;
        comment = description;
    }

    //Nathan - takes user back to ServiceSubController (and fills in proper info)
    @SuppressWarnings("Duplicates")
    @FXML
    private void backPressed() throws IOException {
        Singleton single = Singleton.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServiceSubController.fxml"));

        Parent sceneMain = loader.load();

        ServiceSubController controller = loader.<ServiceSubController>getController();

        controller.init(typeOfService, comment);

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
        Singleton single = Singleton.getInstance();
        Stage theStage = (Stage) no.getScene().getWindow();
        AnchorPane root;
        if(single.isLoggedIn()){
            FXMLLoader sLoader = new FXMLLoader(getClass().getResource("LoggedInHome.fxml"));

            Parent sceneMain = sLoader.load();

            LoggedInHomeController sController = sLoader.<LoggedInHomeController>getController();

            theStage = (Stage) yes.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);
            return;
        } else {
            root = FXMLLoader.load(getClass().getResource("HospitalHome.fxml"));
        }
        Scene scene = new Scene(root);
        theStage.setScene(scene);
    }
}
