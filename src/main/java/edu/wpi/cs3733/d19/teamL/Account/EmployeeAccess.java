package edu.wpi.cs3733.d19.teamL.Account;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import edu.wpi.cs3733.d19.teamL.DBAccess;
import javafx.scene.control.TreeItem;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

public class EmployeeAccess extends DBAccess {
    /**ANDREW MADE THIS
     * deletes all the records from the employee table
     */
    public void deleteRecords() {
        String sql = "Delete from employee;";

        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**ANDREW MADE THIS
     * checks if an employee entered correct login information
     * @param username
     * @param password
     * @return
     */
    public boolean checkEmployee(String username, String password){
        String sql = "select password from employee where username = ?";
        String check = "";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                if(rs.getString("password").equals(password)){
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**ANDREW MADE THIS
     * checks if an employee username is already taken
     * @param username
     * @return
     */
    public boolean checkFields(String employeeID, String username){
        String sql = "select employeeID, username from employee where username = ? or employeeID = ?";
        String check = "";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, employeeID);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                if(rs.getString("username").equals(username)){
                    return true;
                }
                if(rs.getString("employeeID").equals(employeeID)){
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**ANDREW MADE THIS
     *  returns the fields of a particular employee in an arraylist
     * @param username
     * @return
     */
    public ArrayList<String> getEmployeeInformation(String username){
        String sql = "SELECT * FROM employee where username = ?";
        //noinspection Convert2Diamond
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                data.add(rs.getString("employeeID"));
                data.add(rs.getString("department"));
                data.add(Boolean.toString(rs.getBoolean("isAdmin")));
                data.add(rs.getString("nickname"));
                data.add(rs.getString("password"));
                data.add(rs.getString("type"));
                data.add(rs.getString("firstName"));
                data.add(rs.getString("lastName"));
                data.add(rs.getString("email"));
            }
            return data;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**ANDREW MADE THIS
     *  returns the fields of a particular employee in an arraylist
     * @param username
     * @return
     */
    public String getEmpEmail(String username){
        String sql = "SELECT email FROM employee where username = ?";
        //noinspection Convert2Diamond
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getString("email");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return "";
    }

    /**ANDREW MADE THIS
     *  returns the fields of a particular employee in an arraylist
     * @param employeeID
     * @return
     */
    public String getEmployeeUsername(String employeeID){
        String sql = "SELECT username FROM employee where employeeID = ?";
        //noinspection Convert2Diamond
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, employeeID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getString("username");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /** ANDREW MADE THIS
     * Queries the database for all fields of the employee table and returns an arraylist of arraylist string on a certain condition
     */
    public ArrayList<ArrayList<String>> getEmployees(String field, String value) {
        String sql = "";
        int check = 0;
        if(field.equals("") && value.equals("")){
            sql = "SELECT * FROM employee where employeeID = employeeID";
        }else{
            check = 1;
            sql = "SELECT * FROM employee where " + field + " = ?";
        }
        //noinspection Convert2Diamond
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            if(check == 1) {
                pstmt.setString(1, value);
            }
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ArrayList<String> data = new ArrayList<String>();
                data.add(rs.getString("employeeID"));
                data.add(rs.getString("department"));
                if(rs.getBoolean("isAdmin")){
                    data.add("true");
                } else {
                    data.add("false");
                }
                data.add(rs.getString("type"));
                data.add(rs.getString("firstName"));
                data.add(rs.getString("lastName"));
                data.add(rs.getString("nickname"));
                data.add(rs.getString("email"));
                data.add(rs.getString("username"));
                list.add(data);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /** ANDREW MADE THIS
     * Call this to update the database with the required paramaters
     *
     * @param employeeID, the ID of the node you want to edit
     * @param field, the column of the table you want to edit
     * @param data, the data you want to put in
     */
    public void updateEmployee(String employeeID, String field, String data) throws SQLException{
        String sql = "update employee set " + field + "= ? where employeeID= ?;";

        Connection conn = this.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, data);
        pstmt.setString(2, employeeID);
        pstmt.executeUpdate();

    }

    /** ANDREW MADE THIS
     * Call this to change an employee's admin priveleges
     *
     * @param employeeID, the ID of the node you want to edit
     * @param check, true if you want to make them an admin, false if you want to remove privelege
     */
    public void changeAdmin(String employeeID, boolean check) {
        String sql = "update employee set isAdmin = ? where employeeID= ?;";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if(check) {
                pstmt.setInt(1, 1);
            } else {
                pstmt.setInt(1, 0);
            }
            pstmt.setString(2, employeeID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**ANDREW MADE THIS
     * adds an employee to the database with required parameters
     * @param data
     */
    public void addEmployee(ArrayList<String> data){
        String sql = "insert into employee(" +
                "employeeID, username, password, department, isAdmin, type, firstName, lastName, nickname, email)" +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, data.get(0));
            pstmt.setString(2, data.get(1));
            pstmt.setString(3, data.get(2));
            pstmt.setString(4, data.get(3));
            pstmt.setBoolean(5, false);
            pstmt.setString(6, data.get(4));
            pstmt.setString(7, data.get(5));
            pstmt.setString(8, data.get(6));
            pstmt.setString(9, data.get(7));
            pstmt.setString(10, data.get(8));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**ANDREW MADE THIS
     * deletes the employee with the given employeeID
     */
    public void deleteEmployee(String employeeID) {
        String sql = "Delete from employee where employeeID = ?;";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, employeeID);
            pstmt.executeUpdate();
            System.out.println(employeeID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**NATHAN MADE THIS
     * Counts number of employee records in database
     * @return
     */
    public int countRecords() {
        String sql = "select COUNT(*) from employee where username is not null";
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;

    }

    /**NATHAN MADE THIS
     * gets record getNum
     * @param getNum
     * @return
     */
    public TreeItem<EmployeeTable> getRequests(int getNum){
        TreeItem<EmployeeTable> nodeRoot = null;
        String sql = "SELECT * FROM employee where username is not NULL";
        int count = 0;
        //noinspection Convert2Diamond
        ArrayList<String> data = new ArrayList<String>();
        try (Connection conn = this.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                if (count == getNum) {
                    data.add(rs.getString("employeeID"));
                    data.add(rs.getString("department"));
                    data.add(rs.getString("type"));
                    data.add(rs.getString("firstName"));
                    data.add(rs.getString("lastName"));
                    nodeRoot = new TreeItem<>(new EmployeeTable(data.get(0), data.get(1), data.get(2), data.get(3), data.get(4)));
                }
                count++;
            }
            return nodeRoot;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**@author Nathan
     * Updates and employee's image file
     *
     * @param data
     * @throws SQLException
     */
    public void updateEmployeeImg(String username, BufferedImage data) throws SQLException{
        String sql = "update employee set image = ? where username = ?;";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(data, "jpg", baos);
        } catch (Exception e){
            e.printStackTrace();
        }
        //Blob blFile = new SerialBlob(baos.toByteArray());
        Connection conn = this.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setBytes(1, baos.toByteArray());
        pstmt.setString(2, username);
        pstmt.executeUpdate();
    }

    /**@author Nathan
     * retrieves the image version of the blob stored in the given employee's image field
     * @param username
     * @return
     */
    public BufferedImage getEmpImg(String username){
        String sql = "select username, image from employee where username = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next() && rs != null){
                if(rs.getString("username").equals(username)){
                    if(rs.getBinaryStream("image") == null){
                        return null;
                    }
                    InputStream in = rs.getBinaryStream("image");
                    BufferedImage image = ImageIO.read(in);
                    return image;
                }
            }
            System.out.println("exit loop");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**@author Nathan
     * Gets all images from the database
     * @return arrayList of images from database
     */
    public ArrayList<BufferedImage> getEmpImgs(){
        String sql = "select image from employee";
        ArrayList<BufferedImage> bi = new ArrayList<BufferedImage>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while(rs.next() && rs != null){
                    if(rs.getBinaryStream("image") == null){
                        continue;
                    }
                    InputStream in = rs.getBinaryStream("image");
                    BufferedImage image = ImageIO.read(in);
                    bi.add(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bi;
    }

    /**@author Nathan
     * Gets all images from the database
     * @return arrayList of images from database
     */
    public ArrayList<String> getEmpsWithImg(){
        String sql = "select username, image from employee";
        ArrayList<String> bi = new ArrayList<String>();
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while(rs.next() && rs != null){
                if(rs.getBinaryStream("image") == null){
                    continue;
                }
                bi.add(rs.getString("username"));
            }
            System.out.println("exit loop");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bi;
    }

    /**Andrew made this for testing
     *
     * @param args
     */
    public static void main(String[] args) {
        EmployeeAccess ea = new EmployeeAccess();
        System.out.println(ea.getEmployees("","").get(0));
        System.out.println(ea.getEmployees("type","creator").get(0));
        System.out.println(ea.getEmployeeInformation("Andrew").get(2));
    }

}
