import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableViewController {

    @FXML
    Button populate;

    @FXML
    TableColumn<PrototypeLocation, Integer> idCol;

    @FXML
    TableColumn<PrototypeLocation, String> nameCol;

    @FXML
    private TableView<PrototypeLocation> table = new TableView();


    @FXML
    public void initialize(){
        table.setEditable(true);

        PrototypeLocation test1 = new PrototypeLocation(1, "Elevator");
        PrototypeLocation test2 = new PrototypeLocation(2, "Coffee");
        PrototypeLocation test3 = new PrototypeLocation(1, "Elevator");
        final ObservableList<PrototypeLocation> data = FXCollections.observableArrayList(test1, test2, test3, test1, test1, test2, test1, test2, test3, test1, test1, test2);

        idCol.setCellValueFactory(new PropertyValueFactory<PrototypeLocation,Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<PrototypeLocation,String>("name"));

        table.setItems(data);
        System.out.println("HERE");
    }
}
