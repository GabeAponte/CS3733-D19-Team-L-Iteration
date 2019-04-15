package edu.wpi.cs3733.d19.teamL.Map.MapEditing;

import com.jfoenix.controls.JFXScrollPane;
import edu.wpi.cs3733.d19.teamL.API.UpdateLocationThread;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.CircleLocation;
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
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
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
    Button downloadEdge;

    @FXML
    private Button G;

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

    @FXML
    private ImageView Map;

    @FXML
    private GridPane gridPane;

    private int floorSelected = -2;
    private boolean displayingNodes = true;
    private CircleLocation thisCircle;

    private CircleLocation lastCircle;

    private ArrayList<String> mapURLs = new ArrayList<String>();
    private ArrayList<CircleLocation> circles = new ArrayList<CircleLocation>();
    private ArrayList<Line> lines = new ArrayList<Line>();

    private ArrayList<CircleLocation> shiftClick = new ArrayList<CircleLocation>();

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

    double orgSceneX, orgSceneY;

    double oldCircleX, oldCircleY;

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
        gesturePane.setFitHeight(true);

        gridPane.add(gesturePane,0,0, 7, GridPane.REMAINING);
        gesturePane.zoomTo(2.0,new Point2D(Map.getImage().getWidth(), Map.getImage().getHeight()));

