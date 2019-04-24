import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.EdgesAccess;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.NodesAccess;
import edu.wpi.cs3733.d19.teamL.Reports.pathReportAccess;
import edu.wpi.cs3733.d19.teamL.Singleton;
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
        primaryStage.setTitle("Team L Iteration 3");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        //primaryStage.setResizable(false);
        primaryStage.show();
    }

    @SuppressWarnings("RedundantThrows")
    public static void main(String[] args) throws URISyntaxException {
        Singleton single = Singleton.getInstance();
        NodesAccess na = new NodesAccess();
        EdgesAccess ea = new EdgesAccess();
        pathReportAccess p = new pathReportAccess();
        ea.deleteRecords();
        na.readCSVintoTable();
        ea.readCSVintoTable();

        single.setData();
        single.populateTweets();
        single.updateWeather();
        launch(args);



    }
}
