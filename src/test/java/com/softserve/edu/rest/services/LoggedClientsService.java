package com.softserve.edu.rest.services;

import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.resources.AliveTokensResource;
import com.softserve.edu.rest.resources.LoggedAdminsResource;
import com.softserve.edu.rest.resources.LoggedUsersResource;

public class LoggedClientsService extends UsersService{ //TODO Rename it (maybe)
    private LoggedUsersResource loggedUsersResource;
    private LoggedAdminsResource loggedAdminsResource;
    private AliveTokensResource aliveTokensResource;

    public LoggedClientsService(LoginedUser loginedUser){
        super(loginedUser);
        this.loggedUsersResource = new LoggedUsersResource();
        this.loggedAdminsResource = new LoggedAdminsResource();
        this.aliveTokensResource = new AliveTokensResource();
    }


}
