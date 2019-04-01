import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class BookRoomController {

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

    @FXML
    private void backPressed() throws IOException {
        thestage = (Stage) bookRoomBack.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    //LocalTime startTimeValue = null;

    @FXML
    private void findRoom(ActionEvent event) {
        LocalTime startTimeValue = startTime.getValue();
        LocalTime endTimeValue = endTime.getValue();
        LocalDate roomDate = datePicker.getValue();

        if (startTimeValue == null && endTimeValue == null && roomDate == null) {
            error.setText("Please select start and end times and a date");
        }

        else if (startTimeValue == null && endTimeValue == null) {
            error.setText("Please select start and end times");
        }

        else if (startTimeValue == null && roomDate == null) {
            error.setText("Please select a start time and a date");
        }

        else if (endTimeValue == null && roomDate == null) {
            error.setText("Please select an end time and a date");}

        else if (startTimeValue == null) {
            error.setText("Please select a start time");
        }
        else if (endTimeValue == null) {
            error.setText("Please select an end time");

        }else if (startTimeValue.equals(endTimeValue)) {
            error.setText("Times cannot be the same");

        } else {
            error.setText("test");
        }

        int startTimeMil = startTime.getValue().getHour() * 100 + startTime.getValue().getMinute();
        int endTimeMil = endTime.getValue().getHour() * 100 + endTime.getValue().getMinute();
        String date = datePicker.getValue().toString();
        String roomID = "RoomTest";
        String employeeID = "Test";
        ReservationAccess roomReq = new ReservationAccess();
        roomReq.makeReservation(roomID, employeeID, date, startTimeMil, endTimeMil);
    }

    @FXML
    public void fieldsEntered(){
        ArrayList<String> rooms = new ArrayList<>();
        RoomAccess ra = new RoomAccess();
        int startTimeMil = 0;
        int endTimeMil = 0;
        String date = "";

        if(startTime.getValue() != null && endTime != null && datePicker.getValue() != null){
            startTimeMil = startTime.getValue().getHour() * 100 + startTime.getValue().getMinute();
            endTimeMil = endTime.getValue().getHour() * 100 + endTime.getValue().getMinute();
            date = datePicker.getValue().toString();
            System.out.println(startTimeMil);
            System.out.println(endTimeMil);
            System.out.println(date);
            avaliableRooms.getSelectionModel().clearSelection();
            listOfRooms.clear();

            rooms = ra.getAvailRooms(date, startTimeMil, endTimeMil);

            for(int i = 1; i < rooms.size(); i+=2){
                System.out.println(rooms.get(i));
                listOfRooms.add(rooms.get(i));
            }

            avaliableRooms.setItems(listOfRooms);
        }

    }

    @FXML
    public void selectRoom() {

    }

}
