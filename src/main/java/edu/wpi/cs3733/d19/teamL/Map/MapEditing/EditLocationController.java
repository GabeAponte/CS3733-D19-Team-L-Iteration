package edu.wpi.cs3733.d19.teamL.Map.MapEditing;

import edu.wpi.cs3733.d19.teamL.Map.MapLocations.CircleLocation;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Edge;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.LineEdge;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.EdgesAccess;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.NodesAccess;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static javafx.scene.control.ScrollPane.ScrollBarPolicy.NEVER;

public class EditLocationController {

    @FXML
    Button back;

    @FXML
    Button downloadNode;

    @FXML
    private Label thisMap;

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
    private ImageView Map;

    @FXML
    private GridPane gridPane;

    private int floorSelected = 0;
    private boolean displayingNodes = true;
    private CircleLocation thisCircle;

    private CircleLocation lastCircle;
    private boolean animationPlayed = false;
    private boolean showEdge = false;

    private ArrayList<String> mapURLs = new ArrayList<String>();
    private ArrayList<CircleLocation> circles = new ArrayList<CircleLocation>();
    private ArrayList<LineEdge> lines = new ArrayList<LineEdge>();

    private ArrayList<LineEdge> showedges = new ArrayList<LineEdge>();

    private ArrayList<ScrollPane> sps = new ArrayList<ScrollPane>();
    private ArrayList<Polygon> pns = new ArrayList<Polygon>();


    private ArrayList<CircleLocation> shiftClick = new ArrayList<CircleLocation>();
    private ArrayList<CircleLocation> addEdgeList = new ArrayList<CircleLocation>();
    private ArrayList<CircleLocation> crossEdgeList = new ArrayList<CircleLocation>();

    private CircleLocation betweenFloorsCircle;

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
        changeMapLabel();
        if(displayingNodes){
            eraseNodes();
            drawNodes();
        }
        showEdge=false;
    }
    @FXML
    private void clickedL1(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel1.png"));
        floorSelected = -1;
        changeMapLabel();
        if(displayingNodes) {
            eraseNodes();
            drawNodes();
        }
        showEdge=false;
    }

    @FXML public void clickedL2(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel2.png"));
        floorSelected = -2;
        changeMapLabel();
        if(displayingNodes){
            eraseNodes();
            drawNodes();
        }
        showEdge=false;
    }
    @FXML
    private void clicked1(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/01_thefirstfloor.png"));
        floorSelected = 1;
        changeMapLabel();
        if(displayingNodes){
            eraseNodes();
            drawNodes();
        }
        showEdge=false;
    }
    @FXML
    private void clicked2(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/02_thesecondfloor.png"));
        floorSelected = 2;
        changeMapLabel();
        if(displayingNodes){
            eraseNodes();
            drawNodes();
        }
        showEdge=false;
    }
    @FXML
    private void clicked3(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/03_thethirdfloor.png"));
        floorSelected = 3;
        changeMapLabel();
        if(displayingNodes){
            eraseNodes();
            drawNodes();
        }
        showEdge=false;
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
                        Memento m = single.getOrig();
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(m.getFxml()));

                        Parent sceneMain = loader.load();
                        HomeScreenController controller = loader.<HomeScreenController>getController();
                        controller.displayPopup();

                        Stage thisStage = (Stage) back.getScene().getWindow();

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
        thisMap.toFront();

        gesturePane.setOnMousePressed(mouseClickedOnMap);

        gridPane.add(gesturePane,0,0, 1, 1);

