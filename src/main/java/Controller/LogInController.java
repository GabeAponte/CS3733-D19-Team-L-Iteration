package Controller;

import Object.*;
import Access.EmployeeAccess;
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

import java.io.IOException;

public class LogInController {

    private Stage thestage;

    @FXML
    private Button back;

    @FXML
    private Button login;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

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
                        timeout.stop();
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        Parent sceneMain = loader.load();

                        Stage thisStage = (Stage) errorLabel.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
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
        root = FXMLLoader.load(getClass().getClassLoader().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    //grace
    //
    @FXML
    public void enterKeyPressToLogin(ActionEvent ae) throws IOException {
        LogIn();
    }

    @FXML
    private void enableLogin(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Boolean disable = (username.getText().isEmpty() || username.getText().trim().isEmpty() || password.getText().isEmpty() || password.getText().trim().isEmpty());
        if(!disable){
            login.setDisable(false);
        } else {
            login.setDisable(true);
        }
    }

    @FXML
    private void LogIn() throws IOException{
        String uname = username.getText();
        String pass = password.getText();

        boolean validLogin = false;
        Singleton single = Singleton.getInstance();
        single.setLastTime();

        EmployeeAccess ea = new EmployeeAccess();
        validLogin = ea.checkEmployee(uname, pass);
        if(validLogin){
            single.setLoggedIn(true);
            single.setUsername(uname);
            single.setIsAdmin(false);
            if(ea.getEmployeeInformation(uname).get(2).equals("true")){
                single.setIsAdmin(true);
            }
            SwitchToSignedIn();
        } else {
            displayError();
        }
    }

    private void SwitchToSignedIn() throws IOException{
        timeout.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));

        Parent sceneMain = loader.load();

        LoggedInHomeController controller = loader.<LoggedInHomeController>getController();
        Stage theStage = (Stage) login.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    private void displayError(){
        errorLabel.setText("Username or Password is incorrect");
    }



}
