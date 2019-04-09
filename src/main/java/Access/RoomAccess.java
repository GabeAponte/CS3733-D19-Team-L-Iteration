package Access;

import java.sql.*;
import java.util.ArrayList;

public class RoomAccess extends DBAccess {
    /**ANDREW MADE THIS
     * deletes all the records from the room table
     */
    public void deleteRecords() {
        String sql = "Delete from room;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**ANDREW MADE THIS
     * returns the record fields for the given index in room
     * @param getNum
     * @return
     */
    public ArrayList<String> getRooms(int getNum){
        String sql = "SELECT * FROM room";
        int count = 0;
        //noinspection Convert2Diamond
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                if (count == getNum) {
                    data.add(rs.getString("roomID"));
                    data.add(rs.getString("name"));
                    data.add(rs.getString("type"));
                    data.add(Integer.toString(rs.getInt("floor")));
                    data.add(Boolean.toString(rs.getBoolean("isOccupied")));
                }
                count++;
            }
            return data;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


    /**ANDREW MADE THIS
     * returns an arraylist of roomIDs and names that are available on this date
     * @param startDate
     * @param endDate
     * @param startTime
     * @param endTime
     * @return
     */
    public ArrayList<String> getAvailRooms(String startDate, String endDate, int startTime, int endTime){
        //TODO Should make this not ugly
        String sql = "select rID from reservation where (? between startDate and endDate) or (? between startDate and endDate) and (endtime <= ? or starttime >= ?);";
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
            pstmt.setInt(3, endTime);
            pstmt.setInt(4, startTime);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                data.add(rs.getString("rID"));
            }

            return data;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;

    }

    /**ANDREW MADE THIS
     * returns the record fields for the given index in room
     * @return
     */
    public ArrayList<String> getRoomID(){
        String sql = "SELECT roomID FROM room";
        //noinspection Convert2Diamond
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                data.add(rs.getString("roomID"));
            }
            return data;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static void main(String[] args) {
        RoomAccess ra = new RoomAccess();
        ra.getRooms(1);
    }
}
