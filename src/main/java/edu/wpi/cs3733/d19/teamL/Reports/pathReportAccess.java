package edu.wpi.cs3733.d19.teamL.Reports;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import edu.wpi.cs3733.d19.teamL.DBAccess;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.ServiceRequestDBAccess.ReligiousRequestAccess;
import javafx.scene.control.TreeItem;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

public class pathReportAccess extends DBAccess {
    /**
     * ANDREW MADE THIS
     * deletes all the records from the table
     */
    public void deleteRecords() {
        String sql = "Delete from pathReport;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * ANDREW MADE THIS
     * adds a report to the database with required parameters
     *
     */
    public void addReport(String timeCreate, String startLoc, String endLoc, String foundType) {
        String sql = "insert into pathReport(" +
                "timeCreated, startLocation, endLocation, foundType)" +
                "values (?, ?, ?, ?)";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, timeCreate);
            pstmt.setString(2, startLoc);
            pstmt.setString(3, endLoc);
            pstmt.setString(4, foundType);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> getItems(int getNum) {
        String sql = "SELECT * FROM pathReport";
        int count = 0;
        //noinspection Convert2Diamond
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                if (count == getNum) {
                    data.add(rs.getString("timeCreate"));
                    return getFields(data, rs);
                }
                count++;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private ArrayList<String> getFields(ArrayList<String> data, ResultSet rs) throws SQLException {
        data.add(rs.getString("startLoc"));
        data.add(rs.getString("endLoc"));
        data.add(rs.getString("foundType"));
        return data;
    }

    public ArrayList<Integer> getTypeCounts() throws SQLException {
        ArrayList<Integer> counts = new ArrayList<Integer>();
        int searched = 0;
        int poi = 0;
        int selected = 0;
        searched = getSingleCount("search");
        poi = getSingleCount("poi");
        selected = getSingleCount("selected");
        counts.add(searched);
        counts.add(poi);
        counts.add(selected);
        return counts;
    }


    public int getSingleCount(String types) throws SQLException {
        String sql = "select COUNT(*) from pathReport where foundType =?;";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, types);

            ResultSet rs = pstmt.executeQuery(); {

                return rs.getInt(1);
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static void main(String[] args) {
        pathReportAccess pa = new pathReportAccess();
    }

}

