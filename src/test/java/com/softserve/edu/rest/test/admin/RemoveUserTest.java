package com.softserve.edu.rest.test.admin;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.data.dataproviders.AdminData;
import com.softserve.edu.rest.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RemoveUserTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveUserTest.class);

    @Test(dataProvider = "removeUserTestData", dataProviderClass = AdminData.class)
    public void removeUserTest(User admin, User noExistedUser) {
        LOGGER.info("Login as {} create and remove {}", admin, noExistedUser);
        boolean actual = new LoginService()
            .successfulAdminLogin(admin)
            .gotoManageUserService()
            .createUser(noExistedUser)
            .gotoManageUserService()
            .removeUser(noExistedUser)
            .gotoUsersService()
            .isUserPresent(noExistedUser);

        Assert.assertFalse(actual);
        LOGGER.info("check if remove {} missing", noExistedUser);
    }

}
