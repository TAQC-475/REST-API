package com.softserve.edu.rest.services;

import com.softserve.edu.rest.dto.LogginedUser;

public class AdministrationService extends UserService{
    private LogginedUser logginedUser;
//  private UsersService usersService;


    public AdministrationService(LogginedUser logginedUser) {
        super(logginedUser);
        this.logginedUser = logginedUser;
    }

    public UsersService gotoUsersService() {
        return new UsersService(logginedUser);
    }

    public ItemService goToItemService() {
        return new ItemService(logginedUser);
    }

    public ItemsService goToItemsService() {
        return new ItemsService(logginedUser);
    }

    public LoginService goToLoginService() {
        return new LoginService();
    }

    public CooldownService gotoCooldownService() { return new CooldownService(logginedUser); }
    public LockService gotoLockService(){ return new LockService(logginedUser);}

    public LogginedUsersService gotoLogginedUsersService(){
        return new LogginedUsersService(logginedUser);
    }

    public LogginedUser getLogginedUser() {
        return logginedUser;
    }
    public ManageUserService gotoManageUserService(){
        return new ManageUserService(logginedUser);
    }

//    public AdministrationService createUser(){
//
//    }
}
