import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import  javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLDocumentController   {

    boolean check1, check2, check3 = false;

    @FXML
    private Button btncalc;
    @FXML
    private TextField txtloan;
    @FXML
    private TextField txtinterest;
    @FXML
    private TextField txtnumyears;

    @FXML
    private Label lblanswer;

    @FXML
    public void initialize(){
        //btncalc.setDisable(true);
    }


    /*
        @FXML
        private void handleTextFieldChecker(Acti e) {
            System.out.println("HERE");
            if (e.getSource() ==txtinterest) {
                if (txtinterest.getText() == null || txtinterest.getText().trim().isEmpty()) {
                    check1 = false;
                }
            }
            else if (e.getSource() == txtloan) {
                if (txtloan.getText() == null || txtloan.getText().trim().isEmpty()) {
                    check2 = false;
                }
            }
            else {
                if (txtnumyears.getText() == null || txtnumyears.getText().trim().isEmpty()) {
                    check3 = false;
                }
            }
            if (check1 && check2 && check3) {
                btncalc.setDisable(false);
            }
            else {
                btncalc.setDisable(true);
            }

        }
    */
    @FXML
    private void handleButtonAction(ActionEvent e) {
        double answer, amount, monthlyrate, compound, years;
        monthlyrate = Double.parseDouble(txtinterest.getText());
        amount = Double.parseDouble(txtloan.getText());
        years = Double.parseDouble(txtnumyears.getText());
        compound = Math.pow(1.0+monthlyrate, years);
        answer = amount * (monthlyrate * compound ) / (compound - 1) / (years*12);
        lblanswer.setText("$ " + answer);
    }

}
