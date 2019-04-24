package edu.wpi.cs3733.d19.teamL.Account;

import edu.wpi.cs3733.d19.teamL.HomeScreens.HomeScreenController;
import edu.wpi.cs3733.d19.teamL.Memento;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class EmployeeTableController{

    private Stage thestage;
    TreeItem root = new TreeItem<>("rootxxx");

    @FXML
    private Button back;

    @FXML
    TreeTableColumn<EmployeeTable,String> ID;

    @FXML
    TreeTableColumn<EmployeeTable,String> firstName;

    @FXML
    TreeTableColumn<EmployeeTable,String> lastName;

    @FXML
    TreeTableColumn<EmployeeTable,String> position;

    @FXML
    TreeTableColumn<EmployeeTable,String> department;

    @FXML
    TreeTableColumn<EmployeeTable,String> isAdmin;

    @FXML
    TreeTableView<EmployeeTable> employees;

    private TreeItem<EmployeeTable> selectedEmployee;

    Timeline timeout;

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
                        Memento m = single.getOrig();
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(m.getFxml()));

                        Parent sceneMain = loader.load();
                        HomeScreenController controller = loader.<HomeScreenController>getController();
                        single.setLastTime();
                        controller.displayPopup();
                        single.setLastTime();

                        Stage thisStage = (Stage) employees.getScene().getWindow();
                        Screen screen = Screen.getPrimary();
                        Rectangle2D bounds = screen.getVisualBounds();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setMaximized(true);
                        thisStage.setScene(newScene);
                        thisStage.setX(bounds.getMinX());
                        thisStage.setY(bounds.getMinY());
                        thisStage.setWidth(bounds.getWidth());
                        thisStage.setHeight(bounds.getHeight());
                        timeout.stop();
                    } catch (IOException io){
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));
        timeout.setCycleCount(Timeline.INDEFINITE);
        timeout.play();

        employees.setEditable(false);
        EmployeeAccess ea = new EmployeeAccess();

        int count;
        count = ea.countRecords()-1;
        while(count >= 0){
            TreeItem<EmployeeTable> arr= ea.getRequests(count);
            root.getChildren().add(arr);
            count--;
        }

        ID.setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue()instanceof EmployeeTable) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getID());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        firstName.setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue()instanceof EmployeeTable) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getFirstName());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        lastName.setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue()instanceof EmployeeTable) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getLastName());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        position.setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue()instanceof EmployeeTable) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getType());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        department.setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue()instanceof EmployeeTable) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getDepartment());
            }
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        employees.setTreeColumn(ID);
        employees.setRoot(root);
        employees.setShowRoot(false);
    }

    /**@author Gabe
     * Returns admin to the memento's screen when the back button is pressed
     */
    @FXML
    private void backPressed(ActionEvent event) throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        Memento m = single.restore();
        single.setDoPopup(true);

        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setUsername("");
        single.setIsAdmin(false);
        single.setLoggedIn(false);
        single.setDoPopup(true);
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }

    @FXML
    private void goHome(ActionEvent event) throws IOException {
        timeout.stop();
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        single.setDoPopup(true);
        saveState();
        Memento m = single.getOrig();
        Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
        ((Node) event.getSource()).getScene().setRoot(newPage);
    }


    /**@author Gabe
     * When double clicking on a row in the table, admin is sent to the create/edit screen
     * and all the employee information from the clicked on row is passed along so that the edit account
     * fields are populated and the admin can make any changes
     */
    public void setNext(TreeItem<EmployeeTable> employee) {
        this.selectedEmployee = employee;
    }


    @FXML
    private void SwitchToCreateEditAccount() throws IOException {
        Singleton single = Singleton.getInstance();
        single.setLastTime();
        employees.setOnMouseClicked(event -> {
            setNext(employees.getSelectionModel().getSelectedItem());
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                try {
                    single.setLastTime();
                    timeout.stop();
                    //timeout.pause();
                    //Load second scene
                    saveState();
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("CreateEditAccount.fxml"));
                    Parent roots = loader.load();

                    CreateEditAccountController scene2Controller = loader.getController();
                   // Scene scene = new Scene(roots);
                    scene2Controller.setType(3, selectedEmployee.getValue().getID());

                    //Parent newPage = FXMLLoader.load(getClass().getClassLoader().getResource(m.getFxml()));
                    ((Node) event.getSource()).getScene().setRoot(roots);

                } catch (IOException ex) {
                    //noinspection ThrowablePrintedToSystemOut
                    //timeout.play();
                    System.err.println(ex);
                }

            }
        });
    }

    /**@author Nathan
     * Saves the memento state
     */
    private void saveState(){
        Singleton single = Singleton.getInstance();
        single.saveMemento("EmployeeTable.fxml");
    }
}

