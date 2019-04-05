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
     * @param date
     * @param startTime
     * @param endTime
     * @return
     */
    public ArrayList<String> getAvailRooms(String date, int startTime, int endTime){
        //TODO Should make this not ugly
        String sql = "select roomID, name from room left outer join (select rID from reservation where rdate = ? and not (endtime <= ? or starttime >= ?))  on roomID = rID where rID is null;";
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, date);
            pstmt.setInt(2, startTime);
            pstmt.setInt(3, endTime);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                data.add(rs.getString("roomID"));
                data.add(rs.getString("name"));
            }

            return data;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;

    }

    public static void main(String[] args) {
        RoomAccess ra = new RoomAccess();
        ra.getRooms(1);
    }
}