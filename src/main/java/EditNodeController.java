import javafx.fxml.FXML;

import java.awt.*;

public class EditNodeController {
    //grace

    private Location data;
    private String tempNodeID; //for getting og id

    @FXML
    private TextField nodeID;
    @FXML
    private TextField nodeXCoord;
    @FXML
    private TextField nodeYCoord;
    @FXML
    private TextField nodeFloor;
    @FXML
    private TextField nodeBuilding;
    @FXML
    private TextField nodeType;
    @FXML
    private TextField nodeLongName;
    @FXML
    private TextField nodeShortName;


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
    }

}
