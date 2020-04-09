package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LogginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.LogginedAdminsResource;
import com.softserve.edu.rest.resources.LogginedUsersResource;
import com.softserve.edu.rest.tools.EntityUtils;

import java.util.List;


public class LogginedUsersService extends UsersService{ //TODO Rename it (maybe)
    private LogginedUsersResource logginedUsersResource;
    private LogginedAdminsResource logginedAdminsResource;

    public LogginedUsersService(LogginedUser logginedUser){
        super(logginedUser);
        this.logginedUsersResource = new LogginedUsersResource();
        this.logginedAdminsResource = new LogginedAdminsResource();
    }


    public List<User> getLoggedUsers() {
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity loggedUsersResult = logginedUsersResource.httpGetAsEntity(null, urlParameters);
        EntityUtils.get().checkEntity(loggedUsersResult);
        return parseUsers(loggedUsersResult.getContent());
    }

    public List<User> getLoggedAdmins() {
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity loggedAdminsResult = logginedAdminsResource.httpGetAsEntity(null, urlParameters);
        EntityUtils.get().checkEntity(loggedAdminsResult);
        return parseUsers(loggedAdminsResult.getContent());
    }

}
