package Controller;

import API.UpdateLocationThread;
import Access.EdgesAccess;
import Access.NodesAccess;
import com.jfoenix.controls.JFXRadioButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import Object.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Duration;

public class EditLinkBetweenFloorsController {

    @FXML
    private Button clearButton;

    @FXML
    private Stage thestage;

    @FXML
    private Button PathFindBack;

    @FXML
    private Button PathFindSubmit;

    @FXML
    private Button displayNodes;

    @FXML
    private ComboBox<String> Filter;

    @FXML
    private ComboBox<String> Floor;

    @FXML
    private AnchorPane anchorPaneWindow;

    @FXML
    private Pane mapPane;

    @FXML
    private Pane upperPane;

    @FXML
    private Pane lowerPane;

    @FXML
    private ImageView Map;

    @FXML
    private ImageView MapUpper;

    @FXML
    private ImageView MapLower;

    @FXML
    private Label node1Name;
    @FXML
    private Label node2Name;
    @FXML
    private Label node1X;
    @FXML
    private Label node2X;
    @FXML
    private Label node1Y;
    @FXML
    private Label node2Y;

    private AnchorPane anchorPaneMain = new AnchorPane();
    private SceneGesturesForEditing sceneGestureMain;
    private final DoubleProperty zoomPropertyMain = new SimpleDoubleProperty(1.0d);
    private final DoubleProperty deltaYMain = new SimpleDoubleProperty(0.0d);

    private AnchorPane anchorPaneLower = new AnchorPane();
    private SceneGesturesForEditing sceneGestureLower;
    private final DoubleProperty zoomPropertyLower = new SimpleDoubleProperty(1.0d);
    private final DoubleProperty deltaYLower = new SimpleDoubleProperty(0.0d);

    private AnchorPane anchorPaneUpper = new AnchorPane();
    private SceneGesturesForEditing sceneGestureUpper;
    private final DoubleProperty zoomPropertyUpper = new SimpleDoubleProperty(1.0d);
    private final DoubleProperty deltaYUpper = new SimpleDoubleProperty(0.0d);

    private HashMap<String, String> mapURLlookup = new HashMap<String, String>();

    private NodesAccess na;
    private EdgesAccess ea;

    private final ObservableList<String> filterList = FXCollections.observableArrayList();
    private final ObservableList<String> floorList = FXCollections.observableArrayList();
    private Singleton single = Singleton.getInstance();


    private ArrayList<String> mapURLs = new ArrayList<String>();
    private ArrayList<Circle> circles = new ArrayList<Circle>();
    private ArrayList<Line> lines = new ArrayList<Line>();
    private ArrayList<Circle> circlesUpper = new ArrayList<Circle>();
    private ArrayList<Line> linesUpper = new ArrayList<Line>();
    private ArrayList<Circle> circlesLower= new ArrayList<Circle>();
    private ArrayList<Line> linesLower = new ArrayList<Line>();

    private boolean selectingFirst = true;
    private boolean isSelectingSecond = false;

    @FXML
    private JFXRadioButton singleFloorSelect;
    @FXML
    private JFXRadioButton multiFloorSelect;

    private Location focusLoc;
    private Location toConnectLoc;

    String pickedFloor = "test";
    String type = "test";

    String currentMap = "";
    String currentMapAbove = "";
    String currentMapBelow = "";

    Timeline timeout;

