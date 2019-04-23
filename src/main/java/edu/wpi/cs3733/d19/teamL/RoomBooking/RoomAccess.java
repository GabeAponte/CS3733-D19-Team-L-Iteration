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
     * @return
     */
    public ArrayList<String> getAvailRooms(String startDate, String endDate) {
        String sql = "select name from room as ro where ro.roomID not in\n" +
                "            (\n" +
                "              select re.rID\n" +
                "              from reservation as re\n" +
                "              where (startDate <= ? and endDate > ?)\n" +
                "                or (startDate < ? and endDate >= ?)\n" +

                "\n" +
                "             )";
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, startDate);
            pstmt.setString(3, endDate);
            pstmt.setString(4, endDate);

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
     * returns a boolean of whether a room is available
     * @param classRoomName
     * @param startDate
     * @param endDate
     * @return
     */
    public boolean checkRoom(String startDate, String endDate, String classRoomName) {
        String sql = "select name from room as ro where ro.roomID not in\n" +
                "            (\n" +
                "              select re.rID\n" +
                "              from reservation as re\n" +
                "              where (startDate <= ? and endDate > ?)\n" +
                "                or (startDate < ? and endDate >= ?)\n" +

                "\n" +
                "             )";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, startDate);
            pstmt.setString(2, startDate);
            pstmt.setString(3, endDate);
            pstmt.setString(4, endDate);


            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                if(rs.getString("name").equals(classRoomName)){
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
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




    /*public static void main(String[] args) {
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
    }*/
}
