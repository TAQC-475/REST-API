package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LogginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.LogginedAdminsResource;
import com.softserve.edu.rest.resources.LogginedUsersResource;
import com.softserve.edu.rest.tools.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LogginedUsersService{ //TODO Rename it (maybe)
    private LogginedUsersResource logginedUsersResource;
    private LogginedAdminsResource logginedAdminsResource;
    private LogginedUser logginedUser;

    public LogginedUsersService(LogginedUser logginedUser){
        this.logginedUser = logginedUser;
        this.logginedUsersResource = new LogginedUsersResource();
        this.logginedAdminsResource = new LogginedAdminsResource();
    }

    private List<User> parseUsers(String users) {
        List<User> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\t\\w+\n");
        Matcher matcher = pattern.matcher(users);
        while (matcher.find()) {
            result.add(new User(users.substring(matcher.start() + 1, matcher.end() - 1)));
        }
        return result;
    }


    public List<User> getLogginedUsers() {
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity loggedUsersResult = logginedUsersResource.httpGetAsEntity(null, urlParameters);
        EntityUtils.get().checkEntity(loggedUsersResult);
        return parseUsers(loggedUsersResult.getContent());
    }

    public List<User> getLogginedAdmins() {
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity loggedAdminsResult = logginedAdminsResource.httpGetAsEntity(null, urlParameters);
        EntityUtils.get().checkEntity(loggedAdminsResult);
        return parseUsers(loggedAdminsResult.getContent());
    }

}
