import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class downloadViewController {
    //new branch test
    @SuppressWarnings("FieldCanBeLocal")
    private Stage stage;

    @FXML
    Button downloadButton;

    @FXML
    Button cancelButton;

    @FXML
    TextField downloadPath;


    @SuppressWarnings("EmptyMethod")
    @FXML
    public void initialize(){ }


    @SuppressWarnings("Duplicates")
    @FXML
    private void cancelClicker() throws IOException {
        stage = (Stage) cancelButton.getScene().getWindow();
        AnchorPane root;
        root =  FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @SuppressWarnings("RedundantThrows")
    @FXML
    private void downloadCSV() throws IOException {
        DBAccess db = new DBAccess();
        db.writeTableIntoCSV(downloadPath.getText());
    }
}
