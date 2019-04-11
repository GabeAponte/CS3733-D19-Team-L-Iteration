package edu.wpi.cs3733.d19.teamL.SearchingAlgorithms;
import Object.*;
import edu.wpi.cs3733.d19.teamL.Map.Location;
import edu.wpi.cs3733.d19.teamL.Map.Path;

public interface PathfindingStrategy {

    public Path findPath(Location start, Location end);
}
