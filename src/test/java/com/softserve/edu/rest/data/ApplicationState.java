package com.softserve.edu.rest.data;

import com.softserve.edu.rest.dto.LogginedUser;
import com.softserve.edu.rest.tools.CustomException;

import java.util.LinkedList;
import java.util.Stack;

public class ApplicationState {
    private static Stack<LogginedUser> loggedClients;
    private static ApplicationState instance;

    private ApplicationState(){
        loggedClients = new Stack<>();
    }

    public static ApplicationState get(){
        if(instance == null){
            instance = new ApplicationState();
        }
        return instance;
    }

    public void addUser(LogginedUser client){
        loggedClients.add(client);
    }

    public LogginedUser getLastLogged(){
        return loggedClients.peek();
    }

    public LogginedUser getAtIndex(int index){
        return loggedClients.get(index);
    }

    public void removeLastLoggined(){
        loggedClients.pop();
    }

    public void removeAtIndex(int index){
        loggedClients.remove(index);
    }

    public Stack<LogginedUser> getLoggedUsers() {
        return loggedClients;
    }

    public void removeLogged(LogginedUser logginedUser){
        loggedClients.remove(logginedUser);
    }

    public boolean isLoggined(LogginedUser logginedUser){
        return loggedClients.contains(logginedUser);
    }

    public LogginedUser getLogginedAdmin() throws CustomException{
        for(LogginedUser logginedUser : loggedClients){
            if(logginedUser.getUser().isAdmin())
                return logginedUser;
        }
        throw new CustomException("Cannot go to the AdministrationService because no administrators are loggined");
    }

    public LogginedUser getLogginedUser() throws CustomException{
        for(LogginedUser logginedUser : loggedClients){
            if(!logginedUser.getUser().isAdmin())
                return logginedUser;
        }
        throw new CustomException("Cannot go to the UserService because no users are loggined");
    }

    public LogginedUser getLogginedAdmin(String name){
        for(LogginedUser logginedUser : loggedClients){
            if(logginedUser.getUser().isAdmin() && logginedUser.getUser().getName().equalsIgnoreCase(name))
                return logginedUser;
        }
        return null;
    }

    public LogginedUser getLogginedUser(String name){
        for(LogginedUser logginedUser : loggedClients) {
            if (!logginedUser.getUser().isAdmin() && logginedUser.getUser().equalName(name))
                return logginedUser;
        }
        return null;
    }

}
