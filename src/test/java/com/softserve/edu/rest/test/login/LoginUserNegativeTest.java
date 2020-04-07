package com.softserve.edu.rest.test.login;

import com.softserve.edu.rest.data.User;
import com.softserve.edu.rest.entity.SimpleEntity;
import com.softserve.edu.rest.services.GuestService;
import com.softserve.edu.rest.services.LogginedUsersService;
import com.softserve.edu.rest.services.LoginService;
import com.softserve.edu.rest.test.RestTestRunner;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginUserNegativeTest extends RestTestRunner {

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
}
