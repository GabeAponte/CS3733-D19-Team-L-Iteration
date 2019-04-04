package Controller;

import Access.EdgesAccess;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class EditEdgesController {

    private String uname;
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

    private boolean isNew = false;
    private String initialStart, initialEnd, initialID;
    private ObservableList<String> locationIDS = FXCollections.observableArrayList();

    public void init(String username){
        uname = username;
    }
    @FXML
    private void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EditLocation.fxml"));
        Parent roots = loader.load();

        //Get controller of scene2
        EditLocationController scene2Controller = loader.getController();
        scene2Controller.init(uname);

        Scene scene = new Scene(roots);
        Stage thestage = (Stage) EditEdgeBack.getScene().getWindow();
        //Show scene 2 in new window
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
        PathFindStartDrop.setValue(initialStart);
        PathFindEndDrop.setValue(initialEnd);
    }

    public void flipBool() {
        isNew = true;
        PathFindStartDrop.setDisable(true);
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
    private void submitButtonPressed() throws IOException {
        EdgesAccess ea = new EdgesAccess();
        if (isNew) {
            ea.addEdge(PathFindStartDrop.getValue(), PathFindEndDrop.getValue());
        }
        else {
            ea.updateEdge(initialID, PathFindStartDrop.getValue(), PathFindEndDrop.getValue());
            ea.changeID(initialID, PathFindStartDrop.getValue() + "_" + PathFindEndDrop.getValue());
        }
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EditLocation.fxml"));
        Parent roots = loader.load();

        //Get controller of scene2
        EditLocationController scene2Controller = loader.getController();
        scene2Controller.init(uname);

        Scene scene = new Scene(roots);
        Stage thestage = (Stage) EditEdgeBack.getScene().getWindow();
        //Show scene 2 in new window
        thestage.setScene(scene);
    }

}
