package edu.wpi.cs3733.d19.teamL.RoomBooking;

import com.twilio.rest.api.v2010.account.incomingphonenumber.Local;
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
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.transform.Translate;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.util.Duration;
import org.controlsfx.control.CheckComboBox;
import org.w3c.dom.Text;

//Import APIs
import giftRequest.GiftRequest;
import foodRequest.FoodRequest;

public class BookRoomController {

    @FXML
    private TabPane tabPane;

    //Weekly Schedule Stuff ------------------------------------------------------------------------------------------

    @FXML
    private Label classroomLabel;

    @FXML
    private JFXDatePicker weeklyDatePicker;

    @FXML
    private JFXComboBox roomPicker;

    @FXML
    private TreeTableView<WeeklyRoom> weeklySchedule;

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

    private TreeItem WeeklyRoot = new TreeItem("rootxxx");

    //Daily Schedule Stuff --------------------------------------------------------------------------------------------

    @FXML
    private JFXDatePicker dailyDatePicker;

    @FXML
    private TreeTableView<Room> dailySchedule;

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

    //Visual Display Stuff --------------------------------------------------------------------------------------------

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXDatePicker endDatePicker;

    @FXML
    private JFXComboBox<String> availableRooms;

    @FXML
    private Label error;

    @FXML
    private Label roomName;

    @FXML
    private Label popupName;

    @FXML
    private JFXButton requestRoom;

    @FXML
    private JFXButton orderGifts;

    @FXML
    private JFXButton orderFood;

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
    private Pane sizingPane;

    @FXML
    private ImageView roomImage;

    @FXML
    private Button back;

    @FXML
    private Button homebtn;

    @FXML
    private AnchorPane reservationPane;

    @FXML
    private AnchorPane bookedEventPane;

    @FXML
    private AnchorPane anchorPane;

    //Event Info Pop-up ------------------------------------

    @FXML
    private Button eventInfo;

    @FXML
    private Label eventTitle;
    @FXML
    private Label roomNameLabel;
    @FXML
    private Label startTimeLabel;
    @FXML
    private Label endTimeLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label endDateLabel;
    @FXML
    private Label creatorLabel;
    @FXML
    private Label eventTypeLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label invitedEmployeesLabel;

    //Reservation Pop-up -----------------------------------
    @FXML
    private TextField eventName;

    @FXML
    private TextArea eventDescription;

    @FXML
    private RadioButton privateEvent;

    @FXML
    private JFXComboBox<String> eventType;

    @FXML
    CheckComboBox<String> eventEmployees;
    final ObservableList<String> eventEmployeeData = FXCollections.observableArrayList();

    Timeline timeout;
    private boolean firstTimeRan = true;
    private boolean resShowing = false;
    private boolean bookedEventShowing = false;
    private boolean calledFromVisualClick = false;
    String previousEvent = "";

    final ObservableList<String> listOfRooms = FXCollections.observableArrayList();
    ArrayList<String> rooms = new ArrayList<>();
    ArrayList<String> allRooms = new ArrayList<String>();
    ArrayList<Boolean> availRooms = new ArrayList<Boolean>();
    ArrayList<Polygon> flexSpaces = new ArrayList<Polygon>();
    private Singleton single;
    private ArrayList<RoomDisplay> DisplayRooms = new ArrayList<RoomDisplay>();

