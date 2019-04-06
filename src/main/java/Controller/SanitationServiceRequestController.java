package Controller;
import Access.*;
import Object.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SanitationServiceRequestController {
    @FXML
    private JFXButton submit1;

    @FXML
    private JFXButton Back2;

    @FXML
    private JFXComboBox<Location> location1;

    @FXML
    private JFXTextArea comment1;

    @FXML
    private JFXComboBox<String> typeBox1;

    @FXML
    private JFXComboBox<String> urgencyLevel1;

    private NodesAccess na;
    private final ObservableList<Location> data = FXCollections.observableArrayList();
    private HashMap<String, Location> lookup = new HashMap<String, Location>();

    /**Andrew made this
     * Initializes the items that the controller uses and adds fields to combo boxes
     */
    public void initialize(){
        na = new NodesAccess();
        initializeTable(na);
        location1.setItems(data);
        typeBox1.getItems().addAll(
                "Vomit",
                "Spill",
                "Rodent Found",
                "Bio Hazard"
        );
        urgencyLevel1.getItems().addAll(
                "Utmost Urgency",
                "Complete Quickly",
                "Complete Within Week"
        );
        submit1.setDisable(true);
    }

    /** Andrew copied this from somewhere
     * initializes the location combo box to show possible node locations
     * @param na
     */
    private void initializeTable(NodesAccess na) {
        ArrayList<String> edgeList;
        int count;
        count = 0;
        while (count < na.countRecords()) {
            ArrayList<String> arr = na.getNodes(count);
            ArrayList<String> arr2;
            Location testx = new Location(arr.get(0), Integer.parseInt(arr.get(1)), Integer.parseInt(arr.get(2)), Integer.parseInt(arr.get(3)), arr.get(4), arr.get(5), arr.get(6), arr.get(7));
            //only add the node if it hasn't been done yet
            if (!(lookup.containsKey(arr.get(0)))) {
                lookup.put((arr.get(0)), testx);
                data.add(testx);
            }
            count++;
        }
    }

    /**Andrew made this
     * checks if the submit should be enabled or not
     */
    @FXML
    private void checkSubmit(){
        if(comment1 == null || comment1.getText().trim().isEmpty()){
            submit1.setDisable(true);
            return;
        }else if(typeBox1.getValue() == null){
            submit1.setDisable(true);
            return;
        }else if(location1.getValue() == null){
            submit1.setDisable(true);
            return;
        }else if(urgencyLevel1.getValue() == null){
            submit1.setDisable(true);
            return;
        }
        //System.out.println("nathan gay");

        submit1.setDisable(false);
    }

    /**Andrew made this
     * submits the request fields to be stored in the database
     */
    @FXML
    private void makeRequest(){
        SanitationAccess sa = new SanitationAccess();
        sa.makeRequest(location1.getValue().getLocID(), comment1.getText(), typeBox1.getValue(), urgencyLevel1.getValue());
    }

    //Nathan - changes screen to service sub screen, param "service" determines label on sub screen
    @FXML
    private void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequest.fxml"));
        Singleton single = Singleton.getInstance();

        Parent sceneMain = loader.load();

        ServiceRequestController controller = loader.<ServiceRequestController>getController();

        Stage theStage = (Stage) Back2.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }
}
