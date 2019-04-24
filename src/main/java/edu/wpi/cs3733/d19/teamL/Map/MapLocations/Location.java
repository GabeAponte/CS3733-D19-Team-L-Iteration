package edu.wpi.cs3733.d19.teamL.Map.MapLocations;

import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.EdgesAccess;

import java.util.ArrayList;
import java.util.PriorityQueue;

@SuppressWarnings("unused")
public class Location implements Comparable<Location>{

    private int xcoord, ycoord;
    private String locID, building, nodeType, longName, shortName, parentID, floor;
    private ArrayList<Edge> connectedEdges;
    private double score, gScore;
    private int nameSimilarityScore;


    public Location(String idIn, int xcoordIn, int ycoordIn, String floorIn, String buildingIn, String nodeTypeIn,
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
        gScore = 0;
    }

    public Location() {}


    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        if (this.parentID.equals("NONE")) {
            this.parentID = parentID;
        }
        if (parentID.equals("RESET")) {
            this.parentID = "NONE";
        }
    }

    public double getScore() {
        return score;
    }

    public double getGScore() {
        return gScore;
    }


    public void setScore(double score) {
        this.score = score;
    }

    public void setGScore(double gscore) {
        this.gScore = gscore;
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

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
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
    public boolean isntClosed(Location loc, ArrayList<Location> closed){
        for(int i = 0; i < closed.size(); i++){
            //for all elements in closed, if any ID matches this ID, this ID is closed
            if(closed.get(i).getLocID().equals(loc.getLocID()) || loc.getParentID() != "NONE"){
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
    //g is total edge length to this node, end node is ending node
    public double calculateScore(double g, Location endNode){
        double thisScore = g + findDistance(endNode);
        this.setScore(thisScore);
        return thisScore;
    }

    //Edited by Nikhil- Accounts for z-axis now
    //Nathan - finds DIRECT distance between two nodes
    public double findDistance(Location endNode){
        double xDiff, yDiff, zDiff;

        xDiff = this.getXcoord() - endNode.getXcoord();
        yDiff = this.getYcoord() - endNode.getYcoord();

        //Nikhil- We want to prioritize getting onto the same floor to ensure we travel as fast as possible.
        zDiff = 100 * (this.convertToNum() - endNode.convertToNum());
        if(this.nodeType.equals("STAI") || endNode.nodeType.equals("STAI")){
            zDiff *= 3;
        }
        else if(this.nodeType.equals("ELEV") || endNode.nodeType.equals("ELEV")){
            zDiff *= 2;
        }
        xDiff = Math.pow(xDiff, 2);
        yDiff = Math.pow(yDiff, 2);
        zDiff = Math.pow(zDiff, 2);

        xDiff += yDiff;
        xDiff += zDiff;
        return Math.sqrt(xDiff);
    }

    //Larry - Given a list of locations
    //This function will find the node in the Array list that has the lowest F value
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
    //Larry - Given a list of location and a location
    //This function will add element to the open list if it is not in it before
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

    public int convertToNum() {
        int fl;
        if(floor.equals("L2")){
            fl = 0;
            return fl;
        }
        if(floor.equals("L1")){
            fl = 1;
            return fl;
        }
        if(floor.equals("G")){
            fl = 2;
            return fl;
        }
        if(floor.equals("1")){
            fl = 3;
            return fl;
        }
        if(floor.equals("2")){
            fl = 4;
            return fl;
        }
        if(floor.equals("3")){
            fl = 5;
            return fl;
        }
        return 0;
    }

    @Override
    public String toString() {
        return longName;
    }

    public void removeEdge(ArrayList<Edge> e) {
        connectedEdges.removeAll(e);
    }

    public void restitch() {
        EdgesAccess ea = new EdgesAccess();
        if (this.checkIfEasyHallway()) {
            Location[] locs = new Location[2];
            for (int i = 0; i <connectedEdges.size(); i ++) {
                if (!connectedEdges.get(i).getStartNode().equals(this)) {
                    locs[i] = connectedEdges.get(i).getStartNode();
                }
                else {
                    locs[i] = connectedEdges.get(i).getEndNode();
                }
            }
            ea.addEdge(locs[0].getLocID(), locs[1].getLocID());
            System.out.println("SUCCESS");
        }
        else {
            System.out.println("NOT EASY ENOUGH");
        }
    }

    private boolean checkIfEasyHallway() {
        int count = 0;
        if (this.getNodeType().equals("HALL")) {
            if (this.connectedEdges.isEmpty()) {
                System.out.println("EMPTY EDGES");
                return false;
            }
            for (Edge e: connectedEdges) {
                if (!e.getEndNode().getNodeType().equals("HALL") || !e.getStartNode().getNodeType().equals("HALL") || count >1) {
                    if (count >1) {
                        System.out.println("TOO MANY EDGES");
                    }
                    else {
                        System.out.println("NOT HALLWAYS, TOO RISKY");
                    }
                    return false;
                }
            }
        }
        else {
            return false;
        }
        if (count == 2) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getNameSimilarityScore() {
        return nameSimilarityScore;
    }

    public void setNameSimilarityScore(int nameSimilarityScore) {
        this.nameSimilarityScore = nameSimilarityScore;
    }

}