    public void initialize() {
        single = Singleton.getInstance();

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

        displayFlexSpaces(single.getSimulation());

        ArrayList<String> events = new ArrayList<String> (Arrays.asList("Meeting", "Party", "Conference", "Reception"));
        ObservableList obList = FXCollections.observableList(events);
        eventType.setItems(obList);

        LocalTime currentTime = LocalTime.now();
        int currentMinute = currentTime.getMinute();
        if(currentMinute < 15)
        {
            currentTime = currentTime.plusMinutes(15 - currentMinute);
        }
        else if(currentMinute >= 15 && currentMinute < 30){
            currentTime = currentTime.plusMinutes(30 - currentMinute);
        }
        else if(currentMinute >= 30 && currentMinute < 45){
            currentTime = currentTime.plusMinutes(45 - currentMinute);
        }
        else if(currentMinute >= 45 && currentMinute < 60){
            currentTime = currentTime.plusMinutes(60 - currentMinute);
        }
        startTime.setValue(currentTime);
        endTime.setValue(currentTime.plusHours(1));
        datePicker.setValue(LocalDate.now());
        single.setLastTime();
        endDatePicker.setValue(LocalDate.now());
        dailyDatePicker.setValue(LocalDate.now());
        weeklyDatePicker.setValue(LocalDate.now());
        timeout = new Timeline(new KeyFrame(Duration.millis(1500), new EventHandler<ActionEvent>() {


            @Override
            public void handle(ActionEvent event) {
                displayFlexSpaces(single.getSimulation());
                if ((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()) {
                    try {
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

                        Stage thisStage = (Stage) endTime.getScene().getWindow();
                        Screen screen = Screen.getPrimary();
                        Rectangle2D bounds = screen.getVisualBounds();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setMaximized(true);
                        thisStage.setScene(newScene);
                        thisStage.setX(bounds.getMinX());
                        thisStage.setY(bounds.getMinY());
                        thisStage.setWidth(bounds.getWidth());
                        thisStage.setHeight(bounds.getHeight());
                        timeout.stop();
                    } catch (IOException io) {
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));


        timeout.setCycleCount(Timeline.INDEFINITE);
        timeout.play();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //displayFlexSpaces();
                double width = sizingPane.getWidth() - 10;
                double height = sizingPane.getHeight() - 10;
                reservationPane.setMinSize(width, height);
                reservationPane.setPrefSize(width, height);
                reservationPane.setLayoutX(anchorPane.getWidth());
                bookedEventPane.setMinSize(width, height);
                bookedEventPane.setPrefSize(width, height);
                bookedEventPane.setLayoutX(anchorPane.getWidth());
                fieldsEntered();
            }
        });

        EmployeeAccess ea = new EmployeeAccess();
        ArrayList<ArrayList<String>> emps = ea.getEmployees("","");

        for(int i = 0; i < emps.size(); i++){
            String temp = "";
            temp = emps.get(i).get(4) + " " + emps.get(i).get(5) + " " + "(" + emps.get(i).get(8) + ")";
            eventEmployeeData.add(temp);
        }

        eventEmployees.getItems().addAll(eventEmployeeData);

        dailyDatePicker.setValue(LocalDate.now());
        findRooms();
        roomPicker.getItems().addAll("Room 1 - Computer", "Room 2 - Computer", "Room 3 - Computer", "Room 4 - Classroom", "Room 5 - Computer", "Room 6 - Classroom", "Room 7 - Computer", "Room 8 - Classroom", "Room 9 - Computer", "Mission Hall Auditorium");
        roomPicker.getSelectionModel().select(0);
        viewSched();



    }

    public void loadFromPathfind(String roomName){

    }

    @FXML
    /** @author Gabe, Nikhil
     * When a user selcts a valid start and end time and a date, they are given the option
     * to book any avaliable rooms
     */
    private void findRoom() {
        single.setLastTime();

        LocalTime startTimeValue = startTime.getValue();
        LocalTime endTimeValue = endTime.getValue();
        LocalDate roomDate = datePicker.getValue();
        LocalDate endRoomDate = endDatePicker.getValue();
        LocalDate curDate = LocalDate.now();
        LocalTime curTime = LocalTime.now();
        boolean eventIsPrivate = false;
        String eventTypeString = eventType.getSelectionModel().getSelectedItem();
        String eventNameString = eventName.getText();
        String eventDescriptionString = eventDescription.getText();
        String listOfGuests = "";

        List<String> guestList = eventEmployees.getCheckModel().getCheckedItems();
        for(int i = 0; i < guestList.size(); i ++){
            listOfGuests = listOfGuests + guestList.get(i) + ",";
        }

        System.out.println(listOfGuests);
        System.out.println(guestList);

        if(privateEvent.isSelected()){
            eventIsPrivate = true;
        }

        error.setTextFill(Color.RED);

        //Gabe - error when start time, end time, and dates are blank
        if (startTimeValue == null && endTimeValue == null && roomDate == null && endRoomDate == null) {
            error.setText("Please select start and end times and dates.");
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

        else if (eventNameString == null || eventNameString.isEmpty()) {
            error.setText("Please enter an event name.");
        }

        else if (eventDescriptionString == null || eventDescriptionString.isEmpty()) {
            error.setText("Please enter an event description.");
        }

        else if (eventTypeString == null || eventTypeString.isEmpty()) {
            error.setText("Please select an event type.");
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
            String endDate = endDatePicker.getValue().toString();
            date += "T" + String.format("%02d", startTime.getValue().getHour()) + ":" + String.format("%02d", startTime.getValue().getMinute()) + ":00";
            endDate += "T" + String.format("%02d", endTime.getValue().getHour()) + ":" + String.format("%02d", endTime.getValue().getMinute()) + ":00";
            String roomName = availableRooms.getValue().toString();
            EmployeeAccess ea = new EmployeeAccess();
            String employeeID = ea.getEmployeeInformation(single.getUsername()).get(0);
            ReservationAccess roomReq = new ReservationAccess();
            RoomAccess ra = new RoomAccess();
            roomReq.makeReservation(ra.getRoomID(roomName), employeeID, date, endDate, eventNameString, eventDescriptionString, listOfGuests, eventTypeString, eventIsPrivate);
            //add event name, event description, event type, guestList (String), privacy (boolean)
            Reservation r = new Reservation(ra.getRoomID(roomName), employeeID, date, endDate, eventNameString, eventDescriptionString, listOfGuests, eventTypeString, eventIsPrivate);
            for(int i = 0; i < listOfGuests.length(); i++){
                int idx = listOfGuests.indexOf("(", i);
                int lastidx = listOfGuests.indexOf(")", i);
                String recipient = "";
                if(idx == -1 || lastidx == -1){
                    i = Integer.MAX_VALUE;
                } else {
                    i=lastidx + 1;
                }
                for(int j = idx + 1; j < lastidx; j++){
                    recipient+=listOfGuests.charAt(j);
                }
                if(recipient != ""){
                    InviteThread it = new InviteThread(r, ea.getEmpEmail(recipient));
                    it.start();
                }
            }
            openReservation(false);
            openEventInfo(true, roomName);
            fieldsEntered();
        }
    }

    @FXML
    public void roundStartTime(){
        LocalTime selectedStartTime = startTime.getValue();
        int startMinute = selectedStartTime.getMinute();
        if(startMinute == 0 || startMinute == 60){

        }
        else if(startMinute < 8)
        {
            selectedStartTime = selectedStartTime.minusMinutes(startMinute);
            startTime.setValue(selectedStartTime);
        }
        else if(startMinute >= 8 && startMinute < 15){
            selectedStartTime = selectedStartTime.plusMinutes(15 - startMinute);
            startTime.setValue(selectedStartTime);

        }
        else if(startMinute > 15 && startMinute < 23){
            selectedStartTime = selectedStartTime.plusMinutes(15 - startMinute);
            startTime.setValue(selectedStartTime);

        }
        else if(startMinute >= 23 && startMinute < 30){
            selectedStartTime = selectedStartTime.plusMinutes(30 - startMinute);
            startTime.setValue(selectedStartTime);
        }
        else if(startMinute > 30 && startMinute < 38){
            selectedStartTime = selectedStartTime.plusMinutes(30 - startMinute);
            startTime.setValue(selectedStartTime);
        }
        else if(startMinute >= 38 && startMinute < 45){
            selectedStartTime = selectedStartTime.plusMinutes(45 - startMinute);
            startTime.setValue(selectedStartTime);
        }
        else if(startMinute > 45 && startMinute < 53){
            selectedStartTime = selectedStartTime.plusMinutes(45 - startMinute);
            startTime.setValue(selectedStartTime);
        }
        else if(startMinute >= 53 && startMinute < 60){
            selectedStartTime = selectedStartTime.minusMinutes(startMinute);
            startTime.setValue(selectedStartTime);
        }
        else{

        }
    }

    public void roundEndTime(){
        LocalTime selectedEndTime = endTime.getValue();
        int endMinute = selectedEndTime.getMinute();
        if(endMinute == 0 || endMinute == 60){

        }
        else if(endMinute < 8)
        {
            selectedEndTime = selectedEndTime.minusMinutes(endMinute);
            endTime.setValue(selectedEndTime);
        }
        else if(endMinute >= 8 && endMinute < 15){
            selectedEndTime = selectedEndTime.plusMinutes(15 - endMinute);
            endTime.setValue(selectedEndTime);

        }
        else if(endMinute > 15 && endMinute < 23){
            selectedEndTime = selectedEndTime.plusMinutes(15 - endMinute);
            endTime.setValue(selectedEndTime);

        }
        else if(endMinute >= 23 && endMinute < 30){
            selectedEndTime = selectedEndTime.plusMinutes(30 - endMinute);
            endTime.setValue(selectedEndTime);
        }
        else if(endMinute > 30 && endMinute < 38){
            selectedEndTime = selectedEndTime.plusMinutes(30 - endMinute);
            endTime.setValue(selectedEndTime);
        }
        else if(endMinute >= 38 && endMinute < 45){
            selectedEndTime = selectedEndTime.plusMinutes(45 - endMinute);
            endTime.setValue(selectedEndTime);
        }
        else if(endMinute > 45 && endMinute < 53){
            selectedEndTime = selectedEndTime.plusMinutes(45 - endMinute);
            endTime.setValue(selectedEndTime);
        }
        else if(endMinute >= 53 && endMinute < 60){
            selectedEndTime = selectedEndTime.minusMinutes(endMinute);
            endTime.setValue(selectedEndTime);
        }
        else{

        }
        fieldsEntered();
    }

    //todo checks if fields are null and populates table here
    // RA.getAvailRooms returns list of available rooms
    @FXML
    public void fieldsEntered() {
        single.setLastTime();
        RoomAccess ra = new RoomAccess();
        int startTimeMil = 0;
        int endTimeMil = 0;
        String date = "";
        String endDate = "";

        if (startTime.getValue() != null && endTime.getValue() != null && datePicker.getValue() != null) {
            date = datePicker.getValue().toString();
            endDate = endDatePicker.getValue().toString();
            date += "T" + String.format("%02d", startTime.getValue().getHour()) + ":" + String.format("%02d", startTime.getValue().getMinute()) + ":00";
            endDate += "T" + String.format("%02d", endTime.getValue().getHour()) + ":" + String.format("%02d", endTime.getValue().getMinute()) + ":00";
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

            for (int j = 0; j < rooms.size(); j++) {
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

            eventName.clear();
            eventDescription.clear();
            eventType.getSelectionModel().clearSelection();
            error.setText("");
            privateEvent.setSelected(false);
            EmployeeAccess ea = new EmployeeAccess();
            ArrayList<ArrayList<String>> emps = ea.getEmployees("","");
            for(int i = 0; i < emps.size(); i++) {
                eventEmployees.getCheckModel().clearCheck(i);
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
            double scaleRatio;
            scaleRatio = Math.min(roomImage.getFitWidth() / roomImage.getImage().getWidth(), roomImage.getFitHeight() / roomImage.getImage().getHeight());
            for (int i = 0; i < DisplayRooms.size(); i++) {
                DisplayRooms.get(i).makePolygon(scaleRatio);
                DisplayRooms.get(i).getPolygon().setOnMouseClicked(onMouseClickedEventHandler);
                //DisplayRooms.get(i).getPolygon().setOnMouseClicked(mouseRightClicked);
                DisplayRooms.get(i).getPolygon().setVisible(false);
                imagePane.getChildren().add(DisplayRooms.get(i).getPolygon());
            }
            firstTimeRan = false;
        }

        for (int i = 0; i < DisplayRooms.size(); i++) {

            if (DisplayRooms.get(i).isAvailable()) {
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
            if (!event.isSecondaryButtonDown()) {
                boolean resPaneCalled = false;
                calledFromVisualClick = true;
                displayAllRooms();
                for (int k = 0; k < DisplayRooms.size(); k++) {
                    Point2D mousePress = new Point2D(event.getX(), event.getY());
                    if (DisplayRooms.get(k).p.contains(mousePress)) {
                        imagePane.getChildren().remove(DisplayRooms.get(k).getPolygon());
                        roomName.setText(DisplayRooms.get(k).niceName);
                        fieldsEntered();
                        for (int z = 0; z < listOfRooms.size(); z++) {
                            if (DisplayRooms.get(k).getRoomName().equals(listOfRooms.get(z))) {
                                if (DisplayRooms.get(k).isAvailable()) {
                                    availableRooms.getSelectionModel().select(listOfRooms.get(z));
                                    if (popupName.getText().contains(DisplayRooms.get(k).niceName)) {
                                        resPaneCalled = true;
                                        //System.out.println("Same");
                                        if (resShowing) {
                                            openReservation(false);
                                        } else {
                                            openReservation(true);
                                        }
                                    } else {
                                        resPaneCalled = true;
                                        popupName.setText("Reserve " + DisplayRooms.get(k).niceName);
                                        //System.out.println("New");
                                        openReservation(true);
                                    }
                                }
                            }
                        }
                        if (resPaneCalled == false) {
                            if (DisplayRooms.get(k).getRoomName().equals(previousEvent)) {
                                if (bookedEventShowing) {
                                    openEventInfo(false, null);
                                } else {

                                    openEventInfo(true, DisplayRooms.get(k).getRoomName());
                                }
                            } else {
                                openEventInfo(true, DisplayRooms.get(k).getRoomName());
                            }
                        }
                        DisplayRooms.get(k).changePolygonColor("BLUE");
                        imagePane.getChildren().add(DisplayRooms.get(k).getPolygon());
                        calledFromVisualClick = false;
                    }
                }
            }

        }

    };

/*
    private EventHandler<MouseEvent> mouseRightClicked = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            if (event.isSecondaryButtonDown()) {
                System.out.println("Right Click");
                for (int k = 0; k < DisplayRooms.size(); k++) {
                    Point2D mousePress = new Point2D(event.getX(), event.getY());
                    if (DisplayRooms.get(k).p.contains(mousePress)) {
                        tabPane.getSelectionModel().select(2);
                        loadWeekly(DisplayRooms.get(k).niceName, datePicker.getValue());
                    }
                }
            }
        }
    };
*/

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
    public void displayFlexSpaces(ArrayList<Boolean> flexSpaceAvailable) {
        int i = 0;
        for (i = 0; i < flexSpaces.size(); i++) {
            imagePane.getChildren().remove(flexSpaces.get(i));
        }
        flexSpaces.clear();


        double sr = Math.min(roomImage.getFitWidth() / roomImage.getImage().getWidth(), roomImage.getFitHeight() / roomImage.getImage().getHeight());

        //large important room
        flexSpaces.add(new Polygon(860 * sr, 1270 * sr, 1160 * sr, 1270 * sr, 1160 * sr, 1580 * sr, 860 * sr, 1580 * sr));

        flexSpaces.add(new Polygon(150 * sr, 230 * sr, 200 * sr, 230 * sr, 200 * sr, 340 * sr, 150 * sr, 340 * sr)); //left side desk top
        flexSpaces.add(new Polygon(150 * sr, 420 * sr, 200 * sr, 420 * sr, 200 * sr, 530 * sr, 150 * sr, 530 * sr)); //left side desk middle
        flexSpaces.add(new Polygon(150 * sr, 590 * sr, 200 * sr, 590 * sr, 200 * sr, 740 * sr, 150 * sr, 740 * sr)); //left side desk lower
        flexSpaces.add(new Polygon(340 * sr, 560 * sr, 540 * sr, 560 * sr, 540 * sr, 710 * sr, 340 * sr, 710 * sr)); //left conference room
        flexSpaces.add(new Polygon(200 * sr, 1300 * sr, 270 * sr, 1300 * sr, 270 * sr, 1360 * sr, 200 * sr, 1360 * sr)); //small three chairs
        flexSpaces.add(new Polygon(570 * sr, 1340 * sr, 630 * sr, 1340 * sr, 630 * sr, 1430 * sr, 570 * sr, 1430 * sr)); //standalone square
        flexSpaces.add(new Polygon(1320 * sr, 2070 * sr, 1380 * sr, 2070 * sr, 1380 * sr, 2100 * sr, 1320 * sr, 2100 * sr)); //small two chairs
        flexSpaces.add(new Polygon(1980 * sr, 1690 * sr, 2230 * sr, 1690 * sr, 2230 * sr, 1880 * sr, 1980 * sr, 1880 * sr)); //bottom right room
        flexSpaces.add(new Polygon(1760 * sr, 2230 * sr, 1820 * sr, 2230 * sr, 1820 * sr, 2380 * sr, 1760 * sr, 2380 * sr)); //bottom vertical top
        flexSpaces.add(new Polygon(1740 * sr, 2440 * sr, 1800 * sr, 2440 * sr, 1800 * sr, 2600 * sr, 1740 * sr, 2600 * sr)); //bottom vertical bottom

        //top left desk bank
        flexSpaces.add(new Polygon(240 * sr, 240 * sr, 305 * sr, 240 * sr, 305 * sr, 290 * sr, 240 * sr, 290 * sr));
        flexSpaces.add(new Polygon(305 * sr, 240 * sr, 370 * sr, 240 * sr, 370 * sr, 290 * sr, 305 * sr, 290 * sr));
        flexSpaces.add(new Polygon(370 * sr, 240 * sr, 435 * sr, 240 * sr, 435 * sr, 290 * sr, 370 * sr, 290 * sr));
        flexSpaces.add(new Polygon(435 * sr, 240 * sr, 500 * sr, 240 * sr, 500 * sr, 290 * sr, 435 * sr, 290 * sr));
        flexSpaces.add(new Polygon(240 * sr, 290 * sr, 305 * sr, 290 * sr, 305 * sr, 340 * sr, 240 * sr, 340 * sr));
        flexSpaces.add(new Polygon(305 * sr, 290 * sr, 370 * sr, 290 * sr, 370 * sr, 340 * sr, 305 * sr, 340 * sr));
        flexSpaces.add(new Polygon(370 * sr, 290 * sr, 435 * sr, 290 * sr, 435 * sr, 340 * sr, 370 * sr, 340 * sr));
        flexSpaces.add(new Polygon(435 * sr, 290 * sr, 500 * sr, 290 * sr, 500 * sr, 340 * sr, 435 * sr, 340 * sr));

        //second down top left desk bank
        flexSpaces.add(new Polygon(250 * sr, 390 * sr, 313 * sr, 390 * sr, 313 * sr, 440 * sr, 250 * sr, 440 * sr));
        flexSpaces.add(new Polygon(313 * sr, 390 * sr, 375 * sr, 390 * sr, 375 * sr, 440 * sr, 313 * sr, 440 * sr));
        flexSpaces.add(new Polygon(375 * sr, 390 * sr, 438 * sr, 390 * sr, 438 * sr, 440 * sr, 375 * sr, 440 * sr));
        flexSpaces.add(new Polygon(438 * sr, 390 * sr, 500 * sr, 390 * sr, 500 * sr, 440 * sr, 438 * sr, 440 * sr));
        flexSpaces.add(new Polygon(250 * sr, 440 * sr, 313 * sr, 440 * sr, 313 * sr, 490 * sr, 250 * sr, 490 * sr));
        flexSpaces.add(new Polygon(313 * sr, 440 * sr, 375 * sr, 440 * sr, 375 * sr, 490 * sr, 313 * sr, 490 * sr));
        flexSpaces.add(new Polygon(375 * sr, 440 * sr, 438 * sr, 440 * sr, 438 * sr, 490 * sr, 375 * sr, 490 * sr));
        flexSpaces.add(new Polygon(438 * sr, 440 * sr, 500 * sr, 440 * sr, 500 * sr, 490 * sr, 438 * sr, 490 * sr));

        //center column of small rooms
        flexSpaces.add(new Polygon(660 * sr, 660 * sr, 730 * sr, 660 * sr, 730 * sr, 748 * sr, 660 * sr, 748 * sr));
        flexSpaces.add(new Polygon(660 * sr, 748 * sr, 730 * sr, 748 * sr, 730 * sr, 836 * sr, 660 * sr, 836 * sr));
        flexSpaces.add(new Polygon(660 * sr, 836 * sr, 730 * sr, 836 * sr, 730 * sr, 924 * sr, 660 * sr, 924 * sr));
        flexSpaces.add(new Polygon(660 * sr, 924 * sr, 730 * sr, 924 * sr, 730 * sr, 1012 * sr, 660 * sr, 1012 * sr));
        flexSpaces.add(new Polygon(660 * sr, 1012 * sr, 730 * sr, 1012 * sr, 730 * sr, 1100 * sr, 660 * sr, 1100 * sr));

        //upper vertical column of desks - right
        flexSpaces.add(new Polygon(1410 * sr, 190 * sr, 1460 * sr, 190 * sr, 1460 * sr, 260 * sr, 1410 * sr, 260 * sr));
        flexSpaces.add(new Polygon(1410 * sr, 260 * sr, 1460 * sr, 260 * sr, 1460 * sr, 322 * sr, 1410 * sr, 322 * sr));
        flexSpaces.add(new Polygon(1410 * sr, 322 * sr, 1460 * sr, 322 * sr, 1460 * sr, 388 * sr, 1410 * sr, 388 * sr));
        flexSpaces.add(new Polygon(1410 * sr, 388 * sr, 1460 * sr, 388 * sr, 1460 * sr, 454 * sr, 1410 * sr, 454 * sr));
        flexSpaces.add(new Polygon(1410 * sr, 454 * sr, 1460 * sr, 454 * sr, 1460 * sr, 520 * sr, 1410 * sr, 520 * sr));

        //upper vertical column of desks - left
        flexSpaces.add(new Polygon(1360 * sr, 270 * sr, 1410 * sr, 270 * sr, 1410 * sr, 333 * sr, 1360 * sr, 333 * sr));
        flexSpaces.add(new Polygon(1360 * sr, 333 * sr, 1410 * sr, 333 * sr, 1410 * sr, 395 * sr, 1360 * sr, 395 * sr));
        flexSpaces.add(new Polygon(1360 * sr, 395 * sr, 1410 * sr, 395 * sr, 1410 * sr, 458 * sr, 1360 * sr, 458 * sr));
        flexSpaces.add(new Polygon(1360 * sr, 458 * sr, 1410 * sr, 458 * sr, 1410 * sr, 520 * sr, 1360 * sr, 520 * sr));

        //center horizontal desks left
        flexSpaces.add(new Polygon(1240 * sr, 1540 * sr, 1303 * sr, 1540 * sr, 1303 * sr, 1590 * sr, 1240 * sr, 1590 * sr));
        flexSpaces.add(new Polygon(1303 * sr, 1540 * sr, 1367 * sr, 1540 * sr, 1367 * sr, 1590 * sr, 1303 * sr, 1590 * sr));
        flexSpaces.add(new Polygon(1367 * sr, 1540 * sr, 1430 * sr, 1540 * sr, 1430 * sr, 1590 * sr, 1367 * sr, 1590 * sr));
        flexSpaces.add(new Polygon(1240 * sr, 1590 * sr, 1303 * sr, 1590 * sr, 1303 * sr, 1640 * sr, 1240 * sr, 1640 * sr));
        flexSpaces.add(new Polygon(1303 * sr, 1590 * sr, 1367 * sr, 1590 * sr, 1367 * sr, 1640 * sr, 1303 * sr, 1640 * sr));
        flexSpaces.add(new Polygon(1367 * sr, 1590 * sr, 1430 * sr, 1590 * sr, 1430 * sr, 1640 * sr, 1367 * sr, 1640 * sr));

        //center horizontal desks right
        flexSpaces.add(new Polygon(1490 * sr, 1540 * sr, 1553 * sr, 1540 * sr, 1553 * sr, 1590 * sr, 1490 * sr, 1590 * sr));
        flexSpaces.add(new Polygon(1553 * sr, 1540 * sr, 1617 * sr, 1540 * sr, 1617 * sr, 1590 * sr, 1553 * sr, 1590 * sr));
        flexSpaces.add(new Polygon(1617 * sr, 1540 * sr, 1680 * sr, 1540 * sr, 1680 * sr, 1590 * sr, 1617 * sr, 1590 * sr));
        flexSpaces.add(new Polygon(1490 * sr, 1590 * sr, 1553 * sr, 1590 * sr, 1553 * sr, 1640 * sr, 1490 * sr, 1640 * sr));
        flexSpaces.add(new Polygon(1553 * sr, 1590 * sr, 1617 * sr, 1590 * sr, 1617 * sr, 1640 * sr, 1553 * sr, 1640 * sr));
        flexSpaces.add(new Polygon(1617 * sr, 1590 * sr, 1680 * sr, 1590 * sr, 1680 * sr, 1640 * sr, 1617 * sr, 1640 * sr));

        //center vertical column of desks
        flexSpaces.add(new Polygon(1520 * sr, 1190 * sr, 1570 * sr, 1190 * sr, 1570 * sr, 1252 * sr, 1520 * sr, 1252 * sr));
        flexSpaces.add(new Polygon(1520 * sr, 1252 * sr, 1570 * sr, 1252 * sr, 1570 * sr, 1314 * sr, 1520 * sr, 1314 * sr));
        flexSpaces.add(new Polygon(1520 * sr, 1314 * sr, 1570 * sr, 1314 * sr, 1570 * sr, 1376 * sr, 1520 * sr, 1376 * sr));
        flexSpaces.add(new Polygon(1520 * sr, 1376 * sr, 1570 * sr, 1376 * sr, 1570 * sr, 1438 * sr, 1520 * sr, 1438 * sr));
        flexSpaces.add(new Polygon(1520 * sr, 1438 * sr, 1570 * sr, 1438 * sr, 1570 * sr, 1500 * sr, 1520 * sr, 1500 * sr));
        flexSpaces.add(new Polygon(1570 * sr, 1190 * sr, 1620 * sr, 1190 * sr, 1620 * sr, 1252 * sr, 1570 * sr, 1252 * sr));
        flexSpaces.add(new Polygon(1570 * sr, 1252 * sr, 1620 * sr, 1252 * sr, 1620 * sr, 1314 * sr, 1570 * sr, 1314 * sr));
        flexSpaces.add(new Polygon(1570 * sr, 1314 * sr, 1620 * sr, 1314 * sr, 1620 * sr, 1376 * sr, 1570 * sr, 1376 * sr));
        flexSpaces.add(new Polygon(1570 * sr, 1376 * sr, 1620 * sr, 1376 * sr, 1620 * sr, 1438 * sr, 1570 * sr, 1438 * sr));
        flexSpaces.add(new Polygon(1570 * sr, 1438 * sr, 1620 * sr, 1438 * sr, 1620 * sr, 1500 * sr, 1570 * sr, 1500 * sr));

        //bottom right vertical column of desks - left
        flexSpaces.add(new Polygon(1900 * sr, 1890 * sr, 1950 * sr, 1890 * sr, 1950 * sr, 1955 * sr, 1900 * sr, 1955 * sr));
        flexSpaces.add(new Polygon(1900 * sr, 1955 * sr, 1950 * sr, 1955 * sr, 1950 * sr, 2020 * sr, 1900 * sr, 2020 * sr));
        flexSpaces.add(new Polygon(1900 * sr, 2020 * sr, 1950 * sr, 2020 * sr, 1950 * sr, 2085 * sr, 1900 * sr, 2085 * sr));
        flexSpaces.add(new Polygon(1900 * sr, 2085 * sr, 1950 * sr, 2085 * sr, 1950 * sr, 2150 * sr, 1900 * sr, 2150 * sr));
        flexSpaces.add(new Polygon(1950 * sr, 1890 * sr, 2000 * sr, 1890 * sr, 2000 * sr, 1955 * sr, 1950 * sr, 1955 * sr));
        flexSpaces.add(new Polygon(1950 * sr, 1955 * sr, 2000 * sr, 1955 * sr, 2000 * sr, 2020 * sr, 1950 * sr, 2020 * sr));
        flexSpaces.add(new Polygon(1950 * sr, 2020 * sr, 2000 * sr, 2020 * sr, 2000 * sr, 2085 * sr, 1950 * sr, 2085 * sr));
        flexSpaces.add(new Polygon(1950 * sr, 2085 * sr, 2000 * sr, 2085 * sr, 2000 * sr, 2150 * sr, 1950 * sr, 2150 * sr));

        //bottom right vertical column of desks - right
        flexSpaces.add(new Polygon(2040 * sr, 1890 * sr, 2090 * sr, 1890 * sr, 2090 * sr, 1955 * sr, 2040 * sr, 1955 * sr));
        flexSpaces.add(new Polygon(2040 * sr, 1955 * sr, 2090 * sr, 1955 * sr, 2090 * sr, 2020 * sr, 2040 * sr, 2020 * sr));
        flexSpaces.add(new Polygon(2040 * sr, 2020 * sr, 2090 * sr, 2020 * sr, 2090 * sr, 2085 * sr, 2040 * sr, 2085 * sr));
        flexSpaces.add(new Polygon(2040 * sr, 2085 * sr, 2090 * sr, 2085 * sr, 2090 * sr, 2150 * sr, 2040 * sr, 2150 * sr));
        flexSpaces.add(new Polygon(2090 * sr, 1890 * sr, 2140 * sr, 1890 * sr, 2140 * sr, 1955 * sr, 2090 * sr, 1955 * sr));
        flexSpaces.add(new Polygon(2090 * sr, 1955 * sr, 2140 * sr, 1955 * sr, 2140 * sr, 2020 * sr, 2090 * sr, 2020 * sr));
        flexSpaces.add(new Polygon(2090 * sr, 2020 * sr, 2140 * sr, 2020 * sr, 2140 * sr, 2085 * sr, 2090 * sr, 2085 * sr));
        flexSpaces.add(new Polygon(2090 * sr, 2085 * sr, 2140 * sr, 2085 * sr, 2140 * sr, 2150 * sr, 2090 * sr, 2150 * sr));

        //bottom right vertical rooms
        flexSpaces.add(new Polygon(2220 * sr, 2220 * sr, 2290 * sr, 2220 * sr, 2290 * sr, 2340 * sr, 2220 * sr, 2340 * sr));
        flexSpaces.add(new Polygon(2220 * sr, 2340 * sr, 2290 * sr, 2340 * sr, 2290 * sr, 2430 * sr, 2220 * sr, 2430 * sr));
        flexSpaces.add(new Polygon(2220 * sr, 2430 * sr, 2290 * sr, 2430 * sr, 2290 * sr, 2515 * sr, 2220 * sr, 2515 * sr));
        flexSpaces.add(new Polygon(2220 * sr, 2515 * sr, 2290 * sr, 2515 * sr, 2290 * sr, 2620 * sr, 2220 * sr, 2620 * sr));

        //little rooms above center room
        flexSpaces.add(new Polygon(860 * sr, 1190 * sr, 960 * sr, 1190 * sr, 960 * sr, 1270 * sr, 860 * sr, 1270 * sr));
        flexSpaces.add(new Polygon(960 * sr, 1190 * sr, 1060 * sr, 1190 * sr, 1060 * sr, 1270 * sr, 960 * sr, 1270 * sr));
        flexSpaces.add(new Polygon(1060 * sr, 1190 * sr, 1160 * sr, 1190 * sr, 1160 * sr, 1270 * sr, 1060 * sr, 1270 * sr));

        for (i = 1; i < flexSpaces.size(); i++) {
            if (flexSpaceAvailable.get(i)) {
                flexSpaces.get(i).setStroke(Color.web("TURQUOISE"));
                flexSpaces.get(i).setFill(Color.web("TURQUOISE"));
                flexSpaces.get(i).setOpacity(0.5);
            }else{
                flexSpaces.get(i).setStroke(Color.web("ORANGERED"));
                flexSpaces.get(i).setFill(Color.web("ORANGERED"));
                flexSpaces.get(i).setOpacity(0.5);
            }
            imagePane.getChildren().add(flexSpaces.get(i));
        }
        if (single.isFree()) {
            //System.out.println("Set T");
            flexSpaces.get(0).setStroke(Color.web("TURQUOISE"));
            flexSpaces.get(0).setFill(Color.web("TURQUOISE"));
            flexSpaces.get(0).setOpacity(0.5);
        }
        else {
            //System.out.println("Set R");
            flexSpaces.get(0).setStroke(Color.web("ORANGERED"));
            flexSpaces.get(0).setFill(Color.web("ORANGERED"));
            flexSpaces.get(0).setOpacity(0.5);
        }
        imagePane.getChildren().add(flexSpaces.get(0));
    }

    /**@author Nathan
     * Restores previous screen
     */
    @FXML
    private void backPressed(ActionEvent event) throws IOException {
        timeout.stop();
        single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);

        Memento m = single.restore();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(m.getFxml()));
        Parent newPage = loader.load();
        if (m.getFxml().contains("HospitalPathFinding")) {
            PathFindingController pfc = loader.getController();
            pfc.initWithMeme(m.getPathPref(), m.getTypeFilter(), m.getFloorFilter(), m.getStart(), m.getEnd());
        }

        //Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    /**
     * @author Nathan
     * Saves the memento state
     */
    private void saveState() {
        Singleton single = Singleton.getInstance();
        single.saveMemento("ActiveServiceRequests.fxml");
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
        //openNav.setToX(0.0D);
        TranslateTransition closeNav = new TranslateTransition(new Duration(400.0D), this.reservationPane);
        if (open == true){
            System.out.println(reservationPane.getWidth());
            openNav.setToX(-this.anchorPane.getWidth()+this.sizingPane.getLayoutX());
            openNav.play();
            resShowing = true;
            openEventInfo(false, null);
            callServiceRequests();
            //System.out.println("ResShowing = true");
        } else {
            closeNav.setToX(-this.anchorPane.getWidth()+this.reservationPane.getWidth()+sizingPane.getLayoutX()+sizingPane.getWidth());
            closeNav.play();
            resShowing = false;
            //System.out.println("ResShowing = false");
        }
    }

    private void callServiceRequests() {
        orderFood.setOnAction((evt) -> {
            FoodRequest foodRequest = new FoodRequest();
            try{
                Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
                foodRequest.run(0,0,(int)bounds.getWidth(),(int)bounds.getHeight(),null,null,null);
            }catch (Exception e){
                System.out.println("Failed to run API");
                e.printStackTrace();
            }
        });

        orderGifts.setOnAction((evt) -> {
            GiftRequest gr = new GiftRequest();
            try{
                Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
                gr.run(0,0,(int)bounds.getWidth(),(int)bounds.getHeight(), "",null,null);
            }catch (Exception e){
                System.out.println("Failed to run API");
                e.printStackTrace();
            }
        });
    }

    /** @author Isabella
     * Slides in the event information menu from the right side
     */
    private void openEventInfo(boolean open, String roomName) {  //Pass in the room name as a parameter here

        previousEvent = roomName;

        String date = datePicker.getValue().toString();
        String endDate = endDatePicker.getValue().toString();
        date += "T" + String.format("%02d", startTime.getValue().getHour()) + ":" + String.format("%02d", startTime.getValue().getMinute()) + ":00";
        endDate += "T" + String.format("%02d", endTime.getValue().getHour()) + ":" + String.format("%02d", endTime.getValue().getMinute()) + ":00";
        RoomAccess ra = new RoomAccess();

        ArrayList<String[]> data = ra.getReservations(date, endDate, roomName);

        TranslateTransition openNav = new TranslateTransition(new Duration(400.0D), this.bookedEventPane);
        openNav.setToX(0.0D);
        TranslateTransition closeNav = new TranslateTransition(new Duration(400.0D), this.bookedEventPane);
        if (open == true){

            EmployeeAccess ea = new EmployeeAccess();
            ArrayList<String> empAccess = ea.getEmployeeInformation(Singleton.getUsername());
            String temp = empAccess.get(0);

            if((data.get(0)[1]).equals(temp)){
                eventInfo.setVisible(true);
                eventInfo.setText("Cancel Reservation");
            }else if (data.get(0)[5].contains(Singleton.getUsername())) {
                eventInfo.setVisible(false);
            } else{
                eventInfo.setVisible(true);
                eventInfo.setText("Request Event Access");
            }

            if(((data.get(0)[6]).equals("1") && !(data.get(0)[1]).equals(temp))){
                System.out.println("PRIVATE EVENT");
                eventTitle.setText("Private Event");
                creatorLabel.setText("Creator: Private");
                eventTypeLabel.setText("Event type: Private");
                descriptionLabel.setText("Description: Private");
                invitedEmployeesLabel.setText("Invited Employees: Private");
            }else{
                eventTitle.setText(data.get(0)[2]);
                creatorLabel.setText("Creator: " + data.get(0)[1]);
                eventTypeLabel.setText("Event type: " + data.get(0)[4]);
                descriptionLabel.setText("Description: " + data.get(0)[3]);
                invitedEmployeesLabel.setText("Invited Employees: " + data.get(0)[5]);
            }
            roomNameLabel.setText("Room name: " + roomName);
            startTimeLabel.setText("Start date: " + data.get(0)[7].substring(0,10));
            endTimeLabel.setText("End date: " + data.get(0)[8].substring(0,10));
            startDateLabel.setText("Start time: " + data.get(0)[7].substring(11));
            endDateLabel.setText("End time: " + data.get(0)[8].substring(11));

            openNav.setToX(-this.anchorPane.getWidth()+this.sizingPane.getLayoutX());
            openNav.play();
            openReservation(false);
            bookedEventShowing = true;
        } else {
            closeNav.setToX(-this.anchorPane.getWidth()+this.reservationPane.getWidth()+sizingPane.getLayoutX()+sizingPane.getWidth());
            closeNav.play();
            bookedEventShowing = false;
        }
    }

    public void editEventInfo(){
        ReservationAccess ra = new ReservationAccess();
        RoomAccess roa = new RoomAccess();
        String eventStartDate = datePicker.getValue().toString() + "T" + String.format("%02d", startTime.getValue().getHour()) + ":" + String.format("%02d", startTime.getValue().getMinute()) + ":00";
        String eventEndDate = endDatePicker.getValue().toString() + "T" + String.format("%02d", endTime.getValue().getHour()) + ":" + String.format("%02d", endTime.getValue().getMinute()) + ":00";
        ArrayList<String[]> data = roa.getReservations(eventStartDate, eventEndDate, roomName.getText());
        String[] info = data.get(0);
        Singleton single = Singleton.getInstance();
        if(eventInfo.getText().equals("Cancel Reservation")){
            ra.deleteReservation(info[0], info[1], info[7], info[8]);
            fieldsEntered();
            openEventInfo(false, "");
        }else{
            String sender = Singleton.getUsername();
            boolean privateEventInfo = false;
            if((info[6]).equals("1")){
                privateEventInfo = true;
            }//rID, eID, sdate, endate, title, desc, type, isPrivate
            Reservation thisEvent = new Reservation (info[0], info[1], info[7], info[8], info[2], info[3], info[5], info[4], privateEventInfo);
            EmployeeAccess ea = new EmployeeAccess();
            String recipient = ea.getEmpEmail(ea.getEmployeeUsername(info[1]));
            AddToEventThread emailEvent = new AddToEventThread(thisEvent, recipient, sender);
            emailEvent.start();
        }
    }

//----------------------------------------------------------------------------------------------------------------------
//                                          BOOK ROOM 2 CONTROLLER
//----------------------------------------------------------------------------------------------------------------------
//todo edit this portion of the vode to allow for the booking across days
    @FXML
    private void dailyTab() {
        openReservation(false);
        openEventInfo(false, null);
    }

    @FXML
    private void findRooms() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        dailySchedule.setRoot(null);
        Root.getChildren().clear();
        RoomAccess ra = new RoomAccess();
        String theDate;
        String endDate;
        LocalTime startLT = LocalTime.of(0,0);
        LocalTime endLT = LocalTime.of(0, 30);
        for(int i = 0; i < 48; i++){
            theDate = dailyDatePicker.getValue().toString() + "T" + String.format("%02d",startLT.getHour()) + ":" + String.format("%02d",startLT.getMinute()) + ":00";
            endDate = dailyDatePicker.getValue().toString() + "T" + String.format("%02d",endLT.getHour()) + ":" + String.format("%02d",endLT.getMinute()) + ":00";
            // System.out.println("Start Time: " + startTime + " End Time: " + endTime);
            TreeItem<Room> bookedRooms = new TreeItem<Room>(new Room(startLT, endLT, ra.getAvailRooms(theDate, endDate)));
            Root.getChildren().add(bookedRooms);

            if(i == 46){
                startLT = startLT.plusMinutes(30);
                endLT = endLT.plusMinutes(29);
            }
            else {
                startLT = startLT.plusMinutes(30);
                endLT = endLT.plusMinutes(30);
            }
        }

        //dailyTimeCol = new TreeTableColumn<Room, String>("Time");
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
                        setStyle("-fx-background-color: ee5253");
                        setText("Occupied");
                    } else {
                        setStyle("-fx-background-color: #7bed9f");
                        setText("Available");
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
                        setStyle("-fx-background-color: ee5253");

                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: #7bed9f");

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
                        setStyle("-fx-background-color: ee5253");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: #7bed9f");
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
                        setStyle("-fx-background-color: ee5253");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: #7bed9f");
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
                        setStyle("-fx-background-color: ee5253");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: #7bed9f");
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
                        setStyle("-fx-background-color: ee5253");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: #7bed9f");
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
                        setStyle("-fx-background-color: ee5253");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: #7bed9f");
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
                        setStyle("-fx-background-color: ee5253");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: #7bed9f");
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
                        setStyle("-fx-background-color: ee5253");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: #7bed9f");
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
                        setStyle("-fx-background-color: ee5253");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: #7bed9f");
                    }
                }
            };
            return cell;
        });

