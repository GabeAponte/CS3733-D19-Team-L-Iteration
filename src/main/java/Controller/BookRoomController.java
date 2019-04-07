package Controller;

import Access.EmployeeAccess;
import Access.ReservationAccess;
import Access.RoomAccess;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import Object.*;

public class BookRoomController {
    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXDatePicker datePicker1;

    @FXML
    private ComboBox<String> avaliableRooms;

    @FXML
    private Label error;

    @FXML
    private JFXButton requestRoom;

    @FXML
    private JFXTimePicker startTime;

    @FXML
    private JFXTimePicker endTime;

    @FXML
    private Stage thestage;

    @FXML
    private Pane imagePane;

    @FXML
    private ImageView roomImage;

    @FXML
    private Button bookRoomBack;

    final ObservableList<String> listOfRooms = FXCollections.observableArrayList();
    ArrayList<String> rooms = new ArrayList<>();

    @FXML
    private void initialize(){
        roomImage.fitWidthProperty().bind(imagePane.widthProperty());
        roomImage.fitHeightProperty().bind(imagePane.heightProperty());
    }

    @FXML
    private void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));

        Parent sceneMain = loader.load();

        LoggedInHomeController controller = loader.<LoggedInHomeController>getController();

        Stage theStage = (Stage) bookRoomBack.getScene().getWindow();

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
        else if (startTimeValue.compareTo(endTimeValue) > 0) {
            error.setText("Start time cannot be after end time.");
        }
        else if (startTimeValue.compareTo(curTime) < 0 && roomDate.equals(curDate)) {
            error.setText("Please select a current or future time for today.");
        }
        else if (roomDate.compareTo(curDate) < 0) {
            error.setText("Please select a time for today or a future day.");
        }
        else if (avaliableRooms.getValue() == null) {
            error.setText("Please pick a room.");
        }
        else {
            error.setTextFill(Color.WHITE);
            error.setText("Room booked.");
            int startTimeMil = startTime.getValue().getHour() * 100 + startTime.getValue().getMinute();
            int endTimeMil = endTime.getValue().getHour() * 100 + endTime.getValue().getMinute();
            String date = datePicker.getValue().toString();
            String endDate = datePicker1.getValue().toString();
            String roomID = "RoomTest";
            EmployeeAccess ea = new EmployeeAccess();
            Singleton single = Singleton.getInstance();
            String employeeID = ea.getEmployeeInformation(single.getUsername()).get(0);
            ReservationAccess roomReq = new ReservationAccess();
            for(int i = 1; i < rooms.size(); i+=2) {
                if (rooms.get(i).equals(avaliableRooms.getValue())) {
                    roomID = rooms.get(i - 1);
                }
            }
            roomReq.makeReservation(roomID, employeeID, date, endDate, startTimeMil, endTimeMil);
        }
    }

    @FXML
    public void fieldsEntered(){
        RoomAccess ra = new RoomAccess();
        int startTimeMil = 0;
        int endTimeMil = 0;
        String date = "";

        if(startTime.getValue() != null && endTime != null && datePicker.getValue() != null && datePicker1.getValue() != null){
            startTimeMil = startTime.getValue().getHour() * 100 + startTime.getValue().getMinute();
            endTimeMil = endTime.getValue().getHour() * 100 + endTime.getValue().getMinute();
            date = datePicker.getValue().toString();
            avaliableRooms.getSelectionModel().clearSelection();
            listOfRooms.clear();

            rooms = ra.getAvailRooms(date, startTimeMil, endTimeMil);

            for(int i = 1; i < rooms.size(); i+=2){
                listOfRooms.add(rooms.get(i));
            }

            avaliableRooms.setItems(listOfRooms);
        }
    }
}
