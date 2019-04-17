package edu.wpi.cs3733.d19.teamL.SearchingAlgorithms;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.EdgesAccess;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class DepthFirstStrategy implements PathfindingStrategy{

    private HashMap<String, Location> lookup;
    private ArrayList<Location> visited;
    private LinkedList<Location> stack;

    public DepthFirstStrategy(HashMap<String, Location> hash) {
        this.lookup = hash;
        this.visited = new ArrayList<Location>();
        this.stack = new LinkedList<Location>();
        //this.start = start;
        //this.end = end;
    }

    //Larry - Using Depth First Search (DFS) to find the path from given start position to end position
    //Nathan modified this to include path prefernece (restrictions)
    public Path findPath(Location start, Location end, String restrictions) {
        EdgesAccess ea = new EdgesAccess();
        ArrayList<Location> path = new ArrayList<Location>();
        stack.push(start);
        start.setParentID("START");
        Path p = new Path(path);

        while(!stack.isEmpty()){
            Location A = stack.pop();
            if (A.equals(end)) {
                return returnPath(A);
            }
            else{
                visited.add(A);
                ArrayList<String> neighboursID = ea.getConnectedNodes(A.getLocID());

                for(int i = 0; i < neighboursID.size(); i++ ){
                    Location l = lookup.get(neighboursID.get(i));
                    if(l != null && !(visited.contains(l))){
                        //if child and parent node are restricted type, continue
                        if(l.getNodeType().equals(restrictions) && A.getNodeType().equals(restrictions) && !l.getFloor().equals(A.getFloor())){
                            continue;
                        }
                        stack.add(l);
                        visited.add(l);
                        l.setParentID(A.getLocID());

                    }
                }
            }

        }


        return p;
    }
    //Larry - revise the return path for DFS; return the path given a location
    public Path returnPath(Location obj) {
        Location l = obj;
        ArrayList<Location> path = new ArrayList<Location>();
        Path p = new Path(path);
        while (!(l.getParentID().equals("START"))) {
            p.addToPath(l);
            l = lookup.get(l.getParentID());
        }
        p.reversePath();
        cleanup();
        return p;
    }

    //Larry - clean up the list
    private void cleanup() {
        for (Location x : lookup.values()) {
            x.setParentID("RESET");
        }
        visited.clear();
        stack.clear();
    }

    @Override
    public String toString() {
        return "DepthFirst";
    }

}