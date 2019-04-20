package edu.wpi.cs3733.d19.teamL;

import edu.wpi.cs3733.d19.teamL.Map.MapLocations.Location;

/*MEMENTO WILL RETURN TO BASE SCREENS ONLY
//EmployeeTableController - done
//All homescreens - homescreen done, login done, employee done, admin done, about done
//Map Editor - done
//PathFindingController - done
//BookRoom
//ActiveServiceRequestsController
//SuggestionBoxController - done
//SuggestionTableController

WILL NOT GO TO:
//CreateEditAccountController - done
//Make Event Tab in Book Room Controller
//FulfillRequestController - NOT NEEDED
//All individual service requests


WILL RESTORE INFORMATION BUT WILL NOT OVERWRITE DB ENTRIES
    going back to a service request, changing a field, submitting will make new request not overwrite old
    THIS IS A FEATURE NOT A BUG
*/
public class Memento {
    String fxml;
    String pathPref;
    String typeFilter;
    String floorFilter;
    Location start;
    Location end;

    public Memento(String fxml){
        this.fxml = fxml;
    }

    public Memento(String fxml, String Preference, String type, String floorFilter, Location start, Location end){
        this.fxml = fxml;
        pathPref = Preference;
        typeFilter = type;
        this.floorFilter = floorFilter;
        this.start = start;
        this.end = end;
    }

    public String getFxml(){
        return fxml;
    }

    public String getPathPref() {
        return pathPref;
    }

    public String getTypeFilter() {
        return typeFilter;
    }

    public String getFloorFilter() {
        return floorFilter;
    }

    public Location getStart() {
        return start;
    }

    public Location getEnd() {
        return end;
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

    /**@author Nathan
     * @return false if this screen is ONLY accessible to logged in users or signed out users, not both
     */
    public boolean isMultiUser(){
        if(fxml.contains("PathFinding") || fxml.contains("ServiceRequest")){
            return true;
        }
        return false;
    }

    /**@author Nathan
     *
     */
    public boolean hasPrivileges() {
        Singleton single = Singleton.getInstance();
        if ((fxml.contains("Admin") || fxml.contains("EmployeeTable") || fxml.contains("EditLocation")) && single.isIsAdmin()) {
            return true;
        } else if (fxml.contains("EmployeeLogged") && single.isLoggedIn()){
            return true;
        }
        return false;
    }
}
