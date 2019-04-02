import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    private ServiceRequestTable selectedRequest;

    @FXML
    private Button back;

    @FXML
    TableColumn<ServiceRequestTable,String> requestDepartment;

    @FXML
    TableColumn<ServiceRequestTable, String> assignedEmployee;

    //@FXML
    //TableColumn<ServiceRequestTable, String> fullfilled;

    @FXML
    TableColumn<ServiceRequestTable, String> comment;

    @FXML
    private TableView<ServiceRequestTable> activeRequests;

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

    //Gabe - Populates table
    public void initialize(){
        activeRequests.setEditable(false);
        final ObservableList<ServiceRequestTable> data = FXCollections.observableArrayList();
        ServiceRequestAccess sr = new ServiceRequestAccess();

        int count;
        count = sr.countRecords()-1;
        System.out.println(sr.countRecords());
        while(count >= 0){
            ArrayList<String> arr= sr.getRequests(count);
            ServiceRequestTable testx = new ServiceRequestTable(arr.get(0), arr.get(1),arr.get(2), arr.get(3));
            count--;
            data.add(testx);
        }

        requestDepartment.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable,String>("requestDepartment"));
        comment.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable, String>("comment"));
        assignedEmployee.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable, String>("assignedEmployee"));
        //fullfilled.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable, String>("fullfilled"));
        activeRequests.setItems(data);
    }

    public void setNext(ServiceRequestTable request){
        this.selectedRequest = request;
    }

    //Gabe - Switches to fulfill screen when mouse double clicks a row in the table
    //TODO: Bring over request information so that fulfill page can update a request
    @FXML
    private void SwitchToFulfillRequestScreen() throws IOException {
        activeRequests.setOnMouseClicked(event -> {
            setNext(activeRequests.getSelectionModel().getSelectedItem());
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                try {
                    //Load second scene
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("FulfillRequest.fxml"));
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
