import edu.wpi.cs3733.d19.teamL.Account.EmployeeAccess;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.EdgesAccess;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.NodesAccess;
import edu.wpi.cs3733.d19.teamL.Singleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("HospitalHome.fxml")));
        primaryStage.setTitle("Team L Iteration 3");
        primaryStage.setScene(new Scene(root));
        //primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    @SuppressWarnings("RedundantThrows")
    public static void main(String[] args) throws URISyntaxException {
        Singleton single = Singleton.getInstance();
        NodesAccess na = new NodesAccess();
        EdgesAccess ea = new EdgesAccess();
        ea.deleteRecords();
        na.readCSVintoTable();
        ea.readCSVintoTable();
        single.setData();
        single.populateTweets();
        launch(args);



    }
}
