package com.softserve.edu.rest.services;


import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.resources.UserResource;

public class UserService {
    private UserResource userResource;
    private LoginedUser loginedUser;

    public UserService(LoginedUser loginedUser) {
        userResource = new UserResource();
        this.loginedUser = loginedUser;
    }

    public ItemService goToItemService(){
        return new ItemService(loginedUser);
    }

    public ItemsService goToItemsService(){
        return new ItemsService(loginedUser);
    }

//    public boolean changePassword(User oldPassword, User newPassword){
//        RestParameters bodyParameters = new RestParameters()
//                .addParameter(EParameters.TOKEN, loginedUser.getToken())
//                .addParameter(EParameters.OLD_PASSWORD, oldPassword.getPassword())
//                .addParameter(EParameters.NEW_PASSWORD, newPassword.getPassword());
//    }
}
