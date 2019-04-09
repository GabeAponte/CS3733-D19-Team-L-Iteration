package Object;


public class ServiceRequestTable {
    String requestID;
    String assignedEmployee;
    String fulfilled;
    String comment;
    String creationTime;
    String completionTime;
    String creationDate;
    String completionDate;
    String location;
    String type;
    String startLocation;
    String endLocation;
    String phoneNumber;
    String destination;
    String receiverName;
    String flowerName;
    String device;
    String problem;
    String language;
    String level;
    int interpreters;
    Boolean isHazard;
    String medicineType;
    int deliveryTime;
    int ammount;
    int urgenecyLevel;
    String personDesc;
    String denomination;
    String name;

    //sanitation
    /*public ServiceRequestTable(String requestID, String assignedEmployee, Boolean fulfilled, String comment, int creationTime, int completionTime, String creationDate, String completionDate, String location, String type, int urgenecyLevel) {
        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = creationTime;
        this.completionTime = completionTime;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.type = type;
        this.urgenecyLevel = urgenecyLevel; */

    //religious
    public ServiceRequestTable(String requestID, String assignedEmployee, String comment, String fulfilled, String creationTime, String completionTime, String creationDate, String completionDate, String location, String type, String denomination, String name) {
        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = creationTime;
        this.completionTime = completionTime;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.type = type;
        this.denomination = denomination;
        this.name = name;
    }


    /*
    //prescription
    public ServiceRequestTable(String requestID, String assignedEmployee, Boolean fulfilled, String comment, int creationTime, int completionTime, String creationDate, String completionDate, String location, String destination, String medicineType, int deliveryTime, int ammount) {
        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = creationTime;
        this.completionTime = completionTime;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.destination = destination;
        this.medicineType = medicineType;
        this.deliveryTime = deliveryTime;
        this.ammount = ammount;
    }
    //maintenanceRequest
    public ServiceRequestTable(String requestID, String assignedEmployee, Boolean fulfilled, String comment, int creationTime, int completionTime, String creationDate, String completionDate, String location, String type, Boolean isHazard) {
        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = creationTime;
        this.completionTime = completionTime;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.type = type;
        this.isHazard = isHazard;
    }
    //Language
    public ServiceRequestTable(String requestID, String assignedEmployee, Boolean fulfilled, String comment, int creationTime, int completionTime, String creationDate, String completionDate, String location, String language, String level, int interpreters) {
        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = creationTime;
        this.completionTime = completionTime;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.language = language;
        this.level = level;
        this.interpreters = interpreters;
    }
    //IT
        public ServiceRequestTable(String requestID, String assignedEmployee, Boolean fulfilled, String comment, int creationTime, int completionTime, String creationDate, String completionDate, String location, String device, String problem) {
        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = creationTime;
        this.completionTime = completionTime;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.device = device;
        this.problem = problem;
    }
    //InternalTrans
    public ServiceRequestTable(String requestID, Boolean fulfilled, String assignedEmployee, String comment, int creationTime, int completionTime, String creationDate, String completionDate, String type, String startLocation, String endLocation, String phoneNumber) {
        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = creationTime;
        this.completionTime = completionTime;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.type = type;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.phoneNumber = phoneNumber;
    }
    //florist
    public ServiceRequestTable(String requestID, String assignedEmployee, String comment, int creationTime, int completionTime, Boolean fulfilled, String creationDate, String completionDate, String location, String receiverName, String flowerName) {
        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = creationTime;
        this.completionTime = completionTime;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.receiverName = receiverName;
        this.flowerName = flowerName;
    }

    //externalTrans
    public ServiceRequestTable(String requestID, String assignedEmployee, Boolean fulfilled, String comment, int creationTime, int completionTime, String creationDate, String completionDate, String location, String type, String phoneNumber, String destination) {
        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = creationTime;
        this.completionTime = completionTime;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.destination = destination;
    }

   //audio visual
   public ServiceRequestTable(String requestID, String assignedEmployee, String comment, int creationTime, int completionTime, String creationDate, String completionDate, Boolean fulfilled, String location, String type, String destination) {

        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = creationTime;
        this.completionTime = completionTime;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.type = type;
        this.destination = destination;
    }

    //securityRequest
    public ServiceRequestTable(String requestID, String assignedEmployee, String comment, int creationTime, int completionTime, String creationDate, String completionDate, String location, Boolean fulfilled, String type, String personDesc) {
        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = creationTime;
        this.completionTime = completionTime;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.type = type;
        this.personDesc = personDesc;
    } */

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(String assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    public String getFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(String fulfilled) {
        this.fulfilled = fulfilled;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(String completionTime) {
        this.completionTime = completionTime;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(String completionDate) {
        this.completionDate = completionDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getInterpreters() {
        return interpreters;
    }

    public void setInterpreters(int interpreters) {
        this.interpreters = interpreters;
    }

    public Boolean getHazard() {
        return isHazard;
    }

    public void setHazard(Boolean hazard) {
        isHazard = hazard;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    public int getUrgenecyLevel() {
        return urgenecyLevel;
    }

    public void setUrgenecyLevel(int urgenecyLevel) {
        this.urgenecyLevel = urgenecyLevel;
    }

    public String getPersonDesc() {
        return personDesc;
    }

    public void setPersonDesc(String personDesc) {
        this.personDesc = personDesc;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
