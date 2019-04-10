package Controller;

import API.UpdateLocationThread;
import Access.EdgesAccess;
import Access.NodesAccess;
import Object.*;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;

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
    private JFXButton nodeDisplay; //nodeDisplayPress

    @FXML
    private JFXTextField nodeInfoID;
    @FXML
    private JFXTextField nodeInfoX;
    @FXML
    private JFXTextField nodeInfoY;
    @FXML
    private JFXTextField nodeInfoType;
    @FXML
    private JFXTextField nodeInfoBuilding;
    @FXML
    private JFXTextField nodeInfoFloor;
    @FXML
    private JFXTextField nodeInfoLong;
    @FXML
    private JFXTextField nodeInfoShort;

    @FXML Button SubmitButton;

    @FXML
    private ImageView Map;

    @FXML
    private AnchorPane anchorPaneWindow;

    @FXML
    private Pane imagePane;

    private int floorSelected = -2;
    private boolean displayingNodes = false;
    private Circle thisCircle;

    private ArrayList<String> mapURLs = new ArrayList<String>();
    private ArrayList<Circle> circles = new ArrayList<Circle>();
    private ArrayList<Line> lines = new ArrayList<Line>();

    private Point2D mousePress;

    private Rectangle clip;

    private PanAndZoomPane zoomPaneImage;
    private SceneGesturesForEditing sceneGestures;
    private NodesAccess na;
    private EdgesAccess ea;
    private AnchorPane anchorPanePath;
    private final DoubleProperty zoomProperty = new SimpleDoubleProperty(1.0d);
    private final DoubleProperty deltaY = new SimpleDoubleProperty(0.0d);
    private Singleton single = Singleton.getInstance();

    private HashMap<String, Location> lookup = new HashMap<String, Location>();
    private final ObservableList<Location> nodeData = FXCollections.observableArrayList();
    private final ObservableList<Edge> edgeData = FXCollections.observableArrayList();

    private Location focusNode;
    private Edge focusEdge;

    Timeline timeout;

    @FXML
    private void clickedG(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thegroundfloor.png"));
        floorSelected = 0;
        if(displayingNodes){
            eraseNodes();
            drawNodes();
        }
    }
    @FXML
    private void clickedL1(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel1.png"));
        floorSelected = -1;
        if(displayingNodes) {
            eraseNodes();
            drawNodes();
        }
    }

    @FXML public void clickedL2(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel2.png"));
        floorSelected = -2;
        if(displayingNodes){
            eraseNodes();
            drawNodes();
        }
    }
    @FXML
    private void clicked1(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/01_thefirstfloor.png"));
        floorSelected = 1;
        if(displayingNodes){
            eraseNodes();
            drawNodes();
        }
    }
    @FXML
    private void clicked2(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/02_thesecondfloor.png"));
        floorSelected = 2;
        if(displayingNodes){
            eraseNodes();
            drawNodes();
        }
    }
    @FXML
    private void clicked3(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/03_thethirdfloor.png"));
        floorSelected = 3;
        if(displayingNodes){
            eraseNodes();
            drawNodes();
        }
    }

    @FXML
    /**
     * Grace made this, but its from gabe's code
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

    ListIterator<String> listIterator = null;
    boolean upclickedLast = false;
    boolean downclickedLast = false;
/*
    @FXML
    /**
     * Grace made this, but its from gabe's code
     * Replaces image URL with the next floor up when the UP button is pressed
     *//*
    private void upClicked() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
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
     * Grace made this, but its from gabe's code
     * allows the map to change when UP is pressed and the last button clicked was DOWN by calling next one more time.
     *//*
    private void upAgain() {
        Singleton single = Singleton.getInstance();
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
     * Grace made this, but its from gabe's code
     * allows the map to change when down is pressed and the last button clicked was UP by calling previous one more time.
     *//*
    private void downAgain() {
        Singleton single = Singleton.getInstance();
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
     * Grace made this, but its from gabe's code
     * Replaces image URL with the next floor down when the DOWN button is pressed
     *//*
    private void downClicked(){
        Singleton single = Singleton.getInstance();
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
*/

    @SuppressWarnings("Convert2Diamond")
    @FXML
    public void initialize(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()){
                    try{
                        single.setLastTime();
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        Parent sceneMain = loader.load();

                        Stage thisStage = (Stage) addEdge.getScene().getWindow();

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

        anchorPanePath = new AnchorPane();

//        Map.fitWidthProperty().bind(imagePane.widthProperty());
//        Map.fitHeightProperty().bind(imagePane.heightProperty());

        Map.setFitWidth(610);
        Map.setFitHeight(415);

        clip = new Rectangle();
        clip.widthProperty().bind(Map.fitWidthProperty());
        clip.heightProperty().bind(Map.fitHeightProperty());
        anchorPanePath.setClip(clip);

        zoomPaneImage = new PanAndZoomPane();

        zoomProperty.bind(zoomPaneImage.myScale);
        deltaY.bind(zoomPaneImage.deltaY);
        zoomPaneImage.getChildren().add(Map);

        sceneGestures = new SceneGesturesForEditing(zoomPaneImage, Map);

        imagePane.addEventFilter( MouseEvent.MOUSE_CLICKED, getOnMouseClickedEventHandler());
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
                            eraseNodes();
                            drawNodesResize(oldValue.doubleValue(), newValue.doubleValue());

                            Map.setFitWidth(Map.getFitWidth()/oldValue.doubleValue()*newValue.doubleValue());
                        };

                        ChangeListener<Number> stageSizeListenerHeight = (observable, oldValue, newValue) -> {
                            eraseNodes();
                            drawNodesResize(oldValue.doubleValue(), newValue.doubleValue());

                            Map.setFitHeight(Map.getFitHeight()/oldValue.doubleValue()*newValue.doubleValue());
                        };

                        ((Stage) newWindow).widthProperty().addListener(stageSizeListenerWidth);
                        ((Stage) newWindow).heightProperty().addListener(stageSizeListenerHeight);
                    }
                });
            }
        });

