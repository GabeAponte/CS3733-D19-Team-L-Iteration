package Controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrescriptionServiceRequestControler {

    @FXML
    private Button submitButton;

    @FXML
    private Button backButton;

    @FXML
    private JFXTextField destinationField;

    @FXML
    private JFXTextField medicineTypeFIeld;

    @FXML
    private JFXTextField deliveryTimeField;

    @FXML
    private JFXTextField amountField;

    @FXML
    private JFXTextArea commentsField;

}
