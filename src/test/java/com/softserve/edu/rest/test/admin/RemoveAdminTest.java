package com.softserve.edu.rest.test.admin;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.data.dataproviders.AdminData;
import com.softserve.edu.rest.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RemoveAdminTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveAdminTest.class);

    @Test(dataProvider = "removeAdminTestData", dataProviderClass = AdminData.class)
    public void removeAdminTest(User admin, User adminVasya) {
        boolean actual = new LoginService()
            .successfulAdminLogin(admin)
            .gotoManageUserService()
            .createUser(adminVasya)
            .gotoManageUserService()
            .removeUser(admin)
            .goToLoginService()
            .successfulAdminLogin(adminVasya)
            .gotoUsersService()
            .isUserPresent(admin);

        Assert.assertFalse(actual);
    }

}
