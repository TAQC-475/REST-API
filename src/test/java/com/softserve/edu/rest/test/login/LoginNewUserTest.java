package com.softserve.edu.rest.test.login;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.data.UserRepository;
import com.softserve.edu.rest.services.*;
import com.softserve.edu.rest.test.RestTestRunner;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

public class LoginNewUserTest extends RestTestRunner {
    private static final int tokenLength = 32;


    @Test(dataProvider = "nonExistingUserDataProvider", dataProviderClass = UsersTestData.class)
    public void createAndLoginUserTest(User admin, User newUser){
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
        AdministrationService adminService = new LoginService()
                .successfulAdminLogin(admin)
                .gotoManageUserService()
                .removeUser(UserRepository.getValidUser())
                .createUser(newAdmin)
                .goToLoginService()
                .successfulAdminLogin(newAdmin);
        Assert.assertEquals(tokenLength, ApplicationState.get().getLastLoggined().getToken().length());
    }

    @AfterMethod()
    public void removeCreated(ITestResult result){
        Object[] inputArgs = result.getParameters();
        User admin = (User) inputArgs[0];
        User newUser = (User) inputArgs[1];
        new LoginService().successfulAdminLogin(admin)
                .gotoManageUserService().removeUser(newUser);
    }

}
