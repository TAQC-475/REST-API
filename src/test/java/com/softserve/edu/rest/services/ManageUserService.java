package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.UserResource;
import com.softserve.edu.rest.tools.EntityUtils;

public class ManageUserService {
    private LoginedUser loginedUser;
    private UserResource userResource;

    public ManageUserService(LoginedUser loginedUser) {
        this.loginedUser = loginedUser;
        userResource = new UserResource();
    }

    public AdministrationService createUser(User user){
        createUserExample(user);
        return new AdministrationService(loginedUser);
    }



    private void createUserExample(User user){
        RestParameters bodyParameters = new RestParameters()
            .addParameter(EParameters.TOKEN, loginedUser.getToken())
            .addParameter(EParameters.NAME, user.getName())
            .addParameter(EParameters.PASSWORD, user.getPassword())
            .addParameter(EParameters.RIGHTS, String.valueOf(user.isAdmin()));
        SimpleEntity simpleEntity = userResource.httpPostAsEntity(null, null, bodyParameters);
        EntityUtils.get().checkEntity(simpleEntity);
    }

    public AdministrationService gotoAdminService(){
        return new AdministrationService(loginedUser);
    }
}
