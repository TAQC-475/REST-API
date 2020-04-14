package com.softserve.edu.rest.test.admin;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.dataproviders.AdminData;
import com.softserve.edu.rest.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateAdminTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateAdminTest.class);

    @Test(dataProvider = "createAdminTestData", dataProviderClass = AdminData.class)
    public void adminTest(User admin, User adminVasya, User userVasya, User nonExistUser) {
        LOGGER.info("Login as Admin {} , and create new Admin {}", admin, adminVasya);
        boolean actual = new LoginService()
            .successfulAdminLogin(admin)
            .gotoManageUserService()
            .createUser(adminVasya)
            .gotoManageUserService()
            .removeUser(adminVasya)
            .gotoManageUserService()
            .createUser(userVasya)
            .goToLoginService()
            .successfulLogout(ApplicationState.get().getLastLogged())
            .gotoLoginService()
            .successfulAdminLogin(userVasya)
            .gotoManageUserService()
            .createUser(nonExistUser)
            .gotoUsersService()
            .isUserPresent(nonExistUser);

        Assert.assertTrue(actual);
        LOGGER.info("{} user create by fake Admin", nonExistUser);

    }


}
