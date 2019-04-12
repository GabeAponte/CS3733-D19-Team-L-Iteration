package SearchingAlgorithms;

import SearchingAlgorithms.PathfindingStrategy;
import Object.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AStarStrategy extends  TemplatePathFinder implements PathfindingStrategy {

    private HashMap<String, Location> lookup;
    private Location start, end;

    private ArrayList<Location> openList;
    private ArrayList<Location> closeList;

    public AStarStrategy(HashMap<String, Location> hash) {
        this.lookup = hash;
        //this.start = start;
        //this.end = end;
        openList = new ArrayList<Location>();
        closeList = new ArrayList<Location>();
    }

    public void calculateTotalScore(Location l, Location end, double gscore) {
        l.setScore(l.calculateScore(gscore, end));
    }
}
