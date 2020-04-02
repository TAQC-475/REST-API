package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.*;
import com.softserve.edu.rest.tools.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsersService {
    private UsersResource usersResource;
    protected LoginedUser loginedUser;

    public UsersService(LoginedUser loginedUser) {
        this.usersResource = new UsersResource();
        this.loginedUser = loginedUser;
    }


    public List<User> getAllUsers(){
        RestParameters urlParameters = new RestParameters()
            .addParameter(EParameters.TOKEN, loginedUser.getToken());
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
        return new AdministrationService(loginedUser);
    }

    /*...*/
}
