package Access;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeAccess extends DBAccess{
    /**ANDREW MADE THIS
     * deletes all the records from the employee table
     */
    public void deleteRecords() {
        String sql = "Delete from employee;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**ANDREW MADE THIS
     * checks if an employee entered correct login information
     * @param username
     * @param password
     * @return
     */
    public boolean checkEmployee(String username, String password){
        String sql = "select password from employee where username = ?";
        String check = "";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                if(rs.getString("password").equals(password)){
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**ANDREW MADE THIS
     *  returns the fields of a particular employee in an arraylist
     * @param username
     * @return
     */
    public ArrayList<String> getNodeInformation(String username){
        String sql = "SELECT * FROM employee where username = ?";
        //noinspection Convert2Diamond
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                data.add(rs.getString("employeeID"));
                data.add(rs.getString("department"));
                data.add(rs.getString("isAdmin"));
            }
            return data;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


}
