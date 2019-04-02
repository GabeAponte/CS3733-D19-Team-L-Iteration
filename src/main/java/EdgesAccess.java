import java.io.*;
import java.sql.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

// Refer to DJ or Andrew for questions or concerns with this class
public class EdgesAccess extends DBAccess
{

    /**ANDREW MADE THIS
     * deletes all the records from the nodes table
     */
    public void deleteRecords() {
        String sql = "Delete from edges;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** DJ MADE THIS (Andrew also knows how this works)
     * reads the csvFile given and adds it to given table
     */
    public void readCSVintoTable() {
        //todo need csv file
        String line;
        String cvsSplitBy = ",";
        int count = 0;

        InputStream file;
        file = this.getClass().getClassLoader().getResourceAsStream("MapLedges.csv");//This needs to be modified
        //noinspection ConstantConditions
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file))) {

            while ((line = br.readLine()) != null) {
                if (count != 0) {
                    // use comma as separator
                    String[] data = line.split(cvsSplitBy);

                    String sql = "insert into edges(" +
                            "edgeID, startNode, endNode)" +
                            "values (?, ?, ?)";

                    try (Connection conn = this.connect();
                         PreparedStatement pstmt = createPreparedStatement(conn, sql, data)) {
                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    count++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** ANDREW MADE THIS
     * takes the information in the database table and writes it to a CSV
     */
    public void writeTableIntoCSV(String path) {
        File file;
        if (path.equals("")) {
            file = new File("output" + Long.toString(System.currentTimeMillis()) + ".csv");
        } else {
            file = new File(path + "\\output" + Long.toString(System.currentTimeMillis()) + ".csv");
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file, true));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sql = "select * from edges;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            try {
                //noinspection ConstantConditions
                writer.append("edgeID," +
                        "startNode," +
                        "endNode");
                writer.newLine();
            } catch (IOException o) {
                System.out.println(o.getMessage());
            }
            // loop through the result set
            while (rs.next()) {
                try {
                    //noinspection StringConcatenationInsideStringBufferAppend
                    writer.append(rs.getString("edgeID") +
                            "," + rs.getString("startNode") +
                            "," + rs.getString("endNode"));
                    writer.newLine();
                } catch (IOException o) {
                    System.out.println(o.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            //noinspection ConstantConditions
            writer.close();
        } catch (IOException o) {
            System.out.println(o.getMessage());
        }

    }

    @SuppressWarnings("Duplicates")
    public ArrayList<String> getConnectedNodes(String nodeID){
        String sql = "SELECT startNode, endNode FROM edges WHERE startNode= ? OR endNode= ?;";

        ArrayList<String> connectedNodes = new ArrayList<>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, nodeID);
            pstmt.setString(2, nodeID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                getFields(connectedNodes, rs);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("HERE");
        }

        for (int i = connectedNodes.size() - 1; i >= 0; i--) {
            if (connectedNodes.get(i).contains(nodeID)) {
                connectedNodes.remove(i);
            }
        }

        for(int i = 0; i < connectedNodes.size(); i++)
        {
            //System.out.println(connectedNodes.get(i));
        }
        return connectedNodes;

    }

    private ArrayList<String> getFields(ArrayList<String> data, ResultSet rs) throws SQLException {
        data.add(rs.getString("startNode"));
        data.add(rs.getString("endNode"));
        return data;
    }

    /**
     * DJ set this up (Andrew can also answer questions
     * creates a prepared statement with terms from csv
     * separate method because java 8 does not support preparedStatement methods
     * within a try block
     * ANDREW MADE THIS
     * @param con, Connection
     * @param sql, the sql string of code
     * @param data, the data we want to change
     * @return PreparedStatement
     * @throws SQLException, More info
     */
    private PreparedStatement createPreparedStatement(Connection con, String sql, String[] data) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, data[0]);
        pstmt.setString(2, data[1]);
        pstmt.setString(3, data[2]);
        return pstmt;
    }

    /**ANDREW MADE THIS
     * adds an edge to the database from the two given nodeIDs
     * @param node1
     * @param node2
     */
    public void addEdge(String node1, String node2){
        String sql = "insert into edges(" +
                "edgeID, startNode, endNode)" +
                "values (?, ?, ?)";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, node1 + "_" + node2);
            pstmt.setString(2, node1);
            pstmt.setString(3, node2);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** ANDREW MADE THIS
     * Call this to update the database with the new nodes into an edge
     *
     * @param edgeID, the ID of the node you want to edit
     * @param node1, the column of the table you want to edit
     * @param node2, the data you want to put in
     */
    public void updateEdge(String edgeID, String node1, String node2) {
        String sql = "update edges set startNode = ?, endNode = ? where edgeID= ?;";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, node1);
            pstmt.setString(2, node2);
            pstmt.setString(3, edgeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**ANDREW MADE THIS
     * deletes the edge with the given edgeID
     */
    public void deleteEdge(String edgeID) {
        String sql = "Delete from edges where edgeID = ?;";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, edgeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** ANDREW MADE THIS
     * Queries the database for all fields of the protoNodes class and returns an arraylist
     */
    public ArrayList<String> getEdges(int getNum) {
        String sql = "SELECT * FROM edges";
        int count = 0;
        //noinspection Convert2Diamond
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                if (count == getNum) {
                    data.add(rs.getString("edgeID"));
                    return getFields(data, rs);
                }
                count++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**ANDREW MADE THIS
     *  returns the fields of a particular nodeID in an arraylist
     * @param edgeID
     * @return
     */
    public ArrayList<String> getEdgeInformation(String edgeID){
        String sql = "SELECT * FROM edges where edgeID = ?";
        //noinspection Convert2Diamond
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, edgeID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return getFields(data, rs);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /** ANDREW MADE THIS
     * returns the number of records in protoNodes
     *
     * @return int
     */
    public int countRecords() {
        String sql = "select COUNT(*) from edges";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;

    }

    public boolean containsEdge(String edgeID) {
        ArrayList<String> edges = new ArrayList<String>();
        edges = getEdgeInformation(edgeID);
        if(edges == null) {
            return false;
        }
        else{
            return true;
        }
    }


    public static void main(String[] args) {
        EdgesAccess test = new EdgesAccess();
        //test.readCSVintoTable();
    }
}