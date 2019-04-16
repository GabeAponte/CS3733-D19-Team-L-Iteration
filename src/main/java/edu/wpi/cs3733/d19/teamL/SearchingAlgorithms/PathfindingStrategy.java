package edu.wpi.cs3733.d19.teamL.SearchingAlgorithms;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Path;

public interface PathfindingStrategy {

    //Nathan modified this to include path prefernce (restrictions)
    public Path findPath(Location start, Location end, String restriction);
}
