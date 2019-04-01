import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RoomAccess extends DBAccess {
    /**ANDREW MADE THIS
     * deletes all the records from the room table
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

    //todo ask DJ about the intention for isOccupied - is it part of a reservation or something else?

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
