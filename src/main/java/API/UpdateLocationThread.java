package API;
import Object.*;

public class UpdateLocationThread extends Thread {

    public UpdateLocationThread() {}

    @Override
    public void run() {
        Singleton single = Singleton.getInstance();
        single.setData();
        System.out.println("This was called successfully.");
        this.stop();
    }
}
