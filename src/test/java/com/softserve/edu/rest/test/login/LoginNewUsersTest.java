package com.softserve.edu.rest.test.login;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class LoginNewUsersTest extends LoginTestRunner{

    @Test(dataProvider = "nonExistingUsersDataProvider", dataProviderClass = UsersTestData.class)
    public void createAndLoginUsersTest(User admin, List<User> nonExistingUsers){
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

    @Test(dataProvider = "nonExistingAdminsDataProvider", dataProviderClass = UsersTestData.class)
    public void createAndLoginAdminsTest(User admin, List<User> nonExistingAdmins){
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
