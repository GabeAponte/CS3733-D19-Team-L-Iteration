package edu.wpi.cs3733.d19.teamL.Reports;

import org.bytedeco.opencv.presets.opencv_core;

public class serviceRequestReportData {

    private String timeOfRequest;
    private String assignedToEID;
    private String completedTime;
    private String type, specificType, location;

    public String getTimeOfRequest() {
        return timeOfRequest;
    }

    public void setTimeOfRequest(String timeOfRequest) {
        this.timeOfRequest = timeOfRequest;
    }

    public String getAssignedToEID() {
        return assignedToEID;
    }

    public void setAssignedToEID(String assignedToEID) {
        this.assignedToEID = assignedToEID;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpecificType() {
        return specificType;
    }

    public void setSpecificType(String specificType) {
        this.specificType = specificType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public serviceRequestReportData(String time, String eid, String completedTime, String type,
                                    String specificType, String location) {
        this.timeOfRequest = time;
        this.assignedToEID = eid;
        this.completedTime = completedTime;
        this.type = type;
        this.specificType = specificType;
        this.location = location;
    }



}
