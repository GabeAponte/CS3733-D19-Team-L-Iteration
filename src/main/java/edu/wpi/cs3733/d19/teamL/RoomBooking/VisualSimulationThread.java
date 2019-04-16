package edu.wpi.cs3733.d19.teamL.RoomBooking;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class VisualSimulationThread extends Thread{
    private int length;
    private ArrayList<Boolean> sim;
    private boolean die;

    public VisualSimulationThread (int total){
        length = total;
        sim = new ArrayList<Boolean>();
        for(int i = 0; i < length; i++){
            sim.add(false);
        }
        die = false;
    }

    public void run(){
        Random random = new Random();
        int num = random.nextInt(2) + 1;
        int natural = random.nextInt(length);
        int natural2 = -1;
        if(num == 2){
            natural2 = random.nextInt(length);
        }
        for (int i = 0; i < length; i++) {
            if(i == natural){
                sim.set(i, switchValue(i));
            } else if(i == natural2){
                sim.set(i, switchValue(i));
            }

        }
    }

    private boolean switchValue(int i){
        if(sim.get(i)){
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Boolean> getSimulation(){
        run();
        return sim;
    }

    public void end(){
        die = true;
    }
}
