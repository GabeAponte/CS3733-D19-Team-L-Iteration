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

import java.io.IOException;
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

    private boolean type;

    private boolean hasPrivelege;

    @SuppressWarnings("Duplicates")
    @FXML
    private void backPressed() throws IOException {
        thestage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getClassLoader().getResource("LoggedInHome.fxml"));
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

    public void setType(boolean check){
        type = check;
        if(check){
            title.setText("Create an Account");
        }else {
            title.setText("Edit an Account");
        }
    }

    public void initialize(){
        Singleton single = Singleton.getInstance();
        hasPrivelege = single.isIsAdmin();
        if(!hasPrivelege){
            position.setDisable(true);
            employeeID.setDisable(true);
            isAdmin.setDisable(true);
        }
    }

    private void submit(){
        EmployeeAccess ea = new EmployeeAccess();
        if(type){
            ArrayList<String> list = new ArrayList<String>();
            list.add(employeeID.getText());
            list.add(username.getText());
            list.add(password.getText());
            list.add(department.getValue().toString());
            list.add(isAdmin.getText());
            list.add(position.getText());
            list.add(firstName.getText());
            list.add(lastName.getText());
            list.add(nickname.getText());
            list.add(email.getText());
            ea.addEmployee(list);
        }else{
            
        }
    }
}