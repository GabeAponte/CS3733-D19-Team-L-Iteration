package Access;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
}

