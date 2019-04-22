package edu.wpi.cs3733.d19.teamL.Account;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.jfoenix.controls.*;
import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

@SuppressWarnings({"Duplicates", "FieldCanBeLocal", "AccessStaticViaInstance", "WeakerAccess", "ConstantConditions"})
public class CreateEditAccountController {

    private Stage thestage;

    @FXML
    private ImageView picView;

    @FXML
    private JFXButton picbtn;

    @FXML
    private Button back;

    @FXML
    private Button submit;

    @FXML
    private Label title;

    @FXML
    private TextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField confirmPassword;

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

    private static boolean clickedDelete = false;

    private Timeline timeout;

    public void initialize() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();

        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if ((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()) {
                    try {
                        single.setLastTime();
                        single.setDoPopup(true);
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        Memento m = single.getOrig();
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(m.getFxml()));

                        Parent sceneMain = loader.load();
                        HomeScreenController controller = loader.getController();
                        single.setLastTime();
                        controller.displayPopup();
                        single.setLastTime();

                        Stage thisStage = (Stage) firstName.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
                        timeout.stop();
                    } catch (IOException io) {
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

    @FXML
    private void takePic() {
        Webcam webcam;
        webcam = Webcam.getDefault();
        //THE VIEW SIZE WILL PROBABLY CHANGE DEPENDING ON THE COMPUTER
        //IMAGE COMPARISON WILL FAIL IMMEDIATELY IF SIZE CHANGES
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        WebcamPanel wp = new WebcamPanel(webcam);
        wp.setFPSDisplayed(true);
        wp.setDisplayDebugInfo(true);
        wp.setImageSizeDisplayed(true);
        wp.setMirrored(true);
        JFrame window = new JFrame("Hold still for 2.5 seconds");
        window.add(wp);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        try {
            sleep(2500);
        } catch (InterruptedException e) {
            System.out.println(e);
            System.out.println(e.getMessage());
        }
        wp.stop();
        webcam.close();
        window.dispose();

        webcam.open();
        try {
            BufferedImage image = webcam.getImage();
            ImageIO.write(image, "JPG", new File("TempOutput.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        webcam.close();

        try {
            Singleton single = Singleton.getInstance();
            EmployeeAccess ea = new EmployeeAccess();
            Image image = ImageIO.read(new File("TempOutput.jpg"));
            BufferedImage buffered = (BufferedImage) image;
            ea.updateEmployeeImg(single.getUsername(), buffered);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void backPressed(ActionEvent event) throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        Memento m = single.restore();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    /**
     * ANDREW MADE THIS
     * sets the type of the controller when dealing with editing or creating an account
     * type = 1 when creating, type = 2 when editing your employee account, type = 3 when admin editing any account
     *
     * @param check check
     * @param user  current user
     */
    public void setType(int check, String user) {
        Singleton single = Singleton.getInstance();
        type = check;
        if (type == 1) {
            title.setText("Create an Account");
            delete.setVisible(false);
            delete.setDisable(true);
        } else if (type == 2) {
            title.setText("Edit your Account");
            EmployeeAccess ea = new EmployeeAccess();
            ArrayList<String> data = ea.getEmployeeInformation(single.getUsername());
            username.setText(single.getUsername());
            employeeID.setText(data.get(0));
            department.getSelectionModel().select(data.get(1));
            if (data.get(2).equals("true")) {
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
            try {
                BufferedImage img1 = ea.getEmpImg(pusername);
                //File outputfile = new File("DBInput.jpg");
                //ImageIO.write(img1, "jpg", outputfile);
                if (img1 != null) {
                    picView.setImage(SwingFXUtils.toFXImage(img1, null));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (type == 3) {
            title.setText("Edit an Account");
            employeeID.setDisable(true);
            EmployeeAccess ea = new EmployeeAccess();
            pusername = ea.getEmployeeUsername(user);
            ArrayList<String> data = ea.getEmployeeInformation(pusername);
            username.setText(pusername);
            empID = data.get(0);
            employeeID.setText(data.get(0));
            department.getSelectionModel().select(data.get(1));
            if (data.get(2).equals("true")) {
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
            try {
                BufferedImage img1 = ea.getEmpImg(pusername);
                //File outputfile = new File("DBInput.jpg");
                //ImageIO.write(img1, "jpg", outputfile);
                if (img1 != null) {
                    picView.setImage(SwingFXUtils.toFXImage(img1, null));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!single.getUsername().equals(username.getText())) {
            picbtn.setDisable(true);
        }
    }


    /**
     * Andrew made this
     * deletes an employee
     *
     * @throws IOException throwsException
     */
    @FXML
    public void deleteClicked() throws IOException {
        timeout.pause();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Stage editStage = (Stage) delete.getScene().getWindow();
        Stage stage;
        Parent root;
        edu.wpi.cs3733.d19.teamL.Account.employeeID ID = new employeeID(empID);
        stage = new Stage();
        root = FXMLLoader.load(getClass().getClassLoader().getResource("DeleteEmployee.fxml"));
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(delete.getScene().getWindow());
        stage.setUserData(ID);
        clickedDelete = true;
        stage.setResizable(false);
        stage.showAndWait();

        if (clickedDelete) {
            AnchorPane root2;
            timeout.stop();
            Memento m = single.restore();
            root2 = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
            Scene scene2 = new Scene(root2);
            editStage.setScene(scene2);


        } else {
            single.setLastTime();
            timeout.play();
        }

    }

    @FXML
    public void yesPressed() {
        clickedDelete = true;
        Stage stage = (Stage) yes.getScene().getWindow();
        stage.close();
        deleteEmployee(stage);
    }

    @FXML
    public void noPressed() {
        clickedDelete = false;
        Stage stage = (Stage) no.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void deleteEmployee(Stage stage) {
        EmployeeAccess ea = new EmployeeAccess();
        ea.deleteEmployee(stage.getUserData().toString());

    }

    /**
     * ANDREW MADE THIS
     * helper method that validates an email address
     *
     * @param email employee email
     * @return boolean
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
        if (email.lastIndexOf('.') < email.lastIndexOf('@')) {
            result = false;
        }
        return result;
    }

    /**
     * ANDREW MADE THIS
     * disables submit button until all fields are entered
     */
    @FXML
    private void checkSubmit() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if (employeeID == null || employeeID.getText().trim().isEmpty()) {
            submit.setDisable(true);
            return;
        } else if (username == null || username.getText().trim().isEmpty()) {
            submit.setDisable(true);
            return;
        } else if (lastName == null || lastName.getText().trim().isEmpty()) {
            submit.setDisable(true);
            return;
        } else if (firstName == null || firstName.getText().trim().isEmpty()) {
            submit.setDisable(true);
            return;
        } else if (nickname == null || nickname.getText().trim().isEmpty()) {
            submit.setDisable(true);
            return;
        } else if (email == null || email.getText().trim().isEmpty()) {
            submit.setDisable(true);
            return;
        } else if (position == null || position.getText().trim().isEmpty()) {
            submit.setDisable(true);
            return;
        } else if (password == null || password.getText().trim().isEmpty()) {
            submit.setDisable(true);
            return;
        } else if (confirmPassword == null || confirmPassword.getText().trim().isEmpty()) {
            submit.setDisable(true);
            return;
        } else if (department.getValue() == null) {
            submit.setDisable(true);
            return;
        }

        submit.setDisable(false);
    }


    /**
     * ANDREW MADE THIS
     * submit button functionality - does a lot of error checking
     */
    @FXML
    private void submitPressed(ActionEvent event) throws IOException {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        EmployeeAccess ea = new EmployeeAccess();
        if (!isValidEmailAddress(email.getText())) {
            errorLabel.setText("Invalid Email Address");
            return;
        }
        if (!password.getText().equals(confirmPassword.getText())) {
            errorLabel.setText("Passwords do not match");
            return;
        }
        if (nickname.getText().length() > 8) {
            errorLabel.setText("The nickname is too long");
            return;
        }
        if (type == 1) {
            if (ea.checkFields(employeeID.getText(), username.getText())) {
                errorLabel.setText("The Username or Employee ID is taken");
                return;
            }
            ArrayList<String> list = new ArrayList<>();
            list.add(employeeID.getText());
            list.add(username.getText());
            list.add(password.getText());
            list.add(department.getValue());
            list.add(position.getText());
            list.add(firstName.getText());
            list.add(lastName.getText());
            list.add(nickname.getText());
            list.add(email.getText());
            errorLabel.setText("");
            ea.addEmployee(list);
            if (isAdmin.isSelected()) {
                ea.changeAdmin(employeeID.getText(), true);
            } else {
                ea.changeAdmin(employeeID.getText(), false);
            }
            backPressed(event);
        } else if (type == 2) {
            if (!pusername.equals(username.getText())) {
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
                ea.updateEmployee(employeeID.getText(), "department", department.getValue());
                ea.updateEmployee(employeeID.getText(), "type", position.getText());
                ea.updateEmployee(employeeID.getText(), "firstName", firstName.getText());
                ea.updateEmployee(employeeID.getText(), "lastName", lastName.getText());
                ea.updateEmployee(employeeID.getText(), "nickname", nickname.getText());
                ea.updateEmployee(employeeID.getText(), "email", email.getText());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            errorLabel.setText("");
            backPressed(event);
        } else {
            if (!pusername.equals(username.getText())) {
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
                ea.updateEmployee(employeeID.getText(), "department", department.getValue());
                ea.updateEmployee(employeeID.getText(), "type", position.getText());
                ea.updateEmployee(employeeID.getText(), "firstName", firstName.getText());
                ea.updateEmployee(employeeID.getText(), "lastName", lastName.getText());
                ea.updateEmployee(employeeID.getText(), "nickname", nickname.getText());
                ea.updateEmployee(employeeID.getText(), "email", email.getText());
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            errorLabel.setText("");
            timeout.stop();
            backPressed(event);
        }
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setUsername("");
        single.setIsAdmin(false);
        single.setLoggedIn(false);
        single.setDoPopup(true);
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        //saveState();
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }
}