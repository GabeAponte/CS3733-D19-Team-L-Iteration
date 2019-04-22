package edu.wpi.cs3733.d19.teamL.RoomBooking;

import edu.wpi.cs3733.d19.teamL.Account.EmployeeAccess;
import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.PathFindingController;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.util.Duration;

public class BookRoomController {

    @FXML
    private JFXDatePicker weeklyDatePicker;

    @FXML
    private Label classroomLabel;

    @FXML
    private JFXComboBox roomPicker;

    @FXML
    private TreeTableView<WeeklyRoom> weeklyBookedTime;

    @FXML
    private TreeTableColumn<WeeklyRoom, String> weeklyTimeCol;

    @FXML
    private TreeTableColumn<WeeklyRoom, Boolean> sunCol;

    @FXML
    private TreeTableColumn<WeeklyRoom, Boolean> monCol;

    @FXML
    private TreeTableColumn<WeeklyRoom, Boolean> tueCol;

    @FXML
    private TreeTableColumn<WeeklyRoom, Boolean> wedCol;

    @FXML
    private TreeTableColumn<WeeklyRoom, Boolean> thuCol;

    @FXML
    private TreeTableColumn<WeeklyRoom, Boolean> friCol;

    @FXML
    private TreeTableColumn<WeeklyRoom, Boolean> satCol;

    private TreeItem Root = new TreeItem<>("rootxxx");

    String chosenRoom;
    LocalDate chosenDate;


    @FXML
    private JFXDatePicker dailyDatePicker;

    @FXML
    private TreeTableView<Room> dailyBookedTime;

    @FXML
    private TreeTableColumn<Room, String> dailyTimeCol;

    @FXML
    private TreeTableColumn<Room, Boolean> class1Col;

    @FXML
    private TreeTableColumn<Room, Boolean> class2Col;

    @FXML
    private TreeTableColumn<Room, Boolean> class3Col;

    @FXML
    private TreeTableColumn<Room, Boolean> class4Col;

    @FXML
    private TreeTableColumn<Room, Boolean> class5Col;

    @FXML
    private TreeTableColumn<Room, Boolean> class6Col;

    @FXML
    private TreeTableColumn<Room, Boolean> class7Col;

    @FXML
    private TreeTableColumn<Room, Boolean> class8Col;

    @FXML
    private TreeTableColumn<Room, Boolean> class9Col;

    @FXML
    private TreeTableColumn<Room, Boolean> auditorium;

    //private TreeItem Root = new TreeItem<>("rootxxx");

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXComboBox<String> availableRooms;

    @FXML
    private JFXComboBox<String> eventType;

    @FXML
    private Label error;

    @FXML
    private Label roomName;

    @FXML
    private Label popupName;

    @FXML
    private JFXButton viewSchedule;

    @FXML
    private JFXButton requestRoom;

    @FXML
    private JFXTimePicker startTime;

    @FXML
    private JFXTimePicker endTime;

    @FXML
    private Stage thestage;

    @FXML
    private GridPane gridPane;

    @FXML
    private Pane imagePane;

    @FXML
    private ImageView roomImage;

    @FXML
    private Button back;

    @FXML
    private Button homebtn;

    @FXML
    private Button viewWeekly;

    @FXML
    private AnchorPane reservationPane;

    @FXML
    private AnchorPane bookedEventPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ListView<EmployeeAccess> myListView;
    private ObservableList<EmployeeAccess> listViewData = FXCollections.observableArrayList();

    Timeline timeout;
    VisualSimulationThread sim;
    private boolean firstTimeRan = true;
    private boolean resShowing = false;
    private boolean bookedEventShowing = false;
    private boolean calledFromVisualClick = false;

    final ObservableList<String> listOfRooms = FXCollections.observableArrayList();
    ArrayList<String> rooms = new ArrayList<>();
    ArrayList<String> allRooms = new ArrayList<String>();
    ArrayList<Boolean> availRooms = new ArrayList<Boolean>();
    ArrayList<Polygon> flexSpaces = new ArrayList<Polygon>();
    private ArrayList<RoomDisplay> DisplayRooms = new ArrayList<RoomDisplay>();

