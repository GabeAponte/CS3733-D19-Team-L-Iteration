package edu.wpi.cs3733.d19.teamL.RoomBooking;

package edu.wpi.cs3733.d19.teamL.RoomBooking;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Room{

    String time;
    boolean isMonday;
    boolean isTuesday;
    boolean isWednesday;
    boolean isThursday;
    boolean isFriday;
    boolean isSaturday;
    boolean isSunday;

    public Room(String startTime, String endTime, LocalDate theDate){
        int milTime = Integer.parseInt(startTime);
        int endMilTime = Integer.parseInt(endTime);

        Date startDate = null;
        try {
            startDate = new SimpleDateFormat("hhmm").parse(String.format("%04d", milTime));
        } catch (ParseException e) {
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
        //  System.out.println(startSimpleDate.format(endDate));

        time = startSimpleDate.format(startDate) + " - " + endSimpleDate.format(endDate);

        
    }

}

