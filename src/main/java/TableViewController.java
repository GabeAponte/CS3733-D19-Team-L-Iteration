import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
//this is a comment
    @FXML
    Button makeEditable;

    @FXML
    Button download;

    @FXML
    TableColumn<Location, String> idCol;

    @FXML
    TableColumn<Location,Integer> xCol;

    @FXML
    TableColumn<Location, Integer> yCol;

    @FXML
    TableColumn<Location, Integer> floorCol;

    @FXML
    TableColumn<Location, String> buildingCol;

    @FXML
    TableColumn<Location, String> typeCol;

    @FXML
    TableColumn<Location, String> longCol;

    @FXML
    TableColumn<Location, String> shortCol;


    @SuppressWarnings("unchecked")
    @FXML
    private TableView<Location> table = new TableView();

    private Stage thestage;
    private Location proto;


    @SuppressWarnings("Convert2Diamond")
    @FXML
    public void initialize(){
        table.setEditable(false);
        this.makeEditable.setDisable(true);
        final ObservableList<Location> data = FXCollections.observableArrayList();
        DBAccess db = new DBAccess();

        int count;
        count  = 0;
        while(count < db.countRecords()){
            ArrayList<String> arr= db.getNodes(count);
            Location testx = new Location(arr.get(0), Integer.parseInt(arr.get(1)), Integer.parseInt(arr.get(2)), Integer.parseInt(arr.get(3)), arr.get(4), arr.get(5), arr.get(6), arr.get(7));
            count++;
            data.add(testx);
        }


        idCol.setCellValueFactory(new PropertyValueFactory<Location,String>("locID"));
        xCol.setCellValueFactory(new PropertyValueFactory<Location, Integer>("xcoord"));
        yCol.setCellValueFactory(new PropertyValueFactory<Location, Integer>("ycoord"));
        floorCol.setCellValueFactory(new PropertyValueFactory<Location, Integer>("floor"));
        buildingCol.setCellValueFactory(new PropertyValueFactory<Location, String >("building"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Location, String >("nodeType"));
        longCol.setCellValueFactory(new PropertyValueFactory<Location,String>("longName"));
        shortCol.setCellValueFactory(new PropertyValueFactory<Location, String>("shortName"));
        //noinspection CodeBlock2Expr
        table.setOnMouseClicked( event -> {
                setNext(table.getSelectionModel().getSelectedItem());});
        table.setItems(data);
    }

    public void setNext(Location proto) {
        this.makeEditable.setDisable(false);
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
            //noinspection ThrowablePrintedToSystemOut
            System.err.println(ex);
        }
    }

    @FXML
    private void downloadClicker() throws IOException {
        thestage = (Stage) download.getScene().getWindow();
        AnchorPane root;
        root =  FXMLLoader.load(getClass().getResource("downloadScreen.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }


}
