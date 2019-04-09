package Object;

/*Nikhil and Nathan
  Class created to allow for easy access to common fields ie. loggedIn, username
  Only use at the moment you need the class' information
  ***Example of how to use Object.Singleton Class***
  Object.Singleton single = Object.Singleton.getInstance();
  int check = single.getNum();
 */

import Access.EdgesAccess;
import Access.NodesAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Object.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Singleton {

    private static boolean loggedIn;
    private static String username;
    private static int num;
    private static String kioskID;
    private static long lastTime;
    private static int typePathfind;
    private static boolean isAdmin;
    private static int timeoutSec;

    private ObservableList<Location> data = FXCollections.observableArrayList();
    public HashMap<String, Location> lookup = new HashMap<String, Location>();

    private static Singleton single = new Singleton();
    private Singleton(){
        loggedIn = false;
        username = "";
        num = 1;
        kioskID = "";
        typePathfind = 1;
        isAdmin = false;
        timeoutSec = 500000;
    }

    public static int getTimeoutSec() {
        return timeoutSec;
    }

    public static void setTimeoutSec(int timeoutSec) {
        Singleton.timeoutSec = timeoutSec;
    }

    public synchronized void setData() {
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
