import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Refer to DJ or Andrew for questions or concerns with this class
public class EdgesAccess extends DBAccess
{
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

    public static void main(String[] args) {
        EdgesAccess test = new EdgesAccess();
        test.readCSVintoTable();
    }
}