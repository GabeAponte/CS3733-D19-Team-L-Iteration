package edu.wpi.cs3733.d19.teamL.RoomBooking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class WeeklyRoom{

    String time;
    boolean isMonday;
    boolean isTuesday;
    boolean isWednesday;
    boolean isThursday;
    boolean isFriday;
    boolean isSaturday;
    boolean isSunday;

    public WeeklyRoom(LocalTime startTime, LocalTime endTime, LocalDate theDate, String classroomName){
        RoomAccess ra = new RoomAccess();
        String startDate = "";
        String endDate = "";

        if(startTime.getHour() >= 12){
            startDate = startTime.toString() + " PM";
        }else {
            startDate = startTime.toString() + " AM";
        }
        if(endTime.getHour() >= 12){
            endDate = endTime.toString() + " PM";
        }else {
            endDate = endTime.toString() + " AM";
        }

        time = startDate + " - " + endDate;

        LocalDate weekDay = theDate;
        String dayOfWeek = theDate.getDayOfWeek().toString();
        String sDate = "";
        String eDate = "";
        switch(dayOfWeek){
            case "SUNDAY":
                sDate = weekDay.toString() + "T" + startTime.toString();
                eDate = weekDay.toString() + "T" + endTime.toString();
                isSunday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(1).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(1).toString() + "T" + endTime.toString();
                isMonday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(2).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(2).toString() + "T" + endTime.toString();
                isTuesday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(3).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(3).toString() + "T" + endTime.toString();
                isWednesday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(4).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(4).toString() + "T" + endTime.toString();
                isThursday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(5).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(5).toString() + "T" + endTime.toString();
                isFriday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(6).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(6).toString() + "T" + endTime.toString();
                isSaturday = ra.checkRoom(sDate, eDate,classroomName);
                break;
            case "MONDAY":
                sDate = weekDay.minusDays(1).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(1).toString() + "T" + endTime.toString();
                isSunday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.toString() + "T" + startTime.toString();
                eDate = weekDay.toString() + "T" + endTime.toString();
                isMonday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(1).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(1).toString() + "T" + endTime.toString();
                isTuesday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(2).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(2).toString() + "T" + endTime.toString();
                isWednesday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(3).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(3).toString() + "T" + endTime.toString();
                isThursday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(4).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(4).toString() + "T" + endTime.toString();
                isFriday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(5).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(5).toString() + "T" + endTime.toString();
                isSaturday = ra.checkRoom(sDate, eDate,classroomName);
                break;
            case "TUESDAY":
                sDate = weekDay.minusDays(2).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(2).toString() + "T" + endTime.toString();
                isSunday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.minusDays(1).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(1).toString() + "T" + endTime.toString();
                isMonday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.toString() + "T" + startTime.toString();
                eDate = weekDay.toString() + "T" + endTime.toString();
                isTuesday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(1).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(1).toString() + "T" + endTime.toString();
                isWednesday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(2).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(2).toString() + "T" + endTime.toString();
                isThursday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(3).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(3).toString() + "T" + endTime.toString();
                isFriday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(4).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(4).toString() + "T" + endTime.toString();
                isSaturday = ra.checkRoom(sDate, eDate,classroomName);
                break;
            case "WEDNESDAY":
                sDate = weekDay.minusDays(3).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(3).toString() + "T" + endTime.toString();
                isSunday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.minusDays(2).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(2).toString() + "T" + endTime.toString();
                isMonday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.minusDays(1).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(1).toString() + "T" + endTime.toString();
                isTuesday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.toString() + "T" + startTime.toString();
                eDate = weekDay.toString() + "T" + endTime.toString();
                isWednesday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(1).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(1).toString() + "T" + endTime.toString();
                isThursday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(2).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(2).toString() + "T" + endTime.toString();
                isFriday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(3).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(3).toString() + "T" + endTime.toString();
                isSaturday = ra.checkRoom(sDate, eDate,classroomName);
                break;
            case "THURSDAY":
                sDate = weekDay.minusDays(4).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(4).toString() + "T" + endTime.toString();
                isSunday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.minusDays(3).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(3).toString() + "T" + endTime.toString();
                isMonday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.minusDays(2).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(2).toString() + "T" + endTime.toString();
                isTuesday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.minusDays(1).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(1).toString() + "T" + endTime.toString();
                isWednesday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.toString() + "T" + startTime.toString();
                eDate = weekDay.toString() + "T" + endTime.toString();
                isThursday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(1).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(1).toString() + "T" + endTime.toString();
                isFriday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(2).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(2).toString() + "T" + endTime.toString();
                isSaturday = ra.checkRoom(sDate, eDate,classroomName);
                break;
            case "FRIDAY":
                sDate = weekDay.minusDays(5).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(5).toString() + "T" + endTime.toString();
                isSunday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.minusDays(4).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(4).toString() + "T" + endTime.toString();
                isMonday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.minusDays(3).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(3).toString() + "T" + endTime.toString();
                isTuesday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.minusDays(2).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(2).toString() + "T" + endTime.toString();
                isWednesday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.minusDays(1).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(1).toString() + "T" + endTime.toString();
                isThursday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.toString() + "T" + startTime.toString();
                eDate = weekDay.toString() + "T" + endTime.toString();
                isFriday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.plusDays(1).toString() + "T" + startTime.toString();
                eDate = weekDay.plusDays(1).toString() + "T" + endTime.toString();
                isSaturday = ra.checkRoom(sDate, eDate,classroomName);
                break;
            case "SATURDAY":
                sDate = weekDay.minusDays(6).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(6).toString() + "T" + endTime.toString();
                isSunday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.minusDays(5).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(5).toString() + "T" + endTime.toString();
                isMonday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.minusDays(4).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(4).toString() + "T" + endTime.toString();
                isTuesday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.minusDays(3).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(3).toString() + "T" + endTime.toString();
                isWednesday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.minusDays(2).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(2).toString() + "T" + endTime.toString();
                isThursday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.minusDays(1).toString() + "T" + startTime.toString();
                eDate = weekDay.minusDays(1).toString() + "T" + endTime.toString();
                isFriday = ra.checkRoom(sDate, eDate,classroomName);
                sDate = weekDay.toString() + "T" + startTime.toString();
                eDate = weekDay.toString() + "T" + endTime.toString();
                isSaturday = ra.checkRoom(sDate, eDate,classroomName);
                break;
        }
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isMonday() {
        return isMonday;
    }

    public void setMonday(boolean monday) {
        isMonday = monday;
    }

    public boolean isTuesday() {
        return isTuesday;
    }

    public void setTuesday(boolean tuesday) {
        isTuesday = tuesday;
    }

    public boolean isWednesday() {
        return isWednesday;
    }

    public void setWednesday(boolean wednesday) {
        isWednesday = wednesday;
    }

    public boolean isThursday() {
        return isThursday;
    }

    public void setThursday(boolean thursday) {
        isThursday = thursday;
    }

    public boolean isFriday() {
        return isFriday;
    }

    public void setFriday(boolean friday) {
        isFriday = friday;
    }

    public boolean isSaturday() {
        return isSaturday;
    }

    public void setSaturday(boolean saturday) {
        isSaturday = saturday;
    }

    public boolean isSunday() {
        return isSunday;
    }

    public void setSunday(boolean sunday) {
        isSunday = sunday;
    }

}

