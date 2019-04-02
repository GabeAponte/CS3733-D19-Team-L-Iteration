import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EditEdgesController {

    @FXML
    private Stage thestage;

    @FXML
    private JFXButton EditEdgeBack;

    @FXML
    private ComboBox<String> PathFindEndDrop;

    @FXML
    private ComboBox<String> PathFindStartDrop;

    @FXML
    private Button EditEdgeSubmit;

    private boolean isNew = true;
    private String initialStart, initialEnd, initialID;
    private ObservableList<String> locationIDS = FXCollections.observableArrayList();

    @FXML
    private void backPressed() throws IOException {
        thestage = (Stage) EditEdgeBack.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("EditLocation.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    public void populateNodeList(ObservableList<String> nodes)  {
        this.locationIDS = nodes;
        PathFindStartDrop.setItems(locationIDS);
        PathFindEndDrop.setItems(locationIDS);
    }

    public void setInitialValues(String initialStart, String initialEnd) {
        this.initialStart = initialStart;
        this.initialEnd = initialEnd;
        this.initialID = initialStart + "_" + initialEnd;
        PathFindStartDrop.setPromptText(initialStart);
        PathFindEndDrop.setPromptText(initialEnd);
        isNew = false;
    }

    public void initialize() {
        EditEdgeSubmit.setDisable(true);
    }

    @FXML
    private void locationsSelected(){
        if(PathFindStartDrop.getValue() != null && PathFindEndDrop.getValue() != null){
            EditEdgeSubmit.setDisable(false);
        }
        else{
            EditEdgeSubmit.setDisable(true);
        }
    }

    @FXML
    private void submitButtonPressed() {
        EdgesAccess ea = new EdgesAccess();
        if (isNew) {
            ea.addEdge(PathFindStartDrop.getValue(), PathFindEndDrop.getValue());
        }
        else {
            ea.updateEdge(initialID, PathFindStartDrop.getValue(), PathFindEndDrop.getValue());
        }
    }

}
