package edu.wpi.cs3733.d19.teamL;

/*MEMENTO WILL RETURN TO BASE SCREENS ONLY
//EmployeeTableController
//All homescreens - homescreen done, login done,
//Map Editor
//PathFindingController
//BookRoom
//ActiveServiceRequestsController
//SuggestionBoxController
//SuggestionTableController

WILL NOT GO TO:
//CreateEditAccountController
//Make Event Tab in Book Room Controller
//FulfillRequestController
//All individual service requests


WILL RESTORE INFORMATION BUT WILL NOT OVERWRITE DB ENTRIES
    going back to a service request, changing a field, submitting will make new request not overwrite old
    THIS IS A FEATURE NOT A BUG
*/
public class Memento {
    String fxml;

    public Memento(String fxml){
        this.fxml = fxml;
    }

    public String getFxml(){
        return fxml;
    }

    /**@author Nathan
     * @return false if this screen is inaccessible to logged in users
     */
    public boolean goingToSignedScreen(){
        if(fxml.contains("HospitalHome") || fxml.contains("SuggestionBox") || fxml.contains("LogIn") || fxml.contains("About")){
            return false;
        }
        return true;
    }
}
