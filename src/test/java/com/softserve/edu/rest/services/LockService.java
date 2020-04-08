package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LoginedUser;
import com.softserve.edu.rest.dto.RestParameters;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.resources.LockAdminsResource;
import com.softserve.edu.rest.resources.LockUserResource;
import com.softserve.edu.rest.resources.LockUsersResource;
import com.softserve.edu.rest.tools.EntityUtils;

public class LockService {

    protected LockUserResource lockUserResource;
    protected LockUsersResource lockUsersResource;
    protected LockAdminsResource lockAdminsResource;
    protected LoginedUser loginedUser;

    public LockService(LoginedUser loginedUser) {
        lockUserResource = new LockUserResource();
        lockUsersResource = new LockUsersResource();
        lockAdminsResource = new LockAdminsResource();
        this.loginedUser = loginedUser;
    }

    public LockService lockUser(User userToLock) {
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.NAME, userToLock.getName());
        SimpleEntity simpleEntity = lockUserResource
                .httpPostAsEntity(pathParameters, null, bodyParameters);
        EntityUtils.get().checkLockEntity(simpleEntity, "User was not locked");
        return this;
    }

    public String getAllLockedUsers() {
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken());
        SimpleEntity simpleEntity = lockUsersResource
                .httpGetAsEntity(null, bodyParameters);

        System.out.println("locked users - "+simpleEntity.getContent());  //  !!!
        return simpleEntity.getContent();
    }

    public LockService unlockUser(User user) {
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.NAME, user.getName());
        SimpleEntity simpleEntity = lockUserResource
                .httpPutAsEntity(pathParameters, null, bodyParameters);
        EntityUtils.get().checkLockEntity(simpleEntity, "User was not unlocked");
        return this;
    }

    public LockService unlockAllUsers() {
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken());
        SimpleEntity simpleEntity = lockUsersResource
                .httpPutAsEntity(null, null, bodyParameters);
        return this;
    }

    public String getAllLockedAdmins() {
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken());
        SimpleEntity simpleEntity = lockUserResource.httpGetAsEntity(null, urlParameters);
        return simpleEntity.getContent();
    }

    public boolean isUserLocked(User userToLock) {
        if (getAllLockedUsers().contains(userToLock.getName())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isAdminLocked(User user) {
        if (getAllLockedAdmins().contains(user.getName())) {
            return true;
        } else {
            return false;
        }
    }

    public LoginService loadApplication() {
        // TODO Check Server Availability
        return new LoginService();
    }

}
