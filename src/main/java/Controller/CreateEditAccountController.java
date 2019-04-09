package Controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import Object.*;

import java.io.IOException;

public class CreateEditAccountController {

    private Stage thestage;

    @FXML
    private Button back;

    @FXML
    private Button submit;

    @FXML
    private Label title;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField confrimPassword;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private TextField employeeID;

    @FXML
    private TextField nickname;

    @FXML
    private JFXComboBox department;

    @FXML
    private TextField position;

    @FXML
    private JFXRadioButton isAdmin;

    @FXML
    private Label errorLabel;

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
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        Parent sceneMain = loader.load();

                        Stage thisStage = (Stage) nickname.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
                        //System.out.println("Hey");
                        timeout.stop();
                    } catch (IOException io){
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));
        timeout.setCycleCount(Timeline.INDEFINITE);
        timeout.play();
    }

    @SuppressWarnings("Duplicates")
    @FXML
    private void backPressed() throws IOException {
        timeout.stop();
        thestage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getClassLoader().getResource("LoggedInHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);

        //TODO: Gray out submit button if not everything is filled in.
        // If admin users select create account on previous page, have the title label reflect that
        // If employee clicks on their account, have the title label reflect that
        // employeeID can't be change
        // Only admins can edit position, employee ID and isAdmin
        // email must contain @ and .domain
        // username must be unique
        // add error cases for the error label
        // have the new employee information be updated or created in the database when submit button is pressed
    }
}