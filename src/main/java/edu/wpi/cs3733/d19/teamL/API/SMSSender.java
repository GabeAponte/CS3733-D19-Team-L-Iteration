package edu.wpi.cs3733.d19.teamL.API;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSSender extends Thread{
    //EVERY TIME YOU RUN THIS PROGRAM IT WILL SUBTRACT $0.0075 FROM YOUR ACCOUNT
    // Find your Account Sid and Token at twilio.com/user/account
    //if issues arise, change these from private to public
    private static final String ACCOUNT_SID = "AC7de78fd59ecd796684e237c4113f754d";
    private static final String AUTH_TOKEN = "8dd5293b60aad7cc9fdaf36bb9f456f2";
    private String toSend;
    private String recipient;


    public SMSSender(String messege, String recipient){
        this.toSend = messege;
        this.recipient = recipient;
        convertPhone();
    }

    private void convertPhone(){
        recipient = recipient.replaceAll("-", "");

        if(recipient.length() < 10){
            Thread.currentThread().stop();
        }

        if(!recipient.startsWith("+1")){
            recipient = "+1" + recipient;
        }

        if(recipient.length() != 12){
            Thread.currentThread().stop();
        }
    }

    public void run() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        //First phone number is recipient
        //Second phone number is sender (Twilio) w/ non-test acount SID and AUTH token
        Message message = Message.creator(new PhoneNumber(recipient),
                new PhoneNumber("+13394445441"),
                toSend).create();


        Thread.currentThread().stop();
    }
}