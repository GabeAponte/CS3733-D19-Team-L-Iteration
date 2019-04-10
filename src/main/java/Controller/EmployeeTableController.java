package Controller;

import Access.DBAccess;
import Access.EmployeeAccess;
import Access.ServiceRequestAccess;
import Object.EmployeeTable;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import Object.*;
import javafx.util.Duration;
import Object.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

public class EmployeeTableController{

    private Stage thestage;
    TreeItem root = new TreeItem<>("rootxxx");

    @FXML
    private Button back;

    @FXML
    TreeTableColumn<EmployeeTable,String> ID;

    @FXML
    TreeTableColumn<EmployeeTable,String> firstName;

    @FXML
    TreeTableColumn<EmployeeTable,String> lastName;

    @FXML
    TreeTableColumn<EmployeeTable,String> position;

    @FXML
    TreeTableColumn<EmployeeTable,String> department;

    @FXML
    TreeTableColumn<EmployeeTable,String> isAdmin;

    @FXML
    TreeTableView<EmployeeTable> employees;

    private TreeItem<EmployeeTable> selectedEmployee;

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
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        Parent sceneMain = loader.load();

                        Stage thisStage = (Stage) employees.getScene().getWindow();

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

        employees.setEditable(false);
        EmployeeAccess ea = new EmployeeAccess();

        int count;
        count = ea.countRecords()-1;
        while(count >= 0){
            TreeItem<EmployeeTable> arr= ea.getRequests(count);
            root.getChildren().add(arr);
            count--;
        }

        ID.setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue()instanceof EmployeeTable) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getID());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        firstName.setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue()instanceof EmployeeTable) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getFirstName());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        lastName.setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue()instanceof EmployeeTable) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getLastName());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        position.setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue()instanceof EmployeeTable) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getType());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        department.setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue()instanceof EmployeeTable) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getDepartment());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        employees.setTreeColumn(ID);
        employees.setRoot(root);
        employees.setShowRoot(false);
    }
    @FXML
    /**@author Gabe
     * Returns admin to the Admin Logged In Home screen when the back button is pressed
     */
    private void backPressed() throws IOException {
        timeout.stop();
        thestage = (Stage) back.getScene().getWindow();
        AnchorPane root;

        root = FXMLLoader.load(getClass().getClassLoader().getResource("AdminLoggedInHome.fxml"));

        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    /**
     * @author Gabe
     * Populates the table on the screen with any employees in the database
     */

    /*public void initializeTable(EmployeeAccess ea) {
        ArrayList<ArrayList<String>>  ls = ea.getEmployees("", "");
    }


    /**@author Gabe
     * When double clicking on a row in the table, admin is sent to the create/edit screen
     * and all the employee information from the clicked on row is passed along so that the edit account
     * fields are populated and the admin can make any changes
     */
    public void setNext(TreeItem<EmployeeTable> employee) {
        this.selectedEmployee = employee;
    }


    @FXML
    private void SwitchToCreateEditAccount() throws IOException {
        employees.setOnMouseClicked(event -> {
            setNext(employees.getSelectionModel().getSelectedItem());
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                try {
                    Singleton single = Singleton.getInstance();
                    single.setLastTime();
                    timeout.stop();
                    //Load second scene
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("CreateEditAccount.fxml"));
                    Parent roots = loader.load();
                    //Get controller of scene2
                    CreateEditAccountController scene2Controller = loader.getController();
                    Scene scene = new Scene(roots);
                    scene2Controller.setType(3, selectedEmployee.getValue().getID());
                    thestage = (Stage) back.getScene().getWindow();
                    //Show scene 2 in new window
                    thestage.setScene(scene);

                } catch (IOException ex) {
                    //noinspection ThrowablePrintedToSystemOut
                    System.err.println(ex);
                }

            }
        });
    }
}

