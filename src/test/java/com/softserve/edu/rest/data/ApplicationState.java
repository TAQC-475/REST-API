package com.softserve.edu.rest.data;

import com.softserve.edu.rest.dto.LogginedUser;
import com.softserve.edu.rest.tools.CustomException;

import java.util.LinkedList;

public class ApplicationState {
    private static LinkedList<LogginedUser> logginedClients;
    private static ApplicationState instance;

    private ApplicationState(){
        logginedClients = new LinkedList<>();
    }

    public static ApplicationState get(){
        if(instance == null){
            instance = new ApplicationState();
        }
        return instance;
    }

    public void addUser(LogginedUser client){
        logginedClients.add(client);
    }

    public LogginedUser getLastLoggined(){
        return logginedClients.getLast();
    }

    public LogginedUser getAtIndex(int index){
        return logginedClients.get(index);
    }

    public void removeLastLoggined(){
        logginedClients.removeLast();
    }

    public void removeAtIndex(int index){
        logginedClients.remove(index);
    }

    public LinkedList<LogginedUser> getLogginedUsers() {
        return logginedClients;
    }

    public void removeLoggined(LogginedUser logginedUser){
        logginedClients.remove(logginedUser);
    }

    public boolean isLoggined(LogginedUser logginedUser){
        return logginedClients.contains(logginedUser);
    }

    public LogginedUser getLogginedAdmin() throws CustomException{
        for(LogginedUser logginedUser : logginedClients){
            if(logginedUser.getUser().isAdmin())
                return logginedUser;
        }
        throw new CustomException("Cannot go to the AdministrationService because no administrators are loggined");
    }

    public LogginedUser getLogginedUser() throws CustomException{
        for(LogginedUser logginedUser : logginedClients){
            if(!logginedUser.getUser().isAdmin())
                return logginedUser;
        }
        throw new CustomException("Cannot go to the UserService because no users are loggined");
    }

    public LogginedUser getLogginedAdmin(String name){
        for(LogginedUser logginedUser : logginedClients){
            if(logginedUser.getUser().isAdmin() && logginedUser.getUser().getName().equalsIgnoreCase(name))
                return logginedUser;
        }
        return null;
    }

    public LogginedUser getLogginedUser(String name){
        for(LogginedUser logginedUser : logginedClients){
            if(!logginedUser.getUser().isAdmin() && logginedUser.getUser().equalName(name))
                return logginedUser;
        }
        return null;
    }
}
