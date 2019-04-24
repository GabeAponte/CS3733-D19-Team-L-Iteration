package edu.wpi.cs3733.d19.teamL.Map.Pathfinding;

import com.jfoenix.controls.*;
import edu.wpi.cs3733.d19.teamL.Account.EmployeeAccess;
import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Path;
import edu.wpi.cs3733.d19.teamL.SearchingAlgorithms.*;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.ResourceBundle;

import static java.lang.Math.sqrt;
import static javafx.scene.paint.Color.DODGERBLUE;
import static javafx.scene.paint.Color.RED;

@SuppressWarnings("Duplicates")
public class EmergencyController {
    @FXML
    private Stage thestage;

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
    private Button F4;

    @FXML
    private JFXTextField adminUser;

    @FXML
    private JFXPasswordField adminPassword;

    @FXML
    private JFXButton AdminRemoveEmerg;

    @FXML
    private JFXButton DisableEmergMode;

    @FXML
    private Label LongNameOfExit;

    @FXML
    private Label errorLabel;

    @FXML
    private JFXTextArea direction;

    @FXML
    private GridPane gridPane;

    @FXML
    private ImageView Map;

    @FXML
    private Pane imagePane;

    @FXML
    private Label thisMap;

    @FXML
    private HBox vBottom;

    @FXML
    private VBox vLeft;

    @FXML
    private JFXToolbar toolbar;

    Location kioskTemp;

    private boolean displayingPath;
    //Arraylist for the buttons that generate on path
    private ArrayList<Button> buttons = new ArrayList<Button>();
    //Arraylist of floor buttons
    private ArrayList<Button> floorButtons = new ArrayList<Button>();
    private int count;

    private Path path = null;

    private GesturePane gesturePane;
    private StackPane childPane;
    private AnchorPane pathPane;

    private NodesAccess na;
    private EdgesAccess ea;
    private HashMap<String, Location> nameToLoc = new HashMap<>();;

    private final ObservableList<String> floorList = FXCollections.observableArrayList();
    private Singleton single = Singleton.getInstance();


    private ArrayList<String> mapURLs = new ArrayList<String>();
    private ArrayList<Circle> circles = new ArrayList<Circle>();
    private ArrayList<Line> lines = new ArrayList<Line>();

    private String currentMap = "G"; //defaults to floor G

    @FXML
    private AnchorPane navList;





    @FXML
    private void DisableEmergModePress(ActionEvent event) throws IOException {
        //TODO: this v
        //get adminUser.getText() or something like that idk
        // & get adminPassword.getWhatever()
        //check if admin

        String uname = adminUser.getText();
        String pass = adminPassword.getText();
        if(!(uname.isEmpty() && pass.isEmpty())){
            DisableEmergMode.setVisible(true);
        }

        boolean validLogin;
        Singleton single = Singleton.getInstance();
        single.setLastTime();

        EmployeeAccess ea = new EmployeeAccess();
        validLogin = ea.checkEmployee(uname, pass);
        if(validLogin){
            single.setLoggedIn(true);
            single.setUsername(uname);
            single.setIsAdmin(false);
            if(ea.getEmployeeInformation(uname).get(2).equals("true")){
                single.setIsAdmin(true);
                Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("HospitalPathFinding.fxml"));
                ((Node) event.getSource()).getScene().setRoot(newPage);
            }
            //do not do anything unless admin logs in correctly
            //SwitchToSignedIn("EmployeeLoggedInHome.fxml");
        } else {
            displayError();
        }

