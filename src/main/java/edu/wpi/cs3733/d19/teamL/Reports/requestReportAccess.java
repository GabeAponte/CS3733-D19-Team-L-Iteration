package edu.wpi.cs3733.d19.teamL.Reports;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import edu.wpi.cs3733.d19.teamL.DBAccess;
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
    public void addReport(String time, String assignedTo, String assignedBy, String completedTime, String type) {
        String sql = "insert into requestReport(" +
                "timeOfRequest, assignedToEID, assignedByEID, completedTime, type)" +
                "values (?, ?, ?, ?, ?)";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, time);
            pstmt.setString(2, assignedTo);
            pstmt.setString(3, assignedBy);
            pstmt.setString(4, completedTime);
            pstmt.setString(5, type);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
