import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class BookRoomController {

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private ChoiceBox avaliableRooms;

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

        }else if (startTimeValue == endTimeValue) {
            error.setText("Times cannot be the same");

        } else {
            error.setText("test");
        }
    }

}
