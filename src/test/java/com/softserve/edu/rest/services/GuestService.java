package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.tools.CustomException;

public class GuestService {
    public GuestService(){}

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
}