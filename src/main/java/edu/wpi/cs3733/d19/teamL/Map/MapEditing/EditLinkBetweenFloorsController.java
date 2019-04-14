package edu.wpi.cs3733.d19.teamL.Map.MapEditing;

import edu.wpi.cs3733.d19.teamL.API.UpdateLocationThread;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.EdgesAccess;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.NodesAccess;
import com.jfoenix.controls.JFXRadioButton;
import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

public class EditLinkBetweenFloorsController {

    @FXML
    private Stage thestage;

    @FXML
    private Button clearButton;

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
    private GridPane gridPane;

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

    private GesturePane gesturePaneMain;
    private AnchorPane aPaneMain = new AnchorPane();

    private GesturePane gesturePaneLower;
    private AnchorPane aPaneLower = new AnchorPane();

    private GesturePane gesturePaneUpper;
    private AnchorPane aPaneUpper = new AnchorPane();

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


        MapLower.setVisible(false);
        MapUpper.setVisible(false);

        gesturePaneMain = setUpImage(aPaneMain,Map);
        gridPane.add(gesturePaneMain,3,2,1,2);
        gesturePaneUpper = setUpImage(aPaneUpper,MapUpper);
        gridPane.add(gesturePaneUpper,1,2,1,2);
        gesturePaneLower = setUpImage(aPaneLower,MapLower);
        gridPane.add(gesturePaneLower,1,5,1,2);
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
        UpdateLocationThread ul = new UpdateLocationThread();
        ul.start();
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
    private GesturePane setUpImage(AnchorPane anchorPanePath, ImageView Map1) {
        StackPane sp = new StackPane();
        sp.getChildren().add(Map1);
        sp.getChildren().add(anchorPanePath);

        anchorPanePath.setOnMouseClicked(onMouseClickedEventHandler);


        Map1.setPreserveRatio(true);
        GesturePane gesturePane = new GesturePane(sp);
        Map1.fitWidthProperty().bind(gesturePane.widthProperty());
        Map1.fitHeightProperty().bind(gesturePane.heightProperty());
        gesturePane.setHBarEnabled(false);
        gesturePane.setVBarEnabled(false);
        return gesturePane;
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
        eraseNodes(aPaneLower, circlesLower);
        eraseNodes(aPaneUpper, circlesUpper);
        eraseNodes(aPaneMain, circles);
    }


