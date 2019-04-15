package edu.wpi.cs3733.d19.teamL;

/*Nikhil and Nathan
  Class created to allow for easy access to common fields ie. loggedIn, username
  Only use at the moment you need the class' information
  ***Example of how to use Object.Singleton Class***
  Object.Singleton single = Object.Singleton.getInstance();
  int check = single.getNum();
 */

import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Edge;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.EdgesAccess;
import edu.wpi.cs3733.d19.teamL.Map.Pathfinding.NodesAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.text.Text;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Singleton {

    private static boolean loggedIn;
    private static String username;
    private static int num;
    private static String kioskID;
    private static long lastTime;
    private static int typePathfind;
    private static boolean isAdmin;
    private static int timeoutSec;
    private static boolean doPopup;
    private static Text txt;

    private ObservableList<Location> data = FXCollections.observableArrayList();
    public HashMap<String, Location> lookup = new HashMap<String, Location>();

    private static Singleton single = new Singleton();
    private Singleton(){
        loggedIn = false; //is user logged in
        username = ""; //username of logged in user
        num = 1; //for test classes only
        kioskID = ""; //kiosk node ID
        typePathfind = 1; //which strategy selection for pathfinding
        isAdmin = false; //is signedin employee an admin
        timeoutSec = 45000; //how long before timeout (in ms) 1000 = 1 second
        doPopup = true; //should be more appropriately named initializeClock
        txt = new Text();
    }

    public void populateTweets(){
        List<Status> statuses = searchtweets();
        for (Status status : statuses) {
            Text temp = new Text(status.getText());
            txt = new Text(txt.getText() + "     " + temp.getText());
        }
    }

    private static List<Status> searchtweets() {
        // The factory instance is re-useable and thread safe.
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            List<Status> statuses = twitter.getHomeTimeline();
            for (Status status : statuses) {
                System.out.println(status.getUser().getName() + ":" +
                        status.getText());
            }
            System.out.println("finished search");
            return statuses;
        } catch (TwitterException e){
            System.out.println("ERROR");
            System.out.println(e.getCause());
            return null;
        }
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
        }
    }

    public static boolean isIsAdmin() {
        return isAdmin;
    }

    public static void setIsAdmin(boolean isAdmin) {
        Singleton.isAdmin = isAdmin;
    }

    public static int getTypePathfind() {
        return typePathfind;
    }

    public static void setTypePathfind(int typePathfind) {
        Singleton.typePathfind = typePathfind;
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

    public static String getKioskID() {
        return kioskID;
    }

    public static void setKioskID(String kioskID) {
        Singleton.kioskID = kioskID;
    }
}