    public void initialize() {
        sim = new VisualSimulationThread(86);
        sim.start();

        double room1[] = {2240, 1640, 2590, 1640, 2590, 1790, 2670, 1790, 2670, 1890, 2240, 1890};
        double room2[] = {2860, 1140, 3060, 1065, 3195, 1440, 2995, 1510};
        double room3[] = {2720, 750, 2855, 1130, 3060, 1065, 2995, 875, 3015, 860, 2970, 770, 2900, 720, 2810, 690};
        double room4[] = {2420, 180, 2610, 180, 2730, 540, 2420, 540};
        double room5[] = {2240, 755, 2550, 755, 2550, 1100, 2240, 1100};
        double room6[] = {2090, 180, 2410, 180, 2410, 540, 2090, 540};
        double room7[] = {2000, 755, 2230, 755, 2230, 1100, 2000, 1100};
        double room8[] = {1750, 180, 2080, 180, 2080, 540, 1750, 540};
        double room9[] = {1780, 720, 1860, 720, 1860, 755, 2000, 755, 2000, 940, 1780, 940};
        double auditorium[] = {2860, 1920, 2860, 2030, 3130, 2030, 3150, 2060, 3250, 2000, 3300, 1945, 3330, 1870, 3330, 1770, 3325, 1720, 3300, 1725, 3200, 1450, 3040, 1500, 3070, 1590, 3030, 1600};

        DisplayRooms.add(new RoomDisplay("Room 1 - Computer", room1, "Room 1 - Computer"));
        DisplayRooms.add(new RoomDisplay("Room 2 - Computer", room2, "Room 2 - Computer"));
        DisplayRooms.add(new RoomDisplay("Room 3 - Computer", room3, "Room 3 - Computer"));
        DisplayRooms.add(new RoomDisplay("Room 4 - Classroom", room4, "Room 4 - Classroom"));
        DisplayRooms.add(new RoomDisplay("Room 5 - Computer", room5, "Room 5 - Computer"));
        DisplayRooms.add(new RoomDisplay("Room 6 - Classroom", room6, "Room 6 - Classroom"));
        DisplayRooms.add(new RoomDisplay("Room 7 - Computer", room7, "Room 7 - Computer"));
        DisplayRooms.add(new RoomDisplay("Room 8 - Classroom", room8, "Room 8 - Classroom"));
        DisplayRooms.add(new RoomDisplay("Room 9 - Computer", room9, "Room 9 - Computer"));
        DisplayRooms.add(new RoomDisplay("Mission Hall Auditorium", auditorium, "Mission Hall Auditorium"));

        roomImage.fitWidthProperty().bind(imagePane.widthProperty());
        roomImage.fitHeightProperty().bind(imagePane.heightProperty());

        anchorPane.sceneProperty().addListener((observableScene, oldScene, newScene) -> {
            if (oldScene == null && newScene != null) {
                // scene is set for the first time. Now its the time to listen stage changes.
                newScene.windowProperty().addListener((observableWindow, oldWindow, newWindow) -> {
                    if (oldWindow == null && newWindow != null) {
                        // stage is set. now is the right time to do whatever we need to the stage in the controller.
                        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
                        System.out.println("Screen resized");
                            for(int i = 0; i < DisplayRooms.size(); i++){
                                imagePane.getChildren().remove(DisplayRooms.get(i).getPolygon());
                            }

                            firstTimeRan = true;
                            timeout.setCycleCount(Timeline.INDEFINITE);
                            timeout.play();
                            Platform.runLater(new Runnable(){
                                @Override
                                public void run(){
                                    //displayFlexSpaces();
                                    fieldsEntered();
                                }
                            });

                        };

                        ((Stage) newWindow).widthProperty().addListener(stageSizeListener);
                        ((Stage) newWindow).heightProperty().addListener(stageSizeListener);
                    }
                });
            }
        });

        displayFlexSpaces(sim.getSimulation());

        ArrayList<String> events = new ArrayList<String> (Arrays.asList("Meeting", "Party", "Conference", "Reception"));
        ObservableList obList = FXCollections.observableList(events);
        eventType.setItems(obList);

        startTime.setValue(LocalTime.now().plusMinutes(1));
        endTime.setValue(LocalTime.now().plusHours(1).plusMinutes(1));
        datePicker.setValue(LocalDate.now());
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        timeout = new Timeline(new KeyFrame(Duration.millis(1500), new EventHandler<ActionEvent>() {


            @Override
            public void handle(ActionEvent event) {
                displayFlexSpaces(sim.getSimulation());
                if ((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()) {
                    try {
                        single.setLastTime();
                        single.setDoPopup(true);
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        try{
                            sim.join();
                        } catch (Exception e){
                            e.printStackTrace();
                            sim.stop();
                        }
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        Parent sceneMain = loader.load();
                        HomeScreenController controller = loader.<HomeScreenController>getController();
                        single.setLastTime();
                        controller.displayPopup();
                        single.setLastTime();

                        Stage thisStage = (Stage) endTime.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
                        timeout.stop();
                    } catch (IOException io) {
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));


        timeout.setCycleCount(Timeline.INDEFINITE);
        timeout.play();
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                //displayFlexSpaces();
                fieldsEntered();
            }
        });

    }

    @FXML
    private void switchToTable(ActionEvent event) throws IOException {
        timeout.stop();
        try{
            sim.join();
        } catch (Exception e){
            e.printStackTrace();
            sim.stop();
        }
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource("BookRoom2.fxml"));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    /** @author Gabe, Nikhil
     * When a user selcts a valid start and end time and a date, they are given the option
     * to book any avaliable rooms
     */
    private void findRoom() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();

        LocalTime startTimeValue = startTime.getValue();
        LocalTime endTimeValue = endTime.getValue();
        LocalDate roomDate = datePicker.getValue();
        LocalDate curDate = LocalDate.now();
        LocalTime curTime = LocalTime.now();

        error.setTextFill(Color.RED);

        //Gabe - error when start time, end time, and date are blank
        if (startTimeValue == null && endTimeValue == null && roomDate == null) {
            error.setText("Please select start and end times and a date.");
        }

        //Gabe - error when start time and end time are blank
        else if (startTimeValue == null && endTimeValue == null) {
            error.setText("Please select start and end times.");
        }

        //Gabe - error when start time and date are blank
        else if (startTimeValue == null && roomDate == null) {
            error.setText("Please select a start time and a date.");
        }

        //Gabe - error when end time and date are blank
        else if (endTimeValue == null && roomDate == null) {
            error.setText("Please select an end time and a date.");
        }

        //Gabe - error when start time is blank
        else if (startTimeValue == null) {
            error.setText("Please select a start time.");
        }

        //Gabe - error when end time is blank
        else if (endTimeValue == null) {
            error.setText("Please select an end time.");
        }

        //Gabe - error when date is blank
        else if (roomDate == null) {
            error.setText("Please select a date.");
        } else if (startTimeValue.equals(endTimeValue)) {
            error.setText("Times cannot be the same.");
        } else if (startTimeValue.compareTo(curTime) <= 0 && roomDate.equals(curDate)) {
            error.setText("Please select a current or future time for today.");
        } else if (roomDate.compareTo(curDate) < 0) {
            error.setText("Please select a time for today or a future day.");
        } else if (availableRooms.getValue() == null) {
            error.setText("Please pick a room.");
        } else if (startTimeValue.compareTo(endTimeValue) > 0) {
            error.setText("Start time cannot be ahead of end time.");
        } else {
            error.setTextFill(Color.WHITE);
            error.setText("Room booked.");
            int startTimeMil = startTime.getValue().getHour() * 100 + startTime.getValue().getMinute();
            int endTimeMil = endTime.getValue().getHour() * 100 + endTime.getValue().getMinute();
            String date = datePicker.getValue().toString();
            String endDate;
            String roomName = availableRooms.getValue().toString();
            EmployeeAccess ea = new EmployeeAccess();
            String employeeID = ea.getEmployeeInformation(single.getUsername()).get(0);
            ReservationAccess roomReq = new ReservationAccess();
            RoomAccess ra = new RoomAccess();
            roomReq.makeReservation(ra.getRoomID(roomName), employeeID, date, date, startTimeMil, endTimeMil);
            fieldsEntered();
        }
    }

