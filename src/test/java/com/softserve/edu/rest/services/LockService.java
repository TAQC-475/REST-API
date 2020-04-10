package com.softserve.edu.rest.services;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.dto.EParameters;
import com.softserve.edu.rest.dto.LogginedUser;
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
    protected LogginedUser logginedUser;

    public LockService(LogginedUser logginedUser) {
        lockUserResource = new LockUserResource();
        lockUsersResource = new LockUsersResource();
        lockAdminsResource = new LockAdminsResource();
        this.logginedUser = logginedUser;
    }

    @Step("Locking user with token")
    public LockService lockUser(User userToLock) {
        logger.debug("Locking user named = " + userToLock.getName());
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.NAME, userToLock.getName());
        SimpleEntity simpleEntity = lockUserResource
                .httpPostAsEntity(pathParameters, null, bodyParameters);
        logger.debug("Lock user response = " + simpleEntity.getContent());
        EntityUtils.get().checkLockEntity(simpleEntity, "User was not locked");
        return this;
    }

    @Step("Get all locked users")
    public String getAllLockedUsers() {
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity simpleEntity = lockUsersResource
                .httpGetAsEntity(null, bodyParameters);

        logger.debug("Locked users = "+simpleEntity.getContent());
        return simpleEntity.getContent();
    }

    @Step("Unlock user")
    public LockService unlockUser(User user) {
        logger.debug("unlocking user named = " + user.getName());
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        RestParameters pathParameters = new RestParameters()
                .addParameter(EParameters.NAME, user.getName());
        SimpleEntity simpleEntity = lockUserResource
                .httpPutAsEntity(pathParameters, null, bodyParameters);
        EntityUtils.get().checkLockEntity(simpleEntity, "User was not unlocked");
        logger.debug("unlock user response = " + simpleEntity.getContent());
        return this;
    }

    @Step("Unlock all users")
    public LockService unlockAllUsers() {
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity simpleEntity = lockUsersResource
                .httpPutAsEntity(null, null, bodyParameters);
        logger.debug("Unlock all locked users is DONE");
        return this;
    }

    @Step("Get all locked admins")
    public String getAllLockedAdmins() {
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity simpleEntity = lockUserResource.httpGetAsEntity(null, urlParameters);
        logger.debug("Unlock all locked users is DONE");
        return simpleEntity.getContent();
    }

    @Step("Check if user is locked")
    public boolean isUserLocked(User user) {
        logger.debug("Checking if locked user = "+user.getName());
        if (getAllLockedUsers().contains(user.getName())) {
            return true;
        } else {
            return false;
        }
    }

    @Step("Check if admin is locked")
    public boolean isAdminLocked(User user) {
        logger.debug("Checking if locked admin = "+user.getName());
        if (getAllLockedAdmins().contains(user.getName())) {
            return true;
        } else {
            return false;
        }
    }

}
