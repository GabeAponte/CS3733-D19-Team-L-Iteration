package Controller;

import Object.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

public class InternalTransportController {
    @FXML
    JFXComboBox<Location> startBox;

    @FXML
    JFXComboBox<Location> endBox;

    @FXML
    JFXTextField phoneField;

    @FXML
    JFXTextField typeField;

    @FXML
    JFXTextArea commentBox;

    public void initialize(){
        //do something
    }


}
