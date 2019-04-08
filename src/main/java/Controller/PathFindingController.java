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
    private RadioButton bathroomRadButton;
    @FXML
    private RadioButton cafeRadButton;
    @FXML
    private RadioButton eleRadButton;
    @FXML
    private RadioButton stairsRadButton;

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
    private final ObservableList<Location> data = FXCollections.observableArrayList();
    private HashMap<String, Location> lookup = new HashMap<String, Location>();
    private final ObservableList<Location> noHallStart = FXCollections.observableArrayList();
    private final ObservableList<Location> noHallEnd = FXCollections.observableArrayList();
    private final ObservableList<String> filterList = FXCollections.observableArrayList();
    private final ObservableList<String> floorList = FXCollections.observableArrayList();


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

    @FXML public void clickedL2(){
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
        initializeTable(na, ea);
        if(single.getNum() == 1){
            PathFindStartDrop.setItems(data);
            PathFindEndDrop.setItems(data);
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
        Path path = findAbstractPath(astar, startNode, endNode);

        displayPath(path.getPath(), startNode, endNode);
    }

    public void displayPath(ArrayList<Location> path, Location startNode, Location endNode){
        path.add(0,startNode);

        for (Circle c: circles) {
            anchorPanePath.getChildren().remove(c);
        }
        for (Line l: lines) {
            anchorPanePath.getChildren().remove(l);
        }

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

        sceneGestures.setDrawPath(StartCircle, EndCircle, lines);
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
                    if (lookup.containsKey(na.getNodeInformation(nodeID).get(0))) {
                        Edge e = new Edge(Integer.toString(j), testx, lookup.get(nodeID));
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
                for (int j = 0; j < data.size(); j++) {
                    if (!(data.get(j).getNodeType().contains("HALL"))) {
                        noHallStart.add(data.get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < data.size(); j++) {
                    if (!(data.get(j).getNodeType().contains("HALL"))) {
                        noHallEnd.add(data.get(j));
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
                for (int j = 0; j < data.size(); j++) {
                    if (!(data.get(j).getNodeType().contains("HALL"))) {
                        noHallStart.add(data.get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < data.size(); j++) {
                    if (!(data.get(j).getNodeType().contains("HALL"))) {
                        noHallEnd.add(data.get(j));
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
                for (int j = 0; j < data.size(); j++) {
                    if (!(data.get(j).getNodeType().contains("HALL"))) {
                        noHallStart.add(data.get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < data.size(); j++) {
                    if (!(data.get(j).getNodeType().contains("HALL"))) {
                        noHallEnd.add(data.get(j));
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
                for (int j = 0; j < data.size(); j++) {
                    if (!(data.get(j).getNodeType().contains("HALL")) && (data.get(j).getFloor().equals(pickedFloor))) {
                        noHallStart.add(data.get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < data.size(); j++) {
                    if (!(data.get(j).getNodeType().contains("HALL")) && (data.get(j).getFloor().equals(pickedFloor))) {
                        noHallEnd.add(data.get(j));
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
                for (int j = 0; j < data.size(); j++) {
                    if ((data.get(j).getNodeType().contains(type))) {
                        noHallStart.add(data.get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < data.size(); j++) {
                    if ((data.get(j).getNodeType().contains(type))) {
                        noHallEnd.add(data.get(j));
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
                for (int j = 0; j < data.size(); j++) {
                    if (!(data.get(j).getNodeType().contains("HALL"))) {
                        noHallStart.add(data.get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < data.size(); j++) {
                    if (!(data.get(j).getNodeType().contains("HALL"))) {
                        noHallEnd.add(data.get(j));
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
                for (int j = 0; j < data.size(); j++) {
                    if ((data.get(j).getNodeType().contains(type)) || (data.get(j).getNodeType().contains(type2))) {
                        noHallStart.add(data.get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < data.size(); j++) {
                    if ((data.get(j).getNodeType().contains(type)) || (data.get(j).getNodeType().contains(type2))) {
                        noHallEnd.add(data.get(j));
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
                for (int j = 0; j < data.size(); j++) {
                    if ((data.get(j).getNodeType().contains(type)) || (data.get(j).getNodeType().contains(type2))) {
                        noHallStart.add(data.get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < data.size(); j++) {
                    if ((data.get(j).getNodeType().contains(type)) || (data.get(j).getNodeType().contains(type2))) {
                        noHallEnd.add(data.get(j));
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
                for (int j = 0; j < data.size(); j++) {
                    if ((data.get(j).getNodeType().contains(type)) && (data.get(j).getFloor().equals(pickedFloor)) || ((data.get(j).getNodeType().contains(type2)) && (data.get(j).getFloor().equals(pickedFloor)))) {
                        noHallStart.add(data.get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < data.size(); j++) {
                    if ((data.get(j).getNodeType().contains(type)) && (data.get(j).getFloor().equals(pickedFloor)) || ((data.get(j).getNodeType().contains(type2)) && (data.get(j).getFloor().equals(pickedFloor)))) {
                        noHallEnd.add(data.get(j));
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
                for (int j = 0; j < data.size(); j++) {
                    if ((data.get(j).getNodeType().contains(type))) {
                        noHallStart.add(data.get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < data.size(); j++) {
                    if ((data.get(j).getNodeType().contains(type))) {
                        noHallEnd.add(data.get(j));
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
                for (int j = 0; j < data.size(); j++) {
                    if ((data.get(j).getNodeType().contains(type)) && (data.get(j).getFloor().equals(pickedFloor))) {
                        noHallStart.add(data.get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < data.size(); j++) {
                    if ((data.get(j).getNodeType().contains(type)) && (data.get(j).getFloor().equals(pickedFloor))) {
                        noHallEnd.add(data.get(j));
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
        for (Location x : lookup.values()) {
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



    /** GRACE MADE THIS
     * display all nodes whose nodeType contains("keyword") && nodeFloor = currentKioskFloor
     */
    public void displayPOINodes(String keyword){
        ArrayList<Location> nodes = new ArrayList<Location>();
        //want to fill nodes w/ floor = currrentKioskFloor && nodeLongName? or nodeType? .contains(keyword)
        int temp = 0;
        Point2D point = sceneGestures.getImageLocation();
        for(int i=0; i<data.size(); i++){
            //if nodetype contains keyword
            if(data.get(i).getNodeType().contains(keyword)  && data.get(i).getFloor() == "2"/* && data.get(i).getFloor() == kioskNode.getFloor*/){
                nodes.add(data.get(i));

                Circle thisCircle = new Circle();

                anchorPanePath.getChildren().add(thisCircle);

                //Setting the properties of the circle
                thisCircle.setCenterX((nodes.get(temp).getXcoord()-point.getX())*0.137*sceneGestures.getImageScale());
                thisCircle.setCenterY((nodes.get(temp).getYcoord()-point.getY())*0.137*sceneGestures.getImageScale());
                thisCircle.setRadius(Math.max(2.5,2.5f*(sceneGestures.getImageScale()/5)));
                thisCircle.setStroke(Color.web("#f5d96b"));
                thisCircle.setFill(Color.web("#f5d96b"));

                circles.add(thisCircle);
                temp++;
            }
        }
        //System.out.println("displaying all "+ keyword +"s on this floor");

    }

    /** GRACE MADE THIS
     * display path to nearest keyword
     */
    public void displayClosestPOI(String keyword){
        ArrayList<Location> nodes = new ArrayList<Location>();
        Location kioskTemp = data.get(0);
        //want to fill nodes w/ relevent POI

        for(int i=0; i<data.size(); i++) {
            //if nodetype contains keyword
            if (data.get(i).getNodeType().contains(keyword) && data.get(i).getFloor() == kioskTemp.getFloor()) {
                nodes.add(data.get(i));
            }
        }

        //TODO: FIX THIS & ACTUALLY GET THE CLOSEST ONE
        long startTime;
        long endTime;
        long currentCountTime;

        //just to initailize closestTime
        startTime = System.nanoTime();
        findPath(kioskTemp, nodes.get(0));
        endTime = System.nanoTime();

        long closestTime = (endTime - startTime);
        Location closestLOC = nodes.get(0);
        Path closestPath = findPath(kioskTemp, nodes.get(0)); //?
        //finding closest POI
        for(int i=0; i<nodes.size(); i++){
            startTime = System.nanoTime();
            findPath(kioskTemp, nodes.get(i));
            endTime = System.nanoTime();

            currentCountTime = (endTime - startTime);

            if(closestTime > currentCountTime){
                closestPath = findPath(kioskTemp, nodes.get(i));
                closestLOC = nodes.get(i);
            }

        }


        displayPath(closestPath.getPath(), kioskTemp, closestLOC);
        //System.out.println("just pretend it prints out the path to closest");
    }

    /** GRACE MADE THIS
     *display and find closest bathroom
     */
    @FXML
    private void bathRadButtPressed(){
        //when pressed, change color to #f5d96b (gold/yellow), to do later
        //display and find closest bathroom
        //System.out.println("find closest bathroom selected");
        for (Circle c: circles) {
            anchorPanePath.getChildren().remove(c);
        }
        for (Line l: lines) {
            anchorPanePath.getChildren().remove(l);
        }
        if(bathroomRadButton.isSelected()) {
            bathroomRadButton.setTextFill(Color.web("#f5d96b"));
            cafeRadButton.setSelected(false);
            cafeRadButton.setTextFill(Color.web("#ffffff"));
            eleRadButton.setSelected(false);
            eleRadButton.setTextFill(Color.web("#ffffff"));
            stairsRadButton.setSelected(false);
            stairsRadButton.setTextFill(Color.web("#ffffff"));

            displayClosestPOI("REST");
            displayPOINodes("REST");
            // for some reason displaying poi nodes cannot go before displaying the closest path
        }
        else if(!bathroomRadButton.isSelected()){
            bathroomRadButton.setTextFill(Color.web("#ffffff"));
            for (Circle c: circles) {
                anchorPanePath.getChildren().remove(c);
            }
        }
    }
    /** GRACE MADE THIS
     *display and find closest cafe/vending/retail
     */
    @FXML
    private void cafeRadButtPressed(){
        //when pressed, change color to #f5d96b (gold/yellow)
        //display and find closest cafe - nodeType is RETL
        //System.out.println("find closest retail/food selected");
        for (Circle c: circles) {
            anchorPanePath.getChildren().remove(c);
        }
        for (Line l: lines) {
            anchorPanePath.getChildren().remove(l);
        }
        if(cafeRadButton.isSelected()) {
            cafeRadButton.setTextFill(Color.web("#f5d96b"));
            bathroomRadButton.setSelected(false);
            bathroomRadButton.setTextFill(Color.web("#ffffff"));
            eleRadButton.setSelected(false);
            eleRadButton.setTextFill(Color.web("#ffffff"));
            stairsRadButton.setSelected(false);
            stairsRadButton.setTextFill(Color.web("#ffffff"));

            displayClosestPOI("RETL");
            displayPOINodes("RETL");
        }
        if(!cafeRadButton.isSelected()){
            cafeRadButton.setTextFill(Color.web("#ffffff"));
            for (Circle c: circles) {
                anchorPanePath.getChildren().remove(c);
            }
        }
    }
    /** GRACE MADE THIS
     *display and find closest elevator
     */
    @FXML
    private void eleRadButtPressed(){
        //when pressed, change color to #f5d96b (gold/yellow)
        //display and find closest elevator
        //System.out.println("find closest elevator selected");
        for (Circle c: circles) {
            anchorPanePath.getChildren().remove(c);
        }
        for (Line l: lines) {
            anchorPanePath.getChildren().remove(l);
        }
        if(eleRadButton.isSelected()) {
            eleRadButton.setTextFill(Color.web("#f5d96b"));
            cafeRadButton.setSelected(false);
            cafeRadButton.setTextFill(Color.web("#ffffff"));
            bathroomRadButton.setSelected(false);
            bathroomRadButton.setTextFill(Color.web("#ffffff"));
            stairsRadButton.setSelected(false);
            stairsRadButton.setTextFill(Color.web("#ffffff"));

            displayClosestPOI("ELEV");
            displayPOINodes("ELEV");
        }
        if(!eleRadButton.isSelected()){
            eleRadButton.setTextFill(Color.web("#ffffff"));
            for (Circle c: circles) {
                anchorPanePath.getChildren().remove(c);
            }
        }
    }
    /** GRACE MADE THIS
     *display and find closest stairs
     */
    @FXML
    private void stairsRadButtPressed(){
        //when pressed, change color to #f5d96b (gold/yellow)
        //display and find closest stairs
        //System.out.println("find closest stairs selected");
        for (Circle c: circles) {
            anchorPanePath.getChildren().remove(c);
        }
        for (Line l: lines) {
            anchorPanePath.getChildren().remove(l);
        }
        if(stairsRadButton.isSelected()) {
            stairsRadButton.setTextFill(Color.web("#f5d96b"));
            cafeRadButton.setSelected(false);
            cafeRadButton.setTextFill(Color.web("#ffffff"));
            eleRadButton.setSelected(false);
            eleRadButton.setTextFill(Color.web("#ffffff"));
            bathroomRadButton.setSelected(false);
            bathroomRadButton.setTextFill(Color.web("#ffffff"));

            displayClosestPOI("STAI");
            displayPOINodes("STAI");
        }
        if(!stairsRadButton.isSelected()){
            stairsRadButton.setTextFill(Color.web("#ffffff"));
            for (Circle c: circles) {
                anchorPanePath.getChildren().remove(c);
            }
        }
    }
}