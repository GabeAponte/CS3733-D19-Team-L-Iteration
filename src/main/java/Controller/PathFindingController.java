package Controller;

import Access.EdgesAccess;
import Access.NodesAccess;
import Object.*;
import SearchingAlgorithms.AStarStrategy;
import SearchingAlgorithms.BreadthFirstStrategy;
import SearchingAlgorithms.PathfindingStrategy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("Duplicates")
public class PathFindingController {


    private boolean signedIn;
    private String uname;

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
    private ComboBox<Location> PathFindEndDrop;

    @FXML
    private ComboBox<Location> PathFindStartDrop;

    @FXML
    private AnchorPane anchorPaneWindow;


    private NodesAccess na;
    private EdgesAccess ea;
    private final ObservableList<Location> data = FXCollections.observableArrayList();
    private HashMap<String, Location> lookup = new HashMap<String, Location>();

    private ArrayList<Circle> circles = new ArrayList<Circle>();
    private ArrayList<Line> lines = new ArrayList<Line>();

    private PathfindingStrategy pathAlgorithm;


    @FXML
    private void backPressed() throws IOException {
        thestage = (Stage) PathFindBack.getScene().getWindow();
        AnchorPane root;
        if(signedIn) {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));

            Parent sceneMain = loader.load();

            LoggedInHomeController controller = loader.<LoggedInHomeController>getController();
            controller.init(uname);

            Stage theStage = (Stage) PathFindBack.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);
            return;
        } else {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("HospitalHome.fxml"));
        }
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    public void init(boolean loggeedIn, String username){
            uname = username;
            init(loggeedIn);
    }

    @SuppressWarnings("Convert2Diamond")
    @FXML
    public void init(boolean loggedIn) {
        signedIn = loggedIn;
        na = new NodesAccess();
        ea = new EdgesAccess();
        initializeTable(na, ea);
        PathFindStartDrop.setItems(data);
        PathFindEndDrop.setItems(data);
    }

        @SuppressWarnings("Convert2Diamond")
    @FXML
    public void init(boolean loggedIn, int num) {
        signedIn = loggedIn;
        na = new NodesAccess();
        ea = new EdgesAccess();
        initializeTable(na, ea);
        if(num == 1){
        PathFindStartDrop.setItems(data);
        PathFindEndDrop.setItems(data);
        }

    }

    public HashMap<String, Location> getLookup() {
        return lookup;
    }

    @FXML
    private void locationsSelected(){
        if(PathFindStartDrop.getValue() != null && PathFindEndDrop.getValue() != null){
            PathFindSubmit.setDisable(false);
        }
        else{
            PathFindSubmit.setDisable(true);
        }
    }

    @FXML
    private void submitPressed(){
        Location startNode = lookup.get(PathFindStartDrop.getValue().getLocID());
        Location endNode = lookup.get(PathFindEndDrop.getValue().getLocID());

        AStarStrategy astar = new AStarStrategy(lookup);
        BreadthFirstStrategy bfirst = new BreadthFirstStrategy(lookup);
        Path path = findAbstractPath(bfirst, startNode, endNode);
        //Path path = findAbstractPath(astar, startNode, endNode);
        //Path path = findPath(startNode, endNode);
        displayPath(path.getPath(), startNode, endNode);
        printPath(path);

    }

    public void displayPath(ArrayList<Location> path, Location startNode, Location endNode){
        //path.add(startNode);

        for (Circle c: circles) {
            anchorPaneWindow.getChildren().remove(c);
        }
        for (Line l: lines) {
            anchorPaneWindow.getChildren().remove(l);
        }

        Circle StartCircle = new Circle();

        anchorPaneWindow.getChildren().add(StartCircle);

        //Setting the properties of the circle
        StartCircle.setCenterX(79f + startNode.getXcoord()*0.137);
        StartCircle.setCenterY(189f + startNode.getYcoord()*0.137);
        StartCircle.setRadius(3.0f);

        Circle EndCircle = new Circle();

        anchorPaneWindow.getChildren().add(EndCircle);

        //Setting the properties of the circle
        EndCircle.setCenterX(79f + endNode.getXcoord()*0.137);
        EndCircle.setCenterY(189f + endNode.getYcoord()*0.137);
        EndCircle.setRadius(3.0f);
        EndCircle.setVisible(true);

        circles.add(StartCircle);
        circles.add(EndCircle);

        for (int i = 0; i < path.size()-1; i++) {
            Line line = new Line();

            line.setStartX(79f + path.get(i).getXcoord()*0.137);
            line.setStartY(189f + path.get(i).getYcoord()*0.137);
            line.setEndX(79f + path.get(i+1).getXcoord()*0.137);
            line.setEndY(189f + path.get(i+1).getYcoord()*0.137);

            anchorPaneWindow.getChildren().add(line);

            lines.add(line);
        }
    }


    private Path findAbstractPath(PathfindingStrategy strategy, Location start, Location end) {
        Path p = strategy.findPath(start, end);
        return p;
    }

    private void printPath(Path p) {
        for (int i = 0; i < p.getPath().size() - 1; i ++) {
            double pls = calculateSlope(p.getPath().get(i), p.getPath().get(i+1));
            System.out.println(pls);
        }
       // for (Location place : p.getPath()) {
            //System.out.println("Next, go to " + place.getLongName());
       // }
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

    private double calculateSlope(Location start, Location end) {
        double xDiff = end.getXcoord() - start.getXcoord();
        double yDiff = end.getYcoord() - start.getYcoord();
        return yDiff/xDiff;
    }
}