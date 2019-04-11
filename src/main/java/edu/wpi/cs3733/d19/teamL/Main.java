package edu.wpi.cs3733.d19.teamL;

import Access.*;
import Object.*;
import edu.wpi.cs3733.d19.teamL.Access.EdgesAccess;
import edu.wpi.cs3733.d19.teamL.Access.NodesAccess;
import edu.wpi.cs3733.d19.teamL.Object.Singleton;
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
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @SuppressWarnings("RedundantThrows")
    public static void main(String[] args) throws URISyntaxException {
        Singleton single = Singleton.getInstance();
        NodesAccess na = new NodesAccess();
        EdgesAccess ea = new EdgesAccess();
        //ea.deleteRecords();
        //na.readCSVintoTable();
        //ea.readCSVintoTable();
        single.setData();
        launch(args);
    }
}
