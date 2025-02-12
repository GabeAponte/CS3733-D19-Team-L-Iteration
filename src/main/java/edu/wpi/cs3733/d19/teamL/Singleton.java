package edu.wpi.cs3733.d19.teamL;

/*Nikhil and Nathan
  Class created to allow for easy access to common fields ie. loggedIn, username
  Only use at the moment you need the class' information
  ***Example of how to use Object.Singleton Class***
  Object.Singleton single = Object.Singleton.getInstance();
  int check = single.getNum();
 */

import edu.wpi.cs3733.d19.teamL.API.Weather;
import edu.wpi.cs3733.d19.teamL.Connectivity.Esp_Server;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Edge;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.EdgesAccess;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.NodesAccess;
import edu.wpi.cs3733.d19.teamL.RoomBooking.VisualSimulationThread;
import edu.wpi.cs3733.d19.teamL.SearchingAlgorithms.AStarStrategy;
import edu.wpi.cs3733.d19.teamL.SearchingAlgorithms.PathfindingStrategy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.text.Text;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.*;

public class Singleton {

    private static boolean loggedIn;
    private static String username;
    private static int num;
    private static Location kiosk;
    private static long lastTime;
    private static PathfindingStrategy typePathfind;
    private static boolean isAdmin;
    private static int timeoutSec;
    private static boolean doPopup;
    private static Text txt;
    private static Weather weather;
    private static long startTime;
    private static CareTaker ct;
    private static VisualSimulationThread sim;
    private static Esp_Server esp;

    private ObservableList<Location> data = FXCollections.observableArrayList();
    public HashMap<String, Location> lookup = new HashMap<String, Location>();

    private Comparator comparator = new Comparator() {
        @Override
        public int compare(Object o1, Object o2) {
            Location l1 = (Location) o1;
            Location l2 = (Location) o2;
            return l1.getLongName().compareTo(l2.getLongName());

        }
    };

    private static Singleton single = new Singleton();
    private Singleton(){
        loggedIn = false; //is user logged in
        username = ""; //username of logged in user
        num = 1; //for test classes only
        kiosk = null; //kiosk node
        typePathfind = null; //which strategy selection for pathfinding
        isAdmin = false; //is signedin employee an admin
        timeoutSec = 500000; //how long before timeout (in ms) 1000 = 1 second
        doPopup = true; //should be more appropriately named initializeClock
        txt = new Text();
        weather = new Weather();
        startTime = System.currentTimeMillis();
        ct = new CareTaker();
        sim = new VisualSimulationThread(86);
        sim.start();
        esp = new Esp_Server();
        //esp.setDaemon(true);
        esp.start();
    }

    public static boolean isFree() {
        //System.out.println("SINGLETON: " + esp.getFree());
        return esp.getFree();
    }

    public static void resetEmergency() {
        esp.turnOffEmergency();
    }

    public static boolean isEmergency() {
        return esp.getEmergency();
    }

    public static ArrayList<Boolean> getSimulation(){
        return sim.getSimulation();
    }

    public void populateTweets(){
        List<Status> statuses = searchtweets();
        if(statuses != null && statuses.size() != 0) {
            for (Status status : statuses) {
                Text temp = new Text(status.getText());
                txt = new Text(txt.getText() + "     " + temp.getText());
            }
        }else{
            txt = new Text("No Tweets to Display :)");
        }
    }

