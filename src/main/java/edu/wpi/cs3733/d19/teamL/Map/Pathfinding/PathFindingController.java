package edu.wpi.cs3733.d19.teamL.Map.Pathfinding;

import com.jfoenix.controls.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.AutoCompleteList;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.CircleLocation;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Path;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.RoomBooking.BookRoomController;
import edu.wpi.cs3733.d19.teamL.RoomBooking.RoomAccess;
import edu.wpi.cs3733.d19.teamL.RoomBooking.RoomDisplay;
import edu.wpi.cs3733.d19.teamL.Reports.pathReportAccess;
import edu.wpi.cs3733.d19.teamL.SearchingAlgorithms.*;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;

import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.paint.Color.*;

import static java.lang.Math.sqrt;

@SuppressWarnings("Duplicates")
public class PathFindingController {

    @FXML
    private ColumnConstraints mapColumn;

    @FXML
    private TextArea direction;

    @FXML
    private Button back;

    @FXML
    private Stage thestage;

    @FXML
    private Button PathFindSubmit;

    @FXML
    private Button menuBack;

    @FXML
    private Button G;

    @FXML
    private Button Up;

    @FXML
    private Button Down;

    @FXML
    private JFXComboBox<String> restrictChoice;

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
    private Button F4;

    @FXML
    private Button about;

    @FXML
    private JFXButton homebtn;

    @FXML
    private JFXButton aboutButton;

    @FXML
    private Button logOut;


    @FXML
    private RadioButton bathroomRadButton;
    @FXML
    private RadioButton cafeRadButton;
    @FXML
    private RadioButton eleRadButton;
    @FXML
    private RadioButton stairsRadButton;
    @FXML
    private RadioButton vendRadButton;
    @FXML
    private RadioButton giftRadButton;
    @FXML
    private RadioButton cuisineRadButton;

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
    private TextField searchField;

    @FXML
    private JFXComboBox<Location> kioskConnectedTo;
    @FXML
    private TextField kioskName;

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView Map;

    @FXML
    private JFXComboBox<PathfindingStrategy> pathStrategy;

    @FXML
    private Label thisMap;

    @FXML
    private HBox vBottom;

    @FXML
    private VBox vLeft;

    @FXML
    private AnchorPane settingPane;

    @FXML
    private JFXSlider sliderBar;

    Location kioskTemp;

    Pane suggestions;

    private boolean displayingPath;
    //Arraylist for the buttons that generate on path
    private ArrayList<Button> buttons = new ArrayList<Button>();
    //Arraylist of floor buttons
    private ArrayList<JFXButton> floorButtons = new ArrayList<JFXButton>();
    private Path path;

    private GesturePane gesturePane;
    private StackPane childPane;
    private AnchorPane pathPane;

    private PathfindingStrategy strategyAlgorithm;
    TemplatePathFinder aStarStrategy;
    TemplatePathFinder dijkstraStrategy;
    PathfindingStrategy depth;
    PathfindingStrategy breadth;

    private NodesAccess na;
    private EdgesAccess ea;
    private HashMap<String, Location> nameToLoc = new HashMap<>();;
    private final ObservableList<Location> noHallStart = FXCollections.observableArrayList();
    private final ObservableList<Location> noHallEnd = FXCollections.observableArrayList();
    private final ObservableList<String> filterList = FXCollections.observableArrayList();
    private final ObservableList<String> floorList = FXCollections.observableArrayList();
    private Singleton single = Singleton.getInstance();

    private AutoCompleteList autoList = new AutoCompleteList();

    private ArrayList<String> mapURLs = new ArrayList<String>();
    private ArrayList<Circle> circles = new ArrayList<Circle>();
    private ArrayList<Line> lines = new ArrayList<Line>();
    private ArrayList<Label> labels = new ArrayList<Label>();

    private ArrayList<Polygon> flexSpaces = new ArrayList<Polygon>();
    private ArrayList<RoomDisplay> DisplayRooms = new ArrayList<RoomDisplay>();
    private final ObservableList<String> listOfRooms = FXCollections.observableArrayList();
    private ArrayList<String> rooms = new ArrayList<>();

    private ListIterator<String> listIterator = null;
    private boolean showingSettings;

    Timeline timeout;

    private String pickedFloor = "test";
    private String type = "test";
    private String type2 = "";
    private String currentMap = "G"; //defaults to floor G

    private String typeSelected = "selected";


    @FXML
    private Button menu;
    @FXML
    private AnchorPane navList;

    public void initialize(URL url, ResourceBundle rb) {
        this.prepareSlideMenuAnimation();
        direction.setEditable(false);
    }

