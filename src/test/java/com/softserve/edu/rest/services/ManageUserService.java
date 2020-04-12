package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LogginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.UserResource;
import com.softserve.edu.rest.tools.EntityUtils;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ManageUserService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ManageUserService.class);

    private LogginedUser logginedUser;
    private UserResource userResource;

    public ManageUserService(LogginedUser logginedUser) {
        this.logginedUser = logginedUser;
        userResource = new UserResource();
    }

    @Step("Create user")
    public AdministrationService createUser(User user) {
        LOGGER.debug("Create: " + user);
        createUserExample(user);
        return new AdministrationService(logginedUser);
    }

    @Step("Remove User")
    public AdministrationService removeUser(User user) {
        LOGGER.debug("Remove: {}", user.getName());
        removeUserExample(user);
        return new AdministrationService(logginedUser);
    }

    @Step("Remove User And Check It")
    public boolean removeUserAndCheckIt(User user) {
        LOGGER.debug("Remove: {}", user.getName());
        removeUserExample(user);
        return true;
    }

    @Step("Remove Users")
    public AdministrationService removeUsers(List<User> users) {
        LOGGER.debug("Remove users list: {}", users);
        for (User current : users) {
            removeUser(current);
        }
        return new AdministrationService(logginedUser);
    }

    @Step("Create Users")
    public AdministrationService createUsers(List<User> users) {
        LOGGER.debug("Create users list: {}", users);
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