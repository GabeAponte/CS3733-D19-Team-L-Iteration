import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

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
    private MenuButton PathFindEndDrop;

    @FXML
    private MenuButton PathFindStartDrop;


    final ObservableList<Location> data = FXCollections.observableArrayList();
    HashMap<String, Location> lookup = new HashMap<String, Location>();

    @FXML
    private void backPressed() throws IOException {
        thestage = (Stage) PathFindBack.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @SuppressWarnings("Convert2Diamond")
    @FXML
    public void initialize() {

        NodesAccess na = new NodesAccess();
        EdgesAccess ea = new EdgesAccess();
        initializeTable(na, ea);
        // AT THIS POINT:
        // Lookup contains all nodes, you can look them up with their keys
        // Each node contains a list of edges properly

        Location testLoc = lookup.get("DHALL01102");
        System.out.println(testLoc.getXcoord());
        System.out.println(testLoc.getEdges().get(1).getEndNode().getLocID());

        //TODO: allow user to specify start and end location
        Location start = new Location("FIX", 5, 5, 5, "FIX", "FIX", "FIX", "FIX");
        Location end = new Location("FIX", 5, 5, 5, "FIX", "FIX", "FIX", "FIX");
        //generatePath(start, end);

    }


    //Nathan - function that will be called when user pressed ENTER button, will do pathfinding
    public void generatePath(Location start, Location end){
        PriorityQueue<Location> open = new PriorityQueue<Location>();
        ArrayList<Location> closed = new ArrayList<Location>();
        displayPath(start.findPath(end, open, closed));
    }

    public void displayPath(ArrayList<Location> path){

    }

    ArrayList<Location> openList = new ArrayList<Location>();
    ArrayList<Location> closeList = new ArrayList<Location>();


    private ArrayList<Location> findPath(Location start, Location end) {
        openList.add(start);

        return new ArrayList<Location>();
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