//        gridPane.add(gesturePane,0,0);
        Map.fitWidthProperty().bind(gesturePane.widthProperty());
        Map.fitHeightProperty().bind(gesturePane.heightProperty());

        gesturePane.toBack();

        thisCircle = new CircleLocation();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                displayingNodes = true;
                drawNodes();
            }
        });
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
    private void changeMapLabel() {
        if (floorNum().equals("L2")){
            thisMap.setText("Lower Level 2");
        }

        if (floorNum().equals("L1")){
            thisMap.setText("Lower Level 1");
        }

        if (floorNum().equals("G")){
            thisMap.setText("Ground Floor");
        }

        if (floorNum().equals("1")){
            thisMap.setText("Floor 1");
        }

        if (floorNum().equals("2")){
            thisMap.setText("Floor 2");
        }

        if (floorNum().equals("3")){
            thisMap.setText("Floor 3");
        }

        if (floorNum().equals("4")){
            thisMap.setText("Flexible Workspace");
        }
    }


    private void eraseNodes(){
//        circles.add(thisCircle);
        for (Circle c: circles){
            pathPane.getChildren().remove(c);
        }


        for(Line l : lines){
            pathPane.getChildren().remove(l);
        }

        for (ScrollPane sp: sps){
            pathPane.getChildren().remove(sp);
        }

        for(Polygon pn: pns){
            pathPane.getChildren().remove(pn);

        }

        circles.clear();
        lines.clear();
        pns.clear();
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
                            if (animationPlayed) {
                                orgSceneX = t.getSceneX();
                                orgSceneY = t.getSceneY();
                                animationPlayed = false;
                                System.out.println("HERE");
                            }

                            double offsetX = (t.getSceneX() - orgSceneX)/gesturePane.getCurrentScale();
                            double offsetY = (t.getSceneY() - orgSceneY)/gesturePane.getCurrentScale();

                            CircleLocation c = (CircleLocation) (t.getSource());


                            c.setCenterX(c.getCenterX() + offsetX);
                            c.setCenterY(c.getCenterY() + offsetY);
                            if (!(c.getSp() == null)) {
                                c.getSp().setVisible(false);
                                c.getSp().setLayoutX(c.getCenterX() - 50);
                                c.getSp().setLayoutY(c.getCenterY() - 128);

                                int newX = (int) (c.getCenterX()*(Map.getImage().getWidth()/childPane.getWidth()));
                                int newY = (int) (c.getCenterY()*(Map.getImage().getHeight()/childPane.getHeight()));
                                c.getxField().setText(Integer.toString(newX));
                                c.getyField().setText(Integer.toString(newY));
                            }
                            if(!(c.getPn() == null)){
                                c.getPn().setVisible(false);
                                c.getPn().getPoints().clear();
                                c.getPn().getPoints().addAll(c.getCenterX(),c.getCenterY()-5,
                                        c.getCenterX()-5,c.getCenterY()-10,
                                        c.getCenterX()+5,c.getCenterY()-10);
                            }


                            }

                            orgSceneX = t.getSceneX();
                            orgSceneY = t.getSceneY();
                            t.consume();

                        }
                    );
                    thisCircle.setOnMouseReleased((t) -> {
                        CircleLocation c = (CircleLocation) (t.getSource());
                        if (!(c.getSp() == null)) {
                            c.getSp().setVisible(true);
                        }
                        if (!(c.getPn() == null)) {
                            c.getPn().setVisible(true);
                        }
                            });

                    pathPane.getChildren().add(thisCircle);


                    circles.add(thisCircle);
                    temp++;
                }
            }
            if (!(betweenFloorsCircle == null)) {
                circles.add(betweenFloorsCircle);
                betweenFloorsCircle.setCenterX(betweenFloorsCircle.getLocation().getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
                betweenFloorsCircle.setCenterY(betweenFloorsCircle.getLocation().getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
                betweenFloorsCircle.setRadius(Math.max(2.0, 2.0f * gesturePane.getCurrentScale()/20));
                betweenFloorsCircle.setStroke(Color.web("BLUE")); //#f5d96b
                betweenFloorsCircle.setFill(Color.web("BLUE"));
                pathPane.getChildren().add(betweenFloorsCircle);
            }
            else {
                addEdgeList.clear();
            }
        }

    }


    @FXML
    private void backPressed(ActionEvent event) throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        Memento m = single.restore();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }
    //Larry - Handler for pressing circle

    EventHandler<MouseEvent> mouseClickedOnMap =
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent t) {

                    if (t.isSecondaryButtonDown() && !(t.isShiftDown())&& !(t.isAltDown())) {
                        //System.out.println("COOL");
                        CircleLocation newCircle = new CircleLocation();
                        ScrollPane sp = new ScrollPane();
                        sp.getStylesheets().add("MapBuilderScrollPane.css");
                        Point2D point = gesturePane.targetPointAt(new Point2D(t.getX(), t.getY())).get();
                       // System.out.println("px" + point.getX());
                       // System.out.println("py" + point.getY());
                        if(point.getX()<=50){
                            sp.setLayoutX(point.getX());
                        }
                        else if(point.getX()>=970){
                            sp.setLayoutX(point.getX()-100);
                        }
                        else {
                            sp.setLayoutX(point.getX() - 50);
                        }

                        if(point.getY()<=150){
                            sp.setLayoutY(point.getY());
                        }
                        else if(point.getY()>=600){
                            sp.setLayoutY(point.getY()- 150);
                        }
                        else{
                            sp.setLayoutY(point.getY()- 150);
                        }

                        GridPane gp = new GridPane();

                        newCircle.setLayoutX(point.getX());
                        newCircle.setLayoutY(point.getY());
                        newCircle.setStroke(Color.web("GREEN"));
                        newCircle.setFill(Color.web("GREEN"));
                        newCircle.setRadius(Math.max(2.0, 2.0f * gesturePane.getCurrentScale()/20));
                        newCircle.toFront();
                        pathPane.getChildren().add(newCircle);
                        circles.add(newCircle);


                        JFXButton close = new JFXButton("\u274E");
                        close.setPrefWidth(40);

                        JFXButton Update = new JFXButton("\u2705");
                        Update.setPrefWidth(40);

                        Update.setStyle("-fx-border-color: green; -fx-border-width: 1px;");
                        close.setStyle("-fx-border-color: red; -fx-border-width: 1px;");

                        gp.add(close, 1, 0);
                        gp.add(Update, 0, 0);
                        //Triangle

                        Polygon triangle = new Polygon();
                        triangle.getPoints().addAll(
                                newCircle.getLayoutX(),newCircle.getLayoutY()-5,
                                newCircle.getLayoutX()-5,newCircle.getLayoutY()-10,
                                newCircle.getLayoutX()+5,newCircle.getLayoutY()-10);
                        triangle.setFill(Color.BLACK);
                        triangle.setStroke(Color.BLACK);
                        pathPane.getChildren().add(triangle);
                        pns.add(triangle);
                        newCircle.setPn(triangle);

                        //if user didn't press cancel or submit...
                        close.setOnAction(event -> {
                                    pathPane.getChildren().remove(triangle);
                                    pathPane.getChildren().remove(sp);
                                    pathPane.getChildren().remove(newCircle);
                                }
                        );

                        Font f = new Font("System", 5);

                        Label idlb = new Label("ID : ");
                        idlb.setFont(f);
                        String idtxt = "";
                        JFXTextField idtf = new JFXTextField(idtxt);
                        idtf.setFont(f);
                        idtf.setAlignment(Pos.BASELINE_LEFT);
                        idtf.setPrefWidth(50);
                        gp.add(idlb, 0, 1);
                        gp.add(idtf, 1, 1);

                        Label lb = new Label("X coordinate : ");
                        lb.setFont(f);
                        String txt = "" + (int) (point.getX()*Map.getImage().getWidth()/childPane.getWidth());
                        JFXTextField tf = new JFXTextField(txt);
                        tf.setFont(f);
                        tf.setAlignment(Pos.BASELINE_LEFT);
                        tf.setPrefWidth(50);
                        gp.add(lb, 0, 2);
                        gp.add(tf, 1, 2);

                        Label lb1 = new Label("Y coordinate : ");
                        lb1.setFont(f);
                        String txt1 = "" + (int)(point.getY()*Map.getImage().getHeight()/childPane.getHeight());
                        JFXTextField tf1 = new JFXTextField(txt1);
                        tf1.setFont(f);
                        tf1.setAlignment(Pos.BASELINE_LEFT);
                        tf1.setPrefWidth(50);
                        gp.add(lb1, 0, 3);
                        gp.add(tf1, 1, 3);

                        Label lb2 = new Label("Floor : ");
                        lb2.setFont(f);
                        String txt2 = "" + floorNum();
                        JFXTextField tf2 = new JFXTextField(txt2);
                        tf2.setDisable(true);
                        tf2.setFont(f);
                        tf2.setAlignment(Pos.BASELINE_LEFT);
                        tf2.setPrefWidth(50);
                        gp.add(lb2, 0, 4);
                        gp.add(tf2, 1, 4);

                        Label lb3 = new Label("Building : ");
                        lb3.setFont(f);
                        String txt3 = "";
                        JFXTextField tf3 = new JFXTextField(txt3);
                        tf3.setFont(f);
                        tf3.setAlignment(Pos.BASELINE_LEFT);
                        tf3.setPrefWidth(50);
                        gp.add(lb3, 0, 5);
                        gp.add(tf3, 1, 5);

                        Label lb4 = new Label("NodeType : ");
                        lb4.setFont(f);
                        String txt4 = "";
                        JFXTextField tf4 = new JFXTextField(txt4);
                        tf4.setFont(f);
                        tf4.setAlignment(Pos.BASELINE_LEFT);
                        tf4.setPrefWidth(50);
                        gp.add(lb4, 0, 6);
                        gp.add(tf4, 1, 6);

                        Label lb5 = new Label("LongName : ");
                        lb5.setFont(f);
                        String txt5 = "";
                        JFXTextField tf5 = new JFXTextField(txt5);
                        tf5.setFont(f);
                        tf5.setAlignment(Pos.BASELINE_LEFT);
                        tf5.setPrefWidth(50);
                        gp.add(lb5, 0, 7);
                        gp.add(tf5, 1, 7);

                        Label lb6 = new Label("ShortName : ");
                        lb6.setFont(f);
                        String txt6 = "";
                        JFXTextField tf6 = new JFXTextField(txt6);
                        tf6.setFont(f);
                        tf6.setAlignment(Pos.BASELINE_LEFT);
                        tf6.setPrefWidth(50);
                        gp.add(lb6, 0, 8);
                        gp.add(tf6, 1, 8);

                        sp.setPrefSize(Control.USE_COMPUTED_SIZE, 140);

                        sp.setContent(gp);
                        sps.add(sp);
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
                            pathPane.getChildren().remove(triangle);
                            eraseNodes();
                            drawNodes();
                        });

                        //sp.getChildren().add(gp);
                        gp.setMargin(close,new Insets(0,0,0,20));
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
                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();
                    if (t.isSecondaryButtonDown()&&t.isShiftDown()) {
                        animationPlayed = false;
                        CircleLocation cl = ((CircleLocation) (t.getSource()));
                        if (cl.getLocation().getNodeType().equals("ELEV") || cl.getLocation().getNodeType().equals("STAI")) {
                            //betweenFloorsCircle = cl;
                            if(!crossEdgeList.contains(cl)){
                                crossEdgeList.add(cl);
                                cl.setStroke(Color.web("BLUE"));
                                cl.setFill(Color.web("BLUE"));
                            }
                            else {
                                crossEdgeList.remove(cl);
                                cl.setStroke(Color.web("RED"));
                                cl.setFill(Color.web("RED"));
                            }
                        }
//                        else {
//                            betweenFloorsCircle = null;
//                        }
                        if(!addEdgeList.contains(cl)){
                            addEdgeList.add(cl);
                            cl.setStroke(Color.web("BLUE"));
                            cl.setFill(Color.web("BLUE"));
                        }
                        else {
                            addEdgeList.remove(cl);
                            cl.setStroke(Color.web("RED"));
                            cl.setFill(Color.web("RED"));
                        }
                        if(crossEdgeList.size() == 2){
                            CircleLocation clStart = crossEdgeList.get(0);
                            CircleLocation clEnd = crossEdgeList.get(1);
                            int startX = clStart.getLocation().getXcoord();
                            int startY = clStart.getLocation().getYcoord();

                            int endX = clEnd.getLocation().getXcoord();
                            int endY = clEnd.getLocation().getYcoord();


                            if(!clStart.getLocation().getNodeType().equals(clEnd.getLocation().getNodeType())&&
                            clStart.getLocation().getFloor().equals(clEnd.getLocation().getFloor())){
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select the same " +
                                        "staircase or elevator",ButtonType.OK);
                                alert.showAndWait();
                                crossEdgeList.clear();
                                clStart.setStroke(Color.web("RED"));
                                clStart.setFill(Color.web("RED"));
                                clEnd.setStroke(Color.web("RED"));
                                clEnd.setFill(Color.web("RED"));
                            }


                            else if(clStart.getLocation().getNodeType().equals(clEnd.getLocation().getNodeType())&&
                                    clStart.getLocation().getFloor().equals(clEnd.getLocation().getFloor())){
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Please select different " +
                                        "floor",ButtonType.OK);
                                alert.showAndWait();
                                crossEdgeList.clear();
                                clStart.setStroke(Color.web("RED"));
                                clStart.setFill(Color.web("RED"));
                                clEnd.setStroke(Color.web("RED"));
                                clEnd.setFill(Color.web("RED"));
                            }

                            else if( (startX <= (endX + 50)) && (startX >= (endX - 50)) &&
                                    (startY <= (endY + 50)) && (startY >= (endY - 50)) ){
                                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Add edges between"+
                                        clStart.getLocation().getLongName() + " and " + clEnd.getLocation().getLongName()
                                        + " successfully" ,ButtonType.OK);
                                alert.showAndWait();
                                Edge e = new Edge(clStart.getLocation().getLocID()+"_"+
                                        clEnd.getLocation().getLocID(), clStart.getLocation(), clEnd.getLocation());
                                single.addEdge(clStart.getLocation(),clEnd.getLocation(),e);
                                ea =new EdgesAccess();
                                ea.addEdge(clStart.getLocation().getLocID(), clEnd.getLocation().getLocID());


                                addEdgeList.clear();
                                crossEdgeList.clear();
                                eraseNodes();
                                drawNodes();

                            }
                            else{
                                System.out.println("cel " + crossEdgeList);
                                crossEdgeList.clear();
                                System.out.println("Oh no 1");
                                clStart.setStroke(Color.web("RED"));
                                clStart.setFill(Color.web("RED"));
                                clEnd.setStroke(Color.web("RED"));
                                clEnd.setFill(Color.web("RED"));
                            }

                        }

                        else if(addEdgeList.size() == 2){
                            CircleLocation clStart = addEdgeList.get(0);
                            CircleLocation clEnd = addEdgeList.get(1);

                            ea =new EdgesAccess();
                            ea.addEdge(clStart.getLocation().getLocID(), clEnd.getLocation().getLocID());
                            Edge e = new Edge(clStart.getLocation().getLocID()+"_"+
                                    clEnd.getLocation().getLocID(), clStart.getLocation(), clEnd.getLocation());
                            single.addEdge(clStart.getLocation(),clEnd.getLocation(),e);

                            CircleLocation endcl = new CircleLocation();
                            LineEdge line = new LineEdge();

                            line.setStartX(e.getEndNode().getXcoord() * childPane.getWidth() / Map.getImage().getWidth());
                            line.setStartY(e.getEndNode().getYcoord() * childPane.getHeight() / Map.getImage().getHeight());
                            line.setEndX(e.getStartNode().getXcoord() * childPane.getWidth() / Map.getImage().getWidth());
                            line.setEndY(e.getStartNode().getYcoord() * childPane.getHeight() / Map.getImage().getHeight());

                            line.startXProperty().bind(clStart.centerXProperty());
                            line.startYProperty().bind(clStart.centerYProperty());
                            line.endXProperty().bind(clEnd.centerXProperty());
                            line.endYProperty().bind(clEnd.centerYProperty());
                            line.setE(e);
                            clStart.getLineList().add(line);
                            clEnd.getLineList().add(line);
                            pathPane.getChildren().add(line);
                            lines.add(line);
                            line.toBack();

                            addEdgeList.remove(clStart);
                            addEdgeList.remove(clEnd);
                            crossEdgeList.clear();
                            clStart.setStroke(Color.web("RED"));
                            clStart.setFill(Color.web("RED"));
                            clEnd.setStroke(Color.web("RED"));
                            clEnd.setFill(Color.web("RED"));
                            //eraseNodes();
                            //drawNodes();
                        }
                        else{
                            System.out.println("Oh no");
                        }

                    }

                    else if(t.isAltDown()){
                        animationPlayed = false;
                        single = Singleton.getInstance();
                        single.setLastTime();
                        focusNode = ((CircleLocation) (t.getSource())).getLocation();
                        betweenFloorsCircle = null;

                        //this is the dialogue popup
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete node "+
                                ((CircleLocation) (t.getSource())).getLocation().toString()+ "?", ButtonType.YES, ButtonType.NO);
                        alert.showAndWait();
                        single.setLastTime();
                        if (alert.getResult() == ButtonType.YES) {
                            pathPane.getChildren().remove(((CircleLocation) (t.getSource())).getSp());
                            single.lookup.get(focusNode.getLocID()).restitch();
                            //delete the node here
                            na = new NodesAccess();
                            single.deleteNode(focusNode);
                            na.deleteNode(focusNode.getLocID());
                            eraseNodes();
                            drawNodes();

                        }
                        else if (alert.getResult() == ButtonType.NO) {
                            //do nothing
                        }


                    }

                    else if(t.isShiftDown()&& !(t.isSecondaryButtonDown())){
                        animationPlayed = false;
                        CircleLocation loc = (CircleLocation) (t.getSource());
                        betweenFloorsCircle = null;
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
                                System.out.println("loc " + loc + "\n Edges " + edgeslist);
                                CircleLocation endcl = new CircleLocation();
                                for (Edge e: edgeslist){
                                    LineEdge line = new LineEdge();
                                    for(int i = 0; i< circles.size(); i++){
                                        String ID = circles.get(i).getLocation().getLocID();
                                        if(ID.equals(e.getEndID())||ID.equals(e.getStartID())&&
                                                !(ID.equals(loc.getLocation().getLocID()))){
                                            endcl = circles.get(i);
                                        }
                                    }
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
                                        line.setE(e);
                                        loc.getLineList().add(line);
                                        pathPane.getChildren().add(line);
                                        lines.add(line);
                                        line.toBack();

                                        line.setOnMousePressed(lineOnMousePressedEventHandler);
//                                        System.out.println("Lines " + line);
//                                        System.out.println("edge" + line.getE());

//                                        loc.getLineList().add(line);
//                                        pathPane.getChildren().add(line);
//                                        lines.add(line);
                                    }

                                }
                            }
                        }
                    }

                    else if (!t.isShiftDown()&&!t.isAltDown()&& !t.isSecondaryButtonDown() && !t.isControlDown()){
                        CircleLocation toCenterOn = ((CircleLocation) (t.getSource()));
                        Point2D point = new Point2D(toCenterOn.getCenterX(), toCenterOn.getCenterY()-30);
                        if(toCenterOn.getCenterX()>=50 &&toCenterOn.getCenterX()<=970 &&
                                toCenterOn.getCenterY()<=600&& toCenterOn.getCenterY()>=150){

                            gesturePane.zoomTo(3.0, point);
                            Duration d = new Duration(500);
                            GesturePane.AnimationInterpolatorBuilder animate = gesturePane.animate(d);
                            animate.centreOn(point);
                        }

                        //animationPlayed = true;
                        betweenFloorsCircle = null;
                        ((Circle)(t.getSource())).setFill(Color.web("GREEN"));


                        orgSceneX = t.getSceneX();
                        orgSceneY = t.getSceneY();

                        CircleLocation c = (CircleLocation) (t.getSource());
                        c.toFront();
                        if (c.getSp() == null) {
                            if (!(lastCircle==null)){
                                pathPane.getChildren().remove(lastCircle.getSp());
                                pathPane.getChildren().remove(lastCircle.getPn());
                                lastCircle.setFill(Color.web("RED"));
                                lastCircle.setSp(null);
                            }
                            ScrollPane sp = new ScrollPane();
                            sp.getStylesheets().add("MapBuilderScrollPane.css");
                            if(c.getCenterX()<=50){
                                sp.setLayoutX(c.getCenterX());
                            }
                            else if(c.getCenterX()>=970){
                                sp.setLayoutX(c.getCenterX()-100);
                            }
                            else {
                                sp.setLayoutX(c.getCenterX() - 50);
                            }

                            if(c.getCenterY()<=150){
                                sp.setLayoutY(c.getCenterY());
                            }
                            else if(c.getCenterY()>=600){
                                sp.setLayoutY(c.getCenterY()- 130);
                            }
                            else{
                                sp.setLayoutY(c.getCenterY()- 130);
                            }

                            GridPane gp = new GridPane();



                            JFXButton close = new JFXButton("\u274E");
                            close.setPrefWidth(40);

                            JFXButton Update = new JFXButton("\u2705");
                            Update.setPrefWidth(40);

                            Update.setStyle("-fx-border-color: green; -fx-border-width: 1px;");
                            close.setStyle("-fx-border-color: red; -fx-border-width: 1px;");




                            gp.add(close,1,0);
                            gp.add(Update,0,0);

                            //Triangle

                            Polygon triangle = new Polygon();
                            triangle.getPoints().addAll(
                                    c.getCenterX(),c.getCenterY()-5,
                                    c.getCenterX()-5,c.getCenterY()-10,
                                    c.getCenterX()+5,c.getCenterY()-10);
                            triangle.setFill(Color.BLACK);
                            triangle.setStroke(Color.BLACK);
                            pathPane.getChildren().add(triangle);
                            pns.add(triangle);
                            c.setPn(triangle);


                            //if user didn't press cancel or submit...


                            close.setOnAction(event -> {
                                        pathPane.getChildren().remove(sp);
                                        pathPane.getChildren().remove(triangle);
                                        c.setSp(null);
                                        //((Circle) (t.getSource())).setStroke(Color.web("RED"));
                                        ((Circle) (t.getSource())).setFill(Color.web("RED"));
                                        eraseNodes();
                                        drawNodes();
                                    }
                            );

                            Font f = new Font("System", 5);

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
                                pathPane.getChildren().remove(triangle);
                                c.setSp(null);
                                c.setPn(null);
                                //((Circle) (t.getSource())).setStroke(Color.web("RED"));
                                ((Circle) (t.getSource())).setFill(Color.web("RED"));
                                lastCircle = null;
                            });

                            double X = ((Circle) (t.getSource())).getCenterX() - 50;
                            double Y = ((Circle) (t.getSource())).getCenterY() - 128;

                            //sp.setLayoutX(X);
                            //sp.setLayoutY(Y);
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

                    else{
                        //animationPlayed = false;
                        System.out.println("FALLTHROUGH CASE");
                        betweenFloorsCircle = null;
                    }

                    if (!(lastCircle == null) && !(t.isShiftDown())) {
                        if (!(((CircleLocation) (t.getSource())).equals(lastCircle))) {
                            pathPane.getChildren().remove(lastCircle.getSp());
                            pathPane.getChildren().remove(lastCircle.getPn());
                            lastCircle.setSp(null);
                            lastCircle.setPn(null);
                            lastCircle.setFill(Color.web("RED"));
                            lastCircle.setCenterX(lastCircle.getLocation().getXcoord() * childPane.getWidth() / Map.getImage().getWidth());
                            lastCircle.setCenterY(lastCircle.getLocation().getYcoord() * childPane.getHeight() / Map.getImage().getHeight());
                        }
                    }
                    t.consume();

                }

            };

