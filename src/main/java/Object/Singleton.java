package Object;

/*Nikhil and Nathan
  Class created to allow for easy access to common fields ie. loggedIn, username
  Only use at the moment you need the class' information
  ***Example of how to use Object.Singleton Class***
  Object.Singleton single = Object.Singleton.getInstance();
  int check = single.getNum();
 */
public class Singleton {

    private static boolean loggedIn;
    private static String username;
    private static int num;
    private static String kioskID;
    private static long lastTime;

    private static Singleton single = new Singleton();
    private Singleton(){
        loggedIn = false;
        username = "";
        num = 1;
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
