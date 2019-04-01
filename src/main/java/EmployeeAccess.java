import java.sql.*;

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


}
