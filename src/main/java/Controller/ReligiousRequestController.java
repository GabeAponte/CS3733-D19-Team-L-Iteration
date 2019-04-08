package Controller;

import API.ChildThread;
import Access.ReligiousRequestAccess;
import Access.ServiceRequestAccess;
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

public class ReligiousRequestController {
    private boolean signedIn;
    private String uname;


    @FXML
    public Button Back;

    @FXML
    public Button Submit;

    @FXML
    public JFXTextField Name;

    @FXML
    public JFXTextField Location;

    @FXML
    public JFXTextField Denomination;

    @FXML
    public JFXTextField Type;

    @FXML
    public JFXTextArea Description;

    public void init(boolean loggedIn) {
        signedIn = loggedIn;
    }

    public void init(boolean loggedIn, String username) {
        uname = username;
        init(loggedIn);
    }

    @FXML
    private void submitClicked() throws IOException {
        ReligiousRequestAccess rra = new ReligiousRequestAccess();
        rra.makeRequest(Description.getText(), Denomination.getText(),Location.getText(), Name.getText(), Type.getText());
        System.out.println("Submit Pressed");
        backPressed();
    }

    @FXML
    protected void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequest.fxml"));

        Parent sceneMain = loader.load();

        Stage theStage = (Stage) Back.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }
}