//        zoomPaneImage.setLayoutX(30);
//        zoomPaneImage.setLayoutY(185);


        imagePane.getChildren().add(zoomPaneImage);
        imagePane.getChildren().add(anchorPanePath);
        //sceneGestures.reset(Map, Map.getImage().getWidth(), Map.getImage().getHeight());

        thisCircle = new Circle();
        anchorPanePath.getChildren().add(thisCircle);

        sceneGestures.reset(Map, Map.getImage().getWidth(), Map.getImage().getHeight());
        sceneGestures.setDrawPath(circles, lines);

        drawNodes();
    }



    public void setNextNode(Location proto) {

        this.focusNode = proto;
        this.deleteNode.setDisable(false);
    }

    public void setNextEdge(Edge proto) {

        this.focusEdge = proto;
        this.deleteEdge.setDisable(false);
    }

    @FXML
    private void deleteEdgePress() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        /*
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected edge?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            EdgesAccess ea = new EdgesAccess();

            deleteEdge.setDisable(true);
        }
        else if (alert.getResult() == ButtonType.NO) {
        }
        */
    }

    @FXML
    private void modifyEdgePress() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        /*
        ObservableList<String> toPass = FXCollections.observableArrayList();
        for (Location l : nodeData) {
            toPass.add(l.getLocID());

        }
        try {
            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EditEdges.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            EditEdgesController scene2Controller = loader.getController();

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
        */
    }

    @FXML
    private void addEdgePress() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        /*
        ObservableList<String> toPass = FXCollections.observableArrayList();
        for (Location l : nodeData) {
            toPass.add(l.getLocID());
        }
        try {
            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EditEdges.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            EditEdgesController scene2Controller = loader.getController();

            Scene scene = new Scene(roots);
            scene2Controller.populateNodeList(toPass);
            Stage thestage = (Stage) makeEditableNode.getScene().getWindow();
            //Show scene 2 in new window
            thestage.setScene(scene);

        } catch (IOException ex) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(ex);
        }
        */
    }

    @FXML
    private void deleteNodePress() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        /*
        //this is the dialogue popup
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected node?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        Singleton single = Singleton.getInstance();
        if (alert.getResult() == ButtonType.YES) {
            single.lookup.get(focusNode.getLocID()).restitch();
            //delete the node here
            NodesAccess na = new NodesAccess();
            na.deleteNode(focusNode.getLocID());


            EdgesAccess ea = new EdgesAccess();
            ArrayList<Edge> edgeList = new ArrayList<Edge>();

            for (Edge e: edgeData) {
                if (!(ea.containsEdge(e.getEdgeID()))) {
                    edgeList.add(e);
                }
            }
        }
        else if (alert.getResult() == ButtonType.NO) {
            //do nothing
        }

*/
    }

    @FXML
    private void modifyNodePress() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        /*
        try {
            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EditNode.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            EditNodeController scene2Controller = loader.getController();

            Scene scene = new Scene(roots);
            scene2Controller.fillFields(this.focusNode);
            Stage thestage = (Stage) makeEditableNode.getScene().getWindow();
            //Show scene 2 in new window
            thestage.setScene(scene);

        } catch (IOException ex) {
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(ex);
        }
        */
    }

    @FXML
    private void addNodePress() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        /*
        ObservableList<String> toPass = FXCollections.observableArrayList();
        for (Location l : nodeData) {
            toPass.add(l.getLocID());
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EditNode.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            EditNodeController scene2Controller = loader.getController();

            Scene scene = new Scene(roots);
            scene2Controller.populateNodeList(toPass);
            Stage thestage = (Stage) makeEditableNode.getScene().getWindow();
            //Show scene 2 in new window
            thestage.setScene(scene);
        } catch (Exception e){
        }
        */
    }

    @FXML
    private void downloadNodes() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        NodesAccess na = new NodesAccess();
        na.writeTableIntoCSV("");
    }

    @FXML
    private void downloadEdges() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        EdgesAccess ea = new EdgesAccess();
        ea.writeTableIntoCSV("");
    }

    private void initializeTable(NodesAccess na, EdgesAccess ea) {
        /*
        ArrayList<String> edgeList;
        int count;
        count = 0;
        while (count < na.countRecords()) {
            ArrayList<String> arr = na.getNodes(count);
            Location testx = new Location(arr.get(0), Integer.parseInt(arr.get(1)), Integer.parseInt(arr.get(2)), (arr.get(3)), arr.get(4), arr.get(5), arr.get(6), arr.get(7));
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
            if (!edgeList.get(0).equals("edgeID")) {
                Edge testy = new Edge(edgeList.get(0), lookup.get(edgeList.get(1)), lookup.get(edgeList.get(2)));
                edgeData.add(testy);
            }
            count++;
        }
        */
    }

    /**
     * Grace made these
     *
     */
    private String floorNum() {

        if(floorSelected == 3){
            return "3";
        }
        else if(floorSelected == 2){
            return "2";
        }
        else if(floorSelected == 1){
            return "1";
        }
        else if(floorSelected == 0){
            return "G";
        }
        else if(floorSelected == -1){
            return "L1";
        }
        else /*Map.getImage().equals("/SoftEng_UI_Mockup_Pics/02_thesecondfloor.png")*/{
            return "L2";
        }
    }

    @FXML
    private void nodeDisplayPress(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        displayingNodes = !displayingNodes;

        eraseNodes();
        if(displayingNodes) {
            drawNodes();
        }
    }

    private void eraseNodes(){
        circles.add(thisCircle);
        for (Circle c: circles){
            anchorPanePath.getChildren().remove(c);
        }

        circles.clear();

        circles.add(thisCircle);
        anchorPanePath.getChildren().add(thisCircle);
    }

    private void drawNodes(){
        double scaleRatio = Math.min(Map.getFitWidth() / Map.getImage().getWidth(), Map.getFitHeight() / Map.getImage().getHeight());
        Point2D point = sceneGestures.getImageLocation();

        if(displayingNodes) {
            //display all nodes on that floor!!!
            ArrayList<Location> nodes = new ArrayList<Location>();
            //want to fill nodes w/ floor = currrentFloor
            int temp = 0;
            for (int i = 0; i < single.getData().size(); i++) {
                if (single.getData().get(i).getFloor().equals(floorNum())/* current Map floor*/) {
                    nodes.add(single.getData().get(i));

                    Circle thisCircle = new Circle();


                    //Setting the properties of the circle
                    thisCircle.setCenterX((nodes.get(temp).getXcoord() - point.getX()) * scaleRatio * sceneGestures.getImageScale());
                    thisCircle.setCenterY((nodes.get(temp).getYcoord() - point.getY()) * scaleRatio * sceneGestures.getImageScale());
                    thisCircle.setRadius(Math.max(2.5, 2.5f * (sceneGestures.getImageScale() / 5)));
                    thisCircle.setStroke(Color.web("RED")); //#f5d96b
                    thisCircle.setFill(Color.web("RED"));

                    anchorPanePath.getChildren().add(thisCircle);

                    circles.add(thisCircle);
                    temp++;
                }
            }
        }
        if(thisCircle != null && mousePress != null) {
            thisCircle.setCenterX((mousePress.getX() - point.getX()) * scaleRatio * sceneGestures.getImageScale());
            thisCircle.setCenterY((mousePress.getY() - point.getY()) * scaleRatio * sceneGestures.getImageScale());
            thisCircle.setRadius(Math.max(2.5, 2.5f * (sceneGestures.getImageScale() / 5)));
            thisCircle.setStroke(Color.web("GREEN")); //#f5d96b
            thisCircle.setFill(Color.web("GREEN"));
        }
    }

    private void drawNodesResize(double oldSize, double newSize) {
        double newRatio = Math.min((Map.getFitWidth() / oldSize * newSize) / Map.getImage().getWidth(),(Map.getFitHeight() / oldSize * newSize) / Map.getImage().getHeight());
        double beforeRatio = Math.min(Map.getFitWidth()/Map.getImage().getWidth(), Map.getFitHeight()/Map.getImage().getHeight());

        double scaleRatio;

        scaleRatio = newRatio;

        Point2D point = sceneGestures.getImageLocation();

        if(displayingNodes) {
            //display all nodes on that floor!!!
            ArrayList<Location> nodes = new ArrayList<Location>();
            //want to fill nodes w/ floor = currrentFloor
            int temp = 0;
            for (int i = 0; i < single.getData().size(); i++) {
                if (single.getData().get(i).getFloor().equals(floorNum())/* current Map floor*/) {
                    nodes.add(single.getData().get(i));

                    Circle thisCircle = new Circle();


                    //Setting the properties of the circle
                    thisCircle.setCenterX((nodes.get(temp).getXcoord() - point.getX()) * scaleRatio * sceneGestures.getImageScale());
                    thisCircle.setCenterY((nodes.get(temp).getYcoord() - point.getY()) * scaleRatio * sceneGestures.getImageScale());
                    thisCircle.setRadius(Math.max(2.5, 2.5f * (sceneGestures.getImageScale() / 5)));
                    thisCircle.setStroke(Color.web("RED")); //#f5d96b
                    thisCircle.setFill(Color.web("RED"));

                    anchorPanePath.getChildren().add(thisCircle);

                    circles.add(thisCircle);
                    temp++;
                }
            }
        }
        if(thisCircle != null && mousePress != null) {
            thisCircle.setCenterX((mousePress.getX() - point.getX()) * scaleRatio * sceneGestures.getImageScale());
            thisCircle.setCenterY((mousePress.getY() - point.getY()) * scaleRatio * sceneGestures.getImageScale());
            thisCircle.setRadius(Math.max(2.5, 2.5f * (sceneGestures.getImageScale() / 5)));
            thisCircle.setStroke(Color.web("GREEN")); //#f5d96b
            thisCircle.setFill(Color.web("GREEN"));
        }
    }
        
        
    @FXML
    private void nodeInfoIDPress(){
        Singleton single = Singleton.getInstance();
        single.setData();
        //be able to modify the selected nodeID

    }

    @FXML
    private void nodeInfoFloorPress(){
        Singleton single = Singleton.getInstance();
        single.setData();
        //be able to modify the selected nodeID
    }
    @FXML
    private void nodeInfoXPress(){
        Singleton single = Singleton.getInstance();
        single.setData();
        //be able to modify the selected nodeID
    }
    @FXML
    private void nodeInfoYPress(){
        Singleton single = Singleton.getInstance();
        single.setData();
        //be able to modify the selected nodeID
    }
    @FXML
    private void nodeInfoTypePress(){
        Singleton single = Singleton.getInstance();
        single.setData();
        //be able to modify the selected nodeID
    }
    @FXML
    private void nodeInfoBuildingPress(){
        Singleton single = Singleton.getInstance();
        single.setData();
        //be able to modify the selected nodeID
    }
    @FXML
    private void nodeInfoLongPress(){
        Singleton single = Singleton.getInstance();
        single.setData();
        //be able to modify the selected nodeID
    }
    @FXML
    private void nodeInfoShortPress(){
        Singleton single = Singleton.getInstance();
        single.setData();
        //be able to modify the selected nodeID
    }

    @FXML
    private void submitButtonPressed() {
        UpdateLocationThread ul = new UpdateLocationThread();
        ul.start();
    }

    @FXML
    private void backPressed() throws IOException{
        timeout.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));

        Parent sceneMain = loader.load();

        LoggedInHomeController controller = loader.<LoggedInHomeController>getController();

        Stage theStage = (Stage) addEdge.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    /**
     * Mouse click handler
     */
    private EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            System.out.println(Map.getFitWidth());

            Singleton single = Singleton.getInstance();
            single.setLastTime();
            mousePress = sceneGestures.imageViewToImage(Map, new Point2D(event.getX(), event.getY()));
            if(mousePress.getX() == sceneGestures.getMouseDown().getValue().getX() && mousePress.getY() == sceneGestures.getMouseDown().getValue().getY()) {
                int getX = (int) mousePress.getX();
                int getY = (int) mousePress.getY();
                String newQuery = na.getNodebyCoordNoType(getX, getY, floorNum(), 5);
                Point2D point = sceneGestures.getImageLocation();
                if (newQuery != null) {
                    Location focusLoc = single.lookup.get(newQuery);
                    nodeInfoID.setText(focusLoc.getLocID());
                    nodeInfoX.setText("" + focusLoc.getXcoord());
                    nodeInfoY.setText("" + focusLoc.getYcoord());
                    nodeInfoType.setText("" + focusLoc.getNodeType());
                    nodeInfoBuilding.setText("" + focusLoc.getBuilding());
                    nodeInfoFloor.setText("" + focusLoc.getFloor());
                    nodeInfoLong.setText("" + focusLoc.getLongName());
                    nodeInfoShort.setText("" + focusLoc.getShortName());

                } else {
                    nodeInfoID.setText("");
                    nodeInfoX.setText("" + (int) mousePress.getX());
                    nodeInfoY.setText("" + (int) mousePress.getY());
                    nodeInfoType.setText("");
                    nodeInfoBuilding.setText("");
                    nodeInfoFloor.setText("");
                    nodeInfoLong.setText("");
                    nodeInfoShort.setText("");
                    circles.remove(thisCircle);
                    double scaleRatio = Math.min(Map.getFitWidth() / Map.getImage().getWidth(),Map.getFitHeight()/Map.getImage().getHeight());

                    //System.out.println(sceneGestures.getImageScale());
                    //System.out.println((mousePress.getX() - point.getX()) * scaleRatio * sceneGestures.getImageScale());

                    //Setting the properties of the circle
                    thisCircle.setCenterX((mousePress.getX() - point.getX()) * scaleRatio * sceneGestures.getImageScale());
                    thisCircle.setCenterY((mousePress.getY() - point.getY()) * scaleRatio * sceneGestures.getImageScale());
                    thisCircle.setRadius(Math.max(2.5, 2.5f * (sceneGestures.getImageScale() / 5)));
                    thisCircle.setStroke(Color.web("GREEN")); //#f5d96b
                    thisCircle.setFill(Color.web("GREEN"));

                    circles.add(thisCircle);
                }
            }
        }
    };

    private EventHandler<MouseEvent> getOnMouseClickedEventHandler(){
        return onMouseClickedEventHandler;
    }
}

