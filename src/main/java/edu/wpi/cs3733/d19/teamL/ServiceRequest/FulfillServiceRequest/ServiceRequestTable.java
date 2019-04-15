package edu.wpi.cs3733.d19.teamL.ServiceRequest.FulfillServiceRequest;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServiceRequestTable  {
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
    String interpreters;
    String isHazard;
    String medicineType;
    String deliveryTime;
    String ammount;
    String urgenecyLevel;
    String personDesc;
    String denomination;
    String name;
    String threatLevel;


    //sanitation
    public ServiceRequestTable(String requestID, String assignedEmployee, String fulfilled, String location, int k, String creationTime, String completionTime, String comment, String type, String urgenecyLevel, String creationDate, String completionDate) {
        int milTime = Integer.parseInt(creationTime);
        int endMilTime = Integer.parseInt(completionTime);

        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", milTime));
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat startSimpleDate = new SimpleDateFormat("hh:mm a");
       // System.out.println(startSimpleDate.format(startDate));

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", endMilTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat endSimpleDate = new SimpleDateFormat("hh:mm a");
      //  System.out.println(startSimpleDate.format(endDate));

        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = startSimpleDate.format(startDate);
        this.completionTime = endSimpleDate.format(endDate);
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.type = type;
        this.urgenecyLevel = urgenecyLevel;
    }

    //religious
    public ServiceRequestTable(String requestID, String assignedEmployee, String fulfilled, String location, String creationTime, String completionTime, String comment, String denomination, String type,  String name, String creationDate, String completionDate) {
        int milTime = Integer.parseInt(creationTime);
        int endMilTime = Integer.parseInt(completionTime);

        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", milTime));
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat startSimpleDate = new SimpleDateFormat("hh:mm a");
     //   System.out.println(startSimpleDate.format(startDate));

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", endMilTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat endSimpleDate = new SimpleDateFormat("hh:mm a");
    //    System.out.println(startSimpleDate.format(endDate));

        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = startSimpleDate.format(startDate);
        this.completionTime = endSimpleDate.format(endDate);
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.type = type;
        this.denomination = denomination;
        this.name = name;
    }



    //prescription
    public ServiceRequestTable(String requestID, String assignedEmployee, String fulfilled, String location, String creationTime, String completionTime, String comment, String medicineType, String destination, String deliveryTime, String ammount, String creationDate, String completionDate) {
        int milTime = Integer.parseInt(creationTime);
        int endMilTime = Integer.parseInt(completionTime);

        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", milTime));
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat startSimpleDate = new SimpleDateFormat("hh:mm a");
     //   System.out.println(startSimpleDate.format(startDate));

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", endMilTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat endSimpleDate = new SimpleDateFormat("hh:mm a");
     //   System.out.println(startSimpleDate.format(endDate));

        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = startSimpleDate.format(startDate);
        this.completionTime = endSimpleDate.format(endDate);
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.destination = destination;
        this.medicineType = medicineType;
        this.deliveryTime = deliveryTime;
        this.ammount = ammount;
    }
    //maintenanceRequest
    public ServiceRequestTable(String requestID, int k, String assignedEmployee, String fulfilled, String location, String creationTime, String completionTime, String comment, String type, String isHazard, String creationDate, String completionDate) {
        int milTime = Integer.parseInt(creationTime);
        int endMilTime = Integer.parseInt(completionTime);

        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", milTime));
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat startSimpleDate = new SimpleDateFormat("hh:mm a");
    //    System.out.println(startSimpleDate.format(startDate));

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", endMilTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat endSimpleDate = new SimpleDateFormat("hh:mm a");
     //   System.out.println(startSimpleDate.format(endDate));

        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = startSimpleDate.format(startDate);
        this.completionTime = endSimpleDate.format(endDate);
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.type = type;
        this.isHazard = isHazard;
    }
    //Language
    public ServiceRequestTable(String requestID, String assignedEmployee, String fulfilled, int k, String location, String creationTime, String completionTime, String comment, String language, String level, String interpreters, String creationDate, String completionDate) {
        int milTime = Integer.parseInt(creationTime);
        int endMilTime = Integer.parseInt(completionTime);

        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", milTime));
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat startSimpleDate = new SimpleDateFormat("hh:mm a");
     //   System.out.println(startSimpleDate.format(startDate));

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", endMilTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat endSimpleDate = new SimpleDateFormat("hh:mm a");
      //  System.out.println(startSimpleDate.format(endDate));

        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = startSimpleDate.format(startDate);
        this.completionTime = endSimpleDate.format(endDate);
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.language = language;
        this.level = level;
        this.interpreters = interpreters;
    }

    //IT
    public ServiceRequestTable(int k, String requestID, String assignedEmployee, String fulfilled, String location, String creationTime, String completionTime, String comment, String device, String problem, String creationDate, String completionDate) {
        int milTime = Integer.parseInt(creationTime);
        int endMilTime = Integer.parseInt(completionTime);

        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", milTime));
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat startSimpleDate = new SimpleDateFormat("hh:mm a");
     //   System.out.println(startSimpleDate.format(startDate));

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", endMilTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat endSimpleDate = new SimpleDateFormat("hh:mm a");
    //    System.out.println(startSimpleDate.format(endDate));

        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = startSimpleDate.format(startDate);
        this.completionTime = endSimpleDate.format(endDate);
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.device = device;
        this.problem = problem;
    }

    //internal transportation
    public ServiceRequestTable(int k, String requestID, String assignedEmployee, String fulfilled, String creationTime, String completionTime,String comment, String startLocation, String endLocation, String type, String phoneNumber, String creationDate, String completionDate) {
        int milTime = Integer.parseInt(creationTime);
        int endMilTime = Integer.parseInt(completionTime);

        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", milTime));
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat startSimpleDate = new SimpleDateFormat("hh:mm a");
    //    System.out.println(startSimpleDate.format(startDate));

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", endMilTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat endSimpleDate = new SimpleDateFormat("hh:mm a");
     //   System.out.println(startSimpleDate.format(endDate));

        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = startSimpleDate.format(startDate);
        this.completionTime = endSimpleDate.format(endDate);
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.type = type;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.phoneNumber = phoneNumber;
    }

    //florist
    public ServiceRequestTable(String requestID, String assignedEmployee, int k, String fulfilled, String creationTime, String completionTime, String comment, String receiverName, String flowerName, String location, String creationDate, String completionDate) {
        int milTime = Integer.parseInt(creationTime);
        int endMilTime = Integer.parseInt(completionTime);

        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", milTime));
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat startSimpleDate = new SimpleDateFormat("hh:mm a");
      //  System.out.println(startSimpleDate.format(startDate));

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", endMilTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat endSimpleDate = new SimpleDateFormat("hh:mm a");
    //    System.out.println(startSimpleDate.format(endDate));

        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = startSimpleDate.format(startDate);
        this.completionTime = endSimpleDate.format(endDate);
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.receiverName = receiverName;
        this.flowerName = flowerName;
    }

    //externalTrans
    public ServiceRequestTable(String requestID, int k, String assignedEmployee, String fulfilled, String location, String creationTime, String completionTime, String comment, String type, String destination, String phoneNumber, String creationDate, String completionDate) {
        int milTime = Integer.parseInt(creationTime);
        int endMilTime = Integer.parseInt(completionTime);

        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", milTime));
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat startSimpleDate = new SimpleDateFormat("hh:mm a");
     //   System.out.println(startSimpleDate.format(startDate));

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", endMilTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat endSimpleDate = new SimpleDateFormat("hh:mm a");
    //    System.out.println(startSimpleDate.format(endDate));

        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = startSimpleDate.format(startDate);
        this.completionTime = endSimpleDate.format(endDate);
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.destination = destination;
    }

   //audio visual
   public ServiceRequestTable(String requestID, String assignedEmployee, String fulfilled, String location, String creationTime, String completionTime, String comment, String destination, String type, String creationDate, String completionDate) {
       int milTime = Integer.parseInt(creationTime);
       int endMilTime = Integer.parseInt(completionTime);

       Date startDate = null;
       try {
           startDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", milTime));
       } catch (
               ParseException e) {
           e.printStackTrace();
       }
       SimpleDateFormat startSimpleDate = new SimpleDateFormat("hh:mm a");
    //   System.out.println(startSimpleDate.format(startDate));

       Date endDate = null;
       try {
           endDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", endMilTime));
       } catch (ParseException e) {
           e.printStackTrace();
       }
       SimpleDateFormat endSimpleDate = new SimpleDateFormat("hh:mm a");
  //     System.out.println(startSimpleDate.format(endDate));

        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = startSimpleDate.format(startDate);
        this.completionTime = endSimpleDate.format(endDate);
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.type = type;
        this.destination = destination;
    }

    //securityRequest
    public ServiceRequestTable(String requestID, String assignedEmployee, String fulfilled, String location, int k,String creationTime, String completionTime, String comment, String name, String type, String threatLevel, String creationDate, String completionDate) {
        int milTime = Integer.parseInt(creationTime);
        int endMilTime = Integer.parseInt(completionTime);

        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", milTime));
        } catch (
                ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat startSimpleDate = new SimpleDateFormat("hh:mm a");
    //    System.out.println(startSimpleDate.format(startDate));

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", endMilTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat endSimpleDate = new SimpleDateFormat("hh:mm a");
     //   System.out.println(startSimpleDate.format(endDate));

        this.requestID = requestID;
        this.assignedEmployee = assignedEmployee;
        this.fulfilled = fulfilled;
        this.comment = comment;
        this.creationTime = startSimpleDate.format(startDate);
        this.completionTime = endSimpleDate.format(endDate);
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.location = location;
        this.type = type;
        this.name = name;
        this.threatLevel = threatLevel;
    }


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

    public String getInterpreters() {
        return interpreters;
    }

    public void setInterpreters(String interpreters) {
        this.interpreters = interpreters;
    }

    public String getIsHazard() {
        return isHazard;
    }

    public void setIsHazard(String isHazard) {
        this.isHazard = isHazard;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

    public String getUrgenecyLevel() {
        return urgenecyLevel;
    }

    public void setUrgenecyLevel(String urgenecyLevel) {
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

    public String getThreatLevel() {
        return threatLevel;
    }

    public void setThreatLevel(String threatLevel) {
        this.threatLevel = threatLevel;
    }

    @Override
    public String toString(){
        String result = "";
        if(requestID != null){
            result += "Request ID: " + requestID + "\n";
        }
        if(assignedEmployee != null){
            result += "Assigned Employee: " + assignedEmployee + "\n";
        }
        if(fulfilled != null){
            result += "Fulfilled: " + fulfilled + "\n";
        }
        if(comment != null){
            result += "Comment: " + comment + "\n";
        }
        if(creationTime != null){
            result += "Creation Time: " + creationTime + "\n";
        }
        if(completionTime != null){
            result += "Completion Time: " + completionTime + "\n";
        }
        if(creationDate != null){
            result += "Creation Date: " + creationDate + "\n";
        }
        if(location != null){
            result += "Location: " + location + "\n";
        }
        if(type != null){
            result += "Type: " + type + "\n";
        }
        if(startLocation != null){
            result += "Start Location: " + startLocation + "\n";
        }
        if(phoneNumber != null){
            result += "Phone Number: " + phoneNumber + "\n";
        }
        if(destination != null){
            result += "Destination: " + destination + "\n";
        }
        if(receiverName != null){
            result += "Recipient: " + receiverName + "\n";
        }
        if(flowerName != null){
            result += "Flower Name: " + flowerName + "\n";
        }
        if(device != null){
            result += "Device: " + device + "\n";
        }
        if(problem != null){
            result += "Problem: " + problem + "\n";
        }
        if(language != null){
            result += "Language: " + language + "\n";
        }
        if(level != null){
            result += "Competency: " + level + "\n";
        }
        if(interpreters != null){
            result += "Interpreters: " + interpreters + "\n";
        }
        if(isHazard != null){
            result += "Is a Hazard: " + isHazard + "\n";
        }
        if(medicineType != null){
            result += "Type of Medicine: " + medicineType + "\n";
        }
        if(deliveryTime != null){
            result += "Delivery Time: " + deliveryTime + "\n";
        }
        if(ammount != null){
            result += "Amount of Interpreters: " + ammount + "\n";
        }
        if(urgenecyLevel != null){
            result += "Urgency Level: " + urgenecyLevel + "\n";
        }
        if(personDesc != null){
            result += "Desciption of Suspect: " + personDesc + "\n";
        }
        if(denomination != null){
            result += "Denomination: " + denomination + "\n";
        }
        if(threatLevel != null){
            result += "Threat Level: " + threatLevel + "\n";
        }

        return result;
    }
}
