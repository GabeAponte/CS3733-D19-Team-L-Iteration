package edu.wpi.cs3733.d19.teamL.Map.MapLocations;

import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class CircleLocation extends Circle {

    Location location;
    ScrollPane sp;
    ArrayList<Line> lineList;
    private JFXTextField xField;
    private JFXTextField yField;

    public ArrayList<Line> getLineList() {
        return lineList;
    }

    public void setLineList(ArrayList<Line> lineList) {
        this.lineList = lineList;
    }

    public  CircleLocation() {
        this.location = location;
        this.lineList = new ArrayList<Line>();
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

    public boolean equals(CircleLocation test) {
        if(location.getLocID().equals(test.getLocation().getLocID())) {
            System.out.println("IS EQUAL");
            return true;
        }
        else {
            System.out.println("IS NOT EQUAL");
            return false;
        }
    }

    @Override
    public String toString() {
        return "CircleLocation{" +
                "location=" + location +
                '}';
    }
}
