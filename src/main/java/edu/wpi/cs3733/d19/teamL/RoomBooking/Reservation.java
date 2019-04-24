package edu.wpi.cs3733.d19.teamL.RoomBooking;

import edu.wpi.cs3733.d19.teamL.Account.EmployeeAccess;
import edu.wpi.cs3733.d19.teamL.Account.employeeID;

public class Reservation {
    private String rID;
    private String eID;
    private String sDate;
    private String eDate;
    private String title;
    private String desc;
    private String guestList;
    private String type;
    private boolean isPrivate;

    public Reservation(String ID, String employeeID, String date, String endDate, String eventNameString, String eventDescriptionString, String listOfGuests, String eventTypeString, boolean eventIsPrivate){
        rID = ID;
        eID = employeeID;
        sDate = date;
        eDate = endDate;
        title = eventNameString;
        desc = eventDescriptionString;
        guestList = listOfGuests;
        type = eventTypeString;
        isPrivate = eventIsPrivate;
    }

    public String getrID() {
        return rID;
    }

    public void setrID(String rID) {
        this.rID = rID;
    }

    public String geteID() {
        return eID;
    }

    public void seteID(String eID) {
        this.eID = eID;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String geteDate() {
        return eDate;
    }

    public void seteDate(String eDate) {
        this.eDate = eDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getGuestList() {
        return guestList;
    }

    public void setGuestList(String guestList) {
        this.guestList = guestList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String toString(){
        RoomAccess ra = new RoomAccess();
        EmployeeAccess ea = new EmployeeAccess();
        String result = "";
        result += "Room ID: " + ra.getRoomName(rID);
        result += "\nCreator: " + ea.getEmployeeUsername(eID);
        result += "\nStart Date: " + sDate;
        result += "\nEnd Date: " + eDate;
        result += "\nEvent Title: " + title;
        result += "\nDescription: " + desc;
        result += "\nType of Event: " + type;
        result += "\nPrivate Event: " + isPrivate;
        return result;
    }
}
