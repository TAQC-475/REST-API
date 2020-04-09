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
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockService {

    public static final Logger logger = LoggerFactory.getLogger(LockService.class);

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

    @Step("Locking user with token")
    public LockService lockUser(User userToLock) {
        logger.debug("Locking user START, user name = " + userToLock.getName());
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.NAME, userToLock.getName());
        SimpleEntity simpleEntity = lockUserResource
                .httpPostAsEntity(pathParameters, null, bodyParameters);
        logger.debug("Locking user DONE, response = " + simpleEntity.getContent());
        EntityUtils.get().checkLockEntity(simpleEntity, "User was not locked");
        return this;
    }

    @Step("Get all locked users")
    public String getAllLockedUsers() {
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken());
        SimpleEntity simpleEntity = lockUsersResource
                .httpGetAsEntity(null, bodyParameters);

        logger.debug("Locked users = "+simpleEntity.getContent());
        return simpleEntity.getContent();
    }

    @Step("Unlock user")
    public LockService unlockUser(User user) {
        logger.debug("Unlocking user START, user name = " + user.getName());
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.NAME, user.getName());
        SimpleEntity simpleEntity = lockUserResource
                .httpPutAsEntity(pathParameters, null, bodyParameters);
        EntityUtils.get().checkLockEntity(simpleEntity, "User was not unlocked");
        logger.debug("Unlocking user DONE, response = " + simpleEntity.getContent());
        return this;
    }

    @Step("Unlock all users")
    public LockService unlockAllUsers() {
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken());
        SimpleEntity simpleEntity = lockUsersResource
                .httpPutAsEntity(null, null, bodyParameters);
        logger.debug("Unlock all locked users is DONE");
        return this;
    }

    @Step("Get all locked admins")
    public String getAllLockedAdmins() {
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, loginedUser.getToken());
        SimpleEntity simpleEntity = lockUserResource.httpGetAsEntity(null, urlParameters);
        logger.debug("Unlock all locked users is DONE");
        return simpleEntity.getContent();
    }

    @Step("Check if user is logged")
    public boolean isUserLocked(User userToLock) {
        if (getAllLockedUsers().contains(userToLock.getName())) {
            return true;
        } else {
            return false;
        }
    }

    @Step("Check if admin is logged")
    public boolean isAdminLocked(User user) {
        if (getAllLockedAdmins().contains(user.getName())) {
            return true;
        } else {
            return false;
        }
    }

}
