import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

@SuppressWarnings("unused")
public class Location implements Comparable<Location>{

    private int xcoord, ycoord, floor;
    private String locID, building, nodeType, longName, shortName, parentID;
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
        parentID = "NONE";
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
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

    public ArrayList<Edge> getEdges() { return connectedEdges;}

    public void addConnectedLocation(Location l) {}

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public int compareTo(Location loc){
        if(score <= loc.getScore()){
            return 1;
        }
        return 0;
    }

    //Nathan - checks to see if given location is closed (False if closed, true if not closed)
    private boolean isntClosed(Location loc, ArrayList<Location> closed){
        for(int i = 0; i < closed.size(); i++){
            //for all elements in closed, if any ID matches this ID, this ID is closed
            if(closed.get(i).getLocID() == loc.getLocID() || loc.getParentID() != "NONE"){
                return false;
            }
        }
        return true;
    }

    //Nathan - returns ArrayList of locations indicating path
    public ArrayList<Location> findPath(Location endNode, PriorityQueue<Location> open, ArrayList<Location> closed) {
        ArrayList<Location> neighbors = findNeighbors();
        //get this locations neighbors

        for(int i = 0; i < neighbors.size(); i++){
            //for each neighbor, if it isnt in closed and DOESNT have a parent, add to open, this = parent
            if(isntClosed(neighbors.get(i), closed)){
                neighbors.get(i).setParentID(this.locID);
                open.add(neighbors.get(i));
            }
        }

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

    //Larry - find the node in the Array list that has the lowest F value
    public Location findBestF(ArrayList<Location> locations){
        Location bestF = locations.get(0);
        for(int i = 1; i < locations.size(); i++){
            if(bestF.score < locations.get(i).score){
                bestF = bestF;
            }
            else{
                bestF = locations.get(i);
            }
        }
        return  bestF;

    }
    //Larry - add element to the open list if it is not in it before
    public void addToOpen(Location A, ArrayList<Location> openList){
        int count = 0;
        for(int i = 0; i< openList.size(); i++){
            if(A.locID == openList.get(i).locID){
                return;    // does not add anything
            }
            else{
                count++;
            }
        }
        //This means we did not find the Node in the List so add it into list
        if(count == openList.size()-1){
            openList.add(A);
        }


    }
}