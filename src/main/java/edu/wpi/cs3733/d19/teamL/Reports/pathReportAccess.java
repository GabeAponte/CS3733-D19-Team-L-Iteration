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

}

