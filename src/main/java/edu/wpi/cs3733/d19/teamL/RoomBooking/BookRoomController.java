package edu.wpi.cs3733.d19.teamL.RoomBooking;

import edu.wpi.cs3733.d19.teamL.Account.EmployeeAccess;
import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import edu.wpi.cs3733.d19.teamL.Map.ImageInteraction.SceneGestures;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    private JFXDatePicker datePicker1;

    @FXML
    private JFXComboBox<String> availableRooms;

    @FXML
    private Label error;

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

    Timeline timeout;
    private boolean firstTimeRan = true;

    final ObservableList<String> listOfRooms = FXCollections.observableArrayList();
    ArrayList<String> rooms = new ArrayList<>();
    ArrayList<String> allRooms = new ArrayList<String>();
    ArrayList<Boolean> availRooms = new ArrayList<Boolean>();
    private SceneGestures sceneGestures;
    private ArrayList<RoomDisplay> DisplayRooms = new ArrayList<RoomDisplay>();

    public void initialize() {

        double room1[] = {};
        double room2[] = {};
        double room3[] = {};
        double room4[] = {2420, 180, 2610, 180, 2730, 540, 2420, 540};
        double room5[] = {};
        double room6[] = {2090, 180, 2410, 180, 2410, 540, 2090, 540};
        double room7[] = {};
        double room8[] = {1750, 180, 2080, 180, 2080, 540, 1750, 540};
        double room9[] = {1775, 710, 1840, 710, 1840, 750, 1980, 750, 1980, 930, 1775, 930};
        double auditorium[] = {};

        DisplayRooms.add(new RoomDisplay("Classroom 1 (Computer)", room1));
        DisplayRooms.add(new RoomDisplay("Classroom 2 (Computer)", room2));
        DisplayRooms.add(new RoomDisplay("Classroom 3 (Computer)", room3));
        DisplayRooms.add(new RoomDisplay("Classroom 4 (Classroom)", room4));
        DisplayRooms.add(new RoomDisplay("Classroom 5 (Computer)", room5));
        DisplayRooms.add(new RoomDisplay("Classroom 6 (Classroom)", room6));
        DisplayRooms.add(new RoomDisplay("Classroom 7 (Computer)", room7));
        DisplayRooms.add(new RoomDisplay("Classroom 8 (Classroom)", room8));
        DisplayRooms.add(new RoomDisplay("Classroom 9 (Computer)", room9));
        DisplayRooms.add(new RoomDisplay("Mission Hall Auditorium", auditorium));

        roomImage.fitWidthProperty().bind(imagePane.widthProperty());
        roomImage.fitHeightProperty().bind(imagePane.heightProperty());

        startTime.setValue(LocalTime.now());
        endTime.setValue(LocalTime.now().plusHours(1));
        datePicker.setValue(LocalDate.now());
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
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
        //fieldsEntered();

    }

    @FXML
    private void backPressed() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EmployeeLoggedInHome.fxml"));
        if (single.isIsAdmin()) {
            loader = new FXMLLoader(getClass().getClassLoader().getResource("AdminLoggedInHome.fxml"));
        }
        Parent sceneMain = loader.load();
        Stage theStage = (Stage) bookRoomBack.getScene().getWindow();
        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void switchToTable() throws IOException {
        timeout.stop();
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
        } else if (startTimeValue.compareTo(curTime) < 0 && roomDate.equals(curDate)) {
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
//            for(int i = 1; i < rooms.size(); i+=2) {
//                if (rooms.get(i).equals(availableRooms.getValue())) {
//                    roomID = rooms.get(i - 1);
//                }
//            }
            // System.out.println("Start Time (MIL): " + startTimeMil + "End Time (MIL): " + endTimeMil);
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

        if (startTime.getValue() != null && endTime != null && datePicker.getValue() != null) {
            startTimeMil = startTime.getValue().getHour() * 100 + startTime.getValue().getMinute();
            endTimeMil = endTime.getValue().getHour() * 100 + endTime.getValue().getMinute();
            date = datePicker.getValue().toString();
            availableRooms.getSelectionModel().clearSelection();

            rooms = ra.getAvailRooms(date, date, startTimeMil, endTimeMil);
            for (int i = 0; i < rooms.size(); i++) {
                //System.out.println("Available Rooms: " + rooms.get(i));
            }

            listOfRooms.clear();

            for (int i = 0; i < DisplayRooms.size(); i++) {
                DisplayRooms.get(i).setAvailable(false);
            }

                //System.out.println("startTimeMil: " + startTimeMil + "\n endTimeMil:" + endTimeMil);
            rooms = ra.getAvailRooms(date, date, startTimeMil, endTimeMil);

            for(int j = 0; j < rooms.size(); j ++) {
                listOfRooms.add(rooms.get(j));
                for (int i = 0; i < DisplayRooms.size(); i++) {

                    if (DisplayRooms.get(i).roomName.equals(rooms.get(j))) {
                        DisplayRooms.get(i).setAvailable(true);
                        System.out.println("True: " + i);
                        break;
                    }
                }
            }

            availableRooms.setItems(listOfRooms);
            displayAllRooms();
        }
    }

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
            //System.out.println(DisplayRooms.get(i).getPolygon());
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
                    fieldsEntered();
                    availableRooms.getSelectionModel().select(listOfRooms.get(k));
                    DisplayRooms.get(k).changePolygonColor("BLUE");
                    imagePane.getChildren().add(DisplayRooms.get(k).getPolygon());
                    //System.out.println("Room" + (DisplayRooms.get(k).getRoomName()));
                }
            }

        }

    };

}






