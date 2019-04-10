package Controller;

import API.ChildThread;
import Access.NodesAccess;
import Access.ReligiousRequestAccess;
import Access.ServiceRequestAccess;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.scene.NodeHelper;
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
import java.util.ArrayList;


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
        NodesAccess na = new NodesAccess();
        submit.setDisable(true);
        device.getItems().addAll("Desktop Computer", "Laptop Computer", "Tablet", "Smartphone", "Kiosk", "Television");
        floor.getItems().addAll("L1", "L2", "G", "1", "2", "3");
        ArrayList<String> theRooms = na.getValidITLocations("2");
        for(int i = 0; i < theRooms.size(); i++){
            loc.getItems().add(theRooms.get(i));
        }
    }

    @FXML
    private void submitRequest(){
        System.out.println("Submit pressed with this info");
    }

    @FXML
    private void deviceSelected(){
        if(device.getValue().equals("Desktop Computer")){
            problem.getItems().clear();
            problem.getItems().addAll("Computer not powering on","Computer running slowly", "Computer frozen","Internet or network connectivity issues", "Strange noises from computer", "Malware/Virus related issue", "Display not powering on or working","Need HDMI", "Need ethernet cord", "Mouse/Keyboard needed", "Other");
        }
        else if(device.getValue().equals("Laptop Computer")){
            problem.getItems().clear();
            problem.getItems().addAll("Laptop not powering on", "Laptop running slowly", "Laptop frozen", "Internet or network connectivity issues", "Strange noises from laptop","Malware/Virus related issue", "Display not powering on or working","Need HDMI", "Need ethernet cord", "Laptop overheating", "Trackpad malfunctioning", "Keyboard malfunctioning", "Other");
        }
        else if(device.getValue().equals("Tablet")){
            problem.getItems().clear();
            problem.getItems().addAll("Tablet not powering","Tablet running slowly","Tablet frozen", "Internet or network connectivity issues","Screen cracked", "Display not working", "Touch screen not working", "Power/Home button not working", "Tablet not charging", "Tablet overheating" , "Other");
        }
        else if(device.getValue().equals("Smartphone")){
            problem.getItems().clear();
            problem.getItems().addAll("Phone not powering on", "Phone running slowly", "Phone Frozen", "Internet or network connectivity", "Cellular network connection issues", "Screen cracked", "Display not working","Touch screen not working", "Power/Home button not working", "Phone not charging", "Phone overheating", "Other");
        }
        else if(device.getValue().equals("Kiosk")){
            problem.getItems().clear();
            problem.getItems().addAll("Kiosk not powering on", "Kiosk running", "Kiosk frozen", "Kiosk touch screen not working", "Display not powering on or working","Keyboard/Mouse malfunctioning", "Strange noises coming from kiosk", "Other");
        }
        else if(device.getValue().equals("Television")){
            problem.getItems().clear();
            problem.getItems().addAll("TV not powering", "TV Frozen", "Display not powering on or working","Internet or network connectivity issues","Need HDMI", "Need coaxial cable", "Remote out of batteries", "Remote not working", "Picture quality poor", "Cable box not working", "Other");
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
