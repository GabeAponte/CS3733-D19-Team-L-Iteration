package Controller;

import Access.EmployeeAccess;
import Access.ServiceRequestAccess;
import Object.EmployeeTable;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import Object.*;

import java.io.IOException;
import java.util.ArrayList;

public class EmployeeTableController {

    private Stage thestage;

    @FXML
    private Button back;

    @FXML
    TableColumn<EmployeeTable,String> ID;

    @FXML
    TableColumn<EmployeeTable,String> firstName;

    @FXML
    TableColumn<EmployeeTable,String> lastName;

    @FXML
    TableColumn<EmployeeTable,String> position;

    @FXML
    TableColumn<EmployeeTable,String> department;

    @FXML
    TableColumn<EmployeeTable,String> isAdmin;


    @FXML
    TableView<EmployeeTable> employees;

    public void initialize(){
        employees.setEditable(false);
        EmployeeAccess ea = new EmployeeAccess();
        final ObservableList<EmployeeTable> data = FXCollections.observableArrayList();
        //initializeTable(ea);

        ID.setCellValueFactory(new PropertyValueFactory<EmployeeTable,String>("employeeID"));
        firstName.setCellValueFactory(new PropertyValueFactory<EmployeeTable, String>("firstName"));
        firstName.setCellValueFactory(new PropertyValueFactory<EmployeeTable, String>("lastName"));
        position.setCellValueFactory(new PropertyValueFactory<EmployeeTable, String>("lastName"));
        department.setCellValueFactory(new PropertyValueFactory<EmployeeTable, String>("type"));

        int count;
        count = ea.countRecords()-1;
        while(count >= 0){
            ArrayList<String> arr= ea.getRequests(count);
            EmployeeTable testx = new EmployeeTable(arr.get(0), arr.get(1),arr.get(2), arr.get(3), arr.get(4));
            count--;
            data.add(testx);
        }

        employees.setItems(data);
    }
    @FXML
    /**@author Gabe
     * Returns admin to the Admin Logged In Home screen when the back button is pressed
     */
    private void backPressed() throws IOException {
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

    /* @FXML
    private void SwitchToCreateEditAccount() throws IOException { }
        activeRequests.setOnMouseClicked(event -> {
            setNext(activeRequests.getSelectionModel().getSelectedItem());
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                try {
                    //Load second scene
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FulfillRequest.fxml"));
                    Parent roots = loader.load();
                    //Get controller of scene2
                    FulfillRequestController scene2Controller = loader.getController();
                    Scene scene = new Scene(roots);
                    scene2Controller.getRequestID(selectedRequest);
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
} */
}
