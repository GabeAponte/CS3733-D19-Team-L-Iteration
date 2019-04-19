package edu.wpi.cs3733.d19.teamL;

import java.util.LinkedList;

public class CareTaker {
    LinkedList<Memento> memes;
    Memento original;

    public CareTaker(){
        memes = new LinkedList<>();
        original = new Memento("HospitalHome.fxml");
    }

    public Memento restore(){
        Singleton single = Singleton.getInstance();
        if(memes.peek() == null || memes.size() == 0){ //if list is empty, go back home
            return original;
        } else if(memes.peek().goingToSignedScreen() && !single.isLoggedIn()){ //if signed out returning to signed in screen
            memes.clear();
            return original;
        } else if(!memes.peek().goingToSignedScreen()){ //if going to guest only screen, sign out user
            single.setIsAdmin(false);
            single.setLoggedIn(false);
            single.setUsername("");
        }
        return memes.pop();
    }

    public void save(Memento m){
        memes.push(m);
    }

    public Memento getOriginal(){
        memes.clear();
        return original;
    }
}
