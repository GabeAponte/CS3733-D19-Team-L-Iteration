package edu.wpi.cs3733.d19.teamL.RoomBooking;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class VisualSimulationThread extends Thread{
    private int length;
    private ArrayList<Boolean> sim;

    public VisualSimulationThread (int total){
        length = total;
        sim = new ArrayList<Boolean>();
        for(int i = 0; i < length; i++){
            sim.add(true);
        }
    }

    public void run(){
        Random random = new Random();
        int num = random.nextInt(2) + 1;
        int natural = random.nextInt(length);
        sim.set(natural, switchValue(natural));
        if(num == 2){
            int natural2 = random.nextInt(length);
            sim.set(natural2, switchValue(natural2));
        }

    }

    private boolean switchValue(int i){
        return !sim.get(i);
    }

    public ArrayList<Boolean> getSimulation(){
        run();
        return sim;
    }
}