        //switch scenes to normal pathfind
    }

    private void displayError(){
        errorLabel.setText("Login is incorrect. Cannot disable emergency mode.");
    }

    private void displayExits(){
        ArrayList<Location> nodes = new ArrayList<Location>();
        //want to fill nodes w/ floor = currrentKioskFloor && nodeLongName? or nodeType? .contains(keyword)
        int temp = 0;

        for(int i=0; i<single.getData().size(); i++){
            //if nodetype contains keyword
            //System.out.println(currentMap);

            if(single.getData().get(i).getNodeType().contains("EXIT") && single.getData().get(i).getFloor().equals(currentMap)/* && data.get(i).getFloor() == kioskNode.getFloor*/){
                nodes.add(single.getData().get(i));

                //System.out.println("ok at least one node gets here");

                Circle thisCircle = new Circle();

                pathPane.getChildren().add(thisCircle);

                //Setting the properties of the circle
                thisCircle.setCenterX((nodes.get(temp).getXcoord())*gesturePane.getWidth() / Map.getImage().getWidth());
                thisCircle.setCenterY((nodes.get(temp).getYcoord())*gesturePane.getHeight() / Map.getImage().getHeight());
                thisCircle.setRadius(Math.max(3.5,3.5f*(gesturePane.getCurrentScale()/5)));
                thisCircle.setStroke(Color.web("RED"));
                thisCircle.setFill(Color.web("RED"));

                circles.add(thisCircle);
                temp++;
            }
        }
    }


    private void cleanMap(){
        for (Circle c: circles) {
            pathPane.getChildren().remove(c);
        }
        for (Line l: lines) {
            pathPane.getChildren().remove(l);
        }
        cleanLabel();
    }

    private void findClosestExit(){
        checkAndSetKiosk();

        ArrayList<Location> nodes = new ArrayList<Location>();
        //want to fill nodes w/ relevent POI

        for(int i=0; i<single.getData().size(); i++) {
            //if nodetype contains keyword
            if (single.getData().get(i).getNodeType().contains("EXIT") && single.getData().get(i).getFloor().equals(kioskTemp.getFloor())) {
                nodes.add(single.getData().get(i));
                //System.out.println("node added");


            }
        }
        //if there are no nodes, fill with all exits on differnet floors
        if(nodes.isEmpty()){
            System.out.println("nodes are empty???");

            for(int i=0; i<single.getData().size(); i++) {
                //if nodetype contains keyword
                if (single.getData().get(i).getNodeType().contains("EXIT")) {
                    nodes.add(single.getData().get(i));
                    //System.out.println("node added");


                }
            }
        }
        //nodes are now never empty
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
                //System.out.println("location: "+nodes.get(i).getLongName()+"  smallest distance: "+length);

                if(length < smallestDistance){
                    smallestDistance = length;
                    //System.out.println("location: "+nodes.get(i).getLongName()+"  smallest distance: "+smallestDistance);
                    //set this node to be for pathing
                    closestLOC = nodes.get(i);
                    //restricts elevators here
                    closestPath = findAbstractPath(astar,kioskTemp, closestLOC, "ELEV");

                    count = i;
                }
            }

            displayingPath = true;

            startNode = kioskTemp;
            endNode = closestLOC;
            path = closestPath;


            //set the closest location label here!
            LongNameOfExit.setText(closestLOC.getLongName());

            //TODO: dispaly path doesnt wanna work
            displayPath();
            //printPath(path.getPath());
            direction.setText(printPath(path.getPath()));

        }

    }


    private void activateEmergencyMode(){
        //GOTO KIOSKFLOOR
        gotoKioskFloor();
        //does displaykiosk
        //System.out.println(kioskTemp.getFloor());


        //DISPLAY ALL EXITS
        //DISPLAYS ALL EXITS HERE
        displayExits();

        //FINDS CLOSEST EXIT
        findClosestExit();
        //also sets the direction setTetxt stuff
        //sets the path

        if(path != null  &&  currentMap.equals(kioskTemp.getFloor())) {
            displayingPath = true;
            displayPath();
        }
        gotoKioskFloor();
        displayExits();
        DisableEmergMode.setDisable(false);
    }





    private void gotoKioskFloor(){
        checkAndSetKiosk();

        //set current floor to the kiosk floor
        currentMap = kioskTemp.getFloor();
        changeMapLabel();
        if(currentMap.equals("L2")){
            clickedL2();
        } else if(currentMap.equals("L1")){
            clickedL1();
        } else if(currentMap.equals("G")){
            clickedG();
        } else if(currentMap.equals("1")){
            clicked1();
        } else if(currentMap.equals("2")){
            clicked2();
        } else if(currentMap.equals("3")){
            clicked3();
        }

        displayKiosk();

    }

    private void cleanLabel(){
        hereLabel.setVisible(false);
        endLabel.setVisible(false);
        startLabel.setVisible(false);
    }


    @FXML
    private void clickedG(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thegroundfloor.png"));
        currentMap = "G";
        changeMapLabel();
        cleanMap();
        if(kioskTemp.getFloor().equals(currentMap)){
            displayKiosk();
        }
        if(path != null){
            displayingPath = true;
            displayPath();
        }
        displayExits();

    }
    @FXML
    private void clickedL1() {
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel1.png"));
        currentMap = "L1";
        changeMapLabel();
        cleanMap();
        if(kioskTemp.getFloor().equals(currentMap)){
            displayKiosk();
        }
        if(path != null){
            displayingPath = true;
            displayPath();
        }
        displayExits();
    }
    @FXML
    public void clickedL2(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel2.png"));
        currentMap = "L2";
        changeMapLabel();
        cleanMap();
        if(kioskTemp.getFloor().equals(currentMap)){
            displayKiosk();
        }
        if(path != null){
            displayingPath = true;
            displayPath();
        }
        displayExits();
    }
    @FXML
    private void clicked1(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/01_thefirstfloor.png"));
        currentMap = "1";
        changeMapLabel();
        cleanMap();
        if(kioskTemp.getFloor().equals(currentMap)){
            displayKiosk();
        }
        if(path != null){
            displayingPath = true;
            displayPath();
        }
        displayExits();
    }
    @FXML
    private void clicked2(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/02_thesecondfloor.png"));
        currentMap = "2";
        changeMapLabel();
        cleanMap();
        if(kioskTemp.getFloor().equals(currentMap)){
            displayKiosk();
        }
        if(path != null){
            displayingPath = true;
            displayPath();
        }
        displayExits();
    }
    @FXML
    private void clicked3(){
        Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/03_thethirdfloor.png"));
        currentMap = "3";
        changeMapLabel();
        cleanMap();
        if(kioskTemp.getFloor().equals(currentMap)){
            displayKiosk();
        }
        if(path != null){
            displayingPath = true;
            displayPath();
        }
        displayExits();
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



    public void initialize(URL url, ResourceBundle rb) {
        this.prepareSlideMenuAnimation();
    }



    public void initialize() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), toolbar);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();

        na = new NodesAccess();
        ea = new EdgesAccess();

        ObservableList<PathfindingStrategy> strategies = FXCollections.observableArrayList();
        ObservableList<String> preference = FXCollections.observableArrayList();


