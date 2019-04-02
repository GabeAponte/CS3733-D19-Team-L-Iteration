import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EditLocationController {

    @FXML
    Button backButton;

    @FXML
    Button downloadNode;

    @FXML
    Button downloadEdge;

    @FXML
    Button makeEditableNode;

    @FXML
    Button makeEditableEdge;

    @FXML
    Button addNode;

    @FXML
    Button addEdge;

    @FXML
    Button deleteNode;

    @FXML
    Button deleteEdge;

    @FXML
    TableColumn<Location, String> idCol;
    @FXML
    TableColumn<Location,Integer> xCol;
    @FXML
    TableColumn<Location, Integer> yCol;
    @FXML
    TableColumn<Location, Integer> floorCol;
    @FXML
    TableColumn<Location, String> buildingCol;
    @FXML
    TableColumn<Location, String> typeCol;
    @FXML
    TableColumn<Location, String> longCol;
    @FXML
    TableColumn<Location, String> shortCol;

    @FXML
    TableColumn<Edge, String> edgeIDCol;
    @FXML
    TableColumn<Edge, String> startNodeCol;
    @FXML
    TableColumn<Edge, String> endNodeCol;


    @SuppressWarnings("unchecked")
    @FXML
    private TableView<Location> nodeTable = new TableView();
    @FXML
    private TableView<Edge> edgeTable = new TableView();

    private HashMap<String, Location> lookup = new HashMap<String, Location>();
    private final ObservableList<Location> nodeData = FXCollections.observableArrayList();
    private final ObservableList<Edge> edgeData = FXCollections.observableArrayList();

    private Stage thestage;
    private  Location focusNode;
    private Edge focusEdge;




    @SuppressWarnings("Convert2Diamond")
    @FXML
    public void initialize(){
        nodeTable.setEditable(false);
        edgeTable.setEditable(false);
        NodesAccess na = new NodesAccess();
        EdgesAccess ea = new EdgesAccess();
        makeEditableNode.setDisable(true);
        makeEditableEdge.setDisable(true);
        initializeTable(na, ea);

        //node table setup
        idCol.setCellValueFactory(new PropertyValueFactory<Location,String>("locID"));
        xCol.setCellValueFactory(new PropertyValueFactory<Location, Integer>("xcoord"));
        yCol.setCellValueFactory(new PropertyValueFactory<Location, Integer>("ycoord"));
        floorCol.setCellValueFactory(new PropertyValueFactory<Location, Integer>("floor"));
        buildingCol.setCellValueFactory(new PropertyValueFactory<Location, String >("building"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Location, String >("nodeType"));
        longCol.setCellValueFactory(new PropertyValueFactory<Location,String>("longName"));
        shortCol.setCellValueFactory(new PropertyValueFactory<Location, String>("shortName"));
        //noinspection CodeBlock2Expr
        nodeTable.setOnMouseClicked(event -> {
            setNextNode(nodeTable.getSelectionModel().getSelectedItem());});
        nodeTable.setItems(nodeData);

        //edge table setup
        edgeIDCol.setCellValueFactory(new PropertyValueFactory<Edge, String>("edgeID"));
        startNodeCol.setCellValueFactory(new PropertyValueFactory<Edge, String>("startID"));
        endNodeCol.setCellValueFactory(new PropertyValueFactory<Edge, String>("endID"));
        edgeTable.setOnMouseClicked(event -> {
            setNextEdge(edgeTable.getSelectionModel().getSelectedItem());});
        edgeTable.setItems(edgeData);



    }

    public void setNextNode(Location proto) {
        this.makeEditableNode.setDisable(false);
        this.focusNode = proto;
    }

    public void setNextEdge(Edge proto) {
        this.makeEditableEdge.setDisable(false);
        this.focusEdge = proto;
    }

    @FXML
    private void deleteEdgePress() {
        
    }

    @FXML
    private void modifyEdgePress() {
        try {
            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditEdges.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            EditNodeController scene2Controller = loader.getController();

            Scene scene = new Scene(roots);
            scene2Controller.fillFields(this.focusNode);
            thestage = (Stage) makeEditableNode.getScene().getWindow();
            //Show scene 2 in new window
            thestage.setScene(scene);

        } catch (IOException ex) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(ex);
        }
    }

    @FXML
    private void addEdgePress() {}

    @FXML
    private void deleteNodePress() {

    }

    @FXML
    private void modifyNodePress() {
        try {
            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditNode.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            EditNodeController scene2Controller = loader.getController();

            Scene scene = new Scene(roots);
            scene2Controller.fillFields(this.focusNode);
            thestage = (Stage) makeEditableNode.getScene().getWindow();
            //Show scene 2 in new window
            thestage.setScene(scene);

        } catch (IOException ex) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(ex);
        }
    }

    @FXML
    private void addNodePress() {
        try {
            Stage thestage = (Stage) backButton.getScene().getWindow();
            AnchorPane root;
            root = FXMLLoader.load(getClass().getResource("EditNode.fxml"));
            Scene scene = new Scene(root);
            thestage.setScene(scene);
        } catch (Exception e){
        }
    }

    @FXML
    private void downloadNodes() {
        NodesAccess na = new NodesAccess();
        na.writeTableIntoCSV("");
    }

    @FXML
    private void downloadEdges() {
        EdgesAccess ea = new EdgesAccess();
        ea.writeTableIntoCSV("");
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
                nodeData.add(testx);
                edgeList = ea.getConnectedNodes(arr.get(0));
                for (int j = 0; j < edgeList.size(); j++) {
                    Edge toAdd;
                    String nodeID = edgeList.get(j);
                    if (lookup.containsKey(na.getNodeInformation(nodeID).get(0))) {
                        Edge e = new Edge(Integer.toString(j), testx, lookup.get(nodeID));
                        testx.addEdge(e);
                        toAdd = e;
                    } else {
                        arr2 = na.getNodeInformation(nodeID);
                        Location testy = new Location(nodeID, Integer.parseInt(arr2.get(0)), Integer.parseInt(arr2.get(1)), Integer.parseInt(arr2.get(2)), arr2.get(3), arr2.get(4), arr2.get(5), arr2.get(6));
                        Edge e = new Edge(Integer.toString(j), testx, testy);
                        testx.addEdge(e);
                        toAdd = e;
                    }
                    if (!(edgeData.contains(toAdd))){
                        toAdd.setEdgeID(toAdd.getStartID()+ "_" + toAdd.getEndID());
                        edgeData.add(toAdd);
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

    @FXML
    private void backPressed() {
        try {
            Stage thestage = (Stage) backButton.getScene().getWindow();
            AnchorPane root;
            root = FXMLLoader.load(getClass().getResource("LoggedInHome.fxml"));
            Scene scene = new Scene(root);
            thestage.setScene(scene);
        } catch (Exception e){
        }
    }




}

