package Access;

import java.sql.*;
import java.util.ArrayList;

public class SuggestionBasicAccess extends DBAccess{
    /**@author Gabe
     * deletes all the records from the SuggestionBasic table
     */
    public void deleteRecords() {
        String sql = "Delete from suggestionBasic;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** @author Gabe
     * Adds an inputed suggestion into the databse in the format of a string
     *
     * @param suggestion
     */
    public void addSuggestion (String suggestion) {
        String sql = "insert into suggestionBasic(" +
                "ID, body)" +
                "values (?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "" + System.currentTimeMillis());
            pstmt.setString(2, suggestion);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /** ANDREW MADE THIS
     * Queries the database and returns an arraylist of all the suggestions
     */
    public ArrayList<String> getSuggestions() {
        //noinspection Convert2Diamond
        String sql = "select * from suggestionBasic";
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                data.add(rs.getString("body"));
            }
            return data;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}

