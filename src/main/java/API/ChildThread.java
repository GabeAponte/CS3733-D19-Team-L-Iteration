package API;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ChildThread extends Thread {
    String type;
    String comment;

    public ChildThread(String type, String comment) {
        this.type = type;
        this.comment = comment;
    }

    //Nathan - sends email with given type and comment
    @SuppressWarnings("deprecation")
    @Override
    public void run() {
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
            message.setSubject("New " + type + " Service Request");

            // THIS STRING IS THE BODY OF THE EMAIL
            message.setText("Hello,\n" + "There is an outstanding service request with the following information:\n\n" + comment);

            // Send message
            Transport.send(message);
            Thread.currentThread().stop();
            //Thread.currentThread().join();

        } catch (MessagingException mex) {
            mex.printStackTrace();
            System.out.println("Failed to send: Messaging Exception");
        } /*catch (InterruptedException ie){
            ie.printStackTrace();
        }*/
    }
} 