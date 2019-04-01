public class Edge {

    private Location startNode, endNode;
    private String edgeID;
    private double length;

    public void setEdgeID(String edgeID) {
        this.edgeID = edgeID;
    }

    //Two constructors- not sure if we really need ID, we can delete later
    public Edge(String edgeID, Location start, Location end) {
        this.edgeID = edgeID;
        this.startNode = start;
        this.endNode = end;
        this.length = findDistance(start, end);
    }
    public Edge(Location start, Location end) {
        this.startNode = start;
        this.endNode = end;
        this.edgeID = "";
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

    //Nathan - finds distance between two nodes, used in constructors
    public double findDistance(Location startNode, Location endNode){
        double xDiff, yDiff;

        xDiff = startNode.getXcoord() - endNode.getXcoord();
        yDiff = startNode.getYcoord() - endNode.getYcoord();

        xDiff = Math.pow(xDiff, 2);
        yDiff = Math.pow(yDiff, 2);

        xDiff += yDiff;
        return Math.sqrt(xDiff);
    }

}
