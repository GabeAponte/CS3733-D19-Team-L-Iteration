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
    private TreeTableColumn<Room, String> class1Col;

    @FXML
    private TreeTableColumn<Room, String> class2Col;

    @FXML
    private TreeTableColumn<Room, String> class3Col;

    @FXML
    private TreeTableColumn<Room, String> class4Col;

    @FXML
    private TreeTableColumn<Room, String> class5Col;

    @FXML
    private TreeTableColumn<Room, String> class6Col;

    @FXML
    private TreeTableColumn<Room, String> class7Col;

    @FXML
    private TreeTableColumn<Room, String> class8Col;

    @FXML
    private TreeTableColumn<Room, String> class9Col;

    @FXML
    private TreeTableColumn<Room, String> auditorium;

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
        for(int i = 0; i < 48; i++){
            System.out.println("Start Time: " + startTime + " End Time: " + endTime);
            TreeItem<Room> bookedRooms = new TreeItem<Room>(new Room(Integer.toString(startTime), ra.getAvailRooms(theDate, theDate, startTime, endTime)));
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
        TreeItem<Room> bookedRooms2 = new TreeItem<Room>(new Room(Integer.toString(startTime), ra.getAvailRooms(theDate, theDate, startTime, endTime)));
        Root.getChildren().add(bookedRooms2);

        //timeCol = new TreeTableColumn<Room, String>("Time");
        timeCol.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getTime());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        //class1Col = new TreeTableColumn<Room, Boolean>("Classroom 1");
        class1Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                if(cellData.getValue().getValue().isClass1()){
                    //cellData.getValue().
                    return new ReadOnlyObjectWrapper("Available");
                }
                return new ReadOnlyObjectWrapper("Occupied");
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        //class2Col = new TreeTableColumn<Room, Boolean>("Classroom 2");
        class2Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().isClass2());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        //class3Col = new TreeTableColumn<Room, Boolean>("Classroom 3");
        class3Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().isClass3());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        //class4Col = new TreeTableColumn<Room, Boolean>("Classroom 4");
        class4Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().isClass4());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        //class5Col = new TreeTableColumn<Room, Boolean>("Classroom 5");
        class5Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().isClass5());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        //class6Col = new TreeTableColumn<Room, Boolean>("Classroom 6");
        class6Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().isClass6());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        //class7Col = new TreeTableColumn<Room, Boolean>("Classroom 7");
        class7Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().isClass7());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        //class8Col = new TreeTableColumn<Room, Boolean>("Classroom 8");
        class8Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().isClass8());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        //class9Col = new TreeTableColumn<Room, Boolean>("Classroom 9");
        class9Col.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().isClass9());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        //auditorium = new TreeTableColumn<Room, Boolean>("Auditorium");
        auditorium.setCellValueFactory(cellData -> {
            if(cellData.getValue().getValue()instanceof Room) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().isAuditorium());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        bookedTime.getColumns().clear();
        bookedTime.getColumns().addAll(timeCol, class1Col, class2Col, class3Col, class4Col, class5Col, class6Col, class7Col, class8Col, class9Col, auditorium);
        bookedTime.setTreeColumn(timeCol);
        bookedTime.setRoot(Root);
        bookedTime.setShowRoot(false);


    }


}
