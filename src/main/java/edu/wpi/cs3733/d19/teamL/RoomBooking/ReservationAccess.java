package edu.wpi.cs3733.d19.teamL.RoomBooking;

import edu.wpi.cs3733.d19.teamL.DBAccess;

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

    public void deleteReservation(String rID, String eID, String startDate, String endDate){
        String sql = "Delete from reservation where rID = ? and eID = ? and startDate = ? and endDate = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1,rID);
            pstmt.setString(2, eID);
            pstmt.setString(3, startDate);
            pstmt.setString(4, endDate);
            pstmt.executeUpdate();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }

    /**ANDREW MADE THIS
     * creates a reservation with the given parameters
     * @param rID
     * @param eID
     * @param startDate
     */
    public void makeReservation(String rID, String eID, String startDate, String endDate, String title, String description, String guestList, String type, boolean privacy){
        String sql = "insert into reservation(" +
                "rID, eID, startDate, endDate, title, description, type, guestList, privacy)" +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, rID);
            pstmt.setString(2, eID);
            pstmt.setString(3, startDate);
            pstmt.setString(4, endDate);
            pstmt.setString(5, title);
            pstmt.setString(6, description);
            pstmt.setString(7, type);
            pstmt.setString(8, guestList);
            pstmt.setBoolean(9, privacy);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        ReservationAccess ra = new ReservationAccess();
        ra.makeReservation("1", "1", "2018-05-05T11:50:55", "2018-05-05T11:55:55", "Birthday Bash", "DJ's birthday party", "Birthday", "Bob,Tim,Brian", true);
    }

}
