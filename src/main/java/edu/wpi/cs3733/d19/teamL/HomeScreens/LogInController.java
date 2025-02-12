package edu.wpi.cs3733.d19.teamL.HomeScreens;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.d19.teamL.API.ImageComparison;
import edu.wpi.cs3733.d19.teamL.Account.EmployeeAccess;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
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

    @FXML
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

    @FXML
    public void initialize() {
        //facialRec.setDisable(true);
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if ((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()) {
                    try {
                        single.setLastTime();
                        single.setDoPopup(true);
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        Parent sceneMain = loader.load();

                        Stage thisStage = (Stage) username.getScene().getWindow();
                        Screen screen = Screen.getPrimary();
                        Rectangle2D bounds = screen.getVisualBounds();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setMaximized(true);
                        thisStage.setScene(newScene);
                        thisStage.setX(bounds.getMinX());
                        thisStage.setY(bounds.getMinY());
                        thisStage.setWidth(bounds.getWidth());
                        thisStage.setHeight(bounds.getHeight());
                        timeout.stop();
                    } catch (IOException io) {
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
    private void backPressed(ActionEvent event) throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        Memento m = single.restore();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    //grace
    //
    @FXML
    public void enterKeyPressToLogin(ActionEvent event) throws IOException {
        LogIn(event);
    }

    @FXML
    private void enableLogin() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();

        Boolean disable = (username.getText().isEmpty() || username.getText().trim().isEmpty() || password.getText().isEmpty() || password.getText().trim().isEmpty());
        if (!disable) {
            login.setDisable(false);
        } else {
            login.setDisable(true);
        }
    }

    @FXML
    private void tryFR(ActionEvent event) {
        try {
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
            JFrame window = new JFrame("Hold still for 2.5 seconds");
            window.add(wp);
            window.setResizable(true);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);
            try {
                sleep(2500);
            } catch (InterruptedException e) {
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
            if (diff < 10) {
                single.setLoggedIn(true);
                single.setUsername(results.get(1));
                System.out.println(results.get(1));
                single.setIsAdmin(false);
                if (ea.getEmployeeInformation(results.get(1)).get(2).equals("true")) {
                    single.setIsAdmin(true);
                    SwitchToSignedIn(event, "AdminLoggedInHome.fxml");
                    return;
                }
                SwitchToSignedIn(event,"EmployeeLoggedInHome.fxml");
            } else {
                displayError();
            }//*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void LogIn(ActionEvent event) throws IOException {
        String uname = username.getText();
        String pass = password.getText();

        boolean validLogin;
        Singleton single = Singleton.getInstance();
        single.setLastTime();

        EmployeeAccess ea = new EmployeeAccess();
        validLogin = ea.checkEmployee(uname, pass);
        if (validLogin) {
            single.setLoggedIn(true);
            single.setUsername(uname);
            single.setIsAdmin(false);
            if (ea.getEmployeeInformation(uname).get(2).equals("true")) {
                single.setIsAdmin(true);
                SwitchToSignedIn(event, "AdminLoggedInHome.fxml");
                return;
            }
            SwitchToSignedIn(event,"EmployeeLoggedInHome.fxml");
        } else {
            displayError();
        }
    }

    private void SwitchToSignedIn(ActionEvent event, String fxml) throws IOException {
        timeout.stop();
        saveState();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(fxml));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    private void displayError() {
        errorLabel.setText("Username or Password is incorrect");
    }

    /**
     * @author Nathan
     * Saves the memento state
     */
    private void saveState() {
        Singleton single = Singleton.getInstance();
        single.saveMemento("LogIn.fxml");
    }

    @FXML
    private void logOut() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setUsername("");
        single.setIsAdmin(false);
        single.setLoggedIn(false);
        single.setDoPopup(true);
        thestage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        Memento m = single.getOrig();
        root = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        saveState();
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }
}