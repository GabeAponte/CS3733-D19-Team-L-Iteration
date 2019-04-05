package Object;

import java.util.ArrayList;

public class Path {

    private ArrayList<Location> path;

    public Path(ArrayList<Location> pathIn) {
        this.path = pathIn;
    }

    public ArrayList<Location> getPath() {
        return this.path;
    }

    public void addToPath(Location l) {
        path.add(l);
    }

    public String toString() {
        String toRet = "";
        for (int i = 0; i < path.size(); i ++) {
            toRet += path.get(i).getLocID() + ", ";
        }
        return toRet;
    }
}
