package edu.wpi.cs3733.d19.teamL.Map.Pathfinding;

import com.jfoenix.controls.JFXButton;
import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Map.ImageInteraction.SceneGestures;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Path;
import edu.wpi.cs3733.d19.teamL.Map.ImageInteraction.PanAndZoomPane;
import edu.wpi.cs3733.d19.teamL.SearchingAlgorithms.AStarStrategy;
import edu.wpi.cs3733.d19.teamL.SearchingAlgorithms.BreadthFirstStrategy;
import edu.wpi.cs3733.d19.teamL.SearchingAlgorithms.DepthFirstStrategy;
import edu.wpi.cs3733.d19.teamL.SearchingAlgorithms.PathfindingStrategy;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import static java.lang.Math.sqrt;

@SuppressWarnings("Duplicates")
public class PathFindingController {
    @FXML
    private Label Direction;

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
    private JFXButton setKioskButton; //setKioskButtPress

    @FXML
    private ComboBox<String> Filter;

    @FXML
    private ComboBox<String> Floor;

    @FXML
    private ComboBox<Location> PathFindEndDrop;

    @FXML
    private ComboBox<Location> PathFindStartDrop;

    @FXML
    private ComboBox<PathfindingStrategy> strategySelector;


    @FXML
    private AnchorPane anchorPaneWindow;

    @FXML
    private ImageView Map;

    @FXML
    private Pane imagePane;

    private Rectangle clip;
    private boolean displayingPath;
    private Path path;

    private PanAndZoomPane zoomPaneImage;
    private SceneGestures sceneGestures;
    private AnchorPane anchorPanePath;
    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(1.0d);
    private final DoubleProperty deltaY = new SimpleDoubleProperty(0.0d);
    private PathfindingStrategy strategyAlgorithm;

    private NodesAccess na;
    private EdgesAccess ea;
    private final ObservableList<Location> noHallStart = FXCollections.observableArrayList();
    private final ObservableList<Location> noHallEnd = FXCollections.observableArrayList();
    private final ObservableList<String> filterList = FXCollections.observableArrayList();
    private final ObservableList<String> floorList = FXCollections.observableArrayList();
    private Singleton single = Singleton.getInstance();

    Location kioskTemp;

    private ArrayList<String> mapURLs = new ArrayList<String>();
    private ArrayList<Circle> circles = new ArrayList<Circle>();
    private ArrayList<Line> lines = new ArrayList<Line>();

    private ListIterator<String> listIterator = null;

    Timeline timeout;

    private String pickedFloor = "test";
    private String type = "test";
    private String type2 = "";
    private String currentMap = "G"; //defaults to floor G

    @FXML
    private void clickedG(){
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thegroundfloor.png"));
        currentMap = "G";
        resetRadButts();
        if(PathFindStartDrop.getValue() != null && PathFindEndDrop.getValue() != null){
            submitPressed();
        }
    }
    @FXML
    private void clickedL1() {
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel1.png"));
        currentMap = "L1";
        resetRadButts();
        if(PathFindStartDrop.getValue() != null && PathFindEndDrop.getValue() != null){
            submitPressed();
        }
    }

    @FXML public void clickedL2(){
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel2.png"));
        currentMap = "L2";
        resetRadButts();
        if(PathFindStartDrop.getValue() != null && PathFindEndDrop.getValue() != null){
            submitPressed();
        }
    }
    @FXML
    private void clicked1(){
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/01_thefirstfloor.png"));
        currentMap = "1";
        resetRadButts();
        if(PathFindStartDrop.getValue() != null && PathFindEndDrop.getValue() != null){
            submitPressed();
        }
    }
    @FXML
    private void clicked2(){
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/02_thesecondfloor.png"));
        currentMap = "2";
        resetRadButts();
        if(PathFindStartDrop.getValue() != null && PathFindEndDrop.getValue() != null){
            submitPressed();
        }
    }
    @FXML
    private void clicked3(){
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/03_thethirdfloor.png"));
        currentMap = "3";
        resetRadButts();
        if(PathFindStartDrop.getValue() != null && PathFindEndDrop.getValue() != null){
            submitPressed();
        }
    }

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
    Location startNode;
    Location endNode;

