package edu.wpi.cs3733.d19.teamL.Map.Pathfinding;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXComboBox;
import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Path;
import edu.wpi.cs3733.d19.teamL.SearchingAlgorithms.*;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;

import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Math.asin;
import static javafx.scene.paint.Color.*;

import static java.lang.Math.sqrt;

@SuppressWarnings("Duplicates")
public class PathFindingController {

    @FXML
    private ColumnConstraints mapColumn;
    @FXML
    private TextArea direction;

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
    private JFXButton menubtn;

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

    Location kioskTemp;

    private boolean displayingPath;
    private ArrayList<Button> buttons = new ArrayList<Button>();
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


    private ArrayList<String> mapURLs = new ArrayList<String>();
    private ArrayList<Circle> circles = new ArrayList<Circle>();
    private ArrayList<Line> lines = new ArrayList<Line>();

    private ListIterator<String> listIterator = null;
    private boolean showingSettings;

    Timeline timeout;

    private String pickedFloor = "test";
    private String type = "test";
    private String type2 = "";
    private String currentMap = "G"; //defaults to floor G
    //Larry - This will show up the text direction in pop up screen when you click on the text direction button
    @FXML
    private void PopupText(ActionEvent event) throws IOException{
        Stage stage;
        Parent root;
        stage = new Stage();
        //root = FXMLLoader.load(getClass().getClassLoader().getResource("PopUpTextDirection.fxml"));
        //stage.setScene(new Scene(root));
        //stage.setTitle("I am Text Direction");
        //stage.initModality(Modality.APPLICATION_MODAL);
        //stage.show();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("PopUpTextDirection.fxml"));
        Parent sceneMain = loader.load();
        TextDirectionController controller = loader.<TextDirectionController>getController();
        controller.setTextOfDirection(printPath(path.getPath()));
        Scene scene = new Scene(sceneMain);
        stage.setScene(scene);
       // stage.setTitle("I am Text Direction");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private Button menu;
    @FXML
    private AnchorPane navList;

    public void initialize(URL url, ResourceBundle rb) {
        this.prepareSlideMenuAnimation();
        direction.setEditable(false);
    }

    @FXML
    private void clickedG(){
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thegroundfloor.png"));
        currentMap = "G";

        changeMapLabel();
        displayKiosk();
        if(path != null) {
            displayPath();
        }
    }
    @FXML
    private void clickedL1() {
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel1.png"));
        currentMap = "L1";

        changeMapLabel();
        displayKiosk();
        if(path != null){
            displayPath();
        }
    }

    @FXML public void clickedL2(){
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel2.png"));
        currentMap = "L2";

        changeMapLabel();
        displayKiosk();
        if(path != null){
            displayPath();
        }
    }
    @FXML
    private void clicked1(){
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/01_thefirstfloor.png"));
        currentMap = "1";

        changeMapLabel();
        displayKiosk();
        if(path != null){
            displayPath();
        }
    }
    @FXML
    private void clicked2(){
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/02_thesecondfloor.png"));
        currentMap = "2";

        changeMapLabel();
        displayKiosk();
        if(path != null){
            displayPath();
        }
    }
    @FXML
    private void clicked3(){
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/03_thethirdfloor.png"));
        currentMap = "3";

        changeMapLabel();
        displayKiosk();
        if(path != null){
            displayPath();
        }
    }



    @FXML
    private void changeMapLabel() {
        if (currentMap.equals("L2")){
            thisMap.setText("Lower Level 2");
        }

        if (currentMap.equals("L1")){
            thisMap.setText("Lower Level 1");
        }

        if (currentMap.equals("G")){
            thisMap.setText("Ground Floor");
        }

        if (currentMap.equals("1")){
            thisMap.setText("Floor 1");
        }

        if (currentMap.equals("2")){
            thisMap.setText("Floor 2");
        }

        if (currentMap.equals("3")){
            thisMap.setText("Floor 3");
        }

        if (currentMap.equals("4")){
            thisMap.setText("Flexible Workspace");
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

    Label startLabel;
    Label endLabel;
    Label hereLabel;
    Location startNode;
    Location endNode;

    @FXML
    private void strategySelected() {
        strategyAlgorithm = strategySelector.getValue();
        single.setTypePathfind(strategyAlgorithm);
    }

    public void initialize() {
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
                if((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()){
                    try{
                        single.setDoPopup(true);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));
                        Parent sceneMain = loader.load();
                        if(single.isLoggedIn()){
                            HomeScreenController controller = loader.<HomeScreenController>getController();
                            controller.displayPopup();
                        }
                        Stage thisStage = (Stage) homebtn.getScene().getWindow();

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

        gridPane.add(gesturePane,0,0, 1, GridPane.REMAINING);
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

        gesturePane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Location clostestToClick = single.getData().get(0);
                for (Location l: single.getData()) {
                    if(currentMap.equals(l.getFloor()) && Math.pow(event.getX()*Map.getImage().getWidth()/gesturePane.getWidth() - l.getXcoord(), 2) + Math.pow(event.getY()*Map.getImage().getHeight()/gesturePane.getHeight()  - l.getYcoord(), 2) <
                            Math.pow(event.getX()*Map.getImage().getWidth()/gesturePane.getWidth()  - clostestToClick.getXcoord(), 2) + Math.pow(event.getY()*Map.getImage().getHeight()/gesturePane.getHeight()  - clostestToClick.getYcoord(), 2)){
                        clostestToClick = l;
                    }
                }
                System.out.println(clostestToClick);
                if(Math.pow(event.getX() - clostestToClick.getXcoord(), 2) + Math.pow(event.getY() - clostestToClick.getYcoord(), 2) <= 100){
                    Floor.setValue("All");
                    Filter.setValue("All");
                    noHall();

                    PathFindEndDrop.setValue(clostestToClick);

                    submitPressed();
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
        startLabel = new Label();
        endLabel = new Label();
        hereLabel = new Label();
        //Adds the text to the screen
        pathPane.getChildren().add(startLabel);
        pathPane.getChildren().add(endLabel);
        pathPane.getChildren().add(hereLabel);
        menubtn.setVisible(false);
        if(!single.isLoggedIn()){
            logOut.setVisible(false);
        }
        if(single.isIsAdmin()){
            menubtn.setDisable(false);
            menubtn.setVisible(true);
        }

        nameToLoc.clear();
        for (Location l: PathFindStartDrop.getItems()) {
            nameToLoc.put(l.toString(), l);
        }
        TextFields.bindAutoCompletion(searchField,nameToLoc.keySet());
        //Code to immediately set kiosk
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                displayKiosk();
                startNode = kioskTemp;
            }
        });
    }

    @FXML
    private void logOut() throws IOException{
        single.setLoggedIn(false);
        single.setIsAdmin(false);
        single.setUsername("");
        single.setDoPopup(true);
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

        Parent sceneMain = loader.load();

        Stage theStage = (Stage) homebtn.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void backPressed() throws IOException {
        timeout.stop();
        single = Singleton.getInstance();
        single.setLastTime();
        thestage = (Stage) homebtn.getScene().getWindow();
        AnchorPane root;

        if(single.isLoggedIn()) {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EmployeeLoggedInHome.fxml"));
            if(single.isIsAdmin()){
                loader = new FXMLLoader(getClass().getClassLoader().getResource("AdminLoggedInHome.fxml"));
            }
            Parent sceneMain = loader.load();

            Stage theStage = (Stage) homebtn.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);
            return;
        } else {
            single.setDoPopup(true);
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

            Parent sceneMain = loader.load();

            Stage theStage = (Stage) homebtn.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);
        }
    }

    /**
     * Modified by Nikhil: Now defaults the start location to kiosk node.
     */
    @FXML
    private void locationsSelected(){
        single.setLastTime();
        if((PathFindStartDrop.getValue() != null || startNode != null) && PathFindEndDrop.getValue() != null){
            PathFindSubmit.setDisable(false);
        }
        else{
            PathFindSubmit.setDisable(true);
            direction.setDisable(true);
            direction.setEditable(false);
        }
    }

    /**Nathan modified this to include a path preference choice (restriction)
     * Nikhil modified this so it automatically switches the map to the starting floor.
     */
    @FXML
    private void submitPressed(){
        single.setLastTime();
        //This handles if the user wants to set their own start location.
        if(PathFindStartDrop.getValue() != null) {
            startNode = single.lookup.get(PathFindStartDrop.getValue().getLocID());
        }
        endNode = single.lookup.get(PathFindEndDrop.getValue().getLocID());
        String restriction = restrictChoice.getValue();
        if(restriction == null || restriction.trim().equals("") || restriction.equals("Both")){
            restriction = "    ";
        } else if(restriction.equals("Stairs Only")){
            restriction = "ELEV";
        } else if (restriction.equals("Elevators Only")){
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

        displayPath();
        //printPath(path.getPath());
        direction.setText(printPath(path.getPath()));

        direction.setWrapText(true);

        direction.setDisable(false);
        direction.setEditable(false);
    }

    /**
     * @author: Nikhil: Displays the kiosk location automatically on the path navigation screen.
     */
    public void displayKiosk() {
        checkAndSetKiosk();
        Circle kiosk = new Circle();
        kiosk.setCenterX(kioskTemp.getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
        kiosk.setCenterY(kioskTemp.getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
        kiosk.setRadius(Math.max(1.5, 1.5f * (gesturePane.getCurrentScale() / 4)));
        kiosk.setStroke(Color.BLUE);
        kiosk.setFill(Color.BLUE);
        hereLabel.setLayoutX(kioskTemp.getXcoord()*childPane.getWidth()/Map.getImage().getWidth() -20);
        hereLabel.setLayoutY(kioskTemp.getYcoord()*childPane.getHeight()/Map.getImage().getHeight() - 20);
        hereLabel.setText(" You are here ");
        hereLabel.setStyle("-fx-text-fill: WHITE;-fx-font-size: 6; -fx-background-color: BLUE; -fx-border-color: WHITE; -fx-border-width: 2; -fx-min-width: 40;");
        if(currentMap.equals(kioskTemp.getFloor())) {
            kiosk.setVisible(true);
            gesturePane.zoomTo(2, new Point2D(kioskTemp.getXcoord() - 1350, kioskTemp.getYcoord() - 2000));
            hereLabel.setVisible(true);
        }
        else {
            hereLabel.setVisible(false);
            kiosk.setVisible(false);
        }
        circles.add(kiosk);
        pathPane.getChildren().add(kiosk);
    }

    @FXML
    private void settingPressed(){
        TranslateTransition openSetting = new TranslateTransition(new Duration(300.0D), this.settingPane);
        openSetting.setToX(Map.getFitWidth()-850);
        TranslateTransition closeSetting = new TranslateTransition(new Duration(300.0D), this.settingPane);
        this.menubtn.setOnAction((evt) -> {
          //  settingPane.setLayoutX(mapColumn.getMaxWidth()-200);
            if (this.settingPane.getTranslateY() != 450.0D) {
                openSetting.setToY(450.0D);
                openSetting.play();
            } else {
                closeSetting.setToY(-this.settingPane.getHeight());
                closeSetting.play();
            }

        });

        this.menuBack.setOnAction((evt) -> {
            if (this.navList.getTranslateY() != 450.0D) {
                openSetting.setToY(450);
                openSetting.play();
            } else {
                closeSetting.setToY(-this.settingPane.getHeight());
                closeSetting.play();
            }

        });
        if (this.navList.getTranslateY() != 450.0D) {
            openSetting.setToY(450);
            openSetting.play();
        } else {
            closeSetting.setToX(-this.navList.getHeight());
            closeSetting.play();
        }


    }


        /*
        showingSettings = !showingSettings;

        if(showingSettings){
            settingPane.setDisable(false);
            settingPane.setLayoutY(113);
        }
        else{
            settingPane.setDisable(true);
            settingPane.setLayoutY(-320);
        }
    }*/

    @FXML
    private void updateKiosk(){
        if(!kioskName.getText().equals("")) {
            kioskTemp.setLongName(kioskName.getText());
        }
        if(kioskConnectedTo.getValue() != null) {
            kioskTemp = kioskConnectedTo.getValue();
            single.setKiosk(kioskConnectedTo.getValue());
        }
        clearStart();
        //        displayKiosk();
    }

    @FXML
    private void prepareSlideMenuAnimation() {
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
     * @author: Nikhil: Displays start node, end node and line in between
     * Also contains code that will generate buttons above transitions between floors
     * Now automatically zooms on the floor's path
     */
    public void displayPath(){
        single.setLastTime();
        //Clears the lines and circles to avoid any duplicates or reproducing data
        if(displayingPath) {
            path.getPath().add(0,startNode);

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

            //Counts how many nodes are on the floor
            int floorCount = 0;
            //Start node on floor
            int floorSwitch1 = 0;
            //End node on floor
            int floorSwitch2 = path.getPath().size()-1;

            //Creates the path for the user to follow, and displays based on the current floor.
            for (int i = 0; i < path.getPath().size() - 1; i++) {
                Line line = new Line();
                floorCount++;
                line.setStartX(path.getPath().get(i).getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
                line.setStartY(path.getPath().get(i).getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
                line.setEndX(path.getPath().get(i+1).getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
                line.setEndY(path.getPath().get(i+1).getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
                line.setStrokeWidth(2.5);
                line.setStroke(DODGERBLUE);
                //Toggles visibility based on current floor to adjust for travelling across multiple floors.
                if (!(path.getPath().get(i).getFloor().equals(currentMap)) || !(path.getPath().get(i + 1).getFloor().equals(currentMap))) {
                    line.setVisible(false);
                }
                //Creates buttons to transition between floors on the map
                if((!(path.getPath().get(i + 1).getFloor().equals(currentMap)))) {
                    //Sets the start and end nodes on the floor
                    if(path.getPath().get(i).getFloor().equals(currentMap)) {
                        floorSwitch2 = i;
                        floorSwitch1 = i-(floorCount-1);
                    }
                    floorCount = 0;
                    Button nBut = new Button();
                    nBut.setLayoutX((path.getPath().get(i).getXcoord()*childPane.getWidth()/Map.getImage().getWidth()));
                    nBut.setLayoutY((path.getPath().get(i).getYcoord()*childPane.getHeight()/Map.getImage().getHeight()));
                    final int transit = i + 1;
                    String transition = path.getPath().get(transit).getFloor();
                    String display = "Take Elevator to ";
                    if((path.getPath().get(i).getNodeType().equals("STAI")))
                    {
                        display = "Take Stairs to ";
                    }
                    //Sets button text
                    if (transition.equals("L2"))
                        display += "Floor Lower 2";
                    if (transition.equals("L1"))
                        display += "Floor Lower 1";
                    if (transition.equals("G"))
                        display += "Ground Floor";
                    if (transition.equals("1"))
                        display += "First Floor";
                    if (transition.equals("2"))
                        display += "Second Floor";
                    if (transition.equals("3"))
                        display += "Third Floor";
                    nBut.setText(display);
                    //Sets the action to be performed when the button is pressed
                    nBut.setOnAction(event -> {
                        if (transition.equals("L2"))
                            clickedL2();
                        if (transition.equals("L1"))
                            clickedL1();
                        if (transition.equals("G"))
                            clickedG();
                        if (transition.equals("1"))
                            clicked1();
                        if (transition.equals("2"))
                            clicked2();
                        if (transition.equals("3"))
                            clicked3();
                    });
                    if(nBut.getLayoutX() + 400 > pathPane.getWidth())
                    {
                        nBut.setLayoutX(nBut.getLayoutX() - 400);
                    }
                    if(nBut.getLayoutY() + 50 > pathPane.getHeight())
                    {
                        nBut.setMaxHeight(pathPane.getHeight() - nBut.getLayoutY());
                    }
                    //Sets button visibility
                    if (buttons.isEmpty() && (path.getPath().get(i).getFloor().equals(currentMap) || currentMap.equals(startNode.getFloor()) || currentMap.equals(endNode.getFloor()))) {
                        buttons.add(nBut);
                        nBut.setVisible(true);
                        //Change the display of the button based on which floor you're on
                        if(currentMap.equals(startNode.getFloor())) {
                            nBut.setStyle("-fx-text-fill: WHITE; -fx-font-size: 6; -fx-background-color: GREEN; -fx-border-color: WHITE; -fx-background-radius: 18; -fx-border-radius: 18; -fx-border-width: 3");
                            //Should handle weird case for displaying button way out in nowhere.
                            if(!path.getPath().get(i+1).getFloor().equals(currentMap)) {
                                nBut.setLayoutX((path.getPath().get(floorSwitch2).getXcoord()*childPane.getWidth()/Map.getImage().getWidth()));
                                nBut.setLayoutY((path.getPath().get(floorSwitch2).getYcoord()*childPane.getHeight()/Map.getImage().getHeight()));
                            }
                        }
                        else if(!currentMap.equals(startNode.getFloor()) && !currentMap.equals(endNode.getFloor())){
                            nBut.setStyle("-fx-text-fill: WHITE; -fx-font-size: 6; -fx-background-color: GREEN; -fx-border-color: WHITE; -fx-background-radius: 18; -fx-border-radius: 18; -fx-border-width: 3");
                            nBut.setLayoutX((path.getPath().get(floorSwitch2).getXcoord()*childPane.getWidth()/Map.getImage().getWidth()));
                            nBut.setLayoutY((path.getPath().get(floorSwitch2).getYcoord()*childPane.getHeight()/Map.getImage().getHeight()));
                        }
                        else {
                            //Modified the return to start button position.
                            nBut.setStyle("-fx-text-fill: WHITE;-fx-font-size: 6; -fx-background-color: RED; -fx-border-color: WHITE; -fx-background-radius: 18; -fx-border-radius: 18; -fx-border-width: 3");
                            nBut.setText("Go to Starting Floor");
                            nBut.setLayoutX((endNode.getXcoord()*childPane.getWidth()/Map.getImage().getWidth()));
                            nBut.setLayoutY((endNode.getYcoord()*childPane.getHeight()/Map.getImage().getHeight()));
                        }
                        pathPane.getChildren().add(nBut);
                    }
                }
                else if(currentMap.equals(endNode.getFloor())) {
                    floorSwitch1 = i - (floorCount-1);
                }
                pathPane.getChildren().add(line);
                lines.add(line);
            }
            //Creates the start and end nodes to display them and sets colors.
            Circle StartCircle = new Circle();
            Circle kioskDis = new Circle();

            //Setting the properties of the circle
            StartCircle.setCenterX(startNode.getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
            StartCircle.setCenterY(startNode.getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
            StartCircle.setRadius(Math.max(1.5, 1.5f * (gesturePane.getCurrentScale() / 4)));

            startLabel.setLayoutX(startNode.getXcoord()*childPane.getWidth()/Map.getImage().getWidth() -20);
            startLabel.setLayoutY(startNode.getYcoord()*childPane.getHeight()/Map.getImage().getHeight() - 20);
            //Displays the kiosk location for the startNode, changes the color to indicate the Kiosk, also sets startLabel
            if(startNode.equals(kioskTemp)) {
                StartCircle.setStroke(Color.BLUE);
                StartCircle.setFill(Color.BLUE);
                startLabel.setText(" You are here ");
                //System.out.println("1");
                startLabel.setStyle("-fx-text-fill: WHITE;-fx-font-size: 6; -fx-background-color: BLUE; -fx-border-color: WHITE; -fx-border-width: 2; -fx-min-width: 40;");
            }
            else {
                kioskDis.setCenterX(kioskTemp.getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
                kioskDis.setCenterY(kioskTemp.getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
                kioskDis.setRadius(Math.max(1.5, 1.5f * (gesturePane.getCurrentScale() / 4)));
                kioskDis.setStroke(Color.BLUE);
                kioskDis.setFill(Color.BLUE);
                StartCircle.setStroke(Color.GREEN);
                StartCircle.setFill(Color.GREEN);
                //System.out.println("2");
                startLabel.setText(startNode.getLongName());
                startLabel.setStyle("-fx-text-fill: WHITE;-fx-font-size: 6; -fx-background-color: GREEN; -fx-border-color: WHITE; -fx-border-width: 2; -fx-min-width: 40;");
            }
            if (!startNode.getFloor().equals(currentMap)) {
                StartCircle.setVisible(false);
                startLabel.setVisible(false);
                //System.out.println("3");
            }
            else if(currentMap.equals(kioskTemp.getFloor())) {
                kioskDis.setVisible(true);
                //System.out.println("4");
            }
            else {
                startLabel.setVisible(true);
                kioskDis.setVisible(false);
                hereLabel.setVisible(true);
            }

            pathPane.getChildren().add(StartCircle);


            Circle EndCircle = new Circle();

            //Setting the properties of the circle
            EndCircle.setCenterX(endNode.getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
            EndCircle.setCenterY(endNode.getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
            EndCircle.setRadius(Math.max(1.5, 1.5f * (gesturePane.getCurrentScale() / 5)));
            EndCircle.setStroke(Color.RED);
            EndCircle.setFill(Color.RED);
            //Setting text for the end node
            endLabel.setText(endNode.getLongName());
            endLabel.setStyle("-fx-text-fill: WHITE;-fx-font-size: 6; -fx-background-color: RED; -fx-border-color: WHITE; -fx-border-width: 2; -fx-min-width: 40;");
            endLabel.setLayoutX(endNode.getXcoord()*childPane.getWidth()/Map.getImage().getWidth() - 30);
            endLabel.setLayoutY(endNode.getYcoord()*childPane.getHeight()/Map.getImage().getHeight() - 20);
            if (!endNode.getFloor().equals(currentMap)) {
                EndCircle.setVisible(false);
                endLabel.setVisible(false);
            }
            else {
                endLabel.setVisible(true);
            }

            if(startNode.equals(endNode))
            {
                EndCircle.setVisible(true);
                StartCircle.setVisible(false);
                startLabel.setVisible(false);
                endLabel.setVisible(true);
            }

            pathPane.getChildren().add(EndCircle);

            pathPane.setPrefSize(childPane.getWidth(), childPane.getHeight());

            circles.add(StartCircle);
            circles.add(EndCircle);
            changeMapLabel();
            //Auto-zooms the screen
            autoZoom(path.getPath().get(floorSwitch1), path.getPath().get(floorSwitch2));
        }
    }

    /**
     * @Author: Nikhil
     * This function automatically zooms on our map when we display paths.
     * @param start
     * @param end
     */
    private void autoZoom(Location start, Location end) {
        double x = gesturePane.getWidth()/(Math.abs((start.getXcoord() - end.getXcoord())));
        double y = gesturePane.getHeight()/Math.abs(((start.getYcoord() - end.getYcoord())));
        double scale = (Math.min(x, y)/2.5) + 1.1;
        gesturePane.reset();
        gesturePane.zoomTo(scale, gesturePane.targetPointAtViewportCentre());
        double xSameVal = (start.getXcoord() + end.getXcoord()) / 2.0*childPane.getWidth()/Map.getImage().getWidth();
        double ySameVal = (start.getYcoord() + end.getYcoord()) / 2.0*childPane.getHeight()/Map.getImage().getHeight();
        gesturePane.centreOn(new Point2D(xSameVal, ySameVal));
    }

    @FXML
    /**
     * @author Gabe
     * reads the input for the floor combo box
     */
    private void filterFloor() {
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

    /**
     * Modified by Nikhil: When start is cleared the start node resets to the kiosk location, to display kiosk location properly.
     */
    @FXML
    private void clearStart(){
        single.setLastTime();
        PathFindStartDrop.getSelectionModel().clearSelection();
        PathFindStartDrop.setValue(null);
        startNode = kioskTemp;
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
                    if (type.contains((single.getData().get(j).getNodeType()))&& (single.getData().get(j).getFloor().equals(pickedFloor))) {
                        noHallStart.add(single.getData().get(j));
                    }
                }
            }
            if (PathFindEndDrop.getValue() == null) {
                PathFindEndDrop.setItems(noHallEnd);
                noHallEnd.clear();
                for (int j = 0; j < single.getData().size(); j++) {
                    if (type.contains((single.getData().get(j).getNodeType()))&& (single.getData().get(j).getFloor().equals(pickedFloor))) {
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
                    closestPath = findAbstractPath(astar,kioskTemp, closestLOC, "    ");
                }
            }

            //displayPath(closestPath.getPath(), kioskTemp, closestLOC);

            displayingPath = true;

            startNode = kioskTemp;
            endNode = closestLOC;
            path = closestPath;

            displayPath();
            //printPath(path.getPath());
            direction.setText(printPath(path.getPath()));


        //    sceneGestures.setDrawPath(circles,lines);
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
            single.setKiosk(single.getData().get(0));
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
        text += " Begin from " + A.get(0).getLongName() + "\n";
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


                text += "\u21E7 Go straight to " + b.getLongName()
                        + " (" + convertToExact(start.findDistance(b)) + " ft) \n";

                //- -> + , x+ : left
                d = i + 1;
                double slopeAB = calculateSlope(a,b);
                double slopeBC = calculateSlope(b,c);
                if(curDirection == nextDirection){
                    if(curDirection == 2 || curDirection == 6){
                        if(Math.abs(slopeBC)> Math.abs(slopeAB)){
                            text += "\u21E6 Turn left \n";
                        }
                        else{
                            text += "\u21E8 Turn right\n";
                        }
                    }
                    else if(curDirection == 4 || curDirection ==8){
                        if(Math.abs(slopeBC)> Math.abs(slopeAB)){
                            text += "\u21E8 Turn right\n";
                        }
                        else{
                            text += "\u21E6 Turn left \n";
                        }

                    }

                }
                else if((curDirection == 2 && nextDirection ==6) || (curDirection == 6 && nextDirection ==2)){
                    if(Math.abs(slopeBC)>Math.abs(slopeAB)){
                        text += "\u21E8 Turn right\n";
                    }
                    else{
                        text += "\u21E6 Turn left\n";
                    }

                }

                else if((curDirection == 8 && nextDirection ==4) || (curDirection == 6 && nextDirection ==2)){
                    if(Math.abs(slopeBC)>Math.abs(slopeAB)){
                        text += "\u21E6 Turn left\n";
                    }
                    else{
                        text += "\u21E8 Turn right\n";
                    }

                }

                else if(curDirection <= 5){
                    if(nextDirection < curDirection + 4 && nextDirection > curDirection){
                        text += "\u21E8 Turn right\n";
                    }
                    else {

                        text += "\u21E6 Turn left\n";
                    }
                }
                else{
                    if(curDirection == 6){
                        if(nextDirection == 7 || nextDirection == 8 || nextDirection == 1){
                            text += "\u21E8 Turn right\n";
                        }
                        if(nextDirection == 5 || nextDirection == 4 || nextDirection == 3){
                            text += "\u21E6 Turn left\n";
                        }


                    }
                    else if (curDirection ==7){
                        if(nextDirection == 8 || nextDirection == 1 || nextDirection == 2){
                            text += "\u21E8 Turn right\n";
                        }
                        else if(nextDirection == 6 || nextDirection == 5 || nextDirection == 4){
                            text += "\u21E6 Turn left\n";
                        }
                    }
                    else if(curDirection ==8){
                        if(nextDirection == 1 || nextDirection == 2 || nextDirection == 3){
                            text += "\u21E8 Turn right\n";
                        }
                        else if(nextDirection == 5 || nextDirection == 6 || nextDirection == 7){
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
                text += "\u21E7 Go straight to your destination " + A.get(A.size()-1).getLongName() +
                        " (" + convertToExact(b.findDistance(c)) + " ft) \n";
                return text;
            }
            if(isStairELe(a) && isStairELe(b)){
                //        System.out.println("Go to floor " + b.getFloor() + " by " + a.getLongName());
                text += "Go to floor " + b.getFloor() + " by " + a.getLongName() +"\n";

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


    //Alex
    @FXML
    public void submitSearchField(Event ae) {
        Filter.setValue(null);
        Floor.setValue(null);
        noHall();
        if(PathFindStartDrop.getValue() == null && startNode != kioskTemp){
            if(nameToLoc.get(searchField.getText()) != null) {
                PathFindStartDrop.setValue(nameToLoc.get(searchField.getText()));
                searchField.setText("");
            }
        }
        else{
            if(nameToLoc.get(searchField.getText()) != null) {
                PathFindEndDrop.setValue(nameToLoc.get(searchField.getText()));
                searchField.setText("");
            }
        }
    }
}
