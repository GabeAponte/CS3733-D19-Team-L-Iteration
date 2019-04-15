package edu.wpi.cs3733.d19.teamL.Map.MapLocations;

import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Circle;

public class CircleLocation extends Circle {

    Location location;
    ScrollPane sp;
    private JFXTextField xField;
    private JFXTextField yField;

    public  CircleLocation() {
        this.location = location;
        //setRadius(radius);
    }

    public ScrollPane getSp() {
        return sp;
    }

    public void setSp(ScrollPane sp) {
        this.sp = sp;
    }

    public JFXTextField getxField() {
        return xField;
    }

    public void setxField(JFXTextField xField) {
        this.xField = xField;
    }

    public JFXTextField getyField() {
        return yField;
    }

    public void setyField(JFXTextField yField) {
        this.yField = yField;
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
