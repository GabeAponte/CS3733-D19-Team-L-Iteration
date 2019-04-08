package Access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql .*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MaintenanceRequestAccess extends DBAccess{

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private static final DateFormat tdf = new SimpleDateFormat("HHmm");

    /**
     * @author PJ
     * deletes all the records from the serviceRequest table
     */
    public void deleteRecords() {
        String sql = "Delete from maintenanceRequest;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @author Gabe
     * adds a new religious request to the database
     */
    public void makeRequest(String desc, String denom, String location, Boolean isHazard, String type) {
        String sql = "insert into maintenanceRequest(" +
                "comment, denomination, location, creationTime, creationDate, isHazard, type)" +
                "values (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Date date = new Date();
            pstmt.setString(1, desc);
            pstmt.setString(2, denom);
            pstmt.setString(3, location);
            pstmt.setInt(4, Integer.parseInt(tdf.format(date.getTime())));
            pstmt.setString(5, sdf.format(date));
            pstmt.setString(6, isHazard);
            pstmt.setString(7, type);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Access.MaintenanceRequestAccess sra = new Access.MaintenanceRequestAccess();
    }
}
