package SearchingAlgorithms;
import Access.EdgesAccess;
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

    //Larry - Using Depth First Search (DFS) to find the path from given start position to end position
    public Path findPath(Location start, Location end) {
        EdgesAccess ea = new EdgesAccess();
        ArrayList<Location> path = new ArrayList<Location>();
        Path p = new Path(path);
        if(start == end)
        {
            p.addToPath(start);
            return p;
        }

        path.add(start);
        start.setParentID("START");
        ArrayList<String> tempList = new ArrayList<String>();
        tempList = (ea.getConnectedNodes(start.getLocID()));
        






        Path p = new Path(path);

        return p;
    }
}