    //todo checks if fields are null and populates table here
    // RA.getAvailRooms returns list of available rooms
    @FXML
    public void fieldsEntered() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        RoomAccess ra = new RoomAccess();
        int startTimeMil = 0;
        int endTimeMil = 0;
        String date = "";
        String endDate = "";

        if (startTime.getValue() != null && endTime.getValue() != null && datePicker.getValue() != null) {
            startTimeMil = startTime.getValue().getHour() * 100 + startTime.getValue().getMinute();
            endTimeMil = endTime.getValue().getHour() * 100 + endTime.getValue().getMinute();
            date = datePicker.getValue().toString();
            availableRooms.getSelectionModel().clearSelection();

            rooms = ra.getAvailRooms(date, date, startTimeMil, endTimeMil);
            /*for (int i = 0; i < rooms.size(); i++) {
                System.out.println("Available Rooms: " + rooms.get(i));
            }*/

            listOfRooms.clear();

            for (int i = 0; i < DisplayRooms.size(); i++) {
                DisplayRooms.get(i).setAvailable(false);
            }

            //System.out.println("startTimeMil: " + startTimeMil + "\n endTimeMil:" + endTimeMil);
            rooms = ra.getAvailRooms(date, date, startTimeMil, endTimeMil);

            for(int j = 0; j < rooms.size(); j ++) {
                listOfRooms.add(rooms.get(j));
                for (int i = 0; i < DisplayRooms.size(); i++) {
                    //System.out.println("COMPARE " + DisplayRooms.get(i).roomName + " AND " + rooms.get(j));
                    if (DisplayRooms.get(i).roomName.equals(rooms.get(j))) {
                        DisplayRooms.get(i).setAvailable(true);
                        //System.out.println("True: " + i);
                        break;
                    }
                }
            }

            availableRooms.setItems(listOfRooms);
            displayAllRooms();
        }
    }

    /** @author Isabella
     * When the dropdown of availible rooms is generated, the map changes to reflect availiblity.
     * Clicking on either the map or the dropdown will highlight the corresponding room.
     */
    @FXML
    public void displayAllRooms() {

        if(firstTimeRan) {
            double scaleRatio = 0;
            scaleRatio = Math.min(roomImage.getFitWidth() / roomImage.getImage().getWidth(), roomImage.getFitHeight() / roomImage.getImage().getHeight());
            for (int i = 0; i < DisplayRooms.size(); i++) {
                DisplayRooms.get(i).makePolygon(scaleRatio);
                DisplayRooms.get(i).getPolygon().setOnMouseClicked(onMouseClickedEventHandler);
                DisplayRooms.get(i).getPolygon().setVisible(false);
                imagePane.getChildren().add(DisplayRooms.get(i).getPolygon());
            }
            firstTimeRan = false;
        }

        for (int i = 0; i < DisplayRooms.size(); i++) {

            if(DisplayRooms.get(i).isAvailable()){
                DisplayRooms.get(i).changePolygonColor("GREEN");
            } else {
                DisplayRooms.get(i).changePolygonColor("RED");
            }
            DisplayRooms.get(i).getPolygon().setVisible(true);
        }
    }

    private EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            boolean resPaneCalled = false;
            calledFromVisualClick = true;
            displayAllRooms();
            for (int k = 0; k < DisplayRooms.size(); k++) {
                Point2D mousePress = new Point2D(event.getX(), event.getY());
                if (DisplayRooms.get(k).p.contains(mousePress)) {
                    imagePane.getChildren().remove(DisplayRooms.get(k).getPolygon());
                    roomName.setText(DisplayRooms.get(k).niceName);
                    fieldsEntered();
                    for(int z = 0; z<listOfRooms.size(); z++){
                        if(DisplayRooms.get(k).getRoomName().equals(listOfRooms.get(z))){
                            if(DisplayRooms.get(k).isAvailable()) {
                                availableRooms.getSelectionModel().select(listOfRooms.get(z));
                                if(popupName.getText().contains(DisplayRooms.get(k).niceName)){
                                    resPaneCalled = true;
                                    //System.out.println("Same");
                                    if(resShowing) {
                                        openReservation(false);
                                    }else{
                                        openReservation(true);
                                    }
                                }else {
                                    resPaneCalled = true;
                                    popupName.setText("Reserve " + DisplayRooms.get(k).niceName);
                                    //System.out.println("New");
                                    openReservation(true);
                                }
                            }
                        }
                    }
                    if(resPaneCalled == false){
                        openEventInfo(true);
                    }
                    DisplayRooms.get(k).changePolygonColor("BLUE");
                    imagePane.getChildren().add(DisplayRooms.get(k).getPolygon());
                    calledFromVisualClick = false;
                }
            }

        }

    };

    public void highlightFromDropdown(){
        //System.out.println("VisClick:"+calledFromVisualClick);
        for(int j = 0; j<DisplayRooms.size(); j++){
            if(DisplayRooms.get(j).getRoomName().equals(availableRooms.getValue())){
                for (int i = 0; i < DisplayRooms.size(); i++) {
                    if(DisplayRooms.get(i).isAvailable()){
                        if(calledFromVisualClick == false){
                                popupName.setText("Reserve " + DisplayRooms.get(j).niceName);
                                System.out.println("hd");
                                openReservation(true);
                        }
                        DisplayRooms.get(i).changePolygonColor("GREEN");
                    } else {
                        DisplayRooms.get(i).changePolygonColor("RED");
                    }
                }
                roomName.setText(DisplayRooms.get(j).niceName);
                DisplayRooms.get(j).changePolygonColor("BLUE");
            }
        }

    }

    /** @author Isabella
     * Displays individual flex spaces that update randomly
     */
    public void displayFlexSpaces(ArrayList<Boolean> flexSpaceAvailable){
        int i = 0;
        for(i = 0; i < flexSpaces.size(); i++){
            imagePane.getChildren().remove(flexSpaces.get(i));
        }
        flexSpaces.clear();

        double sr = Math.min(roomImage.getFitWidth() / roomImage.getImage().getWidth(), roomImage.getFitHeight() / roomImage.getImage().getHeight());

        //large important room
        flexSpaces.add(new Polygon(860*sr, 1270*sr, 1160*sr, 1270*sr, 1160*sr, 1580*sr, 860*sr, 1580*sr));

        flexSpaces.add(new Polygon(150*sr, 230*sr, 200*sr, 230*sr, 200*sr, 340*sr, 150*sr, 340*sr)); //left side desk top
        flexSpaces.add(new Polygon(150*sr, 420*sr, 200*sr, 420*sr, 200*sr, 530*sr, 150*sr, 530*sr)); //left side desk middle
        flexSpaces.add(new Polygon(150*sr, 590*sr, 200*sr, 590*sr, 200*sr, 740*sr, 150*sr, 740*sr)); //left side desk lower
        flexSpaces.add(new Polygon(320*sr, 560*sr, 540*sr, 560*sr, 540*sr, 710*sr, 340*sr, 710*sr)); //left conference room
        flexSpaces.add(new Polygon(200*sr, 1300*sr, 270*sr, 1300*sr, 270*sr, 1360*sr, 200*sr, 1360*sr)); //small three chairs
        flexSpaces.add(new Polygon(570*sr, 1340*sr, 630*sr, 1340*sr, 630*sr, 1430*sr, 570*sr, 1430*sr)); //standalone square
        flexSpaces.add(new Polygon(1320*sr, 2070*sr, 1380*sr, 2070*sr, 1380*sr, 2100*sr, 1320*sr, 2100*sr)); //small two chairs
        flexSpaces.add(new Polygon(1980*sr, 1690*sr, 2230*sr, 1690*sr, 2230*sr, 1880*sr, 1980*sr, 1880*sr)); //bottom right room
        flexSpaces.add(new Polygon(1760*sr, 2230*sr, 1820*sr, 2230*sr, 1820*sr, 2380*sr, 1760*sr, 2380*sr)); //bottom vertical top
        flexSpaces.add(new Polygon(1740*sr, 2440*sr, 1800*sr, 2440*sr, 1800*sr, 2600*sr, 1740*sr, 2600*sr)); //bottom vertical bottom

        //top left desk bank
        flexSpaces.add(new Polygon(240*sr, 240*sr, 305*sr, 240*sr, 305*sr, 290*sr, 240*sr, 290*sr));
        flexSpaces.add(new Polygon(305*sr, 240*sr, 370*sr, 240*sr, 370*sr, 290*sr, 305*sr, 290*sr));
        flexSpaces.add(new Polygon(370*sr, 240*sr, 435*sr, 240*sr, 435*sr, 290*sr, 370*sr, 290*sr));
        flexSpaces.add(new Polygon(435*sr, 240*sr, 500*sr, 240*sr, 500*sr, 290*sr, 435*sr, 290*sr));
        flexSpaces.add(new Polygon(240*sr, 290*sr, 305*sr, 290*sr, 305*sr, 340*sr, 240*sr, 340*sr));
        flexSpaces.add(new Polygon(305*sr, 290*sr, 370*sr, 290*sr, 370*sr, 340*sr, 305*sr, 340*sr));
        flexSpaces.add(new Polygon(370*sr, 290*sr, 435*sr, 290*sr, 435*sr, 340*sr, 370*sr, 340*sr));
        flexSpaces.add(new Polygon(435*sr, 290*sr, 500*sr, 290*sr, 500*sr, 340*sr, 435*sr, 340*sr));

        //second down top left desk bank
        flexSpaces.add(new Polygon(250*sr, 390*sr, 313*sr, 390*sr, 313*sr, 440*sr, 250*sr, 440*sr));
        flexSpaces.add(new Polygon(313*sr, 390*sr, 375*sr, 390*sr, 375*sr, 440*sr, 313*sr, 440*sr));
        flexSpaces.add(new Polygon(375*sr, 390*sr, 438*sr, 390*sr, 438*sr, 440*sr, 375*sr, 440*sr));
        flexSpaces.add(new Polygon(438*sr, 390*sr, 500*sr, 390*sr, 500*sr, 440*sr, 438*sr, 440*sr));
        flexSpaces.add(new Polygon(250*sr, 440*sr, 313*sr, 440*sr, 313*sr, 490*sr, 250*sr, 490*sr));
        flexSpaces.add(new Polygon(313*sr, 440*sr, 375*sr, 440*sr, 375*sr, 490*sr, 313*sr, 490*sr));
        flexSpaces.add(new Polygon(375*sr, 440*sr, 438*sr, 440*sr, 438*sr, 490*sr, 375*sr, 490*sr));
        flexSpaces.add(new Polygon(438*sr, 440*sr, 500*sr, 440*sr, 500*sr, 490*sr, 438*sr, 490*sr));

        //center column of small rooms
        flexSpaces.add(new Polygon(660*sr, 660*sr, 730*sr, 660*sr, 730*sr, 748*sr, 660*sr, 748*sr));
        flexSpaces.add(new Polygon(660*sr, 748*sr, 730*sr, 748*sr, 730*sr, 836*sr, 660*sr, 836*sr));
        flexSpaces.add(new Polygon(660*sr, 836*sr, 730*sr, 836*sr, 730*sr, 924*sr, 660*sr, 924*sr));
        flexSpaces.add(new Polygon(660*sr, 924*sr, 730*sr, 924*sr, 730*sr, 1012*sr, 660*sr, 1012*sr));
        flexSpaces.add(new Polygon(660*sr, 1012*sr, 730*sr, 1012*sr, 730*sr, 1100*sr, 660*sr, 1100*sr));

        //upper vertical column of desks - right
        flexSpaces.add(new Polygon(1410*sr, 190*sr, 1460*sr, 190*sr, 1460*sr, 260*sr, 1410*sr, 260*sr));
        flexSpaces.add(new Polygon(1410*sr, 260*sr, 1460*sr, 260*sr, 1460*sr, 322*sr, 1410*sr, 322*sr));
        flexSpaces.add(new Polygon(1410*sr, 322*sr, 1460*sr, 322*sr, 1460*sr, 388*sr, 1410*sr, 388*sr));
        flexSpaces.add(new Polygon(1410*sr, 388*sr, 1460*sr, 388*sr, 1460*sr, 454*sr, 1410*sr, 454*sr));
        flexSpaces.add(new Polygon(1410*sr, 454*sr, 1460*sr, 454*sr, 1460*sr, 520*sr, 1410*sr, 520*sr));

        //upper vertical column of desks - left
        flexSpaces.add(new Polygon(1360*sr, 270*sr, 1410*sr, 270*sr, 1410*sr, 333*sr, 1360*sr, 333*sr));
        flexSpaces.add(new Polygon(1360*sr, 333*sr, 1410*sr, 333*sr, 1410*sr, 395*sr, 1360*sr, 395*sr));
        flexSpaces.add(new Polygon(1360*sr, 395*sr, 1410*sr, 395*sr, 1410*sr, 458*sr, 1360*sr, 458*sr));
        flexSpaces.add(new Polygon(1360*sr, 458*sr, 1410*sr, 458*sr, 1410*sr, 520*sr, 1360*sr, 520*sr));

        //center horizontal desks left
        flexSpaces.add(new Polygon(1240*sr, 1540*sr, 1303*sr, 1540*sr, 1303*sr, 1590*sr, 1240*sr, 1590*sr));
        flexSpaces.add(new Polygon(1303*sr, 1540*sr, 1367*sr, 1540*sr, 1367*sr, 1590*sr, 1303*sr, 1590*sr));
        flexSpaces.add(new Polygon(1367*sr, 1540*sr, 1430*sr, 1540*sr, 1430*sr, 1590*sr, 1367*sr, 1590*sr));
        flexSpaces.add(new Polygon(1240*sr, 1590*sr, 1303*sr, 1590*sr, 1303*sr, 1640*sr, 1240*sr, 1640*sr));
        flexSpaces.add(new Polygon(1303*sr, 1590*sr, 1367*sr, 1590*sr, 1367*sr, 1640*sr, 1303*sr, 1640*sr));
        flexSpaces.add(new Polygon(1367*sr, 1590*sr, 1430*sr, 1590*sr, 1430*sr, 1640*sr, 1367*sr, 1640*sr));

        //center horizontal desks right
        flexSpaces.add(new Polygon(1490*sr, 1540*sr, 1553*sr, 1540*sr, 1553*sr, 1590*sr, 1490*sr, 1590*sr));
        flexSpaces.add(new Polygon(1553*sr, 1540*sr, 1617*sr, 1540*sr, 1617*sr, 1590*sr, 1553*sr, 1590*sr));
        flexSpaces.add(new Polygon(1617*sr, 1540*sr, 1680*sr, 1540*sr, 1680*sr, 1590*sr, 1617*sr, 1590*sr));
        flexSpaces.add(new Polygon(1490*sr, 1590*sr, 1553*sr, 1590*sr, 1553*sr, 1640*sr, 1490*sr, 1640*sr));
        flexSpaces.add(new Polygon(1553*sr, 1590*sr, 1617*sr, 1590*sr, 1617*sr, 1640*sr, 1553*sr, 1640*sr));
        flexSpaces.add(new Polygon(1617*sr, 1590*sr, 1680*sr, 1590*sr, 1680*sr, 1640*sr, 1617*sr, 1640*sr));

        //center vertical column of desks
        flexSpaces.add(new Polygon(1520*sr, 1190*sr, 1570*sr, 1190*sr, 1570*sr, 1252*sr, 1520*sr, 1252*sr));
        flexSpaces.add(new Polygon(1520*sr, 1252*sr, 1570*sr, 1252*sr, 1570*sr, 1314*sr, 1520*sr, 1314*sr));
        flexSpaces.add(new Polygon(1520*sr, 1314*sr, 1570*sr, 1314*sr, 1570*sr, 1376*sr, 1520*sr, 1376*sr));
        flexSpaces.add(new Polygon(1520*sr, 1376*sr, 1570*sr, 1376*sr, 1570*sr, 1438*sr, 1520*sr, 1438*sr));
        flexSpaces.add(new Polygon(1520*sr, 1438*sr, 1570*sr, 1438*sr, 1570*sr, 1500*sr, 1520*sr, 1500*sr));
        flexSpaces.add(new Polygon(1570*sr, 1190*sr, 1620*sr, 1190*sr, 1620*sr, 1252*sr, 1570*sr, 1252*sr));
        flexSpaces.add(new Polygon(1570*sr, 1252*sr, 1620*sr, 1252*sr, 1620*sr, 1314*sr, 1570*sr, 1314*sr));
        flexSpaces.add(new Polygon(1570*sr, 1314*sr, 1620*sr, 1314*sr, 1620*sr, 1376*sr, 1570*sr, 1376*sr));
        flexSpaces.add(new Polygon(1570*sr, 1376*sr, 1620*sr, 1376*sr, 1620*sr, 1438*sr, 1570*sr, 1438*sr));
        flexSpaces.add(new Polygon(1570*sr, 1438*sr, 1620*sr, 1438*sr, 1620*sr, 1500*sr, 1570*sr, 1500*sr));

        //bottom right vertical column of desks - left
        flexSpaces.add(new Polygon(1900*sr, 1890*sr, 1950*sr, 1890*sr, 1950*sr, 1955*sr, 1900*sr, 1955*sr));
        flexSpaces.add(new Polygon(1900*sr, 1955*sr, 1950*sr, 1955*sr, 1950*sr, 2020*sr, 1900*sr, 2020*sr));
        flexSpaces.add(new Polygon(1900*sr, 2020*sr, 1950*sr, 2020*sr, 1950*sr, 2085*sr, 1900*sr, 2085*sr));
        flexSpaces.add(new Polygon(1900*sr, 2085*sr, 1950*sr, 2085*sr, 1950*sr, 2150*sr, 1900*sr, 2150*sr));
        flexSpaces.add(new Polygon(1950*sr, 1890*sr, 2000*sr, 1890*sr, 2000*sr, 1955*sr, 1950*sr, 1955*sr));
        flexSpaces.add(new Polygon(1950*sr, 1955*sr, 2000*sr, 1955*sr, 2000*sr, 2020*sr, 1950*sr, 2020*sr));
        flexSpaces.add(new Polygon(1950*sr, 2020*sr, 2000*sr, 2020*sr, 2000*sr, 2085*sr, 1950*sr, 2085*sr));
        flexSpaces.add(new Polygon(1950*sr, 2085*sr, 2000*sr, 2085*sr, 2000*sr, 2150*sr, 1950*sr, 2150*sr));

        //bottom right vertical column of desks - right
        flexSpaces.add(new Polygon(2040*sr, 1890*sr, 2090*sr, 1890*sr, 2090*sr, 1955*sr, 2040*sr, 1955*sr));
        flexSpaces.add(new Polygon(2040*sr, 1955*sr, 2090*sr, 1955*sr, 2090*sr, 2020*sr, 2040*sr, 2020*sr));
        flexSpaces.add(new Polygon(2040*sr, 2020*sr, 2090*sr, 2020*sr, 2090*sr, 2085*sr, 2040*sr, 2085*sr));
        flexSpaces.add(new Polygon(2040*sr, 2085*sr, 2090*sr, 2085*sr, 2090*sr, 2150*sr, 2040*sr, 2150*sr));
        flexSpaces.add(new Polygon(2090*sr, 1890*sr, 2140*sr, 1890*sr, 2140*sr, 1955*sr, 2090*sr, 1955*sr));
        flexSpaces.add(new Polygon(2090*sr, 1955*sr, 2140*sr, 1955*sr, 2140*sr, 2020*sr, 2090*sr, 2020*sr));
        flexSpaces.add(new Polygon(2090*sr, 2020*sr, 2140*sr, 2020*sr, 2140*sr, 2085*sr, 2090*sr, 2085*sr));
        flexSpaces.add(new Polygon(2090*sr, 2085*sr, 2140*sr, 2085*sr, 2140*sr, 2150*sr, 2090*sr, 2150*sr));

        //bottom right vertical rooms
        flexSpaces.add(new Polygon(2220*sr, 2220*sr, 2290*sr, 2220*sr, 2290*sr, 2340*sr, 2220*sr, 2340*sr));
        flexSpaces.add(new Polygon(2220*sr, 2340*sr, 2290*sr, 2340*sr, 2290*sr, 2430*sr, 2220*sr, 2430*sr));
        flexSpaces.add(new Polygon(2220*sr, 2430*sr, 2290*sr, 2430*sr, 2290*sr, 2515*sr, 2220*sr, 2515*sr));
        flexSpaces.add(new Polygon(2220*sr, 2515*sr, 2290*sr, 2515*sr, 2290*sr, 2620*sr, 2220*sr, 2620*sr));

        //little rooms above center room
        flexSpaces.add(new Polygon(860*sr, 1190*sr, 960*sr, 1190*sr, 960*sr, 1270*sr, 860*sr, 1270*sr));
        flexSpaces.add(new Polygon(960*sr, 1190*sr, 1060*sr, 1190*sr, 1060*sr, 1270*sr, 960*sr, 1270*sr));
        flexSpaces.add(new Polygon(1060*sr, 1190*sr, 1160*sr, 1190*sr, 1160*sr, 1270*sr, 1060*sr, 1270*sr));

        for(i = 0; i < flexSpaces.size(); i++){
            if(flexSpaceAvailable.get(i)) {
                flexSpaces.get(i).setStroke(Color.web("TURQUOISE"));
                flexSpaces.get(i).setFill(Color.web("TURQUOISE"));
                flexSpaces.get(i).setOpacity(0.5);
            }else{
                flexSpaces.get(i).setStroke(Color.web("RED"));
                flexSpaces.get(i).setFill(Color.web("RED"));
                flexSpaces.get(i).setOpacity(0.3);
            }
            imagePane.getChildren().add(flexSpaces.get(i));
        }
    }

    public void switchToWeekly(ActionEvent event) throws IOException {
        timeout.stop();
        try{
            sim.join();
        } catch (Exception e){
            e.printStackTrace();
            sim.stop();
        }
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("WeeklySchedule.fxml"));
        Parent sceneMain = loader.load();

        WeeklyScheduleController wsc = loader.getController();
        String name = roomName.getText();
        //if the text is null
        if(name == null || name.equals("Select a Room")){
            //if there are no available rooms
            if(listOfRooms.size() < 1){
                name = "Room 1 - Computer";
            } else {
                name = listOfRooms.get(0);
                //else set to first available room
            }
        }
        System.out.println(name);
        wsc.loadWeekly(name, datePicker.getValue());

        ((Node) event.getSource()).getScene().setRoot(sceneMain);
    }

    /**@author Nathan
     * Restores previous screen
     * @throws IOException
     */
    @FXML
    private void backPressed(ActionEvent event) throws IOException{
        Singleton single = Singleton.getInstance();
        timeout.stop();
        try{
            sim.join();
        } catch (Exception e){
            e.printStackTrace();
            sim.stop();
        }

        single.setLastTime();
        single.setDoPopup(true);

        Memento m = single.restore();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(m.getFxml()));
        Parent newPage = loader.load();
        if(m.getFxml().contains("HospitalPathFinding")){
            PathFindingController pfc = loader.getController();
            pfc.initWithMeme(m.getPathPref(), m.getTypeFilter(), m.getFloorFilter(), m.getStart(), m.getEnd());
        }

        //Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    /**@author Nathan
     * Saves the memento state
     */
    private void saveState(){
        Singleton single = Singleton.getInstance();
        single.saveMemento("ActiveServiceRequests.fxml");
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        timeout.stop();
        try{
            sim.join();
        } catch (Exception e){
            e.printStackTrace();
            sim.stop();
        }
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
        // saveState();
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    /** @author Isabella
     * Slides in the reservation menu from the right side
     */
    private void openReservation(boolean open) {
        TranslateTransition openNav = new TranslateTransition(new Duration(400.0D), this.reservationPane);
        openNav.setToX(0.0D);
        TranslateTransition closeNav = new TranslateTransition(new Duration(400.0D), this.reservationPane);
        if (open == true){
            openNav.setToX(-20.0D-this.reservationPane.getWidth());
            openNav.play();
            resShowing = true;
            openEventInfo(false);
            //System.out.println("ResShowing = true");
        } else {
            closeNav.setToX(20+this.anchorPane.getWidth()+this.reservationPane.getWidth());
            closeNav.play();
            resShowing = false;
            //System.out.println("ResShowing = false");
        }
    }

    /** @author Isabella
     * Slides in the event information menu from the right side
     */
    private void openEventInfo(boolean open) {
        //System.out.println("Open Event Info called");
        TranslateTransition openNav = new TranslateTransition(new Duration(400.0D), this.bookedEventPane);
        openNav.setToX(0.0D);
        TranslateTransition closeNav = new TranslateTransition(new Duration(400.0D), this.bookedEventPane);
        if (open == true){
            openNav.setToX(-570.0D-this.bookedEventPane.getWidth());
            openNav.play();
            openReservation(false);
            bookedEventShowing = true;
        } else {
            closeNav.setToX(570+this.anchorPane.getWidth()+this.reservationPane.getWidth());
            closeNav.play();
            bookedEventShowing = false;
        }
    }

//----------------------------------------------------------------------------------------------------------------------
//                                          BOOK ROOM 2 CONTROLLER
//----------------------------------------------------------------------------------------------------------------------

    @FXML
    private void dailyTab() {
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
                        single.setLastTime();
                        controller.displayPopup();
                        single.setLastTime();

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
        dailyDatePicker.setValue(LocalDate.now());
        findRooms();
    }

    @FXML
    private void findRooms() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        dailyBookedTime.setRoot(null);
        Root.getChildren().clear();
        RoomAccess ra = new RoomAccess();
        String theDate = dailyDatePicker.getValue().toString();
        int startTime = 0;
        int endTime = 30;
        for(int i = 0; i < 47; i++){
            // System.out.println("Start Time: " + startTime + " End Time: " + endTime);
            TreeItem<Room> dailyBookedRooms = new TreeItem<Room>(new Room(Integer.toString(startTime), Integer.toString(endTime), ra.getAvailRooms(theDate, theDate, startTime, endTime)));
            Root.getChildren().add(dailyBookedRooms);
            //System.out.println(bookedRooms.getValue().getTime());
            if(i == 33) {
                // System.out.println("Start Time: " + startTime + "End Time: " +endTime);
            }
            if(i == 0){
                startTime += 30;
                endTime += 70;
            }
            else if(i%2 == 0) {
                startTime += 30;
                endTime += 70;
            }
            else{
                startTime +=70;
                endTime +=30;
            }
            startTime %= 2400;
            endTime %= 2400;
        }
        //System.out.println("Start Time: " + startTime + " End Time: " + endTime);
        TreeItem<Room> bookedRooms2 = new TreeItem<Room>(new Room(Integer.toString(startTime), Integer.toString(endTime), ra.getAvailRooms(theDate, theDate, startTime, endTime)));
        Root.getChildren().add(bookedRooms2);

        //timeCol = new TreeTableColumn<Room, String>("Time");
        dailyTimeCol.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getTime());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class1Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass1()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class1Col.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        class2Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass2()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class2Col.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");

                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");

                    }
                }
            };
            return cell;
        });

        class3Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass3()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class3Col.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        class4Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass4()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class4Col.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        class5Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass5()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class5Col.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        class6Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass6()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class6Col.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        class7Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass7()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class7Col.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        class8Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass8()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class8Col.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        class9Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass9()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class9Col.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        auditorium.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isAuditorium()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        auditorium.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        dailyBookedTime.getColumns().clear();
        dailyBookedTime.getColumns().addAll(dailyTimeCol, class1Col, class2Col, class3Col, class4Col, class5Col, class6Col, class7Col, class8Col, class9Col, auditorium);
        dailyBookedTime.setTreeColumn(dailyTimeCol);
        dailyBookedTime.setRoot(Root);
        dailyBookedTime.setShowRoot(false);
        single.setLastTime();
    }


