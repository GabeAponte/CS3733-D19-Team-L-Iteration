package edu.wpi.cs3733.d19.teamL.SearchingAlgorithms;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.EdgesAccess;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class BreadthFirstStrategy implements PathfindingStrategy{

    private HashMap<String, Location> lookup;
    private ArrayList<Location> visited;
    private LinkedList<Location> queue;

    private Location start, end;

    public BreadthFirstStrategy(HashMap<String, Location> hash) {
        this.lookup = hash;
        this.visited = new ArrayList<Location>();
        this.queue = new LinkedList<Location>();
    }

    //Author: PJ Mara
    public Path findPath(Location start, Location end) {
        EdgesAccess ea = new EdgesAccess();
        ArrayList<Location> path = new ArrayList<Location>();
        queue.add(start);
        start.setParentID("START");
        while (!queue.isEmpty()) {
            Location p = queue.poll();
            if (p.equals(end)) {
               // System.out.println(p.getParentID());
                return returnPath(p);
            }
            else {
                visited.add(p);
                ArrayList<Location> children = new ArrayList<Location>();
                ArrayList<String> childrenString = ea.getConnectedNodes(p.getLocID());
                for (String s : childrenString) {
                    children.add(lookup.get(s));
                }
                for (Location l : children) {
                    if (!queue.contains(l)) {
                        l.setParentID(p.getLocID());
                        queue.add(l);
                    }
                }
            }
        }

        Path p = new Path(path);
        return p;
    }

    public Path returnPath(Location obj) {
        Location l = obj;
        ArrayList<Location> forwardsPath = new ArrayList<Location>();
        Path p = new Path(forwardsPath);
        while (!(l.getParentID().equals("START"))) {
            p.addToPath(l);
            l = lookup.get(l.getParentID());
        }
        p.reversePath();
        cleanup();
        return p;
    }

    private void cleanup() {
        for (Location x : lookup.values()) {
            x.setParentID("RESET");
        }
        visited.clear();
        queue.clear();
    }

}


