package com.softserve.edu.rest.data;

import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.tools.CustomException;

import java.util.LinkedList;

public class ApplicationState {
    private static LinkedList<LoginedUser> logginedClients;
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

    public void addUser(LoginedUser client){
        logginedClients.add(client);
    }

    public LoginedUser getLastLoggined(){
        return logginedClients.getLast();
    }

    public LoginedUser getAtIndex(int index){
        return logginedClients.get(index);
    }

    public void removeLastLoggined(){
        logginedClients.removeLast();
    }

    public void removeAtIndex(int index){
        logginedClients.remove(index);
    }

    public LinkedList<LoginedUser> getLogginedUsers() {
        return logginedClients;
    }

    public void removeLoggined(LoginedUser loginedUser){
        logginedClients.remove(loginedUser);
    }

    public boolean isLoggined(LoginedUser loginedUser){
        return logginedClients.contains(loginedUser);
    }

    public LoginedUser getAdmin() throws CustomException{
        for(LoginedUser loginedUser: logginedClients){
            if(loginedUser.getUser().isAdmin())
                return loginedUser;
        }
        throw new CustomException("Could not go to the AdministrationService because no administrators are logined");
    }
}
