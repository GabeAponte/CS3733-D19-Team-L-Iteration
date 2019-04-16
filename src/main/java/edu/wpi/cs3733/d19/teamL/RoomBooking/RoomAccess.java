package edu.wpi.cs3733.d19.teamL.RoomBooking;

import edu.wpi.cs3733.d19.teamL.DBAccess;

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
    public ArrayList<String> getAvailRooms(String startDate, String endDate, int startTime, int endTime) {
        String sql = "select name from room left outer join (select rID from reservation where ? = startDate and ((? between startTime and endTime) or (? between startTime and endTime) or (startTime between ? and ?) or (endTime between ? and ?))) on roomID = rID where rID is null;";
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, startDate);
            pstmt.setInt(2, startTime);
            pstmt.setInt(3, endTime);
            pstmt.setInt(4, startTime);
            pstmt.setInt(5, endTime);
            pstmt.setInt(6, startTime);
            pstmt.setInt(7, endTime);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                data.add(rs.getString("name"));
            }
            return data;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
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

    /**ANDREW MADE THIS
     * returns the record fields for the given index in room
     * @return
     */
    public String getRoomID(String roomName){
        String sql = "SELECT roomID FROM room where name = ?";
        //noinspection Convert2Diamond
        String data = "";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, roomName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                data = (rs.getString("roomID"));
            }
            return data;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return data;
    }



    public static void main(String[] args) {
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> temp1 = new ArrayList<>();
        RoomAccess ra = new RoomAccess();
        temp = ra.getAvailRooms("2019-04-09", "2019-04-09", 1300, 1330);
        temp1 = ra.getRoomID();
        for(int i = 0; i < temp.size(); i++){
            System.out.println(temp.get(i));
        }
        for(int i = 0; i < temp1.size(); i++){
            //System.out.println("Room ID: " + temp1.get(i));
        }
        System.out.println("WTH");
    }
}
