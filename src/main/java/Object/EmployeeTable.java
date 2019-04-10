package Object;

public class EmployeeTable {
    String ID;
    String department;
    String type;
    String firstName;
    String lastName;

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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

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
}


