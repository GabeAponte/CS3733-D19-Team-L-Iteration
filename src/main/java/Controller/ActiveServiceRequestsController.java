package Controller;

import Access.ReligiousRequestAccess;
import Access.ServiceRequestAccess;
import Object.ServiceRequestTable;

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

import java.io.IOException;
import java.util.ArrayList;

public class ActiveServiceRequestsController {

    private Stage thestage;
    private ServiceRequestTable selectedRequest;

    @FXML
    private Button back;

    @FXML
    private ComboBox filter;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> dateRequested;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> timeRequested;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> type;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> name;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> assignedEmployee;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> comment;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> field1;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> field2;

    @FXML
    TreeTableColumn<ServiceRequestTable, String> field3;


    @FXML
    TreeTableColumn<ServiceRequestTable, String> hi;

    @FXML
    private TreeTableView<ServiceRequestTable> activeRequests;

    @FXML
    /**@author Gabe
     * Returns user to the Logged In Home screen when the back button is pressed
     */
    private void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));
        Parent roots = loader.load();

        //Get controller of scene2
        LoggedInHomeController scene2Controller = loader.getController();

        Scene scene = new Scene(roots);
        Stage thestage = (Stage) activeRequests.getScene().getWindow();

        //Show scene 2 in new window
        thestage.setScene(scene);
    }
    @FXML
    public void initialize() {
        filter.getItems().addAll(
                "Religious", "Internal Transportation", "Audio/Visual",
                "External Transportation", "Florist Delivery", "Internal Transportation", "IT",
                "Language Assistance", "Maintenance", "Prescriptions", "Sanitation", "Security");
        filterTable();
        activeRequests.getColumns().clear();

    }
    public void filter(){

    }


    //Gabe - Populates table
    public void filterTable() {
        /**@author Gabe
         * Populates the table on the screen with any active service rquests in the database
         */
        activeRequests.setEditable(false);
        if (filter.getValue() == "Religious") {
            field1.setText("Denomination");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(name);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(type);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);

            final ObservableList<ServiceRequestTable> data = FXCollections.observableArrayList();
            ReligiousRequestAccess rr = new ReligiousRequestAccess();

            int count;
            count = rr.countRecords()-1;
            while(count >= 0){
                ArrayList<String> arr= rr.getReligiousRequests(count);
                ServiceRequestTable testx = new ServiceRequestTable(arr.get(0), arr.get(1),arr.get(6), arr.get(2), arr.get(4),arr.get(5), arr.get(10), arr.get(11),arr.get(3), arr.get(8),arr.get(7), arr.get(9));
                count--;
                data.add(testx);
            }

            timeRequested.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable,String>("creationTime"));
            dateRequested.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable, String>("creationDate"));
            name.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable, String>("name"));
            field1.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable,String>("denomination"));
            type.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable, String>("type"));
            hi.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable, String>("location"));
            comment.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable,String>("comment"));
            assignedEmployee.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable, String>("assignedEmployee"));
            activeRequests.setItems(data);
        }

        if (filter.getValue() == "Internal Transportation") {
            field1.setText("Start Location");
            field2.setText("End Location");
            field3.setText("Phone Number");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(field2);
            activeRequests.getColumns().add(type);
            activeRequests.getColumns().add(field3);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
        }

        if (filter.getValue() == "Audio/Visual") {
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(type);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
        }
        if (filter.getValue() == "External Transportation") {
            field1.setText("Start Location");
            field2.setText("End Location");
            field3.setText("Phone Number");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(field2);
            activeRequests.getColumns().add(type);
            activeRequests.getColumns().add(field3);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
        }
        if (filter.getValue() == "Florist Delivery") {
            field1.setText("Flower");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(name);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
        }
        if (filter.getValue() == "IT") {
            field1.setText("Device");
            field2.setText("Problem");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(field2);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
        }
        if (filter.getValue() == "Language Assistance") {
            field1.setText("Language");
            field2.setText("Their Proficiency");
            field3.setText("Interpreters Needed");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(field2);
            activeRequests.getColumns().add(field3);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
        }
        if (filter.getValue() == "Maintenance") {
            field1.setText("Hazardous?");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(type);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
        }
        if (filter.getValue() == "Prescriptions") {
            field1.setText("Medicine");
            field2.setText("Amount");
            field3.setText("Delivery Time");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(field2);
            activeRequests.getColumns().add(field3);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
        }
        if (filter.getValue() == "Sanitation") {
            field1.setText("Urgency");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(type);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
        }
        if (filter.getValue() == "Security") {
            field1.setText("Identifier");
            activeRequests.getColumns().clear();
            activeRequests.getColumns().add(timeRequested);
            activeRequests.getColumns().add(dateRequested);
            activeRequests.getColumns().add(hi);
            activeRequests.getColumns().add(type);
            activeRequests.getColumns().add(field1);
            activeRequests.getColumns().add(comment);
            activeRequests.getColumns().add(assignedEmployee);
        }

        }



    public void setNext(ServiceRequestTable request){
        this.selectedRequest = request;
    }

    //Gabe - Switches to fulfill screen when mouse double clicks a row in the table
    //TODO: Bring over request information so that fulfill page can update a request
    //@FXML
    /**@author Gabe, DJ
     * When double clicking on a row in the table, user is sent to the fulfill request screen
     * and all the information from that row is passed along so that a user can update it
     */
   /* private void SwitchToFulfillRequestScreen() throws IOException {
        activeRequests.setOnMouseClicked(event -> {
         //   setNext(activeRequests.getSelectionModel().getSelectedItem());
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
}
    */
    }
