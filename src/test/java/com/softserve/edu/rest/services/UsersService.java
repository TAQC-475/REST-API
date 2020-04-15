package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LogginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.*;
import com.softserve.edu.rest.tools.EntityUtils;
import io.qameta.allure.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsersService {
    private UsersResource usersResource;
    private AdminsResource adminsResource;
    protected LogginedUser logginedUser;

    public UsersService(LogginedUser logginedUser) {
        this.usersResource = new UsersResource();
        this.adminsResource = new AdminsResource();
        this.logginedUser = logginedUser;
    }

    @Step("Get list of users")
    public List<User> getAllUsers(){
        RestParameters urlParameters = new RestParameters()
            .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity usersResult = usersResource.httpGetAsEntity(null, urlParameters);
        EntityUtils.get().checkEntity(usersResult);
        return parseUsers(usersResult.getContent());
    }

    protected List<User> parseUsers(String users){
        List<User> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\t\\w+\n");
        Matcher matcher = pattern.matcher(users);
        while (matcher.find()){
            result.add(new User(users.substring(matcher.start() + 1,matcher.end() - 1)));
        }
        return result;
    }

    public boolean isUserPresent(User user){
        boolean result = false;
        for (User current: getAllUsers()){
            if (current.equalName(user)){
                result = true;
                break;
            }
        }
        return result;
    }

    public AdministrationService gotoAdministrationService(){
        return new AdministrationService(logginedUser);
    }

    @Step("Get list of admins")
    public List<User> getAdmins(){
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity usersResult = adminsResource.httpGetAsEntity(null, urlParameters);
        EntityUtils.get().checkEntity(usersResult);
        return parseUsers(usersResult.getContent());
    }

    /*...*/
}
