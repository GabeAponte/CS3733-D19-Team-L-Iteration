package edu.wpi.cs3733.d19.teamL.Map.MapLocations;

import javafx.scene.shape.Circle;

public class CircleLocation extends Circle {

    Location location;

    public  CircleLocation() {
        this.location = location;
        //setRadius(radius);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "CircleLocation{" +
                "location=" + location +
                '}';
    }
}
