import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class PathFindingController {
    @FXML
    private Stage thestage;

    @FXML
    private Button PathFindBack;

    @FXML
    private Button PathFindSubmit;

    @FXML
    private Button PathFindLogOut;

    @FXML
    private TextField PathFindEndSearch;

    @FXML
    private TextField PathFindStartSearch;

    @FXML
    private RadioButton PathFindStairsPOI;

    @FXML
    private RadioButton PathFindElevatorPOI;

    @FXML
    private RadioButton PathFindBrPOI;

    @FXML
    private ComboBox<String> PathFindEndDrop;

    @FXML
    private ComboBox<String> PathFindStartDrop;

    private HashMap<String, String> hash;

    @SuppressWarnings("Convert2Diamond")
    @FXML
    public void initialize(){
        final ObservableList<String> OLList = FXCollections.observableArrayList();
        hash = new HashMap<String, String>();
        DBAccess db = new DBAccess();

        for (int count = 0; count < db.countRecords(); count++) {
            ArrayList<String> arr= db.getNodes(count);
            String LongName = arr.get(6);
            OLList.add(LongName);
            hash.put(arr.get(6), arr.get(0));
        }
        PathFindStartDrop.setItems(OLList);
        PathFindEndDrop.setItems(OLList);
    }

    @FXML
    private void backPressed() throws IOException {
        thestage = (Stage) PathFindBack.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    private void submitPressed(){
        String startNodeID = hash.get(PathFindStartDrop.getValue());
        String endNodeID = hash.get(PathFindEndDrop.getValue());

        System.out.println(startNodeID + "   " + endNodeID);

        //TODO Get node information from ID's, Then call Pallfinding on them.
    }
}
