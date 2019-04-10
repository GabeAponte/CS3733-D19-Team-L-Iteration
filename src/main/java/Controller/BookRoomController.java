package Controller;

import Access.EmployeeAccess;
import Access.ReservationAccess;
import Access.RoomAccess;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.print.Book;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import Object.*;
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

    final ObservableList<String> listOfRooms = FXCollections.observableArrayList();
    ArrayList<String> rooms = new ArrayList<>();
    //ArrayList<ArrayList<String>> myLocations = new ArrayList<ArrayList<String>>();
    private SceneGestures sceneGestures;
    private ArrayList<Circle> circles = new ArrayList<Circle>();
    private ArrayList<String> reverseListOfRooms = new ArrayList<String>();

    @FXML
    public void adjustEndDate(){
        if(startTime.getValue().isAfter(LocalTime.NOON)&&(endTime.getValue().isBefore(LocalTime.NOON))){
                datePicker1.setValue(LocalDate.now().plusDays(1));
        }else{
            datePicker1.setValue(LocalDate.now());
        }
        fieldsEntered();
    }

    public void initialize(){
        roomImage.fitWidthProperty().bind(imagePane.widthProperty());
        roomImage.fitHeightProperty().bind(imagePane.heightProperty());
        startTime.setValue(LocalTime.now());
        endTime.setValue(LocalTime.now().plusHours(1));
        datePicker.setValue(LocalDate.now());
        datePicker1.setValue(LocalDate.now());
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("checking if");
                if((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()){
                    System.out.println("if successfull");
                    try{
                        single.setLastTime();
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        backPressed();
                    } catch (IOException io){
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));
        timeout.setCycleCount(Timeline.INDEFINITE);
        timeout.play();
        fieldsEntered();
    }

    @FXML
    private void backPressed() throws IOException {
        timeout.stop();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));
        Parent sceneMain = loader.load();
        LoggedInHomeController controller = loader.<LoggedInHomeController>getController();
        Stage theStage = (Stage) bookRoomBack.getScene().getWindow();
        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void switchToTable() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("BookRoom2.fxml"));
        Parent sceneMain = loader.load();
        BookRoom2Controller controller = loader.<BookRoom2Controller>getController();
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
    private void findRoom(ActionEvent event) {
        Singleton single = Singleton.getInstance();
        single.setLastTime();

        LocalTime startTimeValue = startTime.getValue();
        LocalTime endTimeValue = endTime.getValue();
        LocalDate roomDate = datePicker.getValue();
        LocalDate endRoomDate = datePicker1.getValue();
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
            error.setText("Please select an end time and a date.");}

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
        }
        else if (startTimeValue.equals(endTimeValue)) {
            error.setText("Times cannot be the same.");
        }
        else if (startTimeValue.compareTo(endTimeValue) > 0 && roomDate.equals(endRoomDate)) {
            error.setText("Start time cannot be after end time.");
        }
        else if (startTimeValue.compareTo(curTime) < 0 && roomDate.equals(curDate)) {
            error.setText("Please select a current or future time for today.");
        }
        else if (roomDate.compareTo(curDate) < 0) {
            error.setText("Please select a time for today or a future day.");
        }
        else if (availableRooms.getValue() == null) {
            error.setText("Please pick a room.");
        }
        else {
            error.setTextFill(Color.WHITE);
            error.setText("Room booked.");
            int startTimeMil = startTime.getValue().getHour() * 100 + startTime.getValue().getMinute();
            int endTimeMil = endTime.getValue().getHour() * 100 + endTime.getValue().getMinute();
            String date = datePicker.getValue().toString();
            String endDate;
            if(datePicker1.getValue() == null){
                endDate = date;
            }
            else {
                endDate = datePicker1.getValue().toString();
            }
            String roomID = "RoomTest";
            EmployeeAccess ea = new EmployeeAccess();
            String employeeID = ea.getEmployeeInformation(single.getUsername()).get(0);
            ReservationAccess roomReq = new ReservationAccess();
            for(int i = 1; i < rooms.size(); i+=2) {
                if (rooms.get(i).equals(availableRooms.getValue())) {
                    roomID = rooms.get(i - 1);
                }
            }
            roomReq.makeReservation(roomID, employeeID, date, endDate, startTimeMil, endTimeMil);
        }
    }

    @FXML
    public void fieldsEntered(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        RoomAccess ra = new RoomAccess();
        int startTimeMil = 0;
        int endTimeMil = 0;
        String date = "";
        String endDate = "";

        if(startTime.getValue() != null && endTime != null && datePicker.getValue() != null && datePicker1.getValue() != null){
            startTimeMil = startTime.getValue().getHour() * 100 + startTime.getValue().getMinute();
            endTimeMil = endTime.getValue().getHour() * 100 + endTime.getValue().getMinute();
            date = datePicker.getValue().toString();
            endDate = datePicker1.getValue().toString();
            availableRooms.getSelectionModel().clearSelection();

            rooms = ra.getAvailRooms(date, date, startTimeMil, endTimeMil);
            for(int i = 0; i < rooms.size(); i++){
                System.out.println("Available Rooms: " + rooms.get(i));
            }


            for(int i = 0; i< listOfRooms.size(); i++){
                reverseListOfRooms.add(i, listOfRooms.get(i));
            }

            listOfRooms.clear();

            System.out.println("startTimeMil: " + startTimeMil + "\n endTimeMil:" + endTimeMil);
            rooms = ra.getAvailRooms(date, date, startTimeMil, endTimeMil);

            for(int i = 0; i < rooms.size(); i++){
                listOfRooms.add(rooms.get(i));
                reverseListOfRooms.remove(rooms.get(i));
            }

            availableRooms.setItems(listOfRooms);
            displayOccupiedRooms();
        }
    }

    @FXML
    public void displayOccupiedRooms(){

        //Point2D point = sceneGestures.getImageLocation();
        //double scaleRatio = roomImage.getFitWidth()/roomImage.getImage().getWidth();

        Circle thisCircle = new Circle();
        //in a regular pane
        imagePane.getChildren().add(thisCircle);

        for(int i = 0; i < reverseListOfRooms.size(); i++) {

            //if (reverseListOfRooms.get(i).equals("Classroom 1")) {
              //  thisCircle.setCenterX(700 - point.getX() * scaleRatio * sceneGestures.getImageScale());
              //  thisCircle.setCenterY(100 - point.getY() * scaleRatio * sceneGestures.getImageScale());
                thisCircle.setRadius(Math.max(2.5, 2.5f * (sceneGestures.getImageScale() / 5)));
                thisCircle.setStroke(Color.web("RED"));
                thisCircle.setFill(Color.web("RED"));
                System.out.println("We reach this");
           // }



        }
    }
}
