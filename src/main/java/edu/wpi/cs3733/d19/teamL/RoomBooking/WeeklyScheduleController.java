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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
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
        dayOfWeek = theDate.getDayOfWeek().toString();
        System.out.println(dayOfWeek);
        switch(dayOfWeek){
            case "SUNDAY":
                break;
            case "MONDAY":
                break;
            case "TUESDAY":
                break;
            case "WEDNESDAY":
                break;
            case "THURSDAY":
                break;
            case "FRIDAY":
                break;
            case "SATURDAYDAY":
                break;
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
