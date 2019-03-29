import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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


    ArrayList<String[]> edgeBase = new ArrayList<String[]>();
    final ObservableList<Location> data = FXCollections.observableArrayList();
    HashMap<String, Location> lookup = new HashMap<String, Location>();


    @SuppressWarnings("Convert2Diamond")
    @FXML
    public void initialize() {

        NodesAccess na = new NodesAccess();
        EdgesAccess ea = new EdgesAccess();

        int count;
        count = 0;
        while (count < na.countRecords()) {
            ArrayList<String> arr = na.getNodes(count);
            Location testx = new Location(arr.get(0), Integer.parseInt(arr.get(1)), Integer.parseInt(arr.get(2)), Integer.parseInt(arr.get(3)), arr.get(4), arr.get(5), arr.get(6), arr.get(7));
            count++;
            //System.out.println(arr.get(0));
            lookup.put((arr.get(0)), testx);
            data.add(testx);

        }



        System.out.println(edgeBase.get(1)[2]);

        for (int i = 1; i < edgeBase.size(); i++) {
            Edge e = new Edge(edgeBase.get(i)[0], lookup.get(edgeBase.get(i)[1]), lookup.get(edgeBase.get(i)[2]));
            lookup.get(edgeBase.get(i)[1]).addEdge(e);
            lookup.get(edgeBase.get(i)[2]).addEdge(e);
        }

        //TODO: allow user to specify start and end location
        Location start = new Location("FIX", 5, 5, 5, "FIX", "FIX", "FIX", "FIX");
        Location end = new Location("FIX", 5, 5, 5, "FIX", "FIX", "FIX", "FIX");
        //generatePath(start, end);
        
    }
    @FXML
    private void backPressed() throws IOException {
        thestage = (Stage) PathFindBack.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
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

}
