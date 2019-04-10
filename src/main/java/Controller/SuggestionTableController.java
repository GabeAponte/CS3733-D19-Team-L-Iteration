package Controller;

import Access.SuggestionBasicAccess;
import Object.SuggestionTable;
import Object.Singleton;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class SuggestionTableController {

    private Stage thestage;
    TreeItem root = new TreeItem<>("rootxxx");

    @FXML
    private Button back;

    @FXML
    private TextArea commentText;

    @FXML
    TreeTableColumn<SuggestionTable,String> comments;


    @FXML
    TreeTableView<SuggestionTable> suggestions;

    private TreeItem<SuggestionTable> selectedSuggestion;

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
                        single.setLoggedIn(false);
                        single.setUsername("");
                        single.setIsAdmin(false);
                        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

                        Parent sceneMain = loader.load();

                        Stage thisStage = (Stage) suggestions.getScene().getWindow();

                        Scene newScene = new Scene(sceneMain);
                        thisStage.setScene(newScene);
                        timeout.stop();
                    } catch (IOException io){
                        System.out.println(io.getMessage());
                    }
                }
            }
        }));
        makeTable();

        deleteComment();
    }

    @FXML
    public void makeTable() {
        suggestions.setEditable(false);
        SuggestionBasicAccess ea = new SuggestionBasicAccess();
        root.getChildren().clear();
        suggestions.setRoot(null);
        commentText.clear();
        suggestions.getColumns().clear();
        int count;
        count = ea.countRecords()-1;
        while(count >= 0){
            TreeItem<SuggestionTable> arr= ea.getSuggestions(count);
            root.getChildren().add(arr);
            count--;
        }

        comments.setCellValueFactory(cellData -> {
            if (cellData.getValue().getValue()instanceof SuggestionTable) {
                return new ReadOnlyObjectWrapper(cellData.getValue().getValue().getComment());
            }
            System.out.println("Invalid");
            return new ReadOnlyObjectWrapper(cellData.getValue().getValue());
        });

        suggestions.setTreeColumn(comments);
        suggestions.getColumns().add(comments);
        suggestions.setRoot(root);
        suggestions.setShowRoot(false);
    }

    /**@author Gabe
     * Returns admin to the Admin Logged In Home screen when the back button is pressed
     */
    @FXML
    private void backPressed() throws IOException {
        timeout.stop();
        thestage = (Stage) back.getScene().getWindow();
        AnchorPane root;

        root = FXMLLoader.load(getClass().getClassLoader().getResource("AdminLoggedInHome.fxml"));

        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    /**
     * @author Gabe
     * Populates the table on the screen with any employees in the database
     */

    /*public void initializeTable(EmployeeAccess ea) {
        ArrayList<ArrayList<String>>  ls = ea.getEmployees("", "");
    }


    /**@author Gabe
     * When double clicking on a row in the table, admin is sent to the create/edit screen
     * and all the employee information from the clicked on row is passed along so that the edit account
     * fields are populated and the admin can make any changes
     */
    public void setNext(TreeItem<SuggestionTable> selected) {
        selectedSuggestion = selected;
    }

    @FXML
    private void deleteComment() {
               SuggestionBasicAccess sba = new SuggestionBasicAccess();
        suggestions.setOnKeyPressed(keyEvent -> {
            setNext(suggestions.getSelectionModel().getSelectedItem());
            if (keyEvent.getCode().equals(KeyCode.DELETE) && selectedSuggestion != null){
                sba.deleteRecords(selectedSuggestion.getValue().getComment());
                makeTable();
                setNext(null);
            }
        });
    }


   @FXML
    private void getComment() {
        suggestions.setOnMouseClicked(event -> {
            setNext(suggestions.getSelectionModel().getSelectedItem());
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                commentText.setText(selectedSuggestion.getValue().getComment());
            }
        });
   }
}
