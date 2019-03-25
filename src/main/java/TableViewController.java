import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TableViewController {

    @FXML
    Button populate;

    @FXML
    Button makeEditable;

    @FXML
    Button download;

    @FXML
    TableColumn<PrototypeLocation, String> idCol;

    @FXML
    TableColumn<PrototypeLocation,Integer> xCol;

    @FXML
    TableColumn<PrototypeLocation, Integer> yCol;

    @FXML
    TableColumn<PrototypeLocation, Integer> floorCol;

    @FXML
    TableColumn<PrototypeLocation, String> buildingCol;

    @FXML
    TableColumn<PrototypeLocation, String> typeCol;

    @FXML
    TableColumn<PrototypeLocation, String> longCol;

    @FXML
    TableColumn<PrototypeLocation, String> shortCol;


    @FXML
    private TableView<PrototypeLocation> table = new TableView();


    @FXML
    public void initialize(){
        table.setEditable(false);
        PrototypeLocation test1 = new PrototypeLocation("1", 2, 2, 2, "Main", "elevator", "second floor elevator", "Elevator2");
        //PrototypeLocation test2 = new PrototypeLocation("1", "Coffee");

        final ObservableList<PrototypeLocation> data = FXCollections.observableArrayList();
        DBAccess db = new DBAccess();
        db.dropTable();
        db.createDatabase();
        db.readCSVintoTable("src/main/resources/PrototypeNodes.csv");


        int count;
        count  = 0;
        while(count < db.countRecords()){
            String[] arr= db.getNodes(count);
            System.out.println(arr[0]);
            PrototypeLocation testx = new PrototypeLocation(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), Integer.parseInt(arr[3]), arr[4], arr[5], arr[6], arr[7]);

            data.add(testx);
        }


        idCol.setCellValueFactory(new PropertyValueFactory<PrototypeLocation,String>("id"));
        xCol.setCellValueFactory(new PropertyValueFactory<PrototypeLocation, Integer>("xcoord"));
        yCol.setCellValueFactory(new PropertyValueFactory<PrototypeLocation, Integer>("ycoord"));
        floorCol.setCellValueFactory(new PropertyValueFactory<PrototypeLocation, Integer>("floor"));
        buildingCol.setCellValueFactory(new PropertyValueFactory<PrototypeLocation, String >("floor"));
        typeCol.setCellValueFactory(new PropertyValueFactory<PrototypeLocation, String >("nodeType"));
        longCol.setCellValueFactory(new PropertyValueFactory<PrototypeLocation,String>("longName"));
        shortCol.setCellValueFactory(new PropertyValueFactory<PrototypeLocation, String>("shortName"));

        table.setItems(data);
        System.out.println("HERE");
    }

    @FXML
    public void callAccepted(ActionEvent event){
        System.out.println("From controller");
        //nrb.loadSecondFxml();
    }
}
