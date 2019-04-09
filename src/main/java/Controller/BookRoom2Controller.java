package Controller;

import Access.EmployeeAccess;
import Access.ReservationAccess;
import Access.RoomAccess;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private JFXButton goToBookRoom;

    @FXML
    private JFXButton requestRoom;

    @FXML
    private Stage thestage;

    @FXML
    private GridPane gridPane;

    @FXML
    private JFXScrollPane ScrollPane;

    @FXML
    private Button bookRoom2Back;

    @FXML
    private TreeTableView bookedTime;

    @FXML
    private TreeTableColumn timeCol;

    @FXML
    private TreeTableColumn class1Col;

    @FXML
    private TreeTableColumn class2Col;

    @FXML
    private TreeTableColumn class3Col;

    @FXML
    private TreeTableColumn class4Col;

    @FXML
    private TreeTableColumn class5Col;

    @FXML
    private TreeTableColumn class6Col;

    @FXML
    private TreeTableColumn class7Col;

    @FXML
    private TreeTableColumn class8Col;

    @FXML
    private TreeTableColumn class9Col;

    @FXML
    private TreeTableColumn auditorium;

    @FXML
    private void initialize() {
        datePicker.setValue(LocalDate.now());
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

    @FXML
    private void switchToBookScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("BookRoom.fxml"));
        Parent sceneMain = loader.load();
        BookRoomController controller = loader.<BookRoomController>getController();
        Stage theStage = (Stage) goToBookRoom.getScene().getWindow();
        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void findRooms(ActionEvent event) {
        RoomAccess ra = new RoomAccess();
        String theDate = datePicker.getValue().toString();
        ArrayList<Room> theAvailableRooms = new ArrayList<>();
        int startTime = 0;
        int endTime = 30;
        for(int i = 0; i < 48; i++){
            System.out.println("The Times: " + startTime);
            theAvailableRooms.add(new Room(Integer.toString(startTime), ra.getAvailRooms(theDate, theDate, startTime, endTime)));
            if(i == 0){
                startTime += 30;
                endTime += 70;
            }
            else if(i%2 == 0) {
                startTime += 30;
                endTime += 70;
            }
            else{
                startTime +=70;
                endTime +=30;
            }
            endTime %= 2400;
        }
        theAvailableRooms.add(new Room(Integer.toString(startTime), ra.getAvailRooms(theDate, theDate, startTime, endTime)));
    }

    
}
