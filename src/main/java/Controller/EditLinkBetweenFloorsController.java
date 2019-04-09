package Controller;

import Access.EdgesAccess;
import Access.NodesAccess;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Object.*;

public class EditLinkBetweenFloorsController {

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

    private Location focusLoc;
    private Location toConnectLoc;

    String pickedFloor = "test";
    String type = "test";

    String currentMap = "";
    String currentMapAbove = "";
    String currentMapBelow = "";


    public void initialize() {
        na = new NodesAccess();
        ea = new EdgesAccess();
        PathFindSubmit.setDisable(false);
        displayNodes.setDisable(false);
        filter();
        floor();
        Filter.setItems(filterList);
        Floor.setItems(floorList);
        map();
        //initializeTable(na, ea);
        sceneGestureMain = setUpImage(anchorPaneMain, sceneGestureMain, Map, zoomPropertyMain, deltaYMain, 431, 115);
        sceneGestureUpper = setUpImage(anchorPaneUpper, sceneGestureUpper, MapUpper, zoomPropertyUpper, deltaYUpper, 830, 417);
        sceneGestureLower = setUpImage(anchorPaneLower, sceneGestureLower, MapLower, zoomPropertyLower, deltaYLower, 25, 417);


        sceneGestureMain.setDrawPath(circles, lines);
        sceneGestureUpper.setDrawPath(circlesUpper, linesUpper);
        sceneGestureLower.setDrawPath(circlesLower, linesLower);
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
        mapURLlookup.put("Ground", "/SoftEng_UI_Mockup_Pics/00_thegroundfloor.png");
        mapURLlookup.put("L1", "/SoftEng_UI_Mockup_Pics/00_thelowerlevel1.png");
        mapURLlookup.put("L2", "/SoftEng_UI_Mockup_Pics/00_thelowerlevel2.png");
    }

    @FXML
    private void backPressed() throws IOException {
        Singleton single = Singleton.getInstance();
        thestage = (Stage) PathFindBack.getScene().getWindow();
        AnchorPane root;
        if (single.isLoggedIn()) {
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
    private void submitPressed() {
        String start = focusLoc.getLocID();
        String end = toConnectLoc.getLocID();
        Edge e = new Edge(start+"_"+end,focusLoc, toConnectLoc);
        ea.addEdge(start, end);
        single.setData();
    }

    @FXML
    /**
     * @author Gabe
     * reads the input for the room type combo box
     */
    private void filterType() {
        if (Filter.getValue() == ("Stairs"))
        if (Filter.getValue() == ("Elevators")) {
            type = "ELEV";
        }
    }

    @FXML
    private void floor() {
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
        filterList.add("Stairs");
        filterList.add("Elevators");
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

    /*
    Author: PJ Mara. Sets up a pan and scroll image!
     */
    private SceneGesturesForEditing setUpImage(AnchorPane anchorPanePath, SceneGesturesForEditing sceneGestures, ImageView Map1, DoubleProperty zoomProperty, DoubleProperty deltaY, int x, int y) {
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

        anchorPaneWindow.getChildren().add(zoomPaneImage);
        anchorPaneWindow.getChildren().add(anchorPanePath);
        sceneGestures.reset(Map1, Map1.getImage().getWidth(), Map1.getImage().getHeight());

        return sceneGestures;
    }

    @FXML
    private void typeSelected() {
        if (Filter.getValue().equals("Stairs")) {
            type = "STAI";
        } else {
            type = "ELEV";
        }
    }

    @FXML
    private void floorSelected() {
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
            MapLower.setImage(new Image(mapURLlookup.get("Ground")));
            currentMap = "1";
            currentMapAbove = "2";
            currentMapBelow = "G";
        } else if (Floor.getValue().equals("Ground")) {
            Map.setImage(new Image(mapURLlookup.get("Ground")));
            MapUpper.setImage(new Image(mapURLlookup.get("1")));
            MapLower.setImage(new Image(mapURLlookup.get("L1")));
            currentMap = "G";
            currentMapAbove = "1";
            currentMapBelow = "L1";
        } else if (Floor.getValue().equals("L1")) {
            Map.setImage(new Image(mapURLlookup.get("L1")));
            MapUpper.setImage(new Image(mapURLlookup.get("Ground")));
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
    }



    @FXML
    private void nodeDisplayPress() {
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
        drawOtherFloors();
        //sceneGestureMain.setDrawPath(circles, lines);
    }

    private void drawNodes() {

        //display all nodes on that floor!!!
        ArrayList<Location> nodes = new ArrayList<Location>();
        //want to fill nodes w/ floor = currrentFloor
        int temp = 0;
        double scaleRatio = Map.getFitWidth() / Map.getImage().getWidth();
        Point2D point = sceneGestureMain.getImageLocation();
        for (int i = 0; i < single.getData().size(); i++) {
            if (single.getData().get(i).getFloor().equals(currentMap) && single.getData().get(i).getNodeType().equals(type)) {
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
            if (event.getSceneY() < 417) {
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
            }
            else if (event.getSceneX() > 830 && !currentMap.equals("3")){
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
            }
            else {
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

        //display all nodes on that floor!!!
        ArrayList<Location> nodes = new ArrayList<Location>();
        //want to fill nodes w/ floor = currrentFloor
        int temp = 0;
        double scaleRatio = MapUpper.getFitWidth() / MapUpper.getImage().getWidth();
        Point2D point = sceneGestureUpper.getImageLocation();
        for (int i = 0; i < single.getData().size(); i++) {
            if (single.getData().get(i).getFloor().equals(currentMapAbove) && single.getData().get(i).getNodeType().equals(type)) {
                //System.out.println(currentMap);
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
                //System.out.println(currentMap);
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
}

