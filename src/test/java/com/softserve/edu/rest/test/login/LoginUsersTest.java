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
    @DataProvider(name = "existingUsersDataProvider")
    public Object[][] getExistingUsers(){
        Object[][] objects = {{UserRepository.getAdmin(), UserRepository.getExistingUsers()}};
        return objects;
    }

    @DataProvider(name = "nonExistingUsersDataProvider")
    public Object[][] getNonExistingUsers(){
        Object[][] objects = {{UserRepository.getAdmin(), UserRepository.getNonExistingUsers()}};
        return objects;
    }


    @Test(dataProvider = "existingUsersDataProvider")
    public void loginExistingUsersTest(User admin, List<User> existingUsers){
        LogginedUsersService logginedUsersService = new LoginService()
                .successfulUsersLogin(existingUsers)
                .goToLoginService()
                .successfulAdminLogin(admin)
                .gotoLogginedUsersService();

        Assert.assertTrue(logginedUsersService.getLoggedUsers().containsAll(existingUsers));
    }

    @Test(dataProvider = "nonExistingUsersDataProvider")
    public void createAndLoginUsersTest(User admin, List<User> nonExistingUsers){
        LogginedUsersService logginedUsersService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoManageUserService()
                .createUsers(nonExistingUsers)
                .goToLoginService()
                .successfulUsersLogin(nonExistingUsers)
                .gotoAdministrationService()
                .gotoLogginedUsersService();
        List<User> users = logginedUsersService.getLoggedUsers();
        Assert.assertTrue(logginedUsersService.getLoggedUsers().containsAll(nonExistingUsers));
    }

}
