package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.tools.EntityUtils;

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
  public ItemService goToItemService(){return  new ItemService(loginedUser);}

  public ItemsService goToItemsService() {return  new ItemsService(loginedUser); }

  public CooldownService gotoCooldownService() {return new CooldownService(loginedUser); }

    public LogginedUsersService gotoLogginedUsersService(){
        return new LogginedUsersService(loginedUser);
    }

    public LoginedUser getLoginedUser() {
        return loginedUser;
    }

}
