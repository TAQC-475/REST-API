package com.softserve.edu.rest.test.lock;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.dataproviders.LockData;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.services.GuestService;
import com.softserve.edu.rest.services.LockService;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.tools.EntityUtils;
import io.qameta.allure.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LockNegativeTest {

    public static final Logger logger = LoggerFactory.getLogger(LockService.class);

    @BeforeMethod
    public void resetService() {
        logger.info("BEFORE METHOD reset service to initial state");
        new GuestService().resetServiceToInitialState();
    }

    /**
     * logs in as admin and tries to lock unexciting user
     * verifies that service response will be negative
     *
     * @param admin          for login and locking unexciting user
     * @param unexcitingUser user for locking by admin
     */
    @Description("Verify that logged-in admin can't lock unexciting user")
    @Parameters({"Admin login", "User for locking"})
    @Test(priority = 11, dataProvider = "lockUnexcitingUser", dataProviderClass = LockData.class)
    public void lockUnexcitingUserFromAdmin(User admin, User unexcitingUser) {
        logger.info("START TEST lock user = {}, as admin = {} ", unexcitingUser.getName(), admin.getName());
        SimpleEntity response = new LoginService()
                .successfulAdminLogin(admin)
                .gotoLockService()
                .lockUser(unexcitingUser)
                .getResponse();

        Assert.assertFalse(EntityUtils.isUserActionSuccessful(response));
        logger.info("END OF THE TEST");
    }

    /**
     * logs in as admin and tries to unlock unexciting user
     * verifies that service response will be negative
     *
     * @param admin          for login and unlocking unexciting user
     * @param unexcitingUser user for unlocking by admin
     */
    @Description("Verify that logged-in admin can't unlock unexciting user")
    @Parameters({"Admin login", "User for unlocking"})
    @Test(priority = 12, dataProvider = "lockUnexcitingUser", dataProviderClass = LockData.class)
    public void unlockUnexcitingUserFromAdmin(User admin, User unexcitingUser) {
        logger.info("START TEST unlock user = {}, as admin = {} ", unexcitingUser.getName(), admin.getName());
        SimpleEntity response = new LoginService()
                .successfulAdminLogin(admin)
                .gotoLockService()
                .unlockUser(unexcitingUser)
                .getResponse();

        Assert.assertFalse(EntityUtils.isUserActionSuccessful(response));
        logger.info("END OF THE TEST");
    }

}
