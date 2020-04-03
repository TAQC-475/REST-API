package com.softserve.edu.rest.services;

import com.softserve.edu.rest.dto.LoginedUser;

public class AdministrationService {
    private LoginedUser loginedUser;
//  private UsersService usersService;


    public AdministrationService(LoginedUser loginedUser) {
        this.loginedUser = loginedUser;
//    this.usersService = new UsersService(loginedUser);

    }

    public UsersService gotoUsersService() {
        return new UsersService(loginedUser);
    }

    public ItemsService goToItemsService() {
        return new ItemsService(loginedUser);
    }

    public CooldownService gotoCooldownService() {
        return new CooldownService(loginedUser);
    }

    public LoginedUser getLoginedUser() {
        return loginedUser;
    }
}
