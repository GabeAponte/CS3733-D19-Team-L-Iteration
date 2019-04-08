package Controller;

import Access.PrescriptionRequestAccess;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PrescriptionServiceRequestControler {

    @FXML
    private Button submitButton;

    @FXML
    private Button backButton;

    @FXML
    private JFXTextField destinationField;

    @FXML
    private JFXTextField medicineTypeField;

    @FXML
    private JFXTextField deliveryTimeField;

    @FXML
    private JFXTextField amountField;

    @FXML
    private JFXTextArea commentsField;


    private boolean signedIn;
    private String uname;

    public void init(boolean loggedIn) {
        signedIn = loggedIn;
    }

    public void init(boolean loggedIn, String username) {
        uname = username;
        init(loggedIn);
    }

    public void initialize(){
        submitButton.setDisable(true);
    }

    @FXML
    private void reenableSubmit() {
        if (destinationField.getText().trim().isEmpty() || medicineTypeField.getText().trim().isEmpty() || deliveryTimeField.getText().trim().isEmpty() || amountField.getText().trim().isEmpty()) {
            submitButton.setDisable(true);
        } else {
            submitButton.setDisable(false);
        }
    }
    @FXML
    private void submitClicked() throws IOException {
        PrescriptionRequestAccess rra = new PrescriptionRequestAccess();
        rra.makeRequest(commentsField.getText(), medicineTypeField.getText(), destinationField.getText(), deliveryTimeField.getText(), amountField.getText());
        backPressed();
    }

    @FXML
    protected void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequest.fxml"));

        Parent sceneMain = loader.load();

        Stage theStage = (Stage) backButton.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

}
