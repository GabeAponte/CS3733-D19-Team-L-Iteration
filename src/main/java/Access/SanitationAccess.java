package Access;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    /** ANDREW MADE THIS
     * Queries the database for all fields of the sanitation request table and returns an arraylist of arraylist string
     */
    public ArrayList<ArrayList<String>> getRequests() {
        String sql = "SELECT * FROM sanitationRequest";
        //noinspection Convert2Diamond
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ArrayList<String> data = new ArrayList<String>();
                data.add(Integer.toString(rs.getInt("requestID")));
                data.add(rs.getString("assignedEmployee"));
                if(rs.getBoolean("fulfilled")){
                    data.add("true");
                } else {
                    data.add("false");
                }
                data.add(rs.getString("location"));
                data.add(rs.getString("comment"));
                data.add(rs.getString("type"));
                data.add(rs.getString("urgencyLevel"));
                data.add(rs.getString("creationDate"));
                data.add(rs.getString("completionDate"));
                list.add(data);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
