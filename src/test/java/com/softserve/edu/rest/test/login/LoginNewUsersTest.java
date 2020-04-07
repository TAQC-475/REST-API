package com.softserve.edu.rest.test.login;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.services.AdministrationService;
import com.softserve.edu.rest.services.LogginedUsersService;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.test.RestTestRunner;
import com.softserve.edu.rest.tools.CustomException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;

public class LoginNewUsersTest{

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

    @AfterMethod()
    public void removeCreated(ITestResult result) throws CustomException {
        Object[] inputArgs = result.getParameters();
        User admin = (User) inputArgs[0];
        List<User> newUsers = (List<User>) inputArgs[1];
        new LoginService().successfulUsersLogout((ApplicationState.get().getLogginedUsers()))
                .gotoLoginService().successfulAdminLogin(admin)
                .gotoManageUserService()
                .removeUsers(newUsers)
                .goToLoginService()
                .successfulLogout(ApplicationState.get().getLastLoggined());
    }
}
