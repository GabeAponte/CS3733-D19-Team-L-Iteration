package Controller;

import SearchingAlgorithms.AStarStrategy;
import SearchingAlgorithms.PathfindingStrategy;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import Access.EdgesAccess;
import Access.NodesAccess;
import Object.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

@SuppressWarnings("Duplicates")
public class PathFindingController {

    @FXML
    private Stage thestage;

    @FXML
    private Button PathFindBack;

    @FXML
    private Button PathFindSubmit;

    @FXML
    private Button G;

    @FXML
    private Button Up;

    @FXML
    private Button Down;

    @FXML
    private Button L1;

    @FXML
    private Button L2;

    @FXML
    private Button F1;

    @FXML
    private Button F2;

    @FXML
    private Button F3;

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
    private ComboBox<String> Filter;

    @FXML
    private ComboBox<String> Floor;

    @FXML
    private ComboBox<Location> PathFindEndDrop;

    @FXML
    private ComboBox<Location> PathFindStartDrop;


    @FXML
    private AnchorPane anchorPaneWindow;

    @FXML
    private ImageView Map;

    private PanAndZoomPane zoomPaneImage;
    private SceneGestures sceneGestures;
    private AnchorPane anchorPanePath;
    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(1.0d);
    private final DoubleProperty deltaY = new SimpleDoubleProperty(0.0d);

    private NodesAccess na;
    private EdgesAccess ea;
    //private final ObservableList<Location> data = FXCollections.observableArrayList();
    //private HashMap<String, Location> lookup = new HashMap<String, Location>();
    private final ObservableList<Location> noHallStart = FXCollections.observableArrayList();
    private final ObservableList<Location> noHallEnd = FXCollections.observableArrayList();
    private final ObservableList<String> filterList = FXCollections.observableArrayList();
    private final ObservableList<String> floorList = FXCollections.observableArrayList();
    private Singleton single = Singleton.getInstance();


    private ArrayList<String> mapURLs = new ArrayList<String>();
    private ArrayList<Circle> circles = new ArrayList<Circle>();
    private ArrayList<Line> lines = new ArrayList<Line>();

    String pickedFloor = "test";
    String type = "test";
    String type2 = "";

    String currentMap = "";
    @FXML
    private void clickedG(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thegroundfloor.png"));
    }
    @FXML
    private void clickedL1(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel1.png"));
    }

    @FXML
    public void clickedL2(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel2.png"));
    }
    @FXML
    private void clicked1(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/01_thefirstfloor.png"));
    }
    @FXML
    private void clicked2(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/02_thesecondfloor.png"));
    }
    @FXML
    private void clicked3(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/03_thethirdfloor.png"));
    }
    ListIterator<String> listIterator = null;

    @FXML
    /**
     * @author Gabe
     * adds all the URLs to the list, starts with 3rd floor since that is the next floor to come
     */
    public void map(){
        mapURLs.add("/SoftEng_UI_Mockup_Pics/03_thethirdfloor.png");
        mapURLs.add("/SoftEng_UI_Mockup_Pics/00_thegroundfloor.png");
        mapURLs.add("/SoftEng_UI_Mockup_Pics/00_thelowerlevel1.png");
        mapURLs.add("/SoftEng_UI_Mockup_Pics/00_thelowerlevel2.png");
        mapURLs.add("/SoftEng_UI_Mockup_Pics/01_thefirstfloor.png");
        mapURLs.add("/SoftEng_UI_Mockup_Pics/02_thesecondfloor.png");
    }

    boolean upclickedLast = false;
    boolean downclickedLast = false;

    @FXML
    /**
     * @author Gabe
     * Replaces image URL with the next floor up when the UP button is pressed
     */
    private void upClicked() {
        if (mapURLs.isEmpty()) {
            map();
            listIterator = mapURLs.listIterator();
        }
        if (listIterator.hasNext() == false && downclickedLast == true) {
            listIterator = mapURLs.listIterator();
            String next = listIterator.next();
            Map.setImage(new Image(next));
        }
        else if (listIterator.hasNext() == false) {
            listIterator = mapURLs.listIterator();
            String next = listIterator.next();
            Map.setImage(new Image(next));
        }

        else if (downclickedLast == true){
            String next = listIterator.next();
            Map.setImage(new Image(next));
            upAgain();
            // Map.setImage(new Image(next));
        }

        else if (downclickedLast == false){
            String next = listIterator.next();
            Map.setImage(new Image(next));
        }
        upclickedLast = true;
        downclickedLast = false;
    }
    @FXML
    /**
     * @author Gabe
     * allows the map to change when UP is pressed and the last button clicked was DOWN by calling next one more time.
     */
    private void upAgain() {
        if (listIterator.hasNext() == false) {
            listIterator = mapURLs.listIterator();
            String next = listIterator.next();
            Map.setImage(new Image(next));
        } else {
            String next = listIterator.next();
            Map.setImage(new Image(next));
        }
    }

