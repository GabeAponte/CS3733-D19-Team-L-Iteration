import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class EditEdgesController {

    @FXML
    private Stage thestage;

    @FXML
    private Button EditEdgeBack;

    @FXML
    private Button EditEdgeSubmit;

    @FXML
    private void backPressed() throws IOException {
        /*thestage = (Stage) EditEdgeBack.getScene().getWindow();
        AnchorPane root;
        if(--) {
            root = FXMLLoader.load(getClass().getResource("EditLocation.fxml"));
        } else {
            root = FXMLLoader.load(getClass().getResource("EditNode.fxml"));
        }
        Scene scene = new Scene(root);
        thestage.setScene(scene);*/
    }

    private void populateNodeList(ArrayList<String> nodes)  {

    }

}
