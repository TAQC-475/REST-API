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
import com.softserve.edu.rest.tools.RegexUtils;
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

    /**
     * Preparing and sending PUT request as logged in admin to lock user or admin
     * @param userToLock item to lock
     * @return LockService after getting response
     */
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

    /**
     * Preparing and sending GET request as logged in admin to get list of all locked users
     * @return string of all locked users after getting response
     */
    @Step("Get all locked users")
    public String getAllLockedUsers() {
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity simpleEntity = lockUsersResource
                .httpGetAsEntity(null, bodyParameters);

        logger.debug("Locked users = "+ RegexUtils.extractNewLinesFromLockedUsers(simpleEntity.getContent()));
        return simpleEntity.getContent();
    }

    /**
     * Preparing and sending PUT request as logged in admin to unlock user
     * @param user item to unlock
     * @return LockService after getting response
     */
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

    /**
     * Preparing and sending PUT request as logged in admin to unlock all users and admins
     * @return LockService after getting response
     */
    @Step("Unlock all users")
    public LockService unlockAllUsers() {
        RestParameters bodyParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity simpleEntity = lockUsersResource
                .httpPutAsEntity(null, null, bodyParameters);
        logger.debug("Unlock all locked users is DONE");
        return this;
    }

    /**
     * Preparing and sending GET request as logged in admin to get list of all locked admins
     * @return string of all locked admins after getting response
     */
    @Step("Get all locked admins")
    public String getAllLockedAdmins() {
        RestParameters urlParameters = new RestParameters()
                .addParameter(EParameters.TOKEN, logginedUser.getToken());
        SimpleEntity simpleEntity = lockUserResource.httpGetAsEntity(null, urlParameters);
        logger.debug("Unlock all locked users is DONE");
        return simpleEntity.getContent();
    }

    /**
     * Checking if out user is in list of locked users
     * @param user is user locked
     * @return boolean
     */
    @Step("Check if user is locked")
    public boolean isUserLocked(User user) {
        logger.debug("Checking if locked user = "+user.getName());
        if (getAllLockedUsers().contains(user.getName())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checking if out admin is in list of locked admins
     * @param admin is admin locked
     * @return boolean
     */
    @Step("Check if admin is locked")
    public boolean isAdminLocked(User admin) {
        logger.debug("Checking if locked admin = "+admin.getName());
        if (getAllLockedAdmins().contains(admin.getName())) {
            return true;
        } else {
            return false;
        }
    }

}
