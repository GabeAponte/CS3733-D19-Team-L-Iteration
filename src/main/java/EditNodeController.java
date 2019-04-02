import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.sun.javafx.scene.NodeHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    }

    @FXML
    private void returnAndSave() throws IOException {
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
        }
        else {
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
        }
        thestage = (Stage) submitButton.getScene().getWindow();
        AnchorPane root;
        root =  FXMLLoader.load(getClass().getResource("EditLocation.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
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
