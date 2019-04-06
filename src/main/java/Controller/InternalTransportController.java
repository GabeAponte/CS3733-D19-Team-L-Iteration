package Controller;

import Access.EdgesAccess;
import Access.NodesAccess;
import Object.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.util.ArrayList;
import java.util.HashMap;

public class InternalTransportController {
    @FXML
    JFXComboBox<Location> startBox;

    @FXML
    JFXComboBox<Location> endBox;

    @FXML
    JFXTextField phoneField;

    @FXML
    JFXTextField typeField;

    @FXML
    JFXTextArea commentBox;

    private NodesAccess na;
    private EdgesAccess ea;
    private final ObservableList<Location> data = FXCollections.observableArrayList();
    private HashMap<String, Location> lookup = new HashMap<String, Location>();

    public void initialize(){
        na = new NodesAccess();
        ea = new EdgesAccess();
        initializeTable(na, ea);
        startBox.setItems(data);
        endBox.setItems(data);
        //do something
    }

    private void initializeTable(NodesAccess na, EdgesAccess ea) {
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
                edgeList = ea.getConnectedNodes(arr.get(0));
                for (int j = 0; j < edgeList.size(); j++) {
                    String nodeID = edgeList.get(j);
                    if (lookup.containsKey(na.getNodeInformation(nodeID).get(0))) {
                        Edge e = new Edge(Integer.toString(j), testx, lookup.get(nodeID));
                        testx.addEdge(e);
                    } else {
                        arr2 = na.getNodeInformation(nodeID);
                        Location testy = new Location(nodeID, Integer.parseInt(arr2.get(0)), Integer.parseInt(arr2.get(1)), Integer.parseInt(arr2.get(2)), arr2.get(3), arr2.get(4), arr2.get(5), arr2.get(6));
                        Edge e = new Edge(Integer.toString(j), testx, testy);
                        testx.addEdge(e);
                    }
                }
            }
            else {
                edgeList = ea.getConnectedNodes(arr.get(0));
                for (int j = 0; j < edgeList.size(); j++) {
                    String nodeID = edgeList.get(j);
                    if (lookup.containsKey(na.getNodeInformation(nodeID).get(0))) {
                        Edge e = new Edge(Integer.toString(j), testx, lookup.get(nodeID));
                        testx.addEdge(e);
                    } else {
                        arr2 = na.getNodeInformation(nodeID);
                        Location testy = new Location(nodeID, Integer.parseInt(arr2.get(0)), Integer.parseInt(arr2.get(1)), Integer.parseInt(arr2.get(2)), arr2.get(3), arr2.get(4), arr2.get(5), arr2.get(6));
                        Edge e = new Edge(Integer.toString(j), testx, testy);
                        testx.addEdge(e);
                    }
                }
            }
            count++;
        }
    }


}
