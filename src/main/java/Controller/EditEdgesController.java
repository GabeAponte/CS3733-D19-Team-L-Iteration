package Controller;

import Access.EdgesAccess;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.*;
import javafx.stage.Stage;
import Object.*;
import javafx.util.Duration;

import java.io.IOException;

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

    Timeline timeout;
    private boolean isNew = false;
    private String initialStart, initialEnd, initialID;
    private ObservableList<String> locationIDS = FXCollections.observableArrayList();

    @FXML
    private void backPressed() throws IOException {
        timeout.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EditLocation.fxml"));
        Parent roots = loader.load();

        EditLocationController scene2Controller = loader.getController();

        Scene scene = new Scene(roots);
        Stage thestage = (Stage) EditEdgeBack.getScene().getWindow();

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

                        Stage thisStage = (Stage) PathFindEndDrop.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
                        System.out.println("Hey");
                        timeout.stop();
                    } catch (IOException io){
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));
        timeout.setCycleCount(Timeline.INDEFINITE);
        timeout.play();
        EditEdgeSubmit.setDisable(true);
    }

    @FXML
    private void locationsSelected(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if(PathFindStartDrop.getValue() != null && PathFindEndDrop.getValue() != null){
            EditEdgeSubmit.setDisable(false);
        }
        else{
            EditEdgeSubmit.setDisable(true);
        }
    }

    @FXML
    private void submitButtonPressed() throws IOException {
        timeout.stop();
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

        EditLocationController scene2Controller = loader.getController();

        Scene scene = new Scene(roots);
        Stage thestage = (Stage) EditEdgeBack.getScene().getWindow();

        thestage.setScene(scene);
    }

}
