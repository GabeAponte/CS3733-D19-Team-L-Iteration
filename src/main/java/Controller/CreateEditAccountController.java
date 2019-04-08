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
    private JFXComboBox department;

    @FXML
    private TextField position;

    @FXML
    private JFXRadioButton isAdmin;

    @FXML
    private Label errorLabel;

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
            root = FXMLLoader.load(getClass().getClassLoader().getResource("LoggedInHome.fxml"));
        } else {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("EmployeeTable.fxml"));
        }
        Scene scene = new Scene(root);
        thestage.setScene(scene);

        //TODO: Gray out submit button if not everything is filled in.
        // If admin users select create account on previous page, have the title label reflect that
        // If employee clicks on their account, have the title label reflect that
        // employeeID can't be change
        // Only admins can edit position, employee ID and isAdmin
        // email must contain @ and .domain
        // username must be unique
        // add error cases for the error label
        // have the new employee information be updated or created in the database when submit button is pressed
    }


    /**ANDREW MADE THIS
     * sets the type of the controller when dealing with editing or creating an account
     * type = 1 when creating, type = 2 when editing your employee account, type = 3 when admin editing any account
     * @param check
     */
    public void setType(int check){
        type = check;
        if(type == 1){
            title.setText("Create an Account");
        }else if(type == 2){
            title.setText("Edit your Account");
        }else if(type == 3){
            title.setText("Edit an Account");
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
        }
        submit.setDisable(true);
        errorLabel.setText("");
        department.getItems().addAll("Sanitation", "Security", "IT");
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
    private void submit(){
        EmployeeAccess ea = new EmployeeAccess();
        if(!isValidEmailAddress(email.getText())){
            errorLabel.setText("Invalid Email Address");
            return;
        }
        if(!password.getText().equals(confrimPassword.getText())){
            errorLabel.setText("Passwords do not match");
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
            if(isAdmin.isSelected()) {
                list.add("true");
            }else{
                list.add("false");
            }
            list.add(position.getText());
            list.add(firstName.getText());
            list.add(lastName.getText());
            list.add(nickname.getText());
            list.add(email.getText());
            ea.addEmployee(list);
        }else{
            try{
                ea.updateEmployee(employeeID.getText(), "employeeID", employeeID.getText());
                ea.updateEmployee(employeeID.getText(), "username", username.getText());
            } catch(SQLException e){
                errorLabel.setText("The employee ID or Username are already in use");
                return;
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
        }
    }
}