package Controller;

import API.ChildThread;
import Access.ReligiousRequestAccess;
import Access.ServiceRequestAccess;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class ITController {

    @FXML
    private JFXComboBox<String> device;

    @FXML
    private JFXComboBox<String> problem;

    @FXML
    private JFXComboBox<String> floor;

    @FXML
    private JFXComboBox<String> loc;

    @FXML
    private JFXTextArea description;

    @FXML
    public Button back;

    @FXML
    private Button submit;

    private boolean signedIn;
    private String uname;

    //Specs
    //HDMI
    // Ethernet

    public void init(boolean loggedIn, String username) {
        uname = username;
        signedIn = loggedIn;
    }

    public void initialize(){
        submit.setDisable(true);
        device.getItems().addAll("Desktop Computer", "Laptop Computer", "Tablet", "Smartphone", "Kiosk", "Television");
    }

    @FXML
    private void submitRequest(){
        System.out.println("Submit pressed with this info");
    }

    @FXML
    private void deviceSelected(){
        if(device.getValue().equals("Desktop Computer")){
            problem.getItems().clear();
            problem.getItems().addAll("Computer not powering on", "Computer Frozen","Internet or network connectivity issues", "Strange noises from computer", "Malware/Virus related issue", "Display not powering on or working","WiFi Problems","Need HDMI", "Mouse/Keyboard needed");
        }
        else if(device.getValue().equals("Laptop Computer")){
            problem.getItems().clear();
            problem.getItems().addAll("Laptop not powering", "Laptop Frozen", "Display not powering on or working","WiFi Problems","Need HDMI");
        }
        else if(device.getValue().equals("Tablet")){
            problem.getItems().clear();
            problem.getItems().addAll("Tablet not powering", "Tablet Frozen", "Screen cracked","WiFi Problems","Need HDMI");
        }
        else if(device.getValue().equals("Smartphone")){
            problem.getItems().clear();
            problem.getItems().addAll("Phone not powering on", "Phone Frozen", "Display cracked", "Display cracked","WiFi Problems","Need HDMI");
        }
        else if(device.getValue().equals("Kiosk")){
            problem.getItems().clear();
            problem.getItems().addAll("Kiosk powered off", "Kiosk touch screen not working", "Display not powering on or working","WiFi Problems","Need HDMI");
        }
        else if(device.getValue().equals("Television")){
            problem.getItems().clear();
            problem.getItems().addAll("Computer not powering", "Computer Frozen", "Display not powering on or working","WiFi Problems","Need HDMI");
        }
    }

    @FXML
    protected void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequest.fxml"));

        Parent sceneMain = loader.load();

        Stage theStage = (Stage) back.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

}
