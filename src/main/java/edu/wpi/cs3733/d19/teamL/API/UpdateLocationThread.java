package edu.wpi.cs3733.d19.teamL.API;
import edu.wpi.cs3733.d19.teamL.Singleton;

public class UpdateLocationThread extends Thread {

    public UpdateLocationThread() {}

    @Override
    public void run() {
        Singleton single = Singleton.getInstance();
        single.setData();
        this.stop();
    }
}
