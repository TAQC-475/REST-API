package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LogginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.UserResource;
import com.softserve.edu.rest.tools.EntityUtils;
import io.qameta.allure.Step;

import java.util.List;

public class ManageUserService {

    private LogginedUser logginedUser;
    private UserResource userResource;

    public ManageUserService(LogginedUser logginedUser) {
        this.logginedUser = logginedUser;
        userResource = new UserResource();
    }

    @Step("Create user")
    public AdministrationService createUser(User user) {
        createUserExample(user);
        return new AdministrationService(logginedUser);
    }

    @Step("removeUser")
    public AdministrationService removeUser(User user) {
        removeUserExample(user);
        return new AdministrationService(logginedUser);
    }
    @Step("removeUserAndCheckIt")
    public boolean removeUserAndCheckIt(User user){
        removeUserExample(user);
        return true;
    }

    @Step("removeUsers")
    public AdministrationService removeUsers(List<User> users) {
        for (User current : users) {
            removeUser(current);
        }
        return new AdministrationService(logginedUser);
    }

    @Step("createUsers")
    public AdministrationService createUsers(List<User> users) {
        for (User current : users) {
            createUserExample(current);
        }
        return new AdministrationService(logginedUser);
    }



    private void removeUserExample(User user) {
        RestParameters bodyParameters = new RestParameters()
            .addParameter(EParameters.TOKEN, logginedUser.getToken())
            .addParameter(EParameters.NAME, user.getName());
        SimpleEntity simpleEntity = userResource
            .httpDeleteAsEntity(null, bodyParameters, bodyParameters);
        EntityUtils.get().checkEntity(simpleEntity);
    }


    private void createUserExample(User user) {
        RestParameters bodyParameters = new RestParameters()
            .addParameter(EParameters.TOKEN, logginedUser.getToken())
            .addParameter(EParameters.NAME, user.getName())
            .addParameter(EParameters.PASSWORD, user.getPassword())
            .addParameter(EParameters.RIGHTS, String.valueOf(user.isAdmin()));
        SimpleEntity simpleEntity = userResource.httpPostAsEntity(null, null, bodyParameters);
        EntityUtils.get().checkEntity(simpleEntity);
    }

    public AdministrationService gotoAdminService() {
        return new AdministrationService(logginedUser);
    }
}