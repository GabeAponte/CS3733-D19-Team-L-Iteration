package edu.wpi.cs3733.d19.teamL.ServiceRequest.ServiceRequestDBAccess;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import edu.wpi.cs3733.d19.teamL.DBAccess;
import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;
import javafx.scene.control.TreeItem;
import edu.wpi.cs3733.d19.teamL.ServiceRequest.FulfillServiceRequest.ServiceRequestTable;

public class ServiceRequestAccess extends DBAccess {

    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private static final DateFormat tdf = new SimpleDateFormat("HHmm");
    private SanitationAccess sa = new SanitationAccess();
    private InternalTransportAccess ita = new InternalTransportAccess();
    private ReligiousRequestAccess rra = new ReligiousRequestAccess();
    private AudioVisualAccess ava = new AudioVisualAccess();
    private ExternalTransportAccess eta = new ExternalTransportAccess();
    private FloristDeliveryAccess fda = new FloristDeliveryAccess();
    private ITAccess ia = new ITAccess();
    private LanguageAccess la = new LanguageAccess();
    private MaintenanceAccess ma = new MaintenanceAccess();
    private PrescriptionAccess pa = new PrescriptionAccess();
    private SecurityAccess sca = new SecurityAccess();

    public void deleteSanitationRecords() {
        sa.deleteRecords();
    }

    public void makeSanitationRequest(String desc, String location, String type, String urgency, String reportTime) {
        sa.makeRequest(desc, location, type, urgency, reportTime);
    }

    public TreeItem<ServiceRequestTable> getSanitationRequests(int getNum) {
        return sa.getSanitationRequests(getNum);
    }

    public int countSanitationRecords() {
        return sa.countRecords();
    }

    public void deleteInternalRecords() {
        ita.deleteRecords();
    }

    public void makeInternalRequest(String desc, Location startLocation, Location endLocation, String type, String phoneNumber, String reportTime) {
        ita.makeRequest(desc, startLocation, endLocation, type, phoneNumber, reportTime);
    }

    public TreeItem<ServiceRequestTable> getInternalRequests(int getNum) {
        return ita.getRequests(getNum);
    }

    public int countInternalRecords() {
        return ita.countRecords();
    }

    public void deleteReligiousRecords() {
        rra.deleteRecords();
    }

    public void makeReligiousRequest(String desc, String denom, String location, String name, String type, String reportTime) {
        rra.makeRequest(desc, denom, location, name, type, reportTime);
    }

    public TreeItem<ServiceRequestTable> getReligiousRequests(int getNum) {
        return rra.getReligiousRequests(getNum);
    }

    public int countReligiousRecords() {
        return rra.countRecords();
    }

    public void deleteAudioRecords() {
        ava.deleteRecords();
    }

    public void makeAudioRequest(String desc, String destination, String location, String type, String reportTime) {
        ava.makeRequest(desc, destination, location, type, reportTime);
    }

    public TreeItem<ServiceRequestTable> getAudioVisualRequests(int getNum) {
        return ava.getAudioVisualRequests(getNum);
    }

    public int countAudioRecords() {
        return ava.countRecords();
    }

    public void deleteExternalRecords() {
        eta.deleteRecords();
    }

    public void makeExternalRequest(String desc, String location, String destination, String type, String phone, String reportTime) {
        eta.makeRequest(desc, location, destination, type, phone, reportTime);
    }

    public TreeItem<ServiceRequestTable> getExternalRequests(int getNum) {
        return eta.getExternalRequests(getNum);
    }

    public int countExternalRecords() {
        return eta.countRecords();
    }

    public void deleteFloristRecords() {
        fda.deleteRecords();
    }

    public void makeFloristRequest(String desc, String recieverName, String location, String flowerName, String reportTime) {
        fda.makeRequest(desc, recieverName, location, flowerName, reportTime);
    }

    public TreeItem<ServiceRequestTable> getFloristRequests(int getNum) {
        return fda.getFloristRequests(getNum);
    }

    public int countFloristRecords() {
        return fda.countRecords();
    }

    public void deleteITRecords() {
        ia.deleteRecords();
    }

    public void makeITRequest(String desc, String location, String device, String problem, String reportTime) {
        ia.makeRequest(desc, location, device, problem, reportTime);
    }

    public TreeItem<ServiceRequestTable> getITRequests(int getNum) {
        return ia.getITRequests(getNum);
    }

    public int countITRecords() {
        return ia.countRecords();
    }

    public void deleteLanguageRecords() {
        la.deleteRecords();
    }

    public void makeLanguageRequest(String desc, String location, String language, String level, String interpreters, String reportTime) {
        la.makeRequest(desc, location, language, level, interpreters, reportTime);
    }

    public TreeItem<ServiceRequestTable> getLanguageRequests(int getNum) {
        return la.getLanguageRequests(getNum);
    }

    public int countLanguageRecords() {
        return la.countRecords();
    }

    public void deleteMaintenanceRecords() {
        ma.deleteRecords();
    }

    public void makeMaintenanceRequest(String desc, String location, String type, String isHazard, String reportTime) {
        ma.makeRequest(desc, location, type, isHazard, reportTime);
    }

    public TreeItem<ServiceRequestTable> getMaintenanceRequests(int getNum) {
        return ma.getMaintenanceRequests(getNum);
    }

    public int countMaintenanceRecords() {
        return ma.countRecords();
    }

    public void deletePrescriptionRecords() {
        pa.deleteRecords();
    }

    public void makePrescriptionRequest(String desc, String location, String medicineType, String destination, String deliveryTime, String amount, String reportTime) {
        pa.makeRequest(desc, location, medicineType, destination, deliveryTime, amount, reportTime);
    }

    public TreeItem<ServiceRequestTable> getPrescriptionRequests(int getNum) {
        return pa.getPrescriptionRequests(getNum);
    }

    public int countPrescriptionRecords() {
        return pa.countRecords();
    }

    public void deleteSecurityRecords() {
        sca.deleteRecords();
    }

    public void makeSecurityRequest(String desc, String location, String name, String type, String threatLevel, String reportTime) {
        sca.makeRequest(desc, location, name, type, threatLevel, reportTime);
    }

    public TreeItem<ServiceRequestTable> getSecurityRequests(int getNum) {
        return sca.getSecurityRequests(getNum);
    }

    public int countSecurityRecords() {
        return sca.countRecords();
    }

    /**ANDREW MADE THIS
     * assign an employee to fulfill a request
     */
    public void assignEmployee(int rid, String employeeID, String table){
        String sql = "update " + table + " set assignedEmployee = ? where requestID = ?";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employeeID);
            pstmt.setInt(2, rid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getReportTime(String table, int rid) {
        String sql = "SELECT * FROM " + table +  " where  requestID= ?";
        //noinspection Convert2Diamond
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, rid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return getFields(rs);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private String getFields(ResultSet rs) throws SQLException {
        return rs.getString("reportTime");
    }

    /**ANDREW MADE THIS
     * fulfill a request
     */
    public void fulfillRequest(int rid, String table){
        String sql = "update " + table + " set completionTime = ?, completionDate = ?, fulfilled = true where requestID = ?";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Date date = new Date();
            pstmt.setInt(1, Integer.parseInt(tdf.format(date.getTime())));
            pstmt.setString(2, sdf.format(date));
            pstmt.setInt(3, rid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }





    public static void main(String[] args) {
        ServiceRequestAccess sra = new ServiceRequestAccess();
        //sra.assignEmployee(1, "wong", "sanitationRequest");
        //sra.fulfillRequest(1, "sanitationRequest");
    }
}