//        strategyAlgorithm = strategySelector.getValue();
        direction.setEditable(false);


        changeMapLabel();

        floor();
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


                           //direction.setText("");
                        };

                        ((Stage) newWindow).widthProperty().addListener(stageSizeListener);
                        ((Stage) newWindow).heightProperty().addListener(stageSizeListener);
                    }
                });
            }
        });

        Map.fitHeightProperty().bind(gesturePane.heightProperty());
        Map.fitWidthProperty().bind(gesturePane.widthProperty());
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
        direction.toFront();
        startLabel = new Label();
        endLabel = new Label();
        hereLabel = new Label();
        //Adds the text to the screen
        pathPane.getChildren().add(startLabel);
        pathPane.getChildren().add(endLabel);
        pathPane.getChildren().add(hereLabel);


        nameToLoc.clear();

        //Code to immediately set kiosk
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                displayKiosk();
                startNode = kioskTemp;

                activateEmergencyMode();
                displayPath();
            }
        });
    }



    Label startLabel;
    Label endLabel;
    Label hereLabel;
    Location startNode;
    Location endNode;

    /**
     * @author: Nikhil: Displays the kiosk location automatically on the path navigation screen.
     */
    public void displayKiosk() {
        checkAndSetKiosk();
        Circle kiosk = new Circle();
        kiosk.setCenterX(kioskTemp.getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
        kiosk.setCenterY(kioskTemp.getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
        kiosk.setRadius(Math.max(3.5, 3.5f * (gesturePane.getCurrentScale() / 4)));
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
    private void AdminRemoveEmergPress(){
        errorLabel.setText("");
        prepareSlideMenuAnimation();
    }

    @FXML
    private void prepareSlideMenuAnimation() {
        TranslateTransition openNav = new TranslateTransition(new Duration(300.0D), this.navList);
        openNav.setToX(0.0D);
        TranslateTransition closeNav = new TranslateTransition(new Duration(5000.0D), this.navList);
        this.AdminRemoveEmerg.setOnAction((evt) -> {
            if (this.navList.getTranslateX() != 130.0D) {
                openNav.setToX(130);
                openNav.play();
            } else {
                closeNav.setToX(-this.navList.getWidth());
                closeNav.play();
            }

        });

        this.AdminRemoveEmerg.setOnAction((evt) -> {
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
    public void displayPath(){
        single.setLastTime();

        //Add an arraylist to keep track of what floors you are visiting
        ArrayList<String> floorsVisited = new ArrayList<>();

        if(displayingPath) {
            path.getPath().add(0,startNode);
            start = 0;
            counter = 0;

            //Clears the lines and circles to avoid any duplicates or reproducing data
            clear();

            //Used to check for first switch
            boolean found = false;
            //End node on floor
            int floorSwitch = path.getPath().size()-1;
            //Creates the path for the user to follow, and displays based on the current floor.
            for (int i = 0; i < path.getPath().size() - 1; i++) {
                floorsVisited.add(path.getPath().get(i).getFloor());
                if((!(path.getPath().get(i + 1).getFloor().equals(currentMap))) && !found) {
                    floorSwitch = i;
                    found = true;
                }
            }
            if(floorsVisited.contains(currentMap)) {
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
        double x = gesturePane.getWidth()/(Math.abs((start.getXcoord() - end.getXcoord())));
        double y = gesturePane.getHeight()/Math.abs(((start.getYcoord() - end.getYcoord())));
        double scale = (Math.min(x, y)/2.5) + 1.1;
        gesturePane.reset();
        gesturePane.zoomTo(scale, gesturePane.targetPointAtViewportCentre());
        double xSameVal = (start.getXcoord() + end.getXcoord()) / 2.0*childPane.getWidth()/Map.getImage().getWidth();
        double ySameVal = (start.getYcoord() + end.getYcoord()) / 2.0*childPane.getHeight()/Map.getImage().getHeight();
        gesturePane.centreOn(new Point2D(xSameVal, ySameVal));
    }


    //Globals used for makeButtons
    int start  = 0;
    int counter = 0;

    /**
     * @Author Nikhil
     * This function will be used to make the buttons that display on the screen that transition from floor to floor.
     * @param floors
     */
    private void makeButtons(ArrayList<String> floors) {
        int midx = 400;
        int midy = 425;
        int numOfBut = countFloors(floors);
        int totalNum = countFloors(floors);
        int center = (numOfBut + 1)/2;
        boolean change = false;
        for(int i = 0; i < floors.size()-1; i++) {
            Button fBut = new Button();

            if(!floors.get(i+1).equals(floors.get(start))) {
                fBut.setPrefSize(50,50);
                fBut.setText(floors.get(i));
                fBut.getStyleClass().add("buttonMap");
//                fBut.setStyle("-fx-background-radius: 10000;" +
//                        "    -fx-border-color : #012D5A;" +
//                        "    -fx-border-radius: 100;" +
//                        "    -fx-border-width: 2;" +
//                        "    -fx-pref-height: 46;" +
//                        "-fx-background-color: transparent");
                final String next = floors.get(i);
                int startstore1 = start;
                int counterstore1 = counter;
                fBut.setOnAction(event -> {
                    displaySelected(startstore1, counterstore1);

                });
                floorButtons.add(fBut);
                gridPane.getChildren().add(fBut);
                int diff  = numOfBut - center;
                gridPane.setMargin(fBut,new Insets(0,0,midy,midx - diff*(100)));
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
                fBut.getStyleClass().add("buttonMap");
                fBut.setStyle("-fx-fill-color: transparent");
                final String next = floors.get(i);
                int startstore1 = start;
                int counterstore1 =floors.size();
                fBut.setOnAction(event -> {
                    displaySelected(startstore1, counterstore1);

                });
                floorButtons.add(fBut);
                gridPane.getChildren().add(fBut);
                int diff  = numOfBut - center;
                gridPane.setMargin(fBut,new Insets(0,0,midy,midx - diff*(100)));
            }
            counter++;
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
    private void displaySelected(int begin, int count) {

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

        circles.clear();
        lines.clear();
        buttons.clear();
        //Changes the map displayed to the floor of begin and count
        String floor = path.getPath().get(begin).getFloor();
        if(floor.equals("L2")) {
            Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel2.png"));
            currentMap = "L2";
        }
        if(floor.equals("L1")) {
            Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thelowerlevel1.png"));
            currentMap = "L1";
        }
        if(floor.equals("G")) {
            Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/00_thegroundfloor.png"));
            currentMap = "G";
        }
        if(floor.equals("1")) {
            Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/01_thefirstfloor.png"));
            currentMap = "1";
        }
        if(floor.equals("2")) {
            Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/02_thesecondfloor.png"));
            currentMap = "2";
        }
        if(floor.equals("3")) {
            Map.setImage(new Image("/SoftEng_UI_Mockup_Pics/03_thethirdfloor.png"));
            currentMap = "3";
        }

        //Create all necessary objects for animating path.
        Circle dude  = new Circle();
        dude.setCenterX(path.getPath().get(begin).getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
        dude.setCenterY(path.getPath().get(begin).getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
        dude.setRadius(Math.max(6, 6f));
        dude.setFill(new ImagePattern((new Image("/SoftEng_UI_Mockup_Pics/IconPerson.png"))));

        javafx.scene.shape.Path path2 = new  javafx.scene.shape.Path();

        //Sets up transition
        PathTransition travel = new PathTransition();

        circles.add(dude);
        //Setting the line display
        for(int i = begin; i < count; i++) {
            Line line = new Line();
            line.setStartX(path.getPath().get(i).getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
            line.setStartY(path.getPath().get(i).getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
            line.setEndX(path.getPath().get(i+1).getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
            line.setEndY(path.getPath().get(i+1).getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
            line.setStrokeWidth(2.5);
            line.setStroke(RED);
            lines.add(line);
            pathPane.getChildren().add(line);

            MoveTo next = new MoveTo(line.getStartX(), line.getStartY());
            CubicCurveTo end = new CubicCurveTo(line.getStartX(), line.getStartY(), line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
            path2.getElements().add(next);
            path2.getElements().add(end);
        }
        Circle startCircle = new Circle();

        //Setting the properties of the circle
        startCircle.setCenterX(path.getPath().get(begin).getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
        startCircle.setCenterY(path.getPath().get(begin).getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
        startCircle.setRadius(Math.max(1.5, 1.5f));

        //Fills in information for labels
        startLabel.setLayoutX(path.getPath().get(begin).getXcoord()*childPane.getWidth()/Map.getImage().getWidth() -20);
        startLabel.setLayoutY(path.getPath().get(begin).getYcoord()*childPane.getHeight()/Map.getImage().getHeight() - 20);
        endLabel.setLayoutX(path.getPath().get(count).getXcoord()*childPane.getWidth()/Map.getImage().getWidth() - 30);
        endLabel.setLayoutY(path.getPath().get(count).getYcoord()*childPane.getHeight()/Map.getImage().getHeight() - 20);
        endLabel.setText(path.getPath().get(count).getLongName());
        //Changing the color of the start circle and start label
        if(path.getPath().get(begin).getLocID().equals(kioskTemp.getLocID())) {
            startCircle.setStroke(Color.BLUE);
            startCircle.setFill(Color.BLUE);
            startLabel.setText(" You are here ");
            startLabel.setStyle("-fx-text-fill: WHITE;-fx-font-size: 6; -fx-background-color: BLUE; -fx-border-color: WHITE; -fx-border-width: 2; -fx-min-width: 40;");
        }
        else if(path.getPath().get(begin).getLocID().equals(startNode.getLocID())) {
            startCircle.setStroke(Color.GREEN);
            startCircle.setFill(Color.GREEN);
            startLabel.setText(startNode.getLongName());
            startLabel.setStyle("-fx-text-fill: WHITE;-fx-font-size: 6; -fx-background-color: GREEN; -fx-border-color: WHITE; -fx-border-width: 2; -fx-min-width: 40;");
        }
        else {
            startCircle.setStroke(DODGERBLUE);
            startCircle.setFill(DODGERBLUE);
            startLabel.setText(path.getPath().get(begin).getLongName());
            startLabel.setStyle("-fx-text-fill: WHITE;-fx-font-size: 6; -fx-background-color: DODGERBLUE; -fx-border-color: WHITE; -fx-border-width: 2; -fx-min-width: 40;");
        }

        circles.add(startCircle);
        Circle endCircle = new Circle();

        //Setting the properties of the circle
        endCircle.setCenterX(path.getPath().get(count).getXcoord()*childPane.getWidth()/Map.getImage().getWidth());
        endCircle.setCenterY(path.getPath().get(count).getYcoord()*childPane.getHeight()/Map.getImage().getHeight());
        endCircle.setRadius(Math.max(1.5, 1.5f));

        if(path.getPath().get(count).getLocID().equals(endNode.getLocID())) {
            endCircle.setStroke(RED);
            endCircle.setFill(RED);
            endLabel.setStyle("-fx-text-fill: WHITE;-fx-font-size: 6; -fx-background-color: RED; -fx-border-color: WHITE; -fx-border-width: 2; -fx-min-width: 40;");
        }
        else {
            endCircle.setStroke(DODGERBLUE);
            endCircle.setFill(DODGERBLUE);
            endLabel.setStyle("-fx-text-fill: WHITE;-fx-font-size: 6; -fx-background-color: DODGERBLUE; -fx-border-color: WHITE; -fx-border-width: 2; -fx-min-width: 40;");
        }
        circles.add(endCircle);
        pathPane.getChildren().add(startCircle);
        pathPane.getChildren().add(endCircle);
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
        for (int i = begin; i < count + 1; i++){
            al.add(path.getPath().get(i));
        }
        String directionS = printPath(al);
        if(path.getPath().get(count) != null){
            if(isStairELe(al.get(al.size()-1)) && isStairELe(path.getPath().get(count))){
                //        System.out.println("Go to floor " + b.getFloor() + " by " + a.getLongName());
                directionS += "\u21C5 Go to floor " + path.getPath().get(count).getFloor() + " by "
                        + al.get(al.size()-1).getLongName() +"\n";

            }
        }

        startLabel.setVisible(true);
        endLabel.setVisible(true);

        direction.setText(directionS);
        direction.setWrapText(true);
        direction.setDisable(false);
        direction.setEditable(false);

        displayExits();
    }

    /**
     * @Author Nikhil
     * Method to clear all path display on map
     */
    private void clear() {
        for (Circle c : circles) {
            pathPane.getChildren().remove(c);
        }
        for (Line l : lines) {
            pathPane.getChildren().remove(l);
        }
        for (Button b : buttons) {
            pathPane.getChildren().remove(b);
        }
        for (Button b : floorButtons) {
            gridPane.getChildren().remove(b);
        }

        floorButtons.clear();
        circles.clear();
        lines.clear();
        buttons.clear();
    }

    /**
     * @Author Nikhil
     * Helper for makeButtons
     * @param floors
     * @return
     */
    private int countFloors(ArrayList<String> floors) {
        int floorCounter = 1;
        for(int i = 0; i < floors.size()-1; i++) {
            if(!floors.get(i).equals(floors.get(i+1))) {
                floorCounter++;
            }
        }
        return floorCounter;
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



}