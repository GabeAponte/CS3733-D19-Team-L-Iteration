package Controller;

import Access.EdgesAccess;
import Access.NodesAccess;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Object.*;

public class EditLinkBetweenFloorsController {

    @FXML
    private Stage thestage;

    @FXML
    private Button PathFindBack;

    @FXML
    private Button PathFindSubmit;

    @FXML
    private ComboBox<String> Filter;

    @FXML
    private ComboBox<String> Floor;

    @FXML
    private AnchorPane anchorPaneWindow;

    @FXML
    private ImageView Map;

    @FXML
    private ImageView MapUpper;

    @FXML
    private ImageView MapLower;


    private final DoubleProperty zoomPropertyMain = new SimpleDoubleProperty(1.0d);
    private final DoubleProperty deltaYMain = new SimpleDoubleProperty(0.0d);

    private final DoubleProperty zoomPropertyLower = new SimpleDoubleProperty(1.0d);
    private final DoubleProperty deltaYLower = new SimpleDoubleProperty(0.0d);

    private final DoubleProperty zoomPropertyUpper = new SimpleDoubleProperty(1.0d);
    private final DoubleProperty deltaYUpper = new SimpleDoubleProperty(0.0d);

    private HashMap<String, String> mapURLlookup = new HashMap<String, String>();

    private NodesAccess na;
    private EdgesAccess ea;

    private final ObservableList<String> filterList = FXCollections.observableArrayList();
    private final ObservableList<String> floorList = FXCollections.observableArrayList();
    private Singleton single = Singleton.getInstance();


    private ArrayList<String> mapURLs = new ArrayList<String>();
    private ArrayList<Circle> circles = new ArrayList<Circle>();
    private ArrayList<Line> lines = new ArrayList<Line>();

    String pickedFloor = "test";
    String type = "test";
    String type2 = "";

    String currentMap = "";

    public void initialize() {
        Singleton single = Singleton.getInstance();
        na = new NodesAccess();
        ea = new EdgesAccess();
        PathFindSubmit.setDisable(false);
        filter();
        floor();
        Filter.setItems(filterList);
        Floor.setItems(floorList);
        map();
        //initializeTable(na, ea);
        setUpImage(Map, zoomPropertyMain, deltaYMain, 431, 115);
        setUpImage(MapUpper, zoomPropertyUpper, deltaYUpper, 830, 417);
        setUpImage(MapLower, zoomPropertyLower, deltaYLower, 25, 417);

        System.out.println("NO CRASH??");
    }

    @FXML
    /**
     * @author Gabe
     * adds all the URLs to the list, starts with 3rd floor since that is the next floor to come
     */
    public void map(){
        mapURLlookup.put("3", "/SoftEng_UI_Mockup_Pics/03_thethirdfloor.png");
        mapURLlookup.put("2", "/SoftEng_UI_Mockup_Pics/02_thesecondfloor.png");
        mapURLlookup.put("1", "/SoftEng_UI_Mockup_Pics/01_thefirstfloor.png");
        mapURLlookup.put("Ground", "/SoftEng_UI_Mockup_Pics/00_thegroundfloor.png");
        mapURLlookup.put("L1", "/SoftEng_UI_Mockup_Pics/00_thelowerlevel1.png");
        mapURLlookup.put("L2", "/SoftEng_UI_Mockup_Pics/00_thelowerlevel2.png");
    }

    @FXML
    private void backPressed() throws IOException {
        Singleton single = Singleton.getInstance();
        thestage = (Stage) PathFindBack.getScene().getWindow();
        AnchorPane root;
        if(single.isLoggedIn()) {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));

            Parent sceneMain = loader.load();

            LoggedInHomeController controller = loader.<LoggedInHomeController>getController();

