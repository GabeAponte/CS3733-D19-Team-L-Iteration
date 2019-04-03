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
        primaryStage.setTitle("Team L Iteration 1");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    @SuppressWarnings("RedundantThrows")
    public static void main(String[] args) throws URISyntaxException {
        NodesAccess na = new NodesAccess();
        EdgesAccess ea = new EdgesAccess();
        ReservationAccess ra = new ReservationAccess();
        ServiceRequestAccess sra = new ServiceRequestAccess();
        SuggestionBasicAccess sba = new SuggestionBasicAccess();
        na.deleteRecords();
        ea.deleteRecords();
        ra.deleteRecords();
        sra.deleteRecords();
        sba.deleteRecords();

        na.readCSVintoTable();
        ea.readCSVintoTable();
        launch(args);
    }
}
