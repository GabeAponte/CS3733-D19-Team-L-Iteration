import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SuggestionBasicAccess extends DBAccess{
    /**GABE MADE THIS
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

    public static void main(String[] args) {
        SuggestionBasicAccess sga = new SuggestionBasicAccess();

        sga.addSuggestion("uhfbdfibwerifnwrif");

        sga.deleteRecords();
    }
}

