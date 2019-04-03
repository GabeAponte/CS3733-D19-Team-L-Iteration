import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.scene.NodeHelper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.xml.soap.Node;
import java.awt.*;
import java.io.IOException;
import java.io.IOException;
import java.util.ArrayList;

public class EditNodeController {
    //grace

    //need to add an array list to pass to editEdges

    private Location data;
    private String tempNodeID; //for getting og id

    @FXML
    private JFXTextField nodeID;
    @FXML
    private JFXTextField nodeXCoord;
    @FXML
    private JFXTextField nodeYCoord;
    @FXML
    private JFXTextField nodeFloor;
    @FXML
    private JFXTextField nodeBuilding;
    @FXML
    private JFXTextField nodeType;
    @FXML
    private JFXTextField nodeLongName;
    @FXML
    private JFXTextField nodeShortName;

    @FXML
    private JFXButton editNodeBackButton;
    @FXML
    private JFXButton submitButton;

    private Stage thestage;
    private boolean isNew =true;
    private ObservableList<String> locationIDS = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        nodeXCoord.textProperty().addListener(this::changedX);
        nodeYCoord.textProperty().addListener(this::changedY);
        nodeFloor.textProperty().addListener(this::changedFloor);

        nodeID.setDisable(false);
    }


    public void changedY(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!newValue.matches("\\d{0,4}")) {
            nodeYCoord.setText(oldValue);
        }
    }
    public void changedX(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!newValue.matches("\\d{0,4}")) {
            nodeXCoord.setText(oldValue);
        }
    }
    public void changedFloor(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!newValue.matches("\\d{0,2}")) {
            nodeFloor.setText(oldValue);
        }
    }


    //end of getters and setters

    //if modify node, set texts to existing data
    public void fillFields(Location obj){
        this.data = obj;
        tempNodeID = data.getLocID();

        nodeID.setText(data.getLocID());
        nodeXCoord.setText(Integer.toString(data.getXcoord()));
        nodeYCoord.setText(Integer.toString(data.getYcoord()));
        nodeFloor.setText(Integer.toString(data.getFloor()));
        nodeBuilding.setText(data.getBuilding());
        nodeType.setText(data.getNodeType());
        nodeLongName.setText(data.getLongName());
        nodeShortName.setText(data.getShortName());
        isNew = false;
        nodeID.setDisable(true);
    }

    @FXML
    private void returnAndSave() throws IOException {
        if (checkFields()) {

            NodesAccess na = new NodesAccess();
            if (!isNew) {
                na.updateNode(tempNodeID, "xcoord", Integer.parseInt(nodeXCoord.getText()));
                na.updateNode(tempNodeID, "ycoord", Integer.parseInt(nodeYCoord.getText()));
                na.updateNode(tempNodeID, "floor", Integer.parseInt(nodeFloor.getText()));
                na.updateNode(tempNodeID, "building", nodeBuilding.getText());
                na.updateNode(tempNodeID, "nodeType", nodeType.getText());
                na.updateNode(tempNodeID, "longName", nodeLongName.getText());
                na.updateNode(tempNodeID, "shortName", nodeShortName.getText());
                na.updateNode(tempNodeID, "nodeID", nodeID.getText());
            } else {
                ArrayList<String> newNode = new ArrayList<String>();
                newNode.add(nodeID.getText());
                newNode.add(nodeXCoord.getText());
                newNode.add(nodeYCoord.getText());
                newNode.add(nodeFloor.getText());
                newNode.add(nodeBuilding.getText());
                newNode.add(nodeType.getText());
                newNode.add(nodeLongName.getText());
                newNode.add(nodeShortName.getText());
                na.addNode(newNode);
                locationIDS.add(nodeID.getText());
            }
            thestage = (Stage) submitButton.getScene().getWindow();
            Parent roots;
            if (isNew) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditEdges.fxml"));
                roots = loader.load();
                EditEdgesController scene2Controller = loader.getController();
                scene2Controller.populateNodeList(locationIDS);
                scene2Controller.flipBool();
                scene2Controller.setInitialValues(nodeID.getText(), "ADD EDGE");
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EditLocation.fxml"));
                roots = loader.load();
            }
            Scene scene = new Scene(roots);
            thestage.setScene(scene);
        }
        else {
            System.out.println("INPROPER INPUT");
        }
    }

    public void populateNodeList(ObservableList<String> nodes)  {
        this.locationIDS = nodes;
    }

    public boolean checkFields() {
        boolean checkid = nodeID.getText().equals("");
        boolean checkx = nodeXCoord.getText().equals("");
        boolean checky = nodeYCoord.getText().equals("");
        boolean checkfloor = nodeFloor.getText().equals("");
        boolean checkbuilding = nodeBuilding.getText().equals("");
        boolean checktype = nodeType.getText().equals("");
        boolean checklongname = nodeLongName.getText().equals("");
        boolean checkshortname = nodeShortName.getText().equals("");
        boolean checkcoords = Integer.parseInt(nodeYCoord.getText()) < 3300 && Integer.parseInt(nodeXCoord.getText()) < 4950
                && Integer.parseInt(nodeYCoord.getText()) > 10 && Integer.parseInt(nodeYCoord.getText()) > 20;

        if (!checkid && !checkx && !checky && !checkfloor && !checkbuilding && !checktype &&
                !checklongname && !checkshortname && checkcoords) {
            return true;
        }
        else {
            return false;
        }
    }



    //back/cancel button here
    @FXML
    private void editNodeBackPress() throws IOException {
        thestage = (Stage) editNodeBackButton.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("EditLocation.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

}