//        gridPane.add(gesturePane,0,0);
        Map.fitWidthProperty().bind(gesturePane.widthProperty());
        Map.fitHeightProperty().bind(gesturePane.heightProperty());


        thisCircle = new CircleLocation();
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


    private void eraseNodes(){
        circles.add(thisCircle);
        for (Circle c: circles){
            pathPane.getChildren().remove(c);
        }

        circles.clear();

        circles.add(thisCircle);
        pathPane.getChildren().add(thisCircle);
    }


    private void drawNodes(){

        single.setLastTime();

        if(displayingNodes) {
            //display all nodes on that floor!!!
            ArrayList<Location> nodes = new ArrayList<Location>();
            //want to fill nodes w/ floor = currrentFloor
            int temp = 0;
            for (int i = 0; i < single.getData().size(); i++) {
                if (single.getData().get(i).getFloor().equals(floorNum())/* current Map floor*/) {
                    nodes.add(single.getData().get(i));

                    CircleLocation thisCircle = new CircleLocation();

                    //Setting the properties of the circle
                    thisCircle.setCenterX(nodes.get(temp).getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
                    thisCircle.setCenterY(nodes.get(temp).getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
                    thisCircle.setRadius(Math.max(2.0, 2.0f * gesturePane.getCurrentScale()/20));
                    thisCircle.setStroke(Color.web("RED")); //#f5d96b
                    thisCircle.setFill(Color.web("RED"));
                    thisCircle.setLocation(single.getData().get(i));
                    thisCircle.setOnMousePressed(circleOnMousePressedEventHandler);
                    //set the mouse drag to move the circle
                    thisCircle.setOnMouseDragged((t) -> {
                        if (t.isControlDown()) {


                            double offsetX = (t.getSceneX() - orgSceneX)/gesturePane.getCurrentScale();
                            double offsetY = (t.getSceneY() - orgSceneY)/gesturePane.getCurrentScale();

                            CircleLocation c = (CircleLocation) (t.getSource());
                            oldCircleX = c.getCenterX();
                            oldCircleY = c.getCenterY();

                            c.setCenterX(c.getCenterX() + offsetX);
                            c.setCenterY(c.getCenterY() + offsetY);

                            c.getSp().setLayoutX(c.getCenterX());
                            c.getSp().setLayoutY(c.getCenterY());
                            int newX = (int) (c.getCenterX()*(Map.getImage().getWidth()/childPane.getWidth()));
                            int newY = (int) (c.getCenterY()*(Map.getImage().getHeight()/childPane.getHeight()));
                            c.getxField().setText(Integer.toString(newX));
                            c.getyField().setText(Integer.toString(newY));


                            orgSceneX = t.getSceneX();
                            orgSceneY = t.getSceneY();
                            //System.out.println(c.getCenterX()*(Map.getImage().getWidth()/childPane.getWidth()));
                            //System.out.println(c.getCenterY()*(Map.getImage().getHeight()/childPane.getHeight()));
                            t.consume();
                        }
                    });

                    pathPane.getChildren().add(thisCircle);


                    circles.add(thisCircle);
                    temp++;
                }
            }
        }
//        if(thisCircle != null && mousePress != null) {
//            thisCircle.setCenterX(mousePress.getX() *Map.getImage().getWidth()*scaleRatio/Map.getFitWidth());
//            thisCircle.setCenterY(mousePress.getY() *Map.getImage().getHeight()*scaleRatio/Map.getFitHeight());
//            thisCircle.setRadius(Math.max(2.0, 2.0f * gesturePane.getCurrentScale() / 20));
//            thisCircle.setStroke(Color.web("GREEN")); //#f5d96b
//            thisCircle.setFill(Color.web("GREEN"));
//        }
        //System.out.println(circles);
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
    //Larry - Handler for pressing circle

    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                //floor,building,nodeType,longName,shortName


                @Override
                public void handle(MouseEvent t) {


                    if(t.isShiftDown()){
                        CircleLocation loc = (CircleLocation) (t.getSource());
                        if(shiftClick.contains(loc)){
                            shiftClick.remove(loc);
                            loc.setStroke(Color.web("RED"));
                            loc.setFill(Color.web("RED"));
                            for(Line A: loc.getLineList()){
                                pathPane.getChildren().remove(A);
                            }
                        }

                        else {
                             loc.setStroke(Color.web("GREEN"));
                             loc.setFill(Color.web("GREEN"));
                             ArrayList<Edge> edgeslist = loc.getLocation().getEdges();
                             shiftClick.add(loc);

                            if(edgeslist.isEmpty()){
                            //nothing
                             }
                              else {
                                  for (Edge e: edgeslist){
                                     Line line = new Line();
                                     line.setStartX(e.getEndNode().getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
                                     line.setStartY(e.getEndNode().getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
                                     line.setEndX(e.getStartNode().getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
                                     line.setEndY(e.getStartNode().getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
                                     loc.getLineList().add(line);
                                     pathPane.getChildren().add(line);
                                     lines.add(line);

                            }
                        }
                        }
                    }
                    if (!(lastCircle == null)) {
                        pathPane.getChildren().remove(lastCircle.getSp());
                        lastCircle.setSp(null);
                        lastCircle.setStroke(Color.web("RED"));
                        lastCircle.setFill(Color.web("RED"));
                        lastCircle.setCenterX(lastCircle.getLocation().getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
                        lastCircle.setCenterY(lastCircle.getLocation().getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
                    }
                    if (!(t.isShiftDown())) {
                        ((Circle)(t.getSource())).setStroke(Color.web("GREEN"));
                        ((Circle)(t.getSource())).setFill(Color.web("GREEN"));

                        orgSceneX = t.getSceneX();
                        orgSceneY = t.getSceneY();

                    CircleLocation c = (CircleLocation) (t.getSource());
                    c.toFront();
                   if (c.getSp() == null) {

                        ScrollPane sp = new ScrollPane();
                        sp.getStylesheets().add("MapBuilderScrollPane.css");

                        sp.setLayoutX(c.getLayoutX());
                        sp.setLayoutY(c.getLayoutY());
                        GridPane gp = new GridPane();



                        JFXButton close = new JFXButton("\u274C");
                        close.setPrefWidth(50);

                        JFXButton Update = new JFXButton("\u2713");
                        Update.setPrefWidth(50);

                        gp.add(close,1,0);
                        gp.add(Update,0,0);

                            //if user didn't press cancel or submit...


                            close.setOnAction(event -> {
                                pathPane.getChildren().remove(sp);
                                c.setSp(null);
                                ((Circle) (t.getSource())).setStroke(Color.web("RED"));
                                ((Circle) (t.getSource())).setFill(Color.web("RED"));
                                    }
                            );

                            // StackPane container = new StackPane(gp);
                            // container.setPadding(new Insets(24));
                            // container.getChildren().add(gp);
                            Font f = new Font("System", 8);

                        Label lb = new Label("X coordinate : ");
                        lb.setFont(f);
                        String txt = "" + ((CircleLocation) (t.getSource())).getLocation().getXcoord();
                        JFXTextField tf = new JFXTextField(txt);
                        tf.setFont(f);
                        tf.setAlignment(Pos.CENTER);
                        tf.setPrefWidth(50);
                        gp.add(lb, 0, 1);
                        gp.add(tf, 1, 1);

                        Label lb1 = new Label("Y coordinate : ");
                        lb1.setFont(f);
                        String txt1 = "" + ((CircleLocation) (t.getSource())).getLocation().getYcoord();
                        JFXTextField tf1 = new JFXTextField(txt1);
                        tf1.setFont(f);
                        tf1.setAlignment(Pos.CENTER);
                        tf1.setPrefWidth(50);
                        gp.add(lb1, 0, 2);
                        gp.add(tf1, 1, 2);

                        Label lb2 = new Label("Floor : ");
                        lb2.setFont(f);
                        String txt2 = "" + ((CircleLocation) (t.getSource())).getLocation().getFloor();
                        JFXTextField tf2 = new JFXTextField(txt2);
                        tf2.setFont(f);
                        tf2.setAlignment(Pos.CENTER);
                        tf2.setPrefWidth(50);
                        gp.add(lb2, 0, 3);
                        gp.add(tf2, 1, 3);

                        Label lb3 = new Label("Building : ");
                        lb3.setFont(f);
                        String txt3 = "" + ((CircleLocation) (t.getSource())).getLocation().getBuilding();
                        JFXTextField tf3 = new JFXTextField(txt3);
                        tf3.setFont(f);
                        tf3.setAlignment(Pos.CENTER);
                        tf3.setPrefWidth(50);
                        gp.add(lb3, 0, 4);
                        gp.add(tf3, 1, 4);

                        Label lb4 = new Label("NodeType : ");
                        lb4.setFont(f);
                        String txt4 = "" + ((CircleLocation) (t.getSource())).getLocation().getNodeType();
                        JFXTextField tf4 = new JFXTextField(txt4);
                        tf4.setFont(f);
                        tf4.setAlignment(Pos.CENTER);
                        tf4.setPrefWidth(50);
                        gp.add(lb4, 0, 5);
                        gp.add(tf4, 1, 5);

                        Label lb5 = new Label("LongName : ");
                        lb5.setFont(f);
                        String txt5 = "" + ((CircleLocation) (t.getSource())).getLocation().getLongName();
                        JFXTextField tf5 = new JFXTextField(txt5);
                        tf5.setFont(f);
                        tf5.setAlignment(Pos.CENTER);
                        tf5.setPrefWidth(50);
                        gp.add(lb5, 0, 6);
                        gp.add(tf5, 1, 6);

                        Label lb6 = new Label("ShortName : ");
                        lb6.setFont(f);
                        String txt6 = "" + ((CircleLocation) (t.getSource())).getLocation().getShortName();
                        JFXTextField tf6 = new JFXTextField(txt6);
                        tf6.setFont(f);
                        tf6.setAlignment(Pos.CENTER);
                        tf6.setPrefWidth(50);
                        gp.add(lb6, 0, 7);
                        gp.add(tf6, 1, 7);

                        //sp.getMainHeader().setMaxHeight(0);
                        //sp.getTopBar().setMaxHeight(0);
                        //sp.setVmax(440);
                        sp.setPrefSize(125, 100);


                        sp.setLayoutX(((Circle) (t.getSource())).getCenterX());
                        sp.setLayoutY(((Circle) (t.getSource())).getCenterY());
                        //sp.getChildren().add(gp);
                        //ToolBar tb = new ToolBar();
                        sp.setContent(gp);
                            Update.setOnAction(event ->  {
                                String id = c.getLocation().getLocID();
                                int x = Integer.parseInt(tf.getText());
                                int y = Integer.parseInt(tf1.getText());
                                String floor = tf2.getText();
                                String building = tf3.getText();
                                String type = tf4.getText();
                                String longName = tf5.getText();
                                String shortName = tf6.getText();


                                Location newLoc = new Location(id, x, y, floor, building, type, longName, shortName);
                                c.setLocation(newLoc);
                                Location oldLoc = single.lookup.get(id);
                                single.modifyNode(oldLoc, newLoc);
                                na.updateNode(id, "xcoord", x);
                                na.updateNode(id, "ycoord", y);
                                na.updateNode(id, "floor", floor);
                                na.updateNode(id, "building", building);
                                na.updateNode(id, "nodeType", type);
                                na.updateNode(id, "longName", longName);
                                na.updateNode(id, "shortName", shortName);
                                pathPane.getChildren().remove(sp);
                                c.setSp(null);
                                ((Circle) (t.getSource())).setStroke(Color.web("RED"));
                                ((Circle) (t.getSource())).setFill(Color.web("RED"));
                                lastCircle = null;
                            });


                            sp.setLayoutX(((Circle) (t.getSource())).getCenterX());
                            sp.setLayoutY(((Circle) (t.getSource())).getCenterY());
                            //sp.getChildren().add(gp);
                            sp.setContent(gp);
                            
                            pathPane.getChildren().add(sp);
                            c.setSp(sp);
                            lastCircle = c;
                            c.setxField(tf);
                            c.setyField(tf1);

                            System.out.println("Xcor "+ ((Circle)(t.getSource())).getCenterX());
                            System.out.println("Ycor "+ ((Circle)(t.getSource())).getCenterY());
                            System.out.println("successful scroll pane");
                        }
                        else {
                            System.out.println("DETECTED PREVIOUS PANE");
                        }
                    }
                    else {
                        //draw the lines here, shift has been clicked
                        System.out.println("EDGE SELECTION MODE");
                        if (focusNode == null) {
                            focusNode = ((CircleLocation) t.getSource()).getLocation();
                        }

                    }

                }
            };

}
