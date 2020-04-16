package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LogginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.UserResource;
import com.softserve.edu.rest.tools.EntityUtils;
import io.qameta.allure.Step;

public class UserService extends GuestService{
    private UserResource userResource;
    private LogginedUser logginedUser;

    public UserService(LogginedUser logginedUser) {
        userResource = new UserResource();
        this.logginedUser = logginedUser;
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

    /**
     * Change password
     * @param oldPassword actual user password
     * @param newPassword new user password
     * @return
     */
    @Step("Change password")
    protected UserService changePassword(User oldPassword, String newPassword){
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken())
                .addParameter(EParameters.OLD_PASSWORD, oldPassword.getPassword())
                .addParameter(EParameters.NEW_PASSWORD, newPassword);
        SimpleEntity result = userResource.httpPutAsEntity(null, urlParameters, null);
        EntityUtils.get().checkEntity(result);
        return this;
    }
}