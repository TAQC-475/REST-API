package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.ApplicationResource;
import com.softserve.edu.rest.tools.EntityUtils;
import io.qameta.allure.Step;

public class GuestService {
    private ApplicationResource applicationResource;

    public GuestService(){
        applicationResource = new ApplicationResource();
    }

    public LoginService gotoLoginService(){
        return new LoginService();
    }

    public UserService gotoUserService(String name){
        UserService userService = new UserService(ApplicationState.get().getLogginedUser(name));
        return userService;
    }

    public AdministrationService gotoAdministrationService(String name){
        AdministrationService administrationService = new AdministrationService(ApplicationState.get()
                .getLogginedAdmin(name));
        return administrationService;
    }

    @Step("Reset_Service_To_Initial_State")
    public GuestService resetServiceToInitialState() {
        SimpleEntity simpleEntity = applicationResource.httpGetAsEntity(null, null);
        EntityUtils.get().checkEntity(simpleEntity);
        ApplicationState.get().getLogginedUsers().clear();
        return this;
    }
}