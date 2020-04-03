package com.softserve.edu.rest.data;

import com.softserve.edu.rest.dto.LoginedUser;

import java.util.LinkedList;

public class LogginedUsers {
    private static LinkedList<LoginedUser> logginedClients;
    private static LogginedUsers instance;

    private LogginedUsers(){
        logginedClients = new LinkedList<>();
    }

    public static LogginedUsers get(){
        if(instance == null){
            instance = new LogginedUsers();
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
}
