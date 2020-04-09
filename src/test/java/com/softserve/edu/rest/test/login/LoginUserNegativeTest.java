package com.softserve.edu.rest.test.login;

import com.softserve.edu.rest.data.ApplicationState;
import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.services.GuestService;
import com.softserve.edu.rest.services.LogginedUsersService;
import com.softserve.edu.rest.services.LoginService;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginUserNegativeTest extends LoginTestRunner{

    @Test(dataProviderClass = UsersTestData.class, dataProvider = "nonExistingUserDataProvider")
    public void loginUserNegativeTestAsEntity(User admin, User notExisingUser){
        SimpleEntity simpleEntity = new GuestService()
                .gotoLoginService()
                .unsuccessfulUserLoginAsEntity(notExisingUser);

        Assert.assertEquals(LoginService.INVALID_USER, simpleEntity.getContent());
    }

    @Test(dataProviderClass = UsersTestData.class, dataProvider = "nonExistingUserDataProvider")
    public void loginUserNegativeTest(User admin, User notExisingUser){
        LogginedUsersService logginedUsersService = new GuestService()
                .gotoLoginService()
                .unsuccessfulUserLogin(notExisingUser)
                .successfulAdminLogin(admin)
                .gotoLogginedUsersService();
        Assert.assertFalse(logginedUsersService.getLoggedUsers().contains(notExisingUser));
    }

    @Test(dataProviderClass = UsersTestData.class, dataProvider = "nonExistingAdminDataProvider")
    public void loginAdminNegativeTest(User admin, User notExisingAdmin){
        LogginedUsersService logginedUsersService = new GuestService()
                .gotoLoginService()
                .unsuccessfulUserLogin(notExisingAdmin)
                .successfulAdminLogin(admin)
                .gotoLogginedUsersService();
        Assert.assertFalse(logginedUsersService.getLoggedUsers().contains(notExisingAdmin));
    }

    @Test(dataProviderClass = UsersTestData.class, dataProvider = "existingAdminDataProvider")
    public void logoutWithInvalidTokenTest(User admin) {
        SimpleEntity errorMessage = new LoginService()
                .successfulAdminLogin(admin)
                .goToLoginService()
                .unsuccessfulLogoutAsEntity(ApplicationState.get().getLastLoggined());
        Assert.assertFalse(Boolean.valueOf(errorMessage.getContent()));
    }
}
