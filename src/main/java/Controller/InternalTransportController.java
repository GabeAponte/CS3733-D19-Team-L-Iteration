package Controller;

import Access.EdgesAccess;
import Access.InternalTransportAccess;
import Access.NodesAccess;
import Object.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class InternalTransportController {
    @FXML
    JFXComboBox<Location> startBox;

    @FXML
    JFXComboBox<Location> endBox;

    @FXML
    JFXTextField phoneField;

    @FXML
    JFXComboBox<String> typeField;

    @FXML
    JFXTextArea commentBox;

    @FXML
    JFXButton submitbtn;

    @FXML
    JFXButton backBtn;

    private NodesAccess na;
    private final ObservableList<Location> data = FXCollections.observableArrayList();
    private HashMap<String, Location> lookup = new HashMap<String, Location>();

    public void initialize(){
        Singleton single = Singleton.getInstance();
        submitbtn.setDisable(true);
        na = new NodesAccess();
        //initializeTable(na);
        startBox.setItems(single.getData());
        endBox.setItems(single.getData());
        typeField.getItems().addAll(
            "Wheelchair", "Walker", "Escort", "Crutches", "Other"
        );
    }

    private void initializeTable(NodesAccess na) {
        int count;
        count = 0;
        while (count < na.countRecords()) {
            ArrayList<String> arr = na.getNodes(count);
            Location testx = new Location(arr.get(0), Integer.parseInt(arr.get(1)), Integer.parseInt(arr.get(2)), arr.get(3), arr.get(4), arr.get(5), arr.get(6), arr.get(7));
            //only add the node if it hasn't been done yet
            if (!(lookup.containsKey(arr.get(0))) && arr.get(3).equals(2)) {
                lookup.put((arr.get(0)), testx);
                data.add(testx);
            }
            count++;
        }
    }

    private boolean isDigit(char c){
        if(c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'){
            return true;
        }
        return false;
    }

    private boolean NotValidPhone(){
        String num = phoneField.getText();
        if(num.length() != 12){
            return true;
        }
        boolean isValid = isDigit(num.charAt(0)) && isDigit(num.charAt(1)) && isDigit(num.charAt(2));
        isValid = isValid && num.charAt(3) == '-' && isDigit(num.charAt(4)) && isDigit(num.charAt(5));
        isValid = isValid && isDigit(num.charAt(6)) && num.charAt(7) == '-' && isDigit(num.charAt(8));
        isValid = isValid && isDigit(num.charAt(9)) && isDigit(num.charAt(10)) && isDigit(num.charAt(11));
        return !isValid;
    }

    @FXML
    private void reenableSubmit(){
        if(commentBox.getText().trim().isEmpty() || typeField.getValue() == null || startBox.getValue() == null || endBox.getValue() == null || phoneField.getText().trim().isEmpty()){
            submitbtn.setDisable(true);
            return;
        }

        submitbtn.setDisable(NotValidPhone());
    }

    @FXML
    private void submitPressed() throws IOException{
        Singleton single = Singleton.getInstance();
        Location startNode = single.lookup.get(startBox.getValue().getLocID());
        Location endNode = single.lookup.get(endBox.getValue().getLocID());
        String comment = commentBox.getText();
        String type = typeField.getValue();
        String phone = phoneField.getText();
        InternalTransportAccess ita = new InternalTransportAccess();
        ita.makeRequest(comment, startNode, endNode, type, phone);

        if(single.isLoggedIn()){
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("LoggedInHome.fxml"));

            Parent sceneMain = loader.load();
            Stage theStage = (Stage) backBtn.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("HospitalHome.fxml"));

            Parent sceneMain = loader.load();
            Stage theStage = (Stage) backBtn.getScene().getWindow();

            Scene scene = new Scene(sceneMain);
            theStage.setScene(scene);
        }

    }

    @FXML
    private void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("ServiceRequest.fxml"));

        Parent sceneMain = loader.load();
        Stage theStage = (Stage) backBtn.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        theStage.setScene(scene);
    }
}
