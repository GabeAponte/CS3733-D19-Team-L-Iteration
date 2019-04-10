package Controller;

import Access.MaintenanceRequestAccess;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Object.*;

import java.io.IOException;

public class MaintenanceServiceRequestController {

    @FXML
    JFXTextField field1;

    @FXML
    JFXTextField field2;

    @FXML
    JFXTextField Location;

    @FXML
    RadioButton hazardYes;

    @FXML
    RadioButton hazardNo;

    @FXML
    public Button Back;


    public void initialize() {
        Singleton single = Singleton.getInstance();
    }

    @FXML
    protected void backPressed() throws IOException {
        Singleton single = Singleton.getInstance();
        Stage theStage = (Stage) Back.getScene().getWindow();
        AnchorPane root;
        if(single.isLoggedIn()){
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequest.fxml"));

            Parent sceneMain = loader.load();

            LoggedInHomeController controller = loader.<LoggedInHomeController>getController();

            theStage = (Stage) Back.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);
            return;
        } else {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("HospitalHome.fxml"));
        }
        Scene scene = new Scene(root);
        theStage.setScene(scene);
    }

    @FXML
    protected void submitPressed() throws IOException {
        MaintenanceRequestAccess mra = new MaintenanceRequestAccess();
        mra.makeRequest(field1.getText(), field2.getText(), Location.getText(),  );
    }
}
