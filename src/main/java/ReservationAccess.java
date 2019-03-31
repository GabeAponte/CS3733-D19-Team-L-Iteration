import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ReservationAccess extends DBAccess {
    /**ANDREW MADE THIS
     * deletes all the records from the reservation table
     */
    public void deleteRecords() {
        String sql = "Delete from reservation;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**ANDREW MADE THIS
     * creates a reservation with the given parameters
     *
     * @param eID
     * @param startTime
     * @param endTime
     */
    public void makeReservation(String rID, String eID, int startTime, int endTime){
        String sql = "insert into reservation(" +
                "rID, eID, startTime, endTime)" +
                "values (?, ?, ?, ?)";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, rID);
            pstmt.setString(2, eID);
            pstmt.setInt(3, startTime);
            pstmt.setInt(4, endTime);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        ReservationAccess ra = new ReservationAccess();
        ra.makeReservation("1", "1", 1400, 1500);
    }

}
