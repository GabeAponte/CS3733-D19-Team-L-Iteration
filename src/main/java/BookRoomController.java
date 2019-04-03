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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class BookRoomController {
    String uname;

    @FXML
    private JFXDatePicker datePicker;

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
    private Button bookRoomBack;

    final ObservableList<String> listOfRooms = FXCollections.observableArrayList();
    ArrayList<String> rooms = new ArrayList<>();

    public void init(String username){
        uname = username;
    }

    @FXML
    private void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoggedInHome.fxml"));

        Parent sceneMain = loader.load();

        LoggedInHomeController controller = loader.<LoggedInHomeController>getController();
        controller.init(uname);

        Stage theStage = (Stage) bookRoomBack.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    //LocalTime startTimeValue = null;

    @FXML
    private void findRoom(ActionEvent event) {
        LocalTime startTimeValue = startTime.getValue();
        LocalTime endTimeValue = endTime.getValue();
        LocalDate roomDate = datePicker.getValue();
        LocalDate curDate = LocalDate.now();
        LocalTime curTime = LocalTime.now();

        error.setTextFill(Color.RED);

        if (startTimeValue == null && endTimeValue == null && roomDate == null) {
            error.setText("Please select start and end times and a date.");
        }
        else if (startTimeValue == null && endTimeValue == null) {
            error.setText("Please select start and end times.");
        }
        else if (startTimeValue == null && roomDate == null) {
            error.setText("Please select a start time and a date.");
        }
        else if (endTimeValue == null && roomDate == null) {
            error.setText("Please select an end time and a date.");}

        else if (startTimeValue == null) {
            error.setText("Please select a start time.");
        }
        else if (endTimeValue == null) {
            error.setText("Please select an end time.");
        }
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
            String roomID = "RoomTest";
            EmployeeAccess ea = new EmployeeAccess();
            String employeeID = ea.getNodeInformation(uname).get(0);
            ReservationAccess roomReq = new ReservationAccess();
            for(int i = 1; i < rooms.size(); i+=2) {
                if (rooms.get(i).equals(avaliableRooms.getValue())) {
                    roomID = rooms.get(i - 1);
                }
            }
            roomReq.makeReservation(roomID, employeeID, date, startTimeMil, endTimeMil);
        }
    }

    @FXML
    public void fieldsEntered(){
        RoomAccess ra = new RoomAccess();
        int startTimeMil = 0;
        int endTimeMil = 0;
        String date = "";

        if(startTime.getValue() != null && endTime != null && datePicker.getValue() != null){
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
