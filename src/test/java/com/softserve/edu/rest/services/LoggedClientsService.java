package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.AliveTokensResource;
import com.softserve.edu.rest.resources.LoggedAdminsResource;
import com.softserve.edu.rest.resources.LoggedUsersResource;

import java.util.List;

public class LoggedClientsService extends UsersService{ //TODO Rename it (maybe)
    private LoggedUsersResource loggedUsersResource;
    private LoggedAdminsResource loggedAdminsResource;

    public LoggedClientsService(LoginedUser loginedUser){
        super(loginedUser);
        this.loggedUsersResource = new LoggedUsersResource();
        this.loggedAdminsResource = new LoggedAdminsResource();
    }



    public List<User> getLoggedUsers(){
        RestParameters urlParameters = new RestParameters()
                .addParameter("token",loginedUser.getToken());
        SimpleEntity loggedUsersResult = loggedUsersResource.httpGetAsEntity(null, urlParameters);
        checkUsersFound(loggedUsersResult);
        return parseUsers(loggedUsersResult.getContent());
    }

    public List<User> getLoggedAdmins(){
        RestParameters urlParameters = new RestParameters()
                .addParameter("token",loginedUser.getToken());
        SimpleEntity loggedAdminsResult = loggedAdminsResource.httpGetAsEntity(null, urlParameters);
        checkUsersFound(loggedAdminsResult);
        return parseUsers(loggedAdminsResult.getContent());
    }

}
