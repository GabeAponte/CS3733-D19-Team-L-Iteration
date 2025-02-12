package edu.wpi.cs3733.d19.teamL.SearchingAlgorithms;

import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Edge;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Path;
import edu.wpi.cs3733.d19.teamL.Singleton;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class TemplatePathFinder implements PathfindingStrategy{

    private ArrayList<Location> openList;
    private ArrayList<Location> closeList;
    private HashMap<String, Location> lookup;

    abstract void calculateTotalScore(Location l, Location end, double gscore);

    public final Path findPath(Location start, Location end, String restrictions) {
        ArrayList<Location> openList = new ArrayList<Location>();
        ArrayList<Location> closeList = new ArrayList<Location>();
        Singleton single = Singleton.getInstance();
        lookup = single.lookup;
        openList.add(start);
        start.setParentID("START");
        ArrayList<Location> path = new ArrayList<Location>();
        Path p = new Path(path);
        if(start == end)
        {
            p.addToPath(start);
            cleanup();
            return p;
        }
        Location q = new Location();
        //while there are items in the open list
        while (!(openList.isEmpty())) {
            q = q.findBestF(openList);
            openList.remove(q);
            closeList.add(q);
            q = lookup.get(q.getLocID());
            ArrayList<Edge> edge = q.getEdges();
            ArrayList<Location> children = new ArrayList<Location>();
            for (Edge e : edge) {
                if (!(closeList.contains(e.getEndNode())) && !(openList.contains(e.getEndNode()))) {
                    children.add(e.getEndNode());
                    e.getEndNode().setGScore(e.findDistance(q, e.getEndNode()));
                }
            }
            for (Location l : children) {
                //condition for found node
                if (l.getLocID().equals(end.getLocID())) {
                    lookup.get(l.getLocID()).setParentID(q.getLocID());
                    l.setParentID(q.getLocID());
                    return returnPath(l);
                } else {
                    //double gScore = q.getGScore() + l.calculateScore(0, q);
                    double gScore = q.getGScore() + l.getGScore(); //calculate base G score
                    calculateTotalScore(l, end, gScore);
                    l.setParentID(q.getLocID());
                    lookup.get(l.getLocID()).setParentID(q.getLocID());
                    if (!openList.contains(l) && !closeList.contains(l)) {
                        if(l.getNodeType().equals(restrictions) && q.getNodeType().equals(restrictions) && !l.getFloor().equals(q.getFloor())){
                            continue;
                        }
                        openList.add(l);
                    }
                }
            }
        }
        return p;
    }
    private void cleanup() {
        Singleton single = Singleton.getInstance();
        lookup = single.lookup;
        for (Location x : lookup.values()) {
            x.setParentID("RESET");
        }
        //openList.clear();
        //closeList.clear();
    }

    public Path returnPath(Location obj) {
        Singleton single = Singleton.getInstance();
        lookup = single.lookup;
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

    abstract public String toString();

}
