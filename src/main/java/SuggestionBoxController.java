import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SuggestionBoxController {

    @FXML
    private Button submitFeedback;

    @FXML
    private TextArea feedbackComments;

    @FXML
    private Stage thestage;

    @FXML
    private Label error;

    @FXML
    private Button SuggestionBack;

    @FXML
    private void backPressed() throws IOException {
        thestage = (Stage) SuggestionBack.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    private void submitPressed() {
        SuggestionBasicAccess sga = new SuggestionBasicAccess();

        if (feedbackComments.getText().trim().isEmpty() || feedbackComments.getText().equals("Type suggestions here")) {
            error.setText("Please enter your feedback");

        } else {
            sga.addSuggestion(feedbackComments.getText());
            error.setText("Thank you for your feedback");
            feedbackComments.setText("");
        }
    }

}