package edu.wpi.cs3733.d19.teamL.RoomBooking;

import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import com.jfoenix.controls.*;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javafx.util.Duration;

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
    private TreeTableView<Room> bookedTime;

    @FXML
    private TreeTableColumn<Room, String> timeCol;

    @FXML
    private TreeTableColumn<Room, Boolean> class1Col;

    @FXML
    private TreeTableColumn<Room, Boolean> class2Col;

    @FXML
    private TreeTableColumn<Room, Boolean> class3Col;

    @FXML
    private TreeTableColumn<Room, Boolean> class4Col;

    @FXML
    private TreeTableColumn<Room, Boolean> class5Col;

    @FXML
    private TreeTableColumn<Room, Boolean> class6Col;

    @FXML
    private TreeTableColumn<Room, Boolean> class7Col;

    @FXML
    private TreeTableColumn<Room, Boolean> class8Col;

    @FXML
    private TreeTableColumn<Room, Boolean> class9Col;

    @FXML
    private TreeTableColumn<Room, Boolean> auditorium;

    private TreeItem Root = new TreeItem<>("rootxxx");

    Timeline timeout;

    @FXML
    private void initialize() {
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

                        Stage thisStage = (Stage) bookRoom2Back.getScene().getWindow();

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
        datePicker.setValue(LocalDate.now());
        findRooms();
    }

    @FXML
    private void backPressed() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("BookRoom.fxml"));
        Parent sceneMain = loader.load();
        BookRoomController controller = loader.<BookRoomController>getController();
        Stage theStage = (Stage) bookRoom2Back.getScene().getWindow();
        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    private void findRooms() {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        bookedTime.setRoot(null);
        Root.getChildren().clear();
        RoomAccess ra = new RoomAccess();
        String theDate = datePicker.getValue().toString();
        String endDate = datePicker.getValue().plusDays(1).toString();
        LocalTime startLT = LocalTime.of(0,0);
        LocalTime endLT = LocalTime.of(0, 30);
        for(int i = 0; i < 48; i++){
           // System.out.println("Start Time: " + startTime + " End Time: " + endTime);
            TreeItem<Room> bookedRooms = new TreeItem<Room>(new Room(startLT, endLT, ra.getAvailRooms(theDate, theDate, startTime, endTime)));
            Root.getChildren().add(bookedRooms);
            startLT.plusMinutes(30);
            endLT.plusMinutes(30);
        }

        //timeCol = new TreeTableColumn<Room, String>("Time");
        timeCol.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getTime());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class1Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass1()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class1Col.setCellFactory(column -> {
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

        class2Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass2()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class2Col.setCellFactory(column -> {
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

        class3Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass3()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class3Col.setCellFactory(column -> {
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

        class4Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass4()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class4Col.setCellFactory(column -> {
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

        class5Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass5()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class5Col.setCellFactory(column -> {
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

        class6Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass6()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class6Col.setCellFactory(column -> {
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

        class7Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass7()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class7Col.setCellFactory(column -> {
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

        class8Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass8()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class8Col.setCellFactory(column -> {
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

        class9Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass9()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        class9Col.setCellFactory(column -> {
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

        auditorium.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isAuditorium()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper(true);
                }else {
                    return new ReadOnlyObjectWrapper(false);
                }
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        auditorium.setCellFactory(column -> {
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
        bookedTime.getColumns().addAll(timeCol, class1Col, class2Col, class3Col, class4Col, class5Col, class6Col, class7Col, class8Col, class9Col, auditorium);
        bookedTime.setTreeColumn(timeCol);
        bookedTime.setRoot(Root);
        bookedTime.setShowRoot(false);
        single.setLastTime();
    }


}
