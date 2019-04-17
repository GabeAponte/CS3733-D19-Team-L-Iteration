package edu.wpi.cs3733.d19.teamL.API;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserPreferences;
import com.teamdev.jxbrowser.chromium.javafx.BrowserView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Represents FXML control with address bar and view area that
 * displays URL entered in the address bar text field.
 */
public class BrowserViewControl implements Initializable {

    @FXML
    private TextField textField;

    @FXML
    private BrowserView browserView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Browser browser = new Browser();
        browserView = new BrowserView(browser);
        BrowserPreferences preferences = browserView.getBrowser().getPreferences();
        browserView.getBrowser().loadURL(textField.getText());
    }

    public void loadURL(ActionEvent actionEvent) {
        browserView.getBrowser().loadURL(textField.getText());
    }
}