package edu.wpi.cs3733.d19.teamL.API;
import Object.*;
import edu.wpi.cs3733.d19.teamL.Object.Singleton;

public class UpdateLocationThread extends Thread {

    public UpdateLocationThread() {}

    @Override
    public void run() {
        Singleton single = Singleton.getInstance();
        single.setData();
        //System.out.println("This was called successfully.");
        this.stop();
    }
}
