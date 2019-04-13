package edu.wpi.cs3733.d19.teamL.Map.MapEditing;

import edu.wpi.cs3733.d19.teamL.API.UpdateLocationThread;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Edge;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.EdgesAccess;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.NodesAccess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EditLocationController {

    @FXML
    Button backButton;

    @FXML
    Button downloadNode;

    @FXML
    ComboBox<Location> edgeDropDown;

    @FXML
    Button downloadEdge;

    @FXML
    Button makeEditableNode;

    @FXML
    Button makeEditableEdge;

    @FXML
    Button addNode;

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
    @FXML Button ButtonLinkBetweenScreens;

    @FXML
    private ImageView Map;

    @FXML
    private GridPane gridPane;

    private int floorSelected = -2;
    private boolean displayingNodes = false;
    private Circle thisCircle;

    private ArrayList<String> mapURLs = new ArrayList<String>();
    private ArrayList<Circle> circles = new ArrayList<Circle>();

    private Point2D mousePress;
    private GesturePane gesturePane;
    private StackPane childPane;
    private AnchorPane pathPane;

    private NodesAccess na;
    private EdgesAccess ea;
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

    @SuppressWarnings("Convert2Diamond")
    @FXML
    public void initialize(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        addNode.setDisable(true);
        deleteNode.setDisable(true);
        deleteEdge.setDisable(true);
        SubmitButton.setDisable(true);
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
                        controller.displayPopup();

                        Stage thisStage = (Stage) backButton.getScene().getWindow();

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

        pathPane = new AnchorPane();
        childPane = new StackPane();
        childPane.getChildren().add(Map);
        childPane.getChildren().add(pathPane);
        gesturePane = new GesturePane(childPane);
        gesturePane.setHBarEnabled(false);
        gesturePane.setVBarEnabled(false);
        gridPane.add(gesturePane,0,0);
        Map.fitWidthProperty().bind(gesturePane.widthProperty());
        Map.fitHeightProperty().bind(gesturePane.heightProperty());


        thisCircle = new Circle();
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
        single = Singleton.getInstance();
        single.setLastTime();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected edge?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            //EdgesAccess ea = new EdgesAccess();
            String focusNodeFirst = focusNode.getLocID()+"_"+edgeDropDown.getValue().getLocID();
            String focusNodeSecond = edgeDropDown.getValue().getLocID()+"_"+focusNode.getLocID();
            if(ea.containsEdge(focusNodeFirst)) {
                ea.deleteEdge(focusNodeFirst);
                //System.out.println("DELETING FIRST");
            }
            else if (ea.containsEdge(focusNodeSecond)){
                ea.deleteEdge(focusNodeSecond);
                //System.out.println("DELETING SECOND");
            }
            else {
                System.out.println("COULD NOT FIND EDGE");
            }
            UpdateLocationThread ul = new UpdateLocationThread();
            ul.start();
            //System.out.println(single.lookup.get("GHALL012L2").getEdges());
            populateEdges(focusNode);
            deleteEdge.setDisable(true);
            edgeDropDown.setValue(null);
            edgeDropDown.setPromptText("SELECT ANOTHER");
        }
        else if (alert.getResult() == ButtonType.NO) {
        }

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

    }

    @FXML
    private void deleteNodePress() {
        single = Singleton.getInstance();
        single.setLastTime();

        //this is the dialogue popup
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected node?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        single.setLastTime();
        if (alert.getResult() == ButtonType.YES) {
            single.lookup.get(focusNode.getLocID()).restitch();
            //delete the node here
            na = new NodesAccess();
            na.deleteNode(focusNode.getLocID());
            UpdateLocationThread ul = new UpdateLocationThread();
            ul.start();
            edgeDropDown.setItems(null);
            edgeDropDown.setPromptText("DELETED");
            nodeInfoID.setText("");
            nodeInfoX.setText("");
            nodeInfoY.setText("");
            nodeInfoType.setText("");
            nodeInfoBuilding.setText("");
            nodeInfoFloor.setText("");
            nodeInfoLong.setText("");
            nodeInfoShort.setText("");
            eraseNodes();
            drawNodes();
            deleteNode.setDisable(true);

        }
        else if (alert.getResult() == ButtonType.NO) {
            //do nothing
        }


    }

    @FXML
    private void modifyNodePress() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();

    }

    @FXML
    private void switchToLinkFloors() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EditLinkBetweenFloors.fxml"));

        Parent sceneMain = loader.load();

        Stage theStage = (Stage) deleteEdge.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void addNodePress() {
        //todo: only make this pressable when a new node is being drawn, and fields are filled
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        ArrayList<String> data = new ArrayList<String>();
        data.add(nodeInfoID.getText());
        data.add(nodeInfoX.getText());
        data.add(nodeInfoY.getText());
        data.add(nodeInfoFloor.getText());
        data.add(nodeInfoBuilding.getText());
        data.add(nodeInfoType.getText());
        data.add(nodeInfoLong.getText());
        data.add(nodeInfoShort.getText());
        na.addNode(data);
        UpdateLocationThread ul = new UpdateLocationThread();
        ul.start();
        addNode.setDisable(true);
        pathPane.getChildren().remove(thisCircle);

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
        else {
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
            pathPane.getChildren().remove(c);
        }

        circles.clear();

        circles.add(thisCircle);
        pathPane.getChildren().add(thisCircle);
    }


    @FXML
    private void onTextReleased() {

        if (nodeInfoID.getText().equals("") || nodeInfoX.getText().equals("") || nodeInfoY.getText().equals("") ||
                nodeInfoFloor.getText().equals("") || nodeInfoBuilding.getText().equals("") || nodeInfoType.getText().equals("") ||
                nodeInfoShort.getText().equals("") || nodeInfoLong.getText().equals("")) {
            addNode.setDisable(true);
            SubmitButton.setDisable(true);
        }
        else {
            if (!single.lookup.containsKey(nodeInfoID.getText())) {
                addNode.setDisable(false);
            }
            SubmitButton.setDisable(false);
        }
    }


    private void drawNodes(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        double scaleRatio = Math.min(childPane.getWidth() / Map.getImage().getWidth(), childPane.getHeight() / Map.getImage().getHeight());

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
                    thisCircle.setCenterX(nodes.get(temp).getXcoord()*scaleRatio);
                    thisCircle.setCenterY(nodes.get(temp).getYcoord()*scaleRatio);
                    thisCircle.setRadius(Math.max(2.5, 2.5f * gesturePane.getCurrentScale()/5));
                    thisCircle.setStroke(Color.web("RED")); //#f5d96b
                    thisCircle.setFill(Color.web("RED"));

                    pathPane.getChildren().add(thisCircle);

                    circles.add(thisCircle);
                    temp++;
                }
            }
        }
        if(thisCircle != null && mousePress != null) {
            thisCircle.setCenterX(mousePress.getX() * scaleRatio);
            thisCircle.setCenterY(mousePress.getY() * scaleRatio);
            thisCircle.setRadius(Math.max(2.5, 2.5f * gesturePane.getCurrentScale() / 5));
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
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        na.updateNode(nodeInfoID.getText(), "xcoord", nodeInfoX.getText());
        na.updateNode(nodeInfoID.getText(), "ycoord", nodeInfoY.getText());
        na.updateNode(nodeInfoID.getText(), "floor", nodeInfoFloor.getText());
        na.updateNode(nodeInfoID.getText(), "building", nodeInfoBuilding.getText());
        na.updateNode(nodeInfoID.getText(), "nodeType", nodeInfoType.getText());
        na.updateNode(nodeInfoID.getText(), "longName", nodeInfoLong.getText());
        na.updateNode(nodeInfoID.getText(), "shortName", nodeInfoShort.getText());
        UpdateLocationThread ul = new UpdateLocationThread();
        ul.start();
    }

    @FXML
    private void backPressed() throws IOException{
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EmployeeLoggedInHome.fxml"));

        if(single.isIsAdmin()){
            loader = new FXMLLoader(getClass().getClassLoader().getResource("AdminLoggedInHome.fxml"));
        }
        Parent sceneMain = loader.load();

        Stage theStage = (Stage) backButton.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    /**
     * Mouse click handler
     */
    private EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            single = Singleton.getInstance();
            single.setLastTime();
            mousePress = new Point2D(event.getX(), event.getY());
            if (mousePress.getX() <= 5000 && mousePress.getY() <= 3400) {
                int getX = (int) mousePress.getX();
                int getY = (int) mousePress.getY();
                String newQuery = na.getNodebyCoordNoType(getX, getY, floorNum(), 5);
                if (newQuery != null) {
                    focusNode = single.lookup.get(newQuery);
                    nodeInfoID.setText(focusNode.getLocID());
                    nodeInfoX.setText("" + focusNode.getXcoord());
                    nodeInfoY.setText("" + focusNode.getYcoord());
                    nodeInfoType.setText("" + focusNode.getNodeType());
                    nodeInfoBuilding.setText("" + focusNode.getBuilding());
                    nodeInfoFloor.setText("" + focusNode.getFloor());
                    nodeInfoLong.setText("" + focusNode.getLongName());
                    nodeInfoShort.setText("" + focusNode.getShortName());
                    populateEdges(focusNode);
                    nodeInfoID.setDisable(true);
                    deleteNode.setDisable(false);

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
                    nodeInfoID.setDisable(false);
                    deleteNode.setDisable(true);
                    double scaleRatio = Math.min(Map.getFitWidth() / Map.getImage().getWidth(), Map.getFitHeight() / Map.getImage().getHeight());

                    //System.out.println(sceneGestures.getImageScale());
                    //System.out.println((mousePress.getX() - point.getX()) * scaleRatio * sceneGestures.getImageScale());

                    //Setting the properties of the circle
                    thisCircle.setCenterX(mousePress.getX()*scaleRatio);
                    thisCircle.setCenterY(mousePress.getY()*scaleRatio);
                    thisCircle.setRadius(Math.max(2.5, 2.5f * (gesturePane.getCurrentScale() / 5)));
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

    private void populateEdges(Location x) {
        ObservableList<Location> locsToAdd = FXCollections.observableArrayList();
        Location l = single.lookup.get(x.getLocID());
        for (Edge e: l.getEdges()) {
            //System.out.println(e.getEdgeID());
            if (!e.getStartNode().equals(l)) {
                locsToAdd.add(e.getStartNode());
            }
            else {
                locsToAdd.add(e.getEndNode());
            }
        }
        edgeDropDown.setItems(locsToAdd);
        if (!locsToAdd.isEmpty()) {
            edgeDropDown.setValue(locsToAdd.get(0));
        }
        else {
            edgeDropDown.setPromptText("NO EDGES");
        }

    }

    @FXML
    private void selectedEdge() {

        deleteEdge.setDisable(false);
    }

}
