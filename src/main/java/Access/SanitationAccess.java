package Access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SanitationAccess extends DBAccess {

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private static final DateFormat tdf = new SimpleDateFormat("HHmm");
    /**ANDREW MADE THIS
     * deletes all the records from the nodes table
     */
    public void deleteRecords() {
        String sql = "Delete from sanitationRequest;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**ANDREW MADE THIS
     * adds a new sanitation request to the database
     */
    public void makeRequest(String location, String comment, String type, String urgencyLevel){
        String sql = "insert into sanitationRequest(" +
                "location, creationTime, comment, type, urgencyLevel, creationDate)" +
                "values (?, ?, ?, ?, ?, ?)";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Date date = new Date();
            pstmt.setString(1, location);
            pstmt.setInt(2, Integer.parseInt(tdf.format(date.getTime())));
            pstmt.setString(3, comment);
            pstmt.setString(4, type);
            pstmt.setString(5, urgencyLevel);
            pstmt.setString(6, sdf.format(date));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
