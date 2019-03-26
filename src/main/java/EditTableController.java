import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class EditTableController {

    private Stage stage;

    @FXML
    Button cancel;

    @FXML
    Button saveAndReturn;

    @FXML
    TextField id;

    @FXML
    TextField xcoord;

    @FXML
    TextField ycoord;

    @FXML
    TextField floor;

    @FXML
    TextField building;

    @FXML
    TextField nodeType;

    @FXML
    TextField longName;

    @FXML
    TextField shortName;


    @FXML
    public void initialize(){
        // populate text fields
        System.out.println("HERE");
    }

    @FXML
    private void returnAndSave() throws IOException {
        stage = (Stage) saveAndReturn.getScene().getWindow();
        AnchorPane root;
        root =  FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);

    }
}
