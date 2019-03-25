import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {


    @FXML
    TableColumn id;

    @FXML
    TableColumn name;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("sample.fxml")));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 600, 475));
        primaryStage.show();
        /*
        PrototypeLocation test1 = new PrototypeLocation(1, "Elevator");
        PrototypeLocation test2 = new PrototypeLocation(2, "Coffee");

        final ObservableList<PrototypeLocation> data = FXCollections.observableArrayList(test1, test2);
        */




    }


    public static void main(String[] args) {
        DBAccess db = new DBAccess();
        db.dropTable();
        db.createDatabase();
        db.readCSVintoTable("src/main/resources/PrototypeNodes.csv");
        launch(args);
    }
}
