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

    private NodesAccess na;
    private final ObservableList<Location> data = FXCollections.observableArrayList();
    private HashMap<String, Location> lookup = new HashMap<String, Location>();

    public void initialize(){
        submitbtn.setDisable(true);
        na = new NodesAccess();
        initializeTable(na);
        startBox.setItems(data);
        endBox.setItems(data);
        typeField.getItems().addAll(
            "Wheelchair", "Walker", "Escort", "Crutches", "Other"
        );
    }

    private void initializeTable(NodesAccess na) {
        int count;
        count = 0;
        while (count < na.countRecords()) {
            ArrayList<String> arr = na.getNodes(count);
            Location testx = new Location(arr.get(0), Integer.parseInt(arr.get(1)), Integer.parseInt(arr.get(2)), Integer.parseInt(arr.get(3)), arr.get(4), arr.get(5), arr.get(6), arr.get(7));
            //only add the node if it hasn't been done yet
            if (!(lookup.containsKey(arr.get(0))) && Integer.parseInt(arr.get(3)) == 2) {
                lookup.put((arr.get(0)), testx);
                data.add(testx);
            }
            count++;
        }
    }

    @FXML
    private void reenableSubmit(){
        if(commentBox.getText().trim().isEmpty() || typeField.getValue() == null || startBox.getValue() == null || endBox.getValue() == null || phoneField.getText().trim().isEmpty()){
            submitbtn.setDisable(true);
            return;
        }
        submitbtn.setDisable(false);
    }

    @FXML
    private void submitPressed(){
        Location startNode = lookup.get(startBox.getValue().getLocID());
        Location endNode = lookup.get(endBox.getValue().getLocID());
        String comment = commentBox.getText();
        String type = typeField.getValue();
        String phone = phoneField.getText();
        InternalTransportAccess ita = new InternalTransportAccess();
        ita.makeRequest(comment, startNode, endNode, type, phone);
    }


}
