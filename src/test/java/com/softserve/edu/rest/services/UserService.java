package com.softserve.edu.rest.services;


import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.UserResource;
import com.softserve.edu.rest.tools.EntityUtils;

public class UserService {

    public static final String SUCCESS_MARKER = "true";

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

    public LoginService goToLoginService(){
        return new LoginService();
    }

    public UserService changePassword(User oldPassword, User newPassword){
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken())
                .addParameter(EParameters.OLD_PASSWORD, oldPassword.getPassword())
                .addParameter(EParameters.NEW_PASSWORD, newPassword.getPassword());
        SimpleEntity result = userResource.httpPutAsEntity(null, urlParameters, null);
        EntityUtils.get().checkEntity(result);
        return this;
    }

    public static boolean isUserActionSuccessful(SimpleEntity logoutEntity) {
        return logoutEntity.getContent().equalsIgnoreCase(SUCCESS_MARKER);
    }

}