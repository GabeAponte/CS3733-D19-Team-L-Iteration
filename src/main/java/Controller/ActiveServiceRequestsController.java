package Controller;

import Access.ServiceRequestAccess;
import Object.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class ActiveServiceRequestsController {

    private Stage thestage;
    private ServiceRequestTable selectedRequest;

    @FXML
    private Button back;

    @FXML
    TableColumn<ServiceRequestTable,String> requestDepartment;

    @FXML
    TableColumn<ServiceRequestTable, String> assignedEmployee;

    @FXML
    TableColumn<ServiceRequestTable, String> comment;

    @FXML
    private TableView<ServiceRequestTable> activeRequests;

    Timeline timeout;

    @FXML
    /**@author Gabe
     * Returns user to the Logged In Home screen when the back button is pressed
     */
    private void backPressed() throws IOException {
        timeout.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));
        Parent roots = loader.load();

        //Get controller of scene2
        LoggedInHomeController scene2Controller = loader.getController();

        Scene scene = new Scene(roots);
        Stage thestage = (Stage) activeRequests.getScene().getWindow();

        //Show scene 2 in new window
        thestage.setScene(scene);
    }

    //Gabe - Populates table
    public void initialize(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("checking if");
                if((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()){
                    try{
                        System.out.println("did it");
                        single.setLastTime();
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        Parent sceneMain = loader.load();

                        Stage thisStage = (Stage) activeRequests.getScene().getWindow();

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

    /**@author Gabe
     * Populates the table on the screen with any active service rquests in the database
     */
        activeRequests.setEditable(false);
        final ObservableList<ServiceRequestTable> data = FXCollections.observableArrayList();
        ServiceRequestAccess sr = new ServiceRequestAccess();

        int count;
        count = sr.countRecords()-1;
        while(count >= 0){
            ArrayList<String> arr= sr.getRequests(count);
            ServiceRequestTable testx = new ServiceRequestTable(arr.get(0), arr.get(1),arr.get(2), arr.get(3));
            count--;
            data.add(testx);
        }

        requestDepartment.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable,String>("requestDepartment"));
        comment.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable, String>("comment"));
        assignedEmployee.setCellValueFactory(new PropertyValueFactory<ServiceRequestTable, String>("assignedEmployee"));
        activeRequests.setItems(data);
    }

    public void setNext(ServiceRequestTable request){
        this.selectedRequest = request;
    }

    //Gabe - Switches to fulfill screen when mouse double clicks a row in the table
    //TODO: Bring over request information so that fulfill page can update a request
    @FXML
    /**@author Gabe, DJ
     * When double clicking on a row in the table, user is sent to the fulfill request screen
     * and all the information from that row is passed along so that a user can update it
     */
    private void SwitchToFulfillRequestScreen() throws IOException {
        timeout.stop();
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
}