// ---------------------------------------------------------------------------------------------------------------------
//                                          WEEKLY SCHEDULE CONTROLLER
//----------------------------------------------------------------------------------------------------------------------

    @FXML
    public void weeklyTab(){
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
                        single.setLastTime();
                        controller.displayPopup();
                        single.setLastTime();

                        Stage thisStage = (Stage) roomPicker.getScene().getWindow();

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

        roomPicker.getItems().addAll("Room 1 - Computer", "Room 2 - Computer", "Room 3 - Computer", "Room 4 - Classroom", "Room 5 - Computer", "Room 6 - Classroom", "Room 7 - Computer", "Room 8 - Classroom", "Room 9 - Computer", "Mission Hall Auditorium");
    }

    @FXML
    private void viewSched(){
        changeRooms();
    }

    @FXML
    private void changeRooms(){
        loadWeekly(roomPicker.getValue().toString(), weeklyDatePicker.getValue());
    }

    public void loadWeekly(String theRoom, LocalDate theDate){
        roomPicker.setValue(theRoom);
        classroomLabel.setText(theRoom + " Weekly Schedule");
        weeklyDatePicker.setValue(theDate);

        checkAvailability(theRoom, theDate);
    }

    public void checkAvailability(String roomName, LocalDate theDate){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        weeklyBookedTime.setRoot(null);
        Root.getChildren().clear();
        RoomAccess ra = new RoomAccess();
        LocalDate givenDate = weeklyDatePicker.getValue();
        int startTime = 0;
        int endTime = 30;
        for(int i = 0; i < 47; i++){
            // System.out.println("Start Time: " + startTime + " End Time: " + endTime);
            TreeItem<WeeklyRoom> weeklyBookedRooms = new TreeItem<WeeklyRoom>(new WeeklyRoom(startTime, endTime, theDate, roomName ));
            Root.getChildren().add(weeklyBookedRooms);
            //System.out.println(bookedRooms.getValue().getTime());
            if(i == 33) {
                // System.out.println("Start Time: " + startTime + "End Time: " +endTime);
            }
            if(i == 0){
                startTime += 30;
                endTime += 70;
            }
            else if(i%2 == 0) {
                startTime += 30;
                endTime += 70;
            }
            else{
                startTime +=70;
                endTime +=30;
            }
            startTime %= 2400;
            endTime %= 2400;
        }
        //System.out.println("Start Time: " + startTime + " End Time: " + endTime);
        TreeItem<WeeklyRoom> bookedRooms = new TreeItem<WeeklyRoom>(new WeeklyRoom(startTime, endTime, theDate, roomName));
        Root.getChildren().add(bookedRooms);

        System.out.println("Trying to make weekly");

        //timeCol = new TreeTableColumn<Room, String>("Time");
        weeklyTimeCol.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof WeeklyRoom) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getTime());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        sunCol.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof WeeklyRoom) {
                if(cellData.getValue().getValue().isSunday()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        sunCol.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        monCol.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof WeeklyRoom) {
                if(cellData.getValue().getValue().isMonday()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        monCol.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        tueCol.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof WeeklyRoom) {
                if(cellData.getValue().getValue().isTuesday()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        tueCol.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        wedCol.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof WeeklyRoom) {
                if(cellData.getValue().getValue().isWednesday()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        wedCol.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        thuCol.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof WeeklyRoom) {
                if(cellData.getValue().getValue().isThursday()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        thuCol.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        friCol.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof WeeklyRoom) {
                if(cellData.getValue().getValue().isFriday()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        friCol.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        satCol.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof WeeklyRoom) {
                if(cellData.getValue().getValue().isSaturday()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        satCol.setCellFactory(column -> {
            TreeTableCell cell = new TreeTableCell<Room, Boolean>() {
                //@Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || !item){
                        setText("Occupied");
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        weeklyBookedTime.getColumns().clear();
        weeklyBookedTime.getColumns().addAll(weeklyTimeCol, sunCol, monCol, tueCol, wedCol, thuCol, friCol, satCol);
        weeklyBookedTime.setTreeColumn(weeklyTimeCol);
        weeklyBookedTime.setRoot(Root);
        weeklyBookedTime.setShowRoot(false);
        single.setLastTime();
    }


}
