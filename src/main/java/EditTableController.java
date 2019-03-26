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
    private PrototypeLocation data;
    private String tempNodeID;
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


    public void fillTable(PrototypeLocation data) {
        this.data = data;
        tempNodeID = data.getId();
        id.setText(data.getId());
        xcoord.setText(Integer.toString(data.getXcoord()));
        ycoord.setText(Integer.toString(data.getYcoord()));
        floor.setText(Integer.toString(data.getFloor()));
        building.setText(data.getBuilding());
        nodeType.setText(data.getNodeType());
        longName.setText(data.getLongName());
        shortName.setText(data.getShortName());
    }

    @FXML
    public void initialize(){
        // populate text fields
        System.out.println("HERE");
    }

    @FXML
    private void returnAndSave() throws IOException {
        DBAccess db = new DBAccess();

        db.updateProto(tempNodeID, "xcoord" , Integer.parseInt(xcoord.getText()));
        db.updateProto(tempNodeID, "ycoord" , Integer.parseInt(ycoord.getText()));
        db.updateProto(tempNodeID, "floor" , Integer.parseInt(floor.getText()));
        db.updateProto(tempNodeID, "building" , building.getText());
        db.updateProto(tempNodeID, "nodeType" , nodeType.getText());
        db.updateProto(tempNodeID, "longName" , longName.getText());
        db.updateProto(tempNodeID, "shortName" , shortName.getText());
        db.updateProto(tempNodeID, "nodeID", id.getText());
        stage = (Stage) saveAndReturn.getScene().getWindow();
        AnchorPane root;
        root =  FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
