package com.softserve.edu.rest.test.login;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.services.AdministrationService;
import com.softserve.edu.rest.services.LogginedUsersService;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.util.List;

public class LoginExistingUserTest extends LoginTestRunner{
    private static Logger logger = LoggerFactory.getLogger(LoginExistingUserTest.class);

    @Test(dataProvider = "existingUserDataProvider", dataProviderClass = UsersTestData.class,
            description = "This test verifies that predefined user can successfully login to the system")
    public void loginExistingUserTest(User adminUser, User user){
        logger.info("Login with existing user credentials {}", user);
        UserService userService = new LoginService()
                .successfulUserLogin(user);
        Assert.assertEquals(tokenLength, ApplicationState.get().getLastLoggined().getToken().length());
    }

    @Test(dataProvider = "existingAdminDataProvider", dataProviderClass = UsersTestData.class,
           description = "This test verifies that predefined admin can successfully login to the system")
    public void loginExistingAdminTest(User admin){
        logger.info("Login with existing admin credentials {}", admin);
        AdministrationService adminService = new LoginService()
                .successfulAdminLogin(admin);
        Assert.assertEquals(tokenLength, ApplicationState.get().getLastLoggined().getToken().length());
    }



    @Test(dataProvider = "existingUserDataProvider", dataProviderClass = UsersTestData.class,
            description = "This test verifies that predefined loggined user will be shown to the administrator")
    public void logoutExistingUserTest(User admin, User existingUser){
        logger.info("Login existing user with credentials {}", existingUser);

        List<User> users = new LoginService()
                .successfulUserLogin(existingUser)
                .gotoLoginService()
                .successfulLogout(ApplicationState.get().getLastLoggined())
                .gotoLoginService()
                .successfulAdminLogin(admin)
                .gotoLogginedUsersService()
                .getLoggedUsers();
        Assert.assertFalse(users.contains(existingUser));

        logger.info("All logged users in the system {}", users);

    }

    @Test(dataProvider = "existingUserDataProvider", dataProviderClass = UsersTestData.class,
        description = "This test verifies that existing user can login and logout successfully")
    public void logoutExistingUserTestAsEntity(User admin, User existingUser){
        logger.info("Login and logout for user {}", existingUser);

        SimpleEntity simpleEntity = new LoginService()
                .successfulUserLogin(existingUser)
                .goToLoginService()
                .successfulLogoutAsEntity(ApplicationState.get().getLastLoggined());
        Assert.assertTrue(Boolean.valueOf(simpleEntity.getContent()));

        logger.trace("Logout result {}", simpleEntity.getContent());
    }



    @Test(dataProvider = "existingUsersDataProvider", dataProviderClass = UsersTestData.class,
     description = "This test verifies that multiple predefined loggined users will be shown to the administrator")
    public void loginExistingUsersTest(User admin, List<User> existingUsers){
        logger.info("Login for users with credentials {}", existingUsers);
        List<User> users = new LoginService()
                .successfulUsersLogin(existingUsers)
                .gotoLoginService()
                .successfulAdminLogin(admin)
                .gotoLogginedUsersService()
                .getLoggedUsers();

        Assert.assertTrue(users.containsAll(existingUsers) );
        logger.trace("All loggined users {}", users);


    }

}
