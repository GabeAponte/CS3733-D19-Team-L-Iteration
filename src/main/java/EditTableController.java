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
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

public class EditTableController {

    private Stage stage;
    private ArrayList<String> data;

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


    public void fillTable(ArrayList<String> data) {
        this.data = data;
        id.setText(data.get(0));
        xcoord.setText(data.get(1));
        ycoord.setText(data.get(2));
        floor.setText(data.get(3));
        building.setText(data.get(4));
        nodeType.setText(data.get(5));
        longName.setText(data.get(6));
        shortName.setText(data.get(7));


        //this.id.setText(data);
    }

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