    @FXML
    /**
     * @author Gabe
     * allows the map to change when down is pressed and the last button clicked was UP by calling previous one more time.
     */
    private void downAgain() {
        if (listIterator.hasPrevious() == false) {
            listIterator = mapURLs.listIterator(mapURLs.size() - 1);
            String previous = listIterator.previous();
            Map.setImage(new Image(previous));
        } else {
            String previous = listIterator.previous();
            Map.setImage(new Image(previous));
        }
    }

    @FXML
    /**
     * @author Gabe
     * Replaces image URL with the next floor down when the DOWN button is pressed
     */
    private void downClicked(){
        if (mapURLs.isEmpty()) {
            map();
            listIterator = mapURLs.listIterator();
        }
        if (listIterator.hasPrevious() == false && upclickedLast == true) {
            listIterator = mapURLs.listIterator(mapURLs.size()-1);
            String previous = listIterator.previous();
            Map.setImage(new Image(previous));
        }
        else if (listIterator.hasPrevious() == false) {
            listIterator = mapURLs.listIterator(mapURLs.size()-1);
            String previous = listIterator.previous();
            Map.setImage(new Image(previous));
        }
        else if (upclickedLast == true){
            String previous = listIterator.previous();
            Map.setImage(new Image(previous));
            downAgain();//Due to the nature of listIterator, previous needs to be called twice inorder for the image to switch
        }
        else if (upclickedLast == false){
            String previous = listIterator.previous();
            Map.setImage(new Image(previous));
        }
        upclickedLast = false;
        downclickedLast = true;
    }