        dailySchedule.getColumns().clear();
        dailySchedule.getColumns().addAll(dailyTimeCol, class1Col, class2Col, class3Col, class4Col, class5Col, class6Col, class7Col, class8Col, class9Col, auditorium);
        dailySchedule.setTreeColumn(dailyTimeCol);
        dailySchedule.setRoot(Root);
        dailySchedule.setShowRoot(false);
        single.setLastTime();
    }


// ---------------------------------------------------------------------------------------------------------------------
//                                          WEEKLY SCHEDULE CONTROLLER
//----------------------------------------------------------------------------------------------------------------------
//todo edit this portion of the vode to allow for the booking across days
    @FXML
    private void weeklyTab(){
        openReservation(false);
        openEventInfo(false, null);
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
        single.setLastTime();
        weeklySchedule.setRoot(null);
        WeeklyRoot.getChildren().clear();
        RoomAccess ra = new RoomAccess();
        LocalDate givenDate = weeklyDatePicker.getValue();
        LocalTime startLT = LocalTime.of(0,0);
        LocalTime endLT = LocalTime.of(0, 30);
        for(int i = 0; i < 48; i++) {
            //System.out.println("Start Time: " + startLT + " End Time: " + endLT);
            TreeItem<WeeklyRoom> bookedRooms = new TreeItem<WeeklyRoom>(new WeeklyRoom(startLT, endLT, theDate, roomName));
            WeeklyRoot.getChildren().add(bookedRooms);

            if(i == 46){
                startLT = startLT.plusMinutes(30);
                endLT = endLT.plusMinutes(29);
            }
            else{
                startLT = startLT.plusMinutes(30);
                endLT = endLT.plusMinutes(30);

            }
        }
        //weeklyTimeCol = new TreeTableColumn<Room, String>("Time");
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
                        setStyle("-fx-background-color: ee5253");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: #7bed9f");
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
                        setStyle("-fx-background-color: ee5253");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: #7bed9f");
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
                        setStyle("-fx-background-color: ee5253");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: #7bed9f");
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
                        setStyle("-fx-background-color: ee5253");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: #7bed9f");
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
                        setStyle("-fx-background-color: ee5253");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: #7bed9f");
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
                        setStyle("-fx-background-color: ee5253");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: #7bed9f");
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
                        setStyle("-fx-background-color: ee5253");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: #7bed9f");
                    }
                }
            };
            return cell;
        });

        weeklySchedule.getColumns().clear();
        weeklySchedule.getColumns().addAll(weeklyTimeCol, sunCol, monCol, tueCol, wedCol, thuCol, friCol, satCol);
        weeklySchedule.setTreeColumn(weeklyTimeCol);
        weeklySchedule.setRoot(WeeklyRoot);
        weeklySchedule.setShowRoot(false);
        single.setLastTime();
    }

    public void loadWithRoomSelected(String roomName){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fieldsEntered();
                if(availableRooms.getItems().contains(roomName)) {
                    availableRooms.getSelectionModel().select(roomName);
                    openReservation(true);
                }
                else{
                    openEventInfo(true, roomName);
                }
            }
        });
    }

}