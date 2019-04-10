package Access;

import javafx.scene.control.TreeItem;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import Object.ServiceRequestTable;

public class AudioVisualAccess extends DBAccess{
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private static final DateFormat tdf = new SimpleDateFormat("HHmm");

    /**@author Gabe
     * deletes all the records from the serviceRequest table
     */
    public void deleteRecords() {
        String sql = "Delete from audioVisualRequest;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**@author Gabe
     * adds a new audioVisual request to the database
     *
     */
    public void makeRequest(String desc, String destination, String location, String type){
        String sql = "insert into audioVisualRequest(" +
                "comment, location, destination, creationTime, creationDate, type)" +
                "values (?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Date date = new Date();
            pstmt.setString(1, desc);
            pstmt.setString(2, location);
            pstmt.setString(3, destination);
            pstmt.setInt(4, Integer.parseInt(tdf.format(date.getTime())));
            pstmt.setString(5, sdf.format(date));
            pstmt.setString(6, type);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

/*
    /**ANDREW MADE THIS
     * assign an employee to fulfill a request
     * @param rid
     * @param name

    public void fulfillRequest(int rid, String name){
        String sql = "update serviceRequest set assignedEmployee = ?, fulfilled = 1 where requestID = ?";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, rid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
*/
    /**Gabe MADE THIS
     * returns the record fields for the given index in serviceRequest
     * @param getNum
     * @return
     */
    public TreeItem<ServiceRequestTable> getAudioVisualRequests(int getNum){
        TreeItem<ServiceRequestTable> nodeRoot = null;
        String sql = "SELECT * FROM audioVisualRequest where requestID is not NULL and fulfilled = 0";
        int count = 0;
        //noinspection Convert2Diamond
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                if (count == getNum) {
                    data.add(Integer.toString(rs.getInt("requestID")));
                    data.add(rs.getString("assignedEmployee"));
                    if (rs.getBoolean("fulfilled")){
                        data.add("Yes");
                    } else {
                        data.add("No");
                    }
                    data.add(rs.getString("location"));
                    data.add(Integer.toString(rs.getInt("creationTime")));
                    data.add(Integer.toString(rs.getInt("completionTime")));
                    data.add(rs.getString("comment"));
                    data.add(rs.getString("destination"));
                    data.add(rs.getString("type"));
                    data.add(rs.getString("creationDate"));
                    data.add(rs.getString("completionDate"));
                    nodeRoot = new TreeItem<>(new ServiceRequestTable(data.get(0), data.get(1), data.get(2), data.get(3), data.get(4), data.get(5), data.get(6), data.get(7), data.get(8), data.get(9), data.get(10)));
                }
                count++;
            }
            return nodeRoot;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /** Gabe MADE THIS
     * returns the number of records in serviceRequest
     *
     * @return int
     */
    public int countRecords() {
        String sql = "select COUNT(*) from audioVisualRequest where requestID is not null and fulfilled = false";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;

    }

    public static void main(String[] args) {
        ReligiousRequestAccess sra = new ReligiousRequestAccess();
    }
}
