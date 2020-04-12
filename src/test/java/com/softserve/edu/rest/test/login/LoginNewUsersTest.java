package com.softserve.edu.rest.test.login;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.services.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class LoginNewUsersTest extends LoginTestRunner{
    private static Logger logger = LoggerFactory.getLogger(LoginNewUserTest.class);

    @Test(dataProvider = "nonExistingUsersDataProvider", dataProviderClass = UsersTestData.class,
    description = "This test verifies that newly created users after successful login" +
            " will be displayed in the loggined users list")
    public void createAndLoginUsersTest(User admin, List<User> nonExistingUsers){
        logger.info("Creation and authorization of users with credentials {}", nonExistingUsers);
        List<User> users = new LoginService()
                .successfulAdminLogin(admin)
                .gotoManageUserService()
                .createUsers(nonExistingUsers)
                .gotoLoginService()
                .successfulUsersLogin(nonExistingUsers)
                .gotoAdministrationService(admin.getName())
                .gotoLogginedUsersService().getLoggedUsers();
        Assert.assertTrue(users.containsAll(nonExistingUsers));
    }

    @Test(dataProvider = "nonExistingAdminsDataProvider", dataProviderClass = UsersTestData.class,
            description = "This test verifies that newly created admins after successful login" +
                    " will be displayed in the loggined admins list")
    public void createAndLoginAdminsTest(User admin, List<User> nonExistingAdmins){
        logger.info("Creation and authorization of admins with credentials {}", nonExistingAdmins);
        List<User> admins = new LoginService()
                .successfulAdminLogin(admin)
                .gotoManageUserService()
                .createUsers(nonExistingAdmins)
                .gotoLoginService()
                .successfulAdminsLogin(nonExistingAdmins)
                .gotoLogginedUsersService().getLoggedAdmins();
        Assert.assertTrue(admins.containsAll(nonExistingAdmins));
    }

   }
