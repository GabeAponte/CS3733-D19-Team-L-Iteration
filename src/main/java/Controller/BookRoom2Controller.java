package Controller;

import Access.EmployeeAccess;
import Access.ReservationAccess;
import Access.RoomAccess;
import com.jfoenix.controls.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
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

    @FXML
    private void initialize() {
        datePicker.setValue(LocalDate.now());
        findRooms();
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
    private void findRooms() {
        bookedTime.setRoot(null);
        Root.getChildren().clear();
        RoomAccess ra = new RoomAccess();
        String theDate = datePicker.getValue().toString();
        int startTime = 0;
        int endTime = 30;
        for(int i = 0; i < 47; i++){
            System.out.println("Start Time: " + startTime + " End Time: " + endTime);
            TreeItem<Room> bookedRooms = new TreeItem<Room>(new Room(Integer.toString(startTime), Integer.toString(endTime), ra.getAvailRooms(theDate, theDate, startTime, endTime)));
            Root.getChildren().add(bookedRooms);
            System.out.println(bookedRooms.getValue().getTime());
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
            startTime %= 2400;
            endTime %= 2400;
        }
        System.out.println("Start Time: " + startTime + " End Time: " + endTime);
        TreeItem<Room> bookedRooms2 = new TreeItem<Room>(new Room(Integer.toString(startTime), Integer.toString(endTime), ra.getAvailRooms(theDate, theDate, startTime, endTime)));
        Root.getChildren().add(bookedRooms2);

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


    }


}