    @FXML
    /**
     * @author Gabe
     * Replaces image URL with the next floor up when the UP button is pressed
     */
    private void upClicked() {
        single.setData();
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
        single.setLastTime();
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
        single.setLastTime();
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
        single.setLastTime();
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

    @FXML
    private void strategySelected() {
        strategyAlgorithm = strategySelector.getValue();
    }

    public void initialize() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        ObservableList<PathfindingStrategy> strategies = FXCollections.observableArrayList();
        AStarStrategy aStarStrategy = new AStarStrategy(single.lookup);
        strategies.add(aStarStrategy);
        strategies.add(new DepthFirstStrategy(single.lookup));
        strategies.add(new BreadthFirstStrategy(single.lookup));
        strategySelector.setItems(strategies);
        strategySelector.setValue(aStarStrategy);
        strategyAlgorithm = strategySelector.getValue();
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()){
                    try{
                        single.setDoPopup(true);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));
                        Parent sceneMain = loader.load();
                        if(single.isLoggedIn()){
                            HomeScreenController controller = loader.<HomeScreenController>getController();
                            controller.displayPopup();
                        }
                        Stage thisStage = (Stage) PathFindBack.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
                        single.setLastTime();
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
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
        filter();
        floor();
        noHall();
        Filter.setItems(filterList);
        Floor.setItems(floorList);
        //initializeTable(na, ea);

        anchorPanePath = new AnchorPane();

//        Map.fitWidthProperty().bind(imagePane.widthProperty());
//        Map.fitHeightProperty().bind(imagePane.heightProperty());

        Map.setFitWidth(754);
        Map.setFitHeight(507);

        clip = new Rectangle();
        clip.widthProperty().bind(Map.fitWidthProperty());
        clip.heightProperty().bind(Map.fitHeightProperty());
        anchorPanePath.setClip(clip);

        zoomPaneImage = new PanAndZoomPane();

        zoomProperty.bind(zoomPaneImage.myScale);
        deltaY.bind(zoomPaneImage.deltaY);
        zoomPaneImage.getChildren().add(Map);

        sceneGestures = new SceneGestures(zoomPaneImage, Map);

        imagePane.addEventFilter( MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
        imagePane.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        imagePane.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        imagePane.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());


        Map.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // scene is set for the first time. Now its the time to listen stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        // stage is set. now is the right time to do whatever we need to the stage in the controller.
                        ChangeListener<Number> stageSizeListenerWidth = (observable, oldValue, newValue) -> {
                            displayPath();

                            Map.setFitWidth(Map.getFitWidth()/oldValue.doubleValue()*newValue.doubleValue());
                        };

                        ChangeListener<Number> stageSizeListenerHeight = (observable, oldValue, newValue) -> {
                            displayPath();

                            Map.setFitHeight(Map.getFitHeight()/oldValue.doubleValue()*newValue.doubleValue());
                        };

