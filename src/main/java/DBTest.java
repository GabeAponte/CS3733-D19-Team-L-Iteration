import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTest {
    /**
     * Connect to the test.db database
     * @return the Connection object
     */
    /*private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:myDB/DB";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }*/


    /**
     * select all rows in the warehouses table
     */
    /*
    public void selectAll(){
        String sql = "SELECT Destination FROM PatientRooms";

        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("Destination") + "\t");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    */

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DBTest app = new DBTest();
        //app.selectAll();
    }
}
