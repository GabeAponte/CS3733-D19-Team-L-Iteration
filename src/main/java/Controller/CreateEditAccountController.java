package Controller;

import Access.EmployeeAccess;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Object.*;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
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

    private String pusername;

    private String empID;

    private int type;

    private boolean hasPrivelege;

    @SuppressWarnings("Duplicates")
    @FXML
    private void backPressed() throws IOException {
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

    /**ANDREW MADE THIS
     * initializer for the scene
     */
    public void initialize(){
        Singleton single = Singleton.getInstance();
        hasPrivelege = single.isIsAdmin();
        if(!hasPrivelege){
            position.setDisable(true);
            employeeID.setDisable(true);
            isAdmin.setDisable(true);
            department.setDisable(true);
        }
        submit.setDisable(true);
        errorLabel.setText("");
        department.getItems().addAll("Sanitation", "Security", "IT", "Religious", "Audio Visual", "External Transportation", "Internal Transportation",
                "Language", "Maintenance", "Prescription");
    }

    /**Andrew made this
     * deletes an employee
     * @throws IOException
     */
    @FXML
    public void deleteEmployee() throws IOException{
        EmployeeAccess ea = new EmployeeAccess();
        ea.deleteEmployee(empID);
        backPressed();
    }

    /**ANDREW MADE THIS
     * helper method that validates an email address
     * @param email
     * @return
     */
    public static boolean isValidEmailAddress(String email) {
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


        //System.out.println("nathan gay");

        submit.setDisable(false);
    }


    /**ANDREW MADE THIS
     * submit button functionality - does a lot of error checking
     */
    @FXML
    private void submitPressed()throws IOException{
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
            backPressed();
        }
    }
}