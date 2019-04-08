package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class FloristDeliveryController {
    @FXML
    private JFXTextField receiverName;

    @FXML
    private JFXTextField Location;

    @FXML
    private JFXTextField flowerName;

    @FXML
    private JFXTextArea comment;

    @FXML
    private JFXButton submit;

    @FXML
    private JFXButton backBtn;

    @FXML
    private void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequest.fxml"));

        Parent sceneMain = loader.load();
        Stage theStage = (Stage) backBtn.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

}
