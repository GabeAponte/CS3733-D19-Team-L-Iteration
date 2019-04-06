import Access.*;
import Object.*;
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
        //Singleton single = Singleton.getInstance();
        NodesAccess na = new NodesAccess();
        System.out.println("NA");
        EdgesAccess ea = new EdgesAccess();
        System.out.println("EA");
        ReservationAccess ra = new ReservationAccess();
        System.out.println("RA");
        ServiceRequestAccess sra = new ServiceRequestAccess();
        System.out.println("SRA");
        SuggestionBasicAccess sba = new SuggestionBasicAccess();
        System.out.println("SBA");
        //ea.deleteRecords();

        //na.readCSVintoTable();
        System.out.println("CSV1");
        //ea.readCSVintoTable();
        System.out.println("CSV2");
        launch(args);
    }
}
