import java.util.ArrayList;

@SuppressWarnings("unused")
public class Location {

    private int xcoord, ycoord, floor;
    private String locID, building, nodeType, longName, shortName;
    private ArrayList<Edge> connectedEdges;

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

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public ArrayList<Location> findPath(Location startNode, Location endNode) {
        System.out.println("COOL");
        return new ArrayList<Location>();
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