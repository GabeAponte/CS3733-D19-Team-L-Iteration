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
        int milTime = startTime;
        int endMilTime = endTime;

        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", milTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat startSimpleDate = new SimpleDateFormat("hh:mm a");

        Date endDate = null;
        try {
            endDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", endMilTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat endSimpleDate = new SimpleDateFormat("hh:mm a");

        time = startSimpleDate.format(startDate) + " - " + endSimpleDate.format(endDate);

        LocalDate weekDay = theDate;
        String dayOfWeek = theDate.getDayOfWeek().toString();
        switch(dayOfWeek){
            case "SUNDAY":
                isSunday = ra.checkRoom(startTime, endTime,classroomName, weekDay.toString());
                isMonday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(1).toString());
                isTuesday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(2).toString());
                isWednesday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(3).toString());
                isThursday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(4).toString());
                isFriday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(5).toString());
                isSaturday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(6).toString());
                break;
            case "MONDAY":
                isSunday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(1).toString());
                isMonday = ra.checkRoom(startTime, endTime,classroomName, weekDay.toString());
                isTuesday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(1).toString());
                isWednesday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(2).toString());
                isThursday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(3).toString());
                isFriday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(4).toString());
                isSaturday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(5).toString());
                break;
            case "TUESDAY":
                isSunday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(2).toString());
                isMonday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(1).toString());
                isTuesday = ra.checkRoom(startTime, endTime,classroomName, weekDay.toString());
                isWednesday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(1).toString());
                isThursday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(2).toString());
                isFriday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(3).toString());
                isSaturday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(4).toString());
                break;
            case "WEDNESDAY":
                isSunday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(3).toString());
                isMonday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(2).toString());
                isTuesday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(1).toString());
                isWednesday = ra.checkRoom(startTime, endTime,classroomName, weekDay.toString());
                isThursday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(1).toString());
                isFriday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(2).toString());
                isSaturday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(3).toString());
                break;
            case "THURSDAY":
                isSunday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(4).toString());
                isMonday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(3).toString());
                isTuesday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(2).toString());
                isWednesday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(1).toString());
                isThursday = ra.checkRoom(startTime, endTime,classroomName, weekDay.toString());
                isFriday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(1).toString());
                isSaturday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(2).toString());
                break;
            case "FRIDAY":
                isSunday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(5).toString());
                isMonday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(4).toString());
                isTuesday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(3).toString());
                isWednesday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(2).toString());
                isThursday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(1).toString());
                isFriday = ra.checkRoom(startTime, endTime,classroomName, weekDay.toString());
                isSaturday = ra.checkRoom(startTime, endTime,classroomName, weekDay.plusDays(1).toString());
                break;
            case "SATURDAY":
                isSunday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(6).toString());
                isMonday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(5).toString());
                isTuesday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(4).toString());
                isWednesday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(3).toString());
                isThursday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(2).toString());
                isFriday = ra.checkRoom(startTime, endTime,classroomName, weekDay.minusDays(1).toString());
                isSaturday = ra.checkRoom(startTime, endTime,classroomName, weekDay.toString());
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

