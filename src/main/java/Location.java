import java.util.ArrayList;
import java.util.Queue;

@SuppressWarnings("unused")
public class Location {

    private int xcoord, ycoord, floor;
    private String locID, building, nodeType, longName, shortName;
    private ArrayList<Edge> connectedEdges;
    private double score;

    public Location(String idIn, int xcoordIn, int ycoordIn, int floorIn, String buildingIn, String nodeTypeIn,
                    String longNameIn, String shortNameIn) {
        locID = idIn;
        xcoord = xcoordIn;
        ycoord = ycoordIn;
        floor=  floorIn;
        building = buildingIn;
        nodeType = nodeTypeIn;
        longName = longNameIn;
        shortName = shortNameIn;
        connectedEdges = new ArrayList<Edge>();
        score = 0;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getXcoord() {
        return xcoord;
    }

    public void setXcoord(int xcoord) {
        this.xcoord = xcoord;
    }

    public int getYcoord() {
        return ycoord;
    }

    public void setYcoord(int ycoord) {
        this.ycoord = ycoord;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getLocID() {
        return locID;
    }

    public void setLocID(String locID) {
        this.locID = locID;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void addEdge(Edge e) { connectedEdges.add(e);}

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public ArrayList<Location> findPath(Location endNode, Queue<Location> open, Queue<Location> closed) {
        return new ArrayList<Location>();

    }

    //Nathan - returns ArrayList of locations which this location is connected to
    public ArrayList<Location> findNeighbors(){
        ArrayList<Location> neighbors = new ArrayList<Location>();
        for (int i = 0; i < connectedEdges.size(); i++){
            if(connectedEdges.get(i).getEndNode().getLocID() != this.getLocID()){
                neighbors.add(connectedEdges.get(i).getEndNode());
            } else {
                neighbors.add(connectedEdges.get(i).getStartNode());
            }
        }

        return neighbors;
    }

    //Nathan - calcualte this location's score
    //h is total edge length to this node, end node is ending node
    public double calculateScore(int h, Location endNode){
        double thisScore = h + findDistance(endNode);
        setScore(thisScore);
        return thisScore;
    }

    //Nathan - finds DIRECT distance between two nodes
    public double findDistance(Location endNode){
        double xDiff, yDiff;

        xDiff = this.getXcoord() - endNode.getXcoord();
        yDiff = this.getYcoord() - endNode.getYcoord();

        xDiff = Math.pow(xDiff, 2);
        yDiff = Math.pow(yDiff, 2);

        xDiff += yDiff;
        return Math.sqrt(xDiff);
    }
}