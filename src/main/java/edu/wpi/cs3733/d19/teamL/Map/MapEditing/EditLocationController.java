package edu.wpi.cs3733.d19.teamL.Map.MapEditing;

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

    private ArrayList<String> mapURLs = new ArrayList<String>();
    private ArrayList<CircleLocation> circles = new ArrayList<CircleLocation>();

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
        Singleton single = Singleton.getInstance();
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

                            Circle c = (Circle) (t.getSource());

                            c.setCenterX(c.getCenterX() + offsetX);
                            c.setCenterY(c.getCenterY() + offsetY);

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
                    ((Circle)(t.getSource())).setStroke(Color.web("GREEN"));
                    ((Circle)(t.getSource())).setFill(Color.web("GREEN"));

                    orgSceneX = t.getSceneX();
                    orgSceneY = t.getSceneY();

                    Circle c = (Circle) (t.getSource());
                    c.toFront();

                    ScrollPane sp = new ScrollPane();
                    sp.setLayoutX(c.getLayoutX());
                    sp.setLayoutY(c.getLayoutY());
                    GridPane gp = new GridPane();
                    Button close = new Button("Close");
                    close.setPrefWidth(50);
                    close.setOnAction(event-> {
                        pathPane.getChildren().remove(sp);
                        ((Circle)(t.getSource())).setStroke(Color.web("RED"));
                        ((Circle)(t.getSource())).setFill(Color.web("RED"));}
                        );

                    Label lb = new Label("X coordinate : ");
                    String txt = "" + ((CircleLocation)(t.getSource())).getLocation().getXcoord();
                    TextField tf = new TextField(txt);
                    tf.setPrefWidth(100);
                    gp.add(lb,0,0);
                    gp.add(tf,1,0);

                    Label lb1 = new Label("Y coordinate : ");
                    String txt1 = "" + ((CircleLocation)(t.getSource())).getLocation().getYcoord();
                    TextField tf1 = new TextField(txt1);
                    tf1.setPrefWidth(100);
                    gp.add(lb1,0,1);
                    gp.add(tf1,1,1);

                    Label lb2 = new Label("Floor : ");
                    String txt2 = "" + ((CircleLocation)(t.getSource())).getLocation().getFloor();
                    TextField tf2 = new TextField(txt2);
                    tf2.setPrefWidth(100);
                    gp.add(lb2,0,2);
                    gp.add(tf2,1,2);

                    Label lb3 = new Label("Building : ");
                    String txt3 = "" + ((CircleLocation)(t.getSource())).getLocation().getBuilding();
                    TextField tf3 = new TextField(txt3);
                    tf3.setPrefWidth(100);
                    gp.add(lb3,0,3);
                    gp.add(tf3,1,3);

                    Label lb4 = new Label("NodeType : ");
                    String txt4 = "" + ((CircleLocation)(t.getSource())).getLocation().getNodeType();
                    TextField tf4 = new TextField(txt4);
                    tf4.setPrefWidth(100);
                    gp.add(lb4,0,4);
                    gp.add(tf4,1,4);

                    Label lb5 = new Label("LongName : ");
                    String txt5 = "" + ((CircleLocation)(t.getSource())).getLocation().getLongName();
                    TextField tf5 = new TextField(txt5);
                    tf5.setPrefWidth(100);
                    gp.add(lb5,0,5);
                    gp.add(tf5,1,5);

                    Label lb6 = new Label("ShortName : ");
                    String txt6 = "" + ((CircleLocation)(t.getSource())).getLocation().getShortName();
                    TextField tf6 = new TextField(txt6);
                    tf6.setPrefWidth(100);
                    gp.add(lb6,0,6);
                    gp.add(tf6,1,6);

                    gp.add(close,1,7);


                    sp.setVmax(440);
                    sp.setPrefSize(200, 150);


                    sp.setLayoutX(((Circle)(t.getSource())).getCenterX());
                    sp.setLayoutY(((Circle)(t.getSource())).getCenterY());
                    sp.setContent(gp);

                    pathPane.getChildren().add(sp);


                    System.out.println("Xcor "+ ((Circle)(t.getSource())).getCenterX());
                    System.out.println("Ycor "+ ((Circle)(t.getSource())).getCenterY());
                    System.out.println("successful scroll pane");


                }
            };

}
