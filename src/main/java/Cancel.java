import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Cancel {

    Stage thestage;
    private String typeOfService;

    @FXML
    Button Back2;

    @FXML
    public Label typeLabel;

    @FXML
    public void init(String type){
        typeOfService = type;
        typeLabel.setText(typeOfService + " Services");
    }

    @FXML
    private void backPressed() throws IOException {
        thestage = (Stage) Back2.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("ServiceRequest.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }

    //TODO: when submit is pressed, prompt "Are You Sure"
    @FXML
    private void promptCancel(ActionEvent e){
        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 600, 600);
        Stage stage = new Stage(); stage.setScene(scene);
        stage.setTitle("Cool Window");
        stage.show();
    }
}
