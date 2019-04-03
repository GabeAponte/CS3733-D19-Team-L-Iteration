import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {

    private Stage thestage;
    public String uname;

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

    //grace
    //
    @FXML
    public void enterKeyPressToLogin(ActionEvent ae) throws IOException {
        LogIn();
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
    private void LogIn() throws IOException{
        uname = username.getText();
        String pass = password.getText();

        boolean validLogin = false;
        
        EmployeeAccess ea = new EmployeeAccess();
        validLogin = ea.checkEmployee(uname, pass);
        if(validLogin){
            SwitchToSignedIn(uname);
        } else {
            displayError();
        }
    }

    private void SwitchToSignedIn(String un) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoggedInHome.fxml"));

        Parent sceneMain = loader.load();

        LoggedInHomeController controller = loader.<LoggedInHomeController>getController();
        controller.init(un);

        Stage theStage = (Stage) login.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    private void displayError(){
        errorLabel.setText("Username or Password is incorrect");
    }



}