    private static List<Status> searchtweets() {
        // The factory instance is re-useable and thread safe.
        List<Status> statuses;
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            statuses = twitter.getHomeTimeline();
            return statuses;
        } catch (TwitterException e){
            System.out.println("ERROR");
            //System.out.println(e.getCause());
            return null;
        }
    }

    public static void saveMemento(String fxml){
        Memento m = new Memento(fxml);
        ct.save(m);
    }

    public static void saveMemento(String fxml, String Preference, String type, String floorFilter, Location start, Location end){
        Memento m = new Memento(fxml, Preference, type, floorFilter, start, end);
        ct.save(m);
    }

    public static Memento getOrig(){
        if(loggedIn){
            if(isAdmin){
                return ct.getAdmin();
            }
            return ct.getEmp();
        }
        return ct.getOriginal();
    }

    public static Memento restore(){
        return ct.restore();
    }

    public static void setStartTime(){
        startTime = System.currentTimeMillis();
    }

    public static long getStartTime(){
        return startTime;
    }

    public static String getWeatherIcon(){
        return weather.getIcon();
    }

    public static void updateWeather(){
        weather = new Weather();
    }

    public static String getWeatherTemp(){
        return weather.getActTemp();
    }
    public static Text getTxt(){
        return txt;
    }

    public static boolean isDoPopup(){
        return doPopup;
    }

    public static void setDoPopup(boolean flag){
        doPopup = flag;
    }

    public static int getTimeoutSec() {
        return timeoutSec;
    }

    public static void setTimeoutSec(int timeoutSec) {
        Singleton.timeoutSec = timeoutSec;
    }

    public synchronized void setData() {
        lookup.clear();
        data.clear();
        NodesAccess na = new NodesAccess();
        EdgesAccess ea = new EdgesAccess();
        ArrayList<String> edgeList;
        int count;
        count = 0;
        while (count < na.countRecords()) {
            ArrayList<String> arr = na.getNodes(count);
            ArrayList<String> arr2;
            Location testx = new Location(arr.get(0), Integer.parseInt(arr.get(1)), Integer.parseInt(arr.get(2)), arr.get(3), arr.get(4), arr.get(5), arr.get(6), arr.get(7));
            //only add the node if it hasn't been done yet
            if (!(lookup.containsKey(arr.get(0)))) {
                lookup.put((arr.get(0)), testx);
                data.add(testx);
                edgeList = ea.getConnectedNodes(arr.get(0));
                for (int j = 0; j < edgeList.size(); j++) {
                    String nodeID = edgeList.get(j);
                    if (lookup.containsKey(na.getNodeInformation(nodeID).get(0))) {
                        Edge e = new Edge(testx.getLocID()+"_"+nodeID, testx, lookup.get(nodeID));
                        testx.addEdge(e);
                    } else {
                        arr2 = na.getNodeInformation(nodeID);
                        Location testy = new Location(nodeID, Integer.parseInt(arr2.get(0)), Integer.parseInt(arr2.get(1)), arr2.get(2), arr2.get(3), arr2.get(4), arr2.get(5), arr2.get(6));
                        Edge e = new Edge(testx.getLocID()+"_"+nodeID, testx, testy);
                        testx.addEdge(e);
                    }
                }
            }
            else {
                edgeList = ea.getConnectedNodes(arr.get(0));
                for (int j = 0; j < edgeList.size(); j++) {
                    String nodeID = edgeList.get(j);
                    if (lookup.containsKey(na.getNodeInformation(nodeID).get(0))) {
                        Edge e = new Edge(testx.getLocID()+"_"+nodeID, testx, lookup.get(nodeID));
                        testx.addEdge(e);
                    } else {
                        arr2 = na.getNodeInformation(nodeID);
                        Location testy = new Location(nodeID, Integer.parseInt(arr2.get(0)), Integer.parseInt(arr2.get(1)), arr2.get(2), arr2.get(3), arr2.get(4), arr2.get(5), arr2.get(6));
                        Edge e = new Edge(testx.getLocID()+"_"+nodeID, testx, testy);
                        testx.addEdge(e);
                    }
                }
            }
            count++;
            setTypePathfind(new AStarStrategy(lookup));
        }
        data.sort(comparator);
    }

    public static boolean isIsAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        Singleton.isAdmin = isAdmin;
    }

    public static PathfindingStrategy getTypePathfind() {
        return typePathfind;
    }

    public static void setTypePathfind(PathfindingStrategy strategy) {
        Singleton.typePathfind = strategy;
    }

    public ObservableList<Location> getData() {
        return data;
    }

    public static void setLastTime(){
        lastTime = System.currentTimeMillis();
    }

    public static long getLastTime(){
        return lastTime;
    }

    public static Singleton getInstance() {
        return single;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static void setLoggedIn(boolean loggedIn) {
        Singleton.loggedIn = loggedIn;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Singleton.username = username;
    }

    public static int getNum() {
        return num;
    }

    public static void setNum(int num) {
        Singleton.num = num;
    }

    public static Location getKiosk() { return kiosk; }

    public static void setKiosk(Location Kiosk) {
        Singleton.kiosk = Kiosk;
    }

    /*
    Author: PJ Mara
    Deletes specific node records and associated edges
    so that the entire data doesn't need to be rebuilt for performance
     */
    public void deleteNode(Location l) {

        EdgesAccess ea = new EdgesAccess();
        //get a list of the nodes it is connected to
        ArrayList<String> locIDs = ea.getConnectedNodes(l.getLocID());
        //loop through those and delete an edge that contains this loc ID
        for (String id : locIDs) {
            Location focus = lookup.get(id);
            ArrayList<Edge> edgeList = new ArrayList<Edge>();
            for (Edge e: focus.getEdges()) {
                if (e.getStartID().equals(l.getLocID()) || e.getEndID().equals(l.getLocID())) {
                    edgeList.add(e);
                }
            }
            focus.removeEdge(edgeList);
        }
        // once its been cleaned up, delete from the lookup
        lookup.remove(l.getLocID(), l);
        data.remove(l);
    }

    /*
    Author: PJ Mara
    Changes out the current node with the ID with the new passed in loc
     */
    public void modifyNode(Location oldLoc, Location newLoc) {
        ArrayList<Edge> edges = oldLoc.getEdges();
        for (Edge e : edges) {
            if (e.getEndNode().getLocID().equals(oldLoc)) {
                e.setEndNode(newLoc);
            }
            else {
                e.setStartNode(newLoc);
            }
            newLoc.addEdge(e);
        }
        data.remove(lookup.get(oldLoc.getLocID()));
        data.add(newLoc);

        lookup.remove(oldLoc.getLocID(), oldLoc);
        lookup.put(newLoc.getLocID(), newLoc);
        data.sort(comparator);
    }

    /*
    Author: PJ Mara
    Adds an edge between the two passed in nodes
     */
    public void addEdge(Location start, Location end, Edge e) {
        for (Edge x : start.getEdges()) {
            if (x.equals(e)) {
                return;
            }
        }
        System.out.println(start.getLocID());
        System.out.println(end.getLocID());
        Edge e1 = new Edge(end.getLocID()+"_"+start.getLocID(), end, start);
        lookup.get(start.getLocID()).addEdge(e);
        lookup.get(end.getLocID()).addEdge(e1);
        System.out.println(e.getEdgeID());
    }

    /*
    Author: PJ Mara
    Deletes an edge between two nodes
     */
    public void deleteEdge(Edge edge) {
        Location focus = lookup.get(edge.getStartID());
        Location second = lookup.get(edge.getEndID());
        ArrayList<Edge> toDel = new ArrayList<Edge>();
        for (Edge e: focus.getEdges()) {
            if (e.getEdgeID().equals(edge.getEdgeID())) {
                toDel.add(e);
            }
        }
        focus.removeEdge(toDel);
        toDel.clear();
        for (Edge e : second.getEdges()) {
            if (e.getEdgeID().equals(edge.getEdgeID()) || (e.getEndID()+"_"+e.getStartID()).equals(edge.getEdgeID())) {
                toDel.add(e);
            }
        }
        second.removeEdge(toDel);
    }

    /*
    Author: PJ Mara
    Adds a node to the lookup and data
     */
    public void addNode (Location l) {
        lookup.put(l.getLocID(), l);
        data.add(l);
        data.sort(comparator);
    }
}