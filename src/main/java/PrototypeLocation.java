@SuppressWarnings("unused")
public class PrototypeLocation {

    private int xcoord, ycoord, floor;
    private String id, building, nodeType, longName, shortName;

    public PrototypeLocation(String idIn, int xcoordIn, int ycoordIn, int floorIn, String buildingIn, String nodeTypeIn,
                             String longNameIn, String shortNameIn) {
        id = idIn;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}