    @FXML
    private void nodeDisplayPress() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        //displayingNodes = !displayingNodes;
        eraseNodes(aPaneMain, circles);
        eraseNodes(aPaneLower, circlesLower);
        eraseNodes(aPaneUpper, circlesUpper);
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
        eraseNodes(aPaneLower, circlesLower);
        eraseNodes(aPaneUpper, circlesUpper);
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
        double scaleRatio = Math.min(gesturePaneMain.getWidth() / Map.getImage().getWidth(),gesturePaneMain.getHeight()/Map.getImage().getHeight());
        for (int i = 0; i < single.getData().size(); i++) {
            boolean badCode = false;
            if (type == "ALL") {
                badCode = true;
            }
            if (single.getData().get(i).getFloor().equals(currentMap) && (single.getData().get(i).getNodeType().equals(type) || badCode)) {
                nodes.add(single.getData().get(i));

                Circle thisCircle = new Circle();

                aPaneMain.getChildren().add(thisCircle);

                //Setting the properties of the circle
                thisCircle.setCenterX(nodes.get(temp).getXcoord() * scaleRatio);
                thisCircle.setCenterY(nodes.get(temp).getYcoord() * scaleRatio);
                thisCircle.setRadius(Math.max(0.5, 0.5f * (gesturePaneMain.getCurrentScale() / 20)));
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
            int getX;
            int getY;

            System.out.println(Map.getFitWidth());
            System.out.println(gesturePaneMain.getWidth());
            System.out.println(Map.isPreserveRatio());
            System.out.println(gesturePaneMain.getFitMode());

            double scaleRatio = Math.min(Map.getFitWidth() / Map.getImage().getWidth(), Map.getFitHeight() / Map.getImage().getHeight());

            if (multiFloorSelect.isSelected()) {
                if (event.getSceneX() > gesturePaneMain.getLayoutX() && event.getSceneY() < gesturePaneMain.getLayoutY() + gesturePaneMain.getHeight()) {
                    getX = (int)(event.getX()/scaleRatio);
                    getY = (int)(event.getY()/scaleRatio);
                    String newQuery = na.getNodebyCoordNoType(getX, getY, currentMap, 10);
                    if (newQuery != null) {
                        focusLoc = single.lookup.get(newQuery);
                        node1X.setText("" + focusLoc.getXcoord());
                        node1Y.setText("" + focusLoc.getYcoord());
                        node1Name.setText(focusLoc.getLocID());
                    }
                } else if (event.getSceneY() < gesturePaneUpper.getLayoutY() + gesturePaneUpper.getHeight() && !currentMap.equals("3")) {
                    getX = (int)(event.getX()/scaleRatio);
                    getY = (int)(event.getY()/scaleRatio);
                    String newQuery = na.getNodebyCoordNoType(getX, getY, currentMap, 10);
                    if (newQuery != null) {
                        focusLoc = single.lookup.get(newQuery);
                        node1X.setText("" + focusLoc.getXcoord());
                        node1Y.setText("" + focusLoc.getYcoord());
                        node1Name.setText(focusLoc.getLocID());
                    }
                } else {
                    getX = (int)(event.getX()/scaleRatio);
                    getY = (int)(event.getY()/scaleRatio);
                    String newQuery = na.getNodebyCoordNoType(getX, getY, currentMap, 10);
                    if (newQuery != null) {
                        focusLoc = single.lookup.get(newQuery);
                        node1X.setText("" + focusLoc.getXcoord());
                        node1Y.setText("" + focusLoc.getYcoord());
                        node1Name.setText(focusLoc.getLocID());
                    }
                }
            }
            else {
                if (event.getSceneX() > gesturePaneMain.getLayoutX() && event.getSceneY() < gesturePaneMain.getLayoutY() + gesturePaneMain.getHeight()) {
                    getX = (int)(event.getX()/scaleRatio);
                    getY = (int)(event.getY()/scaleRatio);
                    String newQuery;
                    if (type != "ALL") {
                        newQuery = na.getNodebyCoord(getX, getY, currentMap, type);
                    }
                    else {
                        newQuery = na.getNodebyCoordNoType(getX, getY, currentMap, 10);
                    }
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
        double scaleRatio = Math.min(gesturePaneUpper.getWidth() / Map.getImage().getWidth(), gesturePaneUpper.getHeight()/Map.getImage().getHeight());
        for (int i = 0; i < single.getData().size(); i++) {
            if (single.getData().get(i).getFloor().equals(currentMapAbove) && single.getData().get(i).getNodeType().equals(type)) {
                nodes.add(single.getData().get(i));

                Circle thisCircle = new Circle();

                aPaneUpper.getChildren().add(thisCircle);

                //Setting the properties of the circle
                thisCircle.setCenterX(nodes.get(temp).getXcoord() * scaleRatio);
                thisCircle.setCenterY(nodes.get(temp).getYcoord() * scaleRatio);
                thisCircle.setRadius(Math.max(0.5, 0.5f * (gesturePaneUpper.getCurrentScale() / 20)));
                thisCircle.setStroke(Color.web("RED")); //#f5d96b
                thisCircle.setFill(Color.web("RED"));

                circlesUpper.add(thisCircle);
                temp++;
            }
        }
        ArrayList<Location> nodesLower = new ArrayList<Location>();
        //want to fill nodes w/ floor = currrentFloor
        temp = 0;
        for (int i = 0; i < single.getData().size(); i++) {
            if (single.getData().get(i).getFloor().equals(currentMapBelow) && single.getData().get(i).getNodeType().equals(type)) {
                nodesLower.add(single.getData().get(i));

                Circle thisCircle = new Circle();

                aPaneLower.getChildren().add(thisCircle);

                //Setting the properties of the circle
                thisCircle.setCenterX(nodesLower.get(temp).getXcoord() *scaleRatio);
                thisCircle.setCenterY(nodesLower.get(temp).getYcoord() * scaleRatio);
                thisCircle.setRadius(Math.max(0.5, 0.5f * (gesturePaneLower.getCurrentScale() / 20)));
                thisCircle.setStroke(Color.web("RED")); //#f5d96b
                thisCircle.setFill(Color.web("RED"));

                circlesLower.add(thisCircle);
                System.out.println(circlesLower);
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
        for (Circle c: circle){
            anchorPanePath.getChildren().remove(c);
        }

        circle.clear();
    }
}

