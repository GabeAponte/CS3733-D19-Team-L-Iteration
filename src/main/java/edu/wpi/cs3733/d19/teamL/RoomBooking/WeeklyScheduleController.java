package edu.wpi.cs3733.d19.teamL.RoomBooking;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

public class WeeklyScheduleController
{
    @FXML
    private Label classroomLabel;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private JFXComboBox roomPicker;

    @FXML
    private Button weeklyBack;

    @FXML
    private Button goToBookRoom;

    @FXML
    private TreeTableView<Room> bookedTime;

    @FXML
    private TreeTableColumn<Room, String> timeCol;

    @FXML
    private TreeTableColumn<Room, Boolean> sunCol;

    @FXML
    private TreeTableColumn<Room, Boolean> monCol;

    @FXML
    private TreeTableColumn<Room, Boolean> tueCol;

    @FXML
    private TreeTableColumn<Room, Boolean> wedCol;

    @FXML
    private TreeTableColumn<Room, Boolean> thuCol;

    @FXML
    private TreeTableColumn<Room, Boolean> friCol;

    @FXML
    private TreeTableColumn<Room, Boolean> satCol;

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
    }

    public void loadWeekly(String theRoom, LocalDate theDate){
        classroomLabel.setText(theRoom + " Weekly Schedule");
        datePicker.setValue(theDate);

        checkAvailability(theRoom, theDate);
    }

    public void checkAvailability(String roomName, LocalDate theDate){
        String dayOfWeek = "";
        LocalDate weekDay = theDate;
        //dayOfWeek = weekDay.getDayOfWeek().toString();
        dayOfWeek = theDate.getDayOfWeek().toString();
        switch(dayOfWeek){
            case "SUNDAY":
                checkAvailiablityOfWeek(weekDay);
                break;
            case "MONDAY":
                checkAvailiablityOfWeek(weekDay.minusDays(1));
                break;
            case "TUESDAY":
                checkAvailiablityOfWeek(weekDay.minusDays(2));
                break;
            case "WEDNESDAY":
                checkAvailiablityOfWeek(weekDay.minusDays(3));
                break;
            case "THURSDAY":
                checkAvailiablityOfWeek(weekDay.minusDays(4));
                break;
            case "FRIDAY":
                checkAvailiablityOfWeek(weekDay.minusDays(5));
                break;
            case "SATURDAY":
                checkAvailiablityOfWeek(weekDay.minusDays(6));
                break;
        }
    }

    @SuppressWarnings("Duplicates")
    public void checkAvailiablityOfWeek(LocalDate date){
        int startTime = 0;
        int endTime = 30;
        for(int i = 0; i < 7; i++) {
            for (int j = 0; j < 47; j++) {
                // System.out.println("Start Time: " + startTime + " End Time: " + endTime);
                //TreeItem<Room> bookedRooms = new TreeItem<Room>(new Room(Integer.toString(startTime), Integer.toString(endTime), ra.getAvailRooms(theDate, theDate, startTime, endTime)));
                //Root.getChildren().add(bookedRooms);
                //System.out.println(bookedRooms.getValue().getTime());
                if (i == 0) {
                    startTime += 30;
                    endTime += 70;
                } else if (i % 2 == 0) {
                    startTime += 30;
                    endTime += 70;
                } else {
                    startTime += 70;
                    endTime += 30;
                }
                startTime %= 2400;
                endTime %= 2400;
            }
            System.out.println(date.getDayOfWeek().toString());
            date = date.plusDays(1);
        }

    }

    @FXML
    public void backPressed() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("EmployeeLoggedInHome.fxml"));
        if(single.isIsAdmin()) {
            loader = new FXMLLoader(getClass().getClassLoader().getResource("AdminLoggedInHome.fxml"));
        }
        Parent sceneMain = loader.load();
        Stage theStage = (Stage) weeklyBack.getScene().getWindow();
        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

    @FXML
    public void switchToBookRoom() throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("BookRoom.fxml"));
        Parent sceneMain = loader.load();
        BookRoomController controller = loader.<BookRoomController>getController();
        Stage theStage = (Stage) goToBookRoom.getScene().getWindow();
        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }

}
