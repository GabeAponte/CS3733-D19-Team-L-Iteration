package Controller;

import Access.EmployeeAccess;
import Access.ReservationAccess;
import Access.RoomAccess;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import Object.*;

public class BookRoom2Controller {
    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXComboBox<String> avaliableRooms;

    @FXML
    private Label error;

    @FXML
    private JFXButton viewSchedule;

    @FXML
    private JFXButton requestRoom;

    @FXML
    private Stage thestage;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button bookRoom2Back;

    @FXML
    private void initialize() {

    }

    @FXML
    private void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));
        Parent sceneMain = loader.load();
        LoggedInHomeController controller = loader.<LoggedInHomeController>getController();
        Stage theStage = (Stage) bookRoom2Back.getScene().getWindow();
        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    private void findRooms(ActionEvent event) {
        LocalDate roomDate = datePicker.getValue();
        LocalDate curDate = LocalDate.now();

        error.setTextFill(Color.RED);
        if (roomDate.compareTo(curDate) < 0) {
            error.setText("Please select a time for today or a future day.");
        }



    }

}