            Stage theStage = (Stage) PathFindBack.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);
            return;
        } else {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("HospitalHome.fxml"));
        }
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    public HashMap<String, Location> getLookup() {
        return single.lookup;
    }

    @FXML
    private void submitPressed(){
        System.out.println("SUBMIT WORKS");
    }

    @FXML
    /**
     * @author Gabe
     * reads the input for the room type combo box
     */
    private void filterType() {
        if (Filter.getValue() == ("Stairs")) {
            type = "STAI";
        }
        if (Filter.getValue() == ("Elevators")) {
            type = "ELEV";
        }
    }

    @FXML
    private void floor() {
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
        filterList.add("Stairs");
        filterList.add("Elevators");
    }

    @FXML
    /**
     * @author Gabe
     * reads the input for the floor combo box
     */
    private void filterFloor() {
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

    /*
    Author: PJ Mara. Sets up a pan and scroll image!
     */
    private void setUpImage(ImageView Map1, DoubleProperty zoomProperty, DoubleProperty deltaY, int x, int y) {
        AnchorPane anchorPanePath = new AnchorPane();
        anchorPanePath.setLayoutX(x);
        anchorPanePath.setLayoutY(y);
        anchorPanePath.setPrefSize(446,280);
        Rectangle clip = new Rectangle();
        //clip.widthProperty().bind(Map1.fitWidthProperty());
        //clip.heightProperty().bind(Map1.fitHeightProperty());
        clip.setX(0);
        clip.setY(0);
        clip.setWidth(Map1.getFitWidth());
        clip.setHeight(Map1.getFitHeight());

        anchorPanePath.setClip(clip);

        PanAndZoomPane zoomPaneImage = new PanAndZoomPane();

        zoomProperty.bind(zoomPaneImage.myScale);
        deltaY.bind(zoomPaneImage.deltaY);
        zoomPaneImage.getChildren().add(Map1);

        SceneGestures sceneGestures = new SceneGestures(zoomPaneImage, Map1);
        anchorPanePath.addEventFilter( MouseEvent.MOUSE_CLICKED, sceneGestures.getOnMouseClickedEventHandler());
        anchorPanePath.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        anchorPanePath.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        anchorPanePath.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());
        zoomPaneImage.setLayoutX(x);
        zoomPaneImage.setLayoutY(y);

        anchorPaneWindow.getChildren().add(zoomPaneImage);
        anchorPaneWindow.getChildren().add(anchorPanePath);
        sceneGestures.reset(Map1, Map1.getImage().getWidth(), Map1.getImage().getHeight());
    }

    @FXML
    private void typeSelected() {

    }

    @FXML
    private void floorSelected() {
        if (Floor.getValue().equals("3")) {
            Map.setImage(new Image(mapURLlookup.get("3")));
            MapUpper.setImage(null);
            MapLower.setImage(new Image(mapURLlookup.get("2")));
        }
        else if (Floor.getValue().equals("2")) {
            Map.setImage(new Image(mapURLlookup.get("2")));
            MapUpper.setImage(new Image(mapURLlookup.get("3")));
            MapLower.setImage(new Image(mapURLlookup.get("1")));
        }
        else if (Floor.getValue().equals("1")) {
            Map.setImage(new Image(mapURLlookup.get("1")));
            MapUpper.setImage(new Image(mapURLlookup.get("2")));
            MapLower.setImage(new Image(mapURLlookup.get("Ground")));
        }
        else if (Floor.getValue().equals("Ground")) {
            Map.setImage(new Image(mapURLlookup.get("Ground")));
            MapUpper.setImage(new Image(mapURLlookup.get("1")));
            MapLower.setImage(new Image(mapURLlookup.get("L1")));
        }
        else if (Floor.getValue().equals("L1")) {
            Map.setImage(new Image(mapURLlookup.get("L1")));
            MapUpper.setImage(new Image(mapURLlookup.get("Ground")));
            MapLower.setImage(new Image(mapURLlookup.get("L2")));
        }
        else {
            Map.setImage(new Image(mapURLlookup.get("L2")));
            MapUpper.setImage(new Image(mapURLlookup.get("L1")));
            MapLower.setImage(null);
        }
    }
}
