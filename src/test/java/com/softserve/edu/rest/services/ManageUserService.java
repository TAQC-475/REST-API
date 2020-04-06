package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.LockedUsersResource;
import com.softserve.edu.rest.resources.UserResource;
import com.softserve.edu.rest.tools.EntityUtils;
import java.util.List;
import javax.jws.soap.SOAPBinding.Use;

public class ManageUserService {

    private LoginedUser loginedUser;
    private UserResource userResource;
    private LockedUsersResource lockedUsersResource;

    public ManageUserService(LoginedUser loginedUser) {
        this.loginedUser = loginedUser;
        userResource = new UserResource();
    }

    public AdministrationService createUser(User user) {
        createUserExample(user);
        return new AdministrationService(loginedUser);
    }

    public AdministrationService removeUser(User user) {
        removeUserExample(user);
        return new AdministrationService(loginedUser);
    }

    public AdministrationService removeUsers(List<User> users) {
        for (User current : users) {
            removeUser(current);
        }
        return new AdministrationService(loginedUser);
    }

    public AdministrationService createUsers(List<User> users) {
        for (User current : users) {
            createUser(current);
        }
        return new AdministrationService(loginedUser);
    }



    private void removeUserExample(User user) {
        RestParameters bodyParameters = new RestParameters()
            .addParameter(EParameters.TOKEN, loginedUser.getToken())
            .addParameter(EParameters.NAME, user.getName());
        SimpleEntity simpleEntity = userResource
            .httpDeleteAsEntity(null, bodyParameters, bodyParameters);
        EntityUtils.get().checkEntity(simpleEntity);
    }


    private void createUserExample(User user) {

        RestParameters bodyParameters = new RestParameters()
            .addParameter(EParameters.TOKEN, loginedUser.getToken())
            .addParameter(EParameters.NAME, user.getName())
            .addParameter(EParameters.PASSWORD, user.getPassword())
            .addParameter(EParameters.RIGHTS, String.valueOf(user.isAdmin()));
        SimpleEntity simpleEntity = userResource.httpPostAsEntity(null, null, bodyParameters);
        EntityUtils.get().checkEntity(simpleEntity);
    }

    public AdministrationService gotoAdminService() {
        return new AdministrationService(loginedUser);
    }
}