package edu.wpi.cs3733.d19.teamL.Map;

public class Edge {

    private Location startNode, endNode;
    private String startID, endID;
    private String edgeID;
    private double length;

    public void setEdgeID(String edgeID) {
        this.edgeID = edgeID;
    }

    public String getStartID() {
        return startID;
    }

    public String getEndID() {
        return endID;
    }

    //Two constructors- not sure if we really need ID, we can delete later
    public Edge(String edgeID, Location start, Location end) {
        this.edgeID = edgeID;
        this.startNode = start;
        this.endNode = end;
        this.startID = start.getLocID();
        this.endID = end.getLocID();

       // this.length = findDistance(start, end);
    }
    public Edge(Location start, Location end) {
        this.startNode = start;
        this.endNode = end;
        this.edgeID = "";
        this.startID = start.getLocID();
        this.endID = end.getLocID();
       // this.length = findDistance(start, end);
    }


    //getters and setters. Maybe delete setters?
    public Location getStartNode() {
        return startNode;
    }
    public void setStartNode(Location startNode) {
        this.startNode = startNode;
    }
    public Location getEndNode() {
        return endNode;
    }
    public void setEndNode(Location endNode) {
        this.endNode = endNode;
    }
    public String getEdgeID() {
        return edgeID;
    }

    //Edited by Nikhil- Accounts for z-axis now
    //Nathan - finds distance between two nodes, used in constructors
    public double findDistance(Location startNode, Location endNode){
        return startNode.findDistance(endNode);
    }

}
