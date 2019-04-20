package edu.wpi.cs3733.d19.teamL.RoomBooking;

import com.android.dx.gen.Local;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class WeeklyScheduleController
{
    @FXML
    private Label classroomLabel;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXComboBox roomPicker;

    @FXML
    private Button back;

    @FXML
    private Button goToBookRoom;

    @FXML
    private TreeTableView<WeeklyRoom> bookedTime;

    @FXML
    private TreeTableColumn<WeeklyRoom, String> timeCol;

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

    Timeline timeout;

    String chosenRoom;
    LocalDate chosenDate;

    @FXML
    public void initialize(){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        timeout = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if((System.currentTimeMillis() - single.getLastTime()) > single.getTimeoutSec()){
                    try{
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

                        Stage thisStage = (Stage) roomPicker.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
                        timeout.stop();
                    } catch (IOException io){
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));
        timeout.setCycleCount(Timeline.INDEFINITE);
        timeout.play();

        roomPicker.getItems().addAll("Room 1 - Computer", "Room 2 - Computer", "Room 3 - Computer", "Room 4 - Classroom", "Classtoom 5 - Computer", "Room 6 - Classroom", "Room 7 - Computer", "Room 8 - Classroom", "Room 9 - Computer", "Mission Hall Auditorium");
    }

    @FXML
    private void viewSched(){
        changeRooms();
    }

    @FXML
    private void changeRooms(){
        loadWeekly(roomPicker.getValue().toString(), datePicker.getValue());
    }

    public void loadWeekly(String theRoom, LocalDate theDate){
        roomPicker.setValue(theRoom);
        classroomLabel.setText(theRoom + " Weekly Schedule");
        datePicker.setValue(theDate);

        checkAvailability(theRoom, theDate);
    }

    public void checkAvailability(String roomName, LocalDate theDate){
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        bookedTime.setRoot(null);
        Root.getChildren().clear();
        RoomAccess ra = new RoomAccess();
        LocalDate givenDate = datePicker.getValue();
        LocalTime startLT = LocalTime.of(0,0);
        LocalTime endLT = LocalTime.of(0, 30);
        for(int i = 0; i < 48; i++){
            //System.out.println("Start Time: " + startLT + " End Time: " + endLT);
            if(i == 47)
            {
                System.out.println(startLT.toString());
                System.out.println(endLT.toString());
            }
            TreeItem<WeeklyRoom> bookedRooms = new TreeItem<WeeklyRoom>(new WeeklyRoom(startLT, endLT, theDate, roomName ));
            Root.getChildren().add(bookedRooms);
            startLT = startLT.plusMinutes(30);
            endLT = endLT.plusMinutes(30);
        }

        //timeCol = new TreeTableColumn<Room, String>("Time");
        timeCol.setCellValueFactory(cellData -> {
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
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
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
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
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
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
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
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
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
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
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
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
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
                        setStyle("-fx-background-color: red");
                    } else {
                        setText("Available");
                        setStyle("-fx-background-color: green");
                    }
                }
            };
            return cell;
        });

        bookedTime.getColumns().clear();
        bookedTime.getColumns().addAll(timeCol, sunCol, monCol, tueCol, wedCol, thuCol, friCol, satCol);
        bookedTime.setTreeColumn(timeCol);
        bookedTime.setRoot(Root);
        bookedTime.setShowRoot(false);
        single.setLastTime();
    }

    @FXML
    public void backPressed() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("BookRoom.fxml"));
        Parent sceneMain = loader.load();
        BookRoomController controller = loader.<BookRoomController>getController();
        Stage theStage = (Stage) back.getScene().getWindow();
        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }
    @FXML
    private void logOut() throws IOException {

    }


    @FXML
    private void goHome() throws IOException {

    }

}
