import java.util.ArrayList;

public class Path {

    private ArrayList<Location> path;

    public Path(ArrayList<Location> pathIn) {
        this.path = pathIn;
    }

    public ArrayList<Location> getPath() {
        return this.path;
    }
}
