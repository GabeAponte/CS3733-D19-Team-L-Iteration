package Controller;

import Access.EmployeeAccess;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import Object.*;
import javafx.util.Duration;
import Object.*;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateEditAccountController {

    private Stage thestage;

    @FXML
    private Button back;

    @FXML
    private Button submit;

    @FXML
    private Label title;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField confrimPassword;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField email;

    @FXML
    private TextField employeeID;

    @FXML
    private TextField nickname;

    @FXML
    private JFXComboBox<String> department;

    @FXML
    private TextField position;

    @FXML
    private JFXRadioButton isAdmin;

    @FXML
    private Label errorLabel;

    @FXML
    private Button delete;

    @FXML
    private Button yes;

    @FXML
    private Button no;

    private String pusername;

    private String empID;

    private int type;

    private boolean hasPrivelege;

    private boolean onScreen;

    private static boolean clickedDelete = false;

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
                        single.setDoPopup(true);
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        Parent sceneMain = loader.load();
                        HomeScreenController controller = loader.<HomeScreenController>getController();
                        single.setLastTime();
                        controller.displayPopup();
                        single.setLastTime();

                        Stage thisStage = (Stage) firstName.getScene().getWindow();

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

        hasPrivelege = single.isIsAdmin();
        if (!hasPrivelege) {
            position.setDisable(true);
            employeeID.setDisable(true);
            isAdmin.setDisable(true);
            department.setDisable(true);
        }
        submit.setDisable(true);
        errorLabel.setText("");
        department.getItems().addAll("Sanitation", "Security", "IT", "Religious", "Audio Visual", "External Transportation", "Internal Transportation",
                "Language", "Maintenance", "Prescription", "Florist Delivery");
    }

    @SuppressWarnings("Duplicates")
    @FXML
    private void backPressed() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        thestage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        if(type == 1) {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("AdminLoggedInHome.fxml"));
        } else if(type == 2){
            root = FXMLLoader.load(getClass().getClassLoader().getResource("EmployeeLoggedInHome.fxml"));
        } else {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("EmployeeTable.fxml"));
        }
        Scene scene = new Scene(root);
        thestage.setScene(scene);

    }


    /**ANDREW MADE THIS
     * sets the type of the controller when dealing with editing or creating an account
     * type = 1 when creating, type = 2 when editing your employee account, type = 3 when admin editing any account
     * @param check
     */
    public void setType(int check, String user){
        type = check;
        if(type == 1){
            title.setText("Create an Account");
            delete.setVisible(false);
            delete.setDisable(true);
        }else if(type == 2){
            title.setText("Edit your Account");
            Singleton single = Singleton.getInstance();
            EmployeeAccess ea = new EmployeeAccess();
            ArrayList<String> data = ea.getEmployeeInformation(single.getUsername());
            username.setText(single.getUsername());
            employeeID.setText(data.get(0));
            department.getSelectionModel().select(data.get(1));
            if(data.get(2).equals("true")){
                isAdmin.setSelected(true);
            }
            nickname.setText(data.get(3));
            password.setText(data.get(4));
            position.setText(data.get(5));
            firstName.setText(data.get(6));
            lastName.setText(data.get(7));
            email.setText(data.get(8));
            pusername = single.getUsername();
            delete.setVisible(false);
            delete.setDisable(true);
        }else if(type == 3){
            title.setText("Edit an Account");
            employeeID.setDisable(true);
            EmployeeAccess ea = new EmployeeAccess();
            pusername = ea.getEmployeeUsername(user);
            ArrayList<String> data = ea.getEmployeeInformation(pusername);
            username.setText(pusername);
            empID = data.get(0);
            employeeID.setText(data.get(0));
            department.getSelectionModel().select(data.get(1));
            if(data.get(2).equals("true")){
                isAdmin.setSelected(true);
            }
            nickname.setText(data.get(3));
            password.setText(data.get(4));
            position.setText(data.get(5));
            firstName.setText(data.get(6));
            lastName.setText(data.get(7));
            email.setText(data.get(8));
            delete.setVisible(true);
            delete.setDisable(false);
        }
    }


    /**Andrew made this
     * deletes an employee
     * @throws IOException
     */
    @FXML
    public void deleteClicked() throws IOException{
        timeout.pause();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Stage editStage = (Stage) delete.getScene().getWindow();
        onScreen = false;
        Stage stage;
        Parent root;
        employeeID ID = new employeeID(empID);
        stage = new Stage();
        root = FXMLLoader.load(getClass().getClassLoader().getResource("DeleteEmployee.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(delete.getScene().getWindow());
        stage.setUserData(ID);
        clickedDelete = true;
        stage.showAndWait();

        if(clickedDelete) {
            AnchorPane root2;
            timeout.stop();
            root2 = FXMLLoader.load(getClass().getClassLoader().getResource("EmployeeTable.fxml"));
            Scene scene2 = new Scene(root2);
            editStage.setScene(scene2);
        } else {
            single.setLastTime();
            timeout.play();
        }

        /*EmployeeAccess ea = new EmployeeAccess();
        ea.deleteEmployee(empID);
        backPressed(); */
    }

    @FXML
    public void yesPressed() throws IOException{
        clickedDelete = true;
        Stage stage = (Stage) yes.getScene().getWindow();
        stage.close();
        deleteEmployee(stage);
    }

    @FXML
    public void noPressed() throws IOException{
        clickedDelete = false;
        Stage stage = (Stage) no.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void deleteEmployee(Stage stage) throws IOException {
        EmployeeAccess ea = new EmployeeAccess();
        ea.deleteEmployee(stage.getUserData().toString());

    }

    /**ANDREW MADE THIS
     * helper method that validates an email address
     * @param email
     * @return
     */
    public static boolean isValidEmailAddress(String email) {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        if(email.lastIndexOf('.') < email.lastIndexOf('@')){
            result = false;
        }
        return result;
    }

    /**ANDREW MADE THIS
     * disables submit button until all fields are entered
     */
    @FXML
    private void checkSubmit(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if(employeeID == null || employeeID.getText().trim().isEmpty()) {
            submit.setDisable(true);
            return;
        } else if(username == null || username.getText().trim().isEmpty()){
            submit.setDisable(true);
            return;
        } else if(lastName == null || lastName.getText().trim().isEmpty()){
            submit.setDisable(true);
            return;
        } else if(firstName == null || firstName.getText().trim().isEmpty()){
            submit.setDisable(true);
            return;
        } else if(nickname == null || nickname.getText().trim().isEmpty()){
            submit.setDisable(true);
            return;
        } else if(email == null || email.getText().trim().isEmpty()){
            submit.setDisable(true);
            return;
        } else if(position == null || position.getText().trim().isEmpty()){
            submit.setDisable(true);
            return;
        } else if(password == null || password.getText().trim().isEmpty()){
            submit.setDisable(true);
            return;
        } else if(confrimPassword == null || confrimPassword.getText().trim().isEmpty()){
            submit.setDisable(true);
            return;
        }else if(department.getValue() == null){
            submit.setDisable(true);
            return;
        }

        submit.setDisable(false);
    }


    /**ANDREW MADE THIS
     * submit button functionality - does a lot of error checking
     */
    @FXML
    private void submitPressed()throws IOException{
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        EmployeeAccess ea = new EmployeeAccess();
        if(!isValidEmailAddress(email.getText())){
            errorLabel.setText("Invalid Email Address");
            return;
        }
        if(!password.getText().equals(confrimPassword.getText())){
            errorLabel.setText("Passwords do not match");
            return;
        }
        if(nickname.getText().length() > 8){
            errorLabel.setText("The nickname is too long");
            return;
        }
        if(type == 1){
            if(ea.checkFields(employeeID.getText(), username.getText())){
                errorLabel.setText("The Username or Employee ID is taken");
                return;
            }
            ArrayList<String> list = new ArrayList<String>();
            list.add(employeeID.getText());
            list.add(username.getText());
            list.add(password.getText());
            list.add(department.getValue().toString());
            list.add(position.getText());
            list.add(firstName.getText());
            list.add(lastName.getText());
            list.add(nickname.getText());
            list.add(email.getText());
            errorLabel.setText("");
            ea.addEmployee(list);
            if(isAdmin.isSelected()) {
                ea.changeAdmin(employeeID.getText(), true);
            }else{
                ea.changeAdmin(employeeID.getText(), false);
            }
            backPressed();
        }else if(type == 2){
            if(!pusername.equals(username.getText())) {
                try {
                    ea.updateEmployee(employeeID.getText(), "username", username.getText());
                } catch (SQLException e) {
                    errorLabel.setText("The Username is already in use");
                    return;
                }
            }
            ea.changeAdmin(employeeID.getText(), isAdmin.isSelected());
            try {
                ea.updateEmployee(employeeID.getText(), "password", password.getText());
                ea.updateEmployee(employeeID.getText(), "department", department.getValue().toString());
                ea.updateEmployee(employeeID.getText(), "type", position.getText());
                ea.updateEmployee(employeeID.getText(), "firstName", firstName.getText());
                ea.updateEmployee(employeeID.getText(), "lastName", lastName.getText());
                ea.updateEmployee(employeeID.getText(), "nickname", nickname.getText());
                ea.updateEmployee(employeeID.getText(), "email", email.getText());
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
            errorLabel.setText("");
            backPressed();
        } else {
            if(!pusername.equals(username.getText())) {
                try {
                    ea.updateEmployee(employeeID.getText(), "username", username.getText());
                } catch (SQLException e) {
                    errorLabel.setText("The Username is already in use");
                    return;
                }
            }
            ea.changeAdmin(employeeID.getText(), isAdmin.isSelected());
            try {
                ea.updateEmployee(employeeID.getText(), "password", password.getText());
                ea.updateEmployee(employeeID.getText(), "department", department.getValue().toString());
                ea.updateEmployee(employeeID.getText(), "type", position.getText());
                ea.updateEmployee(employeeID.getText(), "firstName", firstName.getText());
                ea.updateEmployee(employeeID.getText(), "lastName", lastName.getText());
                ea.updateEmployee(employeeID.getText(), "nickname", nickname.getText());
                ea.updateEmployee(employeeID.getText(), "email", email.getText());
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
            errorLabel.setText("");
            timeout.stop();
            backPressed();
        }
    }
}