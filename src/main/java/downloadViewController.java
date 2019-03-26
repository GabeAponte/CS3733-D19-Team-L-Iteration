import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.xml.soap.Text;
import java.io.IOException;

public class downloadViewController {
    private Stage stage;

    @FXML
    Button cancel;

    @FXML
    Button downloadButton;

    @FXML
    public void initialize(){
        // populate text fields
        System.out.println("HEREDOWNLOAD");
    }

    @FXML
    private void downloadClicker2() throws IOException {
        stage = (Stage) downloadButton.getScene().getWindow();
        AnchorPane root;
        root =  FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
