import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EditLocationController {
    private String uname;

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

    private  Location focusNode;
    private Edge focusEdge;




    @SuppressWarnings("Convert2Diamond")
    @FXML
    public void init(String username){
        uname = username;
        nodeTable.setEditable(false);
        edgeTable.setEditable(false);
        NodesAccess na = new NodesAccess();
        EdgesAccess ea = new EdgesAccess();
        makeEditableNode.setDisable(true);
        makeEditableEdge.setDisable(true);
        initializeTable(na, ea);

        deleteNode.setDisable(true);
        deleteEdge.setDisable(true);

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
        this.deleteNode.setDisable(false);
    }

    public void setNextEdge(Edge proto) {
        this.makeEditableEdge.setDisable(false);
        this.focusEdge = proto;
        this.deleteEdge.setDisable(false);
    }

    @FXML
    private void deleteEdgePress() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected edge?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            System.out.println("user deletes this edge");
            EdgesAccess ea = new EdgesAccess();
            ea.deleteEdge(focusEdge.getEdgeID());
            edgeTable.getItems().remove(focusEdge);
        }
        else if (alert.getResult() == ButtonType.NO) {
            System.out.println("user does not delete edge");
        }
    }

    @FXML
    private void modifyEdgePress() {
        ObservableList<String> toPass = FXCollections.observableArrayList();
        for (Location l : nodeData) {
            toPass.add(l.getLocID());
            //System.out.println(l.getLocID());
        }
        try {
            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditEdges.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            EditEdgesController scene2Controller = loader.getController();
            scene2Controller.init(uname);

            Scene scene = new Scene(roots);
            scene2Controller.setInitialValues(focusEdge.getStartID(), focusEdge.getEndID());
            scene2Controller.populateNodeList(toPass);
            Stage thestage = (Stage) makeEditableNode.getScene().getWindow();
            //Show scene 2 in new window
            thestage.setScene(scene);

        } catch (IOException ex) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(ex);
        }
    }

    @FXML
    private void addEdgePress() {
        ObservableList<String> toPass = FXCollections.observableArrayList();
        for (Location l : nodeData) {
            toPass.add(l.getLocID());
            //System.out.println(l.getLocID());
        }
        try {
            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditEdges.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            EditEdgesController scene2Controller = loader.getController();
            scene2Controller.init(uname);

            Scene scene = new Scene(roots);
            scene2Controller.populateNodeList(toPass);
            Stage thestage = (Stage) makeEditableNode.getScene().getWindow();
            //Show scene 2 in new window
            thestage.setScene(scene);

        } catch (IOException ex) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(ex);
        }
    }

    @FXML
    private void deleteNodePress() {
        //this is the dialogue popup
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected node?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            System.out.println("user deletes this node");
            //delete the node here
            NodesAccess na = new NodesAccess();
            na.deleteNode(focusNode.getLocID());

            nodeTable.getItems().remove(focusNode);
            EdgesAccess ea = new EdgesAccess();
            ArrayList<Edge> edgeList = new ArrayList<Edge>();

            for (Edge e: edgeData) {
                if (!(ea.containsEdge(e.getEdgeID()))) {
                    edgeList.add(e);
                }
            }
            edgeTable.getItems().removeAll(edgeList);

        }
        else if (alert.getResult() == ButtonType.NO) {
            //do nothing
            System.out.println("user does not delete node");
        }


    }

    @FXML
    private void modifyNodePress() {
        try {
            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditNode.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            EditNodeController scene2Controller = loader.getController();
            scene2Controller.init(uname);

            Scene scene = new Scene(roots);
            scene2Controller.fillFields(this.focusNode);
            Stage thestage = (Stage) makeEditableNode.getScene().getWindow();
            //Show scene 2 in new window
            thestage.setScene(scene);

        } catch (IOException ex) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(ex);
        }
    }

    @FXML
    private void addNodePress() {
        ObservableList<String> toPass = FXCollections.observableArrayList();
        for (Location l : nodeData) {
            toPass.add(l.getLocID());
            //System.out.println(l.getLocID());
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditNode.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            EditNodeController scene2Controller = loader.getController();
            scene2Controller.init(uname);

            Scene scene = new Scene(roots);
            scene2Controller.populateNodeList(toPass);
            Stage thestage = (Stage) makeEditableNode.getScene().getWindow();
            //Show scene 2 in new window
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
            Location testx = new Location(arr.get(0), Integer.parseInt(arr.get(1)), Integer.parseInt(arr.get(2)), Integer.parseInt(arr.get(3)), arr.get(4), arr.get(5), arr.get(6), arr.get(7));
            //only add the node if it hasn't been done yet
            if (!(lookup.containsKey(arr.get(0)))) {
                lookup.put((arr.get(0)), testx);
                nodeData.add(testx);
            }
            count++;
        }
        count = 0;
        while (count < ea.countRecords()) {
            edgeList = ea.getEdges(count);
            Edge testy = new Edge(edgeList.get(0), lookup.get(edgeList.get(1)), lookup.get(edgeList.get(2)));
            edgeData.add(testy);
            count ++;
        }

    }

    @FXML
    private void backPressed() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoggedInHome.fxml"));

        Parent sceneMain = loader.load();

        LoggedInHomeController controller = loader.<LoggedInHomeController>getController();
        controller.init(uname);

        Stage theStage = (Stage) addEdge.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }




}

