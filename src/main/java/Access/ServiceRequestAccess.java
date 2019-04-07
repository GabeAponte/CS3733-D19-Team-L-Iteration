package Access;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ServiceRequestAccess extends DBAccess{

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private static final DateFormat tdf = new SimpleDateFormat("HHmm");

    /**ANDREW MADE THIS
     * deletes all the records from the serviceRequest table
     */
    public void deleteRecords() {
        String sql = "Delete from serviceRequest;";

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
     * @param rDept
     */
    public void makeRequest(String desc, String rDept){
        String sql = "insert into serviceRequest(" +
                "comment, requestDepartment, assignedEmployee, fulfilled)" +
                "values (?, ?, NULL, 0)";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, desc);
            pstmt.setString(2, rDept);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //todo REMIND DJ TO MAKE REQUESTID A AUTOINCREMENTED FIELD also our database doesnt have dates for reservations


    /**ANDREW MADE THIS
     * assign an employee to fulfill a request
     * @param rid
     * @param name
     */
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

    /**ANDREW MADE THIS
     * returns the record fields for the given index in serviceRequest
     * @param getNum
     * @return
     */
    public ArrayList<String> getRequests(int getNum){
        String sql = "SELECT * FROM serviceRequest where assignedEmployee is NULL";
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

    /** ANDREW MADE THIS
     * returns the number of records in serviceRequest
     *
     * @return int
     */
    public int countRecords() {
        String sql = "select COUNT(*) from serviceRequest where assignedEmployee is null";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;

    }
    /**ANDREW MADE THIS
     * assign an employee to fulfill a request
     */
    public void fulfillRequest(int rid, String employeeID, String table){
        String sql = "update " + table + " set assignedEmployee = ?, fulfilled = 1, completionTime = ?, completionDate = ? where requestID = ?";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Date date = new Date();
            pstmt.setString(1, employeeID);
            pstmt.setInt(2, Integer.parseInt(tdf.format(date.getTime())));
            pstmt.setString(3, sdf.format(date));
            pstmt.setInt(4, rid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }





    public static void main(String[] args) {
        ServiceRequestAccess sra = new ServiceRequestAccess();
    }
}
