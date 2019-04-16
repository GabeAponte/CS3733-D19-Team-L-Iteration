package edu.wpi.cs3733.d19.teamL.Map.Pathfinding;

import com.jfoenix.controls.JFXTextArea;
import javafx.fxml.FXML;

public class TextDirectionController {
    @FXML
    private JFXTextArea TextOfDirection;

    public void setTextOfDirection(String A){
        TextOfDirection.setText(A);
    }
}
