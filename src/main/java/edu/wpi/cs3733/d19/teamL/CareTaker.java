package edu.wpi.cs3733.d19.teamL;

import java.util.LinkedList;

public class CareTaker {
    LinkedList<Memento> memes;
    Memento original;
    Memento admin;
    Memento employee;

    public CareTaker(){
        memes = new LinkedList<>();
        original = new Memento("HospitalHome.fxml");
        admin = new Memento("AdminLoggedInHome.fxml");
        employee = new Memento("EmployeeLoggedInHome.fxml");
    }

    public Memento restore(){
        Singleton single = Singleton.getInstance();
        if(memes.peek() == null || memes.size() == 0){ //if list is empty, go back home
            return original;
        } else if(!memes.peek().goingToSignedScreen()){ //if going to guest only screen, sign out user
            single.setIsAdmin(false);
            single.setLoggedIn(false);
            single.setUsername("");
        } else if(memes.peek().isMultiUser()){ //if going to multi privilege screen
        } else if(!memes.peek().hasPrivileges()){
            memes.clear();
            return original;
        }
        return memes.pop();
    }

    public void save(Memento m){
        memes.push(m);
    }

    public Memento getOriginal(){
        return original;
    }

    public Memento getAdmin(){
        return admin;
    }

    public Memento getEmp(){
        return employee;
    }
}
