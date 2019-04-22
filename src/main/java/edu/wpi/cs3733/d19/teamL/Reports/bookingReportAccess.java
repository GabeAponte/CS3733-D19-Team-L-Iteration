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

public class bookingReportAccess extends DBAccess {
    /**
     * ANDREW MADE THIS
     * deletes all the records from the table
     */
    public void deleteRecords() {
        String sql = "Delete from bookingReport;";

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
    public void addReport(String name, String startDate, String endDate, String eID, String type) {
        String sql = "insert into bookingReport(" +
                "roomName, startDate, eID, type, endDate)" +
                "values (?, ?, ?, ?, ?)";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, startDate);
            pstmt.setString(3, eID);
            pstmt.setString(4, type);
            pstmt.setString(5, endDate);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        bookingReportAccess ba = new bookingReportAccess();
    }

}
