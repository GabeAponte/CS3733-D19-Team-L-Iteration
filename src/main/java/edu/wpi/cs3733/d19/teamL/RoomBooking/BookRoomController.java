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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javafx.util.Duration;

public class BookRoomController {
    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private Button back;

    @FXML
    private JFXComboBox<String> availableRooms;

    @FXML
    private Label error;

    @FXML
    private Label roomName;

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
    private Button bookRoomBack;

    @FXML
    private Button viewWeekly;

    Timeline timeout;
    VisualSimulationThread sim;
    private boolean firstTimeRan = true;

    final ObservableList<String> listOfRooms = FXCollections.observableArrayList();
    ArrayList<String> rooms = new ArrayList<>();
    ArrayList<String> allRooms = new ArrayList<String>();
    ArrayList<Boolean> availRooms = new ArrayList<Boolean>();
    ArrayList<Polygon> flexSpaces = new ArrayList<Polygon>();
    private ArrayList<RoomDisplay> DisplayRooms = new ArrayList<RoomDisplay>();

    public void initialize() {
        sim = new VisualSimulationThread(86);
        sim.start();

        double room1[] = {2230, 1630, 2650, 1630, 2650, 1880, 2230, 1880};
        double room2[] = {2860, 1130, 3040, 1070, 3180, 1430, 2990, 1500};
        double room3[] = {2720, 750, 2850, 1120, 3040, 1060, 2970, 850, 3000, 840, 2970, 770, 2900, 720, 2810, 690};
        double room4[] = {2420, 180, 2610, 180, 2730, 540, 2420, 540};
        double room5[] = {2240, 755, 2540, 755, 2540, 1080, 2240, 1080};
        double room6[] = {2090, 180, 2410, 180, 2410, 540, 2090, 540};
        double room7[] = {2000, 755, 2230, 755, 2230, 1080, 2000, 1080};
        double room8[] = {1750, 180, 2080, 180, 2080, 540, 1750, 540};
        double room9[] = {1775, 710, 1840, 710, 1840, 750, 1980, 750, 1980, 930, 1775, 930};
        double auditorium[] = {2860, 1920, 2860, 2030, 3130, 2030, 3150, 2060, 3240, 2000, 3320, 1870, 3330, 1770, 3310, 1730, 3270, 1720, 3190, 1460, 3040, 1500, 3070, 1590, 3030, 1600};

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

        displayFlexSpaces(sim.getSimulation());

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
    private void switchToTable() throws IOException {
        timeout.stop();
        try{
            sim.join();
        } catch (Exception e){
            e.printStackTrace();
            sim.stop();
        }
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("BookRoom2.fxml"));
        Parent sceneMain = loader.load();
        Stage theStage = (Stage) viewSchedule.getScene().getWindow();
        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    //LocalTime startTimeValue = null;

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
            String date = datePicker.getValue().toString();
            String endDate = datePicker.getValue().plusDays(1).toString();
            date += "T" + startTime.getValue().getHour() + ":" + startTime.getValue().getMinute() + ":00";
            endDate += "T" + endTime.getValue().getHour()  + ":" + endTime.getValue().getMinute() + ":00";
            String roomName = availableRooms.getValue().toString();
            EmployeeAccess ea = new EmployeeAccess();
            String employeeID = ea.getEmployeeInformation(single.getUsername()).get(0);
            ReservationAccess roomReq = new ReservationAccess();
            RoomAccess ra = new RoomAccess();
            roomReq.makeReservation(ra.getRoomID(roomName), employeeID, date, endDate);
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
            date = datePicker.getValue().toString();
            endDate = datePicker.getValue().toString();
            date += "T" + startTime.getValue().getHour() * 100 + ":" + startTime.getValue().getMinute() + ":00";
            endDate += "T" + endTime.getValue().getHour() * 100 + ":" + endTime.getValue().getMinute() + ":00";
            availableRooms.getSelectionModel().clearSelection();

            rooms = ra.getAvailRooms(date, endDate);
            /*for (int i = 0; i < rooms.size(); i++) {
                System.out.println("Available Rooms: " + rooms.get(i));
            }*/

            listOfRooms.clear();

            for (int i = 0; i < DisplayRooms.size(); i++) {
                DisplayRooms.get(i).setAvailable(false);
            }

                //System.out.println("startTimeMil: " + startTimeMil + "\n endTimeMil:" + endTimeMil);
            rooms = ra.getAvailRooms(date, endDate);

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

    //Isabella did this

    @FXML
    public void displayAllRooms() {

        if(firstTimeRan) {
            double scaleRatio = Math.min(roomImage.getFitWidth() / roomImage.getImage().getWidth(), roomImage.getFitHeight() / roomImage.getImage().getHeight());
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
                            }
                        }
                    }
                    DisplayRooms.get(k).changePolygonColor("BLUE");
                    imagePane.getChildren().add(DisplayRooms.get(k).getPolygon());
                }
            }

        }

    };

    public void highlightFromDropdown(){

        for(int j = 0; j<DisplayRooms.size(); j++){
            if(DisplayRooms.get(j).getRoomName().equals(availableRooms.getValue())){
                for (int i = 0; i < DisplayRooms.size(); i++) {
                    if(DisplayRooms.get(i).isAvailable()){
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

    public void switchToWeekly() throws IOException {
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

        Scene scene = new Scene(sceneMain);

        Stage theStage = (Stage) viewSchedule.getScene().getWindow();
        theStage.setScene(scene);
    }

    /**@author Nathan
     * Restores previous screen
     * @throws IOException
     */
    @FXML
    private void backPressed() throws IOException{
        Singleton single = Singleton.getInstance();
        timeout.stop();
        try{
            sim.join();
        } catch (Exception e){
            e.printStackTrace();
            sim.stop();
        }
        single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);

        Memento m = single.restore();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(m.getFxml()));
        Parent sceneMain = loader.load();
        if(m.getFxml().contains("HospitalPathFinding")){
            PathFindingController pfc = loader.getController();
            pfc.initWithMeme(m.getPathPref(), m.getTypeFilter(), m.getFloorFilter(), m.getStart(), m.getEnd());
        }

        Stage theStage = (Stage) back.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    /**@author Nathan
     * Saves the memento state
     */
    private void saveState(){
        Singleton single = Singleton.getInstance();
        single.saveMemento("ActiveServiceRequests.fxml");
    }

    @FXML
    private void logOut() throws IOException {
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
        thestage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        Memento m = single.getOrig();
        root = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    private void goHome() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        saveState();
        thestage = (Stage) back.getScene().getWindow();
        AnchorPane root;
        Memento m = single.getOrig();
        root = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }


}








