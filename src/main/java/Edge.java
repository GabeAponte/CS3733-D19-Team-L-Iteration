public class Edge {

    private Location startNode, endNode;
    private String edgeID;
    private double length;

    public void setEdgeID(String edgeID) {
        this.edgeID = edgeID;
    }

    //Two constructors- not sure if we really need ID, we can delete later
    //TODO: compute length based off of X and Y
    public Edge(String edgeID, Location start, Location end) {
        this.edgeID = edgeID;
        this.startNode = start;
        this.endNode = end;
    }
    public Edge(Location start, Location end) {
        this.startNode = start;
        this.endNode = end;
        this.edgeID = "";
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



}
