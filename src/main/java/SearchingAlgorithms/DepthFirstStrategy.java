package SearchingAlgorithms;
import Object.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DepthFirstStrategy implements PathfindingStrategy{

    private HashMap<String, Location> lookup;

    public DepthFirstStrategy(HashMap<String, Location> hash) {
        this.lookup = hash;
        //this.start = start;
        //this.end = end;
    }

    public Path findPath(Location start, Location end) {
        ArrayList<Location> path = new ArrayList<Location>();
        Path p = new Path(path);
        return p;
    }
}
