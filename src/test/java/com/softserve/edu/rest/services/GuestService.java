package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.ApplicationResource;
import com.softserve.edu.rest.tools.CustomException;
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

    public UserService gotoUserService(){
        UserService userService = null;
        try {
            userService = new UserService(ApplicationState.get().getLogginedUser());
        }catch (CustomException exception){
            exception.printStackTrace();
        }
        return userService;
    }

    public AdministrationService gotoAdministrationService(){
        AdministrationService administrationService = null;
        try {
            administrationService = new AdministrationService(ApplicationState.get().getLogginedAdmin());
        }catch (CustomException exception){
            exception.printStackTrace();
        }
        return administrationService;
    }

    @Step("Reset_Service_To_Initial_State")
    public GuestService resetServiceToInitialState() {
        SimpleEntity simpleEntity = applicationResource.httpGetAsEntity(null, null);
        EntityUtils.get().checkEntity(simpleEntity);
        return this;
    }
}