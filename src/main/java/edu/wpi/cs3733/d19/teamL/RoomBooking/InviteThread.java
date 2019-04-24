package edu.wpi.cs3733.d19.teamL.RoomBooking;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class InviteThread extends Thread{
    Reservation r;
    String recipient;

    //TODO Make a reservation class with toString method for appending on to body of email
    public InviteThread(Reservation r, String recipient) {
        this.r = r;
        this.recipient = recipient;
    }

    /**@author Nathan
     * Sends an email to the specified recipient and with information for a given event
     */
    @SuppressWarnings("deprecation")
    @Override
    public void run() {
        // RECIPIENT EMAIL
        // Note: while you DO need sender username and password you do NOT need recipients (obviously)
        String to = recipient;

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
            message.setSubject("New Event Invite");

            // THIS STRING IS THE BODY OF THE EMAIL
            message.setText("You have been invited to attend an event with the following information:\n\n" + r.toString());

            // Send message
            Transport.send(message);

            // Thread kills itself
            Thread.currentThread().stop();

        } catch (MessagingException mex) {
            mex.printStackTrace();
            Thread.currentThread().stop();
        }
    }
}
