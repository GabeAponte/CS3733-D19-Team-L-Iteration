import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

    //TODO: Format Error Label
    @FXML
    private Label errorLabel;

    @SuppressWarnings("Duplicates")
    @FXML
    private void backPressed() throws IOException {
        thestage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    private void enableLogin(){
        Boolean disable = (username.getText().isEmpty() || username.getText().trim().isEmpty() || password.getText().isEmpty() || password.getText().trim().isEmpty());
        if(!disable){
            login.setDisable(false);
        } else {
            login.setDisable(true);
        }
    }

    @FXML
    private void LogIn(){
        String uname = username.getText();
        String pass = password.getText();

        Boolean validLogin = false;

        if(uname.equals("YO")){ validLogin = true; }
        //TODO: Query database

        if(validLogin){
            SwitchToSignedIn();
        } else {
            displayError();
        }
    }

    private void SwitchToSignedIn() {
        try {
            //TODO: Change BookRoom.fxml to signed in screen
            Stage thestage = (Stage) username.getScene().getWindow();
            AnchorPane root;
            root = FXMLLoader.load(getClass().getResource("BookRoom.fxml"));
            Scene scene = new Scene(root);
            thestage.setScene(scene);
        } catch (Exception e) {

        }
    }

    private void displayError(){
        errorLabel.setText("Username or Password is incorrect");
    }

}
