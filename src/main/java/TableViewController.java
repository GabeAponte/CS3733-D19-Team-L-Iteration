import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
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

    private Stage thestage;
    private  PrototypeLocation proto;


    @FXML
    public void initialize(){
        table.setEditable(false);
        PrototypeLocation test1 = new PrototypeLocation("1", 2, 2, 2, "Main", "elevator", "second floor elevator", "Elevator2");
        //PrototypeLocation test2 = new PrototypeLocation("1", "Coffee");

        final ObservableList<PrototypeLocation> data = FXCollections.observableArrayList();
        DBAccess db = new DBAccess();
       // db.dropTable();
        //db.createDatabase();
        //db.readCSVintoTable("src/main/resources/PrototypeNodes.csv");
        //System.out.println("DATABASE CREATED RIGHT HERE LOOK HERE");

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
        buildingCol.setCellValueFactory(new PropertyValueFactory<PrototypeLocation, String >("building"));
        typeCol.setCellValueFactory(new PropertyValueFactory<PrototypeLocation, String >("nodeType"));
        longCol.setCellValueFactory(new PropertyValueFactory<PrototypeLocation,String>("longName"));
        shortCol.setCellValueFactory(new PropertyValueFactory<PrototypeLocation, String>("shortName"));
        table.setOnMouseClicked( event -> {
                setNext(table.getSelectionModel().getSelectedItem());});
        table.setItems(data);
        System.out.println("HERE");



    }

    @FXML
    public void callAccepted(ActionEvent event){
        System.out.println("cool");
    }

    public void setNext(PrototypeLocation proto) {
        this.proto= proto;
    }

    @FXML
    private void openEdit() {
        try {
            //Load second scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("editScreen.fxml"));
            Parent roots = loader.load();

            //Get controller of scene2
            EditTableController scene2Controller = loader.getController();

            Scene scene = new Scene(roots);
            scene2Controller.fillTable(this.proto);
            thestage = (Stage) makeEditable.getScene().getWindow();
            //Show scene 2 in new window
            thestage.setScene(scene);

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }


}
