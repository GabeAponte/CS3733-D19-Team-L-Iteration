import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ActiveServiceRequestsController {

    private boolean signedIn;
    private Stage thestage;

    @FXML
    private Button back;

    @FXML
    TableColumn<ServiceRequestTable,String> requestDepartment;

    @FXML
    TableColumn<ServiceRequestTable, String> assignedEmployee;

    @FXML
    TableColumn<ServiceRequestTable, String> fullfilled;

    @FXML
    TableColumn<ServiceRequestTable, String> comment;

    @FXML
    private TableView activeRequests;

    @SuppressWarnings("Duplicates")

    void init(boolean loggedIn){
        signedIn = loggedIn;
    }

    @FXML
    private void backPressed() throws IOException {
        thestage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("LoggedInHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }


    public void initialize(){
        activeRequests.setEditable(false);
       // this.makeEditable.setDisable(true);
        final ObservableList<ServiceRequestTable> data = FXCollections.observableArrayList();
        ServiceRequestAccess sr = new ServiceRequestAccess();

        int count;
        count  = 0;
        System.out.println(sr.countRecords());
        while(count < sr.countRecords()){
            ArrayList<String> arr= sr.getRequests(count);
            ServiceRequestTable testx = new ServiceRequestTable(arr.get(0), arr.get(1),arr.get(2), arr.get(3), arr.get(4));
            count++;
            data.add(testx);
        }


        requestDepartment.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable,String>("requestDepartment"));
        comment.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable, String>("comment"));
        assignedEmployee.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable, String>("assignedEmployee"));
        fullfilled.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable, String>("fullfilled"));
        //noinspection CodeBlock2Expr
        activeRequests.setItems(data);
    }

    //TODO: Add database functionality to populate service request table and select one to go to fulfill screen
    @FXML
    private void SwitchToFulfillRequestScreen() throws IOException {

            activeRequests.setOnMouseClicked(event -> {
                if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {

                Stage thestage = (Stage) activeRequests.getScene().getWindow();
                AnchorPane root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("FulfillRequest.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root);
                thestage.setScene(scene);
            }
        });
    }
}

