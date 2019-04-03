import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URISyntaxException;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("HospitalHome.fxml")));
        primaryStage.setTitle("Prototype");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    @SuppressWarnings("RedundantThrows")
    public static void main(String[] args) throws URISyntaxException {
        NodesAccess na = new NodesAccess();
        EdgesAccess ea = new EdgesAccess();
        na.deleteRecords();
        ea.deleteRecords();
        na.readCSVintoTable();
        ea.readCSVintoTable();
        //System.out.println("2");
        //System.out.println("" + na.countRecords());
        ea.getConnectedNodes("DHALL02702");
        launch(args);

    }
}
