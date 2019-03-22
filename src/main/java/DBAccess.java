import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DBAccess {
    /**
     * Connect to the db database
     * @return the Connection object
     */
    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:myDB/DB";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Drops the table protoNodes in the database
     * Basically it deletes all of the database information, probably for use on start up
     */
    public void dropTable(){
        String sql = "DROP TABLE IF EXISTS protoNodes;";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement()){
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * reads the csvFile given and adds it to given table
     * @param csvFile
     */
    public void readCSVintoTable(String csvFile){
        //String csvFile = "src/main/resources/PrototypeNodes.csv";
        String line = "";
        String cvsSplitBy = ",";
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                if(count != 0) {
                    // use comma as separator
                    String[] data = line.split(cvsSplitBy);

                    String sql = "insert into protoNodes(" +
                            "nodeID, xcoord, ycoord, floor, building, nodeType, longName, shortName)" +
                            "values (?, ?, ?, ?, ?, ?, ?, ?)";


                    try (Connection conn = this.connect();
                         PreparedStatement pstmt  = createPreparedStatement(conn, sql, data)) {
                        pstmt.executeUpdate();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
                else{
                    count++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * takes the information in the database table and writes it to a CSV
     */
    public void writeTableIntoCSV(){

    }


    /**
     * creates a prepared statement with terms from csv
     * separate method because java 8 does not support preparedStatement methods
     * within a try block
     * @param con
     * @param sql
     * @param data
     * @return
     * @throws SQLException
     */
    private PreparedStatement createPreparedStatement(Connection con, String sql, String[] data) throws SQLException{
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, data[0]);
        pstmt.setInt(2, Integer.parseInt(data[1]));
        pstmt.setInt(3, Integer.parseInt(data[2]));
        pstmt.setInt(4, Integer.parseInt(data[3]));
        pstmt.setString(5, data[4]);
        pstmt.setString(6, data[5]);
        pstmt.setString(7, data[6]);
        pstmt.setString(8, data[7]);
        return pstmt;
    }

    /**
     * create the database for prototype nodes
     */
    public void createDatabase(){
        String sql = "CREATE TABLE protoNodes(" +
                "nodeID TEXT PRIMARY KEY, " +
                "xcoord integer," +
                "ycoord integer," +
                "floor integer," +
                "building TEXT," +
                "nodeType TEXT," +
                "longName TEXT," +
                "shortName TEXT);";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement()){
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //todo
    /*
    method for editing database and creating the CSV from database
     */

    /**
     * Main within database class for testing use
     * @param args
     */
    public static void main(String[] args) {
        DBAccess db = new DBAccess();
        db.dropTable();
        db.createDatabase();
        db.readCSVintoTable("src/main/resources/PrototypeNodes.csv");
    }
}
