package edu.wpi.cs3733.d19.teamL.Access;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

@SuppressWarnings({"UnnecessaryCallToStringValueOf", "SqlResolve"})
public class NodesAccess extends DBAccess{
    /**
     * Drops the table protoNodes in the database
     * Basically it deletes all of the database information, probably for use on start up
     * Andrew made this
     */
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS nodes;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**ANDREW MADE THIS
     * deletes all the records from the nodes table
     */
    public void deleteRecords() {
        String sql = "Delete from nodes;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** ANDREW MADE THIS
     * reads the csvFile given and adds it to given table
     */
    public void readCSVintoTable() {
        //todo need csv file
        this.deleteRecords();
        String line;
        String cvsSplitBy = ",";
        int count = 0;

        InputStream file;
        file = this.getClass().getClassLoader().getResourceAsStream("MapLnodes.csv");//This needs to be modified
        //noinspection ConstantConditions
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file))) {

            while ((line = br.readLine()) != null) {
                if (count != 0) {
                    // use comma as separator
                    String[] data = line.split(cvsSplitBy);

                    String sql = "insert into nodes(" +
                            "nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName)" +
                            "values (?, ?, ?, ?, ?, ?, ?, ?)";


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

    /**
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
        pstmt.setInt(2, Integer.parseInt(data[1]));
        pstmt.setInt(3, Integer.parseInt(data[2]));
        pstmt.setString(4, data[3]);
        pstmt.setString(5, data[4]);
        pstmt.setString(6, data[5]);
        pstmt.setString(7, data[6]);
        pstmt.setString(8, data[7]);
        return pstmt;
    }


    /** ANDREW MADE THIS
     * create the database for prototype nodes
     */
    public void createDatabase() {
        String sql = "CREATE TABLE nodes(" +
                "nodeID TEXT PRIMARY KEY, " +
                "xcoord integer not null, " +
                "ycoord integer not null," +
                "floor TEXT not null," +
                "building TEXT not null," +
                "nodeType TEXT not null," +
                "longName TEXT not null," +
                "shortName TEXT not null);";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /** ANDREW MADE THIS
     * returns the number of records in protoNodes
     *
     * @return int
     */
    public int countRecords() {
        String sql = "select COUNT(*) from nodes";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;

    }

    /** ANDREW MADE THIS
     * Queries the database for all fields of the protoNodes class and returns an arraylist
     */
    public ArrayList<String> getNodes(int getNum) {
        String sql = "SELECT * FROM nodes";
        int count = 0;
        //noinspection Convert2Diamond
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                if (count == getNum) {
                    data.add(rs.getString("nodeID"));
                    return getFields(data, rs);
                }
                count++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return null;
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
        String sql = "select * from nodes;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            try {
                //noinspection ConstantConditions
                writer.append("nodeID," +
                        "xcoord," +
                        "ycoord," +
                        "floor," +
                        "building," +
                        "nodeType," +
                        "longName," +
                        "shortName");
                writer.newLine();
            } catch (IOException o) {
                System.out.println(o.getMessage());
            }
            // loop through the result set
            while (rs.next()) {
                try {
                    //noinspection StringConcatenationInsideStringBufferAppend
                    writer.append(rs.getString("nodeID") +
                            "," + Integer.toString(rs.getInt("xcoord")) +
                            "," + Integer.toString(rs.getInt("ycoord")) +
                            "," + (rs.getString("floor")) +
                            "," + rs.getString("building") +
                            "," + rs.getString("nodeType") +
                            "," + rs.getString("longName") +
                            "," + rs.getString("shortName"));
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

    /** ANDREW MADE THIS
     * Call this to update the database with the required paramaters
     *
     * @param nodeID, the ID of the node you want to edit
     * @param field, the column of the table you want to edit
     * @param data, the data you want to put in
     */
    public void updateNode(String nodeID, String field, int data) {
        String sql = "update nodes set " + field + "= ? where nodeID= ?;";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, data);
            pstmt.setString(2, nodeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** DJ MADE THIS
     * Call this to update the database with the required paramaters
     *
     * @param nodeID, the ID of the node you want to edit
     * @param field, the column of the table you want to edit
     * @param data, the data you want to put in
     */
    public void updateNode(String nodeID, String field, String data) {
        String sql = "update nodes set " + field + "= ? where nodeID= ?;";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, data);
            pstmt.setString(2, nodeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**ANDREW MADE THIS
     * adds a node to the database from an arraylist string of the fields
     * @param data
     */
    public void addNode(ArrayList<String> data){
        String sql = "insert into nodes(" +
                "nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName)" +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = createPreparedStatement(conn, sql, data)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**ANDREW MADE THIS
     * deletes the node with the given nodeID
     */
    public void deleteNode(String nodeID) {
        String sql = "Delete from nodes where nodeID = ?;";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nodeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
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
    private PreparedStatement createPreparedStatement(Connection con, String sql, ArrayList<String> data) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, data.get(0));
        pstmt.setInt(2, Integer.parseInt(data.get(1)));
        pstmt.setInt(3, Integer.parseInt(data.get(2)));
        pstmt.setString(4, data.get(3));
        pstmt.setString(5, data.get(4));
        pstmt.setString(6, data.get(5));
        pstmt.setString(7, data.get(6));
        pstmt.setString(8, data.get(7));
        return pstmt;
    }

    /**ANDREW MADE THIS
     *  returns the fields of a particular nodeID in an arraylist
     * @param nodeID
     * @return
     */
    public ArrayList<String> getNodeInformation(String nodeID){
        String sql = "SELECT * FROM nodes where nodeID = ?";
        //noinspection Convert2Diamond
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, nodeID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return getFields(data, rs);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private ArrayList<String> getFields(ArrayList<String> data, ResultSet rs) throws SQLException {
        data.add(Integer.toString(rs.getInt("xcoord")));
        data.add(Integer.toString(rs.getInt("ycoord")));
        data.add(rs.getString("floor"));
        data.add(rs.getString("building"));
        data.add(rs.getString("nodeType"));
        data.add(rs.getString("longName"));
        data.add(rs.getString("shortName"));
        return data;
    }


    public String getNodebyCoord(int x, int y, String floor, String type) {
        String sql = "SELECT * from nodes where xcoord >= ? - 15 AND xcoord <= ? + 15 AND ycoord >= ? - 15 AND ycoord <= ? + 15 AND floor = ? " +
                "AND nodeType = ?;";
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, x);
            pstmt.setInt(2, x);
            pstmt.setInt(3, y);
            pstmt.setInt(4, y);
            pstmt.setString(5, floor);
            pstmt.setString(6,type);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getString("nodeID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String getNodebyCoordNoType(int x, int y, String floor, int tolerance) {
        String sql = "SELECT * from nodes where xcoord >= ? - ? AND xcoord <= ? + ? AND ycoord >= ? - ? AND ycoord <= ? + ? AND floor = ?;";
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, x);
            pstmt.setInt(2, tolerance);
            pstmt.setInt(3, x);
            pstmt.setInt(4, tolerance);
            pstmt.setInt(5, y);
            pstmt.setInt(6, tolerance);
            pstmt.setInt(7, y);
            pstmt.setInt(8, tolerance);
            pstmt.setString(9, floor);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getString("nodeID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<String> getValidITLocations(String theFloor){
        String sql = "SELECT * from nodes where (nodeType != \"HALL\") and (nodeType != \"STAI\") and (nodeType != \"ELEV\") and (nodeType != \"REST\") and (nodeType != \"BATH\") and (nodeType != \"EXIT\") and floor = ?;";
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, theFloor);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                data.add(rs.getString("longName"));
            }
            return data;
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        NodesAccess na = new NodesAccess();
        //na.dropTable();
        //na.createDatabase();
        //na.readCSVintoTable();
    }
}
