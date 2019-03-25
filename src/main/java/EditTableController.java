import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EditTableController {


    @FXML
    Button download;

    @FXML
    TableColumn<PrototypeLocation, Integer> idCol;

    @FXML
    TableColumn<PrototypeLocation, String> nameCol;

    @FXML
    private TableView<PrototypeLocation> table = new TableView();


    @FXML
    public void initialize(){
        table.setEditable(true);

        PrototypeLocation test1 = new PrototypeLocation("1", 2, 2, 2, "Main", "elevator", "second floor elevator", "Elevator2");
        //PrototypeLocation test2 = new PrototypeLocation("1", "Coffee");

        final ObservableList<PrototypeLocation> data = FXCollections.observableArrayList();

        DBAccess db = new DBAccess();
        ResultSet rs = db.getNodes();
        try {
            while(rs.next()){
                PrototypeLocation testx = new PrototypeLocation(rs.getString("nodeID"), rs.getInt("xcoord"), rs.getInt("ycoord"), rs.getInt("floor"), rs.getString("building"), rs.getString("nodeType"), rs.getString("longName"), rs.getString("shortName"));

                data.add(testx);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



        idCol.setCellValueFactory(new PropertyValueFactory<PrototypeLocation,Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<PrototypeLocation,String>("name"));

        table.setItems(data);
        System.out.println("HERE");
    }
}
