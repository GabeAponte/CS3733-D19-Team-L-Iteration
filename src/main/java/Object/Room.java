package Object;

import java.util.ArrayList;

public class Room{

    String time;
    boolean class1;
    boolean class2;
    boolean class3;
    boolean class4;
    boolean class5;
    boolean class6;
    boolean class7;
    boolean class8;
    boolean class9;
    boolean auditorium;

    public Room(String theTime, ArrayList<String> theRooms){
        time = theTime;
        if(theRooms.contains("Classroom 1 (Computer)"))
            class1 = true;
        else
            class1 = false;

        if(theRooms.contains("Classroom 2 (Computer)"))
            class2 = true;
        else
            class2 = false;

        if(theRooms.contains("Classroom 3 (Computer)"))
            class3 = true;
        else
            class3 = false;

        if(theRooms.contains("Classroom 4 (Classroom)"))
            class4 = true;
        else
            class4 = false;

        if(theRooms.contains("Classroom 5 (Computer)"))
            class5 = true;
        else
            class5 = false;

        if(theRooms.contains("Classroom 6 (Classroom)"))
            class6 = true;
        else
            class6 = false;

        if(theRooms.contains("Classroom 7 (Computer)"))
            class7 = true;
        else
            class7 = false;

        if(theRooms.contains("Classroom 8 (Classroom)"))
            class8 = true;
        else
            class8 = false;

        if(theRooms.contains("Classroom 9 (Computer)"))
            class9 = true;
        else
            class9 = false;


        if(theRooms.contains("Mission Hall Auditorium"))
            auditorium = true;
        else
            auditorium = false;
    }

    public String getTime() {
        return time;
    }

    public boolean isClass1() {
        return class1;
    }

    public boolean isClass2() {
        return class2;
    }

    public boolean isClass3() {
        return class3;
    }

    public boolean isClass4() {
        return class4;
    }

    public boolean isClass5() {
        return class5;
    }

    public boolean isClass6() {
        return class6;
    }

    public boolean isClass7() {
        return class7;
    }

    public boolean isClass8() {
        return class8;
    }

    public boolean isClass9() {
        return class9;
    }

    public boolean isAuditorium() {
        return auditorium;
    }
}
