import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
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
    private ComboBox<String> PathFindEndDrop;

    @FXML
    private ComboBox<String> PathFindStartDrop;

    @FXML
    private AnchorPane anchorPaneWindow;

    private NodesAccess na;
    private EdgesAccess ea;
    final ObservableList<Location> data = FXCollections.observableArrayList();
    final ObservableList<String> LongNames = FXCollections.observableArrayList();
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
        na = new NodesAccess();
        ea = new EdgesAccess();
        initializeTable(na, ea);
        // AT THIS POINT:
        // Lookup contains all nodes, you can look them up with their keys
        // Each node contains a list of edges properly

        Location start = lookup.get("DHALL01202");
        Location end = lookup.get("DHALL00102");
        System.out.println("TEST 1:");

        Path p = findPath(start, end);
        System.out.println(p.toString());

        Location startb = lookup.get("DHALL00102");
        Location endb = lookup.get("DHALL01202");
        System.out.println("TEST BACK:");

        Path pb = findPath(startb, endb);
        System.out.println(pb.toString());

        Location start1 = lookup.get("DHALL05702");
        Location end1 = lookup.get("DHALL00102");
        System.out.println("TEST 2:");

        Path p1 = findPath(start1, end1);
        System.out.println(p1.toString());

        Location start2 = lookup.get("DHALL01202");
        Location end2 = lookup.get("DHALL02702");
        System.out.println("TEST 3:");

        Path p2 = findPath(start2, end2);
        System.out.println(p2.toString());

        Location start4 = lookup.get("DSTAI00202");
        Location end4 = lookup.get("DHALL00602");
        System.out.println("TEST 5:");

        Path p4 = findPath(start4, end4);
        System.out.println(p4.toString());

        Location start5 = lookup.get("DHALL00602");
        Location end5 = lookup.get("DHALL00602");
        System.out.println("TEST SAME LOCAL:");

        Path p5 = findPath(start5, end5);
        System.out.println(p5.toString());

        Location start6 = lookup.get("DHALL00102");
        Location end6 = lookup.get("DSTAI00602");
        System.out.println("TEST EDGE NODE:");

        Path p6 = findPath(start6, end6);
        System.out.println(p6.toString());

        //TODO: allow user to specify start and end location

        //generatePath(start, end);

    }

    @FXML
    private void submitPressed(){
        ArrayList<String> arr1 = na.getNodes(60);
        Location startNode = new Location(arr1.get(0),Integer.parseInt(arr1.get(1)),Integer.parseInt(arr1.get(2)),Integer.parseInt(arr1.get(3)),arr1.get(4),arr1.get(5),arr1.get(6),arr1.get(7));

        ArrayList<String> arr2 = na.getNodes(70);
        Location endNode = new Location(arr2.get(0),Integer.parseInt(arr2.get(1)),Integer.parseInt(arr2.get(2)),Integer.parseInt(arr2.get(3)),arr2.get(4),arr2.get(5),arr2.get(6),arr2.get(7));


        Circle StartCircle = new Circle();

        anchorPaneWindow.getChildren().add(StartCircle);

        //Setting the properties of the circle
        StartCircle.setCenterX(27f + startNode.getXcoord()/5.0);
        StartCircle.setCenterY(213f + startNode.getYcoord()/5.0);
        StartCircle.setRadius(6.0f);

        Circle EndCircle = new Circle();

        anchorPaneWindow.getChildren().add(EndCircle);

        //Setting the properties of the circle
        EndCircle.setCenterX(27f + endNode.getXcoord()/5.0);
        EndCircle.setCenterY(213f + endNode.getYcoord()/5.0);
        EndCircle.setRadius(6.0f);

        System.out.println(startNode.getLocID() + "   " + endNode.getLocID());

        //TODO Get node information from ID's, Then call Pallfinding on them.
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
    ArrayList<String> visited = new ArrayList<String>();


    private Path findPath(Location start, Location end) {
        openList.add(start);
        start.setParentID("START");
        ArrayList<Location> path = new ArrayList<Location>();
        Path p = new Path(path);
        if(start == end)
        {
            p.addToPath(start);
            System.out.println("Same location entered for start and end.");
            cleanup();
            return p;
        }
        Location q = new Location();
        //while there are items in the open list
        while (!(openList.isEmpty())) {
            q = q.findBestF(openList);
            //System.out.println(q.getLocID());
            openList.remove(q);
            closeList.add(q);
            q = lookup.get(q.getLocID());
            ArrayList<Edge> edge = q.getEdges();
            ArrayList<Location> children = new ArrayList<Location>();
            for (Edge e : edge) {
                if (!(closeList.contains(e.getEndNode())) && !(openList.contains(e.getEndNode()))) {
                    children.add(e.getEndNode());
                    e.getEndNode().setGScore(e.findDistance(q, e.getEndNode()));
                }
            }
            for (Location l : children) {
                //condition for found node
                if (l.getLocID().equals(end.getLocID())) {
                    lookup.get(l.getLocID()).setParentID(q.getLocID());
                    l.setParentID(q.getLocID());
                    return returnPath(l);
                } else {
                    double gScore = q.getGScore() + l.getGScore(); //calculate base G score
                    l.setScore(l.calculateScore(gScore, end)); //add in H score
                    l.setParentID(q.getLocID());
                    lookup.get(l.getLocID()).setParentID(q.getLocID());
                    if (!openList.contains(l) && !closeList.contains(l)) {
                        openList.add(l);
                    }
                }
            }
        }
        return p;
    }

    public Path returnPath(Location obj) {
        Location l = obj;
        System.out.println("RUNNING RETURN PATH");
        ArrayList<Location> path = new ArrayList<Location>();
        Path p = new Path(path);
        while (!(l.getParentID().equals("START"))) {
            p.addToPath(l);
            l = lookup.get(l.getParentID());
        }
        cleanup();
        return p;
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

    private void cleanup() {
        for (Location x : lookup.values()) {
            x.setParentID("RESET");
        }
        openList.clear();
        closeList.clear();
    }
}
