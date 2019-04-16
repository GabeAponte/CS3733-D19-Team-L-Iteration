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

import static javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER;

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
    private ArrayList<ScrollPane> sps = new ArrayList<ScrollPane>();


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

        gesturePane.setOnMousePressed(mouseClickedOnMap);

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




        for(Line l : lines){
            pathPane.getChildren().remove(l);
        }

        for (ScrollPane sp: sps){
            pathPane.getChildren().remove(sp);
        }

        circles.clear();
        lines.clear();
        pathPane.getChildren().removeAll();
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
                            c.getSp().setVisible(false);

                            c.setCenterX(c.getCenterX() + offsetX);
                            c.setCenterY(c.getCenterY() + offsetY);

                            c.getSp().setLayoutX(c.getCenterX()-150);
                            c.getSp().setLayoutY(c.getCenterY());
                            int newX = (int) (c.getCenterX()*(Map.getImage().getWidth()/childPane.getWidth()));
                            int newY = (int) (c.getCenterY()*(Map.getImage().getHeight()/childPane.getHeight()));
                            c.getxField().setText(Integer.toString(newX));
                            c.getyField().setText(Integer.toString(newY));


                            orgSceneX = t.getSceneX();
                            orgSceneY = t.getSceneY();
                            t.consume();
                        }
                    });
                    thisCircle.setOnMouseReleased((t) -> {
                        CircleLocation c = (CircleLocation) (t.getSource());
                        if (!(c.getSp() == null)) {
                            c.getSp().setVisible(true);
                        }
                            });

                    pathPane.getChildren().add(thisCircle);


                    circles.add(thisCircle);
                    temp++;
                }
            }
        }

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

    EventHandler<MouseEvent> mouseClickedOnMap =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {

                    if (t.isSecondaryButtonDown()) {
                        //System.out.println("COOL");
                        CircleLocation newCircle = new CircleLocation();
                        ScrollPane sp = new ScrollPane();
                        sp.getStylesheets().add("MapBuilderScrollPane.css");
                        Point2D point = gesturePane.targetPointAt(new Point2D(t.getX(), t.getY())).get();
                        sp.setLayoutX(point.getX());
                        sp.setLayoutY(point.getY());
                        GridPane gp = new GridPane();

                        newCircle.setLayoutX(point.getX());
                        newCircle.setLayoutY(point.getY());
                        newCircle.setStroke(Color.web("GREEN"));
                        newCircle.setFill(Color.web("GREEN"));
                        newCircle.setRadius(Math.max(2.0, 2.0f * gesturePane.getCurrentScale()/20));
                        newCircle.toFront();
                        pathPane.getChildren().add(newCircle);


                        JFXButton close = new JFXButton("\u274C");
                        close.setPrefWidth(50);

                        JFXButton Update = new JFXButton("\u2713");
                        Update.setPrefWidth(50);

                        gp.add(close, 1, 0);
                        gp.add(Update, 0, 0);

                        //if user didn't press cancel or submit...
                        close.setOnAction(event -> {
                                    pathPane.getChildren().remove(sp);
                                    pathPane.getChildren().remove(newCircle);
                                }
                        );

                        Font f = new Font("System", 8);

                        Label idlb = new Label("X coordinate : ");
                        idlb.setFont(f);
                        String idtxt = "";
                        JFXTextField idtf = new JFXTextField(idtxt);
                        idtf.setFont(f);
                        idtf.setAlignment(Pos.CENTER);
                        idtf.setPrefWidth(50);
                        gp.add(idlb, 0, 1);
                        gp.add(idtf, 1, 1);

                        Label lb = new Label("X coordinate : ");
                        lb.setFont(f);
                        String txt = "" + (int) (point.getX()*Map.getImage().getWidth()/childPane.getWidth());
                        JFXTextField tf = new JFXTextField(txt);
                        tf.setFont(f);
                        tf.setAlignment(Pos.CENTER);
                        tf.setPrefWidth(50);
                        gp.add(lb, 0, 2);
                        gp.add(tf, 1, 2);

                        Label lb1 = new Label("Y coordinate : ");
                        lb1.setFont(f);
                        String txt1 = "" + (int)(point.getY()*Map.getImage().getHeight()/childPane.getHeight());
                        JFXTextField tf1 = new JFXTextField(txt1);
                        tf1.setFont(f);
                        tf1.setAlignment(Pos.CENTER);
                        tf1.setPrefWidth(50);
                        gp.add(lb1, 0, 3);
                        gp.add(tf1, 1, 3);

                        Label lb2 = new Label("Floor : ");
                        lb2.setFont(f);
                        String txt2 = "" + floorNum();
                        JFXTextField tf2 = new JFXTextField(txt2);
                        tf2.setDisable(true);
                        tf2.setFont(f);
                        tf2.setAlignment(Pos.CENTER);
                        tf2.setPrefWidth(50);
                        gp.add(lb2, 0, 4);
                        gp.add(tf2, 1, 4);

                        Label lb3 = new Label("Building : ");
                        lb3.setFont(f);
                        String txt3 = "";
                        JFXTextField tf3 = new JFXTextField(txt3);
                        tf3.setFont(f);
                        tf3.setAlignment(Pos.CENTER);
                        tf3.setPrefWidth(50);
                        gp.add(lb3, 0, 5);
                        gp.add(tf3, 1, 5);

                        Label lb4 = new Label("NodeType : ");
                        lb4.setFont(f);
                        String txt4 = "";
                        JFXTextField tf4 = new JFXTextField(txt4);
                        tf4.setFont(f);
                        tf4.setAlignment(Pos.CENTER);
                        tf4.setPrefWidth(50);
                        gp.add(lb4, 0, 6);
                        gp.add(tf4, 1, 6);

                        Label lb5 = new Label("LongName : ");
                        lb5.setFont(f);
                        String txt5 = "";
                        JFXTextField tf5 = new JFXTextField(txt5);
                        tf5.setFont(f);
                        tf5.setAlignment(Pos.CENTER);
                        tf5.setPrefWidth(50);
                        gp.add(lb5, 0, 7);
                        gp.add(tf5, 1, 7);

                        Label lb6 = new Label("ShortName : ");
                        lb6.setFont(f);
                        String txt6 = "";
                        JFXTextField tf6 = new JFXTextField(txt6);
                        tf6.setFont(f);
                        tf6.setAlignment(Pos.CENTER);
                        tf6.setPrefWidth(50);
                        gp.add(lb6, 0, 8);
                        gp.add(tf6, 1, 8);

                        sp.setPrefSize(125, 100);

                        sp.setContent(gp);
                        Update.setOnAction(event -> {
                            String id = idtf.getText();
                            int x = Integer.parseInt(tf.getText());
                            int y = Integer.parseInt(tf1.getText());
                            String floor = tf2.getText();
                            String building = tf3.getText();
                            String type = tf4.getText();
                            String longName = tf5.getText();
                            String shortName = tf6.getText();


                            Location newLoc = new Location(id, x, y, floor, building, type, longName, shortName);
                            newCircle.setLocation(newLoc);
                            single.addNode(newLoc);
                            ArrayList<String> newData = new ArrayList<String >();
                            newData.add(id);
                            newData.add(Integer.toString(x));
                            newData.add(Integer.toString(y));
                            newData.add(floor);
                            newData.add(building);
                            newData.add(type);
                            newData.add(longName);
                            newData.add(shortName);
                            na.addNode(newData);
                            pathPane.getChildren().remove(sp);
                            newCircle.setSp(null);
                            newCircle.setStroke(Color.web("RED"));
                            newCircle.setFill(Color.web("RED"));
                            newCircle.setOnMousePressed(circleOnMousePressedEventHandler);
                            lastCircle = null;
                            circles.add(newCircle);
                            pathPane.getChildren().remove(sp);
                            eraseNodes();
                            drawNodes();
                        });

                        //sp.getChildren().add(gp);
                        sp.setContent(gp);

                        pathPane.getChildren().add(sp);
                        newCircle.setSp(sp);
                        lastCircle = newCircle;
                        newCircle.setxField(tf);
                        newCircle.setyField(tf1);

//                    System.out.println("Xcor "+ ((Circle)(t.getSource())).getCenterX());
                        //                   System.out.println("Ycor "+ ((Circle)(t.getSource())).getCenterY());
                        //System.out.println("successful scroll pane");

                    }
                }
            };

    EventHandler<MouseEvent> circleOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {
                //floor,building,nodeType,longName,shortName


                @Override
                public void handle(MouseEvent t) {
                    if(t.isAltDown()){
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete selected node?", ButtonType.YES, ButtonType.NO);
                        alert.showAndWait();

                    }

                    CircleLocation circ = (CircleLocation) t.getSource();
                    //System.out.println(circ.getCenterX());
                    //System.out.println(circ.getCenterY());

                    if(t.isShiftDown()){
                        CircleLocation loc = (CircleLocation) (t.getSource());
                        if(shiftClick.contains(loc)){
                            shiftClick.remove(loc);
                            loc.setStroke(Color.web("RED"));
                            for(Line A: loc.getLineList()){
                                pathPane.getChildren().remove(A);
                            }
                        }

                        else {
                            loc.setStroke(Color.web("GREEN"));
                            ArrayList<Edge> edgeslist = loc.getLocation().getEdges();
                            shiftClick.add(loc);

                            if(edgeslist.isEmpty()){
                                //nothing
                            }
                            else {
                                CircleLocation endcl = new CircleLocation();
                                for (Edge e: edgeslist){
                                    Line line = new Line();
                                    for(int i = 1; i< circles.size(); i++){
                                        String ID = circles.get(i).getLocation().getLocID();
                                        if(ID.equals(e.getEndID())||ID.equals(e.getStartID())&&
                                                !(ID.equals(loc.getLocation().getLocID()))){
                                            endcl = circles.get(i);
                                        }
                                    }
                                    System.out.println(endcl);
                                    if(e.getStartNode().getFloor().equals(floorNum())&&
                                            e.getEndNode().getFloor().equals(floorNum())&& endcl != null) {
                                        line.setStartX(e.getEndNode().getXcoord() * childPane.getWidth() / Map.getImage().getWidth());
                                        line.setStartY(e.getEndNode().getYcoord() * childPane.getHeight() / Map.getImage().getHeight());
                                        line.setEndX(e.getStartNode().getXcoord() * childPane.getWidth() / Map.getImage().getWidth());
                                        line.setEndY(e.getStartNode().getYcoord() * childPane.getHeight() / Map.getImage().getHeight());

                                        line.startXProperty().bind(loc.centerXProperty());
                                        line.startYProperty().bind(loc.centerYProperty());
                                        line.endXProperty().bind(endcl.centerXProperty());
                                        line.endYProperty().bind(endcl.centerYProperty());

                                        loc.getLineList().add(line);
                                        pathPane.getChildren().add(line);
                                        lines.add(line);
                                    }

                                }
                            }
                        }
                    }
                    if (!(lastCircle == null) && !(t.isShiftDown())) {
                        if (!(((CircleLocation) (t.getSource())).equals(lastCircle))) {
                            pathPane.getChildren().remove(lastCircle.getSp());
                            lastCircle.setSp(null);
                            lastCircle.setFill(Color.web("RED"));
                            lastCircle.setCenterX(lastCircle.getLocation().getXcoord() * childPane.getWidth() / Map.getImage().getWidth());
                            lastCircle.setCenterY(lastCircle.getLocation().getYcoord() * childPane.getHeight() / Map.getImage().getHeight());
                        }
                    }
                    if (!(t.isShiftDown())) {
                        //((Circle)(t.getSource())).setStroke(Color.web("GREEN"));
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



                            JFXButton close = new JFXButton("\u274E");
                            close.setPrefWidth(50);

                            JFXButton Update = new JFXButton("\u2705");
                            Update.setPrefWidth(50);

                            Update.setStyle("-fx-border-color: green; -fx-border-width: 1px;");
                            close.setStyle("-fx-border-color: red; -fx-border-width: 1px;");




                            gp.add(close,1,0);
                            gp.add(Update,0,0);


                            //if user didn't press cancel or submit...


                            close.setOnAction(event -> {
                                        pathPane.getChildren().remove(sp);
                                        c.setSp(null);
                                        //((Circle) (t.getSource())).setStroke(Color.web("RED"));
                                        ((Circle) (t.getSource())).setFill(Color.web("RED"));
                                }
                            );

                            Font f = new Font("System", 8);

                            Label lb = new Label("X coordinate : ");
                            lb.setFont(f);
                            String txt = "" + ((CircleLocation) (t.getSource())).getLocation().getXcoord();
                            JFXTextField tf = new JFXTextField(txt);
                            tf.setFont(f);
                            tf.setAlignment(Pos.BASELINE_LEFT);
                            tf.setPrefWidth(50);
                            gp.add(lb, 0, 1);
                            gp.add(tf, 1, 1);

                            Label lb1 = new Label("Y coordinate : ");
                            lb1.setFont(f);
                            String txt1 = "" + ((CircleLocation) (t.getSource())).getLocation().getYcoord();
                            JFXTextField tf1 = new JFXTextField(txt1);
                            tf1.setFont(f);
                            tf1.setAlignment(Pos.BASELINE_LEFT);
                            tf1.setPrefWidth(50);
                            gp.add(lb1, 0, 2);
                            gp.add(tf1, 1, 2);

                            Label lb2 = new Label("Floor : ");
                            lb2.setFont(f);
                            String txt2 = "" + ((CircleLocation) (t.getSource())).getLocation().getFloor();
                            JFXTextField tf2 = new JFXTextField(txt2);
                            tf2.setFont(f);
                            tf2.setAlignment(Pos.BASELINE_LEFT);
                            tf2.setPrefWidth(50);
                            gp.add(lb2, 0, 3);
                            gp.add(tf2, 1, 3);

                            Label lb3 = new Label("Building : ");
                            lb3.setFont(f);
                            String txt3 = "" + ((CircleLocation) (t.getSource())).getLocation().getBuilding();
                            JFXTextField tf3 = new JFXTextField(txt3);
                            tf3.setFont(f);
                            tf3.setAlignment(Pos.BASELINE_LEFT);
                            tf3.setPrefWidth(50);
                            gp.add(lb3, 0, 4);
                            gp.add(tf3, 1, 4);

                            Label lb4 = new Label("NodeType : ");
                            lb4.setFont(f);
                            String txt4 = "" + ((CircleLocation) (t.getSource())).getLocation().getNodeType();
                            JFXTextField tf4 = new JFXTextField(txt4);
                            tf4.setFont(f);
                            tf4.setAlignment(Pos.BASELINE_LEFT);
                            tf4.setPrefWidth(50);
                            gp.add(lb4, 0, 5);
                            gp.add(tf4, 1, 5);

                            Label lb5 = new Label("LongName : ");
                            lb5.setFont(f);
                            String txt5 = "" + ((CircleLocation) (t.getSource())).getLocation().getLongName();
                            JFXTextField tf5 = new JFXTextField(txt5);
                            tf5.setFont(f);
                            tf5.setAlignment(Pos.BASELINE_LEFT);
                            tf5.setPrefWidth(Control.USE_COMPUTED_SIZE);
                            gp.add(lb5, 0, 6);
                            gp.add(tf5, 1, 6);


                            Label lb6 = new Label("ShortName : ");
                            lb6.setFont(f);
                            String txt6 = "" + ((CircleLocation) (t.getSource())).getLocation().getShortName();
                            JFXTextField tf6 = new JFXTextField(txt6);
                            tf6.setFont(f);
                            tf6.setAlignment(Pos.BASELINE_LEFT);
                            tf6.setPrefWidth(Control.USE_COMPUTED_SIZE);
                            gp.add(lb6, 0, 7);
                            gp.add(tf6, 1, 7);


                            sp.setPrefSize(Control.USE_COMPUTED_SIZE, 120);
                            sp.setHbarPolicy(NEVER);

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
                                //((Circle) (t.getSource())).setStroke(Color.web("RED"));
                                ((Circle) (t.getSource())).setFill(Color.web("RED"));
                                lastCircle = null;
                            });


                            sp.setLayoutX(((Circle) (t.getSource())).getCenterX()-150);
                            sp.setLayoutY(((Circle) (t.getSource())).getCenterY());
                            gp.setMargin(close,new Insets(0,0,0,20));
                            sp.setContent(gp);

                            pathPane.getChildren().add(sp);
                            sps.add(sp);
                            c.setSp(sp);
                            lastCircle = c;
                            c.setxField(tf);
                            c.setyField(tf1);

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
                    t.consume();

                }

            };


}
