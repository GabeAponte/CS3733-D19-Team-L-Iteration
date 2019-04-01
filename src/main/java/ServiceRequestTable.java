public class ServiceRequestTable{
    String requestID;
    String requestDepartment;
    String assignedEmployee;
    String fulfullied;
    String comment;


    ServiceRequestTable(String requestID, String comment, String requestDepartment, String assignedEmployee, String fulfullied) {
        this.requestID = requestID;
        this.requestDepartment = requestDepartment;
        this.assignedEmployee = assignedEmployee;
        this.fulfullied = fulfullied;
        this.comment = comment;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getRequestDepartment() {
        return requestDepartment;
    }

    public void setRequestDepartment(String requestDepartment) {
        this.requestDepartment = requestDepartment;
    }

    public String getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(String assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    public String getFulfullied() {
        return fulfullied;
    }

    public void setFulfullied(String fulfullied) {
        this.fulfullied = fulfullied;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}