package com.softserve.edu.rest.test.login;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginNewUserTest extends LoginTestRunner {
    private static Logger logger = LoggerFactory.getLogger(LoginNewUserTest.class);

    @Test(dataProvider = "nonExistingUserDataProvider", dataProviderClass = UsersTestData.class,
    description = "This test verifies that newly created user by admin alter successful " +
            "login will be displayed in the loggined users list")
    public void createAndLoginUserTest(User admin, User newUser){
        logger.info("Creation and authorization of user with credential {}", newUser);
        UserService userService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoManageUserService()
                .createUser(newUser)
                .gotoLoginService()
                .successfulUserLogin(newUser);
        Assert.assertEquals(tokenLength, ApplicationState.get().getLastLoggined().getToken().length());
    }

    @Test(dataProvider = "nonExistingAdminDataProvider", dataProviderClass = UsersTestData.class)
    public void createAndLoginAdminTest(User admin, User newAdmin){
        logger.info("Creation and authorization of admin with credentials {}", newAdmin);
        AdministrationService adminService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoManageUserService()
                .createUser(newAdmin)
                .goToLoginService()
                .successfulAdminLogin(newAdmin);
        Assert.assertEquals(tokenLength, ApplicationState.get().getLastLoggined().getToken().length());
    }

}
