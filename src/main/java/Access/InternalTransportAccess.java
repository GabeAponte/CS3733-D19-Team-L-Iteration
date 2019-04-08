package Access;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import Object.*;

public class InternalTransportAccess extends DBAccess{
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private static final DateFormat tdf = new SimpleDateFormat("HHmm");

    /**ANDREW MADE THIS
     * deletes all the records from the serviceRequest table
     */
    public void deleteRecords() {
        String sql = "Delete from internalTransportationRequest;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**ANDREW MADE THIS
     * adds a new request to the database
     * @param desc
     * @param startLocation
     * @param endLocation
     * @param type
     * @param phoneNumber
     */
    public void makeRequest(String desc, Location startLocation, Location endLocation, String type, String phoneNumber){
        String sql = "insert into internalTransportationRequest(" +
                "creationTime, comment, startLocation, endLocation, type, phoneNumber, creationDate)" +
                "values (?, ?, ?, ?, ?, ?, ?)";
        //TODO: creationtime and creationdate

        //create the date object
        Date date = new Date();

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sdf.format(date));
            pstmt.setString(2, desc);
            pstmt.setString(3, startLocation.getLocID());
            pstmt.setString(4, endLocation.getLocID());
            pstmt.setString(5, type);
            pstmt.setString(6, phoneNumber);
            pstmt.setInt(7, Integer.parseInt(tdf.format(date.getTime())));

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
     *
    public void fulfillRequest(int rid, String name){
        String sql = "update internalTransportationRequest set assignedEmployee = ?, fulfilled = 1 where requestID = ?";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, rid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    **ANDREW MADE THIS
     * returns the record fields for the given index in serviceRequest
     * @param getNum
     * @return
     *
    public ArrayList<String> getRequests(int getNum){
        String sql = "SELECT * FROM internalTransportationRequest where assignedEmployee is NULL";
        int count = 0;
        //noinspection Convert2Diamond
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                if (count == getNum) {
                    data.add(""+rs.getInt("requestID"));
                    if(rs.getString("comment") != null){
                        data.add(rs.getString("comment"));
                    }
                    else{
                        data.add("no comment");
                    }
                    data.add(rs.getString("requestDepartment"));
                    if(rs.getString("assignedEmployee") != null){
                        data.add(rs.getString("assignedEmployee"));
                        data.add("Fulfilled");
                    }
                    else{
                        data.add("none assigned");
                        data.add("not Fulfilled");
                    }
                }
                count++;
            }
            return data;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    ** NATHAN MADE THIS
     * returns the number of records in internalTransportationRequest
     *
     * @return int
     *
    public int countRecords() {
        String sql = "select COUNT(*) from internalTransportationRequest where assignedEmployee is null";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;

    }*/

    public static void main(String[] args) {
        ServiceRequestAccess sra = new ServiceRequestAccess();
    }
}
