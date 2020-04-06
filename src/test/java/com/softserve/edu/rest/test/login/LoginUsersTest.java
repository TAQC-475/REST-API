package com.softserve.edu.rest.test.login;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.*;
import com.softserve.edu.rest.test.RestTestRunner;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class LoginUsersTest extends RestTestRunner {



    @Test(dataProvider = "existingUsersDataProvider", dataProviderClass = UsersTestData.class)
    public void loginExistingUsersTest(User admin, List<User> existingUsers){
        LogginedUsersService logginedUsersService = new LoginService()
                .successfulUsersLogin(existingUsers)
                .gotoLoginService()
                .successfulAdminLogin(admin)
                .gotoLogginedUsersService();

        Assert.assertTrue(logginedUsersService.getLoggedUsers().containsAll(existingUsers));
    }

    @Test(dataProvider = "nonExistingUsersDataProvider", dataProviderClass = UsersTestData.class)
    public void createAndLoginUsersTest(User admin, List<User> nonExistingUsers){
        LogginedUsersService logginedUsersService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoManageUserService()
                .createUsers(nonExistingUsers)
                .gotoLoginService()
                .successfulUsersLogin(nonExistingUsers)
                .gotoAdministrationService()
                .gotoLogginedUsersService();
        Assert.assertTrue(logginedUsersService.getLoggedUsers().containsAll(nonExistingUsers));
    }

    @Test(dataProvider = "nonExistingAdminsDataProvider", dataProviderClass = UsersTestData.class)
    public void createAndLoginAdminsTest(User admin, List<User> nonExistingAdmins){
        LogginedUsersService logginedUsersService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoManageUserService()
                .createUsers(nonExistingAdmins)
                .gotoLoginService()
                .successfulAdminsLogin(nonExistingAdmins)
                .gotoLogginedUsersService();
        Assert.assertTrue(logginedUsersService.getLoggedAdmins().containsAll(nonExistingAdmins));
    }

}
