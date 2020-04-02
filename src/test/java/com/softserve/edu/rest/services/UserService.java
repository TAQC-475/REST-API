package com.softserve.edu.rest.services;


import com.softserve.edu.rest.dto.LoginedUser;

public class UserService {
    private LoginedUser loginedUser;

    public UserService(LoginedUser loginedUser) {
        this.loginedUser = loginedUser;
    }

    public ItemService goToItemService(){
        return new ItemService(loginedUser);
    }
}