    public void initWithMeme(String preference, String typeFilter, String floorFilter, Location start, Location end){
        if(preference != null) {
            restrictChoice.setValue(preference);
        }
        if(start != null) {
            PathFindStartDrop.setValue(start);
        } else {
            Singleton single = Singleton.getInstance();
            start = single.getKiosk();
            PathFindStartDrop.setValue(start);
        }
        if(end != null) {
            PathFindEndDrop.setValue(end);
        }
        if(typeFilter != null) {
            Filter.setValue(typeFilter);
        }
        if(floorFilter != null) {
            Floor.setValue(floorFilter);
        }
        if(start != null && end != null) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    submitPressed();
                }
            });
        }
    }

    @FXML
    private void clickedG(){
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thegroundfloor.png"));
        currentMap = "G";

        changeMapLabel();
        displayKiosk();
        clear();
        hideFlexSpaces();
        gesturePane.reset();
        direction.clear();
    }
    @FXML
    private void clickedL1() {
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel1.png"));
        currentMap = "L1";

        changeMapLabel();
        displayKiosk();
        clear();
        hideFlexSpaces();
        direction.clear();
    }

    @FXML public void clickedL2(){
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel2.png"));
        currentMap = "L2";

        changeMapLabel();
        displayKiosk();
        clear();
        hideFlexSpaces();
        gesturePane.reset();
        direction.clear();
    }
    @FXML
    private void clicked1(){
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/01_thefirstfloor.png"));
        currentMap = "1";

        changeMapLabel();
        displayKiosk();
        clear();
        hideFlexSpaces();
        gesturePane.reset();
        direction.clear();
    }
    @FXML
    private void clicked2(){
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/02_thesecondfloor.png"));
        currentMap = "2";

        changeMapLabel();
        displayKiosk();
        clear();
        hideFlexSpaces();
        gesturePane.reset();
        direction.clear();
    }
    @FXML
    private void clicked3(){
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/03_thethirdfloor.png"));
        currentMap = "3";

        changeMapLabel();
        displayKiosk();
        clear();
        hideFlexSpaces();
        gesturePane.reset();
        direction.clear();
    }

    @FXML
    private void clicked4() {
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/04_thefourthfloor.png"));
        currentMap = "4";

        changeMapLabel();
        displayKiosk();
        if (single.isLoggedIn()) {
            displayFlexSpaces(single.getSimulation());
            displayBookableRooms();
        }
        gesturePane.reset();
        gesturePane.zoomTo(4.0, new Point2D(4375*childPane.getWidth()/Map.getImage().getWidth(), 2260*childPane.getHeight()/Map.getImage().getHeight()));
        clear();
        direction.clear();
    }

        @FXML
        private void changeMapLabel () {
            if (currentMap.equals("L2")) {
                thisMap.setText("Lower Level 2");
            }

            if (currentMap.equals("L1")) {
                thisMap.setText("Lower Level 1");
            }

            if (currentMap.equals("G")) {
                thisMap.setText("Ground Floor");
            }

            if (currentMap.equals("1")) {
                thisMap.setText("Floor 1");
            }

            if (currentMap.equals("2")) {
                thisMap.setText("Floor 2");
            }

            if (currentMap.equals("3")) {
                thisMap.setText("Floor 3");
            }

            if (currentMap.equals("4")) {
                thisMap.setText("Floor 4");
            }
        }
        @FXML
        /**
         * @author Gabe
         * adds all the URLs to the list, starts with 3rd floor since that is the next floor to come
         */
        public void map () {
            mapURLs.add("/SoftEng_UI_Mockup_Pics/03_thethirdfloor.png");
            mapURLs.add("/SoftEng_UI_Mockup_Pics/00_thegroundfloor.png");
            mapURLs.add("/SoftEng_UI_Mockup_Pics/00_thelowerlevel1.png");
            mapURLs.add("/SoftEng_UI_Mockup_Pics/00_thelowerlevel2.png");
            mapURLs.add("/SoftEng_UI_Mockup_Pics/01_thefirstfloor.png");
            mapURLs.add("/SoftEng_UI_Mockup_Pics/02_thesecondfloor.png");
        mapURLs.add("/SoftEng_UI_Mockup_Pics/04_thefourthfloor.png");
        }

        private Label startLabel;
        private Label endLabel;
        private Label hereLabel;
        private Location startNode;
        private Location endNode;

        @FXML
        private void strategySelected () {
            strategyAlgorithm = strategySelector.getValue();
            single.setTypePathfind(strategyAlgorithm);
        }

        public void initialize () {
            Singleton single = Singleton.getInstance();
            single.setLastTime();

            na = new NodesAccess();
            ea = new EdgesAccess();

            ObservableList<PathfindingStrategy> strategies = FXCollections.observableArrayList();
            ObservableList<String> preference = FXCollections.observableArrayList();
            aStarStrategy = new AStarStrategy(single.lookup);
            dijkstraStrategy = new DijkstraStrategy(single.lookup);
            strategies.add(dijkstraStrategy);
            strategies.add(aStarStrategy);
            depth = new DepthFirstStrategy(single.lookup);
            breadth = new BreadthFirstStrategy(single.lookup);
            strategies.add(depth);
            strategies.add(breadth);
            preference.addAll("Stairs Only", "Elevators Only", "Both");
            restrictChoice.setItems(preference);
            //Nikhil modified this code, it should work, but if it doesn't you can yell at me PJ
            ObservableList strategiesDropDown = FXCollections.observableArrayList();
            strategiesDropDown.add(aStarStrategy);
            strategiesDropDown.add(dijkstraStrategy);
            strategiesDropDown.add(depth);
            strategiesDropDown.add(breadth);
            strategySelector.setItems(strategiesDropDown);
            strategySelector.setValue(single.getTypePathfind());
//        strategyAlgorithm = strategySelector.getValue();
            direction.setEditable(false);
            PathFindSubmit.setDisable(true);

            kioskConnectedTo.setItems(single.getData());

            changeMapLabel();
            showingSettings = false;
            timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    updateFlexSpaces(single.getSimulation());
                    if ((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()) {
                        try {
                            single.setLastTime();
                            single.setLoggedIn(false);
                            single.setUsername("");
                            single.setIsAdmin(false);
                            single.setDoPopup(true);
                            Memento m = single.getOrig();
                            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(m.getFxml()));
                            Parent sceneMain = loader.load();
                            if (single.isLoggedIn()) {
                                HomeScreenController controller = loader.<HomeScreenController>getController();
                                controller.displayPopup();
                            }
                            Stage thisStage = (Stage) homebtn.getScene().getWindow();

                            Scene newScene = new Scene(sceneMain);
                            thisStage.setScene(newScene);
                            timeout.stop();
                        } catch (IOException io) {
                            System.out.println(io.getMessage());
                        }
                    }
                }
            }));
            timeout.setCycleCount(Timeline.INDEFINITE);
            timeout.play();

            filter();
            floor();
            noHall();
            Filter.setItems(filterList);
            Floor.setItems(floorList);
            //initializeTable(na, ea);

            pathPane = new AnchorPane();
            childPane = new StackPane();
            childPane.getChildren().add(Map);
            childPane.getChildren().add(pathPane);
            gesturePane = new GesturePane(childPane);
            gesturePane.setHBarEnabled(false);
            gesturePane.setVBarEnabled(false);

            gridPane.add(gesturePane, 0, 0, 1, GridPane.REMAINING);
            Map.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
                if (oldScene == null && newScene != null) {
                    // scene is set for the first time. Now its the time to listen stage changes.
                    newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                        if (oldWindow == null && newWindow != null) {
                            // stage is set. now is the right time to do whatever we need to the stage in the controller.
                            ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
                                displayingPath = false;

                                for (Circle c : circles) {
                                    pathPane.getChildren().remove(c);
                                }
                                for (Line l : lines) {
                                    pathPane.getChildren().remove(l);
                                }
                                for (Button b : buttons) {
                                    pathPane.getChildren().remove(b);
                                }

                                circles.clear();
                                lines.clear();
                                buttons.clear();

                                resetRadButts();

                                direction.setText("");
                            };

                            ((Stage) newWindow).widthProperty().addListener(stageSizeListener);
                            ((Stage) newWindow).heightProperty().addListener(stageSizeListener);
                        }
                    });
                }
            });

            Map.fitHeightProperty().bind(gesturePane.heightProperty());
            Map.fitWidthProperty().bind(gesturePane.widthProperty());

        childPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.SECONDARY)) {
                    Location closestToClick = single.getData().get(0);
//                Point2D topLeftPoint = new Point2D(Math.abs(gesturePane.getCurrentX()), Math.abs(gesturePane.getCurrentY()));

                    for (Location l : single.getData()) {
                        double distanceToL = Math.pow(event.getX() * Map.getImage().getWidth() / childPane.getWidth() - l.getXcoord(), 2) + Math.pow(event.getY() * Map.getImage().getHeight() / childPane.getHeight() - l.getYcoord(), 2);
                        double distanceToClosest = Math.pow(event.getX() * Map.getImage().getWidth() / childPane.getWidth() - closestToClick.getXcoord(), 2) + Math.pow(event.getY() * Map.getImage().getHeight() / childPane.getHeight() - closestToClick.getYcoord(), 2);
                        if (!l.getNodeType().equals("HALL") && currentMap.equals(l.getFloor()) && distanceToL < distanceToClosest) {
                            closestToClick = l;
                        }
                    }

                    System.out.println(closestToClick);
                    System.out.println(event.getX() * Map.getImage().getWidth() / childPane.getWidth() + " " + event.getY() * Map.getImage().getHeight() / childPane.getHeight());
//                System.out.println(Math.pow(topLeftPoint.getX()+event.getX()*Map.getImage().getWidth()/gesturePane.getViewportWidth() - closestToClick.getXcoord(), 2) + Math.pow(topLeftPoint.getY()+event.getY()*Map.getImage().getHeight()/gesturePane.getViewportHeight() - closestToClick.getYcoord(), 2));

                    if (Math.pow(event.getX() * Map.getImage().getWidth() / childPane.getWidth() - closestToClick.getXcoord(), 2) + Math.pow(event.getY() * Map.getImage().getHeight() / childPane.getHeight() - closestToClick.getYcoord(), 2) <= 65 * 65) {
                        Floor.setValue("All");
                        Filter.setValue("All");
                        noHall();
                        if(!(PathFindStartDrop.getValue() == null)){
                            PathFindEndDrop.setValue(closestToClick);
                            submitPressed();
                        }
                        else{
                            PathFindStartDrop.setValue(closestToClick);
                        }
                    }
                }
            }
        });

            menu.toFront();
            thisMap.toFront();
            L1.toFront();
            L2.toFront();
            G.toFront();
            F1.toFront();
            F2.toFront();
            F3.toFront();
            F4.toFront();
            vBottom.toFront();
            vLeft.toFront();
            sliderBar.toFront();
            startLabel = new Label();
            endLabel = new Label();
            hereLabel = new Label();
            //Adds the text to the screen
            pathPane.getChildren().add(hereLabel);
            if (!single.isLoggedIn()) {
                logOut.setVisible(false);
            }

            nameToLoc.clear();
            for (Location l : PathFindStartDrop.getItems()) {
                nameToLoc.put(l.toString(), l);
            }
            //TextFields.bindAutoCompletion(searchField,nameToLoc.keySet());
            searchField.setOnKeyReleased(searchFieldKeyPress);
            //sp = new ScrollPane();
            //navList.getChildren().add(sp);
            //sp.setVisible(false);
            //sp.setPrefWidth(searchField.getPrefWidth());
            suggestions = new Pane();
            navList.getChildren().add(suggestions);
            suggestions.setVisible(false);
            suggestions.setPrefWidth(searchField.getPrefWidth());
            suggestions.setStyle("-fx-background-color:  #012d5a;");

        //Code to immediately set kiosk
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                displayKiosk();
                PathFindStartDrop.setValue(kioskTemp);
            }
        });

            sliderBar.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                gesturePane.zoomTo(sliderBar.getValue(), gesturePane.targetPointAtViewportCentre());
                }
            });

            gesturePane.currentScaleProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    sliderBar.setValue(gesturePane.getCurrentScale());
                }
            });
        }

        @FXML
        private void logOut (ActionEvent event) throws IOException {
            timeout.stop();
            Singleton single = Singleton.getInstance();
            single.setLastTime();
            single.setUsername("");
            single.setIsAdmin(false);
            single.setLoggedIn(false);
            single.setDoPopup(true);
            Memento m = single.getOrig();
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
            ((Node) event.getSource()).getScene().setRoot(newPage);
        }

        @FXML
        private void goHome (ActionEvent event) throws IOException {
            timeout.stop();
            Singleton single = Singleton.getInstance();
            single.setLastTime();
            single.setDoPopup(true);
            saveState();
            Memento m = single.getOrig();
            Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
            ((Node) event.getSource()).getScene().setRoot(newPage);
        }

        @FXML
        private void backPressed (ActionEvent event) throws IOException {
            timeout.stop();
            Singleton single = Singleton.getInstance();
            single.setLastTime();
            Memento m = single.restore();
            single.setDoPopup(true);

        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }
    /**
     * Modified by Nikhil: Now defaults the start location to kiosk node.
     */
    @FXML
    private void locationsSelected(){
        single.setLastTime();
        if((PathFindStartDrop.getValue() != null) && PathFindEndDrop.getValue() != null){
            PathFindSubmit.setDisable(false);
        }
        else{
            PathFindSubmit.setDisable(true);
            direction.setDisable(true);
            direction.setEditable(false);
        }
        typeSelected = "selected";
    }

        /**Nathan modified this to include a path preference choice (restriction)
         * Nikhil modified this so it automatically switches the map to the starting floor.
         */
        @FXML
        private void submitPressed () {
            single.setLastTime();
            //This handles if the user wants to set their own start location.
            if (PathFindStartDrop.getValue() != null) {
                startNode = single.lookup.get(PathFindStartDrop.getValue().getLocID());
            }
            endNode = single.lookup.get(PathFindEndDrop.getValue().getLocID());
            String restriction = restrictChoice.getValue();
            if (restriction == null || restriction.trim().equals("") || restriction.equals("Both")) {
                restriction = "    ";
            } else if (restriction.equals("Stairs Only")) {
                restriction = "ELEV";
            } else if (restriction.equals("Elevators Only")) {
                restriction = "STAI";
            } else {
                restriction = "    ";
            }

            strategyAlgorithm = single.getTypePathfind();
            //System.out.println(strategyAlgorithm.toString());

            displayingPath = true;

            path = findAbstractPath(strategyAlgorithm, startNode, endNode, restriction);

            //To switch the map displayed to the startNode map automatically
            if (startNode.getFloor().equals("L2") && !currentMap.equals("L2")) {
                clickedL2();
            }
            if (startNode.getFloor().equals("L1") && !currentMap.equals("L1")) {
                clickedL1();
            }
            if (startNode.getFloor().equals("G") && !currentMap.equals("G")) {
                clickedG();
            }
            if (startNode.getFloor().equals("1") && !currentMap.equals("1")) {
                clicked1();
            }
            if (startNode.getFloor().equals("2") && !currentMap.equals("2")) {
                clicked2();
            }
            if (startNode.getFloor().equals("3") && !currentMap.equals("3")) {
                clicked3();
            }
        if (startNode.getFloor().equals("4") && !currentMap.equals("4")) {
            clicked4();
        }

            displayPath();


            direction.setDisable(false);
            direction.setEditable(false);
            pathReportAccess p = new pathReportAccess();
            p.addReport(Long.toString(System.currentTimeMillis()), startNode.getLongName(), endNode.getLongName(), typeSelected);
            if (typeSelected.equals("search")) {
                typeSelected = "selected"; //only way to reset properly
            }
        }

        /**
         * @author: Nikhil: Displays the kiosk location automatically on the path navigation screen.
         */
        public void displayKiosk () {
            checkAndSetKiosk();
            Circle kiosk = new Circle();
            kiosk.setCenterX(kioskTemp.getXcoord() * childPane.getWidth() / Map.getImage().getWidth());
            kiosk.setCenterY(kioskTemp.getYcoord() * childPane.getHeight() / Map.getImage().getHeight());
            kiosk.setRadius(Math.max(1.5, 1.5f * (gesturePane.getCurrentScale() / 4)));
            kiosk.setStroke(Color.BLUE);
            kiosk.setFill(Color.BLUE);
            hereLabel.setLayoutX(kioskTemp.getXcoord() * childPane.getWidth() / Map.getImage().getWidth() - 20);
            hereLabel.setLayoutY(kioskTemp.getYcoord() * childPane.getHeight() / Map.getImage().getHeight() - 20);
            hereLabel.setText(" You are here ");
            hereLabel.setStyle("-fx-text-fill: WHITE;-fx-font-size: 6; -fx-background-color: BLUE; -fx-border-color: WHITE; -fx-border-width: 2; -fx-min-width: 40;");
            if (currentMap.equals(kioskTemp.getFloor())) {
                kiosk.setVisible(true);
                gesturePane.zoomTo(2, new Point2D(kioskTemp.getXcoord() - 1350, kioskTemp.getYcoord() - 2000));
                hereLabel.setVisible(true);
            } else {
                hereLabel.setVisible(false);
                kiosk.setVisible(false);
            }
            circles.add(kiosk);
            pathPane.getChildren().add(kiosk);
            labels.add(hereLabel);
        }

        @FXML
        private void updateKiosk () {
            if (!kioskName.getText().equals("")) {
                kioskTemp.setLongName(kioskName.getText());
            }
            if (kioskConnectedTo.getValue() != null) {
                kioskTemp = kioskConnectedTo.getValue();
                single.setKiosk(kioskConnectedTo.getValue());
            }
            clearStart();
            //        displayKiosk();
        }

        @FXML
        private void prepareSlideMenuAnimation () {
            TranslateTransition openNav = new TranslateTransition(new Duration(300.0D), this.navList);
            openNav.setToX(0.0D);
            TranslateTransition closeNav = new TranslateTransition(new Duration(300.0D), this.navList);
            this.menu.setOnAction((evt) -> {
                if (this.navList.getTranslateX() != 130.0D) {
                    openNav.setToX(130);
                    openNav.play();
                } else {
                    closeNav.setToX(-this.navList.getWidth());
                    closeNav.play();
                }

            });

            this.menuBack.setOnAction((evt) -> {
                if (this.navList.getTranslateX() != 130.0D) {
                    openNav.setToX(130);
                    openNav.play();
                } else {
                    closeNav.setToX(-this.navList.getWidth());
                    closeNav.play();
                }

            });
            if (this.navList.getTranslateX() != 130.0D) {
                openNav.setToX(130);
                openNav.play();
            } else {
                closeNav.setToX(-this.navList.getWidth());
                closeNav.play();
            }


        }

        /**
         * @author: Nikhil: General method to set up displaying the path
         */
        public void displayPath () {
            single.setLastTime();

            //Add an arraylist to keep track of what floors you are visiting
            ArrayList<String> floorsVisited = new ArrayList<>();

            if (displayingPath) {
                path.getPath().add(0, startNode);
                start = 0;
                counter = 0;

                //Clears the lines and circles to avoid any duplicates or reproducing data
                clear();

                //Used to check for first switch
                boolean found = false;
                //End node on floor
                int floorSwitch = path.getPath().size() - 1;
                //Creates the path for the user to follow, and displays based on the current floor.
                for (int i = 0; i < path.getPath().size() - 1; i++) {
                    floorsVisited.add(path.getPath().get(i).getFloor());
                    if ((!(path.getPath().get(i + 1).getFloor().equals(currentMap))) && !found) {
                        floorSwitch = i;
                        found = true;
                    }
                }
                floorsVisited.add(path.getPath().get(path.getPath().size() - 1).getFloor());
                if (floorsVisited.contains(currentMap)) {
                    displaySelected(0, floorSwitch);
                }
                makeButtons(floorsVisited);
                changeMapLabel();
            }
        }

    /**
     * @Author: Nikhil
     * This function automatically zooms on our map when we display paths.
     * @param start
     * @param end
     */
    private void autoZoom(Location start, Location end) {
        if((Math.abs((start.getXcoord() - end.getXcoord()))) < 3200 && Math.abs(((start.getYcoord() - end.getYcoord()))) < 1050
                || (Math.abs((start.getXcoord() - end.getXcoord()))) < 2500 && Math.abs(((start.getYcoord() - end.getYcoord()))) < 3050 ){
            double x = gesturePane.getWidth()/(Math.abs((start.getXcoord() - end.getXcoord())));
            double y = gesturePane.getHeight()/Math.abs(((start.getYcoord() - end.getYcoord())));
            double scale = (Math.min(x, y)/2) + 1.1;
            gesturePane.reset();
            gesturePane.zoomTo(scale, gesturePane.targetPointAtViewportCentre());
            double xSameVal = (start.getXcoord() + end.getXcoord()) / 2.0*childPane.getWidth()/Map.getImage().getWidth();
            double ySameVal = (start.getYcoord() + end.getYcoord()) / 2.0*childPane.getHeight()/Map.getImage().getHeight();
            gesturePane.centreOn(new Point2D(xSameVal, ySameVal));
        }
        else {
            gesturePane.reset();
        }
    }

        //Globals used for makeButtons
        int start = 0;
        int counter = 0;

        /**
         * @Author Nikhil
         * This function will be used to make the buttons that display on the screen that transition from floor to floor.
         * @param floors
         */
        private void makeButtons (ArrayList < String > floors) {
            int shift = 0;
            int numOfBut = countFloors(floors);
            int totalNum = countFloors(floors);
            int center = (numOfBut + 1) / 2;
            if (totalNum % 2 == 0) {
                shift = 135;
            }
            boolean change = false;
            for (int i = 0; i < floors.size() - 1; i++) {
                //Sets up the buttons
                JFXButton fBut = new JFXButton();
                if (!floors.get(i + 1).equals(floors.get(start))) {
                    fBut.setPrefSize(50, 50);
                    fBut.setText(floors.get(i));
                    fBut.setStyle("-fx-font-weight: BOLD");
                    fBut.getStyleClass().add("buttonMap");
                    int startstore1 = start;
                    int counterstore1 = counter;
                    fBut.setOnAction(event -> {
                        displaySelected(startstore1, counterstore1);
                    });
                    floorButtons.add(fBut);
                    gridPane.getChildren().add(fBut);
                    gridPane.setHalignment(fBut, HPos.CENTER);
                    gridPane.setValignment(fBut, VPos.TOP);
                    //int diff  = numOfBut - center;
                    int diff = center - numOfBut;
                    gridPane.setMargin(fBut, new Insets(65, 0, 0, diff * (200) + shift));

                //Reduce this as we go so we know how many buttons we have left
                numOfBut--;
                //Reset
                start = i+1;
                change = true;
            }
            //This is the final button
            else if(numOfBut == 1 && change){
                fBut.setPrefSize(50,50);
                fBut.setText(floors.get(i));
                fBut.setStyle("-fx-font-weight: BOLD");
                fBut.getStyleClass().add("buttonMap");
                int startstore1 = start;
                int counterstore1 =floors.size() - 1 ;
                fBut.setOnAction(event -> {
                    displaySelected(startstore1, counterstore1);
                });
                floorButtons.add(fBut);
                gridPane.getChildren().add(fBut);
                gridPane.setHalignment(fBut, HPos.CENTER);
                gridPane.setValignment(fBut, VPos.TOP);
                //int diff  = numOfBut - center;
                int diff  = center - numOfBut;
                numOfBut--;
                gridPane.setMargin(fBut,new Insets(65,0,0,diff*(200)+ shift));
            }
            if(totalNum > 1){
                if(i == floors.size()-2 && !floors.get(i+1).equals(floors.get(start-1)) && numOfBut ==1)
                {
                    JFXButton fBut1 = new JFXButton();
                    fBut1.setPrefSize(50,50);
                    fBut1.setText(floors.get(i+1));
                    fBut1.setStyle("-fx-font-weight: BOLD");
                    fBut1.getStyleClass().add("buttonMap");
                    int startstore1 = start;
                    int counterstore1 =floors.size() - 1 ;
                    fBut1.setOnAction(event -> {
                        displaySelected(startstore1, counterstore1);
                    });
                    floorButtons.add(fBut1);
                    gridPane.getChildren().add(fBut1);
                    gridPane.setHalignment(fBut1, HPos.CENTER);
                    gridPane.setValignment(fBut1, VPos.TOP);
                    //int diff  = numOfBut - center;
                    int diff  = center - numOfBut;
                    numOfBut--;
                    gridPane.setMargin(fBut1,new Insets(65,0,0,diff*(200)+ shift));
                }
            }
            counter++;
            //Displays the part of the path you're on by highlighting the button.
//            if(fBut.getText().equals(currentMap)) {
//                fBut.setStyle("-fx-background-color: #012d5a; -fx-text-fill: white");
//            }
            }

        }

        /**
         * @Author Nikhil
         * This method displays the path only for the segment the user has selected.
         * Handles the path animation.
         * Handles the label displays.
         * Handles auto-zooming.
         * @param begin
         * @param count
         */
        private void displaySelected ( int begin, int count){

            //Clears everything before displaying, needs to be different from clear() because we need the buttons
            for (Circle c : circles) {
                pathPane.getChildren().remove(c);
            }
            for (Line l : lines) {
                pathPane.getChildren().remove(l);
            }
            for (Button b : buttons) {
                pathPane.getChildren().remove(b);
            }
            for (Label la : labels) {
                pathPane.getChildren().remove(la);
            }

            circles.clear();
            lines.clear();
            buttons.clear();
            labels.clear();
            //Changes the map displayed to the floor of begin and count
            String floor = path.getPath().get(begin).getFloor();
            if (floor.equals("L2")) {
                Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel2.png"));
                currentMap = "L2";
                changeMapLabel();
            }
            if (floor.equals("L1")) {
                Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel1.png"));
                currentMap = "L1";
                changeMapLabel();
            }
            if (floor.equals("G")) {
                Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thegroundfloor.png"));
                currentMap = "G";
                changeMapLabel();
            }
            if (floor.equals("1")) {
                Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/01_thefirstfloor.png"));
                currentMap = "1";
                changeMapLabel();
            }
            if (floor.equals("2")) {
                Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/02_thesecondfloor.png"));
                currentMap = "2";
                changeMapLabel();
            }
            if (floor.equals("3")) {
                Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/03_thethirdfloor.png"));
                currentMap = "3";
                changeMapLabel();
            }
            if(floor.equals("4")) {
                Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/04_thefourthfloor.png"));
                currentMap = "4";
                changeMapLabel();
            }

            if(currentMap.equals("4")){
                displayFlexSpaces(single.getSimulation());
                displayBookableRooms();
            }
            else{
                hideFlexSpaces();
            }


            //Create all necessary objects for animating path.
            Circle dude = new Circle();
            dude.setCenterX(path.getPath().get(begin).getXcoord() * childPane.getWidth() / Map.getImage().getWidth());
            dude.setCenterY(path.getPath().get(begin).getYcoord() * childPane.getHeight() / Map.getImage().getHeight());
            dude.setRadius(Math.max(5, 5f));
            dude.setFill(new ImagePattern((new Image("/SoftEng_UI_Mockup_Pics/IconPerson.png"))));

            javafx.scene.shape.Path path2 = new javafx.scene.shape.Path();

            //Sets up transition
            PathTransition travel = new PathTransition();

            circles.add(dude);
            //Setting the line display
            for (int i = begin; i < count; i++) {
                Line line = new Line();
                line.setStartX(path.getPath().get(i).getXcoord() * childPane.getWidth() / Map.getImage().getWidth());
                line.setStartY(path.getPath().get(i).getYcoord() * childPane.getHeight() / Map.getImage().getHeight());
                line.setEndX(path.getPath().get(i + 1).getXcoord() * childPane.getWidth() / Map.getImage().getWidth());
                line.setEndY(path.getPath().get(i + 1).getYcoord() * childPane.getHeight() / Map.getImage().getHeight());
                line.setStrokeWidth(2.5);
                line.setStroke(DODGERBLUE);
                lines.add(line);
                pathPane.getChildren().add(line);

                MoveTo next = new MoveTo(line.getStartX(), line.getStartY());
                CubicCurveTo end = new CubicCurveTo(line.getStartX(), line.getStartY(), line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
                path2.getElements().add(next);
                path2.getElements().add(end);
            }
            Circle startCircle = new Circle();

            //Setting the properties of the circle
            startCircle.setCenterX(path.getPath().get(begin).getXcoord() * childPane.getWidth() / Map.getImage().getWidth());
            startCircle.setCenterY(path.getPath().get(begin).getYcoord() * childPane.getHeight() / Map.getImage().getHeight());
            startCircle.setRadius(Math.max(1.5, 1.5f));

            //Fills in information for labels
            startLabel.setLayoutX(path.getPath().get(begin).getXcoord() * childPane.getWidth() / Map.getImage().getWidth() - 20);
            startLabel.setLayoutY(path.getPath().get(begin).getYcoord() * childPane.getHeight() / Map.getImage().getHeight() - 20);
            endLabel.setLayoutX(path.getPath().get(count).getXcoord() * childPane.getWidth() / Map.getImage().getWidth() - 30);
            endLabel.setLayoutY(path.getPath().get(count).getYcoord() * childPane.getHeight() / Map.getImage().getHeight() - 20);
            //if(startLabel.getLayoutX() > endLabel.getLayoutX())
            endLabel.setText(path.getPath().get(count).getLongName());
            //Changing the color of the start circle and start label
            if (path.getPath().get(begin).getLocID().equals(kioskTemp.getLocID())) {
                startCircle.setStroke(Color.BLUE);
                startCircle.setFill(Color.BLUE);
                startLabel.setText(" You are here ");
                startLabel.setStyle("-fx-text-fill: WHITE;-fx-font-size: 6; -fx-background-color: rgba(0, 0, 255, 0.75); -fx-border-color: WHITE; -fx-border-width: 1; -fx-min-width: 40;");
            } else if (path.getPath().get(begin).getLocID().equals(startNode.getLocID())) {
                startCircle.setStroke(Color.GREEN);
                startCircle.setFill(Color.GREEN);
                startLabel.setText(startNode.getLongName());
                startLabel.setStyle("-fx-text-fill: WHITE;-fx-font-size: 6; -fx-background-color: rgba(34, 137, 51, 0.75); -fx-border-color: WHITE; -fx-border-width: 1; -fx-min-width: 40;");
            } else {
                startCircle.setStroke(DODGERBLUE);
                startCircle.setFill(DODGERBLUE);
                startLabel.setText(path.getPath().get(begin).getLongName());
                startLabel.setStyle("-fx-text-fill: WHITE;-fx-font-size: 6; -fx-background-color: rgba(51, 100, 255, 0.75); -fx-border-color: WHITE; -fx-border-width: 1; -fx-min-width: 40;");
            }

            circles.add(startCircle);
            Circle endCircle = new Circle();

            //Setting the properties of the circle
            endCircle.setCenterX(path.getPath().get(count).getXcoord() * childPane.getWidth() / Map.getImage().getWidth());
            endCircle.setCenterY(path.getPath().get(count).getYcoord() * childPane.getHeight() / Map.getImage().getHeight());
            endCircle.setRadius(Math.max(1.5, 1.5f));

            if (path.getPath().get(count).getLocID().equals(endNode.getLocID())) {
                endCircle.setStroke(RED);
                endCircle.setFill(RED);
                endLabel.setStyle("-fx-text-fill: WHITE;-fx-font-size: 6; -fx-background-color: rgba(255, 0, 0, 0.75); -fx-border-color: WHITE; -fx-border-width: 1; -fx-min-width: 40;");
            } else {
                endCircle.setStroke(DODGERBLUE);
                endCircle.setFill(DODGERBLUE);
                endLabel.setStyle("-fx-text-fill: WHITE;-fx-font-size: 6; -fx-background-color: rgba(51, 100, 255, 0.75); -fx-border-color: WHITE; -fx-border-width: 1; -fx-min-width: 40;");
            }
            //Handles cases when you only display one location
            if (path.getPath().get(begin).getLocID().equals(path.getPath().get(count).getLocID()) && path.getPath().get(begin).getLocID().equals(startNode.getLocID())) {
                endLabel.setVisible(false);
                endCircle.setVisible(false);
            } else if (path.getPath().get(begin).getLocID().equals(path.getPath().get(count).getLocID())) {
                startLabel.setVisible(false);
                endLabel.setVisible(true);
                endCircle.setVisible(true);
            } else {
                startLabel.setVisible(true);
                endLabel.setVisible(true);
                endCircle.setVisible(true);
            }
            //Adding everything necessary to display the path
            labels.add(startLabel);
            labels.add(endLabel);
            circles.add(endCircle);
            pathPane.getChildren().add(startCircle);
            pathPane.getChildren().add(endCircle);
            pathPane.getChildren().add(startLabel);
            pathPane.getChildren().add(endLabel);
            //Displays the node that travels
            pathPane.getChildren().add(dude);

            //Sets everything for the animation
            travel.setDuration(Duration.millis(20000));
            travel.setNode(dude);
            travel.setPath(path2);
            travel.setOrientation(PathTransition.OrientationType.NONE);
            travel.setCycleCount(Animation.INDEFINITE);
            travel.setAutoReverse(false);
            travel.play();

            //Auto-zooms the screen
            autoZoom(path.getPath().get(begin), path.getPath().get(count));

            direction.clear();

            ArrayList<Location> al = new ArrayList<Location>();
            for (int i = begin; i < count + 1; i++) {
                al.add(path.getPath().get(i));
            }

            String directionS = printPath(al);
            if (al.size() > 1) {
                if (count + 1 < path.getPath().size()) {
                    if (isStairELe(al.get(al.size() - 1)) && isStairELe(path.getPath().get(count + 1)) &&
                            !al.get(al.size() - 1).getFloor().equals(path.getPath().get(count + 1).getFloor())) {
                        directionS += "\u21C5 Go to floor " + path.getPath().get(count + 1).getFloor() + " by "
                                + al.get(al.size() - 1).getLongName() + "\n";
                    }
                }
            }

            direction.setText(directionS);
            direction.setWrapText(true);
            direction.setDisable(false);
            direction.setEditable(false);

        }

        /**
         * @Author Nikhil
         * Method to clear all path display on map
         */
        private void clear () {
            for (Circle c : circles) {
                pathPane.getChildren().remove(c);
            }
            for (Line l : lines) {
                pathPane.getChildren().remove(l);
            }
            for (Button b : buttons) {
                pathPane.getChildren().remove(b);
            }
            for (JFXButton jb : floorButtons) {
                gridPane.getChildren().remove(jb);
            }
            for (Label la : labels) {
                pathPane.getChildren().remove(la);
            }

            floorButtons.clear();
            circles.clear();
            lines.clear();
            buttons.clear();
            labels.clear();
        }

        /**
         * @Author Nikhil
         * Helper for makeButtons
         * @param floors
         * @return
         */
        private int countFloors (ArrayList < String > floors) {
            int floorCounter = 1;
            for (int i = 0; i < floors.size() - 1; i++) {
                if (!floors.get(i).equals(floors.get(i + 1))) {
                    floorCounter++;
                }
            }
            return floorCounter;
        }

        @FXML
        /**
         * @author Gabe
         * reads the input for the floor combo box
         */
        private void filterFloor () {
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
        if (Floor.getValue() == "4") {
            pickedFloor = "4";
        }
        }

    /**
     * Modified by Nikhil: When start is cleared the start node resets to the kiosk location, to display kiosk location properly.
     */
    @FXML
    private void clearStart(){
        single.setLastTime();
        PathFindStartDrop.getSelectionModel().clearSelection();
        PathFindStartDrop.setValue(null);
        PathFindSubmit.setDisable(true);
        noHall();
    }

        @FXML
        private void clearEnd () {
            single.setLastTime();
            PathFindEndDrop.getSelectionModel().clearSelection();
            PathFindEndDrop.setValue(null);
            PathFindSubmit.setDisable(true);
            noHall();
        }
        @FXML
        /**
         * @author Gabe
         * reads the input for the room type combo box
         */
        private void filterType () {
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
            if (Filter.getValue() == ("Food")) {
                type = "CAFE FOOD VEND";
            }
            if (Filter.getValue() == ("Shops")) {
                type = "GIFT";
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
        private void noHall () {
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

            } else if (Filter.getValue() == (null) && Floor.getValue() == "All") {
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

            } else if (Filter.getValue() == (null) && Floor.getValue() != null) {
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

            } else if (Filter.getValue() == "Food" && Floor.getValue() == null) {
                if (PathFindStartDrop.getValue() == null) {
                    noHallStart.clear();
                    for (int j = 0; j < single.getData().size(); j++) {
                        if (type.contains((single.getData().get(j).getNodeType()))) {
                            noHallStart.add(single.getData().get(j));
                        }
                    }
                }
                if (PathFindEndDrop.getValue() == null) {
                    PathFindEndDrop.setItems(noHallEnd);
                    noHallEnd.clear();
                    for (int j = 0; j < single.getData().size(); j++) {
                        if (type.contains((single.getData().get(j).getNodeType()))) {
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

            } else if (Filter.getValue() == "Food" && Floor.getValue() == "All") {
                if (PathFindStartDrop.getValue() == null) {
                    noHallStart.clear();
                    for (int j = 0; j < single.getData().size(); j++) {
                        if (type.contains((single.getData().get(j).getNodeType()))) {
                            noHallStart.add(single.getData().get(j));
                        }
                    }
                }
                if (PathFindEndDrop.getValue() == null) {
                    PathFindEndDrop.setItems(noHallEnd);
                    noHallEnd.clear();
                    for (int j = 0; j < single.getData().size(); j++) {
                        if (type.contains((single.getData().get(j).getNodeType()))) {
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

            } else if (Filter.getValue() == "Food" && Floor.getValue() != null) {
                if (PathFindStartDrop.getValue() == null) {
                    noHallStart.clear();
                    for (int j = 0; j < single.getData().size(); j++) {
                        if (type.contains((single.getData().get(j).getNodeType())) && (single.getData().get(j).getFloor().equals(pickedFloor))) {
                            noHallStart.add(single.getData().get(j));
                        }
                    }
                }
                if (PathFindEndDrop.getValue() == null) {
                    PathFindEndDrop.setItems(noHallEnd);
                    noHallEnd.clear();
                    for (int j = 0; j < single.getData().size(); j++) {
                        if (type.contains((single.getData().get(j).getNodeType())) && (single.getData().get(j).getFloor().equals(pickedFloor))) {
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
            // Don't worry about this line, but don't delete it, could be used for kiosk implementation later.
            // PathFindStartDrop.getItems().add(0,kioskTemp);
        }

    /**Nathan modified this to include path preference (restriction)
     *
     * @param strategy
     * @param start
     * @param end
     * @param restriction
     * @return
     */
    public Path findAbstractPath(PathfindingStrategy strategy, Location start, Location end, String restriction) {
        single.setLastTime();
        Path p = strategy.findPath(start, end, restriction);
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
        floorList.add("G");
        floorList.add("L1");
        floorList.add("L2");
        floorList.add("1");
        floorList.add("2");
        floorList.add("3");
        floorList.add("4");
    }

    @FXML
    /**
     * @author Gabe
     * Adds the fields that can be used to filter locations by type
     */
    private void filter() {
        filterList.add("All");
        //filterList.add("Food and Retail");
        filterList.add("Restrooms");
        filterList.add("Food");
        filterList.add("Shops");
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

        double scaleRatio = gesturePane.getWidth() / Map.getImage().getWidth();

        for(int i=0; i<single.getData().size(); i++){
            //if nodetype contains keyword
            //System.out.println(currentMap);

            if(single.getData().get(i).getNodeType().contains(keyword) && single.getData().get(i).getFloor().equals(currentMap)/* && data.get(i).getFloor() == kioskNode.getFloor*/){
                nodes.add(single.getData().get(i));

                //System.out.println("ok at least one node gets here");

                Circle thisCircle = new Circle();

                pathPane.getChildren().add(thisCircle);

                //Setting the properties of the circle
                thisCircle.setCenterX((nodes.get(temp).getXcoord())*gesturePane.getWidth() / Map.getImage().getWidth());
                thisCircle.setCenterY((nodes.get(temp).getYcoord())*gesturePane.getHeight() / Map.getImage().getHeight());
                thisCircle.setRadius(Math.max(1.5,1.5f*(gesturePane.getCurrentScale()/5)));
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
            //System.out.println(single.getData().get(i).getNodeType());

            if(single.getData().get(i).getNodeType().contains(keyword) && (keyword.equals("CAFE") || keyword.equals("FOOD") || keyword.equals("GIFT"))){
                nodes.add(single.getData().get(i));
                //System.out.println("oooooooooooooooooooooooooooooooooooooooooooo");
            }
            else if (single.getData().get(i).getNodeType().contains(keyword) && single.getData().get(i).getFloor().equals(kioskTemp.getFloor())) {
                nodes.add(single.getData().get(i));
                //System.out.println("node added");
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
            Path closestPath = findAbstractPath(astar,kioskTemp, nodes.get(0), "    "); //?

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
                }
            }
            closestPath = findAbstractPath(astar,kioskTemp, closestLOC, "    ");
            pathReportAccess p = new pathReportAccess();
            p.addReport(Long.toString(System.currentTimeMillis()), kioskTemp.getLongName(), closestLOC.getLongName(), "poi");

            //displayPath(closestPath.getPath(), kioskTemp, closestLOC);

            displayingPath = true;

            startNode = kioskTemp;
            endNode = closestLOC;
            path = closestPath;

            displayPath();
            //printPath(path.getPath());
            direction.setText(printPath(path.getPath()));


        }
        //do not display any path
    }

    /**Grace Made this
     * just resets the radio buttons
     */
    public void resetRadButts(){
        for (Circle c: circles) {
            pathPane.getChildren().remove(c);
        }
        for (Line l: lines) {
            pathPane.getChildren().remove(l);
        }
        bathroomRadButton.setSelected(false);
        bathroomRadButton.setTextFill(Color.web("#ffffff"));
        cafeRadButton.setSelected(false);
        cafeRadButton.setTextFill(Color.web("#ffffff"));
        eleRadButton.setSelected(false);
        eleRadButton.setTextFill(Color.web("#ffffff"));
        stairsRadButton.setSelected(false);
        stairsRadButton.setTextFill(Color.web("#ffffff"));
        vendRadButton.setSelected(false);
        vendRadButton.setTextFill(Color.web("#ffffff"));
        giftRadButton.setSelected(false);
        giftRadButton.setTextFill(Color.web("#ffffff"));
        cuisineRadButton.setSelected(false);
        cuisineRadButton.setTextFill(Color.web("#ffffff"));
        //reset the text path display to empty/////////////////////////////////////////////////////////////
        direction.setText("");
    }
    public void specialResetRadButts(){
        bathroomRadButton.setSelected(false);
        bathroomRadButton.setTextFill(Color.web("#ffffff"));
        eleRadButton.setSelected(false);
        eleRadButton.setTextFill(Color.web("#ffffff"));
        stairsRadButton.setSelected(false);
        stairsRadButton.setTextFill(Color.web("#ffffff"));
        vendRadButton.setSelected(false);
        vendRadButton.setTextFill(Color.web("#ffffff"));
    }

    /**Grace made this
     * just sets the kiosk to an actual location
     */
    public void checkAndSetKiosk(){
        //if kiosk was initiated its fine
        //if not set kiosk to random (first location stuff) thing
        if(single.getKiosk() == null){
            //Location kioskTemp = single.getData().get(0); //initially at floor 2
            for(int i = 0; i < single.lookup.size() - 1; i++) {
                if(single.getData().get(i).getLocID().contains("KIOS")) {
                    single.setKiosk(single.getData().get(i));
                }
            }
            if(single.getKiosk() == null) {
                single.setKiosk(single.getData().get(0));
            }
        }
        //find actual "location" of kiosk
        for(int i=0; i<single.getData().size(); i++){
            if(single.getData().get(i).equals(single.getKiosk())){
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
        switchToKioskFloor();
        //when pressed, change color to #f5d96b (gold/yellow), to do later
        //display and find closest bathroom
        //System.out.println("find closest bathroom selected");

        if(bathroomRadButton.isSelected()) {
            resetRadButts();
            bathroomRadButton.setSelected(true);
            bathroomRadButton.setTextFill(Color.web("#f5d96b"));

            if((currentMap.equals(kioskTemp.getFloor()))){
                displayClosestPOI("REST");
            }
            displayPOINodes("REST");
            // for some reason displaying poi nodes cannot go before displaying the closest path
        }
        else if(!bathroomRadButton.isSelected()){
            bathroomRadButton.setTextFill(Color.web("#ffffff"));
            endLabel.setVisible(false);
            for (Circle c: circles) {
                pathPane.getChildren().remove(c);
            }
            for (Line l: lines) {
                pathPane.getChildren().remove(l);
            }
        }
    }
    /** GRACE MADE THIS
     *display and find closest cafe/vending/retail
     */
    @FXML
    private void cafeRadButtPressed(){
        checkAndSetKiosk();
        switchToKioskFloor();
        //when pressed, change color to #f5d96b (gold/yellow)
        //display and find closest cafe - nodeType is RETL
        //System.out.println("find closest retail/food selected");

        if(cafeRadButton.isSelected()) {
            specialResetRadButts();
            giftRadButton.setSelected(false);
            giftRadButton.setTextFill(Color.web("#ffffff"));
            cuisineRadButton.setSelected(false);
            cuisineRadButton.setTextFill(Color.web("#ffffff"));

            cafeRadButton.setSelected(true);
            cafeRadButton.setTextFill(Color.web("#f5d96b"));
            // -----------------------------------------------------------------------???????????????????????????

            displayClosestPOI("CAFE");

            displayPOINodes("CAFE");
        }
        if(!cafeRadButton.isSelected()){
            endLabel.setVisible(false);
            cafeRadButton.setTextFill(Color.web("#ffffff"));
            for (Circle c: circles) {
                pathPane.getChildren().remove(c);
            }
            for (Line l: lines) {
                pathPane.getChildren().remove(l);
            }
        }
    }
    @FXML
    private void cuisineRadButtPressed(){
        checkAndSetKiosk();
        switchToKioskFloor();
        //when pressed, change color to #f5d96b (gold/yellow), to do later
        //display and find closest bathroom
        //System.out.println("find closest bathroom selected");

        if(cuisineRadButton.isSelected()) {
            specialResetRadButts();
            giftRadButton.setSelected(false);
            giftRadButton.setTextFill(Color.web("#ffffff"));
            cafeRadButton.setSelected(false);
            cafeRadButton.setTextFill(Color.web("#ffffff"));

            cuisineRadButton.setSelected(true);
            cuisineRadButton.setTextFill(Color.web("#f5d96b"));


            displayClosestPOI("FOOD");

            displayPOINodes("FOOD");
            // for some reason displaying poi nodes cannot go before displaying the closest path
        }
        else if(!cuisineRadButton.isSelected()){
            endLabel.setVisible(false);
            cuisineRadButton.setTextFill(Color.web("#ffffff"));
            for (Circle c: circles) {
                pathPane.getChildren().remove(c);
            }
            for (Line l: lines) {
                pathPane.getChildren().remove(l);
            }
        }
    }
    @FXML
    private void vendRadButtPressed(){
        checkAndSetKiosk();
        switchToKioskFloor();
        //when pressed, change color to #f5d96b (gold/yellow), to do later
        //display and find closest bathroom
        //System.out.println("find closest bathroom selected");

        if(vendRadButton.isSelected()) {
            resetRadButts();
            vendRadButton.setSelected(true);
            vendRadButton.setTextFill(Color.web("#f5d96b"));

            if((currentMap.equals(kioskTemp.getFloor()))){
                displayClosestPOI("VEND");
            }
            displayPOINodes("VEND");
            // for some reason displaying poi nodes cannot go before displaying the closest path
        }
        else if(!vendRadButton.isSelected()){
            endLabel.setVisible(false);
            vendRadButton.setTextFill(Color.web("#ffffff"));
            for (Circle c: circles) {
                pathPane.getChildren().remove(c);
            }
            for (Line l: lines) {
                pathPane.getChildren().remove(l);
            }
        }
    }
    @FXML
    private void giftRadButtPressed(){
        checkAndSetKiosk();
        switchToKioskFloor();
        //when pressed, change color to #f5d96b (gold/yellow), to do later
        //display and find closest bathroom
        //System.out.println("find closest bathroom selected");

        if(giftRadButton.isSelected()) {
            specialResetRadButts();
            cafeRadButton.setSelected(false);
            cafeRadButton.setTextFill(Color.web("#ffffff"));
            cuisineRadButton.setSelected(false);
            cuisineRadButton.setTextFill(Color.web("#ffffff"));

            giftRadButton.setSelected(true);
            giftRadButton.setTextFill(Color.web("#f5d96b"));

            displayClosestPOI("GIFT");
            displayPOINodes("GIFT");
            // for some reason displaying poi nodes cannot go before displaying the closest path
        }
        else if(!giftRadButton.isSelected()){
            endLabel.setVisible(false);
            giftRadButton.setTextFill(Color.web("#ffffff"));
            for (Circle c: circles) {
                pathPane.getChildren().remove(c);
            }
            for (Line l: lines) {
                pathPane.getChildren().remove(l);
            }
        }
    }
    /** GRACE MADE THIS
     *display and find closest elevator
     */
    @FXML
    private void eleRadButtPressed(){
        checkAndSetKiosk();
        switchToKioskFloor();
        //when pressed, change color to #f5d96b (gold/yellow)
        //display and find closest elevator
        //System.out.println("find closest elevator selected");

        if(eleRadButton.isSelected()) {
            resetRadButts();
            eleRadButton.setSelected(true);
            eleRadButton.setTextFill(Color.web("#f5d96b"));

            if((currentMap.equals(kioskTemp.getFloor()))) {
                displayClosestPOI("ELEV");
            }
            displayPOINodes("ELEV");
        }
        if(!eleRadButton.isSelected()){
            endLabel.setVisible(false);
            eleRadButton.setTextFill(Color.web("#ffffff"));
            for (Circle c: circles) {
                pathPane.getChildren().remove(c);
            }
            for (Line l: lines) {
                pathPane.getChildren().remove(l);
            }
        }
    }
    /** GRACE MADE THIS
     *display and find closest stairs
     */
    @FXML
    private void stairsRadButtPressed(){
        checkAndSetKiosk();
        switchToKioskFloor();
        //when pressed, change color to #f5d96b (gold/yellow)
        //display and find closest stairs
        //System.out.println("find closest stairs selected");

        if(stairsRadButton.isSelected()) {
            resetRadButts();
            stairsRadButton.setSelected(true);
            stairsRadButton.setTextFill(Color.web("#f5d96b"));

            if((currentMap.equals(kioskTemp.getFloor()))) {
                displayClosestPOI("STAI");
            }
            displayPOINodes("STAI");
        }
        if(! stairsRadButton.isSelected()){
            endLabel.setVisible(false);
            stairsRadButton.setTextFill(Color.web("#ffffff"));
            for (Circle c: circles) {
                pathPane.getChildren().remove(c);
            }
            for (Line l: lines) {
                pathPane.getChildren().remove(l);
            }
        }
    }


    public void switchToKioskFloor(){
        checkAndSetKiosk();
        currentMap = kioskTemp.getFloor();
        if (currentMap.equals("L2")) {
            clickedL2();
        }
        if (currentMap.equals("L1")) {
            clickedL1();
        }
        if (currentMap.equals("G")) {
            clickedG();
        }
        if (currentMap.equals("1")) {
            clicked1();
        }
        if (currentMap.equals("2")) {
            clicked2();
        }
        if (currentMap.equals("3")) {
            clicked3();
        }
        if (currentMap.equals("4")) {
            clicked4();
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
        String aType;
        String bType;
        String aFloor;
        String bFloor;
        int curDirection = 0;
        int nextDirection = 0;
        String text = "";

        int d = 0; // count for the start location for exact location
        //same start and end location
        if(A.size() == 1){
            text += "You are already at your destination :)\n";
            return text;
        }
        if(A.size() == 2 && A.get(0) == A.get(1)){
            text += "You are already at your destination :)\n";
            return text;
        }
        double time = estimateTime(A);
        text += "Your estimated travel time is: " + estimateTime(A);
        if(time > 1){
            text += " minutes \n";
        }
        else {
            text += " minute \n";
        }
        text +="\u235FBegin from " + A.get(0).getLongName() + "\n";
        //when size is two, but two location are different
        if(A.size() == 2){
            aType = A.get(0).getNodeType();
            bType = A.get(1).getNodeType();
            aFloor = A.get(0).getFloor();
            bFloor = A.get(1).getFloor();
            if((aType=="STAI" || aType == "ELEV") && (bType == "STAI" || bType =="ELEV") && !aFloor.equals(bFloor) ){
                if(A.get(1).getNodeType() == "STAI" ||A.get(1).getNodeType() == "ELEV" ){
                    //  System.out.println("Go to floor " + bFloor + " by " + bType);
                    text += "\u21C5 Go to floor " + bFloor + " by " + bType + "\n";

                    return text;
                }
                else{
                        text += "\u2191 Go straight to " + A.get(1).getLongName() + " (" +
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


                text += "\u2191 Go straight to " + b.getLongName()
                        + " (" + convertToExact(start.findDistance(b)) + " ft) \n";

                //- -> + , x+ : left
                d = i + 1;
                double slopeAB = calculateSlope(a,b);
                double slopeBC = calculateSlope(b,c);
                if(curDirection == nextDirection){
                    if(curDirection == 2 || curDirection == 6){
                        if(Math.abs(slopeBC)> Math.abs(slopeAB)){
                            text += "\u21B0 Turn left \n";
                        }
                        else{
                            text += "\u21B1 Turn right\n";
                        }
                    }
                    else if(curDirection == 4 || curDirection ==8){
                        if(Math.abs(slopeBC)> Math.abs(slopeAB)){
                            text += "\u21B1 Turn right\n";
                        }
                        else{
                            text += "\u21B0 Turn left \n";
                        }

                    }

                }
                else if((curDirection == 2 && nextDirection ==6) || (curDirection == 6 && nextDirection ==2)){
                    if(Math.abs(slopeBC)>Math.abs(slopeAB)){
                        text += "\u21B1 Turn right\n";
                    }
                    else{
                        text += "\u21B0 Turn left\n";
                    }

                }

                else if((curDirection == 8 && nextDirection ==4) || (curDirection == 6 && nextDirection ==2)){
                    if(Math.abs(slopeBC)>Math.abs(slopeAB)){
                        text += "\u21B0 Turn left\n";
                    }
                    else{
                        text += "\u21B1 Turn right\n";
                    }

                }

                else if(curDirection <= 5){
                    if(nextDirection < curDirection + 4 && nextDirection > curDirection){
                        text += "\u21B1 Turn right\n";
                    }
                    else {

                        text += "\u21B0 Turn left\n";
                    }
                }
                else{
                    if(curDirection == 6){
                        if(nextDirection == 7 || nextDirection == 8 || nextDirection == 1){
                            text += "\u21B1 Turn right\n";
                        }
                        if(nextDirection == 5 || nextDirection == 4 || nextDirection == 3){
                            text += "\u21B0 Turn left\n";
                        }


                    }
                    else if (curDirection ==7){
                        if(nextDirection == 8 || nextDirection == 1 || nextDirection == 2){
                            text += "\u21B1 Turn right\n";
                        }
                        else if(nextDirection == 6 || nextDirection == 5 || nextDirection == 4){
                            text += "\u21B0 Turn left\n";
                        }
                    }
                    else if(curDirection ==8){
                        if(nextDirection == 1 || nextDirection == 2 || nextDirection == 3){
                            text += "\u21B1 Turn right\n";
                        }
                        else if(nextDirection == 5 || nextDirection == 6 || nextDirection == 7){
                            text += "\u21B0 Turn left\n";
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
                text += "\u2191 Go straight to " + A.get(A.size()-1).getLongName() +
                        " (" + convertToExact(A.get(A.size()-3).findDistance(A.get(A.size()-1))) + " ft) \n";
                return text;
            }

        }

        return text;
    }
    //Larry - Calculate estimate time to finish the whole path
    private double estimateTime(ArrayList<Location> A){
        double totalDistance = 0.0;
        double currentDistance = 0.0;
        double minutes = 0.0;
        for(int i = 0; i<A.size()-1; i++){
            currentDistance = A.get(i).findDistance(A.get(i+1));
            totalDistance += currentDistance;
        }
        //on average, walking speed 4.6 ft / sec
        minutes = convertToExact(totalDistance) / (4.6 * 60);

        return (int) (minutes * 100) / 100.0;

    }

    EventHandler<KeyEvent> searchFieldKeyPress = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            //sp.setVisible(false);
            autoList.takeTopTen(noHallEnd, searchField.getText());
            //System.out.println(autoList);
            suggestions.getChildren().removeAll(suggestions.getChildren());
            if (autoList.size() > 0) {
                //sp = new ScrollPane();
                //GridPane gp = new GridPane();
                VBox vBox = new VBox();
                for (int i = 0; i < autoList.size(); i ++) {
                    JFXButton b = new JFXButton(autoList.get(i).toString());
                    Font f = new Font("System", 15);
                    b.setFont(f);
                    Tooltip t = new Tooltip("Click to travel here");
                    b.setPadding(new Insets(1));
                    b.setPrefWidth(searchField.getPrefWidth());
                    b.setTooltip(t);
                    // b.getStyleClass().set(1, "buttonMain");
                    b.getStyleClass().add("buttonMain");
                    b.setOnAction(event1 -> {
                        String toString = ((JFXButton) event1.getSource()).getText();
                        for (Location l : single.lookup.values()) {
                            if (l.toString().equals(toString)) {
                                if(!(PathFindStartDrop.getValue() == null)) {
                                    PathFindEndDrop.setValue(l);
                                }
                                else{
                                    PathFindStartDrop.setValue(l);
                                }
                                typeSelected = "search";
                                searchField.setText(l.toString());
                            }
                        }
                        suggestions.setVisible(false);
                        if((PathFindStartDrop.getValue() != null || startNode != null) && PathFindEndDrop.getValue() != null){
                            PathFindSubmit.setDisable(false);
                        }
                        else{
                            PathFindSubmit.setDisable(true);
                            direction.setDisable(true);
                            direction.setEditable(false);
                        }
                    });
                    vBox.getChildren().add(b);

                    Text newText = new Text(autoList.get(i).toString());

                }
                suggestions.getChildren().add(vBox);
                suggestions.setLayoutX(searchField.getLayoutX() + 2);
                suggestions.setLayoutY(searchField.getLayoutY()+50);
                suggestions.toFront();
                suggestions.setVisible(true);
                //sp.setContent(gp);
                //sp.setLayoutX(searchField.getLayoutX() + 2);
                //sp.setLayoutY(searchField.getLayoutY()+50);
                //sp.toFront();
                //sp.setVisible(true);
            }
            else {
                suggestions.setVisible(false);
            }

        }
    };

    //Alex
    @FXML
    public void submitSearchField(Event ae) {
        searchField.setText("");
        Filter.setValue(null);
        Floor.setValue(null);
        noHall();
    }

    public void displayFlexSpaces(ArrayList<Boolean> flexSpaceAvailable){
        hideFlexSpaces();

        double srWidth = gesturePane.getWidth() / Map.getImage().getWidth();
        double srHeight = gesturePane.getHeight() / Map.getImage().getHeight();

        //large important room
        flexSpaces.add(new Polygon((860+11310)* srWidth *.303, ( 1270+5624)* srHeight *.3023, ( 1160+11310)* srWidth *.303, ( 1270+5624)* srHeight *.3023, ( 1160+11310)* srWidth *.303, ( 1580+5624)* srHeight *.3023, ( 860+11310)* srWidth *.303, ( 1580+5624)* srHeight *.3023));

        flexSpaces.add(new Polygon((150+11310)* srWidth *.303, ( 230+5624)* srHeight *.3023, ( 200+11310)* srWidth *.303, ( 230+5624)* srHeight *.3023, ( 200+11310)* srWidth *.303, ( 340+5624)* srHeight *.3023, ( 150+11310)* srWidth *.303, ( 340+5624)* srHeight *.3023)); //left side desk top
        flexSpaces.add(new Polygon((150+11310)* srWidth *.303, ( 420+5624)* srHeight *.3023, ( 200+11310)* srWidth *.303, ( 420+5624)* srHeight *.3023, ( 200+11310)* srWidth *.303, ( 530+5624)* srHeight *.3023, ( 150+11310)* srWidth *.303, ( 530+5624)* srHeight *.3023)); //left side desk middle
        flexSpaces.add(new Polygon((150+11310)* srWidth *.303, ( 590+5624)* srHeight *.3023, ( 200+11310)* srWidth *.303, ( 590+5624)* srHeight *.3023, ( 200+11310)* srWidth *.303, ( 740+5624)* srHeight *.3023, ( 150+11310)* srWidth *.303, ( 740+5624)* srHeight *.3023)); //left side desk lower
        flexSpaces.add(new Polygon((340+11310)* srWidth *.303, ( 560+5624)* srHeight *.3023, ( 540+11310)* srWidth *.303, ( 560+5624)* srHeight *.3023, ( 540+11310)* srWidth *.303, ( 710+5624)* srHeight *.3023, ( 340+11310)* srWidth *.303, ( 710+5624)* srHeight *.3023)); //left conference room
        flexSpaces.add(new Polygon((200+11310)* srWidth *.303, ( 1300+5624)* srHeight *.3023, ( 270+11310)* srWidth *.303, ( 1300+5624)* srHeight *.3023, ( 270+11310)* srWidth *.303, ( 1360+5624)* srHeight *.3023, ( 200+11310)* srWidth *.303, ( 1360+5624)* srHeight *.3023)); //small three chairs
        flexSpaces.add(new Polygon((570+11310)* srWidth *.303, ( 1340+5624)* srHeight *.3023, ( 630+11310)* srWidth *.303, ( 1340+5624)* srHeight *.3023, ( 630+11310)* srWidth *.303, ( 1430+5624)* srHeight *.3023, ( 570+11310)* srWidth *.303, ( 1430+5624)* srHeight *.3023)); //standalone square
        flexSpaces.add(new Polygon((1320+11310)* srWidth *.303, ( 2070+5624)* srHeight *.3023, ( 1380+11310)* srWidth *.303, ( 2070+5624)* srHeight *.3023, ( 1380+11310)* srWidth *.303, ( 2100+5624)* srHeight *.3023, ( 1320+11310)* srWidth *.303, ( 2100+5624)* srHeight *.3023)); //small two chairs
        flexSpaces.add(new Polygon((1980+11310)* srWidth *.303, ( 1690+5624)* srHeight *.3023, ( 2230+11310)* srWidth *.303, ( 1690+5624)* srHeight *.3023, ( 2230+11310)* srWidth *.303, ( 1880+5624)* srHeight *.3023, ( 1980+11310)* srWidth *.303, ( 1880+5624)* srHeight *.3023)); //bottom right room
        flexSpaces.add(new Polygon((1760+11310)* srWidth *.303, ( 2230+5624)* srHeight *.3023, ( 1820+11310)* srWidth *.303, ( 2230+5624)* srHeight *.3023, ( 1820+11310)* srWidth *.303, ( 2380+5624)* srHeight *.3023, ( 1760+11310)* srWidth *.303, ( 2380+5624)* srHeight *.3023)); //bottom vertical top
        flexSpaces.add(new Polygon((1740+11310)* srWidth *.303, ( 2440+5624)* srHeight *.3023, ( 1800+11310)* srWidth *.303, ( 2440+5624)* srHeight *.3023, ( 1800+11310)* srWidth *.303, ( 2600+5624)* srHeight *.3023, ( 1740+11310)* srWidth *.303, ( 2600+5624)* srHeight *.3023)); //bottom vertical bottom

        //top left desk bank
        flexSpaces.add(new Polygon((240+11310)* srWidth *.303, ( 240+5624)* srHeight *.3023, ( 305+11310)* srWidth *.303, ( 240+5624)* srHeight *.3023, ( 305+11310)* srWidth *.303, ( 290+5624)* srHeight *.3023, ( 240+11310)* srWidth *.303, ( 290+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((305+11310)* srWidth *.303, ( 240+5624)* srHeight *.3023, ( 370+11310)* srWidth *.303, ( 240+5624)* srHeight *.3023, ( 370+11310)* srWidth *.303, ( 290+5624)* srHeight *.3023, ( 305+11310)* srWidth *.303, ( 290+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((370+11310)* srWidth *.303, ( 240+5624)* srHeight *.3023, ( 435+11310)* srWidth *.303, ( 240+5624)* srHeight *.3023, ( 435+11310)* srWidth *.303, ( 290+5624)* srHeight *.3023, ( 370+11310)* srWidth *.303, ( 290+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((435+11310)* srWidth *.303, ( 240+5624)* srHeight *.3023, ( 500+11310)* srWidth *.303, ( 240+5624)* srHeight *.3023, ( 500+11310)* srWidth *.303, ( 290+5624)* srHeight *.3023, ( 435+11310)* srWidth *.303, ( 290+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((240+11310)* srWidth *.303, ( 290+5624)* srHeight *.3023, ( 305+11310)* srWidth *.303, ( 290+5624)* srHeight *.3023, ( 305+11310)* srWidth *.303, ( 340+5624)* srHeight *.3023, ( 240+11310)* srWidth *.303, ( 340+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((305+11310)* srWidth *.303, ( 290+5624)* srHeight *.3023, ( 370+11310)* srWidth *.303, ( 290+5624)* srHeight *.3023, ( 370+11310)* srWidth *.303, ( 340+5624)* srHeight *.3023, ( 305+11310)* srWidth *.303, ( 340+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((370+11310)* srWidth *.303, ( 290+5624)* srHeight *.3023, ( 435+11310)* srWidth *.303, ( 290+5624)* srHeight *.3023, ( 435+11310)* srWidth *.303, ( 340+5624)* srHeight *.3023, ( 370+11310)* srWidth *.303, ( 340+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((435+11310)* srWidth *.303, ( 290+5624)* srHeight *.3023, ( 500+11310)* srWidth *.303, ( 290+5624)* srHeight *.3023, ( 500+11310)* srWidth *.303, ( 340+5624)* srHeight *.3023, ( 435+11310)* srWidth *.303, ( 340+5624)* srHeight *.3023));

        //second down top left desk bank
        flexSpaces.add(new Polygon((250+11310)* srWidth *.303, ( 390+5624)* srHeight *.3023, ( 313+11310)* srWidth *.303, ( 390+5624)* srHeight *.3023, ( 313+11310)* srWidth *.303, ( 440+5624)* srHeight *.3023, ( 250+11310)* srWidth *.303, ( 440+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((313+11310)* srWidth *.303, ( 390+5624)* srHeight *.3023, ( 375+11310)* srWidth *.303, ( 390+5624)* srHeight *.3023, ( 375+11310)* srWidth *.303, ( 440+5624)* srHeight *.3023, ( 313+11310)* srWidth *.303, ( 440+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((375+11310)* srWidth *.303, ( 390+5624)* srHeight *.3023, ( 438+11310)* srWidth *.303, ( 390+5624)* srHeight *.3023, ( 438+11310)* srWidth *.303, ( 440+5624)* srHeight *.3023, ( 375+11310)* srWidth *.303, ( 440+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((438+11310)* srWidth *.303, ( 390+5624)* srHeight *.3023, ( 500+11310)* srWidth *.303, ( 390+5624)* srHeight *.3023, ( 500+11310)* srWidth *.303, ( 440+5624)* srHeight *.3023, ( 438+11310)* srWidth *.303, ( 440+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((250+11310)* srWidth *.303, ( 440+5624)* srHeight *.3023, ( 313+11310)* srWidth *.303, ( 440+5624)* srHeight *.3023, ( 313+11310)* srWidth *.303, ( 490+5624)* srHeight *.3023, ( 250+11310)* srWidth *.303, ( 490+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((313+11310)* srWidth *.303, ( 440+5624)* srHeight *.3023, ( 375+11310)* srWidth *.303, ( 440+5624)* srHeight *.3023, ( 375+11310)* srWidth *.303, ( 490+5624)* srHeight *.3023, ( 313+11310)* srWidth *.303, ( 490+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((375+11310)* srWidth *.303, ( 440+5624)* srHeight *.3023, ( 438+11310)* srWidth *.303, ( 440+5624)* srHeight *.3023, ( 438+11310)* srWidth *.303, ( 490+5624)* srHeight *.3023, ( 375+11310)* srWidth *.303, ( 490+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((438+11310)* srWidth *.303, ( 440+5624)* srHeight *.3023, ( 500+11310)* srWidth *.303, ( 440+5624)* srHeight *.3023, ( 500+11310)* srWidth *.303, ( 490+5624)* srHeight *.3023, ( 438+11310)* srWidth *.303, ( 490+5624)* srHeight *.3023));

        //center column of small rooms
        flexSpaces.add(new Polygon((660+11310)* srWidth *.303, ( 660+5624)* srHeight *.3023, ( 730+11310)* srWidth *.303, ( 660+5624)* srHeight *.3023, ( 730+11310)* srWidth *.303, ( 748+5624)* srHeight *.3023, ( 660+11310)* srWidth *.303, ( 748+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((660+11310)* srWidth *.303, ( 748+5624)* srHeight *.3023, ( 730+11310)* srWidth *.303, ( 748+5624)* srHeight *.3023, ( 730+11310)* srWidth *.303, ( 836+5624)* srHeight *.3023, ( 660+11310)* srWidth *.303, ( 836+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((660+11310)* srWidth *.303, ( 836+5624)* srHeight *.3023, ( 730+11310)* srWidth *.303, ( 836+5624)* srHeight *.3023, ( 730+11310)* srWidth *.303, ( 924+5624)* srHeight *.3023, ( 660+11310)* srWidth *.303, ( 924+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((660+11310)* srWidth *.303, ( 924+5624)* srHeight *.3023, ( 730+11310)* srWidth *.303, ( 924+5624)* srHeight *.3023, ( 730+11310)* srWidth *.303, ( 1012+5624)* srHeight *.3023, ( 660+11310)* srWidth *.303, ( 1012+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((660+11310)* srWidth *.303, ( 1012+5624)* srHeight *.3023, ( 730+11310)* srWidth *.303, ( 1012+5624)* srHeight *.3023, ( 730+11310)* srWidth *.303, ( 1100+5624)* srHeight *.3023, ( 660+11310)* srWidth *.303, ( 1100+5624)* srHeight *.3023));

        //upper vertical column of desks - right
        flexSpaces.add(new Polygon((1410+11310)* srWidth *.303, ( 190+5624)* srHeight *.3023, ( 1460+11310)* srWidth *.303, ( 190+5624)* srHeight *.3023, ( 1460+11310)* srWidth *.303, ( 260+5624)* srHeight *.3023, ( 1410+11310)* srWidth *.303, ( 260+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1410+11310)* srWidth *.303, ( 260+5624)* srHeight *.3023, ( 1460+11310)* srWidth *.303, ( 260+5624)* srHeight *.3023, ( 1460+11310)* srWidth *.303, ( 322+5624)* srHeight *.3023, ( 1410+11310)* srWidth *.303, ( 322+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1410+11310)* srWidth *.303, ( 322+5624)* srHeight *.3023, ( 1460+11310)* srWidth *.303, ( 322+5624)* srHeight *.3023, ( 1460+11310)* srWidth *.303, ( 388+5624)* srHeight *.3023, ( 1410+11310)* srWidth *.303, ( 388+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1410+11310)* srWidth *.303, ( 388+5624)* srHeight *.3023, ( 1460+11310)* srWidth *.303, ( 388+5624)* srHeight *.3023, ( 1460+11310)* srWidth *.303, ( 454+5624)* srHeight *.3023, ( 1410+11310)* srWidth *.303, ( 454+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1410+11310)* srWidth *.303, ( 454+5624)* srHeight *.3023, ( 1460+11310)* srWidth *.303, ( 454+5624)* srHeight *.3023, ( 1460+11310)* srWidth *.303, ( 520+5624)* srHeight *.3023, ( 1410+11310)* srWidth *.303, ( 520+5624)* srHeight *.3023));

        //upper vertical column of desks - left
        flexSpaces.add(new Polygon((1360+11310)* srWidth *.303, ( 270+5624)* srHeight *.3023, ( 1410+11310)* srWidth *.303, ( 270+5624)* srHeight *.3023, ( 1410+11310)* srWidth *.303, ( 333+5624)* srHeight *.3023, ( 1360+11310)* srWidth *.303, ( 333+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1360+11310)* srWidth *.303, ( 333+5624)* srHeight *.3023, ( 1410+11310)* srWidth *.303, ( 333+5624)* srHeight *.3023, ( 1410+11310)* srWidth *.303, ( 395+5624)* srHeight *.3023, ( 1360+11310)* srWidth *.303, ( 395+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1360+11310)* srWidth *.303, ( 395+5624)* srHeight *.3023, ( 1410+11310)* srWidth *.303, ( 395+5624)* srHeight *.3023, ( 1410+11310)* srWidth *.303, ( 458+5624)* srHeight *.3023, ( 1360+11310)* srWidth *.303, ( 458+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1360+11310)* srWidth *.303, ( 458+5624)* srHeight *.3023, ( 1410+11310)* srWidth *.303, ( 458+5624)* srHeight *.3023, ( 1410+11310)* srWidth *.303, ( 520+5624)* srHeight *.3023, ( 1360+11310)* srWidth *.303, ( 520+5624)* srHeight *.3023));

        //center horizontal desks left
        flexSpaces.add(new Polygon((1240+11310)* srWidth *.303, ( 1540+5624)* srHeight *.3023, ( 1303+11310)* srWidth *.303, ( 1540+5624)* srHeight *.3023, ( 1303+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1240+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1303+11310)* srWidth *.303, ( 1540+5624)* srHeight *.3023, ( 1367+11310)* srWidth *.303, ( 1540+5624)* srHeight *.3023, ( 1367+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1303+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1367+11310)* srWidth *.303, ( 1540+5624)* srHeight *.3023, ( 1430+11310)* srWidth *.303, ( 1540+5624)* srHeight *.3023, ( 1430+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1367+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1240+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1303+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1303+11310)* srWidth *.303, ( 1640+5624)* srHeight *.3023, ( 1240+11310)* srWidth *.303, ( 1640+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1303+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1367+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1367+11310)* srWidth *.303, ( 1640+5624)* srHeight *.3023, ( 1303+11310)* srWidth *.303, ( 1640+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1367+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1430+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1430+11310)* srWidth *.303, ( 1640+5624)* srHeight *.3023, ( 1367+11310)* srWidth *.303, ( 1640+5624)* srHeight *.3023));

        //center horizontal desks right
        flexSpaces.add(new Polygon((1490+11310)* srWidth *.303, ( 1540+5624)* srHeight *.3023, ( 1553+11310)* srWidth *.303, ( 1540+5624)* srHeight *.3023, ( 1553+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1490+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1553+11310)* srWidth *.303, ( 1540+5624)* srHeight *.3023, ( 1617+11310)* srWidth *.303, ( 1540+5624)* srHeight *.3023, ( 1617+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1553+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1617+11310)* srWidth *.303, ( 1540+5624)* srHeight *.3023, ( 1680+11310)* srWidth *.303, ( 1540+5624)* srHeight *.3023, ( 1680+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1617+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1490+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1553+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1553+11310)* srWidth *.303, ( 1640+5624)* srHeight *.3023, ( 1490+11310)* srWidth *.303, ( 1640+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1553+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1617+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1617+11310)* srWidth *.303, ( 1640+5624)* srHeight *.3023, ( 1553+11310)* srWidth *.303, ( 1640+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1617+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1680+11310)* srWidth *.303, ( 1590+5624)* srHeight *.3023, ( 1680+11310)* srWidth *.303, ( 1640+5624)* srHeight *.3023, ( 1617+11310)* srWidth *.303, ( 1640+5624)* srHeight *.3023));

        //center vertical column of desks
        flexSpaces.add(new Polygon((1520+11310)* srWidth *.303, ( 1190+5624)* srHeight *.3023, ( 1570+11310)* srWidth *.303, ( 1190+5624)* srHeight *.3023, ( 1570+11310)* srWidth *.303, ( 1252+5624)* srHeight *.3023, ( 1520+11310)* srWidth *.303, ( 1252+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1520+11310)* srWidth *.303, ( 1252+5624)* srHeight *.3023, ( 1570+11310)* srWidth *.303, ( 1252+5624)* srHeight *.3023, ( 1570+11310)* srWidth *.303, ( 1314+5624)* srHeight *.3023, ( 1520+11310)* srWidth *.303, ( 1314+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1520+11310)* srWidth *.303, ( 1314+5624)* srHeight *.3023, ( 1570+11310)* srWidth *.303, ( 1314+5624)* srHeight *.3023, ( 1570+11310)* srWidth *.303, ( 1376+5624)* srHeight *.3023, ( 1520+11310)* srWidth *.303, ( 1376+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1520+11310)* srWidth *.303, ( 1376+5624)* srHeight *.3023, ( 1570+11310)* srWidth *.303, ( 1376+5624)* srHeight *.3023, ( 1570+11310)* srWidth *.303, ( 1438+5624)* srHeight *.3023, ( 1520+11310)* srWidth *.303, ( 1438+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1520+11310)* srWidth *.303, ( 1438+5624)* srHeight *.3023, ( 1570+11310)* srWidth *.303, ( 1438+5624)* srHeight *.3023, ( 1570+11310)* srWidth *.303, ( 1500+5624)* srHeight *.3023, ( 1520+11310)* srWidth *.303, ( 1500+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1570+11310)* srWidth *.303, ( 1190+5624)* srHeight *.3023, ( 1620+11310)* srWidth *.303, ( 1190+5624)* srHeight *.3023, ( 1620+11310)* srWidth *.303, ( 1252+5624)* srHeight *.3023, ( 1570+11310)* srWidth *.303, ( 1252+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1570+11310)* srWidth *.303, ( 1252+5624)* srHeight *.3023, ( 1620+11310)* srWidth *.303, ( 1252+5624)* srHeight *.3023, ( 1620+11310)* srWidth *.303, ( 1314+5624)* srHeight *.3023, ( 1570+11310)* srWidth *.303, ( 1314+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1570+11310)* srWidth *.303, ( 1314+5624)* srHeight *.3023, ( 1620+11310)* srWidth *.303, ( 1314+5624)* srHeight *.3023, ( 1620+11310)* srWidth *.303, ( 1376+5624)* srHeight *.3023, ( 1570+11310)* srWidth *.303, ( 1376+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1570+11310)* srWidth *.303, ( 1376+5624)* srHeight *.3023, ( 1620+11310)* srWidth *.303, ( 1376+5624)* srHeight *.3023, ( 1620+11310)* srWidth *.303, ( 1438+5624)* srHeight *.3023, ( 1570+11310)* srWidth *.303, ( 1438+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1570+11310)* srWidth *.303, ( 1438+5624)* srHeight *.3023, ( 1620+11310)* srWidth *.303, ( 1438+5624)* srHeight *.3023, ( 1620+11310)* srWidth *.303, ( 1500+5624)* srHeight *.3023, ( 1570+11310)* srWidth *.303, ( 1500+5624)* srHeight *.3023));

        //bottom right vertical column of desks - left
        flexSpaces.add(new Polygon((1900+11310)* srWidth *.303, ( 1890+5624)* srHeight *.3023, ( 1950+11310)* srWidth *.303, ( 1890+5624)* srHeight *.3023, ( 1950+11310)* srWidth *.303, ( 1955+5624)* srHeight *.3023, ( 1900+11310)* srWidth *.303, ( 1955+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1900+11310)* srWidth *.303, ( 1955+5624)* srHeight *.3023, ( 1950+11310)* srWidth *.303, ( 1955+5624)* srHeight *.3023, ( 1950+11310)* srWidth *.303, ( 2020+5624)* srHeight *.3023, ( 1900+11310)* srWidth *.303, ( 2020+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1900+11310)* srWidth *.303, ( 2020+5624)* srHeight *.3023, ( 1950+11310)* srWidth *.303, ( 2020+5624)* srHeight *.3023, ( 1950+11310)* srWidth *.303, ( 2085+5624)* srHeight *.3023, ( 1900+11310)* srWidth *.303, ( 2085+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1900+11310)* srWidth *.303, ( 2085+5624)* srHeight *.3023, ( 1950+11310)* srWidth *.303, ( 2085+5624)* srHeight *.3023, ( 1950+11310)* srWidth *.303, ( 2150+5624)* srHeight *.3023, ( 1900+11310)* srWidth *.303, ( 2150+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1950+11310)* srWidth *.303, ( 1890+5624)* srHeight *.3023, ( 2000+11310)* srWidth *.303, ( 1890+5624)* srHeight *.3023, ( 2000+11310)* srWidth *.303, ( 1955+5624)* srHeight *.3023, ( 1950+11310)* srWidth *.303, ( 1955+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1950+11310)* srWidth *.303, ( 1955+5624)* srHeight *.3023, ( 2000+11310)* srWidth *.303, ( 1955+5624)* srHeight *.3023, ( 2000+11310)* srWidth *.303, ( 2020+5624)* srHeight *.3023, ( 1950+11310)* srWidth *.303, ( 2020+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1950+11310)* srWidth *.303, ( 2020+5624)* srHeight *.3023, ( 2000+11310)* srWidth *.303, ( 2020+5624)* srHeight *.3023, ( 2000+11310)* srWidth *.303, ( 2085+5624)* srHeight *.3023, ( 1950+11310)* srWidth *.303, ( 2085+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1950+11310)* srWidth *.303, ( 2085+5624)* srHeight *.3023, ( 2000+11310)* srWidth *.303, ( 2085+5624)* srHeight *.3023, ( 2000+11310)* srWidth *.303, ( 2150+5624)* srHeight *.3023, ( 1950+11310)* srWidth *.303, ( 2150+5624)* srHeight *.3023));

        //bottom right vertical column of desks - right
        flexSpaces.add(new Polygon((2040+11310)* srWidth *.303, ( 1890+5624)* srHeight *.3023, ( 2090+11310)* srWidth *.303, ( 1890+5624)* srHeight *.3023, ( 2090+11310)* srWidth *.303, ( 1955+5624)* srHeight *.3023, ( 2040+11310)* srWidth *.303, ( 1955+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((2040+11310)* srWidth *.303, ( 1955+5624)* srHeight *.3023, ( 2090+11310)* srWidth *.303, ( 1955+5624)* srHeight *.3023, ( 2090+11310)* srWidth *.303, ( 2020+5624)* srHeight *.3023, ( 2040+11310)* srWidth *.303, ( 2020+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((2040+11310)* srWidth *.303, ( 2020+5624)* srHeight *.3023, ( 2090+11310)* srWidth *.303, ( 2020+5624)* srHeight *.3023, ( 2090+11310)* srWidth *.303, ( 2085+5624)* srHeight *.3023, ( 2040+11310)* srWidth *.303, ( 2085+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((2040+11310)* srWidth *.303, ( 2085+5624)* srHeight *.3023, ( 2090+11310)* srWidth *.303, ( 2085+5624)* srHeight *.3023, ( 2090+11310)* srWidth *.303, ( 2150+5624)* srHeight *.3023, ( 2040+11310)* srWidth *.303, ( 2150+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((2090+11310)* srWidth *.303, ( 1890+5624)* srHeight *.3023, ( 2140+11310)* srWidth *.303, ( 1890+5624)* srHeight *.3023, ( 2140+11310)* srWidth *.303, ( 1955+5624)* srHeight *.3023, ( 2090+11310)* srWidth *.303, ( 1955+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((2090+11310)* srWidth *.303, ( 1955+5624)* srHeight *.3023, ( 2140+11310)* srWidth *.303, ( 1955+5624)* srHeight *.3023, ( 2140+11310)* srWidth *.303, ( 2020+5624)* srHeight *.3023, ( 2090+11310)* srWidth *.303, ( 2020+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((2090+11310)* srWidth *.303, ( 2020+5624)* srHeight *.3023, ( 2140+11310)* srWidth *.303, ( 2020+5624)* srHeight *.3023, ( 2140+11310)* srWidth *.303, ( 2085+5624)* srHeight *.3023, ( 2090+11310)* srWidth *.303, ( 2085+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((2090+11310)* srWidth *.303, ( 2085+5624)* srHeight *.3023, ( 2140+11310)* srWidth *.303, ( 2085+5624)* srHeight *.3023, ( 2140+11310)* srWidth *.303, ( 2150+5624)* srHeight *.3023, ( 2090+11310)* srWidth *.303, ( 2150+5624)* srHeight *.3023));

        //bottom right vertical rooms
        flexSpaces.add(new Polygon((2220+11310)* srWidth *.303, ( 2220+5624)* srHeight *.3023, ( 2290+11310)* srWidth *.303, ( 2220+5624)* srHeight *.3023, ( 2290+11310)* srWidth *.303, ( 2340+5624)* srHeight *.3023, ( 2220+11310)* srWidth *.303, ( 2340+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((2220+11310)* srWidth *.303, ( 2340+5624)* srHeight *.3023, ( 2290+11310)* srWidth *.303, ( 2340+5624)* srHeight *.3023, ( 2290+11310)* srWidth *.303, ( 2430+5624)* srHeight *.3023, ( 2220+11310)* srWidth *.303, ( 2430+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((2220+11310)* srWidth *.303, ( 2430+5624)* srHeight *.3023, ( 2290+11310)* srWidth *.303, ( 2430+5624)* srHeight *.3023, ( 2290+11310)* srWidth *.303, ( 2515+5624)* srHeight *.3023, ( 2220+11310)* srWidth *.303, ( 2515+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((2220+11310)* srWidth *.303, ( 2515+5624)* srHeight *.3023, ( 2290+11310)* srWidth *.303, ( 2515+5624)* srHeight *.3023, ( 2290+11310)* srWidth *.303, ( 2620+5624)* srHeight *.3023, ( 2220+11310)* srWidth *.303, ( 2620+5624)* srHeight *.3023));

        //little rooms above center room
        flexSpaces.add(new Polygon((860+11310)* srWidth *.303, ( 1190+5624)* srHeight *.3023, ( 960+11310)* srWidth *.303, ( 1190+5624)* srHeight *.3023, ( 960+11310)* srWidth *.303, ( 1270+5624)* srHeight *.3023, ( 860+11310)* srWidth *.303, ( 1270+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((960+11310)* srWidth *.303, ( 1190+5624)* srHeight *.3023, ( 1060+11310)* srWidth *.303, ( 1190+5624)* srHeight *.3023, ( 1060+11310)* srWidth *.303, ( 1270+5624)* srHeight *.3023, ( 960+11310)* srWidth *.303, ( 1270+5624)* srHeight *.3023));
        flexSpaces.add(new Polygon((1060+11310)* srWidth *.303, ( 1190+5624)* srHeight *.3023, ( 1160+11310)* srWidth *.303, ( 1190+5624)* srHeight *.3023, ( 1160+11310)* srWidth *.303, ( 1270+5624)* srHeight *.3023, ( 1060+11310)* srWidth *.303, ( 1270+5624)* srHeight *.3023));

        for(int i = 0; i < flexSpaces.size(); i++){
            if(flexSpaceAvailable.get(i)) {
                flexSpaces.get(i).setStroke(Color.web("TURQUOISE"));
                flexSpaces.get(i).setFill(Color.web("TURQUOISE"));
                flexSpaces.get(i).setOpacity(0.5);
            }else{
                flexSpaces.get(i).setStroke(Color.web("RED"));
                flexSpaces.get(i).setFill(Color.web("RED"));
                flexSpaces.get(i).setOpacity(0.3);
            }
            pathPane.getChildren().add(flexSpaces.get(i));
        }
    }

    private void displayBookableRooms(){
        double srWidth = gesturePane.getWidth() / Map.getImage().getWidth();
        double srHeight = gesturePane.getHeight() / Map.getImage().getHeight();

        double room1[] = {(2230+11310)*.303, (1630+5624)*.3023, (2650+11310)*.303, (1630+5624)*.3023, (2650+11310)*.303, (1880+5624)*.3023, (2230+11310)*.303, (1880+5624)*.3023};
        double room2[] = {(2860+11310)*.303, (1130+5624)*.3023, (3040+11310)*.303, (1070+5624)*.3023, (3180+11310)*.303, (1430+5624)*.3023, (2990+11310)*.303, (1500+5624)*.3023};
        double room3[] = {(2720+11310)*.303, (750+5624)*.3023, (2850+11310)*.303, (1120+5624)*.3023, (3040+11310)*.303, (1060+5624)*.3023, (2970+11310)*.303, (850+5624)*.3023, (3000+11310)*.303, (840+5624)*.3023, (2970+11310)*.303, (770+5624)*.3023, (2900+11310)*.303, (720+5624)*.3023, (2810+11310)*.303, (690+5624)*.3023};
        double room4[] = {(2420+11310)*.303, (180+5624)*.3023, (2610+11310)*.303, (180+5624)*.3023, (2730+11310)*.303, (540+5624)*.3023, (2420+11310)*.303, (540+5624)*.3023};
        double room5[] = {(2240+11310)*.303, (755+5624)*.3023, (2540+11310)*.303, (755+5624)*.3023, (2540+11310)*.303, (1080+5624)*.3023, (2240+11310)*.303, (1080+5624)*.3023};
        double room6[] = {(2090+11310)*.303, (180+5624)*.3023, (2410+11310)*.303, (180+5624)*.3023, (2410+11310)*.303, (540+5624)*.3023, (2090+11310)*.303, (540+5624)*.3023};
        double room7[] = {(2000+11310)*.303, (755+5624)*.3023, (2230+11310)*.303, (755+5624)*.3023, (2230+11310)*.303, (1080+5624)*.3023, (2000+11310)*.303, (1080+5624)*.3023};
        double room8[] = {(1750+11310)*.303, (180+5624)*.3023, (2080+11310)*.303, (180+5624)*.3023, (2080+11310)*.303, (540+5624)*.3023, (1750+11310)*.303, (540+5624)*.3023};
        double room9[] = {(1775+11310)*.303, (710+5624)*.3023, (1840+11310)*.303, (710+5624)*.3023, (1840+11310)*.303, (750+5624)*.3023, (1980+11310)*.303, (750+5624)*.3023, (1980+11310)*.303, (930+5624)*.3023, (1775+11310)*.303, (930+5624)*.3023};
        double auditorium[] = {(2860+11310)*.303, (1920+5624)*.3023, (2860+11310)*.303, (2030+5624)*.3023, (3130+11310)*.303, (2030+5624)*.3023, (3150+11310)*.303, (2060+5624)*.3023, (3240+11310)*.303, (2000+5624)*.3023, (3320+11310)*.303, (1870+5624)*.3023, (3330+11310)*.303, (1770+5624)*.3023, (3310+11310)*.303, (1730+5624)*.3023, (3270+11310)*.303, (1720+5624)*.3023, (3190+11310)*.303, (1460+5624)*.3023, (3040+11310)*.303, (1500+5624)*.3023, (3070+11310)*.303, (1590+5624)*.3023, (3030+11310)*.303, (1600+5624)*.3023};

        DisplayRooms.add(new RoomDisplay("Room 1 - Computer", room1, "Room 1 - Computer"));
        DisplayRooms.add(new RoomDisplay("Room 2 - Computer", room2, "Room 2 - Computer"));
        DisplayRooms.add(new RoomDisplay("Room 3 - Computer", room3, "Room 3 - Computer"));
        DisplayRooms.add(new RoomDisplay("Room 4 - Classroom", room4, "Room 4 - Classroom"));
        DisplayRooms.add(new RoomDisplay("Room 5 - Computer", room5, "Room 5 - Computer"));
        DisplayRooms.add(new RoomDisplay("Room 6 - Classroom", room6, "Room 6 - Classroom"));
        DisplayRooms.add(new RoomDisplay("Room 7 - Computer", room7, "Room 7 - Computer"));
        DisplayRooms.add(new RoomDisplay("Room 8 - Classroom", room8, "Room 8 - Classroom"));
        DisplayRooms.add(new RoomDisplay("Room 9 - Computer", room9, "Room 9 - Computer"));
        DisplayRooms.add(new RoomDisplay("Mission Hall Auditorium", auditorium, "Mission Hall Auditorium"));

        RoomAccess ra = new RoomAccess();
        LocalDateTime ldt = LocalDateTime.now();
        LocalDateTime ldt2 = ldt.plusMinutes(1);

        System.out.println(ldt2.toString());
        System.out.println(ra.getAvailRooms(ldt.toString(), ldt2.toString()));

        for (int i = 0; i < DisplayRooms.size(); i++) {
            DisplayRooms.get(i).makePolygon(srWidth, srHeight);
            DisplayRooms.get(i).getPolygon().setOnMouseClicked(onMouseClickedEventHandler);
            pathPane.getChildren().add(DisplayRooms.get(i).getPolygon());
            DisplayRooms.get(i).changePolygonColor("RED");
            for (int j = 0; j < ra.getAvailRooms(ldt.toString(), ldt2.toString()).size(); j++) {
                String roomName = ra.getAvailRooms(ldt.toString(), ldt2.toString()).get(j);
                if (DisplayRooms.get(i).getRoomName().equals(roomName)) {
                    DisplayRooms.get(i).changePolygonColor("GREEN");
                }
            }
        }
    }

    private EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            for (int k = 0; k < DisplayRooms.size(); k++) {
                Point2D mousePress = new Point2D(event.getX(), event.getY());
                if (DisplayRooms.get(k).getPolygon().contains(mousePress)) {
                    try {
                        System.out.println(DisplayRooms.get(k).getRoomName());
                        timeout.stop();
                        Singleton single = Singleton.getInstance();
                        single.setLastTime();

                        saveState();

                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("BookRoom.fxml"));
                        Parent newPage = loader.load();

                        BookRoomController brc = loader.getController();
                        brc.loadWithRoomSelected(DisplayRooms.get(k).getRoomName());

                        ((Node) event.getSource()).getScene().setRoot(newPage);
                        return;
                    } catch (IOException ex) {

                    }
                }
            }
            event.consume();
        }
    };

    private void updateFlexSpaces(ArrayList<Boolean> booleans){
        if(flexSpaces.size() > 0) {
            for (int i = 0; i < booleans.size(); i++) {
                if (booleans.get(i)) {
                    flexSpaces.get(i).setStroke(Color.web("TURQUOISE"));
                    flexSpaces.get(i).setFill(Color.web("TURQUOISE"));
                    flexSpaces.get(i).setOpacity(0.5);
                } else {
                    flexSpaces.get(i).setStroke(Color.web("RED"));
                    flexSpaces.get(i).setFill(Color.web("RED"));
                    flexSpaces.get(i).setOpacity(0.3);
                }
            }
        }
    }

    private void hideFlexSpaces(){
        for(int i = 0; i < flexSpaces.size(); i++){
            pathPane.getChildren().remove(flexSpaces.get(i));
        }
        flexSpaces.clear();

        for (int i = 0; i < DisplayRooms.size(); i++) {
            pathPane.getChildren().remove(DisplayRooms.get(i).getPolygon());
        }
        DisplayRooms.clear();
    }

    /**@author Nathan
     * Saves the memento state
     */
    private void saveState(){
        Singleton single = Singleton.getInstance();
        single.saveMemento("HospitalPathFinding.fxml", restrictChoice.getValue(), Filter.getValue(), Floor.getValue(), PathFindStartDrop.getValue(), PathFindEndDrop.getValue());
    }
}
