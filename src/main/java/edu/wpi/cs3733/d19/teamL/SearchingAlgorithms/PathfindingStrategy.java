package edu.wpi.cs3733.d19.teamL.SearchingAlgorithms;
import Object.*;
import edu.wpi.cs3733.d19.teamL.Object.Location;
import edu.wpi.cs3733.d19.teamL.Object.Path;

public interface PathfindingStrategy {

    public Path findPath(Location start, Location end);
}
