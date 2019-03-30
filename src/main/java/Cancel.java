import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
//TODO: Uncomment the following import statements
/*
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
*/

public class Cancel {

    private Stage thestage;
    private String typeOfService;
    private String comment;

    @FXML
    Button Back;

    @FXML
    private Button yes;

    @FXML
    private Button no;

    @FXML
    public Label typeLabel;

    //Nathan - stores information passed from another controller
    void init(String service){
        typeOfService = service;
        comment = "";
    }

    //Nathan - stores information passed from another controller
    void init(String service, String description){
        typeOfService = service;
        comment = description;
    }

    //Nathan - takes user back to ServiceSubController (and fills in proper info)
    @SuppressWarnings("Duplicates")
    @FXML
    private void backPressed() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServiceSubController.fxml"));

        Parent sceneMain = loader.load();

        ServiceSubController controller = loader.<ServiceSubController>getController();

        if(comment == null || comment.equals("")){
            controller.init(typeOfService);
        } else {
            controller.init(typeOfService, comment);
        }
        thestage = (Stage) yes.getScene().getWindow();

        Scene scene = new Scene(sceneMain);
        thestage.setScene(scene);
    }

    //Nathan - make a new service request and store it in the database, and sends email
    //TODO: Store request in Database
    @FXML
    private void yesClicked() throws IOException{
        //TODO: Uncomment next line
        /*
        // RECIPIENT EMAIL
        // Note: while you DO need sender username and password you do NOT need recipients (obviously)
        String to = "nwalzer007@gmail.com";

        // SENDER EMAIL
        String from = "lavenderloraxcs3733@gmail.com";

        // SEND EMAIL USING GMAIL SERVERS
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // SETUP : DO NOT TOUCH
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.socketFactory.port", "465"); //must be included, default 25
        properties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true"); //requires username and password (needed)
        properties.put("mail.smtp.port", "465"); //must be included, default 25

        // something something internet session who cares
        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        //PUT SENDER USERNAME AND PASSWORD HERE
                        return new PasswordAuthentication("lavenderloraxcs3733","LavenderLorax2019");
                    }
                });

        try {
            // DONT TOUCH THESE LINES
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));


            // THIS STRING IS THE SUBJECT
            message.setSubject("New " + typeOfService + " Service Request");

            // THIS STRING IS THE BODY OF THE EMAIL
            message.setText("Hello,\n" + "There is an outstanding service request with the following information:\n\n" + comment);

            // Send message
            Transport.send(message);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
        */
        //TODO: Uncomment previous line

        noClicked();
    }

    //Nathan - return the user to the home screen
    @SuppressWarnings("Duplicates")
    @FXML
    private void noClicked() throws IOException {
        thestage = (Stage) no.getScene().getWindow();
        AnchorPane root;
        root = FXMLLoader.load(getClass().getResource("HospitalHome.fxml"));
        Scene scene = new Scene(root);
        thestage.setScene(scene);
    }
}
