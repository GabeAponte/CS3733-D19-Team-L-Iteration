package API;

import Object.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TimeoutThread extends Thread{
    private long timeSinceLast;
    private boolean notInterrupted;

    public TimeoutThread(){
        timeSinceLast = System.currentTimeMillis();
        notInterrupted = true;
        run();
    }

    public void resetTime(){
        timeSinceLast = System.currentTimeMillis();
    }

    //TODO update branch with master, set isAdmin to false when changing

    @Override
    public void run(){
        while(notInterrupted){
            if((System.currentTimeMillis() - timeSinceLast) > 10000){
                Singleton single = Singleton.getInstance();
                single.setLoggedIn(false);
                single.setUsername("");
                System.out.println("Attempting screen change");

                try {
                    Stage thestage = new Stage();
                    AnchorPane root;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource("HospitalHome.fxml"));
                    Scene scene = new Scene(root);
                    thestage.setScene(scene);
                } catch (IOException io){
                    System.out.println("Error in changing screens");
                }
            }
        }
    }
}
