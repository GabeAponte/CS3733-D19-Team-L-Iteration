import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    private Stage stage;


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
            ArrayList<String> arr= db.getNodes(count);
            //System.out.println(arr.get(count));
            PrototypeLocation testx = new PrototypeLocation(arr.get(0), Integer.parseInt(arr.get(1)), Integer.parseInt(arr.get(2)), Integer.parseInt(arr.get(3)), arr.get(4), arr.get(5), arr.get(6), arr.get(7));
            count++;
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

    @FXML
    private void cellClicked(ActionEvent event) {

    }

    @FXML
    private void openEdit() throws IOException {
        stage = (Stage) makeEditable.getScene().getWindow();
        AnchorPane root;
        root =  FXMLLoader.load(getClass().getResource("editScreen.fxml"));
        Scene scene = new Scene(root);
        //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        stage.setScene(scene);

    }
}