//  Larry - delete the edges when alt right click on the line
    EventHandler<MouseEvent> lineOnMousePressedEventHandler =
            new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent t) {
                    if(t.isAltDown()){
                        LineEdge l = (LineEdge) (t.getSource());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete Edge ?",
                                ButtonType.YES, ButtonType.NO);
                        alert.showAndWait();
                        if (alert.getResult() == ButtonType.YES) {
                        single.deleteEdge(l.getE());
                        ea = new EdgesAccess();
                        ea.deleteEdge(l.getE().getEdgeID());
                        pathPane.getChildren().remove(l);
                        eraseNodes();
                        drawNodes();
                        }
                        else if(alert.getResult() == ButtonType.NO){
                            //nothing
                        }
                    }
                }
    };

    //Larry - button press to show all the edges
    public void buttonShowEdges(){
        if(showEdge == false){
            showEdge = true;

            Singleton single = Singleton.getInstance();
            single.setLastTime();
            ArrayList<Location> nodes = new ArrayList<Location>();
            ArrayList<Edge> edgeslist = new ArrayList<>();
            for (int i = 0; i < single.getData().size(); i++) {
                if (single.getData().get(i).getFloor().equals(floorNum())/* current Map floor*/) {
                    nodes.add(single.getData().get(i));
                }
            }

            for(Location l : nodes){
                for(int i =0; i < l.getEdges().size(); i++){
                    if(!edgeslist.contains(l.getEdges().get(i))&&l.getEdges().get(i).getStartNode().getFloor().equals(floorNum())
                            &&l.getEdges().get(i).getEndNode().getFloor().equals(floorNum())){
                        edgeslist.add(l.getEdges().get(i));
                    }
                }
            }

            for (Edge e: edgeslist){

                LineEdge line = new LineEdge();
                line.setStartX(e.getEndNode().getXcoord() * childPane.getWidth() / Map.getImage().getWidth());
                line.setStartY(e.getEndNode().getYcoord() * childPane.getHeight() / Map.getImage().getHeight());
                line.setEndX(e.getStartNode().getXcoord() * childPane.getWidth() / Map.getImage().getWidth());
                line.setEndY(e.getStartNode().getYcoord() * childPane.getHeight() / Map.getImage().getHeight());


                line.setE(e);
                pathPane.getChildren().add(line);
                lines.add(line);
                showedges.add(line);
                line.toBack();

                line.setOnMousePressed(lineOnMousePressedEventHandler);
            }
        }
        else{
            showEdge = false;
            for(Line l : showedges){
                pathPane.getChildren().remove(l);
            }
            for(Line l : lines){
                pathPane.getChildren().remove(l);
            }
            lines.clear();
            showedges.clear();

            }
        }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
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
    private void goHome(ActionEvent event) throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        saveState();
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    /**@author Nathan
     * Saves the memento state
     */
    private void saveState(){
        Singleton single = Singleton.getInstance();
        single.saveMemento("EditLocation.fxml");
    }
}



