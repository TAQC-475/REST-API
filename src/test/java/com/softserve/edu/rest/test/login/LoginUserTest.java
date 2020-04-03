package com.softserve.edu.rest.test.login;

import com.softserve.edu.rest.data.LogginedUsers;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.AdministrationService;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.services.UserService;
import com.softserve.edu.rest.test.RestTestRunner;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginUserTest extends RestTestRunner {
    private static final short tokenLength = 32;

    @DataProvider(name = "existingUserDataProvider")
    public Object[] getExistingUser(){
        Object[] user = {UserRepository.getValidUser()};
        return user;
    }

    @DataProvider(name = "existingAdminDataProvider")
    public Object[] getExistingAdmin(){
        Object[] admin = {UserRepository.getAdmin()};
        return admin;
    }

    @Test(dataProvider = "existingUserDataProvider")
    public void loginExistingUserTest(User user){
        UserService userService = new LoginService()
                .successfulUserLogin(user);
        Assert.assertEquals(tokenLength, LogginedUsers.get().getLastLoggined().getToken().length());
    }

    @Test(dataProvider = "existingAdminDataProvider")
    public void loginExistingAdminTest(User admin){
        UserService userService = new LoginService()
                .successfulUserLogin(admin);
        Assert.assertEquals(tokenLength, LogginedUsers.get().getLastLoggined().getToken().length());
    }

}
