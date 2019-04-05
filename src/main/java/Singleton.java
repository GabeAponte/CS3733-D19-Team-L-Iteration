public class Singleton {

    private static boolean loggedIn;
    private static String username;
    private static int num;

    private static Singleton single = new Singleton();
    private Singleton(){
        loggedIn = false;
        username = "";
        num = 0;
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
}
