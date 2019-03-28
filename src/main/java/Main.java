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
        primaryStage.setScene(new Scene(root, 600, 475));
        primaryStage.show();
    }

    @SuppressWarnings("RedundantThrows")
    public static void main(String[] args) throws URISyntaxException {
        DBAccess db = new DBAccess();
        db.dropTable();
        db.createDatabase();
        db.readCSVintoTable();
        launch(args);
    }
}
