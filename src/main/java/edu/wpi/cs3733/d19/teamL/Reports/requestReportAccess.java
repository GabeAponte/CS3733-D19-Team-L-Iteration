package edu.wpi.cs3733.d19.teamL.Reports;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import edu.wpi.cs3733.d19.teamL.DBAccess;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.ServiceRequestDBAccess.ReligiousRequestAccess;
import javafx.scene.control.TreeItem;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

public class requestReportAccess extends DBAccess {
    /**
     * ANDREW MADE THIS
     * deletes all the records from the table
     */
    public void deleteRecords() {
        String sql = "Delete from requestReport;";

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
    public void addReport(String time, String assignedTo, String completedTime, String type, String specificType, String location) {
        String sql = "insert into requestReport(" +
                "timeOfRequest, assignedToEID, completedTime, type, specificType, location)" +
                "values (?, ?, ?, ?, ?, ?)";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, time);
            pstmt.setString(2, assignedTo);
            pstmt.setString(3, completedTime);
            pstmt.setString(4, type);
            pstmt.setString(5, specificType);
            pstmt.setString(6, location);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void assignReport(String time, String assignedTo) {
        updateField(time, "assignedToEID", assignedTo);
    }

    public void updateField(String requestID, String field, String  data) {
        String sql = "update requestReport set " + field + "= ? where timeOfRequest= ?;";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, data);
            pstmt.setString(2, requestID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void assignReportAndFufill(String time, String assignedTo, String completedTime) {
        updateField(time, "completedTime", completedTime);
        updateField(time, "assignedToEID", assignedTo);
    }


    public static void main(String[] args) {
        requestReportAccess rra = new requestReportAccess();
    }

}
