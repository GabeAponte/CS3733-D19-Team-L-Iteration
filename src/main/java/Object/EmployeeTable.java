package Object;


import Access.EmployeeAccess;

public class EmployeeTable {
    String ID;
    //String username;
    //String password;
    String department;
    //boolean isAdmin;
    String type;
    String firstName;
    String lastName;
    //String nickname;
    //String email;

    public EmployeeTable(String ID, String username, String password, String department, boolean isAdmin, String type, String firstName, String lastName, String nickname, String email) {
        this.ID = ID;
        //this.username = username;
        //this.password = password;
        this.department = department;
        //this.isAdmin = isAdmin;
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        //this.nickname = nickname;
        //this.email = email;
    }

    public EmployeeTable(String ID, String department, String type, String firstName, String lastName){
        this.ID = ID;
        this.department = department;
        this.firstName = firstName;
        this.type = type;
        this.lastName = lastName;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    /*public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }*/

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    /*public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }*/

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /*public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }*/
}


