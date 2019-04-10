package Access;

import javafx.scene.control.TreeItem;
import Object.SuggestionTable;

import java.sql.*;
import java.util.ArrayList;

public class SuggestionBasicAccess extends DBAccess{
    /**@author Gabe
     * deletes all the records from the SuggestionBasic table
     */
    public void deleteRecords(String body) {
        String sql = "Delete from suggestionBasic WHERE body = ?;";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, body);
            pstmt.executeUpdate();
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
    public TreeItem<SuggestionTable> getSuggestions(int getNum){
        TreeItem<SuggestionTable> nodeRoot = null;
        String sql = "SELECT * FROM suggestionBasic";
        int count = 0;
        //noinspection Convert2Diamond
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                if (count == getNum) {
                    data.add(rs.getString("body"));
                    nodeRoot = new TreeItem<>(new SuggestionTable(data.get(0)));
                }
                count++;
            }
            return nodeRoot;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
    public int countRecords() {
        String sql = "select COUNT(*) from suggestionBasic";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;

    }
}

