import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

import java.awt.*;

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

    //back/cancel button here

}
