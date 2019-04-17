package edu.wpi.cs3733.d19.teamL.HomeScreens;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.d19.teamL.API.FaceDetector;
import edu.wpi.cs3733.d19.teamL.API.ImageComparison;
import edu.wpi.cs3733.d19.teamL.API.faceDetectionJavaFXX;
import edu.wpi.cs3733.d19.teamL.Account.EmployeeAccess;
import edu.wpi.cs3733.d19.teamL.Singleton;
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
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;
import org.bytedeco.opencv.opencv_core.IplImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;
import static org.bytedeco.opencv.global.opencv_core.cvFlip;
import static org.bytedeco.opencv.helper.opencv_imgcodecs.cvSaveImage;

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

    @FXML
    private JFXButton facialRec;

    Timeline timeout;
    public void initialize(){
        //facialRec.setDisable(true);
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()){
                    try{
                        single.setLastTime();
                        single.setDoPopup(true);
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        Parent sceneMain = loader.load();

                        Stage thisStage = (Stage) username.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
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
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

        Parent sceneMain = loader.load();

        Stage thisStage = (Stage) username.getScene().getWindow();

        Scene newScene = new Scene(sceneMain);
        thisStage.setScene(newScene);
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

        /*if(username.getText().trim().isEmpty()){
            facialRec.setDisable(true);
        } else {
            facialRec.setDisable(false);
        }*/
        Boolean disable = (username.getText().isEmpty() || username.getText().trim().isEmpty() || password.getText().isEmpty() || password.getText().trim().isEmpty());
        if(!disable){
            login.setDisable(false);
        } else {
            login.setDisable(true);
        }
    }

    @FXML
    private void tryFR() throws IOException{
        Webcam webcam;
        webcam = Webcam.getDefault();
        //THE VIEW SIZE WILL PROBABLY CHANGE DEPENDING ON THE COMPUTER
        //IMAGE COMPARISON WILL FAIL IMMEDIATELY IF SIZE CHANGES
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        WebcamPanel wp = new WebcamPanel(webcam);
        wp.setFPSDisplayed(true);
        wp.setDisplayDebugInfo(true);
        wp.setImageSizeDisplayed(true);
        wp.setMirrored(true);
        JFrame window = new JFrame("Hold still for 5 seconds");
        window.add(wp);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        try {
            sleep(5000);
        } catch (InterruptedException e){
            System.out.println(e);
            System.out.println(e.getMessage());
        }
        wp.stop();
        webcam.close();
        window.dispose();

        webcam.open();
        BufferedImage image = webcam.getImage();
        ImageIO.write(image, "JPG", new File("TempOutput.jpg"));
        webcam.close();

        ImageComparison ic = new ImageComparison();
        //double diff = ic.doIT(username.getText());
        ArrayList<String> results = ic.doWithAll();
        double diff = Double.parseDouble(results.get(0));

        Singleton single = Singleton.getInstance();
        EmployeeAccess ea = new EmployeeAccess();
        if(diff < 10){
            single.setLoggedIn(true);
            single.setUsername(results.get(1));
            single.setIsAdmin(false);
            if(ea.getEmployeeInformation(results.get(1)).get(2).equals("true")){
                single.setIsAdmin(true);
                SwitchToSignedIn("AdminLoggedInHome.fxml");
                return;
            }
            SwitchToSignedIn("EmployeeLoggedInHome.fxml");
        } else {
            displayError();
        }//*/
        /*if(diff < 10){
            single.setLoggedIn(true);
            single.setUsername(results.get(1));
            single.setIsAdmin(false);
            if(ea.getEmployeeInformation(username.getText()).get(2).equals("true")){
                single.setIsAdmin(true);
                SwitchToSignedIn("AdminLoggedInHome.fxml");
                return;
            }
            SwitchToSignedIn("EmployeeLoggedInHome.fxml");
        } else {
            displayError();
        }//*/
    }

    @FXML
    private void LogIn() throws IOException{
        String uname = username.getText();
        String pass = password.getText();

        boolean validLogin;
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
                SwitchToSignedIn("AdminLoggedInHome.fxml");
                return;
            }
            SwitchToSignedIn("EmployeeLoggedInHome.fxml");
        } else {
            displayError();
        }
    }

    private void SwitchToSignedIn(String fxml) throws IOException{
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxml));

        Parent sceneMain = loader.load();

        Stage theStage = (Stage) login.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    private void displayError(){
        errorLabel.setText("Username or Password is incorrect");
    }



}
