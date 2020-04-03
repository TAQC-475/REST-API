package com.softserve.edu.rest.services;

import com.softserve.edu.rest.dto.LoginedUser;

public class AdministrationService extends UserService{
    private LoginedUser loginedUser;
//  private UsersService usersService;


    public AdministrationService(LoginedUser loginedUser) {
        super(loginedUser);
        this.loginedUser = loginedUser;
    }

  public UsersService gotoUsersService(){
    return new UsersService(loginedUser);
  }

  public ItemsService goToItemsService() {return  new ItemsService(loginedUser); }

  public CooldownService gotoCooldownService() {return new CooldownService(loginedUser); }

    public LogginedUsersService gotoLogginedUsersService(){
        return new LogginedUsersService(loginedUser);
    }

    public LoginedUser getLoginedUser() {
        return loginedUser;
    }
}
