package Access;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import Object.*;

public class ServiceRequestAccess extends DBAccess{

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

    /**ANDREW MADE THIS
     * deletes all the records from the serviceRequest table
     */
    public void deleteRecords() {
        String sql = "Delete from serviceRequest;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**Andrew Made this
     * Facade method for getting ReligiousRequests
     * @return
     */
    public void makeReligiousRequest(String desc, String denom, String location, String name, String type) {
        rra.makeRequest(desc, denom, location, name, type);
    }

    /**Andrew Made this
     * Facade method for deleting religiousRequests
     * @return
     */
    public void deleteReligiousRequests() {
        rra.deleteRecords();
    }

    /**Andrew Made this
     * Facade method for getting internalTransportRequests
     * @return
     */
    public void makeInternalTransportRequest(String desc, Location startLocation, Location endLocation, String type, String phoneNumber) {
        ita.makeRequest(desc, startLocation, endLocation, type, phoneNumber);
    }

    /**Andrew Made this
     * Facade method for deleting internalTransportRequests
     * @return
     */
    public void deleteInternalTransportRequests() {
        ita.deleteRecords();
    }

    /**Andrew Made this
     * Facade method for making sanitationRequest
     * @param location
     * @param comment
     * @param type
     * @param urgencyLevel
     */
    public void makeSanitationRequest(String location, String comment, String type, String urgencyLevel) {
        sa.makeRequest(location, comment, type, urgencyLevel);
    }

    /**Andrew Made this
     * Facade method for getting sanitationRequests
     * @return
     */
    public ArrayList<ArrayList<String>> getSanitationRequests() {
        return sa.getRequests();
    }

    /**Andrew Made this
     * Facade method for deleting sanitationRequests
     * @return
     */
    public void deleteSanitationRequests() {
        sa.deleteRecords();
    }

    /**Andrew Made this
     * Facade method for deleting AudioVisualRequests
     * @return
     */
    public void deleteAudioVisualRequests() {
        ava.deleteRecords();
    }

    /**Andrew Made this
     * Facade method for deleting FloristDeliverRequests
     * @return
     */
    public void deleteFloristRequests() {
        fda.deleteRecords();
    }

    /**Andrew Made this
     * Facade method for deleting externalTransportationRequests
     * @return
     */
    public void deleteEXternalTransportationRequests() {
        eta.deleteRecords();
    }

    /**Andrew Made this
     * Facade method for deleting ITRequests
     * @return
     */
    public void deleteITRequests() {
        ia.deleteRecords();
    }

    /**Andrew Made this
     * Facade method for deleting languageRequests
     * @return
     */
    public void deleteLanguageRequests() {
        la.deleteRecords();
    }

    /**Andrew Made this
     * Facade method for deleting maintenanceRequests
     * @return
     */
    public void deleteMaintenanceRequests() {
        ma.deleteRecords();
    }

    /**Andrew Made this
     * Facade method for deleting prescriptionRequests
     * @return
     */
    public void deletePrescriptionRequests() {
        pa.deleteRecords();
    }

    /**Andrew Made this
     * Facade method for deleting securityRequests
     * @return
     */
    public void deleteSecurityRequests() {
        sca.deleteRecords();
    }

    /**ANDREW MADE THIS
     * adds a new request to the database
     * @param desc
     * @param rDept
     */
    public void makeRequest(String desc, String rDept){
        String sql = "insert into serviceRequest(" +
                "comment, requestDepartment, assignedEmployee, fulfilled)" +
                "values (?, ?, NULL, 0)";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, desc);
            pstmt.setString(2, rDept);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    /**ANDREW MADE THIS
     * returns the record fields for the given index in serviceRequest
     * @param getNum
     * @return
     */
    public ArrayList<String> getRequests(int getNum){
        String sql = "SELECT * FROM serviceRequest where assignedEmployee is NULL";
        int count = 0;
        //noinspection Convert2Diamond
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                if (count == getNum) {
                    data.add(""+rs.getInt("requestID"));
                    if(rs.getString("comment") != null){
                        data.add(rs.getString("comment"));
                    }
                    else{
                        data.add("no comment");
                    }
                    data.add(rs.getString("requestDepartment"));
                    if(rs.getString("assignedEmployee") != null){
                        data.add(rs.getString("assignedEmployee"));
                        data.add("Fulfilled");
                    }
                    else{
                        data.add("none assigned");
                        data.add("not Fulfilled");
                    }
                }
                count++;
            }
            return data;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /** ANDREW MADE THIS
     * returns the number of records in serviceRequest
     *
     * @return int
     */
    public int countRecords() {
        String sql = "select COUNT(*) from serviceRequest where assignedEmployee is null";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;

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
