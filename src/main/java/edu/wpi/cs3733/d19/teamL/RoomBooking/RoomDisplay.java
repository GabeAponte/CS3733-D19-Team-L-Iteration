package edu.wpi.cs3733.d19.teamL.RoomBooking;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class RoomDisplay {

    boolean available;
    int index;
    String roomName;
    double coordinates[] = {0};
    Polygon p;

    RoomDisplay(String name, double[] coords){
        this.roomName = name;
        this.coordinates = coords;
    }

    public boolean isAvailable() {
        return available;
    }

    public void makePolygon(double scaleRatio){
        for (int j = 0; j < this.coordinates.length; j++) {
            coordinates[j] = coordinates[j] * scaleRatio;
        }
        p = new Polygon(coordinates);
    }

    public void changePolygonColor(String color){
        p.setStroke(Color.web(color));
        p.setFill(Color.web(color));
        p.setOpacity(0.3);
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getRoomName() {
        return roomName;
    }

    public Polygon getPolygon() {
        return p;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public double[] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(double[] coordinates) {
        this.coordinates = coordinates;
    }
}