    public void initialize() {
        Singleton single = Singleton.getInstance();
        na = new NodesAccess();
        ea = new EdgesAccess();
        filter();
        floor();
        noHall();
        Filter.setItems(filterList);
        Floor.setItems(floorList);
        //initializeTable(na, ea);
        if(single.getNum() == 1){
            PathFindStartDrop.setItems(single.getData());
            PathFindEndDrop.setItems(single.getData());
        }
        anchorPanePath = new AnchorPane();
        anchorPanePath.setLayoutX(79);
        anchorPanePath.setLayoutY(189);
        anchorPanePath.setPrefSize(685,464);
        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(Map.fitWidthProperty());
        clip.heightProperty().bind(Map.fitHeightProperty());
        anchorPanePath.setClip(clip);

        zoomPaneImage = new PanAndZoomPane();

        zoomProperty.bind(zoomPaneImage.myScale);
        deltaY.bind(zoomPaneImage.deltaY);
        zoomPaneImage.getChildren().add(Map);

        sceneGestures = new SceneGestures(zoomPaneImage, Map);
        anchorPanePath.addEventFilter( MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
        anchorPanePath.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        anchorPanePath.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        anchorPanePath.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        zoomPaneImage.setLayoutX(79);
        zoomPaneImage.setLayoutY(189);

        anchorPaneWindow.getChildren().add(zoomPaneImage);
        anchorPaneWindow.getChildren().add(anchorPanePath);

        sceneGestures.reset(Map, Map.getImage().getWidth(), Map.getImage().getHeight());

    }

    @FXML
    private void backPressed() throws IOException {
        Singleton single = Singleton.getInstance();
        thestage = (Stage) PathFindBack.getScene().getWindow();
        AnchorPane root;
        if(single.isLoggedIn()) {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));

            Parent sceneMain = loader.load();

            LoggedInHomeController controller = loader.<LoggedInHomeController>getController();

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

    public HashMap<String, Location> getLookup() {
        return single.lookup;
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
        Location startNode = single.lookup.get(PathFindStartDrop.getValue().getLocID());
        Location endNode = single.lookup.get(PathFindEndDrop.getValue().getLocID());

        AStarStrategy astar = new AStarStrategy(single.lookup);
        Path path = findAbstractPath(astar, startNode, endNode);

        displayPath(path.getPath(), startNode, endNode);
        printPath(path.getPath());

        sceneGestures.setDrawPath(circles,lines);
       // TextDirection.setText(printPath(path.getPath()));
    }

    public void displayPath(ArrayList<Location> path, Location startNode, Location endNode){
        path.add(0,startNode);

        for (Circle c: circles) {
            anchorPanePath.getChildren().remove(c);
        }
        for (Line l: lines) {
            anchorPanePath.getChildren().remove(l);
        }

        circles.clear(); //Slight Optimization Please add these in -Alex
        lines.clear();

        Point2D point = sceneGestures.getImageLocation();

        for (int i = 0; i < path.size()-1; i++) {
            Line line = new Line();

            line.setStartX((path.get(i).getXcoord()-point.getX())*0.137*sceneGestures.getImageScale());
            line.setStartY((path.get(i).getYcoord()-point.getY())*0.137*sceneGestures.getImageScale());
            line.setEndX((path.get(i+1).getXcoord()-point.getX())*0.137*sceneGestures.getImageScale());
            line.setEndY((path.get(i+1).getYcoord()-point.getY())*0.137*sceneGestures.getImageScale());

            line.setStrokeWidth(Math.max(1,sceneGestures.getImageScale()/8));

            lines.add(line);

            anchorPanePath.getChildren().add(line);
        }

        Circle StartCircle = new Circle();

        //Setting the properties of the circle
        StartCircle.setCenterX((startNode.getXcoord()-point.getX())*0.137*sceneGestures.getImageScale());
        StartCircle.setCenterY((startNode.getYcoord()-point.getY())*0.137*sceneGestures.getImageScale());
        StartCircle.setRadius(Math.max(2.5,2.5f*(sceneGestures.getImageScale()/5)));
        StartCircle.setStroke(Color.GREEN);
        StartCircle.setFill(Color.GREEN);

        anchorPanePath.getChildren().add(StartCircle);

        Circle EndCircle = new Circle();

        //Setting the properties of the circle
        EndCircle.setCenterX((endNode.getXcoord()-point.getX())*0.137*sceneGestures.getImageScale());
        EndCircle.setCenterY((endNode.getYcoord()-point.getY())*0.137*sceneGestures.getImageScale());
        EndCircle.setRadius(Math.max(2.5,2.5f*(sceneGestures.getImageScale()/5)));
        EndCircle.setStroke(Color.RED);
        EndCircle.setFill(Color.RED);

        anchorPanePath.getChildren().add(EndCircle);


        circles.add(StartCircle);
        circles.add(EndCircle);
    }

    ArrayList<Location> openList = new ArrayList<Location>();
    ArrayList<Location> closeList = new ArrayList<Location>();
    ArrayList<String> visited = new ArrayList<String>();


    private void initializeTable(NodesAccess na, EdgesAccess ea) {
        ArrayList<String> edgeList;
        int count;
        count = 0;
        while (count < na.countRecords()) {
            ArrayList<String> arr = na.getNodes(count);
            ArrayList<String> arr2;
            Location testx = new Location(arr.get(0), Integer.parseInt(arr.get(1)), Integer.parseInt(arr.get(2)), arr.get(3), arr.get(4), arr.get(5), arr.get(6), arr.get(7));
            //only add the node if it hasn't been done yet
            if (!(single.lookup.containsKey(arr.get(0)))) {
                single.lookup.put((arr.get(0)), testx);
                //single.getData().add(testx);
                edgeList = ea.getConnectedNodes(arr.get(0));
                for (int j = 0; j < edgeList.size(); j++) {
                    String nodeID = edgeList.get(j);
                    if (single.lookup.containsKey(na.getNodeInformation(nodeID).get(0))) {
                        Edge e = new Edge(Integer.toString(j), testx, single.lookup.get(nodeID));
                        testx.addEdge(e);
                    } else {
                        arr2 = na.getNodeInformation(nodeID);
                        Location testy = new Location(nodeID, Integer.parseInt(arr2.get(0)), Integer.parseInt(arr2.get(1)), arr2.get(2), arr2.get(3), arr2.get(4), arr2.get(5), arr2.get(6));
                        Edge e = new Edge(Integer.toString(j), testx, testy);
                        testx.addEdge(e);
                    }
                }
            }
            else {
                edgeList = ea.getConnectedNodes(arr.get(0));
                for (int j = 0; j < edgeList.size(); j++) {
                    String nodeID = edgeList.get(j);
                    if (single.lookup.containsKey(na.getNodeInformation(nodeID).get(0))) {
                        Edge e = new Edge(Integer.toString(j), testx, single.lookup.get(nodeID));
                        testx.addEdge(e);
                    } else {
                        arr2 = na.getNodeInformation(nodeID);
                        Location testy = new Location(nodeID, Integer.parseInt(arr2.get(0)), Integer.parseInt(arr2.get(1)), arr2.get(2), arr2.get(3), arr2.get(4), arr2.get(5), arr2.get(6));
                        Edge e = new Edge(Integer.toString(j), testx, testy);
                        testx.addEdge(e);
                    }
                }
            }
            count++;
        }
    }

    @FXML
    /**
     * @author Gabe
     * reads the input for the floor combo box
     */
    private void filterFloor() {
        if (Floor.getValue() == "Ground") {
            pickedFloor = "G";
        }
        if (Floor.getValue() == "L1") {
            pickedFloor = "L1";
        }
        if (Floor.getValue() == "L2") {
            pickedFloor = "L2";
        }
        if (Floor.getValue() == "1") {
            pickedFloor = "1";
        }
        if (Floor.getValue() == "2") {
            pickedFloor = "2";
        }
        if (Floor.getValue() == "3") {
            pickedFloor = "3";
        }
    }

    @FXML
    private void clearStart(){
        PathFindStartDrop.getSelectionModel().clearSelection();
        PathFindStartDrop.setValue(null);
        noHall();
    }

    @FXML
    private void clearEnd(){
        PathFindEndDrop.getSelectionModel().clearSelection();
        PathFindEndDrop.setValue(null);
        noHall();
    }
    @FXML
    /**
     * @author Gabe
     * reads the input for the room type combo box
     */
    private void filterType() {
        if (Filter.getValue() == ("Conference Rooms")) {
            type = "CONF";
        }
        if (Filter.getValue() == ("Exits")) {
            type = "EXIT";
        }
        if (Filter.getValue() == ("Departments")) {
            type = "DEPT";
        }
        if (Filter.getValue() == ("Stairs")) {
            type = "STAI";
        }
        if (Filter.getValue() == ("Elevators")) {
            type = "ELEV";
        }
        if (Filter.getValue() == ("Labs")) {
            type = "LABS";
        }
        if (Filter.getValue() == ("Information")) {
            type = "INFO";
        }
        if (Filter.getValue() == ("Services")) {
            type = "SERV";
        }
        if (Filter.getValue() == ("Food and Retail")) {
            type = "RETL";
        }
        if (Filter.getValue() == ("Restrooms")) {
            type = "REST";
            type2 = "BATH";
        }

    }

    @FXML
    /**
     * @author Gabe
     * populates the start and end drop downs with filtered locations based on type and floor number.
     * No hallways are ever showed.
     */
    private void noHall() {
        filterFloor();
        filterType();

        if (Filter.getValue() == null && Floor.getValue() == null) {
            if (PathFindStartDrop.getValue() == null) {
                noHallStart.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if (!(single.getData().get(j).getNodeType().contains("HALL"))) {
                        noHallStart.add(single.getData().get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if (!(single.getData().get(j).getNodeType().contains("HALL"))) {
                        noHallEnd.add(single.getData().get(j));
                    }
                }
            }

            if (PathFindStartDrop.getValue() == null) {
                PathFindStartDrop.setItems(noHallStart);
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
            }

        } else if (Filter.getValue() == ("All") && Floor.getValue() == null) {
            if (PathFindStartDrop.getValue() == null) {
                noHallStart.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if (!(single.getData().get(j).getNodeType().contains("HALL"))) {
                        noHallStart.add(single.getData().get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if (!(single.getData().get(j).getNodeType().contains("HALL"))) {
                        noHallEnd.add(single.getData().get(j));
                    }
                }
            }

            if (PathFindStartDrop.getValue() == null) {
                PathFindStartDrop.setItems(noHallStart);
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
            }
        } else if (Filter.getValue() == ("All") && Floor.getValue() == "All") {
            if (PathFindStartDrop.getValue() == null) {
                noHallStart.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if (!(single.getData().get(j).getNodeType().contains("HALL"))) {
                        noHallStart.add(single.getData().get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if (!(single.getData().get(j).getNodeType().contains("HALL"))) {
                        noHallEnd.add(single.getData().get(j));
                    }
                }
            }

            if (PathFindStartDrop.getValue() == null) {
                PathFindStartDrop.setItems(noHallStart);
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
            }

        } else if (Filter.getValue() == ("All") && Floor.getValue() != null) {
            if (PathFindStartDrop.getValue() == null) {
                noHallStart.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if (!(single.getData().get(j).getNodeType().contains("HALL")) && (single.getData().get(j).getFloor().equals(pickedFloor))) {
                        noHallStart.add(single.getData().get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if (!(single.getData().get(j).getNodeType().contains("HALL")) && (single.getData().get(j).getFloor().equals(pickedFloor))) {
                        noHallEnd.add(single.getData().get(j));
                    }
                }
            }

            if (PathFindStartDrop.getValue() == null) {
                PathFindStartDrop.setItems(noHallStart);
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
            }

        } else if (Filter.getValue() != (null) && Floor.getValue() == "All") {
            if (PathFindStartDrop.getValue() == null) {
                noHallStart.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if ((single.getData().get(j).getNodeType().contains(type))) {
                        noHallStart.add(single.getData().get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if ((single.getData().get(j).getNodeType().contains(type))) {
                        noHallEnd.add(single.getData().get(j));
                    }
                }
            }

            if (PathFindStartDrop.getValue() == null) {
                PathFindStartDrop.setItems(noHallStart);
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
            }


        } else if (Filter.getValue() == null && Floor.getValue() == "All") {
            if (PathFindStartDrop.getValue() == null) {
                noHallStart.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if (!(single.getData().get(j).getNodeType().contains("HALL"))) {
                        noHallStart.add(single.getData().get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if (!(single.getData().get(j).getNodeType().contains("HALL"))) {
                        noHallEnd.add(single.getData().get(j));
                    }
                }
            }

            if (PathFindStartDrop.getValue() == null) {
                PathFindStartDrop.setItems(noHallStart);
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
            }


        } else if (Filter.getValue() == "Restrooms" && Floor.getValue() == null) {
            if (PathFindStartDrop.getValue() == null) {
                noHallStart.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if ((single.getData().get(j).getNodeType().contains(type)) || (single.getData().get(j).getNodeType().contains(type2))) {
                        noHallStart.add(single.getData().get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if ((single.getData().get(j).getNodeType().contains(type)) || (single.getData().get(j).getNodeType().contains(type2))) {
                        noHallEnd.add(single.getData().get(j));
                    }
                }
            }

            if (PathFindStartDrop.getValue() == null) {
                PathFindStartDrop.setItems(noHallStart);
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
            }


        } else if (Filter.getValue() == "Restrooms" && Floor.getValue() == "All") {
            if (PathFindStartDrop.getValue() == null) {
                noHallStart.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if ((single.getData().get(j).getNodeType().contains(type)) || (single.getData().get(j).getNodeType().contains(type2))) {
                        noHallStart.add(single.getData().get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if ((single.getData().get(j).getNodeType().contains(type)) || (single.getData().get(j).getNodeType().contains(type2))) {
                        noHallEnd.add(single.getData().get(j));
                    }
                }
            }

            if (PathFindStartDrop.getValue() == null) {
                PathFindStartDrop.setItems(noHallStart);
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
            }


        } else if (Filter.getValue() != null && Filter.getValue() == "Restrooms" && Floor.getValue() != null) {
            if (PathFindStartDrop.getValue() == null) {
                noHallStart.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if ((single.getData().get(j).getNodeType().contains(type)) && (single.getData().get(j).getFloor().equals(pickedFloor)) || ((single.getData().get(j).getNodeType().contains(type2)) && (single.getData().get(j).getFloor().equals(pickedFloor)))) {
                        noHallStart.add(single.getData().get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if ((single.getData().get(j).getNodeType().contains(type)) && (single.getData().get(j).getFloor().equals(pickedFloor)) || ((single.getData().get(j).getNodeType().contains(type2)) && (single.getData().get(j).getFloor().equals(pickedFloor)))) {
                        noHallEnd.add(single.getData().get(j));
                    }
                }
            }

            if (PathFindStartDrop.getValue() == null) {
                PathFindStartDrop.setItems(noHallStart);
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
            }


        } else if (Filter.getValue() != null && Filter.getValue() != "All" && Floor.getValue() == null) {
            if (PathFindStartDrop.getValue() == null) {
                noHallStart.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if ((single.getData().get(j).getNodeType().contains(type))) {
                        noHallStart.add(single.getData().get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if ((single.getData().get(j).getNodeType().contains(type))) {
                        noHallEnd.add(single.getData().get(j));
                    }
                }
            }

            if (PathFindStartDrop.getValue() == null) {
                PathFindStartDrop.setItems(noHallStart);
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
            }

        } else if (Filter.getValue() != null && Filter.getValue() != "All" && Floor.getValue() != null) {
            if (PathFindStartDrop.getValue() == null) {
                noHallStart.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if ((single.getData().get(j).getNodeType().contains(type)) && (single.getData().get(j).getFloor().equals(pickedFloor))) {
                        noHallStart.add(single.getData().get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if ((single.getData().get(j).getNodeType().contains(type)) && (single.getData().get(j).getFloor().equals(pickedFloor))) {
                        noHallEnd.add(single.getData().get(j));
                    }
                }
            }

            if (PathFindStartDrop.getValue() == null) {
                PathFindStartDrop.setItems(noHallStart);
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
            }
        }
    }

    private void cleanup() {
        for (Location x : single.lookup.values()) {
            x.setParentID("RESET");
        }
        openList.clear();
        closeList.clear();
    }

    public Path findAbstractPath(PathfindingStrategy strategy, Location start, Location end) {
        Path p = strategy.findPath(start, end);
        return p;
    }

    private double calculateSlope(Location start, Location end) {
        double xDiff = end.getXcoord() - start.getXcoord();
        double yDiff = end.getYcoord() - start.getYcoord();
        return yDiff/xDiff;
    }

    @FXML
    private void floor() {
        floorList.add("All");
        floorList.add("Ground");
        floorList.add("L1");
        floorList.add("L2");
        floorList.add("1");
        floorList.add("2");
        floorList.add("3");
    }

    @FXML
    /**
     * @author Gabe
     * Adds the fields that can be used to filter locations by type
     */
    private void filter() {
        filterList.add("All");
        filterList.add("Food and Retail");
        filterList.add("Restrooms");
        filterList.add("Conference Rooms");
        filterList.add("Stairs");
        filterList.add("Elevators");
        filterList.add("Departments");
        filterList.add("Exits");
        filterList.add("Services");
        filterList.add("Labs");
        filterList.add("Information");
    }
    //Larry - To calculate the angele of turning
    private double calculateAngle(Location a, Location b, Location c){
        double distanceA, distanceB,distanceC;
        double angleTurning;
        //distance A
        distanceA = a.findDistance(b);
        //distance B
        distanceB = b.findDistance(c);
        //distance C
        distanceC = a.findDistance(c);

        angleTurning = Math.acos((distanceA*distanceA + distanceB*distanceB - distanceC*distanceC)
                / (2*distanceA*distanceB));
        //angleTurning = Math.acos(1);
        angleTurning = angleTurning /(2*Math.PI) * 360;
//        System.out.println("Da " + distanceA);
//        System.out.println("Db " + distanceB);
//        System.out.println("Dc " + distanceC);
//        System.out.println("Angle " + angleTurning);


        return  angleTurning;

    }
    //Larry - convert pixel distance to exact distance
    //147 pixel distance = 52 ft
    private int convertToExact(double pixelDistance){
        double actualLength = pixelDistance / 147 * 52;

        return  (int)actualLength;
    }
    //Larry - face direction given two locations to retrun the direction you are facing
    private int faceDicrection(Location a, Location b){
        int direction = 0;
        // 1 is right, 2 is left, 3 is up ,4 is down
        if(calculateSlope(a,b) > 0 && calculateSlope(a,b) < 0.5 && b.getXcoord() > a.getXcoord() ){
            direction = 1;
        }
        else if(calculateSlope(a,b) > 0.5 && calculateSlope(a,b) < 1 && b.getXcoord() > a.getXcoord() ){
            direction = 3;
        }
        else if(calculateSlope(a,b) > 0 && calculateSlope(a,b) < 0.5 && b.getXcoord() < a.getXcoord() ){
            direction = 2;
        }
        else if(calculateSlope(a,b) > 0.5 && calculateSlope(a,b) < 1 && b.getXcoord() > a.getXcoord() ){
            direction = 4;
        }
        return direction;
    }

    //Larry - to determine whether the path is go vertical or go horizontal
    private  boolean isHorizontal(double A){
        if(A == 0){
            return true;
        }
        else{
            return false;
        }
    }

    //Larry - to determine whether the path is go vertical or go horizontal
    private  boolean isVertical(double A){
        if(A > 9999 || A < -9999){
            return true;
        }
        else{
            return false;
        }
    }

    //Larry - determine the direction of path
    private int directionPath(Location a, Location b){
        int xa = a.getXcoord();
        int ya = a.getYcoord();
        int xb = b.getXcoord();
        int yb = b.getYcoord();

        //           1
        //      8         2
        //   7               3
        //      6         4
        //           5

        if(xb > xa){
            if(ya > yb){
                return 2;
            }
            else if(ya == yb){
                return 3;
            }
            else{
                return 4;
            }
        }
        else if(xb < xa){
            if(ya > yb){
                return 8;
            }
            else if (ya == yb){
                return 7;
            }
            else{
                return 6;
            }
        }
        else{
             if(ya < yb){
                 return 5;
             }
             else {
                 return 1;
             }
        }

    }






    //Larry - Print the textual direction based on the path return from algorithm
    private String printPath(ArrayList<Location> A){
        System.out.println(A);
        int curDirection = 0;
        int nextDirection = 0;
        String text = "";

        int d = 0; // count for the start location for exact location
        //same start and end location
        if(A.size() == 2 && A.get(0) == A.get(1)){
            System.out.println("You are already at your destination");
            text += "You are already at your destination \n";
            return text;
        }
        System.out.println("Begin from " + A.get(0).getLongName());
        text += "Begin from " + A.get(0).getLongName();
        //when size is two, but two location are different
        if(A.size() == 2){
            if(A.get(0).getNodeType() =="STAI" || A.get(0).getNodeType() == "ELEV"){
                    if(A.get(1).getNodeType() == "STAI" ||A.get(1).getNodeType() == "ELEV" ){
                        System.out.println("Go to floor " + A.get(1).getFloor() + " by " + A.get(1).getNodeType());
                        text += "Go to floor " + A.get(1).getFloor() + " by " + A.get(1).getNodeType() + "\n";

                            return text;
                }
                else{
                        System.out.println("Go straight to " + A.get(1).getLongName() + " (" +
                            convertToExact(A.get(0).findDistance(A.get(1))) + " ft) \n");
                        text += "Go straight to " + A.get(1).getLongName() + " (" +
                            convertToExact(A.get(0).findDistance(A.get(1))) + " ft) \n";
                        return text;
                }
            }
        }


        //when the size of path is at least 3 locations
        for(int i = 0; i<A.size() - 2; i++){
            Location start = A.get(d);
            Location a = A.get(i);
            Location b = A.get(i+1);
            Location c = A.get(i+2);
            if(b.getNodeType().equals("STAI")||b.getNodeType().equals("ELEV")){
                System.out.println("Go to floor "+ c.getFloor() + " by " + b.getNodeType());
                text += "Go to " + c.getFloor() + " by " + b.getNodeType() + "\n";
                i = i +2;
                if(i == A.size()-1){
                    System.out.println("You are at your destination");
                    text += "You are at your destination \n";
                }
                else if(i == A.size() -2){
                    System.out.println("Go straight to your destination" + c.getLongName());
                    text += "Go straight to your destination" + c.getLongName() + "\n";
                }
                else{
                    a = A.get(i);
                    b = A.get(i+1);
                    c = A.get(i+2);

                }

            }


            double angle = calculateAngle(a,b,c);
            if(angle < 110 && angle > 70){
                curDirection = directionPath(a,b);
                nextDirection = directionPath(b,c);

                Point2D point = sceneGestures.getImageLocation();
                Circle TurningCircle = new Circle();

                //Setting the properties of the circle
                TurningCircle.setCenterX((b.getXcoord()-point.getX())*0.137*sceneGestures.getImageScale());
                TurningCircle.setCenterY((b.getYcoord()-point.getY())*0.137*sceneGestures.getImageScale());
                TurningCircle.setRadius(Math.max(2.5,2.5f*(sceneGestures.getImageScale()/5)));
                TurningCircle.setStroke(Color.YELLOW);
                TurningCircle.setFill(Color.YELLOW);

                anchorPanePath.getChildren().add(TurningCircle);
                circles.add(TurningCircle);



                System.out.println("Go straight to " + b.getLongName()
                        + " (" + convertToExact(start.findDistance(b)) + " ft) " );
                text += "Go straight to " + b.getLongName()
                        + " (" + convertToExact(start.findDistance(b)) + " ft) \n";

                //- -> + , x+ : left
                d = i + 1;
               // System.out.println("cur " + curDirection);
               // System.out.println("next " + nextDirection);
                double slopeAB = calculateSlope(a,b);
                double slopeBC = calculateSlope(b,c);
                if(curDirection == nextDirection){
                    if(curDirection == 2 || curDirection == 6){
                        if(Math.abs(slopeBC)> Math.abs(slopeAB)){
                            System.out.println("Turn left");
                            text += "Turn left\n";
                        }
                        else{
                            System.out.println("Turn right");
                            text += "Turn right\n";
                        }
                    }
                    else if(curDirection == 4 || curDirection ==8){
                        if(Math.abs(slopeBC)> Math.abs(slopeAB)){
                            System.out.println("Turn right");
                            text += "Turn right\n";
                        }
                        else{
                            System.out.println("Turn left");
                            text += "Turn left\n";
                        }

                    }

                }
                else if((curDirection == 2 && nextDirection ==6) || (curDirection == 6 && nextDirection ==2)){
                    if(Math.abs(slopeBC)>Math.abs(slopeAB)){
                        System.out.println("Turn right");
                        text += "Turn right\n";
                    }
                    else{
                        System.out.println("Turn left");
                        text += "Turn left\n";
                    }

                }

                else if((curDirection == 8 && nextDirection ==4) || (curDirection == 6 && nextDirection ==2)){
                    if(Math.abs(slopeBC)>Math.abs(slopeAB)){
                        System.out.println("Turn left");
                        text += "Turn left\n";
                    }
                    else{
                        System.out.println("Turn right");
                        text += "Turn right\n";
                    }

                }

                else if(curDirection <= 5){
                    if(nextDirection < curDirection + 4 && nextDirection > curDirection){
                        System.out.println("Turn right");
                        text += "Turn right\n";
                    }
                    else {
                        System.out.println("Turn left");
                        text += "Turn left\n";
                    }
                }
                else{
                    if(curDirection == 6){
                        if(nextDirection == 7 || nextDirection == 8 || nextDirection == 1){
                            System.out.println("Turn right");
                            text += "Turn right\n";
                        }
                        if(nextDirection == 5 || nextDirection == 4 || nextDirection == 3){
                            System.out.println("Turn left");
                            text += "Turn left\n";
                        }


                    }
                    else if (curDirection ==7){
                        if(nextDirection == 8 || nextDirection == 1 || nextDirection == 2){
                            System.out.println("Turn right");
                            text += "Turn right\n";
                        }
                        else if(nextDirection == 6 || nextDirection == 5 || nextDirection == 4){
                            System.out.println("Turn left");
                            text += "Turn left\n";
                        }
                        else {

                        }

                    }
                    else if(curDirection ==8){
                        if(nextDirection == 1 || nextDirection == 2 || nextDirection == 3){
                            System.out.println("Turn right");
                            text += "Turn right\n";
                        }
                        else if(nextDirection == 5 || nextDirection == 6 || nextDirection == 7){
                            System.out.println("Turn left");
                            text += "Turn left\n";
                        }
                        else {

                        }
                    }
                    else{
                        //nothing handle error in case
                    }
                }



//                double slopeAB = calculateSlope(a,b);
//                double slopeBC = calculateSlope(b,c);
//                if(curDirection < 4){
//                    if(nextDirection - curDirection < 4){
//                        System.out.println("Turn right");
//                    }
//                    else {
//                        System.out.println("Turn left");
//                    }
//                }
//                else{
//                    if(nextDirection - curDirection < 0){
//                        System.out.println("Turn left");
//                    }
//                    else {
//                        System.out.println("Turn right");
//                    }
//
//                }


//                if(curDirection == 2 && nextDirection ==1){
//                    if(b.getYcoord() < a.getYcoord()){
//                        if(b.getYcoord() > c.getYcoord()){
//                            System.out.println("Turn left");
//                        }
//                        else{
//                            System.out.println("Turn right");
//                        }
//                    }
//                    else{
//                        if(b.getYcoord() > c.getYcoord()){
//                            System.out.println("Turn left");
//                        }
//                        else{
//                            System.out.println("Turn right");
//                        }
//
//                    }
//
//                }
//                System.out.println("SlopeAB " + slopeAB);
//                System.out.println("SlopeBC " + slopeBC);
//                System.out.println("Direction" + direction );

//                    if (slopeAB > slopeBC && c.getXcoord() < a.getXcoord()) {
//                        if (direction == 1) {
//                            System.out.println("Turn left");
//                        } else {
//                            System.out.println("Turn right");
//                        }
//                    }
//                    else if (slopeAB > slopeBC && c.getXcoord() > a.getXcoord()) {
//                        if (direction == 1) {
//                            System.out.println("Turn right");
//                        } else {
//                            System.out.println("Turn left");
//                        }
//
//                    }
//                    else if (slopeAB < slopeBC && c.getXcoord() < a.getXcoord()) {
//                        if (direction == 1) {
//                            System.out.println("Turn right");
//                        } else {
//                            System.out.println("Turn left");
//                        }
//                    }
//                    else if (slopeAB < slopeBC && c.getXcoord() > a.getXcoord()) {
//                        if (direction == 1) {
//                            System.out.println("Turn left");
//                        } else {
//                            System.out.println("Turn right");
//                        }
//                    }
//                    else {
//                        System.out.println("Turn");
//                        System.out.println("AB " + slopeAB);
//                        System.out.println("BC " + slopeBC);
//                        System.out.println("c " + c.getXcoord());
//                        System.out.println("b " + b.getXcoord());
//                    }


                }
                    if(i == A.size() - 3){
                         System.out.println("Go straight to your destination " + A.get(A.size()-1).getLongName() +
                        " (" + convertToExact(b.findDistance(c)) + " ft) " );
                        text += "Go straight to your destination " + A.get(A.size()-1).getLongName() +
                                " (" + convertToExact(b.findDistance(c)) + " ft) \n";
                        return text;
            }
        }

        return text;
    }
}
