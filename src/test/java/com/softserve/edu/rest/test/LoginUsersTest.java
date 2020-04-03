package com.softserve.edu.rest.test;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class LoginUsersTest extends RestTestRunner{
    @DataProvider(name = "existingUsersDataProvider")
    public Object[][] getExistingUsers(){
        Object[][] objects = {{UserRepository.getAdmin(), UserRepository.getExistingUsers()}};
        return objects;
    }

    @Test(dataProvider = "existingUsersDataProvider")
    public void loginExistingUsersTest(User admin, List<User> existingUsers){
        LogginedUsersService logginedUsersService = new LoginService()
                .successfulUsersLogin(existingUsers)
                .goToLoginService()
                .successfulAdminLogin(admin)
                .gotoLogginedUsersService();

        Assert.assertEquals(logginedUsersService.getLoggedUsers(), existingUsers);
    }

}