                        ((Stage) newWindow).widthProperty().addListener(stageSizeListenerWidth);
                        ((Stage) newWindow).heightProperty().addListener(stageSizeListenerHeight);
                    }
                });
            }
        });

        imagePane.getChildren().add(zoomPaneImage);
        imagePane.getChildren().add(anchorPanePath);

        sceneGestures.reset(Map, Map.getImage().getWidth(), Map.getImage().getHeight());
        sceneGestures.setDrawPath(circles, lines);
    }

    @FXML
    private void backPressed() throws IOException {
        timeout.stop();
        single = Singleton.getInstance();
        single.setLastTime();
        thestage = (Stage) PathFindBack.getScene().getWindow();
        AnchorPane root;

        if(single.isLoggedIn()) {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EmployeeLoggedInHome.fxml"));
            if(single.isIsAdmin()){
                loader = new FXMLLoader(getClass().getClassLoader().getResource("AdminLoggedInHome.fxml"));
            }
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
        }
    }

    @FXML
    private void locationsSelected(){
        single.setLastTime();
        if(PathFindStartDrop.getValue() != null && PathFindEndDrop.getValue() != null){
            PathFindSubmit.setDisable(false);
        }
        else{
            PathFindSubmit.setDisable(true);
        }
    }

    @FXML
    private void submitPressed(){
        single.setLastTime();
        startNode = single.lookup.get(PathFindStartDrop.getValue().getLocID());
        endNode = single.lookup.get(PathFindEndDrop.getValue().getLocID());



        displayingPath = true;


        path = findAbstractPath(strategyAlgorithm, startNode, endNode);

        displayPath();
        //printPath(path.getPath());
        Direction.setText(printPath(path.getPath()));


        sceneGestures.setDrawPath(circles,lines);
    }

    public void displayPath(){
        single.setLastTime();

        if(displayingPath) {
            path.getPath().add(0,startNode);

            for (Circle c : circles) {
                anchorPanePath.getChildren().remove(c);
            }
            for (Line l : lines) {
                anchorPanePath.getChildren().remove(l);
            }

            circles.clear();
            lines.clear();

            Point2D point = sceneGestures.getImageLocation();
            double scaleRatio = Math.min(Map.getFitWidth() / Map.getImage().getWidth(), Map.getFitHeight() / Map.getImage().getHeight());

            for (int i = 0; i < path.getPath().size() - 1; i++) {
                Line line = new Line();

                line.setStartX((path.getPath().get(i).getXcoord() - point.getX()) * scaleRatio * sceneGestures.getImageScale());
                line.setStartY((path.getPath().get(i).getYcoord() - point.getY()) * scaleRatio * sceneGestures.getImageScale());
                line.setEndX((path.getPath().get(i + 1).getXcoord() - point.getX()) * scaleRatio * sceneGestures.getImageScale());
                line.setEndY((path.getPath().get(i + 1).getYcoord() - point.getY()) * scaleRatio * sceneGestures.getImageScale());

                if (!(path.getPath().get(i).getFloor().equals(currentMap)) || !(path.getPath().get(i + 1).getFloor().equals(currentMap))) {
                    line.setVisible(false);
                }
                anchorPanePath.getChildren().add(line);

                lines.add(line);
            }
            Circle StartCircle = new Circle();

            //Setting the properties of the circle
            StartCircle.setCenterX((startNode.getXcoord() - point.getX()) * scaleRatio * sceneGestures.getImageScale());
            StartCircle.setCenterY((startNode.getYcoord() - point.getY()) * scaleRatio * sceneGestures.getImageScale());
            StartCircle.setRadius(Math.max(2.5, 2.5f * (sceneGestures.getImageScale() / 5)));
            StartCircle.setStroke(Color.GREEN);
            StartCircle.setFill(Color.GREEN);
            if (!startNode.getFloor().equals(currentMap)) {
                StartCircle.setVisible(false);
            }


            anchorPanePath.getChildren().add(StartCircle);

            Circle EndCircle = new Circle();

            //Setting the properties of the circle
            EndCircle.setCenterX((endNode.getXcoord() - point.getX()) * scaleRatio * sceneGestures.getImageScale());
            EndCircle.setCenterY((endNode.getYcoord() - point.getY()) * scaleRatio * sceneGestures.getImageScale());
            EndCircle.setRadius(Math.max(2.5, 2.5f * (sceneGestures.getImageScale() / 5)));
            EndCircle.setStroke(Color.RED);
            EndCircle.setFill(Color.RED);
            if (!endNode.getFloor().equals(currentMap)) {
                EndCircle.setVisible(false);
            }

            anchorPanePath.getChildren().add(EndCircle);


            circles.add(StartCircle);
            circles.add(EndCircle);
        }
    }

    @FXML
    /**
     * @author Gabe
     * reads the input for the floor combo box
     */
    private void filterFloor() {
        single.setLastTime();
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
        single.setLastTime();
        PathFindStartDrop.getSelectionModel().clearSelection();
        PathFindStartDrop.setValue(null);
        noHall();
    }

    @FXML
    private void clearEnd(){
        single.setLastTime();
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
        single.setLastTime();
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
        single.setLastTime();
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

    public Path findAbstractPath(PathfindingStrategy strategy, Location start, Location end) {
        single.setLastTime();
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

        double scaleRatio = Map.getFitWidth() / Map.getImage().getWidth();
        Point2D point = sceneGestures.getImageLocation();

        for(int i=0; i<single.getData().size(); i++){
            //if nodetype contains keyword
            //System.out.println(currentMap);

            if(single.getData().get(i).getNodeType().contains(keyword) && single.getData().get(i).getFloor().equals(currentMap)/* && data.get(i).getFloor() == kioskNode.getFloor*/){
                nodes.add(single.getData().get(i));

                //System.out.println("ok at least one node gets here");

                Circle thisCircle = new Circle();

                anchorPanePath.getChildren().add(thisCircle);

                //Setting the properties of the circle
                thisCircle.setCenterX((nodes.get(temp).getXcoord()-point.getX())*scaleRatio*sceneGestures.getImageScale());
                thisCircle.setCenterY((nodes.get(temp).getYcoord()-point.getY())*scaleRatio*sceneGestures.getImageScale());
                thisCircle.setRadius(Math.max(2.5,2.5f*(sceneGestures.getImageScale()/5)));
                thisCircle.setStroke(Color.web("RED"));
                thisCircle.setFill(Color.web("RED"));

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
        checkAndSetKiosk();

        ArrayList<Location> nodes = new ArrayList<Location>();
        //want to fill nodes w/ relevent POI

        for(int i=0; i<single.getData().size(); i++) {
            //if nodetype contains keyword
            if (single.getData().get(i).getNodeType().contains(keyword) && single.getData().get(i).getFloor().equals(kioskTemp.getFloor())) {
                nodes.add(single.getData().get(i));
                System.out.println("node added");
            }
        }
        //if there are no nodes, dont do anything
        if(! nodes.isEmpty()){

            AStarStrategy astar = new AStarStrategy(single.lookup);

            //get closest node
            int nodeAx=0;
            int nodeAy=0;

            int nodeBx= kioskTemp.getXcoord();
            int nodeBy= kioskTemp.getYcoord();

            int m=0; //x coord stuff
            int n=0; //y coord stuff

            double smallestDistance = 500000;
            double length =0;

            Location closestLOC = nodes.get(0);
            Path closestPath = findAbstractPath(astar,kioskTemp, nodes.get(0)); //?

            for(int i=0; i<nodes.size(); i++){

                //kiosk is B
                nodeAx = nodes.get(i).getXcoord();
                nodeAy = nodes.get(i).getYcoord();

                n = nodeAx - nodeBx;
                n = Math.abs(n);
                //absolute val in case its negative
                m = nodeAy -nodeBy;
                m = Math.abs(m);
                //abs val
                //length
                length = sqrt((m*m)+(n+n));
                //a^2 + b^2 = c^2 therefore sqrt gets the length of the distance between kiosk and this node

                //System.out.println("n:"+n+" m:"+m+" length:"+length);

                if(length < smallestDistance){
                    smallestDistance = length;
                    //System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"+"  smallest distance: "+smallestDistance);
                    //set this node to be for pathing
                    closestLOC = nodes.get(i);
                    closestPath = findAbstractPath(astar,kioskTemp, closestLOC);

                }
            }

            //displayPath(closestPath.getPath(), kioskTemp, closestLOC);

            displayingPath = true;

            startNode = kioskTemp;
            endNode = closestLOC;
            path = closestPath;

            displayPath();
            //printPath(path.getPath());
            Direction.setText(printPath(path.getPath()));


            sceneGestures.setDrawPath(circles,lines);
        }
        //do not display any path
    }

    /**
     * Grace made this
     * sets the kiosk to the "Start Location" dropdown location
     */
    @FXML
    private void setKioskButtPress(){
        //get start location from dropdown
        if(!(PathFindStartDrop.getValue() == null)){
            kioskTemp = PathFindStartDrop.getValue();
            //set kioskTemp to that^
        }

    }


    /**Grace Made this
     * just resets the radio buttons
     */
    public void resetRadButts(){
        for (Circle c: circles) {
            anchorPanePath.getChildren().remove(c);
        }
        for (Line l: lines) {
            anchorPanePath.getChildren().remove(l);
        }
        bathroomRadButton.setSelected(false);
        bathroomRadButton.setTextFill(Color.web("#ffffff"));
        cafeRadButton.setSelected(false);
        cafeRadButton.setTextFill(Color.web("#ffffff"));
        eleRadButton.setSelected(false);
        eleRadButton.setTextFill(Color.web("#ffffff"));
        stairsRadButton.setSelected(false);
        stairsRadButton.setTextFill(Color.web("#ffffff"));
    }

    /**Grace made this
     * just sets the kiosk to an actual location
     */
    public void checkAndSetKiosk(){
        //if kisosk was initiated its fine
        //if not set kiosk to random (first location stuff) things
        if(single.getKioskID().equals("")){
            //Location kioskTemp = single.getData().get(0); //initially at floor 2
            single.setKioskID(single.getData().get(0).getLocID());
        }
        //find actual "location" of kiosk
        for(int i=0; i<single.getData().size(); i++){
            if(single.getData().get(i).getLocID().equals(single.getKioskID())){
                kioskTemp = single.getData().get(i);
            }
        }
    }

    /** GRACE MADE THIS
     *display and find closest bathroom
     */
    @FXML
    private void bathRadButtPressed(){
        checkAndSetKiosk();
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

            if((currentMap.equals(kioskTemp.getFloor()))){
                displayClosestPOI("REST");
            }
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
        checkAndSetKiosk();
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

            if((currentMap.equals(kioskTemp.getFloor()))) {
                System.out.println("");
                displayClosestPOI("RETL");
            }
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
        checkAndSetKiosk();
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

            if((currentMap.equals(kioskTemp.getFloor()))) {
                displayClosestPOI("ELEV");
            }
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
        checkAndSetKiosk();
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

            if((currentMap.equals(kioskTemp.getFloor()))) {
                displayClosestPOI("STAI");
            }
            displayPOINodes("STAI");
        }
        if(!stairsRadButton.isSelected()){
            stairsRadButton.setTextFill(Color.web("#ffffff"));
            for (Circle c: circles) {
                anchorPanePath.getChildren().remove(c);
            }
        }
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
        angleTurning = angleTurning /(2*Math.PI) * 360;


        return  angleTurning;

    }
    //Larry - convert pixel distance to exact distance
    //147 pixel distance = 52 ft
    private int convertToExact(double pixelDistance){
        double actualLength = pixelDistance / 147 * 52;

        return  (int)actualLength;
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

    //Larry - determine the location is stair or elevator or not
    // This is a helper function in order to save a lot of space
    private boolean isStairELe(Location A){
        if(A.getNodeType().equals("STAI") || A.getNodeType().equals("ELEV")){
            return true;
        }
        else {
            return false;
        }

    }

    //Larry - Print the textual direction based on the path return from algorithm
    private String printPath(ArrayList<Location> A){
       // System.out.println(A);
        for(Location a: A){
         //   System.out.print(a.getNodeType() + "  ");
       }
      //  System.out.println(" ");
        String aType;
        String bType;
        String aFloor;
        String bFloor;
        int curDirection = 0;
        int nextDirection = 0;
        String text = "";

        int d = 0; // count for the start location for exact location
        //same start and end location
        if(A.size() == 2 && A.get(0) == A.get(1)){
           // System.out.println(A.size());
           // System.out.println(A.size() == 2);
//
         //   System.out.println("You are already at your destination");
            text += "You are already at your destination :)\n";
            return text;
        }
       // System.out.println("Begin from " + A.get(0).getLongName());
        text += "Begin from " + A.get(0).getLongName() + "\n";
        //when size is two, but two location are different
        if(A.size() == 2){
            aType = A.get(0).getNodeType();
            bType = A.get(1).getNodeType();
            aFloor = A.get(0).getFloor();
            bFloor = A.get(1).getFloor();
            if((aType=="STAI" || aType == "ELEV") && (bType == "STAI" || bType =="ELEV") && !aFloor.equals(bFloor) ){
                    if(A.get(1).getNodeType() == "STAI" ||A.get(1).getNodeType() == "ELEV" ){
                      //  System.out.println("Go to floor " + bFloor + " by " + bType);
                        text += "Go to floor " + bFloor + " by " + bType + "\n";

                            return text;
                }
                else{
                     //   System.out.println("Go straight to " + A.get(1).getLongName() + " (" +
                     //       convertToExact(A.get(0).findDistance(A.get(1))) + " ft) \n");
                        text += "\u21E7 Go straight to " + A.get(1).getLongName() + " (" +
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

            double angle = calculateAngle(a,b,c);
            if(angle < 120 && angle > 60){
                curDirection = directionPath(a,b);
                nextDirection = directionPath(b,c);

            //    System.out.println("Go straight to " + b.getLongName()
                 //       + " (" + convertToExact(start.findDistance(b)) + " ft) " );
                text += "\u21E7 Go straight to " + b.getLongName()
                        + " (" + convertToExact(start.findDistance(b)) + " ft) \n";

                //- -> + , x+ : left
                d = i + 1;
                double slopeAB = calculateSlope(a,b);
                double slopeBC = calculateSlope(b,c);
                if(curDirection == nextDirection){
                    if(curDirection == 2 || curDirection == 6){
                        if(Math.abs(slopeBC)> Math.abs(slopeAB)){
                         //   System.out.println("Turn left");
                            text += "\u21E6 Turn left \n";
                        }
                        else{
                         //   System.out.println("Turn right");
                            text += "\u21E8 Turn right\n";
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
                   //     System.out.println("Turn right");
                        text += "\u21E8 Turn right\n";
                    }
                    else{
                  //      System.out.println("Turn left");
                        text += "\u21E6 Turn left\n";
                    }

                }

                else if((curDirection == 8 && nextDirection ==4) || (curDirection == 6 && nextDirection ==2)){
                    if(Math.abs(slopeBC)>Math.abs(slopeAB)){
                //        System.out.println("Turn left");
                        text += "\u21E6 Turn left\n";
                    }
                    else{
              //          System.out.println("Turn right");
                        text += "\u21E8 Turn right\n";
                    }

                }

                else if(curDirection <= 5){
                    if(nextDirection < curDirection + 4 && nextDirection > curDirection){
              //          System.out.println("Turn right");
                        text += "\u21E8 Turn right\n";
                    }
                    else {
              //          System.out.println("Turn left");
                        text += "\u21E6 Turn left\n";
                    }
                }
                else{
                    if(curDirection == 6){
                        if(nextDirection == 7 || nextDirection == 8 || nextDirection == 1){
                     //       System.out.println("Turn right");
                            text += "\u21E8 Turn right\n";
                        }
                        if(nextDirection == 5 || nextDirection == 4 || nextDirection == 3){
                    //        System.out.println("Turn left");
                            text += "\u21E6 Turn left\n";
                        }


                    }
                    else if (curDirection ==7){
                        if(nextDirection == 8 || nextDirection == 1 || nextDirection == 2){
                       //     System.out.println("Turn right");
                            text += "\u21E8 Turn right\n";
                        }
                        else if(nextDirection == 6 || nextDirection == 5 || nextDirection == 4){
                        //    System.out.println("Turn left");
                            text += "\u21E6 Turn left\n";
                        }
                        else {

                        }

                    }
                    else if(curDirection ==8){
                        if(nextDirection == 1 || nextDirection == 2 || nextDirection == 3){
                       //     System.out.println("Turn right");
                            text += "Turn right\n";
                        }
                        else if(nextDirection == 5 || nextDirection == 6 || nextDirection == 7){
                      //      System.out.println("Turn left");
                            text += "\u21E6 Turn left\n";
                        }
                        else {

                        }
                    }
                    else{
                        //nothing handle error in case
                    }
                }


            }
            if(i == A.size() - 3){
             //   System.out.println("Go straight to your destination " + A.get(A.size()-1).getLongName() +
                   //     " (" + convertToExact(b.findDistance(c)) + " ft) " );
                text += "\u21E7 Go straight to your destination " + A.get(A.size()-1).getLongName() +
                                " (" + convertToExact(b.findDistance(c)) + " ft) \n";
                return text;
            }
            if(isStairELe(a) && isStairELe(b)){
        //        System.out.println("Go to floor " + b.getFloor() + " by " + a.getLongName());
                text += "Go to floor " + b.getFloor() + " by " + a.getLongName();

            }

        }

        return text;
    }
}