    public void initialize() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        multiFloorSelect.setSelected(false);
        singleFloorSelect.setSelected(true);

        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()){
                    try{
                        single.setLastTime();
                        single.setDoPopup(true);
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        Parent sceneMain = loader.load();
                        HomeScreenController controller = loader.<HomeScreenController>getController();
                        single.setLastTime();
                        controller.displayPopup();
                        single.setLastTime();

                        Stage thisStage = (Stage) node2X.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
                        timeout.stop();
                    } catch (IOException io){
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));
        timeout.setCycleCount(Timeline.INDEFINITE);
        timeout.play();

        na = new NodesAccess();
        ea = new EdgesAccess();
        PathFindSubmit.setDisable(true);
        displayNodes.setDisable(true);
        filter();
        floor();
        Filter.setItems(filterList);
        Floor.setItems(floorList);
        map();
        //initializeTable(na, ea);
        sceneGestureMain = setUpImage(anchorPaneMain, sceneGestureMain, Map, zoomPropertyMain, deltaYMain, mapPane, 0, 0);
        sceneGestureUpper = setUpImage(anchorPaneUpper, sceneGestureUpper, MapUpper, zoomPropertyUpper, deltaYUpper, upperPane, 0, 0);
        sceneGestureLower = setUpImage(anchorPaneLower, sceneGestureLower, MapLower, zoomPropertyLower, deltaYLower, lowerPane, 0, 0);


        sceneGestureMain.setDrawPath(circles, lines);
        sceneGestureUpper.setDrawPath(circlesUpper, linesUpper);
        sceneGestureLower.setDrawPath(circlesLower, linesLower);
        MapLower.setVisible(false);
        MapUpper.setVisible(false);
    }

    @FXML
    /**
     * @author Gabe
     * adds all the URLs to the list, starts with 3rd floor since that is the next floor to come
     */
    public void map() {
        mapURLlookup.put("3", "/SoftEng_UI_Mockup_Pics/03_thethirdfloor.png");
        mapURLlookup.put("2", "/SoftEng_UI_Mockup_Pics/02_thesecondfloor.png");
        mapURLlookup.put("1", "/SoftEng_UI_Mockup_Pics/01_thefirstfloor.png");
        mapURLlookup.put("G", "/SoftEng_UI_Mockup_Pics/00_thegroundfloor.png");
        mapURLlookup.put("L1", "/SoftEng_UI_Mockup_Pics/00_thelowerlevel1.png");
        mapURLlookup.put("L2", "/SoftEng_UI_Mockup_Pics/00_thelowerlevel2.png");
    }

    @FXML
    private void backPressed() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        thestage = (Stage) PathFindBack.getScene().getWindow();
        AnchorPane root;
        if (single.isLoggedIn()) {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EditLocation.fxml"));

            Parent sceneMain = loader.load();

            Stage theStage = (Stage) PathFindBack.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);
            return;
        } else {
            single.setDoPopup(true);
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

            Parent sceneMain = loader.load();

            Stage theStage = (Stage) PathFindBack.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);
            return;
        }
    }

    public HashMap<String, Location> getLookup() {
        return single.lookup;
    }

    @FXML
    private void submitPressed() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        String start = focusLoc.getLocID();
        String end = toConnectLoc.getLocID();
        ea.addEdge(start, end);
        Edge e = new Edge(focusLoc.getLocID()+"_"+toConnectLoc.getLocID(), focusLoc, toConnectLoc);
        single.addEdge(focusLoc, toConnectLoc,  e);
        //UpdateLocationThread ul = new UpdateLocationThread();
        //ul.start();
        //single.setData();
    }

    @FXML
    /**
     * @author Gabe
     * reads the input for the room type combo box
     */
    private void filterType() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if (Filter.getValue() == ("Stairs"))
        if (Filter.getValue() == ("Elevators")) {
            type = "ELEV";
        }
    }

    @FXML
    private void floor() {
        floorList.add("3");
        floorList.add("2");
        floorList.add("1");
        floorList.add("G");
        floorList.add("L1");
        floorList.add("L2");
    }

    @FXML
    /**
     * @author Gabe
     * Adds the fields that can be used to filter locations by type
     */
    private void filter() {
        filterList.add("Stairs");
        filterList.add("Elevators");
        filterList.add("Restrooms");
        filterList.add("Conference Rooms");
        filterList.add("Labs");
        filterList.add("Departments");
        filterList.add("Information");
        filterList.add("Exit");
        filterList.add("Retail");
        filterList.add("Halls");
        filterList.add("ALL");
    }

    @FXML
    /**
     * @author Gabe
     * reads the input for the floor combo box
     */
    private void filterFloor() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if (Floor.getValue() == "G") {
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

    /*
    Author: PJ Mara. Sets up a pan and scroll image!
     */
    private SceneGesturesForEditing setUpImage(AnchorPane anchorPanePath, SceneGesturesForEditing sceneGestures, ImageView Map1, DoubleProperty zoomProperty, DoubleProperty deltaY, Pane pane, int x, int y) {
        anchorPanePath.setLayoutX(x);
        anchorPanePath.setLayoutY(y);
        anchorPanePath.setPrefSize(420, 285.6);
        Rectangle clip = new Rectangle();
        //clip.widthProperty().bind(Map1.fitWidthProperty());
        //clip.heightProperty().bind(Map1.fitHeightProperty());
        clip.setX(0);
        clip.setY(0);
        clip.setWidth(Map1.getFitWidth());
        clip.setHeight(Map1.getFitHeight());


        anchorPanePath.setClip(clip);

        PanAndZoomPane zoomPaneImage = new PanAndZoomPane();

        zoomProperty.bind(zoomPaneImage.myScale);
        deltaY.bind(zoomPaneImage.deltaY);
        zoomPaneImage.getChildren().add(Map1);

        sceneGestures = new SceneGesturesForEditing(zoomPaneImage, Map1);
        anchorPanePath.addEventFilter(MouseEvent.MOUSE_CLICKED, getOnMouseClickedEventHandler());
        anchorPanePath.addEventFilter(MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        anchorPanePath.addEventFilter(MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        anchorPanePath.addEventFilter(ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        zoomPaneImage.setLayoutX(x);
        zoomPaneImage.setLayoutY(y);

        Map1.fitWidthProperty().bind(pane.widthProperty());
        Map1.fitHeightProperty().bind(pane.heightProperty());

        pane.getChildren().add(zoomPaneImage);
        pane.getChildren().add(anchorPanePath);
        sceneGestures.reset(Map1, Map1.getImage().getWidth(), Map1.getImage().getHeight());

        return sceneGestures;
    }

    @FXML
    private void typeSelected() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if (Filter.getValue() != null) {
            //System.out.println(Filter);
            if (Filter.getValue().equals("Stairs")) {
                type = "STAI";
            } else if (Filter.getValue().equals("Restrooms")) {
                type = "REST";
            } else if (Filter.getValue().equals("Elevators")) {
                type = "ELEV";
            } else if (Filter.getValue().equals("Conference Rooms")) {
                type = "CONF";
            } else if (Filter.getValue().equals("Labs")) {
                type = "LABS";
            } else if (Filter.getValue().equals("Departments")) {
                type = "DEPT";
            } else if (Filter.getValue().equals("Information")) {
                type = "INFO";
            } else if (Filter.getValue().equals("Exit")) {
                type = "EXIT";
            } else if (Filter.getValue().equals("Retail")) {
                type = "RETL";
            } else if (Filter.getValue().equals("Halls")) {
                type = "HALL";
            } else if (Filter.getValue().equals("ALL")) {
                type = "ALL";
            }
            if (Floor.getValue() != null) {
                displayNodes.setDisable(false);
            }
        }
        nodeDisplayPress();
    }

    @FXML
    private void floorSelected() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        if (Floor.getValue().equals("3")) {
            Map.setImage(new Image(mapURLlookup.get("3")));
            MapUpper.setImage(null);
            MapLower.setImage(new Image(mapURLlookup.get("2")));
            currentMap = "3";
            currentMapAbove = "";
            currentMapBelow = "2";
        } else if (Floor.getValue().equals("2")) {
            Map.setImage(new Image(mapURLlookup.get("2")));
            MapUpper.setImage(new Image(mapURLlookup.get("3")));
            MapLower.setImage(new Image(mapURLlookup.get("1")));
            currentMap = "2";
            currentMapAbove = "3";
            currentMapBelow = "1";
            //drawNodes(Map);
        } else if (Floor.getValue().equals("1")) {
            Map.setImage(new Image(mapURLlookup.get("1")));
            MapUpper.setImage(new Image(mapURLlookup.get("2")));
            MapLower.setImage(new Image(mapURLlookup.get("G")));
            currentMap = "1";
            currentMapAbove = "2";
            currentMapBelow = "G";
        } else if (Floor.getValue().equals("G")) {
            Map.setImage(new Image(mapURLlookup.get("G")));
            MapUpper.setImage(new Image(mapURLlookup.get("1")));
            MapLower.setImage(new Image(mapURLlookup.get("L1")));
            currentMap = "G";
            currentMapAbove = "1";
            currentMapBelow = "L1";
        } else if (Floor.getValue().equals("L1")) {
            Map.setImage(new Image(mapURLlookup.get("L1")));
            MapUpper.setImage(new Image(mapURLlookup.get("G")));
            MapLower.setImage(new Image(mapURLlookup.get("L2")));
            currentMap = "L1";
            currentMapAbove = "G";
            currentMapBelow = "L2";
        } else {
            Map.setImage(new Image(mapURLlookup.get("L2")));
            MapUpper.setImage(new Image(mapURLlookup.get("L1")));
            MapLower.setImage(null);
            currentMap = "L2";
            currentMapAbove = "L1";
            currentMapBelow = "";
        }
        if (type != "") {
            displayNodes.setDisable(false);
        }
        clearPressed();
        eraseNodes(anchorPaneLower, circlesLower);
        eraseNodes(anchorPaneUpper, circlesUpper);
        eraseNodes(anchorPaneMain, circles);
    }


    @FXML
    private void nodeDisplayPress() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        //displayingNodes = !displayingNodes;
        for (Circle c : circles) {
            anchorPaneMain.getChildren().remove(c);
        }
        for (Circle c : circlesUpper) {
            anchorPaneUpper.getChildren().remove(c);
        }
        for (Circle c : circlesLower) {
            anchorPaneLower.getChildren().remove(c);
        }

        circles.clear();
        circlesLower.clear();
        circlesUpper.clear();
        drawNodes();
        if (multiFloorSelect.isSelected()) {
            drawOtherFloors();
        }
        //sceneGestureMain.setDrawPath(circles, lines);
    }

    @FXML
    private void multiFloorClicked() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        singleFloorSelect.setSelected(false);
        multiFloorSelect.setSelected(true);
        MapUpper.setVisible(true);
        MapLower.setVisible(true);
        Filter.setValue(null);
        Filter.setPromptText("Select Type");
        filterList.remove("Stairs");
        filterList.remove("Elevators");
        filterList.remove("Restrooms");
        filterList.remove("Conference Rooms");
        filterList.remove("Labs");
        filterList.remove("Departments");
        filterList.remove("Information");
        filterList.remove("Exit");
        filterList.remove("Retail");
        filterList.remove("Halls");
        filterList.remove("ALL");
        filterList.add("Stairs");
        filterList.add("Elevators");
    }

    @FXML
    private void singleFloorClicked() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        multiFloorSelect.setSelected(false);
        singleFloorSelect.setSelected(true);
        MapLower.setVisible(false);
        MapUpper.setVisible(false);
        Filter.setValue(null);
        Filter.setPromptText("Select Type");
        eraseNodes(anchorPaneLower, circlesLower);
        eraseNodes(anchorPaneUpper, circlesUpper);
        filterList.remove("Stairs");
        filterList.remove("Elevators");
        filterList.remove("Restrooms");
        filterList.remove("Conference Rooms");
        filterList.remove("Labs");
        filterList.remove("Departments");
        filterList.remove("Information");
        filterList.remove("Exit");
        filterList.remove("Retail");
        filterList.remove("Halls");
        filterList.remove("ALL");
        filter();

    }

    private void drawNodes() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        //display all nodes on that floor!!!
        ArrayList<Location> nodes = new ArrayList<Location>();
        //want to fill nodes w/ floor = currrentFloor
        int temp = 0;
        double scaleRatio = Math.min(Map.getFitWidth() / Map.getImage().getWidth(),Map.getFitHeight()/Map.getImage().getHeight());
        Point2D point = sceneGestureMain.getImageLocation();
        for (int i = 0; i < single.getData().size(); i++) {
            boolean badCode = false;
            if (type == "ALL") {
                badCode = true;
            }
            if (single.getData().get(i).getFloor().equals(currentMap) && (single.getData().get(i).getNodeType().equals(type) || badCode)) {
                nodes.add(single.getData().get(i));

                Circle thisCircle = new Circle();

                anchorPaneMain.getChildren().add(thisCircle);

                //Setting the properties of the circle
                thisCircle.setCenterX((nodes.get(temp).getXcoord() - point.getX()) * scaleRatio * sceneGestureMain.getImageScale());
                thisCircle.setCenterY((nodes.get(temp).getYcoord() - point.getY()) * scaleRatio * sceneGestureMain.getImageScale());
                thisCircle.setRadius(Math.max(2.5, 2.5f * (sceneGestureMain.getImageScale() / 5)));
                thisCircle.setStroke(Color.web("RED")); //#f5d96b
                thisCircle.setFill(Color.web("RED"));

                circles.add(thisCircle);
                temp++;
            }
        }
    }

    /**
     * Mouse click handler
     */
    private EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            Singleton single = Singleton.getInstance();
            single.setLastTime();
            //todo make this coordinate based not hard coded
            SceneGesturesForEditing sceneGestures;
            if (event.getSceneY() < 417) {
                sceneGestures = sceneGestureMain;
            } else if (event.getSceneX() > 830) {
                sceneGestures = sceneGestureUpper;
            } else {
                sceneGestures = sceneGestureLower;
            }
            int getX;
            int getY;
            if (multiFloorSelect.isSelected()) {

                if (event.getSceneX() > mapPane.getLayoutX() && event.getSceneY() < mapPane.getLayoutY() + mapPane.getHeight()) {
                    Point2D mousePress = sceneGestures.imageViewToImage(Map, new Point2D(event.getX(), event.getY()));
                    sceneGestures.setMouseDown(mousePress);
                    getX = roundToFive((int) mousePress.getX());
                    getY = roundToFive((int) mousePress.getY());
                    String newQuery = na.getNodebyCoord(getX, getY, currentMap, type);
                    Point2D point = sceneGestureMain.getImageLocation();
                    if (newQuery != null) {
                        focusLoc = single.lookup.get(newQuery);
                        node1X.setText("" + focusLoc.getXcoord());
                        node1Y.setText("" + focusLoc.getYcoord());
                        node1Name.setText(focusLoc.getLocID());
                    }
                } else if (event.getSceneY() < upperPane.getLayoutY() + upperPane.getHeight() && !currentMap.equals("3")) {
                    Point2D mousePress = sceneGestures.imageViewToImage(MapUpper, new Point2D(event.getX(), event.getY()));
                    sceneGestures.setMouseDown(mousePress);
                    getX = roundToFive((int) mousePress.getX());
                    getY = roundToFive((int) mousePress.getY());
                    String newQuery = na.getNodebyCoord(getX, getY, currentMapAbove, type);

                    if (newQuery != null) {
                        toConnectLoc = single.lookup.get(newQuery);
                        node2X.setText("" + toConnectLoc.getXcoord());
                        node2Y.setText("" + toConnectLoc.getYcoord());
                        node2Name.setText(toConnectLoc.getLocID());
                    }
                } else {
                    Point2D mousePress = sceneGestures.imageViewToImage(MapLower, new Point2D(event.getX(), event.getY()));
                    sceneGestures.setMouseDown(mousePress);
                    getX = roundToFive((int) mousePress.getX());
                    getY = roundToFive((int) mousePress.getY());
                    String newQuery = na.getNodebyCoord(getX, getY, currentMapBelow, type);
                    if (newQuery != null) {
                        toConnectLoc = single.lookup.get(newQuery);
                        node2X.setText("" + toConnectLoc.getXcoord());
                        node2Y.setText("" + toConnectLoc.getYcoord());
                        node2Name.setText(toConnectLoc.getLocID());
                    }
                }
            }
            else {
                if (event.getSceneX() > mapPane.getLayoutX() && event.getSceneY() < mapPane.getLayoutY() + mapPane.getHeight()) {
                    Point2D mousePress = sceneGestures.imageViewToImage(Map, new Point2D(event.getX(), event.getY()));
                    sceneGestures.setMouseDown(mousePress);
                    getX = roundToFive((int) mousePress.getX());
                    getY = roundToFive((int) mousePress.getY());
                    String newQuery;
                    if (type != "ALL") {
                        newQuery = na.getNodebyCoord(getX, getY, currentMap, type);
                    }
                    else {
                        newQuery = na.getNodebyCoordNoType(getX, getY, currentMap, 7);
                    }
                    Point2D point = sceneGestureMain.getImageLocation();
                    if (newQuery != null && focusLoc == null) {
                        focusLoc = single.lookup.get(newQuery);
                        node1X.setText("" + focusLoc.getXcoord());
                        node1Y.setText("" + focusLoc.getYcoord());
                        node1Name.setText(focusLoc.getLocID());
                    }
                    else if (newQuery != null){
                        toConnectLoc = single.lookup.get(newQuery);
                        node2X.setText("" + toConnectLoc.getXcoord());
                        node2Y.setText("" + toConnectLoc.getYcoord());
                        node2Name.setText(toConnectLoc.getLocID());
                    }
                    else {
                        //System.out.println("QUERY FAILED");
                    }
                }

            }
            if (toConnectLoc != null && focusLoc != null) {
                PathFindSubmit.setDisable(false);
            }
        }

    };

    private EventHandler<MouseEvent> getOnMouseClickedEventHandler() {
        return onMouseClickedEventHandler;
    }

    private int roundToFive(int num) {
        int temp = num%5;
        if (temp<3)
            return num-temp;
        else
            return num+5-temp;
    }

    private void drawOtherFloors() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        //display all nodes on that floor!!!
        ArrayList<Location> nodes = new ArrayList<Location>();
        //want to fill nodes w/ floor = currrentFloor
        int temp = 0;
        double scaleRatio = Math.min(Map.getFitWidth() / Map.getImage().getWidth(), Map.getFitHeight()/Map.getImage().getHeight());
        Point2D point = sceneGestureUpper.getImageLocation();
        for (int i = 0; i < single.getData().size(); i++) {
            if (single.getData().get(i).getFloor().equals(currentMapAbove) && single.getData().get(i).getNodeType().equals(type)) {
                nodes.add(single.getData().get(i));

                Circle thisCircle = new Circle();

                anchorPaneUpper.getChildren().add(thisCircle);

                //Setting the properties of the circle
                thisCircle.setCenterX((nodes.get(temp).getXcoord() - point.getX()) * scaleRatio * sceneGestureUpper.getImageScale());
                thisCircle.setCenterY((nodes.get(temp).getYcoord() - point.getY()) * scaleRatio * sceneGestureUpper.getImageScale());
                thisCircle.setRadius(Math.max(2.5, 2.5f * (sceneGestureUpper.getImageScale() / 5)));
                thisCircle.setStroke(Color.web("RED")); //#f5d96b
                thisCircle.setFill(Color.web("RED"));

                circlesUpper.add(thisCircle);
                temp++;
            }
        }
        ArrayList<Location> nodesLower = new ArrayList<Location>();
        //want to fill nodes w/ floor = currrentFloor
        temp = 0;
        Point2D pointLower = sceneGestureLower.getImageLocation();
        for (int i = 0; i < single.getData().size(); i++) {
            if (single.getData().get(i).getFloor().equals(currentMapBelow) && single.getData().get(i).getNodeType().equals(type)) {
                nodesLower.add(single.getData().get(i));

                Circle thisCircle = new Circle();

                anchorPaneLower.getChildren().add(thisCircle);

                //Setting the properties of the circle
                thisCircle.setCenterX((nodesLower.get(temp).getXcoord() - pointLower.getX()) * scaleRatio * sceneGestureLower.getImageScale());
                thisCircle.setCenterY((nodesLower.get(temp).getYcoord() - pointLower.getY()) * scaleRatio * sceneGestureLower.getImageScale());
                thisCircle.setRadius(Math.max(2.5, 2.5f * (sceneGestureLower.getImageScale() / 5)));
                thisCircle.setStroke(Color.web("RED")); //#f5d96b
                thisCircle.setFill(Color.web("RED"));

                circlesLower.add(thisCircle);
                temp++;
            }
        }
    }

    @FXML
    private void clearPressed() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        focusLoc = null;
        toConnectLoc = null;


        node1X.setText("");
        node1Y.setText("");
        node1Name.setText("");

        node2X.setText("");
        node2Y.setText("");
        node2Name.setText("");
        PathFindSubmit.setDisable(true);

    }

    private void eraseNodes(AnchorPane anchorPanePath, ArrayList<Circle> circle){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        for (Circle c: circle){
            anchorPanePath.getChildren().remove(c);
        }

        circle.clear();
    }
}

