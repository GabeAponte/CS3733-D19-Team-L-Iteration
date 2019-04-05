package Controller;

import Access.SuggestionBasicAccess;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

@SuppressWarnings("ALL")
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
    /**@author Gabe
     * Returns user to the Logged In Home screen when the back button is pressed
     */
    private void backPressed() throws IOException {
        thestage = (Stage) SuggestionBack.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getClassLoader().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    @FXML
    /**@author Gabe
     * When the submit button is pressed, the suggestion is added to the databse if the inputed
     * value is valid.
     */
    private void submitPressed() {
        SuggestionBasicAccess sga = new SuggestionBasicAccess();

        //Gabe - checks if the comment is nothing and that it isn't the prompt text
        if (feedbackComments.getText().trim().isEmpty() || feedbackComments.getText().equals("Type suggestions here")) {
            error.setText("Please enter your feedback");

        } else {
            //Gabe - valid suggestion and is added to database
            sga.addSuggestion(feedbackComments.getText());
            error.setText("Thank you for your feedback");
            feedbackComments.setText("");
        }
    }

}