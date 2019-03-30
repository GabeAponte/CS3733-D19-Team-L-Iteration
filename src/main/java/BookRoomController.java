import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class BookRoomController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox avaliableRooms;

    @FXML
    private JFXButton requestRoom;

    @FXML
    private JFXComboBox startTime;

    @FXML
    private JFXComboBox endTime;

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
}
