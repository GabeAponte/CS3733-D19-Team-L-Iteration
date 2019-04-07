package Access;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ServiceRequestAccess extends DBAccess{

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private static final DateFormat tdf = new SimpleDateFormat("HHmm");
    /**ANDREW MADE THIS
     * assign an employee to fulfill a request
     */
    public void fulfillRequest(int rid, String employeeID, String table){
        String sql = "update " + table + " set assignedEmployee = ?, fulfilled = 1, completionTime = ?, completionDate = ? where requestID = ?";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Date date = new Date();
            pstmt.setString(1, employeeID);
            pstmt.setInt(2, Integer.parseInt(tdf.format(date.getTime())));
            pstmt.setString(3, sdf.format(date));
            pstmt.setInt(4, rid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public static void main(String[] args) {
        ServiceRequestAccess sra = new ServiceRequestAccess();
    }
}
